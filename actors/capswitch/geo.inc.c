// 0x0C000048
const GeoLayout cap_switch_geo[] = {
   GEO_CULLING_RADIUS(600),
   GEO_OPEN_NODE(),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT_DECAL, cap_switch_exclamation_seg5_dl_05002E00),
      GEO_SWITCH_CASE(4, geo_switch_anim_state),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, capswitch_seg5_dl_05003350),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, capswitch_seg5_dl_05003370),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, capswitch_seg5_dl_05003390),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, capswitch_seg5_dl_050033B0),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
