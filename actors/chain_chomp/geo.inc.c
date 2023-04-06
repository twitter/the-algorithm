// 0x0D0005EC
const GeoLayout chain_chomp_geo[] = {
   GEO_SHADOW(SHADOW_CIRCLE_4_VERTS, 0x96, 200),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 16384),
      GEO_OPEN_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, chain_chomp_seg6_dl_06024940),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, chain_chomp_seg6_dl_06024FC0),
         GEO_CLOSE_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, chain_chomp_seg6_dl_06024240),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, chain_chomp_seg6_dl_06024D60),
            GEO_ANIMATED_PART(LAYER_ALPHA, 0, 0, 0, chain_chomp_seg6_dl_06024B00),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
GEO_CLOSE_NODE(), //! more close than open nodes
GEO_END(),
};
