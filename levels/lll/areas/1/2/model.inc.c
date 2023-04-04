// 0x07013620 - 0x07013638
static const Lights1 lll_seg7_lights_07013620 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07013638 - 0x07013738
static const Vtx lll_seg7_vertex_07013638[] = {
    {{{ -3327,     10,   3584}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -3071,     10,   3584}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -3071,     10,   3328}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -3327,     10,   3328}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2816,   1280,   8192}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,    256,   8192}, 0, {     0,   1244}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,    256,   7552}, 0, {   766,   1244}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,    563,   5504}, 0, {  3322,    862}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,   1280,   4608}, 0, {  4440,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,    563,   4608}, 0, {  4440,    862}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  2816,   1280,   4608}, 0, {     0,    224}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  2816,    563,   4608}, 0, {     0,   1116}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  4352,    563,   4608}, 0, {  1884,   1116}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  5888,   1280,   4608}, 0, {  3800,    224}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  4352,      0,   4608}, 0, {  1884,   1818}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  5888,      0,   4608}, 0, {  3800,   1818}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07013738 - 0x070137C0
static const Gfx lll_seg7_dl_07013738[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_07013620.l, 1),
    gsSPLight(&lll_seg7_lights_07013620.a, 2),
    gsSPVertex(lll_seg7_vertex_07013638, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  8, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 13, 10, 0x0),
    gsSP2Triangles(12, 14, 15, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x070137C0 - 0x07013830
const Gfx lll_seg7_dl_070137C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07013738),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
