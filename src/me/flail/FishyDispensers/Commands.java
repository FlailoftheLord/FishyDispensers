package me.flail.FishyDispensers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor {

	private FishyDispensers plugin = FishyDispensers.getPlugin(FishyDispensers.class);

	public ItemStack fishyDispenser() {

		FileConfiguration config = plugin.getConfig();

		String dispenserName = config.getString("DispenserName");
		ItemStack dispenser = new ItemStack(Material.DISPENSER, 1);

		ItemMeta dMeta = dispenser.getItemMeta();
		dMeta.setDisplayName(plugin.chat(dispenserName));
		dispenser.setItemMeta(dMeta);

		return dispenser;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		FileConfiguration config = plugin.getConfig();

		String cmd = command.getName().toLowerCase();

		String noPermission = config.getString("NoPermission");
		String getDispenserMsg = config.getString("GetDispenserMessage");
		String reloadMsg = config.getString("ReloadPluginMessage");
		String version = plugin.getDescription().getVersion();
		String usage = plugin.chat("%prefix% &cProper usage: &7/" + label + " [get:type:reload]");

		if (cmd.equals("fishydispensers")) {

			command.setPermission("fishydispensers.command");
			command.setPermissionMessage(noPermission);
			command.setUsage(usage);

			if (sender instanceof Player) {

				Player player = (Player) sender;


				if (args.length == 0) {
					player.sendMessage(
							plugin.chat("&3Fishy&2Dispensers &7version &8" + version + " &2by FlailoftheLord."));
				} else if (args.length == 1) {

					if (args[0].equalsIgnoreCase("reload")) {

						plugin.reloadConfig();
						player.sendMessage(plugin.chat(reloadMsg));

						return true;
					} else if (args[0].equalsIgnoreCase("get")) {

						player.getInventory().addItem(fishyDispenser());
						player.sendMessage(plugin.chat(getDispenserMsg));

						return true;
					} else if (args[0].equalsIgnoreCase("type")) {

					}

				}




			} else {

				plugin.console.sendMessage(
						plugin.chat("&3Fishy&2Dispensers &7version &8" + version + " &2by FlailoftheLord."));

			}

			sender.sendMessage(usage);
		}

		return true;
	}

}
