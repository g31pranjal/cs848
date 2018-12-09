package store;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import store.topology.GTopology;
import store.topology.Neighbour;
import store.topology.Result;
import store.btree.BTree;
import store.btree.LElement;


public class GraphBTree extends Graph {

	public BTree<Short, LElement> ePropBTree;
	public BTree<Short, LElement> vPropBTree;

	public GraphBTree() {
		super();
		this.ePropBTree = new BTree<Short, LElement>();
		this.vPropBTree = new BTree<Short, LElement>();
	}

	public void addVertexProperty(int vid, String key, int val) {
		short k = this.keystore.addV(key);
		LElement e = new LElement(vid, -1, -1, Integer.toString(val));	
		vPropBTree.insert(k, e);
	}

	public void addVertexProperty(int vid, String key, String val) {
		short k = this.keystore.addV(key);
		LElement e = new LElement(vid, -1, -1, val);	
		vPropBTree.insert(k, e);
	}

	public void addEdgeProperty(long eid, String key, int val) {
		short k = this.keystore.addV(key);
		List<Neighbour> a = this.nmap.get(eid);
		LElement e = new LElement(a.get(0).src, a.get(0).dst, 
							a.get(0).eid, Integer.toString(val));	
		ePropBTree.insert(k, e);
		
	}

	public void addEdgeProperty(long eid, String key, String val) {
		short k = this.keystore.addV(key);
		List<Neighbour> a = this.nmap.get(eid);
		LElement e = new LElement(a.get(0).src, a.get(0).dst, a.get(0).eid, val);	
		ePropBTree.insert(k, e);
	}

	public List<Result> searchByProperty(String key) {
		List<Result> ret = new ArrayList<Result>();
		Short k = this.keystore.getK(key);
		List<LElement> r = ePropBTree.search(k);
		for(LElement e : r ) {
			Result newr = new Result();
			newr.path.add(e.src);
			newr.path.add(e.dst);
			newr.edges.add(e.eid);
			newr.val = e.val;
			ret.add(newr);
		}
		return ret;
	}


	public String toString() {
		return this.gtout.toString() + 
				"\n" + this.gtin.toString();
	}




}