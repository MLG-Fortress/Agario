package me.coolmann24.main;

import org.bukkit.Location;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GreenVirusSplit extends BukkitRunnable {
   private GreenVirus g;
   private Vector direction;
   private int time;
   private Map map;

   public GreenVirusSplit(GreenVirus g, Vector direction, Map map) {
      this.g = g;
      this.direction = direction.multiply(3.0);
      this.map = map;
      this.time = 0;
   }

   public void run() {
      this.time++;
      Slime s = this.g.getSlime();
      if (s == null) {
         this.cancel();
      }

      if (s.getLocation().getX() + this.direction.getX() > this.map.getXMinBounds()
         && s.getLocation().getX() + this.direction.getX() < this.map.getXMaxBounds()
         && s.getLocation().getZ() + this.direction.getZ() > this.map.getZMinBounds()
         && s.getLocation().getZ() + this.direction.getZ() < this.map.getZMaxBounds()) {
         s.teleport(
            new Location(s.getWorld(), s.getLocation().getX() + this.direction.getX(), s.getLocation().getY(), s.getLocation().getZ() + this.direction.getZ())
         );
      } else {
         this.time = 5;
      }

      if (this.time == 5) {
         this.g.setPermanentLocation(this.g.getGreenVirusLocation());
         this.cancel();
      }
   }
}
