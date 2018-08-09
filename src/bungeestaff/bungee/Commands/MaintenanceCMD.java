package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import sun.applet.Main;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.List;

public class MaintenanceCMD extends Command {

    public MaintenanceCMD() {
        super("maintenance", "");
    }

    public void execute(CommandSender sender, String[] args) {

        if (sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Command"))) {
            if (args.length == 0) {
                for (String string : BungeeStaff.getInstance().getMessages().getStringList("Maintenance-Module.No-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(string));
                }
                return;
            }
            if (args[0].equalsIgnoreCase("help")) {
                for (String help : BungeeStaff.getInstance().getMessages().getStringList("Maintenance-Module.Help")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(help));
                }
            } else if (args[0].equalsIgnoreCase("on")) {
                if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Already-Enabled")));
                } else {
                    if (BungeeStaff.getInstance().getMessages().getBoolean("Maintenance-Module.M-On.Broadcast-Staff") == true) {
                        for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                            if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Notify"))) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.M-On.Message")).replaceAll("%player%", sender.getName()));
                            }
                        }
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.M-On.Message")).replaceAll("%player%", sender.getName()));
                    }
                    for (ProxiedPlayer pp : BungeeCord.getInstance().getPlayers()) {
                        if (!pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Bypass"))) {
                            pp.disconnect(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Online-Kick")).replaceAll("%NEWLINE%", "\n"));
                        }
                    }
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                        BungeeStaff.getInstance().getBungeeStaff().set("Maintenance.Enabled", true);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (args[0].equalsIgnoreCase("off")) {
                if (BungeeStaff.getInstance().getBungeeStaff().getBoolean("Maintenance.Enabled") == true) {
                    if (BungeeStaff.getInstance().getMessages().getBoolean("Maintenance-Module.M-Off.Broadcast-Staff") == true) {
                        for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                            if (pp.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Maintenance-Notify"))) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.M-Off.Message")).replaceAll("%player%", sender.getName()));
                            } else {
                                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.M-Off.Message")).replaceAll("%player%", sender.getName()));
                            }
                        }
                    }
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                        BungeeStaff.getInstance().getBungeeStaff().set("Maintenance.Enabled", false);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Already-Disabled")));
                }
            } else if (args[0].equalsIgnoreCase("list")) {
                for (String message : BungeeStaff.getInstance().getMessages().getStringList("Maintenance-Module.List")) {
                    List<String> lp = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                    sender.sendMessage(BungeeStaff.getInstance().translate(message.replaceAll("%players%", String.valueOf(lp).replaceAll("\\[", "").replaceAll("]", ""))));
                }
            } else if (args[0].equalsIgnoreCase("add")) {
                if (args.length == 1) {
                    for (String string : BungeeStaff.getInstance().getMessages().getStringList("Maintenance-Module.No-Argument")) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(string));
                    }
                    return;
                }
                String username = args[1].toLowerCase();
                List<String> whitelisted = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                try {
                    if (whitelisted.contains(username)) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Player-Already-Whitelisted").replaceAll("%arg%", username)));
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Player-Add-Whitelist").replaceAll("%arg%", username)));
                        whitelisted.add(username);
                        BungeeStaff.getInstance().getBungeeStaff().set("Maintenance.Whitelisted-Players", whitelisted);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    for (String string : BungeeStaff.getInstance().getMessages().getStringList("Maintenance-Module.No-Argument")) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(string));
                    }
                    return;
                }
                String username = args[1].toLowerCase();
                List<String> whitelisted = BungeeStaff.getInstance().getBungeeStaff().getStringList("Maintenance.Whitelisted-Players");
                File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                try {
                    if (whitelisted.contains(username)) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Player-Remove-Whitelist").replaceAll("%arg%", username)));
                        whitelisted.remove(username);
                        BungeeStaff.getInstance().getBungeeStaff().set("Maintenance.Whitelisted-Players", whitelisted);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Maintenance-Module.Player-Not-Whitelisted").replaceAll("%arg%", username)));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
        }
    }
}
