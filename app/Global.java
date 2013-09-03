import com.mongodb.BasicDBObject;

import play.GlobalSettings;
import play.mvc.Result;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;


public class Global extends GlobalSettings {
	
	@Override
	public Result onHandlerNotFound(RequestHeader arg) {
		return Results.notFound(new BasicDBObject("status", "error").append("message", arg+" is not a valid resource").toString()).as("application/json");
	}

}
