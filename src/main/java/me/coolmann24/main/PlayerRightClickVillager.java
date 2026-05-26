package me.coolmann24.main;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerRightClickVillager implements Listener {
   @EventHandler
   public void onPlayerClick(PlayerInteractEntityEvent event) {
      if (Main.mapmanager.isPlayerPlaying(event.getPlayer()) && event.getRightClicked() instanceof Villager) {
         event.setCancelled(true);
      }
   }
}
