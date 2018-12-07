package exec;
import java.util.List;
import java.util.ArrayList;

class MatchSeg {
	public String e;
	public String src;
	public String dst;
	MatchSeg(String e, String src, String dst) {
		this.e = e;
		this.src = src;
		this.dst = dst;
	}
}

class WhereSeg {
	public String e;
	public String prop;
	public String opr;
	public String val;
	WhereSeg(String e, String prop, String opr, String val) {
		this.e = e;
		this.prop = prop;
		this.opr = opr;
		this.val = val;
	}
}

public class Query {
	public List<MatchSeg> matches;
	public List<WhereSeg> wheres;

	Query() {	
		this.matches = new ArrayList<MatchSeg>();
		this.wheres = new ArrayList<WhereSeg>();
	}

	public boolean addMatch(String a, String b, String c) {
		MatchSeg ms = new MatchSeg(a, b, c);
		this.matches.add(ms);
		return true;
	}

	public boolean addWhere(String a, String b, String c, String d) {
		WhereSeg ws = new WhereSeg(a, b, c, d);
		this.wheres.add(ws);
		return true;
	}

	public String toString() {
		String cnst = "MATCH\n";
		for(MatchSeg m : this.matches) {
			cnst += "\t" + "("+m.src+")-"+m.e+"->("+m.dst+")\n";
		}
		cnst += "WHERE\n";
		for(WhereSeg w : this.wheres) {
			cnst += "\t" +w.e+"."+w.prop+" "+w.opr+" "+w.val+"\n";
		}
		return cnst;
	}
}