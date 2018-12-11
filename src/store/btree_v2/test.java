package store.btree_v2;

class test {
	public static void main(String[] args) {

		BTree_v2 t = new BTree_v2();

		t.insert(new CompoundKey((short)2, "a"), new LElement(-1, -1, 0, "2a") );
		t.insert(new CompoundKey((short)2, "b"), new LElement(-1, -1, 0, "2b") );
		t.insert(new CompoundKey((short)2, "c"), new LElement(-1, -1, 0, "2c") );
		t.insert(new CompoundKey((short)2, "d"), new LElement(-1, -1, 0, "2d") );
		t.insert(new CompoundKey((short)2, "e"), new LElement(-1, -1, 0, "2e") );

		System.out.println(t.rangeSearch( new CompoundKey((short)2, "c"), "<" ));
		
	}
}