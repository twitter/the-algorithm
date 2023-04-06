// 0x16000040
const GeoLayout explosion_geo[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SWITCH_CASE(9, geo_switch_anim_state),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_03004298),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_03004298),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_030042B0),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_030042B0),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_030042C8),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_030042E0),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_030042F8),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_03004310),
         GEO_DISPLAY_LIST(LAYER_TRANSPARENT, explosion_seg3_dl_03004328),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
