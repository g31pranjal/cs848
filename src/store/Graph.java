package store;
import store.topology.GTopology;

public class Graph {

	private KeyStore keystore;
	private ValueStore valuestore;
	private GTopology gtout;
	private GTopology gtin;
	private PropertyStore vPropStore;

	public Graph() {
		this.keystore = new KeyStore();
		this.valuestore = new ValueStore();
		this.gtout = new GTopology();
		this.gtin = new GTopology();
		this.vPropStore = new PropertyStore();
	}

	public void addEdge(int a, int b, long eid) {
		this.gtout.add(a, b, eid);
		this.gtin.add(b, a, eid);
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


	// add edge property

	// get 

	public String toString() {
		return this.gtout.toString() + 
				"\n" + this.gtin.toString() + 
				"\n" + this.vPropStore.toString();
	}


}