import exec.Query;
import exec.Parser;
import store.Graph;
import exec.Execute;
	
public class Console {


	// get the result and display



	public static void main(String[] args) {
		
		// read the query 
		Parser p = new Parser();
		Query  q = p.parseFromFile("./queries/query1.json");

		// read the dataset
		Graph g = new Graph();
		DatasetParser dp = new DatasetParser(g, "./datasets/1/");
		dp.parse();

		// System.out.println(g);

		// execute the query 
		Execute ex = new Execute(g, q);		
		ex.getResults("var1");


	}

}