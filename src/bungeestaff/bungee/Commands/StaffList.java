package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffList extends Command {

    public StaffList() {
        super("stafflist", "", "slist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Staff-List"))) {
                net.md_5.bungee.config.Configuration s = BungeeStaff.getInstance().getBungeeStaff().getSection("Ranks");

                for(String key : s.getKeys()) {
                    s.get(key);

                    for (String oof : s.getSection(key).getStringList("users")) {
                        for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                            if (oof.contains(pp.getUniqueId().toString())) {
                                if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                    for(String message : BungeeStaff.getInstance().getMessages().getStringList("Staff-List.Message")) {

                                        p.sendMessage(BungeeStaff.getInstance().staffonline.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
