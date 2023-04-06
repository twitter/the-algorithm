// 0x0C000308
const GeoLayout water_bomb_geo[] = {
   GEO_CULLING_RADIUS(150),
   GEO_OPEN_NODE(),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_bubble_seg5_dl_05011000),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0C000328
const GeoLayout water_bomb_shadow_geo[] = {
   GEO_CULLING_RADIUS(150),
   GEO_OPEN_NODE(),
      GEO_SHADOW(SHADOW_CIRCLE_4_VERTS, 0x64, 160),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
