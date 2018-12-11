package store.btree_v2;

public class CompoundKey implements Comparable<CompoundKey> {

	public Short key;
	public String val;

	public CompoundKey(Short key, String val) {
		this.key = key;
		this.val = val;
	}

	@Override
	public int compareTo(CompoundKey other) throws ClassCastException {
		if(this.key != ((CompoundKey)other).key)
			return this.key.compareTo(((CompoundKey)other).key);
		else {
			if(this.val == null || ((CompoundKey)other).val == null )
				return 0;
			try {
				Integer vala = new Integer(this.val);
				try {
					Integer valb = new Integer(((CompoundKey)other).val);
					return vala.compareTo(valb);
				}
				catch(Exception e) {
					return this.val.compareTo(((CompoundKey)other).val);
				}
			}
			catch(Exception e) {
				return this.val.compareTo(((CompoundKey)other).val);
			}
		}
	}

}