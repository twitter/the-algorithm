// 0x0C000274
const GeoLayout haunted_cage_geo[] = {
   GEO_CULLING_RADIUS(300),
   GEO_OPEN_NODE(),
      GEO_SHADOW(SHADOW_CIRCLE_9_VERTS, 0x96, 100),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, haunted_cage_seg5_dl_0500F7D8),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, haunted_cage_seg5_dl_0500FC28),
         GEO_DISPLAY_LIST(LAYER_ALPHA, haunted_cage_seg5_dl_05010100),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
