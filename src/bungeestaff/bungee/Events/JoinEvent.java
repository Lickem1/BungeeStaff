package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;

public class JoinEvent implements Listener {

    public JoinEvent() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();

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
                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName())));
                    }
                }
            }
        }
        net.md_5.bungee.config.Configuration s = BungeeStaff.getInstance().getBungeeStaff().getSection("Ranks");

        for (String key : s.getKeys()) {
            s.get(key);

            for (String oof : s.getSection(key).getStringList("users")) {
                for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                    if (oof.contains(pp.getUniqueId().toString())) {
                        BungeeStaff.getInstance().staffonline.add(p);
                    }
                }
            }
        }
    }
}
