package exec;
import store.Graph;
import store.topology.Result;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Execute {

	private Query q;
	private Graph g;

	public Execute(Graph g, Query q) {
		this.q = q;
		this.g = g;
	}

	public String getPlan() {
		if(this.q.matches.size() == 1 && this.q.wheres.size() == 1 )
			return "plan1";
		else if(this.q.matches.size() == 3 && this.q.wheres.size() == 1 )
			return "plan3";
		else if(this.q.matches.size() == 4 && this.q.wheres.size() == 2 )
			return "plan3";
		else 
			return "unknown";
	}

	public void searchEdgeProperties(Result r, String property) {
		Map<String, String> props = this.g.retrieveEdgeProperties(r.ePropIndex.get(0));
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
		Map<String, String> props = this.g.retrieveEdgeProperties(r.ePropIndex.get(0));
		
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
		Iterator<Result> it = this.g.scanByEdges();
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

	public void getResults(String varient) {
		String plan = this.getPlan();

		if(plan.equals("plan1")) {
			if(varient.equals("var1")) {

				System.out.println(this.scanNFilter(0));


				

			}
			else if(varient.equals("var2")) {

			}
		}
		else if(plan.equals("plan2")) {
			if(varient.equals("var1")) {
				this.scanNFilter(0);

			}
			else if(varient.equals("var2")) {

			}
		}
		else if(plan.equals("plan3")) {
			if(varient.equals("var1")) {
				this.scanNFilter(0);
				this.scanNFilter(1);

			}
			else if(varient.equals("var2")) {

			}
		}
		else {
			System.out.println("plan choose error.");
		}
	}
}

