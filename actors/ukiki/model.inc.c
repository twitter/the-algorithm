// Ukiki

// Unreferenced light group
UNUSED static const Lights1 ukiki_lights_unused1 = gdSPDefLights1(
    0x1d, 0x12, 0x07,
    0x77, 0x48, 0x1f, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 ukiki_lights_unused2 = gdSPDefLights1(
    0x39, 0x24, 0x18,
    0xe7, 0x93, 0x61, 0x28, 0x28, 0x28
);

// 0x05007BA0
static const Lights1 ukiki_seg5_lights_05007BA0 = gdSPDefLights1(
    0x39, 0x24, 0x18,
    0xe7, 0x93, 0x61, 0x28, 0x28, 0x28
);

// The hell?
UNUSED static const u64 ukiki_unused_1 = 1;

// 0x05007BC0
ALIGNED8 static const Texture ukiki_seg5_texture_05007BC0[] = {
#include "actors/ukiki/ukiki_face.rgba16.inc.c"
};

// 0x05008BC0
ALIGNED8 static const Texture ukiki_seg5_texture_05008BC0[] = {
#include "actors/ukiki/ukiki_face_blink.rgba16.inc.c"
};

// 0x05009BC0
ALIGNED8 static const Texture ukiki_seg5_texture_05009BC0[] = {
#include "actors/ukiki/ukiki_butt.rgba16.inc.c"
};

// 0x0500A3C0
ALIGNED8 static const Texture ukiki_seg5_texture_0500A3C0[] = {
#include "actors/ukiki/ukiki_fur.rgba16.inc.c"
};

// 0x0500ABC0
static const Lights1 ukiki_seg5_lights_0500ABC0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500ABD8
static const Lights1 ukiki_seg5_lights_0500ABD8 = gdSPDefLights1(
    0x77, 0x66, 0x45,
    0xee, 0xcd, 0x8a, 0x28, 0x28, 0x28
);

// 0x0500ABF0
static const Vtx ukiki_seg5_vertex_0500ABF0[] = {
    {{{   188,    -23,     43}, 0, { -1000,    918}, {0x6f, 0xdd, 0x32, 0xff}}},
    {{{   178,     68,     52}, 0, {  -832,   1062}, {0x5c, 0x2c, 0x4a, 0xff}}},
    {{{   137,     14,     87}, 0, {  -124,   1652}, {0x45, 0x2e, 0x5f, 0xff}}},
    {{{   178,     68,    -51}, 0, { -2900,   1062}, {0x68, 0x30, 0xcb, 0xff}}},
    {{{   116,     50,     83}, 0, {  -200,   1954}, {0x16, 0x2b, 0x75, 0xff}}},
    {{{    39,     19,     92}, 0, {     0,   3042}, {0xbc, 0x38, 0x5b, 0xff}}},
    {{{   104,    -40,     87}, 0, {  -124,   2114}, {0x2c, 0xa8, 0x4f, 0xff}}},
    {{{   116,     50,    -82}, 0, { -3532,   1954}, {0x16, 0x2b, 0x8b, 0xff}}},
    {{{   137,     14,    -86}, 0, { -3612,   1652}, {0x3e, 0x42, 0xa8, 0xff}}},
    {{{   188,    -23,    -42}, 0, { -2732,    918}, {0x64, 0xde, 0xbb, 0xff}}},
    {{{   144,    -81,    -42}, 0, { -2732,   1554}, {0x2a, 0x93, 0xd0, 0xff}}},
    {{{   104,    -40,    -86}, 0, { -3612,   2114}, {0x2c, 0xa8, 0xb1, 0xff}}},
    {{{    39,     19,    -91}, 0, { -3704,   3042}, {0xb6, 0x29, 0xa2, 0xff}}},
    {{{     0,    -40,    -52}, 0, { -2928,   3600}, {0x91, 0xd3, 0xd8, 0xff}}},
    {{{    39,    -79,      0}, 0, { -1868,   3042}, {0xde, 0x86, 0x00, 0xff}}},
    {{{     0,    -40,     53}, 0, {  -808,   3600}, {0x9c, 0xd0, 0x3c, 0xff}}},
};

// 0x0500ACF0
static const Vtx ukiki_seg5_vertex_0500ACF0[] = {
    {{{    46,    -22,    -91}, 0, { -3704,   2950}, {0xbc, 0xb1, 0xba, 0xff}}},
    {{{   104,    -40,    -86}, 0, { -3612,   2114}, {0x2c, 0xa8, 0xb1, 0xff}}},
    {{{   144,    -81,    -42}, 0, { -2732,   1554}, {0x2a, 0x93, 0xd0, 0xff}}},
    {{{   104,    -40,     87}, 0, {  -124,   2114}, {0x2c, 0xa8, 0x4f, 0xff}}},
    {{{   144,    -81,     43}, 0, { -1000,   1554}, {0x19, 0x92, 0x39, 0xff}}},
    {{{   188,    -23,     43}, 0, { -1000,    918}, {0x6f, 0xdd, 0x32, 0xff}}},
    {{{    46,    -22,     92}, 0, {     0,   2950}, {0xc8, 0xa7, 0x45, 0xff}}},
    {{{    39,    -79,      0}, 0, { -1868,   3042}, {0xde, 0x86, 0x00, 0xff}}},
    {{{     0,     79,    -32}, 0, { -2536,   3600}, {0x93, 0x25, 0xcd, 0xff}}},
    {{{    39,     19,    -91}, 0, { -3704,   3042}, {0xb6, 0x29, 0xa2, 0xff}}},
    {{{     0,    -40,    -52}, 0, { -2928,   3600}, {0x91, 0xd3, 0xd8, 0xff}}},
    {{{    39,     19,     92}, 0, {     0,   3042}, {0xbc, 0x38, 0x5b, 0xff}}},
    {{{     0,    -40,     53}, 0, {  -808,   3600}, {0x9c, 0xd0, 0x3c, 0xff}}},
    {{{     0,    -40,    -52}, 0, { -2852,   3826}, {0x91, 0xd3, 0xd8, 0xff}}},
    {{{     0,    -40,     53}, 0, {  -920,   3826}, {0x9c, 0xd0, 0x3c, 0xff}}},
    {{{     0,     79,     33}, 0, { -1280,   3826}, {0x8e, 0x28, 0x26, 0xff}}},
};

// 0x0500ADF0
static const Vtx ukiki_seg5_vertex_0500ADF0[] = {
    {{{   116,     50,    -82}, 0, {    64,    174}, {0x16, 0x2b, 0x8b, 0xff}}},
    {{{   139,    115,    -40}, 0, {   540,     -8}, {0x26, 0x68, 0xc3, 0xff}}},
    {{{   178,     68,    -51}, 0, {   416,   -306}, {0x68, 0x30, 0xcb, 0xff}}},
    {{{   139,    115,     41}, 0, {  1440,     -8}, {0x33, 0x67, 0x33, 0xff}}},
    {{{   178,     68,     52}, 0, {  1564,   -306}, {0x5c, 0x2c, 0x4a, 0xff}}},
    {{{   116,     50,     83}, 0, {  1916,    174}, {0x16, 0x2b, 0x75, 0xff}}},
    {{{    39,     19,     92}, 0, {  -220,   3044}, {0xbc, 0x38, 0x5b, 0xff}}},
    {{{     0,     79,     33}, 0, { -1280,   3826}, {0x8e, 0x28, 0x26, 0xff}}},
    {{{     0,    -40,     53}, 0, {  -920,   3826}, {0x9c, 0xd0, 0x3c, 0xff}}},
    {{{     0,    -40,    -52}, 0, { -2852,   3826}, {0x91, 0xd3, 0xd8, 0xff}}},
    {{{     0,     79,    -32}, 0, { -2492,   3826}, {0x93, 0x25, 0xcd, 0xff}}},
    {{{     0,     79,     33}, 0, {  1360,   1058}, {0x8e, 0x28, 0x26, 0xff}}},
    {{{    43,    100,     68}, 0, {  1744,    734}, {0xde, 0x4e, 0x5d, 0xff}}},
    {{{    40,    126,      0}, 0, {   992,    754}, {0xd2, 0x76, 0x00, 0xff}}},
    {{{     0,     79,    -32}, 0, {   620,   1058}, {0x93, 0x25, 0xcd, 0xff}}},
    {{{    39,     19,     92}, 0, {  2008,    760}, {0xbc, 0x38, 0x5b, 0xff}}},
};

// 0x0500AEF0
static const Vtx ukiki_seg5_vertex_0500AEF0[] = {
    {{{    43,    100,    -67}, 0, {   236,    734}, {0xde, 0x4e, 0xa3, 0xff}}},
    {{{    40,    126,      0}, 0, {   992,    754}, {0xd2, 0x76, 0x00, 0xff}}},
    {{{   139,    115,    -40}, 0, {   540,     -8}, {0x26, 0x68, 0xc3, 0xff}}},
    {{{     0,     79,    -32}, 0, {   620,   1058}, {0x93, 0x25, 0xcd, 0xff}}},
    {{{   139,    115,     41}, 0, {  1440,     -8}, {0x33, 0x67, 0x33, 0xff}}},
    {{{    39,     19,    -91}, 0, {   -28,    760}, {0xb6, 0x29, 0xa2, 0xff}}},
    {{{   116,     50,    -82}, 0, {    64,    174}, {0x16, 0x2b, 0x8b, 0xff}}},
};

// 0x0500AF60
static const Vtx ukiki_seg5_vertex_0500AF60[] = {
    {{{    89,     14,   -164}, 0, {     0,      0}, {0xf3, 0xea, 0x84, 0xff}}},
    {{{   143,     14,   -129}, 0, {     0,      0}, {0x74, 0xe9, 0xd5, 0xff}}},
    {{{   104,    -40,    -86}, 0, {     0,      0}, {0x2c, 0xa8, 0xb1, 0xff}}},
    {{{   137,     14,    -86}, 0, {     0,      0}, {0x3e, 0x42, 0xa8, 0xff}}},
    {{{    46,    -22,    -91}, 0, {     0,      0}, {0xbc, 0xb1, 0xba, 0xff}}},
    {{{    39,     19,    -91}, 0, {     0,      0}, {0xb6, 0x29, 0xa2, 0xff}}},
    {{{    34,     14,   -131}, 0, {     0,      0}, {0xa5, 0x4e, 0xd8, 0xff}}},
    {{{    46,    -22,     92}, 0, {     0,      0}, {0xc8, 0xa7, 0x45, 0xff}}},
    {{{    34,     14,    132}, 0, {     0,      0}, {0x8a, 0x06, 0x2e, 0xff}}},
    {{{    39,     19,     92}, 0, {     0,      0}, {0xbc, 0x38, 0x5b, 0xff}}},
    {{{    89,     14,    165}, 0, {     0,      0}, {0xf4, 0x2c, 0x76, 0xff}}},
    {{{   104,    -40,     87}, 0, {     0,      0}, {0x2c, 0xa8, 0x4f, 0xff}}},
    {{{   137,     14,     87}, 0, {     0,      0}, {0x45, 0x2e, 0x5f, 0xff}}},
    {{{   143,     14,    130}, 0, {     0,      0}, {0x74, 0xe9, 0x2b, 0xff}}},
};

// 0x0500B040 - 0x0500B1D8
const Gfx ukiki_seg5_dl_0500B040[] = {
    gsSPLight(&ukiki_seg5_lights_0500ABC0.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500ABC0.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500ABF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 2,  6,  0, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 3,  0,  9, 0x0,  3,  9,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0,  9, 11,  8, 0x0),
    gsSP2Triangles( 0, 10,  9, 0x0, 12,  7,  8, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500ACF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  5,  4,  2, 0x0),
    gsSP2Triangles( 2,  7,  0, 0x0,  0,  8,  9, 0x0),
    gsSP2Triangles( 0, 10,  8, 0x0,  0,  7, 10, 0x0),
    gsSP2Triangles( 7,  2,  4, 0x0, 11, 12,  6, 0x0),
    gsSP2Triangles( 7,  6, 12, 0x0,  6,  7,  4, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500ADF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 14, 11, 13, 0x0),
    gsSP2Triangles(15, 12, 11, 0x0, 13, 12,  3, 0x0),
    gsSP2Triangles( 5,  3, 12, 0x0, 12, 15,  5, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500AEF0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  0,  5,  3, 0x0),
    gsSP2Triangles( 5,  0,  6, 0x0,  0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B1D8 - 0x0500B278
const Gfx ukiki_seg5_dl_0500B1D8[] = {
    gsSPLight(&ukiki_seg5_lights_0500ABD8.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500ABD8.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500AF60, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 0,  4,  6, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  0,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0,  7, 11, 10, 0x0),
    gsSP2Triangles(12, 13, 11, 0x0, 11, 13, 10, 0x0),
    gsSP2Triangles(10, 12,  9, 0x0, 10, 13, 12, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  3,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B278 - 0x0500B2B8
const Gfx ukiki_seg5_dl_0500B278[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0500B2B8 - 0x0500B2E8
const Gfx ukiki_seg5_dl_0500B2B8[] = {
    gsSPDisplayList(ukiki_seg5_dl_0500B040),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(ukiki_seg5_dl_0500B1D8),
    gsSPEndDisplayList(),
};

// 0x0500B2E8 - 0x0500B310
const Gfx ukiki_seg5_dl_0500B2E8[] = {
    gsSPDisplayList(ukiki_seg5_dl_0500B278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_05007BC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(ukiki_seg5_dl_0500B2B8),
};

// 0x0500B310 - 0x0500B338
const Gfx ukiki_seg5_dl_0500B310[] = {
    gsSPDisplayList(ukiki_seg5_dl_0500B278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_05008BC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(ukiki_seg5_dl_0500B2B8),
};

// 0x0500B338
static const Lights1 ukiki_seg5_lights_0500B338 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500B350
static const Vtx ukiki_seg5_vertex_0500B350[] = {
    {{{    45,    -43,     43}, 0, {   350,   -182}, {0x26, 0x9e, 0x45, 0xff}}},
    {{{    42,      5,     63}, 0, {   386,    460}, {0x30, 0x02, 0x75, 0xff}}},
    {{{   -39,      5,     63}, 0, {   572,    496}, {0xe1, 0x01, 0x7a, 0xff}}},
    {{{    82,      2,     24}, 0, {   270,    412}, {0x78, 0xfc, 0x27, 0xff}}},
    {{{    45,    -44,    -47}, 0, {    88,   -194}, {0x3f, 0xa0, 0xcb, 0xff}}},
    {{{    59,     67,    -36}, 0, {   134,   1270}, {0x60, 0x40, 0xcd, 0xff}}},
    {{{    64,     67,      0}, 0, {   224,   1268}, {0x6d, 0x39, 0x1e, 0xff}}},
    {{{    82,      2,    -44}, 0, {   142,    412}, {0x63, 0xfe, 0xb2, 0xff}}},
    {{{    35,     61,     41}, 0, {   362,   1212}, {0x29, 0x3e, 0x66, 0xff}}},
    {{{    35,    107,      4}, 0, {   242,   1812}, {0x3f, 0x6b, 0x18, 0xff}}},
    {{{   -34,     61,     41}, 0, {   592,   1242}, {0xc7, 0x3b, 0x60, 0xff}}},
    {{{   -34,    107,      4}, 0, {   712,   1842}, {0xcb, 0x6a, 0x2a, 0xff}}},
    {{{     0,    107,    -36}, 0, {  1002,   1826}, {0x00, 0x66, 0xb5, 0xff}}},
    {{{   -14,     68,    -61}, 0, {   954,   1320}, {0xe8, 0x2e, 0x8d, 0xff}}},
    {{{   -59,     66,    -35}, 0, {   824,   1314}, {0xab, 0x41, 0xbd, 0xff}}},
};

// 0x0500B440
static const Vtx ukiki_seg5_vertex_0500B440[] = {
    {{{   -80,      2,    -44}, 0, {   816,    484}, {0x8b, 0xee, 0xd5, 0xff}}},
    {{{   -44,    -43,     43}, 0, {   616,   -142}, {0xb9, 0xa5, 0x34, 0xff}}},
    {{{   -80,      2,     24}, 0, {   688,    484}, {0x90, 0x03, 0x3a, 0xff}}},
    {{{   -63,     66,      1}, 0, {   730,   1316}, {0x91, 0x36, 0x19, 0xff}}},
    {{{   -59,     66,    -35}, 0, {   824,   1314}, {0xab, 0x41, 0xbd, 0xff}}},
    {{{   -34,    107,      4}, 0, {   712,   1842}, {0xcb, 0x6a, 0x2a, 0xff}}},
    {{{   -34,     61,     41}, 0, {   592,   1242}, {0xc7, 0x3b, 0x60, 0xff}}},
    {{{    59,     67,    -36}, 0, {   134,   1270}, {0x60, 0x40, 0xcd, 0xff}}},
    {{{    15,     68,    -61}, 0, {    12,   1306}, {0x24, 0x30, 0x91, 0xff}}},
    {{{     0,    107,    -36}, 0, {   -20,   1826}, {0x00, 0x66, 0xb5, 0xff}}},
    {{{    35,    107,      4}, 0, {   242,   1812}, {0x3f, 0x6b, 0x18, 0xff}}},
    {{{    82,      2,    -44}, 0, {   142,    412}, {0x63, 0xfe, 0xb2, 0xff}}},
    {{{   -39,      5,     63}, 0, {   572,    496}, {0xe1, 0x01, 0x7a, 0xff}}},
    {{{    45,    -43,     43}, 0, {   350,   -182}, {0x26, 0x9e, 0x45, 0xff}}},
    {{{   -44,    -44,    -47}, 0, {   862,   -154}, {0xde, 0x9a, 0xbe, 0xff}}},
    {{{    45,    -43,     43}, 0, {  1372,   -182}, {0x26, 0x9e, 0x45, 0xff}}},
};

// 0x0500B540
static const Vtx ukiki_seg5_vertex_0500B540[] = {
    {{{    35,      2,    -71}, 0, {    40,    434}, {0x15, 0xf1, 0x84, 0xff}}},
    {{{    82,      2,    -44}, 0, {   142,    412}, {0x63, 0xfe, 0xb2, 0xff}}},
    {{{    45,    -44,    -47}, 0, {    88,   -194}, {0x3f, 0xa0, 0xcb, 0xff}}},
    {{{    15,     68,    -61}, 0, {    12,   1306}, {0x24, 0x30, 0x91, 0xff}}},
    {{{     0,    107,    -36}, 0, {  1002,   1826}, {0x00, 0x66, 0xb5, 0xff}}},
    {{{    15,     68,    -61}, 0, {  1034,   1306}, {0x24, 0x30, 0x91, 0xff}}},
    {{{   -14,     68,    -61}, 0, {   954,   1320}, {0xe8, 0x2e, 0x8d, 0xff}}},
    {{{    35,      2,    -71}, 0, {  1062,    434}, {0x15, 0xf1, 0x84, 0xff}}},
    {{{   -39,      2,    -71}, 0, {   906,    466}, {0xd6, 0xff, 0x89, 0xff}}},
    {{{   -59,     66,    -35}, 0, {   824,   1314}, {0xab, 0x41, 0xbd, 0xff}}},
    {{{   -80,      2,    -44}, 0, {   816,    484}, {0x8b, 0xee, 0xd5, 0xff}}},
    {{{   -44,    -44,    -47}, 0, {   862,   -154}, {0xde, 0x9a, 0xbe, 0xff}}},
    {{{    45,    -44,    -47}, 0, {  1110,   -194}, {0x3f, 0xa0, 0xcb, 0xff}}},
    {{{    45,    -43,     43}, 0, {  1372,   -182}, {0x26, 0x9e, 0x45, 0xff}}},
};

// 0x0500B620
static const Vtx ukiki_seg5_vertex_0500B620[] = {
    {{{   -44,    -44,    -47}, 0, {   976,    850}, {0xde, 0x9a, 0xbe, 0xff}}},
    {{{    35,      2,    -71}, 0, {    56,    194}, {0x15, 0xf1, 0x84, 0xff}}},
    {{{    45,    -44,    -47}, 0, {   -88,    842}, {0x3f, 0xa0, 0xcb, 0xff}}},
    {{{   -39,      2,    -71}, 0, {   932,    200}, {0xd6, 0xff, 0x89, 0xff}}},
};

// 0x0500B660 - 0x0500B7E8
const Gfx ukiki_seg5_dl_0500B660[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500B338.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500B338.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500B350, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  7,  5, 0x0,  3,  4,  7, 0x0),
    gsSP2Triangles( 8,  1,  3, 0x0,  3,  6,  8, 0x0),
    gsSP2Triangles( 9,  8,  6, 0x0,  5,  9,  6, 0x0),
    gsSP2Triangles( 2,  8, 10, 0x0,  2,  1,  8, 0x0),
    gsSP2Triangles(11, 10,  8, 0x0, 11,  8,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 11, 12, 14, 0x0),
    gsSP1Triangle(11,  9, 12, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500B440, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  3,  6,  5, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  2,  6,  3, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles(11,  8,  7, 0x0, 12,  2,  1, 0x0),
    gsSP2Triangles( 2, 12,  6, 0x0, 13, 12,  1, 0x0),
    gsSP2Triangles(14, 15,  1, 0x0,  0, 14,  1, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500B540, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  9,  6,  8, 0x0),
    gsSP2Triangles(10,  8, 11, 0x0,  9,  8, 10, 0x0),
    gsSP1Triangle(11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B7E8 - 0x0500B820
const Gfx ukiki_seg5_dl_0500B7E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_05009BC0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ukiki_seg5_vertex_0500B620, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B820 - 0x0500B888
const Gfx ukiki_seg5_dl_0500B820[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500B660),
    gsSPDisplayList(ukiki_seg5_dl_0500B7E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500B888
static const Lights1 ukiki_seg5_lights_0500B888 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500B8A0
static const Vtx ukiki_seg5_vertex_0500B8A0[] = {
    {{{    88,     19,    -15}, 0, {  1848,   -114}, {0x5f, 0x3d, 0xc6, 0xff}}},
    {{{    89,    -13,    -15}, 0, {  1310,   -140}, {0x38, 0xb1, 0xaf, 0xff}}},
    {{{     0,      0,      1}, 0, {  2896,   1502}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{    89,    -13,     17}, 0, {  2828,   -140}, {0x62, 0xca, 0x39, 0xff}}},
    {{{    89,    -13,     17}, 0, {   718,   -140}, {0x62, 0xca, 0x39, 0xff}}},
    {{{     0,      0,      1}, 0, {   788,   1502}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{    88,     19,     17}, 0, {   214,   -114}, {0x32, 0x53, 0x51, 0xff}}},
    {{{    88,     19,     17}, 0, {  2322,   -114}, {0x32, 0x53, 0x51, 0xff}}},
};

// 0x0500B920 - 0x0500B988
const Gfx ukiki_seg5_dl_0500B920[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500B888.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500B888.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500B8A0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 0,  7,  3, 0x0,  7,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B988 - 0x0500B9E8
const Gfx ukiki_seg5_dl_0500B988[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500B920),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500B9E8
static const Lights1 ukiki_seg5_lights_0500B9E8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BA00
static const Vtx ukiki_seg5_vertex_0500BA00[] = {
    {{{    73,    -12,    -23}, 0, {   248,    740}, {0x2f, 0x98, 0xca, 0xff}}},
    {{{    73,    -12,     25}, 0, {   950,    740}, {0x50, 0xdb, 0x5b, 0xff}}},
    {{{    -1,    -12,     16}, 0, {  1256,   -602}, {0xca, 0x99, 0x30, 0xff}}},
    {{{    68,     35,    -23}, 0, {  2128,    900}, {0x45, 0x35, 0xa5, 0xff}}},
    {{{    73,    -12,    -23}, 0, {  2752,    740}, {0x2f, 0x98, 0xca, 0xff}}},
    {{{    68,     35,     25}, 0, {  1554,    900}, {0x16, 0x70, 0x36, 0xff}}},
    {{{    -4,     18,     16}, 0, {  1660,   -498}, {0xa0, 0x1d, 0x4d, 0xff}}},
    {{{    -1,    -12,    -14}, 0, {  2434,   -602}, {0xa9, 0xd0, 0xb2, 0xff}}},
    {{{    -4,     18,    -14}, 0, {  2020,   -498}, {0xb4, 0x59, 0xd0, 0xff}}},
};

// 0x0500BA90 - 0x0500BB28
const Gfx ukiki_seg5_dl_0500BA90[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500B9E8.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500B9E8.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500BA00, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 1,  5,  6, 0x0,  1,  6,  2, 0x0),
    gsSP2Triangles( 3,  5,  1, 0x0,  4,  2,  7, 0x0),
    gsSP2Triangles( 7,  2,  6, 0x0,  3,  4,  7, 0x0),
    gsSP2Triangles( 3,  7,  8, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 5,  3,  8, 0x0,  5,  8,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BB28 - 0x0500BB88
const Gfx ukiki_seg5_dl_0500BB28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500BA90),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500BB88
static const Lights1 ukiki_seg5_lights_0500BB88 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BBA0
static const Vtx ukiki_seg5_vertex_0500BBA0[] = {
    {{{     0,      0,      0}, 0, {   498,   -748}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{    89,    -13,    -16}, 0, {   290,   1200}, {0x38, 0xb1, 0xaf, 0xff}}},
    {{{    89,    -13,     16}, 0, {  1014,   1200}, {0x62, 0xca, 0x39, 0xff}}},
    {{{    88,     19,     16}, 0, {  1728,   1174}, {0x32, 0x53, 0x51, 0xff}}},
    {{{    88,     19,    -16}, 0, {  2394,   1174}, {0x5f, 0x3d, 0xc6, 0xff}}},
    {{{    89,    -13,    -16}, 0, {  3066,   1200}, {0x38, 0xb1, 0xaf, 0xff}}},
    {{{     0,      0,      0}, 0, {  3274,   -748}, {0x82, 0xfc, 0x00, 0xff}}},
};

// 0x0500BC10 - 0x0500BC78
const Gfx ukiki_seg5_dl_0500BC10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500BB88.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500BB88.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500BBA0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  2,  5,  4, 0x0),
    gsSP2Triangles( 6,  3,  4, 0x0,  6,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BC78 - 0x0500BCD8
const Gfx ukiki_seg5_dl_0500BC78[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500BC10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500BCD8
static const Lights1 ukiki_seg5_lights_0500BCD8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BCF0
static const Vtx ukiki_seg5_vertex_0500BCF0[] = {
    {{{    -4,     18,     15}, 0, {  1770,   -134}, {0xa0, 0x1d, 0x4d, 0xff}}},
    {{{    -1,    -12,    -15}, 0, {   356,   -128}, {0xa9, 0xd0, 0xb2, 0xff}}},
    {{{    -1,    -12,     15}, 0, {  1024,   -128}, {0xca, 0x99, 0x30, 0xff}}},
    {{{    -4,     18,    -15}, 0, {  2572,   -134}, {0xb4, 0x59, 0xd0, 0xff}}},
    {{{    68,     35,     24}, 0, {  1792,    948}, {0x16, 0x70, 0x36, 0xff}}},
    {{{    -1,    -12,    -15}, 0, {  3280,   -128}, {0xa9, 0xd0, 0xb2, 0xff}}},
    {{{    73,    -12,     24}, 0, {  1048,    958}, {0x50, 0xdb, 0x5b, 0xff}}},
    {{{    73,    -12,    -24}, 0, {   338,    958}, {0x2f, 0x98, 0xca, 0xff}}},
    {{{    68,     35,    -24}, 0, {  2544,    948}, {0x45, 0x35, 0xa5, 0xff}}},
    {{{    73,    -12,     24}, 0, {  3974,    958}, {0x50, 0xdb, 0x5b, 0xff}}},
    {{{    73,    -12,    -24}, 0, {  3262,    958}, {0x2f, 0x98, 0xca, 0xff}}},
};

// 0x0500BDA0 - 0x0500BE38
const Gfx ukiki_seg5_dl_0500BDA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500BCD8.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500BCD8.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500BCF0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  3,  5, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 0,  6,  4, 0x0,  2,  1,  7, 0x0),
    gsSP2Triangles( 2,  7,  6, 0x0,  3,  4,  8, 0x0),
    gsSP2Triangles( 9,  8,  4, 0x0,  5,  3,  8, 0x0),
    gsSP2Triangles( 5,  8, 10, 0x0,  9, 10,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BE38 - 0x0500BE98
const Gfx ukiki_seg5_dl_0500BE38[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500BDA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500BE98
static const Lights1 ukiki_seg5_lights_0500BE98 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BEB0
static const Vtx ukiki_seg5_vertex_0500BEB0[] = {
    {{{    47,     29,      0}, 0, {  1588,     60}, {0x4e, 0x5b, 0xd9, 0xff}}},
    {{{    53,    -18,      1}, 0, {  2668,    104}, {0x63, 0xbb, 0x26, 0xff}}},
    {{{    50,      4,    -23}, 0, {   980,     84}, {0x33, 0xea, 0x8f, 0xff}}},
    {{{    -1,     -2,    -18}, 0, {  1002,   1040}, {0xae, 0x0e, 0xa1, 0xff}}},
    {{{    53,    -18,      1}, 0, {   496,    104}, {0x63, 0xbb, 0x26, 0xff}}},
    {{{     2,    -33,      1}, 0, {   500,   1068}, {0xd7, 0x90, 0xd7, 0xff}}},
    {{{    50,      6,     25}, 0, {    18,     82}, {0x2b, 0x23, 0x72, 0xff}}},
    {{{    -2,      0,     19}, 0, {   -20,   1038}, {0xb2, 0xdd, 0x5d, 0xff}}},
    {{{    -6,     29,      0}, 0, {  1592,   1010}, {0xba, 0x62, 0x26, 0xff}}},
    {{{    -2,      0,     19}, 0, {  2150,   1038}, {0xb2, 0xdd, 0x5d, 0xff}}},
    {{{    50,      6,     25}, 0, {  2190,     82}, {0x2b, 0x23, 0x72, 0xff}}},
};

// 0x0500BF60 - 0x0500BFF8
const Gfx ukiki_seg5_dl_0500BF60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500BE98.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500BE98.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500BEB0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  3, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 3,  5,  7, 0x0,  0,  3,  8, 0x0),
    gsSP2Triangles( 3,  9,  8, 0x0,  0, 10,  1, 0x0),
    gsSP2Triangles(10,  0,  8, 0x0, 10,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BFF8 - 0x0500C058
const Gfx ukiki_seg5_dl_0500BFF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500BF60),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C058
static const Lights1 ukiki_seg5_lights_0500C058 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C070
static const Vtx ukiki_seg5_vertex_0500C070[] = {
    {{{    45,     19,      0}, 0, {  2822,    894}, {0x69, 0x3d, 0xde, 0xff}}},
    {{{    26,    -34,      1}, 0, {  1692,    926}, {0x2a, 0x8d, 0x1f, 0xff}}},
    {{{    35,     -8,    -27}, 0, {  1100,    910}, {0x19, 0xcc, 0x8f, 0xff}}},
    {{{    45,     19,      0}, 0, {   546,    894}, {0x69, 0x3d, 0xde, 0xff}}},
    {{{    -4,      6,    -23}, 0, {  1112,   -128}, {0xb5, 0x35, 0xaa, 0xff}}},
    {{{   -12,    -15,      1}, 0, {  1694,   -116}, {0xa3, 0xb1, 0xde, 0xff}}},
    {{{    36,     -6,     29}, 0, {  2274,    908}, {0x35, 0x19, 0x70, 0xff}}},
    {{{    -4,      7,     25}, 0, {  2264,   -130}, {0xa2, 0x03, 0x54, 0xff}}},
    {{{     3,     29,      0}, 0, {   544,   -142}, {0xe9, 0x78, 0x1f, 0xff}}},
    {{{    -4,      7,     25}, 0, {   -14,   -130}, {0xa2, 0x03, 0x54, 0xff}}},
    {{{    36,     -6,     29}, 0, {    -2,    908}, {0x35, 0x19, 0x70, 0xff}}},
};

// 0x0500C120 - 0x0500C1B8
const Gfx ukiki_seg5_dl_0500C120[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C058.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C058.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C070, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  2,  5,  4, 0x0),
    gsSP2Triangles( 1,  6,  7, 0x0,  1,  7,  5, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  4,  5,  7, 0x0),
    gsSP2Triangles( 3,  4,  8, 0x0,  4,  9,  8, 0x0),
    gsSP2Triangles(10,  3,  8, 0x0, 10,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C1B8 - 0x0500C218
const Gfx ukiki_seg5_dl_0500C1B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C120),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C218
static const Lights1 ukiki_seg5_lights_0500C218 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C230
static const Vtx ukiki_seg5_vertex_0500C230[] = {
    {{{    -6,     29,      1}, 0, {  1878,     52}, {0x9c, 0x44, 0x25, 0xff}}},
    {{{     2,    -33,      0}, 0, {  3150,    -22}, {0xb0, 0xa4, 0xde, 0xff}}},
    {{{    -1,     -2,     19}, 0, {  1244,     14}, {0xd4, 0xd8, 0x6f, 0xff}}},
    {{{    -2,      0,    -18}, 0, {  2480,     16}, {0xcb, 0x1d, 0x91, 0xff}}},
    {{{    47,     29,      1}, 0, {  1872,   1100}, {0x2d, 0x6e, 0xd6, 0xff}}},
    {{{    50,      4,     24}, 0, {  1194,   1070}, {0x4e, 0x1c, 0x60, 0xff}}},
    {{{     2,    -33,      0}, 0, {   596,    -22}, {0xb0, 0xa4, 0xde, 0xff}}},
    {{{    53,    -18,      0}, 0, {   592,   1042}, {0x48, 0xa2, 0x2b, 0xff}}},
    {{{    50,      6,    -23}, 0, {   -10,   1072}, {0x53, 0xf9, 0xa1, 0xff}}},
    {{{    50,      6,    -23}, 0, {  2546,   1072}, {0x53, 0xf9, 0xa1, 0xff}}},
};

// 0x0500C2D0 - 0x0500C368
const Gfx ukiki_seg5_dl_0500C2D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C218.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C218.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C230, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 0,  5,  4, 0x0,  2,  6,  7, 0x0),
    gsSP2Triangles( 2,  7,  5, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  9,  4, 0x0,  3,  4,  9, 0x0),
    gsSP2Triangles( 1,  3,  9, 0x0,  6,  8,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C368 - 0x0500C3C8
const Gfx ukiki_seg5_dl_0500C368[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C2D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C3C8
static const Lights1 ukiki_seg5_lights_0500C3C8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C3E0
static const Vtx ukiki_seg5_vertex_0500C3E0[] = {
    {{{     3,     29,      1}, 0, {  3150,     -2}, {0xc8, 0x6d, 0x1f, 0xff}}},
    {{{   -12,    -15,      0}, 0, {  1868,     42}, {0x90, 0xce, 0xe2, 0xff}}},
    {{{    -4,      6,     24}, 0, {  2484,     20}, {0xbb, 0xf1, 0x69, 0xff}}},
    {{{    35,     -8,     28}, 0, {  2514,    966}, {0x54, 0x00, 0x5f, 0xff}}},
    {{{    45,     19,      1}, 0, {  3150,    938}, {0x4d, 0x5e, 0xdd, 0xff}}},
    {{{    26,    -34,      0}, 0, {  1868,    990}, {0x00, 0x87, 0x23, 0xff}}},
    {{{    36,     -6,    -27}, 0, {  3782,    964}, {0x41, 0xca, 0xa2, 0xff}}},
    {{{     3,     29,      1}, 0, {   594,     -2}, {0xc8, 0x6d, 0x1f, 0xff}}},
    {{{    -4,      7,    -23}, 0, {  1256,     20}, {0xd4, 0x36, 0x97, 0xff}}},
    {{{    36,     -6,    -27}, 0, {  1226,    964}, {0x41, 0xca, 0xa2, 0xff}}},
    {{{    45,     19,      1}, 0, {   596,    938}, {0x4d, 0x5e, 0xdd, 0xff}}},
};

// 0x0500C490 - 0x0500C528
const Gfx ukiki_seg5_dl_0500C490[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C3C8.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C3C8.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C3E0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 2,  5,  3, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  1, 0x0),
    gsSP2Triangles( 1,  8,  9, 0x0,  1,  9,  5, 0x0),
    gsSP2Triangles( 8,  7, 10, 0x0,  8, 10,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C528 - 0x0500C588
const Gfx ukiki_seg5_dl_0500C528[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C490),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C588
static const Lights1 ukiki_seg5_lights_0500C588 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C5A0
static const Vtx ukiki_seg5_vertex_0500C5A0[] = {
    {{{    48,     11,     12}, 0, {  1362,     20}, {0x55, 0x52, 0x2c, 0xff}}},
    {{{    46,    -11,    -10}, 0, {   876,    -10}, {0x46, 0xa0, 0xd4, 0xff}}},
    {{{    48,     11,    -10}, 0, {  1132,     20}, {0x2d, 0x31, 0x95, 0xff}}},
    {{{     7,     -4,     -7}, 0, {   862,   1056}, {0xbf, 0xd4, 0x9e, 0xff}}},
    {{{     8,     11,     -7}, 0, {  1120,   1078}, {0xad, 0x56, 0xd8, 0xff}}},
    {{{    46,    -11,     12}, 0, {   590,    -10}, {0x21, 0xc7, 0x6c, 0xff}}},
    {{{     7,     -4,      8}, 0, {   604,   1056}, {0xa0, 0xb8, 0x27, 0xff}}},
    {{{    48,     11,     12}, 0, {   340,     20}, {0x55, 0x52, 0x2c, 0xff}}},
    {{{     8,     11,      8}, 0, {   352,   1078}, {0xc5, 0x35, 0x62, 0xff}}},
    {{{     8,     11,     -7}, 0, {    98,   1078}, {0xad, 0x56, 0xd8, 0xff}}},
    {{{    48,     11,    -10}, 0, {   110,     20}, {0x2d, 0x31, 0x95, 0xff}}},
};

// 0x0500C650 - 0x0500C6E8
const Gfx ukiki_seg5_dl_0500C650[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C588.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C588.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C5A0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 1,  5,  6, 0x0,  1,  6,  3, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9,  6,  8, 0x0),
    gsSP2Triangles( 7,  9,  8, 0x0,  7, 10,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C6E8 - 0x0500C748
const Gfx ukiki_seg5_dl_0500C6E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C650),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C748
static const Lights1 ukiki_seg5_lights_0500C748 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C760
static const Vtx ukiki_seg5_vertex_0500C760[] = {
    {{{    47,     12,     14}, 0, {   160,    998}, {0x54, 0x54, 0x2b, 0xff}}},
    {{{    46,    -14,    -13}, 0, {   924,   1008}, {0x4e, 0xa7, 0xd5, 0xff}}},
    {{{    47,     12,    -13}, 0, {   538,    998}, {0x2d, 0x33, 0x96, 0xff}}},
    {{{    -2,     11,    -10}, 0, {   546,    -98}, {0xa9, 0x52, 0xd7, 0xff}}},
    {{{    -2,    -11,    -10}, 0, {   932,    -90}, {0xc5, 0xce, 0x9c, 0xff}}},
    {{{    47,     12,     14}, 0, {  1694,    998}, {0x54, 0x54, 0x2b, 0xff}}},
    {{{    46,    -14,     14}, 0, {  1314,   1008}, {0x2d, 0xca, 0x69, 0xff}}},
    {{{    -2,    -11,     12}, 0, {  1306,    -90}, {0xa8, 0xaf, 0x28, 0xff}}},
    {{{    -2,     11,     12}, 0, {   152,    -98}, {0xc8, 0x33, 0x65, 0xff}}},
    {{{    -2,     11,     12}, 0, {  1686,    -98}, {0xc8, 0x33, 0x65, 0xff}}},
};

// 0x0500C800 - 0x0500C898
const Gfx ukiki_seg5_dl_0500C800[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C748.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C748.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C760, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  4,  3, 0x0),
    gsSP2Triangles( 5,  6,  1, 0x0,  1,  6,  7, 0x0),
    gsSP2Triangles( 1,  7,  4, 0x0,  3,  4,  7, 0x0),
    gsSP2Triangles( 0,  3,  8, 0x0,  3,  7,  9, 0x0),
    gsSP2Triangles( 6,  5,  9, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C898 - 0x0500C8F8
const Gfx ukiki_seg5_dl_0500C898[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C800),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500C8F8
static const Lights1 ukiki_seg5_lights_0500C8F8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C910
static const Vtx ukiki_seg5_vertex_0500C910[] = {
    {{{    49,     16,     14}, 0, {   612,    -26}, {0x50, 0x57, 0x2b, 0xff}}},
    {{{    49,    -15,    -13}, 0, {  1114,    -26}, {0x50, 0xa9, 0xd5, 0xff}}},
    {{{    49,     16,    -13}, 0, {   370,    -16}, {0x2b, 0x35, 0x96, 0xff}}},
    {{{    -2,     13,    -10}, 0, {   372,    996}, {0xa8, 0x51, 0xd8, 0xff}}},
    {{{    49,    -15,    -13}, 0, {    92,    -26}, {0x50, 0xa9, 0xd5, 0xff}}},
    {{{    -2,    -12,    -10}, 0, {    80,    990}, {0xc5, 0xce, 0x9c, 0xff}}},
    {{{    -2,     13,     12}, 0, {   622,    990}, {0xc7, 0x32, 0x65, 0xff}}},
    {{{    -2,    -12,    -10}, 0, {  1102,    990}, {0xc5, 0xce, 0x9c, 0xff}}},
    {{{    -2,    -12,     12}, 0, {   884,    980}, {0xa8, 0xaf, 0x28, 0xff}}},
    {{{    49,    -15,     14}, 0, {   886,    -36}, {0x2d, 0xcc, 0x69, 0xff}}},
};

// 0x0500C9B0 - 0x0500CA48
const Gfx ukiki_seg5_dl_0500C9B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ukiki_seg5_texture_0500A3C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ukiki_seg5_lights_0500C8F8.l, 1),
    gsSPLight(&ukiki_seg5_lights_0500C8F8.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500C910, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  3, 0x0),
    gsSP2Triangles( 0,  3,  6, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 3,  8,  6, 0x0,  0,  9,  1, 0x0),
    gsSP2Triangles( 1,  9,  8, 0x0,  1,  8,  7, 0x0),
    gsSP2Triangles( 9,  0,  6, 0x0,  9,  6,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500CA48 - 0x0500CAA8
const Gfx ukiki_seg5_dl_0500CA48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ukiki_seg5_dl_0500C9B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0500CAA8
static const Vtx ukiki_seg5_vertex_0500CAA8[] = {
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0x00}}},
    {{{    22,    -23,     14}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0x00}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0x00}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0xff}}},
    {{{    50,      4,     22}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{    50,      4,    -21}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{    12,     30,    -21}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{    12,     30,     22}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{    12,     30,     22}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{    12,     30,    -21}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{    50,      4,    -21}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    50,      4,     22}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    22,    -23,     14}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
};

// 0x0500CBA8
static const Vtx ukiki_seg5_vertex_0500CBA8[] = {
    {{{    22,    -23,     14}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0xff}}},
    {{{    50,      4,     22}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0x00}}},
    {{{    12,     30,     22}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0x00}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0xff}}},
    {{{    50,      4,    -21}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    12,     30,    -21}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
};

// 0x0500CC28
static const Vtx ukiki_seg5_vertex_0500CC28[] = {
    {{{    12,     30,     21}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0x00}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0x00}}},
    {{{    22,    -23,     14}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0x00}}},
    {{{    50,      4,     21}, 0, {     0,      0}, {0xf7, 0xee, 0x7d, 0xff}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    12,     30,    -22}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    50,      4,    -22}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0xf7, 0xee, 0x83, 0xff}}},
    {{{    22,    -23,     14}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    50,      4,    -22}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    50,      4,     21}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    12,     30,     21}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{    12,     30,    -22}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0x97, 0x47, 0x00, 0xff}}},
};

// 0x0500CD28
static const Vtx ukiki_seg5_vertex_0500CD28[] = {
    {{{    12,     30,    -22}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{    12,     30,     21}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0x00}}},
    {{{    50,      4,     21}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0x00}}},
    {{{    50,      4,    -22}, 0, {     0,      0}, {0x47, 0x69, 0x00, 0xff}}},
    {{{   -11,     -5,     15}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0xff}}},
    {{{   -11,     -5,    -15}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0xff}}},
    {{{    22,    -23,    -14}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0xff}}},
    {{{    22,    -23,     14}, 0, {     0,      0}, {0xc6, 0x90, 0x00, 0xff}}},
};

// 0x0500CDA8
static const Vtx ukiki_seg5_vertex_0500CDA8[] = {
    {{{    81,     10,    -39}, 0, {     0,      0}, {0x68, 0xb9, 0x00, 0x00}}},
    {{{    81,     10,      5}, 0, {     0,      0}, {0x58, 0xa8, 0x17, 0x00}}},
    {{{    62,    -17,    -36}, 0, {     0,      0}, {0x18, 0x93, 0xc5, 0x00}}},
    {{{   -26,     21,     23}, 0, {     0,      0}, {0x8f, 0xec, 0x34, 0xff}}},
    {{{   -26,     21,    -25}, 0, {     0,      0}, {0xbd, 0xf5, 0x95, 0xff}}},
    {{{   -22,      0,    -25}, 0, {     0,      0}, {0x9a, 0xc4, 0xd4, 0xff}}},
    {{{   -22,      0,     23}, 0, {     0,      0}, {0xbb, 0xb5, 0x4a, 0xff}}},
    {{{    36,    -14,     39}, 0, {     0,      0}, {0x24, 0x9b, 0x42, 0xff}}},
    {{{   -26,     21,    -25}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{   -26,     21,     23}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    66,     11,     39}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    81,     10,      5}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    81,     10,    -39}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    66,     11,     39}, 0, {     0,      0}, {0x0e, 0xe4, 0x7a, 0xff}}},
    {{{    81,     10,    -39}, 0, {     0,      0}, {0xf0, 0x00, 0x83, 0xff}}},
};

// 0x0500CE98
static const Vtx ukiki_seg5_vertex_0500CE98[] = {
    {{{    62,    -17,     41}, 0, {     0,      0}, {0x3b, 0x92, 0x12, 0x00}}},
    {{{    36,    -14,    -34}, 0, {     0,      0}, {0xfc, 0x9f, 0xaf, 0x00}}},
    {{{    66,     11,    -34}, 0, {     0,      0}, {0x4e, 0x24, 0xa4, 0x00}}},
    {{{    81,     10,      0}, 0, {     0,      0}, {0x59, 0xa8, 0xee, 0xff}}},
    {{{   -22,      0,     25}, 0, {     0,      0}, {0xbe, 0xb2, 0x4a, 0xff}}},
    {{{    81,     10,     43}, 0, {     0,      0}, {0xeb, 0x00, 0x7d, 0xff}}},
    {{{   -26,     21,     25}, 0, {     0,      0}, {0x8f, 0xec, 0x34, 0xff}}},
    {{{   -22,      0,    -23}, 0, {     0,      0}, {0x98, 0xc6, 0xd6, 0xff}}},
    {{{   -26,     21,    -23}, 0, {     0,      0}, {0xd2, 0x52, 0xac, 0xff}}},
    {{{    81,     10,     43}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    81,     10,      0}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{   -26,     21,     25}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{    81,     10,     43}, 0, {     0,      0}, {0x68, 0xb9, 0x00, 0xff}}},
};

// 0x0500CF68 - 0x0500CFF0
const Gfx ukiki_seg5_dl_0500CF68[] = {
    gsSPLight(&ukiki_seg5_lights_05007BA0.l, 1),
    gsSPLight(&ukiki_seg5_lights_05007BA0.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500CAA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500CBA8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500CFF0 - 0x0500D078
const Gfx ukiki_seg5_dl_0500CFF0[] = {
    gsSPLight(&ukiki_seg5_lights_05007BA0.l, 1),
    gsSPLight(&ukiki_seg5_lights_05007BA0.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500CC28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(ukiki_seg5_vertex_0500CD28, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D078 - 0x0500D108
const Gfx ukiki_seg5_dl_0500D078[] = {
    gsSPLight(&ukiki_seg5_lights_05007BA0.l, 1),
    gsSPLight(&ukiki_seg5_lights_05007BA0.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500CDA8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  2,  7,  6, 0x0),
    gsSP2Triangles( 2,  6,  5, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 8, 10, 11, 0x0,  8, 11, 12, 0x0),
    gsSP2Triangles(13,  3,  6, 0x0, 13,  6,  7, 0x0),
    gsSP2Triangles( 4, 14,  2, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 1, 13,  7, 0x0,  1,  7,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D108 - 0x0500D198
const Gfx ukiki_seg5_dl_0500D108[] = {
    gsSPLight(&ukiki_seg5_lights_05007BA0.l, 1),
    gsSPLight(&ukiki_seg5_lights_05007BA0.a, 2),
    gsSPVertex(ukiki_seg5_vertex_0500CE98, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  0,  5, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 1,  7,  8, 0x0,  1,  8,  2, 0x0),
    gsSP2Triangles( 9, 10,  2, 0x0,  9,  2,  8, 0x0),
    gsSP2Triangles( 9,  8, 11, 0x0,  4,  7,  1, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  7,  4,  6, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  0,  3, 12, 0x0),
    gsSPEndDisplayList(),
};
