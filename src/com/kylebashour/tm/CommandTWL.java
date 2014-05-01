package com.kylebashour.tm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTWL implements CommandExecutor {

	private TeamPlugin plugin;
	
	public CommandTWL(TeamPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {

		if(plugin.isWhitelistOn() == true){
			plugin.setWhitelist(false);
			sender.sendMessage("Team Whitelisting has been turned off");
		}
		else{
			plugin.setWhitelist(true);
			sender.sendMessage("Team Whitelisting has been turned on");
		}
		return true;
	}
}
