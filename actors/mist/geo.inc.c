// 0x16000000
const GeoLayout mist_geo[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_ASM(0, geo_update_layer_transparency),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, mist_seg3_dl_03000880),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x16000020
const GeoLayout white_puff_geo[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_ASM(0, geo_update_layer_transparency),
      GEO_DISPLAY_LIST(LAYER_TRANSPARENT, mist_seg3_dl_03000920),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
