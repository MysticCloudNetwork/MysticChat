package net.mysticcloud.spigot.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class MessageListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("mystic:mystic")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("MysticPlayerChat")) {
			Bukkit.broadcastMessage(in.readUTF());
		}
		if (subchannel.equals("MysticStaffChat")) {
			String p = in.readUTF();
			for(Player s : Bukkit.getOnlinePlayers()){
				if(s.hasPermission("mystic.staffchat")) s.sendMessage(p);
				
			}
			Bukkit.getConsoleSender().sendMessage(p);
		}
	}
}
