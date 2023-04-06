// 0x0D000414
const GeoLayout water_ring_geo[] = {
   GEO_SCALE(0x00, 32768),
   GEO_OPEN_NODE(),
      GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
      GEO_OPEN_NODE(),
         GEO_ASM(0, geo_update_layer_transparency),
         GEO_ANIMATED_PART(LAYER_TRANSPARENT, 0, 0, 0, water_ring_seg6_dl_06013AC0),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
