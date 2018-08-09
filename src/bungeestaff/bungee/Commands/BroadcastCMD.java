package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BroadcastCMD extends Command {

    public BroadcastCMD() {
        super("broadcast", "", "announce");
    }

    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Broadcast-Command"))) {
                if (args.length == 0) {
                    for (String noarg : BungeeStaff.getInstance().getMessages().getStringList("Broadcast-Module.No-Argument")) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(noarg));
                    }
                    return;
                }
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    s.append(args[i]).append(" ");
                }
                for (String message : BungeeStaff.getInstance().getMessages().getStringList("Broadcast-Module.Message")) {
                    for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                        if (Data.prefix.containsKey(p.getName())) {
                            String rank = Data.onlinestaff.get(p.getName());
                            pp.sendMessage(BungeeStaff.getInstance().translate(message.replaceAll("%player%", sender.getName())
                                    .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%message%", s.toString()))
                                    .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                        } else {
                            pp.sendMessage(BungeeStaff.getInstance().translate(message.replaceAll("%player%", sender.getName())
                                    .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%message%", s.toString()))
                                    .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                        }
                    }
                }
            } else {
                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
