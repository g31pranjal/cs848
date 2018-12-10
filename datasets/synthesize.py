import random
import string
import json

VERTICES = 1000;

CON_MIN = 25;
CON_MAX = 100;

VPROP = 25
VPROP_MIN = 2;
VPROP_MAX = 15;

EPROP = 25
EPROP_MIN = 1;
EPROP_MAX = 10;

GLOBAL_V = 1;
GLOBAL_E = 1;




print "TOPO"

topology = { "connections" : list() }

for i in range(1,VERTICES + 1) :
	cons = CON_MIN + int(random.random()*(CON_MAX - CON_MIN))
	for j in range(0,cons) :
		dst = 1 + CON_MIN + int(random.random()*(VERTICES - 1))
		topology['connections'].append([i, dst, GLOBAL_E])
		GLOBAL_E += 1

with open('topology.json', 'w') as f:
    json.dump(topology, f)




print "EDGE"

ePropKeys = set()
while(len(ePropKeys) < EPROP) :
	ePropKeys.add(''.join(random.choice(string.ascii_uppercase) for _ in range(4)))
ePropKeys = list(ePropKeys)
ePropKeysType = list()

for i in range(0, len(ePropKeys)) :
	if random.random() > 0.75 :
		ePropKeysType.append(0)
	else :
		ePropKeysType.append(int(random.random()*2000))

print ePropKeysType

eProps = { "properties" : list() }

for i in range(1, GLOBAL_E) :
	nprops = EPROP_MIN + int(random.random()*(EPROP_MAX - EPROP_MIN))
	for j in range(0, nprops) :
		propIndex = int(random.random()*len(ePropKeys))
		key = ePropKeys[propIndex]
		typ = ePropKeysType[propIndex]
		if typ == 0 :
			val = ''.join(random.choice(string.ascii_lowercase) \
					for _ in range(int(random.random()*50)))
		else :
			val = int(random.random()*typ) - (typ/2)

		eProps["properties"].append({ "eid" : i, "key" : key, "val" : val })


with open('eprops.json', 'w') as f:
    json.dump(eProps, f)





print "VERT"

vPropKeys = set()
while(len(vPropKeys) < VPROP) :
	vPropKeys.add(''.join(random.choice(string.ascii_uppercase) for _ in range(4)))
vPropKeys = list(vPropKeys)
vPropKeysType = list()

for i in range(0, len(vPropKeys)) :
	if random.random() > 0.75 :
		vPropKeysType.append(0)
	else :
		vPropKeysType.append(int(random.random()*2000))

print vPropKeysType

vProps = { "properties" : list() }

for i in range(1, VERTICES + 1) :
	nprops = VPROP_MIN + int(random.random()*(VPROP_MAX - VPROP_MIN))
	for j in range(0, nprops) :
		propIndex = int(random.random()*len(vPropKeys))
		key = vPropKeys[propIndex]
		typ = vPropKeysType[propIndex]
		if typ == 0 :
			val = ''.join(random.choice(string.ascii_lowercase) \
					for _ in range(int(random.random()*50)))
		else :
			val = int(random.random()*typ) - (typ/2)

		vProps["properties"].append({ "vid" : i, "key" : key, "val" : val })


with open('vprops.json', 'w') as f:
    json.dump(vProps, f)








