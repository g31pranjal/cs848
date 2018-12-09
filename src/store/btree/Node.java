package store.btree;

enum NodeType {
	iNode,
	lNode
}

abstract class Node<tKey extends Comparable<tKey> > {
	protected Object[] keys;
	protected int keyCount;
	protected Node<tKey> parentNode;
	protected Node<tKey> leftSibling;
	protected Node<tKey> rightSibling;
	

	protected Node() {
		this.keyCount = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}

	public int getKeyCount() {
		return this.keyCount;
	}

	@SuppressWarnings("unchecked")
	public tKey getKey(int index) {
		return (tKey)this.keys[index];
	}

	public void setKey(int index, tKey key) {
		this.keys[index] = key;
	}

	public Node<tKey> getParent() {
		return this.parentNode;
	}

	public void setParent(Node<tKey> parent) {
		this.parentNode = parent;
	}	
	

	public abstract NodeType getNodeType();
	
	public abstract int search(tKey key);
	
	public boolean isOverflow() {
		return this.getKeyCount() == this.keys.length;
	}
	
	public abstract Node<tKey> dealOverflow();
	
	protected abstract Node<tKey> split();
	
	protected abstract Node<tKey> pushUpKey(tKey key, Node<tKey> leftChild, Node<tKey> rightNode);
	


	
	/* The codes below are used to support deletion operation */
	
	// public boolean isUnderflow() {
	// 	return this.getKeyCount() < (this.keys.length / 2);
	// }
	
	// public boolean canLendAKey() {
	// 	return this.getKeyCount() > (this.keys.length / 2);
	// }
	
	public Node<tKey> getLeftSibling() {
		if (this.leftSibling != null && this.leftSibling.getParent() == this.getParent())
			return this.leftSibling;
		return null;
	}

	public void setLeftSibling(Node<tKey> sibling) {
		this.leftSibling = sibling;
	}

	public Node<tKey> getRightSibling() {
		if (this.rightSibling != null && this.rightSibling.getParent() == this.getParent())
			return this.rightSibling;
		return null;
	}

	public void setRightSibling(Node<tKey> silbling) {
		this.rightSibling = silbling;
	}
	
	// public BTreeNode<TKey> dealUnderflow() {
	// 	if (this.getParent() == null)
	// 		return null;
		
	// 	// try to borrow a key from sibling
	// 	BTreeNode<TKey> leftSibling = this.getLeftSibling();
	// 	if (leftSibling != null && leftSibling.canLendAKey()) {
	// 		this.getParent().processChildrenTransfer(this, leftSibling, leftSibling.getKeyCount() - 1);
	// 		return null;
	// 	}
		
	// 	BTreeNode<TKey> rightSibling = this.getRightSibling();
	// 	if (rightSibling != null && rightSibling.canLendAKey()) {
	// 		this.getParent().processChildrenTransfer(this, rightSibling, 0);
	// 		return null;
	// 	}
		
	// 	// Can not borrow a key from any sibling, then do fusion with sibling
	// 	if (leftSibling != null) {
	// 		return this.getParent().processChildrenFusion(leftSibling, this);
	// 	}
	// 	else {
	// 		return this.getParent().processChildrenFusion(this, rightSibling);
	// 	}
	// }
	
	// protected abstract void processChildrenTransfer(BTreeNode<TKey> borrower, BTreeNode<TKey> lender, int borrowIndex);
	
	// protected abstract BTreeNode<TKey> processChildrenFusion(BTreeNode<TKey> leftChild, BTreeNode<TKey> rightChild);
	
	// protected abstract void fusionWithSibling(TKey sinkKey, BTreeNode<TKey> rightSibling);
	
	// protected abstract TKey transferFromSibling(TKey sinkKey, BTreeNode<TKey> sibling, int borrowIndex);
}
