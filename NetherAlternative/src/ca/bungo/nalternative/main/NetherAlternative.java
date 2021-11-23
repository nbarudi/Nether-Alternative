package ca.bungo.nalternative.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ca.bungo.nalternative.cmds.GetListCommand;
import ca.bungo.nalternative.events.InteractEvent;
import ca.bungo.nalternative.util.DataController;
import net.md_5.bungee.api.ChatColor;

public class NetherAlternative extends JavaPlugin {
	
	public DataController dc;

	
	@Override
	public void onEnable() {
		registerEvents();
		registerCommands();
		registerConfig();
		
		this.dc = new DataController(this);
	}

	private void registerConfig() {
		// TODO Auto-generated method stub
		saveDefaultConfig();
		
	}

	private void registerCommands() {
		// TODO Auto-generated method stub
		this.getCommand("getlist").setExecutor(new GetListCommand(this));
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new InteractEvent(this), this);
		
	}
	
	public void logConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&cNether Alternative&7] &e" + message));
	}
	
}
