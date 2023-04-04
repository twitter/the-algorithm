// Possible Removed Actor File
// It's possible that bubba and bub used to be 2 "actors" in
// one actor file.

// Bin ID? What is this?
UNUSED static const u64 bubba_unused_1 = 0;

// Bubba

// 0x05000008
ALIGNED8 static const Texture bubba_seg5_texture_05000008[] = {
#include "actors/bubba/bubba_sunglasses.rgba16.inc.c"
};

// unused eye texture, assumed leftover from when actor file was copied from bub
// 0x05000408
ALIGNED8 static const Texture bubba_seg5_texture_05000408[] = {
#include "actors/bubba/bubba_eyes_unused.rgba16.inc.c"
};

// 0x05001408
ALIGNED8 static const Texture bubba_seg5_texture_05001408[] = {
#include "actors/bubba/bubba_eye_border.rgba16.inc.c"
};

// 0x05001C08
ALIGNED8 static const Texture bubba_seg5_texture_05001C08[] = {
#include "actors/bubba/bubba_fins.rgba16.inc.c"
};

// 0x05002408
ALIGNED8 static const Texture bubba_seg5_texture_05002408[] = {
#include "actors/bubba/bubba_scales.rgba16.inc.c"
};

// 0x05004408
static const Lights1 bubba_seg5_lights_05004408 = gdSPDefLights1(
    0x48, 0x31, 0x2a,
    0xf1, 0xa6, 0x8c, 0x28, 0x28, 0x28
);

// 0x05004420
static const Lights1 bubba_seg5_lights_05004420 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004438
static const Lights1 bubba_seg5_lights_05004438 = gdSPDefLights1(
    0x10, 0x07, 0x08,
    0x36, 0x1a, 0x1c, 0x28, 0x28, 0x28
);

// 0x05004450
static const Vtx bubba_seg5_vertex_05004450[] = {
    {{{   239,   -320,    320}, 0, {   203,    530}, {0x44, 0xad, 0x42, 0xff}}},
    {{{     6,   -197,    290}, 0, {   372,    190}, {0x00, 0x99, 0x49, 0xff}}},
    {{{     6,   -284,    420}, 0, {   267,    636}, {0x01, 0x07, 0x7e, 0xff}}},
    {{{   123,   -217,    469}, 0, {   369,    578}, {0x09, 0x99, 0x48, 0xff}}},
    {{{     7,   -195,    518}, 0, {   407,    618}, {0x00, 0xaf, 0x61, 0xff}}},
    {{{  -109,   -217,    471}, 0, {   369,    580}, {0xf8, 0x99, 0x48, 0xff}}},
    {{{  -227,   -320,    324}, 0, {   203,    536}, {0xbd, 0xad, 0x43, 0xff}}},
};

// 0x050044C0
static const Vtx bubba_seg5_vertex_050044C0[] = {
    {{{  -512,   -352,     46}, 0, {   856,    794}, {0xab, 0xa7, 0xe5, 0xff}}},
    {{{  -248,   -181,    193}, 0, {    94,    684}, {0xf5, 0x65, 0x4b, 0xff}}},
    {{{  -508,   -306,    182}, 0, {   816,    650}, {0xaf, 0x47, 0x41, 0xff}}},
    {{{  -451,   -258,    151}, 0, {   664,    694}, {0xb3, 0x64, 0x08, 0xff}}},
    {{{  -450,   -258,     81}, 0, {   678,    768}, {0xc5, 0x6b, 0xe1, 0xff}}},
    {{{  -352,   -273,    -31}, 0, {   432,    906}, {0xdc, 0x4c, 0xa2, 0xff}}},
    {{{  -269,   -214,     84}, 0, {   176,    796}, {0x6b, 0x1b, 0xc3, 0xff}}},
    {{{     4,    -82,   -352}, 0, {   384,    704}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{     3,     43,   -352}, 0, {   554,    698}, {0xff, 0x3c, 0x6f, 0xff}}},
    {{{   -44,    -19,   -501}, 0, {   458,    800}, {0x82, 0xff, 0xfa, 0xff}}},
    {{{    52,    -18,   -501}, 0, {   458,    800}, {0x7e, 0x01, 0xfa, 0xff}}},
    {{{     6,   -222,   -550}, 0, {   182,    844}, {0x00, 0x85, 0xe1, 0xff}}},
    {{{     1,    184,   -550}, 0, {   728,    822}, {0xff, 0x7b, 0xe2, 0xff}}},
    {{{     4,    -94,   -615}, 0, {   348,    880}, {0x00, 0xe5, 0x84, 0xff}}},
    {{{     3,     56,   -615}, 0, {   552,    872}, {0x00, 0x1b, 0x84, 0xff}}},
};

// 0x050045B0
static const Vtx bubba_seg5_vertex_050045B0[] = {
    {{{   513,   -352,     46}, 0, {   482,    860}, {0x55, 0xa7, 0xe5, 0xff}}},
    {{{   353,   -273,    -31}, 0, {   896,    900}, {0x24, 0x4c, 0xa2, 0xff}}},
    {{{   451,   -258,     81}, 0, {   626,    798}, {0x3b, 0x6b, 0xe1, 0xff}}},
    {{{   452,   -258,    151}, 0, {   608,    716}, {0x4d, 0x64, 0x08, 0xff}}},
    {{{   509,   -306,    182}, 0, {   458,    698}, {0x51, 0x47, 0x41, 0xff}}},
    {{{   270,   -214,     84}, 0, {  1072,    736}, {0x95, 0x1b, 0xc3, 0xff}}},
    {{{   249,   -181,    193}, 0, {  1098,    600}, {0x0b, 0x65, 0x4b, 0xff}}},
};

// 0x05004620
static const Vtx bubba_seg5_vertex_05004620[] = {
    {{{    51,    274,    117}, 0, {   360,    626}, {0x6b, 0x41, 0x12, 0xff}}},
    {{{     5,    317,    341}, 0, {   -14,    458}, {0x00, 0x59, 0x5a, 0xff}}},
    {{{     5,    215,    291}, 0, {    72,    912}, {0x01, 0x59, 0x5a, 0xff}}},
    {{{   -42,    274,    118}, 0, {   358,    626}, {0x96, 0x41, 0x13, 0xff}}},
    {{{     3,    404,    -20}, 0, {   586,     18}, {0x00, 0x7e, 0xf2, 0xff}}},
    {{{     1,    191,   -223}, 0, {   928,    960}, {0x00, 0x6d, 0xbf, 0xff}}},
};

// 0x05004680
static const Vtx bubba_seg5_vertex_05004680[] = {
    {{{   261,    127,    217}, 0, {     0,  -2344}, {0x5f, 0x53, 0x07, 0xff}}},
    {{{   350,   -212,    251}, 0, {  -196,   -646}, {0x7c, 0xf6, 0x18, 0xff}}},
    {{{   222,     29,   -150}, 0, {  1808,  -1854}, {0x68, 0x23, 0xc1, 0xff}}},
    {{{     0,     44,   -355}, 0, {  2828,  -1928}, {0x00, 0x2f, 0x8b, 0xff}}},
    {{{     0,    -85,   -355}, 0, {  2828,  -1278}, {0xff, 0xe3, 0x85, 0xff}}},
    {{{  -218,     29,   -147}, 0, {  1788,  -1854}, {0x97, 0x23, 0xc3, 0xff}}},
    {{{     1,    191,   -223}, 0, {  2172,  -2664}, {0x00, 0x6d, 0xbf, 0xff}}},
    {{{  -246,   -210,   -101}, 0, {  1560,   -654}, {0xa7, 0xb8, 0xcc, 0xff}}},
    {{{  -251,    127,    221}, 0, {   -48,  -2344}, {0xa1, 0x53, 0x08, 0xff}}},
    {{{  -339,   -212,    257}, 0, {  -224,   -646}, {0x85, 0xf6, 0x1a, 0xff}}},
    {{{     1,   -337,   -197}, 0, {  2040,    -22}, {0x00, 0x95, 0xbd, 0xff}}},
    {{{   251,   -210,   -105}, 0, {  1580,   -654}, {0x58, 0xb8, 0xca, 0xff}}},
    {{{  -227,   -320,    324}, 0, {  -560,   -106}, {0xbd, 0xad, 0x43, 0xff}}},
    {{{   230,   -105,    361}, 0, {  -744,  -1182}, {0x5d, 0x15, 0x53, 0xff}}},
    {{{   239,   -320,    320}, 0, {  -540,   -106}, {0x44, 0xad, 0x42, 0xff}}},
    {{{    51,    274,    117}, 0, {   468,  -3076}, {0x6b, 0x41, 0x12, 0xff}}},
};

// 0x05004780
static const Vtx bubba_seg5_vertex_05004780[] = {
    {{{   -42,    274,    118}, 0, {   464,  -3076}, {0x96, 0x41, 0x13, 0xff}}},
    {{{     1,    191,   -223}, 0, {  2172,  -2664}, {0x00, 0x6d, 0xbf, 0xff}}},
    {{{  -251,    127,    221}, 0, {   -48,  -2344}, {0xa1, 0x53, 0x08, 0xff}}},
    {{{   -58,     79,    436}, 0, { -1120,  -2104}, {0xe3, 0x42, 0x67, 0xff}}},
    {{{     5,    215,    291}, 0, {  -396,  -2784}, {0x01, 0x59, 0x5a, 0xff}}},
    {{{  -217,   -105,    364}, 0, {  -760,  -1182}, {0xa4, 0x15, 0x54, 0xff}}},
    {{{  -339,   -212,    257}, 0, {  -224,   -646}, {0x85, 0xf6, 0x1a, 0xff}}},
    {{{   230,   -105,    361}, 0, {  -744,  -1182}, {0x5d, 0x15, 0x53, 0xff}}},
    {{{   350,   -212,    251}, 0, {  -196,   -646}, {0x7c, 0xf6, 0x18, 0xff}}},
    {{{   261,    127,    217}, 0, {     0,  -2344}, {0x5f, 0x53, 0x07, 0xff}}},
    {{{    65,     79,    434}, 0, { -1108,  -2104}, {0x1f, 0x42, 0x67, 0xff}}},
    {{{    51,    274,    117}, 0, {   468,  -3076}, {0x6b, 0x41, 0x12, 0xff}}},
    {{{   239,   -320,    320}, 0, {  -540,   -106}, {0x44, 0xad, 0x42, 0xff}}},
    {{{   251,   -210,   -105}, 0, {  1580,   -654}, {0x58, 0xb8, 0xca, 0xff}}},
    {{{  -227,   -320,    324}, 0, {  -560,   -106}, {0xbd, 0xad, 0x43, 0xff}}},
};

// 0x05004870
static const Vtx bubba_seg5_vertex_05004870[] = {
    {{{   129,   -136,    504}, 0, {     0,      0}, {0x48, 0xf3, 0x67, 0xff}}},
    {{{   123,   -217,    469}, 0, {     0,      0}, {0x09, 0x99, 0x48, 0xff}}},
    {{{   239,   -320,    320}, 0, {     0,      0}, {0x44, 0xad, 0x42, 0xff}}},
    {{{   230,   -105,    361}, 0, {     0,      0}, {0x5d, 0x15, 0x53, 0xff}}},
    {{{     6,   -284,    420}, 0, {     0,      0}, {0x01, 0x07, 0x7e, 0xff}}},
    {{{     6,   -357,    343}, 0, {     0,      0}, {0x00, 0x8d, 0x35, 0xff}}},
    {{{  -227,   -320,    324}, 0, {     0,      0}, {0xbd, 0xad, 0x43, 0xff}}},
    {{{     7,    -24,    547}, 0, {     0,      0}, {0x00, 0x4b, 0x65, 0xff}}},
    {{{     6,     -7,    415}, 0, {     0,      0}, {0x01, 0x26, 0x78, 0xff}}},
    {{{  -217,   -105,    364}, 0, {     0,      0}, {0xa4, 0x15, 0x54, 0xff}}},
    {{{  -115,   -136,    506}, 0, {     0,      0}, {0xba, 0xf3, 0x68, 0xff}}},
    {{{     7,   -195,    518}, 0, {     0,      0}, {0x00, 0xaf, 0x61, 0xff}}},
    {{{  -109,   -217,    471}, 0, {     0,      0}, {0xf8, 0x99, 0x48, 0xff}}},
};

// 0x05004940
static const Vtx bubba_seg5_vertex_05004940[] = {
    {{{  -217,   -105,    364}, 0, {     0,      0}, {0xa4, 0x15, 0x54, 0xff}}},
    {{{  -255,    129,    314}, 0, {     0,      0}, {0xa6, 0x42, 0x3b, 0xff}}},
    {{{  -251,    127,    221}, 0, {     0,      0}, {0xa1, 0x53, 0x08, 0xff}}},
    {{{    65,     79,    434}, 0, {     0,      0}, {0x1f, 0x42, 0x67, 0xff}}},
    {{{     6,     -7,    415}, 0, {     0,      0}, {0x01, 0x26, 0x78, 0xff}}},
    {{{   230,   -105,    361}, 0, {     0,      0}, {0x5d, 0x15, 0x53, 0xff}}},
    {{{   267,    129,    309}, 0, {     0,      0}, {0x5b, 0x42, 0x39, 0xff}}},
    {{{   261,    127,    217}, 0, {     0,      0}, {0x5f, 0x53, 0x07, 0xff}}},
    {{{   -58,     79,    436}, 0, {     0,      0}, {0xe3, 0x42, 0x67, 0xff}}},
};

// 0x050049D0
static const Vtx bubba_seg5_vertex_050049D0[] = {
    {{{     4,   -377,    154}, 0, {     0,      0}, {0x00, 0x82, 0xf7, 0xff}}},
    {{{   239,   -320,    320}, 0, {     0,      0}, {0x44, 0xad, 0x42, 0xff}}},
    {{{     6,   -357,    343}, 0, {     0,      0}, {0x00, 0x8d, 0x35, 0xff}}},
    {{{   251,   -210,   -105}, 0, {     0,      0}, {0x58, 0xb8, 0xca, 0xff}}},
    {{{  -246,   -210,   -101}, 0, {     0,      0}, {0xa7, 0xb8, 0xcc, 0xff}}},
    {{{     1,   -337,   -197}, 0, {     0,      0}, {0x00, 0x95, 0xbd, 0xff}}},
    {{{  -227,   -320,    324}, 0, {     0,      0}, {0xbd, 0xad, 0x43, 0xff}}},
};

// 0x05004A40 - 0x05004AA8
const Gfx bubba_seg5_dl_05004A40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05000008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bubba_seg5_lights_05004408.l, 1),
    gsSPLight(&bubba_seg5_lights_05004408.a, 2),
    gsSPVertex(bubba_seg5_vertex_05004450, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 4,  1,  3, 0x0,  5,  1,  4, 0x0),
    gsSP2Triangles( 1,  6,  2, 0x0,  5,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004AA8 - 0x05004BE8
const Gfx bubba_seg5_dl_05004AA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05001408),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bubba_seg5_lights_05004420.l, 1),
    gsSPLight(&bubba_seg5_lights_05004420.a, 2),
    gsSPVertex(bubba_seg5_vertex_050044C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  5,  0,  4, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 6,  4,  1, 0x0,  1,  4,  3, 0x0),
    gsSP2Triangles( 1,  3,  2, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  8,  7, 0x0),
    gsSP2Triangles( 9, 11,  7, 0x0, 10,  7, 11, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0, 12,  8, 10, 0x0),
    gsSP2Triangles(13,  9, 14, 0x0,  9, 12, 14, 0x0),
    gsSP2Triangles(13, 11,  9, 0x0, 12, 10, 14, 0x0),
    gsSP2Triangles(13, 14, 10, 0x0, 10, 11, 13, 0x0),
    gsSPVertex(bubba_seg5_vertex_050045B0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  0, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  6,  5, 0x0,  4,  6,  0, 0x0),
    gsSP2Triangles( 6,  2,  5, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 6,  3,  2, 0x0,  3,  6,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004BE8 - 0x05004C40
const Gfx bubba_seg5_dl_05004BE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05001C08),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bubba_seg5_vertex_05004620, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  1,  0,  4, 0x0),
    gsSP2Triangles( 4,  5,  3, 0x0,  4,  0,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004C40 - 0x05004D48
const Gfx bubba_seg5_dl_05004C40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05002408),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bubba_seg5_vertex_05004680, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9,  8,  5, 0x0),
    gsSP2Triangles( 9,  5,  7, 0x0,  2,  4,  3, 0x0),
    gsSP2Triangles(10,  4, 11, 0x0,  7,  4, 10, 0x0),
    gsSP2Triangles(11,  4,  2, 0x0,  9,  7, 12, 0x0),
    gsSP2Triangles( 2,  3,  6, 0x0,  0,  2,  6, 0x0),
    gsSP2Triangles( 2,  1, 11, 0x0,  1, 13, 14, 0x0),
    gsSP1Triangle( 0,  6, 15, 0x0),
    gsSPVertex(bubba_seg5_vertex_05004780, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  5,  2,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(11,  4, 10, 0x0, 10,  4,  3, 0x0),
    gsSP2Triangles(12, 13,  8, 0x0,  5,  6, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004D48 - 0x05004E80
const Gfx bubba_seg5_dl_05004D48[] = {
    gsSPLight(&bubba_seg5_lights_05004408.l, 1),
    gsSPLight(&bubba_seg5_lights_05004408.a, 2),
    gsSPVertex(bubba_seg5_vertex_05004870, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  4,  6,  5, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  8,  7,  3, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 11,  0, 0x0),
    gsSP2Triangles(10, 11,  7, 0x0,  3,  7,  0, 0x0),
    gsSP2Triangles( 0, 11,  1, 0x0,  6, 10,  9, 0x0),
    gsSP2Triangles(12, 10,  6, 0x0, 12, 11, 10, 0x0),
    gsSPLight(&bubba_seg5_lights_05004438.l, 1),
    gsSPLight(&bubba_seg5_lights_05004438.a, 2),
    gsSPVertex(bubba_seg5_vertex_05004940, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  5,  6,  3, 0x0),
    gsSP2Triangles( 3,  8,  4, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 8,  2,  1, 0x0,  8,  1,  0, 0x0),
    gsSP1Triangle( 4,  8,  0, 0x0),
    gsSPLight(&bubba_seg5_lights_05004420.l, 1),
    gsSPLight(&bubba_seg5_lights_05004420.a, 2),
    gsSPVertex(bubba_seg5_vertex_050049D0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  0,  5,  3, 0x0),
    gsSP2Triangles( 2,  6,  0, 0x0,  6,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004E80 - 0x05004F30
const Gfx bubba_seg5_dl_05004E80[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_05004A40),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_05004AA8),
    gsSPDisplayList(bubba_seg5_dl_05004BE8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_05004C40),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(bubba_seg5_dl_05004D48),
    gsSPEndDisplayList(),
};

// 0x05004F30
static const Lights1 bubba_seg5_lights_05004F30 = gdSPDefLights1(
    0x48, 0x31, 0x2a,
    0xf1, 0xa6, 0x8c, 0x28, 0x28, 0x28
);

// 0x05004F48
static const Lights1 bubba_seg5_lights_05004F50 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05004F60
static const Lights1 bubba_seg5_lights_05004F60 = gdSPDefLights1(
    0x0a, 0x07, 0x08,
    0x22, 0x1a, 0x1c, 0x28, 0x28, 0x28
);

// 0x05004F78
static const Vtx bubba_seg5_vertex_05004F78[] = {
    {{{     6,   -194,     96}, 0, {   405,     48}, {0x00, 0xbf, 0x6d, 0xff}}},
    {{{  -227,   -437,    270}, 0, {   201,    516}, {0xba, 0xb3, 0x47, 0xff}}},
    {{{     6,   -401,    385}, 0, {   222,    642}, {0x00, 0x0b, 0x7e, 0xff}}},
    {{{     7,    -58,    559}, 0, {   485,    556}, {0x00, 0xb2, 0x64, 0xff}}},
    {{{   123,    -93,    488}, 0, {   461,    492}, {0xf6, 0xa6, 0x58, 0xff}}},
    {{{   239,   -437,    266}, 0, {   201,    512}, {0x47, 0xb3, 0x46, 0xff}}},
    {{{  -108,    -93,    488}, 0, {   461,    492}, {0x0c, 0xa5, 0x57, 0xff}}},
};

// 0x05004FE8
static const Vtx bubba_seg5_vertex_05004FE8[] = {
    {{{   513,   -352,     46}, 0, {   482,    860}, {0x55, 0xa7, 0xe5, 0xff}}},
    {{{   451,   -258,     81}, 0, {   626,    798}, {0x3b, 0x6b, 0xe1, 0xff}}},
    {{{   452,   -258,    151}, 0, {   608,    716}, {0x4d, 0x64, 0x08, 0xff}}},
    {{{   270,   -214,     84}, 0, {  1072,    736}, {0x95, 0x1b, 0xc3, 0xff}}},
    {{{   353,   -273,    -31}, 0, {   896,    900}, {0x24, 0x4c, 0xa2, 0xff}}},
    {{{   249,   -181,    193}, 0, {  1098,    600}, {0x0b, 0x65, 0x4b, 0xff}}},
    {{{   509,   -306,    182}, 0, {   458,    698}, {0x51, 0x47, 0x41, 0xff}}},
    {{{     1,    184,   -550}, 0, {   728,    822}, {0xff, 0x7b, 0xe2, 0xff}}},
    {{{     3,     43,   -352}, 0, {   554,    698}, {0xff, 0x3c, 0x6f, 0xff}}},
    {{{    52,    -18,   -501}, 0, {   458,    800}, {0x7e, 0x01, 0xfa, 0xff}}},
    {{{     4,    -94,   -615}, 0, {   348,    880}, {0x00, 0xe5, 0x84, 0xff}}},
    {{{     3,     56,   -615}, 0, {   552,    872}, {0x00, 0x1b, 0x84, 0xff}}},
    {{{     4,    -82,   -352}, 0, {   384,    704}, {0x00, 0xc4, 0x6f, 0xff}}},
    {{{     6,   -222,   -550}, 0, {   182,    844}, {0x00, 0x85, 0xe1, 0xff}}},
    {{{   -44,    -19,   -501}, 0, {   458,    800}, {0x82, 0xff, 0xfa, 0xff}}},
};

// 0x050050D8
static const Vtx bubba_seg5_vertex_050050D8[] = {
    {{{  -512,   -352,     46}, 0, {   856,    794}, {0xab, 0xa7, 0xe5, 0xff}}},
    {{{  -508,   -306,    182}, 0, {   816,    650}, {0xaf, 0x47, 0x41, 0xff}}},
    {{{  -451,   -258,    151}, 0, {   664,    694}, {0xb3, 0x64, 0x08, 0xff}}},
    {{{  -450,   -258,     81}, 0, {   678,    768}, {0xc5, 0x6b, 0xe1, 0xff}}},
    {{{  -248,   -181,    193}, 0, {    94,    684}, {0xf5, 0x65, 0x4b, 0xff}}},
    {{{  -352,   -273,    -31}, 0, {   432,    906}, {0xdc, 0x4c, 0xa2, 0xff}}},
    {{{  -269,   -214,     84}, 0, {   176,    796}, {0x6b, 0x1b, 0xc3, 0xff}}},
};

// 0x05005148
static const Vtx bubba_seg5_vertex_05005148[] = {
    {{{     5,    465,    287}, 0, {   -14,    458}, {0x00, 0x43, 0x6b, 0xff}}},
    {{{    51,    274,    117}, 0, {   360,    626}, {0x6a, 0x44, 0x06, 0xff}}},
    {{{     3,    404,    -20}, 0, {   586,     18}, {0x00, 0x71, 0xc7, 0xff}}},
    {{{     5,    317,    249}, 0, {    72,    912}, {0x01, 0x5d, 0x56, 0xff}}},
    {{{   -42,    274,    118}, 0, {   358,    626}, {0x96, 0x44, 0x07, 0xff}}},
    {{{     1,    191,   -223}, 0, {   928,    960}, {0x00, 0x6b, 0xbd, 0xff}}},
};

// 0x050051A8
static const Vtx bubba_seg5_vertex_050051A8[] = {
    {{{  -339,   -241,    257}, 0, {  -224,   -646}, {0x83, 0xf8, 0x14, 0xff}}},
    {{{  -251,    216,    243}, 0, {   -48,  -2344}, {0xa6, 0x59, 0xfe, 0xff}}},
    {{{  -218,     29,   -147}, 0, {  1788,  -1854}, {0x98, 0x20, 0xc0, 0xff}}},
    {{{     0,    -85,   -355}, 0, {  2828,  -1278}, {0xff, 0xe7, 0x84, 0xff}}},
    {{{  -246,   -210,   -101}, 0, {  1560,   -654}, {0xa0, 0xca, 0xc2, 0xff}}},
    {{{     1,   -356,   -213}, 0, {  2040,    -22}, {0x00, 0xa4, 0xa9, 0xff}}},
    {{{  -227,   -437,    270}, 0, {  -560,   -106}, {0xba, 0xb3, 0x47, 0xff}}},
    {{{   222,     29,   -150}, 0, {  1808,  -1854}, {0x67, 0x20, 0xbe, 0xff}}},
    {{{     0,     44,   -355}, 0, {  2828,  -1928}, {0x00, 0x2f, 0x8b, 0xff}}},
    {{{   251,   -210,   -105}, 0, {  1580,   -654}, {0x5f, 0xca, 0xc0, 0xff}}},
    {{{   239,   -437,    266}, 0, {  -540,   -106}, {0x47, 0xb3, 0x46, 0xff}}},
    {{{   350,   -241,    251}, 0, {  -196,   -646}, {0x7d, 0xf9, 0x11, 0xff}}},
    {{{  -217,     18,    383}, 0, {  -760,  -1182}, {0xa0, 0x13, 0x4f, 0xff}}},
    {{{   -42,    274,    118}, 0, {   464,  -3076}, {0x96, 0x44, 0x07, 0xff}}},
    {{{     1,    191,   -223}, 0, {  2172,  -2664}, {0x00, 0x6b, 0xbd, 0xff}}},
    {{{   -58,    168,    458}, 0, { -1120,  -2104}, {0xdf, 0x49, 0x61, 0xff}}},
};

// 0x050052A8
static const Vtx bubba_seg5_vertex_050052A8[] = {
    {{{   350,   -241,    251}, 0, {  -196,   -646}, {0x7d, 0xf9, 0x11, 0xff}}},
    {{{   230,     18,    380}, 0, {  -744,  -1182}, {0x61, 0x14, 0x4e, 0xff}}},
    {{{   239,   -437,    266}, 0, {  -540,   -106}, {0x47, 0xb3, 0x46, 0xff}}},
    {{{   261,    216,    239}, 0, {     0,  -2344}, {0x5a, 0x59, 0xfd, 0xff}}},
    {{{   222,     29,   -150}, 0, {  1808,  -1854}, {0x67, 0x20, 0xbe, 0xff}}},
    {{{     0,     44,   -355}, 0, {  2828,  -1928}, {0x00, 0x2f, 0x8b, 0xff}}},
    {{{     1,    191,   -223}, 0, {  2172,  -2664}, {0x00, 0x6b, 0xbd, 0xff}}},
    {{{    65,    168,    456}, 0, { -1108,  -2104}, {0x23, 0x49, 0x61, 0xff}}},
    {{{    51,    274,    117}, 0, {   468,  -3076}, {0x6a, 0x44, 0x06, 0xff}}},
    {{{     5,    317,    249}, 0, {  -396,  -2784}, {0x01, 0x5d, 0x56, 0xff}}},
    {{{   -58,    168,    458}, 0, { -1120,  -2104}, {0xdf, 0x49, 0x61, 0xff}}},
    {{{   -42,    274,    118}, 0, {   464,  -3076}, {0x96, 0x44, 0x07, 0xff}}},
};

// 0x05005368
static const Vtx bubba_seg5_vertex_05005368[] = {
    {{{  -227,   -437,    270}, 0, {     0,      0}, {0xba, 0xb3, 0x47, 0xff}}},
    {{{  -133,    -12,    525}, 0, {     0,      0}, {0xbb, 0xed, 0x68, 0xff}}},
    {{{  -217,     18,    383}, 0, {     0,      0}, {0xa0, 0x13, 0x4f, 0xff}}},
    {{{     6,   -401,    385}, 0, {     0,      0}, {0x00, 0x0b, 0x7e, 0xff}}},
    {{{     6,   -503,    295}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{     7,    111,    588}, 0, {     0,      0}, {0x00, 0x5b, 0x57, 0xff}}},
    {{{     7,    -58,    559}, 0, {     0,      0}, {0x00, 0xb2, 0x64, 0xff}}},
    {{{     6,     81,    437}, 0, {     0,      0}, {0x01, 0x31, 0x74, 0xff}}},
    {{{   239,   -437,    266}, 0, {     0,      0}, {0x47, 0xb3, 0x46, 0xff}}},
    {{{   150,    -12,    523}, 0, {     0,      0}, {0x47, 0xee, 0x67, 0xff}}},
    {{{   123,    -93,    488}, 0, {     0,      0}, {0xf6, 0xa6, 0x58, 0xff}}},
    {{{  -108,    -93,    488}, 0, {     0,      0}, {0x0c, 0xa5, 0x57, 0xff}}},
    {{{   230,     18,    380}, 0, {     0,      0}, {0x61, 0x14, 0x4e, 0xff}}},
};

// 0x05005438
static const Vtx bubba_seg5_vertex_05005438[] = {
    {{{  -217,     18,    383}, 0, {     0,      0}, {0xa0, 0x13, 0x4f, 0xff}}},
    {{{  -255,    217,    336}, 0, {     0,      0}, {0xa5, 0x40, 0x3c, 0xff}}},
    {{{  -251,    216,    243}, 0, {     0,      0}, {0xa6, 0x59, 0xfe, 0xff}}},
    {{{    65,    168,    456}, 0, {     0,      0}, {0x23, 0x49, 0x61, 0xff}}},
    {{{   267,    217,    332}, 0, {     0,      0}, {0x5b, 0x41, 0x3a, 0xff}}},
    {{{   261,    216,    239}, 0, {     0,      0}, {0x5a, 0x59, 0xfd, 0xff}}},
    {{{   230,     18,    380}, 0, {     0,      0}, {0x61, 0x14, 0x4e, 0xff}}},
    {{{     6,     81,    437}, 0, {     0,      0}, {0x01, 0x31, 0x74, 0xff}}},
    {{{   -58,    168,    458}, 0, {     0,      0}, {0xdf, 0x49, 0x61, 0xff}}},
};

// 0x050054C8
static const Vtx bubba_seg5_vertex_050054C8[] = {
    {{{     4,   -488,     81}, 0, {     0,      0}, {0x00, 0x88, 0xd8, 0xff}}},
    {{{   239,   -437,    266}, 0, {     0,      0}, {0x47, 0xb3, 0x46, 0xff}}},
    {{{     6,   -503,    295}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{  -227,   -437,    270}, 0, {     0,      0}, {0xba, 0xb3, 0x47, 0xff}}},
    {{{   251,   -210,   -105}, 0, {     0,      0}, {0x5f, 0xca, 0xc0, 0xff}}},
    {{{  -246,   -210,   -101}, 0, {     0,      0}, {0xa0, 0xca, 0xc2, 0xff}}},
    {{{     1,   -356,   -213}, 0, {     0,      0}, {0x00, 0xa4, 0xa9, 0xff}}},
};

// 0x05005538 - 0x050055A0
const Gfx bubba_seg5_dl_05005538[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05000008),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bubba_seg5_lights_05004F30.l, 1),
    gsSPLight(&bubba_seg5_lights_05004F30.a, 2),
    gsSPVertex(bubba_seg5_vertex_05004F78, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 4,  0,  5, 0x0,  5,  0,  2, 0x0),
    gsSP2Triangles( 6,  0,  3, 0x0,  1,  0,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x050055A0 - 0x050056E0
const Gfx bubba_seg5_dl_050055A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05001408),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bubba_seg5_lights_05004F50.l, 1),
    gsSPLight(&bubba_seg5_lights_05004F50.a, 2),
    gsSPVertex(bubba_seg5_vertex_05004FE8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  6,  5,  0, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  5,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10, 11,  9, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0,  7,  9, 11, 0x0),
    gsSP2Triangles( 9, 13, 10, 0x0,  9, 12, 13, 0x0),
    gsSP2Triangles(12,  8, 14, 0x0, 14, 13, 12, 0x0),
    gsSP2Triangles(10, 13, 14, 0x0, 10, 14, 11, 0x0),
    gsSP2Triangles(14,  8,  7, 0x0, 14,  7, 11, 0x0),
    gsSPVertex(bubba_seg5_vertex_050050D8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  4,  3,  2, 0x0),
    gsSP2Triangles( 5,  0,  3, 0x0,  5,  3,  6, 0x0),
    gsSP2Triangles( 6,  3,  4, 0x0,  0,  4,  1, 0x0),
    gsSP2Triangles( 0,  6,  4, 0x0,  5,  6,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x050056E0 - 0x05005738
const Gfx bubba_seg5_dl_050056E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05001C08),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bubba_seg5_vertex_05005148, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  2,  5,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005738 - 0x05005840
const Gfx bubba_seg5_dl_05005738[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bubba_seg5_texture_05002408),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bubba_seg5_vertex_050051A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  0,  4,  6, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 5,  3,  9, 0x0,  8,  3,  2, 0x0),
    gsSP2Triangles( 9,  3,  7, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles( 7, 11,  9, 0x0, 12,  1,  0, 0x0),
    gsSP2Triangles(12,  0,  6, 0x0, 13, 14,  1, 0x0),
    gsSP2Triangles( 2,  1, 14, 0x0,  1, 15, 13, 0x0),
    gsSP1Triangle(14,  8,  2, 0x0),
    gsSPVertex(bubba_seg5_vertex_050052A8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 3,  4,  6, 0x0,  7,  3,  8, 0x0),
    gsSP2Triangles( 3,  6,  8, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0, 10,  9, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005840 - 0x05005978
const Gfx bubba_seg5_dl_05005840[] = {
    gsSPLight(&bubba_seg5_lights_05004F30.l, 1),
    gsSPLight(&bubba_seg5_lights_05004F30.a, 2),
    gsSPVertex(bubba_seg5_vertex_05005368, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  2,  1, 0x0,  1,  6,  5, 0x0),
    gsSP2Triangles( 5,  7,  2, 0x0,  8,  3,  4, 0x0),
    gsSP2Triangles( 9,  6, 10, 0x0,  5,  6,  9, 0x0),
    gsSP2Triangles(11,  6,  1, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles(12,  5,  9, 0x0,  7,  5, 12, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0,  0, 11,  1, 0x0),
    gsSPLight(&bubba_seg5_lights_05004F60.l, 1),
    gsSPLight(&bubba_seg5_lights_05004F60.a, 2),
    gsSPVertex(bubba_seg5_vertex_05005438, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 3,  7,  6, 0x0,  3,  8,  7, 0x0),
    gsSP2Triangles( 7,  8,  0, 0x0,  8,  1,  0, 0x0),
    gsSP1Triangle( 8,  2,  1, 0x0),
    gsSPLight(&bubba_seg5_lights_05004F50.l, 1),
    gsSPLight(&bubba_seg5_lights_05004F50.a, 2),
    gsSPVertex(bubba_seg5_vertex_050054C8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  5,  6,  0, 0x0),
    gsSP2Triangles( 3,  5,  0, 0x0,  0,  6,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005978 - 0x05005A28
const Gfx bubba_seg5_dl_05005978[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_05005538),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_050055A0),
    gsSPDisplayList(bubba_seg5_dl_050056E0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bubba_seg5_dl_05005738),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(bubba_seg5_dl_05005840),
    gsSPEndDisplayList(),
};
