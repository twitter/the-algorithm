// Lakitu (Cameraman)

// 0x06000000
ALIGNED8 static const Texture lakitu_seg6_texture_06000000[] = {
#include "actors/lakitu_cameraman/lakitu_cameraman_cloud_face_unused.rgba16.inc.c"
};

// 0x06000800
ALIGNED8 static const Texture lakitu_seg6_texture_06000800[] = {
#include "actors/lakitu_cameraman/lakitu_cameraman_eyes_open.rgba16.inc.c"
};

// 0x06001800
ALIGNED8 static const Texture lakitu_seg6_texture_06001800[] = {
#include "actors/lakitu_cameraman/lakitu_cameraman_eyes_closed.rgba16.inc.c"
};

// 0x06002800
ALIGNED8 static const Texture lakitu_seg6_texture_06002800[] = {
#include "actors/lakitu_cameraman/lakitu_cameraman_shell.rgba16.inc.c"
};

// 0x06003000
ALIGNED8 static const Texture lakitu_seg6_texture_06003000[] = {
#include "actors/lakitu_cameraman/lakitu_cameraman_frown.rgba16.inc.c"
};

// 0x06003800
ALIGNED8 static const Texture lakitu_seg6_texture_06003800[] = {
#include "actors/lakitu_cameraman/lakitu_camera_lens.rgba16.inc.c"
};

// 0x06003A00
static const Lights1 lakitu_seg6_lights_06003A00 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06003A18
static const Lights1 lakitu_seg6_lights_06003A18 = gdSPDefLights1(
    0x79, 0x55, 0x00,
    0xf2, 0xab, 0x00, 0x28, 0x28, 0x28
);

// 0x06003A30
static const Vtx lakitu_seg6_vertex_06003A30[] = {
    {{{    91,    -25,     86}, 0, {   240,    464}, {0x3e, 0xd0, 0x63, 0xff}}},
    {{{   132,    -17,     43}, 0, {    34,    468}, {0x71, 0xce, 0x1a, 0xff}}},
    {{{   137,     35,     43}, 0, {     0,    980}, {0x64, 0x33, 0x39, 0xff}}},
    {{{    -2,     15,    -96}, 0, {   684,    990}, {0xd6, 0x33, 0x94, 0xff}}},
    {{{    84,     28,    -96}, 0, {   242,    984}, {0x36, 0x2a, 0x96, 0xff}}},
    {{{    91,    -22,    -81}, 0, {   240,    474}, {0x24, 0xc2, 0x98, 0xff}}},
    {{{   137,     35,    -40}, 0, {     0,    980}, {0x66, 0x45, 0xe3, 0xff}}},
    {{{   132,    -17,    -40}, 0, {    34,    468}, {0x6a, 0xd6, 0xcb, 0xff}}},
    {{{    84,    -76,    -28}, 0, {   310,    -14}, {0x3a, 0x94, 0xe4, 0xff}}},
    {{{    22,    -84,    -28}, 0, {   612,    -10}, {0xeb, 0x8f, 0xcb, 0xff}}},
    {{{     2,    -35,    -81}, 0, {   684,    480}, {0xd1, 0xbf, 0x9f, 0xff}}},
    {{{    22,    -84,     30}, 0, {   612,    -10}, {0xe0, 0x89, 0x1b, 0xff}}},
    {{{    84,    -76,     30}, 0, {   310,    -14}, {0x30, 0x97, 0x33, 0xff}}},
    {{{     2,    -35,     86}, 0, {   684,    470}, {0xea, 0xbb, 0x67, 0xff}}},
    {{{    84,     28,     99}, 0, {   242,    984}, {0x1f, 0x28, 0x74, 0xff}}},
    {{{    -2,     15,     99}, 0, {   684,    990}, {0xc1, 0x34, 0x60, 0xff}}},
};

// 0x06003B30
static const Vtx lakitu_seg6_vertex_06003B30[] = {
    {{{    -2,     15,     99}, 0, {   684,    990}, {0xc1, 0x34, 0x60, 0xff}}},
    {{{   -56,      7,     43}, 0, {   958,    992}, {0x87, 0x10, 0x21, 0xff}}},
    {{{   -33,    -40,     43}, 0, {   884,    484}, {0xa3, 0xbb, 0x31, 0xff}}},
    {{{     2,    -35,     86}, 0, {   684,    470}, {0xea, 0xbb, 0x67, 0xff}}},
    {{{    22,    -84,     30}, 0, {   612,    -10}, {0xe0, 0x89, 0x1b, 0xff}}},
    {{{   -33,    -40,    -40}, 0, {   884,    484}, {0xa1, 0xb1, 0xe7, 0xff}}},
    {{{   -56,      7,    -40}, 0, {   958,    992}, {0x90, 0x14, 0xc9, 0xff}}},
    {{{    22,    -84,    -28}, 0, {   612,    -10}, {0xeb, 0x8f, 0xcb, 0xff}}},
    {{{     2,    -35,    -81}, 0, {   684,    480}, {0xd1, 0xbf, 0x9f, 0xff}}},
    {{{    -2,     15,    -96}, 0, {   684,    990}, {0xd6, 0x33, 0x94, 0xff}}},
};

// 0x06003BD0
static const Vtx lakitu_seg6_vertex_06003BD0[] = {
    {{{     0,     51,      0}, 0, {     0,      0}, {0xcf, 0x75, 0x00, 0xff}}},
    {{{    68,     61,     35}, 0, {     0,      0}, {0x09, 0x7b, 0x1a, 0xff}}},
    {{{    68,     61,    -33}, 0, {     0,      0}, {0x01, 0x7a, 0xe0, 0xff}}},
    {{{    84,     28,    -96}, 0, {     0,      0}, {0x36, 0x2a, 0x96, 0xff}}},
    {{{    -2,     15,    -96}, 0, {     0,      0}, {0xd6, 0x33, 0x94, 0xff}}},
    {{{   137,     35,    -40}, 0, {     0,      0}, {0x66, 0x45, 0xe3, 0xff}}},
    {{{   -56,      7,    -40}, 0, {     0,      0}, {0x90, 0x14, 0xc9, 0xff}}},
    {{{    -2,     15,     99}, 0, {     0,      0}, {0xc1, 0x34, 0x60, 0xff}}},
    {{{    84,     28,     99}, 0, {     0,      0}, {0x1f, 0x28, 0x74, 0xff}}},
    {{{   -56,      7,     43}, 0, {     0,      0}, {0x87, 0x10, 0x21, 0xff}}},
    {{{   137,     35,     43}, 0, {     0,      0}, {0x64, 0x33, 0x39, 0xff}}},
};

// 0x06003C80 - 0x06003DB0
const Gfx lakitu_seg6_dl_06003C80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lakitu_seg6_texture_06002800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lakitu_seg6_lights_06003A00.l, 1),
    gsSPLight(&lakitu_seg6_lights_06003A00.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06003A30, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 5,  7,  8, 0x0,  8,  7,  1, 0x0),
    gsSP2Triangles( 6,  1,  7, 0x0,  5,  8,  9, 0x0),
    gsSP2Triangles( 5,  9, 10, 0x0,  3,  5, 10, 0x0),
    gsSP2Triangles( 6,  2,  1, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles( 8, 12, 11, 0x0,  8,  1, 12, 0x0),
    gsSP2Triangles(12,  1,  0, 0x0, 13, 11, 12, 0x0),
    gsSP2Triangles(13, 12,  0, 0x0, 14, 13,  0, 0x0),
    gsSP2Triangles( 0,  2, 14, 0x0, 14, 15, 13, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06003B30, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 1,  5,  2, 0x0,  1,  6,  5, 0x0),
    gsSP2Triangles( 4,  5,  7, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 8,  5,  6, 0x0,  8,  6,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06003DB0 - 0x06003E30
const Gfx lakitu_seg6_dl_06003DB0[] = {
    gsSPLight(&lakitu_seg6_lights_06003A18.l, 1),
    gsSPLight(&lakitu_seg6_lights_06003A18.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06003BD0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 1,  5,  2, 0x0,  4,  0,  2, 0x0),
    gsSP2Triangles( 5,  3,  2, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 7,  8,  1, 0x0,  9,  7,  0, 0x0),
    gsSP2Triangles( 1,  0,  7, 0x0,  8, 10,  1, 0x0),
    gsSP2Triangles( 1, 10,  5, 0x0,  0,  6,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x06003E30 - 0x06003E98
const Gfx lakitu_seg6_dl_06003E30[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lakitu_seg6_dl_06003C80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(lakitu_seg6_dl_06003DB0),
    gsSPEndDisplayList(),
};

// 0x06003E98
static const Lights1 lakitu_seg6_lights_06003E98 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06003EB0
static const Vtx lakitu_seg6_vertex_06003EB0[] = {
    {{{     2,     -6,     83}, 0, { -1072,    916}, {0xcb, 0x25, 0x6c, 0xff}}},
    {{{   -49,    -11,     35}, 0, {  -556,    874}, {0x92, 0x2e, 0x2b, 0xff}}},
    {{{     1,    -57,     76}, 0, { -1062,    410}, {0xd5, 0xe6, 0x74, 0xff}}},
    {{{     9,     27,    -50}, 0, { -1138,   1254}, {0xce, 0x70, 0xe4, 0xff}}},
    {{{     9,     27,     51}, 0, { -1138,   1254}, {0xce, 0x6d, 0x28, 0xff}}},
    {{{   -49,    -11,    -36}, 0, {  -556,    874}, {0x98, 0x26, 0xc4, 0xff}}},
    {{{   -49,    -73,    -25}, 0, {  -552,    248}, {0x8d, 0xdc, 0xda, 0xff}}},
    {{{   -49,    -73,     26}, 0, {  -552,    248}, {0x93, 0xde, 0x36, 0xff}}},
    {{{     0,   -140,     32}, 0, { -1046,   -414}, {0xc5, 0x94, 0x1d, 0xff}}},
    {{{     0,    -98,     73}, 0, { -1054,     -2}, {0xd6, 0xc8, 0x69, 0xff}}},
    {{{   103,    -17,    101}, 0, { -2084,    810}, {0x33, 0xd1, 0x69, 0xff}}},
    {{{    98,    -35,     81}, 0, { -2034,    626}, {0x43, 0xd5, 0x62, 0xff}}},
    {{{    97,   -100,     73}, 0, { -2022,    -18}, {0x22, 0xd7, 0x73, 0xff}}},
    {{{   101,   -142,     35}, 0, { -2058,   -434}, {0x26, 0x99, 0x3d, 0xff}}},
    {{{     2,     -6,    -82}, 0, { -1072,    916}, {0xcf, 0x36, 0x99, 0xff}}},
    {{{     1,    -57,    -75}, 0, { -1062,    410}, {0xd6, 0xe6, 0x8c, 0xff}}},
};

// 0x06003FB0
static const Vtx lakitu_seg6_vertex_06003FB0[] = {
    {{{     1,    -57,    -75}, 0, { -1062,    410}, {0xd6, 0xe6, 0x8c, 0xff}}},
    {{{    98,    -35,    -80}, 0, { -2034,    626}, {0x43, 0xd5, 0x9e, 0xff}}},
    {{{    97,   -100,    -72}, 0, { -2022,    -18}, {0x1b, 0xcd, 0x90, 0xff}}},
    {{{     0,    -98,    -72}, 0, { -1054,     -2}, {0xcc, 0xd2, 0x97, 0xff}}},
    {{{     2,     -6,    -82}, 0, { -1072,    916}, {0xcf, 0x36, 0x99, 0xff}}},
    {{{   103,    -17,   -100}, 0, { -2084,    810}, {0x33, 0xd1, 0x97, 0xff}}},
    {{{   -49,    -73,    -25}, 0, {  -552,    248}, {0x8d, 0xdc, 0xda, 0xff}}},
    {{{   148,     21,    -45}, 0, { -2528,   1194}, {0x67, 0x30, 0xc9, 0xff}}},
    {{{   104,     34,    -89}, 0, { -2094,   1332}, {0x1d, 0x53, 0xa5, 0xff}}},
    {{{   105,     51,    -39}, 0, { -2096,   1494}, {0x15, 0x78, 0xe0, 0xff}}},
    {{{     0,   -140,     32}, 0, { -1046,   -414}, {0xc5, 0x94, 0x1d, 0xff}}},
    {{{     0,   -140,    -31}, 0, { -1046,   -414}, {0xd1, 0x9a, 0xc6, 0xff}}},
    {{{   161,     16,      0}, 0, {   480,    568}, {0x70, 0x3a, 0x00, 0xff}}},
    {{{   154,    -30,     28}, 0, {   746,    166}, {0x6d, 0xc7, 0x1e, 0xff}}},
    {{{   154,    -30,    -27}, 0, {   210,    164}, {0x6e, 0xcf, 0xdc, 0xff}}},
};

// 0x060040A0
static const Vtx lakitu_seg6_vertex_060040A0[] = {
    {{{   105,     51,     40}, 0, {   864,    924}, {0x15, 0x7c, 0x0f, 0xff}}},
    {{{   148,     21,     46}, 0, {   920,    620}, {0x67, 0x30, 0x36, 0xff}}},
    {{{   161,     16,      0}, 0, {   480,    568}, {0x70, 0x3a, 0x00, 0xff}}},
    {{{   154,    -30,    -27}, 0, {   210,    164}, {0x6e, 0xcf, 0xdc, 0xff}}},
    {{{   148,     21,    -45}, 0, {    40,    620}, {0x67, 0x30, 0xc9, 0xff}}},
    {{{   154,    -30,     28}, 0, {   746,    166}, {0x6d, 0xc7, 0x1e, 0xff}}},
    {{{   105,     51,    -39}, 0, {   100,    924}, {0x15, 0x78, 0xe0, 0xff}}},
    {{{     9,     27,    -50}, 0, { -1138,   1254}, {0xce, 0x70, 0xe4, 0xff}}},
    {{{   105,     51,     40}, 0, { -2096,   1494}, {0x15, 0x7c, 0x0f, 0xff}}},
    {{{   105,     51,    -39}, 0, { -2096,   1494}, {0x15, 0x78, 0xe0, 0xff}}},
    {{{     2,     -6,    -82}, 0, { -1072,    916}, {0xcf, 0x36, 0x99, 0xff}}},
    {{{   104,     34,    -89}, 0, { -2094,   1332}, {0x1d, 0x53, 0xa5, 0xff}}},
    {{{     9,     27,     51}, 0, { -1138,   1254}, {0xce, 0x6d, 0x28, 0xff}}},
    {{{   104,     34,     90}, 0, { -2094,   1332}, {0x12, 0x57, 0x5a, 0xff}}},
    {{{   161,     16,      0}, 0, { -2654,   1148}, {0x70, 0x3a, 0x00, 0xff}}},
    {{{   148,     21,     46}, 0, { -2528,   1194}, {0x67, 0x30, 0x36, 0xff}}},
};

// 0x060041A0
static const Vtx lakitu_seg6_vertex_060041A0[] = {
    {{{     2,     -6,    -82}, 0, { -1072,    916}, {0xcf, 0x36, 0x99, 0xff}}},
    {{{   104,     34,    -89}, 0, { -2094,   1332}, {0x1d, 0x53, 0xa5, 0xff}}},
    {{{   103,    -17,   -100}, 0, { -2084,    810}, {0x33, 0xd1, 0x97, 0xff}}},
    {{{    98,    -35,    -80}, 0, { -2034,    626}, {0x43, 0xd5, 0x9e, 0xff}}},
    {{{   138,    -47,    -10}, 0, { -2428,    508}, {0x68, 0xbf, 0xe0, 0xff}}},
    {{{   148,     21,    -45}, 0, { -2528,   1194}, {0x67, 0x30, 0xc9, 0xff}}},
    {{{   154,    -30,    -27}, 0, { -2588,    680}, {0x6e, 0xcf, 0xdc, 0xff}}},
    {{{    97,   -100,    -72}, 0, { -2022,    -18}, {0x1b, 0xcd, 0x90, 0xff}}},
    {{{   135,    -98,    -38}, 0, { -2404,     -2}, {0x74, 0xe5, 0xd5, 0xff}}},
    {{{   101,   -142,    -34}, 0, { -2058,   -434}, {0x34, 0x92, 0xde, 0xff}}},
    {{{    98,    -35,     81}, 0, { -2034,    626}, {0x43, 0xd5, 0x62, 0xff}}},
    {{{    97,   -100,     73}, 0, { -2022,    -18}, {0x22, 0xd7, 0x73, 0xff}}},
    {{{   135,    -98,     39}, 0, { -2404,     -2}, {0x70, 0xd8, 0x2b, 0xff}}},
    {{{   138,    -47,     11}, 0, { -2428,    508}, {0x6f, 0xce, 0x20, 0xff}}},
    {{{   103,    -17,    101}, 0, { -2084,    810}, {0x33, 0xd1, 0x69, 0xff}}},
};

// 0x06004290
static const Vtx lakitu_seg6_vertex_06004290[] = {
    {{{   103,    -17,    101}, 0, { -2084,    810}, {0x33, 0xd1, 0x69, 0xff}}},
    {{{   104,     34,     90}, 0, { -2094,   1332}, {0x12, 0x57, 0x5a, 0xff}}},
    {{{     2,     -6,     83}, 0, { -1072,    916}, {0xcb, 0x25, 0x6c, 0xff}}},
    {{{   138,    -47,     11}, 0, { -2428,    508}, {0x6f, 0xce, 0x20, 0xff}}},
    {{{   154,    -30,     28}, 0, { -2588,    680}, {0x6d, 0xc7, 0x1e, 0xff}}},
    {{{   148,     21,     46}, 0, { -2528,   1194}, {0x67, 0x30, 0x36, 0xff}}},
    {{{   101,   -142,     35}, 0, { -2058,   -434}, {0x26, 0x99, 0x3d, 0xff}}},
    {{{   135,    -98,     39}, 0, { -2404,     -2}, {0x70, 0xd8, 0x2b, 0xff}}},
    {{{    97,   -100,     73}, 0, { -2022,    -18}, {0x22, 0xd7, 0x73, 0xff}}},
    {{{   101,   -142,    -34}, 0, { -2058,   -434}, {0x34, 0x92, 0xde, 0xff}}},
    {{{   135,    -98,    -38}, 0, { -2404,     -2}, {0x74, 0xe5, 0xd5, 0xff}}},
    {{{    98,    -35,    -80}, 0, { -2034,    626}, {0x43, 0xd5, 0x9e, 0xff}}},
    {{{   138,    -47,    -10}, 0, { -2428,    508}, {0x68, 0xbf, 0xe0, 0xff}}},
    {{{    97,   -100,    -72}, 0, { -2022,    -18}, {0x1b, 0xcd, 0x90, 0xff}}},
    {{{     9,     27,     51}, 0, { -1138,   1254}, {0xce, 0x6d, 0x28, 0xff}}},
    {{{     0,   -140,    -31}, 0, { -1046,   -414}, {0xd1, 0x9a, 0xc6, 0xff}}},
};

// 0x06004390
static const Vtx lakitu_seg6_vertex_06004390[] = {
    {{{   101,   -142,    -34}, 0, { -2058,   -434}, {0x34, 0x92, 0xde, 0xff}}},
    {{{     0,   -140,     32}, 0, { -1046,   -414}, {0xc5, 0x94, 0x1d, 0xff}}},
    {{{     0,   -140,    -31}, 0, { -1046,   -414}, {0xd1, 0x9a, 0xc6, 0xff}}},
    {{{   101,   -142,     35}, 0, { -2058,   -434}, {0x26, 0x99, 0x3d, 0xff}}},
    {{{   138,    -47,    -10}, 0, {   370,    -88}, {0x68, 0xbf, 0xe0, 0xff}}},
    {{{   154,    -30,     28}, 0, {   724,    182}, {0x6d, 0xc7, 0x1e, 0xff}}},
    {{{   138,    -47,     11}, 0, {   570,    -84}, {0x6f, 0xce, 0x20, 0xff}}},
    {{{   154,    -30,    -27}, 0, {   196,    176}, {0x6e, 0xcf, 0xdc, 0xff}}},
};

// 0x06004410 - 0x06004680
const Gfx lakitu_seg6_dl_06004410[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lakitu_seg6_texture_06003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lakitu_seg6_lights_06003E98.l, 1),
    gsSPLight(&lakitu_seg6_lights_06003E98.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06003EB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 3,  5,  1, 0x0,  1,  6,  7, 0x0),
    gsSP2Triangles( 1,  5,  6, 0x0,  1,  0,  4, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  8,  7,  6, 0x0),
    gsSP2Triangles( 9,  7,  8, 0x0,  2,  7,  9, 0x0),
    gsSP2Triangles(10,  0,  2, 0x0,  2, 11, 10, 0x0),
    gsSP2Triangles( 2, 12, 11, 0x0,  2,  9, 12, 0x0),
    gsSP2Triangles( 9, 13, 12, 0x0,  9,  8, 13, 0x0),
    gsSP2Triangles(14,  5,  3, 0x0,  5, 15,  6, 0x0),
    gsSP1Triangle( 5, 14, 15, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06003FB0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  6,  0,  3, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  6, 11, 0x0, 11,  6,  3, 0x0),
    gsSP2Triangles( 2, 11,  3, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(lakitu_seg6_vertex_060040A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  4,  6,  2, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0,  7, 12,  8, 0x0),
    gsSP2Triangles(13,  8, 12, 0x0,  9,  8, 14, 0x0),
    gsSP1Triangle(15,  8, 13, 0x0),
    gsSPVertex(lakitu_seg6_vertex_060041A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  6,  4, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSP1Triangle(14, 10, 13, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06004290, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles( 3,  7, 10, 0x0,  9,  7,  6, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 10, 13, 11, 0x0),
    gsSP2Triangles( 3, 10, 12, 0x0,  1, 14,  2, 0x0),
    gsSP1Triangle(13,  9, 15, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06004390, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004680 - 0x060046E0
const Gfx lakitu_seg6_dl_06004680[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lakitu_seg6_dl_06004410),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060046E0
static const Lights1 lakitu_seg6_lights_060046E0 = gdSPDefLights1(
    0x79, 0x55, 0x00,
    0xf2, 0xab, 0x00, 0x28, 0x28, 0x28
);

// 0x060046F8
static const Vtx lakitu_seg6_vertex_060046F8[] = {
    {{{    99,     30,     33}, 0, {     0,      0}, {0x06, 0x5e, 0x54, 0xff}}},
    {{{   102,      0,     33}, 0, {     0,      0}, {0x21, 0xd1, 0x70, 0xff}}},
    {{{   140,     20,      2}, 0, {     0,      0}, {0x73, 0xfb, 0x35, 0xff}}},
    {{{    99,     30,    -28}, 0, {     0,      0}, {0xfa, 0x4f, 0x9e, 0xff}}},
    {{{   102,      0,    -28}, 0, {     0,      0}, {0x1b, 0xb7, 0x9d, 0xff}}},
    {{{    30,     -5,    -20}, 0, {     0,      0}, {0xdf, 0xce, 0x91, 0xff}}},
    {{{   104,    -15,      2}, 0, {     0,      0}, {0x31, 0x8c, 0x07, 0xff}}},
    {{{    33,    -17,      0}, 0, {     0,      0}, {0xec, 0x84, 0xef, 0xff}}},
    {{{   137,     20,    -12}, 0, {     0,      0}, {0x64, 0xfb, 0xb3, 0xff}}},
    {{{    30,     -5,     25}, 0, {     0,      0}, {0xe3, 0xaf, 0x5d, 0xff}}},
    {{{    -5,      0,      2}, 0, {     0,      0}, {0x83, 0xee, 0xfe, 0xff}}},
    {{{    28,     15,    -20}, 0, {     0,      0}, {0xc9, 0x34, 0x9b, 0xff}}},
    {{{    28,     15,     25}, 0, {     0,      0}, {0xd1, 0x29, 0x6e, 0xff}}},
    {{{    25,     28,      2}, 0, {     0,      0}, {0xce, 0x74, 0x00, 0xff}}},
    {{{   119,     45,      0}, 0, {     0,      0}, {0x35, 0x72, 0xf9, 0xff}}},
};

// 0x060047E8 - 0x060048D8
const Gfx lakitu_seg6_dl_060047E8[] = {
    gsSPLight(&lakitu_seg6_lights_060046E0.l, 1),
    gsSPLight(&lakitu_seg6_lights_060046E0.a, 2),
    gsSPVertex(lakitu_seg6_vertex_060046F8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 6,  4,  8, 0x0,  3,  8,  4, 0x0),
    gsSP2Triangles( 9,  7,  6, 0x0, 10,  7,  9, 0x0),
    gsSP2Triangles( 5,  7, 10, 0x0,  3,  5, 11, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0,  1,  6,  2, 0x0),
    gsSP2Triangles( 2,  6,  8, 0x0,  9,  6,  1, 0x0),
    gsSP2Triangles( 1, 12,  9, 0x0, 12, 10,  9, 0x0),
    gsSP2Triangles( 1,  0, 12, 0x0, 11, 13,  3, 0x0),
    gsSP2Triangles(14,  0,  2, 0x0, 14,  2,  8, 0x0),
    gsSP2Triangles( 3, 14,  8, 0x0, 13,  0,  3, 0x0),
    gsSP2Triangles( 3,  0, 14, 0x0,  0, 13, 12, 0x0),
    gsSP2Triangles(10, 13, 11, 0x0, 12, 13, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x060048D8
static const Lights1 lakitu_seg6_lights_060048D8 = gdSPDefLights1(
    0x79, 0x55, 0x00,
    0xf2, 0xab, 0x00, 0x28, 0x28, 0x28
);

// 0x060048F0
static const Vtx lakitu_seg6_vertex_060048F0[] = {
    {{{    30,     -5,    -22}, 0, {     0,      0}, {0xe3, 0xb1, 0xa2, 0xff}}},
    {{{    -5,      0,      0}, 0, {     0,      0}, {0x83, 0xee, 0x00, 0xff}}},
    {{{    28,     15,    -22}, 0, {     0,      0}, {0xd1, 0x29, 0x92, 0xff}}},
    {{{    30,     -5,     22}, 0, {     0,      0}, {0xdf, 0xcb, 0x6e, 0xff}}},
    {{{   102,      0,     30}, 0, {     0,      0}, {0x1b, 0xb6, 0x63, 0xff}}},
    {{{    99,     30,     30}, 0, {     0,      0}, {0xfa, 0x4f, 0x62, 0xff}}},
    {{{    28,     15,     22}, 0, {     0,      0}, {0xc9, 0x34, 0x65, 0xff}}},
    {{{    33,    -17,      0}, 0, {     0,      0}, {0xec, 0x84, 0x0d, 0xff}}},
    {{{   104,    -15,      0}, 0, {     0,      0}, {0x31, 0x8c, 0xf8, 0xff}}},
    {{{   137,     20,     15}, 0, {     0,      0}, {0x64, 0xfb, 0x4d, 0xff}}},
    {{{   140,     20,      0}, 0, {     0,      0}, {0x73, 0xfb, 0xcb, 0xff}}},
    {{{   102,      0,    -30}, 0, {     0,      0}, {0x21, 0xd1, 0x90, 0xff}}},
    {{{    99,     30,    -30}, 0, {     0,      0}, {0x06, 0x5e, 0xac, 0xff}}},
    {{{   119,     45,      2}, 0, {     0,      0}, {0x35, 0x72, 0x07, 0xff}}},
    {{{    25,     28,      0}, 0, {     0,      0}, {0xce, 0x74, 0x00, 0xff}}},
};

// 0x060049E0 - 0x06004AD0
const Gfx lakitu_seg6_dl_060049E0[] = {
    gsSPLight(&lakitu_seg6_lights_060048D8.l, 1),
    gsSPLight(&lakitu_seg6_lights_060048D8.a, 2),
    gsSPVertex(lakitu_seg6_vertex_060048F0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 6,  1,  3, 0x0,  1,  7,  3, 0x0),
    gsSP2Triangles( 8,  7,  0, 0x0,  7,  8,  4, 0x0),
    gsSP2Triangles( 0,  7,  1, 0x0,  9,  4,  8, 0x0),
    gsSP2Triangles( 4,  9,  5, 0x0, 10,  8, 11, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 11,  8,  0, 0x0),
    gsSP2Triangles( 0,  2, 11, 0x0,  2, 12, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 12,  5, 0x0),
    gsSP2Triangles( 9, 10, 13, 0x0,  9, 13,  5, 0x0),
    gsSP2Triangles(10, 12, 13, 0x0,  1, 14,  2, 0x0),
    gsSP2Triangles( 2, 14, 12, 0x0,  6, 14,  1, 0x0),
    gsSP2Triangles( 5, 14,  6, 0x0,  5, 12, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004AD0
static const Lights1 lakitu_seg6_lights_06004AD0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06004AE8
static const Vtx lakitu_seg6_vertex_06004AE8[] = {
    {{{   150,    -30,      0}, 0, {   992,    994}, {0x7e, 0xf5, 0x09, 0xff}}},
    {{{   136,   -116,     82}, 0, {  2012,      0}, {0x7e, 0xf5, 0x09, 0xff}}},
    {{{   142,   -116,      0}, 0, {   992,    -26}, {0x7e, 0xf5, 0x09, 0xff}}},
    {{{   142,   -116,      0}, 0, {   992,    -26}, {0x7e, 0xf5, 0xf9, 0xff}}},
    {{{   145,    -30,    -81}, 0, {     0,    990}, {0x7e, 0xf5, 0xf9, 0xff}}},
    {{{   150,    -30,      0}, 0, {   992,    994}, {0x7e, 0xf5, 0xf9, 0xff}}},
    {{{   142,   -116,      0}, 0, {   992,    -26}, {0x7d, 0xf3, 0xf7, 0xff}}},
    {{{   136,   -116,    -81}, 0, {     0,      0}, {0x7d, 0xf3, 0xf7, 0xff}}},
    {{{   145,    -30,    -81}, 0, {     0,    990}, {0x7d, 0xf3, 0xf7, 0xff}}},
    {{{   150,    -30,      0}, 0, {   992,    994}, {0x7e, 0xf3, 0x07, 0xff}}},
    {{{   145,    -30,     82}, 0, {  2012,    990}, {0x7e, 0xf3, 0x07, 0xff}}},
    {{{   136,   -116,     82}, 0, {  2012,      0}, {0x7e, 0xf3, 0x07, 0xff}}},
};

// 0x06004BA8 - 0x06004BE8
const Gfx lakitu_seg6_dl_06004BA8[] = {
    gsSPLight(&lakitu_seg6_lights_06004AD0.l, 1),
    gsSPLight(&lakitu_seg6_lights_06004AD0.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06004AE8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004BE8 - 0x06004C30
const Gfx lakitu_seg6_dl_06004BE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x06004C30 - 0x06004C60
const Gfx lakitu_seg6_dl_06004C30[] = {
    gsSPDisplayList(lakitu_seg6_dl_06004BA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004C60 - 0x06004C88
const Gfx lakitu_seg6_dl_06004C60[] = {
    gsSPDisplayList(lakitu_seg6_dl_06004BE8),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lakitu_seg6_texture_06000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(lakitu_seg6_dl_06004C30),
};

// 0x06004C88 - 0x06004CB0
const Gfx lakitu_seg6_dl_06004C88[] = {
    gsSPDisplayList(lakitu_seg6_dl_06004BE8),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lakitu_seg6_texture_06001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(lakitu_seg6_dl_06004C30),
};

// 0x06004CB0
static const Lights1 lakitu_seg6_lights_06004CB0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06004CC8
static const Lights1 lakitu_seg6_lights_06004CC8 = gdSPDefLights1(
    0x0c, 0x0c, 0x0c,
    0x19, 0x19, 0x19, 0x28, 0x28, 0x28
);

// 0x06004CE0
static const Lights1 lakitu_seg6_lights_06004CE0 = gdSPDefLights1(
    0x19, 0x22, 0x20,
    0x32, 0x44, 0x40, 0x28, 0x28, 0x28
);

// 0x06004CF8
static const Lights1 lakitu_seg6_lights_06004CF8 = gdSPDefLights1(
    0x18, 0x18, 0x18,
    0x30, 0x30, 0x30, 0x28, 0x28, 0x28
);

// 0x06004D10
static const Vtx lakitu_seg6_vertex_06004D10[] = {
    {{{    79,   -113,    -34}, 0, {     0,    480}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     7,   -113,     37}, 0, {   480,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     7,   -113,    -34}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    79,   -113,     37}, 0, {   480,    480}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x06004D50
static const Vtx lakitu_seg6_vertex_06004D50[] = {
    {{{    20,    -58,    -21}, 0, {     0,      0}, {0x00, 0x39, 0x8f, 0xff}}},
    {{{    66,    -58,    -21}, 0, {     0,      0}, {0x00, 0x39, 0x8f, 0xff}}},
    {{{    43,   -113,    -49}, 0, {     0,      0}, {0x00, 0x39, 0x8f, 0xff}}},
    {{{    20,    -58,    -21}, 0, {     0,      0}, {0xd2, 0x25, 0x90, 0xff}}},
    {{{    43,   -113,    -49}, 0, {     0,      0}, {0xd2, 0x25, 0x90, 0xff}}},
    {{{     7,   -113,    -34}, 0, {     0,      0}, {0xd2, 0x25, 0x90, 0xff}}},
    {{{    20,    -58,    -21}, 0, {     0,      0}, {0x90, 0x25, 0xd3, 0xff}}},
    {{{     7,   -113,    -34}, 0, {     0,      0}, {0x90, 0x25, 0xd3, 0xff}}},
    {{{    -7,   -113,      1}, 0, {     0,      0}, {0x90, 0x25, 0xd3, 0xff}}},
    {{{    20,    -58,    -21}, 0, {     0,      0}, {0x8e, 0x37, 0x00, 0xff}}},
    {{{    -7,   -113,      1}, 0, {     0,      0}, {0x8e, 0x37, 0x00, 0xff}}},
    {{{    20,    -58,     24}, 0, {     0,      0}, {0x8e, 0x37, 0x00, 0xff}}},
    {{{    79,   -113,    -34}, 0, {     0,      0}, {0x2e, 0x25, 0x90, 0xff}}},
    {{{    43,   -113,    -49}, 0, {     0,      0}, {0x2e, 0x25, 0x90, 0xff}}},
    {{{    66,    -58,    -21}, 0, {     0,      0}, {0x2e, 0x25, 0x90, 0xff}}},
};

// 0x06004E40
static const Vtx lakitu_seg6_vertex_06004E40[] = {
    {{{    66,    -58,    -21}, 0, {     0,      0}, {0x71, 0x39, 0x00, 0xff}}},
    {{{    66,    -58,     24}, 0, {     0,      0}, {0x71, 0x39, 0x00, 0xff}}},
    {{{    94,   -113,      1}, 0, {     0,      0}, {0x71, 0x39, 0x00, 0xff}}},
    {{{    66,    -58,    -21}, 0, {     0,      0}, {0x6f, 0x25, 0xd1, 0xff}}},
    {{{    94,   -113,      1}, 0, {     0,      0}, {0x6f, 0x25, 0xd1, 0xff}}},
    {{{    79,   -113,    -34}, 0, {     0,      0}, {0x6f, 0x25, 0xd1, 0xff}}},
    {{{    94,   -113,      1}, 0, {     0,      0}, {0x70, 0x25, 0x2e, 0xff}}},
    {{{    66,    -58,     24}, 0, {     0,      0}, {0x70, 0x25, 0x2e, 0xff}}},
    {{{    79,   -113,     37}, 0, {     0,      0}, {0x70, 0x25, 0x2e, 0xff}}},
    {{{    79,   -113,     37}, 0, {     0,      0}, {0x2e, 0x25, 0x70, 0xff}}},
    {{{    66,    -58,     24}, 0, {     0,      0}, {0x2e, 0x25, 0x70, 0xff}}},
    {{{    43,   -113,     52}, 0, {     0,      0}, {0x2e, 0x25, 0x70, 0xff}}},
    {{{    66,    -58,     24}, 0, {     0,      0}, {0x00, 0x39, 0x71, 0xff}}},
    {{{    20,    -58,     24}, 0, {     0,      0}, {0x00, 0x39, 0x71, 0xff}}},
    {{{    43,   -113,     52}, 0, {     0,      0}, {0x00, 0x39, 0x71, 0xff}}},
};

// 0x06004F30
static const Vtx lakitu_seg6_vertex_06004F30[] = {
    {{{    43,   -113,     52}, 0, {     0,      0}, {0xd2, 0x25, 0x70, 0xff}}},
    {{{    20,    -58,     24}, 0, {     0,      0}, {0xd2, 0x25, 0x70, 0xff}}},
    {{{     7,   -113,     37}, 0, {     0,      0}, {0xd2, 0x25, 0x70, 0xff}}},
    {{{    -7,   -113,      1}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0xff}}},
    {{{     7,   -113,     37}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0xff}}},
    {{{    20,    -58,     24}, 0, {     0,      0}, {0x8f, 0x25, 0x2c, 0xff}}},
};

// 0x06004F90
static const Vtx lakitu_seg6_vertex_06004F90[] = {
    {{{    -4,     46,    -40}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -4,    -58,     43}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -4,     46,     43}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   100,     46,     43}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   100,     46,    -40}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -4,     46,    -40}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -4,     46,     43}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -4,    -58,    -40}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   100,     46,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -4,    -58,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -4,     46,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   100,    -58,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -4,     46,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -4,    -58,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x06005070
static const Vtx lakitu_seg6_vertex_06005070[] = {
    {{{   100,    -58,    -40}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   100,    -58,     43}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -4,    -58,     43}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -4,    -58,    -40}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   100,    -58,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   100,     46,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -4,     46,     43}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   100,     46,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   100,    -58,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -4,    -58,    -40}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   100,     46,     43}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   100,    -58,    -40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   100,     46,    -40}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   100,    -58,     43}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x06005150
static const Vtx lakitu_seg6_vertex_06005150[] = {
    {{{     7,   -113,    -34}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     7,   -113,     37}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -7,   -113,      1}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    43,   -113,    -49}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    79,   -113,    -34}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    94,   -113,      1}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    79,   -113,     37}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    43,   -113,     52}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x060051D0 - 0x06005218
const Gfx lakitu_seg6_dl_060051D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lakitu_seg6_texture_06003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lakitu_seg6_lights_06004CB0.l, 1),
    gsSPLight(&lakitu_seg6_lights_06004CB0.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06004D10, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005218 - 0x06005360
const Gfx lakitu_seg6_dl_06005218[] = {
    gsSPLight(&lakitu_seg6_lights_06004CC8.l, 1),
    gsSPLight(&lakitu_seg6_lights_06004CC8.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06004D50, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06004E40, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06004F30, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&lakitu_seg6_lights_06004CE0.l, 1),
    gsSPLight(&lakitu_seg6_lights_06004CE0.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06004F90, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(lakitu_seg6_vertex_06005070, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPLight(&lakitu_seg6_lights_06004CF8.l, 1),
    gsSPLight(&lakitu_seg6_lights_06004CF8.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06005150, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  1,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005360 - 0x060053D8
const Gfx lakitu_seg6_dl_06005360[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lakitu_seg6_dl_060051D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(lakitu_seg6_dl_06005218),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// Unreferenced light group
UNUSED static const Lights1 lakitu_cameraman_lights_unused1 = gdSPDefLights1(
    0x33, 0x1f, 0x0c,
    0xcc, 0x7f, 0x33, 0x28, 0x28, 0x28
);

// 0x060053F0
static const Lights1 lakitu_seg6_lights_060053F0 = gdSPDefLights1(
    0x29, 0x13, 0x06,
    0xa5, 0x4f, 0x1b, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 lakitu_cameraman_lights_unused2 = gdSPDefLights1(
    0x06, 0x06, 0x06,
    0x19, 0x19, 0x19, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 lakitu_cameraman_lights_unused3 = gdSPDefLights1(
    0x2c, 0x2c, 0x2c,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// 0x06005438
static const Lights1 lakitu_seg6_lights_06005438 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 lakitu_cameraman_lights_unused4 = gdSPDefLights1(
    0x0d, 0x2c, 0x0b,
    0x34, 0xb2, 0x2c, 0x28, 0x28, 0x28
);

// 0x06005468
static const Vtx lakitu_seg6_vertex_06005468[] = {
    {{{   103,     40,     28}, 0, {     0,      0}, {0xb1, 0x5e, 0x1c, 0x00}}},
    {{{   115,     46,     39}, 0, {     0,      0}, {0xb1, 0x5e, 0x1c, 0x00}}},
    {{{   111,     46,     30}, 0, {     0,      0}, {0xb1, 0x5e, 0x1c, 0x00}}},
    {{{   115,     46,     39}, 0, {     0,      0}, {0xce, 0xb9, 0x5b, 0xff}}},
    {{{   103,     40,     28}, 0, {     0,      0}, {0xce, 0xb9, 0x5b, 0xff}}},
    {{{   345,   -234,    -54}, 0, {     0,      0}, {0xce, 0xb9, 0x5b, 0xff}}},
    {{{   115,     51,     23}, 0, {     0,      0}, {0xb1, 0x5e, 0x1c, 0xff}}},
    {{{   103,     40,     28}, 0, {     0,      0}, {0xd2, 0xfb, 0x8b, 0xff}}},
    {{{   115,     51,     23}, 0, {     0,      0}, {0xd2, 0xfb, 0x8b, 0xff}}},
    {{{   345,   -234,    -54}, 0, {     0,      0}, {0xd2, 0xfb, 0x8b, 0xff}}},
    {{{   115,     51,     23}, 0, {     0,      0}, {0x64, 0x49, 0x18, 0xff}}},
    {{{   115,     46,     39}, 0, {     0,      0}, {0x64, 0x49, 0x18, 0xff}}},
    {{{   345,   -234,    -54}, 0, {     0,      0}, {0x64, 0x49, 0x18, 0xff}}},
};

// 0x06005538
static const Vtx lakitu_seg6_vertex_06005538[] = {
    {{{   189,      1,      1}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{   189,      1,     -2}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    -3,      0,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
};

// 0x06005568
static const Vtx lakitu_seg6_vertex_06005568[] = {
    {{{    -3,      0,      0}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0x00}}},
    {{{   189,      2,     -2}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0x00}}},
    {{{   189,      2,      1}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0x00}}},
};

// 0x06005598 - 0x060055E8
const Gfx lakitu_seg6_dl_06005598[] = {
    gsSPLight(&lakitu_seg6_lights_060053F0.l, 1),
    gsSPLight(&lakitu_seg6_lights_060053F0.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06005468, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 1,  6,  2, 0x0, 10, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x060055E8 - 0x06005610
const Gfx lakitu_seg6_dl_060055E8[] = {
    gsSPLight(&lakitu_seg6_lights_06005438.l, 1),
    gsSPLight(&lakitu_seg6_lights_06005438.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06005538, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005610 - 0x06005638
const Gfx lakitu_seg6_dl_06005610[] = {
    gsSPLight(&lakitu_seg6_lights_06005438.l, 1),
    gsSPLight(&lakitu_seg6_lights_06005438.a, 2),
    gsSPVertex(lakitu_seg6_vertex_06005568, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};
