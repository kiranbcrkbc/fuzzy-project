"""Audit all reachable Git trees/blobs; reports metadata, never secret values."""
from pathlib import Path
import subprocess,json,re,hashlib
def git(*args):return subprocess.check_output(['git',*args])
commits=git('rev-list','--all','--reflog').decode().splitlines()
blobs={};bad_paths=[];credential_candidates=[];manifest=[]
for commit in commits:
 for row in git('ls-tree','-r','-z',commit).split(b'\0'):
  if not row:continue
  meta,path=row.split(b'\t',1);mode,kind,oid=meta.decode().split();name=path.decode()
  if kind!='blob':continue
  blobs.setdefault(oid,set()).add(name)
  if name.startswith(('references/','data/','app/data/','evidence/','report-assets/','report/','app/target/','app/builds/','app/dist/')) or name.endswith(('.key','.pem','.p12','.pfx','.mv.db','.dump','.zip','.docx','.pdf')):bad_paths.append({'commit':commit,'path':name})
token_patterns=[rb'gh[pousr]_[A-Za-z0-9]{20,}',rb'github_pat_[A-Za-z0-9_]{20,}',rb'AKIA[0-9A-Z]{16}',rb'-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----',rb'(?i)jdbc:mysql:[^\s\"\x27]+:[^\s\"\x27]+@']
for oid,paths in blobs.items():
 data=git('cat-file','blob',oid)
 if any(re.search(p,data) for p in token_patterns):credential_candidates.append({'blob':oid,'paths':sorted(paths),'reason':'Credential/private-key pattern; value withheld'})
 manifest.append({'blob':oid,'paths':sorted(paths),'size':len(data),'sha256':hashlib.sha256(data).hexdigest()})
current=git('ls-files','-z').decode().split('\0');current=[x for x in current if x]
ignore_samples=['data/trusted/mysql-env.ps1','data/trusted/master.key','data/hosted-validation/trusted/fuzzy.mv.db','references/Fuzzy_Project.zip','report-assets/chapter-8/Figure_8_10_Audit_PASS.png','FUZZY_PROJECT_Final_Report.docx']
ignored=subprocess.run(['git','check-ignore',*ignore_samples],capture_output=True,text=True).stdout.splitlines()
r={'commits':commits,'unique_history_blobs':len(blobs),'current_tracked_files':len(current),'forbidden_history_paths':bad_paths,'credential_candidates':credential_candidates,'ignore_samples_all_excluded':set(ignored)==set(ignore_samples),'history_blob_manifest':manifest,'safe_structural_scan':not bad_paths and not credential_candidates and set(ignored)==set(ignore_samples)}
out=Path('evidence/deployment/public-audit');out.mkdir(parents=True,exist_ok=True);(out/'history-structure.json').write_text(json.dumps(r,indent=2))
print(json.dumps({k:v for k,v in r.items() if k!='history_blob_manifest'},indent=2))
if not r['safe_structural_scan']:raise SystemExit(1)
