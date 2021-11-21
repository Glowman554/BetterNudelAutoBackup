package io.github.glowman554.nudel.betterautobackup;

import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.discord.commands.Command;
import io.github.glowman554.nudel.discord.commands.CommandEvent;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class LoadBackupCommand implements Command
{

	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.args.length != 1)
		{
			event.commandFail("Please specify a download link!");
		}
		else
		{
			BaseApi api = new BaseApi();
			
			String content = api.request(event.args[0]);
			
			Json json = Json.json();
			
			JsonNode root = json.parse(content);
			
			FileUtils.writeFile("perms.json", json.serialize(root.get("perms")));			
			FileUtils.writeFile("science.json", json.serialize(root.get("science")));

			System.exit(-1);
		}
	}

	@Override
	public String get_long_help()
	{
		return null;
	}

	@Override
	public String get_permission()
	{
		return null;
	}

	@Override
	public String get_short_help()
	{
		return null;
	}

	@Override
	public void on_register()
	{
		System.out.println("WARNING: load backup registerd... this allows everybody to load a backup thats not soooo good!");
	}

}
