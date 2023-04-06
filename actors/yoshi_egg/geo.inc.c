// 0x0C0001E4
const GeoLayout yoshi_egg_geo[] = {
   GEO_SCALE(0x00, 16384),
   GEO_OPEN_NODE(),
      GEO_SWITCH_CASE(8, geo_switch_anim_state),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_050098C8),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_050098B0),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009898),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009880),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009868),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009850),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009838),
         GEO_DISPLAY_LIST(LAYER_ALPHA, yoshi_egg_seg5_dl_05009820),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
