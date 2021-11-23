package ca.bungo.nalternative.events;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ca.bungo.nalternative.main.NetherAlternative;

public class InteractEvent implements Listener {
	
	NetherAlternative na;
	
	public InteractEvent(NetherAlternative na) {
		this.na = na;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if(event.getItem() == null)
			return;
		
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		
		if(event.getItem().getType().equals(Material.FLINT_AND_STEEL)) {
			Block block = event.getClickedBlock();
			if(!block.getType().name().toLowerCase().contains("wood")) //Must be wooden planks
				return;
			
			

			Location loc = block.getLocation();
			block.breakNaturally();
			World w = loc.getWorld();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			
			//Testing multiblock
			/*
			 * 3x3 gold blocks on the bottom
			 * 4 redstone torches, 1 on each edge
			 * */
			
			ArrayList<Block> bottomBlocks = new ArrayList<Block>() {
				private static final long serialVersionUID = 1L;
				{
				add(new Location(w, x, y-1, z).getBlock());
				add(new Location(w, x-1, y-1, z).getBlock());
				add(new Location(w, x, y-1, z-1).getBlock());
				add(new Location(w, x+1, y-1, z).getBlock());
				add(new Location(w, x, y-1, z+1).getBlock());
				add(new Location(w, x-1, y-1, z-1).getBlock());
				add(new Location(w, x+1, y-1, z+1).getBlock());
				add(new Location(w, x-1, y-1, z+1).getBlock());
				add(new Location(w, x+1, y-1, z-1).getBlock());
				}
			};
			
			boolean pass = true;
			for(Block b : bottomBlocks) {
				if(!b.getType().equals(Material.GOLD_BLOCK)){
					System.out.println("Failed On: " + b.getType().name());
					pass = false;
				}
			}
			
			ArrayList<Block> secondRow = new ArrayList<Block>() {
				private static final long serialVersionUID = 1L;
				{
				add(new Location(w, x+1, y, z).getBlock());
				add(new Location(w, x-1, y, z).getBlock());
				add(new Location(w, x, y, z-1).getBlock());
				add(new Location(w, x, y, z+1).getBlock());
				}
			};
			
			for(Block b : secondRow) {
				if(!b.getType().name().equalsIgnoreCase("redstone_torch_on")) {
					System.out.println("Failed On: " + b.getType().name());
					pass = false;
				}
					
			}
			
			if(!pass)
				return;
			
			for(Block b : bottomBlocks) {
				b.breakNaturally();
			}
			for(Block b : secondRow) {
				b.breakNaturally();
			}
			
			na.dc.createNetherZone(player, loc);
			
			
		}
		
	}

}
