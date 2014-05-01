package com.kylebashour.tm;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.ChatColor;

public class CommandT implements CommandExecutor {

	private TeamPlugin plugin;
	
	public CommandT(TeamPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if(args.length <= 0){
			return false;
		}
		
		Scoreboard mainBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();
		
		ArrayList<String> colors = new ArrayList<String>();
		
		colors.add(ChatColor.GREEN.toString());
		colors.add(ChatColor.LIGHT_PURPLE.toString());
		colors.add(ChatColor.BLUE.toString());
		colors.add(ChatColor.GRAY.toString());
		colors.add(ChatColor.DARK_RED.toString());
		colors.add(ChatColor.RED.toString());
		colors.add(ChatColor.GOLD.toString());
		colors.add(ChatColor.AQUA.toString());
		colors.add(ChatColor.DARK_AQUA.toString());
		colors.add(ChatColor.DARK_BLUE.toString());
		colors.add(ChatColor.DARK_GREEN.toString());
		colors.add(ChatColor.DARK_PURPLE.toString());
		colors.add(ChatColor.DARK_GRAY.toString());
		colors.add(ChatColor.YELLOW.toString());
		
		int max = 0;
		
		Set<Team> teamSet = mainBoard.getTeams();
		
		// Loop through the teams to determine the next team name
		for(Team team:teamSet) {
			colors.remove(team.getPrefix());
			Integer tmp = null;
			try {
				tmp = Integer.parseInt(team.getName());
			}
			catch(NumberFormatException e) {
			}
			if(tmp != null){
				if(tmp > max) {
					max = tmp;
				}
			}
		}
		max++;
		
		String teamName = Integer.toString(max);
		Team newTeam = mainBoard.registerNewTeam(teamName);
		if(colors.size() > 0) {
			newTeam.setPrefix(colors.get(0));
		}
		else {
			sender.sendMessage("Out of team colors!");
			return true;
		}
		
		for(String arg:args){
			newTeam.addPlayer(plugin.getServer().getOfflinePlayer(arg));
		}
		
		sender.sendMessage(colors.get(0) + "Team " + teamName + " created");

		return true;
	}

}
