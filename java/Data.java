import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Data
{
	static final String FILE_LOC = "roles.dat";
	
	public static void readEmoteRoleData() throws IOException
	{	
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE_LOC)));
		String line = "";
		
		while((line = br.readLine()) != null)
		{
			String[] ids = line.split("\t"); //replace unicode in dat file with names? jda code seems to suggest this might be the fix
			Main.emote_roles.put(ids[0], Main.jda.getRoleById(ids[1]));
		}
		
		br.close();
	}
	
	public static void writeEmoteRoleData() throws IOException
	{
		PrintWriter pw = new PrintWriter(new FileWriter(new File(FILE_LOC)));
		
		Main.emote_roles.forEach((emote, role) ->
		{
			pw.println(emote + "\t" + role.getId());
		});
		
		pw.close();
	}
	
	public static boolean isUnicode(String emote)
	{
		if(emote.matches("U\\+(\\d|\\w){4,5}"))
			return true;
		
		return false;
	}
}
