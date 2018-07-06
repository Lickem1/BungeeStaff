package bungeestaff.Commands;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class RequestCMD extends Command {
    public RequestCMD() {
        super("request", "", "helpop");
    }

    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length == 0) {
                for (String s : BungeeStaff.getInstance().getMessages().getStringList("Request-Module.No-Argument")) {
                    p.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return;
            }
            if(args.length >= 1) {
                StringBuilder s = new StringBuilder();
                for(int i = 0; i < args.length; i++) {
                    s.append(args[i]).append(" ");
                }
                if(BungeeStaff.getInstance().requestcooldown.contains(p)) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Request-Module.Request-Cooldown-Message"))
                            .replaceAll("%amount%", String.valueOf(BungeeStaff.getInstance().getMessages().getInt("Request-Module.Request-Cooldown"))).replaceAll("%type%", BungeeStaff.getInstance().getMessages().getString("Request-Module.Request-Cooldown-Type")));
                } else {
                    BungeeStaff.getInstance().requestcooldown.add(p);
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Request-Module.Request-Sent")));

                    for(ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                        if(pp.hasPermission("bstaff.request.notify")) {
                            for(String string : BungeeStaff.getInstance().getMessages().getStringList("Request-Module.Request-Broadcast")) {
                                if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                                    pp.sendMessage(BungeeStaff.getInstance().translate(string).replaceAll("%player_server%", p.getServer().getInfo().getName())
                                            .replaceAll("%player%", p.getName()).replaceAll("%reason%", s.toString()));
                                }
                            }
                        }
                    }
                    ProxyServer.getInstance().getScheduler().schedule(BungeeStaff.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            BungeeStaff.getInstance().requestcooldown.remove(p);
                        }
                    }, BungeeStaff.getInstance().getMessages().getInt("Request-Module.Request-Cooldown"), TimeUnit.valueOf(BungeeStaff.getInstance().getMessages().getString("Request-Module.Request-Cooldown-Type").toUpperCase()));
                }
            }
        }
    }
}
