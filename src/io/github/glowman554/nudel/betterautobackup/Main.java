package io.github.glowman554.nudel.betterautobackup;

import java.io.File;
import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.plugin.Plugin;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.entities.TextChannel;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class Main implements Plugin
{

	@Override
	public void on_load() throws Exception
	{
		if (new File("perms.json").exists() && new File("science.json").exists())
		{
			new Thread() {
				public void run()
				{
					while (true)
					{					
						try
						{
							String file = FileUtils.randomTmpFile("json");
							
							Json json = Json.json();
	
							JsonNode perms_root = json.parse(FileUtils.readFile("perms.json"));
							JsonNode science_root = json.parse(FileUtils.readFile("science.json"));
							JsonNode sciencev2_root = json.parse(FileUtils.readFile("sciencev2.json"));
							
							JsonNode root = JsonNode.object();
							root.set("perms", perms_root);
							root.set("science", science_root);
							root.set("sciencev2", sciencev2_root);
							
							FileUtils.writeFile(file, json.serialize(root));
							
							TextChannel c = (TextChannel) Discord.discord.jda.getGuildChannelById(System.getenv("BACKUP_CHANNEL"));
							c.sendFile(new File(file)).queue();
						}
						catch (IOException|JsonSyntaxException e)
						{
							e.printStackTrace();
						}
						
						try
						{
							Thread.sleep(1000 * 60 * 60);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
							return;
						}
						
					}
				}
			}.start();
		} else {
			Discord.discord.commandManager.addCommand("load-backup", new LoadBackupCommand());
		}
	}
}
