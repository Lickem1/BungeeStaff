package bungeestaff.Events;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

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

        for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            if(p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Leave"))) {
                if(pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Leave"))) {
                    if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Leave").replaceAll("%player%", p.getName())));
                    }
                }
            }
        }
    }
}
