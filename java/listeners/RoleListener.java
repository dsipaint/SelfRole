package listeners;

import java.util.ArrayList;

import main.Main;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message.MentionType;
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
				
				StringBuilder returnmsg = new StringBuilder("**Available selfroles:** ");
				ArrayList<String> roles = Main.selfroles.getOrDefault(e.getGuild().getId(), new ArrayList<String>());
				for(String role : roles)
				{
					 Role role_obj = e.getGuild().getRoleById(role);	
					 returnmsg.append("\n" + role_obj.getName() + " (" + e.getGuild().getMembersWithRoles(role_obj).size() + " members)");
				}
				
				returnmsg.append("\n\n**Use " + Main.PREFIX + "selfrole {role} to add/remove a role.**");
				returnmsg.append("\n**Staff can use " + Main.PREFIX + "addselfrole {role id/name} and " + Main.PREFIX + "removeselfrole {role id/name} to make a role a selfrole or stop it being one.**");
				returnmsg.append("\n**Contact al~ if there are any problems with these commands!**");
					
				e.getChannel().sendMessage(returnmsg.toString()).queue();
				
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
						if(addOrRemoveRole(role, e.getMember())) //Ok, NOW it's fixed- thanks for the laughs guys ;)
							e.getChannel().sendMessage(new MessageBuilder("Successfully gave role " + role.getName() + " to " + e.getMember().getEffectiveName()).setAllowedMentions(new ArrayList<MentionType>()).build()).queue();
						else
							e.getChannel().sendMessage(new MessageBuilder("Successfully removed role " + role.getName() + " from " + e.getMember().getEffectiveName()).setAllowedMentions(new ArrayList<MentionType>()).build()).queue();
						
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
}
