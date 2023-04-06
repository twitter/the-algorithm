// 0x07007798 - 0x07007898
static const Vtx bitfs_seg7_vertex_07007798[] = {
    {{{   102,      0,    205}, 0, {  4558,  -4386}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{    51,    205,     51}, 0, {  4812,  -3874}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   -50,    205,     51}, 0, {  4558,  -3618}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -101,      0,    205}, 0, {  4046,  -3874}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{    51,    205,    -50}, 0, {  5068,  -3618}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   -50,    205,    -50}, 0, {  4812,  -3364}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   -50,    205,     51}, 0, {  4558,  -3618}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{    51,    205,     51}, 0, {  4812,  -3874}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{  -101,      0,    205}, 0, {  4046,  -3874}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   -50,    205,     51}, 0, {  4558,  -3618}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -153,      0,   -153}, 0, {  4812,  -2852}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   -50,    205,    -50}, 0, {  4812,  -3364}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   154,      0,   -153}, 0, {  5580,  -3618}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{    51,    205,     51}, 0, {  4812,  -3874}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   102,      0,    205}, 0, {  4558,  -4386}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{    51,    205,    -50}, 0, {  5068,  -3618}, {0x7d, 0x7d, 0x7d, 0xff}}},
};

// 0x07007898 - 0x070078D8
static const Vtx bitfs_seg7_vertex_07007898[] = {
    {{{  -153,      0,   -153}, 0, {  4812,  -2852}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{    51,    205,    -50}, 0, {  5068,  -3618}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   154,      0,   -153}, 0, {  5580,  -3618}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   -50,    205,    -50}, 0, {  4812,  -3364}, {0x8c, 0x8c, 0x8c, 0xff}}},
};

// 0x070078D8 - 0x07007958
static const Gfx bitfs_seg7_dl_070078D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07007798, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitfs_seg7_vertex_07007898, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007958 - 0x070079C8
const Gfx bitfs_seg7_dl_07007958[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_070078D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
