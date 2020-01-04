package net.mysticcloud.spigot.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.chat.Main;
import net.mysticcloud.spigot.chat.utils.Utils;
import net.mysticcloud.spigot.core.utils.CoreUtils;

public class StaffChatCommand implements CommandExecutor {

	public StaffChatCommand(Main plugin, String cmd) {
		plugin.getCommand(cmd).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("mysticcloud.chat")) {
			if (args.length == 0) {
				if (sender instanceof Player) {
					if (Utils.toggleStaffChat(((Player) sender).getName())) {
						sender.sendMessage(CoreUtils.colorize("&e&lStaffChat &f>&7 StaffChat toggled &aon&f."));
					} else {
						sender.sendMessage(CoreUtils.colorize("&e&lStaffChat &f>&7 StaffChat toggled &coff&f."));
					}
				} else
					sender.sendMessage(CoreUtils.colorize("Can't do that. Try /sc <message>"));
			} else {
				String message = "";
				for (int a = 0; a != args.length; a++) {
					message = message + args[a] + " ";
				}
				if (sender instanceof Player)
					Utils.sendStaffChat(((Player) sender), message);
				else Utils.sendStaffChatConsole(message);

			}

		}
		return false;
	}

}
