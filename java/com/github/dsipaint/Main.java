package com.github.dsipaint;

import com.github.dsipaint.listeners.AdminCommandListener;
import com.github.dsipaint.listeners.RoleDeleteListener;
import com.github.dsipaint.listeners.RoleListener;
import com.github.dsipaint.listeners.StopListener;
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
  
  public static void main(String[] args) {
    try {
      jda = (new JDABuilder(AccountType.BOT)).setToken("NjE4NTQzODgwNDM4MTUzMjQ2.XvXFbg.BBtM9ygrAmp3IL1uJpkTKk6ShC8").build();
    }
    catch (LoginException e) {
      
      e.printStackTrace();
    } 

    
    try {
      jda.awaitReady();
    }
    catch (InterruptedException e) {
      
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
    
    jda.addEventListener(new Object[] { new RoleListener() });
    jda.addEventListener(new Object[] { new AdminCommandListener() });
    jda.addEventListener(new Object[] { new StopListener() });
    jda.addEventListener(new Object[] { new RoleDeleteListener() });
  }
}
