package bungeestaff.bungee.Commands;

import bungeestaff.bungee.BungeeStaff;
import bungeestaff.bungee.Data;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
            }
            else if(args[0].equalsIgnoreCase("group")) {
                if(args.length == 1) {
                    for(String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                    }
                    return;
                }
                if(args[1].equalsIgnoreCase("help")) {
                    for(String oof2 : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.Help")) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(oof2));
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("search")) {
                    if(args.length == 2) {
                        for(String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    ProxiedPlayer p2 = BungeeCord.getInstance().getPlayer(args[2]);

                    if(p2 == null) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Not-Found")));
                        return;
                    }
                    String rank = Data.onlinestaff.get(p2.getName());
                    String prefix = BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix");
                    if (Data.prefix.containsKey(p2.getName())) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Search")
                                .replaceAll("%rank%", rank))
                        .replaceAll("%prefix%", BungeeStaff.getInstance().translate(prefix)).replaceAll("%player%", p2.getName()));
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Search-Null").replaceAll("%player%", p2.getName())));

                    }
                }
                else if(args[1].equalsIgnoreCase("list")) {
                    for (String groups : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.Group-List")) {
                        String gro = BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").toString();
                        sender.sendMessage(BungeeStaff.getInstance().translate(groups.replaceAll("%groups%", gro).replaceAll("\\[", "")).replaceAll("]", ""));
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("create")) {
                    if (args.length == 2) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    String group = args[2];
                    if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Found")
                                .replaceAll("%group%", group.toLowerCase())));
                    } else {
                        try {
                            File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".prefix", "[unknown] ");
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".users", "");

                            List<String> glist = BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List");
                            glist.add(group.toLowerCase());
                            BungeeStaff.getInstance().getBungeeStaff().set("Rank-List", glist);

                            BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Created"))
                                .replaceAll("%group%", group.toLowerCase()));
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("delete")) {
                    if (args.length == 2) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    String group = args[2];
                    if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {
                        try {
                            File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".prefix", null);
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".users", null);
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase(), null);
                            List<String> glist = BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List");
                            glist.remove(group.toLowerCase());
                            BungeeStaff.getInstance().getBungeeStaff().set("Rank-List", glist);

                            BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Deleted"))
                                    .replaceAll("%group%", group.toLowerCase()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Not-Found"))
                        .replaceAll("%group%", group));
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("setprefix")) {
                    if(args.length == 2) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    String group = args[2].toLowerCase();

                    if(args.length == 3) {
                        if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Prefix"))
                                    .replaceAll("%group%", group).replaceAll("%prefix%", BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + group + ".prefix")))
                                    .replaceAll("%player%", sender.getName()));
                        } else {
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Not-Found"))
                                    .replaceAll("%group%", group));
                        }
                        return;
                    }
                    StringBuilder s = new StringBuilder();
                    for(int i = 3; i < args.length; i++) {
                        s.append(args[i]).append(" ");
                    }
                    try {
                        if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {
                            File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                            BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".prefix", s.toString());

                            BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Set-Prefix"))
                                    .replaceAll("%group%", group.toLowerCase()).replaceAll("%prefix%", BungeeStaff.getInstance().translate(s.toString())));
                        } else {
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Not-Found"))
                                    .replaceAll("%group%", group));
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("add")) {
                    if(args.length == 2) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    ProxiedPlayer tar = ProxyServer.getInstance().getPlayer(args[2]);

                    if(args.length == 3) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    String group = args[3].toLowerCase();

                    if(tar == null) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Not-Found")));
                        return;
                    }
                    if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {

                        if(BungeeStaff.getInstance().getBungeeStaff().getStringList("Ranks." + group.toLowerCase() + ".users").contains(tar.getUniqueId().toString())) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Exists"))
                                    .replaceAll("%player%", tar.getName()));
                        } else {
                            try {
                                File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                                List<String> glist = BungeeStaff.getInstance().getBungeeStaff().getStringList("Ranks." + group.toLowerCase() + ".users");
                                glist.add(tar.getUniqueId().toString());
                                BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".users", glist);

                                BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);
                                Data.onlinestaff.put(tar.getName(), group);

                                String rank = Data.onlinestaff.get(tar.getName());
                                String prefix = BungeeStaff.getInstance().getBungeeStaff().getString("Ranks." + rank + ".prefix");

                                Data.prefix.put(tar.getName(), prefix);

                                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Add"))
                                        .replaceAll("%player%", tar.getName()).replaceAll("%group%", group));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Not-Found"))
                                .replaceAll("%group%", group));
                    }
                    return;
                }
                else if(args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    ProxiedPlayer tar = ProxyServer.getInstance().getPlayer(args[2]);

                    if (args.length == 3) {
                        for (String oof : BungeeStaff.getInstance().getMessages().getStringList("Group-Module.No-Argument")) {
                            sender.sendMessage(BungeeStaff.getInstance().translate(oof));
                        }
                        return;
                    }
                    String group = args[3].toLowerCase();

                    if (tar == null) {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Not-Found")));
                        return;
                    }
                    if (BungeeStaff.getInstance().getBungeeStaff().getStringList("Rank-List").contains(group.toLowerCase())) {
                        if (BungeeStaff.getInstance().getBungeeStaff().getStringList("Ranks." + group.toLowerCase() + ".users").contains(tar.getUniqueId().toString())) {
                            try {
                                File file = new File(BungeeStaff.getInstance().getDataFolder(), "config.yml");
                                List<String> glist = BungeeStaff.getInstance().getBungeeStaff().getStringList("Ranks." + group.toLowerCase() + ".users");
                                glist.remove(tar.getUniqueId().toString());
                                BungeeStaff.getInstance().getBungeeStaff().set("Ranks." + group.toLowerCase() + ".users", glist);
                                Data.prefix.remove(tar.getName());

                                BungeeStaff.getInstance().bungeestaffPP.save(BungeeStaff.getInstance().bungeestaff, file);

                                sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Remove"))
                                        .replaceAll("%player%", tar.getName()).replaceAll("%group%", group));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Player-Group-Not-Exist"))
                                    .replaceAll("%player%", tar.getName()));
                        }
                    } else {
                        sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("Group-Module.Group-Not-Found"))
                                .replaceAll("%group%", group));
                    }
                    return;
                }

            }else {
                for(String s : BungeeStaff.getInstance().getMessages().getStringList("BungeeStaff-Module.No-Such-Argument")) {
                    sender.sendMessage(BungeeStaff.getInstance().translate(s));
                }
            }
        } else {
            sender.sendMessage(BungeeStaff.getInstance().translate(BungeeStaff.getInstance().getMessages().getString("No-Permission")));
        }
    }
}
