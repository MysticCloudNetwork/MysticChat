package net.mysticcloud.spigot.chat.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.mysticcloud.spigot.chat.Main;
import net.mysticcloud.spigot.core.utils.CoreUtils;

public class Utils {

	private static boolean playerglobal = false;
	private static String playerformat = "%player%: %message%";
	private static boolean staffglobal = true;
	private static String staffformat = "&c[StaffChat]&f%player%: %message%";
	private static boolean chatpermission = false;
	private static List<String> staffchatplayers = new ArrayList<>();

	public static void start() {

		if (Main.getPlugin().getConfig().isSet("PlayerChat.Format"))
			playerformat = Main.getPlugin().getConfig().getString("PlayerChat.Format");
		else {
			Main.getPlugin().getConfig().set("PlayerChat.Format", playerformat);
			Main.getPlugin().saveConfig();
		}

		if (Main.getPlugin().getConfig().isSet("PlayerChat.Global"))
			playerglobal = Main.getPlugin().getConfig().getBoolean("PlayerChat.Global");
		else {
			Main.getPlugin().getConfig().set("PlayerChat.Global", playerglobal);
			Main.getPlugin().saveConfig();
		}

		if (Main.getPlugin().getConfig().isSet("StaffChat.Format"))
			staffformat = Main.getPlugin().getConfig().getString("StaffChat.Format");
		else {
			Main.getPlugin().getConfig().set("StaffChat.Format", staffformat);
			Main.getPlugin().saveConfig();
		}

		if (Main.getPlugin().getConfig().isSet("StaffChat.Global"))
			staffglobal = Main.getPlugin().getConfig().getBoolean("StaffChat.Global");
		else {
			Main.getPlugin().getConfig().set("StaffChat.Global", staffglobal);
			Main.getPlugin().saveConfig();
		}
		
		if (Main.getPlugin().getConfig().isSet("PermissionToChat"))
			chatpermission = Main.getPlugin().getConfig().getBoolean("PermissionToChat");
		else {
			Main.getPlugin().getConfig().set("PermissionToChat", chatpermission);
			Main.getPlugin().saveConfig();
		}

	}

	public static String getPlayerFormat() {
		return playerformat;
	}

	public static boolean isPlayerChatGlobal() {
		return playerglobal;
	}

	public static String getStaffFormat() {
		return staffformat;
	}

	public static boolean isStaffChatGlobal() {
		return staffglobal;
	}

	public static String replaceholders(Player player, String format, String message) {
		format = CoreUtils.colorize(format);
		if (format.contains("%player%"))
			format = format.replace("%player%", player.getName());
		if (format.contains("%displayname%"))
			format = format.replace("%displayname%", player.getDisplayName());
		if (format.contains("%customname%"))
			format = format.replace("%customname%", player.getCustomName());
		if (format.contains("%time%"))
			format = format.replace("%time%", player.getWorld().getFullTime() + "");
		if (format.contains("%realtime%"))
			format = format.replace("%realtime%", CoreUtils.getTime());
		if (format.contains("%playertime%"))
			format = format.replace("%playertime%", player.getPlayerTime() + "");
		if (format.contains("%prefix%"))
			format = format.replace("%prefix%", CoreUtils.colorize(CoreUtils.getPlayerPrefix(player)));
		if (format.contains("%suffix%"))
			format = format.replace("%suffix%", CoreUtils.colorize(CoreUtils.getPlayerSuffix(player)));
		if (format.contains("%message%"))
			if (player.hasPermission("chat.color"))
				format = format.replace("%message%", CoreUtils.colorize(message));
			else
				format = format.replace("%message%", message);
		if (format.contains("%server%"))
			format = format.replace("%server%", Bukkit.getName());
		if (format.contains("%world%"))
			format = format.replace("%world%", player.getWorld().getName());

		return format;
	}
	
	public static String replaceholders(String player, String format, String message) {
		format = CoreUtils.colorize(format);
		if (format.contains("%player%"))
			format = format.replace("%player%", player);
		if (format.contains("%displayname%"))
			format = format.replace("%displayname%", player);
		if (format.contains("%customname%"))
			format = format.replace("%customname%", player);
		if (format.contains("%time%"))
			format = format.replace("%time%", CoreUtils.getTime() + "");
		if (format.contains("%realtime%"))
			format = format.replace("%realtime%", CoreUtils.getTime());
		if (format.contains("%playertime%"))
			format = format.replace("%playertime%", CoreUtils.getTime() + "");
		if (format.contains("%prefix%"))
			format = format.replace("%prefix%", CoreUtils.colorize("&6"));
		if (format.contains("%suffix%"))
			format = format.replace("%suffix%", CoreUtils.colorize("&f"));
		if (format.contains("%message%"))
			format = format.replace("%message%", CoreUtils.colorize(message));
		if (format.contains("%server%"))
			format = format.replace("%server%", Bukkit.getName());
		if (format.contains("%world%"))
			format = format.replace("%world%", "");

		return format;
	}

	public static boolean toggleStaffChat(String name) {
		if (staffchatplayers.contains(name))
			staffchatplayers.remove(name);
		else
			staffchatplayers.add(name);
		return staffchatplayers.contains(name);
	}

	public static void sendStaffChat(Player player, String message) {
		if (staffglobal) {
			CoreUtils.sendPluginMessage(player, "hyverse:hyverse", "HyverseStaffChat",
					Utils.replaceholders(player, Utils.getStaffFormat(), message));
		} else
			Bukkit.broadcast(message, "chat.staffchat");
	}

	public static boolean inStaffChat(String name) {
		return staffchatplayers.contains(name);
	}

	public static void sendStaffChatConsole(String message) {
		if (staffglobal) {
			if(Bukkit.getOnlinePlayers().size() > 0) 
				CoreUtils.sendPluginMessage((Player) Bukkit.getOnlinePlayers().toArray()[0], "mystic:mystic", "MysticStaffChat",
						Utils.replaceholders("CONSOLE", Utils.getStaffFormat(), message));
			else Bukkit.getConsoleSender().sendMessage(CoreUtils.colorize("&eStaffChat &f>&7 There was an error sending chat. There must be at least one player online for the global option to work."));
		} else
			Bukkit.broadcast(message, "chat.staffchat");
	}

}
