package ca.bungo.nalternative.util.ChanceData;

import org.bukkit.configuration.ConfigurationSection;

import ca.bungo.nalternative.main.NetherAlternative;

public class ItemChance {
	
	public int percChance = 0;
	public int minAmount = 1;
	public int maxAmount = minAmount;
	
	public ItemChance(NetherAlternative na, String material) {
		ConfigurationSection sec = na.getConfig().getConfigurationSection("resources.items." + material);
		percChance = sec.getInt("perc-chance");
		minAmount = sec.getInt("min-items");
		maxAmount = sec.getInt("max-items");
		
		if(minAmount == 0)
			minAmount = 1;
		if(maxAmount == 0)
			maxAmount = minAmount;
	}

}
