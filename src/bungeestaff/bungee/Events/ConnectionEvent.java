package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ConnectionEvent implements Listener {

    public ConnectionEvent() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onConnect(ServerConnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Use-Maintenance") == true) {
            if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                if(!p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Bypass"))) {
                    List<String> users = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                    if(!users.contains(p.getName().toLowerCase())) {
                        e.setCancelled(true);
                        String s = BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Join-Message");
                        p.disconnect(BungeeStaff.getInstance().translate(s.replaceAll("%server%", e.getTarget().getName())
                                .replaceAll("%NEWLINE%", "\n")));
                    }
                }
            }
        }
    }
}
