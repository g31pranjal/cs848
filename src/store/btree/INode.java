package store.btree;

class INode<tKey extends Comparable<tKey>> extends Node<tKey> {
	protected final static int INNERORDER = 4;
	protected Object[] children; 
	
	public INode() {
		this.keys = new Object[INNERORDER + 1];
		this.children = new Object[INNERORDER + 2];
	}
	
	@SuppressWarnings("unchecked")
	public Node<tKey> getChild(int index) {
		return (Node<tKey>)this.children[index];
	}

	public void setChild(int index, Node<tKey> child) {
		this.children[index] = child;
		if (child != null)
			child.setParent(this);
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.iNode;
	}
	
	@Override
	public int search(tKey key) {
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
	public Node<tKey> dealOverflow() {
		int midIndex = this.getKeyCount() / 2;
		tKey upKey = this.getKey(midIndex);

		Node<tKey> newRNode = this.split();

		if (this.getParent() == null) {
			this.setParent(new INode<tKey>());
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
	
	private void insertAt(int index, tKey key, Node<tKey> leftChild, Node<tKey> rightChild) {
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
	protected Node<tKey> split() {
		int midIndex = this.getKeyCount() / 2;
		
		INode<tKey> newRNode = new INode<tKey>();
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
	protected Node<tKey> pushUpKey(tKey key, Node<tKey> leftChild, Node<tKey> rightNode) {
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

	
	
	
	
	
	/* The codes below are used to support delete operation */
	
	// private void deleteAt(int index) {
	// 	int i = 0;
	// 	for (i = index; i < this.getKeyCount() - 1; ++i) {
	// 		this.setKey(i, this.getKey(i + 1));
	// 		this.setChild(i + 1, this.getChild(i + 2));
	// 	}
	// 	this.setKey(i, null);
	// 	this.setChild(i + 1, null);
	// 	--this.keyCount;
	// }
	
	
	// @Override
	// protected void processChildrenTransfer(Node<tKey> borrower, Node<tKey> lender, int borrowIndex) {
	// 	int borrowerChildIndex = 0;
	// 	while (borrowerChildIndex < this.getKeyCount() + 1 && this.getChild(borrowerChildIndex) != borrower)
	// 		++borrowerChildIndex;
		
	// 	if (borrowIndex == 0) {
	// 		// borrow a key from right sibling
	// 		tKey upKey = borrower.transferFromSibling(this.getKey(borrowerChildIndex), lender, borrowIndex);
	// 		this.setKey(borrowerChildIndex, upKey);
	// 	}
	// 	else {
	// 		// borrow a key from left sibling
	// 		tKey upKey = borrower.transferFromSibling(this.getKey(borrowerChildIndex - 1), lender, borrowIndex);
	// 		this.setKey(borrowerChildIndex - 1, upKey);
	// 	}
	// }
	
	// @Override
	// protected Node<tKey> processChildrenFusion(Node<tKey> leftChild, Node<tKey> rightChild) {
	// 	int index = 0;
	// 	while (index < this.getKeyCount() && this.getChild(index) != leftChild)
	// 		++index;
	// 	tKey sinkKey = this.getKey(index);
		
	// 	// merge two children and the sink key into the left child node
	// 	leftChild.fusionWithSibling(sinkKey, rightChild);
		
	// 	// remove the sink key, keep the left child and abandon the right child
	// 	this.deleteAt(index);
		
	// 	// check whether need to propagate borrow or fusion to parent
	// 	if (this.isUnderflow()) {
	// 		if (this.getParent() == null) {
	// 			// current node is root, only remove keys or delete the whole root node
	// 			if (this.getKeyCount() == 0) {
	// 				leftChild.setParent(null);
	// 				return leftChild;
	// 			}
	// 			else {
	// 				return null;
	// 			}
	// 		}
			
	// 		return this.dealUnderflow();
	// 	}
		
	// 	return null;
	// }
	
	
	// @Override
	// protected void fusionWithSibling(tKey sinkKey, Node<tKey> rightSibling) {
	// 	INode<tKey> rightSiblingNode = (INode<tKey>)rightSibling;
		
	// 	int j = this.getKeyCount();
	// 	this.setKey(j++, sinkKey);
		
	// 	for (int i = 0; i < rightSiblingNode.getKeyCount(); ++i) {
	// 		this.setKey(j + i, rightSiblingNode.getKey(i));
	// 	}
	// 	for (int i = 0; i < rightSiblingNode.getKeyCount() + 1; ++i) {
	// 		this.setChild(j + i, rightSiblingNode.getChild(i));
	// 	}
	// 	this.keyCount += 1 + rightSiblingNode.getKeyCount();
		
	// 	this.setRightSibling(rightSiblingNode.rightSibling);
	// 	if (rightSiblingNode.rightSibling != null)
	// 		rightSiblingNode.rightSibling.setLeftSibling(this);
	// }
	
	// @Override
	// protected tKey transferFromSibling(tKey sinkKey, Node<tKey> sibling, int borrowIndex) {
	// 	INode<tKey> siblingNode = (INode<tKey>)sibling;
		
	// 	tKey upKey = null;
	// 	if (borrowIndex == 0) {
	// 		// borrow the first key from right sibling, append it to tail
	// 		int index = this.getKeyCount();
	// 		this.setKey(index, sinkKey);
	// 		this.setChild(index + 1, siblingNode.getChild(borrowIndex));			
	// 		this.keyCount += 1;
			
	// 		upKey = siblingNode.getKey(0);
	// 		siblingNode.deleteAt(borrowIndex);
	// 	}
	// 	else {
	// 		// borrow the last key from left sibling, insert it to head
	// 		this.insertAt(0, sinkKey, siblingNode.getChild(borrowIndex + 1), this.getChild(0));
	// 		upKey = siblingNode.getKey(borrowIndex);
	// 		siblingNode.deleteAt(borrowIndex);
	// 	}
		
	// 	return upKey;
	// }
}