package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JoinEvent implements Listener {

    public JoinEvent() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        ProxyServer.getInstance().getScheduler().schedule(BungeeStaff.getInstance(), new Runnable() {
            @Override
            public void run() {
                net.md_5.bungee.config.Configuration s = BungeeStaff.getInstance().getBungeeStaff().getSection("Ranks");
                for (String key : s.getKeys()) {
                    for (String oof : s.getSection(key).getStringList("users")) {
                        if (oof.contains(p.getUniqueId().toString())) {
                            Data.onlinestaff.put(p.getName(), key);

                            String rank = Data.onlinestaff.get(p.getName());
                            String prefix = BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix");

                            Data.prefix.put(p.getName(), prefix);
                        }
                    }
                }
            }
        }, 1, TimeUnit.MILLISECONDS);

        for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Join"))) {
                if (p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Join"))) {

                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "settings.yml");
                        BungeeStaff.getInstance().getSettings().set("Settings." + p.getUniqueId() + ".Username", p.getName());
                        BungeeStaff.getInstance().settingsPP.save(BungeeStaff.getInstance().settings, file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    if (BungeeStaff.getInstance().getSettings().get("Settings." + p.getUniqueId() + ".Staff-Messages") == null) {
                        try {
                            File file = new File(BungeeStaff.getInstance().getDataFolder(), "settings.yml");
                            BungeeStaff.getInstance().getSettings().set("Settings." + p.getUniqueId() + ".Staff-Messages", true);
                            BungeeStaff.getInstance().settingsPP.save(BungeeStaff.getInstance().settings, file);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                        if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Use-Maintenance") == true) {
                            if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                                List<String> users = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                                if (users.contains(p.getName().toLowerCase())) {
                                    if(Data.prefix.containsKey(p.getName())) {
                                        String rank = Data.onlinestaff.get(p.getName());
                                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                                .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                                    } else {
                                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                                .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                                    }
                                }
                            } else {
                                if(Data.prefix.containsKey(p.getName())) {
                                    String rank = Data.onlinestaff.get(p.getName());
                                    pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                            .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                                } else {
                                    pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                            .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                                }
                            }
                        } else {
                            if(Data.prefix.containsKey(p.getName())) {
                                String rank = Data.onlinestaff.get(p.getName());
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                            } else {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName()))
                                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                            }
                        }
                    }
                }
            }
        }
    }

}
