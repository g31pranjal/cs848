package exec;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.util.Iterator;

public class Parser {

	private JSONParser parser;

	public Parser() {
		this.parser = new JSONParser();
	}

	public Query parseFromFile(String path) {
		try {
			Query qry = new Query();
			Object obj = this.parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray m = (JSONArray) jsonObject.get("match");
			JSONArray q = (JSONArray) jsonObject.get("where");

			Iterator<JSONArray> mIterator = m.iterator();
			while (mIterator.hasNext()) {
				JSONArray mEnt = mIterator.next();
				Iterator<String> mEntIterator = mEnt.iterator();
				qry.addMatch(mEntIterator.next(), mEntIterator.next(), mEntIterator.next());
			}

			Iterator<JSONArray> qIterator = q.iterator();
			while (qIterator.hasNext()) {
				JSONArray qEnt = qIterator.next();
				Iterator<String> qEntIterator = qEnt.iterator();
				qry.addWhere(qEntIterator.next(), qEntIterator.next(), 
					qEntIterator.next(), qEntIterator.next().toString());
			}
			return qry;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

	}

	public static void main(String[] args) {
		Parser p = new Parser();
		System.out.println(p.parseFromFile("./query.json"));
	}

}

