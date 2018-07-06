package bungeestaff.Events;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
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

        for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
            if(p.hasPermission("bstaff.staff.join")) {
                if(pp.hasPermission("bstaff.staff.join")) {
                    if(BungeeStaff.getInstance().getBungeeStaff().get("Settings." + p.getUniqueId()) == null) {
                        try {
                            File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                            BungeeStaff.getInstance().getBungeeStaff().set("Settings." + p.getUniqueId(), true);
                            BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                        pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Join").replaceAll("%player%", p.getName())));
                    }
                }
            }
        }
    }
}
