package bungeestaff.Events;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

    public ChatEvent() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        if(BungeeStaff.getInstance().getStaffchat().contains(p)) {
            if(e.getMessage().startsWith("/")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
                for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                    if(pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.StaffChat-Notify-Command"))) {
                        if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                            pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                    .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage()));
                        }
                    }
                }
            }
        }
    }
}
