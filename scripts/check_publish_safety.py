"""Inspect the exact staged tree. Never print matching secret values."""
from pathlib import Path
import subprocess,re,json
files=subprocess.check_output(['git','diff','--cached','--name-only','-z']).decode().split('\0')
files=[f for f in files if f]
forbidden=[];suspect=[]
patterns=[rb'gh[pousr]_[A-Za-z0-9]{20,}',rb'github_pat_[A-Za-z0-9_]{20,}',rb'AKIA[0-9A-Z]{16}',rb'-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----']
for f in files:
 if f.startswith(('data/','references/','report/','report-assets/','evidence/','app/builds/','app/target/','app/dist/')) or f.endswith(('.docx','.pdf','.zip','.mv.db','.key')):forbidden.append(f)
 blob=subprocess.check_output(['git','show',':'+f])
 if any(re.search(p,blob) for p in patterns):suspect.append(f)
result={'staged_count':len(files),'forbidden_paths':forbidden,'credential_pattern_files':suspect,'safe':not forbidden and not suspect}
Path('evidence/deployment/publish-safety.json').write_text(json.dumps(result,indent=2))
print(json.dumps(result,indent=2))
if not result['safe']:raise SystemExit(1)
