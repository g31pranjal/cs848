import exec.Query;
import exec.Parser;
import store.GTopology;

public class Console {

	// read the dataset
	// execute the query 

	// get the result and display



	public static void main(String[] args) {
		
		// read the query 
		// Parser p = new Parser();
		// Query  q = p.parseFromFile("./query.json");

		GTopology g = new GTopology();
		g.add(2, 4);
		g.add(2, 6);
		g.add(6, 7);

		System.out.println(g);		



		// System.out.println(q);

	}

}