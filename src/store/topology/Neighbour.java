package store.topology;
import java.util.Comparator;

class Neighbour {
	public Integer dst;
	public Long eid;
	public Integer eprop;
	
	public Neighbour(int a, long b) {
		this.dst = a;
		this.eid = b;
		this.eprop = 0;
	}

	public String toString() {
		return this.dst.toString()+":"+this.eid.toString()+":"+this.eprop.toString();
	}

}


class NeighbourCompare implements Comparator<Neighbour> {
	@Override
	public int compare(Neighbour a, Neighbour b) {
		return a.dst.compareTo(b.dst);
	}
}
