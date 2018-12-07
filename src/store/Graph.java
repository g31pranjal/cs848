package store;

class Graph {

	private KeyStore keystore;
	private ValueStore valuestore;
	private GTopology gtout;
	private GTopology gtin;

	public Graph() {
		this.keystore = new KeyStore();
		this.valuestore = new ValueStore();
		this.gtout = new GTopology();
		this.gtin = new GTopology();
	}

	public void addEdge(int a, int b, long eid) {
		this.gtout.add(a, b, eid);
		this.gtin.add(b, a, eid);
	}

	public Integer[] getOutNeighbours(int a) {
		return this.gtout.getNeighbours(a);
	}

	public Integer[] getNeighbours(int a) {
		return this.gtout.getNeighbours(a);
	}

	public Integer[] getInNeighbours(int a) {
		return this.gtin.getNeighbours(a);
	}

	public Long[] getOutEdges(int a) {
		return this.gtout.getEdges(a);
	}

	public Long[] getEdges(int a) {
		return this.gtout.getEdges(a);
	}

	public Long[] getInEdges(int a) {
		return this.gtin.getEdges(a);
	}

	// add vertex property

	// add edge property

	// get 


}