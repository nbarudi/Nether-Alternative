package ca.bungo.nalternative.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ca.bungo.nalternative.main.NetherAlternative;
import net.md_5.bungee.api.ChatColor;

public class DataController {
	
	public HashMap<String, MapData> whoSpawned;
	
	NetherAlternative na;
	
	public DataController(NetherAlternative na) {
		this.na = na;
		whoSpawned = new HashMap<>();
	}
	
	public void createNetherZone(Player player, Location loc) {
		
		if(whoSpawned.containsKey(player.getName())) 
			if(whoSpawned.get(player.getName()).timeRemain <= 0)
				whoSpawned.remove(player.getName());
		else
			return;
		
		loc.getWorld().strikeLightning(loc);
		
		player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 2, 0.1f);
		
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4A Rift to the HellWorld has been opened!"));
		
		MapData data = new MapData(na, loc);
		whoSpawned.put(player.getName(), data);
	}

}
