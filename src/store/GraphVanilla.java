package store;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import store.topology.GTopology;
import store.topology.Neighbour;
import store.topology.Result;

public class GraphVanilla extends Graph{

	public PropertyStore vPropStore;
	public PropertyStore ePropStore;

	public GraphVanilla() {
		super();
		this.vPropStore = new PropertyStore();
		this.ePropStore = new PropertyStore();
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
		
		System.out.println(a.get(0).src);
		System.out.println(a.get(0).dst);
		System.out.println(a.get(0).eid);
		
	}

	public void addEdgeProperty(long eid, String key, String val) {
		short k = this.keystore.addV(key);

		List<Neighbour> a = this.nmap.get(eid);

		int b = a.get(0).getPropIndex();
		int d = this.ePropStore.addProperty(b, k, val);

		a.get(0).setPropIndex(d);
		a.get(1).setPropIndex(d);

	}



	/* class specific functions */

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

}