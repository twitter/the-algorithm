// 0x0D000B78 / 0BBC
const GeoLayout bowser_bomb_geo[] = {
   GEO_CULLING_RADIUS(1000),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 196608),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, bomb_seg6_dl_0605A9C0),
         GEO_BILLBOARD(),
         GEO_OPEN_NODE(),
            GEO_DISPLAY_LIST(LAYER_ALPHA, bomb_seg6_dl_0605A830),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
