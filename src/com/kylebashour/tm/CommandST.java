package com.kylebashour.tm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CommandST implements CommandExecutor {

	private TeamPlugin plugin;
	
	public CommandST(TeamPlugin plugin) {
		this.plugin = plugin;
	}
	
	private int radius = 0;
	
	private ArrayList<Location> locations = new ArrayList<Location>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if(args.length <= 2){
			return false;
		}
		
		sender.sendMessage(ChatColor.YELLOW + "Attempting to scatter teams");
		radius = Integer.parseInt(args[1]);
		Scoreboard mainBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();
		Set<Team> teams = mainBoard.getTeams();

		if(teams.size() <= 0){
			sender.sendMessage(ChatColor.RED + "There are no teams to scatter!");
			return true;
		}
		
		while(locations.size() < teams.size()){
			Location newLoc = getSafeLocation(args[0], Integer.parseInt(args[2]));
			if(newLoc != null) {
				locations.add(newLoc);
			}
			else{
				sender.sendMessage(ChatColor.RED + "Failed to find a location after 5000 tries");
				return true;
			}
		}
		
		int teleNumber = teams.size();
		for(Team currentTeam:teams){
			Set<OfflinePlayer> players = currentTeam.getPlayers();
			for(OfflinePlayer currentPlayer:players){
				scatterPlayer(currentPlayer.getPlayer(), locations.get(locations.size() - 1));
			}
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Team " + currentTeam.getName() 
					+ " teleported to " + ChatColor.WHITE + "[" + ChatColor.RED 
					+ teleNumber + ChatColor.WHITE + "]");
			teleNumber--;
			locations.remove(locations.size() - 1);
		}
		
		sender.sendMessage(ChatColor.GREEN + "All teams have been scattered");
		return true;
	}
	
	private Location getSafeLocation(String world, int minDistance){
		
		for(int i = 0; i < 5000; i++){
			boolean safe = true;
			boolean goodDist = true;
			
			Location safeLocation = new Location(plugin.getServer().getWorld(world), 0.0, 0.0, 0.0);
			Random random = new Random();

			double randomAngle = random.nextDouble() * Math.PI * 2.0;
			double randomRadius = radius * random.nextDouble();
			int[] xz = convertRadiansToBlocks(randomAngle, randomRadius);
			
			safeLocation.setX(xz[0] + 0.5);
			safeLocation.setZ(xz[1] + 0.5);
			
		    if (plugin.getServer().getWorld(world).getChunkAt(safeLocation).isLoaded()) {
		    	plugin.getServer().getWorld(world).getChunkAt(safeLocation).load(true);
		    }
		    
			safeLocation.setY(getSafeY(safeLocation) - 1);
			
			if(safeLocation.getBlock().getType().isSolid() 
					&& safeLocation.getBlock().getType() != Material.CACTUS){
				safeLocation.setY(getSafeY(safeLocation) + 1);
				safe = true;
			}
			else{
				safe = false;
			}
			
			for(Location check:locations){
				if(check.distance(safeLocation) < minDistance){
					goodDist = false;
				}
				else{
					goodDist = true;
				}
			}
			
			if(safe == true && goodDist == true){
				return safeLocation;
			}
		}
		return null;
	}
	
	private int[] convertRadiansToBlocks(double angle, double radius){
	    return 
	    	new int[] { 
	    		(int)Math.round(radius * Math.cos(angle)), (int)Math.round(radius * Math.sin(angle)) 
	    	};
	}
	
	private int getSafeY(Location loc) {
		return loc.getWorld().getHighestBlockYAt(loc);
	}
	
	private void scatterPlayer(Player player, Location loc){
		player.teleport(loc);
	}
}
