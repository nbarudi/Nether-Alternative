package ca.bungo.nalternative.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import ca.bungo.nalternative.main.NetherAlternative;

public class GetListCommand implements CommandExecutor {
	
	NetherAlternative na;
	
	public GetListCommand(NetherAlternative na) {
		this.na = na;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments: /getlist <entity/items>");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("entity")) {
			
			EntityType[] entities = EntityType.values();
			for(EntityType ent : entities)
				sender.sendMessage(ChatColor.AQUA + ent.toString());
			return true;
		}else if(args[0].equalsIgnoreCase("items")) {
			Material[] materials = Material.values();
			for(Material mat : materials)
				sender.sendMessage(ChatColor.AQUA + mat.toString());
			return true;
		}else if(args[0].equalsIgnoreCase("testing")) {
			na.dc.createNetherZone((Player) sender, ((Player) sender).getLocation());
			sender.sendMessage(ChatColor.GREEN + "Starting Test!");
		}
		
		return true;
	}

}
