#python
import subprocess
import os 
import sys
import shutil
import time
import getpass
import grp
import pwd
import urllib2
import tarfile

# -----------------------------------------------------

class Artefect:
	def __init__(self, groupId, artifactId, version):
		self.groupId = groupId
		self.artifactId = artifactId
		self.version = version
	def getJar(self):
		return self.artifactId+"-"+self.version+".jar"

bundles = [
Artefect("de.ebus.emarket","persistence","0.0.1-SNAPSHOT"),
Artefect("de.ebus.emarket","api","0.0.1-SNAPSHOT"),
Artefect("de.ebus.emarket","server","0.0.1-SNAPSHOT"),
Artefect("de.ebus.emarket","frontend","0.0.1-SNAPSHOT"),
]

# -----------------------------------------------------

def log(txt,init="---"):
    print(time.strftime(init+" (%H:%M:%S) ", time.localtime())+txt)

def downloadFile(url,path):
	file_name = url.split('/')[-1]
	filepath=path+file_name
	u = urllib2.urlopen(url)
	f = open(filepath, 'wb')
	meta = u.info()
	file_size = int(meta.getheaders("Content-Length")[0])
	print "Downloading: %s Bytes: %s" % (filepath, file_size)
	#cdos.system('cls')
	file_size_dl = 0
	block_sz = 8192
	while True:
		buffer = u.read(block_sz)
		if not buffer:
			break

		file_size_dl += len(buffer)
		f.write(buffer)
		status = r"%10d  [%3.2f%%]" % (file_size_dl, file_size_dl * 100. / file_size)
		status = status + chr(8)*(len(status)+1)
		print status,
	f.close()	
	return filepath

# -----------------------------------------------------

if sys.version_info < (2, 7):
	log ('Python Version must be >= 2.7')

log ('BuildScript v1.0','***')

user = getpass.getuser()
isPosix = (os.name == 'posix')
smxURL = 'http://psg.mtu.edu/pub/apache/servicemix/servicemix-4/4.5.3/apache-servicemix-4.5.3.tar.gz' if (isPosix) else 'http://apache.mirrors.hoobly.com/servicemix/servicemix-4/4.5.3/apache-servicemix-4.5.3.zip'
optPath = '/opt2/' if (isPosix) else 'c:/'
smxPath = optPath+'apache-servicemix/'
deployPath = smxPath+'deploy/'

if len(sys.argv)>1:
	if sys.argv[1]=='installFeatures':
		
		if not os.path.isdir(optPath):
			if isPosix:
				log ('* create folder '+optPath)
				subprocess.call(['sudo', 'mkdir', optPath])

		stat_info = os.stat(optPath)

		if not pwd.getpwuid(stat_info.st_uid)[0] == user:
			if isPosix:
				subprocess.call(['sudo', 'chown', user, optPath])

		if not os.path.isdir(smxPath):
			log ('* download servicemix archive')
			archive = downloadFile(smxURL,optPath)
			log ('* extract servicemix archive')
			tar = tarfile.open(archive)
			tar.extractall(optPath)
			tar.close()

quit()

# -----------------------------------------------------


log ('* run mvn build')

try:
	subprocess.check_output("mvn install", shell=True, stderr=subprocess.STDOUT)
except subprocess.CalledProcessError as e:
	log ('build failed','>>>')
	quit()

log ('build successfull')

# -----------------------------------------------------

isPosix = (os.name == 'posix')
smxPath = '/opt/apache-servicemix/' if (isPosix) else 'c:/apache-servicemix/';
deployPath = smxPath+'deploy/'

if not os.path.isdir(deployPath):
	print ('cant find servicemix on '+smxPath)
	quit()

log ('* start deployment')

for pathentry in os.walk(deployPath, False):
	for file in pathentry[2]:
		path = os.path.join(pathentry[0], file)
		os.unlink(path)

if len(sys.argv)>1:
	if sys.argv[1]=='installFeatures':
		log ("- deploy feature.xml")
		shutil.copy("features.xml", deployPath+"features.xml" )
		time.sleep(2)

for artefect in bundles:
	jarName = artefect.getJar()
	fileSrc = artefect.artifactId+'/target/'+jarName
	fileDst = deployPath+jarName
	log ("- deploy "+artefect.getJar())
	shutil.copy(fileSrc, fileDst)
	
log ('deployment successfull')
