// Snufit

// 0x060070E0
ALIGNED8 static const Texture snufit_seg6_texture_060070E0[] = {
#include "actors/snufit/snufit_body.rgba16.inc.c"
};

// 0x060078E0
ALIGNED8 static const Texture snufit_seg6_texture_060078E0[] = {
#include "actors/snufit/snufit_eye.rgba16.inc.c"
};

// 0x060080E0
ALIGNED8 static const Texture snufit_seg6_texture_060080E0[] = {
#include "actors/snufit/snufit_mask_strap.rgba16.inc.c"
};

// 0x060084E0
ALIGNED8 static const Texture snufit_seg6_texture_060084E0[] = {
#include "actors/snufit/snufit_mouth.rgba16.inc.c"
};

// 0x06008CE0
static const Lights1 snufit_seg6_lights_06008CE0 = gdSPDefLights1(
    0x47, 0x47, 0x47,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// 0x06008CF8
static const Lights1 snufit_seg6_lights_06008CF8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06008D10
static const Vtx snufit_seg6_vertex_06008D10[] = {
    {{{  -105,    -30,   -106}, 0, {   451,    640}, {0x9b, 0x00, 0xb4, 0xff}}},
    {{{  -149,     33,      0}, 0, {   547,    320}, {0x83, 0x00, 0xf0, 0xff}}},
    {{{  -105,     34,   -105}, 0, {   451,    316}, {0xb4, 0x01, 0x9b, 0xff}}},
    {{{  -149,    -31,      0}, 0, {   547,    646}, {0x83, 0x00, 0x13, 0xff}}},
    {{{     0,    -30,   -150}, 0, {   220,    638}, {0xef, 0x01, 0x83, 0xff}}},
    {{{  -127,     32,     50}, 0, {   498,    324}, {0x8c, 0x00, 0x33, 0xff}}},
    {{{     0,     34,   -149}, 0, {   220,    314}, {0x11, 0x01, 0x83, 0xff}}},
    {{{  -127,    -32,     49}, 0, {   498,    648}, {0x8d, 0x00, 0x34, 0xff}}},
    {{{   106,    -30,   -106}, 0, {   -10,    642}, {0x4c, 0x01, 0x9b, 0xff}}},
    {{{   106,     34,   -105}, 0, {   -10,    318}, {0x65, 0x00, 0xb4, 0xff}}},
    {{{   150,    -31,      0}, 0, {  -105,    648}, {0x7d, 0x00, 0xf0, 0xff}}},
    {{{   150,     33,      0}, 0, {  -105,    322}, {0x7d, 0x00, 0x13, 0xff}}},
    {{{   128,    -32,     49}, 0, {   -56,    650}, {0x74, 0x00, 0x33, 0xff}}},
    {{{   128,     32,     50}, 0, {   -56,    326}, {0x74, 0x00, 0x33, 0xff}}},
};

// 0x06008DF0
static const Vtx snufit_seg6_vertex_06008DF0[] = {
    {{{   -91,    -56,    111}, 0, {   -16,   1362}, {0xb5, 0xd5, 0x5c, 0xff}}},
    {{{     0,      0,    149}, 0, {   986,    824}, {0x00, 0x00, 0x7e, 0xff}}},
    {{{   -99,      0,    117}, 0, {   -66,    820}, {0xaf, 0xff, 0x61, 0xff}}},
    {{{     0,     55,    141}, 0, {   962,    260}, {0x00, 0x24, 0x79, 0xff}}},
    {{{   -91,     56,    112}, 0, {   -12,    254}, {0xb4, 0x28, 0x5c, 0xff}}},
    {{{     0,    103,    118}, 0, {   890,   -242}, {0xff, 0x4b, 0x66, 0xff}}},
    {{{     0,    -57,    140}, 0, {    -8,   1364}, {0x00, 0xd9, 0x78, 0xff}}},
    {{{   100,      0,    117}, 0, {  1010,    820}, {0x51, 0x00, 0x61, 0xff}}},
    {{{     0,      0,    149}, 0, {     0,    820}, {0x00, 0x00, 0x7e, 0xff}}},
    {{{    92,     56,    112}, 0, {   956,    252}, {0x4b, 0x29, 0x5d, 0xff}}},
    {{{     0,     55,    141}, 0, {    -8,    252}, {0x00, 0x24, 0x79, 0xff}}},
    {{{    70,    104,     96}, 0, {   802,   -252}, {0x3a, 0x51, 0x4d, 0xff}}},
};

// 0x06008EB0
static const Vtx snufit_seg6_vertex_06008EB0[] = {
    {{{   -37,   -136,     68}, 0, {     0,      0}, {0xde, 0x92, 0x33, 0xff}}},
    {{{   -53,   -135,     39}, 0, {     0,      0}, {0xc7, 0x93, 0x1c, 0xff}}},
    {{{     0,   -147,     39}, 0, {     0,      0}, {0x00, 0x83, 0x16, 0xff}}},
    {{{     0,   -136,     80}, 0, {     0,      0}, {0x05, 0x94, 0x41, 0xff}}},
    {{{     0,   -104,    116}, 0, {     0,      0}, {0x01, 0xb3, 0x64, 0xff}}},
    {{{   -69,   -104,     93}, 0, {     0,      0}, {0xc5, 0xae, 0x4b, 0xff}}},
    {{{   -99,   -103,     39}, 0, {     0,      0}, {0xa7, 0xb4, 0x2f, 0xff}}},
    {{{    54,   -135,     39}, 0, {     0,      0}, {0x31, 0x8e, 0x17, 0xff}}},
    {{{    38,   -136,     68}, 0, {     0,      0}, {0x2a, 0x90, 0x29, 0xff}}},
    {{{   -91,    -56,    111}, 0, {     0,      0}, {0xb5, 0xd5, 0x5c, 0xff}}},
    {{{     0,    -57,    140}, 0, {     0,      0}, {0x00, 0xd9, 0x78, 0xff}}},
    {{{  -129,    -56,     40}, 0, {     0,      0}, {0x95, 0xdc, 0x39, 0xff}}},
    {{{   -99,      0,    117}, 0, {     0,      0}, {0xaf, 0xff, 0x61, 0xff}}},
    {{{     0,      0,    149}, 0, {     0,      0}, {0x00, 0x00, 0x7e, 0xff}}},
};

// 0x06008F90
static const Vtx snufit_seg6_vertex_06008F90[] = {
    {{{     0,    103,    118}, 0, {     0,      0}, {0xff, 0x4b, 0x66, 0xff}}},
    {{{    38,    136,     71}, 0, {     0,      0}, {0x20, 0x6e, 0x35, 0xff}}},
    {{{     0,    136,     83}, 0, {     0,      0}, {0xfb, 0x6b, 0x42, 0xff}}},
    {{{  -129,    -56,     40}, 0, {     0,      0}, {0x95, 0xdc, 0x39, 0xff}}},
    {{{   -99,      0,    117}, 0, {     0,      0}, {0xaf, 0xff, 0x61, 0xff}}},
    {{{  -140,      0,     40}, 0, {     0,      0}, {0x91, 0x06, 0x3b, 0xff}}},
    {{{   -91,     56,    112}, 0, {     0,      0}, {0xb4, 0x28, 0x5c, 0xff}}},
    {{{  -129,     56,     41}, 0, {     0,      0}, {0x99, 0x30, 0x37, 0xff}}},
    {{{   -69,    104,     96}, 0, {     0,      0}, {0xc2, 0x51, 0x4a, 0xff}}},
    {{{   -99,    104,     41}, 0, {     0,      0}, {0xb0, 0x57, 0x2b, 0xff}}},
    {{{   -37,    136,     71}, 0, {     0,      0}, {0xd6, 0x6f, 0x2b, 0xff}}},
    {{{   -53,    136,     42}, 0, {     0,      0}, {0xd0, 0x72, 0x1a, 0xff}}},
    {{{     0,    147,     42}, 0, {     0,      0}, {0x00, 0x7c, 0x17, 0xff}}},
    {{{    54,    136,     42}, 0, {     0,      0}, {0x38, 0x6d, 0x1e, 0xff}}},
};

// 0x06009070
static const Vtx snufit_seg6_vertex_06009070[] = {
    {{{    38,   -136,     68}, 0, {     0,      0}, {0x2a, 0x90, 0x29, 0xff}}},
    {{{    54,   -135,     39}, 0, {     0,      0}, {0x31, 0x8e, 0x17, 0xff}}},
    {{{   100,   -103,     39}, 0, {     0,      0}, {0x50, 0xa8, 0x2a, 0xff}}},
    {{{     0,   -136,     80}, 0, {     0,      0}, {0x05, 0x94, 0x41, 0xff}}},
    {{{    70,   -104,     93}, 0, {     0,      0}, {0x3e, 0xae, 0x49, 0xff}}},
    {{{     0,   -104,    116}, 0, {     0,      0}, {0x01, 0xb3, 0x64, 0xff}}},
    {{{    92,    -56,    111}, 0, {     0,      0}, {0x4c, 0xd5, 0x5b, 0xff}}},
    {{{     0,    -57,    140}, 0, {     0,      0}, {0x00, 0xd9, 0x78, 0xff}}},
    {{{   100,      0,    117}, 0, {     0,      0}, {0x51, 0x00, 0x61, 0xff}}},
    {{{     0,     55,    141}, 0, {     0,      0}, {0x00, 0x24, 0x79, 0xff}}},
    {{{    70,    104,     96}, 0, {     0,      0}, {0x3a, 0x51, 0x4d, 0xff}}},
    {{{     0,    103,    118}, 0, {     0,      0}, {0xff, 0x4b, 0x66, 0xff}}},
    {{{    38,    136,     71}, 0, {     0,      0}, {0x20, 0x6e, 0x35, 0xff}}},
    {{{   130,    -56,     40}, 0, {     0,      0}, {0x66, 0xce, 0x37, 0xff}}},
    {{{   141,      0,     40}, 0, {     0,      0}, {0x6f, 0xf9, 0x3b, 0xff}}},
    {{{   130,     56,     41}, 0, {     0,      0}, {0x6b, 0x22, 0x39, 0xff}}},
};

// 0x06009170
static const Vtx snufit_seg6_vertex_06009170[] = {
    {{{   100,      0,    117}, 0, {     0,      0}, {0x51, 0x00, 0x61, 0xff}}},
    {{{   130,     56,     41}, 0, {     0,      0}, {0x6b, 0x22, 0x39, 0xff}}},
    {{{    92,     56,    112}, 0, {     0,      0}, {0x4b, 0x29, 0x5d, 0xff}}},
    {{{   100,    104,     41}, 0, {     0,      0}, {0x59, 0x4b, 0x30, 0xff}}},
    {{{    70,    104,     96}, 0, {     0,      0}, {0x3a, 0x51, 0x4d, 0xff}}},
    {{{    54,    136,     42}, 0, {     0,      0}, {0x38, 0x6d, 0x1e, 0xff}}},
    {{{    38,    136,     71}, 0, {     0,      0}, {0x20, 0x6e, 0x35, 0xff}}},
};

// 0x060091E0 - 0x06009278
const Gfx snufit_seg6_dl_060091E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060080E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&snufit_seg6_lights_06008CE0.l, 1),
    gsSPLight(&snufit_seg6_lights_06008CE0.a, 2),
    gsSPVertex(snufit_seg6_vertex_06008D10, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  3,  5,  1, 0x0),
    gsSP2Triangles( 4,  2,  6, 0x0,  3,  7,  5, 0x0),
    gsSP2Triangles( 8,  4,  6, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles(10,  8,  9, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(12, 10, 11, 0x0, 12, 11, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009278 - 0x060092F0
const Gfx snufit_seg6_dl_06009278[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060078E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&snufit_seg6_lights_06008CF8.l, 1),
    gsSPLight(&snufit_seg6_lights_06008CF8.a, 2),
    gsSPVertex(snufit_seg6_vertex_06008DF0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x060092F0 - 0x06009498
const Gfx snufit_seg6_dl_060092F0[] = {
    gsSPVertex(snufit_seg6_vertex_06008EB0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  2, 0x0,  8,  3,  2, 0x0),
    gsSP2Triangles( 6,  5,  9, 0x0,  5, 10,  9, 0x0),
    gsSP2Triangles( 5,  4, 10, 0x0,  6,  9, 11, 0x0),
    gsSP2Triangles(11,  9, 12, 0x0,  9, 10, 13, 0x0),
    gsSPVertex(snufit_seg6_vertex_06008F90, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  6,  0,  8, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  9,  8, 10, 0x0),
    gsSP2Triangles( 8,  2, 10, 0x0,  8,  0,  2, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 10, 12, 0x0),
    gsSP2Triangles(10,  2, 12, 0x0,  2,  1, 12, 0x0),
    gsSP1Triangle( 1, 13, 12, 0x0),
    gsSPVertex(snufit_seg6_vertex_06009070, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 9, 10, 11, 0x0, 11, 10, 12, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4, 13,  6, 0x0),
    gsSP2Triangles( 4,  2, 13, 0x0,  6, 14,  8, 0x0),
    gsSP2Triangles( 6, 13, 14, 0x0,  8, 14, 15, 0x0),
    gsSPVertex(snufit_seg6_vertex_06009170, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  3,  5, 0x0),
    gsSP1Triangle( 2,  1,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009498 - 0x06009530
const Gfx snufit_seg6_dl_06009498[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBA, G_CC_BLENDRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_060091E0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_06009278),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(snufit_seg6_dl_060092F0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06009530
static const Lights1 snufit_seg6_lights_06009530 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06009548
static const Vtx snufit_seg6_vertex_06009548[] = {
    {{{   -33,     59,     -6}, 0, {   474,     68}, {0xae, 0x60, 0x00, 0xff}}},
    {{{   -68,      0,     -6}, 0, {   475,    340}, {0x84, 0xe8, 0x00, 0xff}}},
    {{{   -68,      0,     62}, 0, {    21,    332}, {0x8c, 0x17, 0x2d, 0xff}}},
    {{{   -33,     59,     62}, 0, {    20,     62}, {0xe0, 0x5f, 0x4c, 0xff}}},
    {{{    34,     59,     -6}, 0, {   474,     68}, {0x29, 0x78, 0x00, 0xff}}},
    {{{   -33,    -59,     62}, 0, {    22,    604}, {0xd3, 0xcb, 0x69, 0xff}}},
    {{{    34,     59,     62}, 0, {    20,     62}, {0x41, 0x4d, 0x4c, 0xff}}},
    {{{   -33,    -59,     -6}, 0, {   476,    610}, {0xd7, 0x88, 0x00, 0xff}}},
    {{{    69,      0,     -6}, 0, {   475,    340}, {0x7c, 0x18, 0x00, 0xff}}},
    {{{    69,      0,     62}, 0, {    21,    332}, {0x63, 0xed, 0x4c, 0xff}}},
    {{{    34,    -59,     -6}, 0, {   476,    610}, {0x52, 0xa0, 0x00, 0xff}}},
    {{{    34,    -59,     62}, 0, {    22,    604}, {0x26, 0x90, 0x2c, 0xff}}},
};

// 0x06009608
static const Vtx snufit_seg6_vertex_06009608[] = {
    {{{   -33,    -59,     62}, 0, {   248,    960}, {0xd3, 0xcb, 0x69, 0xff}}},
    {{{    34,     59,     62}, 0, {   744,     32}, {0x41, 0x4d, 0x4c, 0xff}}},
    {{{   -33,     59,     62}, 0, {   248,     32}, {0xe0, 0x5f, 0x4c, 0xff}}},
    {{{   -68,      0,     62}, 0, {     0,    496}, {0x8c, 0x17, 0x2d, 0xff}}},
    {{{    34,    -59,     62}, 0, {   744,    960}, {0x26, 0x90, 0x2c, 0xff}}},
    {{{    69,      0,     62}, 0, {   992,    496}, {0x63, 0xed, 0x4c, 0xff}}},
};

// 0x06009668 - 0x06009700
const Gfx snufit_seg6_dl_06009668[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060080E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&snufit_seg6_lights_06009530.l, 1),
    gsSPLight(&snufit_seg6_lights_06009530.a, 2),
    gsSPVertex(snufit_seg6_vertex_06009548, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  1,  7,  5, 0x0),
    gsSP2Triangles( 8,  4,  6, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles(10,  8,  9, 0x0,  7, 10, 11, 0x0),
    gsSP2Triangles(10,  9, 11, 0x0,  7, 11,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009700 - 0x06009748
const Gfx snufit_seg6_dl_06009700[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060084E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(snufit_seg6_vertex_06009608, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  5,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009748 - 0x060097C8
const Gfx snufit_seg6_dl_06009748[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_06009668),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_06009700),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060097C8
static const Lights1 snufit_seg6_lights_060097C8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060097E0
static const Vtx snufit_seg6_vertex_060097E0[] = {
    {{{   -23,      7,    119}, 0, {   -47,    152}, {0xad, 0x5f, 0x00, 0xff}}},
    {{{   -46,    -33,    118}, 0, {   -42,    360}, {0x84, 0xea, 0x00, 0xff}}},
    {{{   -46,    -33,    174}, 0, {   392,    364}, {0x84, 0x18, 0x00, 0xff}}},
    {{{   -23,    -74,    118}, 0, {   -36,    570}, {0xd7, 0x89, 0x00, 0xff}}},
    {{{   -23,    -74,    174}, 0, {   397,    574}, {0xad, 0xa1, 0x00, 0xff}}},
    {{{    24,    -74,    118}, 0, {   -36,    570}, {0x53, 0xa1, 0x00, 0xff}}},
    {{{    24,    -74,    174}, 0, {   397,    574}, {0x29, 0x89, 0x00, 0xff}}},
    {{{   -23,      7,    175}, 0, {   386,    156}, {0xd7, 0x77, 0x00, 0xff}}},
    {{{    47,    -33,    118}, 0, {   -42,    360}, {0x7c, 0x18, 0x00, 0xff}}},
    {{{    47,    -33,    174}, 0, {   392,    364}, {0x7c, 0xea, 0x00, 0xff}}},
    {{{    24,      7,    119}, 0, {   -47,    152}, {0x29, 0x77, 0x00, 0xff}}},
    {{{    24,      7,    175}, 0, {   386,    156}, {0x53, 0x5f, 0x00, 0xff}}},
};

// 0x060098A0 - 0x06009938
const Gfx snufit_seg6_dl_060098A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060080E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&snufit_seg6_lights_060097C8.l, 1),
    gsSPLight(&snufit_seg6_lights_060097C8.a, 2),
    gsSPVertex(snufit_seg6_vertex_060097E0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  0,  2,  7, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles( 8, 11,  9, 0x0, 10,  0,  7, 0x0),
    gsSP2Triangles(10,  7, 11, 0x0,  5,  9,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009938 - 0x06009998
const Gfx snufit_seg6_dl_06009938[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_060098A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06009998
static const Vtx snufit_seg6_vertex_06009998[] = {
    {{{    38,     38,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -37,     38,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -37,    -37,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    38,    -37,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x060099D8 - 0x06009A10
const Gfx snufit_seg6_dl_060099D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snufit_seg6_texture_060070E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(snufit_seg6_vertex_06009998, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009A10 - 0x06009A80
const Gfx snufit_seg6_dl_06009A10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(snufit_seg6_dl_060099D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06009A80
static const Lights1 snufit_seg6_lights_06009A80 = gdSPDefLights1(
    0x65, 0x08, 0x08,
    0xfe, 0x14, 0x14, 0x28, 0x28, 0x28
);

// 0x06009A98
static const Vtx snufit_seg6_vertex_06009A98[] = {
    {{{  -177,   -140,    -68}, 0, {     0,      0}, {0xb9, 0x9c, 0xe2, 0xff}}},
    {{{  -124,    -35,    -44}, 0, {     0,      0}, {0x99, 0x3d, 0xd8, 0xff}}},
    {{{   -71,   -112,    -60}, 0, {     0,      0}, {0x1c, 0xb1, 0xa2, 0xff}}},
    {{{   -72,   -105,     22}, 0, {     0,      0}, {0xd4, 0xad, 0x54, 0xff}}},
    {{{    72,   -112,    -60}, 0, {     0,      0}, {0xe4, 0xb1, 0xa2, 0xff}}},
    {{{   125,    -35,    -44}, 0, {     0,      0}, {0x67, 0x3d, 0xd8, 0xff}}},
    {{{   178,   -140,    -68}, 0, {     0,      0}, {0x47, 0x9c, 0xe2, 0xff}}},
    {{{    73,   -105,     22}, 0, {     0,      0}, {0x2c, 0xad, 0x54, 0xff}}},
};

// 0x06009B18 - 0x06009B68
const Gfx snufit_seg6_dl_06009B18[] = {
    gsSPLight(&snufit_seg6_lights_06009A80.l, 1),
    gsSPLight(&snufit_seg6_lights_06009A80.a, 2),
    gsSPVertex(snufit_seg6_vertex_06009A98, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  6,  7,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009B68 - 0x06009B98
const Gfx snufit_seg6_dl_06009B68[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(snufit_seg6_dl_06009B18),
    gsSPEndDisplayList(),
};
