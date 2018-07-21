package bungeestaff.spigot.Events;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    public ChatEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, BungeeStaff.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if(BungeeStaff.getInstance().getStaffchat().contains(p)) {
            if(e.getMessage().startsWith("/")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
                for(Player pp : Bukkit.getServer().getOnlinePlayers()) {
                    if(pp.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.StaffChat-Notify-Command"))) {
                        if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + pp.getUniqueId()) == true) {
                            pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                    .replaceAll("%player_server%", p.getServer().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage()));
                        }
                    }
                }
            }
        }
    }
}
