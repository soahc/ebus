#python
import subprocess
import os 
import sys
import shutil
from time import *

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
    print strftime(init+" (%H:%M:%S) ", localtime())+txt

if sys.version_info < (2, 7):
	log ('Python Version must be >= 2.7')

log ('BuildScript v1.0','***')
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
	print 'cant find servicemix on '+smxPath
	quit()

log ('* start deployment')

for pathentry in os.walk(deployPath, False):
	for file in pathentry[2]:
		path = os.path.join(pathentry[0], file)
		os.unlink(path)

for artefect in bundles:
	jarName = artefect.getJar()
	fileSrc = artefect.artifactId+'/target/'+jarName
	fileDst = deployPath+jarName
	log ("- deploy "+artefect.getJar())
	shutil.copy(fileSrc, fileDst)
	
log ('deployment successfull')
