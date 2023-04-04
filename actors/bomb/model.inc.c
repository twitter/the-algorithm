// Bomb

// 0x06057AA8
static const Lights1 bomb_seg6_lights_06057AA8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06057AC0
ALIGNED8 static const Texture bomb_seg6_texture_06057AC0[] = {
#include "actors/bomb/bomb_left_side.rgba16.inc.c"
};

// 0x06058AC0
ALIGNED8 static const Texture bomb_seg6_texture_06058AC0[] = {
#include "actors/bomb/bomb_right_side.rgba16.inc.c"
};

// 0x06059AC0
ALIGNED8 static const Texture bomb_seg6_texture_06059AC0[] = {
#include "actors/bomb/bomb_spike.rgba16.inc.c"
};

// 0x0605A2C0
static const Vtx bomb_seg6_vertex_0605A2C0[] = {
    {{{     0,    120,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -120,   -120,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -120,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -120,    120,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0605A300
static const Vtx bomb_seg6_vertex_0605A300[] = {
    {{{   120,    120,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -120,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   120,   -120,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    120,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0605A340
static const Vtx bomb_seg6_vertex_0605A340[] = {
    {{{     0,    -59,      0}, 0, {   502,    -18}, {0xf7, 0x82, 0xf7, 0xff}}},
    {{{    -9,    -36,      0}, 0, {    14,    976}, {0x8a, 0xd2, 0xfd, 0xff}}},
    {{{     0,    -36,     -8}, 0, {   938,    980}, {0xfd, 0xd7, 0x89, 0xff}}},
    {{{     0,     37,     -8}, 0, {    -6,    974}, {0xfd, 0x29, 0x89, 0xff}}},
    {{{    -9,     37,      0}, 0, {   972,    964}, {0x8a, 0x2e, 0xfd, 0xff}}},
    {{{     0,     60,      0}, 0, {   434,    -20}, {0xf7, 0x7e, 0xf7, 0xff}}},
    {{{    10,     37,      0}, 0, {   -14,    962}, {0x74, 0x32, 0xfd, 0xff}}},
    {{{     0,     37,     -8}, 0, {   960,    962}, {0xfd, 0x29, 0x89, 0xff}}},
    {{{     0,     60,      0}, 0, {   468,    -16}, {0xf7, 0x7e, 0xf7, 0xff}}},
    {{{    -9,     37,      0}, 0, {    -8,    974}, {0x8a, 0x2e, 0xfd, 0xff}}},
    {{{     0,     37,      9}, 0, {   974,    968}, {0xfd, 0x2e, 0x76, 0xff}}},
    {{{     0,     60,      0}, 0, {   470,     -6}, {0xf7, 0x7e, 0xf7, 0xff}}},
    {{{     0,     37,      9}, 0, {     4,    974}, {0xfd, 0x2e, 0x76, 0xff}}},
    {{{    10,     37,      0}, 0, {   960,    986}, {0x74, 0x32, 0xfd, 0xff}}},
    {{{     0,     60,      0}, 0, {   492,     34}, {0xf7, 0x7e, 0xf7, 0xff}}},
};

// 0x0605A430
static const Vtx bomb_seg6_vertex_0605A430[] = {
    {{{     0,     10,     37}, 0, {   -20,    976}, {0xfd, 0x74, 0x32, 0xff}}},
    {{{    -9,      0,     37}, 0, {   974,    974}, {0x8a, 0xf9, 0x2e, 0xff}}},
    {{{     0,      0,     60}, 0, {   428,      4}, {0xf8, 0xee, 0x7d, 0xff}}},
    {{{     0,    -59,      0}, 0, {   494,    -12}, {0xf7, 0x82, 0xf7, 0xff}}},
    {{{     0,    -36,     -8}, 0, {    -2,    946}, {0xfd, 0xd7, 0x89, 0xff}}},
    {{{    10,    -36,      0}, 0, {   964,    964}, {0x74, 0xce, 0xfd, 0xff}}},
    {{{     0,    -59,      0}, 0, {   458,    -18}, {0xf7, 0x82, 0xf7, 0xff}}},
    {{{     0,    -36,      9}, 0, {    10,    944}, {0xfd, 0xd2, 0x76, 0xff}}},
    {{{    -9,    -36,      0}, 0, {   916,    956}, {0x8a, 0xd2, 0xfd, 0xff}}},
    {{{     0,    -59,      0}, 0, {   486,     -6}, {0xf7, 0x82, 0xf7, 0xff}}},
    {{{    10,    -36,      0}, 0, {    -4,    958}, {0x74, 0xce, 0xfd, 0xff}}},
    {{{     0,    -36,      9}, 0, {   958,    952}, {0xfd, 0xd2, 0x76, 0xff}}},
    {{{    10,      0,     37}, 0, {    -8,    978}, {0x74, 0xf9, 0x32, 0xff}}},
    {{{     0,     10,     37}, 0, {   952,    976}, {0xfd, 0x74, 0x32, 0xff}}},
    {{{     0,      0,     60}, 0, {   428,    -12}, {0xf8, 0xee, 0x7d, 0xff}}},
};

// 0x0605A520
static const Vtx bomb_seg6_vertex_0605A520[] = {
    {{{    37,      0,     -9}, 0, {    10,    960}, {0x2e, 0xf9, 0x8a, 0xff}}},
    {{{    37,     10,      0}, 0, {   928,    972}, {0x32, 0x74, 0xfd, 0xff}}},
    {{{    60,      0,      0}, 0, {   494,     -4}, {0x7d, 0xee, 0xf8, 0xff}}},
    {{{     0,     -8,     37}, 0, {   -10,    976}, {0xfd, 0x89, 0x29, 0xff}}},
    {{{    10,      0,     37}, 0, {   956,    980}, {0x74, 0xf9, 0x32, 0xff}}},
    {{{     0,      0,     60}, 0, {   428,    -12}, {0xf8, 0xee, 0x7d, 0xff}}},
    {{{    -9,      0,     37}, 0, {   -24,    978}, {0x8a, 0xf9, 0x2e, 0xff}}},
    {{{     0,     -8,     37}, 0, {   974,    976}, {0xfd, 0x89, 0x29, 0xff}}},
    {{{     0,      0,     60}, 0, {   476,     -6}, {0xf8, 0xee, 0x7d, 0xff}}},
    {{{    37,     -8,      0}, 0, {   -20,    968}, {0x29, 0x89, 0xfd, 0xff}}},
    {{{    37,      0,     -9}, 0, {   954,    972}, {0x2e, 0xf9, 0x8a, 0xff}}},
    {{{    60,      0,      0}, 0, {   402,    -10}, {0x7d, 0xee, 0xf8, 0xff}}},
    {{{    37,     10,      0}, 0, {    -8,    964}, {0x32, 0x74, 0xfd, 0xff}}},
    {{{    37,      0,     10}, 0, {   978,    962}, {0x32, 0xf9, 0x74, 0xff}}},
    {{{    60,      0,      0}, 0, {   462,    -18}, {0x7d, 0xee, 0xf8, 0xff}}},
};

// 0x0605A610
static const Vtx bomb_seg6_vertex_0605A610[] = {
    {{{   -59,      0,      0}, 0, {   452,     20}, {0x83, 0xee, 0xf8, 0xff}}},
    {{{   -36,      0,     -9}, 0, {    -4,    974}, {0xd2, 0xf9, 0x8a, 0xff}}},
    {{{   -36,     -8,      0}, 0, {   948,    980}, {0xd7, 0x89, 0xfd, 0xff}}},
    {{{    37,      0,     10}, 0, {   -10,    970}, {0x32, 0xf9, 0x74, 0xff}}},
    {{{    37,     -8,      0}, 0, {   960,    966}, {0x29, 0x89, 0xfd, 0xff}}},
    {{{    60,      0,      0}, 0, {   488,     -8}, {0x7d, 0xee, 0xf8, 0xff}}},
    {{{   -59,      0,      0}, 0, {   370,     10}, {0x83, 0xee, 0xf8, 0xff}}},
    {{{   -36,      0,     10}, 0, {    -6,    964}, {0xce, 0xf9, 0x74, 0xff}}},
    {{{   -36,     10,      0}, 0, {   950,    968}, {0xce, 0x74, 0xfd, 0xff}}},
    {{{   -59,      0,      0}, 0, {   428,     12}, {0x83, 0xee, 0xf8, 0xff}}},
    {{{   -36,     10,      0}, 0, {     8,    968}, {0xce, 0x74, 0xfd, 0xff}}},
    {{{   -36,      0,     -9}, 0, {   938,    974}, {0xd2, 0xf9, 0x8a, 0xff}}},
    {{{   -59,      0,      0}, 0, {   464,    -18}, {0x83, 0xee, 0xf8, 0xff}}},
    {{{   -36,     -8,      0}, 0, {    -4,    966}, {0xd7, 0x89, 0xfd, 0xff}}},
    {{{   -36,      0,     10}, 0, {   964,    974}, {0xce, 0xf9, 0x74, 0xff}}},
};

// 0x0605A700
static const Vtx bomb_seg6_vertex_0605A700[] = {
    {{{     0,      0,    -59}, 0, {   464,      4}, {0xf8, 0xee, 0x83, 0xff}}},
    {{{     0,     10,    -36}, 0, {   -14,    968}, {0xfd, 0x74, 0xce, 0xff}}},
    {{{    10,      0,    -36}, 0, {   942,    976}, {0x74, 0xf9, 0xce, 0xff}}},
    {{{     0,      0,    -59}, 0, {   480,    -20}, {0xf8, 0xee, 0x83, 0xff}}},
    {{{    -9,      0,    -36}, 0, {    20,    962}, {0x8a, 0xf9, 0xd2, 0xff}}},
    {{{     0,     10,    -36}, 0, {   946,    966}, {0xfd, 0x74, 0xce, 0xff}}},
    {{{     0,      0,    -59}, 0, {   412,      6}, {0xf8, 0xee, 0x83, 0xff}}},
    {{{    10,      0,    -36}, 0, {   -16,    970}, {0x74, 0xf9, 0xce, 0xff}}},
    {{{     0,     -8,    -36}, 0, {   968,    968}, {0xfd, 0x89, 0xd7, 0xff}}},
    {{{     0,      0,    -59}, 0, {   402,      8}, {0xf8, 0xee, 0x83, 0xff}}},
    {{{     0,     -8,    -36}, 0, {     0,    970}, {0xfd, 0x89, 0xd7, 0xff}}},
    {{{    -9,      0,    -36}, 0, {   952,    964}, {0x8a, 0xf9, 0xd2, 0xff}}},
};

// 0x0605A7C0 - 0x0605A7F8
const Gfx bomb_seg6_dl_0605A7C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bomb_seg6_texture_06057AC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bomb_seg6_vertex_0605A2C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0605A7F8 - 0x0605A830
const Gfx bomb_seg6_dl_0605A7F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bomb_seg6_texture_06058AC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bomb_seg6_vertex_0605A300, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0605A830 - 0x0605A8A8
const Gfx bomb_seg6_dl_0605A830[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bomb_seg6_dl_0605A7C0),
    gsSPDisplayList(bomb_seg6_dl_0605A7F8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0605A8A8 - 0x0605A9C0
const Gfx bomb_seg6_dl_0605A8A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bomb_seg6_texture_06059AC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bomb_seg6_lights_06057AA8.l, 1),
    gsSPLight(&bomb_seg6_lights_06057AA8.a, 2),
    gsSPVertex(bomb_seg6_vertex_0605A340, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bomb_seg6_vertex_0605A430, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bomb_seg6_vertex_0605A520, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bomb_seg6_vertex_0605A610, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bomb_seg6_vertex_0605A700, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0605A9C0 - 0x0605AA20
const Gfx bomb_seg6_dl_0605A9C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bomb_seg6_dl_0605A8A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
