package me.sophie.clearlag.ClearCommand;

import me.sophie.clearlag.Clearlag;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.broadcastMessage;
import static org.bukkit.Bukkit.getServer;

public class ClearCommand implements CommandExecutor {

    Plugin plugin = Clearlag.getPlugin(Clearlag.class); //get the config file

    String forceclear = plugin.getConfig().getString("ForceClearPermission");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        World world = getServer().getWorld("world");
        List<Entity> entList = world.getEntities(); //get all entities and put it in a list

        if (command.getName().equalsIgnoreCase("clear")) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("items")) {
            if (sender.hasPermission(forceclear)) { //only server operators will be able to issue
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("BeforeMsg"))); //gets the message from the config
                Timer timer = new Timer(); //creating the timer
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        for (Entity current : entList) {
                            if (current instanceof Item) { //making sure entities aren't mobs or players

                                current.remove();

                            }
                        }
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("DoneMessage")));
                    }
                }, 1000 * 60); //counts down 1 minute
            }
            } else if (args.length < 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("InputError")));
            }
        }
        return false;
    }
}