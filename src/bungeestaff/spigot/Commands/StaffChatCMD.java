package bungeestaff.spigot.Commands;

import bungeestaff.spigot.BungeeStaff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCMD implements CommandExecutor {

    public StaffChatCMD() {
        BungeeStaff.getInstance().getCommand("staffchat").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.StaffChat-Command"))) {

                if(args.length == 0) {
                    if(BungeeStaff.getInstance().getStaffchat().contains(p)) {
                        BungeeStaff.getInstance().staffchat.remove(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Disabled")));
                    } else {
                        BungeeStaff.getInstance().staffchat.add(p);
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Enabled")));
                    }
                }
                if(args.length >= 1) {
                    StringBuilder s = new StringBuilder();
                    for(int i = 0; i < args.length; i++) {
                        s.append(args[i]).append(" ");
                    }
                    for(Player pp : Bukkit.getServer().getOnlinePlayers()) {
                        if(pp.hasPermission(BungeeStaff.getInstance().getConfig().getString("Custom-Permissions.Request-Notify"))) {
                            if(BungeeStaff.getInstance().getConfig().getBoolean("Settings." + pp.getUniqueId()) == true) {
                                pp.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("StaffChat-Module.StaffChat-Message")).replaceAll("%player%", p.getName())
                                        .replaceAll("%player_server%", p.getServer().getName()).replaceAll("%message%", s.toString()));
                            }
                        }
                    }
                }

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
        return true;
    }
}
