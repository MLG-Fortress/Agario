package me.coolmann24.main;

import java.util.ArrayList;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   public static MapManager mapmanager;
   public static Random random;
   public GameTimer gametimer;

   public void onEnable() {
      Bukkit.getPluginManager().registerEvents(new EntityDamaged(), this);
      Bukkit.getPluginManager().registerEvents(new InventoryClicked(this), this);
      Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
      Bukkit.getPluginManager().registerEvents(new FoodLevelChange(), this);
      Bukkit.getPluginManager().registerEvents(new ThrowMass(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerExecuteCommandEvent(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerRightClickVillager(), this);
      Bukkit.getPluginManager().registerEvents(new PlayerClickGuiOpener(this), this);
      Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
      this.getLogger().info("Agario Enabled!");
      mapmanager = new MapManager(this);
      random = new Random();
      this.getCommand("agario").setExecutor(new CommandManager(this));
      this.loadMaps();
      mapmanager.killEntitiesInMap();
      mapmanager.restoreMapMaterial();
      if (mapmanager.getMaps().size() > 0) {
         this.gametimer = new GameTimer();
         this.gametimer.runTaskTimer(this, 1L, 2L);
      }

      if (!this.getConfig().contains("guisettings")) {
         this.getConfig().set("guisettings.useobjectforopening", true);
         this.getConfig().set("guisettings.objectforopeningmaterial", "SLIME_BALL");
         this.getConfig().set("guisettings.openguiwithagariogamescommand", true);
         this.getConfig().set("guisettings.addguiopenitemtoinventory", false);
         this.saveConfig();
      }
   }

   public void onDisable() {
      mapmanager.killEntitiesInMap();
      mapmanager.restoreMapMaterial();
      mapmanager = null;
      random = null;
   }

   public static double distance(double x1, double z1, double x2, double z2) {
      double xchange = x1 - x2;
      double zchange = z1 - z2;
      return Math.sqrt(xchange * xchange + zchange * zchange);
   }

   public void loadMaps() {
      if (this.getConfig().contains("Maps")) {
         for (int mapcount = 0; this.getConfig().contains("Maps." + (mapcount + 1)); mapcount++) {
            int mapnumber = mapcount + 1;

            try {
               if (!this.getConfig().contains("Maps." + mapnumber + ".useleaderboard")) {
                  this.getConfig().set("Maps." + mapnumber + ".useleaderboard", false);
                  this.saveConfig();
               }

               if (!this.getConfig().contains("Maps." + mapnumber + ".maxplayercount")) {
                  this.getConfig().set("Maps." + mapnumber + ".maxplayercount", 20);
                  this.saveConfig();
               }

               Map map = new Map(
                  new int[]{
                     this.getConfig().getInt("Maps." + mapnumber + ".xminbound"),
                     this.getConfig().getInt("Maps." + mapnumber + ".xmaxbound"),
                     this.getConfig().getInt("Maps." + mapnumber + ".zminbound"),
                     this.getConfig().getInt("Maps." + mapnumber + ".zmaxbound")
                  },
                  Material.valueOf(this.getConfig().getString("Maps." + mapnumber + ".mapmaterial")),
                  this.getConfig().getString("Maps." + mapnumber + ".mapname"),
                  this.getConfig().getInt("Maps." + mapnumber + ".ylevel"),
                  Bukkit.getServer().getWorld(this.getConfig().getString("Maps." + mapnumber + ".world")),
                  false,
                  this.getConfig().getDouble("Maps." + mapnumber + ".massperblock"),
                  this.getConfig().getInt("Maps." + mapnumber + ".maxsmallblobsnatural"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".decayrate"),
                  new ArrayList<>(),
                  this.getConfig().getInt("Maps." + mapnumber + ".defaultcellsizeint"),
                  new Location(
                     Bukkit.getServer().getWorld(this.getConfig().getString("Maps." + mapnumber + ".world")),
                     this.getConfig().getDouble("Maps." + mapnumber + ".deathspawnlocationx"),
                     this.getConfig().getDouble("Maps." + mapnumber + ".deathspawnlocationy"),
                     this.getConfig().getDouble("Maps." + mapnumber + ".deathspawnlocationz")
                  ),
                  this.getConfig().getDouble("Maps." + mapnumber + ".walkspeedmultiplier"),
                  this.getConfig().getInt("Maps." + mapnumber + ".recombinedelayticks"),
                  this.getConfig().getInt("Maps." + mapnumber + ".smallmassblobspawnrate"),
                  this.getConfig().getBoolean("Maps." + mapnumber + ".usegreenvirus"),
                  this.getConfig().getBoolean("Maps." + mapnumber + ".useredvirus"),
                  this.getConfig().getInt("Maps." + mapnumber + ".maxgreenviruscountint"),
                  this.getConfig().getInt("Maps." + mapnumber + ".maxredviruscountint"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".massthrowloss"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".minmasstothrow"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".minmassgreenvirussplit"),
                  this.getConfig().getInt("Maps." + mapnumber + ".greenvirusduplicatesize"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".massofthrownmassblob"),
                  this.getConfig().getInt("Maps." + mapnumber + ".maxmassredvirusspawn"),
                  this.getConfig().getDouble("Maps." + mapnumber + ".minmassforcellsplit"),
                  (ArrayList<Integer>)this.getConfig().getIntegerList("Maps." + mapnumber + ".cellwoolcolorlist"),
                  GameMode.valueOf(this.getConfig().getString("Maps." + mapnumber + ".gamemodeafterdeath")),
                  (ArrayList<String>)this.getConfig().getStringList("Maps." + mapnumber + ".commandscantrunwhenplaying"),
                  this.getConfig().getBoolean("Maps." + mapnumber + ".useleaderboard"),
                  this.getConfig().getInt("Maps." + mapnumber + ".maxplayercount")
               );
               mapmanager.addMap(map);
               this.getLogger().info("Agario loaded map " + map.getMapName() + "!");
            } catch (Exception e) {
               e.printStackTrace();
               this.getLogger()
                  .info(
                     "[Error]: Agario couldn't load map "
                        + mapnumber
                        + " because of an error reading it from the config! Possible invalid config values, fix or re-create the map again!"
                  );
            }
         }
      }
   }

   public static void openGameGui(Player p) {
      if (mapmanager.getMaps().size() != 0) {
         Inventory i = Bukkit.createInventory(null, (mapmanager.getMaps().size() - 1) / 9 * 9 + 9, "Agario Games");

         for (int j = 0; j < mapmanager.getMaps().size(); j++) {
            ItemStack is = new ItemStack(Material.SLIME_BLOCK);
            Map m = mapmanager.getMaps().get(j);
            ItemMeta s = is.getItemMeta();
            s.setDisplayName(ChatColor.AQUA + "Agario " + m.getMapName() + " Players: " + m.getPlayers().size() + "/" + m.getMaxPlayerCount());
            is.setItemMeta(s);
            i.setItem(j, is);
         }

         p.openInventory(i);
      }
   }

   public void reloadPlugin() {
      this.gametimer.cancel();
      mapmanager.killEntitiesInMap();
      mapmanager = new MapManager(this);
      this.loadMaps();
      mapmanager.restoreMapMaterial();
      if (mapmanager.getMaps().size() > 0) {
         this.gametimer = new GameTimer();
         this.gametimer.runTaskTimer(this, 1L, 2L);
      }

      this.getLogger().info("[Agario] Plugin reloaded!");
   }
}
