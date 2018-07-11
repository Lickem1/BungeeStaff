package bungeestaff.Commands;

import bungeestaff.BungeeStaff;
import bungeestaff.Data;
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
            for(String key : Data.getRanks().keySet()) {
                ProxiedPlayer p = ProxyServer.getInstance().getPlayer(key);

                if(p != null) {
                    ((ProxiedPlayer) sender).sendMessage(BungeeStaff.getInstance().translate(Data.getOnline()
                            .replaceAll("%player%", key)).replaceAll("%server%", p.getServer().getInfo().getName())
                    .replaceAll("%%", ""));
                }
            }
        }
    }
}
