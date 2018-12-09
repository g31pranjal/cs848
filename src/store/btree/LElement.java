package store.btree;

class LElement {

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

}