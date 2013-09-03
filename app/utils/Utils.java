package utils;

import com.mongodb.BasicDBObject;

public class Utils {
	
	public static void appendRunTo_id(BasicDBObject person, String run) {
		person.put("_id", run + "#" + person.getString("_id") );
	}
	
	
	public static void stripRunTo_id(BasicDBObject person) {
		person.put("_id", person.getString("_id").split("#")[1] );
	}

}
