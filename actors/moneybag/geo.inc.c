// 0x0D000000
const GeoLayout moneybag_geo_000000[] = {
   GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
   GEO_OPEN_NODE(),
      GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, moneybag_seg6_dl_06005750),
      GEO_OPEN_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, -120, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, moneybag_seg6_dl_06005980),
         GEO_CLOSE_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 120, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, moneybag_seg6_dl_060059F0),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, moneybag_seg6_dl_06005688),
   GEO_CLOSE_NODE(),
   GEO_RETURN(),
};

// 0x0D000078
const GeoLayout moneybag_geo_000078[] = {
   GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
   GEO_OPEN_NODE(),
      GEO_ANIMATED_PART(LAYER_TRANSPARENT, 0, 0, 0, moneybag_seg6_dl_06005750),
      GEO_OPEN_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, -120, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_TRANSPARENT, 0, 0, 0, moneybag_seg6_dl_06005980),
         GEO_CLOSE_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 120, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_TRANSPARENT, 0, 0, 0, moneybag_seg6_dl_060059F0),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ANIMATED_PART(LAYER_TRANSPARENT, 0, 0, 0, moneybag_seg6_dl_06005688),
   GEO_CLOSE_NODE(),
   GEO_RETURN(),
};

// 0x0D0000F0
const GeoLayout moneybag_geo[] = {
   GEO_SHADOW(SHADOW_CIRCLE_4_VERTS, 0xC8, 100),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 16384),
      GEO_OPEN_NODE(),
         GEO_ASM(0, geo_update_layer_transparency),
         GEO_SWITCH_CASE(2, geo_switch_anim_state),
         GEO_OPEN_NODE(),
            GEO_BRANCH(1, moneybag_geo_000000),
            GEO_BRANCH(1, moneybag_geo_000078),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
GEO_CLOSE_NODE(), //! more close than open nodes
GEO_END(),
};

UNUSED static const u64 moneybag_unused_1 = 0;
