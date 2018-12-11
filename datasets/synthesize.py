import random
import string
import json
import os

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


if __name__ == "__main__" : 

	foldername = "dataset-" + str(int(random.random()*100)) + "/"

	if not os.path.exists(foldername) :
		os.mkdir(foldername)

	print "TOPO"

	topology = { "connections" : list() }

	for i in range(1,VERTICES + 1) :
		cons = CON_MIN + int(random.random()*(CON_MAX - CON_MIN))
		for j in range(0,cons) :
			dst = 1 + CON_MIN + int(random.random()*(VERTICES - 1))
			topology['connections'].append([i, dst, GLOBAL_E])
			GLOBAL_E += 1

	with open(foldername + 'topology.json', 'w') as f:
		json.dump(topology, f)




	print "EDGE"

	ePropKeys = set()
	while(len(ePropKeys) < EPROP) :
		ePropKeys.add(''.join(random.choice(string.ascii_lowercase) for _ in range(4)))
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
		prevprops = set();
		for j in range(0, nprops) :
			propIndex = int(random.random()*len(ePropKeys))
			while(propIndex in prevprops) :
				propIndex = int(random.random()*len(ePropKeys))
			prevprops.add(propIndex)
			key = ePropKeys[propIndex]
			typ = ePropKeysType[propIndex]
			if typ == 0 :
				val = ''.join(random.choice(string.ascii_lowercase) \
						for _ in range(int(random.random()*50)))
			else :
				val = int(random.random()*typ) - (typ/2)

			eProps["properties"].append({ "eid" : i, "key" : key, "val" : val })


	with open(foldername + 'eprops.json', 'w') as f:
		json.dump(eProps, f)





	print "VERT"

	vPropKeys = set()
	while(len(vPropKeys) < VPROP) :
		vPropKeys.add(''.join(random.choice(string.ascii_lowercase) for _ in range(4)))
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
		prevprops = set();
		for j in range(0, nprops) :
			propIndex = int(random.random()*len(vPropKeys))
			while(propIndex in prevprops) :
				propIndex = int(random.random()*len(vPropKeys))
			prevprops.add(propIndex)
			key = vPropKeys[propIndex]
			typ = vPropKeysType[propIndex]
			if typ == 0 :
				val = ''.join(random.choice(string.ascii_lowercase) \
						for _ in range(int(random.random()*50)))
			else :
				val = int(random.random()*typ) - (typ/2)

			vProps["properties"].append({ "vid" : i, "key" : key, "val" : val })


	with open(foldername + 'vprops.json', 'w') as f:
		json.dump(vProps, f)


	with open(foldername + 'metadata.txt', 'w') as f:
		f.write("\n")
		f.write("#VERTICES\n" + str(VERTICES) + "\n\n")
		f.write("#EDGES\n" + str(GLOBAL_E) + "\n\n")
		f.write("#UNIQUE VERTEX PROPERTY\n" + str(VPROP) + "\n\n")
		f.write("#UNIQUE EDGE PROPERTY\n" + str(EPROP) + "\n\n")
		f.write("#PROPERTIES PER EDGE\n" + str(EPROP_MIN) + " - " + str(EPROP_MAX) + "\n\n")
		f.write("#PROPERTIES PER VERTEX\n" + str(VPROP_MIN) + " - " + str(VPROP_MAX) + "\n\n")

		f.write("#VERTEX PROPERTIES\n")

		for i in range(0, len(vPropKeys) ) : 
			if vPropKeysType[i] == 0 :
				f.write(" - '" + vPropKeys[i] + "' (string)\n")
			else :
				f.write(" - '" + vPropKeys[i] + "' (int), |val| = " + \
							str(vPropKeysType[i]/2) + "\n")


		f.write("\n#EDGE PROPERTIES\n")

		for i in range(0, len(ePropKeys) ) : 
			if ePropKeysType[i] == 0 :
				f.write(" - '" + ePropKeys[i] + "' (string)\n")
			else :
				f.write(" - '" + ePropKeys[i] + "' (int), |val| = " + \
							str(ePropKeysType[i]/2) + "\n")














