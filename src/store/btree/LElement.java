package store.btree;

public class LElement {

	public int src;
	public int dst;
	public long eid;
	public String val;

	public LElement(int src, int dst, long eid, String val) {
		this.src = src;
		this.dst = dst;
		this.eid = eid;
		this.val = val;
	}

	public String toString() {
		String a = "";
		a += "src=" + this.src + ", ";
		a += "dst=" + this.dst + ", ";
		a += "eid=" + this.eid + ", ";
		a += "val=" + this.val + ", ";
		return a;
	}

}