import java.io.IOException;
import java.util.LinkedHashMap;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main
{
	static final String REACT_MSG_ID = "764919889239605320";
	static final String REACT_CHANNEL_ID = "764602233563119637";
	static final String PREFIX = "^";
	
	static JDA jda;
	static LinkedHashMap<String, Role> emote_roles = new LinkedHashMap<String, Role>();
	
	public static void main(String[] args)
	{
		try
		{
			jda = JDABuilder.createDefault("")
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
			Data.readEmoteRoleData();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//add reactions to message
		jda.getTextChannelById(REACT_CHANNEL_ID).retrieveMessageById(REACT_MSG_ID).queue(message ->
		{
			emote_roles.keySet().forEach(emote ->
			{
				if(Data.isUnicode(emote))
				{
					message.addReaction(emote).queue();
				}
				else
					message.addReaction(jda.getEmoteById(emote)).queue();
			});
		});
		
		jda.addEventListener(new ReactListener());
		//NB: no role delete listener
		//NB: no role add/remove listener
		// this is because josh didn't let me write the message and hence edit the message which people react to, so I could update the list in the message
		jda.addEventListener(new StopListener());
	}
}
