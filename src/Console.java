import exec.Query;
import exec.Parser;
import store.Graph;
import store.GraphVanilla;
import store.GraphBTree;
import store.GraphBTree_v2;
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
		else if(args.length > 0 && args[0].equals("c"))
			g = new GraphBTree_v2();
		else
			g = new GraphVanilla();

		Instant t1, t2;
		Duration timeDiff;

		t1 = Instant.now();
		DatasetParser dp = new DatasetParser(g, "./datasets/dataset-90/");
		dp.parse();
		t2 = Instant.now();
		timeDiff = Duration.between(t1, t2);
		System.out.println("time: "+ timeDiff.toMillis() +" ms");

		// execute the query
		System.out.println("execute.");
		t1 = Instant.now();	
		Execute ex = new Execute(g, q);
		ex.getResults();
		t2 = Instant.now();

		timeDiff = Duration.between(t1, t2);
		System.out.println("time: "+ timeDiff.toMillis() +" ms");
	}
}

