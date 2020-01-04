package net.mysticcloud.spigot.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.mysticcloud.spigot.chat.commands.StaffChatCommand;
import net.mysticcloud.spigot.chat.listeners.MessageListener;
import net.mysticcloud.spigot.chat.listeners.PlayerListener;
import net.mysticcloud.spigot.chat.utils.Utils;


public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	
	
	int attempt = 1;
	int maxattempts = 3;

	public void onEnable() {
		if(!getServer().getPluginManager().isPluginEnabled("MysticCore")){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Can't find MysticCore. Trying again.. Attempt " + attempt + " out of " + maxattempts));
			attempt+=1;
			if(attempt==maxattempts+1){
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Couldn't find MysticCore after " + maxattempts + " tries. Plugin will not load."));
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
			Bukkit.getScheduler().runTaskLater(this, new Runnable(){

				@Override
				public void run() {
					onEnable();
				}
				
			}, 20*3);
			return;
		} else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + getDescription().getName() + " &f>&7 Found MysticCore! Loading plugin.."));
		}
		plugin = this;
		new PlayerListener(this);
		new StaffChatCommand(this, "staffchat");
		
		getServer().getMessenger().registerIncomingPluginChannel(net.mysticcloud.spigot.core.Main.getPlugin(), "mystic:mystic", new MessageListener());
		getServer().getMessenger().registerOutgoingPluginChannel(net.mysticcloud.spigot.core.Main.getPlugin(), "mystic:mystic");
			
		
		Utils.start();
		
	}
	
	public void onDisable() {
		if(attempt >= maxattempts) return;
		getServer().getMessenger().unregisterIncomingPluginChannel(net.mysticcloud.spigot.core.Main.getPlugin(), "mystic:mystic");
		getServer().getMessenger().unregisterOutgoingPluginChannel(net.mysticcloud.spigot.core.Main.getPlugin(), "mystic:mystic");
		
	}
	
	public static Main getPlugin(){
		return plugin;
	}
}