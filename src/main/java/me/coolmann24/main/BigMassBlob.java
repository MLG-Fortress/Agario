package me.coolmann24.main;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BigMassBlob {
   private ArmorStand a;
   private Vector velocity;

   public BigMassBlob(World world, Player p, int woolcolor) {
      this.a = (ArmorStand)world.spawn(p.getEyeLocation(), ArmorStand.class);
      this.a.setVisible(false);
      this.a.setGravity(false);
      this.a.setItemInHand(new ItemStack(Material.WOOL, 1, (short)woolcolor));
      this.velocity = new Vector(p.getLocation().getDirection().getX() * 3.0, 0.5, p.getLocation().getDirection().getZ() * 3.0);
   }

   public Vector getVelocity() {
      return this.velocity;
   }

   public void setVelocity(Vector v) {
      this.velocity = v;
   }

   public ArmorStand getBlob() {
      return this.a;
   }
}
