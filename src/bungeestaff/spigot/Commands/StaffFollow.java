package bungeestaff.spigot.Commands;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffFollow implements CommandExecutor {


    public StaffFollow() {
        BungeeStaff.getInstance().getCommand("bstafffollow").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(sender.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Staff-Follow"))) {
                if(args.length == 0) {
                    for(String s : BungeeStaff.getInstance().getMessages().getStringList("Staff-Follow.No-Argument")) {
                        p.sendMessage(BungeeStaff.getInstance().translate(s));
                    }
                    return true;
                }
                Player tar = Bukkit.getServer().getPlayer(args[0]);

                if(tar == null) {
                    p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Report-Module.Player-Not-Found")));
                    return true;
                }
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Follow.Joining"))
                        .replaceAll("%reported%", tar.getName()).replaceAll("%reported_server%", tar.getServer().getName()));

                p.teleport(tar);

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
        return true;
    }
}
