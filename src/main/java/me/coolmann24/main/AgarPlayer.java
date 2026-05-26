package me.coolmann24.main;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class AgarPlayer {
   private ArrayList<Cell> cells;
   private ArrayList<Material> materials;
   private int materialdata;
   private Player player;
   private String team;
   private boolean splitbuffered;
   private boolean cooldown;

   public AgarPlayer(Player p, String team, int data) {
      this.player = p;
      this.cells = new ArrayList<>();
      this.materials = new ArrayList<>();
      this.materialdata = data;
      this.materials.add(WoolColors.materialFor(data));
      this.team = team;
      this.splitbuffered = true;
      this.cooldown = false;
   }

   public Player getPlayer() {
      return this.player;
   }

   public void addCell(Entity e, int s, int recdelay) {
      this.cells.add(new Cell(e, s, recdelay));
   }

   public void removeCell(Entity e) {
      for (Cell c : this.cells) {
         if (c.getEntity().equals(e)) {
            this.cells.remove(c);
            return;
         }
      }
   }

   public void removeCell(Cell c, Location spectatorspawn) {
      Cell c1 = c;
      this.cells.remove(c);
      if (c1.getEntity() instanceof Player) {
         Player p = (Player)c1.getEntity();
         if (this.cells.size() != 0) {
            Entity e = this.cells.get(0).getEntity();
            p.teleport(e);
            e.remove();
            this.cells.get(0).setEntity(p);
            p.sendMessage(ChatColor.RED + "You died so you were moved to another one of your cells!");
         } else {
            p.teleport(spectatorspawn);
            p.sendMessage(ChatColor.RED + "You died!");
         }
      } else {
         c1.getEntity().remove();
      }
   }

   public ArrayList<Cell> getCells() {
      return this.cells;
   }

   public ArrayList<Material> getMaterials() {
      return this.materials;
   }

   public String getTeam() {
      return this.team;
   }

   public boolean isSplitBuffered() {
      return this.splitbuffered;
   }

   public void setSplitBuffered(boolean s) {
      this.splitbuffered = s;
   }

   public Cell getPlayerCell() {
      for (Cell cell : this.cells) {
         if (cell.getEntity().equals(this.player)) {
            return cell;
         }
      }

      return null;
   }

   public int getWoolColor() {
      return this.materialdata;
   }

   public boolean getCoolDown() {
      return this.cooldown;
   }

   public void setCoolDown(boolean s) {
      this.cooldown = s;
   }

   public double getTotalMass() {
      double mass = 0.0;

      for (Cell cell : this.cells) {
         mass += cell.getSize();
      }

      return mass;
   }
}
