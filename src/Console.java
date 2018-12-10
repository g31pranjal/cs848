import exec.Query;
import exec.Parser;
import store.Graph;
import store.GraphVanilla;
import store.GraphBTree;
import exec.Execute;
import java.time.Instant;
import java.time.Duration;

public class Console {

	public static void main(String[] args) {
		
		// read the query 
		Parser p = new Parser();
		Query  q = p.parseFromFile("./queries/query3.json");
		Graph g;

		// read the dataset
		if(args.length > 0 && args[0].equals("v"))
			g = new GraphVanilla();
		else if(args.length > 0 && args[0].equals("b"))
			g = new GraphBTree();
		else
			g = new GraphVanilla();

		DatasetParser dp = new DatasetParser(g, "./datasets/1/");
		dp.parse();

		// execute the query
		System.out.println("execute.");
		Instant t1 = Instant.now();
		Execute ex = new Execute(g, q);
		ex.getResults();
		Instant t2 = Instant.now();

		Duration timeElapsed = Duration.between(t1, t2);
		System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");


	}
}