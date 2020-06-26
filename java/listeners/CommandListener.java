package listeners;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		//this is a staff command
		if(isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			if(args[0].equalsIgnoreCase(Main.PREFIX + "rememberunmute"))
			{
				if(args.length == 1)
				{
					e.getChannel().sendMessage("Please specify a user").queue();
					return;
				}
				
				// ^rememberunmute {user id}
				if(args[1].matches("\\d{18}") && e.getGuild().getMemberById(args[1]) != null)
				{
					if(e.getGuild().getMemberById(args[1]).getVoiceState().inVoiceChannel())
					{
						e.getGuild().getMemberById(args[1]).mute(false).queue();
						e.getChannel().sendMessage("This user was still in a vc! I unmuted them :)").queue();
						return;
					}
					
					Main.unmutelist.add(e.getGuild().getMemberById(args[1]));
					e.getChannel().sendMessage("I'll make sure to unmute " + 
					e.getGuild().getMemberById(args[1]).getEffectiveName()
					+ " when they next join a vc!").queue();
					
					return;
				}
				
				// ^rememberunmute {user ping}
				if(args[1].matches("<@!\\d{18}>"))
				{
					//this trims the stuff around the id away
					String id = args[1].substring(3, args[1].length() - 1);
					
					if(e.getGuild().getMemberById(id).getVoiceState().inVoiceChannel())
					{
						e.getGuild().getMemberById(id).mute(false).queue();
						e.getChannel().sendMessage("This user was still in a vc! I unmuted them :)").queue();
						return;
					}
					
					Main.unmutelist.add(e.getGuild().getMemberById(id));
					e.getChannel().sendMessage("I'll make sure to unmute " + 
					e.getGuild().getMemberById(id).getEffectiveName()
					+ " when they next join a vc!").queue();
					
					return;
				}
			}
			
			if(args[0].equalsIgnoreCase(Main.PREFIX + "remembermute"))
			{
				if(args.length == 1)
				{
					e.getChannel().sendMessage("Please specify a user").queue();
					return;
				}
				
				// ^remembermute {user id}
				if(args[1].matches("\\d{18}") && e.getGuild().getMemberById(args[1]) != null)
				{
					if(e.getGuild().getMemberById(args[1]).getVoiceState().inVoiceChannel())
					{
						e.getGuild().getMemberById(args[1]).mute(true).queue();
						e.getChannel().sendMessage("This user was still in a vc! I muted them :)").queue();
						return;
					}
					
					Main.mutelist.add(e.getGuild().getMemberById(args[1]));
					e.getChannel().sendMessage("I'll make sure to mute " + 
					e.getGuild().getMemberById(args[1]).getEffectiveName()
					+ " when they next join a vc!").queue();
					
					return;
				}
				
				// ^remembermute {user ping}
				if(args[1].matches("<@!\\d{18}>"))
				{
					//this trims the stuff around the id away
					String id = args[1].substring(3, args[1].length() - 1);
					
					if(e.getGuild().getMemberById(id).getVoiceState().inVoiceChannel())
					{
						e.getGuild().getMemberById(id).mute(true).queue();
						e.getChannel().sendMessage("This user was still in a vc! I muted them :)").queue();
						return;
					}
					
					Main.mutelist.add(e.getGuild().getMemberById(id));
					e.getChannel().sendMessage("I'll make sure to mute " + 
					e.getGuild().getMemberById(id).getEffectiveName()
					+ " when they next join a vc!").queue();
					
					return;
				}
			}
		}
	}
	
	//return true if a member has discord mod, admin or is owner
		public static boolean isStaff(Member m)
		{
			//if owner
			if(m.isOwner())
				return true;
			
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
