// 0x17000124
const GeoLayout idle_water_wave_geo[] = {
   GEO_SWITCH_CASE(6, geo_switch_anim_state),
   GEO_OPEN_NODE(),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_040273F0),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027408),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027420),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027420),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027408),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x17000168
const GeoLayout wave_trail_geo[] = {
   GEO_SWITCH_CASE(8, geo_switch_anim_state),
   GEO_OPEN_NODE(),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_040273F0),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027408),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027420),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, water_wave_seg4_dl_04027438),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
