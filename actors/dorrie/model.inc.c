// Dorrie

// 0x06009BA0
ALIGNED8 static const Texture dorrie_seg6_texture_06009BA0[] = {
#include "actors/dorrie/dorrie_eye.rgba16.inc.c"
};

// 0x06009DA0
ALIGNED8 static const Texture dorrie_seg6_texture_06009DA0[] = {
#include "actors/dorrie/dorrie_skin.rgba16.inc.c"
};

// 0x0600ADA0
ALIGNED8 static const Texture dorrie_seg6_texture_0600ADA0[] = {
#include "actors/dorrie/dorrie_tongue.rgba16.inc.c"
};

// 0x0600B5A0
static const Lights1 dorrie_seg6_lights_0600B5A0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600B5B8
static const Vtx dorrie_seg6_vertex_0600B5B8[] = {
    {{{   -22,   1201,   -234}, 0, {   672,    994}, {0xc5, 0x6a, 0xdd, 0xff}}},
    {{{   -51,    746,    492}, 0, {   -52,    582}, {0xad, 0x15, 0x5d, 0xff}}},
    {{{   -22,   1201,    234}, 0, {   204,    994}, {0xd6, 0x68, 0x3a, 0xff}}},
    {{{   -51,    746,   -493}, 0, {   930,    582}, {0xd7, 0x20, 0x8d, 0xff}}},
    {{{    55,    376,   -516}, 0, {   952,    216}, {0x9e, 0x17, 0xb4, 0xff}}},
    {{{    55,    376,    515}, 0, {   -76,    216}, {0xb8, 0x10, 0x67, 0xff}}},
};

// 0x0600B618
static const Vtx dorrie_seg6_vertex_0600B618[] = {
    {{{   313,    758,   -422}, 0, {   606,    720}, {0x42, 0x27, 0x9b, 0xff}}},
    {{{   345,   1135,   -185}, 0, {   568,    464}, {0x45, 0x50, 0xbb, 0xff}}},
    {{{   487,    752,   -178}, 0, {   540,    696}, {0x68, 0x38, 0xd4, 0xff}}},
    {{{   -22,   1201,   -234}, 0, {   684,    468}, {0xc5, 0x6a, 0xdd, 0xff}}},
    {{{   345,   1135,    185}, 0, {   452,    456}, {0x4b, 0x58, 0x32, 0xff}}},
    {{{   -51,    746,   -493}, 0, {   690,    776}, {0xd7, 0x20, 0x8d, 0xff}}},
    {{{   349,    372,   -483}, 0, {   598,    968}, {0x2c, 0x13, 0x8b, 0xff}}},
    {{{    55,    376,   -516}, 0, {   650,   1004}, {0x9e, 0x17, 0xb4, 0xff}}},
    {{{   -51,    746,    492}, 0, {   288,    756}, {0xad, 0x15, 0x5d, 0xff}}},
    {{{   313,    758,    421}, 0, {   388,    700}, {0x42, 0x26, 0x65, 0xff}}},
    {{{   487,    752,    177}, 0, {   460,    688}, {0x61, 0x43, 0x2c, 0xff}}},
    {{{   -22,   1201,    234}, 0, {   338,    460}, {0xd6, 0x68, 0x3a, 0xff}}},
    {{{   349,    372,    482}, 0, {   388,    948}, {0x2c, 0x14, 0x75, 0xff}}},
    {{{    55,    376,    515}, 0, {   332,    980}, {0xb8, 0x10, 0x67, 0xff}}},
    {{{    91,     29,    515}, 0, {   350,   1204}, {0xf2, 0xe8, 0x7b, 0xff}}},
    {{{   778,    532,    166}, 0, {   466,    796}, {0x6b, 0x25, 0x38, 0xff}}},
};

// 0x0600B718
static const Vtx dorrie_seg6_vertex_0600B718[] = {
    {{{   778,    532,    166}, 0, {   466,    796}, {0x6b, 0x25, 0x38, 0xff}}},
    {{{   349,    372,    482}, 0, {   388,    948}, {0x2c, 0x14, 0x75, 0xff}}},
    {{{   568,     -9,    418}, 0, {   420,   1172}, {0x3b, 0xdc, 0x6a, 0xff}}},
    {{{   778,    532,   -167}, 0, {   520,    804}, {0x62, 0x39, 0xc8, 0xff}}},
    {{{   487,    752,    177}, 0, {   460,    688}, {0x61, 0x43, 0x2c, 0xff}}},
    {{{   793,    -43,    150}, 0, {   468,   1172}, {0x69, 0xc3, 0x22, 0xff}}},
    {{{   793,    -43,   -151}, 0, {   510,   1176}, {0x73, 0xda, 0xdd, 0xff}}},
    {{{   487,    752,   -178}, 0, {   540,    696}, {0x68, 0x38, 0xd4, 0xff}}},
    {{{   568,     -9,   -418}, 0, {   558,   1188}, {0x3b, 0xdc, 0x96, 0xff}}},
    {{{   349,    372,   -483}, 0, {   598,    968}, {0x2c, 0x13, 0x8b, 0xff}}},
    {{{    91,     29,   -516}, 0, {   628,   1228}, {0xf2, 0xe8, 0x85, 0xff}}},
    {{{    55,    376,   -516}, 0, {   650,   1004}, {0x9e, 0x17, 0xb4, 0xff}}},
    {{{    91,     29,    515}, 0, {   350,   1204}, {0xf2, 0xe8, 0x7b, 0xff}}},
    {{{    46,   -407,    300}, 0, {   402,   1500}, {0xe5, 0x93, 0x39, 0xff}}},
    {{{   484,   -303,    184}, 0, {   456,   1380}, {0x3d, 0x9a, 0x2a, 0xff}}},
};

// 0x0600B808
static const Vtx dorrie_seg6_vertex_0600B808[] = {
    {{{    91,     29,   -516}, 0, {   628,   1228}, {0xf2, 0xe8, 0x85, 0xff}}},
    {{{    46,   -407,   -301}, 0, {   582,   1512}, {0xee, 0x9e, 0xb3, 0xff}}},
    {{{  -269,     26,   -300}, 0, {   696,   1268}, {0x92, 0xe3, 0xca, 0xff}}},
    {{{    55,    376,   -516}, 0, {   650,   1004}, {0x9e, 0x17, 0xb4, 0xff}}},
    {{{   568,     -9,   -418}, 0, {   558,   1188}, {0x3b, 0xdc, 0x96, 0xff}}},
    {{{  -269,     26,    299}, 0, {   284,   1256}, {0x90, 0x0c, 0x39, 0xff}}},
    {{{    55,    376,    515}, 0, {   332,    980}, {0xb8, 0x10, 0x67, 0xff}}},
    {{{    91,     29,    515}, 0, {   350,   1204}, {0xf2, 0xe8, 0x7b, 0xff}}},
    {{{    46,   -407,    300}, 0, {   402,   1500}, {0xe5, 0x93, 0x39, 0xff}}},
    {{{   484,   -303,   -185}, 0, {   522,   1388}, {0x3c, 0x95, 0xe4, 0xff}}},
    {{{   793,    -43,   -151}, 0, {   510,   1176}, {0x73, 0xda, 0xdd, 0xff}}},
    {{{   568,     -9,    418}, 0, {   420,   1172}, {0x3b, 0xdc, 0x6a, 0xff}}},
    {{{   484,   -303,    184}, 0, {   456,   1380}, {0x3d, 0x9a, 0x2a, 0xff}}},
    {{{   793,    -43,    150}, 0, {   468,   1172}, {0x69, 0xc3, 0x22, 0xff}}},
};

// 0x0600B8E8 - 0x0600B940
const Gfx dorrie_seg6_dl_0600B8E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_0600ADA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600B5A0.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600B5A0.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600B5B8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600B940 - 0x0600BAF8
const Gfx dorrie_seg6_dl_0600B940[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dorrie_seg6_vertex_0600B618, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  5,  0,  6, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0,  7,  5,  6, 0x0),
    gsSP2Triangles( 8,  9,  4, 0x0, 10,  4,  9, 0x0),
    gsSP2Triangles( 3, 11,  4, 0x0,  4, 11,  8, 0x0),
    gsSP2Triangles( 2,  4, 10, 0x0,  8, 12,  9, 0x0),
    gsSP2Triangles(12,  8, 13, 0x0, 10,  9, 12, 0x0),
    gsSP2Triangles(13, 14, 12, 0x0, 12, 15, 10, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600B718, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 2,  5,  0, 0x0,  0,  6,  3, 0x0),
    gsSP2Triangles( 0,  5,  6, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 8,  9,  3, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0,  7,  3,  9, 0x0),
    gsSP2Triangles( 3,  6,  8, 0x0, 12,  2,  1, 0x0),
    gsSP1Triangle(13, 14,  2, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600B808, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  2,  5, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 2,  1,  8, 0x0,  4,  9,  1, 0x0),
    gsSP2Triangles( 8,  1,  9, 0x0, 10,  9,  4, 0x0),
    gsSP2Triangles(11,  7,  8, 0x0,  2,  8,  5, 0x0),
    gsSP2Triangles( 8,  9, 12, 0x0,  8,  7,  5, 0x0),
    gsSP2Triangles(13,  9, 10, 0x0, 13, 12,  9, 0x0),
    gsSP1Triangle(11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BAF8 - 0x0600BBC0
const Gfx dorrie_seg6_dl_0600BAF8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600B8E8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600B940),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600BBC0
static const Lights1 dorrie_seg6_lights_0600BBC0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600BBD8
static const Vtx dorrie_seg6_vertex_0600BBD8[] = {
    {{{   442,     17,   -243}, 0, {   220,    696}, {0xdb, 0x88, 0x0b, 0xff}}},
    {{{   442,     17,    253}, 0, {   716,    696}, {0xdf, 0x86, 0xf6, 0xff}}},
    {{{  -109,    -18,    237}, 0, {   700,    208}, {0xed, 0x83, 0x06, 0xff}}},
    {{{  -109,    -18,   -227}, 0, {   236,    208}, {0xf0, 0x83, 0xfd, 0xff}}},
    {{{   390,    -29,   -460}, 0, {     2,    658}, {0x21, 0xb6, 0x9f, 0xff}}},
    {{{   677,   -171,    253}, 0, {   716,    936}, {0x26, 0x89, 0x14, 0xff}}},
    {{{   677,   -171,   -243}, 0, {   220,    936}, {0x21, 0x8b, 0xdf, 0xff}}},
    {{{   390,    -29,    470}, 0, {   932,    658}, {0x27, 0xd0, 0x6e, 0xff}}},
    {{{   -93,     25,    516}, 0, {   978,    216}, {0xf2, 0x9b, 0x4b, 0xff}}},
    {{{  -345,     87,    467}, 0, {   928,    -20}, {0xa2, 0xda, 0x4c, 0xff}}},
    {{{  -345,     87,   -456}, 0, {     6,    -20}, {0x99, 0xcf, 0xcb, 0xff}}},
    {{{   -93,     23,   -481}, 0, {   -18,    216}, {0xef, 0xaf, 0xa1, 0xff}}},
};

// 0x0600BC98
static const Vtx dorrie_seg6_vertex_0600BC98[] = {
    {{{  -272,    375,    253}, 0, {   -80,   1972}, {0xbc, 0x66, 0x1c, 0xff}}},
    {{{  -345,     87,   -456}, 0, {    58,   2052}, {0x99, 0xcf, 0xcb, 0xff}}},
    {{{  -345,     87,    467}, 0, {  -122,   2052}, {0xa2, 0xda, 0x4c, 0xff}}},
    {{{   456,     72,    416}, 0, {  -112,   1164}, {0x44, 0x35, 0x5c, 0xff}}},
    {{{   708,      9,    173}, 0, {   -64,    884}, {0x6b, 0x24, 0x39, 0xff}}},
    {{{   498,    236,    173}, 0, {   -64,   1120}, {0x43, 0x67, 0x1d, 0xff}}},
    {{{   -28,    276,    501}, 0, {  -128,   1704}, {0x09, 0x48, 0x68, 0xff}}},
    {{{     3,    403,   -243}, 0, {    16,   1668}, {0x0f, 0x7c, 0xec, 0xff}}},
    {{{   498,    236,   -165}, 0, {     0,   1120}, {0x41, 0x64, 0xd7, 0xff}}},
    {{{     3,    403,    253}, 0, {   -80,   1668}, {0x0f, 0x7a, 0x1e, 0xff}}},
    {{{   708,      9,   -164}, 0, {     0,    884}, {0x72, 0x24, 0xd7, 0xff}}},
    {{{   390,    -29,    470}, 0, {  -122,   1240}, {0x27, 0xd0, 0x6e, 0xff}}},
    {{{   -93,     25,    516}, 0, {  -132,   1776}, {0xf2, 0x9b, 0x4b, 0xff}}},
    {{{   -93,     23,   -481}, 0, {    62,   1776}, {0xef, 0xaf, 0xa1, 0xff}}},
    {{{   -28,    276,   -491}, 0, {    64,   1704}, {0x09, 0x44, 0x96, 0xff}}},
    {{{   677,   -171,    253}, 0, {   -80,    920}, {0x26, 0x89, 0x14, 0xff}}},
};

// 0x0600BD98
static const Vtx dorrie_seg6_vertex_0600BD98[] = {
    {{{   498,    236,   -165}, 0, {     0,   1120}, {0x41, 0x64, 0xd7, 0xff}}},
    {{{   456,     72,   -407}, 0, {    48,   1164}, {0x44, 0x34, 0xa4, 0xff}}},
    {{{   -28,    276,   -491}, 0, {    64,   1704}, {0x09, 0x44, 0x96, 0xff}}},
    {{{  -272,    375,   -243}, 0, {    16,   1972}, {0xc4, 0x67, 0xd6, 0xff}}},
    {{{     3,    403,   -243}, 0, {    16,   1668}, {0x0f, 0x7c, 0xec, 0xff}}},
    {{{   390,    -29,   -460}, 0, {    58,   1240}, {0x21, 0xb6, 0x9f, 0xff}}},
    {{{   -93,     23,   -481}, 0, {    62,   1776}, {0xef, 0xaf, 0xa1, 0xff}}},
    {{{  -272,    375,    253}, 0, {   -80,   1972}, {0xbc, 0x66, 0x1c, 0xff}}},
    {{{   708,      9,   -164}, 0, {     0,    884}, {0x72, 0x24, 0xd7, 0xff}}},
    {{{   708,      9,    173}, 0, {   -64,    884}, {0x6b, 0x24, 0x39, 0xff}}},
    {{{   677,   -171,    253}, 0, {   -80,    920}, {0x26, 0x89, 0x14, 0xff}}},
    {{{   677,   -171,   -243}, 0, {    16,    920}, {0x21, 0x8b, 0xdf, 0xff}}},
    {{{  -345,     87,   -456}, 0, {    58,   2052}, {0x99, 0xcf, 0xcb, 0xff}}},
};

// 0x0600BE68 - 0x0600BF10
const Gfx dorrie_seg6_dl_0600BE68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_0600ADA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600BBC0.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600BBC0.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600BBD8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  6,  5, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 1,  5,  7, 0x0,  8,  1,  7, 0x0),
    gsSP2Triangles( 8,  2,  1, 0x0,  8,  9,  2, 0x0),
    gsSP2Triangles( 2, 10,  3, 0x0,  2,  9, 10, 0x0),
    gsSP2Triangles( 3, 10, 11, 0x0,  4,  3, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600BF10 - 0x0600C030
const Gfx dorrie_seg6_dl_0600BF10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dorrie_seg6_vertex_0600BC98, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 7,  9,  5, 0x0,  5,  9,  6, 0x0),
    gsSP2Triangles( 5, 10,  8, 0x0,  5,  4, 10, 0x0),
    gsSP2Triangles( 0,  6,  9, 0x0,  7,  0,  9, 0x0),
    gsSP2Triangles(11,  3,  6, 0x0,  6, 12, 11, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0,  6,  2, 12, 0x0),
    gsSP2Triangles(13,  1, 14, 0x0, 14,  7,  8, 0x0),
    gsSP2Triangles( 4,  3, 11, 0x0, 11, 15,  4, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600BD98, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 4,  3,  7, 0x0,  8,  1,  0, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles( 5,  1,  8, 0x0,  8, 11,  5, 0x0),
    gsSP2Triangles( 7,  3, 12, 0x0, 12,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600C030 - 0x0600C0F8
const Gfx dorrie_seg6_dl_0600C030[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600BE68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600BF10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600C0F8
static const Lights1 dorrie_seg6_lights_0600C0F8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600C110
static const Vtx dorrie_seg6_vertex_0600C110[] = {
    {{{  1090,    -93,    204}, 0, {   340,    152}, {0x64, 0xc3, 0x30, 0xff}}},
    {{{   852,   -373,   -231}, 0, {   558,    336}, {0x3f, 0x98, 0xdf, 0xff}}},
    {{{  1090,    -93,   -205}, 0, {   612,    144}, {0x5c, 0xe0, 0xaf, 0xff}}},
    {{{    48,   -514,    297}, 0, {   400,    948}, {0xdb, 0x8c, 0x21, 0xff}}},
    {{{    30,   -226,    563}, 0, {   288,    952}, {0xe6, 0xd2, 0x73, 0xff}}},
    {{{  -192,   -274,    297}, 0, {   350,   1112}, {0x96, 0xdd, 0x3b, 0xff}}},
    {{{    -4,    324,    424}, 0, {   110,    940}, {0xca, 0x3a, 0x62, 0xff}}},
    {{{   837,   -121,    436}, 0, {   290,    348}, {0x33, 0xfa, 0x74, 0xff}}},
    {{{   852,   -373,    230}, 0, {   400,    348}, {0x37, 0xa0, 0x3c, 0xff}}},
    {{{   809,    286,    328}, 0, {   128,    340}, {0x1a, 0x5b, 0x54, 0xff}}},
    {{{  1064,    288,    204}, 0, {   102,    148}, {0x3f, 0x51, 0x4a, 0xff}}},
    {{{    48,   -514,   -298}, 0, {   572,    932}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{  -192,   -274,   -298}, 0, {   630,   1096}, {0x9b, 0xc1, 0xd5, 0xff}}},
    {{{   768,    421,     -1}, 0, {   990,    352}, {0x19, 0x7c, 0xf7, 0xff}}},
    {{{    -4,    324,   -425}, 0, {   836,    916}, {0xd5, 0x56, 0xae, 0xff}}},
    {{{   -19,    548,      0}, 0, {   982,    924}, {0xdb, 0x78, 0x0b, 0xff}}},
};

// 0x0600C210
static const Vtx dorrie_seg6_vertex_0600C210[] = {
    {{{  1090,    -93,   -205}, 0, {   612,    144}, {0x5c, 0xe0, 0xaf, 0xff}}},
    {{{   852,   -373,   -231}, 0, {   558,    336}, {0x3f, 0x98, 0xdf, 0xff}}},
    {{{   837,   -121,   -437}, 0, {   668,    324}, {0x32, 0xda, 0x93, 0xff}}},
    {{{    48,   -514,   -298}, 0, {   572,    932}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{   811,    287,   -349}, 0, {   826,    320}, {0x27, 0x3e, 0x99, 0xff}}},
    {{{  1064,    288,   -205}, 0, {   864,    136}, {0x37, 0x5f, 0xc3, 0xff}}},
    {{{    30,   -226,   -564}, 0, {   676,    920}, {0xd0, 0xf5, 0x8c, 0xff}}},
    {{{  -192,   -274,   -298}, 0, {   630,   1096}, {0x9b, 0xc1, 0xd5, 0xff}}},
    {{{    -4,    324,   -425}, 0, {   836,    916}, {0xd5, 0x56, 0xae, 0xff}}},
    {{{  -227,    255,      0}, 0, {   968,   1096}, {0x8c, 0x2e, 0xef, 0xff}}},
    {{{   768,    421,     -1}, 0, {   990,    352}, {0x19, 0x7c, 0xf7, 0xff}}},
    {{{  1064,    288,    204}, 0, {  1124,    148}, {0x3f, 0x51, 0x4a, 0xff}}},
    {{{    -4,    324,    424}, 0, {  1132,    940}, {0xca, 0x3a, 0x62, 0xff}}},
    {{{   -19,    548,      0}, 0, {   982,    924}, {0xdb, 0x78, 0x0b, 0xff}}},
    {{{  -192,   -274,    297}, 0, {  1372,   1112}, {0x96, 0xdd, 0x3b, 0xff}}},
    {{{   809,    286,    328}, 0, {  1150,    340}, {0x1a, 0x5b, 0x54, 0xff}}},
};

// 0x0600C310 - 0x0600C468
const Gfx dorrie_seg6_dl_0600C310[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600C0F8.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600C0F8.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600C110, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  4,  8,  7, 0x0),
    gsSP2Triangles( 4,  3,  8, 0x0,  6,  5,  4, 0x0),
    gsSP2Triangles( 7,  8,  0, 0x0,  3,  1,  8, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  6,  7,  9, 0x0),
    gsSP2Triangles( 7, 10,  9, 0x0,  7,  0, 10, 0x0),
    gsSP2Triangles( 3, 11,  1, 0x0, 12, 11,  3, 0x0),
    gsSP2Triangles(12,  3,  5, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600C210, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  4,  5,  0, 0x0),
    gsSP2Triangles( 6,  4,  2, 0x0,  2,  3,  6, 0x0),
    gsSP2Triangles( 6,  3,  7, 0x0,  6,  8,  4, 0x0),
    gsSP2Triangles( 6,  7,  9, 0x0,  6,  9,  8, 0x0),
    gsSP2Triangles(10,  4,  8, 0x0,  4, 10,  5, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0, 12, 13,  9, 0x0),
    gsSP2Triangles( 9, 13,  8, 0x0, 12,  9, 14, 0x0),
    gsSP2Triangles(11, 10, 15, 0x0, 15, 13, 12, 0x0),
    gsSP1Triangle(15, 10, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600C468 - 0x0600C510
const Gfx dorrie_seg6_dl_0600C468[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600C310),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600C510
static const Lights1 dorrie_seg6_lights_0600C510 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600C528
static const Vtx dorrie_seg6_vertex_0600C528[] = {
    {{{    19,    -22,   -821}, 0, {   674,   1540}, {0xea, 0xfb, 0x84, 0xff}}},
    {{{   187,    693,   -578}, 0, {   822,   1620}, {0x1c, 0x5d, 0xb0, 0xff}}},
    {{{  1057,    309,   -449}, 0, {   834,    920}, {0x50, 0x27, 0xa6, 0xff}}},
    {{{  1290,    174,      0}, 0, {   980,    736}, {0x79, 0x1f, 0x15, 0xff}}},
    {{{  1106,    540,      0}, 0, {   982,    960}, {0x4f, 0x62, 0xf9, 0xff}}},
    {{{  1057,    309,    440}, 0, {  1134,    948}, {0x4e, 0x3f, 0x4c, 0xff}}},
    {{{   391,    797,      0}, 0, {   972,   1524}, {0x16, 0x7c, 0x0b, 0xff}}},
    {{{   187,    693,    571}, 0, {  1140,   1652}, {0x1c, 0x4c, 0x61, 0xff}}},
    {{{   899,   -211,    569}, 0, {  1306,    920}, {0x3f, 0xef, 0x6c, 0xff}}},
    {{{  -273,    717,    553}, 0, {  1170,   1976}, {0xdf, 0x5f, 0x4c, 0xff}}},
    {{{  -273,    717,   -552}, 0, {   792,   1944}, {0xde, 0x5f, 0xb4, 0xff}}},
    {{{  -423,     59,   -383}, 0, {   612,   1876}, {0x8c, 0xef, 0xd0, 0xff}}},
    {{{  -343,   -381,    386}, 0, {   418,   1728}, {0xaf, 0xa6, 0x25, 0xff}}},
    {{{    19,    -22,    822}, 0, {   294,   1584}, {0xe4, 0xe8, 0x79, 0xff}}},
    {{{  -423,     59,    384}, 0, {   378,   1900}, {0x94, 0xf2, 0x3f, 0xff}}},
    {{{  1185,   -300,   -302}, 0, {   648,    676}, {0x5b, 0xc9, 0xbc, 0xff}}},
};

// 0x0600C628
static const Vtx dorrie_seg6_vertex_0600C628[] = {
    {{{   187,    693,    571}, 0, {   118,   1652}, {0x1c, 0x4c, 0x61, 0xff}}},
    {{{    19,    -22,    822}, 0, {   294,   1584}, {0xe4, 0xe8, 0x79, 0xff}}},
    {{{   899,   -211,    569}, 0, {   284,    920}, {0x3f, 0xef, 0x6c, 0xff}}},
    {{{  -343,   -381,    386}, 0, {   418,   1728}, {0xaf, 0xa6, 0x25, 0xff}}},
    {{{   832,   -489,    343}, 0, {   386,    888}, {0x17, 0x92, 0x3a, 0xff}}},
    {{{  1185,   -300,    304}, 0, {   318,    692}, {0x50, 0xad, 0x33, 0xff}}},
    {{{   832,   -489,   -340}, 0, {   584,    868}, {0x1b, 0x89, 0xdf, 0xff}}},
    {{{  1057,    309,    440}, 0, {   112,    948}, {0x4e, 0x3f, 0x4c, 0xff}}},
    {{{  -343,   -381,   -383}, 0, {   562,   1708}, {0xc5, 0xa7, 0xbc, 0xff}}},
    {{{  1185,   -300,   -302}, 0, {   648,    676}, {0x5b, 0xc9, 0xbc, 0xff}}},
    {{{   899,   -211,   -571}, 0, {   680,    888}, {0x27, 0xc8, 0x96, 0xff}}},
    {{{  1057,    309,   -449}, 0, {   834,    920}, {0x50, 0x27, 0xa6, 0xff}}},
    {{{  -273,    717,    553}, 0, {   148,   1976}, {0xdf, 0x5f, 0x4c, 0xff}}},
    {{{    19,    -22,   -821}, 0, {   674,   1540}, {0xea, 0xfb, 0x84, 0xff}}},
    {{{  -423,     59,   -383}, 0, {   612,   1876}, {0x8c, 0xef, 0xd0, 0xff}}},
    {{{  -423,     59,    384}, 0, {   378,   1900}, {0x94, 0xf2, 0x3f, 0xff}}},
};

// 0x0600C728
static const Vtx dorrie_seg6_vertex_0600C728[] = {
    {{{   899,   -211,    569}, 0, {  1306,    920}, {0x3f, 0xef, 0x6c, 0xff}}},
    {{{  1185,   -300,    304}, 0, {  1340,    692}, {0x50, 0xad, 0x33, 0xff}}},
    {{{  1290,    174,      0}, 0, {   980,    736}, {0x79, 0x1f, 0x15, 0xff}}},
};

// 0x0600C758 - 0x0600C8B8
const Gfx dorrie_seg6_dl_0600C758[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600C510.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600C510.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600C528, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 8,  3,  5, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 4,  1,  6, 0x0,  6,  1, 10, 0x0),
    gsSP2Triangles( 9,  6, 10, 0x0,  2,  4,  3, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  0, 11, 10, 0x0),
    gsSP2Triangles( 0, 10,  1, 0x0, 12, 13, 14, 0x0),
    gsSP2Triangles(11, 12, 14, 0x0,  2,  3, 15, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600C628, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 0,  2,  7, 0x0,  3,  8,  6, 0x0),
    gsSP2Triangles( 5,  6,  9, 0x0,  9,  6, 10, 0x0),
    gsSP2Triangles(10,  6,  8, 0x0, 11,  9, 10, 0x0),
    gsSP2Triangles( 1,  0, 12, 0x0, 13, 11, 10, 0x0),
    gsSP2Triangles(10,  8, 13, 0x0, 13,  8, 14, 0x0),
    gsSP2Triangles(14,  8,  3, 0x0, 12, 15,  1, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600C728, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600C8B8 - 0x0600C960
const Gfx dorrie_seg6_dl_0600C8B8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600C758),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600C960
static const Lights1 dorrie_seg6_lights_0600C960 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600C978
static const Vtx dorrie_seg6_vertex_0600C978[] = {
    {{{  1085,   -101,  -1112}, 0, {   710,    924}, {0x34, 0xd6, 0x95, 0xff}}},
    {{{   934,   -870,   -414}, 0, {   558,   1232}, {0x4e, 0xa0, 0xe8, 0xff}}},
    {{{   154,  -1257,   -586}, 0, {   584,   1788}, {0x11, 0x8e, 0xcc, 0xff}}},
    {{{   154,  -1257,    585}, 0, {   374,   1788}, {0x17, 0x8c, 0x2d, 0xff}}},
    {{{  -975,   -982,   -550}, 0, {   726,   2348}, {0xb2, 0xa8, 0xd1, 0xff}}},
    {{{  -236,   -788,  -1121}, 0, {   708,   1876}, {0xea, 0xc3, 0x93, 0xff}}},
    {{{  -854,  -1215,      0}, 0, {   480,   2344}, {0xcf, 0x8c, 0x00, 0xff}}},
    {{{  -765,    228,  -1205}, 0, {   844,   1888}, {0xd2, 0x28, 0x92, 0xff}}},
    {{{ -1340,   -279,   -695}, 0, {   870,   2356}, {0x8e, 0xf1, 0xcc, 0xff}}},
    {{{  1981,    326,   -380}, 0, {   662,    292}, {0x79, 0xf8, 0xdb, 0xff}}},
    {{{   560,    718,  -1192}, 0, {   830,    992}, {0x18, 0x34, 0x90, 0xff}}},
    {{{  1832,    492,   -639}, 0, {   742,    332}, {0x64, 0x31, 0xc4, 0xff}}},
    {{{  -236,   -788,   1119}, 0, {   250,   1876}, {0xf7, 0xcc, 0x73, 0xff}}},
    {{{  1085,   -101,   1110}, 0, {   248,    924}, {0x3c, 0xd0, 0x64, 0xff}}},
    {{{   934,   -870,    390}, 0, {   404,   1232}, {0x4e, 0xa3, 0x23, 0xff}}},
};

// 0x0600CA68
static const Vtx dorrie_seg6_vertex_0600CA68[] = {
    {{{  -765,    228,   1204}, 0, {   114,   1888}, {0xc2, 0x11, 0x6c, 0xff}}},
    {{{  -975,   -982,    549}, 0, {   232,   2348}, {0xac, 0xb1, 0x34, 0xff}}},
    {{{  -236,   -788,   1119}, 0, {   250,   1876}, {0xf7, 0xcc, 0x73, 0xff}}},
    {{{   154,  -1257,    585}, 0, {   374,   1788}, {0x17, 0x8c, 0x2d, 0xff}}},
    {{{   934,   -870,    390}, 0, {   404,   1232}, {0x4e, 0xa3, 0x23, 0xff}}},
    {{{  1981,    326,    380}, 0, {   296,    292}, {0x6f, 0xcf, 0x22, 0xff}}},
    {{{  1085,   -101,   1110}, 0, {   248,    924}, {0x3c, 0xd0, 0x64, 0xff}}},
    {{{   934,   -870,   -414}, 0, {   558,   1232}, {0x4e, 0xa0, 0xe8, 0xff}}},
    {{{   560,    718,   1191}, 0, {   126,    992}, {0x0b, 0x37, 0x71, 0xff}}},
    {{{  1832,    492,    638}, 0, {   216,    332}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{  -854,  -1215,      0}, 0, {   480,   2344}, {0xcf, 0x8c, 0x00, 0xff}}},
    {{{  1573,    878,    611}, 0, {   112,    368}, {0x4c, 0x5b, 0x2b, 0xff}}},
    {{{   581,   1130,    741}, 0, {    60,    860}, {0xf5, 0x7a, 0x1e, 0xff}}},
    {{{  -860,    411,    775}, 0, {    64,   1888}, {0xb7, 0x5f, 0x27, 0xff}}},
    {{{  1981,    326,   -380}, 0, {   662,    292}, {0x79, 0xf8, 0xdb, 0xff}}},
};

// 0x0600CB58
static const Vtx dorrie_seg6_vertex_0600CB58[] = {
    {{{   560,    718,  -1192}, 0, {   830,    992}, {0x18, 0x34, 0x90, 0xff}}},
    {{{  1573,    878,   -612}, 0, {   844,    368}, {0x3a, 0x67, 0xd5, 0xff}}},
    {{{  1832,    492,   -639}, 0, {   742,    332}, {0x64, 0x31, 0xc4, 0xff}}},
    {{{   581,   1130,   -744}, 0, {   898,    860}, {0xf0, 0x73, 0xcf, 0xff}}},
    {{{  -765,    228,  -1205}, 0, {   844,   1888}, {0xd2, 0x28, 0x92, 0xff}}},
    {{{   934,   -870,   -414}, 0, {   558,   1232}, {0x4e, 0xa0, 0xe8, 0xff}}},
    {{{  1981,    326,   -380}, 0, {   662,    292}, {0x79, 0xf8, 0xdb, 0xff}}},
    {{{  1981,    326,    380}, 0, {   296,    292}, {0x6f, 0xcf, 0x22, 0xff}}},
    {{{   581,   1130,    741}, 0, {  1082,    860}, {0xf5, 0x7a, 0x1e, 0xff}}},
    {{{  1573,    878,    611}, 0, {  1134,    368}, {0x4c, 0x5b, 0x2b, 0xff}}},
    {{{  -860,    411,   -776}, 0, {   894,   1888}, {0xae, 0x5e, 0xee, 0xff}}},
    {{{  -860,    411,    775}, 0, {  1086,   1888}, {0xb7, 0x5f, 0x27, 0xff}}},
    {{{ -1340,   -279,    694}, 0, {  1110,   2356}, {0x8a, 0x19, 0x26, 0xff}}},
    {{{ -1340,   -279,    694}, 0, {    88,   2356}, {0x8a, 0x19, 0x26, 0xff}}},
    {{{  -765,    228,   1204}, 0, {   114,   1888}, {0xc2, 0x11, 0x6c, 0xff}}},
    {{{  -860,    411,    775}, 0, {    64,   1888}, {0xb7, 0x5f, 0x27, 0xff}}},
};

// 0x0600CC58
static const Vtx dorrie_seg6_vertex_0600CC58[] = {
    {{{  -765,    228,   1204}, 0, {   114,   1888}, {0xc2, 0x11, 0x6c, 0xff}}},
    {{{ -1340,   -279,    694}, 0, {    88,   2356}, {0x8a, 0x19, 0x26, 0xff}}},
    {{{  -975,   -982,    549}, 0, {   232,   2348}, {0xac, 0xb1, 0x34, 0xff}}},
    {{{  -860,    411,   -776}, 0, {   894,   1888}, {0xae, 0x5e, 0xee, 0xff}}},
    {{{ -1340,   -279,   -695}, 0, {   870,   2356}, {0x8e, 0xf1, 0xcc, 0xff}}},
    {{{ -1340,   -279,    694}, 0, {  1110,   2356}, {0x8a, 0x19, 0x26, 0xff}}},
    {{{  -765,    228,  -1205}, 0, {   844,   1888}, {0xd2, 0x28, 0x92, 0xff}}},
    {{{ -1340,   -279,   -695}, 0, {   874,   2352}, {0x8e, 0xf1, 0xcc, 0xff}}},
    {{{  -975,   -982,   -550}, 0, {   736,   2344}, {0xb2, 0xa8, 0xd1, 0xff}}},
    {{{  -975,   -982,    549}, 0, {  1240,   2344}, {0xac, 0xb1, 0x34, 0xff}}},
    {{{  1832,    492,    638}, 0, {   698,    764}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{  1981,    326,   -380}, 0, {   352,   1476}, {0x79, 0xf8, 0xdb, 0xff}}},
    {{{  1832,    492,   -639}, 0, {   488,   1848}, {0x64, 0x31, 0xc4, 0xff}}},
    {{{  1832,    492,   -639}, 0, {   742,    332}, {0x64, 0x31, 0xc4, 0xff}}},
    {{{  1573,    878,   -612}, 0, {   844,    368}, {0x3a, 0x67, 0xd5, 0xff}}},
    {{{  1573,    878,    611}, 0, {  1134,    368}, {0x4c, 0x5b, 0x2b, 0xff}}},
};

// 0x0600CD58
static const Vtx dorrie_seg6_vertex_0600CD58[] = {
    {{{  1832,    492,   -639}, 0, {   742,    332}, {0x64, 0x31, 0xc4, 0xff}}},
    {{{  1573,    878,    611}, 0, {  1134,    368}, {0x4c, 0x5b, 0x2b, 0xff}}},
    {{{  1832,    492,    638}, 0, {  1238,    332}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{  -975,   -982,   -550}, 0, {   726,   2348}, {0xb2, 0xa8, 0xd1, 0xff}}},
    {{{  -854,  -1215,      0}, 0, {   480,   2344}, {0xcf, 0x8c, 0x00, 0xff}}},
    {{{  -975,   -982,    549}, 0, {   232,   2348}, {0xac, 0xb1, 0x34, 0xff}}},
    {{{ -1340,   -279,    694}, 0, {  1110,   2356}, {0x8a, 0x19, 0x26, 0xff}}},
    {{{ -1340,   -279,   -695}, 0, {   870,   2356}, {0x8e, 0xf1, 0xcc, 0xff}}},
    {{{  -975,   -982,    549}, 0, {  1254,   2348}, {0xac, 0xb1, 0x34, 0xff}}},
};

// 0x0600CDE8 - 0x0600CFD0
const Gfx dorrie_seg6_dl_0600CDE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600C960.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600C960.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600C978, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 3,  6,  2, 0x0,  6,  4,  2, 0x0),
    gsSP2Triangles( 7,  0,  5, 0x0,  5,  8,  7, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  0,  9,  1, 0x0),
    gsSP2Triangles( 7, 10,  0, 0x0, 11,  9,  0, 0x0),
    gsSP2Triangles(11,  0, 10, 0x0, 12,  3, 13, 0x0),
    gsSP2Triangles( 1, 14,  3, 0x0,  3, 14, 13, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600CA68, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 8,  2,  6, 0x0,  6,  5,  9, 0x0),
    gsSP2Triangles( 8,  6,  9, 0x0,  3,  1, 10, 0x0),
    gsSP2Triangles( 8, 11, 12, 0x0,  8, 12, 13, 0x0),
    gsSP2Triangles( 8, 13,  0, 0x0,  8,  0,  2, 0x0),
    gsSP2Triangles( 9, 11,  8, 0x0,  9,  5, 14, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600CB58, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  3,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  1,  3, 0x0,  8,  9,  1, 0x0),
    gsSP2Triangles(10,  8,  3, 0x0, 10, 11,  8, 0x0),
    gsSP2Triangles( 4, 10,  3, 0x0, 10, 12, 11, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600CC58, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600CD58, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600CFD0 - 0x0600D078
const Gfx dorrie_seg6_dl_0600CFD0[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600CDE8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600D078
static const Lights1 dorrie_seg6_lights_0600D078 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600D090
static const Vtx dorrie_seg6_vertex_0600D090[] = {
    {{{   324,   -369,    725}, 0, {   136,   1456}, {0x0a, 0xb0, 0x61, 0xff}}},
    {{{  1263,    236,    383}, 0, {   320,   1800}, {0x33, 0x44, 0x5d, 0xff}}},
    {{{   241,    413,    672}, 0, {   298,   1396}, {0xfb, 0x35, 0x72, 0xff}}},
    {{{   207,    735,      5}, 0, {   474,   1364}, {0x07, 0x7e, 0x09, 0xff}}},
    {{{  -240,    463,    573}, 0, {   308,   1200}, {0xd7, 0x59, 0x4f, 0xff}}},
    {{{  -150,   -380,    431}, 0, {    88,   1268}, {0xd1, 0xac, 0x51, 0xff}}},
    {{{   218,   -509,      5}, 0, {   -26,   1416}, {0x00, 0x82, 0xfc, 0xff}}},
    {{{  1312,   -229,    383}, 0, {   144,   1840}, {0x4c, 0xc7, 0x53, 0xff}}},
    {{{  1321,   -314,      5}, 0, {   -30,   1844}, {0x26, 0x87, 0x04, 0xff}}},
    {{{  -240,    463,   -562}, 0, {   644,   1196}, {0xcd, 0x43, 0xa2, 0xff}}},
    {{{   218,   -509,      5}, 0, {   996,   1416}, {0x00, 0x82, 0xfc, 0xff}}},
    {{{   324,   -369,   -714}, 0, {   824,   1448}, {0xf6, 0xbc, 0x96, 0xff}}},
    {{{  1312,   -229,   -372}, 0, {   816,   1836}, {0x3d, 0xb9, 0xac, 0xff}}},
    {{{  1321,   -314,      5}, 0, {   992,   1844}, {0x26, 0x87, 0x04, 0xff}}},
    {{{  1244,    411,      5}, 0, {   476,   1784}, {0x2e, 0x75, 0xf7, 0xff}}},
};

// 0x0600D180
static const Vtx dorrie_seg6_vertex_0600D180[] = {
    {{{   218,   -509,      5}, 0, {   996,   1416}, {0x00, 0x82, 0xfc, 0xff}}},
    {{{  -150,   -380,    431}, 0, {  1110,   1268}, {0xd1, 0xac, 0x51, 0xff}}},
    {{{  -150,   -380,   -420}, 0, {   878,   1264}, {0xd1, 0x97, 0xcc, 0xff}}},
    {{{   324,   -369,   -714}, 0, {   824,   1448}, {0xf6, 0xbc, 0x96, 0xff}}},
    {{{  -240,    463,   -562}, 0, {   644,   1196}, {0xcd, 0x43, 0xa2, 0xff}}},
    {{{  1321,   -314,      5}, 0, {   -30,   1844}, {0x26, 0x87, 0x04, 0xff}}},
    {{{  1568,   -185,      4}, 0, {   -30,   1936}, {0x68, 0xbd, 0xe6, 0xff}}},
    {{{  1312,   -229,    383}, 0, {   144,   1840}, {0x4c, 0xc7, 0x53, 0xff}}},
    {{{  1524,    227,    172}, 0, {   388,   1904}, {0x59, 0x3e, 0x40, 0xff}}},
    {{{  1263,    236,    383}, 0, {   320,   1800}, {0x33, 0x44, 0x5d, 0xff}}},
    {{{  1244,    411,      5}, 0, {   476,   1784}, {0x2e, 0x75, 0xf7, 0xff}}},
    {{{  1524,    227,   -163}, 0, {   568,   1900}, {0x4f, 0x4d, 0xc3, 0xff}}},
    {{{  1263,    236,   -372}, 0, {   636,   1796}, {0x46, 0x31, 0xa3, 0xff}}},
    {{{   241,    413,   -661}, 0, {   656,   1388}, {0x17, 0x46, 0x9a, 0xff}}},
    {{{   207,    735,      5}, 0, {   474,   1364}, {0x07, 0x7e, 0x09, 0xff}}},
    {{{  1568,   -185,      4}, 0, {   992,   1936}, {0x68, 0xbd, 0xe6, 0xff}}},
};

// 0x0600D280
static const Vtx dorrie_seg6_vertex_0600D280[] = {
    {{{  1312,   -229,   -372}, 0, {   816,   1836}, {0x3d, 0xb9, 0xac, 0xff}}},
    {{{   241,    413,   -661}, 0, {   656,   1388}, {0x17, 0x46, 0x9a, 0xff}}},
    {{{  1263,    236,   -372}, 0, {   636,   1796}, {0x46, 0x31, 0xa3, 0xff}}},
    {{{  1568,   -185,      4}, 0, {   992,   1936}, {0x68, 0xbd, 0xe6, 0xff}}},
    {{{  1321,   -314,      5}, 0, {   992,   1844}, {0x26, 0x87, 0x04, 0xff}}},
    {{{   324,   -369,   -714}, 0, {   824,   1448}, {0xf6, 0xbc, 0x96, 0xff}}},
    {{{  -240,    463,   -562}, 0, {   644,   1196}, {0xcd, 0x43, 0xa2, 0xff}}},
    {{{   207,    735,      5}, 0, {   474,   1364}, {0x07, 0x7e, 0x09, 0xff}}},
};

// 0x0600D300 - 0x0600D440
const Gfx dorrie_seg6_dl_0600D300[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600D078.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600D078.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600D090, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  0,  6,  8, 0x0),
    gsSP2Triangles( 0,  8,  7, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSP1Triangle( 3,  1, 14, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600D180, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 10,  8, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 10, 0x0),
    gsSP2Triangles(13, 10, 12, 0x0, 12, 11, 15, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600D280, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 5,  6,  1, 0x0,  1,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D440 - 0x0600D4E8
const Gfx dorrie_seg6_dl_0600D440[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600D300),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600D4E8
static const Lights1 dorrie_seg6_lights_0600D4E8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600D500
static const Vtx dorrie_seg6_vertex_0600D500[] = {
    {{{  1443,      8,      5}, 0, {   484,   2048}, {0x7e, 0xf8, 0x00, 0xff}}},
    {{{   -92,    409,      5}, 0, {   470,    932}, {0xef, 0x7d, 0x00, 0xff}}},
    {{{   -65,    219,    361}, 0, {   316,    960}, {0xe2, 0x2a, 0x73, 0xff}}},
    {{{   -65,    219,   -350}, 0, {   630,    948}, {0xfc, 0x41, 0x94, 0xff}}},
    {{{  -332,    214,   -243}, 0, {   600,    756}, {0xb0, 0x32, 0xac, 0xff}}},
    {{{  -332,    214,    254}, 0, {   338,    764}, {0xbb, 0x47, 0x4e, 0xff}}},
    {{{    -3,   -224,   -350}, 0, {   822,   1000}, {0xee, 0xb4, 0x9d, 0xff}}},
    {{{  -255,   -332,      5}, 0, {  1006,    820}, {0xc1, 0x97, 0x1e, 0xff}}},
    {{{  -255,   -332,      5}, 0, {   -16,    820}, {0xc1, 0x97, 0x1e, 0xff}}},
    {{{    -3,   -224,    361}, 0, {   146,   1008}, {0xf4, 0xa9, 0x5b, 0xff}}},
    {{{     9,   -315,      5}, 0, {   -18,   1012}, {0x12, 0x83, 0x00, 0xff}}},
    {{{     9,   -315,      5}, 0, {  1004,   1012}, {0x12, 0x83, 0x00, 0xff}}},
    {{{    -3,   -224,    361}, 0, {   164,   1048}, {0xf4, 0xa9, 0x5b, 0xff}}},
    {{{     9,   -315,      5}, 0, {     0,   1052}, {0x12, 0x83, 0x00, 0xff}}},
    {{{  1443,      8,      5}, 0, {   502,   2084}, {0x7e, 0xf8, 0x00, 0xff}}},
};

// 0x0600D5F0
static const Vtx dorrie_seg6_vertex_0600D5F0[] = {
    {{{  1443,      8,      5}, 0, {   502,   2084}, {0x7e, 0xf8, 0x00, 0xff}}},
    {{{     9,   -315,      5}, 0, {  1022,   1052}, {0x12, 0x83, 0x00, 0xff}}},
    {{{    -3,   -224,   -350}, 0, {   840,   1036}, {0xee, 0xb4, 0x9d, 0xff}}},
};

// 0x0600D620 - 0x0600D6D8
const Gfx dorrie_seg6_dl_0600D620[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600D4E8.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600D4E8.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600D500, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  5,  2, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 6,  7,  4, 0x0,  2,  5,  8, 0x0),
    gsSP2Triangles( 0,  2,  9, 0x0,  2,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 11,  7,  6, 0x0),
    gsSP2Triangles( 0,  6,  3, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(dorrie_seg6_vertex_0600D5F0, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D6D8 - 0x0600D780
const Gfx dorrie_seg6_dl_0600D6D8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600D620),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600D780
static const Lights1 dorrie_seg6_lights_0600D780 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600D798
static const Vtx dorrie_seg6_vertex_0600D798[] = {
    {{{  1134,    -10,   -867}, 0, {   594,   1972}, {0xdd, 0xc3, 0x97, 0xff}}},
    {{{  2429,    -78,   -421}, 0, {   406,   1676}, {0x62, 0xda, 0x46, 0xff}}},
    {{{  1289,   -124,    319}, 0, {   620,   1500}, {0x17, 0x46, 0x66, 0xff}}},
    {{{   -58,    112,   -160}, 0, {   816,   1804}, {0xe0, 0x76, 0x20, 0xff}}},
    {{{   -58,   -112,    147}, 0, {   830,   1696}, {0xc8, 0x8f, 0xfb, 0xff}}},
    {{{  2429,     35,   -729}, 0, {   394,   1792}, {0x1c, 0x79, 0x16, 0xff}}},
    {{{  1290,    101,     11}, 0, {   608,   1608}, {0x0b, 0x7a, 0x1e, 0xff}}},
    {{{  2703,    -24,  -1003}, 0, {   338,   1876}, {0x3e, 0x6e, 0xfe, 0xff}}},
};

// 0x0600D818 - 0x0600D8B0
const Gfx dorrie_seg6_dl_0600D818[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600D780.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600D780.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600D798, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  2,  6,  3, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  4,  0, 0x0),
    gsSP2Triangles( 6,  5,  0, 0x0,  0,  3,  6, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  1,  7,  5, 0x0),
    gsSP2Triangles( 5,  7,  0, 0x0,  0,  7,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600D8B0 - 0x0600D958
const Gfx dorrie_seg6_dl_0600D8B0[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600D818),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600D958
static const Lights1 dorrie_seg6_lights_0600D958 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600D970
static const Vtx dorrie_seg6_vertex_0600D970[] = {
    {{{  1134,    -13,    872}, 0, {   268,   1708}, {0xde, 0xc1, 0x68, 0xff}}},
    {{{  1286,   -114,   -315}, 0, {   328,   1164}, {0x0d, 0x1f, 0x86, 0xff}}},
    {{{  2428,    -73,    422}, 0, {   474,   1360}, {0x56, 0x2a, 0xae, 0xff}}},
    {{{   -60,    113,    170}, 0, {   116,   1520}, {0xe0, 0x76, 0xe2, 0xff}}},
    {{{  1287,    107,     -5}, 0, {   320,   1288}, {0x0e, 0x7a, 0xe0, 0xff}}},
    {{{   -61,   -108,   -139}, 0, {   124,   1396}, {0xc8, 0x8f, 0x05, 0xff}}},
    {{{  2428,     37,    731}, 0, {   464,   1492}, {0x17, 0x7c, 0xf8, 0xff}}},
    {{{  2703,    -24,   1004}, 0, {   496,   1588}, {0x3d, 0x6e, 0x03, 0xff}}},
};

// 0x0600D9F0 - 0x0600DA88
const Gfx dorrie_seg6_dl_0600D9F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600D958.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600D958.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600D970, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  5,  3, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  3,  5,  0, 0x0),
    gsSP2Triangles( 4,  3,  0, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 4,  6,  2, 0x0,  2,  7,  0, 0x0),
    gsSP2Triangles( 6,  4,  0, 0x0,  0,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DA88 - 0x0600DB30
const Gfx dorrie_seg6_dl_0600DA88[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600D9F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600DB30
static const Lights1 dorrie_seg6_lights_0600DB30 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600DB48
static const Vtx dorrie_seg6_vertex_0600DB48[] = {
    {{{   864,     32,   -681}, 0, {   572,   1464}, {0xd8, 0xc2, 0x99, 0xff}}},
    {{{   -63,    -44,     97}, 0, {   786,   1880}, {0xa9, 0xea, 0x59, 0xff}}},
    {{{   -60,    106,    -81}, 0, {   782,   1756}, {0xcd, 0x73, 0xfe, 0xff}}},
    {{{   978,    -62,    236}, 0, {   578,   2152}, {0x1b, 0xca, 0x6f, 0xff}}},
    {{{   981,     88,     57}, 0, {   574,   2028}, {0x0b, 0x75, 0x2e, 0xff}}},
    {{{  1864,    -30,   -331}, 0, {   378,   1888}, {0x56, 0x26, 0x55, 0xff}}},
    {{{  1865,     35,   -511}, 0, {   374,   1760}, {0x18, 0x7b, 0x0f, 0xff}}},
    {{{  2079,     13,   -780}, 0, {   320,   1596}, {0x3b, 0x6e, 0x10, 0xff}}},
};

// 0x0600DBC8 - 0x0600DC60
const Gfx dorrie_seg6_dl_0600DBC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600DB30.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600DB30.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600DB48, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  0,  5,  3, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  0,  2,  4, 0x0),
    gsSP2Triangles( 4,  6,  0, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 0,  7,  5, 0x0,  6,  7,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DC60 - 0x0600DD08
const Gfx dorrie_seg6_dl_0600DC60[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600DBC8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600DD08
static const Lights1 dorrie_seg6_lights_0600DD08 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600DD20
static const Vtx dorrie_seg6_vertex_0600DD20[] = {
    {{{   981,     88,    -55}, 0, {   478,    736}, {0x0b, 0x75, 0xd3, 0xff}}},
    {{{   -61,     97,     82}, 0, {   288,   1108}, {0xcc, 0x73, 0x03, 0xff}}},
    {{{   864,     24,    682}, 0, {   444,   1372}, {0xd8, 0xc1, 0x66, 0xff}}},
    {{{   -63,    -52,    -97}, 0, {   292,    972}, {0xa9, 0xea, 0xa7, 0xff}}},
    {{{  1865,     35,    512}, 0, {   628,    984}, {0x17, 0x7b, 0xf2, 0xff}}},
    {{{  1864,    -28,    332}, 0, {   632,    840}, {0x55, 0x27, 0xac, 0xff}}},
    {{{   979,    -61,   -236}, 0, {   482,    600}, {0x1b, 0xcb, 0x91, 0xff}}},
    {{{  2079,     13,    781}, 0, {   662,   1152}, {0x3a, 0x6f, 0xf1, 0xff}}},
};

// 0x0600DDA0 - 0x0600DE38
const Gfx dorrie_seg6_dl_0600DDA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009DA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&dorrie_seg6_lights_0600DD08.l, 1),
    gsSPLight(&dorrie_seg6_lights_0600DD08.a, 2),
    gsSPVertex(dorrie_seg6_vertex_0600DD20, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  6,  3, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  2,  4,  0, 0x0),
    gsSP2Triangles( 2,  6,  5, 0x0,  2,  3,  6, 0x0),
    gsSP2Triangles( 1,  3,  2, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 5,  7,  2, 0x0,  2,  7,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DE38 - 0x0600DEE0
const Gfx dorrie_seg6_dl_0600DE38[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600DDA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600DEE0
static const Vtx dorrie_seg6_vertex_0600DEE0[] = {
    {{{   558,    286,    434}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   544,    476,    403}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   358,    472,    454}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   371,    281,    485}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   371,    281,   -484}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   358,    472,   -453}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   544,    476,   -402}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   558,    286,   -433}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0600DF60 - 0x0600DFA8
const Gfx dorrie_seg6_dl_0600DF60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dorrie_seg6_texture_06009BA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dorrie_seg6_vertex_0600DEE0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DFA8 - 0x0600E060
const Gfx dorrie_seg6_dl_0600DFA8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dorrie_seg6_dl_0600DF60),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
