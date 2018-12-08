package store;
import java.util.List;
import java.util.ArrayList;

class ValueStore {

	private List<Byte> a;

	ValueStore() {
		this.a = new ArrayList<Byte>(16);
	}

	public int add(int data) {
		int at = this.a.size();
		List<Byte> b = new ArrayList<Byte>();
		b.add((byte)1);

		if(data <= 0){
			b.add((byte)1);
			data = -data;
		}
		else
			b.add((byte)0);

		for(int i=0;i<4;i++) {
			b.add((byte)(data%128));
			data /= 128;
		}
		b.add((byte)-1);

		this.a.addAll(b);
		return at;
	}

	public int add(String data) {
		int at = this.a.size();
		List<Byte> b = new ArrayList<Byte>();

		b.add((byte)0);
		for(int i=0;i<data.length();i++) {
			b.add((byte)data.charAt(i) );
		}
		b.add((byte)-1);

		this.a.addAll(b);
		return at;
	}

	public boolean isValid(int index) {
		if(index == 0 || this.a.get(index-1) == -1) {
			return true;
		}
		return false;
	}

	public String getTypeAt(int index) {
		if(this.isValid(index)) {
			if(this.a.get(index) == 0)
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
			i = this.a.get(index+2) + 128*(this.a.get(index+3) + 
				128*(this.a.get(index+4) + 128*this.a.get(index+5) ) );
			if(this.a.get(index+1) == 1)
				i = -i;
			return i;		
		}
		return null;
	}

	public String getStringAt(int index) {
		if(this.isValid(index)) {
			String i = "";
			index++;
			while(this.a.get(index) != -1)
				i = i + (char)((int)this.a.get(index++));
			return i;		
		}
		return null;
	}

	public int size() {
		return this.a.size();
	}

	public void print() {
		for(int i=0;i<this.a.size();i++)
			System.out.print(this.a.get(i) + ", ");
		System.out.println();
	}



	// public static void main(String[] args) {

	// 	ValueStore v = new ValueStore();

	// 	int a = v.add("hello");
	// 	int b = v.add("pranjal");
	// 	int c = v.add(-3524524);

	// 	v.print();

	// 	System.out.println(a);
	// 	System.out.println(b);
	// 	System.out.println(v.getStringAt(a));

	// }

}



