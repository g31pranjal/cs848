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

	public Execute(Graph g, Query q) {
		this.q = q;
		this.g = g;
		this.plan = getPlan();
		if(g.getClass().getName().equals("store.GraphBTree"))
			this.varient = "btree";
		else if(g.getClass().getName().equals("store.GraphVanilla"))
			this.varient = "vanilla";
	}


	public List<Result> extendRight(List<Result> lr) {
		List<Result> res = new ArrayList<Result>();
		Result newr;
		for(Result r : lr ) {
			int src = r.path.get(r.path.size()-1);
			List<Integer> neigh = this.g.getOutNeighbours(src);
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
		else if(op.equals("=="))
			return valr == valq;

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
		else if(op.equals("=="))
			return valr.compareTo(valq) == 0;

		return false;
	}

	public void filter(Result r, String opr, String val) {
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
		int itr = 0;
		int pf = 0;
		while(it.hasNext()) {
			itr++;
			Result r = it.next();
			this.searchEdgeProperties(r, this.q.wheres.get(i).prop);
			if(r.valid) {
				pf++;
				this.filter(r, this.q.wheres.get(i).opr, this.q.wheres.get(i).val);
			}
			if(r.valid) {
				ret.add(r);
			}
		}
		System.out.println(itr);
		System.out.println(pf);
		return ret;
	}

	public List<Result> hashJoin(List<Result> resL, List<Result> resR) {
		return null;
	}

	public void getResults() {
		String plan = this.getPlan();
		List<Result> res, res1, res2;

		if(plan.equals("plan1")) {
			if(varient.equals("vanilla")) {
				res = this.scanNFilter(0);
				System.out.println(res.size());
			}
			else if(varient.equals("btree")) {
				res = ((GraphBTree)this.g).searchByProperty(this.q.wheres.get(0).prop);
				System.out.println(res.size());
				res1 = new ArrayList<Result>();
				for(Result r : res) {
					this.filter(r, this.q.wheres.get(0).opr, this.q.wheres.get(0).val);
					if(r.valid) {
						res1.add(r);
					}
				}
				System.out.println(res1.size());
			}
		}
		else if(plan.equals("plan2")) {
			if(varient.equals("vanilla")) {
				res = this.scanNFilter(0);
				System.out.println(res.size());
				res = this.extendRight(res);
				System.out.println(res.size());
				res = this.extendRight(res);
				System.out.println(res.size());
			}
			else if(varient.equals("btree")) {
				res1 = ((GraphBTree)this.g).searchByProperty(this.q.wheres.get(0).prop);
				res = new ArrayList<Result>();
				for(Result r : res1) {
					this.filter(r, this.q.wheres.get(0).opr, this.q.wheres.get(0).val);
					if(r.valid) {
						res.add(r);
					}
				}
				res = this.extendRight(res);
				res = this.extendRight(res);
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

