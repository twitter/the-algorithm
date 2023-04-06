// 0x07031608 - 0x070316C8
static const Vtx inside_castle_seg7_vertex_07031608[] = {
    {{{  2001,   1024,  -2688}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1857,   1024,  -2833}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2001,    691,  -2688}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1857,    691,  -2833}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1857,   1024,  -2037}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2001,   1024,  -2182}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1857,    691,  -2037}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2001,    691,  -2182}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1205,    691,  -2688}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1350,   1024,  -2833}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1205,   1024,  -2688}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1350,    691,  -2833}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070316C8 - 0x07031720
static const Gfx inside_castle_seg7_dl_070316C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_07031608, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07031720 - 0x07031790
const Gfx inside_castle_seg7_dl_07031720[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_070316C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
