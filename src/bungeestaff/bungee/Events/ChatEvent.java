package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
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

        if (BungeeStaff.getInstance().getStaffchat().contains(p)) {
            if (e.getMessage().startsWith("/")) {
                e.setCancelled(false);
            } else {
                String rank = Data.onlinestaff.get(p.getName());

                ProxyServer.getInstance().getConsole().sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                        .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage())
                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                e.setCancelled(true);
                for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                    if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.StaffChat-Notify-Command"))) {
                        if (Data.prefix.containsKey(p.getName())) {
                            net.md_5.bungee.config.Configuration s = BungeeStaff.getInstance().getBungeeStaff().getSection("Ranks");

                            for (String key : s.getKeys()) {
                                s.get(key);

                                for (String oof : s.getSection(key).getStringList("users")) {
                                    if (oof.contains(p.getUniqueId().toString())) {
                                        if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                            pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                                    .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage())
                                                    .replaceAll("%prefix%", BungeeStaff.getInstance().translate(s.getSection(key).getString("prefix"))));
                                        }
                                    }
                                }
                            }
                        } else {
                            if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                        .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage())
                                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                            }
                        }
                    }
                }
            }
        }
    }
}
