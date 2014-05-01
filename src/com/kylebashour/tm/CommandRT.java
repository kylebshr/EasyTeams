package com.kylebashour.tm;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CommandRT implements CommandExecutor {

	private TeamPlugin plugin;
	
	public CommandRT(TeamPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if(args.length < 1){
			return false;
		}
		
		int teamSize = Integer.parseInt(args[0]);
		
		Player[] players = plugin.getServer().getOnlinePlayers();
		ArrayList<Player> playerArray = new ArrayList<Player>();
		
		for(Player addPlayer:players){
			playerArray.add(addPlayer);
		}
		
			
		int numTeams = playerArray.size() / teamSize;
		
		if(numTeams * teamSize != playerArray.size()){
			sender.sendMessage(ChatColor.RED + "There is not an evenly divisible number of people online");
			return true;
		}
		
		Scoreboard mainBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Set<Team> teamSet = mainBoard.getTeams();

		for(Team team:teamSet){
			team.unregister();
		}
		
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
		
		int name = 0;
		for(int i = 0; i < numTeams; i++){
			Team newTeam = mainBoard.registerNewTeam(Integer.toString(name));
			if(colors.size() > 0) {
				newTeam.setPrefix(colors.get(0));
				colors.remove(0);
			}
			name++;
			for(int j = teamSize - 1; j >= 0; j--){
				newTeam.addPlayer(playerArray.get(j));
				playerArray.remove(j);
			}
		}
		
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "Created " + numTeams + " teams");
		return true;
	}

}
