package store.topology;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class GTopology implements Iterable {

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

	public List<Integer> getNeighbours(int v) {
		int at = this.searchVertex(v);
		if(at == -1) {
			return new ArrayList<Integer>();
		}
		else {
			return this.str.get(at).getNeighbours();
		}
	}

	public List<Long> getEdges(int v) {
		int at = this.searchVertex(v);
		if(at == -1) {
			return  new ArrayList<Long>();
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

	@Override 
	public Iterator<Result> iterator() {
		Iterator<Result> it = new Iterator<Result>() {

			private int vtrack = 0;
			private int etrack = 0;

			@Override
			public boolean hasNext() {
				if(vtrack + 1 == str.size()) {
					if(etrack >= str.get(vtrack).edgelist.size())
						return false;
					else 
						return true;
				}
				else if(vtrack < str.size())
					return true;
				return false;
			}

			@Override
			public Result next() {	
				Vertex v = str.get(vtrack);
				if(!(etrack < v.edgelist.size()) ) {
					vtrack++;
					etrack = 0;
				}
				v = str.get(vtrack);
				Neighbour e = v.edgelist.get(etrack);
				etrack++;

				Result r = new Result();
				r.path.add(v.vid);
				r.path.add(e.dst);
				r.edges.add(e.eid);
				r.ePropIndex.add(e.eprop);
				r.valid = true;
				return r;
			}
		};
		return it;
	}

}

