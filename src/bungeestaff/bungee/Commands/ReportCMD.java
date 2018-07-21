package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class ReportCMD extends Command {

    public ReportCMD() {
        super("report", "");
    }

    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if (args.length == 0) {
                for (String s : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.No-Argument")) {
                    p.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return;
            }
            if (args.length == 1) {
                for (String s : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.No-Argument")) {
                    p.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return;
            }
            if (args.length >= 2) {
                StringBuilder s = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    s.append(args[i]).append(" ");
                }
                ProxiedPlayer tar = ProxyServer.getInstance().getPlayer(args[0]);
                if (tar == p) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Sender")));
                    return;
                }
                if (tar == null) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Not-Found")));
                    return;
                }
                if (BungeeStaff.getInstance().reportcooldown.contains(p)) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Cooldown-Message"))
                            .replaceAll("%amount%", String.valueOf(BungeeStaff.getInstance().getMessages().getInt("Report-Module.Report-Cooldown"))).replaceAll("%type%", BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Cooldown-Type")));
                    return;
                }
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Sent")));
                BungeeStaff.getInstance().reportcooldown.add(p);

                for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                    if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Report-Notify"))) {
                        if (BungeeStaff.getInstance().getMessages().getBoolean("Report-Module.Report-Clickable") == true) {
                            for (String broad : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.Report-Broadcast")) {

                                BaseComponent[] converted = TextComponent.fromLegacyText(BungeeStaff.getInstance().translate(broad)
                                        .replaceAll("%reporter_server%", p.getServer().getInfo().getName()).replaceAll("%reporter%", p.getName())
                                        .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getInfo().getName())
                                        .replaceAll("%reason%", s.toString()));


                                TextComponent message = new TextComponent(converted);
                                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Hover-Message")
                                        .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getInfo().getName()))).create()));

                                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, BungeeStaff.getInstance().getMessages().getString("Report-Module.JSONClick-Command").replaceAll("%reported%", tar.getName())));
                                if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                    pp.sendMessage(message);
                                }
                            }
                        } else {
                            for (String broad : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.Report-Broadcast")) {
                                if (BungeeStaff.getInstance().getSettings().getBoolean("Settings." + pp.getUniqueId() + ".Staff-Messages") == true) {
                                    pp.sendMessage(BungeeStaff.getInstance().translate(broad).replaceAll("%reporter_server%", p.getServer().getInfo().getName())
                                            .replaceAll("%reporter%", p.getName()).replaceAll("%reported%", tar.getName())
                                            .replaceAll("%reported_server%", tar.getServer().getInfo().getName()).replaceAll("%reason%", s.toString()));
                                }
                            }
                        }
                    }
                    ProxyServer.getInstance().getScheduler().schedule(BungeeStaff.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            BungeeStaff.getInstance().reportcooldown.remove(p);
                        }
                    }, BungeeStaff.getInstance().getMessages().getInt("Report-Module.Report-Cooldown"), TimeUnit.valueOf(BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Cooldown-Type").toUpperCase()));
                }
            }
        }
    }
}
