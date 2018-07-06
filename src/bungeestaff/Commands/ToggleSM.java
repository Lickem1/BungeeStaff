package bungeestaff.Commands;

import bungeestaff.BungeeStaff;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.File;
import java.io.IOException;

public class ToggleSM extends Command {

    public ToggleSM() {
        super("togglestaffmessages", "", "tsm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            if(p.hasPermission(BungeeStaff.getInstance().getBungeeStaff().getString("Custom-Permissions.Toggle-Staff-Messages"))) {
                if(BungeeStaff.getInstance().getBungeeStaff().getBoolean("Settings." + p.getUniqueId()) == true) {
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-Off")));
                        BungeeStaff.getInstance().getBungeeStaff().set("Settings." + p.getUniqueId(), false);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-On")));
                        BungeeStaff.getInstance().getBungeeStaff().set("Settings." + p.getUniqueId(), true);
                        BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
            }
        }
    }
}
