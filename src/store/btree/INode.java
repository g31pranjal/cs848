package store.btree;

class INode extends Node {
	protected final static int INNERORDER = 32;
	protected Object[] children; 
	
	public INode() {
		this.keys = new Short[INNERORDER + 1];
		this.children = new Object[INNERORDER + 2];
	}
	
	@SuppressWarnings("unchecked")
	public Node getChild(int index) {
		return (Node)this.children[index];
	}

	public void setChild(int index, Node child) {
		this.children[index] = child;
		if (child != null)
			child.setParent(this);
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.iNode;
	}
	
	@Override
	public int search(Short key) {
		int index = 0;
		for (index = 0; index < this.getKeyCount(); ++index) {
			int cmp = this.getKey(index).compareTo(key);
			if (cmp == 0) {
				return index + 1;
			}
			else if (cmp > 0) {
				return index;
			}
		}		
		return index;
	}

	@Override
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
	
	
	/* The codes below are used to support insertion operation */
	
	private void insertAt(int index, Short key, Node leftChild, Node rightChild) {
		// move space for the new key
		for (int i = this.getKeyCount() + 1; i > index; --i) {
			this.setChild(i, this.getChild(i - 1));
		}
		for (int i = this.getKeyCount(); i > index; --i) {
			this.setKey(i, this.getKey(i - 1));
		}
		
		// insert the new key
		this.setKey(index, key);
		this.setChild(index, leftChild);
		this.setChild(index + 1, rightChild);
		this.keyCount += 1;
	}
	
	/**
	 * When splits a internal node, the middle key is kicked out and be pushed to parent node.
	 */
	@Override
	protected Node split() {
		int midIndex = this.getKeyCount() / 2;

		INode newRNode = new INode();
		for (int i = midIndex + 1; i < this.getKeyCount(); ++i) {
			newRNode.setKey(i - midIndex - 1, this.getKey(i));
			this.setKey(i, null);
		}
		for (int i = midIndex + 1; i <= this.getKeyCount(); ++i) {
			newRNode.setChild(i - midIndex - 1, this.getChild(i));
			newRNode.getChild(i - midIndex - 1).setParent(newRNode);
			this.setChild(i, null);
		}
		this.setKey(midIndex, null);
		newRNode.keyCount = this.getKeyCount() - midIndex - 1;
		this.keyCount = midIndex;
		
		return newRNode;
	}
	
	@Override
	protected Node pushUpKey(Short key, Node leftChild, Node rightNode) {
		// find the target position of the new key
		int index = this.search(key);
		
		// insert the new key
		this.insertAt(index, key, leftChild, rightNode);

		// check whether current node need to be split
		if (this.isOverflow()) {
			return this.dealOverflow();
		}
		else {
			return this.getParent() == null ? this : null;
		}
	}
}