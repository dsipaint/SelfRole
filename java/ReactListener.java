import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactListener extends ListenerAdapter
{
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e)
	{
		if(e.getMessageId().equals(Main.REACT_MSG_ID))
		{
			Role r = Main.emote_roles.get(e.getReactionEmote().isEmoji() ? e.getReactionEmote().getAsCodepoints() : e.getReactionEmote().getId());
			if(r != null)
			{
				if(e.getMember() == null)
				{
					e.retrieveMember().queue(member -> 
					{
						if(member.getUser().equals(e.getJDA().getSelfUser()))
							return;
						
						e.getGuild().addRoleToMember(member, r).queue();
					});
				}
				else
				{
					if(e.getUser().equals(e.getJDA().getSelfUser()))
						return;
					
					e.getGuild().addRoleToMember(e.getMember(), r).queue();
				}
			}
			
			return;
		}
	}
	
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent e)
	{
		if(e.getMessageId().equals(Main.REACT_MSG_ID))
		{
			Role r = Main.emote_roles.get(e.getReactionEmote().isEmoji() ? e.getReactionEmote().getAsCodepoints() : e.getReactionEmote().getId());
			if(r != null)
			{
				if(e.getMember() == null)
				{
					e.retrieveMember().queue(member -> 
					{
						if(member.getUser().equals(e.getJDA().getSelfUser()))
							return;
						
						e.getGuild().removeRoleFromMember(member, r).queue();
					});
				}
				else
				{
					if(e.getUser().equals(e.getJDA().getSelfUser()))
						return;
					
					e.getGuild().removeRoleFromMember(e.getMember(), r).queue();
				}
			}
			
			return;
		}
	}
}
