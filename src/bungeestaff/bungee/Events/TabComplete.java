package bungeestaff.bungee.Events;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabComplete implements Listener {

    public TabComplete() {
        ProxyServer.getInstance().getPluginManager().registerListener(BungeeStaff.getInstance(), this);
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        String partialName;
        if (!event.isCancelled()) {
            partialName = event.getCursor().toLowerCase().lastIndexOf(32) >= 0 ? event.getCursor().toLowerCase().substring(event.getCursor().toLowerCase().lastIndexOf(32) + 1) : event.getCursor().toLowerCase();
            for (ProxiedPlayer player : BungeeStaff.getInstance().getProxy().getPlayers())
                if ((player.getName().toLowerCase().startsWith(partialName)) && (!event.getSuggestions().contains(player.getName())))
                    event.getSuggestions().add(player.getName());
        }
    }
}
