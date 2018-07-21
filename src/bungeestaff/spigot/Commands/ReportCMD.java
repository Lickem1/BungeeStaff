package bungeestaff.spigot.Commands;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class ReportCMD  {

    /*public ReportCMD() {
        BungeeStaff.getInstance().getCommand("report").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(args.length == 0) {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.No-Argument")) {
                    p.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return true;
            }
            if(args.length == 1) {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.No-Argument")) {
                    p.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return true;
            }
            if(args.length >= 2) {
                StringBuilder s = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    s.append(args[i]).append(" ");
                }
                Player tar = Bukkit.getServer().getPlayer(args[0]);
                if(tar == p) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Sender")));
                    return true;
                }
                if(tar == null) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Not-Found")));
                    return true;
                }
                if(BungeeStaff.getInstance().reportcooldown.contains(p)) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Cooldown-Message"))
                    .replaceAll("%amount%", String.valueOf(BungeeStaff.getInstance().getMessages().getInt("Report-Module.Report-Cooldown"))).replaceAll("%type%", BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Cooldown-Type")));

                } else {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Report-Sent")));
                    BungeeStaff.getInstance().reportcooldown.add(p);

                    for(Player pp : Bukkit.getServer().getOnlinePlayers()) {
                        if(pp.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Report-Notify"))) {
                            if(BungeeStaff.getInstance().getMessages().getBoolean("Report-Module.Report-Clickable") == true) {
                                for(String broad : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.Report-Broadcast")) {

                                    BaseComponent[] converted = TextComponent.fromLegacyText(BungeeStaff.getInstance().translate(broad)
                                            .replaceAll("%reporter_server%", p.getServer().getInfo().getName()).replaceAll("%reporter%", p.getName())
                                    .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getInfo().getName())
                                    .replaceAll("%reason%", s.toString()));


                                    TextComponent message = new TextComponent(converted);
                                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Hover-Message")
                                            .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getInfo().getName()))).create()));

                                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, BungeeStaff.getInstance().getMessages().getString("Report-Module.JSONClick-Command").replaceAll("%reported%", tar.getName())));
                                    if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + pp.getUniqueId()) == true) {
                                        pp.sendMessage(message);
                                    }
                                }
                            } else {
                                for(String broad : BungeeStaff.getInstance().getMessages().getStringList("Report-Module.Report-Broadcast")) {
                                    if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + pp.getUniqueId()) == true) {
                                        pp.sendMessage(BungeeStaff.getInstance().translate(broad).replaceAll("%reporter_server%", p.getServer().getInfo().getName())
                                                .replaceAll("%reporter%", p.getName()).replaceAll("%reported%", tar.getName())
                                                .replaceAll("%reported_server%", tar.getServer().getInfo().getName()).replaceAll("%reason%", s.toString()));
                                    }
                                }
                            }
                        }
                    }
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(BungeeStaff.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            BungeeStaff.getInstance().reportcooldown.remove(p);
                        }
                    }, BungeeStaff.getInstance().getMessages().getInt("Report-Module.Report-Cooldown") * 20L);
                }
            }
        }
    }*/
}
