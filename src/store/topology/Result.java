package store.topology;
import java.util.List;
import java.util.ArrayList;

public class Result {

	public List<Integer> path;
	public List<Long> edges;
	public List<Integer> ePropIndex;
	public String key;
	public String val;
	public boolean valid;

	public Result() {
		this.path = new ArrayList<Integer>();
		this.edges = new ArrayList<Long>();
		this.ePropIndex = new ArrayList<Integer>();
		this.valid = true;
	}

	public String toString() {
		return "result instance : " + path +" "+ edges +
				" "+ ePropIndex +" "+ valid +" "+ key +" "+ val;
	}

}