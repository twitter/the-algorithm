// 0x0E0005D8
const GeoLayout ssl_geo_0005D8[] = {
   GEO_CULLING_RADIUS(2000),
   GEO_OPEN_NODE(),
      GEO_RENDER_RANGE(-1000, 4000),
      GEO_OPEN_NODE(),
         GEO_ASM(0x802, geo_movtex_draw_colored_2_no_update),
      GEO_CLOSE_NODE(),
      GEO_RENDER_RANGE(4000, 12800),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, ssl_dl_pyramid_quicksand_pit_static),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
