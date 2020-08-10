package com.github.dsipaint.listeners;

import com.github.dsipaint.Data;
import com.github.dsipaint.Main;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class StopListener
  extends ListenerAdapter
{
  public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
    String msg = e.getMessage().getContentRaw();
    String[] args = msg.split(" ");

    
    if (AdminCommandListener.isStaff(e.getMember())) {

      
      if (args[0].equalsIgnoreCase("^shutdown")) {

        
        try {

          
          Data.writeSelfRoles(Main.selfroles);
        }
        catch (IOException e1) {
          
          e1.printStackTrace();
        } 
        
        e.getJDA().shutdownNow();
        System.exit(0);
      } 

      
      if (args[0].equalsIgnoreCase("^disable") && args[1].equalsIgnoreCase("alselfroles")) {

        
        try {

          
          Data.writeSelfRoles(Main.selfroles);
        }
        catch (IOException e1) {
          
          e1.printStackTrace();
        } 
        
        e.getChannel().sendMessage("*Disabling al's self-role code...*").queue();
        e.getJDA().shutdown();
        System.exit(0);
      } 
    } 
  }
}
