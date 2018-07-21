package bungeestaff.bungee;

import bungeestaff.bungee.Commands.*;
import bungeestaff.bungee.Events.ChatEvent;
import bungeestaff.bungee.Events.JoinEvent;
import bungeestaff.bungee.Events.QuitEvent;
import bungeestaff.bungee.Events.TabComplete;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;

public class BungeeStaff extends Plugin implements Listener {

    public net.md_5.bungee.config.Configuration bungeestaff;
    public ConfigurationProvider bungeestaffPP;
    public File bungeestaffFile;
    public net.md_5.bungee.config.Configuration messages;
    public ConfigurationProvider messagesPP;
    public File messagesFile;
    public net.md_5.bungee.config.Configuration settings;
    public ConfigurationProvider settingsPP;
    public File settingsFile;

    private static BungeeStaff instance;
    public ArrayList<ProxiedPlayer> staffchat = new ArrayList<ProxiedPlayer>();
    public ArrayList<ProxiedPlayer> requestcooldown = new ArrayList<ProxiedPlayer>();
    public ArrayList<ProxiedPlayer> reportcooldown = new ArrayList<ProxiedPlayer>();
    public ArrayList<ProxiedPlayer> staffonline = new ArrayList<ProxiedPlayer>();

    public static BungeeStaff getInstance() {
        if(instance == null) {
            instance = new BungeeStaff();
        }
        return instance;
    }

    public void onEnable() {
        instance = this;
        getProxy().getPluginManager().registerListener(this, this);

        getProxy().getConsole().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
        getProxy().getConsole().sendMessage("§fBungee§bStaff §8- §dv" + getDescription().getVersion());
        getProxy().getConsole().sendMessage(ChatColor.GRAY + "Status §8» " + ChatColor.GREEN + "Plugin enabled");
        getProxy().getConsole().sendMessage("Author §8» §cLickem");
        getProxy().getConsole().sendMessage("");
        getProxy().getConsole().sendMessage(ChatColor.YELLOW + "SpigotMC §8» §fhttps://www.spigotmc.org/members/gazpachoyt.211629/");
        getProxy().getConsole().sendMessage(ChatColor.DARK_AQUA + "MC-Market §8» §fhttps://www.mc-market.org/members/54378/");
        getProxy().getConsole().sendMessage(ChatColor.WHITE + "You§cTube §8» §fhttps://www.youtube.com/Lickem");
        getProxy().getConsole().sendMessage(ChatColor.AQUA + "Twitter §8» §fhttps://twitter.com/LickemTickem");
        getProxy().getConsole().sendMessage(ChatColor.GRAY + "Discord §8» §fhttps://discord.gg/Cm7NQX3 (Lickem#9444)");
        getProxy().getConsole().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");

        bungeestaffFile = new File(ProxyServer.getInstance().getPluginsFolder() + "/config.yml");
        messagesFile = new File(ProxyServer.getInstance().getPluginsFolder() + "/messages.yml");
        settingsFile = new File(ProxyServer.getInstance().getPluginsFolder() + "/settings.yml");

        bungeestaffPP = ConfigurationProvider.getProvider(YamlConfiguration.class);
        messagesPP = ConfigurationProvider.getProvider(YamlConfiguration.class);
        settingsPP = ConfigurationProvider.getProvider(YamlConfiguration.class);

        register();
        createFiles();
        registerConfig();

    }

    public void onDisable() {
        instance = null;
        getProxy().getConsole().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
        getProxy().getConsole().sendMessage("§fBungee§bStaff §8- §dv" + getDescription().getVersion());
        getProxy().getConsole().sendMessage(ChatColor.GRAY + "Status §8» " + ChatColor.RED + "Plugin disabled");
        getProxy().getConsole().sendMessage("Author §8» §cLickem");
        getProxy().getConsole().sendMessage("");
        getProxy().getConsole().sendMessage(ChatColor.YELLOW + "SpigotMC §8» §fhttps://www.spigotmc.org/members/gazpachoyt.211629/");
        getProxy().getConsole().sendMessage(ChatColor.DARK_AQUA + "MC-Market §8» §fhttps://www.mc-market.org/members/54378/");
        getProxy().getConsole().sendMessage(ChatColor.WHITE + "You§cTube §8» §fhttps://www.youtube.com/Lickem");
        getProxy().getConsole().sendMessage(ChatColor.AQUA + "Twitter §8» §fhttps://twitter.com/LickemTickem");
        getProxy().getConsole().sendMessage(ChatColor.GRAY + "Discord §8» §fhttps://discord.gg/Cm7NQX3 (Lickem#9444)");
        getProxy().getConsole().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
    }

    private void register() {
        getProxy().getPluginManager().registerCommand(this, new CoreCMD());
        getProxy().getPluginManager().registerCommand(this, new StaffChatCMD());
        getProxy().getPluginManager().registerCommand(this, new RequestCMD());
        getProxy().getPluginManager().registerCommand(this, new ReportCMD());
        getProxy().getPluginManager().registerCommand(this, new ToggleSM());
        getProxy().getPluginManager().registerCommand(this, new StaffFollow());
        getProxy().getPluginManager().registerCommand(this, new StaffList());
        new ChatEvent();
        new QuitEvent();
        new JoinEvent();
        new TabComplete();

    }
    private void createFiles() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            try {
                InputStream in = getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        File file2 = new File(getDataFolder(), "messages.yml");
        if (!file2.exists())
            try {
                InputStream in2 = getResourceAsStream("messages.yml");
                Files.copy(in2, file2.toPath(), new CopyOption[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        File file3 = new File(getDataFolder(), "settings.yml");
        if (!file3.exists())
            try {
                InputStream in3 = getResourceAsStream("settings.yml");
                Files.copy(in3, file3.toPath(), new CopyOption[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void registerConfig() {
        try {
            bungeestaff = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "config.yml"));
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "messages.yml"));
            settings = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(instance.getDataFolder(), "settings.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public net.md_5.bungee.config.Configuration getMessages() {
        return messages;
    }

    public net.md_5.bungee.config.Configuration getBungeeStaff() {
        return bungeestaff;
    }

    public ArrayList<ProxiedPlayer> getStaffchat() {
        return staffchat;
    }

    public Configuration getSettings() {
        return settings;
    }
}
