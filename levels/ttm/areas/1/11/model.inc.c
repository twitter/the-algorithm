// 0x0700CB60 - 0x0700CB78
static const Lights1 ttm_seg7_lights_0700CB60 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700CB78 - 0x0700CB90
static const Lights1 ttm_seg7_lights_0700CB78 = gdSPDefLights1(
    0x5d, 0x5d, 0x5d,
    0xbb, 0xbb, 0xbb, 0x28, 0x28, 0x28
);

// 0x0700CB90 - 0x0700CBF0
static const Vtx ttm_seg7_vertex_0700CB90[] = {
    {{{  -323,   -387,    -61}, 0, {  8458,  -5948}, {0x9c, 0xf1, 0x4b, 0xff}}},
    {{{   -30,    389,    495}, 0, {  9592,  -7498}, {0x9c, 0xf1, 0x4b, 0xff}}},
    {{{  -224,    389,    235}, 0, {  8972,  -7498}, {0x9c, 0xf1, 0x4b, 0xff}}},
    {{{  -323,   -387,    -61}, 0, {  8458,  -5948}, {0xc2, 0xd1, 0x63, 0xff}}},
    {{{   377,    389,    751}, 0, { 10552,  -7498}, {0xc2, 0xd1, 0x63, 0xff}}},
    {{{   -30,    389,    495}, 0, {  9592,  -7498}, {0xc2, 0xd1, 0x63, 0xff}}},
};

// 0x0700CBF0 - 0x0700CC20
static const Vtx ttm_seg7_vertex_0700CBF0[] = {
    {{{  -323,   -387,    -61}, 0, {   974,  -2960}, {0x82, 0x0d, 0x07, 0xff}}},
    {{{  -224,    389,    235}, 0, {  1654,  -4488}, {0x82, 0x0d, 0x07, 0xff}}},
    {{{  -282,    389,   -714}, 0, {  -132,  -4554}, {0x82, 0x0d, 0x07, 0xff}}},
};

// 0x0700CC20 - 0x0700CC60
static const Vtx ttm_seg7_vertex_0700CC20[] = {
    {{{   377,    389,    751}, 0, {  7672,  -3032}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -224,    389,    235}, 0, {  6470,  -4062}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -30,    389,    495}, 0, {  6856,  -3544}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -282,    389,   -714}, 0, {  6352,  -5960}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700CC60 - 0x0700CCC8
static const Gfx ttm_seg7_dl_0700CC60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700CB60.l, 1),
    gsSPLight(&ttm_seg7_lights_0700CB60.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700CB90, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&ttm_seg7_lights_0700CB78.l, 1),
    gsSPLight(&ttm_seg7_lights_0700CB78.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700CBF0, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700CCC8 - 0x0700CD10
static const Gfx ttm_seg7_dl_0700CCC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700CB60.l, 1),
    gsSPLight(&ttm_seg7_lights_0700CB60.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700CC20, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700CD10 - 0x0700CD88
const Gfx ttm_seg7_dl_0700CD10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700CC60),
    gsSPDisplayList(ttm_seg7_dl_0700CCC8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
