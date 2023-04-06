// 0x07003350 - 0x07003368
static const Lights1 bowser_3_seg7_lights_07003350 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07003368 - 0x07003380
static const Lights1 bowser_3_seg7_lights_07003368 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07003380 - 0x070033E0
static const Vtx bowser_3_seg7_vertex_07003380[] = {
    {{{  3072,  -1340,   1024}, 0, {  1604,  -4222}, {0x39, 0x90, 0x09, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  2232,  -2336}, {0x39, 0x90, 0x09, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {  3412,  -3518}, {0x39, 0x90, 0x09, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {  1604,  -4222}, {0x4a, 0x9a, 0x00, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {  3412,  -3518}, {0x4a, 0x9a, 0x00, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {  3648,  -4222}, {0x4a, 0x9a, 0x00, 0xff}}},
};

// 0x070033E0 - 0x07003420
static const Vtx bowser_3_seg7_vertex_070033E0[] = {
    {{{  3072,    307,   1024}, 0, {  3034,   -508}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2365,    307,   -787}, 0, {  2564,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1183,    307,    394}, 0, {  1776,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {  3034,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07003420 - 0x07003520
static const Vtx bowser_3_seg7_vertex_07003420[] = {
    {{{  3072,  -1340,   1024}, 0, {   990,   2012}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{  1183,    307,    394}, 0, {  -996,      0}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  -996,   2012}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   246,      0}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {   990,      0}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {   990,   2012}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   246,   2012}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{  1183,  -2364,    394}, 0, {  -678,   2012}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   990,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   990,   2012}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  1183,    307,    394}, 0, {  -678,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {  8418,   1816}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  3072,    307,   1024}, 0, {  6848,    172}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  3072,  -1340,   1024}, 0, {  6848,   1816}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {  8418,    172}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  3072,    307,   1024}, 0, {   990,      0}, {0xd8, 0x00, 0x78, 0xff}}},
};

// 0x07003520 - 0x07003590
static const Gfx bowser_3_seg7_dl_07003520[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07003350.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003350.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07003380, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07003368.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003368.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070033E0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003590 - 0x070035F8
static const Gfx bowser_3_seg7_dl_07003590[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07003420, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070035F8 - 0x07003688
const Gfx bowser_3_seg7_dl_070035F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003520),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003590),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
