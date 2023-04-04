// Chain Chomp

// 0x06021388
UNUSED static const Lights1 chain_chomp_lights_unused1 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060213A0
UNUSED static const Lights1 chain_chomp_lights_unused2 = gdSPDefLights1(
    0x03, 0x03, 0x05,
    0x0d, 0x0f, 0x16, 0x28, 0x28, 0x28
);

// 0x060213B8
UNUSED static const Lights1 chain_chomp_lights_unused3 = gdSPDefLights1(
    0x25, 0x00, 0x00,
    0x96, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x060213D0
ALIGNED8 static const Texture chain_chomp_seg6_texture_060213D0[] = {
#include "actors/chain_chomp/chain_chomp_bright_shine.rgba16.inc.c"
};

// 0x06021BD0
ALIGNED8 static const Texture chain_chomp_seg6_texture_06021BD0[] = {
#include "actors/chain_chomp/chain_chomp_dull_shine.rgba16.inc.c"
};

// 0x060223D0
ALIGNED8 static const Texture chain_chomp_seg6_texture_060223D0[] = {
#include "actors/chain_chomp/chain_chomp_tongue.rgba16.inc.c"
};

// 0x06022BD0
ALIGNED8 static const Texture chain_chomp_seg6_texture_06022BD0[] = {
#include "actors/chain_chomp/chain_chomp_tooth.rgba16.inc.c"
};

// 0x060233D0
ALIGNED8 static const Texture chain_chomp_seg6_texture_060233D0[] = {
#include "actors/chain_chomp/chain_chomp_eye.rgba16.inc.c"
};

// 0x06023BD0
static const Vtx chain_chomp_seg6_vertex_06023BD0[] = {
    {{{     0,      0,    582}, 0, {   990,    380}, {0xb2, 0xb2, 0xb2, 0xff}}},
    {{{   411,      0,   -410}, 0, {   118,    880}, {0xb2, 0xb2, 0xb2, 0xff}}},
    {{{     0,      0,   -581}, 0, {   -52,    472}, {0xb2, 0xb2, 0xb2, 0xff}}},
    {{{   582,      0,      0}, 0, {   496,   1020}, {0xb2, 0xb2, 0xb2, 0xff}}},
    {{{   411,      0,    411}, 0, {   856,    816}, {0xb2, 0xb2, 0xb2, 0xff}}},
};

// 0x06023C20
static const Vtx chain_chomp_seg6_vertex_06023C20[] = {
    {{{     0,   -581,      0}, 0, {   448,   -130}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,   -156}, 0, {   334,   -408}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,   -222}, 0, {    58,   -276}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,    223}, 0, {   818,    106}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,    157}, 0, {   870,   -138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -222,   -536,      0}, 0, {   670,   -350}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   223,   -536,      0}, 0, {   204,    180}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,   -536,    157}, 0, {   542,    238}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,   -536,   -156}, 0, {     4,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,   -410,   -290}, 0, {  -390,    142}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,   -410,      0}, 0, {   -22,    534}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,   -410,    291}, 0, {   600,    640}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   370,   -216,   -369}, 0, {  -652,    368}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   537,   -222,      0}, 0, {  -196,    880}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,   -536}, 0, {  -548,   -222}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   370,   -216,    370}, 0, {   608,   1002}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06023D20
static const Vtx chain_chomp_seg6_vertex_06023D20[] = {
    {{{   291,   -410,    291}, 0, {   600,    640}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -410,    411}, 0, {  1110,    398}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,    223}, 0, {   818,    106}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   370,   -216,    370}, 0, {   608,   1002}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,   -410}, 0, {  -814,    606}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   537,   -222,      0}, 0, {  -196,    880}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   370,   -216,   -369}, 0, {  -652,    368}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   582,      0,      0}, 0, {  -292,   1160}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,    411}, 0, {   586,   1310}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,    537}, 0, {  1282,    700}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    582}, 0, {  1306,    968}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -581}, 0, {  -674,    -30}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,   -536}, 0, {  -548,   -222}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,      0,    411}, 0, {  1446,    334}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,   -222,    380}, 0, {  1412,    114}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06023E10
static const Vtx chain_chomp_seg6_vertex_06023E10[] = {
    {{{     0,   -410,   -410}, 0, {  -290,   -308}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,   -410,   -290}, 0, {  -390,    142}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,   -536,   -156}, 0, {     4,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,   -536}, 0, {  -548,   -222}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,   -222}, 0, {    58,   -276}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -410,    411}, 0, {  1110,    398}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,    157}, 0, {   870,   -138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,    223}, 0, {   818,    106}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,   -410,    291}, 0, {  1210,    -52}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,    537}, 0, {  1282,    700}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    582}, 0, {  1306,    968}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,   -222,    380}, 0, {  1412,    114}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,   -410,      0}, 0, {   840,   -444}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,   -156}, 0, {   334,   -408}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -222,   -536,      0}, 0, {   670,   -350}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06023F00
static const Vtx chain_chomp_seg6_vertex_06023F00[] = {
    {{{  -410,      0,   -410}, 0, {    44,   -372}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -581}, 0, {  -674,    -30}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -222,   -536}, 0, {  -548,   -222}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,   -222,   -379}, 0, {   116,   -538}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -410,   -410}, 0, {  -290,   -308}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,   -410,   -290}, 0, {   218,   -550}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -536,   -222}, 0, {    58,   -276}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,   -156}, 0, {   334,   -408}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,   -410,    291}, 0, {  1210,    -52}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -222,   -536,      0}, 0, {   670,   -350}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,   -536,    157}, 0, {   870,   -138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,   -410,      0}, 0, {   840,   -444}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,   -222,    380}, 0, {  1412,    114}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -536,   -222,      0}, 0, {   928,   -400}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,      0,    411}, 0, {  1446,    334}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -581,      0,      0}, 0, {   924,   -222}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024000 - 0x06024040
const Gfx chain_chomp_seg6_dl_06024000[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_060223D0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_06023BD0, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP1Triangle( 0,  4,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024040 - 0x06024240
const Gfx chain_chomp_seg6_dl_06024040[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_060213D0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_06023C20, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 0,  6,  7, 0x0,  0,  8,  6, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0,  0,  7,  3, 0x0),
    gsSP2Triangles( 9,  6,  8, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(10,  7,  6, 0x0, 10, 11,  7, 0x0),
    gsSP2Triangles(11,  3,  7, 0x0, 12, 10,  9, 0x0),
    gsSP2Triangles(12, 13, 10, 0x0, 13, 11, 10, 0x0),
    gsSP2Triangles(14, 12,  9, 0x0, 13, 15, 11, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_06023D20, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 7,  3,  5, 0x0,  7,  8,  3, 0x0),
    gsSP2Triangles( 3,  9,  1, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11,  4,  6, 0x0),
    gsSP2Triangles(11,  6, 12, 0x0, 10, 13, 14, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_06023E10, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9,  8,  5, 0x0),
    gsSP2Triangles(10, 11,  9, 0x0,  9, 11,  8, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_06023F00, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 11,  8, 0x0, 11,  5,  7, 0x0),
    gsSP2Triangles(13,  5, 11, 0x0, 12, 13, 11, 0x0),
    gsSP2Triangles(14, 13, 12, 0x0, 14, 15, 13, 0x0),
    gsSP2Triangles(15,  0,  3, 0x0, 13,  3,  5, 0x0),
    gsSP1Triangle(15,  3, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024240 - 0x060242D0
const Gfx chain_chomp_seg6_dl_06024240[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024000),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024040),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x060242D0
static const Vtx chain_chomp_seg6_vertex_060242D0[] = {
    {{{   157,    537,    157}, 0, { -1116,    776}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   223,    537,      0}, 0, {  -898,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    582,      0}, 0, { -1454,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,    411,    291}, 0, {  -832,    966}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    411,    411}, 0, { -1600,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    537,    223}, 0, { -1532,    870}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,    537,   -156}, 0, { -1004,    328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,    411,      0}, 0, {  -428,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    537,   -222}, 0, { -1374,    234}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,    537,    157}, 0, { -1902,    776}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,    537,   -156}, 0, { -1790,    328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -222,    537,      0}, 0, { -2008,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   380,    223,    380}, 0, {  -640,   1092}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    223,    537}, 0, { -1646,   1316}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x060243B0
static const Vtx chain_chomp_seg6_vertex_060243B0[] = {
    {{{     0,    411,   -410}, 0, { -1306,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,    411,   -290}, 0, {  -624,    138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   380,    223,   -379}, 0, {  -370,     10}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,    411,      0}, 0, {  -428,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   537,    223,      0}, 0, {  -112,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   380,    223,    380}, 0, {  -640,   1092}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,    537,   -156}, 0, { -1004,    328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,    411}, 0, {  -574,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    582}, 0, { -1660,   1380}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    223,    537}, 0, { -1646,   1316}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   582,      0,      0}, 0, {    -4,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,   -410}, 0, {  -280,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    223,   -536}, 0, { -1262,   -214}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    411,    411}, 0, { -1600,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,    411,    291}, 0, { -2282,    966}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,    223,    380}, 0, { -2538,   1092}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x060244B0
static const Vtx chain_chomp_seg6_vertex_060244B0[] = {
    {{{     0,    223,   -536}, 0, { -1262,   -214}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,   -410}, 0, {  -280,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -581}, 0, { -1246,   -278}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    537,   -222}, 0, { -1374,    234}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   157,    537,   -156}, 0, { -1004,    328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   291,    411,   -290}, 0, {  -624,    138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    411,   -410}, 0, { -1306,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    537,    223}, 0, { -1532,    870}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,    411,    291}, 0, { -2282,    966}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    411,    411}, 0, { -1600,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,    537,    157}, 0, { -1902,    776}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,    223,    380}, 0, { -2538,   1092}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    223,    537}, 0, { -1646,   1316}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,      0,    411}, 0, { -2626,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    582}, 0, { -1660,   1380}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,    223,   -379}, 0, { -2266,     10}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x060245B0
static const Vtx chain_chomp_seg6_vertex_060245B0[] = {
    {{{  -290,    411,   -290}, 0, { -2076,    138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    223,   -536}, 0, { -1262,   -214}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,    223,   -379}, 0, { -2266,     10}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    411,   -410}, 0, { -1306,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -581}, 0, { -1246,   -278}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,      0,   -410}, 0, { -2334,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,    537,   -156}, 0, { -1790,    328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    537,   -222}, 0, { -1374,    234}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -156,    537,    157}, 0, { -1902,    776}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,    411,      0}, 0, { -2480,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -290,    411,    291}, 0, { -2282,    966}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -536,    223,      0}, 0, { -2794,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -379,    223,    380}, 0, { -2538,   1092}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -581,      0,      0}, 0, { -2904,    552}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -410,      0,    411}, 0, { -2626,   1136}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -222,    537,      0}, 0, { -2008,    552}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x060246B0
static const Vtx chain_chomp_seg6_vertex_060246B0[] = {
    {{{     0,      0,   -581}, 0, {  1154,    138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   582,      0,      0}, 0, {   470,   1104}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,    411}, 0, {   -12,    822}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    582}, 0, {  -210,    138}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   411,      0,   -410}, 0, {   954,    822}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024700 - 0x06024900
const Gfx chain_chomp_seg6_dl_06024700[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_06021BD0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_060242D0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 0,  5,  4, 0x0,  6,  1,  7, 0x0),
    gsSP2Triangles( 1,  3,  7, 0x0,  1,  6,  2, 0x0),
    gsSP2Triangles( 6,  8,  2, 0x0,  9,  5,  2, 0x0),
    gsSP2Triangles( 8, 10,  2, 0x0, 10, 11,  2, 0x0),
    gsSP2Triangles(11,  9,  2, 0x0,  7,  3, 12, 0x0),
    gsSP2Triangles( 3, 13, 12, 0x0,  3,  4, 13, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_060243B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  6,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  7, 0x0,  5,  8,  7, 0x0),
    gsSP2Triangles( 5,  9,  8, 0x0,  2,  4, 10, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  4,  7, 10, 0x0),
    gsSP2Triangles( 2, 10, 11, 0x0, 12,  2, 11, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_060244B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0,  9, 11, 12, 0x0),
    gsSP2Triangles(12, 11, 13, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(15,  0,  2, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_060245B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  6,  3,  0, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 9,  0,  2, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0, 12, 13, 14, 0x0),
    gsSP2Triangles(12, 11, 13, 0x0, 11,  2,  5, 0x0),
    gsSP2Triangles( 9,  2, 11, 0x0, 11,  5, 13, 0x0),
    gsSP2Triangles(15,  0,  9, 0x0, 15,  6,  0, 0x0),
    gsSP1Triangle( 8, 15,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024900 - 0x06024940
const Gfx chain_chomp_seg6_dl_06024900[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_060223D0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_060246B0, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP1Triangle( 0,  4,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024940 - 0x060249D0
const Gfx chain_chomp_seg6_dl_06024940[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024700),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024900),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x060249D0
static const Lights1 chain_chomp_seg6_lights_060249D0 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060249E8
static const Vtx chain_chomp_seg6_vertex_060249E8[] = {
    {{{   341,   -287,    410}, 0, {   990,      0}, {0x69, 0xe7, 0x41, 0xff}}},
    {{{   505,    -32,    246}, 0, {     0,    990}, {0x69, 0xe7, 0x41, 0xff}}},
    {{{   374,    -49,    450}, 0, {   990,    990}, {0x69, 0xe7, 0x41, 0xff}}},
    {{{   341,   -287,    410}, 0, {   990,      0}, {0x69, 0xe7, 0x42, 0xff}}},
    {{{   473,   -270,    206}, 0, {     0,      0}, {0x69, 0xe7, 0x42, 0xff}}},
    {{{   505,    -32,    246}, 0, {     0,    990}, {0x69, 0xe7, 0x42, 0xff}}},
    {{{   374,    -49,   -449}, 0, {   990,    990}, {0x69, 0xe7, 0xbf, 0xff}}},
    {{{   505,    -32,   -245}, 0, {     0,    990}, {0x69, 0xe7, 0xbf, 0xff}}},
    {{{   341,   -287,   -409}, 0, {   990,      0}, {0x69, 0xe7, 0xbf, 0xff}}},
    {{{   505,    -32,   -245}, 0, {     0,    990}, {0x69, 0xe7, 0xbe, 0xff}}},
    {{{   473,   -270,   -205}, 0, {     0,      0}, {0x69, 0xe7, 0xbe, 0xff}}},
    {{{   341,   -287,   -409}, 0, {   990,      0}, {0x69, 0xe7, 0xbe, 0xff}}},
};

// 0x06024AA8 - 0x06024B00
const Gfx chain_chomp_seg6_dl_06024AA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_060233D0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&chain_chomp_seg6_lights_060249D0.l, 1),
    gsSPLight(&chain_chomp_seg6_lights_060249D0.a, 2),
    gsSPVertex(chain_chomp_seg6_vertex_060249E8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024B00 - 0x06024B70
const Gfx chain_chomp_seg6_dl_06024B00[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024AA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06024B70
static const Vtx chain_chomp_seg6_vertex_06024B70[] = {
    {{{   568,     -6,      1}, 0, {  -466,    -52}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   458,    227,    139}, 0, {   756,    806}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,     -6,    263}, 0, {   652,   -626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   182,     -6,   -493}, 0, {   -90,    384}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   225,     72,   -473}, 0, {   664,    930}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,     -6,   -454}, 0, {   666,   -726}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,     -6,   -454}, 0, {  -306,    174}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   325,    107,   -419}, 0, {   680,   1044}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,     -6,   -403}, 0, {   772,   -792}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,     -6,   -403}, 0, {  -114,     10}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   379,    160,   -332}, 0, {   526,   1002}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,     -6,   -262}, 0, {   578,   -702}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,     -6,   -262}, 0, {  -172,     58}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   458,    227,   -138}, 0, {   838,    944}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   568,     -6,      1}, 0, {   916,   -848}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024C60
static const Vtx chain_chomp_seg6_vertex_06024C60[] = {
    {{{   284,     -6,    455}, 0, {  -250,    814}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   225,     72,    474}, 0, {   530,   1852}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   182,     -6,    494}, 0, {   764,    134}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,     -6,    263}, 0, {  -108,    910}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   379,    160,    333}, 0, {   930,   1682}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,     -6,    404}, 0, {   670,    278}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,     -6,    404}, 0, {  -412,    892}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   325,    107,    420}, 0, {   430,   1696}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,     -6,    455}, 0, {   482,    364}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024CF0 - 0x06024D60
const Gfx chain_chomp_seg6_dl_06024CF0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_06022BD0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_06024B70, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_06024C60, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024D60 - 0x06024DD0
const Gfx chain_chomp_seg6_dl_06024D60[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024CF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06024DD0
static const Vtx chain_chomp_seg6_vertex_06024DD0[] = {
    {{{   462,      7,    263}, 0, {  1768,   -234}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   455,   -211,    139}, 0, {   702,   -864}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   568,      7,      1}, 0, {   294,    704}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,      7,   -454}, 0, {  1636,   -244}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   225,    -71,   -473}, 0, {   768,   -746}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   182,      7,   -493}, 0, {   594,    640}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,      7,   -403}, 0, {  1486,   -364}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   325,   -105,   -418}, 0, {    94,   -766}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,      7,   -454}, 0, {   268,    516}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,      7,   -262}, 0, {  1448,   -274}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,   -156,   -332}, 0, {   112,   -638}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,      7,   -403}, 0, {   202,    610}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   568,      7,      1}, 0, {  1208,   -184}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   455,   -211,   -138}, 0, {   440,   -848}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,      7,   -262}, 0, {   446,    714}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024EC0
static const Vtx chain_chomp_seg6_vertex_06024EC0[] = {
    {{{   182,      7,    494}, 0, {   860,    726}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   225,    -71,    474}, 0, {   378,    266}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,      7,    455}, 0, {   288,   1474}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,      7,    404}, 0, {  1210,    638}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,   -156,    333}, 0, {   294,    170}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   462,      7,    263}, 0, {   160,   1602}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   284,      7,    455}, 0, {   830,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   325,   -105,    419}, 0, {   360,    204}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   403,      7,    404}, 0, {   192,   1584}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06024F50 - 0x06024FC0
const Gfx chain_chomp_seg6_dl_06024F50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chain_chomp_seg6_texture_06022BD0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chain_chomp_seg6_vertex_06024DD0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(chain_chomp_seg6_vertex_06024EC0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06024FC0 - 0x06025030
const Gfx chain_chomp_seg6_dl_06024FC0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chain_chomp_seg6_dl_06024F50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
