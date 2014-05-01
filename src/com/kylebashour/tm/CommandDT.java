package com.kylebashour.tm;

import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CommandDT implements CommandExecutor {

	private TeamPlugin plugin;
	
	public CommandDT(TeamPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {

		Scoreboard mainBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Set<Team> teamSet = mainBoard.getTeams();

		for(Team team:teamSet){
			team.unregister();
		}
		
		sender.sendMessage("All teams deleted");
		
		return true;
	}
}
