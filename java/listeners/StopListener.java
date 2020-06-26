package listeners;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StopListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		String msg = e.getMessage().getContentRaw();
		String[] args = msg.split(" ");
		
		//if member has discord mods role or admin or is owner
		if(CommandListener.isStaff(e.getMember()))
		{
			// ^shutdown
			if(args[0].equalsIgnoreCase(Main.PREFIX + "shutdown"))
			{
				e.getJDA().shutdownNow();
				System.exit(0);
			}
			
			// ^disable alselfroles
			if(args[0].equalsIgnoreCase(Main.PREFIX + "disable") && args[1].equalsIgnoreCase("remembermutes"))
			{				
				e.getChannel().sendMessage("*Disabling al's remembermutes code...*").queue();
				e.getJDA().shutdown();
				System.exit(0);
			}
		}

	}
}