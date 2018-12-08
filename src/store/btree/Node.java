package store.btree;

enum NodeType {
	iNode,
	lNode
}

abstract class Node {
	protected Short[] keys;
	protected int keyCount;
	protected Node parentNode;
	protected Node leftSibling;
	protected Node rightSibling;
	

	protected Node() {
		this.keyCount = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}

	public int getKeyCount() {
		return this.keyCount;
	}

	// @SuppressWarnings("unchecked")
	public Short getKey(int index) {
		return this.keys[index];
	}

	public void setKey(int index, Short key) {
		this.keys[index] = key;
	}

	public Node getParent() {
		return this.parentNode;
	}

	public void setParent(Node parent) {
		this.parentNode = parent;
	}	
	

	public abstract NodeType getNodeType();
	
	public abstract int search(Short key);
	
	public boolean isOverflow() {
		return this.getKeyCount() == this.keys.length;
	}
	
	public Node dealOverflow() {
		int midIndex = this.getKeyCount() / 2;
		Short upKey = this.getKey(midIndex);

		Node newRNode = this.split();

		if (this.getParent() == null) {
			this.setParent(new INode());
		}
		newRNode.setParent(this.getParent());
		
		newRNode.setLeftSibling(this);
		newRNode.setRightSibling(this.rightSibling);
		if (this.getRightSibling() != null)
			this.getRightSibling().setLeftSibling(newRNode);
		this.setRightSibling(newRNode);
		
		return this.getParent().pushUpKey(upKey, this, newRNode);
	}
	
	protected abstract Node split();
	
	protected abstract Node pushUpKey(Short key, Node leftChild, Node rightNode);
	


	
	/* The codes below are used to support deletion operation */
	
	// public boolean isUnderflow() {
	// 	return this.getKeyCount() < (this.keys.length / 2);
	// }
	
	// public boolean canLendAKey() {
	// 	return this.getKeyCount() > (this.keys.length / 2);
	// }
	
	public BTreeNode<TKey> getLeftSibling() {
		if (this.leftSibling != null && this.leftSibling.getParent() == this.getParent())
			return this.leftSibling;
		return null;
	}

	public void setLeftSibling(BTreeNode<TKey> sibling) {
		this.leftSibling = sibling;
	}

	public BTreeNode<TKey> getRightSibling() {
		if (this.rightSibling != null && this.rightSibling.getParent() == this.getParent())
			return this.rightSibling;
		return null;
	}

	public void setRightSibling(BTreeNode<TKey> silbling) {
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
	
	protected abstract void processChildrenTransfer(BTreeNode<TKey> borrower, BTreeNode<TKey> lender, int borrowIndex);
	
	protected abstract BTreeNode<TKey> processChildrenFusion(BTreeNode<TKey> leftChild, BTreeNode<TKey> rightChild);
	
	protected abstract void fusionWithSibling(TKey sinkKey, BTreeNode<TKey> rightSibling);
	
	protected abstract TKey transferFromSibling(TKey sinkKey, BTreeNode<TKey> sibling, int borrowIndex);
}
