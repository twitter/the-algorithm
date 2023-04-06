// 0x16000F98
const GeoLayout white_particle_geo[] = {
   GEO_SHADOW(SHADOW_CIRCLE_4_VERTS, 0xB4, 50),
   GEO_OPEN_NODE(),
      GEO_DISPLAY_LIST(LAYER_ALPHA, white_particle_dl),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
