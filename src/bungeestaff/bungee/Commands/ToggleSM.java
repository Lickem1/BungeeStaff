package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
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
                if(BungeeStaff.getInstance().getSettings().getBoolean("Settings." + p.getUniqueId() + ".Staff-Messages") == true) {
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "settings.yml");
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-Off")));
                        BungeeStaff.getInstance().getSettings().set("Settings." + p.getUniqueId() + ".Staff-Messages", false);
                        BungeeStaff.getInstance().settingsPP.save(BungeeStaff.getInstance().settings, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        File file = new File(BungeeStaff.getInstance().getDataFolder(), "settings.yml");
                        p.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Staff-Messages.Staff-Messages-On")));
                        BungeeStaff.getInstance().getSettings().set("Settings." + p.getUniqueId() + ".Staff-Messages", true);
                        BungeeStaff.getInstance().settingsPP.save(BungeeStaff.getInstance().settings, file);
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
