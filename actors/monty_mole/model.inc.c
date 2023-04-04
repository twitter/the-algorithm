// Monty Mole

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused1 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused2 = gdSPDefLights1(
    0x30, 0x1f, 0x00,
    0xc3, 0x7e, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused3 = gdSPDefLights1(
    0x05, 0x04, 0x05,
    0x16, 0x13, 0x14, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused4 = gdSPDefLights1(
    0x07, 0x08, 0x07,
    0x1f, 0x20, 0x1f, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused5 = gdSPDefLights1(
    0x14, 0x0b, 0x0a,
    0x53, 0x2e, 0x28, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 monty_mole_lights_unused6 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05000970
ALIGNED8 static const Texture monty_mole_seg5_texture_05000970[] = {
#include "actors/monty_mole/monty_mole_cheek.rgba16.inc.c"
};

// 0x05001170
ALIGNED8 static const Texture monty_mole_seg5_texture_05001170[] = {
#include "actors/monty_mole/monty_mole_eye.rgba16.inc.c"
};

// 0x05001970
ALIGNED8 static const Texture monty_mole_seg5_texture_05001970[] = {
#include "actors/monty_mole/monty_mole_nose.rgba16.inc.c"
};

// 0x05002170
ALIGNED8 static const Texture monty_mole_seg5_texture_05002170[] = {
#include "actors/monty_mole/monty_mole_tooth.rgba16.inc.c"
};

// 0x05002970
ALIGNED8 static const Texture monty_mole_seg5_texture_05002970[] = {
#include "actors/monty_mole/monty_mole_claw.rgba16.inc.c"
};

// 0x05003170
static const Lights1 monty_mole_seg5_lights_05003170 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003188
static const Vtx monty_mole_seg5_vertex_05003188[] = {
    {{{    85,    -12,    -77}, 0, {    -6,    778}, {0x21, 0x97, 0xc2, 0xff}}},
    {{{   -13,     -8,     32}, 0, {   348,    972}, {0xa0, 0xc2, 0x36, 0xff}}},
    {{{   -13,     -8,    -24}, 0, {   150,    978}, {0x99, 0x28, 0xc3, 0xff}}},
    {{{    -3,      7,     23}, 0, {   320,    950}, {0xbd, 0x63, 0x28, 0xff}}},
    {{{    -3,      7,    -18}, 0, {   176,    956}, {0xcf, 0x72, 0xe9, 0xff}}},
    {{{    78,     15,    -63}, 0, {    38,    790}, {0x23, 0x6b, 0xc7, 0xff}}},
    {{{    85,    -12,     81}, 0, {   540,    758}, {0x44, 0x3f, 0x55, 0xff}}},
    {{{    75,      7,     58}, 0, {   458,    782}, {0x12, 0x79, 0x1e, 0xff}}},
};

// 0x05003208 - 0x050032A0
const Gfx monty_mole_seg5_dl_05003208[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003170.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003170.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05003188, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  6,  3,  1, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 7,  5,  4, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x050032A0 - 0x05003300
const Gfx monty_mole_seg5_dl_050032A0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003208),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003300
static const Lights1 monty_mole_seg5_lights_05003300 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003318
static const Vtx monty_mole_seg5_vertex_05003318[] = {
    {{{    80,     10,     54}, 0, {  3144,    202}, {0x17, 0x63, 0x4a, 0xff}}},
    {{{   133,     10,     13}, 0, {  1908,    982}, {0x77, 0xf7, 0x29, 0xff}}},
    {{{    79,     10,    -41}, 0, {  -148,    212}, {0x11, 0x61, 0xb1, 0xff}}},
    {{{   133,     10,    -14}, 0, {   948,    982}, {0x5f, 0xe9, 0xb1, 0xff}}},
    {{{    79,     10,    -41}, 0, {  2864,    188}, {0x11, 0x61, 0xb1, 0xff}}},
    {{{   133,     10,    -14}, 0, {  2014,    954}, {0x5f, 0xe9, 0xb1, 0xff}}},
    {{{    97,    -15,    -19}, 0, {  2068,    162}, {0x1f, 0x92, 0xcc, 0xff}}},
    {{{   133,     10,     13}, 0, {  1096,    982}, {0x77, 0xf7, 0x29, 0xff}}},
    {{{    98,    -18,     18}, 0, {   820,    170}, {0x16, 0x8c, 0x2d, 0xff}}},
    {{{    80,     10,     54}, 0, {  -294,    274}, {0x17, 0x63, 0x4a, 0xff}}},
};

// 0x050033B8
static const Vtx monty_mole_seg5_vertex_050033B8[] = {
    {{{    -4,     10,     36}, 0, {  1044,    680}, {0xd0, 0xda, 0x6e, 0xff}}},
    {{{    98,    -18,     18}, 0, {   780,    984}, {0x16, 0x8c, 0x2d, 0xff}}},
    {{{    80,     10,     54}, 0, {   896,    934}, {0x17, 0x63, 0x4a, 0xff}}},
    {{{    79,     10,    -41}, 0, {   694,    926}, {0x11, 0x61, 0xb1, 0xff}}},
    {{{    -4,     10,    -34}, 0, {   896,    674}, {0xd9, 0x2b, 0x90, 0xff}}},
    {{{    97,    -15,    -19}, 0, {   700,    980}, {0x1f, 0x92, 0xcc, 0xff}}},
    {{{   -46,     10,      1}, 0, {  1064,    552}, {0xa0, 0xae, 0xfe, 0xff}}},
    {{{    -4,     -9,      0}, 0, {   970,    678}, {0xe1, 0x85, 0xfd, 0xff}}},
};

// 0x05003438 - 0x050034A0
const Gfx monty_mole_seg5_dl_05003438[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05002970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003300.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003300.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05003318, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x050034A0 - 0x05003518
const Gfx monty_mole_seg5_dl_050034A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_050033B8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  6,  0, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 5,  7,  4, 0x0,  0,  6,  7, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  5,  1,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003518 - 0x05003598
const Gfx monty_mole_seg5_dl_05003518[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003438),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_050034A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003598
static const Lights1 monty_mole_seg5_lights_05003598 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050035B0
static const Vtx monty_mole_seg5_vertex_050035B0[] = {
    {{{    69,    -15,     58}, 0, {  -562,    834}, {0x64, 0x03, 0x4d, 0xff}}},
    {{{    48,     21,      0}, 0, {  1416,    262}, {0x3b, 0x6e, 0xef, 0xff}}},
    {{{    38,     13,     47}, 0, {   -42,    224}, {0x1e, 0x6b, 0x3c, 0xff}}},
    {{{    83,    -16,      0}, 0, {  1140,   1008}, {0x7a, 0x1e, 0xf5, 0xff}}},
    {{{    69,    -15,    -31}, 0, {  2066,    864}, {0x3a, 0x9d, 0xcc, 0xff}}},
    {{{     4,    -12,    -76}, 0, {  3424,    196}, {0x06, 0xe0, 0x86, 0xff}}},
    {{{    69,    -15,    -31}, 0, {  -174,    804}, {0x3a, 0x9d, 0xcc, 0xff}}},
    {{{    69,    -15,     58}, 0, {  2602,    774}, {0x64, 0x03, 0x4d, 0xff}}},
    {{{     4,    -12,     82}, 0, {  3316,     82}, {0xcd, 0xa8, 0x4b, 0xff}}},
    {{{    83,    -16,      0}, 0, {   808,    950}, {0x7a, 0x1e, 0xf5, 0xff}}},
    {{{     4,    -12,    -76}, 0, { -1640,    128}, {0x06, 0xe0, 0x86, 0xff}}},
    {{{     4,    -12,     82}, 0, { -1266,    142}, {0xcd, 0xa8, 0x4b, 0xff}}},
};

// 0x05003670
static const Vtx monty_mole_seg5_vertex_05003670[] = {
    {{{   -21,     -2,    -55}, 0, {  1006,    906}, {0x93, 0xd0, 0xd6, 0xff}}},
    {{{    -3,     15,    -63}, 0, {   986,    942}, {0xd3, 0x6e, 0xd5, 0xff}}},
    {{{     4,    -12,    -76}, 0, {  1000,    962}, {0x06, 0xe0, 0x86, 0xff}}},
    {{{    48,     21,      0}, 0, {   762,    994}, {0x3b, 0x6e, 0xef, 0xff}}},
    {{{    -5,      8,     58}, 0, {   748,    864}, {0xd3, 0x6f, 0x28, 0xff}}},
    {{{   -21,     -2,     39}, 0, {   816,    848}, {0x8c, 0x2b, 0x1b, 0xff}}},
    {{{    38,     13,     47}, 0, {   686,    950}, {0x1e, 0x6b, 0x3c, 0xff}}},
    {{{     4,    -12,     82}, 0, {   682,    868}, {0xcd, 0xa8, 0x4b, 0xff}}},
    {{{   -21,     -2,     39}, 0, {   836,    598}, {0x8c, 0x2b, 0x1b, 0xff}}},
    {{{   -21,     -2,    -55}, 0, {   584,    594}, {0x93, 0xd0, 0xd6, 0xff}}},
    {{{     4,    -12,     82}, 0, {   946,    700}, {0xcd, 0xa8, 0x4b, 0xff}}},
    {{{     4,    -12,    -76}, 0, {   524,    694}, {0x06, 0xe0, 0x86, 0xff}}},
};

// 0x05003730 - 0x050037A8
const Gfx monty_mole_seg5_dl_05003730[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05002970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003598.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003598.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_050035B0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  1,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 8, 10,  6, 0x0,  2, 11,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x050037A8 - 0x05003820
const Gfx monty_mole_seg5_dl_050037A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_05003670, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  3,  1, 0x0,  5,  4,  1, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  4,  6,  3, 0x0),
    gsSP2Triangles( 7,  4,  5, 0x0,  6,  4,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003820 - 0x050038A0
const Gfx monty_mole_seg5_dl_05003820[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003730),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_050037A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x050038A0
static const Lights1 monty_mole_seg5_lights_050038A0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050038B8
static const Vtx monty_mole_seg5_vertex_050038B8[] = {
    {{{    -3,      7,    -22}, 0, {   234,    806}, {0xd2, 0x73, 0xe5, 0xff}}},
    {{{    -3,      7,     19}, 0, {    80,    810}, {0xb2, 0x58, 0x2e, 0xff}}},
    {{{    78,     15,     64}, 0, {    34,    970}, {0x11, 0x79, 0x22, 0xff}}},
    {{{   -13,     -8,    -31}, 0, {   250,    786}, {0xa0, 0x3a, 0xc7, 0xff}}},
    {{{   -13,     -8,     25}, 0, {    40,    792}, {0xa2, 0xbf, 0x36, 0xff}}},
    {{{    85,    -12,     78}, 0, {    -4,    984}, {0x40, 0x25, 0x67, 0xff}}},
    {{{    75,      7,    -57}, 0, {   480,    952}, {0x2a, 0x6a, 0xcb, 0xff}}},
    {{{    85,    -12,    -80}, 0, {   582,    968}, {0x33, 0xa5, 0xb9, 0xff}}},
};

// 0x05003938 - 0x050039D0
const Gfx monty_mole_seg5_dl_05003938[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_050038A0.l, 1),
    gsSPLight(&monty_mole_seg5_lights_050038A0.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_050038B8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 3,  0,  6, 0x0,  4,  3,  7, 0x0),
    gsSP2Triangles( 4,  7,  5, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x050039D0 - 0x05003A30
const Gfx monty_mole_seg5_dl_050039D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003938),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003A30
static const Lights1 monty_mole_seg5_lights_05003A30 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003A48
static const Vtx monty_mole_seg5_vertex_05003A48[] = {
    {{{    79,     10,     42}, 0, {  -148,    212}, {0x11, 0x61, 0x4f, 0xff}}},
    {{{   133,     10,     15}, 0, {   948,    982}, {0x5f, 0xe9, 0x4f, 0xff}}},
    {{{   133,     10,    -12}, 0, {  1908,    982}, {0x77, 0xf7, 0xd7, 0xff}}},
    {{{    80,     10,    -53}, 0, {  3144,    202}, {0x17, 0x63, 0xb6, 0xff}}},
    {{{    80,     10,    -53}, 0, {  -294,    274}, {0x17, 0x63, 0xb6, 0xff}}},
    {{{   133,     10,    -12}, 0, {  1096,    982}, {0x77, 0xf7, 0xd7, 0xff}}},
    {{{    98,    -18,    -17}, 0, {   820,    170}, {0x16, 0x8c, 0xd3, 0xff}}},
    {{{    97,    -15,     20}, 0, {  2068,    162}, {0x1f, 0x91, 0x34, 0xff}}},
    {{{   133,     10,     15}, 0, {  2014,    954}, {0x5f, 0xe9, 0x4f, 0xff}}},
    {{{    79,     10,     42}, 0, {  2864,    188}, {0x11, 0x61, 0x4f, 0xff}}},
};

// 0x05003AE8
static const Vtx monty_mole_seg5_vertex_05003AE8[] = {
    {{{    -4,     10,    -35}, 0, {   838,    786}, {0xd0, 0xdb, 0x91, 0xff}}},
    {{{   -46,     10,      0}, 0, {   948,    720}, {0xa0, 0xae, 0x00, 0xff}}},
    {{{    -4,     10,     35}, 0, {   970,    808}, {0xd9, 0x2b, 0x70, 0xff}}},
    {{{    -4,     -9,      0}, 0, {   904,    798}, {0xe1, 0x85, 0x02, 0xff}}},
    {{{    79,     10,     42}, 0, {   894,    962}, {0x11, 0x61, 0x4f, 0xff}}},
    {{{    80,     10,    -53}, 0, {   714,    930}, {0x17, 0x63, 0xb6, 0xff}}},
    {{{    97,    -15,     20}, 0, {   834,    986}, {0x1f, 0x91, 0x34, 0xff}}},
    {{{    98,    -18,    -17}, 0, {   764,    974}, {0x16, 0x8c, 0xd3, 0xff}}},
};

// 0x05003B68 - 0x05003BD0
const Gfx monty_mole_seg5_dl_05003B68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05002970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003A30.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003A30.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05003A48, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 5,  8,  7, 0x0,  7,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003BD0 - 0x05003C48
const Gfx monty_mole_seg5_dl_05003BD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_05003AE8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 2,  3,  6, 0x0,  6,  4,  2, 0x0),
    gsSP2Triangles( 5,  7,  0, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 7,  3,  0, 0x0,  3,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003C48 - 0x05003CC8
const Gfx monty_mole_seg5_dl_05003C48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003B68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003BD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003CC8
static const Lights1 monty_mole_seg5_lights_05003CC8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003CE0
static const Vtx monty_mole_seg5_vertex_05003CE0[] = {
    {{{     4,    -12,     77}, 0, {  3424,    196}, {0x06, 0xe0, 0x7a, 0xff}}},
    {{{    69,    -15,     32}, 0, {  2066,    864}, {0x3a, 0x9d, 0x33, 0xff}}},
    {{{    48,     21,      0}, 0, {  1416,    262}, {0x3b, 0x6e, 0x10, 0xff}}},
    {{{    83,    -16,      0}, 0, {  1140,   1008}, {0x7a, 0x1f, 0x0a, 0xff}}},
    {{{    38,     13,    -46}, 0, {   -42,    224}, {0x1e, 0x6b, 0xc4, 0xff}}},
    {{{    69,    -15,    -57}, 0, {  -562,    834}, {0x64, 0x03, 0xb3, 0xff}}},
    {{{     4,    -12,    -81}, 0, {  3368,     64}, {0xcd, 0xa8, 0xb5, 0xff}}},
    {{{    69,    -15,    -57}, 0, {  2592,    812}, {0x64, 0x03, 0xb3, 0xff}}},
    {{{    69,    -15,     32}, 0, {  -302,    824}, {0x3a, 0x9d, 0x33, 0xff}}},
    {{{     4,    -12,     77}, 0, { -1800,     78}, {0x06, 0xe0, 0x7a, 0xff}}},
    {{{    83,    -16,      0}, 0, {   714,    990}, {0x7a, 0x1f, 0x0a, 0xff}}},
    {{{     4,    -12,    -81}, 0, { -1266,    142}, {0xcd, 0xa8, 0xb5, 0xff}}},
};

// 0x05003DA0
static const Vtx monty_mole_seg5_vertex_05003DA0[] = {
    {{{    48,     21,      0}, 0, {   700,    872}, {0x3b, 0x6e, 0x10, 0xff}}},
    {{{    38,     13,    -46}, 0, {   818,    856}, {0x1e, 0x6b, 0xc4, 0xff}}},
    {{{    -5,      8,    -57}, 0, {   898,    758}, {0xd3, 0x6f, 0xd8, 0xff}}},
    {{{    -3,     15,     64}, 0, {   620,    748}, {0xd3, 0x6e, 0x2b, 0xff}}},
    {{{     4,    -12,     77}, 0, {   582,    764}, {0x06, 0xe0, 0x7a, 0xff}}},
    {{{   -21,     -2,     56}, 0, {   660,    708}, {0x93, 0xd0, 0x2a, 0xff}}},
    {{{   -21,     -2,    -38}, 0, {   872,    720}, {0x8c, 0x2b, 0xe5, 0xff}}},
    {{{     4,    -12,    -81}, 0, {   938,    784}, {0xcd, 0xa8, 0xb5, 0xff}}},
    {{{     4,    -12,    -81}, 0, {   382,    662}, {0xcd, 0xa8, 0xb5, 0xff}}},
    {{{   -21,     -2,     56}, 0, {   660,    550}, {0x93, 0xd0, 0x2a, 0xff}}},
    {{{   -21,     -2,    -38}, 0, {   482,    580}, {0x8c, 0x2b, 0xe5, 0xff}}},
    {{{     4,    -12,     77}, 0, {   680,    608}, {0x06, 0xe0, 0x7a, 0xff}}},
};

// 0x05003E60 - 0x05003ED8
const Gfx monty_mole_seg5_dl_05003E60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05002970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003CC8.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003CC8.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05003CE0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  2,  5, 0x0,  2,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0,  5, 11,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003ED8 - 0x05003F50
const Gfx monty_mole_seg5_dl_05003ED8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_05003DA0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 3,  0,  2, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 3,  2,  6, 0x0,  5,  3,  6, 0x0),
    gsSP2Triangles( 6,  2,  7, 0x0,  7,  2,  1, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  9,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003F50 - 0x05003FD0
const Gfx monty_mole_seg5_dl_05003F50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003E60),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05003ED8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05003FD0
static const Lights1 monty_mole_seg5_lights_05003FD0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05003FE8
static const Vtx monty_mole_seg5_vertex_05003FE8[] = {
    {{{   -39,     52,     94}, 0, {  1068,    880}, {0xca, 0x25, 0x6c, 0xff}}},
    {{{    -6,     97,     52}, 0, {   806,    668}, {0xf6, 0x72, 0x35, 0xff}}},
    {{{   -59,     66,     46}, 0, {   756,   1006}, {0x96, 0x42, 0x13, 0xff}}},
    {{{    -6,     97,    -51}, 0, {   132,    676}, {0xf3, 0x78, 0xda, 0xff}}},
    {{{    58,     82,    -37}, 0, {   234,    258}, {0x42, 0x60, 0xd1, 0xff}}},
    {{{    33,     47,    -88}, 0, {   -96,    426}, {0x23, 0x2a, 0x8e, 0xff}}},
    {{{   -39,     52,    -93}, 0, {  -136,    894}, {0xce, 0x36, 0x99, 0xff}}},
    {{{   109,     -4,    -55}, 0, {   122,    -68}, {0x63, 0xfe, 0xb2, 0xff}}},
    {{{   -59,     66,    -45}, 0, {   170,   1012}, {0x93, 0x33, 0xda, 0xff}}},
    {{{    58,     82,     38}, 0, {   718,    252}, {0x3d, 0x67, 0x28, 0xff}}},
    {{{   109,     46,      0}, 0, {   482,    -72}, {0x73, 0x35, 0x00, 0xff}}},
    {{{    33,     47,     89}, 0, {  1044,    414}, {0x28, 0x32, 0x6d, 0xff}}},
    {{{   109,     -4,     56}, 0, {   840,    -76}, {0x63, 0xfe, 0x4e, 0xff}}},
};

// 0x050040B8
static const Vtx monty_mole_seg5_vertex_050040B8[] = {
    {{{     5,    -97,     36}, 0, {   564,    746}, {0x1d, 0x96, 0x3f, 0xff}}},
    {{{    33,    -45,     89}, 0, {   514,    820}, {0x1f, 0xd1, 0x71, 0xff}}},
    {{{   -38,    -44,     92}, 0, {   512,    824}, {0xcc, 0xca, 0x65, 0xff}}},
    {{{   109,     -4,    -55}, 0, {   474,    710}, {0x63, 0xfe, 0xb2, 0xff}}},
    {{{   109,    -51,      0}, 0, {   520,    736}, {0x71, 0xc7, 0x00, 0xff}}},
    {{{    59,    -84,    -30}, 0, {   552,    692}, {0x34, 0x9b, 0xc8, 0xff}}},
    {{{    59,    -84,     31}, 0, {   552,    748}, {0x39, 0x96, 0x27, 0xff}}},
    {{{   109,     -4,     56}, 0, {   474,    812}, {0x63, 0xfe, 0x4e, 0xff}}},
    {{{   109,     46,      0}, 0, {   426,    786}, {0x73, 0x35, 0x00, 0xff}}},
    {{{    33,    -45,    -88}, 0, {   514,    660}, {0x24, 0xcf, 0x91, 0xff}}},
    {{{    33,     47,    -88}, 0, {   424,    708}, {0x23, 0x2a, 0x8e, 0xff}}},
    {{{    33,     47,     89}, 0, {   424,    868}, {0x28, 0x32, 0x6d, 0xff}}},
    {{{   -39,     52,     94}, 0, {   418,    874}, {0xca, 0x25, 0x6c, 0xff}}},
    {{{   -41,    -98,     39}, 0, {   564,    748}, {0x9b, 0xc6, 0x30, 0xff}}},
    {{{   -46,   -140,      0}, 0, {   606,    692}, {0xf2, 0x82, 0x00, 0xff}}},
    {{{     5,    -97,    -35}, 0, {   564,    680}, {0x22, 0x92, 0xcd, 0xff}}},
};

// 0x050041B8
static const Vtx monty_mole_seg5_vertex_050041B8[] = {
    {{{   -38,    -44,    -91}, 0, {   514,    658}, {0xd2, 0xcf, 0x95, 0xff}}},
    {{{    59,    -84,    -30}, 0, {   552,    692}, {0x34, 0x9b, 0xc8, 0xff}}},
    {{{     5,    -97,    -35}, 0, {   564,    680}, {0x22, 0x92, 0xcd, 0xff}}},
    {{{    33,    -45,    -88}, 0, {   514,    660}, {0x24, 0xcf, 0x91, 0xff}}},
    {{{    33,     47,    -88}, 0, {   424,    708}, {0x23, 0x2a, 0x8e, 0xff}}},
    {{{   -39,     52,    -93}, 0, {   420,    706}, {0xce, 0x36, 0x99, 0xff}}},
    {{{   -41,    -98,    -38}, 0, {   566,    678}, {0xa4, 0xc2, 0xc4, 0xff}}},
    {{{   -67,    -12,    -40}, 0, {   482,    720}, {0x84, 0xf3, 0xee, 0xff}}},
    {{{   -59,     66,    -45}, 0, {   406,    756}, {0x93, 0x33, 0xda, 0xff}}},
    {{{     5,    -97,     36}, 0, {   564,    746}, {0x1d, 0x96, 0x3f, 0xff}}},
    {{{   -46,   -140,      0}, 0, {   606,    692}, {0xf2, 0x82, 0x00, 0xff}}},
    {{{   -41,    -98,     39}, 0, {   564,    748}, {0x9b, 0xc6, 0x30, 0xff}}},
    {{{   -59,     66,     46}, 0, {   406,    838}, {0x96, 0x42, 0x13, 0xff}}},
    {{{   -38,    -44,     92}, 0, {   512,    824}, {0xcc, 0xca, 0x65, 0xff}}},
    {{{   -67,    -12,     41}, 0, {   482,    794}, {0x86, 0xf5, 0x20, 0xff}}},
    {{{   -39,     52,     94}, 0, {   418,    874}, {0xca, 0x25, 0x6c, 0xff}}},
};

// 0x050042B8 - 0x05004368
const Gfx monty_mole_seg5_dl_050042B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05000970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05003FD0.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05003FD0.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05003FE8, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 3,  6,  8, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  8, 0x0,  3,  1,  9, 0x0),
    gsSP2Triangles( 3,  9,  4, 0x0,  4, 10,  7, 0x0),
    gsSP2Triangles(10,  4,  9, 0x0,  9, 11, 12, 0x0),
    gsSP2Triangles(12, 10,  9, 0x0, 11,  9,  1, 0x0),
    gsSP1Triangle(11,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004368 - 0x050044B0
const Gfx monty_mole_seg5_dl_05004368[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_050040B8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  4,  3,  8, 0x0),
    gsSP2Triangles( 8,  7,  4, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 9, 10,  3, 0x0,  5,  9,  3, 0x0),
    gsSP2Triangles( 7,  1,  6, 0x0,  7, 11,  1, 0x0),
    gsSP2Triangles(12,  2,  1, 0x0, 12,  1, 11, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  0, 13, 14, 0x0),
    gsSP2Triangles(15,  6,  0, 0x0, 15,  5,  6, 0x0),
    gsSPVertex(monty_mole_seg5_vertex_050041B8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 6,  7,  0, 0x0,  0,  7,  8, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  0,  8,  5, 0x0),
    gsSP2Triangles( 2,  9, 10, 0x0, 10,  6,  2, 0x0),
    gsSP2Triangles( 6, 10, 11, 0x0, 11,  7,  6, 0x0),
    gsSP2Triangles( 7, 12,  8, 0x0, 13, 14, 11, 0x0),
    gsSP2Triangles(11,  9, 13, 0x0, 15, 14, 13, 0x0),
    gsSP2Triangles( 7, 14, 12, 0x0, 11, 14,  7, 0x0),
    gsSP1Triangle(15, 12, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x050044B0 - 0x05004518
const Gfx monty_mole_seg5_dl_050044B0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_050042B8),
    gsSPDisplayList(monty_mole_seg5_dl_05004368),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05004518
static const Lights1 monty_mole_seg5_lights_05004518 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004530
static const Vtx monty_mole_seg5_vertex_05004530[] = {
    {{{    48,    140,      0}, 0, {   474,    386}, {0x1b, 0x7c, 0x00, 0xff}}},
    {{{    57,    101,      0}, 0, {   472,    156}, {0x5e, 0x54, 0x00, 0xff}}},
    {{{    34,     99,    -34}, 0, {    96,    420}, {0x23, 0x56, 0xaa, 0xff}}},
    {{{     2,    111,     11}, 0, {   600,    742}, {0xc9, 0x65, 0x34, 0xff}}},
    {{{     2,    111,    -10}, 0, {   352,    764}, {0xbf, 0x63, 0xd4, 0xff}}},
    {{{    34,     99,     35}, 0, {   852,    352}, {0x23, 0x56, 0x55, 0xff}}},
};

// 0x05004590
static const Vtx monty_mole_seg5_vertex_05004590[] = {
    {{{    34,     99,    -34}, 0, {   404,    956}, {0x23, 0x56, 0xaa, 0xff}}},
    {{{    57,    101,      0}, 0, {  -182,    680}, {0x5e, 0x54, 0x00, 0xff}}},
    {{{   100,     66,    -27}, 0, {   244,    -20}, {0x53, 0x52, 0xd0, 0xff}}},
    {{{    63,     34,    -76}, 0, {  1210,    238}, {0x3c, 0x1f, 0x96, 0xff}}},
    {{{    34,     99,     35}, 0, {   334,    944}, {0x23, 0x56, 0x55, 0xff}}},
    {{{    63,     34,     77}, 0, {  1304,    266}, {0x3c, 0x2c, 0x66, 0xff}}},
    {{{   100,     66,     28}, 0, {   306,     26}, {0x5a, 0x4e, 0x28, 0xff}}},
    {{{    57,    101,      0}, 0, {  -212,    636}, {0x5e, 0x54, 0x00, 0xff}}},
    {{{   100,     66,     28}, 0, {   288,    356}, {0x5a, 0x4e, 0x28, 0xff}}},
    {{{   100,     66,    -27}, 0, {   176,    356}, {0x53, 0x52, 0xd0, 0xff}}},
    {{{    57,    101,      0}, 0, {   206,    400}, {0x5e, 0x54, 0x00, 0xff}}},
    {{{   118,     28,    -28}, 0, {   204,    308}, {0x77, 0x1b, 0xdf, 0xff}}},
    {{{   118,     28,     29}, 0, {   318,    308}, {0x6d, 0x17, 0x3b, 0xff}}},
    {{{    63,     34,     77}, 0, {   408,    316}, {0x3c, 0x2c, 0x66, 0xff}}},
    {{{    63,     34,    -76}, 0, {   102,    316}, {0x3c, 0x1f, 0x96, 0xff}}},
    {{{   118,    -22,     29}, 0, {   356,    246}, {0x71, 0xcf, 0x1b, 0xff}}},
};

// 0x05004690
static const Vtx monty_mole_seg5_vertex_05004690[] = {
    {{{   118,     28,    -28}, 0, {   204,    308}, {0x77, 0x1b, 0xdf, 0xff}}},
    {{{   118,    -22,     29}, 0, {   356,    246}, {0x71, 0xcf, 0x1b, 0xff}}},
    {{{   118,    -22,    -28}, 0, {   240,    246}, {0x6b, 0xdc, 0xc8, 0xff}}},
    {{{    63,     34,    -76}, 0, {   102,    316}, {0x3c, 0x1f, 0x96, 0xff}}},
    {{{    71,    -36,     77}, 0, {   462,    228}, {0x3e, 0xe2, 0x6a, 0xff}}},
    {{{   118,     28,     29}, 0, {   318,    308}, {0x6d, 0x17, 0x3b, 0xff}}},
    {{{    63,     34,     77}, 0, {   408,    316}, {0x3c, 0x2c, 0x66, 0xff}}},
    {{{    -4,    -80,     47}, 0, {   434,    174}, {0xd6, 0x8d, 0x1e, 0xff}}},
    {{{    -7,    -37,     94}, 0, {   496,    228}, {0xe5, 0xc1, 0x6a, 0xff}}},
    {{{   -35,    -41,     55}, 0, {   422,    222}, {0x91, 0xdf, 0x32, 0xff}}},
    {{{     0,     44,     94}, 0, {   436,    330}, {0xe7, 0x30, 0x72, 0xff}}},
    {{{    69,    -67,     40}, 0, {   412,    190}, {0x33, 0x97, 0x30, 0xff}}},
    {{{    -7,    -37,    -93}, 0, {   122,    228}, {0xd9, 0xdf, 0x8d, 0xff}}},
    {{{     0,     44,    -93}, 0, {    60,    330}, {0xf0, 0x40, 0x94, 0xff}}},
    {{{    69,    -67,    -39}, 0, {   252,    190}, {0x3a, 0x93, 0xe8, 0xff}}},
    {{{    -4,    -80,    -46}, 0, {   248,    174}, {0xe6, 0x91, 0xca, 0xff}}},
};

// 0x05004790
static const Vtx monty_mole_seg5_vertex_05004790[] = {
    {{{   -35,    -41,    -54}, 0, {   202,    222}, {0x8c, 0xd5, 0xe7, 0xff}}},
    {{{    -4,    -80,    -46}, 0, {   248,    174}, {0xe6, 0x91, 0xca, 0xff}}},
    {{{    -4,    -80,     47}, 0, {   434,    174}, {0xd6, 0x8d, 0x1e, 0xff}}},
    {{{   -35,    -41,     55}, 0, {   422,    222}, {0x91, 0xdf, 0x32, 0xff}}},
    {{{   118,    -22,     29}, 0, {   356,    246}, {0x71, 0xcf, 0x1b, 0xff}}},
    {{{    69,    -67,    -39}, 0, {   252,    190}, {0x3a, 0x93, 0xe8, 0xff}}},
    {{{   118,    -22,    -28}, 0, {   240,    246}, {0x6b, 0xdc, 0xc8, 0xff}}},
    {{{    71,    -36,    -76}, 0, {   156,    228}, {0x37, 0xc8, 0x9d, 0xff}}},
    {{{    63,     34,    -76}, 0, {   102,    316}, {0x3c, 0x1f, 0x96, 0xff}}},
    {{{    -7,    -37,    -93}, 0, {   122,    228}, {0xd9, 0xdf, 0x8d, 0xff}}},
};

// 0x05004830
static const Vtx monty_mole_seg5_vertex_05004830[] = {
    {{{     2,    111,    -10}, 0, {   898,    558}, {0xbf, 0x63, 0xd4, 0xff}}},
    {{{     0,     44,    -93}, 0, {    -8,    556}, {0xf0, 0x40, 0x94, 0xff}}},
    {{{   -36,     61,    -57}, 0, {   302,    130}, {0x97, 0x29, 0xc8, 0xff}}},
    {{{    34,     99,    -34}, 0, {   696,    928}, {0x23, 0x56, 0xaa, 0xff}}},
    {{{     0,     44,     94}, 0, {   940,    652}, {0xe7, 0x30, 0x72, 0xff}}},
    {{{     2,    111,     11}, 0, {   110,    590}, {0xc9, 0x65, 0x34, 0xff}}},
    {{{   -36,     61,     58}, 0, {   714,    246}, {0x8f, 0x2f, 0x20, 0xff}}},
    {{{    34,     99,     35}, 0, {   244,    938}, {0x23, 0x56, 0x55, 0xff}}},
    {{{    63,     34,     77}, 0, {   764,   1264}, {0x3c, 0x2c, 0x66, 0xff}}},
    {{{    63,     34,    -76}, 0, {    82,   1240}, {0x3c, 0x1f, 0x96, 0xff}}},
    {{{    -7,    -37,    -93}, 0, {    60,   1826}, {0xd9, 0xdf, 0x8d, 0xff}}},
    {{{   -35,    -41,    -54}, 0, {   244,   1890}, {0x8c, 0xd5, 0xe7, 0xff}}},
    {{{   -36,     61,    -57}, 0, {   186,    412}, {0x97, 0x29, 0xc8, 0xff}}},
    {{{     0,     44,    -93}, 0, {    20,    646}, {0xf0, 0x40, 0x94, 0xff}}},
    {{{   -36,     61,     58}, 0, {   742,    440}, {0x8f, 0x2f, 0x20, 0xff}}},
    {{{   -35,    -41,     55}, 0, {   772,   1916}, {0x91, 0xdf, 0x32, 0xff}}},
};

// 0x05004930
static const Vtx monty_mole_seg5_vertex_05004930[] = {
    {{{     0,     44,     94}, 0, {   922,    692}, {0xe7, 0x30, 0x72, 0xff}}},
    {{{   -35,    -41,     55}, 0, {   772,   1916}, {0x91, 0xdf, 0x32, 0xff}}},
    {{{    -7,    -37,     94}, 0, {   952,   1870}, {0xe5, 0xc1, 0x6a, 0xff}}},
    {{{   -36,     61,     58}, 0, {   742,    440}, {0x8f, 0x2f, 0x20, 0xff}}},
};

// 0x05004970
static const Vtx monty_mole_seg5_vertex_05004970[] = {
    {{{   -36,     61,     58}, 0, {     0,      0}, {0x8f, 0x2f, 0x20, 0xff}}},
    {{{     2,    111,     11}, 0, {     0,      0}, {0xc9, 0x65, 0x34, 0xff}}},
    {{{     2,    111,    -10}, 0, {     0,      0}, {0xbf, 0x63, 0xd4, 0xff}}},
    {{{   -36,     61,    -57}, 0, {     0,      0}, {0x97, 0x29, 0xc8, 0xff}}},
};

// 0x050049B0 - 0x05004A10
const Gfx monty_mole_seg5_dl_050049B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05004518.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05004518.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05004530, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  3,  5, 0x0,  5,  1,  0, 0x0),
    gsSP1Triangle( 2,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004A10 - 0x05004B50
const Gfx monty_mole_seg5_dl_05004A10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05001170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_05004590, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  8, 12, 0x0),
    gsSP2Triangles(12,  8, 13, 0x0, 11,  9,  8, 0x0),
    gsSP2Triangles(14,  9, 11, 0x0, 11, 12, 15, 0x0),
    gsSPVertex(monty_mole_seg5_vertex_05004690, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  1,  5, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  8,  4, 0x0),
    gsSP2Triangles( 8,  7, 11, 0x0,  8, 11,  4, 0x0),
    gsSP2Triangles( 4, 11,  1, 0x0, 10,  4,  6, 0x0),
    gsSP2Triangles(12, 13,  3, 0x0,  7, 14, 11, 0x0),
    gsSP2Triangles( 1, 11, 14, 0x0,  7, 15, 14, 0x0),
    gsSPVertex(monty_mole_seg5_vertex_05004790, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 7,  5,  1, 0x0,  8,  6,  7, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0,  7,  1,  9, 0x0),
    gsSP1Triangle( 9,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004B50 - 0x05004BE0
const Gfx monty_mole_seg5_dl_05004B50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05000970),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(monty_mole_seg5_vertex_05004830, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 4,  8,  7, 0x0,  3,  9,  1, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0, 11, 15, 14, 0x0),
    gsSPVertex(monty_mole_seg5_vertex_05004930, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004BE0 - 0x05004C00
const Gfx monty_mole_seg5_dl_05004BE0[] = {
    gsSPVertex(monty_mole_seg5_vertex_05004970, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004C00 - 0x05004C90
const Gfx monty_mole_seg5_dl_05004C00[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_050049B0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05004A10),
    gsSPDisplayList(monty_mole_seg5_dl_05004B50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(monty_mole_seg5_dl_05004BE0),
    gsSPEndDisplayList(),
};

// 0x05004C90
static const Lights1 monty_mole_seg5_lights_05004C90 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004CA8
static const Vtx monty_mole_seg5_vertex_05004CA8[] = {
    {{{     3,     99,    -25}, 0, {  -154,    -14}, {0xed, 0x7d, 0x00, 0xff}}},
    {{{   -42,     92,    -12}, 0, {   160,    980}, {0xed, 0x7d, 0x00, 0xff}}},
    {{{   -42,     92,     13}, 0, {   800,    978}, {0xed, 0x7d, 0x00, 0xff}}},
    {{{     3,     99,     26}, 0, {  1092,    -18}, {0xed, 0x7d, 0x00, 0xff}}},
};

// 0x05004CE8 - 0x05004D30
const Gfx monty_mole_seg5_dl_05004CE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, monty_mole_seg5_texture_05002170),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&monty_mole_seg5_lights_05004C90.l, 1),
    gsSPLight(&monty_mole_seg5_lights_05004C90.a, 2),
    gsSPVertex(monty_mole_seg5_vertex_05004CA8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004D30 - 0x05004DA0
const Gfx monty_mole_seg5_dl_05004D30[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(monty_mole_seg5_dl_05004CE8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
