// Moneybag

// 0x060039B0
ALIGNED8 static const Texture moneybag_seg6_texture_060039B0[] = {
#include "actors/moneybag/moneybag_mouth.rgba16.inc.c"
};

// 0x060049B0
ALIGNED8 static const Texture moneybag_seg6_texture_060049B0[] = {
#include "actors/moneybag/moneybag_eyes.rgba16.inc.c"
};

// 0x060051B0
static const Lights1 moneybag_seg6_lights_060051B0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060051C8
static const Lights1 moneybag_seg6_lights_060051C8 = gdSPDefLights1(
    0x00, 0x3f, 0x23,
    0x00, 0x7f, 0x47, 0x28, 0x28, 0x28
);

// 0x060051E0
static const Vtx moneybag_seg6_vertex_060051E0[] = {
    {{{   226,      1,    300}, 0, {  1988,      0}, {0xcd, 0xf8, 0x73, 0xff}}},
    {{{     0,      0,    200}, 0, {  2656,    358}, {0xac, 0xfb, 0x5e, 0xff}}},
    {{{   278,   -190,    140}, 0, {   988,    390}, {0xd2, 0x96, 0x33, 0xff}}},
    {{{   261,    194,   -174}, 0, {   824,    392}, {0xcc, 0x64, 0xc8, 0xff}}},
    {{{   226,      1,   -299}, 0, {    -8,      0}, {0xcd, 0xf8, 0x8d, 0xff}}},
    {{{     0,      0,   -199}, 0, {  -672,    312}, {0xac, 0xfb, 0xa2, 0xff}}},
    {{{   261,    194,    175}, 0, {  1156,    392}, {0xcc, 0x64, 0x38, 0xff}}},
    {{{     0,      0,    200}, 0, {  2656,    312}, {0xac, 0xfb, 0x5e, 0xff}}},
    {{{     0,      0,   -199}, 0, {  -672,    358}, {0xac, 0xfb, 0xa2, 0xff}}},
    {{{   278,   -190,   -139}, 0, {   992,    390}, {0xd2, 0x96, 0xcd, 0xff}}},
};

// 0x06005280
static const Vtx moneybag_seg6_vertex_06005280[] = {
    {{{     0,      0,    200}, 0, {     0,      0}, {0xac, 0xfb, 0x5e, 0xff}}},
    {{{   -88,    -68,      0}, 0, {     0,      0}, {0x9f, 0xaf, 0x00, 0xff}}},
    {{{   278,   -190,    140}, 0, {     0,      0}, {0xd2, 0x96, 0x33, 0xff}}},
    {{{   -88,     71,      0}, 0, {     0,      0}, {0x9b, 0x4c, 0x00, 0xff}}},
    {{{   261,    194,    175}, 0, {     0,      0}, {0xcc, 0x64, 0x38, 0xff}}},
    {{{     0,      0,   -199}, 0, {     0,      0}, {0xac, 0xfb, 0xa2, 0xff}}},
    {{{   278,   -190,   -139}, 0, {     0,      0}, {0xd2, 0x96, 0xcd, 0xff}}},
    {{{   261,    194,   -174}, 0, {     0,      0}, {0xcc, 0x64, 0xc8, 0xff}}},
};

// 0x06005300 - 0x06005358
const Gfx moneybag_seg6_dl_06005300[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, moneybag_seg6_texture_060039B0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&moneybag_seg6_lights_060051B0.l, 1),
    gsSPLight(&moneybag_seg6_lights_060051B0.a, 2),
    gsSPVertex(moneybag_seg6_vertex_060051E0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  0, 0x0,  8,  4,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005358 - 0x060053B8
const Gfx moneybag_seg6_dl_06005358[] = {
    gsSPLight(&moneybag_seg6_lights_060051C8.l, 1),
    gsSPLight(&moneybag_seg6_lights_060051C8.a, 2),
    gsSPVertex(moneybag_seg6_vertex_06005280, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  3,  5,  1, 0x0),
    gsSP2Triangles( 6,  2,  1, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 5,  3,  7, 0x0,  4,  7,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x060053B8 - 0x06005428
const Gfx moneybag_seg6_dl_060053B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(moneybag_seg6_dl_06005300),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(moneybag_seg6_dl_06005358),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06005428
static const Lights1 moneybag_seg6_lights_06005428 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06005440
static const Vtx moneybag_seg6_vertex_06005440[] = {
    {{{   418,     89,   -157}, 0, {   388,      0}, {0x46, 0x62, 0xdb, 0xff}}},
    {{{   261,    194,    175}, 0, {  1604,    408}, {0x46, 0x62, 0x25, 0xff}}},
    {{{   418,     89,    157}, 0, {  1540,      0}, {0x43, 0x5b, 0x39, 0xff}}},
    {{{   418,     89,    157}, 0, {   684,      0}, {0x43, 0x5b, 0x39, 0xff}}},
    {{{   261,    194,    175}, 0, {  1156,    392}, {0x46, 0x62, 0x25, 0xff}}},
    {{{   226,      1,    300}, 0, {  1988,      0}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{   418,     89,   -157}, 0, {  1296,      0}, {0x46, 0x62, 0xdb, 0xff}}},
    {{{   226,      1,   -299}, 0, {    -8,      0}, {0x33, 0xf9, 0x8d, 0xff}}},
    {{{   261,    194,   -174}, 0, {   824,    392}, {0x43, 0x5b, 0xc7, 0xff}}},
    {{{   418,    -85,   -157}, 0, {  1296,      0}, {0x28, 0xba, 0x9f, 0xff}}},
    {{{   278,   -190,   -139}, 0, {   992,    390}, {0x46, 0x9d, 0xdd, 0xff}}},
    {{{   278,   -190,    140}, 0, {   988,    390}, {0x40, 0xa1, 0x35, 0xff}}},
    {{{   418,    -85,    157}, 0, {   684,      0}, {0x28, 0xba, 0x61, 0xff}}},
    {{{   261,    194,   -174}, 0, {   324,    408}, {0x43, 0x5b, 0xc7, 0xff}}},
};

// 0x06005520
static const Vtx moneybag_seg6_vertex_06005520[] = {
    {{{   278,   -190,   -139}, 0, {  1512,    416}, {0x46, 0x9d, 0xdd, 0xff}}},
    {{{   418,    -85,   -156}, 0, {  1588,      0}, {0x4c, 0x9b, 0x00, 0xff}}},
    {{{   418,    -85,    158}, 0, {   248,      0}, {0x4c, 0x9b, 0x00, 0xff}}},
    {{{   278,   -190,    140}, 0, {   320,    416}, {0x40, 0xa1, 0x35, 0xff}}},
    {{{   238,    -15,    286}, 0, {  2336,    754}, {0x3e, 0xce, 0x62, 0xff}}},
    {{{   272,     77,    312}, 0, {   124,   1062}, {0x3e, 0xce, 0x62, 0xff}}},
    {{{   229,     73,    337}, 0, {   124,    436}, {0x3e, 0xce, 0x62, 0xff}}},
};

// 0x06005590 - 0x06005618
const Gfx moneybag_seg6_dl_06005590[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, moneybag_seg6_texture_060039B0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&moneybag_seg6_lights_06005428.l, 1),
    gsSPLight(&moneybag_seg6_lights_06005428.a, 2),
    gsSPVertex(moneybag_seg6_vertex_06005440, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles( 5, 11, 12, 0x0,  0, 13,  1, 0x0),
    gsSPVertex(moneybag_seg6_vertex_06005520, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP1Triangle( 4,  5,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005618 - 0x06005688
const Gfx moneybag_seg6_dl_06005618[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(moneybag_seg6_dl_06005590),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06005688 - 0x060056A8
const Gfx moneybag_seg6_dl_06005688[] = {
    gsSPDisplayList(moneybag_seg6_dl_060053B8),
    gsSPDisplayList(moneybag_seg6_dl_06005618),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};

// 0x060056A8
static const Vtx moneybag_seg6_vertex_060056A8[] = {
    {{{   356,    121,   -159}, 0, {   960,      4}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   356,   -128,    150}, 0, {    28,    918}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   356,   -128,   -149}, 0, {   930,    918}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   356,    121,    160}, 0, {    -2,      4}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,      1,    298}, 0, {  -414,    442}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,      1,   -297}, 0, {  1374,    442}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06005708 - 0x06005750
const Gfx moneybag_seg6_dl_06005708[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, moneybag_seg6_texture_060049B0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(moneybag_seg6_vertex_060056A8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  5,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005750 - 0x060057C0
const Gfx moneybag_seg6_dl_06005750[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(moneybag_seg6_dl_06005708),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// Unreferenced light group
UNUSED static const Lights1 moneybag_lights_unused1 = gdSPDefLights1(
    0x0c, 0x20, 0x06,
    0x30, 0x83, 0x1a, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 moneybag_lights_unused2 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x060057F0
static const Lights1 moneybag_seg6_lights_060057F0 = gdSPDefLights1(
    0x3f, 0x3f, 0x00,
    0xff, 0xff, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 moneybag_lights_unused3 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x06005820
static const Vtx moneybag_seg6_vertex_06005820[] = {
    {{{     0,      0,      0}, 0, {     0,      0}, {0x83, 0x11, 0x00, 0x00}}},
    {{{   180,     90,      0}, 0, {     0,      0}, {0xc8, 0x71, 0x00, 0x00}}},
    {{{   180,     40,    -80}, 0, {     0,      0}, {0xc8, 0x2a, 0x97, 0x00}}},
    {{{   180,   -120,    -40}, 0, {     0,      0}, {0xbb, 0xb0, 0xbb, 0xff}}},
    {{{   180,   -120,     40}, 0, {     0,      0}, {0xbb, 0xb0, 0x45, 0xff}}},
    {{{   180,     90,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,     80}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,   -120,     40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,   -120,    -40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,    -80}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,     80}, 0, {     0,      0}, {0xc8, 0x2a, 0x69, 0xff}}},
};

// 0x060058D0
static const Vtx moneybag_seg6_vertex_060058D0[] = {
    {{{   180,     40,     80}, 0, {     0,      0}, {0xc8, 0x2a, 0x69, 0x00}}},
    {{{   180,     90,      0}, 0, {     0,      0}, {0xc8, 0x71, 0x00, 0x00}}},
    {{{     0,      0,      0}, 0, {     0,      0}, {0x83, 0x11, 0x00, 0x00}}},
    {{{   180,   -120,    -40}, 0, {     0,      0}, {0xbb, 0xb0, 0xbb, 0xff}}},
    {{{   180,   -120,     40}, 0, {     0,      0}, {0xbb, 0xb0, 0x45, 0xff}}},
    {{{   180,   -120,    -40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,    -80}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     90,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,   -120,     40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,     80}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,     40,    -80}, 0, {     0,      0}, {0xc8, 0x2a, 0x97, 0xff}}},
};

// 0x06005980 - 0x060059F0
const Gfx moneybag_seg6_dl_06005980[] = {
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&moneybag_seg6_lights_060057F0.l, 1),
    gsSPLight(&moneybag_seg6_lights_060057F0.a, 2),
    gsSPVertex(moneybag_seg6_vertex_06005820, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0, 10,  1,  0, 0x0),
    gsSP2Triangles( 0,  4, 10, 0x0,  2,  3,  0, 0x0),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060059F0 - 0x06005A60
const Gfx moneybag_seg6_dl_060059F0[] = {
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPLight(&moneybag_seg6_lights_060057F0.l, 1),
    gsSPLight(&moneybag_seg6_lights_060057F0.a, 2),
    gsSPVertex(moneybag_seg6_vertex_060058D0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  5,  7, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0,  2,  1, 10, 0x0),
    gsSP2Triangles(10,  3,  2, 0x0,  2,  4,  0, 0x0),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
