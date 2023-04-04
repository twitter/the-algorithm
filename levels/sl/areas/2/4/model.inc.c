// 0x0700CA68 - 0x0700CA80
static const Lights1 sl_seg7_lights_0700CA68 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700CA80 - 0x0700CB00
static const Vtx sl_seg7_vertex_0700CA80[] = {
    {{{   410,      0,   1126}, 0, {   786,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  2048,    614,   1126}, 0, {  2420,   -236}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   410,    614,   1126}, 0, {   786,   -236}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  2048,      0,   1126}, 0, {  2420,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2047,    614,   1126}, 0, { -1666,   -236}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -409,      0,   1126}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -409,    614,   1126}, 0, {     0,   -236}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2047,      0,   1126}, 0, { -1666,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0700CB00 - 0x0700CB58
static const Gfx sl_seg7_dl_0700CB00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&sl_seg7_lights_0700CA68.l, 1),
    gsSPLight(&sl_seg7_lights_0700CA68.a, 2),
    gsSPVertex(sl_seg7_vertex_0700CA80, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700CB58 - 0x0700CBD8
const Gfx sl_seg7_dl_0700CB58[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 100),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(sl_seg7_dl_0700CB00),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
