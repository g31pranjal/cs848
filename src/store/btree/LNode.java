package store.btree;
import java.util.List;
import java.util.ArrayList;

class LNode extends Node {
	protected final static int LEAFORDER = 32;
	private LElement[] values;
	
	public LNode() {
		this.keys = new Short[LEAFORDER + 1];
		this.values = new LElement[LEAFORDER + 1];
	}

	@SuppressWarnings("unchecked")
	public LElement getValue(int index) {
		return this.values[index];
	}

	@SuppressWarnings("unchecked")
	public List<LElement> getValues(int index, int direction) {
		Short key = this.keys[index];
		List<LElement> ret = new ArrayList<LElement>();
		List<LElement> l = new ArrayList<LElement>();
		
		while(index < this.getKeyCount() && this.keys[index] == key) {
			ret.add(this.values[index]);
			index++;
		}

		if(this.leftSibling != null && direction <= 0) {
			int i = this.leftSibling.search(key);
			if(i != -1) 
				l.addAll(((LNode)this.leftSibling).getValues(i, -1));
		}

		l.addAll(ret);
		if(this.rightSibling != null && direction >= 0) {
			int i = this.rightSibling.search(key);
			if(i != -1) 
				l.addAll(((LNode)this.rightSibling).getValues(i, 1));
		}
		return l;
	}

	public void setValue(int index, LElement value) {
		this.values[index] = value;
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.lNode;
	}

	@Override
	public int search(Short key) {
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
	
	public void insertKey(Short key, LElement value) {
		int index = 0;
		while (index < this.getKeyCount() && this.getKey(index).compareTo(key) < 0)
			++index;
		this.insertAt(index, key, value);
	}
	
	private void insertAt(int index, Short key, LElement value) {
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
	protected Node split() {
		int midIndex = this.getKeyCount() / 2;
		
		LNode newRNode = new LNode();
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
	protected Node pushUpKey(Short key, Node leftChild, Node rightNode) {
		throw new UnsupportedOperationException();
	}
	
}
