package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

import org.json.simple.DeserializationException;

import listeners.AdminCommandListener;
import listeners.RoleDeleteListener;
import listeners.RoleListener;
import listeners.StopListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main
{
  static JDA jda;
  public static final String PREFIX = "^";
  public static HashMap<String, ArrayList<String>> selfroles;
  
	public static void main(String[] args)
	{
		try
		{
		  jda = JDABuilder.createDefault("NjE4NTQzODgwNDM4MTUzMjQ2.XW7N4Q.6tzP7ZM5Cj2RRl4opmutyuurenY")
				  .enableIntents(GatewayIntent.GUILD_MEMBERS)
				  .build();
		}
		catch (LoginException e)
		{
		  e.printStackTrace();
		} 
		
		try
		{
		  jda.awaitReady();
		}
		catch (InterruptedException e)
		{
		  
		  e.printStackTrace();
		}
		
		jda.getGuildById("565623426501443584").loadMembers();
		
		try
		{
			selfroles = Data.readSelfRoles();
		}
		catch (IOException | DeserializationException e)
		{
			e.printStackTrace();
		}
		
		jda.addEventListener(new RoleListener());
		jda.addEventListener(new AdminCommandListener());
		jda.addEventListener(new StopListener());
		jda.addEventListener(new RoleDeleteListener());
	}
}
