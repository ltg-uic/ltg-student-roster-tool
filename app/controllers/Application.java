package controllers;

import java.net.UnknownHostException;

import org.codehaus.jackson.JsonNode;

import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.WSRequestHolder;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class Application extends Controller {

	public static Result index() {
		return ok(views.html.index.render());
	}


	public static Result getPeopleInRun(String run) {
		if (run==null || run.isEmpty())
			return badRequest("You didn't specify a run!");
		BasicDBList roster = new BasicDBList();
		try {
			DBCollection people = new MongoClient().getDB("roster").getCollection("people");
			DBCursor peopleInRun = people.find(new BasicDBObject("run", run), new BasicDBObject("run", 0));
			try {
				while (peopleInRun.hasNext()) {
					roster.add(peopleInRun.next());
				}
			} finally {
				peopleInRun.close();
			}
		} catch (Exception e) {
			return internalServerError("Impossible to connect to DB");
		}
		// Assemble roster and send out
		return ok(new BasicDBObject("run", run).append("roster", roster).toString()).as("application/json");
	}

	
	@BodyParser.Of(BodyParser.Json.class)
	public static Result addPersonToRun(String run) {
		// Parse and validate
		JsonNode body = request().body().asJson(); 
		if (body==null)
			return badRequest("'Content-type' header needs to be 'application/json'");
		BasicDBObject json_body = (BasicDBObject) JSON.parse(body.toString());
		if (json_body.get("_id")==null)
			return badRequest("You need to specify at least '_id'");
		String nick =  json_body.get("_id").toString();
		if (nick.isEmpty())
			return badRequest("You need to specify at least '_id'");
		// Store in DB
		DBCollection people = null;
		try {
			people = new MongoClient().getDB("roster").getCollection("people");
		} catch (UnknownHostException e) {
			return internalServerError("Impossible to connect to DB");
		}
		try {
			people.insert(json_body.append("run", run));
		} catch (MongoException e) {
			return badRequest("A user with this name already exists, pick a different one");
		}
		// Create XMPP user (if plugin is active)
		WSRequestHolder req = WS.url("http://ltg.evl.uic.edu:9090/plugins/userService/userservice");
		req.setQueryParameter("type", "add");
		req.setQueryParameter("secret", "a6e58698e57a60acb3a8e20cbf3bdc3fdc9d8697");
		req.setQueryParameter("username", json_body.getString("_id"));
		req.setQueryParameter("password", json_body.getString("_id"));
		Promise<WS.Response> res = req.get();
		if (res.get().getBody().equals("<error>UserAlreadyExistsException</error>"))
			return badRequest("A user with this name already exists, pick a different one");
		if (res.get().getBody().equals("<error>RequestNotAuthorised</error>"))
			return badRequest("Impossible to satisfy the request");
		if (!res.get().getBody().equals("<result>ok</result>\n"))
			return internalServerError("Myseterious error while trying to register the XMPP user");
		return ok(json_body.toString()).as("application/json");
	}


	public static Result deletePersonInRun(String run, String person) {
		// Delete from DB
		try {
			DBCollection people = new MongoClient().getDB("roster").getCollection("people");
			people.remove(new BasicDBObject("_id", person));
		} catch (Exception e) {
			return internalServerError("Impossible to connect to DB");
		}
		// Delete XMPP user (if plugin is active)
		WSRequestHolder req = WS.url("http://ltg.evl.uic.edu:9090/plugins/userService/userservice");
		req.setQueryParameter("type", "delete");
		req.setQueryParameter("secret", "a6e58698e57a60acb3a8e20cbf3bdc3fdc9d8697");
		req.setQueryParameter("username", person);
		Promise<WS.Response> res = req.get();
		// TODO for future use...
		//if (res.get().getBody().equals("<error>UserNotFoundException</error>"))
		//return badRequest("A user with this name doesn't exist");
		if (res.get().getBody().equals("<error>RequestNotAuthorised</error>"))
			return badRequest("Impossible to satisfy the request");
		if (!res.get().getBody().equals("<result>ok</result>\n"))
			return internalServerError("Myseterious error while trying to register the XMPP user");
		return ok();
	}
	
	
	public static Result updatePersonInRun(String run, String person) {
		return notFound("Not yet implemented");
	}

}
