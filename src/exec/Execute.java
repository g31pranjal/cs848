package exec;
import store.Graph;
import store.GraphVanilla;
import store.GraphBTree;
import store.topology.Result;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Execute {

	private Query q;
	private Graph g;
	private String plan;
	// - vanilla, btree
	private String varient;

	public String getPlan() {
		if(this.q.matches.size() == 1 && this.q.wheres.size() == 1 )
			return "plan1";
		else if(this.q.matches.size() == 3 && this.q.wheres.size() == 1 )
			return "plan2";
		else if(this.q.matches.size() == 4 && this.q.wheres.size() == 2 )
			return "plan3";
		else 
			return "unknown";
	}

	public Execute(Graph g, Query q, String varient) {
		this.q = q;
		this.g = g;
		this.plan = getPlan();
		this.varient = varient;
	}


	public List<Result> extendRight(List<Result> lr) {
		List<Result> res = new ArrayList<Result>();
		Result newr;
		for(Result r : lr ) {
			int src = r.path.get(r.path.size()-1);
			List<Integer> neigh = this.g.getOutNeighbours(src);
			// System.out.println(neigh);
			for(Integer i : neigh) {
				if(!(r.path.contains(i))) {
					newr = new Result();
					newr.path = (ArrayList<Integer>)((ArrayList<Integer>)r.path).clone();
					newr.path.add(i);
					res.add(newr);
				}
			}
		}
		return res;
	}

	public List<Result> extendLeft(List<Result> lr) {
		List<Result> res = new ArrayList<Result>();
		Result newr;
		for(Result r : lr ) {
			int src = r.path.get(0);
			List<Integer> neigh = this.g.getInNeighbours(src);
			// System.out.println(neigh);
			for(Integer i : neigh) {
				if(!(r.path.contains(i))) {
					newr = new Result();
					newr.path.add(i);
					newr.path.addAll(r.path);
					res.add(newr);
				}
			}
		}
		return res;
	}

	public void searchEdgeProperties(Result r, String property) {
		Map<String, String> props = ((GraphVanilla)this.g).retrieveEdgeProperties(r.ePropIndex.get(0));
		if(props.containsKey(property)) {
			r.key = property;
			r.val = props.get(property);
		}
		else 
			r.valid = false;
	}

	public boolean comparision(int valr, int valq, String op) {
		if(op.equals("<="))
			return valr <= valq;
		else if(op.equals(">="))
			return valr >= valq;
		else if(op.equals(">"))
			return valr > valq;
		else if(op.equals("<"))
			return valr < valq;

		return false;
	}

	public boolean comparision(String valr, String valq, String op) {
		if(op.equals("<="))
			return valr.compareTo(valq) <= 0;
		else if(op.equals(">="))
			return valr.compareTo(valq) >= 0;
		else if(op.equals(">"))
			return valr.compareTo(valq) > 0;
		else if(op.equals("<"))
			return valr.compareTo(valq) < 0;

		return false;
	}

	public void filter(Result r, String opr, String val) {
		Map<String, String> props = ((GraphVanilla)this.g).retrieveEdgeProperties(r.ePropIndex.get(0));
		
		try {
			int vali = Integer.parseInt(val);
			try {
				int valr = Integer.parseInt(r.val);
				int valq = vali;
				if(!this.comparision(valr, valq, opr))
					r.valid = false;
			}
			catch(Exception e) {
				String valr = r.val;
				String valq = val;
				if(!this.comparision(valr, valq, opr))
					r.valid = false;
			}
		}
		catch(Exception e) {
			String valr = r.val;
			String valq = val;
			if(!this.comparision(valr, valq, opr))
				r.valid = false;
		}
	}

	public List<Result> scanNFilter(int i) {
		List<Result> ret = new ArrayList<Result>();
		Iterator<Result> it = ((GraphVanilla)this.g).scanByEdges();
		while(it.hasNext()) {
			Result r = it.next();
			this.searchEdgeProperties(r, this.q.wheres.get(i).prop);
			if(r.valid) {
				this.filter(r, this.q.wheres.get(i).opr, this.q.wheres.get(i).val);
			}
			if(r.valid) {
				ret.add(r);
			}
		}
		return ret;
	}

	public void getResults() {
		String plan = this.getPlan();
		List<Result> res, res1, res2;

		if(plan.equals("plan1")) {
			if(varient.equals("vanilla")) {
				res = this.scanNFilter(0);
			}
			else if(varient.equals("btree")) {

			}
		}
		else if(plan.equals("plan2")) {
			if(varient.equals("vanilla")) {
				System.out.println("p2v1");
				res = this.scanNFilter(0);
				System.out.println(res);
				res = this.extendRight(res);
				System.out.println(res);
				res = this.extendRight(res);
				System.out.println(res);
			}
			else if(varient.equals("btree")) {
				System.out.println("p2bt");
				res = ((GraphBTree)this.g).searchByProperty(this.q.wheres.get(0).prop);
				System.out.println(res);
			}
		}
		else if(plan.equals("plan3")) {
			if(varient.equals("vanilla")) {
				System.out.println("p3v1");
				res1 = this.scanNFilter(0);
				res1 = this.extendLeft(res1);
				res2 = this.scanNFilter(1);
				res2 = this.extendLeft(res2);
			}
			else if(varient.equals("btree")) {

			}
		}
		else {
			System.out.println("plan choose error.");
		}
	}
}

