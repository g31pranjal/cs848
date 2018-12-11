package store.btree;
import java.util.List;

public class BTree {

	private Node root;
	private int ins;


	public BTree() {
		this.root = new LNode();
		this.ins = 0;
	}


	public void insert(Short key, LElement value) {
		LNode leaf = this.findLeafNodeShouldContainKey(key);
		leaf.insertKey(key, value);
		
		if (leaf.isOverflow()) {
			Node n = leaf.dealOverflow();
			if (n != null)
				this.root = n; 
		}
	}

	
	/**
	 * Search a key value on the tree and return its associated value.
	 */
	public List search(Short key) {
		LNode leaf = this.findLeafNodeShouldContainKey(key);
		
		int index = leaf.search(key);
		return (index == -1) ? null : leaf.getValues(index, 0);
	}
	

	@SuppressWarnings("unchecked")
	private LNode findLeafNodeShouldContainKey(Short key) {
		Node node = this.root;
		while (node.getNodeType() == NodeType.iNode) {
			node = ((INode)node).getChild( node.search(key) );
		}
		
		return (LNode)node;
	}
}

