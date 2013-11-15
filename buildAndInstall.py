#python
import subprocess
import os 
import paramiko 
from time import *

def log(txt,init="---"):
    print strftime(init+" (%H:%M:%S) ", localtime())+txt

def updateBunde(ssh, bundleIdent):
	ssh_stdin, ssh_stdout, ssh_stderr = ssh.exec_command(bundleIdent)
	output = ssh_stdout.read()
	bundleid = output[output.index(':')+2:]
	ssh.exec_command("bundle:update "+bundleid)
	log ("update: "+bundleIdent,"---")

log ('run mvn build','***')

mvnoutput = subprocess.check_output("mvn install", shell=True, stderr=subprocess.STDOUT)
if (mvnoutput.count("BUILD SUCCESS")==0):
	log ('fehler')
	quit()

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.load_host_keys(os.path.expanduser(os.path.join("~", ".ssh", "known_hosts")))

ssh.connect('localhost', port=8101, username='smx', password='smx')

updateBunde(ssh,"bundle:install mvn:de.ebus.emarket/persistence/0.0.1-SNAPSHOT")
updateBunde(ssh,"bundle:install mvn:de.ebus.emarket/api/0.0.1-SNAPSHOT")
updateBunde(ssh,"bundle:install mvn:de.ebus.emarket/server/0.0.1-SNAPSHOT")
updateBunde(ssh,"bundle:install mvn:de.ebus.emarket/frontend/0.0.1-SNAPSHOT")

ssh.close()

log ('end','***')