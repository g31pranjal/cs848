package store.btree_v2;

class test {
	public static void main(String[] args) {

		BTree_v2<CompoundKey, String> t = new BTree_v2<CompoundKey, String>();

		t.insert(new CompoundKey((short)2, "a"), "2a");
		t.insert(new CompoundKey((short)2, "a"), "2b");
		t.insert(new CompoundKey((short)2, "c"), "2c");
		t.insert(new CompoundKey((short)2, "d"), "2d");
		t.insert(new CompoundKey((short)2, "e"), "2e");

		System.out.println(t.search(new CompoundKey((short)2, null)));
		
	}
}