package me.coolmann24.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerJoin implements Listener {
   private Main plugin;

   public PlayerJoin(Main plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent e) {
      if (this.plugin.getConfig().getBoolean("guisettings.addguiopenitemtoinventory")) {
         ItemStack is = new ItemStack(Material.getMaterial(this.plugin.getConfig().getString("guisettings.objectforopeningmaterial")));
         ItemMeta s = is.getItemMeta();
         s.setDisplayName(ChatColor.AQUA + "Agario");
         is.setItemMeta(s);
         e.getPlayer().getInventory().addItem(new ItemStack[]{is});
      }
   }
}
