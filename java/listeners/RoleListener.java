package listeners;

import main.Main;
import java.util.ArrayList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class RoleListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		 String msg = e.getMessage().getContentRaw();
		 String[] args = msg.split(" ");
		 
		 if(e.getAuthor().equals(e.getJDA().getSelfUser()))
		 {
		   return;
		 }
		 
		 if(args[0].equalsIgnoreCase(Main.PREFIX + "selfrole"))
		 {
			  if(args.length == 1)
			  {
				   String returnmsg = "**Available selfroles:** ";
				   
				   for(String role : Main.selfroles.getOrDefault(e.getGuild().getId(), new ArrayList<String>()))
				   {
					    
					    Role role_obj = e.getGuild().getRoleById(role);
					    returnmsg = String.valueOf(returnmsg) + "\n" + role_obj.getName() + " (" + getMemberCount(role_obj) + " members)";
				   } 
				   
				   returnmsg += "\n\n**Use" + Main.PREFIX + "selfrole {role} to add/remove a role.**";
				   returnmsg += "\n**Staff can use " + Main.PREFIX + "addselfrole {role id/name} and " + Main.PREFIX + "removeselfrole {role id/name} to make a role a selfrole or stop it being one.**";
				
				   
				   returnmsg += "\n**Contact al~ if there are any problems with these commands!**";
				   
				   e.getChannel().sendMessage(returnmsg).queue();
				   return;
			  } 
			  
			  String rolename = "";
			  for (int i = 1; i < args.length; i++)
				  rolename = String.valueOf(rolename) + " " + args[i];
			  
			  rolename = rolename.substring(1);
			  
			  //easter egg
			  if(rolename.equalsIgnoreCase("admin"))
			  {
				  e.getChannel().sendMessage("That doesn't work silly ;)").queue();
				  return;
			  }
			  
			  for(String roleid : Main.selfroles.get(e.getGuild().getId()))
			  {
				  Role role = e.getGuild().getRoleById(roleid);
				  
				  if(rolename.equalsIgnoreCase(role.getName()))
				  {
					   if(addOrRemoveRole(role, e.getMember()))
					     e.getChannel().sendMessage("Successfully gave role " + role.getName() + " to " + e.getMember().getEffectiveName()).queue();
					   else
					     e.getChannel().sendMessage("Successfully removed role " + role.getName() + " from " + e.getMember().getEffectiveName()).queue();
					   
					   return;
				  } 
			  } 
			  
			  e.getChannel().sendMessage("This role either doesn't exist or you are not allowed to assign this to yourself. Contact al~ if you believe this is a mistake :)").queue();
		 } 
	}
	
	
	
	
	public boolean addOrRemoveRole(Role r, Member m)
	{
		if(m.getRoles().contains(r))
		{  
			m.getGuild().removeRoleFromMember(m, r).queue();
			return false;
		} 
		
		m.getGuild().addRoleToMember(m, r).queue();
		return true;
	}
	
	public int getMemberCount(Role r)
	{
		return r.getGuild().getMembersWithRoles(r).size();
	}
}
