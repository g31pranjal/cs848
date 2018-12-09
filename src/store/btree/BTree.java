package store.btree;
import java.util.List;

public class BTree<tKey extends Comparable<tKey>, tValue> {
	private Node<tKey> root;
	
	public BTree() {
		this.root = new LNode<tKey, tValue>();
	}

	public void insert(tKey key, tValue value) {
		LNode<tKey, tValue> leaf = this.findLeafNodeShouldContainKey(key);
		leaf.insertKey(key, value);
		
		if (leaf.isOverflow()) {
			Node<tKey> n = leaf.dealOverflow();
			if (n != null)
				this.root = n; 
		}
	}
	
	/**
	 * Search a key value on the tree and return its associated value.
	 */
	public List<tValue> search(tKey key) {
		LNode<tKey, tValue> leaf = this.findLeafNodeShouldContainKey(key);
		
		int index = leaf.search(key);
		return (index == -1) ? null : leaf.getValues(index, 0);
	}
	
	/**
	 * Delete a key and its associated value from the tree.
	 */
	// public void delete(tKey key) {
	// 	BTreeLeafNode<tKey, TValue> leaf = this.findLeafNodeShouldContainKey(key);
		
	// 	if (leaf.delete(key) && leaf.isUnderflow()) {
	// 		BTreeNode<tKey> n = leaf.dealUnderflow();
	// 		if (n != null)
	// 			this.root = n; 
	// 	}
	// }
	
	/**
	 * Search the leaf node which should contain the specified key
	 */
	@SuppressWarnings("unchecked")
	private LNode<tKey, tValue> findLeafNodeShouldContainKey(tKey key) {
		Node<tKey> node = this.root;
		while (node.getNodeType() == NodeType.iNode) {
			node = ((INode<tKey>)node).getChild( node.search(key) );
		}
		
		return (LNode<tKey, tValue>)node;
	}
}

