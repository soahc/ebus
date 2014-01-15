#!/usr/bin/python
import subprocess
import os 
import sys
import shutil
import time
import thread 
import getpass
import urllib2
import tarfile
import zipfile
import platform
import webbrowser
import socket
isPosix = (os.name == 'posix')
if (isPosix):
	import grp
	import pwd
	
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

def progressSleep(seconds):
	for i in range(seconds):
		sys.stdout.write("\r%d%%" %((100/seconds)*i))
		sys.stdout.flush()
		time.sleep(1)
	sys.stdout.write("\r%d%%" %100)
	print("")

def unzip(source_filename, dest_dir):
    with zipfile.ZipFile(source_filename) as zf:
        zf.extractall(dest_dir)

def downloadFile(url,path):
	file_name = url.split('/')[-1]
	filepath=path+file_name
	u = urllib2.urlopen(url)
	f = open(filepath, 'wb')
	meta = u.info()
	file_size = int(meta.getheaders("Content-Length")[0])
	print "Downloading: %s Bytes: %s" % (filepath, file_size)
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

def startWindowsSMX(smxPath):
	os.system("start cmd /c "+smxPath+"bin/servicemix.bat")

splitext = os.path.splitext

# -----------------------------------------------------

user = getpass.getuser()
dist=platform.dist()[0]
plattform=platform.platform()
smxURL = 'http://www.eng.lsu.edu/mirrors/apache/servicemix/servicemix-4/4.5.3/apache-servicemix-full-4.5.3.tar.gz' if (isPosix) else 'http://mirror.nexcess.net/apache/servicemix/servicemix-4/4.5.3/apache-servicemix-full-4.5.3.zip'
optPath = '/opt2/' if (isPosix) else 'c:/opt2/'
dataPath = '/data/' if (isPosix) else 'c:/data/'
extractPath = optPath+'/apache-servicemix-4.5.3/'
emarketPath = dataPath+'emarket'
smxPath = optPath+'apache-servicemix/'
deployPath = smxPath+'deploy/'
installing = False

# -----------------------------------------------------

print("")
if sys.version_info < (2, 7):
	log ('Python Version must be >= 2.7')
	quit()

log ('BuildScript v1.0','***')

if ((len(sys.argv)==1) or (len(sys.argv)>2)):
	print("\nparameter: ")
	print(" installFeatures - downloads servicemix, deploys features, deploys emarket bundles")
	print(" redeploy        - redeploy emarket bundles")
	quit()

if len(sys.argv)>1:
	if sys.argv[1]=='installFeatures':
		
		installing = True

		if not os.path.isdir(optPath):
			log ('* create folder '+optPath)
			if (isPosix):
				subprocess.call(['sudo', 'mkdir', optPath])
				if not os.path.isdir(dataPath):
					subprocess.call(['sudo', 'mkdir', dataPath])
				stat_info = os.stat(optPath)
				if not pwd.getpwuid(stat_info.st_uid)[0] == user:
					subprocess.call(['sudo', 'chown', user, optPath])
					subprocess.call(['sudo', 'chown', user, dataPath])

			else:
				os.makedirs(optPath)
				if not os.path.isdir(dataPath):
					os.makedirs(dataPath)

			log ('* create folder '+emarketPath)
			os.makedirs(emarketPath)

		if not os.path.isdir(smxPath):
			log ('* download servicemix archive')
			archive = downloadFile(smxURL,optPath) #optPath+'apache-servicemix-full-4.5.3.zip' 
			log ('* extract servicemix archive')
			if isPosix:
				tar = tarfile.open(archive)
				tar.extractall(optPath)
				tar.close()
			else:
				unzip(archive,optPath)

			os.rename(extractPath,smxPath)
			os.remove(archive)

		log ('* run servicemix')
		if isPosix: 
			if (dist == 'Ubuntu'):
				subprocess.Popen(["gnome-terminal --command='"+smxPath+"bin/servicemix"+"'"], shell=True)

			if 'Darwin' in plattform:
				subprocess.call(["open" ,"-a","Terminal",smxPath+"bin/servicemix"]);
		else:
			thread.start_new_thread(startWindowsSMX,(smxPath,))

		progressSleep(5)
		log ("- deploy feature.xml")
		shutil.copy("features.xml", deployPath+"features.xml" )
		progressSleep(30)

# -----------------------------------------------------

log ('* run mvn build')

try:
	subprocess.check_output("mvn install", shell=True, stderr=subprocess.STDOUT)
except subprocess.CalledProcessError as e:
	log ('build failed','>>>')
	quit()

log ('build successfull')

# -----------------------------------------------------

if not os.path.isdir(deployPath):
	print ('cant find servicemix on '+smxPath)
	quit()

log ('* start deployment')

if not installing:
	for pathentry in os.walk(deployPath, False):
		for file in pathentry[2]:
			if (file!='features.xml'):
				path = os.path.join(pathentry[0], file)
				os.unlink(path)

for artefect in bundles:
	jarName = artefect.getJar()
	fileSrc = artefect.artifactId+'/target/'+jarName
	fileDst = deployPath+jarName
	log ("- deploy "+artefect.getJar())
	#log ("-> "+fileSrc + " -> " + fileDst)
	shutil.copy(fileSrc, fileDst)
	time.sleep(1)
	
log ('* deployment successfull')

try:
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	s.connect(("127.0.0.1", 5432))
	s.close()
except socket.error as e:
	log ("cant connect postgres sql on localhost:5432","!!!")

if installing:
	log ('* prepare to open browser')
	progressSleep(10)
	webbrowser.open("http://localhost:8181")

# -----------------------------------------------------


