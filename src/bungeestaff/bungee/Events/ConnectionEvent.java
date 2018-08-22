package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
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

        if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Use-Maintenance") == true) {
            if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                if (!p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Bypass"))) {
                    List<String> users = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                    if (!users.contains(p.getName().toLowerCase())) {
                        e.setCancelled(true);
                        String s = BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Join-Message");
                        p.disconnect(BungeeStaff.getInstance().translate(s.replaceAll("%server%", e.getTarget().getName())
                                .replaceAll("%NEWLINE%", "\n")));
                    }
                }
            }
        }
        for (ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
            if (p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Server-Switch"))) {
                if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Server-Switch-Notify"))) {
                    if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                        if (Data.onlinestaff.containsKey(p.getName())) {
                            String server_to = e.getTarget().getName();


                            if (Data.prefix.containsKey(p.getName())) {
                                if(e.getTarget().getPlayers().contains(p)) {
                                    return;
                                } else {
                                    if(p.getServer() == null) {
                                        String rank = Data.onlinestaff.get(p.getName());
                                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Server-Switch-Module.First-Join")
                                                .replaceAll("%player%", p.getName()).replaceAll("%server%", server_to)).replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                                    } else {
                                        String rank = Data.onlinestaff.get(p.getName());
                                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Server-Switch-Module.Switch")
                                                .replaceAll("%player%", p.getName()).replaceAll("%server_to%", server_to).replaceAll("%server_from%", p.getServer().getInfo().getName()))
                                                .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                                    }
                                }
                            }
                        } else {
                            if(p.getServer() == null) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Server-Switch-Module.First-Join")
                                        .replaceAll("%player%", p.getName()).replaceAll("%server%", e.getTarget().getName())).replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                            } else {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Server-Switch-Module.Switch")
                                        .replaceAll("%player%", p.getName()).replaceAll("%server_to%", e.getTarget().getName()).replaceAll("%server_from%", p.getServer().getInfo().getName()))
                                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                            }
                        }
                    }
                }
            }
        }
    }
}
