package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;


public class Data
{
	static final String FILE_LOC = "roles.json";
	
	public static void writeSelfRoles(HashMap<String, ArrayList<String>> selfroles) throws IOException
	{
		 JsonArray jsa = new JsonArray();
		 
		 selfroles.forEach((guild, roles) ->
		 {
		       
		       JsonObject guild_obj = new JsonObject();
		       guild_obj.put("guild", guild);
		       guild_obj.put("roles", roles);
		       
		       jsa.add(guild_obj);
		 });
		 
		 PrintWriter pw = new PrintWriter(new FileWriter(new File(FILE_LOC)));
		 pw.print(jsa.toJson());
		 pw.close();
	}
	
	
	
	
	public static HashMap<String, ArrayList<String>> readSelfRoles() throws FileNotFoundException, IOException, DeserializationException
	{
		 HashMap<String, ArrayList<String>> selfroles = new HashMap<String, ArrayList<String>>();
		 
		 Object file = Jsoner.deserialize(new FileReader(new File(FILE_LOC)));
		 JsonArray file_json_arr = (JsonArray)file;
		
		 
		 file_json_arr.forEach(guild_data ->
		 {
		       
		       JsonObject guild_data_obj = (JsonObject)guild_data;
		       JsonArray roles_json = (JsonArray)guild_data_obj.get("roles");
		       ArrayList<String> guild_roles = new ArrayList<String>();
		       roles_json.forEach(str ->
		       {
		     	  guild_roles.add(str.toString());
		       });
		       
		       selfroles.put((String)guild_data_obj.get("guild"), guild_roles);
		     });
		 
		 return selfroles;
	}
}
