// 0x07013238 - 0x07013250
static const Lights1 wdw_seg7_lights_07013238 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07013250 - 0x07013340
static const Vtx wdw_seg7_vertex_07013250[] = {
    {{{   193,      0,    193}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -190,      0,    193}, 0, {   990,  -2076}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -190,      0,   -190}, 0, { -2074,  -2076}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   193,    128,   -190}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   193,      0,   -190}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -190,      0,   -190}, 0, {   990,  -2076}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -190,    128,   -190}, 0, {     0,  -2076}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   193,    128,    193}, 0, {   990,  -2076}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   193,      0,    193}, 0, {     0,  -2076}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   193,      0,   -190}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   193,    128,   -190}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -190,    128,    193}, 0, {     0,  -2076}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -190,      0,    193}, 0, {   990,  -2076}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   193,      0,    193}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   193,    128,    193}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07013340 - 0x070133F0
static const Vtx wdw_seg7_vertex_07013340[] = {
    {{{   193,    128,   -190}, 0, { -2074,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -190,    128,    193}, 0, {   990,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   193,    128,    193}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   193,      0,    193}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -190,      0,   -190}, 0, { -2074,  -2076}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   193,      0,   -190}, 0, { -2074,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -190,    128,   -190}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -190,      0,    193}, 0, {     0,  -2076}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -190,    128,    193}, 0, {   990,  -2076}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -190,      0,   -190}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -190,    128,   -190}, 0, { -2074,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070133F0 - 0x07013490
static const Gfx wdw_seg7_dl_070133F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07013238.l, 1),
    gsSPLight(&wdw_seg7_lights_07013238.a, 2),
    gsSPVertex(wdw_seg7_vertex_07013250, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wdw_seg7_vertex_07013340, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP1Triangle( 0, 10,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013490 - 0x07013500
const Gfx wdw_seg7_dl_07013490[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_070133F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
