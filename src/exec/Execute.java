package exec;
import store.Graph;
import store.topology.Result;
import java.util.Iterator;

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

	public void getReults(String varient) {
		String plan = this.getPlan();

		if(plan.equals("plan1")) {
			if(varient.equals("var1")) {

			}
			else if(varient.equals("var2")) {

			}
		}
		else if(plan.equals("plan2")) {
			if(varient.equals("var1")) {

			}
			else if(varient.equals("var2")) {

			}
		}
		else if(plan.equals("plan3")) {
			if(varient.equals("var1")) {

			}
			else if(varient.equals("var2")) {

			}
		}
		else {
			System.out.println("plan choose error.");
		}

	}





}