package store.btree_v2;
import java.util.List;


public class BTree_v2<tKey extends Comparable<tKey>, tValue> {

	private Node<tKey> root;

	
	public BTree_v2() {
		this.root = new LNode<tKey, tValue>();
	}


	// inserting the element.
	public void insert(tKey key, tValue value) {
		LNode<tKey, tValue> leaf = this.findLeafNodeShouldContainKey(key);
		leaf.insertKey(key, value);
		
		if (leaf.isOverflow()) {
			Node<tKey> n = leaf.dealOverflow();
			if (n != null)
				this.root = n; 
		}
	}


	//searching the range based on the operator
	public List<tValue> rangeSearch(tKey key, String opr) {
		tKey t;
		if(opr.equals("<="))
			t = new CompoundKey(key.key, null);			
		else if(opr.equals("<"))
			t = new CompoundKey(key.key, null);
		else if(opr.equals(">="))
			t = 
		else if(opr.equals(">"))
		else if(opr.equals("=="))

	}
	

	//searching for the key. operator should go in here.
	public List<tValue> search(tKey key) {
		LNode<tKey, tValue> leaf = this.findLeafNodeShouldContainKey(key);
		
		int index = leaf.search(key);
		return (index == -1) ? null : leaf.getValues(key, index, 0);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private LNode<tKey, tValue> findLeafNodeShouldContainKey(tKey key) {
		Node<tKey> node = this.root;
		while (node.getNodeType() == NodeType.iNode) {
			node = ((INode<tKey>)node).getChild( node.search(key) );
		}
		
		return (LNode<tKey, tValue>)node;
	}
}

