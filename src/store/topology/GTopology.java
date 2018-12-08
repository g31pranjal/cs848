package store.topology;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;



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

	public int getVPropIndex(int i) {
		return this.str.get(i).getPropIndex();
	}

	public void setVPropIndex(int i, int n) {
		this.str.get(i).setPropIndex(n);
	}

	public Neighbour add(int src, int dst, long eid) {
		int at = this.searchVertex(src);
		if(at == -1) {
			Vertex v = new Vertex(src);
			this.str.add(v);
			return v.addEdge(dst, eid);
		}
		else {
			return this.str.get(at).addEdge(dst, eid);
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

