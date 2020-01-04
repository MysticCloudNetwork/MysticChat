package net.mysticcloud.spigot.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class BungeeMessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("MysticPlayerChat")) {
			Bukkit.broadcastMessage(in.readUTF());
		}
		if (subchannel.equals("MysticStaffChat")) {
			String p = in.readUTF();
			for(Player s : Bukkit.getOnlinePlayers()){
				if(s.hasPermission("mysticcloud.chat")) s.sendMessage(p);
				
			}
			Bukkit.getConsoleSender().sendMessage(p);
		}
	}
}
