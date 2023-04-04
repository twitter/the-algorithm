// 0x0701B9F0 - 0x0701BAD0
static const Vtx rr_seg7_vertex_0701B9F0[] = {
    {{{    26,    154,      0}, 0, {  1672,   1328}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{   666,    154,    410}, 0, {     0,    990}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{   666,    154,   -613}, 0, {  1672,   -714}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{    26,    154,   -613}, 0, {  2694,    308}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{  -665,    154,    410}, 0, {  2012,   3032}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{  -665,    154,      0}, 0, {  2694,   2350}, {0xa9, 0xcd, 0x97, 0xff}}},
    {{{  -665,      0,      0}, 0, {  2694,   2350}, {0x67, 0x89, 0x51, 0xff}}},
    {{{    26,    154,      0}, 0, {  1672,   1328}, {0x67, 0x89, 0x51, 0xff}}},
    {{{    26,      0,      0}, 0, {  1672,   1328}, {0x67, 0x89, 0x51, 0xff}}},
    {{{  -665,    154,      0}, 0, {  2694,   2350}, {0x67, 0x89, 0x51, 0xff}}},
    {{{   666,      0,    410}, 0, {     0,    990}, {0x67, 0x89, 0x51, 0xff}}},
    {{{  -665,    154,    410}, 0, {  2012,   3032}, {0x67, 0x89, 0x51, 0xff}}},
    {{{  -665,      0,    410}, 0, {  2012,   3032}, {0x67, 0x89, 0x51, 0xff}}},
    {{{   666,    154,    410}, 0, {     0,    990}, {0x67, 0x89, 0x51, 0xff}}},
};

// 0x0701BAD0 - 0x0701BBB0
static const Vtx rr_seg7_vertex_0701BAD0[] = {
    {{{    26,      0,   -613}, 0, {  2694,    308}, {0x67, 0x89, 0x51, 0xff}}},
    {{{   666,    154,   -613}, 0, {  1672,   -714}, {0x67, 0x89, 0x51, 0xff}}},
    {{{   666,      0,   -613}, 0, {  1672,   -714}, {0x67, 0x89, 0x51, 0xff}}},
    {{{    26,    154,   -613}, 0, {  2694,    308}, {0x67, 0x89, 0x51, 0xff}}},
    {{{  -665,      0,    410}, 0, {  2012,   3032}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{  -665,      0,      0}, 0, {  2694,   2350}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{    26,      0,      0}, 0, {  1672,   1328}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{   666,      0,    410}, 0, {     0,    990}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{    26,      0,   -613}, 0, {  2694,    308}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{   666,      0,   -613}, 0, {  1672,   -714}, {0x4d, 0x50, 0x36, 0xff}}},
    {{{    26,      0,      0}, 0, {  1672,   1328}, {0x62, 0x78, 0x60, 0xff}}},
    {{{    26,    154,      0}, 0, {  1672,   1328}, {0x62, 0x78, 0x60, 0xff}}},
    {{{    26,    154,   -613}, 0, {  2694,    308}, {0x62, 0x78, 0x60, 0xff}}},
    {{{    26,      0,   -613}, 0, {  2694,    308}, {0x62, 0x78, 0x60, 0xff}}},
};

// 0x0701BBB0 - 0x0701BC30
static const Vtx rr_seg7_vertex_0701BBB0[] = {
    {{{  -665,      0,    410}, 0, {  2012,   3032}, {0x62, 0x78, 0x60, 0xff}}},
    {{{  -665,    154,    410}, 0, {  2012,   3032}, {0x62, 0x78, 0x60, 0xff}}},
    {{{  -665,    154,      0}, 0, {  2694,   2350}, {0x62, 0x78, 0x60, 0xff}}},
    {{{  -665,      0,      0}, 0, {  2694,   2350}, {0x62, 0x78, 0x60, 0xff}}},
    {{{   666,      0,   -613}, 0, {  1672,   -714}, {0x62, 0x78, 0x60, 0xff}}},
    {{{   666,    154,   -613}, 0, {  1672,   -714}, {0x62, 0x78, 0x60, 0xff}}},
    {{{   666,    154,    410}, 0, {     0,    990}, {0x62, 0x78, 0x60, 0xff}}},
    {{{   666,      0,    410}, 0, {     0,    990}, {0x62, 0x78, 0x60, 0xff}}},
};

// 0x0701BC30 - 0x0701BD08
static const Gfx rr_seg7_dl_0701BC30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701B9F0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPVertex(rr_seg7_vertex_0701BAD0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(rr_seg7_vertex_0701BBB0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701BD08 - 0x0701BD78
const Gfx rr_seg7_dl_0701BD08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701BC30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
