package me.coolmann24.main;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Map {
   private ArrayList<AgarPlayer> agarplayers;
   private ArrayList<GreenVirus> greenviruses;
   private ArrayList<RedVirus> redviruses;
   private ArrayList<Block> cellblocks;
   private ArrayList<BigMassBlob> movingthrownmass;
   private ArrayList<String> teams;
   private ArrayList<Integer> materialdatalist;
   private ArrayList<String> commandscantrunwhenplaying;
   private int[] mapbounds;
   private int smallblobcount;
   private int mapylevel;
   private int maxsmallblobs;
   private int defaultsize;
   private int recombinedelay;
   private int smallmassblobspawnrate;
   private int maxgreenvirus;
   private int maxredvirus;
   private int greenvirussplitsize;
   private int maxredvirusmassspawn;
   private int redvirusblobcount;
   private int maxplayercount;
   private Material mapmaterial;
   private String mapname;
   private World world;
   private boolean teammode;
   private boolean usegreenviruses;
   private boolean useredviruses;
   private boolean leaderboard;
   private double massperblock;
   private double decay;
   private double walkspeedmultiplier;
   private double massthrowloss;
   private double masstothrow;
   private double minmassvirussplit;
   private double massofbigmassblob;
   private double minmassforsplit;
   private Location spectatorlocation;
   private GameMode gamemodeafterdeath;
   private Objective objective;

   public Map(
      int[] mapbounds,
      Material material,
      String mapname,
      int ylevel,
      World world,
      boolean teammode,
      double massperblock,
      int maxsmallblobs,
      double decay,
      ArrayList<String> teams,
      int defaultsize,
      Location spectatorlocation,
      double walkspeedmultiplier,
      int recombinedelay,
      int smallmassblobspawnrate,
      boolean usegreenviruses,
      boolean useredviruses,
      int maxgreenvirus,
      int maxredvirus,
      double massthrowloss,
      double masstothrow,
      double minmassvirussplit,
      int greenvirussplitsize,
      double massofbigmassblob,
      int maxredvirusmassspawn,
      double minmassforsplit,
      ArrayList<Integer> materialdata,
      GameMode gamemodeafterdeath,
      ArrayList<String> commandscantrunwhenplaying,
      boolean leaderboard,
      int maxplayercount
   ) {
      this.mapbounds = mapbounds;
      this.mapmaterial = material;
      this.mapname = mapname;
      this.smallblobcount = 0;
      this.mapylevel = ylevel;
      this.world = world;
      this.teammode = teammode;
      this.massperblock = massperblock;
      this.maxsmallblobs = maxsmallblobs;
      this.decay = decay;
      this.defaultsize = defaultsize;
      this.spectatorlocation = spectatorlocation;
      this.walkspeedmultiplier = walkspeedmultiplier;
      this.recombinedelay = recombinedelay;
      this.smallmassblobspawnrate = smallmassblobspawnrate;
      this.usegreenviruses = usegreenviruses;
      this.useredviruses = useredviruses;
      this.maxgreenvirus = maxgreenvirus;
      this.maxredvirus = maxredvirus;
      this.massthrowloss = massthrowloss;
      this.masstothrow = masstothrow;
      this.minmassvirussplit = minmassvirussplit;
      this.greenvirussplitsize = greenvirussplitsize;
      this.massofbigmassblob = massofbigmassblob;
      this.maxredvirusmassspawn = maxredvirusmassspawn;
      this.redvirusblobcount = 0;
      this.minmassforsplit = minmassforsplit;
      this.gamemodeafterdeath = gamemodeafterdeath;
      this.maxplayercount = maxplayercount;
      this.agarplayers = new ArrayList<>();
      this.greenviruses = new ArrayList<>();
      this.redviruses = new ArrayList<>();
      this.cellblocks = new ArrayList<>();
      this.movingthrownmass = new ArrayList<>();
      this.materialdatalist = materialdata;
      this.teams = teams;
      this.commandscantrunwhenplaying = commandscantrunwhenplaying;
      this.leaderboard = leaderboard;
      if (leaderboard) {
         this.objective = Bukkit.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective(mapname, "dummy");
         this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      }

   }

   public int getXMinBounds() {
      return this.mapbounds[0];
   }

   public int getXMaxBounds() {
      return this.mapbounds[1];
   }

   public int getZMinBounds() {
      return this.mapbounds[2];
   }

   public int getZMaxBounds() {
      return this.mapbounds[3];
   }

   public int getYLevel() {
      return this.mapylevel;
   }

   public Material getMapMaterial() {
      return this.mapmaterial;
   }

   public World getWorld() {
      return this.world;
   }

   public double getMassPerBlock() {
      return this.massperblock;
   }

   public ArrayList<AgarPlayer> getPlayers() {
      return this.agarplayers;
   }

   public int getMaxSmallBlobs() {
      return this.maxsmallblobs;
   }

   public boolean isTeamMode() {
      return this.teammode;
   }

   public int getSmallBlobCount() {
      return this.smallblobcount;
   }

   public void setSmallBlobCount(int s) {
      this.smallblobcount = s;
   }

   public double getDecay() {
      return this.decay;
   }

   public ArrayList<GreenVirus> getGreenViruses() {
      return this.greenviruses;
   }

   public ArrayList<RedVirus> getRedViruses() {
      return this.redviruses;
   }

   public String getMapName() {
      return this.mapname;
   }

   public void addBlockUsed(Block b) {
      this.cellblocks.add(b);
   }

   public ArrayList<Block> getBlocksUsed() {
      return this.cellblocks;
   }

   public ArrayList<String> getTeams() {
      return this.teams;
   }

   public int getDefaultSize() {
      return this.defaultsize;
   }

   public Location getSpectatorSpawn() {
      return this.spectatorlocation;
   }

   public double getWalkSpeedMultiplier() {
      return this.walkspeedmultiplier;
   }

   public int getRecombineDelay() {
      return this.recombinedelay;
   }

   public void setRecombineDelay(int s) {
      this.recombinedelay = s;
   }

   public int getSmallMassBlobSpawnRate() {
      return this.smallmassblobspawnrate;
   }

   public boolean useGreenViruses() {
      return this.usegreenviruses;
   }

   public boolean useRedViruses() {
      return this.useredviruses;
   }

   public int getMaxGreenVirus() {
      return this.maxgreenvirus;
   }

   public int getMaxRedVirus() {
      return this.maxredvirus;
   }

   public ArrayList<BigMassBlob> getBigMassBlobsMoving() {
      return this.movingthrownmass;
   }

   public AgarPlayer getAgarPlayer(Player p) {
      for (AgarPlayer agp : this.agarplayers) {
         if (agp.getPlayer().equals(p)) {
            return agp;
         }
      }

      return null;
   }

   public double getMassThrowLoss() {
      return this.massthrowloss;
   }

   public double getMassToThrow() {
      return this.masstothrow;
   }

   public double getMinMassVirusSplit() {
      return this.minmassvirussplit;
   }

   public int getGreenVirusSplitSize() {
      return this.greenvirussplitsize;
   }

   public double getMassOfBigMassBlob() {
      return this.massofbigmassblob;
   }

   public int getMaxredVirusMassSpawn() {
      return this.maxredvirusmassspawn;
   }

   public int getRedVirusBlobCount() {
      return this.redvirusblobcount;
   }

   public void setRedVirusBlobCount(int s) {
      this.redvirusblobcount = s;
   }

   public double getMinMassForSplit() {
      return this.minmassforsplit;
   }

   public ArrayList<Integer> getMaterialDataList() {
      return this.materialdatalist;
   }

   public GameMode getGameModeAfterDeath() {
      return this.gamemodeafterdeath;
   }

   public ArrayList<String> getCommandsCantRunWhenPlaying() {
      return this.commandscantrunwhenplaying;
   }

   public boolean contains(Player p) {
      for (AgarPlayer player : this.agarplayers) {
         if (p.equals(player.getPlayer())) {
            return true;
         }
      }

      return false;
   }

   public Objective getScoreboardObjective() {
      return this.objective;
   }

   public boolean usingLeaderboard() {
      return this.leaderboard;
   }

   public int getMaxPlayerCount() {
      return this.maxplayercount;
   }
}
