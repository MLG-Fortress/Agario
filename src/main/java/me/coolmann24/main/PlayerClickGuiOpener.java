package me.coolmann24.main;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerClickGuiOpener implements Listener {
   private Main plugin;

   public PlayerClickGuiOpener(Main plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onPlayerClick(PlayerInteractEvent e) {
      if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
         && this.plugin.getConfig().getBoolean("guisettings.useobjectforopening")
         && e.getPlayer()
            .getInventory()
            .getItemInMainHand()
            .getType()
            .equals(Material.getMaterial(this.plugin.getConfig().getString("guisettings.objectforopeningmaterial")))
         && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Agario")) {
         Main.openGameGui(e.getPlayer());
      }
   }
}
