package bungeestaff.Commands;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class CoreCMD extends Command {

    public CoreCMD() {
        super("bungeestaff", "", "bstaff");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Core-Command"))) {
            if(args.length == 0) {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.No-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(s));
                }
                return;
            }
            if(args[0].equalsIgnoreCase("help")) {
                for(String h : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.Help")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(h));
                }
                return;
            }
            else if(args[0].equalsIgnoreCase("reload")) {
                BungeeStaff.getInstance().registerConfig();
                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("BungeeStaff-Module.Reload")));
            } else {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.No-Such-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(s));
                }
            }
        } else {
            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
        }
    }
}
