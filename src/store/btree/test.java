package store.btree;

class test {

	public static void main(String[] args) {

		BTree b = new BTree<Integer, String>() ;

		b.insert(1, "a");
		b.insert(1, "b");
		b.insert(1, "c");
		b.insert(1, "d");
		b.insert(1, "e");

		System.out.println(b.search(1));
		System.out.println(b.search(2));
		System.out.println(b.search(3));
		System.out.println(b.search(4));
		System.out.println(b.search(5));

	}

}