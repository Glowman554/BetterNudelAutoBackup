package io.github.glowman554.nudel.autobackup;

import java.io.File;
import java.io.IOException;

import io.github.glowman554.nudel.discord.Discord;
import io.github.glowman554.nudel.plugin.Plugin;
import io.github.glowman554.nudel.utils.FileUtils;
import net.dv8tion.jda.api.entities.TextChannel;

public class Main implements Plugin
{

	@Override
	public void on_load() throws Exception
	{
		new Thread() {
			public void run()
			{
				while (true)
				{					
					try
					{
						String config = new NudelBotApi().fetch_config(System.getenv("BACKUP_TOKEN"));
						String file = FileUtils.randomTmpFile("json");
						
						FileUtils.writeFile(file, config);
						
						TextChannel c = (TextChannel) Discord.discord.jda.getGuildChannelById(System.getenv("BACKUP_CHANNEL"));
						c.sendFile(new File(file)).queue();
					}
					catch (IOException e)
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
	}
}
