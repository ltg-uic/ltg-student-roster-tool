package utils;

import com.mongodb.BasicDBObject;

public class Utils {
	
	public static BasicDBObject appendRunTo_id(BasicDBObject person, String run) {
		person.put("_id", run + "#" + person.getString("_id") );
		return person;
	}
	
	
	public static BasicDBObject stripRunTo_id(BasicDBObject person) {
		person.put("_id", person.getString("_id").split("#")[1] );
		return person;
	}

}
