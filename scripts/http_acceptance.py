"""Real HTTP acceptance checks against the packaged local app (standard library only).
Creates clearly named synthetic accounts/files and retains their audit evidence.
Usage: python scripts/http_acceptance.py [http://127.0.0.1:8080]
"""
import urllib.request,urllib.parse,urllib.error,http.cookiejar
import re,uuid,json,hashlib,sys,os
from pathlib import Path
from datetime import datetime,timezone

BASE=sys.argv[1] if len(sys.argv)>1 else 'http://127.0.0.1:8080'
ROOT=Path(__file__).resolve().parent.parent
EVIDENCE=Path(os.environ.get('FUZZY_TEST_EVIDENCE_DIR',str(ROOT/'evidence')));EVIDENCE.mkdir(parents=True,exist_ok=True)
RESULTS=[]
def check(condition,message):
 if not condition:raise AssertionError(message)
 RESULTS.append(message);print('PASS:',message)
class Client:
 def __init__(self):self.opener=urllib.request.build_opener(urllib.request.HTTPCookieProcessor(http.cookiejar.CookieJar()))
 def call(self,path,data=None,files=None,csrf=True):
  url=path if path.startswith('http') else BASE+path
  headers={}
  if data is not None:
   data=dict(data)
   if csrf:
    page=self.call('/dashboard' if hasattr(self,'logged') else '/login')
    tokens=re.findall(r'name="_csrf"[^>]*value="([^"]+)"',page['text'])
    if not tokens:raise RuntimeError('No CSRF field in '+page['url'])
    data['_csrf']=tokens[0]
   if files:
    boundary='fuzzy-test-'+uuid.uuid4().hex;parts=[]
    for k,v in data.items():parts.append(f'--{boundary}\r\nContent-Disposition: form-data; name="{k}"\r\n\r\n{v}\r\n'.encode())
    for field,name,content,mime in files:
     parts.append(f'--{boundary}\r\nContent-Disposition: form-data; name="{field}"; filename="{name}"\r\nContent-Type: {mime}\r\n\r\n'.encode()+content+b'\r\n')
    body=b''.join(parts)+f'--{boundary}--\r\n'.encode();headers['Content-Type']='multipart/form-data; boundary='+boundary
   else:body=urllib.parse.urlencode(data).encode();headers['Content-Type']='application/x-www-form-urlencoded'
   request=urllib.request.Request(url,body,headers,method='POST')
  else:request=urllib.request.Request(url)
  try:response=self.opener.open(request,timeout=30)
  except urllib.error.HTTPError as error:response=error
  raw=response.read();return {'status':response.code,'url':response.url,'bytes':raw,'text':raw.decode('utf-8',errors='replace')}
 def login(self,email,password=None):
  password=password or os.environ.get('FUZZY_TEST_PASSWORD','Demo@12345')
  result=self.call('/login',{'email':email,'password':password});check(result['url'].endswith('/otp'),'Password gate requires OTP for '+email)
  inbox=self.call('/demo-inbox');code=re.search(r'id="otpCode"[^>]*>(\d{6})<',inbox['text'])[1]
  gate=self.call('/dashboard');check(gate['url'].endswith('/login'),'No full session before OTP for '+email)
  wrong=self.call('/otp',{'code':'bad'});check(wrong['status']==400,'Incorrect OTP denied for '+email)
  result=self.call('/otp',{'code':code});check(result['status']==200 and result['url'].endswith('/dashboard'),'OTP signs in '+email);self.logged=True
  reused=self.call('/otp',{'code':code});check(reused['status']==400,'OTP reuse denied for '+email)
 def fuzzy(self):
  for sample,decision in [('11,22,33,44,55','REJECTED'),('11,11,11,11,11,11','REJECTED'),('11,22,33,44,55,66','ACCEPTED')]:
   r=self.call('/user/fuzzy',{'attributes':sample,'threshold':'1','success':'true'});check(r['status']==200 and f'<strong>{decision}</strong>' in r['text'],'Fuzzy server decision '+decision+' for '+sample)
 def upload(self,name,content,previous=''):
  r=self.call('/user/upload',{'previous':previous},[('file',name,content,'application/octet-stream')]);check(r['status']==200 and '/files/' in r['url'],'Upload encrypted '+name);return r['url'].split('/')[-1]
 def request_audit(self,file):
  r=self.call('/user/files/'+file+'/audit',{'mode':'FULL'});check('/audits/' in r['url'],'User submits full audit');return r['url'].split('/')[-1]
 def state(self,id,state):
  r=self.call('/audits/'+id);check(r['status']==200 and 'class="badge '+state+'"' in r['text'],'Audit '+id[:8]+' state '+state);return r

def main():
 anon=Client();check(anon.call('/dashboard')['url'].endswith('/login'),'Anonymous protected route redirects');check(anon.call('/login',{'email':'alice@demo.local','password':'bad'},csrf=False)['status']==403,'POST without CSRF denied')
 tag=uuid.uuid4().hex[:8];email='http-'+tag+'@demo.local';new=Client()
 fields={'name':'HTTP Synthetic '+tag,'email':email,'password':'Demo@12345','rpassword':'Demo@12345','dob':'2002-05-06','gender':'Other','phone':'0000000000','city':'Synthetic City','country':'Demo Country'}
 signature=(ROOT/'demo-samples/synthetic-signature.png').read_bytes()
 r=new.call('/register',fields,[('signature','signature.png',signature,'image/png')]);check(r['status']==200 and 'Registration saved' in r['text'],'Registration with all legacy fields and signature')
 duplicate=new.call('/register',fields);check(duplicate['status']==400,'Duplicate registration rejected')
 new.login(email);check('PENDING' in new.call('/dashboard')['text'],'New account pending');check(new.call('/user/files')['status']==403,'Pending account file restriction')
 kgc=Client();kgc.login('kgc@demo.local');rows=re.findall(r'<tr>(.*?)</tr>',kgc.call('/dashboard')['text'],re.S);row=next(x for x in rows if email in x);user_id=re.search('/kgc/users/([^"/]+)',row)[1]
 check(new.call('/kgc/users/'+user_id+'/decide',{'decision':'APPROVED'})['status']==403,'Non-KGC approval forbidden')
 profile=kgc.call('/kgc/users/'+user_id);check('Synthetic City' in profile['text'] and 'Demo Country' in profile['text'],'KGC sees optional profile fields');check(kgc.call('/kgc/users/'+user_id+'/signature')['bytes'].startswith(bytes.fromhex('89504e470d0a1a0a')),'KGC retrieves normalized enrolled PNG signature')
 r=kgc.call('/kgc/users/'+user_id+'/decide',{'decision':'APPROVED'});check(r['status']==200,'KGC approves and issues key');check('APP-' in new.call('/dashboard')['text'],'Issued masked application key visible');check('KGC approved' in new.call('/notifications')['text'],'Recipient key issuance notification')
 new.fuzzy();content=bytes(range(256))*600;file=new.upload('http-'+tag+'.bin',content);duplicate_file=new.upload('http-'+tag+'.bin',content);check(file!=duplicate_file,'Same display filename creates distinct objects')
 check(new.call('/user/files/'+file+'/download')['bytes']==content,'Binary download exact bytes')
 check(new.call('/user/upload',{},[('file','../escape.txt',b'a','text/plain')])['status']==400,'Traversal filename rejected')
 check(new.call('/user/upload',{},[('file','empty.txt',b'','text/plain')])['status']==400,'Empty upload rejected')
 check(new.call('/user/upload',{},[('file','huge.bin',bytes(10485761),'application/octet-stream')])['status']==413,'Oversized upload rejected with 413')
 bob=Client();bob.login('bob@demo.local');bob.fuzzy();check(bob.call('/user/files/'+file+'/download')['status']==403,'Bob cannot download before sharing')
 check(bob.call('/user/files/'+file+'/request-access',{})['status']==200,'Bob requests file access');check(bob.call('/user/files/'+file+'/request-access',{})['status']==400,'Duplicate sharing request denied')
 row=next(x for x in re.findall(r'<tr>(.*?)</tr>',new.call('/user/shares')['text'],re.S) if 'http-'+tag+'.bin' in x);share=re.search('/user/shares/([^"/]+)/decide',row)[1]
 check(bob.call('/user/shares/'+share+'/decide',{'decision':'APPROVED'})['status']==403,'Nonowner cannot approve access');check(new.call('/user/shares/'+share+'/decide',{'decision':'APPROVED'})['status']==200,'Owner approves access');check(bob.call('/user/files/'+file+'/download')['bytes']==content,'Approved Bob download exact bytes');check('Access APPROVED' in bob.call('/notifications')['text'],'Recipient access/verification-object notice')
 check(new.call('/user/shares/'+share+'/decide',{'decision':'REVOKED'})['status']==200,'Owner revokes access');check(bob.call('/user/files/'+file+'/download')['status']==403,'Revoked access denied')
 bob.call('/user/files/'+duplicate_file+'/request-access',{});rows=re.findall(r'<tr>(.*?)</tr>',new.call('/user/shares')['text'],re.S);pending=next(x for x in rows if 'PENDING' in x and 'http-'+tag+'.bin' in x);reject_id=re.search('/user/shares/([^"/]+)/decide',pending)[1];new.call('/user/shares/'+reject_id+'/decide',{'decision':'REJECTED'});check(bob.call('/user/files/'+duplicate_file+'/download')['status']==403,'Rejected access denied')
 check(bob.call('/user/files/'+file+'/audit',{'mode':'FULL'})['status']==403,'Nonowner audit forbidden')
 tpa=Client();tpa.login('tpa@demo.local');cloud=Client();cloud.login('cloud@demo.local')
 def complete(expected):
  audit=new.request_audit(file);check(tpa.call('/tpa/audits/'+audit+'/challenge',{})['status']==200,'TPA creates challenge');check(tpa.call('/tpa/audits/'+audit+'/challenge',{})['status']==400,'Repeated challenge rejected');check(cloud.call('/cloud/audits/'+audit+'/respond',{})['status']==200,'Cloud reads current bytes');check(tpa.call('/tpa/audits/'+audit+'/verify',{})['status']==200,'TPA verifies server-side');result=new.state(audit,expected);check(tpa.call('/tpa/audits/'+audit+'/verify',{})['status']==400,'Terminal verification replay denied');return audit,result
 passed,_=complete('VERIFIED_PASS');check(cloud.call('/user/files/'+file+'/download')['status']==403,'Cloud cannot obtain plaintext');check('expectedDigest' not in cloud.call('/audits/'+passed)['text'],'Cloud audit page excludes trusted expected digests')
 check(cloud.call('/cloud/files/'+file+'/tamper',{'block':'0'})['status']==200,'Cloud demo flips real byte');failed,result=complete('VERIFIED_FAIL');check('MISMATCH' in result['text'],'Failed block digest evidence visible');check(new.call('/user/files/'+file+'/download')['status']==400,'Corrupt file authenticated download blocked')
 recovered=new.upload('http-'+tag+'.bin',content,file);check(new.call('/user/files/'+recovered+'/download')['bytes']==content,'Recovery new version exact-byte download');new.state(failed,'VERIFIED_FAIL');check(new.call('/audits/'+failed)['status']==200,'Failed audit history retained')
 for page in ['/dashboard','/cloud/files','/performance','/notifications','/about']:
  check(cloud.call(page)['status']==200,'Cloud navigation '+page)
 for asset in ['/style.css','/app.js']:check(anon.call(asset)['status']==200,'Local asset '+asset)
 state={'email':email,'user_id':user_id,'file':file,'recovered':recovered,'passed':passed,'failed':failed,'sha256':hashlib.sha256(content).hexdigest(),'database': 'MySQL' if 'MySQL' in new.call('/about')['text'] else 'H2'}
 (EVIDENCE/'restart-state.json').write_text(json.dumps(state,indent=2));(EVIDENCE/'http-acceptance.json').write_text(json.dumps({'timestamp':datetime.now(timezone.utc).isoformat(),'base':BASE,'checks':RESULTS,'state':state},indent=2));print('Executed',len(RESULTS),'successful assertions')

if __name__=='__main__':main()
