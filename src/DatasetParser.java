import store.Graph;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.util.Iterator;


public class DatasetParser {

	private Graph graph;
	private JSONParser parser;	
	private String topology_path;	
	private String vProperties_path;	
	private String eProperties_path;	

	public DatasetParser(Graph g, String path) {
		this.graph = g;
		this.parser = new JSONParser();
		this.topology_path = path + "topology.json";
		this.vProperties_path = path + "vprops.json";
		this.eProperties_path = path + "eprops.json";
	}

	public void parse() {

		System.out.println("reading topology info ...");
		try {
			Object obj = this.parser.parse(new FileReader(this.topology_path));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray cons = (JSONArray) jsonObject.get("connections");

			Iterator<JSONArray> iterator = cons.iterator();
			while (iterator.hasNext()) {
				JSONArray ent = iterator.next();
				Iterator<Long> enti = ent.iterator();
				this.graph.addEdge(enti.next().intValue(), enti.next().intValue(), enti.next().longValue() );
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("reading vertex properties ...");
		try {
			Object obj = this.parser.parse(new FileReader(this.vProperties_path));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray cons = (JSONArray) jsonObject.get("properties");

			Iterator<JSONObject> iterator = cons.iterator();
			while (iterator.hasNext()) {
				JSONObject ent = iterator.next();
				int vid = ((Long)ent.get("vid")).intValue();
				String key = (String)ent.get("key");
				try {
					int val = ((Long)ent.get("val")).intValue();
					this.graph.addVertexProperty(vid, key, val);
				}
				catch (Exception e) {
					String val = (String)ent.get("val");
					this.graph.addVertexProperty(vid, key, val);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("reading edge properties ...");
		try {
			Object obj = this.parser.parse(new FileReader(this.eProperties_path));
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray cons = (JSONArray) jsonObject.get("properties");

			Iterator<JSONObject> iterator = cons.iterator();
			while (iterator.hasNext()) {
				JSONObject ent = iterator.next();
				long eid = ((Long)ent.get("eid")).longValue();
				String key = (String)ent.get("key");
				try {
					int val = ((Long)ent.get("val")).intValue();
					this.graph.addEdgeProperty(eid, key, val);
				}
				catch (Exception e) {
					String val = (String)ent.get("val");
					this.graph.addEdgeProperty(eid, key, val);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}




	}
}