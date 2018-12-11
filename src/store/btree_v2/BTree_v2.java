package store.btree_v2;
import java.util.List;


public class BTree_v2 {

	private Node root;

	
	public BTree_v2() {
		this.root = new LNode();
	}


	// inserting the element.
	public void insert(CompoundKey key, LElement value) {
		LNode leaf = this.findLeafNodeShouldContainKey(key);
		leaf.insertKey(key, value);
		
		if (leaf.isOverflow()) {
			Node n = leaf.dealOverflow();
			if (n != null)
				this.root = n; 
		}
	}


	//searching the range based on the operator
	public List rangeSearch(CompoundKey key, String opr) {

		CompoundKey t = null;
		if(opr.equals("<="))
			t = new CompoundKey(key.key, null);			
		else if(opr.equals("<"))
			t = new CompoundKey(key.key, null);
		else if(opr.equals(">="))
			t = key;
		else if(opr.equals(">"))
			t = key;
		else if(opr.equals("=="))
			t = key;

		LNode leaf = this.findLeafNodeShouldContainKey(t);

		int index = leaf.search(t);
		return (index == -1) ? null : leaf.getValues(t, opr, key, index, 0);
	}
	

	//searching for the key. operator should go in here.
	public LElement search(CompoundKey key) {
		LNode leaf = this.findLeafNodeShouldContainKey(key);
		
		int index = leaf.search(key);
		return (index == -1) ? null : leaf.getValue(index);
	}



	@SuppressWarnings("unchecked")
	private LNode findLeafNodeShouldContainKey(CompoundKey key) {
		Node node = this.root;
		while (node.getNodeType() == NodeType.iNode) {
			node = ((INode)node).getChild( node.search(key) );
		}
		return (LNode)node;
	}

}

