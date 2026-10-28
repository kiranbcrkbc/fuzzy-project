"""Run AFTER restarting the packaged server. Reads synthetic state from http_acceptance.py."""
from http_acceptance import Client,check,ROOT,RESULTS,EVIDENCE
import json,hashlib
state=json.loads((EVIDENCE/'restart-state.json').read_text())
user=Client();user.login(state['email']);user.fuzzy()
dashboard=user.call('/dashboard');check('APPROVED' in dashboard['text'] and 'APP-' in dashboard['text'],'Restart preserved account approval and application key ID')
data=user.call('/user/files/'+state['recovered']+'/download')['bytes'];check(hashlib.sha256(data).hexdigest()==state['sha256'],'Restart preserved ciphertext, wrapped key and master-key decryption')
user.state(state['passed'],'VERIFIED_PASS');user.state(state['failed'],'VERIFIED_FAIL')
check(user.call('/user/files/'+state['file']+'/download')['status']==400,'Restart retains corrupt old version and download rejection')
check('REVOKED' in user.call('/user/shares')['text'] and 'REJECTED' in user.call('/user/shares')['text'],'Restart retained access decisions')
check('KGC approved' in user.call('/notifications')['text'],'Restart retained recipient notifications')
before=user.call('/user/files')['text'];new=user.upload('restart-new.txt',b'Synthetic restart persistence check\n');after=user.call('/user/files')['text'];check(state['recovered'] in after and state['file'] in after and new in after,'New upload does not reset existing records')
(EVIDENCE/'restart-check.json').write_text(json.dumps({'checks':RESULTS,'state':state},indent=2));print('Restart checks passed:',len(RESULTS))
