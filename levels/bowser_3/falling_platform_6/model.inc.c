// 0x07003018 - 0x07003030
static const Lights1 bowser_3_seg7_lights_07003018 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07003030 - 0x07003048
static const Lights1 bowser_3_seg7_lights_07003030 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07003048 - 0x070030A8
static const Vtx bowser_3_seg7_vertex_07003048[] = {
    {{{  1183,  -2364,    394}, 0, {  2232,  -2336}, {0x34, 0x90, 0x18, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {  1604,  -4222}, {0x34, 0x90, 0x18, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {   580,  -2652}, {0x34, 0x90, 0x18, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {  1604,  -4222}, {0x3c, 0x99, 0x29, 0xff}}},
    {{{  1946,  -1340,   2662}, 0, {     0,  -3098}, {0x3c, 0x99, 0x29, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {   580,  -2652}, {0x3c, 0x99, 0x29, 0xff}}},
};

// 0x070030A8 - 0x070030E8
static const Vtx bowser_3_seg7_vertex_070030A8[] = {
    {{{  3072,    307,   1024}, 0, {  3034,   -508}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1183,    307,    394}, 0, {  1776,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1498,    307,   2050}, 0, {  1986,    174}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1946,    307,   2662}, 0, {  2284,    582}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070030E8 - 0x070031E8
static const Vtx bowser_3_seg7_vertex_070030E8[] = {
    {{{  1183,  -2364,    394}, 0, {  -996,   2012}, {0x28, 0x00, 0x88, 0xff}}},
    {{{  3072,    307,   1024}, 0, {   990,      0}, {0x28, 0x00, 0x88, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {   990,   2012}, {0x28, 0x00, 0x88, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {  6848,   1816}, {0x68, 0x00, 0x47, 0xff}}},
    {{{  1946,    307,   2662}, 0, {  5340,    172}, {0x68, 0x00, 0x47, 0xff}}},
    {{{  1946,  -1340,   2662}, 0, {  5340,   1816}, {0x68, 0x00, 0x47, 0xff}}},
    {{{  3072,    307,   1024}, 0, {  6848,    172}, {0x68, 0x00, 0x47, 0xff}}},
    {{{  1946,  -1340,   2662}, 0, {   990,   2012}, {0x9a, 0x00, 0x4b, 0xff}}},
    {{{  1946,    307,   2662}, 0, {   990,      0}, {0x9a, 0x00, 0x4b, 0xff}}},
    {{{  1498,    307,   2050}, 0, {   234,      0}, {0x9a, 0x00, 0x4b, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {   234,   2012}, {0x9a, 0x00, 0x4b, 0xff}}},
    {{{  1498,    307,   2050}, 0, {  -690,      0}, {0x84, 0x00, 0x17, 0xff}}},
    {{{  1183,    307,    394}, 0, {   990,      0}, {0x84, 0x00, 0x17, 0xff}}},
    {{{  1498,  -1852,   2050}, 0, {  -690,   2012}, {0x84, 0x00, 0x17, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {   990,   2012}, {0x84, 0x00, 0x17, 0xff}}},
    {{{  1183,    307,    394}, 0, {  -996,      0}, {0x28, 0x00, 0x88, 0xff}}},
};

// 0x070031E8 - 0x07003258
static const Gfx bowser_3_seg7_dl_070031E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07003018.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003018.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07003048, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07003030.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003030.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070030A8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003258 - 0x070032C0
static const Gfx bowser_3_seg7_dl_07003258[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_070030E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070032C0 - 0x07003350
const Gfx bowser_3_seg7_dl_070032C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070031E8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003258),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
