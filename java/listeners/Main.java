package listeners;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;

public class Main
{
	static JDA jda;
	static final String PREFIX = "^";
	static ArrayList<Member> unmutelist, mutelist;
	
	/*
	 * TODO: confirmation message for muting/unmuting
	 * 			possible efficiency improvements in VCJoinListener
	 */
	
	public static void main(String[] args)
	{
		try
		{
			jda = new JDABuilder(AccountType.BOT).setToken("NjE4NTQzODgwNDM4MTUzMjQ2.Xc3QXA.WkdHlVPIKOG1jOyX0HtPOP7_ggc").build();
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
		
		unmutelist = new ArrayList<Member>();
		mutelist = new ArrayList<Member>();
		
		jda.addEventListener(new CommandListener());
		jda.addEventListener(new VCJoinListener());
		jda.addEventListener(new StopListener());
	}
}
