package store.topology;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

class Vertex {
	public Integer vid;
	public Integer vprop;
	private List<Neighbour> edgelist;

	Vertex(Integer val) {
		this.vid = val;
		this.edgelist = new ArrayList<Neighbour>();
		this.vprop = 0;
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
		String a = this.vid.toString() + ":" + this.vprop.toString() + " -> ";
		a += this.edgelist.toString();
		return a;
	}

	public Integer getPropIndex() {
		return this.vprop;
	}

	public void setPropIndex(int i) {
		this.vprop = i;
	}
}
