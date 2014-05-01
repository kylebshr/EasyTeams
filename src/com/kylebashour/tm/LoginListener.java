package com.kylebashour.tm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scoreboard.Scoreboard;

public class LoginListener implements Listener {

	private TeamPlugin plugin;
	
	public LoginListener(TeamPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent logEvent) {
		
		Scoreboard mainBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();
		
		// Check that wl is on, and then player is on a team or has admin, if not, kick them
		if(plugin.isWhitelistOn()) {
			if(mainBoard.getPlayerTeam(logEvent.getPlayer()) == null) {
				if(!logEvent.getPlayer().hasPermission("tm.admin")){
					logEvent.disallow(Result.KICK_OTHER, "You're not on a team for this game!");
				}
			}
		}
	}
}
