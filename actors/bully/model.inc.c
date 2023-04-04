// Bully

// 0x05000000
static const Vtx bully_seg5_vertex_05000000[] = {
    {{{   196,     17,    114}, 0, {   325,    310}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   259,      2,    106}, 0, {   422,      7}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   184,    -14,    137}, 0, {   399,    416}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   196,     17,   -113}, 0, {   105,    286}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   259,      2,   -105}, 0, {    69,    -34}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   171,     17,   -157}, 0, {  -107,    540}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   184,    -14,   -136}, 0, {   -34,    392}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    91,     42,   -114}, 0, {   236,    784}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   140,     42,    -34}, 0, {   621,    305}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   116,    -38,    -75}, 0, {   344,    489}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   171,     17,    158}, 0, {   417,    557}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   116,    -38,     76}, 0, {   111,    499}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    91,     42,    115}, 0, {   120,    778}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   140,     42,     35}, 0, {   -40,    311}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050000E0
ALIGNED8 static const Texture bully_seg5_texture_050000E0[] = {
#include "actors/bully/bully_horn.rgba16.inc.c"
};

// 0x050002E0 - 0x05000398
const Gfx bully_seg5_dl_050002E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_050000E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05000000, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  6,  7,  5, 0x0),
    gsSP2Triangles( 5,  7,  8, 0x0,  5,  8,  3, 0x0),
    gsSP2Triangles( 3,  8,  9, 0x0,  3,  9,  6, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11,  2, 0x0,  0, 12, 10, 0x0),
    gsSP2Triangles(10, 12, 11, 0x0,  2,  1, 10, 0x0),
    gsSP2Triangles(10,  1,  0, 0x0,  2, 11, 13, 0x0),
    gsSP2Triangles( 0, 13, 12, 0x0,  2, 13,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05000398 - 0x05000408
const Gfx bully_seg5_dl_05000398[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bully_seg5_dl_050002E0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05000408
static const Lights1 bully_seg5_lights_05000408 = gdSPDefLights1(
    0x00, 0x38, 0x00,
    0x00, 0xe3, 0x00, 0x28, 0x28, 0x28
);

// 0x05000420
static const Lights1 bully_seg5_lights_05000420 = gdSPDefLights1(
    0x00, 0x3f, 0x00,
    0x00, 0xff, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 bully_lights_unused = gdSPDefLights1(
    0x3f, 0x29, 0x00,
    0xff, 0xa5, 0x00, 0x28, 0x28, 0x28
);

// 0x05000450
static const Lights1 bully_seg5_lights_05000450 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// //! There's another malformed light entry here.
// 0x05000468
ALIGNED8 static const Texture bully_seg5_texture_05000468[] = {
#include "actors/bully/bully_left_side.rgba16.inc.c"
};

// 0x05001468
ALIGNED8 static const Texture bully_seg5_texture_05001468[] = {
#include "actors/bully/bully_right_side.rgba16.inc.c"
};

// 0x05002468
ALIGNED8 static const Texture bully_seg5_texture_05002468[] = {
#include "actors/bully/bully_eye.rgba16.inc.c"
};

// 0x05002C68
static const Vtx bully_seg5_vertex_05002C68[] = {
    {{{    27,    -35,      0}, 0, {     0,      0}, {0x0c, 0x82, 0x04, 0x00}}},
    {{{    56,    -16,    -27}, 0, {     0,      0}, {0x16, 0xae, 0xa3, 0x00}}},
    {{{    72,    -16,      0}, 0, {     0,      0}, {0x59, 0xa7, 0x04, 0x00}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0x65, 0xdf, 0xbd, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x65, 0xdf, 0xbd, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xd7, 0xc5, 0x98, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd0, 0xde, 0x90, 0xff}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0xd0, 0xde, 0x90, 0xff}}},
    {{{    56,    -16,     22}, 0, {     0,      0}, {0x16, 0xb4, 0x62, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x5f, 0xde, 0x4b, 0xff}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0x5f, 0xde, 0x4b, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xdc, 0xcb, 0x6c, 0xff}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0xd5, 0xdf, 0x72, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd5, 0xdf, 0x72, 0xff}}},
};

// 0x05002D48
static const Vtx bully_seg5_vertex_05002D48[] = {
    {{{    90,     28,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
};

// 0x05002D88
static const Vtx bully_seg5_vertex_05002D88[] = {
    {{{    72,    -16,      0}, 0, {     0,      0}, {0x59, 0xa7, 0xfc, 0x00}}},
    {{{    56,    -16,     27}, 0, {     0,      0}, {0x16, 0xae, 0x5d, 0x00}}},
    {{{    27,    -35,      0}, 0, {     0,      0}, {0x0c, 0x82, 0xfc, 0x00}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x65, 0xdf, 0x43, 0xff}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0x65, 0xdf, 0x43, 0xff}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0xd0, 0xde, 0x70, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd0, 0xde, 0x70, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xd7, 0xc5, 0x68, 0xff}}},
    {{{    56,    -16,    -22}, 0, {     0,      0}, {0x16, 0xb4, 0x9e, 0xff}}},
    {{{    55,     28,    -41}, 0, {     0,      0}, {0x5f, 0xde, 0xb5, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x5f, 0xde, 0xb5, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd5, 0xdf, 0x8e, 0xff}}},
    {{{    55,     28,    -41}, 0, {     0,      0}, {0xd5, 0xdf, 0x8e, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xdc, 0xcb, 0x94, 0xff}}},
};

// 0x05002E68
static const Vtx bully_seg5_vertex_05002E68[] = {
    {{{    55,     28,    -41}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
};

// 0x05002EA8
static const Vtx bully_seg5_vertex_05002EA8[] = {
    {{{    60,    143,      0}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0x00}}},
    {{{    57,    127,    -67}, 0, {     0,      0}, {0x0c, 0x7a, 0xe3, 0x00}}},
    {{{   -46,    137,    -67}, 0, {     0,      0}, {0x0c, 0x7a, 0xe3, 0x00}}},
    {{{   -42,    153,      0}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{   -46,    137,     67}, 0, {     0,      0}, {0x0c, 0x7a, 0x1d, 0xff}}},
    {{{    57,    127,     67}, 0, {     0,      0}, {0x0c, 0x7a, 0x1d, 0xff}}},
};

// 0x05002F08
static const Vtx bully_seg5_vertex_05002F08[] = {
    {{{  -133,    -40,     39}, 0, {     0,      0}, {0x8b, 0xdd, 0x22, 0x00}}},
    {{{  -134,     -1,     55}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -102,    -73,     72}, 0, {     0,      0}, {0xa7, 0xc0, 0x3f, 0xff}}},
    {{{  -102,      0,    102}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{   -54,    -95,     94}, 0, {     0,      0}, {0xd0, 0xad, 0x52, 0xff}}},
    {{{   -55,      0,    134}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{     0,   -102,    102}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{     0,      0,    145}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    56,    -94,     94}, 0, {     0,      0}, {0x31, 0xae, 0x52, 0xff}}},
    {{{    55,      0,    134}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{   103,    -71,     72}, 0, {     0,      0}, {0x5a, 0xc2, 0x3f, 0xff}}},
    {{{   102,      0,    102}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   134,    -38,     39}, 0, {     0,      0}, {0x75, 0xdf, 0x22, 0xff}}},
    {{{   134,      1,     55}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003008
static const Vtx bully_seg5_vertex_05003008[] = {
    {{{  -133,    -56,      0}, 0, {     0,      0}, {0x8b, 0xcf, 0x00, 0xff}}},
    {{{  -133,    -40,     39}, 0, {     0,      0}, {0x8b, 0xdd, 0x22, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -101,   -103,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{  -102,    -73,     72}, 0, {     0,      0}, {0xa7, 0xc0, 0x3f, 0xff}}},
    {{{   -54,   -134,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -54,    -95,     94}, 0, {     0,      0}, {0xd0, 0xad, 0x52, 0xff}}},
    {{{     1,   -145,      0}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0xff}}},
    {{{     0,   -102,    102}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{    56,   -133,      0}, 0, {     0,      0}, {0x31, 0x8c, 0x00, 0xff}}},
    {{{    56,    -94,     94}, 0, {     0,      0}, {0x31, 0xae, 0x52, 0xff}}},
    {{{   103,   -101,      0}, 0, {     0,      0}, {0x5a, 0xa8, 0x00, 0xff}}},
    {{{   103,    -71,     72}, 0, {     0,      0}, {0x5a, 0xc2, 0x3f, 0xff}}},
    {{{   134,    -54,      0}, 0, {     0,      0}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   134,    -38,     39}, 0, {     0,      0}, {0x75, 0xdf, 0x22, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003108
static const Vtx bully_seg5_vertex_05003108[] = {
    {{{  -133,    -40,    -39}, 0, {     0,      0}, {0x8b, 0xdd, 0xde, 0xff}}},
    {{{  -133,    -56,      0}, 0, {     0,      0}, {0x8b, 0xcf, 0x00, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -102,    -73,    -72}, 0, {     0,      0}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{  -101,   -103,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -54,    -95,    -94}, 0, {     0,      0}, {0xd0, 0xad, 0xae, 0xff}}},
    {{{   -54,   -134,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,   -102,   -102}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{     1,   -145,      0}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0xff}}},
    {{{    56,    -94,    -94}, 0, {     0,      0}, {0x31, 0xae, 0xae, 0xff}}},
    {{{    56,   -133,      0}, 0, {     0,      0}, {0x31, 0x8c, 0x00, 0xff}}},
    {{{   103,    -71,    -72}, 0, {     0,      0}, {0x5a, 0xc2, 0xc1, 0xff}}},
    {{{   103,   -101,      0}, 0, {     0,      0}, {0x5a, 0xa8, 0x00, 0xff}}},
    {{{   134,    -38,    -39}, 0, {     0,      0}, {0x75, 0xdf, 0xde, 0xff}}},
    {{{   134,    -54,      0}, 0, {     0,      0}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003208
static const Vtx bully_seg5_vertex_05003208[] = {
    {{{  -134,     -1,    -55}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0xff}}},
    {{{  -133,    -40,    -39}, 0, {     0,      0}, {0x8b, 0xdd, 0xde, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -102,      0,   -102}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{  -102,    -73,    -72}, 0, {     0,      0}, {0xa7, 0xc0, 0xc1, 0xff}}},
    {{{   -55,      0,   -134}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{   -54,    -95,    -94}, 0, {     0,      0}, {0xd0, 0xad, 0xae, 0xff}}},
    {{{     0,      0,   -145}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   -102,   -102}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{    55,      0,   -134}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{    56,    -94,    -94}, 0, {     0,      0}, {0x31, 0xae, 0xae, 0xff}}},
    {{{   102,      0,   -102}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   103,    -71,    -72}, 0, {     0,      0}, {0x5a, 0xc2, 0xc1, 0xff}}},
    {{{   134,      1,    -55}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   134,    -38,    -39}, 0, {     0,      0}, {0x75, 0xdf, 0xde, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003308
static const Vtx bully_seg5_vertex_05003308[] = {
    {{{  -134,     38,    -39}, 0, {     0,      0}, {0x8b, 0x21, 0xde, 0xff}}},
    {{{  -134,     -1,    -55}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -103,     71,    -72}, 0, {     0,      0}, {0xa6, 0x3e, 0xc1, 0xff}}},
    {{{  -102,      0,   -102}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{   -56,     94,    -94}, 0, {     0,      0}, {0xcf, 0x52, 0xae, 0xff}}},
    {{{   -55,      0,   -134}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{     0,    102,   -102}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{     0,      0,   -145}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    54,     95,    -94}, 0, {     0,      0}, {0x30, 0x53, 0xae, 0xff}}},
    {{{    55,      0,   -134}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{   102,     73,    -72}, 0, {     0,      0}, {0x59, 0x40, 0xc1, 0xff}}},
    {{{   102,      0,   -102}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   133,     40,    -39}, 0, {     0,      0}, {0x75, 0x23, 0xde, 0xff}}},
    {{{   134,      1,    -55}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003408
static const Vtx bully_seg5_vertex_05003408[] = {
    {{{  -134,     54,      0}, 0, {     0,      0}, {0x8b, 0x2f, 0x00, 0xff}}},
    {{{  -134,     38,    -39}, 0, {     0,      0}, {0x8b, 0x21, 0xde, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -103,    101,      0}, 0, {     0,      0}, {0xa6, 0x58, 0x00, 0xff}}},
    {{{  -103,     71,    -72}, 0, {     0,      0}, {0xa6, 0x3e, 0xc1, 0xff}}},
    {{{   -56,    133,      0}, 0, {     0,      0}, {0xcf, 0x74, 0x00, 0xff}}},
    {{{   -56,     94,    -94}, 0, {     0,      0}, {0xcf, 0x52, 0xae, 0xff}}},
    {{{    -1,    145,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{     0,    102,   -102}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{    54,    134,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    54,     95,    -94}, 0, {     0,      0}, {0x30, 0x53, 0xae, 0xff}}},
    {{{   101,    103,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{   102,     73,    -72}, 0, {     0,      0}, {0x59, 0x40, 0xc1, 0xff}}},
    {{{   133,     56,      0}, 0, {     0,      0}, {0x75, 0x31, 0x00, 0xff}}},
    {{{   133,     40,    -39}, 0, {     0,      0}, {0x75, 0x23, 0xde, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003508
static const Vtx bully_seg5_vertex_05003508[] = {
    {{{  -134,     38,     39}, 0, {     0,      0}, {0x8b, 0x21, 0x22, 0xff}}},
    {{{  -134,     54,      0}, 0, {     0,      0}, {0x8b, 0x2f, 0x00, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -103,     71,     72}, 0, {     0,      0}, {0xa6, 0x3e, 0x3f, 0xff}}},
    {{{  -103,    101,      0}, 0, {     0,      0}, {0xa6, 0x58, 0x00, 0xff}}},
    {{{   -56,     94,     94}, 0, {     0,      0}, {0xcf, 0x52, 0x52, 0xff}}},
    {{{   -56,    133,      0}, 0, {     0,      0}, {0xcf, 0x74, 0x00, 0xff}}},
    {{{     0,    102,    102}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{    -1,    145,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{    54,     95,     94}, 0, {     0,      0}, {0x30, 0x53, 0x52, 0xff}}},
    {{{    54,    134,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{   102,     73,     72}, 0, {     0,      0}, {0x59, 0x40, 0x3f, 0xff}}},
    {{{   101,    103,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{   133,     40,     39}, 0, {     0,      0}, {0x75, 0x23, 0x22, 0xff}}},
    {{{   133,     56,      0}, 0, {     0,      0}, {0x75, 0x31, 0x00, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003608
static const Vtx bully_seg5_vertex_05003608[] = {
    {{{  -134,     -1,     55}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0xff}}},
    {{{  -134,     38,     39}, 0, {     0,      0}, {0x8b, 0x21, 0x22, 0x00}}},
    {{{  -145,     -1,      0}, 0, {     0,      0}, {0x81, 0xff, 0x00, 0x00}}},
    {{{  -102,      0,    102}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{  -103,     71,     72}, 0, {     0,      0}, {0xa6, 0x3e, 0x3f, 0xff}}},
    {{{   -55,      0,    134}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{   -56,     94,     94}, 0, {     0,      0}, {0xcf, 0x52, 0x52, 0xff}}},
    {{{     0,      0,    145}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    102,    102}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{    55,      0,    134}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{    54,     95,     94}, 0, {     0,      0}, {0x30, 0x53, 0x52, 0xff}}},
    {{{   102,      0,    102}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   102,     73,     72}, 0, {     0,      0}, {0x59, 0x40, 0x3f, 0xff}}},
    {{{   134,      1,     55}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   133,     40,     39}, 0, {     0,      0}, {0x75, 0x23, 0x22, 0xff}}},
    {{{   145,      1,      0}, 0, {     0,      0}, {0x7f, 0x01, 0x00, 0xff}}},
};

// 0x05003708 - 0x050037A0
const Gfx bully_seg5_dl_05003708[] = {
    gsSPLight(&bully_seg5_lights_05000408.l, 1),
    gsSPLight(&bully_seg5_lights_05000408.a, 2),
    gsSPVertex(bully_seg5_vertex_05002C68, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 1,  6,  7, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0,  8,  2,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  8, 12, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  0,  8, 11, 0x0),
    gsSPVertex(bully_seg5_vertex_05002D48, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x050037A0 - 0x05003838
const Gfx bully_seg5_dl_050037A0[] = {
    gsSPLight(&bully_seg5_lights_05000420.l, 1),
    gsSPLight(&bully_seg5_lights_05000420.a, 2),
    gsSPVertex(bully_seg5_vertex_05002D88, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 3,  1,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  7,  1, 0x0,  1,  7,  2, 0x0),
    gsSP2Triangles( 8,  0,  2, 0x0,  9, 10,  0, 0x0),
    gsSP2Triangles( 9,  0,  8, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles(11,  8, 13, 0x0, 13,  8,  2, 0x0),
    gsSPVertex(bully_seg5_vertex_05002E68, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003838 - 0x05003878
const Gfx bully_seg5_dl_05003838[] = {
    gsSPLight((u8*)bully_seg5_texture_05000468 + 0x8, 1), //! yet another malformed light...
    gsSPLight(bully_seg5_texture_05000468, 2),
    gsSPVertex(bully_seg5_vertex_05002EA8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003878 - 0x05003C50
const Gfx bully_seg5_dl_05003878[] = {
    gsSPLight(&bully_seg5_lights_05000450.l, 1),
    gsSPLight(&bully_seg5_lights_05000450.a, 2),
    gsSPVertex(bully_seg5_vertex_05002F08, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003008, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003108, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003208, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003308, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003408, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003508, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(bully_seg5_vertex_05003608, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003C50
static const Vtx bully_seg5_vertex_05003C50[] = {
    {{{     0,     57,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -55,    -55,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -55,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -55,     57,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05003C90
static const Vtx bully_seg5_vertex_05003C90[] = {
    {{{    57,     57,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -55,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    57,    -55,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     57,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05003CD0 - 0x05003D08
const Gfx bully_seg5_dl_05003CD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_05000468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05003C50, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003D08 - 0x05003D40
const Gfx bully_seg5_dl_05003D08[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_05001468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05003C90, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003D40 - 0x05003DB8
const Gfx bully_seg5_dl_05003D40[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bully_seg5_dl_05003CD0),
    gsSPDisplayList(bully_seg5_dl_05003D08),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05003DB8
static const Vtx bully_seg5_vertex_05003DB8[] = {
    {{{     0,    114,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -111,   -111,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -111,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -111,    114,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05003DF8
static const Vtx bully_seg5_vertex_05003DF8[] = {
    {{{   114,    114,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -111,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   114,   -111,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    114,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05003E38 - 0x05003E70
const Gfx bully_seg5_dl_05003E38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_05000468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05003DB8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003E70 - 0x05003EA8
const Gfx bully_seg5_dl_05003E70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_05001468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05003DF8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003EA8 - 0x05003F20
const Gfx bully_seg5_dl_05003EA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bully_seg5_dl_05003E38),
    gsSPDisplayList(bully_seg5_dl_05003E70),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05003F20
static const Vtx bully_seg5_vertex_05003F20[] = {
    {{{   -36,    152,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,    136,     68}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    56,    124,     68}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    140,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    56,    124,    -64}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,    136,    -64}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05003F80 - 0x05003FC8
const Gfx bully_seg5_dl_05003F80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bully_seg5_texture_05002468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bully_seg5_vertex_05003F20, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05003FC8 - 0x05004038
const Gfx bully_seg5_dl_05003FC8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bully_seg5_dl_05003F80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
