// 0x0C0001B4
const GeoLayout mad_piano_geo[] = {
   GEO_SHADOW(SHADOW_CIRCLE_4_VERTS, 0x96, 100),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 16384),
      GEO_OPEN_NODE(),
         GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, NULL),
         GEO_OPEN_NODE(),
            GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, mad_piano_seg5_dl_050098E8),
            GEO_OPEN_NODE(),
               GEO_ANIMATED_PART(LAYER_OPAQUE, -141, -546, 218, NULL),
               GEO_OPEN_NODE(),
                  GEO_ANIMATED_PART(LAYER_OPAQUE, 0, 0, 0, mad_piano_seg5_dl_05008CB0),
               GEO_CLOSE_NODE(),
            GEO_CLOSE_NODE(),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
GEO_CLOSE_NODE(), //! more close than open nodes
GEO_END(),
};
