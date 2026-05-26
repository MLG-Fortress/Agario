package me.coolmann24.main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Slime;
import org.bukkit.util.Vector;

public class GreenVirus {
   private Slime slime;
   private Location loc;
   private int size;

   public GreenVirus(Slime slime, int size) {
      this.slime = slime;
      this.size = size;
   }

   public GreenVirus(World world, Location loc, int slimesize, int intsize, Location permloc, Vector velocity, Map map) {
      this.slime = (Slime)world.spawn(loc, Slime.class);
      this.slime.setAI(false);
      this.slime.setGravity(false);
      this.slime.setCollidable(false);
      this.slime.setRemoveWhenFarAway(false);
      this.slime.setSize(slimesize);
      this.size = intsize;
      this.loc = permloc;
      if (velocity != null) {
         Main.mapmanager.doGreenVirusSplitTimer(this, velocity, map);
      }
   }

   public Slime getSlime() {
      return this.slime;
   }

   public int getIntSize() {
      return this.size;
   }

   public void setIntSize(int size) {
      this.size = size;
   }

   public Location getGreenVirusLocation() {
      return this.slime.getLocation();
   }

   public Location getPermanentLocation() {
      return this.loc;
   }

   public void setPermanentLocation(Location loc) {
      this.loc = loc;
   }
}
