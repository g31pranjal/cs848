package store.btree_v2;
import java.util.List;
import java.util.ArrayList;

class LNode<tKey extends Comparable<tKey>, tValue> extends Node<tKey> {
	protected final static int LEAFORDER = 32;
	private Object[] values;
	
	public LNode() {
		this.keys = new Object[LEAFORDER + 1];
		this.values = new Object[LEAFORDER + 1];
	}

	@SuppressWarnings("unchecked")
	public tValue getValue(int index) {
		return (tValue)this.values[index];
	}

	@SuppressWarnings("unchecked")
	public List<tValue> getValues(tKey key, int index, int direction) {
		List<tValue> ret = new ArrayList<tValue>();
		List<tValue> l = new ArrayList<tValue>();
		// List<tValue> r = new ArrayList<tValue>();
		while(index < this.getKeyCount() && 
			((CompoundKey)this.keys[index]).compareTo((CompoundKey)key) == 0 ) {
			ret.add((tValue)this.values[index]);
			index++;
		}
		if(this.leftSibling != null && direction <= 0) {
			int i = this.leftSibling.search(key);
			if(i != -1) 
				l.addAll(((LNode<tKey, tValue>)this.leftSibling).getValues(key, i, -1));
		}
		l.addAll(ret);
		if(this.rightSibling != null && direction >= 0) {
			int i = this.rightSibling.search(key);
			if(i != -1) 
				l.addAll(((LNode<tKey, tValue>)this.rightSibling).getValues(key, i, 1));
		}
		return l;
	}

	public void setValue(int index, tValue value) {
		this.values[index] = value;
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.lNode;
	}

	@Override
	public int search(tKey key) {
		for (int i = 0; i < this.getKeyCount(); ++i) {
			 int cmp = this.getKey(i).compareTo(key);
			 if (cmp == 0) {
				 return i;
			 }
			 else if (cmp > 0) {
				 return -1;
			 }
		}
		return -1;
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
	
	public void insertKey(tKey key, tValue value) {
		int index = 0;
		while (index < this.getKeyCount() && this.getKey(index).compareTo(key) < 0)
			++index;
		this.insertAt(index, key, value);
	}
	
	private void insertAt(int index, tKey key, tValue value) {
		// move space for the new key
		for (int i = this.getKeyCount() - 1; i >= index; --i) {
			this.setKey(i + 1, this.getKey(i));
			this.setValue(i + 1, this.getValue(i));
		}
		
		// insert new key and value
		this.setKey(index, key);
		this.setValue(index, value);
		++this.keyCount;
	}
	
	
	/**
	 * When splits a leaf node, the middle key is kept on new node and be pushed to parent node.
	 */
	@Override
	protected Node<tKey> split() {
		int midIndex = this.getKeyCount() / 2;
		
		LNode<tKey, tValue> newRNode = new LNode<tKey, tValue>();
		for (int i = midIndex; i < this.getKeyCount(); ++i) {
			newRNode.setKey(i - midIndex, this.getKey(i));
			newRNode.setValue(i - midIndex, this.getValue(i));
			this.setKey(i, null);
			this.setValue(i, null);
		}
		newRNode.keyCount = this.getKeyCount() - midIndex;
		this.keyCount = midIndex;
		
		return newRNode;
	}
	
	@Override
	protected Node<tKey> pushUpKey(tKey key, Node<tKey> leftChild, Node<tKey> rightNode) {
		throw new UnsupportedOperationException();
	}
	
	
	
	
	/* The codes below are used to support deletion operation */
	
	// public boolean delete(tKey key) {
	// 	int index = this.search(key);
	// 	if (index == -1)
	// 		return false;
		
	// 	this.deleteAt(index);
	// 	return true;
	// }
	
	// private void deleteAt(int index) {
	// 	int i = index;
	// 	for (i = index; i < this.getKeyCount() - 1; ++i) {
	// 		this.setKey(i, this.getKey(i + 1));
	// 		this.setValue(i, this.getValue(i + 1));
	// 	}
	// 	this.setKey(i, null);
	// 	this.setValue(i, null);
	// 	--this.keyCount;
	// }
	
	// @Override
	// protected void processChildrenTransfer(Node<tKey> borrower, Node<tKey> lender, int borrowIndex) {
	// 	throw new UnsupportedOperationException();
	// }
	
	// @Override
	// protected Node<tKey> processChildrenFusion(Node<tKey> leftChild, Node<tKey> rightChild) {
	// 	throw new UnsupportedOperationException();
	// }
	
	// /**
	//  * Notice that the key sunk from parent is be abandoned. 
	//  */
	// @Override
	// @SuppressWarnings("unchecked")
	// protected void fusionWithSibling(tKey sinkKey, Node<tKey> rightSibling) {
	// 	LNode<tKey, tValue> siblingLeaf = (LNode<tKey, tValue>)rightSibling;
		
	// 	int j = this.getKeyCount();
	// 	for (int i = 0; i < siblingLeaf.getKeyCount(); ++i) {
	// 		this.setKey(j + i, siblingLeaf.getKey(i));
	// 		this.setValue(j + i, siblingLeaf.getValue(i));
	// 	}
	// 	this.keyCount += siblingLeaf.getKeyCount();
		
	// 	this.setRightSibling(siblingLeaf.rightSibling);
	// 	if (siblingLeaf.rightSibling != null)
	// 		siblingLeaf.rightSibling.setLeftSibling(this);
	// }
	
	// @Override
	// @SuppressWarnings("unchecked")
	// protected tKey transferFromSibling(tKey sinkKey, Node<tKey> sibling, int borrowIndex) {
	// 	LNode<tKey, tValue> siblingNode = (LNode<tKey, tValue>)sibling;
		
	// 	this.insertKey(siblingNode.getKey(borrowIndex), siblingNode.getValue(borrowIndex));
	// 	siblingNode.deleteAt(borrowIndex);
		
	// 	return borrowIndex == 0 ? sibling.getKey(0) : this.getKey(0);
	// }
}
