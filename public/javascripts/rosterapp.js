// LTG roster app
// Lets us create classroom rosters

// Load the application once the DOM is ready, using `jQuery.ready`:
$(function(){
		
	// Person model
	// -------------
	
	// A person has an `_id` and a `run` field
	var Person = Backbone.Model.extend({
		
		// Map Mongo id to Backbone id field
		idAttribute: "_id",
		
		
	});
	
	var Run = Backbone.Model.extend({
		
	});
	
	
	// Run model (a collection of people and some extra)
	// ------------
	var Run = Backbone.Collection.extend({
		
		// Reference to this collection's model.
		model: Person
	
	});
	
	
	var RunView = Backbone.View.extend({
		tagName:  "li",
	});
	
	
	// Create our global collection of **Runs**.
	//var Roster = new Run();
	
	// The Application
	// --------------- 
	//var RosterView = Backbone.View.extend();
	
	// Finally, we kick things off by creating the **App**.
	//var Roster = new RosterView;
	
	//var gugs = new Person("gugo", "myclass");
	
});

