package me.coolmann24.main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.MagmaCube;

public class RedVirus {
   private MagmaCube magmacube;
   private Location loc;
   private double size;

   public RedVirus(MagmaCube magmacube, int size) {
      this.magmacube = magmacube;
      this.size = size;
   }

   public RedVirus(World world, Location loc, int magmasize, double doublesize) {
      this.magmacube = (MagmaCube)world.spawn(loc, MagmaCube.class);
      this.magmacube.setAI(false);
      this.magmacube.setGravity(false);
      this.magmacube.setCollidable(false);
      this.magmacube.setRemoveWhenFarAway(false);
      this.magmacube.setSize(magmasize);
      this.size = doublesize;
      this.loc = this.magmacube.getLocation();
   }

   public MagmaCube getMagma() {
      return this.magmacube;
   }

   public double getDoubleSize() {
      return this.size;
   }

   public void setDoubleSize(double size) {
      this.size = size;
   }

   public Location getRedVirusLocation() {
      return this.magmacube.getLocation();
   }

   public Location getPermanentLocation() {
      return this.loc;
   }
}
