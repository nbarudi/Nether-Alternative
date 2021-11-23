package ca.bungo.nalternative.util.ChanceData;

import org.bukkit.configuration.ConfigurationSection;

import ca.bungo.nalternative.main.NetherAlternative;

public class EntityChance {
	
	public int percChance = 0;
	public int minAmount = 1;
	public int maxAmount = minAmount;
	
	public EntityChance(NetherAlternative na, String entity) {
		ConfigurationSection sec = na.getConfig().getConfigurationSection("resources.entities." + entity);
		percChance = sec.getInt("perc-chance");
		minAmount = sec.getInt("min-spawn");
		maxAmount = sec.getInt("max-spawn");
		
		if(minAmount == 0)
			minAmount = 1;
		if(maxAmount == 0)
			maxAmount = minAmount;
	}

}
