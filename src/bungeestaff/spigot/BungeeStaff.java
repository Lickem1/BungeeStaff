package bungeestaff.spigot;

import bungeestaff.spigot.Commands.*;
import bungeestaff.spigot.Events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;

public class BungeeStaff extends JavaPlugin implements Listener {

    public File message = null;
    public YamlConfiguration messagesYAML = new YamlConfiguration();


    private static BungeeStaff instance;
    public ArrayList<Player> staffchat = new ArrayList<Player>();
    public ArrayList<Player> requestcooldown = new ArrayList<Player>();
    public ArrayList<Player> reportcooldown = new ArrayList<Player>();

    public static BungeeStaff getInstance() {
        if(instance == null) {
            instance = new BungeeStaff();
        }
        return instance;
    }

    public void onEnable() {
        instance = this;
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
        Bukkit.getServer().getConsoleSender().sendMessage("§fBungee§bStaff §8- §dv" + getDescription().getVersion());
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Status §8» " + ChatColor.GREEN + "Plugin enabled");
        Bukkit.getServer().getConsoleSender().sendMessage("Author §8» §cLickem");
        Bukkit.getServer().getConsoleSender().sendMessage("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "SpigotMC §8» §fhttps://www.spigotmc.org/members/gazpachoyt.211629/");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "MC-Market §8» §fhttps://www.mc-market.org/members/54378/");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "You§cTube §8» §fhttps://www.youtube.com/Lickem");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Twitter §8» §fhttps://twitter.com/LickemTickem");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Discord §8» §fhttps://discord.gg/Cm7NQX3 (Lickem#9444)");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");

        message = new File(getDataFolder() + "/messages.yml");

        getConfig().options().copyDefaults(true).copyHeader(true);
        saveDefaultConfig();

        register();
        mkdir();
        loadYamls();

    }

    public void onDisable() {
        instance = null;
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
        Bukkit.getServer().getConsoleSender().sendMessage("§fBungee§bStaff §8- §dv" + getDescription().getVersion());
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Status §8» " + ChatColor.RED + "Plugin disabled");
        Bukkit.getServer().getConsoleSender().sendMessage("Author §8» §cLickem");
        Bukkit.getServer().getConsoleSender().sendMessage("");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "SpigotMC §8» §fhttps://www.spigotmc.org/members/gazpachoyt.211629/");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "MC-Market §8» §fhttps://www.mc-market.org/members/54378/");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.WHITE + "You§cTube §8» §fhttps://www.youtube.com/Lickem");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Twitter §8» §fhttps://twitter.com/LickemTickem");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GRAY + "Discord §8» §fhttps://discord.gg/Cm7NQX3 (Lickem#9444)");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------");
    }

    private void register() {
        new CoreCMD();
        new StaffChatCMD();
        //new RequestCMD();
        //new ReportCMD();
        new ToggleSM();
        new StaffFollow();
        new ChatEvent();
        new QuitEvent();
        new JoinEvent();

    }
    private void mkdir() {
        if(!message.exists()) {
            saveResource("messages.yml", false);
        }
    }
    public void loadYamls() {
        try {
            messagesYAML.load(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void saveMessage() {
        try {
            messagesYAML.save(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public Configuration getMessages() {
        return messagesYAML;
    }

    public ArrayList<Player> getStaffchat() {
        return staffchat;
    }
}
