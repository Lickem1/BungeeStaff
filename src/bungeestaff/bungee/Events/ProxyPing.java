package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPing implements Listener {

    public ProxyPing() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Use-Maintenance") == true) {
            if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                ServerPing serverPing = e.getResponse();
                ServerPing.Protocol protocol = serverPing.getVersion();
                protocol.setProtocol(2);
                protocol.setName(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.ServerList")));
            }
        }
    }
}
