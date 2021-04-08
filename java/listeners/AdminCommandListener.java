package listeners;

import java.util.ArrayList;
import java.util.List;

import main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AdminCommandListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(e.getAuthor().equals(e.getJDA().getSelfUser()))
		  return;
		
		if(isStaff(e.getMember()))
		{
			 String msg = e.getMessage().getContentRaw();
			 String[] args = msg.split(" ");
			 
			 String guild_id = e.getGuild().getId();
			 
			 if(args[0].equalsIgnoreCase(Main.PREFIX + "addselfrole"))
			 {
				if(args.length == 1)
				{
				  e.getChannel().sendMessage("No role specified.").queue();
				  return;
				} 
				
				String full_arg = "";
				
				for (int i = 1; i < args.length; i++)
				  full_arg = String.valueOf(full_arg) + " " + args[i];
				
				full_arg = full_arg.substring(1);
				
				String roleid = null;
				
				if(full_arg.matches("\\d{18}"))
				{
					if(e.getGuild().getRoleById(full_arg) != null)
					  roleid = full_arg;
					else
					{
						e.getChannel().sendMessage("Could not find role in server.").queue();
						return;
					}
				}
				else
				{
					List<Role> roles = e.getGuild().getRolesByName(full_arg, true);
					if(roles.isEmpty())
					{
						e.getChannel().sendMessage("Could not find a role to add with the name " + 
						    full_arg).queue();
						return;
					}
					
					roleid = roles.get(0).getId();
				} 
				
				
				if((Main.selfroles.getOrDefault(guild_id, new ArrayList<String>())).contains(roleid))
				{		  
					e.getChannel().sendMessage("This role is already a selfrole!").queue();
					return;
				} 
				
				Main.selfroles.get(guild_id).add(roleid);
				
				e.getChannel().sendMessage("Added role " + 
				    e.getGuild().getRoleById(roleid).getName() + " as an available selfrole.").queue();
				
				
				return;
			 } 
			 
			 if(args[0].equalsIgnoreCase("^removeselfrole"))
			 {
				 
				 if(args.length == 1)
				 {
					 e.getChannel().sendMessage("No role specified.").queue();
					 return;
				 } 
				 
				 String full_arg = "";
				 
				 for(int i = 1; i < args.length; i++)
				   full_arg = String.valueOf(full_arg) + " " + args[i];
				
				 full_arg = full_arg.substring(1);
				 
				 String roleid = null;
				
				 
				 if(full_arg.matches("\\d{18}"))
				 {
					 if(Main.selfroles.get(guild_id).contains(full_arg))
					   roleid = full_arg;
					 else
					 {
						 e.getChannel().sendMessage("Could not find role in selfroles.").queue();
						 return;
					 } 
				 }
				 else
				 {
					 for(String id : Main.selfroles.get(guild_id))
					 {
						 if(full_arg.equalsIgnoreCase(e.getGuild().getRoleById(id).getName()))
						 {
							 roleid = id;
							 break;
						 } 
					 } 
					 
					 if(roleid == null)
					 {
						 e.getChannel().sendMessage("Could not find a selfrole to remove with the name " + full_arg).queue();
						 return;
					 } 
				 } 
				 
				 Main.selfroles.get(guild_id).remove(roleid);
				 
				 e.getChannel().sendMessage("Removed role " + 
				     e.getGuild().getRoleById(roleid).getName() + " as an available selfrole.").queue();
				 return;
			 }
		}
	} 
	
	
	//return true if a member has discord mod, admin or is owner
	public static boolean isStaff(Member m)
	{
		try
		{
			//if owner
			if(m.isOwner())
				return true;
		}
		catch(NullPointerException e)
		{
			//no error message reee its pissing me off
		}
		
		//if admin
		if(m.hasPermission(Permission.ADMINISTRATOR))
			return true;
		
		//if discord mod TODO: Make discord mod module for all servers
		switch(m.getGuild().getId())
		{
			case "565623426501443584" : //wilbur's discord
				for(Role r : m.getRoles())
				{
					if(r.getId().equals("565626094917648386")) //wilbur discord mod
						return true;
				}
				break;
				
			case "640254333807755304" : //charlie's server
				for(Role r : m.getRoles())
				{
					if(r.getId().equals("640255355401535499")) //charlie discord mod
						return true;
				}
				break;
		}
		
		return false;
	}
}
