import exec.Query;
import exec.Parser;
import store.Graph;
import store.GraphVanilla;
import store.GraphBTree;
import exec.Execute;
	
public class Console {

	public static void main(String[] args) {
		
		// read the query 
		Parser p = new Parser();
		Query  q = p.parseFromFile("./queries/query2.json");

		// read the dataset
		Graph g = new GraphBTree();
		DatasetParser dp = new DatasetParser(g, "./datasets/1/");
		dp.parse();

		// execute the query 
		Execute ex = new Execute(g, q);
		ex.getResults();
	}
}