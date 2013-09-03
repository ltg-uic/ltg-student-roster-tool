package utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.BasicDBObject;

public class UtilTest {
	
	@Test
	public void appendRunTo_idTest() {
		BasicDBObject person = new BasicDBObject("_id", "testUser");
		Utils.appendRunTo_id(person, "testRun");
		assertEquals(person.getString("_id"), "testRun#testUser");
	}
	
	@Test
	public void stripRunTo_idTest() {
		BasicDBObject person = new BasicDBObject("_id", "testRun#testUser");
		Utils.stripRunFrom_id(person);
		assertEquals(person.getString("_id"), "testUser");
	}

}
