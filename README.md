# Exploring storage schemes for property graphs.


* Templates for query classes Q1, Q2, Q3 are present in 'queries/'
* Datasets are kept in 'dataset/'
* To generate a synthetic dataset, setup the configuration in 'dataset/synthesize.py'
* Run 'python synthesize.py' in 'dataset/' to create a synthetic dataset.
* Setup the query path and dataset path in 'src/Console.java'
* 'build/' contains the dependency JSON file that has to loaded in the directory.

#### Steps:
1. Run the Makefile in the root directory to compile the project
		
		make

2. Run the projects with argument **v** (for native Graphflow storage), **b** (for B+TreeOnPropKey structure), or **c** (for B+TreeOnPropKeyVal structure)

		java -classpath build/ Console {v|b|c}

 