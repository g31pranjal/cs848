package store;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import store.topology.GTopology;
import store.topology.Neighbour;
import store.topology.Result;
import java.util.Iterator;


public class Graph{

	public KeyStore keystore;
	private ValueStore valuestore;
	private GTopology gtout;
	private GTopology gtin;
	public PropertyStore vPropStore;
	public PropertyStore ePropStore;
	private Map<Long, List<Neighbour>> nmap;

	public Graph() {
		this.keystore = new KeyStore();
		this.valuestore = new ValueStore();
		this.gtout = new GTopology();
		this.gtin = new GTopology();
		this.vPropStore = new PropertyStore();
		this.ePropStore = new PropertyStore();
		this.nmap = new HashMap<Long, List<Neighbour>>();
	}

	public void addEdge(int a, int b, long eid) {
		List<Neighbour> c = new ArrayList<Neighbour>();
		c.add(this.gtout.add(a, b, eid));
		c.add(this.gtin.add(b, a, eid));
		nmap.put(eid, c);
	}

	public Integer[] getOutNeighbours(int a) {
		return this.gtout.getNeighbours(a);
	}

	public Integer[] getInNeighbours(int a) {
		return this.gtin.getNeighbours(a);
	}

	public Long[] getOutEdges(int a) {
		return this.gtout.getEdges(a);
	}

	public Long[] getInEdges(int a) {
		return this.gtin.getEdges(a);
	}

	public void addVertexProperty(int vid, String key, int val) {
		short k = this.keystore.addV(key);

		int a = this.gtout.searchVertex(vid);
		int b = this.gtin.searchVertex(vid);
		int c = -1;
		if(a != -1) 
			c = this.gtout.getVPropIndex(a);
		else 
			c = this.gtin.getVPropIndex(b);
		int d = this.vPropStore.addProperty(c, k, val);
		if(a != -1) 
			this.gtout.setVPropIndex(a, d);
		if(b != -1) 
			this.gtin.setVPropIndex(b, d);
	}

	public void addVertexProperty(int vid, String key, String val) {
		short k = this.keystore.addV(key);
		int a = this.gtout.searchVertex(vid);
		int b = this.gtin.searchVertex(vid);
		int c = -1;
		if(a != -1) 
			c = this.gtout.getVPropIndex(a);
		else 
			c = this.gtin.getVPropIndex(b);
		int d = this.vPropStore.addProperty(c, k, val);
		if(a != -1) 
			this.gtout.setVPropIndex(a, d);
		if(b != -1) 
			this.gtin.setVPropIndex(b, d);
	}

	public void addEdgeProperty(long eid, String key, int val) {
		short k = this.keystore.addV(key);

		List<Neighbour> a = this.nmap.get(eid);

		int b = a.get(0).getPropIndex();
		int d = this.ePropStore.addProperty(b, k, val);

		a.get(0).setPropIndex(d);
		a.get(1).setPropIndex(d);
	}

	public void addEdgeProperty(long eid, String key, String val) {
		short k = this.keystore.addV(key);

		List<Neighbour> a = this.nmap.get(eid);

		int b = a.get(0).getPropIndex();
		int d = this.ePropStore.addProperty(b, k, val);

		a.get(0).setPropIndex(d);
		a.get(1).setPropIndex(d);
	}


	public String toString() {
		return this.gtout.toString() + 
				"\n" + this.gtin.toString() + 
				"\n" + this.vPropStore.toString() + 
				"\n\n" + this.ePropStore.toString();
	}

	public Iterator<Result> scanByEdges() {
		return this.gtout.iterator();
	}

	public Map<String, String> retrieveEdgeProperties(int i) {
		Map<String, String> a = new HashMap<String, String>();
		while(i != 0) {
			short k = this.ePropStore.getKeyAt(i);
			String key = this.keystore.getV(k);
			String val = "";
			if(this.ePropStore.getTypeAt(i).equals("num"))
				val = this.ePropStore.getNumAt(i).toString();
			else 
				val = this.ePropStore.getStringAt(i);

			a.put(key, val);
			i = this.ePropStore.getRefAt(i);
		}
		return a;
	}

	public Map<String, String> retrieveVertexProperties(int i) {
		Map<String, String> a = new HashMap<String, String>();
		while(i != 0) {
			short k = this.vPropStore.getKeyAt(i);
			String key = this.keystore.getV(k);
			String val = "";
			if(this.vPropStore.getTypeAt(i).equals("num"))
				val = this.vPropStore.getNumAt(i).toString();
			else 
				val = this.vPropStore.getStringAt(i);

			a.put(key, val);
			i = this.vPropStore.getRefAt(i);
		}
		return a;
	}

	// public getEProps(int i) {

	// }


}