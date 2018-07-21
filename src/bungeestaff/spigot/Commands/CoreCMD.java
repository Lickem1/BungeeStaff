package bungeestaff.spigot.Commands;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CoreCMD implements CommandExecutor {

    public CoreCMD() {
        bungeestaff.spigot.BungeeStaff.getInstance().getCommand("bungeestaff").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Core-Command"))) {
            if(args.length == 0) {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.No-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("help")) {
                for(String h : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.Help")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(h));
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("reload")) {
                BungeeStaff.getInstance().loadYamls();
                BungeeStaff.getInstance().reloadConfig();
                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("BungeeStaff-Module.Reload")));
            } else {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.No-Such-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(s));
                }
            }
        } else {
            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
        }
        return true;
    }
}
