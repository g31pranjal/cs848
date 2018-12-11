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

	@SuppressWarnings("unchecked")
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
	
	public abstract Node dealOverflow();
	
	protected abstract Node split();
	
	protected abstract Node pushUpKey(Short key, Node leftChild, Node rightNode);
	

	
	public Node getLeftSibling() {
		if (this.leftSibling != null && this.leftSibling.getParent() == this.getParent())
			return this.leftSibling;
		return null;
	}

	public void setLeftSibling(Node sibling) {
		this.leftSibling = sibling;
	}

	public Node getRightSibling() {
		if (this.rightSibling != null && this.rightSibling.getParent() == this.getParent())
			return this.rightSibling;
		return null;
	}

	public void setRightSibling(Node silbling) {
		this.rightSibling = silbling;
	}
	
}
