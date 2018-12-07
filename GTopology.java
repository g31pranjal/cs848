package store;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;


class Vertex {
	public Integer v;
	private List<Integer> edgelist;

	Vertex(Integer val) {
		this.v = val;
		this.edgelist = new ArrayList<Integer>();
	}

	public void addEdge(int edge) {
		int i = this.edgelist.indexOf(edge);
		if(i == -1) {
			this.edgelist.add(edge);
			Collections.sort(this.edgelist);
		}
	}

	public Integer[] getNeighbours() {
		Integer[] a = this.edgelist.toArray(new Integer[this.edgelist.size()]);
		return a;
	}

	public String toString() {
		String a = this.v.toString() + " -> ";
		a += this.edgelist.toString();
		return a;
	}


}


public class GTopology {

	private List<Vertex> str;

	public GTopology() {
		this.str = new ArrayList<Vertex>();
	}

	public int searchVertex(int val) {
		for(int i=0;i<this.str.size();i++) {
			Vertex v = this.str.get(i);
			if(v.v == val)
				return i;
		}
		return -1;
	}

	public void add(int src, int dst) {
		int at = this.searchVertex(src);
		if(at == -1) {
			Vertex v = new Vertex(src);
			v.addEdge(dst);
			this.str.add(v);
		}
		else {
			this.str.get(at).addEdge(dst);
		}
	}

	public Integer[] getNeighbours(int v) {
		int at = this.searchVertex(v);
		if(at == -1) {
			return null;
		}
		else {
			return this.str.get(at).getNeighbours();
		}
	}

	public String toString() {
		String a = "";
		for(Vertex v : this.str) {
			a += v.toString() + "\n";
		}
		return a;
	}






}




