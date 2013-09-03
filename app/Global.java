import play.GlobalSettings;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import com.mongodb.BasicDBObject;


public class Global extends GlobalSettings {
	
	@Override
	public Result onHandlerNotFound(RequestHeader arg) {
		return Results.notFound(new BasicDBObject("status", "error").append("message", arg+" is not a valid resource").toString()).as("application/json");
	}
	
	@Override
	public Result onBadRequest(RequestHeader request, String error) {
		return Results.badRequest(new BasicDBObject("status", "error").append("message", "Invalid JSON").toString()).as("application/json");
	}

}
