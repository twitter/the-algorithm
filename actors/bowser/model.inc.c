// Bowser (King Koopa)

// 0x0601F438
ALIGNED8 static const Texture bowser_seg6_texture_0601F438[] = {
#include "actors/bowser/bowser_shell.rgba16.inc.c"
};

// 0x0601FC38
ALIGNED8 static const Texture bowser_seg6_texture_0601FC38[] = {
#include "actors/bowser/bowser_eyebrow.rgba16.inc.c"
};

// 0x06020C38
ALIGNED8 static const Texture bowser_seg6_texture_06020C38[] = {
#include "actors/bowser/bowser_muzzle.rgba16.inc.c"
};

// 0x06021438
ALIGNED8 static const Texture bowser_seg6_texture_06021438[] = {
#include "actors/bowser/bowser_nostrils.rgba16.inc.c"
};

// 0x06022438
ALIGNED8 static const Texture bowser_seg6_texture_06022438[] = {
#include "actors/bowser/bowser_body.rgba16.inc.c"
};

// 0x06022C38
ALIGNED8 static const Texture bowser_seg6_texture_06022C38[] = {
#include "actors/bowser/bowser_armband_spike.rgba16.inc.c"
};

// 0x06023C38
ALIGNED8 static const Texture bowser_seg6_texture_06023C38[] = {
#include "actors/bowser/bowser_armband.rgba16.inc.c"
};

// 0x06024438
ALIGNED8 static const Texture bowser_seg6_texture_06024438[] = {
#include "actors/bowser/bowser_tongue.rgba16.inc.c"
};

// 0x06025438
ALIGNED8 static const Texture bowser_seg6_texture_06025438[] = {
#include "actors/bowser/bowser_chest.rgba16.inc.c"
};

// 0x06025C38
ALIGNED8 static const Texture bowser_seg6_texture_06025C38[] = {
#include "actors/bowser/bowser_shell_edge.rgba16.inc.c"
};

// unreferenced, seen in pre-Spaceworld 1995 B-roll footage build
// 0x06026438
ALIGNED8 static const Texture bowser_seg6_texture_06026438[] = {
#include "actors/bowser/bowser_blue_eye_unused.rgba16.inc.c"
};

// unreferenced (stubbed texture? possibly original texture for mouth)
// 0x06027438
ALIGNED8 static const Texture bowser_seg6_texture_06027438[] = {
#include "actors/bowser/bowser_mouth_unused.rgba16.inc.c"
};

// 0x06028438
ALIGNED8 static const Texture bowser_seg6_texture_06028438[] = {
#include "actors/bowser/bowser_upper_face.rgba16.inc.c"
};

// 0x06028C38
ALIGNED8 static const Texture bowser_seg6_texture_06028C38[] = {
#include "actors/bowser/bowser_hair.rgba16.inc.c"
};

// 0x06029C38
ALIGNED8 static const Texture bowser_seg6_texture_06029C38[] = {
#include "actors/bowser/bowser_claw_edge.rgba16.inc.c"
};

// 0x0602AC38
ALIGNED8 static const Texture bowser_seg6_texture_0602AC38[] = {
#include "actors/bowser/bowser_claw_horn_tooth.rgba16.inc.c"
};

// 0x0602BC38
ALIGNED8 static const Texture bowser_seg6_texture_0602BC38[] = {
#include "actors/bowser/bowser_claw_horn_angle.rgba16.inc.c"
};

// 0x0602CC38
ALIGNED8 static const Texture bowser_seg6_texture_0602CC38[] = {
#include "actors/bowser/bowser_eye_left_0.rgba16.inc.c"
};

// 0x0602DC38
ALIGNED8 static const Texture bowser_seg6_texture_0602DC38[] = {
#include "actors/bowser/bowser_eye_half_closed_0.rgba16.inc.c"
};

// 0x0602EC38
ALIGNED8 static const Texture bowser_seg6_texture_0602EC38[] = {
#include "actors/bowser/bowser_eye_closed_0.rgba16.inc.c"
};

// 0x0602FC38
ALIGNED8 static const Texture bowser_seg6_texture_0602FC38[] = {
#include "actors/bowser/bowser_eye_center_0.rgba16.inc.c"
};

// 0x06030C38
ALIGNED8 static const Texture bowser_seg6_texture_06030C38[] = {
#include "actors/bowser/bowser_eye_right_0.rgba16.inc.c"
};

// 0x06031C38
ALIGNED8 static const Texture bowser_seg6_texture_06031C38[] = {
#include "actors/bowser/bowser_eye_far_left_0.rgba16.inc.c"
};

// 0x06032C38
ALIGNED8 static const Texture bowser_seg6_texture_06032C38[] = {
#include "actors/bowser/bowser_eye_left_1.rgba16.inc.c"
};

// 0x06033C38
ALIGNED8 static const Texture bowser_seg6_texture_06033C38[] = {
#include "actors/bowser/bowser_eye_half_closed_1.rgba16.inc.c"
};

// 0x06034C38
ALIGNED8 static const Texture bowser_seg6_texture_06034C38[] = {
#include "actors/bowser/bowser_eye_closed_1.rgba16.inc.c"
};

// 0x06035C38
ALIGNED8 static const Texture bowser_seg6_texture_06035C38[] = {
#include "actors/bowser/bowser_eye_center_1.rgba16.inc.c"
};

// 0x06036C38
ALIGNED8 static const Texture bowser_seg6_texture_06036C38[] = {
#include "actors/bowser/bowser_eye_right_1.rgba16.inc.c"
};

// 0x06037C38
ALIGNED8 static const Texture bowser_seg6_texture_06037C38[] = {
#include "actors/bowser/bowser_eye_far_left_1.rgba16.inc.c"
};

// 0x06038C38
static const Lights1 bowser_seg6_lights_06038C38 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 bowser_lights_unused1 = gdSPDefLights1(
    0x33, 0x31, 0x00,
    0xce, 0xc7, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 bowser_lights_unused2 = gdSPDefLights1(
    0x34, 0x34, 0x00,
    0xd2, 0xd2, 0x00, 0x28, 0x28, 0x28
);

// 0x06038C80
static const Vtx bowser_seg6_vertex_06038C80[] = {
    {{{    46,     36,     22}, 0, {    -8,    978}, {0x41, 0x5e, 0x35, 0xff}}},
    {{{    66,     32,     21}, 0, {   588,    968}, {0x41, 0x56, 0x41, 0xff}}},
    {{{    46,     36,    -22}, 0, {    20,    -12}, {0x34, 0x65, 0xca, 0xff}}},
    {{{    40,    -33,      0}, 0, {    14,    958}, {0x35, 0x8d, 0x00, 0xff}}},
    {{{    60,    -32,      0}, 0, {   820,    956}, {0x2f, 0x8b, 0x00, 0xff}}},
    {{{    42,     -6,     36}, 0, {    30,     20}, {0x2b, 0xe4, 0x73, 0xff}}},
    {{{    63,     -7,     34}, 0, {   834,     78}, {0x4a, 0xdb, 0x5f, 0xff}}},
    {{{    46,     36,    -22}, 0, {    10,    964}, {0x34, 0x65, 0xca, 0xff}}},
    {{{    66,     32,    -21}, 0, {   746,    964}, {0x47, 0x45, 0xb2, 0xff}}},
    {{{    42,     -6,    -36}, 0, {   -52,    -94}, {0x2b, 0xe4, 0x8d, 0xff}}},
    {{{    63,     -7,    -34}, 0, {   684,    -28}, {0x4a, 0xdb, 0xa1, 0xff}}},
    {{{    42,     -6,    -36}, 0, {   -38,    978}, {0x2b, 0xe4, 0x8d, 0xff}}},
    {{{    60,    -32,      0}, 0, {   812,     20}, {0x2f, 0x8b, 0x00, 0xff}}},
    {{{    40,    -33,      0}, 0, {   -36,    -26}, {0x35, 0x8d, 0x00, 0xff}}},
    {{{    63,     -7,    -34}, 0, {   810,    962}, {0x4a, 0xdb, 0xa1, 0xff}}},
    {{{    66,     32,    -21}, 0, {   614,     40}, {0x47, 0x45, 0xb2, 0xff}}},
};

// 0x06038D80
static const Vtx bowser_seg6_vertex_06038D80[] = {
    {{{    42,     -6,     36}, 0, {   -44,    974}, {0x2b, 0xe4, 0x73, 0xff}}},
    {{{    66,     32,     21}, 0, {   692,     42}, {0x41, 0x56, 0x41, 0xff}}},
    {{{    46,     36,     22}, 0, {   100,     20}, {0x41, 0x5e, 0x35, 0xff}}},
    {{{    63,     -7,     34}, 0, {   556,    936}, {0x4a, 0xdb, 0x5f, 0xff}}},
    {{{    66,     32,    -21}, 0, {  1104,    166}, {0x47, 0x45, 0xb2, 0xff}}},
    {{{    81,      1,      0}, 0, {   402,    394}, {0x7e, 0xf5, 0x00, 0xff}}},
    {{{    63,     -7,    -34}, 0, {   836,    990}, {0x4a, 0xdb, 0xa1, 0xff}}},
    {{{    60,    -32,      0}, 0, {     0,    990}, {0x2f, 0x8b, 0x00, 0xff}}},
    {{{    63,     -7,     34}, 0, {  -298,    166}, {0x4a, 0xdb, 0x5f, 0xff}}},
    {{{    66,     32,     21}, 0, {   402,   -344}, {0x41, 0x56, 0x41, 0xff}}},
};

// 0x06038E20
static const Vtx bowser_seg6_vertex_06038E20[] = {
    {{{    36,     44,     27}, 0, {   -12,    930}, {0x2c, 0x60, 0x45, 0xff}}},
    {{{    32,     -8,     44}, 0, {   954,    904}, {0x21, 0xdb, 0x74, 0xff}}},
    {{{    42,     -6,     36}, 0, {   862,     76}, {0x2b, 0xe4, 0x73, 0xff}}},
    {{{    40,    -33,      0}, 0, {   874,     60}, {0x35, 0x8d, 0x00, 0xff}}},
    {{{    42,     -6,     36}, 0, {    78,     52}, {0x2b, 0xe4, 0x73, 0xff}}},
    {{{    32,     -8,     44}, 0, {    -6,    896}, {0x21, 0xdb, 0x74, 0xff}}},
    {{{    29,    -40,      0}, 0, {   968,    908}, {0x0a, 0x83, 0x13, 0xff}}},
    {{{    32,     -8,    -44}, 0, {   984,    912}, {0x1d, 0xbe, 0x98, 0xff}}},
    {{{    42,     -6,    -36}, 0, {   890,     76}, {0x2b, 0xe4, 0x8d, 0xff}}},
    {{{    40,    -33,      0}, 0, {    80,     30}, {0x35, 0x8d, 0x00, 0xff}}},
    {{{    29,    -40,      0}, 0, {   -10,    854}, {0x0a, 0x83, 0x13, 0xff}}},
    {{{    36,     44,    -27}, 0, {   956,    894}, {0x33, 0x54, 0xb0, 0xff}}},
    {{{    42,     -6,    -36}, 0, {    90,    150}, {0x2b, 0xe4, 0x8d, 0xff}}},
    {{{    32,     -8,    -44}, 0, {     0,    922}, {0x1d, 0xbe, 0x98, 0xff}}},
    {{{    46,     36,    -22}, 0, {   870,    126}, {0x34, 0x65, 0xca, 0xff}}},
    {{{    46,     36,     22}, 0, {    72,     98}, {0x41, 0x5e, 0x35, 0xff}}},
};

// 0x06038F20
static const Vtx bowser_seg6_vertex_06038F20[] = {
    {{{    46,     36,     22}, 0, {   886,     96}, {0x41, 0x5e, 0x35, 0xff}}},
    {{{    46,     36,    -22}, 0, {    86,    110}, {0x34, 0x65, 0xca, 0xff}}},
    {{{    36,     44,    -27}, 0, {    -2,    892}, {0x33, 0x54, 0xb0, 0xff}}},
    {{{    36,     44,     27}, 0, {   978,    876}, {0x2c, 0x60, 0x45, 0xff}}},
    {{{     8,     -4,     41}, 0, {   370,    -46}, {0x97, 0xe3, 0x40, 0xff}}},
    {{{    12,     43,    -25}, 0, {   386,    980}, {0xb0, 0x5c, 0xde, 0xff}}},
    {{{     8,     -4,    -41}, 0, {   982,    980}, {0xa9, 0x01, 0xa4, 0xff}}},
    {{{    12,     43,     25}, 0, {     6,    346}, {0xcb, 0x4d, 0x54, 0xff}}},
    {{{     5,    -34,      0}, 0, {   972,    346}, {0xb9, 0x9b, 0xe6, 0xff}}},
};

// 0x06038FB0
static const Vtx bowser_seg6_vertex_06038FB0[] = {
    {{{     5,    -34,      0}, 0, {   132,    990}, {0xb9, 0x9b, 0xe6, 0xff}}},
    {{{     8,     -4,    -41}, 0, {  1988,    992}, {0xa9, 0x01, 0xa4, 0xff}}},
    {{{    32,     -8,    -44}, 0, {  2012,      0}, {0x1d, 0xbe, 0x98, 0xff}}},
    {{{    12,     43,     25}, 0, {    56,    978}, {0xcb, 0x4d, 0x54, 0xff}}},
    {{{     8,     -4,     41}, 0, {  1928,    978}, {0x97, 0xe3, 0x40, 0xff}}},
    {{{    32,     -8,     44}, 0, {  2012,     88}, {0x21, 0xdb, 0x74, 0xff}}},
    {{{    36,     44,     27}, 0, {   -28,     88}, {0x2c, 0x60, 0x45, 0xff}}},
    {{{    12,     43,    -25}, 0, {    36,    988}, {0xb0, 0x5c, 0xde, 0xff}}},
    {{{    12,     43,     25}, 0, {  1928,    964}, {0xcb, 0x4d, 0x54, 0xff}}},
    {{{    36,     44,     27}, 0, {  2000,     64}, {0x2c, 0x60, 0x45, 0xff}}},
    {{{    36,     44,    -27}, 0, {   -56,     90}, {0x33, 0x54, 0xb0, 0xff}}},
    {{{     8,     -4,    -41}, 0, {    56,    974}, {0xa9, 0x01, 0xa4, 0xff}}},
    {{{    12,     43,    -25}, 0, {  1928,    976}, {0xb0, 0x5c, 0xde, 0xff}}},
    {{{    36,     44,    -27}, 0, {  2016,     84}, {0x33, 0x54, 0xb0, 0xff}}},
    {{{    32,     -8,    -44}, 0, {   -28,     82}, {0x1d, 0xbe, 0x98, 0xff}}},
};

// 0x060390A0
static const Vtx bowser_seg6_vertex_060390A0[] = {
    {{{     8,     -4,     41}, 0, {    60,    980}, {0x97, 0xe3, 0x40, 0xff}}},
    {{{     5,    -34,      0}, 0, {  1944,    980}, {0xb9, 0x9b, 0xe6, 0xff}}},
    {{{    29,    -40,      0}, 0, {  2008,    -40}, {0x0a, 0x83, 0x13, 0xff}}},
    {{{    29,    -40,      0}, 0, {   -12,      0}, {0x0a, 0x83, 0x13, 0xff}}},
    {{{     5,    -34,      0}, 0, {   132,    990}, {0xb9, 0x9b, 0xe6, 0xff}}},
    {{{    32,     -8,    -44}, 0, {  2012,      0}, {0x1d, 0xbe, 0x98, 0xff}}},
    {{{    32,     -8,     44}, 0, {   -44,    -40}, {0x21, 0xdb, 0x74, 0xff}}},
};

// 0x06039110 - 0x060391C8
const Gfx bowser_seg6_dl_06039110[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_06038C80, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  1, 15,  2, 0x0),
    gsSPVertex(bowser_seg6_vertex_06038D80, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 7,  5,  8, 0x0,  8,  5,  9, 0x0),
    gsSP1Triangle( 9,  5,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x060391C8 - 0x06039260
const Gfx bowser_seg6_dl_060391C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06023C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06038E20, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  2, 15,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_06038F20, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP1Triangle( 4,  6,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06039260 - 0x060392E0
const Gfx bowser_seg6_dl_06039260[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06038FB0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(14, 11, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_060390A0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x060392E0 - 0x06039368
const Gfx bowser_seg6_dl_060392E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06039110),
    gsSPDisplayList(bowser_seg6_dl_060391C8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06039260),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06039368
static const Vtx bowser_seg6_vertex_06039368[] = {
    {{{    47,     33,    -21}, 0, {   -12,    326}, {0x68, 0x3a, 0xd7, 0xff}}},
    {{{    47,     33,     21}, 0, {   282,    954}, {0x5b, 0x52, 0x20, 0xff}}},
    {{{    49,    -32,      0}, 0, {   970,    362}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    27,     37,    -24}, 0, {   968,    990}, {0xd6, 0x5f, 0xb9, 0xff}}},
    {{{    21,     31,     20}, 0, {    66,     32}, {0xcb, 0x61, 0x3c, 0xff}}},
    {{{    27,     37,     24}, 0, {    -2,    936}, {0xd7, 0x4c, 0x5c, 0xff}}},
    {{{    21,     31,    -20}, 0, {   864,     76}, {0xcf, 0x51, 0xac, 0xff}}},
    {{{    23,    -31,      0}, 0, {   884,     52}, {0xcd, 0x8c, 0x00, 0xff}}},
    {{{    22,     -7,    -32}, 0, {   112,     30}, {0xc1, 0xea, 0x95, 0xff}}},
    {{{    29,     -9,    -39}, 0, {     8,    884}, {0xde, 0xc0, 0x99, 0xff}}},
    {{{    30,    -38,      0}, 0, {   946,    910}, {0xdf, 0x86, 0x00, 0xff}}},
    {{{    29,     -9,     39}, 0, {   948,    890}, {0xdc, 0xc0, 0x67, 0xff}}},
    {{{    23,    -31,      0}, 0, {    68,     14}, {0xcd, 0x8c, 0x00, 0xff}}},
    {{{    30,    -38,      0}, 0, {    -6,    812}, {0xdf, 0x86, 0x00, 0xff}}},
    {{{    22,     -7,     32}, 0, {   852,     78}, {0xc1, 0xea, 0x6b, 0xff}}},
};

// 0x06039458
static const Vtx bowser_seg6_vertex_06039458[] = {
    {{{    27,     37,     24}, 0, {   940,    856}, {0xd7, 0x4c, 0x5c, 0xff}}},
    {{{    22,     -7,     32}, 0, {   116,    116}, {0xc1, 0xea, 0x6b, 0xff}}},
    {{{    29,     -9,     39}, 0, {    16,    898}, {0xdc, 0xc0, 0x67, 0xff}}},
    {{{    21,     31,     20}, 0, {   878,     82}, {0xcb, 0x61, 0x3c, 0xff}}},
    {{{    29,     -9,    -39}, 0, {   968,    912}, {0xde, 0xc0, 0x99, 0xff}}},
    {{{    22,     -7,    -32}, 0, {   890,     48}, {0xc1, 0xea, 0x95, 0xff}}},
    {{{    27,     37,    -24}, 0, {   -12,    904}, {0xd6, 0x5f, 0xb9, 0xff}}},
    {{{    21,     31,    -20}, 0, {    82,     40}, {0xcf, 0x51, 0xac, 0xff}}},
    {{{    47,     33,     21}, 0, {   282,    954}, {0x5b, 0x52, 0x20, 0xff}}},
    {{{    48,     -7,     35}, 0, {   890,    976}, {0x44, 0xfb, 0x6a, 0xff}}},
    {{{    49,    -32,      0}, 0, {   970,    362}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    48,     -7,    -34}, 0, {   412,    -40}, {0x46, 0xfb, 0x97, 0xff}}},
    {{{    47,     33,    -21}, 0, {   -12,    326}, {0x68, 0x3a, 0xd7, 0xff}}},
};

// 0x06039528
static const Vtx bowser_seg6_vertex_06039528[] = {
    {{{    -8,     -8,    -31}, 0, {   926,    968}, {0xc9, 0xdc, 0x94, 0xff}}},
    {{{    -9,     29,    -19}, 0, {   912,     42}, {0xba, 0x5f, 0xd3, 0xff}}},
    {{{    21,     31,    -20}, 0, {     4,    -22}, {0xcf, 0x51, 0xac, 0xff}}},
    {{{    22,     -7,     32}, 0, {    -8,    950}, {0xc1, 0xea, 0x6b, 0xff}}},
    {{{    -8,     -8,     31}, 0, {   910,    962}, {0xc9, 0xdc, 0x6c, 0xff}}},
    {{{    23,    -31,      0}, 0, {   -16,    -26}, {0xcd, 0x8c, 0x00, 0xff}}},
    {{{    -7,    -31,      0}, 0, {   904,     16}, {0xb3, 0x9c, 0x00, 0xff}}},
    {{{    21,     31,     20}, 0, {   -18,    938}, {0xcb, 0x61, 0x3c, 0xff}}},
    {{{    -8,     -8,     31}, 0, {   936,     36}, {0xc9, 0xdc, 0x6c, 0xff}}},
    {{{    22,     -7,     32}, 0, {    -6,    -10}, {0xc1, 0xea, 0x6b, 0xff}}},
    {{{    -9,     29,     19}, 0, {   926,    956}, {0xac, 0x4b, 0x39, 0xff}}},
    {{{    -9,     29,    -19}, 0, {   972,    944}, {0xba, 0x5f, 0xd3, 0xff}}},
    {{{    -9,     29,     19}, 0, {   946,      0}, {0xac, 0x4b, 0x39, 0xff}}},
    {{{    21,     31,     20}, 0, {    -6,    -26}, {0xcb, 0x61, 0x3c, 0xff}}},
    {{{    21,     31,    -20}, 0, {    20,    948}, {0xcf, 0x51, 0xac, 0xff}}},
    {{{    22,     -7,    -32}, 0, {    20,    934}, {0xc1, 0xea, 0x95, 0xff}}},
};

// 0x06039628
static const Vtx bowser_seg6_vertex_06039628[] = {
    {{{    23,    -31,      0}, 0, {    -8,    944}, {0xcd, 0x8c, 0x00, 0xff}}},
    {{{    -7,    -31,      0}, 0, {   910,    962}, {0xb3, 0x9c, 0x00, 0xff}}},
    {{{    -8,     -8,    -31}, 0, {   926,     32}, {0xc9, 0xdc, 0x94, 0xff}}},
    {{{    22,     -7,    -32}, 0, {     8,    -14}, {0xc1, 0xea, 0x95, 0xff}}},
    {{{    -8,     -8,     31}, 0, {   764,    984}, {0xc9, 0xdc, 0x6c, 0xff}}},
    {{{   -20,      1,      0}, 0, {   350,    440}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{    -7,    -31,      0}, 0, {   -36,   1000}, {0xb3, 0x9c, 0x00, 0xff}}},
    {{{    -9,     29,     19}, 0, {   996,    214}, {0xac, 0x4b, 0x39, 0xff}}},
    {{{    -8,     -8,    -31}, 0, {  -302,    244}, {0xc9, 0xdc, 0x94, 0xff}}},
    {{{    -9,     29,    -19}, 0, {   334,   -244}, {0xba, 0x5f, 0xd3, 0xff}}},
};

// 0x060396C8
static const Vtx bowser_seg6_vertex_060396C8[] = {
    {{{    27,     37,     24}, 0, {     0,     28}, {0xd7, 0x4c, 0x5c, 0xff}}},
    {{{    47,     33,     21}, 0, {   140,    938}, {0x5b, 0x52, 0x20, 0xff}}},
    {{{    27,     37,    -24}, 0, {  1992,     52}, {0xd6, 0x5f, 0xb9, 0xff}}},
    {{{    49,    -32,      0}, 0, {    92,    942}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    48,     -7,     35}, 0, {  1896,    926}, {0x44, 0xfb, 0x6a, 0xff}}},
    {{{    29,     -9,     39}, 0, {  2004,      0}, {0xdc, 0xc0, 0x67, 0xff}}},
    {{{    27,     37,    -24}, 0, {     0,     -8}, {0xd6, 0x5f, 0xb9, 0xff}}},
    {{{    48,     -7,    -34}, 0, {  1884,    934}, {0x46, 0xfb, 0x97, 0xff}}},
    {{{    29,     -9,    -39}, 0, {  1984,      4}, {0xde, 0xc0, 0x99, 0xff}}},
    {{{    47,     33,    -21}, 0, {   108,    924}, {0x68, 0x3a, 0xd7, 0xff}}},
    {{{    47,     33,    -21}, 0, {  1892,    960}, {0x68, 0x3a, 0xd7, 0xff}}},
    {{{    29,     -9,     39}, 0, {   -48,     38}, {0xdc, 0xc0, 0x67, 0xff}}},
    {{{    48,     -7,     35}, 0, {    72,    978}, {0x44, 0xfb, 0x6a, 0xff}}},
    {{{    27,     37,     24}, 0, {  2008,      4}, {0xd7, 0x4c, 0x5c, 0xff}}},
    {{{    47,     33,     21}, 0, {  1884,    950}, {0x5b, 0x52, 0x20, 0xff}}},
    {{{    30,    -38,      0}, 0, {   -44,     18}, {0xdf, 0x86, 0x00, 0xff}}},
};

// 0x060397C8
static const Vtx bowser_seg6_vertex_060397C8[] = {
    {{{    29,     -9,    -39}, 0, {    -8,     36}, {0xde, 0xc0, 0x99, 0xff}}},
    {{{    48,     -7,    -34}, 0, {   104,    890}, {0x46, 0xfb, 0x97, 0xff}}},
    {{{    49,    -32,      0}, 0, {  1840,    892}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    30,    -38,      0}, 0, {  1968,     40}, {0xdf, 0x86, 0x00, 0xff}}},
};

// 0x06039808 - 0x060398B0
const Gfx bowser_seg6_dl_06039808[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06023C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_06039368, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039458, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x060398B0 - 0x06039958
const Gfx bowser_seg6_dl_060398B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06039528, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14, 11, 13, 0x0, 15,  0,  2, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039628, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 8,  5,  9, 0x0,  6,  5,  8, 0x0),
    gsSP1Triangle( 9,  5,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06039958 - 0x060399D8
const Gfx bowser_seg6_dl_06039958[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_060396C8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 1, 10,  2, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0, 15,  3,  5, 0x0),
    gsSPVertex(bowser_seg6_vertex_060397C8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x060399D8 - 0x06039A60
const Gfx bowser_seg6_dl_060399D8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06039808),
    gsSPDisplayList(bowser_seg6_dl_060398B0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06039958),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06039A60
static const Vtx bowser_seg6_vertex_06039A60[] = {
    {{{    91,     -2,    -17}, 0, {  -308,    -60}, {0x32, 0x8c, 0xfb, 0xff}}},
    {{{    91,     -2,     22}, 0, {  -298,    522}, {0x3a, 0x91, 0x0c, 0xff}}},
    {{{    54,    -16,     22}, 0, {   280,    532}, {0x0e, 0x84, 0x15, 0xff}}},
    {{{    74,     -3,    -48}, 0, {   -54,   -518}, {0x12, 0xa7, 0xa8, 0xff}}},
    {{{    54,    -16,    -17}, 0, {   272,    -48}, {0x02, 0x83, 0xeb, 0xff}}},
    {{{    74,     -3,     53}, 0, {     0,    990}, {0x10, 0xb7, 0x66, 0xff}}},
    {{{   -16,      3,     42}, 0, {  1460,    844}, {0xd8, 0xb0, 0x59, 0xff}}},
    {{{   -15,     -5,     22}, 0, {  1418,    552}, {0xdf, 0x87, 0x11, 0xff}}},
    {{{    92,     29,     22}, 0, {   162,   2132}, {0x28, 0x78, 0xfc, 0xff}}},
    {{{    92,     29,    -17}, 0, {   802,   2114}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{    56,     36,    -17}, 0, {   812,   1498}, {0x0e, 0x7e, 0xfa, 0xff}}},
    {{{    56,     36,     22}, 0, {   172,   1516}, {0x0c, 0x7e, 0x07, 0xff}}},
    {{{    76,     31,     53}, 0, {  -342,   1870}, {0x0e, 0x58, 0x5a, 0xff}}},
    {{{   -45,      9,     22}, 0, {  1920,    562}, {0xa1, 0xb9, 0x2d, 0xff}}},
    {{{   -45,      9,    -17}, 0, {  1910,    -20}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{   -15,     -5,    -17}, 0, {  1408,    -28}, {0xd8, 0x89, 0xef, 0xff}}},
};

// 0x06039B60
static const Vtx bowser_seg6_vertex_06039B60[] = {
    {{{    56,     36,    -17}, 0, {   812,   1498}, {0x0e, 0x7e, 0xfa, 0xff}}},
    {{{    92,     29,    -17}, 0, {   802,   2114}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{    76,     31,    -48}, 0, {  1318,   1824}, {0x07, 0x4b, 0x9a, 0xff}}},
    {{{   -14,     40,      2}, 0, {   512,    280}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    56,     36,     22}, 0, {   172,   1516}, {0x0c, 0x7e, 0x07, 0xff}}},
    {{{   -15,     35,    -37}, 0, {  1152,    264}, {0xdf, 0x53, 0xa7, 0xff}}},
    {{{   -16,      3,    -37}, 0, {  1036,    282}, {0xdb, 0xbd, 0x9c, 0xff}}},
    {{{   -15,     35,    -37}, 0, {  1012,    928}, {0xdf, 0x53, 0xa7, 0xff}}},
    {{{    76,     31,    -48}, 0, {  -122,    912}, {0x07, 0x4b, 0x9a, 0xff}}},
    {{{   -45,      9,    -17}, 0, {  1408,    400}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{   -51,     37,    -17}, 0, {  1478,    946}, {0x98, 0x3c, 0xd8, 0xff}}},
    {{{    74,     -3,    -48}, 0, {   -94,    208}, {0x12, 0xa7, 0xa8, 0xff}}},
    {{{    74,     -3,     53}, 0, {   -88,    348}, {0x10, 0xb7, 0x66, 0xff}}},
    {{{    76,     31,     53}, 0, {   -74,    848}, {0x0e, 0x58, 0x5a, 0xff}}},
    {{{   -15,     35,     42}, 0, {  1560,    656}, {0xe1, 0x47, 0x64, 0xff}}},
};

// 0x06039C50
static const Vtx bowser_seg6_vertex_06039C50[] = {
    {{{   -16,      3,     42}, 0, {  1460,    844}, {0xd8, 0xb0, 0x59, 0xff}}},
    {{{   -45,      9,     22}, 0, {  1920,    562}, {0xa1, 0xb9, 0x2d, 0xff}}},
    {{{   -15,     -5,     22}, 0, {  1418,    552}, {0xdf, 0x87, 0x11, 0xff}}},
    {{{   -15,     -5,    -17}, 0, {  1408,    -28}, {0xd8, 0x89, 0xef, 0xff}}},
    {{{   -45,      9,    -17}, 0, {  1910,    -20}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{   -16,      3,    -37}, 0, {  1442,   -318}, {0xdb, 0xbd, 0x9c, 0xff}}},
    {{{    54,    -16,    -17}, 0, {   272,    -48}, {0x02, 0x83, 0xeb, 0xff}}},
    {{{   -45,      9,     22}, 0, {  2092,    216}, {0xa1, 0xb9, 0x2d, 0xff}}},
    {{{   -16,      3,     42}, 0, {  1546,    198}, {0xd8, 0xb0, 0x59, 0xff}}},
    {{{   -15,     35,     42}, 0, {  1560,    656}, {0xe1, 0x47, 0x64, 0xff}}},
    {{{   -51,     37,     22}, 0, {  2236,    586}, {0xb1, 0x55, 0x32, 0xff}}},
    {{{    74,     -3,     53}, 0, {   -88,    348}, {0x10, 0xb7, 0x66, 0xff}}},
    {{{    56,     36,     22}, 0, {   172,   1516}, {0x0c, 0x7e, 0x07, 0xff}}},
    {{{   -14,     40,      2}, 0, {   512,    280}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   -15,     35,     42}, 0, {  -128,    300}, {0xe1, 0x47, 0x64, 0xff}}},
    {{{    76,     31,     53}, 0, {  -342,   1870}, {0x0e, 0x58, 0x5a, 0xff}}},
};

// 0x06039D50
static const Vtx bowser_seg6_vertex_06039D50[] = {
    {{{   -14,     40,      2}, 0, {   512,    280}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   -51,     37,     22}, 0, {   200,   -324}, {0xb1, 0x55, 0x32, 0xff}}},
    {{{   -15,     35,     42}, 0, {  -128,    300}, {0xe1, 0x47, 0x64, 0xff}}},
    {{{   -15,     35,    -37}, 0, {  1152,    264}, {0xdf, 0x53, 0xa7, 0xff}}},
    {{{   -51,     37,    -17}, 0, {   840,   -342}, {0x98, 0x3c, 0xd8, 0xff}}},
    {{{   -45,      9,    -17}, 0, {  1026,    300}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{   -45,      9,     22}, 0, {   310,    298}, {0xa1, 0xb9, 0x2d, 0xff}}},
    {{{   -51,     37,    -17}, 0, {   928,    754}, {0x98, 0x3c, 0xd8, 0xff}}},
    {{{   -51,     37,     22}, 0, {   214,    750}, {0xb1, 0x55, 0x32, 0xff}}},
    {{{    74,     -3,    -48}, 0, {  1318,   1834}, {0x12, 0xa7, 0xa8, 0xff}}},
    {{{    54,    -16,    -17}, 0, {   812,   1512}, {0x02, 0x83, 0xeb, 0xff}}},
    {{{   -16,      3,    -37}, 0, {  1152,    274}, {0xdb, 0xbd, 0x9c, 0xff}}},
};

// 0x06039E10
static const Vtx bowser_seg6_vertex_06039E10[] = {
    {{{    92,     29,     22}, 0, {    44,    928}, {0x28, 0x78, 0xfc, 0xff}}},
    {{{   104,     30,     16}, 0, {   172,   1216}, {0x16, 0x6b, 0x3f, 0xff}}},
    {{{    92,     29,    -17}, 0, {   972,    908}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{   104,     30,    -11}, 0, {   846,   1200}, {0x1f, 0x5c, 0xaf, 0xff}}},
    {{{   136,     31,      2}, 0, {   512,   1972}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{    92,     29,    -17}, 0, {   212,   1076}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{   106,      2,    -11}, 0, {   862,   1244}, {0x46, 0xca, 0xa6, 0xff}}},
    {{{    91,     -2,    -17}, 0, {   858,    916}, {0x32, 0x8c, 0xfb, 0xff}}},
    {{{   104,     30,    -11}, 0, {   270,   1316}, {0x1f, 0x5c, 0xaf, 0xff}}},
    {{{   136,     31,      2}, 0, {   502,   1956}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{    91,     -2,    -17}, 0, {   -82,    924}, {0x32, 0x8c, 0xfb, 0xff}}},
    {{{   106,      2,     16}, 0, {   790,   1272}, {0x44, 0xb7, 0x4d, 0xff}}},
    {{{    91,     -2,     22}, 0, {   914,    976}, {0x3a, 0x91, 0x0c, 0xff}}},
    {{{   106,      2,    -11}, 0, {    78,   1236}, {0x46, 0xca, 0xa6, 0xff}}},
};

// 0x06039EF0
static const Vtx bowser_seg6_vertex_06039EF0[] = {
    {{{   104,     27,     25}, 0, {   300,   1356}, {0x1a, 0x51, 0xa2, 0xff}}},
    {{{   126,     31,     32}, 0, {   416,   1916}, {0x7a, 0x20, 0x05, 0xff}}},
    {{{   106,      6,     26}, 0, {   856,   1344}, {0x47, 0xc6, 0xa9, 0xff}}},
    {{{   106,      2,    -11}, 0, {    78,   1236}, {0x46, 0xca, 0xa6, 0xff}}},
    {{{   136,     31,      2}, 0, {   476,   1924}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{   106,      2,     16}, 0, {   790,   1272}, {0x44, 0xb7, 0x4d, 0xff}}},
    {{{    76,     31,     53}, 0, {   880,    820}, {0x0e, 0x58, 0x5a, 0xff}}},
    {{{    93,     31,     49}, 0, {   748,   1252}, {0x1b, 0x6e, 0x37, 0xff}}},
    {{{    92,     29,     22}, 0, {    18,   1036}, {0x28, 0x78, 0xfc, 0xff}}},
    {{{   104,     27,     25}, 0, {   102,   1384}, {0x1a, 0x51, 0xa2, 0xff}}},
    {{{   126,     31,     32}, 0, {   254,   2032}, {0x7a, 0x20, 0x05, 0xff}}},
    {{{    92,     29,     22}, 0, {   116,   1072}, {0x28, 0x78, 0xfc, 0xff}}},
    {{{    91,     -2,     22}, 0, {   926,    920}, {0x3a, 0x91, 0x0c, 0xff}}},
    {{{    76,     31,     53}, 0, {   -10,    816}, {0x0e, 0x58, 0x5a, 0xff}}},
    {{{    74,     -3,     53}, 0, {   860,    760}, {0x10, 0xb7, 0x66, 0xff}}},
    {{{    95,      6,     49}, 0, {   736,   1228}, {0x3e, 0xc3, 0x5c, 0xff}}},
};

// 0x06039FF0
static const Vtx bowser_seg6_vertex_06039FF0[] = {
    {{{    91,     -2,     22}, 0, {   138,    952}, {0x3a, 0x91, 0x0c, 0xff}}},
    {{{    95,      6,     49}, 0, {   790,   1252}, {0x3e, 0xc3, 0x5c, 0xff}}},
    {{{    74,     -3,     53}, 0, {   958,    796}, {0x10, 0xb7, 0x66, 0xff}}},
    {{{   106,      6,     26}, 0, {   192,   1352}, {0x47, 0xc6, 0xa9, 0xff}}},
    {{{   126,     31,     32}, 0, {   298,   1992}, {0x7a, 0x20, 0x05, 0xff}}},
    {{{   106,      2,     16}, 0, {   968,   1240}, {0x44, 0xb7, 0x4d, 0xff}}},
    {{{   104,     30,     16}, 0, {    34,   1232}, {0x16, 0x6b, 0x3f, 0xff}}},
    {{{    92,     29,     22}, 0, {   -42,    980}, {0x28, 0x78, 0xfc, 0xff}}},
    {{{   136,     31,      2}, 0, {   354,   1912}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{    91,     -2,     22}, 0, {   984,    908}, {0x3a, 0x91, 0x0c, 0xff}}},
    {{{    95,      6,     49}, 0, {   736,   1228}, {0x3e, 0xc3, 0x5c, 0xff}}},
    {{{   126,     31,     32}, 0, {   244,   1992}, {0x7a, 0x20, 0x05, 0xff}}},
    {{{    93,     31,     49}, 0, {    88,   1200}, {0x1b, 0x6e, 0x37, 0xff}}},
    {{{    76,     31,     53}, 0, {   -10,    816}, {0x0e, 0x58, 0x5a, 0xff}}},
};

// 0x0603A0D0
static const Vtx bowser_seg6_vertex_0603A0D0[] = {
    {{{    91,     -2,    -17}, 0, {   992,    904}, {0x32, 0x8c, 0xfb, 0xff}}},
    {{{    74,     -3,    -48}, 0, {   246,    864}, {0x12, 0xa7, 0xa8, 0xff}}},
    {{{   100,      5,    -44}, 0, {   386,   1256}, {0x3c, 0xbc, 0xa8, 0xff}}},
    {{{    92,     29,    -17}, 0, {   222,    956}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{   105,     29,    -19}, 0, {   270,   1160}, {0x10, 0x54, 0x5d, 0xff}}},
    {{{    98,     31,    -44}, 0, {   962,   1236}, {0x15, 0x6e, 0xc5, 0xff}}},
    {{{    76,     31,    -48}, 0, {  1104,    932}, {0x07, 0x4b, 0x9a, 0xff}}},
    {{{   132,     31,    -26}, 0, {   430,   1636}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{    76,     31,    -48}, 0, {   562,    936}, {0x07, 0x4b, 0x9a, 0xff}}},
    {{{    98,     31,    -44}, 0, {   664,   1344}, {0x15, 0x6e, 0xc5, 0xff}}},
    {{{   100,      5,    -44}, 0, {  1234,   1292}, {0x3c, 0xbc, 0xa8, 0xff}}},
    {{{    74,     -3,    -48}, 0, {  1330,    776}, {0x12, 0xa7, 0xa8, 0xff}}},
    {{{   132,     31,    -26}, 0, {   792,   2032}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   132,     31,    -26}, 0, {   848,   1780}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   107,      6,    -20}, 0, {   954,   1212}, {0x3c, 0xc5, 0x5e, 0xff}}},
};

// 0x0603A1C0
static const Vtx bowser_seg6_vertex_0603A1C0[] = {
    {{{    92,     29,    -17}, 0, {   784,    932}, {0x1c, 0x7b, 0x05, 0xff}}},
    {{{    91,     -2,    -17}, 0, {     8,    928}, {0x32, 0x8c, 0xfb, 0xff}}},
    {{{   107,      6,    -20}, 0, {   180,   1232}, {0x3c, 0xc5, 0x5e, 0xff}}},
    {{{   132,     31,    -26}, 0, {   704,   1704}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   105,     29,    -19}, 0, {   740,   1168}, {0x10, 0x54, 0x5d, 0xff}}},
};

// 0x0603A210 - 0x0603A380
const Gfx bowser_seg6_dl_0603A210[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_06039A60, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  7,  2, 0x0,  4,  2,  7, 0x0),
    gsSP2Triangles( 6,  2,  5, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11,  8, 10, 0x0, 11, 12,  8, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039B60, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11,  6,  8, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039C50, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  2,  3,  6, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 15, 12, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039D50, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 6,  8,  7, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603A380 - 0x0603A4E8
const Gfx bowser_seg6_dl_0603A380[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06029C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06039E10, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039EF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 7, 10,  9, 0x0, 11,  2, 12, 0x0),
    gsSP2Triangles(11,  0,  2, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06039FF0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9,  5,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603A0D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10, 11,  8, 0x0),
    gsSP2Triangles( 9, 12, 10, 0x0,  2, 13, 14, 0x0),
    gsSP1Triangle( 2, 14,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603A1C0, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP1Triangle( 2,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603A4E8 - 0x0603A568
const Gfx bowser_seg6_dl_0603A4E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603A210),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603A380),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603A568
static const Vtx bowser_seg6_vertex_0603A568[] = {
    {{{    15,     37,    -13}, 0, {  1182,    694}, {0xda, 0x4e, 0xa5, 0xff}}},
    {{{    37,    -25,    -11}, 0, {  -316,     92}, {0x22, 0xc5, 0x96, 0xff}}},
    {{{    13,     -3,    -13}, 0, {   178,    684}, {0x59, 0x47, 0xca, 0xff}}},
    {{{     0,    -18,     15}, 0, {   680,    -50}, {0x9b, 0xc7, 0x32, 0xff}}},
    {{{     0,     -6,     20}, 0, {   400,    -66}, {0xc6, 0xe2, 0x6c, 0xff}}},
    {{{    -2,     19,     15}, 0, {  -124,    252}, {0x88, 0x21, 0x18, 0xff}}},
    {{{    -1,      8,     20}, 0, {    78,     56}, {0xd9, 0x15, 0x76, 0xff}}},
    {{{     0,    -23,    -11}, 0, {   966,    448}, {0xac, 0xa2, 0xfa, 0xff}}},
    {{{    -2,     20,    -15}, 0, {    38,    882}, {0xa4, 0x3b, 0xc1, 0xff}}},
    {{{     0,    -17,    -30}, 0, {   958,    896}, {0xb9, 0xd5, 0xa1, 0xff}}},
    {{{    -2,     20,    -15}, 0, {   726,   1114}, {0xa4, 0x3b, 0xc1, 0xff}}},
    {{{    15,      2,    -32}, 0, {   324,    686}, {0x08, 0x61, 0xaf, 0xff}}},
    {{{     0,    -17,    -30}, 0, {  -182,   1010}, {0xb9, 0xd5, 0xa1, 0xff}}},
    {{{    36,     38,    -12}, 0, {  1236,    186}, {0x15, 0x66, 0xb9, 0xff}}},
};

// 0x0603A648
static const Vtx bowser_seg6_vertex_0603A648[] = {
    {{{    37,    -25,    -11}, 0, {  1046,    614}, {0x22, 0xc5, 0x96, 0xff}}},
    {{{    51,    -23,    -12}, 0, {  1378,    592}, {0x15, 0xa1, 0xaf, 0xff}}},
    {{{    51,    -20,     14}, 0, {  1332,     -2}, {0x2b, 0xa4, 0x4b, 0xff}}},
    {{{    36,     38,    -12}, 0, {  1236,    186}, {0x15, 0x66, 0xb9, 0xff}}},
    {{{    50,     20,     -2}, 0, {   830,   -180}, {0x25, 0xea, 0x89, 0xff}}},
    {{{    37,    -25,    -11}, 0, {  -316,     92}, {0x22, 0xc5, 0x96, 0xff}}},
    {{{    54,      0,     -2}, 0, {   338,   -322}, {0xf9, 0x18, 0x84, 0xff}}},
    {{{    51,    -23,    -12}, 0, {  -236,   -240}, {0x15, 0xa1, 0xaf, 0xff}}},
    {{{    22,    -26,     15}, 0, {   648,     48}, {0xf8, 0x94, 0x41, 0xff}}},
    {{{     0,    -23,    -11}, 0, {   152,    680}, {0xac, 0xa2, 0xfa, 0xff}}},
    {{{     0,    -18,     15}, 0, {   106,     88}, {0x9b, 0xc7, 0x32, 0xff}}},
    {{{    20,    -23,    -22}, 0, {   664,    880}, {0x2f, 0x8d, 0xe9, 0xff}}},
    {{{     0,    -17,    -30}, 0, {   204,   1114}, {0xb9, 0xd5, 0xa1, 0xff}}},
    {{{    50,     20,     24}, 0, {   216,   -160}, {0x3b, 0x32, 0x64, 0xff}}},
    {{{    36,     35,     14}, 0, {  -154,    106}, {0x08, 0x61, 0x50, 0xff}}},
    {{{    -1,      8,     20}, 0, {   360,    986}, {0xd9, 0x15, 0x76, 0xff}}},
};

// 0x0603A748
static const Vtx bowser_seg6_vertex_0603A748[] = {
    {{{    16,     35,     15}, 0, {   734,    100}, {0xe1, 0x67, 0x43, 0xff}}},
    {{{    36,     35,     14}, 0, {    96,    132}, {0x08, 0x61, 0x50, 0xff}}},
    {{{    36,     38,    -12}, 0, {    84,    916}, {0x15, 0x66, 0xb9, 0xff}}},
    {{{    -2,     19,     15}, 0, {  1290,     72}, {0x88, 0x21, 0x18, 0xff}}},
    {{{    15,     37,    -13}, 0, {   724,    938}, {0xda, 0x4e, 0xa5, 0xff}}},
    {{{    -2,     20,    -15}, 0, {  1278,    964}, {0xa4, 0x3b, 0xc1, 0xff}}},
    {{{    13,     -3,    -13}, 0, {   236,    338}, {0x59, 0x47, 0xca, 0xff}}},
    {{{    15,      2,    -32}, 0, {   236,    830}, {0x08, 0x61, 0xaf, 0xff}}},
    {{{    -2,     20,    -15}, 0, {   966,    548}, {0xa4, 0x3b, 0xc1, 0xff}}},
    {{{    55,      0,     24}, 0, {   700,   -216}, {0x37, 0xf3, 0x71, 0xff}}},
    {{{    50,     20,     24}, 0, {   216,   -160}, {0x3b, 0x32, 0x64, 0xff}}},
    {{{    -1,      8,     20}, 0, {   360,    986}, {0xd9, 0x15, 0x76, 0xff}}},
    {{{    36,     35,     14}, 0, {  -154,    106}, {0x08, 0x61, 0x50, 0xff}}},
    {{{    16,     35,     15}, 0, {  -208,    554}, {0xe1, 0x67, 0x43, 0xff}}},
    {{{     0,     -6,     20}, 0, {   704,   1000}, {0xc6, 0xe2, 0x6c, 0xff}}},
    {{{    -2,     19,     15}, 0, {   106,    984}, {0x88, 0x21, 0x18, 0xff}}},
};

// 0x0603A848
static const Vtx bowser_seg6_vertex_0603A848[] = {
    {{{    20,    -23,    -22}, 0, {   664,    880}, {0x2f, 0x8d, 0xe9, 0xff}}},
    {{{     0,    -17,    -30}, 0, {   204,   1114}, {0xb9, 0xd5, 0xa1, 0xff}}},
    {{{    14,    -12,    -35}, 0, {   552,   1184}, {0x00, 0xcd, 0x8d, 0xff}}},
    {{{    15,      2,    -32}, 0, {   324,    686}, {0x08, 0x61, 0xaf, 0xff}}},
    {{{    14,    -12,    -35}, 0, {   -38,    684}, {0x00, 0xcd, 0x8d, 0xff}}},
    {{{     0,    -17,    -30}, 0, {  -182,   1010}, {0xb9, 0xd5, 0xa1, 0xff}}},
    {{{    13,     -3,    -13}, 0, {   730,    784}, {0x59, 0x47, 0xca, 0xff}}},
    {{{    37,    -25,    -11}, 0, {   172,    178}, {0x22, 0xc5, 0x96, 0xff}}},
    {{{    20,    -23,    -22}, 0, {    74,    774}, {0x2f, 0x8d, 0xe9, 0xff}}},
    {{{     0,     -6,     20}, 0, {   704,   1000}, {0xc6, 0xe2, 0x6c, 0xff}}},
    {{{     0,    -18,     15}, 0, {   964,   1020}, {0x9b, 0xc7, 0x32, 0xff}}},
    {{{    22,    -26,     15}, 0, {  1214,    558}, {0xf8, 0x94, 0x41, 0xff}}},
    {{{    51,    -20,     14}, 0, {  1154,    -68}, {0x2b, 0xa4, 0x4b, 0xff}}},
    {{{    55,      0,     24}, 0, {   700,   -216}, {0x37, 0xf3, 0x71, 0xff}}},
};

// 0x0603A928
static const Vtx bowser_seg6_vertex_0603A928[] = {
    {{{    55,      0,     24}, 0, {   194,    592}, {0x37, 0xf3, 0x71, 0xff}}},
    {{{    69,     -2,      6}, 0, {   248,   1300}, {0x60, 0x42, 0x31, 0xff}}},
    {{{    54,      0,     -2}, 0, {   726,    960}, {0xf9, 0x18, 0x84, 0xff}}},
    {{{    36,     38,    -12}, 0, {   270,    832}, {0x15, 0x66, 0xb9, 0xff}}},
    {{{    48,     36,    -12}, 0, {   378,   1276}, {0xec, 0x09, 0x84, 0xff}}},
    {{{    50,     20,     -2}, 0, {  1064,    960}, {0x25, 0xea, 0x89, 0xff}}},
    {{{    54,     23,     -7}, 0, {   898,   1248}, {0xf1, 0x8f, 0xc9, 0xff}}},
    {{{    62,     24,    -15}, 0, {   708,   1752}, {0x65, 0xe1, 0xbb, 0xff}}},
    {{{    50,     20,     -2}, 0, {   -10,   1060}, {0x25, 0xea, 0x89, 0xff}}},
    {{{    62,     16,     -6}, 0, {   206,   1404}, {0xe7, 0x37, 0x91, 0xff}}},
    {{{    54,      0,     -2}, 0, {   906,    948}, {0xf9, 0x18, 0x84, 0xff}}},
    {{{    65,      2,     -5}, 0, {   834,   1308}, {0xf3, 0x9f, 0xb1, 0xff}}},
    {{{    78,      9,    -18}, 0, {   600,   1912}, {0x66, 0x03, 0xb6, 0xff}}},
    {{{    63,     -2,     -7}, 0, {   632,   1320}, {0xee, 0x67, 0xb9, 0xff}}},
    {{{    78,     -8,    -20}, 0, {   576,   2012}, {0x69, 0x10, 0xbd, 0xff}}},
};

// 0x0603AA18
static const Vtx bowser_seg6_vertex_0603AA18[] = {
    {{{    62,    -17,    -13}, 0, {   688,   1364}, {0xf2, 0xe4, 0x86, 0xff}}},
    {{{    51,    -23,    -12}, 0, {   960,    992}, {0x15, 0xa1, 0xaf, 0xff}}},
    {{{    54,      0,     -2}, 0, {   -70,   1012}, {0xf9, 0x18, 0x84, 0xff}}},
    {{{    15,      2,    -32}, 0, {   876,    840}, {0x08, 0x61, 0xaf, 0xff}}},
    {{{    29,      0,    -32}, 0, {   698,   1420}, {0x1e, 0x6b, 0xc5, 0xff}}},
    {{{    29,     -9,    -34}, 0, {   456,   1336}, {0x13, 0xeb, 0x85, 0xff}}},
    {{{    14,    -12,    -35}, 0, {   480,    704}, {0x00, 0xcd, 0x8d, 0xff}}},
    {{{    44,     -6,    -31}, 0, {   430,   1972}, {0x75, 0x16, 0xd5, 0xff}}},
    {{{    32,    -16,    -26}, 0, {   154,   1348}, {0x43, 0x96, 0xf2, 0xff}}},
    {{{    51,    -20,     14}, 0, {   826,    744}, {0x2b, 0xa4, 0x4b, 0xff}}},
    {{{    67,    -17,      1}, 0, {   794,   1356}, {0x4e, 0xa0, 0x19, 0xff}}},
    {{{    69,     -2,      6}, 0, {   154,   1324}, {0x60, 0x42, 0x31, 0xff}}},
    {{{    55,      0,     24}, 0, {   -82,    672}, {0x37, 0xf3, 0x71, 0xff}}},
    {{{    63,     -2,     -7}, 0, {    54,   1328}, {0xee, 0x67, 0xb9, 0xff}}},
};

// 0x0603AAF8
static const Vtx bowser_seg6_vertex_0603AAF8[] = {
    {{{    67,     17,      6}, 0, {   676,   1304}, {0x52, 0x5a, 0x22, 0xff}}},
    {{{    78,      9,    -18}, 0, {   246,   2024}, {0x66, 0x03, 0xb6, 0xff}}},
    {{{    62,     16,     -6}, 0, {   156,   1420}, {0xe7, 0x37, 0x91, 0xff}}},
    {{{    63,     -2,     -7}, 0, {    54,   1328}, {0xee, 0x67, 0xb9, 0xff}}},
    {{{    78,     -8,    -20}, 0, {   382,   1912}, {0x69, 0x10, 0xbd, 0xff}}},
    {{{    62,    -17,    -13}, 0, {   688,   1364}, {0xf2, 0xe4, 0x86, 0xff}}},
    {{{    54,      0,     -2}, 0, {    60,    948}, {0xf9, 0x18, 0x84, 0xff}}},
    {{{    65,      2,     -5}, 0, {   236,   1312}, {0xf3, 0x9f, 0xb1, 0xff}}},
    {{{    70,      3,      6}, 0, {   672,   1308}, {0x55, 0xa8, 0x1f, 0xff}}},
    {{{    55,      0,     24}, 0, {   744,    612}, {0x37, 0xf3, 0x71, 0xff}}},
    {{{    78,      9,    -18}, 0, {   282,   1932}, {0x66, 0x03, 0xb6, 0xff}}},
    {{{    55,      0,     24}, 0, {   -18,    640}, {0x37, 0xf3, 0x71, 0xff}}},
    {{{    70,      3,      6}, 0, {    78,   1320}, {0x55, 0xa8, 0x1f, 0xff}}},
    {{{    67,     17,      6}, 0, {   596,   1320}, {0x52, 0x5a, 0x22, 0xff}}},
    {{{    50,     20,     24}, 0, {   738,    620}, {0x3b, 0x32, 0x64, 0xff}}},
};

// 0x0603ABE8
static const Vtx bowser_seg6_vertex_0603ABE8[] = {
    {{{    56,     35,      3}, 0, {   634,   1512}, {0x44, 0x68, 0x19, 0xff}}},
    {{{    36,     35,     14}, 0, {  1066,    788}, {0x08, 0x61, 0x50, 0xff}}},
    {{{    50,     20,     24}, 0, {   198,    532}, {0x3b, 0x32, 0x64, 0xff}}},
    {{{    67,     17,      6}, 0, {   676,   1304}, {0x52, 0x5a, 0x22, 0xff}}},
    {{{    62,     16,     -6}, 0, {   156,   1420}, {0xe7, 0x37, 0x91, 0xff}}},
    {{{    50,     20,     -2}, 0, {   -90,   1084}, {0x25, 0xea, 0x89, 0xff}}},
    {{{    50,     20,     24}, 0, {   748,    624}, {0x3b, 0x32, 0x64, 0xff}}},
    {{{    50,     20,     -2}, 0, {   180,   1028}, {0x25, 0xea, 0x89, 0xff}}},
    {{{    54,     23,     -7}, 0, {   310,   1312}, {0xf1, 0x8f, 0xc9, 0xff}}},
    {{{    60,     25,      6}, 0, {   820,   1292}, {0x67, 0xba, 0x14, 0xff}}},
    {{{    50,     20,     24}, 0, {   708,    544}, {0x3b, 0x32, 0x64, 0xff}}},
    {{{    62,     24,    -15}, 0, {   498,   1792}, {0x65, 0xe1, 0xbb, 0xff}}},
    {{{    60,     25,      6}, 0, {   178,   1344}, {0x67, 0xba, 0x14, 0xff}}},
    {{{    28,     -4,    -21}, 0, {   586,   1272}, {0x38, 0x3a, 0x61, 0xff}}},
    {{{    13,     -3,    -13}, 0, {   602,    624}, {0x59, 0x47, 0xca, 0xff}}},
    {{{    20,    -23,    -22}, 0, {   232,    736}, {0x2f, 0x8d, 0xe9, 0xff}}},
};

// 0x0603ACE8
static const Vtx bowser_seg6_vertex_0603ACE8[] = {
    {{{    28,     -4,    -21}, 0, {   562,   1172}, {0x38, 0x3a, 0x61, 0xff}}},
    {{{    29,      0,    -32}, 0, {   718,   1352}, {0x1e, 0x6b, 0xc5, 0xff}}},
    {{{    15,      2,    -32}, 0, {   834,    816}, {0x08, 0x61, 0xaf, 0xff}}},
    {{{    32,    -16,    -26}, 0, {   338,   1244}, {0x43, 0x96, 0xf2, 0xff}}},
    {{{    44,     -6,    -31}, 0, {   540,   1852}, {0x75, 0x16, 0xd5, 0xff}}},
    {{{    13,     -3,    -13}, 0, {   578,    524}, {0x59, 0x47, 0xca, 0xff}}},
    {{{    32,    -16,    -26}, 0, {   154,   1348}, {0x43, 0x96, 0xf2, 0xff}}},
    {{{    20,    -23,    -22}, 0, {   -18,    720}, {0x2f, 0x8d, 0xe9, 0xff}}},
    {{{    14,    -12,    -35}, 0, {   480,    704}, {0x00, 0xcd, 0x8d, 0xff}}},
    {{{    20,    -23,    -22}, 0, {   232,    736}, {0x2f, 0x8d, 0xe9, 0xff}}},
    {{{    32,    -16,    -26}, 0, {   362,   1340}, {0x43, 0x96, 0xf2, 0xff}}},
    {{{    28,     -4,    -21}, 0, {   586,   1272}, {0x38, 0x3a, 0x61, 0xff}}},
    {{{    60,     25,      6}, 0, {   252,   1212}, {0x67, 0xba, 0x14, 0xff}}},
    {{{    62,     24,    -15}, 0, {   196,   2012}, {0x65, 0xe1, 0xbb, 0xff}}},
    {{{    56,     35,      3}, 0, {   708,   1380}, {0x44, 0x68, 0x19, 0xff}}},
};

// 0x0603ADD8
static const Vtx bowser_seg6_vertex_0603ADD8[] = {
    {{{    56,     35,      3}, 0, {   578,   1580}, {0x44, 0x68, 0x19, 0xff}}},
    {{{    48,     36,    -12}, 0, {   -52,   1248}, {0xec, 0x09, 0x84, 0xff}}},
    {{{    36,     38,    -12}, 0, {  -250,    736}, {0x15, 0x66, 0xb9, 0xff}}},
    {{{    62,     24,    -15}, 0, {    86,   1984}, {0x65, 0xe1, 0xbb, 0xff}}},
    {{{    36,     35,     14}, 0, {   640,    724}, {0x08, 0x61, 0x50, 0xff}}},
    {{{    51,    -23,    -12}, 0, {    36,   1020}, {0x15, 0xa1, 0xaf, 0xff}}},
    {{{    62,    -17,    -13}, 0, {   332,   1368}, {0xf2, 0xe4, 0x86, 0xff}}},
    {{{    67,    -17,      1}, 0, {   760,   1272}, {0x4e, 0xa0, 0x19, 0xff}}},
    {{{    51,    -20,     14}, 0, {   682,    732}, {0x2b, 0xa4, 0x4b, 0xff}}},
    {{{    78,     -8,    -20}, 0, {   594,   1908}, {0x69, 0x10, 0xbd, 0xff}}},
    {{{    67,    -17,      1}, 0, {   800,   1316}, {0x4e, 0xa0, 0x19, 0xff}}},
    {{{    78,     -8,    -20}, 0, {   664,   2008}, {0x69, 0x10, 0xbd, 0xff}}},
    {{{    69,     -2,      6}, 0, {   160,   1284}, {0x60, 0x42, 0x31, 0xff}}},
    {{{    70,      3,      6}, 0, {    76,   1272}, {0x55, 0xa8, 0x1f, 0xff}}},
    {{{    78,      9,    -18}, 0, {   334,   2008}, {0x66, 0x03, 0xb6, 0xff}}},
    {{{    67,     17,      6}, 0, {   592,   1272}, {0x52, 0x5a, 0x22, 0xff}}},
};

// 0x0603AED8 - 0x0603B038
const Gfx bowser_seg6_dl_0603AED8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603A568, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 7,  3,  5, 0x0,  8,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10,  0,  2, 0x0),
    gsSP1Triangle( 0, 13,  1, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603A648, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0,  8,  9,  0, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0,  9, 11,  0, 0x0),
    gsSP2Triangles( 9, 12, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603A748, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 11, 0x0, 11, 14,  9, 0x0),
    gsSP1Triangle(13, 15, 11, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603A848, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 13, 0x0,  9, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603B038 - 0x0603B208
const Gfx bowser_seg6_dl_0603B038[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06029C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603A928, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  4,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 9, 12, 11, 0x0,  1, 13,  2, 0x0),
    gsSP1Triangle( 1, 14, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603AA18, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 12,  9, 0x0),
    gsSP1Triangle( 2, 13,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603AAF8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(13, 14, 11, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603ABE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603ACE8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 2,  5,  0, 0x0,  0,  4,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603ADD8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603B208 - 0x0603B288
const Gfx bowser_seg6_dl_0603B208[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603AED8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603B038),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603B288
static const Vtx bowser_seg6_vertex_0603B288[] = {
    {{{    73,    -38,     22}, 0, {  1154,   1256}, {0x24, 0x8f, 0x2b, 0xff}}},
    {{{   111,    -17,     22}, 0, {  1858,    808}, {0x5a, 0xc9, 0x45, 0xff}}},
    {{{    78,    -18,     41}, 0, {  1218,    868}, {0x19, 0xd6, 0x75, 0xff}}},
    {{{    88,     20,     41}, 0, {  1346,     90}, {0x30, 0x1d, 0x71, 0xff}}},
    {{{   114,     19,     22}, 0, {  1848,     80}, {0x70, 0x2c, 0x25, 0xff}}},
    {{{    93,     39,     22}, 0, {  1410,   -298}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{    24,     32,     41}, 0, {    82,    -92}, {0xf4, 0x2c, 0x76, 0xff}}},
    {{{   111,    -17,    -15}, 0, {     8,    -66}, {0x62, 0xc3, 0xce, 0xff}}},
    {{{   111,    -17,     22}, 0, {     0,    902}, {0x5a, 0xc9, 0x45, 0xff}}},
    {{{    73,    -38,     22}, 0, {  1206,    878}, {0x24, 0x8f, 0x2b, 0xff}}},
    {{{    73,    -38,    -15}, 0, {  1214,    -92}, {0x20, 0x96, 0xc4, 0xff}}},
    {{{    93,     39,    -15}, 0, {  1430,   1364}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{   114,     19,    -15}, 0, {  1976,    994}, {0x69, 0x2a, 0xc8, 0xff}}},
    {{{    88,     20,    -34}, 0, {  1400,    940}, {0x30, 0x1d, 0x8f, 0xff}}},
    {{{   111,    -17,    -15}, 0, {  2070,    212}, {0x62, 0xc3, 0xce, 0xff}}},
    {{{    78,    -18,    -34}, 0, {  1340,     88}, {0x19, 0xd6, 0x8b, 0xff}}},
};

// 0x0603B388
static const Vtx bowser_seg6_vertex_0603B388[] = {
    {{{    78,    -18,    -34}, 0, {  1340,     88}, {0x19, 0xd6, 0x8b, 0xff}}},
    {{{   111,    -17,    -15}, 0, {  2070,    212}, {0x62, 0xc3, 0xce, 0xff}}},
    {{{    73,    -38,    -15}, 0, {  1310,   -336}, {0x20, 0x96, 0xc4, 0xff}}},
    {{{    93,     39,     22}, 0, {   226,    912}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{   114,     19,     22}, 0, {   938,    934}, {0x70, 0x2c, 0x25, 0xff}}},
    {{{    93,     39,    -15}, 0, {   216,     30}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{   114,     19,    -15}, 0, {   928,     52}, {0x69, 0x2a, 0xc8, 0xff}}},
    {{{    30,     59,    -15}, 0, {   -54,   1610}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    24,     32,    -34}, 0, {   -74,   1020}, {0xf4, 0x2c, 0x8a, 0xff}}},
    {{{    -7,     38,    -15}, 0, {  -812,   1062}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    12,    -21,    -34}, 0, {  -118,   -158}, {0xe2, 0xde, 0x8a, 0xff}}},
    {{{   -21,    -23,    -15}, 0, {  -848,   -282}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{    88,     20,    -34}, 0, {  1400,    940}, {0x30, 0x1d, 0x8f, 0xff}}},
    {{{    93,     39,    -15}, 0, {  1430,   1364}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{     6,    -49,    -15}, 0, {  -140,   -748}, {0xdc, 0x8e, 0xd7, 0xff}}},
};

// 0x0603B478
static const Vtx bowser_seg6_vertex_0603B478[] = {
    {{{     6,    -49,     22}, 0, {    42,    882}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{   -21,    -23,     22}, 0, {   970,    894}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{     6,    -49,    -15}, 0, {    42,     36}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{   -21,    -23,    -15}, 0, {   968,     48}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{    12,    -21,     41}, 0, {   -60,    990}, {0xe2, 0xde, 0x76, 0xff}}},
    {{{    78,    -18,     41}, 0, {  1218,    868}, {0x19, 0xd6, 0x75, 0xff}}},
    {{{    24,     32,     41}, 0, {    82,    -92}, {0xf4, 0x2c, 0x76, 0xff}}},
    {{{    73,    -38,     22}, 0, {  1154,   1256}, {0x24, 0x8f, 0x2b, 0xff}}},
    {{{   -21,    -23,     22}, 0, {  -700,   1048}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{     6,    -49,     22}, 0, {  -132,   1528}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{    -7,     38,     22}, 0, {  -548,   -184}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    30,     59,     22}, 0, {   154,   -632}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    93,     39,     22}, 0, {  1410,   -298}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{    30,     59,    -15}, 0, {   948,     80}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    -7,     38,    -15}, 0, {    66,     58}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    30,     59,     22}, 0, {   936,    928}, {0xf3, 0x78, 0x27, 0xff}}},
};

// 0x0603B578
static const Vtx bowser_seg6_vertex_0603B578[] = {
    {{{    -7,     38,    -15}, 0, {    66,     58}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    -7,     38,     22}, 0, {    54,    906}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    30,     59,     22}, 0, {   936,    928}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{   -21,    -23,     22}, 0, {    24,    862}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{    -7,     38,     22}, 0, {  1368,    856}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    -7,     38,    -15}, 0, {  1334,     94}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    93,     39,    -15}, 0, {  1430,   1364}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{    88,     20,    -34}, 0, {  1400,    940}, {0x30, 0x1d, 0x8f, 0xff}}},
    {{{    24,     32,    -34}, 0, {   -74,   1020}, {0xf4, 0x2c, 0x8a, 0xff}}},
    {{{    93,     39,     22}, 0, {  1406,    880}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{    93,     39,    -15}, 0, {  1396,     92}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{    30,     59,     22}, 0, {    10,    892}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    30,     59,    -15}, 0, {     0,    104}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{     6,    -49,     22}, 0, {  1378,    818}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{     6,    -49,    -15}, 0, {  1370,      8}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{    73,    -38,     22}, 0, {   -68,    832}, {0x24, 0x8f, 0x2b, 0xff}}},
};

// 0x0603B678
static const Vtx bowser_seg6_vertex_0603B678[] = {
    {{{     6,    -49,    -15}, 0, {  1370,      8}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{    73,    -38,    -15}, 0, {   -76,     22}, {0x20, 0x96, 0xc4, 0xff}}},
    {{{    73,    -38,     22}, 0, {   -68,    832}, {0x24, 0x8f, 0x2b, 0xff}}},
    {{{   -21,    -23,    -15}, 0, {   -10,    100}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{   -21,    -23,     22}, 0, {    24,    862}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{    -7,     38,    -15}, 0, {  1334,     94}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{   111,    -17,    -15}, 0, {   948,     58}, {0x62, 0xc3, 0xce, 0xff}}},
    {{{   114,     19,    -15}, 0, {   124,     38}, {0x69, 0x2a, 0xc8, 0xff}}},
    {{{   114,     19,     22}, 0, {   102,    858}, {0x70, 0x2c, 0x25, 0xff}}},
    {{{   111,    -17,     22}, 0, {   926,    878}, {0x5a, 0xc9, 0x45, 0xff}}},
};

// 0x0603B718 - 0x0603B8D0
const Gfx bowser_seg6_dl_0603B718[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603B288, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  5,  3,  4, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  2,  3,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  9, 10,  7, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 13, 12, 14, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603B388, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11,  9, 0x0,  9,  8, 10, 0x0),
    gsSP2Triangles( 0, 10,  8, 0x0,  8, 12,  0, 0x0),
    gsSP2Triangles(10,  0,  2, 0x0,  8,  7, 13, 0x0),
    gsSP2Triangles(14, 11, 10, 0x0,  2, 14, 10, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603B478, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 4,  8,  9, 0x0,  4,  9,  7, 0x0),
    gsSP2Triangles(10,  8,  4, 0x0,  4,  6, 10, 0x0),
    gsSP2Triangles(11, 10,  6, 0x0, 12, 11,  6, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603B578, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10, 12, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603B678, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  6,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603B8D0 - 0x0603B948
const Gfx bowser_seg6_dl_0603B8D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603B718),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

#if BUGFIX_BOWSER_FADING_OUT
// 0x0603B948 - 0x0603B9C8
const Gfx bowser_seg6_dl_0603B948[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603B718),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetAlphaCompare(G_AC_NONE),
    gsDPSetRenderMode(G_RM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
#endif

// 0x0603B948
static const Vtx bowser_seg6_vertex_0603B948[] = {
    {{{    36,     44,    -26}, 0, {   954,    920}, {0x2c, 0x5f, 0xb9, 0xff}}},
    {{{    46,     36,    -21}, 0, {   866,     54}, {0x3a, 0x59, 0xbc, 0xff}}},
    {{{    42,     -6,    -35}, 0, {    86,     88}, {0x2b, 0xe5, 0x8d, 0xff}}},
    {{{    12,     43,     26}, 0, {   576,    994}, {0xb0, 0x5c, 0x22, 0xff}}},
    {{{    12,     43,    -24}, 0, {   988,    408}, {0xba, 0x57, 0xc6, 0xff}}},
    {{{     8,     -4,    -40}, 0, {   654,    -20}, {0xa2, 0xef, 0xae, 0xff}}},
    {{{     8,     -4,     42}, 0, {   -12,    932}, {0xa9, 0x01, 0x5b, 0xff}}},
    {{{     5,    -34,      0}, 0, {    38,    304}, {0xb9, 0x9b, 0x19, 0xff}}},
    {{{    36,     44,    -26}, 0, {    -8,    932}, {0x2c, 0x5f, 0xb9, 0xff}}},
    {{{    36,     44,     28}, 0, {   948,    992}, {0x2a, 0x4c, 0x5c, 0xff}}},
    {{{    46,     36,     23}, 0, {   886,     80}, {0x3b, 0x67, 0x2c, 0xff}}},
    {{{    46,     36,    -21}, 0, {   106,     30}, {0x3a, 0x59, 0xbc, 0xff}}},
    {{{    32,     -8,    -43}, 0, {    -4,    962}, {0x2a, 0xcb, 0x95, 0xff}}},
    {{{    32,     -8,     45}, 0, {   948,    900}, {0x1d, 0xbe, 0x67, 0xff}}},
    {{{    42,     -6,     37}, 0, {   872,     70}, {0x2b, 0xe3, 0x73, 0xff}}},
    {{{    36,     44,     28}, 0, {   -10,    860}, {0x2a, 0x4c, 0x5c, 0xff}}},
};

// 0x0603BA48
static const Vtx bowser_seg6_vertex_0603BA48[] = {
    {{{    42,     -6,     37}, 0, {   872,     70}, {0x2b, 0xe3, 0x73, 0xff}}},
    {{{    46,     36,     23}, 0, {    90,     38}, {0x3b, 0x67, 0x2c, 0xff}}},
    {{{    36,     44,     28}, 0, {   -10,    860}, {0x2a, 0x4c, 0x5c, 0xff}}},
    {{{    29,    -40,      0}, 0, {   966,    922}, {0x0a, 0x84, 0xec, 0xff}}},
    {{{    40,    -33,      0}, 0, {   872,     54}, {0x35, 0x8d, 0xff, 0xff}}},
    {{{    32,     -8,     45}, 0, {     2,    900}, {0x1d, 0xbe, 0x67, 0xff}}},
    {{{    42,     -6,     37}, 0, {    86,     38}, {0x2b, 0xe3, 0x73, 0xff}}},
    {{{    32,     -8,    -43}, 0, {   964,    910}, {0x2a, 0xcb, 0x95, 0xff}}},
    {{{    42,     -6,    -35}, 0, {   864,     50}, {0x2b, 0xe5, 0x8d, 0xff}}},
    {{{    40,    -33,      0}, 0, {    62,     10}, {0x35, 0x8d, 0xff, 0xff}}},
    {{{    29,    -40,      0}, 0, {   -20,    864}, {0x0a, 0x84, 0xec, 0xff}}},
};

// 0x0603BAF8
static const Vtx bowser_seg6_vertex_0603BAF8[] = {
    {{{    42,     -6,     37}, 0, {   -24,    954}, {0x2b, 0xe3, 0x73, 0xff}}},
    {{{    63,     -7,     35}, 0, {   590,    956}, {0x4a, 0xda, 0x5f, 0xff}}},
    {{{    66,     32,     22}, 0, {   588,     14}, {0x48, 0x45, 0x4d, 0xff}}},
    {{{    66,     32,    -20}, 0, {   620,    948}, {0x41, 0x56, 0xbe, 0xff}}},
    {{{    63,     -7,    -33}, 0, {   606,     52}, {0x49, 0xdb, 0xa0, 0xff}}},
    {{{    42,     -6,    -35}, 0, {     0,     -2}, {0x2b, 0xe5, 0x8d, 0xff}}},
    {{{    46,     36,    -21}, 0, {    14,    954}, {0x3a, 0x59, 0xbc, 0xff}}},
    {{{    46,     36,     23}, 0, {   -12,    958}, {0x3b, 0x67, 0x2c, 0xff}}},
    {{{    66,     32,     22}, 0, {   716,    956}, {0x48, 0x45, 0x4d, 0xff}}},
    {{{    66,     32,    -20}, 0, {   746,     44}, {0x41, 0x56, 0xbe, 0xff}}},
    {{{    46,     36,    -21}, 0, {    20,    -14}, {0x3a, 0x59, 0xbc, 0xff}}},
    {{{    60,    -32,      0}, 0, {   690,    954}, {0x2f, 0x8b, 0xff, 0xff}}},
    {{{    63,     -7,     35}, 0, {   668,     50}, {0x4a, 0xda, 0x5f, 0xff}}},
    {{{    42,     -6,     37}, 0, {   -26,    -10}, {0x2b, 0xe3, 0x73, 0xff}}},
    {{{    40,    -33,      0}, 0, {    -2,    954}, {0x35, 0x8d, 0xff, 0xff}}},
    {{{    46,     36,     23}, 0, {   -26,    -52}, {0x3b, 0x67, 0x2c, 0xff}}},
};

// 0x0603BBF8
static const Vtx bowser_seg6_vertex_0603BBF8[] = {
    {{{    42,     -6,    -35}, 0, {     0,    970}, {0x2b, 0xe5, 0x8d, 0xff}}},
    {{{    63,     -7,    -33}, 0, {   704,    974}, {0x49, 0xdb, 0xa0, 0xff}}},
    {{{    60,    -32,      0}, 0, {   718,     56}, {0x2f, 0x8b, 0xff, 0xff}}},
    {{{    40,    -33,      0}, 0, {    16,    -12}, {0x35, 0x8d, 0xff, 0xff}}},
    {{{    66,     32,    -20}, 0, {  1120,    246}, {0x41, 0x56, 0xbe, 0xff}}},
    {{{    81,      1,      0}, 0, {   508,    472}, {0x7e, 0xf5, 0xff, 0xff}}},
    {{{    63,     -7,    -33}, 0, {   926,    980}, {0x49, 0xdb, 0xa0, 0xff}}},
    {{{    60,    -32,      0}, 0, {   154,   1010}, {0x2f, 0x8b, 0xff, 0xff}}},
    {{{    66,     32,     22}, 0, {   468,   -176}, {0x48, 0x45, 0x4d, 0xff}}},
    {{{    63,     -7,     35}, 0, {  -128,    298}, {0x4a, 0xda, 0x5f, 0xff}}},
};

// 0x0603BC98
static const Vtx bowser_seg6_vertex_0603BC98[] = {
    {{{    36,     44,    -26}, 0, {     0,     18}, {0x2c, 0x5f, 0xb9, 0xff}}},
    {{{    12,     43,     26}, 0, {  1892,    958}, {0xb0, 0x5c, 0x22, 0xff}}},
    {{{    36,     44,     28}, 0, {  1968,     34}, {0x2a, 0x4c, 0x5c, 0xff}}},
    {{{    29,    -40,      0}, 0, {     0,     46}, {0x0a, 0x84, 0xec, 0xff}}},
    {{{     8,     -4,    -40}, 0, {  1928,    976}, {0xa2, 0xef, 0xae, 0xff}}},
    {{{    32,     -8,    -43}, 0, {  1976,     48}, {0x2a, 0xcb, 0x95, 0xff}}},
    {{{     5,    -34,      0}, 0, {    88,    974}, {0xb9, 0x9b, 0x19, 0xff}}},
    {{{    32,     -8,     45}, 0, {     0,     54}, {0x1d, 0xbe, 0x67, 0xff}}},
    {{{     5,    -34,      0}, 0, {  1908,    968}, {0xb9, 0x9b, 0x19, 0xff}}},
    {{{    29,    -40,      0}, 0, {  1992,     52}, {0x0a, 0x84, 0xec, 0xff}}},
    {{{     8,     -4,     42}, 0, {    48,    968}, {0xa9, 0x01, 0x5b, 0xff}}},
    {{{    36,     44,     28}, 0, {   -24,     40}, {0x2a, 0x4c, 0x5c, 0xff}}},
    {{{     8,     -4,     42}, 0, {  1900,    978}, {0xa9, 0x01, 0x5b, 0xff}}},
    {{{    32,     -8,     45}, 0, {  1992,     26}, {0x1d, 0xbe, 0x67, 0xff}}},
    {{{    12,     43,     26}, 0, {    52,    990}, {0xb0, 0x5c, 0x22, 0xff}}},
};

// 0x0603BD88
static const Vtx bowser_seg6_vertex_0603BD88[] = {
    {{{     8,     -4,    -40}, 0, {    56,    982}, {0xa2, 0xef, 0xae, 0xff}}},
    {{{    12,     43,    -24}, 0, {  1924,    976}, {0xba, 0x57, 0xc6, 0xff}}},
    {{{    36,     44,    -26}, 0, {  2016,     68}, {0x2c, 0x5f, 0xb9, 0xff}}},
    {{{    36,     44,    -26}, 0, {     0,     18}, {0x2c, 0x5f, 0xb9, 0xff}}},
    {{{    12,     43,    -24}, 0, {    56,    944}, {0xba, 0x57, 0xc6, 0xff}}},
    {{{    12,     43,     26}, 0, {  1892,    958}, {0xb0, 0x5c, 0x22, 0xff}}},
    {{{    32,     -8,    -43}, 0, {   -24,     76}, {0x2a, 0xcb, 0x95, 0xff}}},
};

// 0x0603BDF8 - 0x0603BEA0
const Gfx bowser_seg6_dl_0603BDF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06023C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603B948, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10, 11,  8, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603BA48, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 7,  9, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603BEA0 - 0x0603BF48
const Gfx bowser_seg6_dl_0603BEA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603BAF8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14, 11, 13, 0x0,  0,  2, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603BBF8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  9,  5,  8, 0x0),
    gsSP1Triangle( 7,  5,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603BF48 - 0x0603BFC8
const Gfx bowser_seg6_dl_0603BF48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603BC98, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603BD88, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603BFC8 - 0x0603C050
const Gfx bowser_seg6_dl_0603BFC8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603BDF8),
    gsSPDisplayList(bowser_seg6_dl_0603BEA0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603BF48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603C050
static const Vtx bowser_seg6_vertex_0603C050[] = {
    {{{    21,     31,    -19}, 0, {   874,     66}, {0xcb, 0x61, 0xc4, 0xff}}},
    {{{    21,     31,     21}, 0, {    96,    112}, {0xcf, 0x51, 0x54, 0xff}}},
    {{{    27,     37,     25}, 0, {     8,    920}, {0xcc, 0x53, 0x50, 0xff}}},
    {{{    49,    -32,      0}, 0, {   344,    990}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    47,     33,     22}, 0, {   370,    -36}, {0x5b, 0x51, 0x21, 0xff}}},
    {{{    48,     -7,     35}, 0, {   -28,    368}, {0x46, 0xfa, 0x69, 0xff}}},
    {{{    48,     -7,    -34}, 0, {   974,    970}, {0x44, 0xfb, 0x96, 0xff}}},
    {{{    47,     33,    -20}, 0, {   990,    336}, {0x68, 0x3b, 0xd8, 0xff}}},
    {{{    27,     37,     25}, 0, {   970,    880}, {0xcc, 0x53, 0x50, 0xff}}},
    {{{    22,     -7,     33}, 0, {    88,    154}, {0xc1, 0xe9, 0x6b, 0xff}}},
    {{{    29,     -9,     40}, 0, {     0,    918}, {0xde, 0xbf, 0x67, 0xff}}},
    {{{    21,     31,     21}, 0, {   886,    122}, {0xcf, 0x51, 0x54, 0xff}}},
    {{{    27,     37,    -23}, 0, {   954,    864}, {0xe1, 0x5c, 0xaf, 0xff}}},
    {{{    29,     -9,    -38}, 0, {   940,    880}, {0xdc, 0xc1, 0x99, 0xff}}},
    {{{    22,     -7,    -31}, 0, {   860,    134}, {0xc2, 0xeb, 0x94, 0xff}}},
    {{{    27,     37,    -23}, 0, {     8,    892}, {0xe1, 0x5c, 0xaf, 0xff}}},
};

// 0x0603C150
static const Vtx bowser_seg6_vertex_0603C150[] = {
    {{{    22,     -7,    -31}, 0, {   860,    134}, {0xc2, 0xeb, 0x94, 0xff}}},
    {{{    21,     31,    -19}, 0, {    92,    144}, {0xcb, 0x61, 0xc4, 0xff}}},
    {{{    27,     37,    -23}, 0, {     8,    892}, {0xe1, 0x5c, 0xaf, 0xff}}},
    {{{    23,    -31,      0}, 0, {   858,     80}, {0xcd, 0x8c, 0xff, 0xff}}},
    {{{    22,     -7,    -31}, 0, {    80,     66}, {0xc2, 0xeb, 0x94, 0xff}}},
    {{{    29,     -9,    -38}, 0, {    -2,    896}, {0xdc, 0xc1, 0x99, 0xff}}},
    {{{    30,    -38,      0}, 0, {   944,    914}, {0xdf, 0x86, 0xff, 0xff}}},
    {{{    29,     -9,     40}, 0, {   960,    908}, {0xde, 0xbf, 0x67, 0xff}}},
    {{{    22,     -7,     33}, 0, {   870,    102}, {0xc1, 0xe9, 0x6b, 0xff}}},
    {{{    23,    -31,      0}, 0, {    90,     96}, {0xcd, 0x8c, 0xff, 0xff}}},
    {{{    30,    -38,      0}, 0, {    12,    900}, {0xdf, 0x86, 0xff, 0xff}}},
};

// 0x0603C200
static const Vtx bowser_seg6_vertex_0603C200[] = {
    {{{    22,     -7,    -31}, 0, {   -16,    962}, {0xc2, 0xeb, 0x94, 0xff}}},
    {{{    -8,     -8,    -30}, 0, {    20,    -28}, {0xca, 0xdd, 0x94, 0xff}}},
    {{{    21,     31,    -19}, 0, {   980,    978}, {0xcb, 0x61, 0xc4, 0xff}}},
    {{{    22,     -7,     33}, 0, {   -38,    982}, {0xc1, 0xe9, 0x6b, 0xff}}},
    {{{    -8,     -8,     32}, 0, {     6,    -28}, {0xc9, 0xdb, 0x6b, 0xff}}},
    {{{    23,    -31,      0}, 0, {   976,    998}, {0xcd, 0x8c, 0xff, 0xff}}},
    {{{    -7,    -31,      0}, 0, {   992,    -10}, {0xb3, 0x9c, 0xff, 0xff}}},
    {{{    21,     31,     21}, 0, {     8,   1004}, {0xcf, 0x51, 0x54, 0xff}}},
    {{{    -8,     -8,     32}, 0, {   956,     18}, {0xc9, 0xdb, 0x6b, 0xff}}},
    {{{    22,     -7,     33}, 0, {   960,    984}, {0xc1, 0xe9, 0x6b, 0xff}}},
    {{{    -9,     29,     20}, 0, {    34,     40}, {0xba, 0x5f, 0x2d, 0xff}}},
    {{{    21,     31,    -19}, 0, {    -8,    966}, {0xcb, 0x61, 0xc4, 0xff}}},
    {{{    -9,     29,    -18}, 0, {    -4,     -8}, {0xad, 0x4b, 0xc7, 0xff}}},
    {{{    -9,     29,     20}, 0, {   970,     -6}, {0xba, 0x5f, 0x2d, 0xff}}},
    {{{    21,     31,     21}, 0, {   996,    968}, {0xcf, 0x51, 0x54, 0xff}}},
    {{{    -9,     29,    -18}, 0, {   984,    -12}, {0xad, 0x4b, 0xc7, 0xff}}},
};

// 0x0603C300
static const Vtx bowser_seg6_vertex_0603C300[] = {
    {{{    23,    -31,      0}, 0, {     0,    982}, {0xcd, 0x8c, 0xff, 0xff}}},
    {{{    -7,    -31,      0}, 0, {    48,     -4}, {0xb3, 0x9c, 0xff, 0xff}}},
    {{{    -8,     -8,    -30}, 0, {   980,    -12}, {0xca, 0xdd, 0x94, 0xff}}},
    {{{    22,     -7,    -31}, 0, {   960,    976}, {0xc2, 0xeb, 0x94, 0xff}}},
    {{{    -8,     -8,     32}, 0, {  -430,    -36}, {0xc9, 0xdb, 0x6b, 0xff}}},
    {{{   -20,      1,      0}, 0, {   504,    248}, {0x82, 0xfc, 0xff, 0xff}}},
    {{{    -7,    -31,      0}, 0, {   508,   -632}, {0xb3, 0x9c, 0xff, 0xff}}},
    {{{    -9,     29,     20}, 0, {   -78,    954}, {0xba, 0x5f, 0x2d, 0xff}}},
    {{{    -9,     29,    -18}, 0, {  1078,    970}, {0xad, 0x4b, 0xc7, 0xff}}},
    {{{    -8,     -8,    -30}, 0, {  1442,    -12}, {0xca, 0xdd, 0x94, 0xff}}},
};

// 0x0603C3A0
static const Vtx bowser_seg6_vertex_0603C3A0[] = {
    {{{    27,     37,     25}, 0, {     0,     60}, {0xcc, 0x53, 0x50, 0xff}}},
    {{{    47,     33,     22}, 0, {   100,    978}, {0x5b, 0x51, 0x21, 0xff}}},
    {{{    27,     37,    -23}, 0, {  1988,     34}, {0xe1, 0x5c, 0xaf, 0xff}}},
    {{{    49,    -32,      0}, 0, {   132,    968}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    48,     -7,     35}, 0, {  1872,    946}, {0x46, 0xfa, 0x69, 0xff}}},
    {{{    29,     -9,     40}, 0, {  1980,     50}, {0xde, 0xbf, 0x67, 0xff}}},
    {{{    30,    -38,      0}, 0, {     4,     76}, {0xdf, 0x86, 0xff, 0xff}}},
    {{{    29,     -9,    -38}, 0, {     8,     62}, {0xdc, 0xc1, 0x99, 0xff}}},
    {{{    49,    -32,      0}, 0, {  1876,    970}, {0x64, 0xb3, 0x00, 0xff}}},
    {{{    30,    -38,      0}, 0, {  1992,     72}, {0xdf, 0x86, 0xff, 0xff}}},
    {{{    48,     -7,    -34}, 0, {   132,    962}, {0x44, 0xfb, 0x96, 0xff}}},
    {{{    27,     37,    -23}, 0, {    -4,     78}, {0xe1, 0x5c, 0xaf, 0xff}}},
    {{{    48,     -7,    -34}, 0, {  1900,    950}, {0x44, 0xfb, 0x96, 0xff}}},
    {{{    29,     -9,    -38}, 0, {  2000,     68}, {0xdc, 0xc1, 0x99, 0xff}}},
    {{{    47,     33,    -20}, 0, {   132,    958}, {0x68, 0x3b, 0xd8, 0xff}}},
};

// 0x0603C490
static const Vtx bowser_seg6_vertex_0603C490[] = {
    {{{    48,     -7,     35}, 0, {   116,    936}, {0x46, 0xfa, 0x69, 0xff}}},
    {{{    47,     33,     22}, 0, {  1860,    958}, {0x5b, 0x51, 0x21, 0xff}}},
    {{{    27,     37,     25}, 0, {  1964,     82}, {0xcc, 0x53, 0x50, 0xff}}},
    {{{    47,     33,     22}, 0, {   100,    978}, {0x5b, 0x51, 0x21, 0xff}}},
    {{{    47,     33,    -20}, 0, {  1880,    956}, {0x68, 0x3b, 0xd8, 0xff}}},
    {{{    27,     37,    -23}, 0, {  1988,     34}, {0xe1, 0x5c, 0xaf, 0xff}}},
    {{{    29,     -9,     40}, 0, {   -20,     58}, {0xde, 0xbf, 0x67, 0xff}}},
};

// 0x0603C500 - 0x0603C5A8
const Gfx bowser_seg6_dl_0603C500[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06023C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603C050, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12,  0,  2, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603C150, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 9, 10,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603C5A8 - 0x0603C650
const Gfx bowser_seg6_dl_0603C5A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603C200, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  1, 15,  2, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603C300, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 8,  5,  7, 0x0,  6,  5,  9, 0x0),
    gsSP1Triangle( 9,  5,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603C650 - 0x0603C6D0
const Gfx bowser_seg6_dl_0603C650[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603C3A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603C490, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603C6D0 - 0x0603C758
const Gfx bowser_seg6_dl_0603C6D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603C500),
    gsSPDisplayList(bowser_seg6_dl_0603C5A8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603C650),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603C758
static const Vtx bowser_seg6_vertex_0603C758[] = {
    {{{    54,    -16,    -21}, 0, {   176,     66}, {0x0e, 0x84, 0xeb, 0xff}}},
    {{{    91,     -2,    -21}, 0, {  -372,     82}, {0x3a, 0x91, 0xf4, 0xff}}},
    {{{    91,     -2,     18}, 0, {  -390,    596}, {0x32, 0x8c, 0x05, 0xff}}},
    {{{   -15,     -5,    -21}, 0, {  1302,     36}, {0xdf, 0x87, 0xef, 0xff}}},
    {{{   -16,      3,    -41}, 0, {  1362,   -224}, {0xd8, 0xb0, 0xa7, 0xff}}},
    {{{    74,     -3,    -52}, 0, {  -102,   -336}, {0x10, 0xb7, 0x9a, 0xff}}},
    {{{    54,    -16,     18}, 0, {   156,    580}, {0x02, 0x83, 0x15, 0xff}}},
    {{{    74,     -3,     49}, 0, {  -150,    996}, {0x12, 0xa7, 0x58, 0xff}}},
    {{{   -16,      3,     38}, 0, {  1324,    806}, {0xdb, 0xbd, 0x64, 0xff}}},
    {{{   -15,     -5,     18}, 0, {  1284,    550}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{    56,     36,     18}, 0, {   780,    614}, {0x0e, 0x7e, 0x06, 0xff}}},
    {{{    56,     36,    -21}, 0, {   236,    594}, {0x0c, 0x7e, 0xf9, 0xff}}},
    {{{   -14,     40,     -1}, 0, {   582,   -466}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    76,     31,     49}, 0, {  1194,    932}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{    92,     29,     18}, 0, {   744,   1156}, {0x1c, 0x7b, 0xfb, 0xff}}},
};

// 0x0603C848
static const Vtx bowser_seg6_vertex_0603C848[] = {
    {{{   -15,     -5,     18}, 0, {  1284,    550}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{   -45,      9,     18}, 0, {  1798,    536}, {0xa7, 0xc0, 0x3f, 0xff}}},
    {{{   -45,      9,    -21}, 0, {  1816,     22}, {0xa1, 0xb9, 0xd3, 0xff}}},
    {{{    56,     36,     18}, 0, {   780,    614}, {0x0e, 0x7e, 0x06, 0xff}}},
    {{{    92,     29,     18}, 0, {   744,   1156}, {0x1c, 0x7b, 0xfb, 0xff}}},
    {{{    92,     29,    -21}, 0, {   198,   1134}, {0x28, 0x78, 0x04, 0xff}}},
    {{{    76,     31,    -52}, 0, {  -216,    880}, {0x0e, 0x58, 0xa6, 0xff}}},
    {{{    56,     36,    -21}, 0, {   236,    594}, {0x0c, 0x7e, 0xf9, 0xff}}},
    {{{   -15,     35,    -41}, 0, {    36,   -478}, {0xe1, 0x47, 0x9c, 0xff}}},
    {{{   -14,     40,     -1}, 0, {   582,   -466}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    76,     31,     49}, 0, {    74,    748}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{   -15,     35,     38}, 0, {  1102,    770}, {0xdf, 0x53, 0x59, 0xff}}},
    {{{   -16,      3,     38}, 0, {  1110,    412}, {0xdb, 0xbd, 0x64, 0xff}}},
    {{{   -45,      9,     18}, 0, {  1426,    482}, {0xa7, 0xc0, 0x3f, 0xff}}},
    {{{   -51,     37,     18}, 0, {  1506,    786}, {0x98, 0x3c, 0x28, 0xff}}},
    {{{   -15,     -5,    -21}, 0, {  1302,     36}, {0xdf, 0x87, 0xef, 0xff}}},
};

// 0x0603C948
static const Vtx bowser_seg6_vertex_0603C948[] = {
    {{{   -15,     -5,    -21}, 0, {  1302,     36}, {0xdf, 0x87, 0xef, 0xff}}},
    {{{   -45,      9,    -21}, 0, {  1816,     22}, {0xa1, 0xb9, 0xd3, 0xff}}},
    {{{   -16,      3,    -41}, 0, {  1362,   -224}, {0xd8, 0xb0, 0xa7, 0xff}}},
    {{{   -15,     35,    -41}, 0, {  1110,    582}, {0xe1, 0x47, 0x9c, 0xff}}},
    {{{   -16,      3,    -41}, 0, {  1106,     96}, {0xd8, 0xb0, 0xa7, 0xff}}},
    {{{   -45,      9,    -21}, 0, {  1488,    198}, {0xa1, 0xb9, 0xd3, 0xff}}},
    {{{   -51,     37,    -21}, 0, {  1592,    612}, {0xb1, 0x55, 0xce, 0xff}}},
    {{{    76,     31,    -52}, 0, {  -118,    528}, {0x0e, 0x58, 0xa6, 0xff}}},
    {{{    74,     -3,    -52}, 0, {  -120,     -2}, {0x10, 0xb7, 0x9a, 0xff}}},
    {{{   -51,     37,    -21}, 0, {   346,  -1002}, {0xb1, 0x55, 0xce, 0xff}}},
    {{{   -51,     37,     18}, 0, {   890,   -982}, {0x98, 0x3c, 0x28, 0xff}}},
    {{{   -14,     40,     -1}, 0, {   582,   -466}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   -15,     35,    -41}, 0, {    36,   -478}, {0xe1, 0x47, 0x9c, 0xff}}},
    {{{   -51,     37,     18}, 0, {   154,    736}, {0x98, 0x3c, 0x28, 0xff}}},
    {{{   -51,     37,    -21}, 0, {   798,    726}, {0xb1, 0x55, 0xce, 0xff}}},
    {{{   -45,      9,    -21}, 0, {   810,    264}, {0xa1, 0xb9, 0xd3, 0xff}}},
};

// 0x0603CA48
static const Vtx bowser_seg6_vertex_0603CA48[] = {
    {{{   -51,     37,     18}, 0, {   890,   -982}, {0x98, 0x3c, 0x28, 0xff}}},
    {{{   -15,     35,     38}, 0, {  1126,   -436}, {0xdf, 0x53, 0x59, 0xff}}},
    {{{   -14,     40,     -1}, 0, {   582,   -466}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    56,     36,     18}, 0, {   780,    614}, {0x0e, 0x7e, 0x06, 0xff}}},
    {{{   -16,      3,     38}, 0, {  1324,    806}, {0xdb, 0xbd, 0x64, 0xff}}},
    {{{   -45,      9,     18}, 0, {  1798,    536}, {0xa7, 0xc0, 0x3f, 0xff}}},
    {{{   -15,     -5,     18}, 0, {  1284,    550}, {0xd8, 0x89, 0x11, 0xff}}},
    {{{    76,     31,     49}, 0, {  1194,    932}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{    76,     31,     49}, 0, {    74,    748}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{   -16,      3,     38}, 0, {  1110,    412}, {0xdb, 0xbd, 0x64, 0xff}}},
    {{{    74,     -3,     49}, 0, {    82,    356}, {0x12, 0xa7, 0x58, 0xff}}},
    {{{   -51,     37,     18}, 0, {   154,    736}, {0x98, 0x3c, 0x28, 0xff}}},
    {{{   -45,      9,    -21}, 0, {   810,    264}, {0xa1, 0xb9, 0xd3, 0xff}}},
    {{{   -45,      9,     18}, 0, {   166,    272}, {0xa7, 0xc0, 0x3f, 0xff}}},
};

// 0x0603CB28
static const Vtx bowser_seg6_vertex_0603CB28[] = {
    {{{   100,      5,     45}, 0, {  -170,   1304}, {0x3c, 0xbc, 0x58, 0xff}}},
    {{{    98,     31,     45}, 0, {   648,   1308}, {0x15, 0x6e, 0x3b, 0xff}}},
    {{{    76,     31,     49}, 0, {   690,    956}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{   107,      6,     21}, 0, {   732,   1260}, {0x3c, 0xc5, 0xa2, 0xff}}},
    {{{    91,     -2,     18}, 0, {   790,    824}, {0x32, 0x8c, 0x05, 0xff}}},
    {{{    92,     29,     18}, 0, {     6,    988}, {0x1c, 0x7b, 0xfb, 0xff}}},
    {{{   105,     29,     20}, 0, {   140,   1292}, {0x10, 0x54, 0xa3, 0xff}}},
    {{{   132,     31,     27}, 0, {   380,   1988}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   100,      5,     45}, 0, {   234,   1344}, {0x3c, 0xbc, 0x58, 0xff}}},
    {{{    74,     -3,     49}, 0, {    -4,    872}, {0x12, 0xa7, 0x58, 0xff}}},
    {{{    91,     -2,     18}, 0, {   844,    892}, {0x32, 0x8c, 0x05, 0xff}}},
    {{{   107,      6,     21}, 0, {   858,   1264}, {0x3c, 0xc5, 0xa2, 0xff}}},
    {{{   132,     31,     27}, 0, {   826,   1940}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   132,     31,     27}, 0, {   644,   1912}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{    74,     -3,     49}, 0, {  -438,    868}, {0x12, 0xa7, 0x58, 0xff}}},
};

// 0x0603CC18
static const Vtx bowser_seg6_vertex_0603CC18[] = {
    {{{    92,     29,     18}, 0, {   950,    936}, {0x1c, 0x7b, 0xfb, 0xff}}},
    {{{    76,     31,     49}, 0, {   136,    892}, {0x07, 0x4b, 0x66, 0xff}}},
    {{{    98,     31,     45}, 0, {   264,   1324}, {0x15, 0x6e, 0x3b, 0xff}}},
    {{{   132,     31,     27}, 0, {   752,   1896}, {0x7c, 0x1b, 0x00, 0xff}}},
    {{{   105,     29,     20}, 0, {   904,   1228}, {0x10, 0x54, 0xa3, 0xff}}},
    {{{    95,      6,    -48}, 0, {   282,   1236}, {0x3e, 0xc3, 0xa4, 0xff}}},
    {{{    74,     -3,    -52}, 0, {   148,    824}, {0x10, 0xb7, 0x9a, 0xff}}},
    {{{    76,     31,    -52}, 0, {   982,    972}, {0x0e, 0x58, 0xa6, 0xff}}},
    {{{    93,     31,    -48}, 0, {   902,   1284}, {0x1b, 0x6e, 0xc9, 0xff}}},
    {{{   126,     31,    -31}, 0, {   776,   1940}, {0x7a, 0x20, 0xfb, 0xff}}},
    {{{    92,     29,    -21}, 0, {   -30,    928}, {0x28, 0x78, 0x04, 0xff}}},
    {{{   106,      2,    -15}, 0, {   780,   1176}, {0x44, 0xb7, 0xb3, 0xff}}},
    {{{    91,     -2,    -21}, 0, {   800,    788}, {0x3a, 0x91, 0xf4, 0xff}}},
    {{{   104,     30,    -15}, 0, {    26,   1220}, {0x16, 0x6b, 0xc1, 0xff}}},
};

// 0x0603CCF8
static const Vtx bowser_seg6_vertex_0603CCF8[] = {
    {{{   106,      6,    -25}, 0, {   648,   1280}, {0x47, 0xc6, 0x57, 0xff}}},
    {{{   104,     27,    -24}, 0, {     0,   1252}, {0x1a, 0x51, 0x5e, 0xff}}},
    {{{    92,     29,    -21}, 0, {  -186,    936}, {0x28, 0x78, 0x04, 0xff}}},
    {{{   104,     30,    -15}, 0, {    26,   1220}, {0x16, 0x6b, 0xc1, 0xff}}},
    {{{   136,     31,     -1}, 0, {   270,   1988}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{   106,      2,    -15}, 0, {   780,   1176}, {0x44, 0xb7, 0xb3, 0xff}}},
    {{{    74,     -3,    -52}, 0, {   984,    832}, {0x10, 0xb7, 0x9a, 0xff}}},
    {{{    95,      6,    -48}, 0, {   818,   1248}, {0x3e, 0xc3, 0xa4, 0xff}}},
    {{{    91,     -2,    -21}, 0, {    26,    892}, {0x3a, 0x91, 0xf4, 0xff}}},
    {{{   126,     31,    -31}, 0, {   292,   1908}, {0x7a, 0x20, 0xfb, 0xff}}},
    {{{   106,      6,    -25}, 0, {   120,   1280}, {0x47, 0xc6, 0x57, 0xff}}},
    {{{    91,     -2,    -21}, 0, {   770,    836}, {0x3a, 0x91, 0xf4, 0xff}}},
    {{{   126,     31,    -31}, 0, {    80,   1864}, {0x7a, 0x20, 0xfb, 0xff}}},
    {{{    91,     -2,     18}, 0, {   602,    744}, {0x32, 0x8c, 0x05, 0xff}}},
    {{{   106,      2,     12}, 0, {   660,   1164}, {0x46, 0xca, 0x5a, 0xff}}},
    {{{    92,     29,     18}, 0, {    16,    796}, {0x1c, 0x7b, 0xfb, 0xff}}},
};

// 0x0603CDF8
static const Vtx bowser_seg6_vertex_0603CDF8[] = {
    {{{    92,     29,    -21}, 0, {   192,    920}, {0x28, 0x78, 0x04, 0xff}}},
    {{{    93,     31,    -48}, 0, {   950,   1280}, {0x1b, 0x6e, 0xc9, 0xff}}},
    {{{    76,     31,    -52}, 0, {  1096,    924}, {0x0e, 0x58, 0xa6, 0xff}}},
    {{{   104,     27,    -24}, 0, {   274,   1248}, {0x1a, 0x51, 0x5e, 0xff}}},
    {{{   126,     31,    -31}, 0, {   420,   1864}, {0x7a, 0x20, 0xfb, 0xff}}},
    {{{   106,      2,    -15}, 0, {    30,   1148}, {0x44, 0xb7, 0xb3, 0xff}}},
    {{{   136,     31,     -1}, 0, {   422,   1932}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{   106,      2,     12}, 0, {   762,   1152}, {0x46, 0xca, 0x5a, 0xff}}},
    {{{    91,     -2,     18}, 0, {   898,    800}, {0x32, 0x8c, 0x05, 0xff}}},
    {{{    91,     -2,    -21}, 0, {  -126,    792}, {0x3a, 0x91, 0xf4, 0xff}}},
    {{{   106,      2,     12}, 0, {   660,   1164}, {0x46, 0xca, 0x5a, 0xff}}},
    {{{   136,     31,     -1}, 0, {   436,   1988}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{   104,     30,     12}, 0, {   112,   1120}, {0x1f, 0x5c, 0x51, 0xff}}},
    {{{    92,     29,     18}, 0, {    16,    796}, {0x1c, 0x7b, 0xfb, 0xff}}},
};

// 0x0603CED8
static const Vtx bowser_seg6_vertex_0603CED8[] = {
    {{{   104,     30,     12}, 0, {    80,   1152}, {0x1f, 0x5c, 0x51, 0xff}}},
    {{{   136,     31,     -1}, 0, {   518,   1904}, {0x7b, 0x1c, 0x00, 0xff}}},
    {{{   104,     30,    -15}, 0, {   964,   1216}, {0x16, 0x6b, 0xc1, 0xff}}},
    {{{    92,     29,     18}, 0, {   -82,    868}, {0x1c, 0x7b, 0xfb, 0xff}}},
    {{{    92,     29,    -21}, 0, {  1132,    956}, {0x28, 0x78, 0x04, 0xff}}},
};

// 0x0603CF28 - 0x0603D098
const Gfx bowser_seg6_dl_0603CF28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603C758, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 5,  0,  4, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  3,  0,  6, 0x0),
    gsSP2Triangles( 6,  2,  7, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 8,  6,  7, 0x0,  6,  9,  3, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 10, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603C848, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  3,  5,  7, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0,  8,  7,  6, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 12, 11, 0x0),
    gsSP2Triangles(11, 14, 13, 0x0,  0,  2, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603C948, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 3,  8,  4, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  9, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603CA48, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  3,  1, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603D098 - 0x0603D200
const Gfx bowser_seg6_dl_0603D098[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06029C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603CB28, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  6,  7,  3, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10, 11,  8, 0x0),
    gsSP2Triangles(11, 12,  8, 0x0,  0, 13,  1, 0x0),
    gsSP1Triangle( 2, 14,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603CC18, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  8,  9,  5, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603CCF8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11,  0,  2, 0x0),
    gsSP2Triangles( 0, 12,  1, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603CDF8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  7,  8, 0x0,  9,  5,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603CED8, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP1Triangle( 3,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603D200 - 0x0603D280
const Gfx bowser_seg6_dl_0603D200[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603CF28),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603D098),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603D280
static const Vtx bowser_seg6_vertex_0603D280[] = {
    {{{    37,    -25,     12}, 0, {    68,    548}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    20,    -23,     23}, 0, {   512,    850}, {0x2f, 0x8d, 0x17, 0xff}}},
    {{{     0,    -23,     12}, 0, {  1034,    558}, {0xac, 0xa2, 0x06, 0xff}}},
    {{{    -2,     20,     16}, 0, {  1170,    662}, {0xa4, 0x3b, 0x3f, 0xff}}},
    {{{    -2,     19,    -14}, 0, {  1092,   -220}, {0x88, 0x21, 0xe8, 0xff}}},
    {{{     0,    -23,     12}, 0, {   -98,    626}, {0xac, 0xa2, 0x06, 0xff}}},
    {{{    -1,      8,    -19}, 0, {   770,   -354}, {0xd9, 0x15, 0x8a, 0xff}}},
    {{{     0,     -6,    -19}, 0, {   342,   -330}, {0xc6, 0xe2, 0x94, 0xff}}},
    {{{     0,    -18,    -14}, 0, {    20,   -160}, {0x9b, 0xc7, 0xce, 0xff}}},
    {{{     0,    -17,     31}, 0, {    98,   1182}, {0xb9, 0xd5, 0x5f, 0xff}}},
    {{{    22,    -26,    -14}, 0, {   416,   -192}, {0xf8, 0x94, 0xbf, 0xff}}},
    {{{     0,    -18,    -14}, 0, {  1012,   -164}, {0x9b, 0xc7, 0xce, 0xff}}},
    {{{     0,    -17,     31}, 0, {  1044,   1120}, {0xb9, 0xd5, 0x5f, 0xff}}},
    {{{    37,    -25,     12}, 0, {  1146,    194}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    36,     38,     13}, 0, {  -166,    126}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    15,     37,     14}, 0, {  -180,    554}, {0xda, 0x4e, 0x5b, 0xff}}},
};

// 0x0603D380
static const Vtx bowser_seg6_vertex_0603D380[] = {
    {{{    22,    -26,    -14}, 0, {   416,   -192}, {0xf8, 0x94, 0xbf, 0xff}}},
    {{{    51,    -20,    -13}, 0, {  -310,   -174}, {0x2b, 0xa4, 0xb5, 0xff}}},
    {{{    37,    -25,     12}, 0, {    68,    548}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    36,     38,     13}, 0, {   942,    912}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    36,     35,    -13}, 0, {   940,    264}, {0x08, 0x61, 0xb0, 0xff}}},
    {{{    16,     35,    -14}, 0, {   318,    266}, {0xe1, 0x67, 0xbd, 0xff}}},
    {{{    15,     37,     14}, 0, {   318,    958}, {0xda, 0x4e, 0x5b, 0xff}}},
    {{{    -2,     19,    -14}, 0, {  -224,    264}, {0x88, 0x21, 0xe8, 0xff}}},
    {{{    13,     -3,     14}, 0, {   660,    640}, {0x59, 0x47, 0x36, 0xff}}},
    {{{    37,    -25,     12}, 0, {  1146,    194}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    15,     37,     14}, 0, {  -180,    554}, {0xda, 0x4e, 0x5b, 0xff}}},
    {{{    50,     20,      3}, 0, {   214,   -140}, {0x25, 0xea, 0x77, 0xff}}},
    {{{    36,     38,     13}, 0, {  -166,    126}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    54,      0,      3}, 0, {   644,   -212}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    51,    -23,     13}, 0, {  1118,    -90}, {0x15, 0xa1, 0x51, 0xff}}},
};

// 0x0603D470
static const Vtx bowser_seg6_vertex_0603D470[] = {
    {{{    14,    -12,     36}, 0, {   690,   1252}, {0x00, 0xcd, 0x73, 0xff}}},
    {{{     0,    -17,     31}, 0, {  1044,   1120}, {0xb9, 0xd5, 0x5f, 0xff}}},
    {{{    20,    -23,     23}, 0, {   512,    850}, {0x2f, 0x8d, 0x17, 0xff}}},
    {{{    13,     -3,     14}, 0, {   660,    640}, {0x59, 0x47, 0x36, 0xff}}},
    {{{    15,     37,     14}, 0, {  -180,    554}, {0xda, 0x4e, 0x5b, 0xff}}},
    {{{    -2,     20,     16}, 0, {   152,    948}, {0xa4, 0x3b, 0x3f, 0xff}}},
    {{{    -1,      8,    -19}, 0, {   872,   1410}, {0xd9, 0x15, 0x8a, 0xff}}},
    {{{    50,     20,    -23}, 0, {   774,   -118}, {0x3b, 0x32, 0x9c, 0xff}}},
    {{{    55,      0,    -23}, 0, {    -6,   -178}, {0x37, 0xf3, 0x8f, 0xff}}},
    {{{     0,     -6,    -19}, 0, {   334,   1440}, {0xc6, 0xe2, 0x94, 0xff}}},
    {{{    16,     35,    -14}, 0, {  1642,    820}, {0xe1, 0x67, 0xbd, 0xff}}},
    {{{    36,     35,    -13}, 0, {  1432,    226}, {0x08, 0x61, 0xb0, 0xff}}},
    {{{    -2,     19,    -14}, 0, {  1268,   1400}, {0x88, 0x21, 0xe8, 0xff}}},
    {{{    -2,     19,    -14}, 0, {  -224,    264}, {0x88, 0x21, 0xe8, 0xff}}},
    {{{    -2,     20,     16}, 0, {  -220,   1000}, {0xa4, 0x3b, 0x3f, 0xff}}},
    {{{    15,     37,     14}, 0, {   318,    958}, {0xda, 0x4e, 0x5b, 0xff}}},
};

// 0x0603D570
static const Vtx bowser_seg6_vertex_0603D570[] = {
    {{{    22,    -26,    -14}, 0, {  -598,    868}, {0xf8, 0x94, 0xbf, 0xff}}},
    {{{     0,    -18,    -14}, 0, {   -70,   1476}, {0x9b, 0xc7, 0xce, 0xff}}},
    {{{     0,     -6,    -19}, 0, {   334,   1440}, {0xc6, 0xe2, 0x94, 0xff}}},
    {{{    51,    -20,    -13}, 0, {  -682,     34}, {0x2b, 0xa4, 0xb5, 0xff}}},
    {{{    55,      0,    -23}, 0, {    -6,   -178}, {0x37, 0xf3, 0x8f, 0xff}}},
    {{{    51,    -20,    -13}, 0, {  -310,   -174}, {0x2b, 0xa4, 0xb5, 0xff}}},
    {{{    51,    -23,     13}, 0, {  -286,    556}, {0x15, 0xa1, 0x51, 0xff}}},
    {{{    37,    -25,     12}, 0, {    68,    548}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    20,    -23,     23}, 0, {  1080,    544}, {0x2f, 0x8d, 0x17, 0xff}}},
    {{{    37,    -25,     12}, 0, {  1146,    194}, {0x22, 0xc5, 0x6a, 0xff}}},
    {{{    13,     -3,     14}, 0, {   660,    640}, {0x59, 0x47, 0x36, 0xff}}},
    {{{     0,    -17,     31}, 0, {   928,    950}, {0xb9, 0xd5, 0x5f, 0xff}}},
    {{{    15,      2,     33}, 0, {   538,    628}, {0x08, 0x61, 0x51, 0xff}}},
    {{{    -2,     20,     16}, 0, {   152,    948}, {0xa4, 0x3b, 0x3f, 0xff}}},
};

// 0x0603D650
static const Vtx bowser_seg6_vertex_0603D650[] = {
    {{{    -2,     20,     16}, 0, {   128,    620}, {0xa4, 0x3b, 0x3f, 0xff}}},
    {{{    15,      2,     33}, 0, {   734,    816}, {0x08, 0x61, 0x51, 0xff}}},
    {{{    13,     -3,     14}, 0, {   724,    238}, {0x59, 0x47, 0x36, 0xff}}},
};

// 0x0603D680
static const Vtx bowser_seg6_vertex_0603D680[] = {
    {{{    70,      3,     -5}, 0, {   786,   1400}, {0x55, 0xa8, 0xe1, 0xff}}},
    {{{    65,      2,      6}, 0, {   280,   1324}, {0xf3, 0x9f, 0x4f, 0xff}}},
    {{{    54,      0,      3}, 0, {   154,    904}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    78,      9,     19}, 0, {   202,   1984}, {0x66, 0x03, 0x4a, 0xff}}},
    {{{    55,      0,    -23}, 0, {  1018,    680}, {0x37, 0xf3, 0x8f, 0xff}}},
    {{{    28,     -4,     22}, 0, {   252,   1228}, {0x38, 0x3a, 0x9f, 0xff}}},
    {{{    13,     -3,     14}, 0, {   224,    532}, {0x59, 0x47, 0x36, 0xff}}},
    {{{    15,      2,     33}, 0, {  -134,    756}, {0x08, 0x61, 0x51, 0xff}}},
    {{{    29,      0,     33}, 0, {    32,   1364}, {0x1e, 0x6b, 0x3b, 0xff}}},
    {{{    44,     -6,     32}, 0, {   292,   1956}, {0x75, 0x16, 0x2b, 0xff}}},
    {{{    20,    -23,     23}, 0, {   748,    776}, {0x2f, 0x8d, 0x17, 0xff}}},
    {{{    32,    -16,     27}, 0, {   572,   1376}, {0x43, 0x96, 0x0e, 0xff}}},
    {{{    32,    -16,     27}, 0, {   692,   1304}, {0x43, 0x96, 0x0e, 0xff}}},
    {{{    44,     -6,     32}, 0, {   388,   1700}, {0x75, 0x16, 0x2b, 0xff}}},
    {{{    29,     -9,     35}, 0, {   298,   1300}, {0x13, 0xeb, 0x7b, 0xff}}},
};

// 0x0603D770
static const Vtx bowser_seg6_vertex_0603D770[] = {
    {{{    14,    -12,     36}, 0, {   248,    912}, {0x00, 0xcd, 0x73, 0xff}}},
    {{{    20,    -23,     23}, 0, {   894,    916}, {0x2f, 0x8d, 0x17, 0xff}}},
    {{{    32,    -16,     27}, 0, {   692,   1304}, {0x43, 0x96, 0x0e, 0xff}}},
    {{{    29,     -9,     35}, 0, {   298,   1300}, {0x13, 0xeb, 0x7b, 0xff}}},
    {{{    50,     20,    -23}, 0, {   908,    648}, {0x3b, 0x32, 0x9c, 0xff}}},
    {{{    36,     35,    -13}, 0, {    84,    748}, {0x08, 0x61, 0xb0, 0xff}}},
    {{{    56,     35,     -2}, 0, {   136,   1444}, {0x44, 0x68, 0xe7, 0xff}}},
    {{{    62,     16,      7}, 0, {   210,   1340}, {0xe7, 0x37, 0x6f, 0xff}}},
    {{{    78,      9,     19}, 0, {    94,   1948}, {0x66, 0x03, 0x4a, 0xff}}},
    {{{    67,     17,     -5}, 0, {   728,   1360}, {0x52, 0x5a, 0xde, 0xff}}},
    {{{    60,     25,     -5}, 0, {   582,   1364}, {0x67, 0xba, 0xec, 0xff}}},
    {{{    50,     20,      3}, 0, {   746,   1120}, {0x25, 0xea, 0x77, 0xff}}},
    {{{    50,     20,    -23}, 0, {    54,    600}, {0x3b, 0x32, 0x9c, 0xff}}},
    {{{    60,     25,     -5}, 0, {   230,   1360}, {0x67, 0xba, 0xec, 0xff}}},
    {{{    62,     24,     16}, 0, {   724,   1916}, {0x65, 0xe1, 0x45, 0xff}}},
    {{{    54,     23,      8}, 0, {   734,   1392}, {0xf1, 0x8f, 0x37, 0xff}}},
};

// 0x0603D870
static const Vtx bowser_seg6_vertex_0603D870[] = {
    {{{    67,     17,     -5}, 0, {   728,   1360}, {0x52, 0x5a, 0xde, 0xff}}},
    {{{    50,     20,    -23}, 0, {  1014,    728}, {0x3b, 0x32, 0x9c, 0xff}}},
    {{{    50,     20,      3}, 0, {    92,    952}, {0x25, 0xea, 0x77, 0xff}}},
    {{{    62,     16,      7}, 0, {   210,   1340}, {0xe7, 0x37, 0x6f, 0xff}}},
    {{{    67,     17,     -5}, 0, {   184,   1364}, {0x52, 0x5a, 0xde, 0xff}}},
    {{{    78,      9,     19}, 0, {   558,   2016}, {0x66, 0x03, 0x4a, 0xff}}},
    {{{    70,      3,     -5}, 0, {   824,   1364}, {0x55, 0xa8, 0xe1, 0xff}}},
    {{{    55,      0,    -23}, 0, {   892,    760}, {0x37, 0xf3, 0x8f, 0xff}}},
    {{{    50,     20,    -23}, 0, {   -42,    740}, {0x3b, 0x32, 0x9c, 0xff}}},
    {{{    62,    -17,     14}, 0, {   716,   1304}, {0xf2, 0xe4, 0x7a, 0xff}}},
    {{{    78,     -8,     21}, 0, {   420,   1872}, {0x69, 0x10, 0x43, 0xff}}},
    {{{    63,     -2,      8}, 0, {    -8,   1300}, {0xee, 0x67, 0x47, 0xff}}},
    {{{    54,      0,      3}, 0, {  -176,    988}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    51,    -23,     13}, 0, {   986,    912}, {0x15, 0xa1, 0x51, 0xff}}},
};

// 0x0603D950
static const Vtx bowser_seg6_vertex_0603D950[] = {
    {{{    51,    -20,    -13}, 0, {   488,    664}, {0x2b, 0xa4, 0xb5, 0xff}}},
    {{{    55,      0,    -23}, 0, {  -392,    708}, {0x37, 0xf3, 0x8f, 0xff}}},
    {{{    69,     -2,     -5}, 0, {  -168,   1360}, {0x60, 0x42, 0xcf, 0xff}}},
    {{{    67,    -17,      0}, 0, {   450,   1308}, {0x4e, 0xa0, 0xe7, 0xff}}},
    {{{    54,      0,      3}, 0, {   944,    888}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    65,      2,      6}, 0, {   868,   1296}, {0xf3, 0x9f, 0x4f, 0xff}}},
    {{{    62,     16,      7}, 0, {   228,   1388}, {0xe7, 0x37, 0x6f, 0xff}}},
    {{{    15,      2,     33}, 0, {   -86,    960}, {0x08, 0x61, 0x51, 0xff}}},
    {{{    14,    -12,     36}, 0, {   566,    824}, {0x00, 0xcd, 0x73, 0xff}}},
    {{{    29,     -9,     35}, 0, {   524,   1364}, {0x13, 0xeb, 0x7b, 0xff}}},
    {{{    44,     -6,     32}, 0, {   434,   1924}, {0x75, 0x16, 0x2b, 0xff}}},
    {{{    29,      0,     33}, 0, {   126,   1448}, {0x1e, 0x6b, 0x3b, 0xff}}},
    {{{     0,    -17,     31}, 0, {   644,    292}, {0xb9, 0xd5, 0x5f, 0xff}}},
    {{{    54,      0,      3}, 0, {  -108,   1016}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    69,     -2,     -5}, 0, {   444,   1280}, {0x60, 0x42, 0xcf, 0xff}}},
    {{{    55,      0,    -23}, 0, {   626,    764}, {0x37, 0xf3, 0x8f, 0xff}}},
};

// 0x0603DA50
static const Vtx bowser_seg6_vertex_0603DA50[] = {
    {{{    54,      0,      3}, 0, {  -108,   1016}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    63,     -2,      8}, 0, {   -46,   1284}, {0xee, 0x67, 0x47, 0xff}}},
    {{{    69,     -2,     -5}, 0, {   444,   1280}, {0x60, 0x42, 0xcf, 0xff}}},
    {{{    78,     -8,     21}, 0, {   -86,   1792}, {0x69, 0x10, 0x43, 0xff}}},
    {{{    54,      0,      3}, 0, {   944,    888}, {0xf9, 0x18, 0x7c, 0xff}}},
    {{{    62,     16,      7}, 0, {   228,   1388}, {0xe7, 0x37, 0x6f, 0xff}}},
    {{{    50,     20,      3}, 0, {    10,    988}, {0x25, 0xea, 0x77, 0xff}}},
    {{{    56,     35,     -2}, 0, {   264,   1480}, {0x44, 0x68, 0xe7, 0xff}}},
    {{{    36,     35,    -13}, 0, {   148,    708}, {0x08, 0x61, 0xb0, 0xff}}},
    {{{    36,     38,     13}, 0, {   966,    956}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    65,      2,      6}, 0, {   868,   1296}, {0xf3, 0x9f, 0x4f, 0xff}}},
    {{{    78,      9,     19}, 0, {   624,   1972}, {0x66, 0x03, 0x4a, 0xff}}},
    {{{    50,     20,      3}, 0, {   798,   1040}, {0x25, 0xea, 0x77, 0xff}}},
    {{{    48,     36,     13}, 0, {   136,   1296}, {0xec, 0x09, 0x7c, 0xff}}},
    {{{    36,     38,     13}, 0, {  -106,    888}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    54,     23,      8}, 0, {   704,   1320}, {0xf1, 0x8f, 0x37, 0xff}}},
};

// 0x0603DB50
static const Vtx bowser_seg6_vertex_0603DB50[] = {
    {{{    54,     23,      8}, 0, {   704,   1320}, {0xf1, 0x8f, 0x37, 0xff}}},
    {{{    62,     24,     16}, 0, {   664,   1856}, {0x65, 0xe1, 0x45, 0xff}}},
    {{{    48,     36,     13}, 0, {   136,   1296}, {0xec, 0x09, 0x7c, 0xff}}},
    {{{    48,     36,     13}, 0, {   820,   1356}, {0xec, 0x09, 0x7c, 0xff}}},
    {{{    62,     24,     16}, 0, {   742,   1968}, {0x65, 0xe1, 0x45, 0xff}}},
    {{{    56,     35,     -2}, 0, {   264,   1480}, {0x44, 0x68, 0xe7, 0xff}}},
    {{{    36,     38,     13}, 0, {   966,    956}, {0x15, 0x66, 0x47, 0xff}}},
    {{{    56,     35,     -2}, 0, {   144,   1364}, {0x44, 0x68, 0xe7, 0xff}}},
    {{{    62,     24,     16}, 0, {   300,   1992}, {0x65, 0xe1, 0x45, 0xff}}},
    {{{    60,     25,     -5}, 0, {   590,   1284}, {0x67, 0xba, 0xec, 0xff}}},
    {{{    51,    -23,     13}, 0, {   738,    896}, {0x15, 0xa1, 0x51, 0xff}}},
    {{{    51,    -20,    -13}, 0, {  -174,    668}, {0x2b, 0xa4, 0xb5, 0xff}}},
    {{{    67,    -17,      0}, 0, {   -16,   1256}, {0x4e, 0xa0, 0xe7, 0xff}}},
    {{{    78,     -8,     21}, 0, {   484,   1908}, {0x69, 0x10, 0x43, 0xff}}},
    {{{    62,    -17,     14}, 0, {   546,   1304}, {0xf2, 0xe4, 0x7a, 0xff}}},
};

// 0x0603DC40
static const Vtx bowser_seg6_vertex_0603DC40[] = {
    {{{    69,     -2,     -5}, 0, {  -156,   1308}, {0x60, 0x42, 0xcf, 0xff}}},
    {{{    78,     -8,     21}, 0, {   324,   1996}, {0x69, 0x10, 0x43, 0xff}}},
    {{{    67,    -17,      0}, 0, {   464,   1256}, {0x4e, 0xa0, 0xe7, 0xff}}},
};

// 0x0603DC70 - 0x0603DDD0
const Gfx bowser_seg6_dl_0603DC70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603D280, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 4,  8,  5, 0x0,  5,  9,  3, 0x0),
    gsSP2Triangles( 0,  2, 10, 0x0,  2, 11, 10, 0x0),
    gsSP2Triangles( 1, 12,  2, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D380, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 12, 0x0),
    gsSP2Triangles( 9, 13, 11, 0x0,  9, 14, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D470, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 6, 10, 11, 0x0,  6, 11,  7, 0x0),
    gsSP2Triangles( 6, 12, 10, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D570, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  3,  2, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D650, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603DDD0 - 0x0603DFB0
const Gfx bowser_seg6_dl_0603DDD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06029C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603D680, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9,  5, 0x0, 10,  6,  5, 0x0),
    gsSP2Triangles( 9, 11,  5, 0x0,  5, 11, 10, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0, 12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D770, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 6, 10,  4, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(13, 14, 15, 0x0, 13, 15, 11, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D870, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  4, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13,  9, 0x0,  9, 11, 12, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603D950, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0,  9, 11,  7, 0x0),
    gsSP2Triangles(12,  8,  7, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603DA50, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11,  5, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(12, 15, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603DB50, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(12, 14, 10, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603DC40, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603DFB0 - 0x0603E030
const Gfx bowser_seg6_dl_0603DFB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603DC70),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603DDD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603E030
static const Vtx bowser_seg6_vertex_0603E030[] = {
    {{{    30,     59,    -21}, 0, {   -60,    -38}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    30,     59,     16}, 0, {   -84,    948}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    93,     39,    -21}, 0, {  1694,    -14}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{   114,     19,    -21}, 0, {    72,     64}, {0x72, 0x1f, 0xd4, 0xff}}},
    {{{   111,    -17,     16}, 0, {   870,    890}, {0x62, 0xc3, 0x32, 0xff}}},
    {{{   111,    -17,    -21}, 0, {   894,     50}, {0x5a, 0xc9, 0xbb, 0xff}}},
    {{{   114,     19,     16}, 0, {    50,    906}, {0x68, 0x37, 0x2c, 0xff}}},
    {{{    -7,     38,     16}, 0, {  1478,    918}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    -7,     38,    -21}, 0, {  1460,     24}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{   -21,    -23,     16}, 0, {   -22,    938}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{   -21,    -23,    -21}, 0, {   -40,     44}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{    73,    -38,    -21}, 0, {  -288,     14}, {0x26, 0x93, 0xce, 0xff}}},
    {{{    73,    -38,     16}, 0, {  -268,    982}, {0x1e, 0x90, 0x32, 0xff}}},
    {{{     6,    -49,    -21}, 0, {  1464,     14}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{     6,    -49,     16}, 0, {  1484,    980}, {0xdf, 0x97, 0x3e, 0xff}}},
};

// 0x0603E120
static const Vtx bowser_seg6_vertex_0603E120[] = {
    {{{    -7,     38,     16}, 0, {  -590,   -436}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    24,     32,     35}, 0, {   206,   -406}, {0xf4, 0x2c, 0x76, 0xff}}},
    {{{    30,     59,     16}, 0, {   264,  -1120}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    30,     59,     16}, 0, {   -84,    948}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    93,     39,     16}, 0, {  1670,    972}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{    93,     39,    -21}, 0, {  1694,    -14}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{    73,    -38,     16}, 0, {  1632,   1200}, {0x1e, 0x90, 0x32, 0xff}}},
    {{{   111,    -17,     16}, 0, {  2488,    518}, {0x62, 0xc3, 0x32, 0xff}}},
    {{{    78,    -18,     35}, 0, {  1688,    686}, {0x19, 0xd6, 0x75, 0xff}}},
    {{{    12,    -21,     35}, 0, {    90,   1020}, {0xe2, 0xde, 0x76, 0xff}}},
    {{{    88,     20,     35}, 0, {  1804,   -344}, {0x30, 0x1d, 0x71, 0xff}}},
    {{{   114,     19,     16}, 0, {  2434,   -426}, {0x68, 0x37, 0x2c, 0xff}}},
    {{{    93,     39,     16}, 0, {  1860,   -860}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{    93,     39,    -21}, 0, {   112,    942}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{    88,     20,    -40}, 0, {   774,    944}, {0x30, 0x1d, 0x8f, 0xff}}},
    {{{    24,     32,    -40}, 0, {   750,   -682}, {0xf4, 0x2c, 0x8a, 0xff}}},
};

// 0x0603E220
static const Vtx bowser_seg6_vertex_0603E220[] = {
    {{{    24,     32,    -40}, 0, {   -40,    824}, {0xf4, 0x2c, 0x8a, 0xff}}},
    {{{    -7,     38,    -21}, 0, {  -748,    892}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    30,     59,    -21}, 0, {    42,   1454}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    78,    -18,    -40}, 0, {  1222,   -226}, {0x19, 0xd6, 0x8b, 0xff}}},
    {{{    12,    -21,    -40}, 0, {  -208,   -442}, {0xe2, 0xde, 0x8a, 0xff}}},
    {{{    88,     20,    -40}, 0, {  1372,    686}, {0x30, 0x1d, 0x8f, 0xff}}},
    {{{   114,     19,    -21}, 0, {  1934,    726}, {0x72, 0x1f, 0xd4, 0xff}}},
    {{{    93,     39,    -21}, 0, {  1446,   1140}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{   111,    -17,    -21}, 0, {  1938,   -116}, {0x5a, 0xc9, 0xbb, 0xff}}},
    {{{    73,    -38,    -21}, 0, {  1148,   -680}, {0x26, 0x93, 0xce, 0xff}}},
    {{{    24,     32,    -40}, 0, {   750,   -682}, {0xf4, 0x2c, 0x8a, 0xff}}},
    {{{    30,     59,    -21}, 0, {   -44,   -700}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    93,     39,    -21}, 0, {   112,    942}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{    12,    -21,     35}, 0, {    90,   1020}, {0xe2, 0xde, 0x76, 0xff}}},
    {{{     6,    -49,     16}, 0, {    32,   1734}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{    73,    -38,     16}, 0, {  1632,   1200}, {0x1e, 0x90, 0x32, 0xff}}},
};

// 0x0603E320
static const Vtx bowser_seg6_vertex_0603E320[] = {
    {{{    12,    -21,     35}, 0, {    90,   1020}, {0xe2, 0xde, 0x76, 0xff}}},
    {{{   -21,    -23,     16}, 0, {  -706,   1188}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{     6,    -49,     16}, 0, {    32,   1734}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{    -7,     38,     16}, 0, {  -590,   -436}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    12,    -21,    -40}, 0, {  -208,   -442}, {0xe2, 0xde, 0x8a, 0xff}}},
    {{{   -21,    -23,    -21}, 0, {  -924,   -550}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{    -7,     38,    -21}, 0, {  -748,    892}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    73,    -38,    -21}, 0, {  1148,   -680}, {0x26, 0x93, 0xce, 0xff}}},
    {{{     6,    -49,    -21}, 0, {  -292,  -1074}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{    30,     59,    -21}, 0, {   988,    114}, {0xf3, 0x6f, 0xc6, 0xff}}},
    {{{    -7,     38,    -21}, 0, {    58,    106}, {0xa2, 0x42, 0xcb, 0xff}}},
    {{{    30,     59,     16}, 0, {   954,    930}, {0xf3, 0x78, 0x27, 0xff}}},
    {{{    -7,     38,     16}, 0, {    24,    922}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{   111,    -17,     16}, 0, {   -34,    922}, {0x62, 0xc3, 0x32, 0xff}}},
    {{{    73,    -38,     16}, 0, {  1048,    922}, {0x1e, 0x90, 0x32, 0xff}}},
    {{{    73,    -38,    -21}, 0, {  1022,    -28}, {0x26, 0x93, 0xce, 0xff}}},
};

// 0x0603E420
static const Vtx bowser_seg6_vertex_0603E420[] = {
    {{{     6,    -49,     16}, 0, {    -8,    940}, {0xdf, 0x97, 0x3e, 0xff}}},
    {{{   -21,    -23,     16}, 0, {   952,    976}, {0x8f, 0xdd, 0x2b, 0xff}}},
    {{{     6,    -49,    -21}, 0, {    -2,    -54}, {0xdc, 0x8e, 0xd7, 0xff}}},
    {{{   -21,    -23,    -21}, 0, {   958,    -20}, {0x99, 0xe0, 0xbf, 0xff}}},
    {{{    12,    -21,     35}, 0, {  -208,   -442}, {0xe2, 0xde, 0x76, 0xff}}},
    {{{    24,     32,     35}, 0, {   -40,    824}, {0xf4, 0x2c, 0x76, 0xff}}},
    {{{    -7,     38,     16}, 0, {  -748,    892}, {0xab, 0x3a, 0x4a, 0xff}}},
    {{{    93,     39,     16}, 0, {    88,    880}, {0x33, 0x61, 0x3e, 0xff}}},
    {{{   114,     19,     16}, 0, {   874,    902}, {0x68, 0x37, 0x2c, 0xff}}},
    {{{    93,     39,    -21}, 0, {   102,      8}, {0x38, 0x68, 0xd3, 0xff}}},
    {{{   114,     19,    -21}, 0, {   886,     30}, {0x72, 0x1f, 0xd4, 0xff}}},
    {{{    73,    -38,    -21}, 0, {  1022,    -28}, {0x26, 0x93, 0xce, 0xff}}},
    {{{   111,    -17,    -21}, 0, {   -62,    -28}, {0x5a, 0xc9, 0xbb, 0xff}}},
    {{{   111,    -17,     16}, 0, {   -34,    922}, {0x62, 0xc3, 0x32, 0xff}}},
};

// 0x0603E500 - 0x0603E6B8
const Gfx bowser_seg6_dl_0603E500[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603E030, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E120, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  1,  9,  8, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  8, 10,  1, 0x0),
    gsSP2Triangles( 8,  7, 10, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles( 1, 10, 12, 0x0,  7, 11, 10, 0x0),
    gsSP2Triangles(12,  2,  1, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E220, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  1,  0,  4, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  5,  6,  8, 0x0),
    gsSP2Triangles( 5,  8,  3, 0x0,  3,  8,  9, 0x0),
    gsSP2Triangles( 4,  3,  9, 0x0, 10, 11, 12, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E320, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  4, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10, 12, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E420, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603E6B8 - 0x0603E718
const Gfx bowser_seg6_dl_0603E6B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603E500),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0603E718
static const Vtx bowser_seg6_vertex_0603E718[] = {
    {{{    67,    104,    167}, 0, {   898,    782}, {0x5c, 0x12, 0x55, 0xff}}},
    {{{    82,    124,    131}, 0, {  1002,    500}, {0x5e, 0x40, 0x37, 0xff}}},
    {{{    57,    174,     99}, 0, {   856,    102}, {0x42, 0x64, 0x27, 0xff}}},
    {{{    40,     85,    189}, 0, {   720,    982}, {0x1d, 0x0d, 0x7a, 0xff}}},
    {{{   -56,    174,     99}, 0, {   120,    106}, {0xbe, 0x64, 0x27, 0xff}}},
    {{{   -81,    124,    131}, 0, {   -50,    504}, {0xa2, 0x40, 0x37, 0xff}}},
    {{{   -66,    104,    167}, 0, {    34,    784}, {0xa4, 0x12, 0x55, 0xff}}},
    {{{   -39,     85,    189}, 0, {   200,    984}, {0xe3, 0x0d, 0x7a, 0xff}}},
    {{{     0,    185,     86}, 0, {   492,    -16}, {0x00, 0x7c, 0x17, 0xff}}},
};

// 0x0603E7A8
static const Vtx bowser_seg6_vertex_0603E7A8[] = {
    {{{   -84,    120,     92}, 0, {   956,     26}, {0xab, 0x5d, 0x0d, 0xff}}},
    {{{  -137,     92,     80}, 0, {  -314,     18}, {0x99, 0x48, 0x06, 0xff}}},
    {{{  -150,     79,    114}, 0, {  -680,    932}, {0x9b, 0x33, 0x39, 0xff}}},
    {{{   151,     79,    114}, 0, {   -42,    800}, {0x65, 0x33, 0x39, 0xff}}},
    {{{   159,     21,     94}, 0, {  1578,    980}, {0x74, 0xf9, 0x31, 0xff}}},
    {{{   142,     56,     59}, 0, {  1040,    -42}, {0x79, 0x23, 0xfa, 0xff}}},
    {{{   138,     92,     80}, 0, {   -50,    -18}, {0x67, 0x48, 0x06, 0xff}}},
    {{{    82,    124,    131}, 0, {  -502,    864}, {0x5e, 0x40, 0x37, 0xff}}},
    {{{   151,     79,    114}, 0, {  1730,    840}, {0x65, 0x33, 0x39, 0xff}}},
    {{{    85,    120,     92}, 0, {  -196,    -56}, {0x55, 0x5d, 0x0d, 0xff}}},
    {{{   138,     92,     80}, 0, {  1410,    -86}, {0x67, 0x48, 0x06, 0xff}}},
    {{{  -154,     28,      6}, 0, {  -114,      8}, {0x8a, 0x28, 0xec, 0xff}}},
    {{{  -158,     21,     94}, 0, {  1274,    976}, {0x8a, 0xfa, 0x2d, 0xff}}},
    {{{  -141,     56,     59}, 0, {  1182,     -8}, {0x86, 0x20, 0xf6, 0xff}}},
    {{{  -176,    -19,     11}, 0, {  -714,    966}, {0x87, 0xde, 0xf7, 0xff}}},
    {{{   -81,    124,    131}, 0, {  1086,    926}, {0xa2, 0x40, 0x37, 0xff}}},
};

// 0x0603E8A8
static const Vtx bowser_seg6_vertex_0603E8A8[] = {
    {{{  -150,     79,    114}, 0, {  1076,    786}, {0x9b, 0x33, 0x39, 0xff}}},
    {{{  -141,     56,     59}, 0, {    94,     -6}, {0x86, 0x20, 0xf6, 0xff}}},
    {{{  -158,     21,     94}, 0, {  -250,    960}, {0x8a, 0xfa, 0x2d, 0xff}}},
    {{{  -137,     92,     80}, 0, {  1004,     14}, {0x99, 0x48, 0x06, 0xff}}},
    {{{   162,     30,      4}, 0, {   -64,      0}, {0x77, 0x26, 0xec, 0xff}}},
    {{{   177,    -19,     11}, 0, {   210,    976}, {0x77, 0xd7, 0xf7, 0xff}}},
    {{{   141,      2,    -36}, 0, {  1076,    -22}, {0x69, 0x1b, 0xbf, 0xff}}},
    {{{   142,     56,     59}, 0, {  -262,    -46}, {0x79, 0x23, 0xfa, 0xff}}},
    {{{   159,     21,     94}, 0, {  -426,    980}, {0x74, 0xf9, 0x31, 0xff}}},
    {{{   162,     30,      4}, 0, {  1088,    -28}, {0x77, 0x26, 0xec, 0xff}}},
    {{{   177,    -19,     11}, 0, {  1540,    954}, {0x77, 0xd7, 0xf7, 0xff}}},
    {{{   -84,    120,     92}, 0, {   116,     -2}, {0xab, 0x5d, 0x0d, 0xff}}},
    {{{   -56,    174,     99}, 0, {  1552,    948}, {0xbe, 0x64, 0x27, 0xff}}},
    {{{   -54,    160,     54}, 0, {  1858,     10}, {0xc6, 0x70, 0xf4, 0xff}}},
};

// 0x0603E988
static const Vtx bowser_seg6_vertex_0603E988[] = {
    {{{   177,    -19,     11}, 0, { -1734,    990}, {0x77, 0xd7, 0xf7, 0xff}}},
    {{{   129,    -50,    -86}, 0, {  1348,    990}, {0x57, 0xc1, 0xbe, 0xff}}},
    {{{   141,      2,    -36}, 0, {  -354,    -74}, {0x69, 0x1b, 0xbf, 0xff}}},
    {{{   111,    -16,    -99}, 0, {  1630,    -90}, {0x50, 0x16, 0xa1, 0xff}}},
    {{{  -140,      2,    -36}, 0, {   982,     10}, {0x95, 0x1e, 0xc4, 0xff}}},
    {{{  -128,    -50,    -86}, 0, {  -290,    928}, {0xa9, 0xc1, 0xbe, 0xff}}},
    {{{  -176,    -19,     11}, 0, {  1970,    930}, {0x87, 0xde, 0xf7, 0xff}}},
    {{{  -110,    -16,    -99}, 0, {  -474,     -6}, {0xb0, 0x16, 0xa1, 0xff}}},
    {{{  -176,    -19,     11}, 0, {   652,    954}, {0x87, 0xde, 0xf7, 0xff}}},
    {{{  -154,     28,      6}, 0, {  1022,     -6}, {0x8a, 0x28, 0xec, 0xff}}},
    {{{  -140,      2,    -36}, 0, {  -278,     -8}, {0x95, 0x1e, 0xc4, 0xff}}},
    {{{   -84,    120,     92}, 0, {   116,     -2}, {0xab, 0x5d, 0x0d, 0xff}}},
    {{{   -81,    124,    131}, 0, {  -234,    712}, {0xa2, 0x40, 0x37, 0xff}}},
    {{{   -56,    174,     99}, 0, {  1552,    948}, {0xbe, 0x64, 0x27, 0xff}}},
};

// 0x0603EA68
static const Vtx bowser_seg6_vertex_0603EA68[] = {
    {{{     0,    185,     86}, 0, {  1042,    808}, {0x00, 0x7c, 0x17, 0xff}}},
    {{{    57,    174,     99}, 0, {  2130,   1006}, {0x42, 0x64, 0x27, 0xff}}},
    {{{    55,    160,     54}, 0, {  2158,     24}, {0x39, 0x70, 0xf4, 0xff}}},
    {{{   -56,    174,     99}, 0, {   -68,    982}, {0xbe, 0x64, 0x27, 0xff}}},
    {{{   -54,    160,     54}, 0, {    30,     -2}, {0xc6, 0x70, 0xf4, 0xff}}},
    {{{     0,    177,     49}, 0, {  1092,     24}, {0x00, 0x7d, 0xec, 0xff}}},
    {{{    82,    124,    131}, 0, {  1360,    816}, {0x5e, 0x40, 0x37, 0xff}}},
    {{{    85,    120,     92}, 0, {  1258,     54}, {0x55, 0x5d, 0x0d, 0xff}}},
    {{{    57,    174,     99}, 0, {  -188,    916}, {0x42, 0x64, 0x27, 0xff}}},
    {{{    55,    160,     54}, 0, {  -174,    -40}, {0x39, 0x70, 0xf4, 0xff}}},
    {{{  -110,    -16,    -99}, 0, {  3194,    -10}, {0xb0, 0x16, 0xa1, 0xff}}},
    {{{     0,    -21,   -146}, 0, {  -316,     -8}, {0x00, 0x17, 0x84, 0xff}}},
    {{{     0,    -48,   -166}, 0, {  -612,   1006}, {0x00, 0xe5, 0x84, 0xff}}},
    {{{  -128,    -50,    -86}, 0, {  3782,    774}, {0xa9, 0xc1, 0xbe, 0xff}}},
};

// 0x0603EB48
static const Vtx bowser_seg6_vertex_0603EB48[] = {
    {{{    97,     -2,    124}, 0, {  2126,  -2680}, {0x2e, 0xd7, 0x6e, 0xff}}},
    {{{    49,     58,    158}, 0, {  1130,  -4916}, {0x39, 0xc9, 0x62, 0xff}}},
    {{{     0,     -8,    133}, 0, {   136,  -2926}, {0x00, 0xbc, 0x6a, 0xff}}},
    {{{   105,    -44,     89}, 0, {  2278,   -676}, {0x2f, 0xa5, 0x4a, 0xff}}},
    {{{     0,     51,    166}, 0, {   136,  -5118}, {0x00, 0xb4, 0x65, 0xff}}},
    {{{   -48,     58,    158}, 0, {  -858,  -4916}, {0xc6, 0xca, 0x62, 0xff}}},
    {{{   -96,     -2,    124}, 0, { -1854,  -2680}, {0xd2, 0xd7, 0x6e, 0xff}}},
    {{{  -104,    -44,     89}, 0, { -2006,   -676}, {0xd1, 0xa5, 0x4a, 0xff}}},
    {{{     0,    -58,     95}, 0, {   136,   -692}, {0x00, 0x95, 0x43, 0xff}}},
    {{{   -82,    -80,      5}, 0, { -1564,   3032}, {0xda, 0x88, 0x0b, 0xff}}},
    {{{    83,    -80,      5}, 0, {  1836,   3032}, {0x26, 0x88, 0x0b, 0xff}}},
    {{{     0,    -95,      7}, 0, {   136,   3224}, {0x00, 0x82, 0x0a, 0xff}}},
    {{{     0,    -73,    -86}, 0, {   136,   6386}, {0x00, 0x86, 0xe0, 0xff}}},
};

// 0x0603EC18
static const Vtx bowser_seg6_vertex_0603EC18[] = {
    {{{    82,    124,    131}, 0, {   -88,    208}, {0x5e, 0x40, 0x37, 0xff}}},
    {{{    81,     91,    138}, 0, {   -96,    706}, {0x4e, 0xf9, 0x63, 0xff}}},
    {{{   151,     79,    114}, 0, {   972,    906}, {0x65, 0x33, 0x39, 0xff}}},
    {{{   -82,    -80,      5}, 0, {  -892,    504}, {0xda, 0x88, 0x0b, 0xff}}},
    {{{  -104,    -44,     89}, 0, { -1294,   -916}, {0xd1, 0xa5, 0x4a, 0xff}}},
    {{{  -176,    -19,     11}, 0, { -2512,    142}, {0x87, 0xde, 0xf7, 0xff}}},
    {{{  -128,    -50,    -86}, 0, { -1658,   1780}, {0xa9, 0xc1, 0xbe, 0xff}}},
    {{{     0,    -73,    -86}, 0, {   568,   1940}, {0x00, 0x86, 0xe0, 0xff}}},
    {{{     0,    -48,   -166}, 0, {   592,   3078}, {0x00, 0xe5, 0x84, 0xff}}},
    {{{   129,    -50,    -86}, 0, {  2790,   1932}, {0x57, 0xc1, 0xbe, 0xff}}},
    {{{    83,    -80,      5}, 0, {  1968,    604}, {0x26, 0x88, 0x0b, 0xff}}},
    {{{   177,    -19,     11}, 0, {  3576,    352}, {0x77, 0xd7, 0xf7, 0xff}}},
    {{{   105,    -44,     89}, 0, {  2310,   -792}, {0x2f, 0xa5, 0x4a, 0xff}}},
    {{{   159,     21,     94}, 0, {  1158,    350}, {0x74, 0xf9, 0x31, 0xff}}},
    {{{    97,     -2,    124}, 0, {   660,   1042}, {0x2e, 0xd7, 0x6e, 0xff}}},
    {{{   105,    -44,     89}, 0, {  1324,   1476}, {0x2f, 0xa5, 0x4a, 0xff}}},
};

// 0x0603ED18
static const Vtx bowser_seg6_vertex_0603ED18[] = {
    {{{   -80,     91,    138}, 0, {   132,   -816}, {0xb2, 0xf9, 0x63, 0xff}}},
    {{{   -96,     -2,    124}, 0, {   792,    334}, {0xd2, 0xd7, 0x6e, 0xff}}},
    {{{   -48,     58,    158}, 0, {   822,   -908}, {0xc6, 0xca, 0x62, 0xff}}},
    {{{  -150,     79,    114}, 0, {  -584,     72}, {0x9b, 0x33, 0x39, 0xff}}},
    {{{  -158,     21,     94}, 0, {  -160,    840}, {0x8a, 0xfa, 0x2d, 0xff}}},
    {{{  -104,    -44,     89}, 0, {  1064,   1074}, {0xd1, 0xa5, 0x4a, 0xff}}},
    {{{  -150,     79,    114}, 0, {  -152,    620}, {0x9b, 0x33, 0x39, 0xff}}},
    {{{   -80,     91,    138}, 0, {  1164,    654}, {0xb2, 0xf9, 0x63, 0xff}}},
    {{{   -81,    124,    131}, 0, {  1086,    194}, {0xa2, 0x40, 0x37, 0xff}}},
    {{{   151,     79,    114}, 0, {   480,   -238}, {0x65, 0x33, 0x39, 0xff}}},
    {{{    97,     -2,    124}, 0, {   660,   1042}, {0x2e, 0xd7, 0x6e, 0xff}}},
    {{{   159,     21,     94}, 0, {  1158,    350}, {0x74, 0xf9, 0x31, 0xff}}},
    {{{    81,     91,    138}, 0, {  -354,    124}, {0x4e, 0xf9, 0x63, 0xff}}},
    {{{    49,     58,    158}, 0, {  -468,    710}, {0x39, 0xc9, 0x62, 0xff}}},
};

// 0x0603EDF8
static const Vtx bowser_seg6_vertex_0603EDF8[] = {
    {{{   159,     21,     94}, 0, {  3230,  -1080}, {0x74, 0xf9, 0x31, 0xff}}},
    {{{   105,    -44,     89}, 0, {  2310,   -792}, {0x2f, 0xa5, 0x4a, 0xff}}},
    {{{   177,    -19,     11}, 0, {  3576,    352}, {0x77, 0xd7, 0xf7, 0xff}}},
    {{{  -176,    -19,     11}, 0, { -2512,    142}, {0x87, 0xde, 0xf7, 0xff}}},
    {{{  -104,    -44,     89}, 0, { -1294,   -916}, {0xd1, 0xa5, 0x4a, 0xff}}},
    {{{  -158,     21,     94}, 0, { -2228,  -1266}, {0x8a, 0xfa, 0x2d, 0xff}}},
};

// 0x0603EE58
static const Vtx bowser_seg6_vertex_0603EE58[] = {
    {{{     0,    162,    -77}, 0, {   708,    976}, {0x00, 0x73, 0xcb, 0xff}}},
    {{{     0,    177,     49}, 0, {   382,   -348}, {0x00, 0x7d, 0xec, 0xff}}},
    {{{    55,    160,     54}, 0, {  -346,   -108}, {0x39, 0x70, 0xf4, 0xff}}},
    {{{    69,     52,   -135}, 0, {     0,    990}, {0x3d, 0x22, 0x97, 0xff}}},
    {{{   -68,     52,   -135}, 0, {   990,    990}, {0xc3, 0x22, 0x97, 0xff}}},
    {{{     0,    162,    -77}, 0, {   500,   -438}, {0x00, 0x73, 0xcb, 0xff}}},
    {{{     0,    -21,   -146}, 0, {   464,    814}, {0x00, 0x17, 0x84, 0xff}}},
    {{{   -68,     52,   -135}, 0, {  1016,     82}, {0xc3, 0x22, 0x97, 0xff}}},
    {{{    69,     52,   -135}, 0, {   -36,     36}, {0x3d, 0x22, 0x97, 0xff}}},
    {{{     0,    162,    -77}, 0, {  1458,    536}, {0x00, 0x73, 0xcb, 0xff}}},
    {{{   133,    130,     -9}, 0, {  -384,    232}, {0x66, 0x49, 0xef, 0xff}}},
    {{{    69,     52,   -135}, 0, {   864,   1906}, {0x3d, 0x22, 0x97, 0xff}}},
    {{{   133,    130,     -9}, 0, { -1174,    954}, {0x66, 0x49, 0xef, 0xff}}},
    {{{  -154,     28,      6}, 0, {   406,    590}, {0x8a, 0x28, 0xec, 0xff}}},
    {{{  -141,     56,     59}, 0, {   600,   -250}, {0x86, 0x20, 0xf6, 0xff}}},
    {{{  -132,    130,     -9}, 0, {  1814,    410}, {0x99, 0x47, 0xf0, 0xff}}},
};

// 0x0603EF58
static const Vtx bowser_seg6_vertex_0603EF58[] = {
    {{{  -132,    130,     -9}, 0, {  1084,   -474}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{     0,    162,    -77}, 0, {  -408,   -586}, {0x00, 0x73, 0xcb, 0xff}}},
    {{{   -68,     52,   -135}, 0, {    -6,   1036}, {0xc3, 0x22, 0x97, 0xff}}},
    {{{  -132,    130,     -9}, 0, {  1362,   1002}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{   -54,    160,     54}, 0, {   640,   -158}, {0xc6, 0x70, 0xf4, 0xff}}},
    {{{     0,    162,    -77}, 0, {  -156,   1064}, {0x00, 0x73, 0xcb, 0xff}}},
    {{{     0,    177,     49}, 0, {    38,   -410}, {0x00, 0x7d, 0xec, 0xff}}},
    {{{  -110,    -16,    -99}, 0, {   606,   1968}, {0xb0, 0x16, 0xa1, 0xff}}},
    {{{  -140,      2,    -36}, 0, {  1264,   1534}, {0x95, 0x1e, 0xc4, 0xff}}},
    {{{   -68,     52,   -135}, 0, {   -42,   1046}, {0xc3, 0x22, 0x97, 0xff}}},
    {{{  -132,    130,     -9}, 0, {  1214,   -390}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{  -154,     28,      6}, 0, {   406,    590}, {0x8a, 0x28, 0xec, 0xff}}},
    {{{  -132,    130,     -9}, 0, {  1814,    410}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{  -140,      2,    -36}, 0, {   266,   1220}, {0x95, 0x1e, 0xc4, 0xff}}},
};

// 0x0603F038
static const Vtx bowser_seg6_vertex_0603F038[] = {
    {{{   138,     92,     80}, 0, {   -42,    -22}, {0x67, 0x48, 0x06, 0xff}}},
    {{{   133,    130,     -9}, 0, {   548,   1220}, {0x66, 0x49, 0xef, 0xff}}},
    {{{    85,    120,     92}, 0, {   702,   -190}, {0x55, 0x5d, 0x0d, 0xff}}},
    {{{  -141,     56,     59}, 0, {   600,   -250}, {0x86, 0x20, 0xf6, 0xff}}},
    {{{  -137,     92,     80}, 0, {   994,   -652}, {0x99, 0x48, 0x06, 0xff}}},
    {{{  -132,    130,     -9}, 0, {  1814,    410}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{   133,    130,     -9}, 0, {   174,   -870}, {0x66, 0x49, 0xef, 0xff}}},
    {{{   162,     30,      4}, 0, {  -568,     30}, {0x77, 0x26, 0xec, 0xff}}},
    {{{   141,      2,    -36}, 0, {  -254,    712}, {0x69, 0x1b, 0xbf, 0xff}}},
    {{{    69,     52,   -135}, 0, {  1164,   1146}, {0x3d, 0x22, 0x97, 0xff}}},
    {{{   111,    -16,    -99}, 0, {   322,   1498}, {0x50, 0x16, 0xa1, 0xff}}},
    {{{  -132,    130,     -9}, 0, {   916,    970}, {0x99, 0x47, 0xf0, 0xff}}},
    {{{   -84,    120,     92}, 0, {   872,   -552}, {0xab, 0x5d, 0x0d, 0xff}}},
    {{{   -54,    160,     54}, 0, {   182,   -192}, {0xc6, 0x70, 0xf4, 0xff}}},
    {{{  -137,     92,     80}, 0, {  1530,   -150}, {0x99, 0x48, 0x06, 0xff}}},
};

// 0x0603F128
static const Vtx bowser_seg6_vertex_0603F128[] = {
    {{{     0,    -21,   -146}, 0, {   -50,    888}, {0x00, 0x17, 0x84, 0xff}}},
    {{{  -110,    -16,    -99}, 0, {   954,    896}, {0xb0, 0x16, 0xa1, 0xff}}},
    {{{   -68,     52,   -135}, 0, {   536,    148}, {0xc3, 0x22, 0x97, 0xff}}},
    {{{    85,    120,     92}, 0, {   702,   -190}, {0x55, 0x5d, 0x0d, 0xff}}},
    {{{   133,    130,     -9}, 0, {   548,   1220}, {0x66, 0x49, 0xef, 0xff}}},
    {{{    55,    160,     54}, 0, {  1428,    366}, {0x39, 0x70, 0xf4, 0xff}}},
    {{{   142,     56,     59}, 0, {    62,    156}, {0x79, 0x23, 0xfa, 0xff}}},
    {{{   133,    130,     -9}, 0, {  1224,    192}, {0x66, 0x49, 0xef, 0xff}}},
    {{{   138,     92,     80}, 0, {    78,   -394}, {0x67, 0x48, 0x06, 0xff}}},
    {{{   162,     30,      4}, 0, {   450,    996}, {0x77, 0x26, 0xec, 0xff}}},
    {{{   111,    -16,    -99}, 0, {    36,    878}, {0x50, 0x16, 0xa1, 0xff}}},
    {{{     0,    -21,   -146}, 0, {   956,    862}, {0x00, 0x17, 0x84, 0xff}}},
    {{{    69,     52,   -135}, 0, {   430,     34}, {0x3d, 0x22, 0x97, 0xff}}},
};

// 0x0603F1F8
static const Vtx bowser_seg6_vertex_0603F1F8[] = {
    {{{  -106,     94,     78}, 0, {   124,   -380}, {0xfb, 0xf9, 0x7e, 0xff}}},
    {{{   -85,    113,     62}, 0, {  1042,   -372}, {0x54, 0x4b, 0x38, 0xff}}},
    {{{  -154,    176,     81}, 0, {   488,   2016}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{  -154,    176,     81}, 0, {   404,   2032}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{  -125,    101,     29}, 0, {   -76,   -380}, {0xa7, 0x19, 0xaa, 0xff}}},
    {{{  -131,     86,     58}, 0, {  1026,   -356}, {0x8e, 0xd9, 0x27, 0xff}}},
    {{{  -154,    176,     81}, 0, {   414,   2012}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   -97,    118,     32}, 0, {   -42,   -372}, {0x21, 0x60, 0xb5, 0xff}}},
    {{{  -125,    101,     29}, 0, {  1030,   -368}, {0xa7, 0x19, 0xaa, 0xff}}},
    {{{  -154,    176,     81}, 0, {   416,   1976}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   -85,    113,     62}, 0, {   -40,   -360}, {0x54, 0x4b, 0x38, 0xff}}},
    {{{   -97,    118,     32}, 0, {   866,   -356}, {0x21, 0x60, 0xb5, 0xff}}},
    {{{  -106,     94,     78}, 0, {   954,   -428}, {0xfb, 0xf9, 0x7e, 0xff}}},
    {{{  -154,    176,     81}, 0, {   534,   2020}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{  -131,     86,     58}, 0, {    44,   -388}, {0x8e, 0xd9, 0x27, 0xff}}},
};

// 0x0603F2E8
static const Vtx bowser_seg6_vertex_0603F2E8[] = {
    {{{  -111,     36,    -71}, 0, {    88,    -12}, {0xe7, 0x99, 0xbb, 0xff}}},
    {{{  -127,     44,    -43}, 0, {  1026,     -8}, {0xa2, 0xbc, 0x31, 0xff}}},
    {{{  -181,     75,   -103}, 0, {   430,   2004}, {0x97, 0x15, 0xbe, 0xff}}},
    {{{  -181,     75,   -103}, 0, {   414,   2004}, {0x97, 0x15, 0xbe, 0xff}}},
    {{{  -106,     88,    -63}, 0, {    24,     -8}, {0xff, 0x79, 0xdd, 0xff}}},
    {{{   -98,     63,    -83}, 0, {   984,      0}, {0x1f, 0x0e, 0x86, 0xff}}},
    {{{  -181,     75,   -103}, 0, {   436,   2012}, {0x97, 0x15, 0xbe, 0xff}}},
    {{{  -124,     76,    -39}, 0, {   -40,     12}, {0xb1, 0x46, 0x45, 0xff}}},
    {{{  -106,     88,    -63}, 0, {   964,    -16}, {0xff, 0x79, 0xdd, 0xff}}},
    {{{  -111,     36,    -71}, 0, {   986,      4}, {0xe7, 0x99, 0xbb, 0xff}}},
    {{{  -181,     75,   -103}, 0, {   540,   1992}, {0x97, 0x15, 0xbe, 0xff}}},
    {{{   -98,     63,    -83}, 0, {  -152,     16}, {0x1f, 0x0e, 0x86, 0xff}}},
    {{{  -181,     75,   -103}, 0, {   480,   2020}, {0x97, 0x15, 0xbe, 0xff}}},
    {{{  -127,     44,    -43}, 0, {    56,     -4}, {0xa2, 0xbc, 0x31, 0xff}}},
    {{{  -124,     76,    -39}, 0, {  1028,    -12}, {0xb1, 0x46, 0x45, 0xff}}},
};

// 0x0603F3D8
static const Vtx bowser_seg6_vertex_0603F3D8[] = {
    {{{   100,    102,     79}, 0, {    -2,   -280}, {0xee, 0x07, 0x7d, 0xff}}},
    {{{   127,     90,     65}, 0, {  1050,   -296}, {0x65, 0xd6, 0x3f, 0xff}}},
    {{{   154,    180,     82}, 0, {   382,   2044}, {0x3f, 0x66, 0x27, 0xff}}},
    {{{   154,    180,     82}, 0, {   512,   2016}, {0x3f, 0x66, 0x27, 0xff}}},
    {{{   128,    102,     34}, 0, {  -126,   -256}, {0x69, 0x07, 0xbb, 0xff}}},
    {{{   102,    120,     29}, 0, {  1068,   -288}, {0xf7, 0x58, 0xa6, 0xff}}},
    {{{   154,    180,     82}, 0, {   468,   2000}, {0x3f, 0x66, 0x27, 0xff}}},
    {{{   127,     90,     65}, 0, {    88,   -332}, {0x65, 0xd6, 0x3f, 0xff}}},
    {{{   128,    102,     34}, 0, {  1024,   -332}, {0x69, 0x07, 0xbb, 0xff}}},
    {{{    84,    120,     57}, 0, {  1024,   -312}, {0xaa, 0x58, 0x1d, 0xff}}},
    {{{   154,    180,     82}, 0, {   458,   2040}, {0x3f, 0x66, 0x27, 0xff}}},
    {{{   102,    120,     29}, 0, {     6,   -280}, {0xf7, 0x58, 0xa6, 0xff}}},
    {{{   100,    102,     79}, 0, {   924,   -300}, {0xee, 0x07, 0x7d, 0xff}}},
    {{{   154,    180,     82}, 0, {   458,   2036}, {0x3f, 0x66, 0x27, 0xff}}},
    {{{    84,    120,     57}, 0, {     0,   -312}, {0xaa, 0x58, 0x1d, 0xff}}},
};

// 0x0603F4C8
static const Vtx bowser_seg6_vertex_0603F4C8[] = {
    {{{   -87,    144,     -1}, 0, {    18,     -8}, {0x88, 0x09, 0x25, 0xff}}},
    {{{   -62,    153,     16}, 0, {   946,      4}, {0xf5, 0x30, 0x74, 0xff}}},
    {{{   -86,    232,    -19}, 0, {   500,   2008}, {0xdc, 0x79, 0xf9, 0xff}}},
    {{{   -86,    232,    -19}, 0, {   426,   1952}, {0xdc, 0x79, 0xf9, 0xff}}},
    {{{   -46,    153,    -33}, 0, {   130,     12}, {0x3a, 0x2f, 0x9a, 0xff}}},
    {{{   -77,    144,    -32}, 0, {   904,      0}, {0xb2, 0x06, 0x9d, 0xff}}},
    {{{   -86,    232,    -19}, 0, {   460,   1992}, {0xdc, 0x79, 0xf9, 0xff}}},
    {{{   -37,    159,     -2}, 0, {    20,    -28}, {0x61, 0x49, 0x21, 0xff}}},
    {{{   -46,    153,    -33}, 0, {  1040,    -12}, {0x3a, 0x2f, 0x9a, 0xff}}},
    {{{   -87,    144,     -1}, 0, {   928,     24}, {0x88, 0x09, 0x25, 0xff}}},
    {{{   -86,    232,    -19}, 0, {   416,   2012}, {0xdc, 0x79, 0xf9, 0xff}}},
    {{{   -77,    144,    -32}, 0, {    52,     12}, {0xb2, 0x06, 0x9d, 0xff}}},
    {{{   -86,    232,    -19}, 0, {   488,   2004}, {0xdc, 0x79, 0xf9, 0xff}}},
    {{{   -62,    153,     16}, 0, {    28,     12}, {0xf5, 0x30, 0x74, 0xff}}},
    {{{   -37,    159,     -2}, 0, {   956,     16}, {0x61, 0x49, 0x21, 0xff}}},
};

// 0x0603F5B8
static const Vtx bowser_seg6_vertex_0603F5B8[] = {
    {{{    47,    153,    -33}, 0, {  -284,     28}, {0xc6, 0x2f, 0x9a, 0xff}}},
    {{{    38,    159,     -2}, 0, {   980,     92}, {0x9f, 0x49, 0x21, 0xff}}},
    {{{    87,    232,    -19}, 0, {   418,   2036}, {0x24, 0x79, 0xf9, 0xff}}},
    {{{    87,    232,    -19}, 0, {   586,   1988}, {0x24, 0x79, 0xf9, 0xff}}},
    {{{    78,    144,    -32}, 0, {   -34,     32}, {0x4e, 0x06, 0x9d, 0xff}}},
    {{{    47,    153,    -33}, 0, {  1302,     16}, {0xc6, 0x2f, 0x9a, 0xff}}},
    {{{    87,    232,    -19}, 0, {   460,   2000}, {0x24, 0x79, 0xf9, 0xff}}},
    {{{    88,    144,     -1}, 0, {     8,     -4}, {0x78, 0x09, 0x25, 0xff}}},
    {{{    78,    144,    -32}, 0, {   974,     -8}, {0x4e, 0x06, 0x9d, 0xff}}},
    {{{    87,    232,    -19}, 0, {   460,   2004}, {0x24, 0x79, 0xf9, 0xff}}},
    {{{    63,    153,     16}, 0, {  -382,     92}, {0x0b, 0x30, 0x74, 0xff}}},
    {{{    88,    144,     -1}, 0, {  1000,      4}, {0x78, 0x09, 0x25, 0xff}}},
    {{{    87,    232,    -19}, 0, {   604,   1996}, {0x24, 0x79, 0xf9, 0xff}}},
    {{{    38,    159,     -2}, 0, {  -150,      4}, {0x9f, 0x49, 0x21, 0xff}}},
    {{{    63,    153,     16}, 0, {  1090,      0}, {0x0b, 0x30, 0x74, 0xff}}},
};

// 0x0603F6A8
static const Vtx bowser_seg6_vertex_0603F6A8[] = {
    {{{   128,     44,    -43}, 0, {  1016,     -8}, {0x36, 0x8e, 0x05, 0xff}}},
    {{{   125,     76,    -39}, 0, {   -10,      4}, {0x1e, 0x74, 0x27, 0xff}}},
    {{{   128,     49,    -43}, 0, {   838,     -8}, {0x9b, 0x00, 0xb4, 0xff}}},
    {{{   107,     88,    -63}, 0, {   962,      0}, {0x01, 0x79, 0xdd, 0xff}}},
    {{{   182,     75,   -103}, 0, {   474,   2012}, {0x69, 0x15, 0xbe, 0xff}}},
    {{{    99,     63,    -83}, 0, {     8,      0}, {0xe1, 0x0e, 0x86, 0xff}}},
    {{{   125,     76,    -39}, 0, {   896,    -12}, {0x1e, 0x74, 0x27, 0xff}}},
    {{{   182,     75,   -103}, 0, {   464,   1980}, {0x69, 0x15, 0xbe, 0xff}}},
    {{{   107,     88,    -63}, 0, {    22,      0}, {0x01, 0x79, 0xdd, 0xff}}},
    {{{   182,     75,   -103}, 0, {   420,   2004}, {0x69, 0x15, 0xbe, 0xff}}},
    {{{   112,     36,    -71}, 0, {  -142,     -8}, {0x19, 0x99, 0xbb, 0xff}}},
    {{{    99,     63,    -83}, 0, {   858,     -8}, {0xe1, 0x0e, 0x86, 0xff}}},
    {{{   182,     75,   -103}, 0, {   448,   2004}, {0x69, 0x15, 0xbe, 0xff}}},
    {{{    72,    132,    -53}, 0, {   898,    -16}, {0x27, 0x64, 0x43, 0xff}}},
    {{{   107,    168,   -127}, 0, {   434,   2008}, {0x3b, 0x50, 0xb2, 0xff}}},
    {{{    46,    133,    -72}, 0, {   -36,     16}, {0xb7, 0x66, 0xf0, 0xff}}},
};

// 0x0603F7A8
static const Vtx bowser_seg6_vertex_0603F7A8[] = {
    {{{   128,     44,    -43}, 0, {   -70,      0}, {0x36, 0x8e, 0x05, 0xff}}},
    {{{   112,     36,    -71}, 0, {  1020,     20}, {0x19, 0x99, 0xbb, 0xff}}},
    {{{   182,     75,   -103}, 0, {   492,   2048}, {0x69, 0x15, 0xbe, 0xff}}},
    {{{    49,    108,    -94}, 0, {   980,    -12}, {0xc4, 0xfd, 0x91, 0xff}}},
    {{{   107,    168,   -127}, 0, {   438,   2008}, {0x3b, 0x50, 0xb2, 0xff}}},
    {{{    77,     93,    -88}, 0, {  -156,      0}, {0x3e, 0xbb, 0xaa, 0xff}}},
    {{{    46,    133,    -72}, 0, {   952,     20}, {0xb7, 0x66, 0xf0, 0xff}}},
    {{{   107,    168,   -127}, 0, {   480,   2004}, {0x3b, 0x50, 0xb2, 0xff}}},
    {{{    49,    108,    -94}, 0, {   -58,    -12}, {0xc4, 0xfd, 0x91, 0xff}}},
    {{{   107,    168,   -127}, 0, {   526,   2012}, {0x3b, 0x50, 0xb2, 0xff}}},
    {{{    92,    108,    -62}, 0, {   -64,     -8}, {0x7c, 0xfc, 0x18, 0xff}}},
    {{{    77,     93,    -88}, 0, {  1038,    -12}, {0x3e, 0xbb, 0xaa, 0xff}}},
    {{{    26,     96,   -112}, 0, {   -18,     -8}, {0x71, 0x34, 0xeb, 0xff}}},
    {{{    16,     69,   -126}, 0, {   976,     -8}, {0x46, 0xbf, 0xad, 0xff}}},
    {{{     0,    124,   -183}, 0, {   434,   2004}, {0xff, 0x38, 0x8f, 0xff}}},
};

// 0x0603F898
static const Vtx bowser_seg6_vertex_0603F898[] = {
    {{{    72,    132,    -53}, 0, {    38,     24}, {0x27, 0x64, 0x43, 0xff}}},
    {{{    92,    108,    -62}, 0, {  1040,      0}, {0x7c, 0xfc, 0x18, 0xff}}},
    {{{   107,    168,   -127}, 0, {   468,   2016}, {0x3b, 0x50, 0xb2, 0xff}}},
    {{{   -25,     96,   -112}, 0, {    -4,     12}, {0x8e, 0x32, 0xec, 0xff}}},
    {{{     0,    113,   -103}, 0, {   968,     20}, {0xff, 0x7d, 0x11, 0xff}}},
    {{{     0,    124,   -183}, 0, {   504,   2004}, {0xff, 0x38, 0x8f, 0xff}}},
    {{{     0,    113,   -103}, 0, {   -74,    -12}, {0xff, 0x7d, 0x11, 0xff}}},
    {{{    26,     96,   -112}, 0, {   916,     -8}, {0x71, 0x34, 0xeb, 0xff}}},
    {{{     0,    124,   -183}, 0, {   400,   2012}, {0xff, 0x38, 0x8f, 0xff}}},
    {{{   -15,     69,   -126}, 0, {  -194,      8}, {0xba, 0xbe, 0xae, 0xff}}},
    {{{   -25,     96,   -112}, 0, {   746,     -8}, {0x8e, 0x32, 0xec, 0xff}}},
    {{{     0,    124,   -183}, 0, {   388,   2008}, {0xff, 0x38, 0x8f, 0xff}}},
    {{{   -71,    132,    -53}, 0, {   928,     -4}, {0xd9, 0x64, 0x43, 0xff}}},
    {{{  -106,    168,   -127}, 0, {   458,   1980}, {0xc5, 0x50, 0xb2, 0xff}}},
    {{{   -91,    108,    -62}, 0, {    52,      0}, {0x84, 0xfc, 0x18, 0xff}}},
};

// 0x0603F988
static const Vtx bowser_seg6_vertex_0603F988[] = {
    {{{    16,     69,   -126}, 0, {     2,     -8}, {0x46, 0xbf, 0xad, 0xff}}},
    {{{   -15,     69,   -126}, 0, {  1120,      8}, {0xba, 0xbe, 0xae, 0xff}}},
    {{{     0,    124,   -183}, 0, {   498,   2008}, {0xff, 0x38, 0x8f, 0xff}}},
    {{{   -48,    108,    -94}, 0, {   950,     -4}, {0x3c, 0xfd, 0x91, 0xff}}},
    {{{  -106,    168,   -127}, 0, {   382,   1992}, {0xc5, 0x50, 0xb2, 0xff}}},
    {{{   -45,    133,    -72}, 0, {     6,     -4}, {0x49, 0x66, 0xf0, 0xff}}},
    {{{  -106,    168,   -127}, 0, {   520,   2028}, {0xc5, 0x50, 0xb2, 0xff}}},
    {{{   -76,     93,    -88}, 0, {  -156,     12}, {0xc2, 0xbb, 0xaa, 0xff}}},
    {{{   -91,    108,    -62}, 0, {   932,     24}, {0x84, 0xfc, 0x18, 0xff}}},
    {{{   -48,    108,    -94}, 0, {   -36,     56}, {0x3c, 0xfd, 0x91, 0xff}}},
    {{{   -76,     93,    -88}, 0, {   984,      0}, {0xc2, 0xbb, 0xaa, 0xff}}},
    {{{  -106,    168,   -127}, 0, {   540,   2024}, {0xc5, 0x50, 0xb2, 0xff}}},
    {{{   -71,    132,    -53}, 0, {    24,    -12}, {0xd9, 0x64, 0x43, 0xff}}},
    {{{   -45,    133,    -72}, 0, {  1000,    -12}, {0x49, 0x66, 0xf0, 0xff}}},
    {{{  -106,    168,   -127}, 0, {   464,   1996}, {0xc5, 0x50, 0xb2, 0xff}}},
};

// 0x0603FA78
static const Vtx bowser_seg6_vertex_0603FA78[] = {
    {{{    67,    104,    167}, 0, {  1256,    140}, {0x5c, 0x12, 0x55, 0xff}}},
    {{{    40,     85,    189}, 0, {   348,   -110}, {0x1d, 0x0d, 0x7a, 0xff}}},
    {{{    49,     58,    158}, 0, {   388,    952}, {0x39, 0xc9, 0x62, 0xff}}},
    {{{    81,     91,    138}, 0, {  1556,    950}, {0x4e, 0xf9, 0x63, 0xff}}},
    {{{    82,    124,    131}, 0, {  2140,    574}, {0x5e, 0x40, 0x37, 0xff}}},
    {{{    49,     58,    158}, 0, {  3948,    972}, {0x39, 0xc9, 0x62, 0xff}}},
    {{{    40,     85,    189}, 0, {  3544,    -10}, {0x1d, 0x0d, 0x7a, 0xff}}},
    {{{     0,     51,    166}, 0, {  2008,    982}, {0x00, 0xb4, 0x65, 0xff}}},
    {{{   -48,     58,    158}, 0, {    68,    942}, {0xc6, 0xca, 0x62, 0xff}}},
    {{{   -39,     85,    189}, 0, {   324,    -34}, {0xe3, 0x0d, 0x7a, 0xff}}},
    {{{   -80,     91,    138}, 0, {  1688,    952}, {0xb2, 0xf9, 0x63, 0xff}}},
    {{{   -66,    104,    167}, 0, {  1344,    156}, {0xa4, 0x12, 0x55, 0xff}}},
    {{{   -81,    124,    131}, 0, {  2464,    566}, {0xa2, 0x40, 0x37, 0xff}}},
    {{{   -48,     58,    158}, 0, {   196,    992}, {0xc6, 0xca, 0x62, 0xff}}},
    {{{   -39,     85,    189}, 0, {   200,    -62}, {0xe3, 0x0d, 0x7a, 0xff}}},
};

// 0x0603FB68
static const Vtx bowser_seg6_vertex_0603FB68[] = {
    {{{   129,    -50,    -86}, 0, {     0,      0}, {0x57, 0xc1, 0xbe, 0xff}}},
    {{{     0,    -48,   -166}, 0, {     0,      0}, {0x00, 0xe5, 0x84, 0xff}}},
    {{{   111,    -16,    -99}, 0, {     0,      0}, {0x50, 0x16, 0xa1, 0xff}}},
    {{{     0,    -21,   -146}, 0, {     0,      0}, {0x00, 0x17, 0x84, 0xff}}},
};

// 0x0603FBA8 - 0x0603FC18
const Gfx bowser_seg6_dl_0603FBA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06023C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_0603E718, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  4, 0x0,  3,  2,  8, 0x0),
    gsSP1Triangle( 7,  3,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603FC18 - 0x0603FD38
const Gfx bowser_seg6_dl_0603FC18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06025C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603E7A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14, 12, 11, 0x0,  0,  2, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E8A8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603E988, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603EA68, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  5,  4,  0, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603FD38 - 0x0603FDD0
const Gfx bowser_seg6_dl_0603FD38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06025438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603EB48, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  2, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 2,  6,  7, 0x0,  8,  3,  2, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  2,  7,  8, 0x0),
    gsSP2Triangles( 7,  9,  8, 0x0, 10,  3,  8, 0x0),
    gsSP2Triangles(11,  8,  9, 0x0, 11,  9, 12, 0x0),
    gsSP2Triangles(10,  8, 11, 0x0, 10, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603FDD0 - 0x0603FEA8
const Gfx bowser_seg6_dl_0603FDD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603EC18, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 8,  7,  6, 0x0,  9,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603ED18, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 10, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603EDF8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603FEA8 - 0x0603FFA8
const Gfx bowser_seg6_dl_0603FEA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0601F438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603EE58, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603EF58, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F038, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F128, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP1Triangle(10, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0603FFA8 - 0x06040180
const Gfx bowser_seg6_dl_0603FFA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602AC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603F1F8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F2E8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F3D8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F4C8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F5B8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F6A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 0, 12,  1, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F7A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F898, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_0603F988, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x06040180 - 0x060401F0
const Gfx bowser_seg6_dl_06040180[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_0603FA78, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  7,  9, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 11, 10, 0x0),
    gsSP1Triangle(13, 14, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x060401F0 - 0x06040210
const Gfx bowser_seg6_dl_060401F0[] = {
    gsSPVertex(bowser_seg6_vertex_0603FB68, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06040210 - 0x060402D8
const Gfx bowser_seg6_dl_06040210[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603FBA8),
    gsSPDisplayList(bowser_seg6_dl_0603FC18),
    gsSPDisplayList(bowser_seg6_dl_0603FD38),
    gsSPDisplayList(bowser_seg6_dl_0603FDD0),
    gsSPDisplayList(bowser_seg6_dl_0603FEA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603FFA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06040180),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(bowser_seg6_dl_060401F0),
    gsSPEndDisplayList(),
};

#if BUGFIX_BOWSER_FADING_OUT
// 0x06040358 - 0x06040428
const Gfx bowser_seg6_dl_06040358[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetRenderMode(G_RM_CUSTOM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603FBA8),
    gsSPDisplayList(bowser_seg6_dl_0603FC18),
    gsSPDisplayList(bowser_seg6_dl_0603FD38),
    gsSPDisplayList(bowser_seg6_dl_0603FDD0),
    gsSPDisplayList(bowser_seg6_dl_0603FEA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_0603FFA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06040180),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(bowser_seg6_dl_060401F0),
    gsSPEndDisplayList(),
};
#endif

// 0x060402D8
static const Vtx bowser_seg6_vertex_060402D8[] = {
    {{{   -36,    -51,     15}, 0, {    58,   -378}, {0xa6, 0xf9, 0x58, 0xff}}},
    {{{   -19,    -43,     45}, 0, {  -690,   -202}, {0x92, 0xef, 0x3b, 0xff}}},
    {{{   -40,      7,     39}, 0, {  -566,    990}, {0x96, 0x22, 0x3c, 0xff}}},
    {{{    26,      4,     87}, 0, {   414,    -46}, {0xb7, 0x11, 0x65, 0xff}}},
    {{{    62,     32,     94}, 0, {  -414,    796}, {0x24, 0x63, 0x46, 0xff}}},
    {{{   -40,      7,     39}, 0, {  2638,    304}, {0x96, 0x22, 0x3c, 0xff}}},
    {{{   157,    -55,    -19}, 0, {  -186,    770}, {0x54, 0xa4, 0xeb, 0xff}}},
    {{{   157,    -55,     20}, 0, {  1026,    616}, {0x67, 0xbb, 0x16, 0xff}}},
    {{{   150,    -73,      0}, 0, {   388,    122}, {0x78, 0xd7, 0x00, 0xff}}},
    {{{    56,   -125,     36}, 0, {  1342,    228}, {0xea, 0x95, 0x3f, 0xff}}},
    {{{   135,   -116,     30}, 0, {  -520,    234}, {0x40, 0x9b, 0x28, 0xff}}},
    {{{    71,   -107,     61}, 0, {   992,    860}, {0xfe, 0xba, 0x69, 0xff}}},
    {{{   -36,    -51,    -14}, 0, {   804,   -382}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{    56,   -125,    -35}, 0, {   728,    -68}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{    71,   -107,    -60}, 0, {   272,    742}, {0xfe, 0xba, 0x97, 0xff}}},
    {{{   135,   -116,    -29}, 0, { -1926,   -108}, {0x40, 0x9b, 0xd8, 0xff}}},
};

// 0x060403D8
static const Vtx bowser_seg6_vertex_060403D8[] = {
    {{{   -40,      7,     39}, 0, {  -566,    990}, {0x96, 0x22, 0x3c, 0xff}}},
    {{{   -40,      7,    -44}, 0, {  1518,    984}, {0xa0, 0x24, 0xb7, 0xff}}},
    {{{   -36,    -51,    -14}, 0, {   804,   -382}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{   -19,    -43,    -44}, 0, {  1548,   -208}, {0x93, 0xeb, 0xc5, 0xff}}},
    {{{    12,    -28,    -72}, 0, {  2238,     88}, {0xc0, 0x01, 0x93, 0xff}}},
    {{{    56,   -125,     36}, 0, {  1264,   -504}, {0xea, 0x95, 0x3f, 0xff}}},
    {{{    56,   -132,      0}, 0, {   588,   -514}, {0x09, 0x82, 0x00, 0xff}}},
    {{{   135,   -116,     30}, 0, {  1068,    970}, {0x40, 0x9b, 0x28, 0xff}}},
    {{{    56,   -125,    -35}, 0, {   -88,   -472}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{   135,   -116,    -29}, 0, {   -62,    996}, {0x40, 0x9b, 0xd8, 0xff}}},
    {{{   137,   -119,      0}, 0, {   500,   1008}, {0x54, 0xa2, 0x00, 0xff}}},
    {{{    56,   -125,    -35}, 0, {   728,    -68}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{    39,    -99,    -51}, 0, {  1272,    664}, {0xfa, 0x9e, 0xb0, 0xff}}},
    {{{    71,   -107,    -60}, 0, {   272,    742}, {0xfe, 0xba, 0x97, 0xff}}},
};

// 0x060404B8
static const Vtx bowser_seg6_vertex_060404B8[] = {
    {{{    10,    -97,     45}, 0, {  -586,    996}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{    56,   -132,      0}, 0, {   500,   -326}, {0x09, 0x82, 0x00, 0xff}}},
    {{{    56,   -125,     36}, 0, {  -290,   -258}, {0xea, 0x95, 0x3f, 0xff}}},
    {{{    10,    -97,    -44}, 0, {  1348,   1070}, {0xc6, 0xac, 0xb6, 0xff}}},
    {{{    56,   -125,    -35}, 0, {  1274,   -198}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{    10,    -97,    -44}, 0, {   192,   1126}, {0xc6, 0xac, 0xb6, 0xff}}},
    {{{    39,    -99,    -51}, 0, {   822,    640}, {0xfa, 0x9e, 0xb0, 0xff}}},
    {{{    56,   -125,    -35}, 0, {   800,   -274}, {0xea, 0x95, 0xc1, 0xff}}},
    {{{   150,    -73,      0}, 0, {   492,    886}, {0x78, 0xd7, 0x00, 0xff}}},
    {{{   137,   -119,      0}, 0, {   514,    -20}, {0x54, 0xa2, 0x00, 0xff}}},
    {{{   135,   -116,    -29}, 0, {   -44,     22}, {0x40, 0x9b, 0xd8, 0xff}}},
    {{{   135,   -116,     30}, 0, {  1074,     38}, {0x40, 0x9b, 0x28, 0xff}}},
    {{{    71,   -107,     61}, 0, {   770,    -12}, {0xfe, 0xba, 0x69, 0xff}}},
    {{{    79,    -67,     69}, 0, {   584,    932}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{    49,    -58,     77}, 0, {  1338,   1218}, {0x1c, 0xce, 0x71, 0xff}}},
    {{{    39,    -99,     52}, 0, {  1566,    166}, {0xfa, 0x9e, 0x50, 0xff}}},
};

// 0x060405B8
static const Vtx bowser_seg6_vertex_060405B8[] = {
    {{{    63,     45,      0}, 0, {   478,   -512}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{    62,     32,    -93}, 0, {  2542,   -382}, {0x25, 0x61, 0xb9, 0xff}}},
    {{{   -40,      7,    -44}, 0, {  1430,   2406}, {0xa0, 0x24, 0xb7, 0xff}}},
    {{{   -40,      7,     39}, 0, {  -414,   2412}, {0x96, 0x22, 0x3c, 0xff}}},
    {{{    62,     32,     94}, 0, { -1586,   -370}, {0x24, 0x63, 0x46, 0xff}}},
    {{{    10,    -97,     45}, 0, {   -12,   -548}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{   -19,    -43,     45}, 0, {   -14,   1000}, {0x92, 0xef, 0x3b, 0xff}}},
    {{{   -36,    -51,     15}, 0, {  1216,   1022}, {0xa6, 0xf9, 0x58, 0xff}}},
    {{{    10,    -97,     45}, 0, {  1618,   1004}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{    56,   -125,     36}, 0, {   726,   -172}, {0xea, 0x95, 0x3f, 0xff}}},
    {{{    39,    -99,     52}, 0, {   900,    806}, {0xfa, 0x9e, 0x50, 0xff}}},
    {{{    39,    -99,    -51}, 0, {  -738,    482}, {0xfa, 0x9e, 0xb0, 0xff}}},
    {{{    49,    -58,    -76}, 0, {   -52,   1388}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{    71,   -107,    -60}, 0, {   -14,     86}, {0xfe, 0xba, 0x97, 0xff}}},
    {{{    71,   -107,     61}, 0, {    82,    472}, {0xfe, 0xba, 0x69, 0xff}}},
};

// 0x060406A8
static const Vtx bowser_seg6_vertex_060406A8[] = {
    {{{   -36,    -51,    -14}, 0, {  -184,   1010}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{   -19,    -43,    -44}, 0, {   896,    874}, {0x93, 0xeb, 0xc5, 0xff}}},
    {{{    10,    -97,    -44}, 0, {   958,   -660}, {0xc6, 0xac, 0xb6, 0xff}}},
    {{{   102,    -67,     73}, 0, {    -2,    948}, {0x08, 0xc2, 0x6e, 0xff}}},
    {{{    79,    -67,     69}, 0, {   584,    932}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{   123,    -90,     62}, 0, {  -536,    328}, {0x29, 0xbd, 0x63, 0xff}}},
    {{{    49,    -58,    -76}, 0, {   -52,   1388}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{    79,    -67,    -68}, 0, {   582,    902}, {0x00, 0xc4, 0x91, 0xff}}},
    {{{    71,   -107,    -60}, 0, {   -14,     86}, {0xfe, 0xba, 0x97, 0xff}}},
    {{{   -40,      7,     39}, 0, {  1442,    942}, {0x96, 0x22, 0x3c, 0xff}}},
    {{{   -19,    -43,     45}, 0, {  1006,   -334}, {0x92, 0xef, 0x3b, 0xff}}},
    {{{    12,    -28,     73}, 0, {  -106,      6}, {0xbe, 0x04, 0x6b, 0xff}}},
    {{{    26,      4,     87}, 0, {  -646,    794}, {0xb7, 0x11, 0x65, 0xff}}},
    {{{   -40,      7,    -44}, 0, {    -2,    932}, {0xa0, 0x24, 0xb7, 0xff}}},
    {{{    26,      4,    -86}, 0, {  1490,    400}, {0xb9, 0x0e, 0x99, 0xff}}},
    {{{    12,    -28,    -72}, 0, {   922,   -146}, {0xc0, 0x01, 0x93, 0xff}}},
};

// 0x060407A8
static const Vtx bowser_seg6_vertex_060407A8[] = {
    {{{   -40,      7,    -44}, 0, {  -952,    762}, {0xa0, 0x24, 0xb7, 0xff}}},
    {{{    62,     32,    -93}, 0, {  1902,    694}, {0x25, 0x61, 0xb9, 0xff}}},
    {{{    26,      4,    -86}, 0, {   916,    272}, {0xb9, 0x0e, 0x99, 0xff}}},
};

// 0x060407D8
static const Vtx bowser_seg6_vertex_060407D8[] = {
    {{{   154,      6,     90}, 0, {  1150,    -66}, {0x0b, 0x42, 0x6b, 0xff}}},
    {{{   151,    -43,     67}, 0, {  1214,  -1116}, {0x20, 0xbf, 0x67, 0xff}}},
    {{{   198,    -43,     64}, 0, {     0,  -1182}, {0x3e, 0xc5, 0x5d, 0xff}}},
    {{{    62,     32,    -93}, 0, {   -48,    928}, {0x25, 0x61, 0xb9, 0xff}}},
    {{{    76,     14,      0}, 0, {  2026,    760}, {0x64, 0x4d, 0x00, 0xff}}},
    {{{    83,      2,    -86}, 0, {    72,    710}, {0x62, 0x34, 0xc4, 0xff}}},
    {{{    63,     45,      0}, 0, {  2068,    964}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{    62,     32,     94}, 0, {  4140,    854}, {0x24, 0x63, 0x46, 0xff}}},
    {{{    98,    -10,    -72}, 0, {   388,    598}, {0x0b, 0x79, 0xdd, 0xff}}},
    {{{    83,      2,     87}, 0, {  3948,    642}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{    98,    -10,     73}, 0, {  3612,    544}, {0x0b, 0x79, 0x23, 0xff}}},
    {{{    94,    -30,     91}, 0, {  2700,   -896}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{   194,     27,     35}, 0, {   -18,    960}, {0x42, 0x68, 0x1d, 0xff}}},
    {{{   170,     27,     44}, 0, {   612,    924}, {0x15, 0x6d, 0x3c, 0xff}}},
};

// 0x060408B8
static const Vtx bowser_seg6_vertex_060408B8[] = {
    {{{   151,    -43,    -66}, 0, {   714,   -182}, {0x20, 0xbf, 0x99, 0xff}}},
    {{{   114,    -47,    -72}, 0, {  -112,    142}, {0x16, 0xef, 0x85, 0xff}}},
    {{{    94,    -30,    -90}, 0, {  -386,    792}, {0x34, 0xf9, 0x8d, 0xff}}},
    {{{    94,    -30,     91}, 0, {  2700,   -896}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{   114,    -47,     73}, 0, {  2184,  -1210}, {0x16, 0xef, 0x7b, 0xff}}},
    {{{   151,    -43,     67}, 0, {  1214,  -1116}, {0x20, 0xbf, 0x67, 0xff}}},
    {{{    62,      0,   -113}, 0, {   736,   1006}, {0x12, 0x1f, 0x87, 0xff}}},
    {{{    63,    -31,   -110}, 0, {   548,    328}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{    26,      4,    -86}, 0, {   -82,   1186}, {0xb9, 0x0e, 0x99, 0xff}}},
    {{{    62,     32,    -93}, 0, {   886,   1590}, {0x25, 0x61, 0xb9, 0xff}}},
    {{{    12,    -28,    -72}, 0, {  -612,    530}, {0xc0, 0x01, 0x93, 0xff}}},
    {{{   198,    -43,    -63}, 0, {  1788,   -624}, {0x3e, 0xc5, 0xa3, 0xff}}},
    {{{   154,      6,    -89}, 0, {  1086,    900}, {0x0b, 0x42, 0x95, 0xff}}},
    {{{   226,    -26,    -24}, 0, {   430,   -686}, {0x7b, 0x0c, 0xe8, 0xff}}},
    {{{   198,    -43,    -63}, 0, {  -716,   -672}, {0x3e, 0xc5, 0xa3, 0xff}}},
    {{{   201,     -2,    -61}, 0, {  -652,    312}, {0x5b, 0x25, 0xb1, 0xff}}},
};

// 0x060409B8
static const Vtx bowser_seg6_vertex_060409B8[] = {
    {{{   170,     27,    -43}, 0, {  -102,   1494}, {0x15, 0x6d, 0xc4, 0xff}}},
    {{{   194,     27,    -34}, 0, {   168,   1126}, {0x42, 0x68, 0xe3, 0xff}}},
    {{{   154,      6,    -89}, 0, { -1448,   1234}, {0x0b, 0x42, 0x95, 0xff}}},
    {{{   199,     13,      0}, 0, {  1184,    690}, {0x36, 0x72, 0x00, 0xff}}},
    {{{   226,    -26,    -24}, 0, {   430,   -686}, {0x7b, 0x0c, 0xe8, 0xff}}},
    {{{   201,     -2,    -61}, 0, {  -652,    312}, {0x5b, 0x25, 0xb1, 0xff}}},
    {{{   170,     33,    -25}, 0, {   434,   1636}, {0x17, 0x74, 0x2d, 0xff}}},
    {{{   226,    -26,     25}, 0, {  1882,   -720}, {0x79, 0x15, 0x1d, 0xff}}},
    {{{   194,     27,     35}, 0, {  2216,   1076}, {0x42, 0x68, 0x1d, 0xff}}},
    {{{   146,     12,    -16}, 0, {   686,   1456}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   170,     33,     26}, 0, {  1970,   1600}, {0x17, 0x74, 0xd4, 0xff}}},
    {{{   146,     12,     17}, 0, {  1710,   1432}, {0xc3, 0x67, 0xd7, 0xff}}},
    {{{   154,      6,    -89}, 0, {     0,   -488}, {0x0b, 0x42, 0x95, 0xff}}},
    {{{   146,     18,    -53}, 0, {   826,   -458}, {0xc0, 0x61, 0xce, 0xff}}},
    {{{   170,     27,    -43}, 0, {  1050,   -996}, {0x15, 0x6d, 0xc4, 0xff}}},
};

// 0x06040AA8
static const Vtx bowser_seg6_vertex_06040AA8[] = {
    {{{   154,      6,    -89}, 0, {     0,   -488}, {0x0b, 0x42, 0x95, 0xff}}},
    {{{    94,    -30,    -90}, 0, {   -46,   1012}, {0x34, 0xf9, 0x8d, 0xff}}},
    {{{    98,    -10,    -72}, 0, {   378,    734}, {0x0b, 0x79, 0xdd, 0xff}}},
    {{{   146,     18,    -53}, 0, {   826,   -458}, {0xc0, 0x61, 0xce, 0xff}}},
    {{{   201,     -2,     62}, 0, {  3004,    226}, {0x5b, 0x25, 0x4f, 0xff}}},
    {{{   194,     27,     35}, 0, {  2216,   1076}, {0x42, 0x68, 0x1d, 0xff}}},
    {{{   154,      6,     90}, 0, {  3836,   1108}, {0x0b, 0x42, 0x6b, 0xff}}},
    {{{   226,    -26,     25}, 0, {  1882,   -720}, {0x79, 0x15, 0x1d, 0xff}}},
    {{{   198,    -43,     64}, 0, {  3028,   -760}, {0x3e, 0xc5, 0x5d, 0xff}}},
    {{{   151,    -43,    -66}, 0, {   -16,     52}, {0x20, 0xbf, 0x99, 0xff}}},
    {{{   198,    -43,    -63}, 0, {     0,    990}, {0x3e, 0xc5, 0xa3, 0xff}}},
    {{{   183,    -75,    -16}, 0, {   850,    672}, {0xff, 0x85, 0xe4, 0xff}}},
    {{{   157,    -55,    -19}, 0, {   826,    164}, {0x54, 0xa4, 0xeb, 0xff}}},
    {{{    63,    -31,   -110}, 0, {   548,    328}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{    49,    -58,    -76}, 0, {    28,   -286}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{    12,    -28,    -72}, 0, {  -612,    530}, {0xc0, 0x01, 0x93, 0xff}}},
};

// 0x06040BA8
static const Vtx bowser_seg6_vertex_06040BA8[] = {
    {{{   215,    -61,    -21}, 0, {   700,   1310}, {0x58, 0xac, 0xdf, 0xff}}},
    {{{   183,    -75,    -16}, 0, {   850,    672}, {0xff, 0x85, 0xe4, 0xff}}},
    {{{   198,    -43,    -63}, 0, {     0,    990}, {0x3e, 0xc5, 0xa3, 0xff}}},
    {{{   215,    -61,     22}, 0, {  1522,   1292}, {0x5b, 0xab, 0x16, 0xff}}},
    {{{   183,    -75,     17}, 0, {  1470,    658}, {0xe6, 0x88, 0x1c, 0xff}}},
    {{{   157,    -55,    -19}, 0, {   826,    164}, {0x54, 0xa4, 0xeb, 0xff}}},
    {{{   157,    -55,     20}, 0, {  1570,    148}, {0x67, 0xbb, 0x16, 0xff}}},
    {{{   198,    -43,     64}, 0, {  2306,    938}, {0x3e, 0xc5, 0x5d, 0xff}}},
    {{{   151,    -43,     67}, 0, {  2432,     -2}, {0x20, 0xbf, 0x67, 0xff}}},
    {{{    79,    -67,    -68}, 0, {   622,   -692}, {0x00, 0xc4, 0x91, 0xff}}},
    {{{    49,    -58,    -76}, 0, {    28,   -286}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{   102,    -67,    -72}, 0, {  1162,   -792}, {0x08, 0xc2, 0x92, 0xff}}},
    {{{    63,    -31,   -110}, 0, {   548,    328}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{    94,    -30,     91}, 0, {  -256,   -102}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{    83,      2,     87}, 0, {  -102,    728}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{    63,    -31,    111}, 0, {   490,    -88}, {0x00, 0xdc, 0x79, 0xff}}},
};

// 0x06040CA8
static const Vtx bowser_seg6_vertex_06040CA8[] = {
    {{{    94,    -30,    -90}, 0, {  1234,     98}, {0x34, 0xf9, 0x8d, 0xff}}},
    {{{   114,    -47,    -72}, 0, {  1524,   -452}, {0x16, 0xef, 0x85, 0xff}}},
    {{{   102,    -67,    -72}, 0, {  1162,   -792}, {0x08, 0xc2, 0x92, 0xff}}},
    {{{    63,    -31,   -110}, 0, {   548,    328}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{   102,    -67,     73}, 0, {  -382,    -18}, {0x08, 0xc2, 0x6e, 0xff}}},
    {{{    49,    -58,     77}, 0, {   802,    130}, {0x1c, 0xce, 0x71, 0xff}}},
    {{{    79,    -67,     69}, 0, {   116,    -10}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{    83,      2,     87}, 0, {  -102,    728}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{    94,    -30,     91}, 0, {  -256,   -102}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{    98,    -10,     73}, 0, {  -412,    392}, {0x0b, 0x79, 0x23, 0xff}}},
    {{{   102,    -67,     73}, 0, {  -310,  -1040}, {0x08, 0xc2, 0x6e, 0xff}}},
    {{{   114,    -47,     73}, 0, {  -636,   -578}, {0x16, 0xef, 0x7b, 0xff}}},
    {{{    63,    -31,    111}, 0, {   490,    -88}, {0x00, 0xdc, 0x79, 0xff}}},
    {{{    98,    -10,     73}, 0, {  3836,    776}, {0x0b, 0x79, 0x23, 0xff}}},
    {{{    94,    -30,     91}, 0, {  4264,   1066}, {0x34, 0xf9, 0x73, 0xff}}},
    {{{   154,      6,     90}, 0, {  4240,   -434}, {0x0b, 0x42, 0x6b, 0xff}}},
};

// 0x06040DA8
static const Vtx bowser_seg6_vertex_06040DA8[] = {
    {{{    26,      4,     87}, 0, {  1196,    900}, {0xb7, 0x11, 0x65, 0xff}}},
    {{{    63,    -31,    111}, 0, {   490,    -88}, {0x00, 0xdc, 0x79, 0xff}}},
    {{{    62,      0,    114}, 0, {   382,    712}, {0x12, 0x1f, 0x79, 0xff}}},
    {{{    83,      2,     87}, 0, {  -102,    728}, {0x62, 0x34, 0x3c, 0xff}}},
    {{{    12,    -28,     73}, 0, {  1636,    114}, {0xbe, 0x04, 0x6b, 0xff}}},
    {{{    49,    -58,     77}, 0, {   898,   -712}, {0x1c, 0xce, 0x71, 0xff}}},
    {{{   102,    -67,     73}, 0, {  -310,  -1040}, {0x08, 0xc2, 0x6e, 0xff}}},
    {{{    83,      2,    -86}, 0, {  1160,    822}, {0x62, 0x34, 0xc4, 0xff}}},
    {{{    94,    -30,    -90}, 0, {  1234,     98}, {0x34, 0xf9, 0x8d, 0xff}}},
    {{{    63,    -31,   -110}, 0, {   548,    328}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{    98,    -10,    -72}, 0, {  1400,    416}, {0x0b, 0x79, 0xdd, 0xff}}},
    {{{   146,     18,     54}, 0, {  3382,   -426}, {0xc0, 0x61, 0x32, 0xff}}},
    {{{    98,    -10,     73}, 0, {  3836,    776}, {0x0b, 0x79, 0x23, 0xff}}},
    {{{   154,      6,     90}, 0, {  4240,   -434}, {0x0b, 0x42, 0x6b, 0xff}}},
    {{{   170,     27,     44}, 0, {  3156,   -970}, {0x15, 0x6d, 0x3c, 0xff}}},
    {{{    62,     32,     94}, 0, {   270,   1514}, {0x24, 0x63, 0x46, 0xff}}},
};

// 0x06040EA8
static const Vtx bowser_seg6_vertex_06040EA8[] = {
    {{{    62,     32,    -93}, 0, {   616,   1358}, {0x25, 0x61, 0xb9, 0xff}}},
    {{{    83,      2,    -86}, 0, {  1006,    410}, {0x62, 0x34, 0xc4, 0xff}}},
    {{{    62,      0,   -113}, 0, {    96,    522}, {0x12, 0x1f, 0x87, 0xff}}},
    {{{    63,    -31,   -110}, 0, {    54,   -430}, {0x00, 0xdc, 0x87, 0xff}}},
    {{{   199,     13,      0}, 0, {  2100,  -1358}, {0x36, 0x72, 0x00, 0xff}}},
    {{{   146,     12,    -16}, 0, {  1690,   -380}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   146,     12,     17}, 0, {  2518,   -370}, {0xc3, 0x67, 0xd7, 0xff}}},
    {{{   170,     33,    -25}, 0, {    86,   1044}, {0x17, 0x74, 0x2d, 0xff}}},
    {{{   194,     27,    -34}, 0, {     0,    798}, {0x42, 0x68, 0xe3, 0xff}}},
    {{{   170,     27,    -43}, 0, {  -168,   1046}, {0x15, 0x6d, 0xc4, 0xff}}},
    {{{   170,     27,     44}, 0, {  1072,   1038}, {0x15, 0x6d, 0x3c, 0xff}}},
    {{{   194,     27,     35}, 0, {   944,    792}, {0x42, 0x68, 0x1d, 0xff}}},
    {{{   170,     33,     26}, 0, {   818,   1038}, {0x17, 0x74, 0xd4, 0xff}}},
    {{{   154,      6,     90}, 0, {  1626,    754}, {0x0b, 0x42, 0x6b, 0xff}}},
    {{{   198,    -43,     64}, 0, {   222,   -854}, {0x3e, 0xc5, 0x5d, 0xff}}},
    {{{   201,     -2,     62}, 0, {   -76,    244}, {0x5b, 0x25, 0x4f, 0xff}}},
};

// 0x06040FA8
static const Vtx bowser_seg6_vertex_06040FA8[] = {
    {{{   201,     -2,    -61}, 0, {  1502,    342}, {0x5b, 0x25, 0xb1, 0xff}}},
    {{{   198,    -43,    -63}, 0, {  1302,   -672}, {0x3e, 0xc5, 0xa3, 0xff}}},
    {{{   154,      6,    -89}, 0, {   100,    724}, {0x0b, 0x42, 0x95, 0xff}}},
};

// 0x06040FD8
static const Vtx bowser_seg6_vertex_06040FD8[] = {
    {{{   146,     12,    -16}, 0, {   348,   1572}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   146,     18,    -53}, 0, {   126,   1636}, {0xc0, 0x61, 0xce, 0xff}}},
    {{{    98,    -10,    -72}, 0, {    20,    612}, {0x0b, 0x79, 0xdd, 0xff}}},
    {{{   146,     12,     17}, 0, {   562,   1556}, {0xc3, 0x67, 0xd7, 0xff}}},
    {{{    98,    -10,      0}, 0, {   464,    576}, {0x06, 0x7e, 0x00, 0xff}}},
    {{{    98,    -10,     73}, 0, {   910,    544}, {0x0b, 0x79, 0x23, 0xff}}},
    {{{   146,     18,     54}, 0, {   784,   1584}, {0xc0, 0x61, 0x32, 0xff}}},
    {{{    98,    -10,      0}, 0, {   446,    776}, {0x06, 0x7e, 0x00, 0xff}}},
    {{{    98,    -10,    -72}, 0, {    16,    820}, {0x0b, 0x79, 0xdd, 0xff}}},
    {{{    76,     14,      0}, 0, {   456,   1804}, {0x64, 0x4d, 0x00, 0xff}}},
    {{{    98,    -10,     73}, 0, {   878,    728}, {0x0b, 0x79, 0x23, 0xff}}},
};

// 0x06041088
static const Vtx bowser_seg6_vertex_06041088[] = {
    {{{   -36,    -51,     15}, 0, {   -54,   1716}, {0xa6, 0xf9, 0x58, 0xff}}},
    {{{   -95,    -69,      0}, 0, {   -44,    752}, {0xa3, 0x56, 0x00, 0xff}}},
    {{{   -64,   -103,      0}, 0, {   580,    640}, {0xaf, 0x9f, 0xff, 0xff}}},
    {{{   -51,   -159,      0}, 0, {   736,    392}, {0x87, 0xdb, 0xff, 0xff}}},
    {{{    10,    -97,    -44}, 0, {   496,   1764}, {0xc6, 0xac, 0xb6, 0xff}}},
    {{{    -8,   -167,      0}, 0, {   976,    744}, {0x03, 0x82, 0x00, 0xff}}},
    {{{   -37,   -120,      0}, 0, {   478,    988}, {0x82, 0xf9, 0x00, 0xff}}},
    {{{    33,   -158,      0}, 0, {  1074,   1296}, {0x41, 0x94, 0x00, 0xff}}},
    {{{    56,   -132,      0}, 0, {   962,   1832}, {0x09, 0x82, 0x00, 0xff}}},
    {{{   -36,    -51,    -14}, 0, {   -58,   1796}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{   -37,   -120,      0}, 0, {   942,    712}, {0x82, 0xf9, 0x00, 0xff}}},
    {{{   -51,   -159,      0}, 0, {  1462,    -16}, {0x87, 0xdb, 0xff, 0xff}}},
    {{{    10,    -97,     45}, 0, {   804,   1636}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{   -36,    -51,    -14}, 0, {   818,   1844}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{   -95,    -69,      0}, 0, {   276,    908}, {0xa3, 0x56, 0x00, 0xff}}},
    {{{   -36,    -51,     15}, 0, {   188,   1864}, {0xa6, 0xf9, 0x58, 0xff}}},
};

// 0x06041188
static const Vtx bowser_seg6_vertex_06041188[] = {
    {{{    10,    -97,     45}, 0, {   804,   1636}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{   -51,   -159,      0}, 0, {  1462,    -16}, {0x87, 0xdb, 0xff, 0xff}}},
    {{{    -8,   -167,      0}, 0, {  1756,    380}, {0x03, 0x82, 0x00, 0xff}}},
    {{{    33,   -158,      0}, 0, {  1794,   1028}, {0x41, 0x94, 0x00, 0xff}}},
    {{{    56,   -132,      0}, 0, {  1510,   1672}, {0x09, 0x82, 0x00, 0xff}}},
    {{{   -37,   -120,      0}, 0, {   478,    988}, {0x82, 0xf9, 0x00, 0xff}}},
    {{{   -64,   -103,      0}, 0, {   234,    912}, {0xaf, 0x9f, 0xff, 0xff}}},
    {{{   -36,    -51,    -14}, 0, {   -58,   1796}, {0x9d, 0xf8, 0xb2, 0xff}}},
    {{{   -95,    -69,      0}, 0, {  -162,    976}, {0xa3, 0x56, 0x00, 0xff}}},
};

// 0x06041218
static const Vtx bowser_seg6_vertex_06041218[] = {
    {{{     8,    -89,    106}, 0, {   912,   1024}, {0xe7, 0xe1, 0x78, 0xff}}},
    {{{   -14,   -133,     74}, 0, {   380,   2016}, {0xdb, 0x87, 0xfe, 0xff}}},
    {{{    21,   -103,     77}, 0, {   176,   1272}, {0x3b, 0x92, 0x14, 0xff}}},
    {{{    21,   -103,     77}, 0, {   974,   1168}, {0x3b, 0x92, 0x14, 0xff}}},
    {{{   -14,   -133,     74}, 0, {   512,   2000}, {0xdb, 0x87, 0xfe, 0xff}}},
    {{{    -8,    -83,     69}, 0, {   146,    928}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{   -19,    -43,     45}, 0, {  1004,    -40}, {0x92, 0xef, 0x3b, 0xff}}},
    {{{    -8,    -83,     69}, 0, {   976,   1256}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{     9,    -57,     98}, 0, {    48,   1108}, {0xd5, 0x38, 0x68, 0xff}}},
    {{{    12,    -28,     73}, 0, {   -52,    -20}, {0xbe, 0x04, 0x6b, 0xff}}},
    {{{     9,    -57,    -97}, 0, {   840,    896}, {0xd5, 0x38, 0x98, 0xff}}},
    {{{     8,    -89,   -105}, 0, {  1072,   1340}, {0xe7, 0xe1, 0x88, 0xff}}},
    {{{    -8,    -83,    -68}, 0, {   -60,   1092}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{   -14,   -133,    -73}, 0, {    84,   1768}, {0xdb, 0x87, 0x02, 0xff}}},
    {{{    30,    -74,    100}, 0, {   646,    660}, {0x4a, 0xf4, 0x66, 0xff}}},
};

// 0x06041308
static const Vtx bowser_seg6_vertex_06041308[] = {
    {{{    10,    -97,     45}, 0, {   612,    -36}, {0xc6, 0xac, 0x4a, 0xff}}},
    {{{    21,   -103,     77}, 0, {  1000,   1276}, {0x3b, 0x92, 0x14, 0xff}}},
    {{{    -8,    -83,     69}, 0, {    96,   1060}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{    21,   -103,    -76}, 0, {   934,    880}, {0x3b, 0x92, 0xec, 0xff}}},
    {{{     8,    -89,   -105}, 0, {   168,   1344}, {0xe7, 0xe1, 0x88, 0xff}}},
    {{{    30,    -74,    -99}, 0, {   292,    612}, {0x4a, 0xf4, 0x9a, 0xff}}},
    {{{   -14,   -133,    -73}, 0, {   996,   2028}, {0xdb, 0x87, 0x02, 0xff}}},
    {{{    -8,    -83,    -68}, 0, {   796,   1460}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{    21,   -103,    -76}, 0, {   262,   1560}, {0x3b, 0x92, 0xec, 0xff}}},
    {{{    10,    -97,    -44}, 0, {   640,     80}, {0xc6, 0xac, 0xb6, 0xff}}},
    {{{    39,    -99,    -51}, 0, {   166,      8}, {0xfa, 0x9e, 0xb0, 0xff}}},
    {{{   -19,    -43,    -44}, 0, {  1182,     52}, {0x93, 0xeb, 0xc5, 0xff}}},
    {{{   -19,    -43,     45}, 0, {  -730,    164}, {0x92, 0xef, 0x3b, 0xff}}},
    {{{    39,    -99,     52}, 0, {  1230,    212}, {0xfa, 0x9e, 0x50, 0xff}}},
};

// 0x060413E8
static const Vtx bowser_seg6_vertex_060413E8[] = {
    {{{    49,    -58,     77}, 0, {  1006,    -40}, {0x1c, 0xce, 0x71, 0xff}}},
    {{{    21,   -103,     77}, 0, {   120,    808}, {0x3b, 0x92, 0x14, 0xff}}},
    {{{    39,    -99,     52}, 0, {   -10,    -24}, {0xfa, 0x9e, 0x50, 0xff}}},
    {{{    30,    -74,    100}, 0, {   910,    908}, {0x4a, 0xf4, 0x66, 0xff}}},
    {{{    -8,    -83,     69}, 0, {   956,   1236}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{   -14,   -133,     74}, 0, {   396,   1884}, {0xdb, 0x87, 0xfe, 0xff}}},
    {{{     8,    -89,    106}, 0, {   206,    872}, {0xe7, 0xe1, 0x78, 0xff}}},
    {{{     9,    -57,     98}, 0, {   644,    520}, {0xd5, 0x38, 0x68, 0xff}}},
    {{{    -8,    -83,    -68}, 0, {  1164,   1184}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{   -14,   -133,    -73}, 0, {   528,   2044}, {0xdb, 0x87, 0x02, 0xff}}},
    {{{    21,   -103,    -76}, 0, {   288,    984}, {0x3b, 0x92, 0xec, 0xff}}},
    {{{    -8,    -83,    -68}, 0, {   108,   1156}, {0x86, 0xdf, 0x00, 0xff}}},
    {{{   -19,    -43,    -44}, 0, {    48,     -8}, {0x93, 0xeb, 0xc5, 0xff}}},
    {{{     9,    -57,    -97}, 0, {   904,   1020}, {0xd5, 0x38, 0x98, 0xff}}},
    {{{    12,    -28,    -72}, 0, {   960,      8}, {0xc0, 0x01, 0x93, 0xff}}},
};

// 0x060414D8
static const Vtx bowser_seg6_vertex_060414D8[] = {
    {{{    30,    -74,    -99}, 0, {   -52,    960}, {0x4a, 0xf4, 0x9a, 0xff}}},
    {{{    49,    -58,    -76}, 0, {    -6,    -16}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{    21,   -103,    -76}, 0, {   780,    828}, {0x3b, 0x92, 0xec, 0xff}}},
    {{{    39,    -99,    -51}, 0, {  1044,    -40}, {0xfa, 0x9e, 0xb0, 0xff}}},
    {{{   146,     12,    -16}, 0, {  -158,     20}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   135,     53,    -34}, 0, {   526,   2012}, {0xe9, 0x7b, 0xec, 0xff}}},
    {{{   146,     18,    -53}, 0, {  1216,     60}, {0xc0, 0x61, 0xce, 0xff}}},
    {{{   146,     18,     54}, 0, {  -576,     52}, {0xc0, 0x61, 0x32, 0xff}}},
    {{{   135,     53,     35}, 0, {   574,   1980}, {0xe9, 0x7b, 0x14, 0xff}}},
    {{{   146,     12,     17}, 0, {  1148,     20}, {0xc3, 0x67, 0xd7, 0xff}}},
    {{{   135,     53,     35}, 0, {   514,   2012}, {0xe9, 0x7b, 0x14, 0xff}}},
    {{{   146,     18,     54}, 0, {  1094,    -20}, {0xc0, 0x61, 0x32, 0xff}}},
    {{{   170,     27,     44}, 0, {  -198,    -16}, {0x15, 0x6d, 0x3c, 0xff}}},
    {{{   170,     27,    -43}, 0, {  1234,     16}, {0x15, 0x6d, 0xc4, 0xff}}},
    {{{   146,     18,    -53}, 0, {     0,     56}, {0xc0, 0x61, 0xce, 0xff}}},
    {{{   135,     53,    -34}, 0, {   522,   1964}, {0xe9, 0x7b, 0xec, 0xff}}},
};

// 0x060415D8
static const Vtx bowser_seg6_vertex_060415D8[] = {
    {{{   146,     12,     17}, 0, {  -398,      4}, {0xc3, 0x67, 0xd7, 0xff}}},
    {{{   135,     53,     35}, 0, {   552,   2028}, {0xe9, 0x7b, 0x14, 0xff}}},
    {{{   170,     33,     26}, 0, {  1294,      0}, {0x17, 0x74, 0xd4, 0xff}}},
    {{{   135,     53,    -34}, 0, {   480,   2076}, {0xe9, 0x7b, 0xec, 0xff}}},
    {{{   170,     33,    -25}, 0, {   924,    112}, {0x17, 0x74, 0x2d, 0xff}}},
    {{{   170,     27,    -43}, 0, {   -36,    116}, {0x15, 0x6d, 0xc4, 0xff}}},
    {{{   135,     53,    -34}, 0, {   400,   1996}, {0xe9, 0x7b, 0xec, 0xff}}},
    {{{   146,     12,    -16}, 0, {  1276,     16}, {0xc3, 0x67, 0x29, 0xff}}},
    {{{   170,     33,    -25}, 0, {  -128,     -4}, {0x17, 0x74, 0x2d, 0xff}}},
    {{{   135,     53,     35}, 0, {   362,   2012}, {0xe9, 0x7b, 0x14, 0xff}}},
    {{{   170,     27,     44}, 0, {  1048,    -12}, {0x15, 0x6d, 0x3c, 0xff}}},
    {{{   170,     33,     26}, 0, {   -52,    -44}, {0x17, 0x74, 0xd4, 0xff}}},
};

// 0x06041698
static const Vtx bowser_seg6_vertex_06041698[] = {
    {{{     9,    -57,    -97}, 0, {   276,    724}, {0xd5, 0x38, 0x98, 0xff}}},
    {{{    12,    -28,    -72}, 0, {   218,      0}, {0xc0, 0x01, 0x93, 0xff}}},
    {{{    49,    -58,    -76}, 0, {   976,    -48}, {0x1c, 0xce, 0x8f, 0xff}}},
    {{{    30,    -74,    -99}, 0, {   712,    712}, {0x4a, 0xf4, 0x9a, 0xff}}},
    {{{     8,    -89,   -105}, 0, {   448,   1212}, {0xe7, 0xe1, 0x88, 0xff}}},
    {{{    49,    -58,     77}, 0, {  -330,    -12}, {0x1c, 0xce, 0x71, 0xff}}},
    {{{    12,    -28,     73}, 0, {   854,     -8}, {0xbe, 0x04, 0x6b, 0xff}}},
    {{{     9,    -57,     98}, 0, {   744,   1408}, {0xd5, 0x38, 0x68, 0xff}}},
    {{{    30,    -74,    100}, 0, {    62,   1408}, {0x4a, 0xf4, 0x66, 0xff}}},
    {{{     9,    -57,     98}, 0, {   764,    916}, {0xd5, 0x38, 0x68, 0xff}}},
    {{{     8,    -89,    106}, 0, {   326,   1780}, {0xe7, 0xe1, 0x78, 0xff}}},
    {{{    30,    -74,    100}, 0, {   -10,    916}, {0x4a, 0xf4, 0x66, 0xff}}},
};

// 0x06041758
static const Vtx bowser_seg6_vertex_06041758[] = {
    {{{   123,    -90,     62}, 0, {  1728,     54}, {0x29, 0xbd, 0x63, 0xff}}},
    {{{   126,    -66,     77}, 0, {  1880,    686}, {0x18, 0xe6, 0x79, 0xff}}},
    {{{   102,    -67,     73}, 0, {  2268,    532}, {0x08, 0xc2, 0x6e, 0xff}}},
    {{{   146,    -71,     61}, 0, {  1304,    498}, {0x63, 0xcc, 0x3b, 0xff}}},
    {{{   151,    -43,     67}, 0, {  1304,   1100}, {0x20, 0xbf, 0x67, 0xff}}},
    {{{   114,    -47,     73}, 0, {  2076,    944}, {0x16, 0xef, 0x7b, 0xff}}},
    {{{   146,    -71,     61}, 0, {  1344,    496}, {0x63, 0xcc, 0x3b, 0xff}}},
    {{{   149,    -75,     42}, 0, {   892,    372}, {0x6c, 0xe0, 0x38, 0xff}}},
    {{{   157,    -55,     20}, 0, {   256,    782}, {0x67, 0xbb, 0x16, 0xff}}},
    {{{   151,    -43,     67}, 0, {  1344,   1116}, {0x20, 0xbf, 0x67, 0xff}}},
    {{{   123,    -90,     62}, 0, {  1588,     -8}, {0x29, 0xbd, 0x63, 0xff}}},
};

// 0x06041808
static const Vtx bowser_seg6_vertex_06041808[] = {
    {{{    71,   -107,    -60}, 0, {  2224,    222}, {0xfe, 0xba, 0x97, 0xff}}},
    {{{   123,    -90,    -61}, 0, {  1540,    648}, {0x29, 0xbd, 0x9d, 0xff}}},
    {{{   135,   -116,    -29}, 0, {   940,    -94}, {0x40, 0x9b, 0xd8, 0xff}}},
    {{{    79,    -67,    -68}, 0, {  2072,   1098}, {0x00, 0xc4, 0x91, 0xff}}},
    {{{   155,    -86,    -33}, 0, {   620,    582}, {0x74, 0xde, 0xda, 0xff}}},
    {{{   149,    -75,    -41}, 0, {   780,    844}, {0x6c, 0xe0, 0xc8, 0xff}}},
    {{{   150,    -73,      0}, 0, {    -4,    584}, {0x78, 0xd7, 0x00, 0xff}}},
    {{{   135,   -116,     30}, 0, {   920,   -114}, {0x40, 0x9b, 0x28, 0xff}}},
    {{{   150,    -73,      0}, 0, {   -20,    588}, {0x78, 0xd7, 0x00, 0xff}}},
    {{{   155,    -86,     34}, 0, {   608,    590}, {0x74, 0xde, 0x26, 0xff}}},
    {{{   149,    -75,     42}, 0, {   768,    862}, {0x6c, 0xe0, 0x38, 0xff}}},
    {{{   123,    -90,     62}, 0, {  1528,    660}, {0x29, 0xbd, 0x63, 0xff}}},
    {{{   157,    -55,     20}, 0, {   184,   1136}, {0x67, 0xbb, 0x16, 0xff}}},
    {{{    79,    -67,     69}, 0, {  2068,   1132}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{    71,   -107,     61}, 0, {  2212,    220}, {0xfe, 0xba, 0x69, 0xff}}},
    {{{   157,    -55,    -19}, 0, {   192,   1108}, {0x54, 0xa4, 0xeb, 0xff}}},
};

// 0x06041908
static const Vtx bowser_seg6_vertex_06041908[] = {
    {{{   198,    -43,     64}, 0, {  2516,    214}, {0x3e, 0xc5, 0x5d, 0xff}}},
    {{{   215,    -61,     22}, 0, {  1532,    -22}, {0x5b, 0xab, 0x16, 0xff}}},
    {{{   226,    -26,     25}, 0, {  1588,    746}, {0x79, 0x15, 0x1d, 0xff}}},
    {{{   215,    -61,    -21}, 0, {   468,     -4}, {0x58, 0xac, 0xdf, 0xff}}},
    {{{   226,    -26,    -24}, 0, {   416,    766}, {0x7b, 0x0c, 0xe8, 0xff}}},
    {{{   198,    -43,    -63}, 0, {  -508,    264}, {0x3e, 0xc5, 0xa3, 0xff}}},
};

// 0x06041968
static const Vtx bowser_seg6_vertex_06041968[] = {
    {{{   146,    -71,    -60}, 0, {  1324,    512}, {0x63, 0xcc, 0xc5, 0xff}}},
    {{{   151,    -43,    -66}, 0, {  1500,   1070}, {0x20, 0xbf, 0x99, 0xff}}},
    {{{   157,    -55,    -19}, 0, {   368,    840}, {0x54, 0xa4, 0xeb, 0xff}}},
    {{{    79,    -67,    -68}, 0, {  2616,    500}, {0x00, 0xc4, 0x91, 0xff}}},
    {{{   102,    -67,    -72}, 0, {  2200,    584}, {0x08, 0xc2, 0x92, 0xff}}},
    {{{   123,    -90,    -61}, 0, {  1784,    124}, {0x29, 0xbd, 0x9d, 0xff}}},
    {{{   126,    -66,    -76}, 0, {  1780,    670}, {0x18, 0xe6, 0x87, 0xff}}},
    {{{   114,    -47,    -72}, 0, {  2000,    976}, {0x16, 0xef, 0x85, 0xff}}},
    {{{   146,    -71,    -60}, 0, {  1364,    536}, {0x63, 0xcc, 0xc5, 0xff}}},
    {{{   151,    -43,    -66}, 0, {  1296,   1098}, {0x20, 0xbf, 0x99, 0xff}}},
    {{{   149,    -75,    -41}, 0, {   860,    432}, {0x6c, 0xe0, 0xc8, 0xff}}},
    {{{   123,    -90,    -61}, 0, {  1436,    -76}, {0x29, 0xbd, 0x9d, 0xff}}},
};

// 0x06041A28 - 0x06041BA0
const Gfx bowser_seg6_dl_06041A28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06028438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_060402D8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_060403D8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  6,  9, 10, 0x0),
    gsSP2Triangles(10,  7,  6, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_060404B8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 8, 11,  9, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(12, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_060405B8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(10,  9, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_060406A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_060407A8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06041BA0 - 0x06041E48
const Gfx bowser_seg6_dl_06041BA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06020C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_060407D8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  4,  9, 10, 0x0),
    gsSP2Triangles( 7,  9,  4, 0x0,  0, 11,  1, 0x0),
    gsSP1Triangle( 0, 12, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_060408B8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11,  0, 12, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_060409B8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  3,  1,  6, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 3,  8,  7, 0x0,  6,  9,  3, 0x0),
    gsSP2Triangles(10,  8,  3, 0x0, 11, 10,  3, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040AA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 4,  8,  7, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040BA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  1,  4, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  4,  8,  6, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 10, 12, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040CA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11,  8, 0x0,  8, 12, 10, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040DA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(14, 11, 13, 0x0,  3, 15,  2, 0x0),
    gsSP1Triangle( 2, 15,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040EA8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06040FA8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06041E48 - 0x06041EA8
const Gfx bowser_seg6_dl_06041E48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06024438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06040FD8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  0,  4,  3, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  9, 10,  7, 0x0),
    gsSP1Triangle( 0,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06041EA8 - 0x06041F50
const Gfx bowser_seg6_dl_06041EA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06028C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06041088, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 4,  8,  7, 0x0,  4,  6,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10,  0,  2, 0x0),
    gsSP2Triangles(12,  0, 10, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06041188, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  5,  6,  7, 0x0),
    gsSP1Triangle( 6,  8,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06041F50 - 0x06042098
const Gfx bowser_seg6_dl_06041F50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602AC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06041218, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 13, 12, 0x0),
    gsSP1Triangle(14,  0,  2, 0x0),
    gsSPVertex(bowser_seg6_vertex_06041308, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0,  9, 11,  7, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13,  1,  0, 0x0),
    gsSPVertex(bowser_seg6_vertex_060413E8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(bowser_seg6_vertex_060414D8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_060415D8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06042098 - 0x060420F0
const Gfx bowser_seg6_dl_06042098[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602BC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06041698, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  8,  5, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x060420F0 - 0x06042140
const Gfx bowser_seg6_dl_060420F0[] = {
    gsSPVertex(bowser_seg6_vertex_06041758, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  1,  4, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 8,  9,  6, 0x0,  6, 10,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06042140 - 0x060421D8
const Gfx bowser_seg6_dl_06042140[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0601FC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06041808, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  1,  5,  4, 0x0),
    gsSP2Triangles( 6,  2,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11,  7,  9, 0x0),
    gsSP2Triangles( 8, 12,  9, 0x0,  9, 12, 10, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 14,  7, 11, 0x0),
    gsSP2Triangles( 4, 15,  6, 0x0,  5, 15,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x060421D8 - 0x06042220
const Gfx bowser_seg6_dl_060421D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06021438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06041908, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  4,  3,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06042220 - 0x06042278
const Gfx bowser_seg6_dl_06042220[] = {
    gsSPVertex(bowser_seg6_vertex_06041968, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 5,  6,  8, 0x0,  9,  6,  7, 0x0),
    gsSP2Triangles( 6,  9,  8, 0x0, 10, 11,  0, 0x0),
    gsSP1Triangle( 2, 10,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x06042278 - 0x06042328
const Gfx bowser_seg6_dl_06042278[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06041A28),
    gsSPDisplayList(bowser_seg6_dl_06041BA0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06041E48),
    gsSPDisplayList(bowser_seg6_dl_06041EA8),
    gsSPDisplayList(bowser_seg6_dl_06041F50),
    gsSPDisplayList(bowser_seg6_dl_06042098),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06042140),
    gsSPDisplayList(bowser_seg6_dl_060421D8),
    gsSPEndDisplayList(),
};

// 0x06042328 - 0x06042348
const Gfx bowser_seg6_dl_06042328[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06042348 - 0x060423A0
const Gfx bowser_seg6_dl_06042348[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602CC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06032C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x060423A0 - 0x060423F8
const Gfx bowser_seg6_dl_060423A0[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602DC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06033C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x060423F8 - 0x06042450
const Gfx bowser_seg6_dl_060423F8[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602EC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06034C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x06042450 - 0x060424A8
const Gfx bowser_seg6_dl_06042450[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06031C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06035C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x060424A8 - 0x06042500
const Gfx bowser_seg6_dl_060424A8[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06031C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06036C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x06042500 - 0x06042558
const Gfx bowser_seg6_dl_06042500[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602FC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06037C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x06042558 - 0x060425B0
const Gfx bowser_seg6_dl_06042558[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06030C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06037C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x060425B0 - 0x06042608
const Gfx bowser_seg6_dl_060425B0[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06030C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06036C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x06042608 - 0x06042660
const Gfx bowser_seg6_dl_06042608[] = {
    gsSPDisplayList(bowser_seg6_dl_06042278),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06031C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_060420F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06031C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(bowser_seg6_dl_06042220),
    gsSPDisplayList(bowser_seg6_dl_06042328),
    gsSPEndDisplayList(),
};

// 0x06042660
static const Vtx bowser_seg6_vertex_06042660[] = {
    {{{   -47,    136,    235}, 0, {  2052,    666}, {0x9e, 0xe1, 0x49, 0xff}}},
    {{{   -87,    152,    188}, 0, {   500,    682}, {0x9d, 0xb2, 0x06, 0xff}}},
    {{{   -60,    128,    206}, 0, {  1400,    972}, {0xbc, 0x95, 0xfe, 0xff}}},
    {{{   -51,    176,    218}, 0, {   980,    444}, {0xc8, 0x34, 0x65, 0xff}}},
    {{{   -44,    199,    200}, 0, {   648,    694}, {0xf2, 0x79, 0x22, 0xff}}},
    {{{   -86,    193,    186}, 0, {   440,    230}, {0xac, 0x57, 0x25, 0xff}}},
    {{{   -72,    171,    214}, 0, {   914,    200}, {0xb2, 0x21, 0x5e, 0xff}}},
    {{{   -43,    156,    235}, 0, {  1324,    384}, {0xa8, 0x42, 0x3d, 0xff}}},
    {{{   -25,    198,    164}, 0, {   374,    964}, {0xf2, 0x65, 0xb5, 0xff}}},
    {{{   -72,    171,    214}, 0, {  1022,    220}, {0xb2, 0x21, 0x5e, 0xff}}},
    {{{   -43,    156,    235}, 0, {  1936,    370}, {0xa8, 0x42, 0x3d, 0xff}}},
    {{{   -86,    193,    186}, 0, {   124,     66}, {0xac, 0x57, 0x25, 0xff}}},
    {{{   -93,    163,    165}, 0, {  -112,    668}, {0xba, 0xfc, 0x97, 0xff}}},
    {{{    87,    193,    186}, 0, {  1352,    404}, {0x54, 0x57, 0x25, 0xff}}},
    {{{    45,    199,    200}, 0, {   652,    752}, {0x0e, 0x7c, 0x15, 0xff}}},
    {{{    52,    176,    218}, 0, {   370,    494}, {0x37, 0x33, 0x65, 0xff}}},
};

// 0x06042760
static const Vtx bowser_seg6_vertex_06042760[] = {
    {{{    88,    152,    188}, 0, {  1086,    706}, {0x63, 0xb2, 0x07, 0xff}}},
    {{{    94,    163,    165}, 0, {  1584,    690}, {0x46, 0xfc, 0x97, 0xff}}},
    {{{    87,    193,    186}, 0, {  1502,    252}, {0x54, 0x57, 0x25, 0xff}}},
    {{{    61,    128,    206}, 0, {   296,    856}, {0x44, 0x95, 0xfe, 0xff}}},
    {{{    48,    136,    235}, 0, {  -174,    626}, {0x62, 0xe1, 0x49, 0xff}}},
    {{{    73,    171,    214}, 0, {   744,    352}, {0x4e, 0x21, 0x5e, 0xff}}},
    {{{    44,    156,    235}, 0, {     0,    394}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{    52,    176,    218}, 0, {   370,    494}, {0x37, 0x33, 0x65, 0xff}}},
    {{{    44,    156,    235}, 0, {   -88,    392}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{    73,    171,    214}, 0, {   674,    304}, {0x4e, 0x21, 0x5e, 0xff}}},
    {{{    87,    193,    186}, 0, {  1352,    404}, {0x54, 0x57, 0x25, 0xff}}},
    {{{    45,    199,    200}, 0, {   652,    752}, {0x0e, 0x7c, 0x15, 0xff}}},
    {{{    26,    198,    164}, 0, {   834,   1014}, {0x13, 0x63, 0xb4, 0xff}}},
    {{{     0,    131,    186}, 0, {   494,    742}, {0x00, 0x8d, 0xcb, 0xff}}},
    {{{   -32,    116,    235}, 0, {   298,    908}, {0xd5, 0x94, 0x31, 0xff}}},
    {{{   -60,    128,    206}, 0, {   126,    784}, {0xbc, 0x95, 0xfe, 0xff}}},
};

// 0x06042860
static const Vtx bowser_seg6_vertex_06042860[] = {
    {{{   -47,    136,    235}, 0, {    52,    614}, {0x9e, 0xe1, 0x49, 0xff}}},
    {{{   -32,    116,    235}, 0, {   122,    880}, {0xd5, 0x94, 0x31, 0xff}}},
    {{{   -32,    163,    271}, 0, {  1098,    190}, {0xab, 0x12, 0x5c, 0xff}}},
    {{{   -43,    156,    235}, 0, {   264,    436}, {0xa8, 0x42, 0x3d, 0xff}}},
    {{{   -60,    128,    206}, 0, {   690,    824}, {0xbc, 0x95, 0xfe, 0xff}}},
    {{{   -32,    116,    235}, 0, {  1540,    920}, {0xd5, 0x94, 0x31, 0xff}}},
    {{{   -47,    136,    235}, 0, {  1270,    688}, {0x9e, 0xe1, 0x49, 0xff}}},
    {{{    48,    136,    235}, 0, {   448,    622}, {0x62, 0xe1, 0x49, 0xff}}},
    {{{    44,    156,    235}, 0, {   530,    422}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{    33,    163,    271}, 0, {  1192,    346}, {0x55, 0x12, 0x5c, 0xff}}},
    {{{    33,    116,    235}, 0, {   558,    928}, {0x2b, 0x94, 0x31, 0xff}}},
    {{{    61,    128,    206}, 0, {  -114,    678}, {0x44, 0x95, 0xfe, 0xff}}},
    {{{     0,    137,    271}, 0, {   976,    -80}, {0x00, 0xbd, 0x6b, 0xff}}},
    {{{   -19,    171,    271}, 0, {   406,   -836}, {0xe6, 0x62, 0x4b, 0xff}}},
    {{{   -32,    163,    271}, 0, {    10,   -680}, {0xab, 0x12, 0x5c, 0xff}}},
};

// 0x06042950
static const Vtx bowser_seg6_vertex_06042950[] = {
    {{{   -33,    152,    163}, 0, {   284,    616}, {0xf5, 0xb3, 0x9d, 0xff}}},
    {{{   -87,    152,    188}, 0, {   -42,    668}, {0x9d, 0xb2, 0x06, 0xff}}},
    {{{   -93,    163,    165}, 0, {   -82,    574}, {0xba, 0xfc, 0x97, 0xff}}},
    {{{   -60,    128,    206}, 0, {   126,    784}, {0xbc, 0x95, 0xfe, 0xff}}},
    {{{     0,    131,    186}, 0, {   494,    742}, {0x00, 0x8d, 0xcb, 0xff}}},
    {{{    88,    152,    188}, 0, {  1032,    722}, {0x63, 0xb2, 0x07, 0xff}}},
    {{{    61,    128,    206}, 0, {   864,    822}, {0x44, 0x95, 0xfe, 0xff}}},
    {{{    34,    152,    163}, 0, {   702,    636}, {0x0d, 0xac, 0xa3, 0xff}}},
    {{{    33,    116,    235}, 0, {   698,    928}, {0x2b, 0x94, 0x31, 0xff}}},
    {{{   -32,    116,    235}, 0, {     0,    990}, {0xd5, 0x94, 0x31, 0xff}}},
    {{{     0,    137,    271}, 0, {   976,    -80}, {0x00, 0xbd, 0x6b, 0xff}}},
    {{{   -32,    163,    271}, 0, {    10,   -680}, {0xab, 0x12, 0x5c, 0xff}}},
    {{{    20,    171,    271}, 0, {  1584,   -822}, {0x1a, 0x62, 0x4b, 0xff}}},
    {{{   -19,    171,    271}, 0, {   406,   -836}, {0xe6, 0x62, 0x4b, 0xff}}},
    {{{     0,    131,    186}, 0, {   936,   1490}, {0x00, 0x8d, 0xcb, 0xff}}},
};

// 0x06042A40
static const Vtx bowser_seg6_vertex_06042A40[] = {
    {{{   -33,    177,    155}, 0, {   680,    956}, {0xf5, 0x08, 0x82, 0xff}}},
    {{{   -93,    163,    165}, 0, {  1024,   1084}, {0xba, 0xfc, 0x97, 0xff}}},
    {{{   -25,    198,    164}, 0, {   646,    620}, {0xf2, 0x65, 0xb5, 0xff}}},
    {{{    34,    152,    163}, 0, {   702,    636}, {0x0d, 0xac, 0xa3, 0xff}}},
    {{{   -33,    152,    163}, 0, {   284,    616}, {0xf5, 0xb3, 0x9d, 0xff}}},
    {{{    34,    177,    155}, 0, {   700,    550}, {0x07, 0x07, 0x82, 0xff}}},
    {{{   -33,    177,    155}, 0, {   282,    530}, {0xf5, 0x08, 0x82, 0xff}}},
    {{{   -93,    163,    165}, 0, {   -82,    574}, {0xba, 0xfc, 0x97, 0xff}}},
    {{{    94,    163,    165}, 0, {  1068,    632}, {0x46, 0xfc, 0x97, 0xff}}},
    {{{     0,    137,    271}, 0, {   976,    -80}, {0x00, 0xbd, 0x6b, 0xff}}},
    {{{    33,    116,    235}, 0, {  1930,   1014}, {0x2b, 0x94, 0x31, 0xff}}},
    {{{    33,    163,    271}, 0, {  1972,   -654}, {0x55, 0x12, 0x5c, 0xff}}},
    {{{    20,    171,    271}, 0, {  1584,   -822}, {0x1a, 0x62, 0x4b, 0xff}}},
    {{{    88,    152,    188}, 0, {  1032,    722}, {0x63, 0xb2, 0x07, 0xff}}},
    {{{   -86,    193,    186}, 0, {  1002,    568}, {0xac, 0x57, 0x25, 0xff}}},
    {{{     0,    131,    186}, 0, {   936,   1490}, {0x00, 0x8d, 0xcb, 0xff}}},
};

// 0x06042B40
static const Vtx bowser_seg6_vertex_06042B40[] = {
    {{{   -25,    198,    164}, 0, {   646,    620}, {0xf2, 0x65, 0xb5, 0xff}}},
    {{{    26,    198,    164}, 0, {   344,    624}, {0x13, 0x63, 0xb4, 0xff}}},
    {{{    34,    177,    155}, 0, {   284,    960}, {0x07, 0x07, 0x82, 0xff}}},
    {{{    87,    193,    186}, 0, {    -8,    582}, {0x54, 0x57, 0x25, 0xff}}},
    {{{    94,    163,    165}, 0, {   -66,   1096}, {0x46, 0xfc, 0x97, 0xff}}},
    {{{   -33,    177,    155}, 0, {   680,    956}, {0xf5, 0x08, 0x82, 0xff}}},
    {{{     0,    164,    240}, 0, {   454,    680}, {0x00, 0x75, 0x30, 0xff}}},
    {{{   -19,    171,    271}, 0, {   120,   1010}, {0xe6, 0x62, 0x4b, 0xff}}},
    {{{    20,    171,    271}, 0, {   882,   1020}, {0x1a, 0x62, 0x4b, 0xff}}},
    {{{    33,    163,    271}, 0, {  1134,   1010}, {0x55, 0x12, 0x5c, 0xff}}},
    {{{   -32,    163,    271}, 0, {  -134,    990}, {0xab, 0x12, 0x5c, 0xff}}},
};

// 0x06042BF0
static const Vtx bowser_seg6_vertex_06042BF0[] = {
    {{{     0,    164,    240}, 0, {  1444,     64}, {0x00, 0x75, 0x30, 0xff}}},
    {{{    30,    189,    252}, 0, {   414,   1972}, {0x1f, 0x7a, 0x00, 0xff}}},
    {{{    44,    156,    235}, 0, {  -244,    -44}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{   -32,    163,    271}, 0, {   950,     80}, {0xab, 0x12, 0x5c, 0xff}}},
    {{{   -29,    189,    252}, 0, {   462,   1936}, {0xe2, 0x7b, 0x00, 0xff}}},
    {{{   -43,    156,    235}, 0, {  -200,     20}, {0xa8, 0x42, 0x3d, 0xff}}},
    {{{     0,    164,    240}, 0, {  1276,     48}, {0x00, 0x75, 0x30, 0xff}}},
    {{{   -29,    189,    252}, 0, {   458,   2016}, {0xe2, 0x7b, 0x00, 0xff}}},
    {{{   -32,    163,    271}, 0, {  -322,     36}, {0xab, 0x12, 0x5c, 0xff}}},
    {{{   -43,    156,    235}, 0, {  1708,     36}, {0xa8, 0x42, 0x3d, 0xff}}},
    {{{   -29,    189,    252}, 0, {   692,   1880}, {0xe2, 0x7b, 0x00, 0xff}}},
    {{{     0,    164,    240}, 0, {  -310,     -8}, {0x00, 0x75, 0x30, 0xff}}},
    {{{    44,    156,    235}, 0, {  1894,     48}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{    30,    189,    252}, 0, {   518,   2028}, {0x1f, 0x7a, 0x00, 0xff}}},
    {{{    33,    163,    271}, 0, {    74,     24}, {0x55, 0x12, 0x5c, 0xff}}},
};

// 0x06042CE0
static const Vtx bowser_seg6_vertex_06042CE0[] = {
    {{{    33,    184,    224}, 0, { -1314,     60}, {0xf8, 0x4a, 0x66, 0xff}}},
    {{{    52,    176,    218}, 0, {  1222,      0}, {0x37, 0x33, 0x65, 0xff}}},
    {{{    50,    205,    232}, 0, {   414,   1972}, {0x2c, 0x34, 0x6a, 0xff}}},
    {{{   -44,    199,    200}, 0, {  1324,    -28}, {0xf2, 0x79, 0x22, 0xff}}},
    {{{   -49,    205,    232}, 0, {   426,   1976}, {0xd4, 0x34, 0x6a, 0xff}}},
    {{{   -32,    184,    224}, 0, {  -210,     68}, {0x0a, 0x3f, 0x6d, 0xff}}},
    {{{   -51,    176,    218}, 0, {  1338,     16}, {0xc8, 0x34, 0x65, 0xff}}},
    {{{   -49,    205,    232}, 0, {   676,   1904}, {0xd4, 0x34, 0x6a, 0xff}}},
    {{{   -44,    199,    200}, 0, {  -170,     28}, {0xf2, 0x79, 0x22, 0xff}}},
    {{{    45,    199,    200}, 0, {  1244,     68}, {0x0e, 0x7c, 0x15, 0xff}}},
    {{{    50,    205,    232}, 0, {   180,   1944}, {0x2c, 0x34, 0x6a, 0xff}}},
    {{{    52,    176,    218}, 0, {  -560,     32}, {0x37, 0x33, 0x65, 0xff}}},
    {{{    33,    184,    224}, 0, {  1218,     36}, {0xf8, 0x4a, 0x66, 0xff}}},
    {{{    50,    205,    232}, 0, {   678,   1928}, {0x2c, 0x34, 0x6a, 0xff}}},
    {{{    45,    199,    200}, 0, {  -110,      0}, {0x0e, 0x7c, 0x15, 0xff}}},
};

// 0x06042DD0
static const Vtx bowser_seg6_vertex_06042DD0[] = {
    {{{   -32,    184,    224}, 0, {  1122,     92}, {0x0a, 0x3f, 0x6d, 0xff}}},
    {{{   -49,    205,    232}, 0, {   524,   1920}, {0xd4, 0x34, 0x6a, 0xff}}},
    {{{   -51,    176,    218}, 0, {   -30,     16}, {0xc8, 0x34, 0x65, 0xff}}},
    {{{    33,    163,    271}, 0, {  1524,     20}, {0x55, 0x12, 0x5c, 0xff}}},
    {{{    30,    189,    252}, 0, {   634,   1952}, {0x1f, 0x7a, 0x00, 0xff}}},
    {{{     0,    164,    240}, 0, {  -788,    -56}, {0x00, 0x75, 0x30, 0xff}}},
};

// 0x06042E30
static const Vtx bowser_seg6_vertex_06042E30[] = {
    {{{    45,    199,    200}, 0, {   862,    688}, {0x0e, 0x7c, 0x15, 0xff}}},
    {{{    26,    198,    164}, 0, {   700,    204}, {0x13, 0x63, 0xb4, 0xff}}},
    {{{   -25,    198,    164}, 0, {   270,    224}, {0xf2, 0x65, 0xb5, 0xff}}},
    {{{    33,    184,    224}, 0, {   762,   1152}, {0xf8, 0x4a, 0x66, 0xff}}},
    {{{   -44,    199,    200}, 0, {   116,    720}, {0xf2, 0x79, 0x22, 0xff}}},
    {{{   -32,    184,    224}, 0, {   226,   1176}, {0x0a, 0x3f, 0x6d, 0xff}}},
    {{{     0,    164,    240}, 0, {   498,   1548}, {0x00, 0x75, 0x30, 0xff}}},
    {{{   -51,    176,    218}, 0, {    64,   1168}, {0xc8, 0x34, 0x65, 0xff}}},
    {{{    52,    176,    218}, 0, {   926,   1128}, {0x37, 0x33, 0x65, 0xff}}},
    {{{    44,    156,    235}, 0, {   860,   1532}, {0x58, 0x42, 0x3d, 0xff}}},
    {{{   -43,    156,    235}, 0, {   138,   1564}, {0xa8, 0x42, 0x3d, 0xff}}},
};

// 0x06042EE0 - 0x060430E8
const Gfx bowser_seg6_dl_06042EE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06020C38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_06042660, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  6,  7,  3, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  1,  0,  9, 0x0),
    gsSP2Triangles( 0, 10,  9, 0x0,  9, 11,  1, 0x0),
    gsSP2Triangles( 1, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042760, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  5,  4, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11, 10, 12, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042860, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 10, 11,  7, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042950, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 7,  5,  4, 0x0,  6,  8,  4, 0x0),
    gsSP2Triangles( 0,  7,  4, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10, 12, 13, 0x0, 14, 10,  9, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042A40, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 3,  5,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0, 13,  3,  8, 0x0),
    gsSP2Triangles( 2,  1, 14, 0x0, 15, 10,  9, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042B40, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP1Triangle(10,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x060430E8 - 0x06043180
const Gfx bowser_seg6_dl_060430E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_0602AC38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06042BF0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042CE0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_seg6_vertex_06042DD0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06043180 - 0x060431F0
const Gfx bowser_seg6_dl_06043180[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06024438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_06042E30, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  4,  5,  3, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 8,  3,  6, 0x0,  6,  9,  8, 0x0),
    gsSP1Triangle(10,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x060431F0 - 0x06043278
const Gfx bowser_seg6_dl_060431F0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06042EE0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_060430E8),
    gsSPDisplayList(bowser_seg6_dl_06043180),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06043278
static const Vtx bowser_seg6_vertex_06043278[] = {
    {{{    56,    -17,     50}, 0, {  -258,   -386}, {0x15, 0x3d, 0x6d, 0xff}}},
    {{{   122,     13,      0}, 0, {   486,    530}, {0xfd, 0x7e, 0x00, 0xff}}},
    {{{    44,      7,      0}, 0, {   462,   -604}, {0x17, 0x7c, 0x00, 0xff}}},
    {{{    16,     20,      0}, 0, {   452,  -1014}, {0xd7, 0x78, 0x00, 0xff}}},
    {{{     7,     -7,     60}, 0, {  -410,  -1108}, {0xd1, 0x37, 0x67, 0xff}}},
    {{{   127,      3,     35}, 0, {   -18,    620}, {0x25, 0x26, 0x73, 0xff}}},
    {{{    56,    -17,    -49}, 0, {  1190,   -428}, {0x15, 0x3c, 0x93, 0xff}}},
    {{{     7,     -7,    -59}, 0, {  1310,  -1160}, {0xd1, 0x37, 0x99, 0xff}}},
    {{{    74,    -55,     31}, 0, {  1284,    -20}, {0x1f, 0x95, 0x3c, 0xff}}},
    {{{   127,      3,     35}, 0, {  2312,   1266}, {0x25, 0x26, 0x73, 0xff}}},
    {{{    56,    -17,     50}, 0, {   942,    780}, {0x15, 0x3d, 0x6d, 0xff}}},
    {{{    -8,    -52,     37}, 0, {  -322,      6}, {0xb0, 0xc8, 0x4f, 0xff}}},
    {{{     7,     -7,     60}, 0, {   -12,    974}, {0xd1, 0x37, 0x67, 0xff}}},
    {{{    16,     20,      0}, 0, {   692,    878}, {0xd7, 0x78, 0x00, 0xff}}},
    {{{    -8,    -52,    -36}, 0, {   708,    270}, {0xbc, 0xac, 0xbe, 0xff}}},
    {{{    -8,    -52,     37}, 0, {   304,    466}, {0xb0, 0xc8, 0x4f, 0xff}}},
};

// 0x06043378
static const Vtx bowser_seg6_vertex_06043378[] = {
    {{{    -8,    -52,    -36}, 0, {  -322,      6}, {0xbc, 0xac, 0xbe, 0xff}}},
    {{{    56,    -17,    -49}, 0, {   942,    780}, {0x15, 0x3c, 0x93, 0xff}}},
    {{{    74,    -55,    -30}, 0, {  1284,    -20}, {0x1e, 0xa6, 0xad, 0xff}}},
    {{{     7,     -7,    -59}, 0, {   -12,    974}, {0xd1, 0x37, 0x99, 0xff}}},
    {{{   127,      3,    -34}, 0, {  2312,   1266}, {0x24, 0x25, 0x8d, 0xff}}},
    {{{   122,     13,      0}, 0, {   486,    530}, {0xfd, 0x7e, 0x00, 0xff}}},
    {{{   127,      3,    -34}, 0, {   996,    588}, {0x24, 0x25, 0x8d, 0xff}}},
    {{{    56,    -17,    -49}, 0, {  1190,   -428}, {0x15, 0x3c, 0x93, 0xff}}},
    {{{    16,     20,      0}, 0, {   692,    878}, {0xd7, 0x78, 0x00, 0xff}}},
    {{{    -8,    -52,     37}, 0, {   304,    466}, {0xb0, 0xc8, 0x4f, 0xff}}},
    {{{     7,     -7,     60}, 0, {   294,    844}, {0xd1, 0x37, 0x67, 0xff}}},
    {{{     7,     -7,    -59}, 0, {   946,    524}, {0xd1, 0x37, 0x99, 0xff}}},
    {{{    -8,    -52,    -36}, 0, {   708,    270}, {0xbc, 0xac, 0xbe, 0xff}}},
    {{{   127,      3,     35}, 0, {   -18,    620}, {0x25, 0x26, 0x73, 0xff}}},
    {{{   187,      0,      0}, 0, {   508,   1472}, {0x77, 0x2a, 0x00, 0xff}}},
};

// 0x06043468
static const Vtx bowser_seg6_vertex_06043468[] = {
    {{{   127,      3,    -34}, 0, {  2312,   1266}, {0x24, 0x25, 0x8d, 0xff}}},
    {{{   187,      0,      0}, 0, {  3464,   1198}, {0x77, 0x2a, 0x00, 0xff}}},
    {{{   140,    -23,    -21}, 0, {  2558,    694}, {0x3e, 0xa0, 0xcb, 0xff}}},
    {{{    74,    -55,    -30}, 0, {  1284,    -20}, {0x1e, 0xa6, 0xad, 0xff}}},
    {{{    74,    -55,     31}, 0, {  1284,    -20}, {0x1f, 0x95, 0x3c, 0xff}}},
    {{{   140,    -23,     22}, 0, {  2558,    694}, {0x3e, 0xa9, 0x43, 0xff}}},
    {{{   127,      3,     35}, 0, {  2312,   1266}, {0x25, 0x26, 0x73, 0xff}}},
};

// 0x060434D8
static const Vtx bowser_seg6_vertex_060434D8[] = {
    {{{   140,    -23,    -21}, 0, {   308,  -1804}, {0x3e, 0xa0, 0xcb, 0xff}}},
    {{{   187,      0,      0}, 0, {   734,  -2986}, {0x77, 0x2a, 0x00, 0xff}}},
    {{{   140,    -23,     22}, 0, {   982,  -1816}, {0x3e, 0xa9, 0x43, 0xff}}},
    {{{    -8,    -52,     37}, 0, {   966,   1500}, {0xb0, 0xc8, 0x4f, 0xff}}},
    {{{    -8,    -52,    -36}, 0, {  -178,   1520}, {0xbc, 0xac, 0xbe, 0xff}}},
    {{{    74,    -55,     31}, 0, {  1002,   -166}, {0x1f, 0x95, 0x3c, 0xff}}},
    {{{    74,    -55,    -30}, 0, {    36,   -150}, {0x1e, 0xa6, 0xad, 0xff}}},
};

// 0x06043548 - 0x06043648
const Gfx bowser_seg6_dl_06043548[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06022438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_seg6_lights_06038C38.l, 1),
    gsSPLight(&bowser_seg6_lights_06038C38.a, 2),
    gsSPVertex(bowser_seg6_vertex_06043278, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 2,  1,  6, 0x0,  3,  2,  6, 0x0),
    gsSP2Triangles( 7,  3,  6, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11,  8, 10, 0x0, 10, 12, 11, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(bowser_seg6_vertex_06043378, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11, 12, 0x0),
    gsSP2Triangles(13, 14,  5, 0x0,  5, 14,  6, 0x0),
    gsSPVertex(bowser_seg6_vertex_06043468, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  1,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06043648 - 0x06043698
const Gfx bowser_seg6_dl_06043648[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_seg6_texture_06025438),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_seg6_vertex_060434D8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  6,  0,  5, 0x0),
    gsSP1Triangle( 0,  2,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06043698 - 0x06043700
const Gfx bowser_seg6_dl_06043698[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_seg6_dl_06043548),
    gsSPDisplayList(bowser_seg6_dl_06043648),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
