package ca.bungo.nalternative.util;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import ca.bungo.nalternative.main.NetherAlternative;
import ca.bungo.nalternative.util.ChanceData.EntityChance;
import ca.bungo.nalternative.util.ChanceData.ItemChance;
import net.md_5.bungee.api.ChatColor;

public class MapData {
	
	public HashMap<Block, Material> blockStorage;
	
	public HashMap<Material, ItemChance> itemSpawns;
	public HashMap<EntityType, EntityChance> entitySpawns;
	
	public Location startLocation;
	public Location expansionLocation;
	
	public int task = 0;
	
	public int timeRemain = 0;
	
	
	NetherAlternative na;
	
	public MapData(NetherAlternative na, Location startLocation) {
		this.na = na;
		blockStorage = new HashMap<>();
		itemSpawns = new HashMap<>();
		entitySpawns = new HashMap<>();
		
		this.startLocation = startLocation;
		
		ConfigurationSection sec = na.getConfig().getConfigurationSection("resources");
		
		for(String key : sec.getConfigurationSection("entities").getKeys(false)) {
			EntityChance chance = new EntityChance(na, key);
			EntityType type = EntityType.valueOf(key.toUpperCase());
			if(type == null) {
				na.logConsole("&4Failed to find entity type: " + key + "! &eTo get a list of EntityTypes do /getlist entity");
				continue;
			}
			entitySpawns.put(type, chance);
		}
		
		for(String key : sec.getConfigurationSection("items").getKeys(false)) {
			ItemChance chance = new ItemChance(na, key);
			Material type = Material.valueOf(key.toUpperCase());
			if(type == null) {
				na.logConsole("&4Failed to find item type: " + key + "! &eTo get a list of ItemTypes do /getlist items");
				continue;
			}
			itemSpawns.put(type, chance);
		}
		
		expansionLocation = new Location(startLocation.getWorld(), startLocation.getBlockX(), startLocation.getBlockY()-2, startLocation.getBlockZ());
		
		
		startTimer();
		
	}
	
	private void startTimer() {
		
		timeRemain = na.getConfig().getInt("max-nether-time");
		if(timeRemain == 0)
			timeRemain = 120;
		
		timeRemain = timeRemain*4; //Loop below runs once every 1/4 of a second
		
		this.task = na.getServer().getScheduler().scheduleSyncRepeatingTask(na, ()->{
			if(timeRemain <= 0) {
				endTimer();
				return;
			}
			
			if(timeRemain % 8 == 0) {
				//Go through every entity
				for(EntityType ent : entitySpawns.keySet()) {
					EntityChance chance = entitySpawns.get(ent);
					Random rnd = new Random();
					int rng = rnd.nextInt(100) + 1;
					if(rng <= chance.percChance) {
						int spawnRNG = rnd.nextInt(chance.maxAmount - chance.minAmount) + chance.minAmount;
						World world = startLocation.getWorld();
						for(int i = 0; i < spawnRNG; i++)
							world.spawnEntity(startLocation, ent);
						break;
					}
				}
				
				//Go through every item
				for(Material mat : itemSpawns.keySet()) {
					ItemChance chance = itemSpawns.get(mat);
					Random rnd = new Random();
					int rng = rnd.nextInt(100) + 1;
					if(rng <= chance.percChance) {
						int spawnRNG = rnd.nextInt(chance.maxAmount - chance.minAmount) + chance.minAmount;
						World world = startLocation.getWorld();
						world.dropItemNaturally(startLocation, new ItemStack(mat, spawnRNG));
					}
				}
			}
			
			timeRemain--;
		}, 5, 5);
		
	}
	
	
	public void endTimer() {
		
		for(Block bl : blockStorage.keySet()) {
			bl.setType(blockStorage.get(bl));
		}
		
		na.getServer().getScheduler().cancelTask(this.task);
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4The Rift to the HellWorld has been closed once again!"));
	}

}
