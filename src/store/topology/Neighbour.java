package store.topology;
import java.util.Comparator;

public class Neighbour {
	public Integer src;
	public Integer dst;
	public Long eid;
	public Integer eprop;
	
	public Neighbour(int a, int b, long c) {
		this.src = a;
		this.dst = b;
		this.eid = c;
		this.eprop = 0;
	}

	public Integer getPropIndex() {
		return this.eprop;
	}

	public void setPropIndex(int i) {
		this.eprop = i;
	}

	public String toString() {
		return this.src.toString()+":"+this.dst.toString()+
				":"+this.eid.toString()+":"+this.eprop.toString();
	}

}


class NeighbourCompare implements Comparator<Neighbour> {
	@Override
	public int compare(Neighbour a, Neighbour b) {
		return a.dst.compareTo(b.dst);
	}
}
