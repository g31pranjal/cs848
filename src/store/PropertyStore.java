package store;
import java.util.List;
import java.util.ArrayList;

// [short key (2 bytes), int ref (4 bytes), type (1 byte), val (4/Any bytes), end (1 byte) ]

public class PropertyStore {

	private List<Byte> a;

	public PropertyStore() {
		this.a = new ArrayList<Byte>();
		this.a.add((byte)-1);
	}

	public int addProperty(int ref, short key, String val) {
		int at = this.a.size();
		List<Byte> b = new ArrayList<Byte>();

		for(int i=0;i<2;i++) {
			b.add((byte)(key%128));
			key /= 128;
		}

		for(int i=0;i<4;i++) {
			b.add((byte)(ref%128));
			ref /= 128;
		}

		b.add((byte)0);
		for(int i=0;i<val.length();i++) {
			b.add((byte)val.charAt(i) );
		}
		b.add((byte)-1);

		this.a.addAll(b);
		return at;
	}

	public int addProperty(int ref, short key, int val) {
		int at = this.a.size();
		List<Byte> b = new ArrayList<Byte>();

		for(int i=0;i<2;i++) {
			b.add((byte)(key%128));
			key /= 128;
		}

		for(int i=0;i<4;i++) {
			b.add((byte)(ref%128));
			ref /= 128;
		}

		b.add((byte)1);
		if(val <= 0){
			b.add((byte)1);
			val = -val;
		}
		else
			b.add((byte)0);

		for(int i=0;i<4;i++) {
			b.add((byte)(val%128));
			val /= 128;
		}
		b.add((byte)-1);

		this.a.addAll(b);
		return at;
	}

	public boolean isValid(int index) {
		if(index == 0) {
			return false;
		}
		if(this.a.get(index-1) == -1) {
			return true;
		}
		return false;
	}

	public Short getKeyAt(int index) {
		if(this.isValid(index)) {
			short i = 0;
			i = (short)(this.a.get(index) + 128*(this.a.get(index+1)) ); 
			return i;		
		}
		return null;
	}

	public Integer getRefAt(int index) {
		if(this.isValid(index)) {
			int i = 0;
			i = this.a.get(index+2) + 128*(this.a.get(index+3) + 
				128*(this.a.get(index+4) + 128*this.a.get(index+5) ) );
			return i;		
		}
		return null;
	}

	public String getTypeAt(int index) {
		if(this.isValid(index)) {
			if(this.a.get(index+6) == 0)
				return "string";
			else 
				return "num";
		}
		else 
			return null;
	}

	public Integer getNumAt(int index) {
		if(this.isValid(index)) {
			int i = 0;
			i = this.a.get(index+8) + 128*(this.a.get(index+9) + 
				128*(this.a.get(index+10) + 128*this.a.get(index+11) ) );
			if(this.a.get(index+7) == 1)
				i = -i;
			return i;		
		}
		return null;
	}

	public String getStringAt(int index) {
		if(this.isValid(index)) {
			String i = "";
			index = index + 7;
			while(this.a.get(index) != -1)
				i = i + (char)((int)this.a.get(index++));
			return i;		
		}
		return null;
	}

	public int size() {
		return this.a.size();
	}

	public String toString() {

		int i = 1;
		String a = "";
		while(i < this.a.size()) {
			if(this.isValid(i)) {
				a += this.getKeyAt(i).toString() + ", ";
				a += this.getRefAt(i).toString() + ", ";
				if(this.getTypeAt(i).equals("num"))
					a += this.getNumAt(i).toString();
				else
					a += this.getStringAt(i).toString();
				a += "\n";
			}
			i++;
		}

		return a;


	}

	

}