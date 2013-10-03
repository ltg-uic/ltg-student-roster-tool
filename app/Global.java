import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.Results;
import play.mvc.SimpleResult;

import com.mongodb.BasicDBObject;


public class Global extends GlobalSettings {
	
	@Override
	public Promise<SimpleResult> onHandlerNotFound(RequestHeader arg) {
		return Promise.<SimpleResult>pure( Results.notFound(new BasicDBObject("status", "error").append("message", arg+" is not a valid resource").toString()).as("application/json") );
	}
	
	@Override
	public Promise<SimpleResult> onBadRequest(RequestHeader request, String error) {
		return Promise.<SimpleResult>pure( Results.badRequest(new BasicDBObject("status", "error").append("message", "Invalid JSON").toString()).as("application/json") );
	}

}
