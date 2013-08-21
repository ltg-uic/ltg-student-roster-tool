# LTG Student Roster Tool 

This is simple tool to create classroom rosters for all LTG applications.

One of the goal of this tool is to standardize the way we save information about students. Look in the schema sub-folder for an exmaple of how we do this.

Another goal of this tool is to simplify the creation of XMPP users for each of the students.

Finally this tool is buil to simplify life of LTG coders NOT to be the most generic tool for classroom rosters. It was build around our workflow therefore it is minimal and to the poin.


## People's schema

```
{
	"_id" : "mandatory",
	"run" : "mandatory, added automatically by APIs"
}
```

- `_id` is usually the nickname (the name students are known in the class) and is a *unique mandatory* field used as the unique identifier for that person.

- XMPP users are created with the same username and password which is their nick. 
 
## API documentation

We used the AWESOME apiary.io to [document our APIs](http://docs.gugo.apiary.io/).
