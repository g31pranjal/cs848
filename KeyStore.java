package store;
import java.util.Map;
import java.util.HashMap;

class KeyStore {

	private Map<Short, String> kv;
	private Map<String, Short> vk;
	private Short index;

	KeyStore() {
		this.kv = new HashMap<Short, String>();
		this.vk = new HashMap<String, Short>();
		this.index = 0;
	}

	public String getV(Short k) {
		if(this.kv.containsKey(k)) {
			return this.kv.get(k);
		}
		else 
			return null;
	}

	public Short getK(String v) {
		if(this.vk.containsKey(v)) {
			return this.vk.get(v);
		}
		else 
			return null;
	}

	public Short addV(String v) {

		if(this.vk.containsKey(v)) {
			return this.vk.get(v);
		}
		else {
			this.index++;
			this.kv.put(this.index, v);
			this.vk.put(v, this.index);
			return this.index;
		}
	}

	public Boolean delK(Short k) {
		if(this.kv.containsKey(k)) {
			this.vk.remove(this.kv.remove(k));
			return true;
		}
		else {
			return false;
		}
	}

	public Boolean delV(String v) {
		if(this.vk.containsKey(v)) {
			this.kv.remove(this.vk.remove(v));
			return true;
		}
		else {
			return false;
		}
	}

	public int nums() {
		return this.kv.size();
	}


	// public static void main(String[] args) {
	// 	KeyStore a = new KeyStore();
	// 	System.out.println(a.addV("hello"));	
	// 	System.out.println( a.getV((short)1) );
	// } 


}