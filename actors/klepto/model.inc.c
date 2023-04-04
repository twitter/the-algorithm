// Possible Removed Actor File

// Bin ID? What is this?
UNUSED static const u64 klepto_unused_1 = 0;

// Klepto

// 0x05000008
ALIGNED8 static const Texture klepto_seg5_texture_05000008[] = {
#include "actors/klepto/klepto_chest_tuft.rgba16.inc.c"
};

// 0x05000808
ALIGNED8 static const Texture klepto_seg5_texture_05000808[] = {
#include "actors/klepto/klepto_eye.rgba16.inc.c"
};

// 0x05001008
ALIGNED8 static const Texture klepto_seg5_texture_05001008[] = {
#include "actors/klepto/klepto_beak.rgba16.inc.c"
};

// 0x05002008
ALIGNED8 static const Texture klepto_seg5_texture_05002008[] = {
#include "actors/klepto/klepto_wing.rgba16.inc.c"
};

// 0x05003008
ALIGNED8 static const Texture klepto_seg5_texture_05003008[] = {
#include "actors/klepto/klepto_wing_flap.rgba16.inc.c"
};

// 0x05003808
static const Lights1 klepto_seg5_lights_05003808 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003820
static const Lights1 klepto_seg5_lights_05003820 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05003838
static const Vtx klepto_seg5_vertex_05003838[] = {
    {{{    14,    -62,     52}, 0, {  1058,   -314}, {0x0e, 0xc0, 0x6c, 0xff}}},
    {{{    65,    -69,      0}, 0, {    80,   -454}, {0x56, 0xa3, 0x00, 0xff}}},
    {{{    76,    -43,      0}, 0, {   -36,    532}, {0x5b, 0xa9, 0x00, 0xff}}},
    {{{    26,     -6,     56}, 0, {   942,   1784}, {0x0e, 0x06, 0x7e, 0xff}}},
    {{{    14,    -62,    -51}, 0, {  1058,   -314}, {0x0e, 0xc0, 0x94, 0xff}}},
    {{{    26,     -6,    -55}, 0, {   942,   1784}, {0x0e, 0x06, 0x82, 0xff}}},
};

// 0x05003898
static const Vtx klepto_seg5_vertex_05003898[] = {
    {{{   134,     -7,     32}, 0, {   496,   1112}, {0x1b, 0x59, 0x55, 0xff}}},
    {{{   134,     -7,    -31}, 0, {   224,   1048}, {0x1a, 0x59, 0xab, 0xff}}},
    {{{     0,     16,      0}, 0, {   454,   -308}, {0xf5, 0x7e, 0x00, 0xff}}},
    {{{     0,     16,      0}, 0, {   480,   -328}, {0xf5, 0x7e, 0x00, 0xff}}},
    {{{   134,     -7,    -31}, 0, {   684,   1248}, {0x1a, 0x59, 0xab, 0xff}}},
    {{{    26,     -6,    -55}, 0, {   836,    324}, {0x0e, 0x06, 0x82, 0xff}}},
    {{{    26,     -6,     56}, 0, {   836,    324}, {0x0e, 0x06, 0x7e, 0xff}}},
    {{{   134,     -7,     32}, 0, {   684,   1248}, {0x1b, 0x59, 0x55, 0xff}}},
    {{{   183,     18,      0}, 0, {   -34,   1968}, {0x72, 0x35, 0xff, 0xff}}},
    {{{   134,     -7,     32}, 0, {   550,   1664}, {0x1b, 0x59, 0x55, 0xff}}},
    {{{   158,    -40,      0}, 0, {   -34,   1564}, {0x36, 0x8e, 0x00, 0xff}}},
    {{{    26,     -6,     56}, 0, {   982,    280}, {0x0e, 0x06, 0x7e, 0xff}}},
    {{{    76,    -43,      0}, 0, {   -34,    256}, {0x5b, 0xa9, 0x00, 0xff}}},
    {{{    26,     -6,    -55}, 0, {   982,    280}, {0x0e, 0x06, 0x82, 0xff}}},
    {{{   134,     -7,    -31}, 0, {   550,   1664}, {0x1a, 0x59, 0xab, 0xff}}},
};

// 0x05003988
static const Vtx klepto_seg5_vertex_05003988[] = {
    {{{   134,     -7,    -31}, 0, {   558,   1256}, {0x1a, 0x59, 0xab, 0xff}}},
    {{{   134,     -7,     32}, 0, {   318,   1032}, {0x1b, 0x59, 0x55, 0xff}}},
    {{{   183,     18,      0}, 0, {   326,   1572}, {0x72, 0x35, 0xff, 0xff}}},
};

// 0x050039B8
static const Vtx klepto_seg5_vertex_050039B8[] = {
    {{{    14,    -62,    -51}, 0, {     0,      0}, {0x0e, 0xc0, 0x94, 0xff}}},
    {{{    65,    -69,      0}, 0, {     0,      0}, {0x56, 0xa3, 0x00, 0xff}}},
    {{{    21,    -88,      0}, 0, {     0,      0}, {0xf8, 0x82, 0x00, 0xff}}},
    {{{    14,    -62,     52}, 0, {     0,      0}, {0x0e, 0xc0, 0x6c, 0xff}}},
    {{{   -42,     -8,     32}, 0, {     0,      0}, {0xa8, 0x2f, 0x4d, 0xff}}},
    {{{   -32,    -61,     23}, 0, {     0,      0}, {0x9f, 0xb8, 0x25, 0xff}}},
    {{{    26,     -6,     56}, 0, {     0,      0}, {0x0e, 0x06, 0x7e, 0xff}}},
    {{{   -32,    -61,    -22}, 0, {     0,      0}, {0xac, 0xae, 0xd2, 0xff}}},
    {{{   -42,     -8,    -31}, 0, {     0,      0}, {0x99, 0x21, 0xbf, 0xff}}},
    {{{    26,     -6,    -55}, 0, {     0,      0}, {0x0e, 0x06, 0x82, 0xff}}},
    {{{     0,     16,      0}, 0, {     0,      0}, {0xf5, 0x7e, 0x00, 0xff}}},
};

// 0x05003A68 - 0x05003AC0
const Gfx klepto_seg5_dl_05003A68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05000808),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05003808.l, 1),
    gsSPLight(&klepto_seg5_lights_05003808.a, 2),
    gsSPVertex(klepto_seg5_vertex_05003838, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  4,  5,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003AC0 - 0x05003B40
const Gfx klepto_seg5_dl_05003AC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05001008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(klepto_seg5_vertex_05003898, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(13, 14, 10, 0x0, 10, 12, 13, 0x0),
    gsSP1Triangle(10, 14,  8, 0x0),
    gsSPVertex(klepto_seg5_vertex_05003988, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003B40 - 0x05003BD0
const Gfx klepto_seg5_dl_05003B40[] = {
    gsSPLight(&klepto_seg5_lights_05003820.l, 1),
    gsSPLight(&klepto_seg5_lights_05003820.a, 2),
    gsSPVertex(klepto_seg5_vertex_050039B8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  3,  6,  4, 0x0),
    gsSP2Triangles( 3,  5,  2, 0x0,  2,  5,  7, 0x0),
    gsSP2Triangles( 7,  0,  2, 0x0,  0,  7,  8, 0x0),
    gsSP2Triangles( 8,  9,  0, 0x0,  8,  7,  5, 0x0),
    gsSP2Triangles(10,  8,  4, 0x0,  4,  8,  5, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0, 10,  4,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003BD0 - 0x05003C58
const Gfx klepto_seg5_dl_05003BD0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05003A68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05003AC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_05003B40),
    gsSPEndDisplayList(),
};

// 0x05003C58
static const Lights1 klepto_seg5_lights_05003C58 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05003C70
static const Vtx klepto_seg5_vertex_05003C70[] = {
    {{{    65,    -11,    -11}, 0, {     0,      0}, {0x0d, 0xa7, 0xa7, 0xff}}},
    {{{    65,    -11,     12}, 0, {     0,      0}, {0x0c, 0xa6, 0x58, 0xff}}},
    {{{     6,    -19,      0}, 0, {     0,      0}, {0xd5, 0x89, 0xff, 0xff}}},
    {{{     6,      1,    -20}, 0, {     0,      0}, {0xd5, 0x00, 0x89, 0xff}}},
    {{{   -10,      1,      0}, 0, {     0,      0}, {0x82, 0x00, 0xff, 0xff}}},
    {{{     6,      1,     21}, 0, {     0,      0}, {0xd4, 0x00, 0x76, 0xff}}},
    {{{    65,     13,    -11}, 0, {     0,      0}, {0x0d, 0x59, 0xa7, 0xff}}},
    {{{     6,     21,      0}, 0, {     0,      0}, {0xd5, 0x77, 0xff, 0xff}}},
    {{{    65,     13,     12}, 0, {     0,      0}, {0x0c, 0x5a, 0x58, 0xff}}},
};

// 0x05003D00 - 0x05003D80
const Gfx klepto_seg5_dl_05003D00[] = {
    gsSPLight(&klepto_seg5_lights_05003C58.l, 1),
    gsSPLight(&klepto_seg5_lights_05003C58.a, 2),
    gsSPVertex(klepto_seg5_vertex_05003C70, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  2,  5, 0x0,  2,  4,  3, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  6,  0,  3, 0x0),
    gsSP2Triangles( 3,  7,  6, 0x0,  4,  7,  3, 0x0),
    gsSP2Triangles( 7,  4,  5, 0x0,  1,  8,  5, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0,  8,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003D80 - 0x05003DB0
const Gfx klepto_seg5_dl_05003D80[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_05003D00),
    gsSPEndDisplayList(),
};

// 0x05003DB0
static const Lights1 klepto_seg5_lights_05003DB0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003DC8
static const Vtx klepto_seg5_vertex_05003DC8[] = {
    {{{   -10,      0,      0}, 0, {   478,    974}, {0x82, 0xf2, 0x00, 0xff}}},
    {{{    10,     24,    -20}, 0, {   186,    654}, {0xc1, 0x4b, 0xb1, 0xff}}},
    {{{    17,    -23,    -20}, 0, {   186,    650}, {0xc2, 0xb5, 0xb0, 0xff}}},
    {{{    44,    -40,      0}, 0, {   480,    314}, {0x29, 0x89, 0x00, 0xff}}},
    {{{    17,    -23,     21}, 0, {   772,    650}, {0xc2, 0xb4, 0x4f, 0xff}}},
    {{{    51,      7,    -33}, 0, {   -10,    134}, {0x32, 0x00, 0x8c, 0xff}}},
    {{{    51,     35,      0}, 0, {   478,     84}, {0x39, 0x71, 0x00, 0xff}}},
    {{{    72,     -9,      0}, 0, {   480,   -110}, {0x7e, 0xf6, 0x00, 0xff}}},
    {{{    51,      7,     34}, 0, {   968,    134}, {0x33, 0x00, 0x74, 0xff}}},
    {{{    10,     24,     21}, 0, {   772,    654}, {0xc0, 0x4b, 0x4e, 0xff}}},
};

// 0x05003E68 - 0x05003F20
const Gfx klepto_seg5_dl_05003E68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05000008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05003DB0.l, 1),
    gsSPLight(&klepto_seg5_lights_05003DB0.a, 2),
    gsSPVertex(klepto_seg5_vertex_05003DC8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  2,  5,  3, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 1,  6,  5, 0x0,  7,  3,  5, 0x0),
    gsSP2Triangles( 4,  3,  8, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 1,  0,  9, 0x0,  6,  1,  9, 0x0),
    gsSP2Triangles( 9,  8,  6, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 8,  9,  4, 0x0,  0,  4,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003F20 - 0x05003F80
const Gfx klepto_seg5_dl_05003F20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05003E68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003F80
static const Lights1 klepto_seg5_lights_05003F80 = gdSPDefLights1(
    0x07, 0x01, 0x01,
    0x1e, 0x05, 0x04, 0x28, 0x28, 0x28
);

// 0x05003F98
static const Vtx klepto_seg5_vertex_05003F98[] = {
    {{{    54,     19,    -37}, 0, {     0,      0}, {0x38, 0x13, 0x91, 0xff}}},
    {{{    86,     16,      0}, 0, {     0,      0}, {0x78, 0x27, 0xfc, 0xff}}},
    {{{    76,    -14,      0}, 0, {     0,      0}, {0x67, 0xb7, 0xfd, 0xff}}},
    {{{    54,     19,     43}, 0, {     0,      0}, {0x38, 0x12, 0x70, 0xff}}},
    {{{    42,    -26,     26}, 0, {     0,      0}, {0x05, 0x9e, 0x50, 0xff}}},
    {{{    42,    -26,    -25}, 0, {     0,      0}, {0x0a, 0xa2, 0xad, 0xff}}},
    {{{    19,     17,    -33}, 0, {     0,      0}, {0xdc, 0x1a, 0x8a, 0xff}}},
    {{{   -34,    -10,      0}, 0, {     0,      0}, {0x8b, 0xcf, 0x00, 0xff}}},
    {{{    47,     50,      0}, 0, {     0,      0}, {0x04, 0x7e, 0xfe, 0xff}}},
    {{{    19,     17,     34}, 0, {     0,      0}, {0xd6, 0x19, 0x74, 0xff}}},
    {{{    -5,     25,     20}, 0, {     0,      0}, {0xb9, 0x57, 0x3a, 0xff}}},
    {{{    -5,     25,    -19}, 0, {     0,      0}, {0xb9, 0x57, 0xc6, 0xff}}},
};

// 0x05004058 - 0x05004118
const Gfx klepto_seg5_dl_05004058[] = {
    gsSPLight(&klepto_seg5_lights_05003F80.l, 1),
    gsSPLight(&klepto_seg5_lights_05003F80.a, 2),
    gsSPVertex(klepto_seg5_vertex_05003F98, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 4,  7,  5, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 8,  1,  0, 0x0,  8,  0,  6, 0x0),
    gsSP2Triangles( 7,  4,  9, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles( 3,  8,  9, 0x0,  9,  8, 10, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  8, 11, 10, 0x0),
    gsSP2Triangles( 6, 11,  8, 0x0,  3,  1,  8, 0x0),
    gsSP2Triangles(10, 11,  7, 0x0,  7, 11,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004118 - 0x05004148
const Gfx klepto_seg5_dl_05004118[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_05004058),
    gsSPEndDisplayList(),
};

// 0x05004148
static const Lights1 klepto_seg5_lights_05004148 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004160
static const Vtx klepto_seg5_vertex_05004160[] = {
    {{{     5,     -3,      0}, 0, {     0,    992}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{   -81,    -45,      0}, 0, {     0,      0}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{   -77,      2,    -47}, 0, {  1700,    224}, {0x2a, 0xaa, 0xae, 0xff}}},
    {{{   -77,      2,     48}, 0, {  1700,    224}, {0x2a, 0xa9, 0x51, 0xff}}},
};

// 0x050041A0 - 0x050041E8
const Gfx klepto_seg5_dl_050041A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05003008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004148.l, 1),
    gsSPLight(&klepto_seg5_lights_05004148.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004160, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x050041E8 - 0x05004258
const Gfx klepto_seg5_dl_050041E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_050041A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004258
static const Lights1 klepto_seg5_lights_05004258 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05004270
static const Vtx klepto_seg5_vertex_05004270[] = {
    {{{     9,     27,      3}, 0, {     0,      0}, {0xd5, 0x50, 0x57, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,      0}, {0x86, 0xe0, 0xfd, 0xff}}},
    {{{    14,      9,      8}, 0, {     0,      0}, {0x01, 0xb1, 0x62, 0xff}}},
    {{{     9,     27,     -3}, 0, {     0,      0}, {0xd6, 0x4d, 0xa5, 0xff}}},
    {{{    69,      0,      0}, 0, {     0,      0}, {0x62, 0x50, 0xfd, 0xff}}},
    {{{    14,      9,     -7}, 0, {     0,      0}, {0x03, 0xb0, 0x9f, 0xff}}},
};

// 0x050042D0 - 0x05004330
const Gfx klepto_seg5_dl_050042D0[] = {
    gsSPLight(&klepto_seg5_lights_05004258.l, 1),
    gsSPLight(&klepto_seg5_lights_05004258.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004270, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  0,  4,  3, 0x0),
    gsSP2Triangles( 5,  1,  3, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 5,  4,  2, 0x0,  4,  5,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004330 - 0x05004360
const Gfx klepto_seg5_dl_05004330[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_050042D0),
    gsSPEndDisplayList(),
};

// 0x05004360
static const Lights1 klepto_seg5_lights_05004360 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05004378
static const Vtx klepto_seg5_vertex_05004378[] = {
    {{{     9,     27,      3}, 0, {     0,      0}, {0xd5, 0x50, 0x57, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,      0}, {0x86, 0xe0, 0xfd, 0xff}}},
    {{{    14,      9,      8}, 0, {     0,      0}, {0x01, 0xb1, 0x62, 0xff}}},
    {{{     9,     27,     -3}, 0, {     0,      0}, {0xd6, 0x4d, 0xa5, 0xff}}},
    {{{    69,      0,      0}, 0, {     0,      0}, {0x62, 0x50, 0xfd, 0xff}}},
    {{{    14,      9,     -7}, 0, {     0,      0}, {0x03, 0xb0, 0x9f, 0xff}}},
};

// 0x050043D8 - 0x05004438
const Gfx klepto_seg5_dl_050043D8[] = {
    gsSPLight(&klepto_seg5_lights_05004360.l, 1),
    gsSPLight(&klepto_seg5_lights_05004360.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004378, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  0,  4,  3, 0x0),
    gsSP2Triangles( 5,  1,  3, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 5,  4,  2, 0x0,  4,  5,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004438 - 0x05004468
const Gfx klepto_seg5_dl_05004438[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_050043D8),
    gsSPEndDisplayList(),
};

// 0x05004468
static const Lights1 klepto_seg5_lights_05004468 = gdSPDefLights1(
    0x02, 0x00, 0x00,
    0x08, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x05004480
static const Lights1 klepto_seg5_lights_05004480 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05004498
static const Vtx klepto_seg5_vertex_05004498[] = {
    {{{    38,      7,     24}, 0, {     0,      0}, {0x10, 0x7d, 0x00, 0xff}}},
    {{{    24,    -12,     28}, 0, {     0,      0}, {0xb4, 0x24, 0x5e, 0xff}}},
    {{{    43,    -19,     30}, 0, {     0,      0}, {0x50, 0xae, 0x35, 0xff}}},
    {{{    48,      7,      0}, 0, {     0,      0}, {0x09, 0x7e, 0xfe, 0xff}}},
    {{{    34,    -14,     -7}, 0, {     0,      0}, {0xf6, 0x1d, 0x85, 0xff}}},
    {{{    34,    -14,      9}, 0, {     0,      0}, {0xf0, 0x1c, 0x7a, 0xff}}},
    {{{    51,    -21,      1}, 0, {     0,      0}, {0x55, 0xa2, 0xfc, 0xff}}},
    {{{    24,    -12,    -25}, 0, {     0,      0}, {0xb4, 0x24, 0xa2, 0xff}}},
    {{{    38,      7,    -23}, 0, {     0,      0}, {0x08, 0x7e, 0x0a, 0xff}}},
    {{{    44,    -19,    -28}, 0, {     0,      0}, {0x49, 0xaa, 0xc7, 0xff}}},
    {{{    33,    -14,    -11}, 0, {     0,      0}, {0x34, 0x11, 0x72, 0xff}}},
    {{{    33,    -13,     13}, 0, {     0,      0}, {0x35, 0x11, 0x8f, 0xff}}},
};

// 0x05004558
static const Vtx klepto_seg5_vertex_05004558[] = {
    {{{     7,     -1,      6}, 0, {     0,      0}, {0xac, 0x4a, 0xc6, 0xff}}},
    {{{    33,    -13,     13}, 0, {     0,      0}, {0x35, 0x11, 0x8f, 0xff}}},
    {{{    20,    -24,     16}, 0, {     0,      0}, {0xdd, 0x87, 0xf3, 0xff}}},
    {{{    34,    -14,      9}, 0, {     0,      0}, {0xf0, 0x1c, 0x7a, 0xff}}},
    {{{    24,    -25,      0}, 0, {     0,      0}, {0xd9, 0x88, 0xfa, 0xff}}},
    {{{    51,    -21,      1}, 0, {     0,      0}, {0x55, 0xa2, 0xfc, 0xff}}},
    {{{    34,    -14,     -7}, 0, {     0,      0}, {0xf6, 0x1d, 0x85, 0xff}}},
    {{{     8,     -2,      0}, 0, {     0,      0}, {0xa6, 0x57, 0xf4, 0xff}}},
    {{{    33,    -14,    -11}, 0, {     0,      0}, {0x34, 0x11, 0x72, 0xff}}},
    {{{    20,    -24,    -14}, 0, {     0,      0}, {0xda, 0x88, 0x09, 0xff}}},
    {{{    44,    -19,    -28}, 0, {     0,      0}, {0x49, 0xaa, 0xc7, 0xff}}},
    {{{    24,    -12,    -25}, 0, {     0,      0}, {0xb4, 0x24, 0xa2, 0xff}}},
    {{{     7,     -1,     -4}, 0, {     0,      0}, {0xaf, 0x4b, 0x3d, 0xff}}},
    {{{    24,    -12,     28}, 0, {     0,      0}, {0xb4, 0x24, 0x5e, 0xff}}},
    {{{    43,    -19,     30}, 0, {     0,      0}, {0x50, 0xae, 0x35, 0xff}}},
};

// 0x05004648
static const Vtx klepto_seg5_vertex_05004648[] = {
    {{{   -22,      7,      0}, 0, {     0,      0}, {0x9a, 0x4a, 0xf4, 0xff}}},
    {{{     2,    -10,      0}, 0, {     0,      0}, {0xe8, 0x84, 0xfe, 0xff}}},
    {{{    15,     -3,     17}, 0, {     0,      0}, {0x4b, 0x0d, 0x65, 0xff}}},
    {{{    15,     -3,    -15}, 0, {     0,      0}, {0x48, 0x0e, 0x99, 0xff}}},
    {{{     6,      5,      0}, 0, {     0,      0}, {0x25, 0x79, 0xfe, 0xff}}},
};

// 0x05004698 - 0x050047C8
const Gfx klepto_seg5_dl_05004698[] = {
    gsSPLight(&klepto_seg5_lights_05004468.l, 1),
    gsSPLight(&klepto_seg5_lights_05004468.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004498, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0,  0, 11,  1, 0x0),
    gsSP1Triangle(11,  0,  2, 0x0),
    gsSPLight(&klepto_seg5_lights_05004480.l, 1),
    gsSPLight(&klepto_seg5_lights_05004480.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004558, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles(12,  9,  8, 0x0, 12, 11,  9, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0,  0,  2, 13, 0x0),
    gsSP2Triangles( 1,  0, 13, 0x0, 13,  2, 14, 0x0),
    gsSP1Triangle(14,  2,  1, 0x0),
    gsSPVertex(klepto_seg5_vertex_05004648, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  4,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  3,  0,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x050047C8 - 0x050047F8
const Gfx klepto_seg5_dl_050047C8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_05004698),
    gsSPEndDisplayList(),
};

// 0x050047F8
static const Lights1 klepto_seg5_lights_050047F8 = gdSPDefLights1(
    0x02, 0x00, 0x00,
    0x08, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x05004810
static const Lights1 klepto_seg5_lights_05004810 = gdSPDefLights1(
    0x3f, 0x1d, 0x08,
    0xff, 0x75, 0x21, 0x28, 0x28, 0x28
);

// 0x05004828
static const Vtx klepto_seg5_vertex_05004828[] = {
    {{{    38,      7,     24}, 0, {     0,      0}, {0x10, 0x7d, 0x00, 0xff}}},
    {{{    24,    -12,     28}, 0, {     0,      0}, {0xb4, 0x24, 0x5e, 0xff}}},
    {{{    43,    -19,     30}, 0, {     0,      0}, {0x50, 0xae, 0x35, 0xff}}},
    {{{    48,      7,      0}, 0, {     0,      0}, {0x09, 0x7e, 0xfe, 0xff}}},
    {{{    34,    -14,     -7}, 0, {     0,      0}, {0xf6, 0x1d, 0x85, 0xff}}},
    {{{    34,    -14,      9}, 0, {     0,      0}, {0xf0, 0x1c, 0x7a, 0xff}}},
    {{{    51,    -21,      1}, 0, {     0,      0}, {0x55, 0xa2, 0xfc, 0xff}}},
    {{{    24,    -12,    -25}, 0, {     0,      0}, {0xb4, 0x24, 0xa2, 0xff}}},
    {{{    38,      7,    -23}, 0, {     0,      0}, {0x08, 0x7e, 0x0a, 0xff}}},
    {{{    44,    -19,    -28}, 0, {     0,      0}, {0x49, 0xaa, 0xc7, 0xff}}},
    {{{    33,    -14,    -11}, 0, {     0,      0}, {0x34, 0x11, 0x72, 0xff}}},
    {{{    33,    -13,     13}, 0, {     0,      0}, {0x35, 0x11, 0x8f, 0xff}}},
};

// 0x050048E8
static const Vtx klepto_seg5_vertex_050048E8[] = {
    {{{     7,     -1,      6}, 0, {     0,      0}, {0xac, 0x4a, 0xc6, 0xff}}},
    {{{    33,    -13,     13}, 0, {     0,      0}, {0x35, 0x11, 0x8f, 0xff}}},
    {{{    20,    -24,     16}, 0, {     0,      0}, {0xdd, 0x87, 0xf3, 0xff}}},
    {{{    34,    -14,      9}, 0, {     0,      0}, {0xf0, 0x1c, 0x7a, 0xff}}},
    {{{    24,    -25,      0}, 0, {     0,      0}, {0xd9, 0x88, 0xfa, 0xff}}},
    {{{    51,    -21,      1}, 0, {     0,      0}, {0x55, 0xa2, 0xfc, 0xff}}},
    {{{    34,    -14,     -7}, 0, {     0,      0}, {0xf6, 0x1d, 0x85, 0xff}}},
    {{{     8,     -2,      0}, 0, {     0,      0}, {0xa6, 0x57, 0xf4, 0xff}}},
    {{{    33,    -14,    -11}, 0, {     0,      0}, {0x34, 0x11, 0x72, 0xff}}},
    {{{    20,    -24,    -14}, 0, {     0,      0}, {0xda, 0x88, 0x09, 0xff}}},
    {{{    44,    -19,    -28}, 0, {     0,      0}, {0x49, 0xaa, 0xc7, 0xff}}},
    {{{    24,    -12,    -25}, 0, {     0,      0}, {0xb4, 0x24, 0xa2, 0xff}}},
    {{{     7,     -1,     -4}, 0, {     0,      0}, {0xaf, 0x4b, 0x3d, 0xff}}},
    {{{    24,    -12,     28}, 0, {     0,      0}, {0xb4, 0x24, 0x5e, 0xff}}},
    {{{    43,    -19,     30}, 0, {     0,      0}, {0x50, 0xae, 0x35, 0xff}}},
};

// 0x050049D8
static const Vtx klepto_seg5_vertex_050049D8[] = {
    {{{   -22,      7,      0}, 0, {     0,      0}, {0x9a, 0x4a, 0xf4, 0xff}}},
    {{{     2,    -10,      0}, 0, {     0,      0}, {0xe8, 0x84, 0xfe, 0xff}}},
    {{{    15,     -3,     17}, 0, {     0,      0}, {0x4b, 0x0d, 0x65, 0xff}}},
    {{{    15,     -3,    -15}, 0, {     0,      0}, {0x48, 0x0e, 0x99, 0xff}}},
    {{{     6,      5,      0}, 0, {     0,      0}, {0x25, 0x79, 0xfe, 0xff}}},
};

// 0x05004A28 - 0x05004B58
const Gfx klepto_seg5_dl_05004A28[] = {
    gsSPLight(&klepto_seg5_lights_050047F8.l, 1),
    gsSPLight(&klepto_seg5_lights_050047F8.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004828, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0,  0, 11,  1, 0x0),
    gsSP1Triangle(11,  0,  2, 0x0),
    gsSPLight(&klepto_seg5_lights_05004810.l, 1),
    gsSPLight(&klepto_seg5_lights_05004810.a, 2),
    gsSPVertex(klepto_seg5_vertex_050048E8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles(12,  9,  8, 0x0, 12, 11,  9, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0,  0,  2, 13, 0x0),
    gsSP2Triangles( 1,  0, 13, 0x0, 13,  2, 14, 0x0),
    gsSP1Triangle(14,  2,  1, 0x0),
    gsSPVertex(klepto_seg5_vertex_050049D8, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  4,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  3,  0,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004B58 - 0x05004B88
const Gfx klepto_seg5_dl_05004B58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(klepto_seg5_dl_05004A28),
    gsSPEndDisplayList(),
};

// 0x05004B88
static const Lights1 klepto_seg5_lights_05004B88 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004BA0
static const Vtx klepto_seg5_vertex_05004BA0[] = {
    {{{    -2,    -13,      0}, 0, {  1992,    528}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -2,     14,      0}, 0, {  1992,    272}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    34,     24,      0}, 0, {  1640,    176}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    34,    -33,      0}, 0, {  1640,    722}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x05004BE0 - 0x05004C28
const Gfx klepto_seg5_dl_05004BE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004B88.l, 1),
    gsSPLight(&klepto_seg5_lights_05004B88.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004BA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004C28 - 0x05004C98
const Gfx klepto_seg5_dl_05004C28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05004BE0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004C98
static const Lights1 klepto_seg5_lights_05004C98 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004CB0
static const Vtx klepto_seg5_vertex_05004CB0[] = {
    {{{    34,    -33,      0}, 0, {  1640,    722}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    34,     24,      0}, 0, {  1640,    176}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -2,    -13,      0}, 0, {  1992,    528}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -2,     14,      0}, 0, {  1992,    272}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x05004CF0 - 0x05004D38
const Gfx klepto_seg5_dl_05004CF0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004C98.l, 1),
    gsSPLight(&klepto_seg5_lights_05004C98.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004CB0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004D38 - 0x05004DA8
const Gfx klepto_seg5_dl_05004D38[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05004CF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004DA8
static const Lights1 klepto_seg5_lights_05004DA8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004DC0
static const Vtx klepto_seg5_vertex_05004DC0[] = {
    {{{     0,    -33,      0}, 0, {  1640,    716}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,     24,      0}, 0, {  1640,    172}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    79,     34,      0}, 0, {   904,     76}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    79,    -58,      0}, 0, {   904,    940}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x05004E00 - 0x05004E48
const Gfx klepto_seg5_dl_05004E00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004DA8.l, 1),
    gsSPLight(&klepto_seg5_lights_05004DA8.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004DC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004E48 - 0x05004EB8
const Gfx klepto_seg5_dl_05004E48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05004E00),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004EB8
static const Lights1 klepto_seg5_lights_05004EB8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004ED0
static const Vtx klepto_seg5_vertex_05004ED0[] = {
    {{{    79,    -58,      0}, 0, {   904,    940}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    79,     34,      0}, 0, {   904,     76}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    -33,      0}, 0, {  1640,    716}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,     24,      0}, 0, {  1640,    172}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x05004F10 - 0x05004F58
const Gfx klepto_seg5_dl_05004F10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004EB8.l, 1),
    gsSPLight(&klepto_seg5_lights_05004EB8.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004ED0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004F58 - 0x05004FC8
const Gfx klepto_seg5_dl_05004F58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05004F10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004FC8
static const Lights1 klepto_seg5_lights_05004FC8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004FE0
static const Vtx klepto_seg5_vertex_05004FE0[] = {
    {{{     0,    -58,      0}, 0, {   904,    942}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,     34,      0}, 0, {   904,     78}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   108,     52,      0}, 0, {   -96,    -86}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    65,    -68,      0}, 0, {   296,   1036}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x05005020 - 0x05005068
const Gfx klepto_seg5_dl_05005020[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_05004FC8.l, 1),
    gsSPLight(&klepto_seg5_lights_05004FC8.a, 2),
    gsSPVertex(klepto_seg5_vertex_05004FE0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005068 - 0x050050D8
const Gfx klepto_seg5_dl_05005068[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05005020),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x050050D8
static const Lights1 klepto_seg5_lights_050050D8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050050F0
static const Vtx klepto_seg5_vertex_050050F0[] = {
    {{{    65,    -68,      0}, 0, {   296,   1036}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   108,     52,      0}, 0, {   -96,    -86}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    -58,      0}, 0, {   904,    942}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,     34,      0}, 0, {   904,     78}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x05005130 - 0x05005178
const Gfx klepto_seg5_dl_05005130[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, klepto_seg5_texture_05002008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&klepto_seg5_lights_050050D8.l, 1),
    gsSPLight(&klepto_seg5_lights_050050D8.a, 2),
    gsSPVertex(klepto_seg5_vertex_050050F0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005178 - 0x050051E8
const Gfx klepto_seg5_dl_05005178[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(klepto_seg5_dl_05005130),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};
