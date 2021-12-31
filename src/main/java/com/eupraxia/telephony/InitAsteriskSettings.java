package com.eupraxia.telephony;


import org.asteriskjava.pbx.DefaultAsteriskSettings;

public class InitAsteriskSettings extends DefaultAsteriskSettings
{

	@Override
	public String getManagerPassword()
	{
		// this password MUST match the password (secret=) in manager.conf
		return "dev@123";
	}

	@Override
	public String getManagerUsername()
	{
		// this MUST match the section header '[myconnection]' in manager.conf
        return "dev";
	}
	
	@Override
	public int getManagerPortNo()
		{
		// this password MUST match the password (secret=) in manager.conf
		return 5038;
	}

	@Override
	public String getAsteriskIP()
	{
		// The IP address or FQDN of your Asterisk server.
        return "192.168.10.210";
	}

	@Override
	public String getAgiHost()
	{
		// The IP Address of FQDN of you asterisk-java application.
		return "localhost";
	}

}
