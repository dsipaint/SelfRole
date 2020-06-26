package listeners;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VCJoinListener extends ListenerAdapter
{
	public void onGuildVoiceJoin(GuildVoiceJoinEvent e)
	{
		for(Member m : Main.unmutelist)
		{
			//check if this is the correct guild and user
			//do this way as opposed to checking the member object itself
			//in case changes such as role affect this comparison
			if(m.getGuild().equals(e.getGuild())
					&& m.getUser().equals(e.getMember().getUser()))
			{
				//if they're still muted, unmute them
				if(m.getVoiceState().isGuildMuted())
					m.mute(false).queue();
				
				//we can stop watching them now
				Main.unmutelist.remove(m);
				
				//confirmation message?
			}
		}
		
		for(Member m : Main.mutelist)
		{
			//check if this is the correct guild and user
			//do this way as opposed to checking the member object itself
			//in case changes such as role affect this comparison
			if(m.getGuild().equals(e.getGuild())
					&& m.getUser().equals(e.getMember().getUser()))
			{
				//if they're still unmuted, unmute them
				if(!m.getVoiceState().isGuildMuted())
					m.mute(true).queue();
				
				//we can stop watching them now
				Main.mutelist.remove(m);
				
				//confirmation message?
			}
		}
	}
}
