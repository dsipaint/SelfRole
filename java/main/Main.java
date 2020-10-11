package main;

import listeners.AdminCommandListener;
import listeners.RoleDeleteListener;
import listeners.RoleListener;
import listeners.StopListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.security.auth.login.LoginException;

import org.json.simple.DeserializationException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main
{
  static JDA jda;
  public static final String PREFIX = "^";
  public static HashMap<String, ArrayList<String>> selfroles;
  
	public static void main(String[] args)
	{
		try
		{
		  jda = (new JDABuilder(AccountType.BOT)).setToken("").build();
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
