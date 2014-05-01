package com.kylebashour.tm;

import org.bukkit.plugin.java.JavaPlugin;

public class TeamPlugin extends JavaPlugin {

	public void onEnable(){
		getCommand("nt").setExecutor(new CommandT(this));
		getCommand("dt").setExecutor(new CommandDT(this));
		getCommand("st").setExecutor(new CommandST(this));
		getCommand("rt").setExecutor(new CommandRT(this));
		getCommand("twl").setExecutor(new CommandTWL(this));
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
	}
	
	private boolean whitelist;
	
	public void setWhitelist(boolean set){
		if(set == true){
			whitelist = true;
		}
		else{
			whitelist = false;
		}
	}
	
	public boolean isWhitelistOn(){
		return whitelist;
	}
	
}
