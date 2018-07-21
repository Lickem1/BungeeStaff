package bungeestaff.spigot.Events;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class JoinEvent implements Listener {

    public JoinEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, BungeeStaff.getInstance());
        }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        for(Player pp : Bukkit.getServer().getOnlinePlayers()) {
            if(pp.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Staff-Join"))) {
                if(p.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Staff-Join"))) {
                    if(BungeeStaff.getInstance().getConfig().get("Settings." + p.getUniqueId()) == null) {
                        BungeeStaff.getInstance().getConfig().set("Settings." + p.getUniqueId(), true);
                        BungeeStaff.getInstance().saveConfig();
                        BungeeStaff.getInstance().reloadConfig();
                    }
                    if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + pp.getUniqueId()) == true) {
                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName())));
                    }
                }
            }
        }
    }
}
