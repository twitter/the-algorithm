// Swoop

// Unreferenced light group
UNUSED static const Lights1 swoop_lights_unused1 = gdSPDefLights1(
    0x0a, 0x00, 0x25,
    0x2a, 0x00, 0x95, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 swoop_lights_unused2 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06004270
ALIGNED8 static const Texture swoop_seg6_texture_06004270[] = {
#include "actors/swoop/swoop_body.rgba16.inc.c"
};

// 0x06004A70
ALIGNED8 static const Texture swoop_seg6_texture_06004A70[] = {
#include "actors/swoop/swoop_eye.rgba16.inc.c"
};

// 0x06005270
ALIGNED8 static const Texture swoop_seg6_texture_06005270[] = {
#include "actors/swoop/swoop_nose.rgba16.inc.c"
};

// 0x06005A70
ALIGNED8 static const Texture swoop_seg6_texture_06005A70[] = {
#include "actors/swoop/swoop_wing.rgba16.inc.c"
};

// 0x06006270
static const Lights1 swoop_seg6_lights_06006270 = gdSPDefLights1(
    0x79, 0x79, 0x79,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06006288
static const Vtx swoop_seg6_vertex_06006288[] = {
    {{{    80,     38,    -44}, 0, {    40,     86}, {0x3f, 0x30, 0x9e, 0xff}}},
    {{{    65,     77,    -24}, 0, {  1108,    796}, {0x44, 0x59, 0xc6, 0xff}}},
    {{{   112,     54,      0}, 0, {   768,   -454}, {0x69, 0x46, 0x00, 0xff}}},
    {{{   -14,     57,     28}, 0, {   622,    254}, {0x96, 0x31, 0x30, 0xff}}},
    {{{   -11,    -13,      0}, 0, {   536,    234}, {0x8d, 0xcc, 0x00, 0xff}}},
    {{{    17,    -18,     89}, 0, {   804,     76}, {0xcc, 0xed, 0x72, 0xff}}},
    {{{    29,     71,     45}, 0, {   668,     -2}, {0x05, 0x44, 0x6a, 0xff}}},
    {{{   -14,     57,    -27}, 0, {   450,    246}, {0x96, 0x31, 0xd0, 0xff}}},
    {{{    17,    -18,    -88}, 0, {   254,     54}, {0xe5, 0xdf, 0x89, 0xff}}},
    {{{    29,     71,    -44}, 0, {   386,    -14}, {0x05, 0x44, 0x95, 0xff}}},
    {{{    82,      4,    -52}, 0, {  -728,   -328}, {0x3f, 0xfe, 0x93, 0xff}}},
    {{{    17,    -18,    -88}, 0, { -1332,    810}, {0xe5, 0xdf, 0x89, 0xff}}},
    {{{    29,     71,    -44}, 0, {   878,   1484}, {0x05, 0x44, 0x95, 0xff}}},
    {{{   114,      5,    -24}, 0, {  -496,  -1006}, {0x71, 0xe7, 0xce, 0xff}}},
};

// 0x06006368
static const Vtx swoop_seg6_vertex_06006368[] = {
    {{{    82,      4,     53}, 0, {   758,   1078}, {0x40, 0xf1, 0x6c, 0xff}}},
    {{{    83,    -37,     25}, 0, {   628,   1076}, {0x54, 0xab, 0x27, 0xff}}},
    {{{   114,      5,     25}, 0, {   626,    928}, {0x6f, 0xf4, 0x3c, 0xff}}},
    {{{    29,     71,     45}, 0, {   386,   1344}, {0x05, 0x44, 0x6a, 0xff}}},
    {{{    80,     38,     45}, 0, {   184,    100}, {0x40, 0x31, 0x62, 0xff}}},
    {{{    65,     77,     25}, 0, {  1194,    792}, {0x44, 0x59, 0x3a, 0xff}}},
    {{{   112,     54,      0}, 0, {  1590,   -282}, {0x69, 0x46, 0x00, 0xff}}},
    {{{    17,    -18,     89}, 0, { -2258,    574}, {0xcc, 0xed, 0x72, 0xff}}},
    {{{    82,      4,     53}, 0, {  -608,   -318}, {0x40, 0xf1, 0x6c, 0xff}}},
    {{{   112,     54,      0}, 0, {   514,    940}, {0x69, 0x46, 0x00, 0xff}}},
    {{{    80,     38,     45}, 0, {   722,   1090}, {0x40, 0x31, 0x62, 0xff}}},
    {{{    34,    -55,     44}, 0, {   722,   1312}, {0x06, 0x8a, 0x2c, 0xff}}},
    {{{    17,    -18,     89}, 0, {   928,   1394}, {0xcc, 0xed, 0x72, 0xff}}},
    {{{    83,    -37,    -24}, 0, {   402,   1080}, {0x48, 0xa4, 0xd1, 0xff}}},
    {{{    82,      4,    -52}, 0, {   272,   1086}, {0x3f, 0xfe, 0x93, 0xff}}},
    {{{   114,      5,    -24}, 0, {   400,    932}, {0x71, 0xe7, 0xce, 0xff}}},
};

// 0x06006468
static const Vtx swoop_seg6_vertex_06006468[] = {
    {{{    34,    -55,     44}, 0, {   722,   1312}, {0x06, 0x8a, 0x2c, 0xff}}},
    {{{    34,    -55,    -43}, 0, {   314,   1318}, {0xe6, 0x8a, 0xdc, 0xff}}},
    {{{    83,    -37,    -24}, 0, {   402,   1080}, {0x48, 0xa4, 0xd1, 0xff}}},
    {{{    83,    -37,     25}, 0, {   628,   1076}, {0x54, 0xab, 0x27, 0xff}}},
    {{{   114,      5,    -24}, 0, {   400,    932}, {0x71, 0xe7, 0xce, 0xff}}},
    {{{   114,      5,     25}, 0, {   626,    928}, {0x6f, 0xf4, 0x3c, 0xff}}},
    {{{   112,     54,      0}, 0, {   514,    940}, {0x69, 0x46, 0x00, 0xff}}},
    {{{   -11,    -13,      0}, 0, {   522,   1540}, {0x8d, 0xcc, 0x00, 0xff}}},
    {{{    17,    -18,     89}, 0, {   928,   1394}, {0xcc, 0xed, 0x72, 0xff}}},
    {{{    17,    -18,    -88}, 0, {   112,   1406}, {0xe5, 0xdf, 0x89, 0xff}}},
    {{{    82,      4,    -52}, 0, {   272,   1086}, {0x3f, 0xfe, 0x93, 0xff}}},
};

// 0x06006518
static const Vtx swoop_seg6_vertex_06006518[] = {
    {{{   112,     54,      0}, 0, {   702,      6}, {0x69, 0x46, 0x00, 0xff}}},
    {{{    65,     77,    -24}, 0, {   382,    266}, {0x44, 0x59, 0xc6, 0xff}}},
    {{{    65,     77,     25}, 0, {   878,    288}, {0x44, 0x59, 0x3a, 0xff}}},
    {{{    40,    113,      0}, 0, {   478,    480}, {0xf9, 0x7e, 0x00, 0xff}}},
    {{{    65,     77,     25}, 0, {   726,    230}, {0x44, 0x59, 0x3a, 0xff}}},
    {{{    65,     77,    -24}, 0, {   232,    230}, {0x44, 0x59, 0xc6, 0xff}}},
    {{{    29,     71,     45}, 0, {   932,    586}, {0x05, 0x44, 0x6a, 0xff}}},
    {{{    29,     71,    -44}, 0, {    26,    586}, {0x05, 0x44, 0x95, 0xff}}},
    {{{   -14,     57,     28}, 0, {   756,   1022}, {0x96, 0x31, 0x30, 0xff}}},
    {{{   -14,     57,    -27}, 0, {   202,   1022}, {0x96, 0x31, 0xd0, 0xff}}},
};

// 0x060065B8 - 0x060066F8
const Gfx swoop_seg6_dl_060065B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06004A70),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&swoop_seg6_lights_06006270.l, 1),
    gsSPLight(&swoop_seg6_lights_06006270.a, 2),
    gsSPVertex(swoop_seg6_vertex_06006288, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 8,  4,  7, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12,  0, 0x0),
    gsSP2Triangles( 0,  2, 13, 0x0,  0, 13, 10, 0x0),
    gsSP1Triangle( 1,  0, 12, 0x0),
    gsSPVertex(swoop_seg6_vertex_06006368, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 3,  8,  4, 0x0,  2,  9, 10, 0x0),
    gsSP2Triangles( 2, 10,  0, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles(11,  0, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(swoop_seg6_vertex_06006468, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  4,  3,  2, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  1,  0, 0x0),
    gsSP2Triangles( 0,  8,  7, 0x0,  9,  1,  7, 0x0),
    gsSP2Triangles( 2,  1,  9, 0x0,  2,  9, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x060066F8 - 0x06006758
const Gfx swoop_seg6_dl_060066F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06005270),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(swoop_seg6_vertex_06006518, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  7,  3,  5, 0x0),
    gsSP2Triangles( 6,  3,  8, 0x0,  3,  9,  8, 0x0),
    gsSP1Triangle( 7,  9,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006758 - 0x06006808
const Gfx swoop_seg6_dl_06006758[] = {
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
    gsSPDisplayList(swoop_seg6_dl_060065B8),
    gsSPDisplayList(swoop_seg6_dl_060066F8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06006808
static const Vtx swoop_seg6_vertex_06006808[] = {
    {{{    15,     15,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -14,     15,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -14,    -14,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    15,    -14,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06006848 - 0x06006880
const Gfx swoop_seg6_dl_06006848[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06004270),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(swoop_seg6_vertex_06006808, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006880 - 0x06006938
const Gfx swoop_seg6_dl_06006880[] = {
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
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(swoop_seg6_dl_06006848),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06006938
static const Lights1 swoop_seg6_lights_06006938 = gdSPDefLights1(
    0x79, 0x79, 0x79,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06006950
static const Vtx swoop_seg6_vertex_06006950[] = {
    {{{   187,     48,    -55}, 0, {   426,    722}, {0xc6, 0x6b, 0xdf, 0xff}}},
    {{{   194,     32,   -119}, 0, {   334,    706}, {0xdf, 0x7a, 0x05, 0xff}}},
    {{{    80,     -3,    -32}, 0, {   458,    990}, {0xdf, 0x7a, 0x05, 0xff}}},
    {{{   116,     34,   -130}, 0, {   318,    900}, {0xfd, 0x77, 0x2b, 0xff}}},
    {{{    80,     -3,     33}, 0, {   552,    990}, {0xdf, 0x7a, 0xfb, 0xff}}},
    {{{   194,     32,    120}, 0, {   676,    706}, {0xdf, 0x7a, 0xfb, 0xff}}},
    {{{   187,     48,     56}, 0, {   584,    722}, {0xc6, 0x6b, 0x21, 0xff}}},
    {{{   116,     34,    131}, 0, {   692,    900}, {0xfd, 0x77, 0xd5, 0xff}}},
};

// 0x060069D0
static const Vtx swoop_seg6_vertex_060069D0[] = {
    {{{    13,     79,    -15}, 0, {     0,      0}, {0xec, 0x7d, 0x00, 0xff}}},
    {{{   -17,     74,     -8}, 0, {     0,      0}, {0xec, 0x7d, 0x00, 0xff}}},
    {{{   -17,     74,      9}, 0, {     0,      0}, {0xec, 0x7d, 0x00, 0xff}}},
    {{{    13,     79,     16}, 0, {     0,      0}, {0xec, 0x7d, 0x00, 0xff}}},
};

// 0x06006A10 - 0x06006A68
const Gfx swoop_seg6_dl_06006A10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06005270),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&swoop_seg6_lights_06006938.l, 1),
    gsSPLight(&swoop_seg6_lights_06006938.a, 2),
    gsSPVertex(swoop_seg6_vertex_06006950, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006A68 - 0x06006A88
const Gfx swoop_seg6_dl_06006A68[] = {
    gsSPVertex(swoop_seg6_vertex_060069D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006A88 - 0x06006B58
const Gfx swoop_seg6_dl_06006A88[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(swoop_seg6_dl_06006A10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_PASS2),
    gsSPDisplayList(swoop_seg6_dl_06006A68),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06006B58
static const Vtx swoop_seg6_vertex_06006B58[] = {
    {{{     0,      1,    133}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      1,   -133}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   265,      1,   -133}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   265,      1,    133}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06006B98 - 0x06006BD0
const Gfx swoop_seg6_dl_06006B98[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06005A70),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(swoop_seg6_vertex_06006B58, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006BD0 - 0x06006C88
const Gfx swoop_seg6_dl_06006BD0[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(swoop_seg6_dl_06006B98),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06006C88
static const Vtx swoop_seg6_vertex_06006C88[] = {
    {{{   265,      1,   -132}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   265,      1,    134}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      1,    134}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      1,   -132}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06006CC8 - 0x06006D00
const Gfx swoop_seg6_dl_06006CC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, swoop_seg6_texture_06005A70),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(swoop_seg6_vertex_06006C88, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06006D00 - 0x06006DB8
const Gfx swoop_seg6_dl_06006D00[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(swoop_seg6_dl_06006CC8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
