import exec.Query;
import exec.Parser;
import store.Graph;
import exec.Execute;

public class Console {


	// get the result and display



	public static void main(String[] args) {
		
		// read the query 
		Parser p = new Parser();
		Query  q = p.parseFromFile("./query.json");

		// read the dataset
		Graph g = new Graph();
		DatasetParser dp = new DatasetParser(g);
		dp.parseFromFile("./amazon0505");

		// execute the query 
		Execute ex = new Execute(g, q);


		// System.out.println(q);

	}

}