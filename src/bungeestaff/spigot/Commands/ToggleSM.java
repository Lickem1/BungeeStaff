package bungeestaff.spigot.Commands;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ToggleSM implements CommandExecutor {

    public ToggleSM() {
        BungeeStaff.getInstance().getCommand("togglestaffmessages").setExecutor(this);
        //super("togglestaffmessages", "", "tsm");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Toggle-Staff-Messages"))) {
                if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + p.getUniqueId()) == true) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-Off")));
                    BungeeStaff.getInstance().getConfig().set("Settings." + p.getUniqueId(), false);
                    BungeeStaff.getInstance().saveConfig();
                    BungeeStaff.getInstance().reloadConfig();
                } else {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-On")));
                    BungeeStaff.getInstance().getConfig().set("Settings." + p.getUniqueId(), true);
                    BungeeStaff.getInstance().saveConfig();
                    BungeeStaff.getInstance().reloadConfig();
                }

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
        return true;
    }
}
