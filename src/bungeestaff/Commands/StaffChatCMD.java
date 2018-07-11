package bungeestaff.Commands;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCMD extends Command {

    public StaffChatCMD() {
        super("staffchat", "", "sc");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.StaffChat-Command"))) {

                if(args.length == 0) {
                    if(BungeeStaff.getInstance().getStaffchat().contains(p)) {
                        BungeeStaff.getInstance().staffchat.remove(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Disabled")));
                    } else {
                        BungeeStaff.getInstance().staffchat.add(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Enabled")));
                    }
                }
                if(args.length >= 1) {
                    StringBuilder s = new StringBuilder();
                    for(int i = 0; i < args.length; i++) {
                        s.append(args[i]).append(" ");
                    }
                    for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                        if(pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Request-Notify"))) {
                            if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message")).replaceAll("%player%", p.getName())
                                        .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%message%", s.toString()));
                            }
                        }
                    }
                }

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
