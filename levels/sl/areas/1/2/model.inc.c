// 0x07005520 - 0x07005538
static const Lights1 sl_seg7_lights_07005520 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07005538 - 0x07005638
static const Vtx sl_seg7_vertex_07005538[] = {
    {{{   768,   1352,  -5375}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -200,   1352,  -3761}, 0, {  1866,   -908}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1091,   1352,  -4084}, 0, {  1866,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1024,    922,  -3071}, 0, { -3096,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1023,    922,  -3071}, 0, { -3096,   2010}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -818,    922,  -1023}, 0, { -1052,   1806}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1024,    922,  -1023}, 0, { -1052,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   365,   3328,   2052}, 0, {  2016,    626}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2027,   3328,    608}, 0, {   576,  -1034}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1725,   3328,    260}, 0, {   228,   -732}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    63,   3328,   1704}, 0, {  1668,    928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  3328,    973,  -4351}, 0, { -4374,  -2332}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2304,    973,  -5375}, 0, { -5396,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  2304,    973,  -4351}, 0, { -4374,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  3328,    973,  -5375}, 0, { -5396,  -2332}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -522,   1352,  -5052}, 0, {     0,   -908}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07005638 - 0x070056B0
static const Gfx sl_seg7_dl_07005638[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&sl_seg7_lights_07005520.l, 1),
    gsSPLight(&sl_seg7_lights_07005520.a, 2),
    gsSPVertex(sl_seg7_vertex_07005538, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070056B0 - 0x07005730
const Gfx sl_seg7_dl_070056B0[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 180),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(sl_seg7_dl_07005638),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
