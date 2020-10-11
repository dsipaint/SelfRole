package listeners;

import main.Main;

import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleDeleteListener extends ListenerAdapter
{
	public void onRoleDelete(RoleDeleteEvent e)
	{
		   String role_id = e.getRole().getId();
		   String guild_id = e.getGuild().getId();
		
		   if(Main.selfroles.get(guild_id) != null && Main.selfroles.get(guild_id).contains(role_id))
		     Main.selfroles.get(guild_id).remove(role_id); 
	}
}
