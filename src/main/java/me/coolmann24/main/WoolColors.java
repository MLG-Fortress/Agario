package me.coolmann24.main;

import org.bukkit.Material;

public final class WoolColors {
   private static final Material[] MATERIALS = {
      Material.WHITE_WOOL,
      Material.ORANGE_WOOL,
      Material.MAGENTA_WOOL,
      Material.LIGHT_BLUE_WOOL,
      Material.YELLOW_WOOL,
      Material.LIME_WOOL,
      Material.PINK_WOOL,
      Material.GRAY_WOOL,
      Material.LIGHT_GRAY_WOOL,
      Material.CYAN_WOOL,
      Material.PURPLE_WOOL,
      Material.BLUE_WOOL,
      Material.BROWN_WOOL,
      Material.GREEN_WOOL,
      Material.RED_WOOL,
      Material.BLACK_WOOL
   };

   private WoolColors() {
   }

   public static Material materialFor(int legacyData) {
      return MATERIALS[Math.floorMod(legacyData, MATERIALS.length)];
   }
}
