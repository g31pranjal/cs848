package store.topology;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

class Vertex {
	public Integer vid;
	public Integer vprop;
	public List<Neighbour> edgelist;

	Vertex(Integer val) {
		this.vid = val;
		this.edgelist = new ArrayList<Neighbour>();
		this.vprop = 0;
	}

	public Neighbour addEdge(int edge, long eid) {
		for(int j=0;j<this.edgelist.size();j++) {
			if(this.edgelist.get(j).dst == edge)
				return this.edgelist.get(j);
		}
		Neighbour n = new Neighbour(this.vid, edge, eid);
		this.edgelist.add(n);
		Collections.sort(this.edgelist, new NeighbourCompare());
		return n;
	}

	public List<Integer> getNeighbours() {
		List<Integer> a = new ArrayList<Integer>();
		for (int i=0; i<this.edgelist.size(); i++) {
			a.add(this.edgelist.get(i).dst);
		}
		return a;
	}

	public List<Long> getEdges() {
		// Integer[] a = this.edgelist.toArray(new Integer[this.edgelist.size()]);
		List<Long> a = new ArrayList<Long>();
		// Long[] a = new Long[this.edgelist.size()];
		for (int i=0; i<this.edgelist.size(); i++) {
			a.add(this.edgelist.get(i).eid);
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
