package store;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;


class Neighbour {
	public Integer dst;
	public Long eid;
	
	public Neighbour(int a, long b) {
		this.dst = a;
		this.eid = b;
	}

	public String toString() {
		return this.dst.toString()+":"+this.eid.toString();
	}

}


class NeighbourCompare implements Comparator<Neighbour> {
	@Override
	public int compare(Neighbour a, Neighbour b) {
		return a.dst.compareTo(b.dst);
	}
}


class Vertex {
	public Integer vid;
	private List<Neighbour> edgelist;

	Vertex(Integer val) {
		this.vid = val;
		this.edgelist = new ArrayList<Neighbour>();
	}

	public void addEdge(int edge, long eid) {
		int i = this.edgelist.indexOf(edge);
		if(i == -1) {
			this.edgelist.add(new Neighbour(edge, eid));
			Collections.sort(this.edgelist, new NeighbourCompare());
		}
	}

	public Integer[] getNeighbours() {
		// Integer[] a = this.edgelist.toArray(new Integer[this.edgelist.size()]);
		Integer[] a = new Integer[this.edgelist.size()];
		for (int i=0; i<this.edgelist.size(); i++) {
			a[i] = this.edgelist.get(i).dst;
		}
		return a;
	}

	public Long[] getEdges() {
		// Integer[] a = this.edgelist.toArray(new Integer[this.edgelist.size()]);
		Long[] a = new Long[this.edgelist.size()];
		for (int i=0; i<this.edgelist.size(); i++) {
			a[i] = this.edgelist.get(i).eid;
		}
		return a;
	}

	public String toString() {
		String a = this.vid.toString() + " -> ";
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
			if(v.vid == val)
				return i;
		}
		return -1;
	}

	public void add(int src, int dst, long eid) {
		int at = this.searchVertex(src);
		if(at == -1) {
			Vertex v = new Vertex(src);
			v.addEdge(dst, eid);
			this.str.add(v);
		}
		else {
			this.str.get(at).addEdge(dst, eid);
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

	public Long[] getEdges(int v) {
		int at = this.searchVertex(v);
		if(at == -1) {
			return null;
		}
		else {
			return this.str.get(at).getEdges();
		}
	}

	public String toString() {
		String a = "";
		for(Vertex v : this.str) {
			a += v.toString() + "\n";
		}
		return a;
	}

	public Integer[] getVertices() {
		Integer[] vs = new Integer[this.str.size()];
		for(int i=0;i<this.str.size();i++) {
			vs[i] = this.str.get(i).vid;
		}
		return vs;
	}

}

