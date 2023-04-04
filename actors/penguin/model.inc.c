// Penguin

// 0x05002D80
static const Lights1 penguin_seg5_lights_05002D80 = gdSPDefLights1(
    0x06, 0x06, 0x39,
    0x0f, 0x0f, 0x90, 0x28, 0x28, 0x28
);

// 0x05002D98
static const Lights1 penguin_seg5_lights_05002D98 = gdSPDefLights1(
    0x52, 0x34, 0x0a,
    0xce, 0x84, 0x1a, 0x28, 0x28, 0x28
);

// 0x05002DB0
static const Lights1 penguin_seg5_lights_05002DB0 = gdSPDefLights1(
    0x59, 0x42, 0x14,
    0xdf, 0xa7, 0x34, 0x28, 0x28, 0x28
);

// 0x05002DC8
static const Lights1 penguin_seg5_lights_05002DC8 = gdSPDefLights1(
    0x66, 0x66, 0x65,
    0xff, 0xff, 0xfd, 0x28, 0x28, 0x28
);

// 0x05002DE0
ALIGNED8 static const Texture penguin_seg5_texture_05002DE0[] = {
#include "actors/penguin/penguin_eye_open.rgba16.inc.c"
};

// 0x050035E0
ALIGNED8 static const Texture penguin_seg5_texture_050035E0[] = {
#include "actors/penguin/penguin_eye_half_closed.rgba16.inc.c"
};

// 0x05003DE0
ALIGNED8 static const Texture penguin_seg5_texture_05003DE0[] = {
#include "actors/penguin/penguin_eye_closed.rgba16.inc.c"
};

// 0x050045E0
ALIGNED8 static const Texture penguin_seg5_texture_050045E0[] = {
#include "actors/penguin/penguin_eye_angry.rgba16.inc.c"
};

// 0x05004DE0
ALIGNED8 static const Texture penguin_seg5_texture_05004DE0[] = {
#include "actors/penguin/penguin_eye_angry_unused.rgba16.inc.c"
};

// 0x050055E0
ALIGNED8 static const Texture penguin_seg5_texture_050055E0[] = {
#include "actors/penguin/penguin_beak.rgba16.inc.c"
};

// 0x05005DE0
static const Lights1 penguin_seg5_lights_05005DE0 = gdSPDefLights1(
    0x04, 0x0f, 0x41,
    0x0b, 0x26, 0xa4, 0x28, 0x28, 0x28
);

// 0x05005DF8
static const Lights1 penguin_seg5_lights_05005DF8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05005E10
static const Lights1 penguin_seg5_lights_05005E10 = gdSPDefLights1(
    0x60, 0x55, 0x0b,
    0xf2, 0xd5, 0x1c, 0x28, 0x28, 0x28
);

// 0x05005E28
static const Vtx penguin_seg5_vertex_05005E28[] = {
    {{{    27,     97,     36}, 0, {   484,   1164}, {0xac, 0x4a, 0x3a, 0xff}}},
    {{{    79,     90,     42}, 0, {   696,   -154}, {0x32, 0x64, 0x3b, 0xff}}},
    {{{    57,    105,      0}, 0, {  -440,    404}, {0x31, 0x75, 0x00, 0xff}}},
    {{{    41,     76,     60}, 0, {  1270,    804}, {0xcc, 0x25, 0x6d, 0xff}}},
    {{{    41,     76,    -59}, 0, {  1262,    804}, {0xcc, 0x25, 0x93, 0xff}}},
    {{{    27,     97,    -35}, 0, {   476,   1164}, {0xbc, 0x50, 0xba, 0xff}}},
    {{{    79,     90,    -41}, 0, {   688,   -154}, {0x32, 0x64, 0xc5, 0xff}}},
    {{{    57,    105,      0}, 0, {  -448,    404}, {0x31, 0x75, 0x00, 0xff}}},
};

// 0x05005EA8
static const Vtx penguin_seg5_vertex_05005EA8[] = {
    {{{    26,    154,      0}, 0, {  -468,   1134}, {0x2c, 0x77, 0xff, 0xff}}},
    {{{    27,     97,     36}, 0, {  1974,   1130}, {0xac, 0x4a, 0x3a, 0xff}}},
    {{{    57,    105,      0}, 0, {  1030,   -328}, {0x31, 0x75, 0x00, 0xff}}},
    {{{    27,     97,    -35}, 0, {  1974,   1130}, {0xbc, 0x50, 0xba, 0xff}}},
};

// 0x05005EE8
static const Vtx penguin_seg5_vertex_05005EE8[] = {
    {{{    93,     17,     53}, 0, {     0,      0}, {0x5a, 0xea, 0x56, 0xff}}},
    {{{   110,     55,     26}, 0, {     0,      0}, {0x76, 0x20, 0x1f, 0xff}}},
    {{{    92,     64,     53}, 0, {     0,      0}, {0x3d, 0x1e, 0x6a, 0xff}}},
    {{{    79,     90,     42}, 0, {     0,      0}, {0x32, 0x64, 0x3b, 0xff}}},
    {{{   110,     55,    -25}, 0, {     0,      0}, {0x76, 0x20, 0xe1, 0xff}}},
    {{{   105,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
    {{{    82,     99,      0}, 0, {     0,      0}, {0x4e, 0x63, 0x00, 0xff}}},
    {{{    92,     64,    -52}, 0, {     0,      0}, {0x3d, 0x1e, 0x96, 0xff}}},
    {{{    79,     90,    -41}, 0, {     0,      0}, {0x32, 0x64, 0xc5, 0xff}}},
    {{{    93,     17,    -52}, 0, {     0,      0}, {0x5a, 0xeb, 0xaa, 0xff}}},
    {{{    57,    105,      0}, 0, {     0,      0}, {0x31, 0x75, 0x00, 0xff}}},
    {{{    41,     76,    -59}, 0, {     0,      0}, {0xcc, 0x25, 0x93, 0xff}}},
    {{{    41,     76,     60}, 0, {     0,      0}, {0xcc, 0x25, 0x6d, 0xff}}},
    {{{    43,      4,    -61}, 0, {     0,      0}, {0xf0, 0xe3, 0x86, 0xff}}},
    {{{    18,     10,     37}, 0, {     0,      0}, {0x89, 0xf9, 0x29, 0xff}}},
    {{{    43,      4,     62}, 0, {     0,      0}, {0xf0, 0xe3, 0x7a, 0xff}}},
};

// 0x05005FE8
static const Vtx penguin_seg5_vertex_05005FE8[] = {
    {{{    18,     10,    -36}, 0, {     0,      0}, {0x8c, 0xf9, 0xcf, 0xff}}},
    {{{    28,    -96,      0}, 0, {     0,      0}, {0xc0, 0x93, 0x00, 0xff}}},
    {{{    18,     10,     37}, 0, {     0,      0}, {0x89, 0xf9, 0x29, 0xff}}},
    {{{    16,     58,    -19}, 0, {     0,      0}, {0x84, 0x0d, 0xea, 0xff}}},
    {{{    43,      4,     62}, 0, {     0,      0}, {0xf0, 0xe3, 0x7a, 0xff}}},
    {{{    41,     76,     60}, 0, {     0,      0}, {0xcc, 0x25, 0x6d, 0xff}}},
    {{{    16,     58,     20}, 0, {     0,      0}, {0x87, 0x0c, 0x21, 0xff}}},
    {{{    27,     97,     36}, 0, {     0,      0}, {0xac, 0x4a, 0x3a, 0xff}}},
    {{{    27,     97,    -35}, 0, {     0,      0}, {0xbc, 0x50, 0xba, 0xff}}},
    {{{    41,     76,    -59}, 0, {     0,      0}, {0xcc, 0x25, 0x93, 0xff}}},
    {{{    92,     64,     53}, 0, {     0,      0}, {0x3d, 0x1e, 0x6a, 0xff}}},
    {{{    43,      4,    -61}, 0, {     0,      0}, {0xf0, 0xe3, 0x86, 0xff}}},
    {{{    91,    -35,    -28}, 0, {     0,      0}, {0x58, 0xbd, 0xc4, 0xff}}},
    {{{    92,     64,    -52}, 0, {     0,      0}, {0x3d, 0x1e, 0x96, 0xff}}},
    {{{    93,     17,    -52}, 0, {     0,      0}, {0x5a, 0xeb, 0xaa, 0xff}}},
    {{{   105,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
};

// 0x050060E8
static const Vtx penguin_seg5_vertex_050060E8[] = {
    {{{    28,    -96,      0}, 0, {     0,      0}, {0xc0, 0x93, 0x00, 0xff}}},
    {{{    91,    -35,    -28}, 0, {     0,      0}, {0x58, 0xbd, 0xc4, 0xff}}},
    {{{    91,    -35,     29}, 0, {     0,      0}, {0x58, 0xbc, 0x3b, 0xff}}},
    {{{   105,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
    {{{    43,      4,     62}, 0, {     0,      0}, {0xf0, 0xe3, 0x7a, 0xff}}},
    {{{    93,     17,     53}, 0, {     0,      0}, {0x5a, 0xea, 0x56, 0xff}}},
    {{{    92,     64,     53}, 0, {     0,      0}, {0x3d, 0x1e, 0x6a, 0xff}}},
};

// 0x05006158
static const Vtx penguin_seg5_vertex_05006158[] = {
    {{{    27,     97,     36}, 0, {     0,      0}, {0xac, 0x4a, 0x3a, 0xff}}},
    {{{    26,    154,      0}, 0, {     0,      0}, {0x2c, 0x77, 0xff, 0xff}}},
    {{{    27,     97,    -35}, 0, {     0,      0}, {0xbc, 0x50, 0xba, 0xff}}},
};

// 0x05006188 - 0x050061C8
const Gfx penguin_seg5_dl_05006188[] = {
    gsSPLight(&penguin_seg5_lights_05005DE0.l, 1),
    gsSPLight(&penguin_seg5_lights_05005DE0.a, 2),
    gsSPVertex(penguin_seg5_vertex_05005E28, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x050061C8 - 0x050061F8
const Gfx penguin_seg5_dl_050061C8[] = {
    gsSPLight(&penguin_seg5_lights_05005DF8.l, 1),
    gsSPLight(&penguin_seg5_lights_05005DF8.a, 2),
    gsSPVertex(penguin_seg5_vertex_05005EA8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x050061F8 - 0x05006380
const Gfx penguin_seg5_dl_050061F8[] = {
    gsSPLight(&penguin_seg5_lights_05005DE0.l, 1),
    gsSPLight(&penguin_seg5_lights_05005DE0.a, 2),
    gsSPVertex(penguin_seg5_vertex_05005EE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  1,  5, 0x0,  1,  4,  6, 0x0),
    gsSP2Triangles( 6,  3,  1, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 7,  8,  4, 0x0,  4,  5,  9, 0x0),
    gsSP2Triangles( 4,  8,  6, 0x0,  9,  7,  4, 0x0),
    gsSP2Triangles( 6,  8, 10, 0x0, 10,  3,  6, 0x0),
    gsSP2Triangles(11,  8,  7, 0x0, 12,  2,  3, 0x0),
    gsSP2Triangles(13, 11,  7, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(penguin_seg5_vertex_05005FE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  6,  2, 0x0),
    gsSP2Triangles( 2,  6,  3, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 7,  8,  3, 0x0,  0,  3,  9, 0x0),
    gsSP2Triangles( 5,  4, 10, 0x0, 11,  0,  9, 0x0),
    gsSP2Triangles(11,  1,  0, 0x0, 12,  1, 11, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 11, 14, 12, 0x0),
    gsSP1Triangle(15, 12, 14, 0x0),
    gsSPVertex(penguin_seg5_vertex_050060E8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  5,  2,  3, 0x0),
    gsSP2Triangles( 2,  5,  4, 0x0,  4,  5,  6, 0x0),
    gsSPLight(&penguin_seg5_lights_05005E10.l, 1),
    gsSPLight(&penguin_seg5_lights_05005E10.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006158, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006380 - 0x050063C8
const Gfx penguin_seg5_dl_05006380[] = {
    gsDPPipeSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBA, G_CC_BLENDRGBA),
    gsSPEndDisplayList(),
};

// 0x050063C8 - 0x05006428
const Gfx penguin_seg5_dl_050063C8[] = {
    gsSPDisplayList(penguin_seg5_dl_05006188),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_050055E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050061C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(penguin_seg5_dl_050061F8),
    gsSPEndDisplayList(),
};

// 0x05006428 - 0x05006458
const Gfx penguin_seg5_dl_05006428[] = {
    gsSPDisplayList(penguin_seg5_dl_05006380),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_05002DE0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050063C8),
    gsSPEndDisplayList(),
};

// 0x05006458 - 0x05006488
const Gfx penguin_seg5_dl_05006458[] = {
    gsSPDisplayList(penguin_seg5_dl_05006380),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_050035E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050063C8),
    gsSPEndDisplayList(),
};

// 0x05006488 - 0x050064B8
const Gfx penguin_seg5_dl_05006488[] = {
    gsSPDisplayList(penguin_seg5_dl_05006380),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_05003DE0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050063C8),
    gsSPEndDisplayList(),
};

// 0x050064B8 - 0x050064E8
const Gfx penguin_seg5_dl_050064B8[] = {
    gsSPDisplayList(penguin_seg5_dl_05006380),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_050045E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050063C8),
    gsSPEndDisplayList(),
};

// 0x050064E8 - 0x05006518
const Gfx penguin_seg5_dl_050064E8[] = {
    gsSPDisplayList(penguin_seg5_dl_05006380),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, penguin_seg5_texture_05004DE0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(penguin_seg5_dl_050063C8),
    gsSPEndDisplayList(),
};

// 0x05006518
static const Vtx penguin_seg5_vertex_05006518[] = {
    {{{   -13,     -3,     -4}, 0, {     0,      0}, {0x01, 0x00, 0x7f, 0x00}}},
    {{{   110,    -22,     -9}, 0, {     0,      0}, {0x02, 0xef, 0x7d, 0x00}}},
    {{{   136,     16,     -8}, 0, {     0,      0}, {0x01, 0x0d, 0x7e, 0x00}}},
    {{{     8,     25,    -12}, 0, {     0,      0}, {0xf0, 0x04, 0x83, 0xff}}},
    {{{   136,     16,     -8}, 0, {     0,      0}, {0x04, 0x01, 0x82, 0xff}}},
    {{{   110,    -22,     -9}, 0, {     0,      0}, {0x03, 0x00, 0x81, 0xff}}},
    {{{    -7,    -33,    -11}, 0, {     0,      0}, {0xe5, 0x05, 0x85, 0xff}}},
    {{{   -13,     -3,     -4}, 0, {     0,      0}, {0xc9, 0x0d, 0x8f, 0xff}}},
    {{{     8,     25,    -12}, 0, {     0,      0}, {0xff, 0x21, 0x7a, 0xff}}},
    {{{    -7,    -33,    -11}, 0, {     0,      0}, {0x00, 0xe4, 0x7b, 0xff}}},
};

// 0x050065B8
static const Vtx penguin_seg5_vertex_050065B8[] = {
    {{{   110,    -22,      9}, 0, {     0,      0}, {0x02, 0xef, 0x83, 0x00}}},
    {{{    -7,    -33,     11}, 0, {     0,      0}, {0x00, 0xe4, 0x85, 0x00}}},
    {{{   -14,     -3,      4}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
    {{{   136,     16,      8}, 0, {     0,      0}, {0x01, 0x0d, 0x82, 0xff}}},
    {{{     8,     25,     12}, 0, {     0,      0}, {0xff, 0x21, 0x86, 0xff}}},
    {{{   -14,     -3,      4}, 0, {     0,      0}, {0xc9, 0x0d, 0x71, 0xff}}},
    {{{    -7,    -33,     11}, 0, {     0,      0}, {0xe5, 0x05, 0x7b, 0xff}}},
    {{{     8,     25,     12}, 0, {     0,      0}, {0xf0, 0x04, 0x7d, 0xff}}},
    {{{   110,    -22,      9}, 0, {     0,      0}, {0x03, 0x00, 0x7f, 0xff}}},
    {{{   136,     16,      8}, 0, {     0,      0}, {0x04, 0x01, 0x7e, 0xff}}},
};

// 0x05006658
static const Vtx penguin_seg5_vertex_05006658[] = {
    {{{   -16,     -2,     -1}, 0, {     0,      0}, {0xca, 0x8e, 0x00, 0x00}}},
    {{{    46,     -8,     -1}, 0, {     0,      0}, {0x02, 0x81, 0x00, 0x00}}},
    {{{    46,     16,     37}, 0, {     0,      0}, {0x04, 0x9e, 0x50, 0x00}}},
    {{{   -21,     16,    -19}, 0, {     0,      0}, {0xa6, 0xb9, 0xcc, 0xff}}},
    {{{    46,     16,    -41}, 0, {     0,      0}, {0x04, 0x9e, 0xb0, 0xff}}},
    {{{    99,     16,    -16}, 0, {     0,      0}, {0x5f, 0xbf, 0xcd, 0xff}}},
    {{{    94,     -1,     -1}, 0, {     0,      0}, {0x40, 0x93, 0x00, 0xff}}},
    {{{    99,     16,     12}, 0, {     0,      0}, {0x5f, 0xbf, 0x33, 0xff}}},
    {{{    46,     16,     37}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    99,     16,     12}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    46,     16,    -41}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -21,     16,     15}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -21,     16,     15}, 0, {     0,      0}, {0xa6, 0xb9, 0x34, 0xff}}},
    {{{   -21,     16,    -19}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    99,     16,    -16}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x05006748
static const Vtx penguin_seg5_vertex_05006748[] = {
    {{{    99,     16,    -12}, 0, {     0,      0}, {0x5f, 0xbf, 0xcd, 0x00}}},
    {{{    99,     16,     16}, 0, {     0,      0}, {0x5f, 0xbf, 0x33, 0x00}}},
    {{{    94,     -1,      1}, 0, {     0,      0}, {0x40, 0x93, 0x00, 0x00}}},
    {{{   -21,     16,     19}, 0, {     0,      0}, {0xa6, 0xb9, 0x34, 0xff}}},
    {{{   -21,     16,    -15}, 0, {     0,      0}, {0xa6, 0xb9, 0xcc, 0xff}}},
    {{{   -16,     -2,      1}, 0, {     0,      0}, {0xca, 0x8e, 0x00, 0xff}}},
    {{{    46,     16,    -37}, 0, {     0,      0}, {0x04, 0x9e, 0xb0, 0xff}}},
    {{{   -21,     16,    -15}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    46,     16,     41}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    46,     16,    -37}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    46,     16,     41}, 0, {     0,      0}, {0x04, 0x9e, 0x50, 0xff}}},
    {{{    46,     -8,      1}, 0, {     0,      0}, {0x02, 0x81, 0x00, 0xff}}},
    {{{    99,     16,    -12}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    99,     16,     16}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -21,     16,     19}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x05006838
static const Vtx penguin_seg5_vertex_05006838[] = {
    {{{    15,    153,      0}, 0, {     0,      0}, {0x6a, 0x45, 0x00, 0x00}}},
    {{{    16,     96,     36}, 0, {     0,      0}, {0xab, 0x4a, 0x39, 0x00}}},
    {{{    46,    105,      0}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0x00}}},
    {{{    16,     96,    -36}, 0, {     0,      0}, {0xbb, 0x50, 0xbb, 0xff}}},
    {{{    15,    153,      0}, 0, {     0,      0}, {0x81, 0xfd, 0x00, 0xff}}},
};

// 0x05006888
static const Vtx penguin_seg5_vertex_05006888[] = {
    {{{     7,      9,    -37}, 0, {     0,      0}, {0x8c, 0xf9, 0xcf, 0x00}}},
    {{{     7,      9,     37}, 0, {     0,      0}, {0x89, 0xfa, 0x29, 0x00}}},
    {{{     6,     57,    -20}, 0, {     0,      0}, {0x84, 0x0d, 0xeb, 0x00}}},
    {{{    30,     76,     60}, 0, {     0,      0}, {0xca, 0x22, 0x6d, 0xff}}},
    {{{    32,      3,     62}, 0, {     0,      0}, {0xed, 0xe4, 0x7a, 0xff}}},
    {{{    94,     64,     53}, 0, {     0,      0}, {0x3b, 0x14, 0x6e, 0xff}}},
    {{{    32,      3,    -62}, 0, {     0,      0}, {0xed, 0xe4, 0x86, 0xff}}},
    {{{    94,     64,    -53}, 0, {     0,      0}, {0x3b, 0x14, 0x92, 0xff}}},
    {{{    95,     17,    -53}, 0, {     0,      0}, {0x58, 0xeb, 0xa8, 0xff}}},
    {{{    17,    -97,      0}, 0, {     0,      0}, {0xbd, 0x95, 0x00, 0xff}}},
    {{{   107,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
    {{{    92,    -35,    -29}, 0, {     0,      0}, {0x55, 0xba, 0xc3, 0xff}}},
    {{{    95,     17,     53}, 0, {     0,      0}, {0x58, 0xeb, 0x58, 0xff}}},
    {{{    92,    -35,     29}, 0, {     0,      0}, {0x55, 0xba, 0x3d, 0xff}}},
    {{{    16,     96,     36}, 0, {     0,      0}, {0xab, 0x4a, 0x39, 0xff}}},
    {{{     6,     57,     20}, 0, {     0,      0}, {0x86, 0x0c, 0x20, 0xff}}},
};

// 0x05006988
static const Vtx penguin_seg5_vertex_05006988[] = {
    {{{   112,     55,     26}, 0, {     0,      0}, {0x79, 0x11, 0x22, 0xff}}},
    {{{    92,     90,     41}, 0, {     0,      0}, {0x34, 0x5e, 0x43, 0x00}}},
    {{{    94,     64,     53}, 0, {     0,      0}, {0x3b, 0x14, 0x6e, 0x00}}},
    {{{    94,     64,    -53}, 0, {     0,      0}, {0x3b, 0x14, 0x92, 0xff}}},
    {{{    92,     90,    -41}, 0, {     0,      0}, {0x33, 0x5e, 0xbd, 0xff}}},
    {{{   112,     55,    -26}, 0, {     0,      0}, {0x79, 0x11, 0xde, 0xff}}},
    {{{    16,     96,    -36}, 0, {     0,      0}, {0xbb, 0x50, 0xbb, 0xff}}},
    {{{    30,     76,    -60}, 0, {     0,      0}, {0xca, 0x22, 0x93, 0xff}}},
    {{{     6,     57,    -20}, 0, {     0,      0}, {0x84, 0x0d, 0xeb, 0xff}}},
    {{{    30,     76,     60}, 0, {     0,      0}, {0xca, 0x22, 0x6d, 0xff}}},
    {{{     7,      9,     37}, 0, {     0,      0}, {0x89, 0xfa, 0x29, 0xff}}},
    {{{    32,      3,     62}, 0, {     0,      0}, {0xed, 0xe4, 0x7a, 0xff}}},
    {{{    32,      3,    -62}, 0, {     0,      0}, {0xed, 0xe4, 0x86, 0xff}}},
    {{{     7,      9,    -37}, 0, {     0,      0}, {0x8c, 0xf9, 0xcf, 0xff}}},
    {{{    95,     17,     53}, 0, {     0,      0}, {0x58, 0xeb, 0x58, 0xff}}},
};

// 0x05006A78
static const Vtx penguin_seg5_vertex_05006A78[] = {
    {{{   112,     55,    -26}, 0, {     0,      0}, {0x79, 0x11, 0xde, 0xff}}},
    {{{   107,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0x00}}},
    {{{    95,     17,    -53}, 0, {     0,      0}, {0x58, 0xeb, 0xa8, 0x00}}},
    {{{     7,      9,    -37}, 0, {     0,      0}, {0x8c, 0xf9, 0xcf, 0xff}}},
    {{{    17,    -97,      0}, 0, {     0,      0}, {0xbd, 0x95, 0x00, 0xff}}},
    {{{     7,      9,     37}, 0, {     0,      0}, {0x89, 0xfa, 0x29, 0xff}}},
    {{{    92,    -35,    -29}, 0, {     0,      0}, {0x55, 0xba, 0xc3, 0xff}}},
    {{{    32,      3,    -62}, 0, {     0,      0}, {0xed, 0xe4, 0x86, 0xff}}},
    {{{    32,      3,     62}, 0, {     0,      0}, {0xed, 0xe4, 0x7a, 0xff}}},
    {{{    92,    -35,     29}, 0, {     0,      0}, {0x55, 0xba, 0x3d, 0xff}}},
    {{{    16,     96,     36}, 0, {     0,      0}, {0xab, 0x4a, 0x39, 0xff}}},
    {{{    16,     96,    -36}, 0, {     0,      0}, {0xbb, 0x50, 0xbb, 0xff}}},
    {{{     6,     57,    -20}, 0, {     0,      0}, {0x84, 0x0d, 0xeb, 0xff}}},
    {{{    92,     90,     41}, 0, {     0,      0}, {0x34, 0x5e, 0x43, 0xff}}},
    {{{    30,     76,     60}, 0, {     0,      0}, {0xca, 0x22, 0x6d, 0xff}}},
};

// 0x05006B68
static const Vtx penguin_seg5_vertex_05006B68[] = {
    {{{    95,     99,      0}, 0, {     0,      0}, {0x56, 0x5d, 0x00, 0xff}}},
    {{{    92,     90,     41}, 0, {     0,      0}, {0x34, 0x5e, 0x43, 0x00}}},
    {{{   112,     55,     26}, 0, {     0,      0}, {0x79, 0x11, 0x22, 0x00}}},
    {{{    30,     76,    -60}, 0, {     0,      0}, {0xca, 0x22, 0x93, 0xff}}},
    {{{    92,     90,    -41}, 0, {     0,      0}, {0x33, 0x5e, 0xbd, 0xff}}},
    {{{    94,     64,    -53}, 0, {     0,      0}, {0x3b, 0x14, 0x92, 0xff}}},
    {{{    46,    105,      0}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0xff}}},
    {{{    16,     96,    -36}, 0, {     0,      0}, {0xbb, 0x50, 0xbb, 0xff}}},
    {{{    17,    -97,      0}, 0, {     0,      0}, {0xbd, 0x95, 0x00, 0xff}}},
    {{{    92,    -35,    -29}, 0, {     0,      0}, {0x55, 0xba, 0xc3, 0xff}}},
    {{{    92,    -35,     29}, 0, {     0,      0}, {0x55, 0xba, 0x3d, 0xff}}},
    {{{    16,     96,     36}, 0, {     0,      0}, {0xab, 0x4a, 0x39, 0xff}}},
    {{{   112,     55,    -26}, 0, {     0,      0}, {0x79, 0x11, 0xde, 0xff}}},
    {{{   107,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
    {{{    30,     76,     60}, 0, {     0,      0}, {0xca, 0x22, 0x6d, 0xff}}},
    {{{    94,     64,     53}, 0, {     0,      0}, {0x3b, 0x14, 0x6e, 0xff}}},
};

// 0x05006C68
static const Vtx penguin_seg5_vertex_05006C68[] = {
    {{{    32,      3,    -62}, 0, {     0,      0}, {0xed, 0xe4, 0x86, 0xff}}},
    {{{    30,     76,    -60}, 0, {     0,      0}, {0xca, 0x22, 0x93, 0x00}}},
    {{{    94,     64,    -53}, 0, {     0,      0}, {0x3b, 0x14, 0x92, 0x00}}},
    {{{    32,      3,     62}, 0, {     0,      0}, {0xed, 0xe4, 0x7a, 0xff}}},
    {{{    95,     17,     53}, 0, {     0,      0}, {0x58, 0xeb, 0x58, 0xff}}},
    {{{    94,     64,     53}, 0, {     0,      0}, {0x3b, 0x14, 0x6e, 0xff}}},
    {{{    92,    -35,    -29}, 0, {     0,      0}, {0x55, 0xba, 0xc3, 0xff}}},
    {{{   107,     -5,      0}, 0, {     0,      0}, {0x7b, 0xe5, 0x00, 0xff}}},
    {{{    92,    -35,     29}, 0, {     0,      0}, {0x55, 0xba, 0x3d, 0xff}}},
    {{{    95,     17,    -53}, 0, {     0,      0}, {0x58, 0xeb, 0xa8, 0xff}}},
    {{{   112,     55,    -26}, 0, {     0,      0}, {0x79, 0x11, 0xde, 0xff}}},
    {{{   112,     55,     26}, 0, {     0,      0}, {0x79, 0x11, 0x22, 0xff}}},
    {{{     6,     57,    -20}, 0, {     0,      0}, {0x84, 0x0d, 0xeb, 0xff}}},
    {{{     6,     57,     20}, 0, {     0,      0}, {0x86, 0x0c, 0x20, 0xff}}},
    {{{    16,     96,     36}, 0, {     0,      0}, {0xab, 0x4a, 0x39, 0xff}}},
    {{{     7,      9,    -37}, 0, {     0,      0}, {0x8c, 0xf9, 0xcf, 0xff}}},
};

// 0x05006D68
static const Vtx penguin_seg5_vertex_05006D68[] = {
    {{{    30,     76,     60}, 0, {     0,      0}, {0xca, 0x22, 0x6d, 0xff}}},
    {{{     6,     57,     20}, 0, {     0,      0}, {0x86, 0x0c, 0x20, 0x00}}},
    {{{     7,      9,     37}, 0, {     0,      0}, {0x89, 0xfa, 0x29, 0x00}}},
    {{{     6,     57,    -20}, 0, {     0,      0}, {0x84, 0x0d, 0xeb, 0xff}}},
    {{{    46,    105,      0}, 0, {     0,      0}, {0x29, 0x77, 0x00, 0xff}}},
    {{{    92,     90,     41}, 0, {     0,      0}, {0x34, 0x5e, 0x43, 0xff}}},
    {{{    95,     99,      0}, 0, {     0,      0}, {0x56, 0x5d, 0x00, 0xff}}},
    {{{    92,     90,    -41}, 0, {     0,      0}, {0x33, 0x5e, 0xbd, 0xff}}},
};

// 0x05006DE8
static const Vtx penguin_seg5_vertex_05006DE8[] = {
    {{{   112,     55,    -26}, 0, {     0,      0}, {0x79, 0x11, 0xde, 0x00}}},
    {{{    92,     90,    -41}, 0, {     0,      0}, {0x33, 0x5e, 0xbd, 0x00}}},
    {{{    95,     99,      0}, 0, {     0,      0}, {0x56, 0x5d, 0x00, 0x00}}},
    {{{   112,     55,     26}, 0, {     0,      0}, {0x79, 0x11, 0x22, 0xff}}},
};

// 0x05006E28
static const Vtx penguin_seg5_vertex_05006E28[] = {
    {{{   -35,    162,     36}, 0, {     0,      0}, {0xc6, 0x5c, 0x3f, 0x00}}},
    {{{    35,    162,     36}, 0, {     0,      0}, {0x3a, 0x6b, 0x22, 0x00}}},
    {{{   -32,    159,    -28}, 0, {     0,      0}, {0xd3, 0x6f, 0xd8, 0x00}}},
    {{{    90,     -8,    -61}, 0, {     0,      0}, {0x70, 0xff, 0xc6, 0xff}}},
    {{{    52,     36,    -53}, 0, {     0,      0}, {0x60, 0x3a, 0xc5, 0xff}}},
    {{{    70,     82,     19}, 0, {     0,      0}, {0x76, 0x2b, 0xf1, 0xff}}},
    {{{   -70,     82,     19}, 0, {     0,      0}, {0x8a, 0x2b, 0xf1, 0xff}}},
    {{{   -52,     36,    -53}, 0, {     0,      0}, {0xa0, 0x3a, 0xc5, 0xff}}},
    {{{   -90,     -8,    -61}, 0, {     0,      0}, {0x90, 0xff, 0xc6, 0xff}}},
    {{{   -17,    148,    -50}, 0, {     0,      0}, {0xb2, 0x38, 0xaf, 0xff}}},
    {{{    17,    148,    -50}, 0, {     0,      0}, {0x39, 0x45, 0xa7, 0xff}}},
    {{{    32,    159,    -28}, 0, {     0,      0}, {0x46, 0x60, 0xd5, 0xff}}},
    {{{   -55,    -39,    -33}, 0, {     0,      0}, {0xb5, 0x9d, 0xeb, 0xff}}},
    {{{   -90,     -2,     44}, 0, {     0,      0}, {0x89, 0xe4, 0x1f, 0xff}}},
    {{{    40,      5,   -103}, 0, {     0,      0}, {0x3d, 0x38, 0xa1, 0xff}}},
};

// 0x05006F18
static const Vtx penguin_seg5_vertex_05006F18[] = {
    {{{    40,      5,   -103}, 0, {     0,      0}, {0x3d, 0x38, 0xa1, 0xff}}},
    {{{     0,    -33,   -140}, 0, {     0,      0}, {0x00, 0xb6, 0x9a, 0x00}}},
    {{{   -40,      5,   -103}, 0, {     0,      0}, {0xcd, 0x37, 0x9a, 0x00}}},
    {{{    70,     82,     19}, 0, {     0,      0}, {0x76, 0x2b, 0xf1, 0xff}}},
    {{{    90,     -2,     44}, 0, {     0,      0}, {0x77, 0xe4, 0x1f, 0xff}}},
    {{{    90,     -8,    -61}, 0, {     0,      0}, {0x70, 0xff, 0xc6, 0xff}}},
    {{{   -90,     -8,    -61}, 0, {     0,      0}, {0x90, 0xff, 0xc6, 0xff}}},
    {{{   -90,     -2,     44}, 0, {     0,      0}, {0x89, 0xe4, 0x1f, 0xff}}},
    {{{   -70,     82,     19}, 0, {     0,      0}, {0x8a, 0x2b, 0xf1, 0xff}}},
    {{{     0,    -56,    -79}, 0, {     0,      0}, {0xf9, 0x84, 0xe8, 0xff}}},
    {{{   -55,    -39,    -33}, 0, {     0,      0}, {0xb5, 0x9d, 0xeb, 0xff}}},
    {{{   -52,     36,    -53}, 0, {     0,      0}, {0xa0, 0x3a, 0xc5, 0xff}}},
    {{{   -17,    148,    -50}, 0, {     0,      0}, {0xb2, 0x38, 0xaf, 0xff}}},
    {{{    55,    -39,    -33}, 0, {     0,      0}, {0x3c, 0x92, 0xef, 0xff}}},
    {{{    17,    148,    -50}, 0, {     0,      0}, {0x39, 0x45, 0xa7, 0xff}}},
    {{{    52,     36,    -53}, 0, {     0,      0}, {0x60, 0x3a, 0xc5, 0xff}}},
};

// 0x05007018
static const Vtx penguin_seg5_vertex_05007018[] = {
    {{{   -32,    159,    -28}, 0, {     0,      0}, {0xd3, 0x6f, 0xd8, 0xff}}},
    {{{    32,    159,    -28}, 0, {     0,      0}, {0x46, 0x60, 0xd5, 0x00}}},
    {{{    17,    148,    -50}, 0, {     0,      0}, {0x39, 0x45, 0xa7, 0x00}}},
    {{{    35,    162,     36}, 0, {     0,      0}, {0x3a, 0x6b, 0x22, 0xff}}},
    {{{     0,    -33,   -140}, 0, {     0,      0}, {0x00, 0xb6, 0x9a, 0xff}}},
    {{{    55,    -39,    -33}, 0, {     0,      0}, {0x3c, 0x92, 0xef, 0xff}}},
    {{{     0,    -56,    -79}, 0, {     0,      0}, {0xf9, 0x84, 0xe8, 0xff}}},
    {{{   -40,      5,   -103}, 0, {     0,      0}, {0xcd, 0x37, 0x9a, 0xff}}},
    {{{    40,      5,   -103}, 0, {     0,      0}, {0x3d, 0x38, 0xa1, 0xff}}},
};

// 0x050070A8
static const Vtx penguin_seg5_vertex_050070A8[] = {
    {{{   -35,    162,     36}, 0, {     0,      0}, {0xc6, 0x5c, 0x3f, 0x00}}},
    {{{   -37,     87,     87}, 0, {     0,      0}, {0xb6, 0x2e, 0x5c, 0x00}}},
    {{{    37,     87,     87}, 0, {     0,      0}, {0x35, 0x31, 0x68, 0x00}}},
    {{{   -90,     -2,     44}, 0, {     0,      0}, {0x89, 0xe4, 0x1f, 0xff}}},
    {{{   -70,     82,     19}, 0, {     0,      0}, {0x8a, 0x2b, 0xf1, 0xff}}},
    {{{    35,    162,     36}, 0, {     0,      0}, {0x3a, 0x6b, 0x22, 0xff}}},
    {{{    70,     82,     19}, 0, {     0,      0}, {0x76, 0x2b, 0xf1, 0xff}}},
    {{{   -47,    -11,    108}, 0, {     0,      0}, {0xcf, 0xe5, 0x71, 0xff}}},
    {{{   -50,    -56,     36}, 0, {     0,      0}, {0xdc, 0x89, 0x15, 0xff}}},
    {{{    47,    -11,    108}, 0, {     0,      0}, {0x31, 0xc3, 0x63, 0xff}}},
    {{{    50,    -56,     36}, 0, {     0,      0}, {0x38, 0x91, 0x16, 0xff}}},
    {{{   -55,    -39,    -33}, 0, {     0,      0}, {0xb5, 0x9d, 0xeb, 0xff}}},
    {{{     0,    -56,    -79}, 0, {     0,      0}, {0xf9, 0x84, 0xe8, 0xff}}},
    {{{    90,     -2,     44}, 0, {     0,      0}, {0x77, 0xe4, 0x1f, 0xff}}},
    {{{    55,    -39,    -33}, 0, {     0,      0}, {0x3c, 0x92, 0xef, 0xff}}},
};

// 0x05007198 - 0x050071E8
const Gfx penguin_seg5_dl_05007198[] = {
    gsSPLight(&penguin_seg5_lights_05002D80.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D80.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006518, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 8,  0,  2, 0x0,  0,  9,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x050071E8 - 0x05007238
const Gfx penguin_seg5_dl_050071E8[] = {
    gsSPLight(&penguin_seg5_lights_05002D80.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D80.a, 2),
    gsSPVertex(penguin_seg5_vertex_050065B8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007238 - 0x050072C8
const Gfx penguin_seg5_dl_05007238[] = {
    gsSPLight(&penguin_seg5_lights_05002D98.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D98.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006658, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  2, 0x0),
    gsSP2Triangles( 1,  4,  6, 0x0,  1,  6,  2, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  1,  0,  4, 0x0),
    gsSP2Triangles( 8, 10, 11, 0x0,  2, 12,  0, 0x0),
    gsSP2Triangles( 0, 12,  3, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles(10, 13, 11, 0x0,  9, 14, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x050072C8 - 0x05007358
const Gfx penguin_seg5_dl_050072C8[] = {
    gsSPLight(&penguin_seg5_lights_05002D98.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D98.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006748, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  5, 11, 0x0,  8, 12,  9, 0x0),
    gsSP2Triangles( 6,  2, 11, 0x0,  2, 10, 11, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0,  2,  1, 10, 0x0),
    gsSP2Triangles(10,  3,  5, 0x0,  6, 11,  5, 0x0),
    gsSP2Triangles( 8, 13, 12, 0x0,  7, 14,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007358 - 0x05007540
const Gfx penguin_seg5_dl_05007358[] = {
    gsSPLight(&penguin_seg5_lights_05002DB0.l, 1),
    gsSPLight(&penguin_seg5_lights_05002DB0.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006838, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP1Triangle( 1,  4,  3, 0x0),
    gsSPLight(&penguin_seg5_lights_05002D80.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D80.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006888, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  0, 0x0),
    gsSP2Triangles(10, 11,  8, 0x0, 12, 13, 10, 0x0),
    gsSP2Triangles( 9,  4,  1, 0x0,  3, 14, 15, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006988, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13,  7, 0x0, 14,  0,  2, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006A78, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  8,  4,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 10, 14, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006B68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles(11,  1,  6, 0x0, 12,  2, 13, 0x0),
    gsSP2Triangles( 3,  7,  4, 0x0, 14, 15,  1, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006C68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  2, 10, 0x0),
    gsSP2Triangles( 4,  7, 11, 0x0,  8,  4,  3, 0x0),
    gsSP2Triangles( 0,  9,  6, 0x0, 12, 13, 14, 0x0),
    gsSP1Triangle(15, 12,  1, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006D68, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSPLight(&penguin_seg5_lights_05002D80.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D80.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006DE8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007540 - 0x05007708
const Gfx penguin_seg5_dl_05007540[] = {
    gsSPLight(&penguin_seg5_lights_05002D80.l, 1),
    gsSPLight(&penguin_seg5_lights_05002D80.a, 2),
    gsSPVertex(penguin_seg5_vertex_05006E28, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  2,  9,  6, 0x0),
    gsSP2Triangles( 5, 10, 11, 0x0,  4, 10,  5, 0x0),
    gsSP2Triangles( 8, 12, 13, 0x0,  5, 11,  1, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0, 10,  9,  2, 0x0),
    gsSP1Triangle( 3, 14,  4, 0x0),
    gsSPVertex(penguin_seg5_vertex_05006F18, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  1, 0x0),
    gsSP2Triangles(11,  8, 12, 0x0, 13,  5,  4, 0x0),
    gsSP2Triangles( 0, 14, 15, 0x0,  2, 11, 12, 0x0),
    gsSP2Triangles( 6, 11,  2, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 1, 10,  6, 0x0,  6,  2,  1, 0x0),
    gsSP2Triangles( 5, 13,  1, 0x0,  2, 12, 14, 0x0),
    gsSPVertex(penguin_seg5_vertex_05007018, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  2,  8, 0x0),
    gsSPLight(&penguin_seg5_lights_05002DC8.l, 1),
    gsSPLight(&penguin_seg5_lights_05002DC8.a, 2),
    gsSPVertex(penguin_seg5_vertex_050070A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 5,  2,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0,  9,  2,  7, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 8, 11, 12, 0x0, 13,  9, 10, 0x0),
    gsSP2Triangles( 2,  1,  7, 0x0, 13, 10, 14, 0x0),
    gsSP2Triangles(14, 10,  8, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles(13,  6,  2, 0x0,  3,  7,  1, 0x0),
    gsSP2Triangles(11,  8,  3, 0x0, 12, 14,  8, 0x0),
    gsSP1Triangle( 2,  9, 13, 0x0),
    gsSPEndDisplayList(),
};
