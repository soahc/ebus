#python
import subprocess
import os 
import sys
from time import *

def log(txt,init="---"):
    print strftime(init+" (%H:%M:%S) ", localtime())+txt

def updateBunde(ssh, bundleIdent):
	ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command("bundle:install mvn:"+bundleIdent)
	output = ssh_stdout.read()
	bundleid = output[output.index(':')+2:]
	ssh.exec_command("bundle:update "+bundleid)
	log ("update bundle: "+bundleIdent,"---")

if sys.version_info < (2, 7):
	log ('Python Version must be >= 2.7')

try: 
	import paramiko
except ImportError:
	log ('lib paramiko is required, but not found -> http://www.lag.net/paramiko/','>>>')
	quit()

#------------------------------------------------------

log ('BuildScript v1.0','***')
log ('* run mvn build')

try:
	subprocess.check_output("mvn install", shell=True, stderr=subprocess.STDOUT)
except subprocess.CalledProcessError as e:
	log ('build failed','>>>')
	quit()

log ('build success')

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.load_host_keys(os.path.expanduser(os.path.join("~", ".ssh", "known_hosts")))

log ('* connect servicemix via ssh')
try:
	ssh.connect('localhost', port=8101, username='smx', password='smx')

	updateBunde(ssh,"de.ebus.emarket/persistence/0.0.1-SNAPSHOT")
	updateBunde(ssh,"de.ebus.emarket/api/0.0.1-SNAPSHOT")
	updateBunde(ssh,"de.ebus.emarket/server/0.0.1-SNAPSHOT")
	updateBunde(ssh,"de.ebus.emarket/frontend/0.0.1-SNAPSHOT")

except:
	log ('connection refused - maybe servicemix not running','>>>') 
ssh.close()

