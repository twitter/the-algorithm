// 0x0F000A5A
const GeoLayout exclamation_box_outline_geo[] = {
   GEO_CULLING_RADIUS(300),
   GEO_OPEN_NODE(),
      GEO_SHADOW(SHADOW_SQUARE_PERMANENT, 0xB4, 70),
      GEO_OPEN_NODE(),
         GEO_SWITCH_CASE(4, geo_switch_anim_state),
         GEO_OPEN_NODE(),
            GEO_DISPLAY_LIST(LAYER_TRANSPARENT, exclamation_box_outline_seg8_dl_08024F88),
            GEO_DISPLAY_LIST(LAYER_TRANSPARENT, exclamation_box_outline_seg8_dl_08024FA8),
            GEO_DISPLAY_LIST(LAYER_TRANSPARENT, exclamation_box_outline_seg8_dl_08024FC8),
            GEO_DISPLAY_LIST(LAYER_TRANSPARENT, exclamation_box_outline_seg8_dl_08024FE8),
         GEO_CLOSE_NODE(),
         GEO_DISPLAY_LIST(LAYER_ALPHA, exclamation_box_outline_seg8_dl_080259F8),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
