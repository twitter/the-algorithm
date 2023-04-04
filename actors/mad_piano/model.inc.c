// Mad Piano

// Unreferenced light group
UNUSED static const Lights1 mad_piano_lights_unused1 = gdSPDefLights1(
    0x05, 0x04, 0x08,
    0x14, 0x13, 0x20, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 mad_piano_lights_unused2 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 mad_piano_lights_unused3 = gdSPDefLights1(
    0x2c, 0x2c, 0x2c,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 mad_piano_lights_unused4 = gdSPDefLights1(
    0x30, 0x00, 0x00,
    0xc3, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x05006AF0
ALIGNED8 static const Texture mad_piano_seg5_texture_05006AF0[] = {
#include "actors/mad_piano/mad_piano_tooth.rgba16.inc.c"
};

// 0x050072F0
ALIGNED8 static const Texture mad_piano_seg5_texture_050072F0[] = {
#include "actors/mad_piano/mad_piano_body.rgba16.inc.c"
};

// 0x050076F0
ALIGNED8 static const Texture mad_piano_seg5_texture_050076F0[] = {
#include "actors/mad_piano/mad_piano_keys_corner.rgba16.inc.c"
};

// 0x05007AF0
ALIGNED8 static const Texture mad_piano_seg5_texture_05007AF0[] = {
#include "actors/mad_piano/mad_piano_mouth.rgba16.inc.c"
};

// 0x05007EF0
ALIGNED8 static const Texture mad_piano_seg5_texture_05007EF0[] = {
#include "actors/mad_piano/mad_piano_keys.rgba16.inc.c"
};

// 0x050082F0
ALIGNED8 static const Texture mad_piano_seg5_texture_050082F0[] = {
#include "actors/mad_piano/mad_piano_keys_edge.rgba16.inc.c"
};

// 0x050086F0
static const Lights1 mad_piano_seg5_lights_050086F0 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05008708
static const Vtx mad_piano_seg5_vertex_05008708[] = {
    {{{     0,      0,    609}, 0, {    17,    776}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   834,     -2,   -523}, 0, {   529,    420}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   626,     -1,   -740}, 0, {   566,    662}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{     0,      0,    609}, 0, {    17,    776}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   201,      0,   -740}, 0, {   495,   1020}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      0,   -523}, 0, {   390,   1124}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   201,      0,   -740}, 0, {   495,   1020}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{  1136,     -2,    140}, 0, {   361,    -38}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   887,     -2,    -47}, 0, {   381,    228}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{  1136,     -2,    609}, 0, {   206,   -184}, {0x00, 0x7e, 0x00, 0xff}}},
};

// 0x050087A8
static const Vtx mad_piano_seg5_vertex_050087A8[] = {
    {{{   641,     -4,    600}, 0, {  -344,    224}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   721,   -189,    600}, 0, {   920,    884}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   802,     -5,    600}, 0, {   536,   -572}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     2,     -2,    600}, 0, {   998,    390}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    83,   -135,    600}, 0, {    22,    878}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   163,     -3,    600}, 0, {   136,   -574}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   160,     -3,    600}, 0, {   -78,    226}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   241,   -164,    600}, 0, {  1000,    906}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   321,     -3,    600}, 0, {   876,   -476}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   320,     -3,    600}, 0, {  -370,    262}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   401,   -189,    600}, 0, {  1036,    966}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   482,     -4,    600}, 0, {   588,   -652}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   479,     -4,    600}, 0, {  -184,    116}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   559,   -211,    600}, 0, {   914,    830}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   640,     -4,    600}, 0, {   566,   -574}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x05008898
static const Vtx mad_piano_seg5_vertex_05008898[] = {
    {{{  1126,     -6,    337}, 0, {    68,    134}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1110,   -157,    267}, 0, {   728,    892}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1126,     -6,    198}, 0, {   726,   -548}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{   799,     -5,    600}, 0, {    14,    114}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   880,   -164,    600}, 0, {   904,    824}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   961,     -5,    600}, 0, {   868,   -900}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   959,     -5,    600}, 0, {   -42,    174}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  1040,   -156,    600}, 0, {   982,    896}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  1120,     -5,    600}, 0, {   974,   -680}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  1126,     -6,    598}, 0, {   -22,    132}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1110,   -159,    541}, 0, {   690,    890}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1126,     -6,    472}, 0, {   574,   -626}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1126,     -6,    475}, 0, {   -38,    232}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1110,   -152,    405}, 0, {   764,    754}, {0x7e, 0xf3, 0x00, 0xff}}},
    {{{  1126,     -6,    336}, 0, {   650,   -482}, {0x7e, 0xf3, 0x00, 0xff}}},
};

// 0x05008988
static const Vtx mad_piano_seg5_vertex_05008988[] = {
    {{{   435,     -4,   -728}, 0, {    90,    138}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   354,   -123,   -728}, 0, {   858,    806}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   274,     -3,   -728}, 0, {   978,   -868}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1126,     -6,    201}, 0, {   120,    -18}, {0x6b, 0x03, 0xbc, 0xff}}},
    {{{  1097,   -151,    148}, 0, {   816,    786}, {0x6b, 0x03, 0xbc, 0xff}}},
    {{{  1059,     -5,     96}, 0, {   662,   -828}, {0x6b, 0x03, 0xbc, 0xff}}},
    {{{  1059,     -5,     97}, 0, {   182,    198}, {0x3c, 0x05, 0x91, 0xff}}},
    {{{  1007,   -140,     62}, 0, {   808,    914}, {0x3c, 0x05, 0x91, 0xff}}},
    {{{   933,     -5,     28}, 0, {   958,   -596}, {0x3c, 0x05, 0x91, 0xff}}},
    {{{   935,     -5,     29}, 0, {   -62,    182}, {0x67, 0xfe, 0xb8, 0xff}}},
    {{{   895,   -134,    -23}, 0, {   834,    682}, {0x67, 0xfe, 0xb8, 0xff}}},
    {{{   860,     -5,    -78}, 0, {   694,   -694}, {0x67, 0xfe, 0xb8, 0xff}}},
    {{{   860,     -5,    -78}, 0, {   128,    310}, {0x7e, 0x01, 0xf3, 0xff}}},
    {{{   854,   -119,   -147}, 0, {   746,    928}, {0x7e, 0x01, 0xf3, 0xff}}},
    {{{   845,     -5,   -216}, 0, {   712,   -528}, {0x7e, 0x01, 0xf3, 0xff}}},
};

// 0x05008A78
static const Vtx mad_piano_seg5_vertex_05008A78[] = {
    {{{   845,     -5,   -216}, 0, {    10,    306}, {0x7e, 0x02, 0xf5, 0xff}}},
    {{{   841,   -123,   -288}, 0, {   828,    886}, {0x7e, 0x02, 0xf5, 0xff}}},
    {{{   831,     -5,   -368}, 0, {   878,   -954}, {0x7e, 0x02, 0xf5, 0xff}}},
    {{{   594,     -4,   -728}, 0, {  -258,    222}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   513,   -123,   -728}, 0, {   840,    686}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   433,     -3,   -728}, 0, {   838,   -766}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   707,     -4,   -612}, 0, {   -84,    164}, {0x5a, 0x00, 0xa8, 0xff}}},
    {{{   651,   -123,   -670}, 0, {   866,    834}, {0x5a, 0x00, 0xa8, 0xff}}},
    {{{   594,     -4,   -728}, 0, {   992,   -908}, {0x5a, 0x00, 0xa8, 0xff}}},
    {{{   817,     -5,   -501}, 0, {  -268,    128}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   759,   -123,   -559}, 0, {   872,    820}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   707,     -4,   -612}, 0, {   826,   -972}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   831,     -5,   -368}, 0, {   166,     88}, {0x7e, 0x02, 0xf3, 0xff}}},
    {{{   828,   -123,   -421}, 0, {   836,    872}, {0x7e, 0x02, 0xf3, 0xff}}},
    {{{   817,     -5,   -501}, 0, {   818,   -724}, {0x7e, 0x02, 0xf3, 0xff}}},
};

// 0x05008B68 - 0x05008BD0
const Gfx mad_piano_seg5_dl_05008B68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_050072F0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mad_piano_seg5_lights_050086F0.l, 1),
    gsSPLight(&mad_piano_seg5_lights_050086F0.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_05008708, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  0,  7,  8, 0x0),
    gsSP2Triangles( 0,  8,  1, 0x0,  0,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008BD0 - 0x05008CB0
const Gfx mad_piano_seg5_dl_05008BD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_05006AF0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(mad_piano_seg5_vertex_050087A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05008898, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05008988, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05008A78, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x05008CB0 - 0x05008D40
const Gfx mad_piano_seg5_dl_05008CB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_05008B68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_05008BD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x05008D40
static const Lights1 mad_piano_seg5_lights_05008D40 = gdSPDefLights1(
    0x3d, 0x3d, 0x3d,
    0xcc, 0xcc, 0xcc, 0x28, 0x28, 0x28
);

// 0x05008D58
static const Lights1 mad_piano_seg5_lights_05008D58 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05008D70
static const Lights1 mad_piano_seg5_lights_05008D70 = gdSPDefLights1(
    0x26, 0x26, 0x26,
    0x7f, 0x7f, 0x7f, 0x28, 0x28, 0x28
);

// 0x05008D88
static const Lights1 mad_piano_seg5_lights_05008D88 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x05008DA0
static const Vtx mad_piano_seg5_vertex_05008DA0[] = {
    {{{   454,    592,     18}, 0, {   262,    962}, {0x2d, 0x74, 0x17, 0xff}}},
    {{{   -14,    586,    220}, 0, {   485,    588}, {0xfe, 0x09, 0x7e, 0xff}}},
    {{{   454,    592,    220}, 0, {   485,    962}, {0x92, 0x00, 0x3d, 0xff}}},
    {{{   454,    592,     18}, 0, {   411,   -102}, {0x2d, 0x74, 0x17, 0xff}}},
    {{{   454,    592,    220}, 0, {   160,    118}, {0x92, 0x00, 0x3d, 0xff}}},
    {{{   467,   -543,    220}, 0, {   160,   1024}, {0x70, 0xfe, 0x3a, 0xff}}},
    {{{   467,   -543,     18}, 0, {   411,    804}, {0x23, 0x9b, 0x43, 0xff}}},
    {{{  -199,    336,    -92}, 0, {   138,    418}, {0xda, 0x5f, 0xb6, 0xff}}},
    {{{  -674,    277,    220}, 0, {   485,     36}, {0xfb, 0x08, 0x7e, 0xff}}},
    {{{  -199,    336,    220}, 0, {   485,    418}, {0xff, 0x0b, 0x7e, 0xff}}},
    {{{  -674,    277,    -92}, 0, {   138,     36}, {0xc9, 0x58, 0xb8, 0xff}}},
    {{{   -14,    586,    -92}, 0, {   138,    588}, {0xce, 0x62, 0xc3, 0xff}}},
    {{{   454,    592,    -92}, 0, {   138,    962}, {0x00, 0x38, 0x8f, 0xff}}},
};

// 0x05008E70
static const Vtx mad_piano_seg5_vertex_05008E70[] = {
    {{{   393,    432,   -372}, 0, {   -64,    714}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   444,    347,    -92}, 0, {   456,    672}, {0x3d, 0x9a, 0xd6, 0xff}}},
    {{{   294,    431,    -92}, 0, {   428,    926}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{  -598,   -135,   -372}, 0, {   -21,    860}, {0xff, 0x00, 0x82, 0xff}}},
    {{{  -549,    -48,    -92}, 0, {   446,    702}, {0x3a, 0x68, 0xd6, 0xff}}},
    {{{  -547,   -221,    -92}, 0, {   456,    934}, {0x3c, 0x9a, 0xd6, 0xff}}},
    {{{  -598,   -135,   -372}, 0, {   -37,    724}, {0xff, 0x00, 0x82, 0xff}}},
    {{{  -547,   -221,    -92}, 0, {   417,    570}, {0x3c, 0x9a, 0xd6, 0xff}}},
    {{{  -697,   -136,    -92}, 0, {   425,    938}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{  -598,   -135,   -372}, 0, {     0,    736}, {0xff, 0x00, 0x82, 0xff}}},
    {{{  -697,   -136,    -92}, 0, {   428,    620}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{  -549,    -48,    -92}, 0, {   422,    816}, {0x3a, 0x68, 0xd6, 0xff}}},
    {{{   393,    432,   -372}, 0, {   -42,    728}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   442,    519,    -92}, 0, {   498,    594}, {0x3a, 0x68, 0xd6, 0xff}}},
    {{{   444,    347,    -92}, 0, {   493,    952}, {0x3d, 0x9a, 0xd6, 0xff}}},
};

// 0x05008F60
static const Vtx mad_piano_seg5_vertex_05008F60[] = {
    {{{  -884,   -357,    -92}, 0, {     0,     50}, {0x9c, 0xc0, 0xd6, 0xff}}},
    {{{  -665,   -556,    220}, 0, {   488,    286}, {0x05, 0xed, 0x7d, 0xff}}},
    {{{  -884,   -357,    220}, 0, {   488,     50}, {0xf5, 0xfd, 0x7e, 0xff}}},
    {{{   393,    432,   -372}, 0, {    82,    738}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   294,    431,    -92}, 0, {   472,    582}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{   442,    519,    -92}, 0, {   462,    838}, {0x3a, 0x68, 0xd6, 0xff}}},
    {{{   403,   -384,   -372}, 0, {   -36,    732}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   451,   -297,    -92}, 0, {   450,    628}, {0x3a, 0x68, 0xd6, 0xff}}},
    {{{   453,   -470,    -92}, 0, {   441,    938}, {0x3c, 0x99, 0xd6, 0xff}}},
    {{{   403,   -384,   -372}, 0, {   -58,    714}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   453,   -470,    -92}, 0, {   495,    672}, {0x3c, 0x99, 0xd6, 0xff}}},
    {{{   303,   -385,    -92}, 0, {   505,    990}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{   403,   -384,   -372}, 0, {   -47,    688}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   303,   -385,    -92}, 0, {   448,    628}, {0x89, 0xff, 0xd6, 0xff}}},
    {{{   451,   -297,    -92}, 0, {   480,    948}, {0x3a, 0x68, 0xd6, 0xff}}},
};

// 0x05009050
static const Vtx mad_piano_seg5_vertex_05009050[] = {
    {{{  -674,    277,    -92}, 0, {   138,     36}, {0xc9, 0x58, 0xb8, 0xff}}},
    {{{  -889,     67,    220}, 0, {   485,   -156}, {0xf5, 0x02, 0x7e, 0xff}}},
    {{{  -674,    277,    220}, 0, {   485,     36}, {0xfb, 0x08, 0x7e, 0xff}}},
    {{{  -884,   -357,    -92}, 0, {     0,     50}, {0x9c, 0xc0, 0xd6, 0xff}}},
    {{{  -665,   -556,    -92}, 0, {     0,    286}, {0xf5, 0xbe, 0x95, 0xff}}},
    {{{  -665,   -556,    220}, 0, {   488,    286}, {0x05, 0xed, 0x7d, 0xff}}},
    {{{  -889,     67,    -92}, 0, {   -60,    754}, {0x9d, 0x19, 0xb6, 0xff}}},
    {{{  -884,   -357,    220}, 0, {   477,    972}, {0xf5, 0xfd, 0x7e, 0xff}}},
    {{{  -889,     67,    220}, 0, {   459,    754}, {0xf5, 0x02, 0x7e, 0xff}}},
    {{{  -884,   -357,    -92}, 0, {   -42,    972}, {0x9c, 0xc0, 0xd6, 0xff}}},
    {{{  -665,   -556,    -92}, 0, {    32,    596}, {0xf5, 0xbe, 0x95, 0xff}}},
    {{{   467,   -543,    220}, 0, {   422,    990}, {0x70, 0xfe, 0x3a, 0xff}}},
    {{{  -665,   -556,    220}, 0, {   422,    596}, {0x05, 0xed, 0x7d, 0xff}}},
    {{{   467,   -543,     18}, 0, {   171,    990}, {0x23, 0x9b, 0x43, 0xff}}},
    {{{   467,   -543,    -92}, 0, {    32,    990}, {0x01, 0x97, 0xba, 0xff}}},
    {{{  -889,     67,    -92}, 0, {   138,   -156}, {0x9d, 0x19, 0xb6, 0xff}}},
};

// 0x05009150
static const Vtx mad_piano_seg5_vertex_05009150[] = {
    {{{  -889,     67,    220}, 0, {  -124,     16}, {0xf5, 0x02, 0x7e, 0xff}}},
    {{{  -855,     64,     46}, 0, {     0,    936}, {0x5a, 0xea, 0x56, 0xff}}},
    {{{  -655,    253,     38}, 0, {   884,    936}, {0x33, 0xb0, 0x53, 0xff}}},
    {{{   -14,    586,    220}, 0, {   -53,     10}, {0xfe, 0x09, 0x7e, 0xff}}},
    {{{   -10,    556,     46}, 0, {   -33,    950}, {0x37, 0xb5, 0x55, 0xff}}},
    {{{   422,    560,     46}, 0, {  1395,    942}, {0xd3, 0xa3, 0x48, 0xff}}},
    {{{   454,    592,    220}, 0, {  1493,     -2}, {0x92, 0x00, 0x3d, 0xff}}},
    {{{  -199,    336,    220}, 0, {     3,    -12}, {0xff, 0x0b, 0x7e, 0xff}}},
    {{{   -10,    556,     46}, 0, {   917,    952}, {0x37, 0xb5, 0x55, 0xff}}},
    {{{   -14,    586,    220}, 0, {   976,      6}, {0xfe, 0x09, 0x7e, 0xff}}},
    {{{  -186,    307,     38}, 0, {   -34,    982}, {0x22, 0xa9, 0x54, 0xff}}},
    {{{  -674,    277,    220}, 0, {   -87,    -28}, {0xfb, 0x08, 0x7e, 0xff}}},
    {{{  -655,    253,     38}, 0, {     0,    990}, {0x33, 0xb0, 0x53, 0xff}}},
    {{{  -186,    307,     38}, 0, {  1537,    990}, {0x22, 0xa9, 0x54, 0xff}}},
    {{{  -199,    336,    220}, 0, {  1506,    -30}, {0xff, 0x0b, 0x7e, 0xff}}},
};

// 0x05009240
static const Vtx mad_piano_seg5_vertex_05009240[] = {
    {{{   467,   -543,    220}, 0, {  -137,      6}, {0x70, 0xfe, 0x3a, 0xff}}},
    {{{  -666,   -525,     46}, 0, {  3633,    936}, {0x1c, 0x6c, 0x3b, 0xff}}},
    {{{  -665,   -556,    220}, 0, {  3631,      6}, {0x05, 0xed, 0x7d, 0xff}}},
    {{{  -889,     67,    220}, 0, {  -124,     16}, {0xf5, 0x02, 0x7e, 0xff}}},
    {{{  -655,    253,     38}, 0, {   884,    936}, {0x33, 0xb0, 0x53, 0xff}}},
    {{{  -674,    277,    220}, 0, {   876,      0}, {0xfb, 0x08, 0x7e, 0xff}}},
    {{{  -884,   -357,    220}, 0, {   -99,      4}, {0xf5, 0xfd, 0x7e, 0xff}}},
    {{{  -855,     64,     46}, 0, {  1302,    936}, {0x5a, 0xea, 0x56, 0xff}}},
    {{{  -889,     67,    220}, 0, {  1313,      4}, {0xf5, 0x02, 0x7e, 0xff}}},
    {{{  -850,   -336,     46}, 0, {     0,    936}, {0x4f, 0x30, 0x55, 0xff}}},
    {{{  -665,   -556,    220}, 0, {   -90,     14}, {0x05, 0xed, 0x7d, 0xff}}},
    {{{  -850,   -336,     46}, 0, {   626,    936}, {0x4f, 0x30, 0x55, 0xff}}},
    {{{  -884,   -357,    220}, 0, {   648,      4}, {0xf5, 0xfd, 0x7e, 0xff}}},
    {{{  -666,   -525,     46}, 0, {     0,    936}, {0x1c, 0x6c, 0x3b, 0xff}}},
    {{{   435,   -512,     46}, 0, {     0,    936}, {0xdc, 0x12, 0x78, 0xff}}},
};

// 0x05009330
static const Vtx mad_piano_seg5_vertex_05009330[] = {
    {{{   454,    592,    220}, 0, {  -204,      6}, {0x92, 0x00, 0x3d, 0xff}}},
    {{{   435,   -512,     46}, 0, {  5439,   1010}, {0xdc, 0x12, 0x78, 0xff}}},
    {{{   467,   -543,    220}, 0, {  5605,     44}, {0x70, 0xfe, 0x3a, 0xff}}},
    {{{   422,    560,     46}, 0, {   -44,    978}, {0xd3, 0xa3, 0x48, 0xff}}},
    {{{   435,   -512,     46}, 0, {    67,    870}, {0xdc, 0x12, 0x78, 0xff}}},
    {{{  -655,    253,     38}, 0, {   340,    166}, {0x33, 0xb0, 0x53, 0xff}}},
    {{{  -855,     64,     46}, 0, {   406,    246}, {0x5a, 0xea, 0x56, 0xff}}},
    {{{  -186,    307,     38}, 0, {   204,    224}, {0x22, 0xa9, 0x54, 0xff}}},
    {{{  -850,   -336,     46}, 0, {   425,    502}, {0x4f, 0x30, 0x55, 0xff}}},
    {{{  -666,   -525,     46}, 0, {   382,    658}, {0x1c, 0x6c, 0x3b, 0xff}}},
    {{{   422,    560,     46}, 0, {    18,    184}, {0xd3, 0xa3, 0x48, 0xff}}},
    {{{   -10,    556,     46}, 0, {   141,    100}, {0x37, 0xb5, 0x55, 0xff}}},
};

// 0x050093F0
static const Vtx mad_piano_seg5_vertex_050093F0[] = {
    {{{   678,   -541,    -92}, 0, {     0,    479}, {0x55, 0xd7, 0xac, 0xff}}},
    {{{   665,    594,     18}, 0, {  4504,    -77}, {0x54, 0x2b, 0x54, 0xff}}},
    {{{   678,   -541,     18}, 0, {     0,    -77}, {0x34, 0x99, 0x33, 0xff}}},
    {{{   665,    594,    -92}, 0, {  4504,    479}, {0x32, 0x68, 0xcd, 0xff}}},
};

// 0x05009430
static const Vtx mad_piano_seg5_vertex_05009430[] = {
    {{{   467,   -543,     18}, 0, {   -40,   -224}, {0x23, 0x9b, 0x43, 0xff}}},
    {{{   665,    594,     18}, 0, {  4504,    436}, {0x54, 0x2b, 0x54, 0xff}}},
    {{{   454,    592,     18}, 0, {  4494,   -268}, {0x2d, 0x74, 0x17, 0xff}}},
    {{{   678,   -541,     18}, 0, {     0,    479}, {0x34, 0x99, 0x33, 0xff}}},
};

// 0x05009470
static const Vtx mad_piano_seg5_vertex_05009470[] = {
    {{{   454,    592,     18}, 0, {   972,    -27}, {0x2d, 0x74, 0x17, 0xff}}},
    {{{   665,    594,     18}, 0, {     0,    -27}, {0x54, 0x2b, 0x54, 0xff}}},
    {{{   665,    594,    -92}, 0, {     0,    479}, {0x32, 0x68, 0xcd, 0xff}}},
    {{{   454,    592,    -92}, 0, {   972,    479}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{   467,   -543,    -92}, 0, {   942,    479}, {0x01, 0x97, 0xba, 0xff}}},
    {{{   678,   -541,     18}, 0, {     0,    -27}, {0x34, 0x99, 0x33, 0xff}}},
    {{{   467,   -543,     18}, 0, {   942,    -27}, {0x23, 0x9b, 0x43, 0xff}}},
    {{{   678,   -541,    -92}, 0, {     0,    479}, {0x55, 0xd7, 0xac, 0xff}}},
};

// 0x050094F0
static const Vtx mad_piano_seg5_vertex_050094F0[] = {
    {{{  -665,   -556,    -92}, 0, {     0,      0}, {0xf5, 0xbe, 0x95, 0xff}}},
    {{{   -14,    586,    -92}, 0, {     0,      0}, {0xce, 0x62, 0xc3, 0xff}}},
    {{{   454,    592,    -92}, 0, {     0,      0}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{   467,   -543,    -92}, 0, {     0,      0}, {0x01, 0x97, 0xba, 0xff}}},
    {{{   678,   -541,    -92}, 0, {     0,      0}, {0x55, 0xd7, 0xac, 0xff}}},
    {{{   665,    594,    -92}, 0, {     0,      0}, {0x32, 0x68, 0xcd, 0xff}}},
    {{{  -199,    336,    -92}, 0, {     0,      0}, {0xda, 0x5f, 0xb6, 0xff}}},
    {{{  -674,    277,    -92}, 0, {     0,      0}, {0xc9, 0x58, 0xb8, 0xff}}},
    {{{  -889,     67,    -92}, 0, {     0,      0}, {0x9d, 0x19, 0xb6, 0xff}}},
    {{{  -884,   -357,    -92}, 0, {     0,      0}, {0x9c, 0xc0, 0xd6, 0xff}}},
};

// 0x05009590 - 0x050096C8
const Gfx mad_piano_seg5_dl_05009590[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_050072F0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mad_piano_seg5_lights_05008D40.l, 1),
    gsSPLight(&mad_piano_seg5_lights_05008D40.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_05008DA0, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11,  7,  9, 0x0),
    gsSP2Triangles(11,  9,  1, 0x0,  0, 12, 11, 0x0),
    gsSP1Triangle( 0, 11,  1, 0x0),
    gsSPLight(&mad_piano_seg5_lights_05008D58.l, 1),
    gsSPLight(&mad_piano_seg5_lights_05008D58.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_05008E70, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05008F60, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05009050, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP2Triangles(10, 14, 13, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x050096C8 - 0x050097B0
const Gfx mad_piano_seg5_dl_050096C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_05007AF0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(mad_piano_seg5_vertex_05009150, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05009240, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0, 14,  1, 0x0),
    gsSPVertex(mad_piano_seg5_vertex_05009330, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 4,  6,  8, 0x0,  4,  8,  9, 0x0),
    gsSP2Triangles( 4, 10, 11, 0x0,  4, 11,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x050097B0 - 0x050097F8
const Gfx mad_piano_seg5_dl_050097B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_050082F0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mad_piano_seg5_lights_05008D70.l, 1),
    gsSPLight(&mad_piano_seg5_lights_05008D70.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_050093F0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x050097F8 - 0x05009840
const Gfx mad_piano_seg5_dl_050097F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_05007EF0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&mad_piano_seg5_lights_05008D40.l, 1),
    gsSPLight(&mad_piano_seg5_lights_05008D40.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_05009430, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05009840 - 0x05009888
const Gfx mad_piano_seg5_dl_05009840[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mad_piano_seg5_texture_050076F0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(mad_piano_seg5_vertex_05009470, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05009888 - 0x050098E8
const Gfx mad_piano_seg5_dl_05009888[] = {
    gsSPLight(&mad_piano_seg5_lights_05008D88.l, 1),
    gsSPLight(&mad_piano_seg5_lights_05008D88.a, 2),
    gsSPVertex(mad_piano_seg5_vertex_050094F0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  2,  5,  4, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  0,  7,  6, 0x0),
    gsSP2Triangles( 0,  8,  7, 0x0,  0,  9,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x050098E8 - 0x050099B8
const Gfx mad_piano_seg5_dl_050098E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_05009590),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_050096C8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_050097B0),
    gsSPDisplayList(mad_piano_seg5_dl_050097F8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(mad_piano_seg5_dl_05009840),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(mad_piano_seg5_dl_05009888),
    gsSPEndDisplayList(),
};
