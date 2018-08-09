package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
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
            if(sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.StaffChat-Command"))) {
                ProxiedPlayer p = (ProxiedPlayer) sender;

                if(args.length == 0) {
                    if(BungeeStaff.getInstance().getStaffchat().contains(p)) {
                        BungeeStaff.getInstance().staffchat.remove(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Disabled")));
                    } else {
                        BungeeStaff.getInstance().staffchat.add(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Enabled")));
                    }
                    return;
                }
                if(args.length >= 1) {
                    StringBuilder ss = new StringBuilder();
                    for(int i = 0; i < args.length; i++) {
                        ss.append(args[i]).append(" ");
                    }
                    for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                        if(pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Request-Notify"))) {
                            if(Data.prefix.containsKey(p.getName())) {
                                net.md_5.bungee.config.Configuration s = BungeeStaff.getInstance().getBungeeStaff().getSection("Ranks");

                                for(String key : s.getKeys()) {
                                    s.get(key);

                                    for (String oof : s.getSection(key).getStringList("users")) {
                                        if (oof.contains(p.getUniqueId().toString())) {
                                            if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                                        .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", ss.toString())
                                                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(s.getSection(key).getString("prefix"))));
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                    pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                                            .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", ss.toString())
                                            .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("No-Rank"))));
                                }
                            }
                        }
                    }
                    String rank = Data.onlinestaff.get(p.getName());

                    ProxyServer.getInstance().getConsole().sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message"))
                            .replaceAll("%player_server%", p.getServer().getInfo().getName()).replaceAll("%player%", p.getName()).replaceAll("%message%", ss.toString())
                            .replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix"))));
                    return;
                }

            } else {
                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
