package io.github.glowman554.nudel.autobackup;

import java.io.IOException;

import io.github.glowman554.nudel.api.BaseApi;

public class NudelBotApi extends BaseApi
{
	public String fetch_config(String token) throws IOException
	{
		return request("https://nudel-production.up.railway.app/config.json?token=" + token);
	}
}
