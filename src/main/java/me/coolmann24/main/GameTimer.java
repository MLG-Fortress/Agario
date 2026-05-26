package me.coolmann24.main;

import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {
   private int time = 0;
   private int latertime = 0;

   public void run() {
      if (this.time == 20) {
         this.time = 0;
         this.latertime++;
         Main.mapmanager.decayCells();
         Main.mapmanager.spawnSmallBlobs();
         Main.mapmanager.spawnRedVirusSmallBlobs();
         Main.mapmanager.returnViruses();
         Main.mapmanager.spawnViruses();
         Main.mapmanager.killCellsOutOfMap();
         if (this.latertime == 2) {
            this.latertime = 0;
            Main.mapmanager.displayMassToPlayers();
         }
      }

      Main.mapmanager.setCellsWalkSpeed();
      Main.mapmanager.cellSplitUpdate();
      Main.mapmanager.updateEating();
      Main.mapmanager.upDateCellRecombine();
      Main.mapmanager.touchVirusUpdate();
      Main.mapmanager.updateCells();
      Main.mapmanager.showPlayerCellsMass();
      Main.mapmanager.updateThrownMass();
      this.time++;
   }
}
