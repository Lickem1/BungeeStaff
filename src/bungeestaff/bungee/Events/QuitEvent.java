package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class QuitEvent implements Listener {

    public QuitEvent() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        BungeeStaff.getInstance().staffchat.remove(p);
        BungeeStaff.getInstance().requestcooldown.remove(p);
        BungeeStaff.getInstance().reportcooldown.remove(p);
        BungeeStaff.getInstance().staffonline.remove(p);
        Data.prefix.remove(p.getName());
        Data.onlinestaff.remove(p.getName());

        for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            if(pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Leave"))) {
                if(p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Leave"))) {
                    if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                        if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Use-Maintenance") == true) {
                            if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                                List<String> users = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                                if(users.contains(p.getName().toLowerCase())) {
                                    pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Leave").replaceAll("%server_from%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName())));
                                }
                            } else {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Leave").replaceAll("%server_from%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName())));
                            }
                        } else {
                            pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Leave").replaceAll("%server_from%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName())));
                        }
                    }
                }
            }
        }
    }
}
