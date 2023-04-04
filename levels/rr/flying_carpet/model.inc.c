// Not a traditional model file. The vertexes are generated in src/geo_misc.c.

// 0x07019230 - 0x07019248
static const Lights1 rr_seg7_lights_07019230 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07019248 - 0x070192F0
const s16 flying_carpet_static_vertex_data[] = {
//     x     z    tx    ty
    -306,  205,    0,    0,
    -306,    0,  992,    0,
    -306, -204,    0,    0,
    -204,  205,    0,  308,
    -204,    0,  992,  308,
    -204, -204,    0,  308,
    -102,  205,    0,  650,
    -102,    0,  992,  650,
    -102, -204,    0,  650,
       0,  205,    0,  992,
       0,    0,  992,  992,
       0, -204,    0,  992,
     102,  205,    0,  650,
     102,    0,  992,  650,
     102, -204,    0,  650,
     205,  205,    0,  308,
     205,    0,  992,  308,
     205, -204,    0,  308,
     307,  205,    0,    0,
     307,    0,  990,    0,
     307, -204,    0,    0,
};

// 0x070192F0 - 0x07019360
const Gfx dl_flying_carpet_begin[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_quarter_flying_carpet),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&rr_seg7_lights_07019230.l, 1),
    gsSPLight(&rr_seg7_lights_07019230.a, 2),
    gsSPEndDisplayList(),
};

// 0x07019360 - 0x070193C8
const Gfx dl_flying_carpet_model_half[] = {
    gsSP2Triangles( 0,  1,  3, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 1,  2,  4, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 3,  4,  6, 0x0,  6,  4,  7, 0x0),
    gsSP2Triangles( 4,  5,  7, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 6,  7,  9, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles( 7,  8, 10, 0x0, 10,  8, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x070193C8 - 0x070193F0
const Gfx dl_flying_carpet_end[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};
