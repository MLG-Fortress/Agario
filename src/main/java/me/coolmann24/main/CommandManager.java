package me.coolmann24.main;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
   private Main plugin;

   public CommandManager(Main plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
      if (cmd.getName().equalsIgnoreCase("agario")) {
         if (!(sender instanceof Player)) {
            return true;
         }

         Player p = (Player)sender;
         if (args.length == 0) {
            p.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
            p.sendMessage(ChatColor.AQUA + "Usage: /agario <args> <args>");
            p.sendMessage(ChatColor.AQUA + "/agario games -- shows active games on the server");
            p.sendMessage(ChatColor.AQUA + "/agario join <map name> -- join a map of specified name");
            p.sendMessage(ChatColor.AQUA + "/agario leave -- leave your current Agario game");
            p.sendMessage(ChatColor.GREEN + "Developed by coolmann24 in 2016, thanks for playing!");
            p.sendMessage(ChatColor.BLUE + "-----------------------------------------------------");
            if (p.hasPermission("agario.createmap")) {
               p.sendMessage(ChatColor.AQUA + "...also...");
               p.sendMessage(
                  ChatColor.AQUA
                     + "/agario createmap <int xminbound> <int xmaxbound> <int zminbound> <int zmaxbound> <int ymaplevel> <double xspectatorspawn> <double yspectatorspawn> <double zspectatorspawn> <String mapname>"
               );
               p.sendMessage(ChatColor.GREEN + "Map specifications can be set in the config, check the spigotmc plugin page for more info!");
            }

            if (p.hasPermission("agario.reload")) {
               p.sendMessage(ChatColor.AQUA + "...also...");
               p.sendMessage(ChatColor.AQUA + "/agario reload -- reload the plugin");
            }

            return true;
         }

         if (args[0].equalsIgnoreCase("join")) {
            if (args.length == 1) {
               p.sendMessage(ChatColor.AQUA + "/agario join <mapname> -- to join a game!");
               p.sendMessage(Main.mapmanager.getActiveGamesMessage());
               if (this.plugin.getConfig().getBoolean("guisettings.openguiwithagariogamescommand")) {
                  Main.openGameGui(p);
               }

               return true;
            }

            if (Main.mapmanager.isPlayerPlaying(p)) {
               p.sendMessage(ChatColor.RED + "You are already playing Agario!");
               return true;
            }

            boolean joined = Main.mapmanager.playerJoinMap(p, args[1]);
            if (joined) {
               p.sendMessage(ChatColor.GREEN + "You joined the game with map name " + args[1] + "!");
            } else {
               p.sendMessage(ChatColor.RED + "The game with map name " + args[1] + " is invalid or currently full!");
            }

            return true;
         }

         if (args[0].equalsIgnoreCase("leave")) {
            if (Main.mapmanager.isPlayerPlaying(p)) {
               Map playersmap = Main.mapmanager.getPlayersMap(p);
               AgarPlayer agarplayer = playersmap.getAgarPlayer(p);
               p.teleport(playersmap.getSpectatorSpawn());
               p.setWalkSpeed(0.2F);
               p.setFoodLevel(20);
               p.setLevel(0);
               p.getInventory().clear();
               Main.mapmanager.restorePlayerInventory(p);
               p.setScoreboard(Main.mapmanager.getBlankScoreboard());
               p.sendMessage(ChatColor.GREEN + "Leaving Agario map " + playersmap.getMapName() + "!");

               for (int i = agarplayer.getCells().size() - 1; i >= 0; i--) {
                  Cell c = agarplayer.getCells().get(i);
                  if (!c.isCellPlayer()) {
                     c.getEntity().remove();
                  }

                  agarplayer.getCells().remove(i);
               }

               playersmap.getPlayers().remove(agarplayer);
               return true;
            }

            p.sendMessage(ChatColor.RED + "You are not currently playing Agario!");
            return true;
         }

         if (args[0].equalsIgnoreCase("games")) {
            p.sendMessage(Main.mapmanager.getActiveGamesMessage());
            if (this.plugin.getConfig().getBoolean("guisettings.openguiwithagariogamescommand")) {
               Main.openGameGui(p);
            }

            return true;
         }

         if (args[0].equalsIgnoreCase("reload")) {
            if (!p.hasPermission("agario.reload")) {
               p.sendMessage(ChatColor.RED + "You don't have permission to reload Agario!");
               return true;
            }

            this.plugin.reloadPlugin();
            p.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
         }

         if (args[0].equalsIgnoreCase("createmap")) {
            if (!p.hasPermission("agario.createmap")) {
               p.sendMessage(ChatColor.RED + "You don't have permission to create an Agario map!");
               return true;
            }

            if (args.length < 7) {
               p.sendMessage(
                  ChatColor.RED
                     + "/agario createmap <int xminbound> <int xmaxbound> <int zminbound> <int zmaxbound> <int ymaplevel> <double xspectatorspawn> <double yspectatorspawn> <double zspectatorspawn> <String mapname>"
               );
               return true;
            }

            int xmin;
            int xmax;
            int zmin;
            int zmax;
            int y;
            double xspawn;
            double yspawn;
            double zspawn;
            try {
               xmin = Integer.parseInt(args[1]);
               xmax = Integer.parseInt(args[2]);
               zmin = Integer.parseInt(args[3]);
               zmax = Integer.parseInt(args[4]);
               y = Integer.parseInt(args[5]);
               xspawn = Double.parseDouble(args[6]);
               yspawn = Double.parseDouble(args[7]);
               zspawn = Double.parseDouble(args[8]);
               if (xmin > xmax) {
                  int a = xmin;
                  xmin = xmax;
                  xmax = a;
               }

               if (zmin > zmax) {
                  int a = zmin;
                  zmin = zmax;
                  zmax = a;
               }
            } catch (Exception e) {
               p.sendMessage(
                  ChatColor.RED
                     + "/agario createmap <int xminbound> <int xmaxbound> <int zminbound> <int zmaxbound> <int ymaplevel> <double xspectatorspawn> <double yspectatorspawn> <double zspectatorspawn> <String mapname>"
               );
               return true;
            }

            for (Map map : Main.mapmanager.getMaps()) {
               if (map.getMapName().equals(args[9])) {
                  p.sendMessage(
                     ChatColor.RED
                        + "A map with that name already exists! Choose a different name or delete the old map in the config file and reload the server!"
                  );
                  return true;
               }
            }

            Material m = p.getWorld().getBlockAt(xmin, y, zmin).getType();

            for (int i = xmin; i <= xmax; i++) {
               for (int j = zmin; j <= zmax; j++) {
                  if (p.getWorld().getBlockAt(i, y, j).getType() != m || m == Material.AIR) {
                     p.sendMessage(ChatColor.RED + "The whole map area must have be the same non-air block material!");
                     return true;
                  }
               }
            }

            ArrayList<Integer> datalist = new ArrayList<>();
            datalist.add(0);
            datalist.add(1);
            datalist.add(2);
            datalist.add(3);
            datalist.add(4);
            datalist.add(5);
            datalist.add(6);
            datalist.add(7);
            datalist.add(8);
            datalist.add(9);
            datalist.add(10);
            datalist.add(11);
            datalist.add(12);
            datalist.add(13);
            datalist.add(14);
            datalist.add(15);
            if (!this.plugin.getConfig().contains("Maps")) {
               this.plugin.getConfig().createSection("Maps");
            }

            int availablemap = 1;
            boolean available = false;

            while (!available) {
               if (this.plugin.getConfig().contains("Maps." + availablemap)) {
                  availablemap++;
               } else {
                  available = true;
                  this.plugin.getConfig().createSection("Maps." + availablemap);
               }
            }

            String mapnumber = String.valueOf(availablemap);
            this.plugin.getConfig().set("Maps." + mapnumber + ".mapname", args[9]);
            this.plugin.getConfig().set("Maps." + mapnumber + ".xminbound", xmin);
            this.plugin.getConfig().set("Maps." + mapnumber + ".xmaxbound", xmax);
            this.plugin.getConfig().set("Maps." + mapnumber + ".zminbound", zmin);
            this.plugin.getConfig().set("Maps." + mapnumber + ".zmaxbound", zmax);
            this.plugin.getConfig().set("Maps." + mapnumber + ".ylevel", y);
            this.plugin.getConfig().set("Maps." + mapnumber + ".world", p.getWorld().getName());
            this.plugin.getConfig().set("Maps." + mapnumber + ".mapmaterial", m.toString());
            this.plugin.getConfig().set("Maps." + mapnumber + ".massperblock", 5);
            this.plugin.getConfig().set("Maps." + mapnumber + ".maxsmallblobsnatural", 1000);
            this.plugin.getConfig().set("Maps." + mapnumber + ".decayrate", 0.005);
            this.plugin.getConfig().set("Maps." + mapnumber + ".defaultcellsizeint", 20);
            this.plugin.getConfig().set("Maps." + mapnumber + ".deathspawnlocationx", xspawn);
            this.plugin.getConfig().set("Maps." + mapnumber + ".deathspawnlocationy", yspawn);
            this.plugin.getConfig().set("Maps." + mapnumber + ".deathspawnlocationz", zspawn);
            this.plugin.getConfig().set("Maps." + mapnumber + ".walkspeedmultiplier", 25);
            this.plugin.getConfig().set("Maps." + mapnumber + ".recombinedelayticks", 800);
            this.plugin.getConfig().set("Maps." + mapnumber + ".smallmassblobspawnrate", 5);
            this.plugin.getConfig().set("Maps." + mapnumber + ".usegreenvirus", true);
            this.plugin.getConfig().set("Maps." + mapnumber + ".useredvirus", true);
            this.plugin.getConfig().set("Maps." + mapnumber + ".maxgreenviruscountint", 6);
            this.plugin.getConfig().set("Maps." + mapnumber + ".maxredviruscountint", 2);
            this.plugin.getConfig().set("Maps." + mapnumber + ".massthrowloss", 15.0);
            this.plugin.getConfig().set("Maps." + mapnumber + ".minmasstothrow", 35.0);
            this.plugin.getConfig().set("Maps." + mapnumber + ".minmassgreenvirussplit", 100.0);
            this.plugin.getConfig().set("Maps." + mapnumber + ".greenvirusduplicatesize", 6);
            this.plugin.getConfig().set("Maps." + mapnumber + ".massofthrownmassblob", 10.0);
            this.plugin.getConfig().set("Maps." + mapnumber + ".maxmassredvirusspawn", 400);
            this.plugin.getConfig().set("Maps." + mapnumber + ".minmassforcellsplit", 35.0);
            this.plugin.getConfig().set("Maps." + mapnumber + ".cellwoolcolorlist", datalist);
            this.plugin.getConfig().set("Maps." + mapnumber + ".gamemodeafterdeath", GameMode.ADVENTURE.toString());
            this.plugin.getConfig().set("Maps." + mapnumber + ".commandscantrunwhenplaying", new ArrayList());
            this.plugin.getConfig().set("Maps." + mapnumber + ".useleaderboard", false);
            this.plugin.getConfig().set("Maps." + mapnumber + ".maxplayercount", 20);
            this.plugin.saveConfig();
            Map newmap = new Map(
               new int[]{xmin, xmax, zmin, zmax},
               m,
               args[9],
               y,
               p.getWorld(),
               false,
               5.0,
               1000,
               0.005,
               null,
               20,
               new Location(p.getWorld(), xspawn, yspawn, zspawn),
               25.0,
               800,
               5,
               true,
               true,
               6,
               2,
               15.0,
               35.0,
               100.0,
               6,
               10.0,
               400,
               35.0,
               datalist,
               GameMode.ADVENTURE,
               new ArrayList<>(),
               false,
               20
            );
            if (Main.mapmanager.getMaps().size() == 0) {
               new GameTimer().runTaskTimer(this.plugin, 1L, 2L);
            }

            Main.mapmanager.addMap(newmap);
            p.sendMessage(ChatColor.GREEN + "Map " + args[9] + " created!");
            return true;
         }
      }

      return true;
   }
}
