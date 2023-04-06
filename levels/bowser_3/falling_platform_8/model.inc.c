// 0x07003688 - 0x070036A0
static const Lights1 bowser_3_seg7_lights_07003688 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x070036A0 - 0x070036B8
static const Lights1 bowser_3_seg7_lights_070036A0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070036B8 - 0x07003718
static const Vtx bowser_3_seg7_vertex_070036B8[] = {
    {{{  2365,  -1852,   -787}, 0, {  3412,  -3518}, {0x3c, 0x99, 0xd7, 0xff}}},
    {{{  1946,  -1340,  -2661}, 0, {  5282,  -3098}, {0x3c, 0x99, 0xd7, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {  3648,  -4222}, {0x3c, 0x99, 0xd7, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {  3412,  -3518}, {0x2a, 0x90, 0xd8, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  3648,  -1944}, {0x2a, 0x90, 0xd8, 0xff}}},
    {{{  1946,  -1340,  -2661}, 0, {  5282,  -3098}, {0x2a, 0x90, 0xd8, 0xff}}},
};

// 0x07003718 - 0x07003758
static const Vtx bowser_3_seg7_vertex_07003718[] = {
    {{{   788,    307,  -1024}, 0, {  1514,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {  3034,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1946,    307,  -2661}, 0, {  2284,  -2962}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2365,    307,   -787}, 0, {  2564,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07003758 - 0x07003858
static const Vtx bowser_3_seg7_vertex_07003758[] = {
    {{{   788,  -2364,  -1024}, 0, { -1010,   2012}, {0x99, 0x00, 0xb7, 0xff}}},
    {{{  1946,    307,  -2661}, 0, {   990,      0}, {0x99, 0x00, 0xb7, 0xff}}},
    {{{  1946,  -1340,  -2661}, 0, {   990,   2012}, {0x99, 0x00, 0xb7, 0xff}}},
    {{{  1946,  -1340,  -2661}, 0, {  9926,   1816}, {0x68, 0x00, 0xb9, 0xff}}},
    {{{  1946,    307,  -2661}, 0, {  9926,    172}, {0x68, 0x00, 0xb9, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {  8418,    172}, {0x68, 0x00, 0xb9, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {  8418,   1816}, {0x68, 0x00, 0xb9, 0xff}}},
    {{{  3072,  -1340,  -1023}, 0, {   990,   2012}, {0x28, 0x00, 0x78, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   246,      0}, {0x28, 0x00, 0x78, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   246,   2012}, {0x28, 0x00, 0x78, 0xff}}},
    {{{  3072,    307,  -1023}, 0, {   990,      0}, {0x28, 0x00, 0x78, 0xff}}},
    {{{  2365,    307,   -787}, 0, {   990,      0}, {0xee, 0x00, 0x7d, 0xff}}},
    {{{   788,    307,  -1024}, 0, {  -600,      0}, {0xee, 0x00, 0x7d, 0xff}}},
    {{{  2365,  -1852,   -787}, 0, {   990,   2012}, {0xee, 0x00, 0x7d, 0xff}}},
    {{{   788,  -2364,  -1024}, 0, {  -600,   2012}, {0xee, 0x00, 0x7d, 0xff}}},
    {{{   788,    307,  -1024}, 0, { -1010,      0}, {0x99, 0x00, 0xb7, 0xff}}},
};

// 0x07003858 - 0x070038C8
static const Gfx bowser_3_seg7_dl_07003858[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07003688.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003688.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070036B8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_070036A0.l, 1),
    gsSPLight(&bowser_3_seg7_lights_070036A0.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07003718, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070038C8 - 0x07003930
static const Gfx bowser_3_seg7_dl_070038C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07003758, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003930 - 0x070039C0
const Gfx bowser_3_seg7_dl_07003930[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003858),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070038C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
