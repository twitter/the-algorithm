// Mario Cap (Normal Cap, Metal, Winged, etc)

// 0x0301CF08
static const Lights1 mario_cap_seg3_lights_0301CF08 = gdSPDefLights1(
    0x39, 0x03, 0x00,
    0x73, 0x06, 0x00, 0x28, 0x28, 0x28
);

// 0x0301CF20
static const Lights1 mario_cap_seg3_lights_0301CF20 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0301CF38
static const Lights1 mario_cap_seg3_lights_0301CF38 = gdSPDefLights1(
    0x7f, 0x00, 0x00,
    0xff, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x0301CF50
ALIGNED8 static const Texture mario_cap_seg3_texture_0301CF50[] = {
#include "actors/mario_cap/mario_cap_metal.rgba16.inc.c"
};

// 0x0301DF50
ALIGNED8 static const Texture mario_cap_seg3_texture_0301DF50[] = {
#include "actors/mario_cap/mario_cap_logo.rgba16.inc.c"
};

// 0x0301E750
ALIGNED8 static const Texture mario_cap_seg3_texture_0301E750[] = {
#include "actors/mario_cap/mario_cap_wing.rgba16.inc.c"
};

// 0x0301F750
ALIGNED8 static const Texture mario_cap_seg3_texture_0301F750[] = {
#include "actors/mario_cap/mario_cap_wing_tip.rgba16.inc.c"
};

// 0x03020750
ALIGNED8 static const Texture mario_cap_seg3_texture_03020750[] = {
#include "actors/mario_cap/mario_cap_metal_wing_unused.rgba16.inc.c"
};

// 0x03021750
ALIGNED8 static const Texture mario_cap_seg3_texture_03021750[] = {
#include "actors/mario_cap/mario_cap_metal_wing_tip_unused.rgba16.inc.c"
};

// 0x03022750
static const Vtx mario_cap_seg3_vertex_03022750[] = {
    {{{    33,     35,    118}, 0, {   728,    758}, {0x30, 0x26, 0x6e, 0xff}}},
    {{{    96,     22,     45}, 0, {  1240,    876}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{    71,    101,    113}, 0, {  1028,    148}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{     0,    110,    143}, 0, {   460,     68}, {0x00, 0x34, 0x73, 0xff}}},
    {{{   -31,     35,    118}, 0, {   206,    762}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{   -70,    101,    113}, 0, {  -106,    158}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{   -95,     22,     46}, 0, {  -302,    890}, {0xa8, 0xb9, 0x38, 0xff}}},
};

// 0x030227C0
static const Vtx mario_cap_seg3_vertex_030227C0[] = {
    {{{   -66,      2,    139}, 0, {     0,      0}, {0xb0, 0xbb, 0x45, 0xff}}},
    {{{     0,      0,    163}, 0, {     0,      0}, {0x00, 0xba, 0x69, 0xff}}},
    {{{   -31,     35,    118}, 0, {     0,      0}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{   -32,     17,    109}, 0, {     0,      0}, {0x00, 0x83, 0xf0, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   -95,     22,     46}, 0, {     0,      0}, {0xa8, 0xb9, 0x38, 0xff}}},
    {{{  -101,     10,     -7}, 0, {     0,      0}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{   -70,    101,    113}, 0, {     0,      0}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{  -135,     70,     23}, 0, {     0,      0}, {0x84, 0x15, 0x10, 0xff}}},
    {{{  -125,     38,    -45}, 0, {     0,      0}, {0x8d, 0xec, 0xd1, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
    {{{   -41,    144,     64}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0xff}}},
    {{{   -76,     84,    -60}, 0, {     0,      0}, {0xd5, 0x6e, 0xd3, 0xff}}},
    {{{   136,     70,     22}, 0, {     0,      0}, {0x7b, 0x16, 0x10, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
};

// 0x030228C0
static const Vtx mario_cap_seg3_vertex_030228C0[] = {
    {{{    42,    144,     64}, 0, {     0,      0}, {0x2b, 0x76, 0x0d, 0xff}}},
    {{{   136,     70,     22}, 0, {     0,      0}, {0x7b, 0x16, 0x10, 0xff}}},
    {{{    76,     84,    -60}, 0, {     0,      0}, {0x2a, 0x6c, 0xcf, 0xff}}},
    {{{   103,     10,     -6}, 0, {     0,      0}, {0x42, 0x96, 0x12, 0xff}}},
    {{{   126,     38,    -46}, 0, {     0,      0}, {0x73, 0xec, 0xd0, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{    67,      2,    139}, 0, {     0,      0}, {0x50, 0xba, 0x44, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{    33,     35,    118}, 0, {     0,      0}, {0x30, 0x26, 0x6e, 0xff}}},
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{     0,      0,    163}, 0, {     0,      0}, {0x00, 0xba, 0x69, 0xff}}},
    {{{   -31,     35,    118}, 0, {     0,      0}, {0xd0, 0x26, 0x6f, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
    {{{    49,     62,   -139}, 0, {     0,      0}, {0x32, 0x49, 0xa6, 0xff}}},
};

// 0x030229B0
static const Vtx mario_cap_seg3_vertex_030229B0[] = {
    {{{   -76,     84,    -60}, 0, {     0,      0}, {0xd5, 0x6e, 0xd3, 0xff}}},
    {{{   -41,    144,     64}, 0, {     0,      0}, {0xdc, 0x79, 0x00, 0xff}}},
    {{{    76,     84,    -60}, 0, {     0,      0}, {0x2a, 0x6c, 0xcf, 0xff}}},
    {{{     0,    110,    143}, 0, {     0,      0}, {0x00, 0x34, 0x73, 0xff}}},
    {{{    42,    144,     64}, 0, {     0,      0}, {0x2b, 0x76, 0x0d, 0xff}}},
    {{{   -70,    101,    113}, 0, {     0,      0}, {0xab, 0x16, 0x5b, 0xff}}},
    {{{    71,    101,    113}, 0, {     0,      0}, {0x55, 0x16, 0x5b, 0xff}}},
    {{{    49,     62,   -139}, 0, {     0,      0}, {0x32, 0x49, 0xa6, 0xff}}},
    {{{   126,     38,    -46}, 0, {     0,      0}, {0x73, 0xec, 0xd0, 0xff}}},
    {{{   -52,      0,   -118}, 0, {     0,      0}, {0xd2, 0x9d, 0xc1, 0xff}}},
    {{{   -49,     62,   -138}, 0, {     0,      0}, {0xce, 0x1a, 0x8f, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
    {{{  -125,     38,    -45}, 0, {     0,      0}, {0x8d, 0xec, 0xd1, 0xff}}},
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
};

// 0x03022AA0
static const Vtx mario_cap_seg3_vertex_03022AA0[] = {
    {{{    86,      1,    -60}, 0, {     0,      0}, {0x20, 0x86, 0xfe, 0xff}}},
    {{{   -86,      1,    -60}, 0, {     0,      0}, {0xce, 0x8c, 0xf6, 0xff}}},
    {{{   -52,      0,   -118}, 0, {     0,      0}, {0xd2, 0x9d, 0xc1, 0xff}}},
    {{{    33,     17,    109}, 0, {     0,      0}, {0xfb, 0x84, 0xea, 0xff}}},
    {{{   -32,     17,    109}, 0, {     0,      0}, {0x00, 0x83, 0xf0, 0xff}}},
    {{{  -101,     10,     -7}, 0, {     0,      0}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{    96,     22,     45}, 0, {     0,      0}, {0x48, 0xa4, 0x31, 0xff}}},
    {{{   103,     10,     -6}, 0, {     0,      0}, {0x42, 0x96, 0x12, 0xff}}},
    {{{    53,      0,   -118}, 0, {     0,      0}, {0x2c, 0xb5, 0xa5, 0xff}}},
};

// 0x03022B30 - 0x03022B68
const Gfx mario_cap_seg3_dl_03022B30[] = {
    gsSPVertex(mario_cap_seg3_vertex_03022750, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  2,  3,  0, 0x0),
    gsSP1Triangle( 5,  6,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x03022B68 - 0x03022CC8
const Gfx mario_cap_seg3_dl_03022B68[] = {
    gsSPVertex(mario_cap_seg3_vertex_030227C0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 3,  1,  0, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  0,  5, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 6,  9, 10, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles(11,  8,  7, 0x0,  8, 12,  9, 0x0),
    gsSP2Triangles(12,  8, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(mario_cap_seg3_vertex_030228C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  5,  1,  0, 0x0),
    gsSP2Triangles( 1,  6,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  7,  6, 0x0, 10,  4,  3, 0x0),
    gsSP2Triangles( 9, 11,  7, 0x0,  7, 11,  8, 0x0),
    gsSP2Triangles(12, 11,  9, 0x0, 13, 14,  4, 0x0),
    gsSPVertex(mario_cap_seg3_vertex_030229B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  1,  4,  2, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  0,  2,  7, 0x0),
    gsSP2Triangles( 8,  7,  2, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 10,  9, 0x0,  7, 10,  0, 0x0),
    gsSP2Triangles(10,  7, 11, 0x0,  0, 10, 12, 0x0),
    gsSP2Triangles(11,  8, 13, 0x0, 14, 12,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x03022CC8 - 0x03022D10
const Gfx mario_cap_seg3_dl_03022CC8[] = {
    gsSPVertex(mario_cap_seg3_vertex_03022AA0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 0,  7,  5, 0x0,  0,  5,  1, 0x0),
    gsSP1Triangle( 2,  8,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x03022D10 - 0x03022D38
const Gfx mario_cap_seg3_dl_03022D10[] = {
    gsSPDisplayList(mario_cap_seg3_dl_03022B68),
    gsSPLight(&mario_cap_seg3_lights_0301CF08.l, 1),
    gsSPLight(&mario_cap_seg3_lights_0301CF08.a, 2),
    gsSPDisplayList(mario_cap_seg3_dl_03022CC8),
    gsSPEndDisplayList(),
};

// 0x03022D38
static const Vtx mario_cap_seg3_vertex_03022D38[] = {
    {{{   199,    247,    -55}, 0, {   990,      0}, {0x59, 0xed, 0x58, 0xff}}},
    {{{   131,    274,     20}, 0, {     0,      0}, {0x59, 0xed, 0x58, 0xff}}},
    {{{    69,     71,     38}, 0, {     0,   2012}, {0x59, 0xed, 0x58, 0xff}}},
    {{{   199,    247,    -55}, 0, {   990,      0}, {0x58, 0xee, 0x58, 0xff}}},
    {{{    69,     71,     38}, 0, {     0,   2012}, {0x58, 0xee, 0x58, 0xff}}},
    {{{   138,     44,    -37}, 0, {   990,   2012}, {0x58, 0xee, 0x58, 0xff}}},
    {{{  -137,     44,    -37}, 0, {   990,   2012}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{   -68,     71,     38}, 0, {     0,   2012}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{  -198,    247,    -55}, 0, {   990,      0}, {0xa8, 0xee, 0x58, 0xff}}},
    {{{   -68,     71,     38}, 0, {     0,   2012}, {0xa7, 0xed, 0x58, 0xff}}},
    {{{  -130,    274,     20}, 0, {     0,      0}, {0xa7, 0xed, 0x58, 0xff}}},
    {{{  -198,    247,    -55}, 0, {   990,      0}, {0xa7, 0xed, 0x58, 0xff}}},
};

// 0x03022DF8
static const Vtx mario_cap_seg3_vertex_03022DF8[] = {
    {{{   268,    219,   -132}, 0, {   990,      0}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   199,    247,    -55}, 0, {     0,      0}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   138,     44,    -37}, 0, {     0,   2012}, {0x59, 0xed, 0x57, 0xff}}},
    {{{   207,     16,   -114}, 0, {   990,   2012}, {0x59, 0xed, 0x57, 0xff}}},
    {{{  -206,     16,   -114}, 0, {   990,   2012}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -137,     44,    -37}, 0, {     0,   2012}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -267,    219,   -132}, 0, {   990,      0}, {0xa7, 0xed, 0x57, 0xff}}},
    {{{  -198,    247,    -55}, 0, {     0,      0}, {0xa7, 0xed, 0x57, 0xff}}},
};

// 0x03022E78 - 0x03022EA8
const Gfx mario_cap_seg3_dl_03022E78[] = {
    gsSPVertex(mario_cap_seg3_vertex_03022D38, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x03022EA8 - 0x03022ED8
const Gfx mario_cap_seg3_dl_03022EA8[] = {
    gsSPVertex(mario_cap_seg3_vertex_03022DF8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x03022ED8 - 0x03022F20
const Gfx mario_cap_seg3_dl_03022ED8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x03022F20 - 0x03022F48
const Gfx mario_cap_seg3_dl_03022F20[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x03022F48 - 0x03022FF8
const Gfx mario_cap_seg3_dl_03022F48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301DF50),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_cap_seg3_lights_0301CF38.l, 1),
    gsSPLight(&mario_cap_seg3_lights_0301CF38.a, 2),
    gsSPDisplayList(mario_cap_seg3_dl_03022B30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_cap_seg3_dl_03022D10),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};

// 0x03022FF8 - 0x030230B0
const Gfx mario_cap_seg3_dl_03022FF8[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPLoadTextureBlock(mario_cap_seg3_texture_0301CF50, G_IM_FMT_RGBA, G_IM_SIZ_16b, 64, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_cap_seg3_lights_0301CF20.l, 1),
    gsSPLight(&mario_cap_seg3_lights_0301CF20.a, 2),
    gsSPDisplayList(mario_cap_seg3_dl_03022B30),
    gsSPDisplayList(mario_cap_seg3_dl_03022B68),
    gsSPDisplayList(mario_cap_seg3_dl_03022CC8),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};

// 0x030230B0 - 0x03023108
const Gfx mario_cap_seg3_dl_030230B0[] = {
    gsSPDisplayList(mario_cap_seg3_dl_03022ED8),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301E750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022E78),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301F750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022EA8),
    gsSPDisplayList(mario_cap_seg3_dl_03022F20),
    gsSPEndDisplayList(),
};

// 0x03023108 - 0x03023160
const Gfx mario_cap_seg3_dl_03023108[] = {
    gsSPDisplayList(mario_cap_seg3_dl_03022ED8),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_03020750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022E78),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_03021750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022EA8),
    gsSPDisplayList(mario_cap_seg3_dl_03022F20),
    gsSPEndDisplayList(),
};

// 0x03023160 - 0x03023298
const Gfx mario_cap_seg3_dl_03023160[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301DF50),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mario_cap_seg3_lights_0301CF38.l, 1),
    gsSPLight(&mario_cap_seg3_lights_0301CF38.a, 2),
    gsSPDisplayList(mario_cap_seg3_dl_03022B30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(mario_cap_seg3_dl_03022D10),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetCombineMode(G_CC_DECALFADEA, G_CC_DECALFADEA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301E750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022E78),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301F750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022EA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};

// 0x03023298 - 0x030233D0
const Gfx mario_cap_seg3_dl_03023298[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_0301CF50),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsDPPipeSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPLight(&mario_cap_seg3_lights_0301CF20.l, 1),
    gsSPLight(&mario_cap_seg3_lights_0301CF20.a, 2),
    gsSPDisplayList(mario_cap_seg3_dl_03022B30),
    gsSPDisplayList(mario_cap_seg3_dl_03022B68),
    gsSPDisplayList(mario_cap_seg3_dl_03022CC8),
    gsSPTexture(0x0F80, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN | G_LIGHTING | G_CULL_BACK),
    gsDPSetCombineMode(G_CC_DECALFADEA, G_CC_DECALFADEA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_03020750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022E78),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mario_cap_seg3_texture_03021750),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(mario_cap_seg3_dl_03022EA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
