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
		// short k = this.keystore.addV(key);

		// int a = this.gtout.searchVertex(vid);
		// int b = this.gtin.searchVertex(vid);
		// int c = -1;
		// if(a != -1) 
		// 	c = this.gtout.getVPropIndex(a);
		// else 
		// 	c = this.gtin.getVPropIndex(b);
		// int d = this.vPropStore.addProperty(c, k, val);
		// if(a != -1) 
		// 	this.gtout.setVPropIndex(a, d);
		// if(b != -1) 
		// 	this.gtin.setVPropIndex(b, d);
	}

	public void addVertexProperty(int vid, String key, String val) {
		// short k = this.keystore.addV(key);
		// int a = this.gtout.searchVertex(vid);
		// int b = this.gtin.searchVertex(vid);
		// int c = -1;
		// if(a != -1) 
		// 	c = this.gtout.getVPropIndex(a);
		// else 
		// 	c = this.gtin.getVPropIndex(b);
		// int d = this.vPropStore.addProperty(c, k, val);
		// if(a != -1) 
		// 	this.gtout.setVPropIndex(a, d);
		// if(b != -1) 
		// 	this.gtin.setVPropIndex(b, d);
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


	public String toString() {
		return this.gtout.toString() + 
				"\n" + this.gtin.toString();
	}




}