package bungeestaff.spigot.Events;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    public QuitEvent() {
        Bukkit.getServer().getPluginManager().registerEvents(this, BungeeStaff.getInstance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        BungeeStaff.getInstance().staffchat.remove(p);
        BungeeStaff.getInstance().requestcooldown.remove(p);
        BungeeStaff.getInstance().reportcooldown.remove(p);

        for(Player pp : Bukkit.getServer().getOnlinePlayers()) {
            if(pp.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Staff-Leave"))) {
                if(p.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Staff-Leave"))) {
                    if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + pp.getUniqueId()) == true) {
                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Leave").replaceAll("%player%", p.getName())));
                    }
                }
            }
        }
    }
}
