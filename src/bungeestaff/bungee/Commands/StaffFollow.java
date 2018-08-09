package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffFollow extends Command {
    public StaffFollow() {
        super("bstafffollow", "");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-Follow"))) {
                if(args.length == 0) {
                    for(String s : BungeeStaff.getInstance().getMessages().getStringList("Staff-Follow.No-Argument")) {
                        p.sendMessage(BungeeStaff.getInstance().translate(s));
                    }
                    return;
                }
                ProxiedPlayer tar = ProxyServer.getInstance().getPlayer(args[0]);

                if(tar == null) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Not-Found")));
                    return;
                }
                ServerInfo tserver = tar.getServer().getInfo();
                if(tserver.getPlayers().contains(p)) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Follow.Already-In"))
                    .replaceAll("%player%", tar.getName()));
                } else {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Follow.Joining"))
                            .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getInfo().getName()));

                    p.connect(tserver);
                }

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
