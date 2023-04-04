// Water Mine (unused)

// 0x0600A4E0
static const Lights1 water_mine_seg6_lights_0600A4E0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600A4F8
ALIGNED8 static const Texture water_mine_seg6_texture_0600A4F8[] = {
#include "actors/water_mine/water_mine_left_side_unused.rgba16.inc.c"
};

// 0x0600B4F8
ALIGNED8 static const Texture water_mine_seg6_texture_0600B4F8[] = {
#include "actors/water_mine/water_mine_right_side_unused.rgba16.inc.c"
};

// 0x0600C4F8
ALIGNED8 static const Texture water_mine_seg6_texture_0600C4F8[] = {
#include "actors/water_mine/water_mine_spike_unused.rgba16.inc.c"
};

// 0x0600CCF8
static const Vtx water_mine_seg6_vertex_0600CCF8[] = {
    {{{     0,     40,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -39,    -39,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -39,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -39,     40,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0600CD38
static const Vtx water_mine_seg6_vertex_0600CD38[] = {
    {{{    40,     40,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -39,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,    -39,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     40,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0600CD78
static const Vtx water_mine_seg6_vertex_0600CD78[] = {
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

// 0x0600CE68
static const Vtx water_mine_seg6_vertex_0600CE68[] = {
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

// 0x0600CF58
static const Vtx water_mine_seg6_vertex_0600CF58[] = {
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

// 0x0600D048
static const Vtx water_mine_seg6_vertex_0600D048[] = {
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

// 0x0600D138
static const Vtx water_mine_seg6_vertex_0600D138[] = {
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

// 0x0600D1F8 - 0x0600D230
const Gfx water_mine_seg6_dl_0600D1F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_mine_seg6_texture_0600A4F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(water_mine_seg6_vertex_0600CCF8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D230 - 0x0600D268
const Gfx water_mine_seg6_dl_0600D230[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_mine_seg6_texture_0600B4F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(water_mine_seg6_vertex_0600CD38, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D268 - 0x0600D2E0
const Gfx water_mine_seg6_dl_0600D268[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(water_mine_seg6_dl_0600D1F8),
    gsSPDisplayList(water_mine_seg6_dl_0600D230),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0600D2E0 - 0x0600D3F8
const Gfx water_mine_seg6_dl_0600D2E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_mine_seg6_texture_0600C4F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&water_mine_seg6_lights_0600A4E0.l, 1),
    gsSPLight(&water_mine_seg6_lights_0600A4E0.a, 2),
    gsSPVertex(water_mine_seg6_vertex_0600CD78, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(water_mine_seg6_vertex_0600CE68, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(water_mine_seg6_vertex_0600CF58, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(water_mine_seg6_vertex_0600D048, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(water_mine_seg6_vertex_0600D138, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D3F8 - 0x0600D458
const Gfx water_mine_seg6_dl_0600D3F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(water_mine_seg6_dl_0600D2E0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
