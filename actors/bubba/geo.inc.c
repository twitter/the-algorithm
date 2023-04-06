// 0x0C000000
const GeoLayout bubba_geo[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SWITCH_CASE(2, geo_switch_anim_state),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, bubba_seg5_dl_05004E80),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, bubba_seg5_dl_05005978),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
