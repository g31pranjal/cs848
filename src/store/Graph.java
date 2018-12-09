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


public abstract class Graph{

	public KeyStore keystore;
	protected GTopology gtout;
	protected GTopology gtin;
	protected Map<Long, List<Neighbour>> nmap;

	public Graph() {
		this.keystore = new KeyStore();
		this.gtout = new GTopology();
		this.gtin = new GTopology();
		this.nmap = new HashMap<Long, List<Neighbour>>();
	}

	public void addEdge(int a, int b, long eid) {
		List<Neighbour> c = new ArrayList<Neighbour>();
		c.add(this.gtout.add(a, b, eid));
		c.add(this.gtin.add(b, a, eid));
		nmap.put(eid, c);
	}

	public List<Integer> getOutNeighbours(int a) {
		return this.gtout.getNeighbours(a);
	}

	public List<Integer> getInNeighbours(int a) {
		return this.gtin.getNeighbours(a);
	}

	public List<Long> getOutEdges(int a) {
		return this.gtout.getEdges(a);
	}

	public List<Long> getInEdges(int a) {
		return this.gtin.getEdges(a);
	}

	public abstract void addVertexProperty(int vid, String key, int val);

	public abstract void addVertexProperty(int vid, String key, String val);

	public abstract void addEdgeProperty(long eid, String key, int val);

	public abstract void addEdgeProperty(long eid, String key, String val);


}