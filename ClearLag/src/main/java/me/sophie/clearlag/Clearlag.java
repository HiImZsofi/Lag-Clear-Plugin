package me.sophie.clearlag;

import me.sophie.clearlag.ClearCommand.ClearCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class Clearlag extends JavaPlugin {

    public static int deletedNumber; //deleted entities' number
    private static Timer bgtimer = new Timer(); //background timer

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("clear").setExecutor(new ClearCommand());

        bgtimer.schedule(new AutoSwipe(), 0, 1000*60*15); //schedule the delete method in every 15 minutes

    }

    public class AutoSwipe extends TimerTask {
        @Override
        public void run() {
            World world = getServer().getWorld("world");
            List<Entity> entiList = world.getEntities(); //get all entities and put it in a list
            for (Entity current : entiList) {
                if (current instanceof Item) { //making sure entities aren't mobs or players

                    deletedNumber++;
                    current.remove();

                }
            }
            Bukkit.broadcastMessage("[" + deletedNumber + "] " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("DoneMessage")));
        }
    }

}


