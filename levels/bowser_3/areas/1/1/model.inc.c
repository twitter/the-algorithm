// 0x07004030 - 0x07004048
static const Lights1 bowser_3_seg7_lights_07004030 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07004048 - 0x07004060
static const Lights1 bowser_3_seg7_lights_07004048 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07004060 - 0x07004160
static const Vtx bowser_3_seg7_vertex_07004060[] = {
    {{{ -1182,  -2364,    394}, 0, {  2232,     24}, {0xd8, 0x89, 0xf5, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  3412,   1202}, {0xd8, 0x89, 0xf5, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  3648,   -370}, {0xd8, 0x89, 0xf5, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  2232,     24}, {0xe9, 0x88, 0x20, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {  1366,  -1156}, {0xe9, 0x88, 0x20, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   580,    338}, {0xe9, 0x88, 0x20, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {  1366,  -1156}, {0x00, 0x81, 0x00, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  2232,     24}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  3648,   -370}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  3648,   -370}, {0x00, 0x88, 0xd7, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {  5144,  -1156}, {0x00, 0x88, 0xd7, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  3648,  -1944}, {0x00, 0x88, 0xd7, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  3648,  -1944}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {   580,  -2652}, {0x17, 0x88, 0x20, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {  1366,  -1156}, {0x17, 0x88, 0x20, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  2232,  -2336}, {0x17, 0x88, 0x20, 0xff}}},
};

// 0x07004160 - 0x070041C0
static const Vtx bowser_3_seg7_vertex_07004160[] = {
    {{{     0,  -2364,   1262}, 0, {  1366,  -1156}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  3648,  -1944}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  2232,  -2336}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  2232,  -2336}, {0x28, 0x89, 0xf5, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  3648,  -1944}, {0x28, 0x89, 0xf5, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {  3412,  -3518}, {0x28, 0x89, 0xf5, 0xff}}},
};

// 0x070041C0 - 0x07004260
static const Vtx bowser_3_seg7_vertex_070041C0[] = {
    {{{ -2364,    307,   -787}, 0, {  -582,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1182,    307,    394}, 0, {   204,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {   466,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,   1262}, 0, {   990,   -350}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {    -6,    174}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1183,    307,    394}, 0, {  1776,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   788,    307,  -1024}, 0, {  1514,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   990,  -2870}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1498,    307,   2050}, 0, {  1986,    174}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2365,    307,   -787}, 0, {  2564,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07004260 - 0x07004350
static const Vtx bowser_3_seg7_vertex_07004260[] = {
    {{{     0,  -1852,  -2522}, 0, {   990,   2012}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   990,      0}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  -698,   2012}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   990,   2012}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  1183,    307,    394}, 0, {  -678,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  -678,   2012}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   990,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  -690,   2012}, {0x7c, 0x00, 0xe9, 0xff}}},
    {{{  1498,    307,   2050}, 0, {   990,      0}, {0x7c, 0x00, 0xe9, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {   990,   2012}, {0x7c, 0x00, 0xe9, 0xff}}},
    {{{  1183,    307,    394}, 0, {  -690,      0}, {0x7c, 0x00, 0xe9, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  -698,   2012}, {0x90, 0x00, 0xc5, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {  -698,      0}, {0x90, 0x00, 0xc5, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   990,      0}, {0x90, 0x00, 0xc5, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {   990,   2012}, {0x90, 0x00, 0xc5, 0xff}}},
};

// 0x07004350 - 0x07004440
static const Vtx bowser_3_seg7_vertex_07004350[] = {
    {{{  1498,  -1852,   2050}, 0, {   990,   2012}, {0xc5, 0x00, 0x70, 0xff}}},
    {{{  1498,    307,   2050}, 0, {   990,      0}, {0xc5, 0x00, 0x70, 0xff}}},
    {{{     0,    307,   1262}, 0, {  -698,      0}, {0xc5, 0x00, 0x70, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   990,      0}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{   788,    307,  -1024}, 0, {  -698,      0}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  -698,   2012}, {0x70, 0x00, 0xc5, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {   990,      0}, {0xee, 0x00, 0x83, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {  -600,      0}, {0xee, 0x00, 0x83, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  -600,   2012}, {0xee, 0x00, 0x83, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {   990,   2012}, {0xee, 0x00, 0x83, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  -600,   2012}, {0x12, 0x00, 0x83, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   990,      0}, {0x12, 0x00, 0x83, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   990,   2012}, {0x12, 0x00, 0x83, 0xff}}},
    {{{   788,    307,  -1024}, 0, {  -600,      0}, {0x12, 0x00, 0x83, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {  -698,   2012}, {0xc5, 0x00, 0x70, 0xff}}},
};

// 0x07004440 - 0x07004500
static const Vtx bowser_3_seg7_vertex_07004440[] = {
    {{{     0,  -2364,   1262}, 0, {  -698,   2012}, {0x3b, 0x00, 0x70, 0xff}}},
    {{{     0,    307,   1262}, 0, {  -698,      0}, {0x3b, 0x00, 0x70, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {   990,      0}, {0x3b, 0x00, 0x70, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   990,   2012}, {0x3b, 0x00, 0x70, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   990,   2012}, {0x84, 0x00, 0xe9, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {   990,      0}, {0x84, 0x00, 0xe9, 0xff}}},
    {{{ -1182,    307,    394}, 0, {  -690,      0}, {0x84, 0x00, 0xe9, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  -690,   2012}, {0x84, 0x00, 0xe9, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  -678,   2012}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {   990,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {   990,   2012}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{ -1182,    307,    394}, 0, {  -678,      0}, {0xa7, 0x00, 0x59, 0xff}}},
};

// 0x07004500 - 0x070045D8
static const Gfx bowser_3_seg7_dl_07004500[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07004030.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07004030.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07004060, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 6,  8, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_3_seg7_vertex_07004160, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07004048.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07004048.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070041C0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  2,  3,  5, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 3,  8,  5, 0x0,  5,  9,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x070045D8 - 0x070046B0
static const Gfx bowser_3_seg7_dl_070045D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07004260, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(bowser_3_seg7_vertex_07004350, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  6,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(bowser_3_seg7_vertex_07004440, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x070046B0 - 0x07004740
const Gfx bowser_3_seg7_dl_070046B0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07004500),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070045D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
