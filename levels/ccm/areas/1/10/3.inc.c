// 0x07010A60 - 0x07010A78
static const Lights1 ccm_seg7_lights_07010A60 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07010A78 - 0x07010AF8
static const Vtx ccm_seg7_vertex_07010A78[] = {
    {{{   -55,    540,    574}, 0, {  -912,   4482}, {0x01, 0x7e, 0x00, 0xff}}},
    {{{ -1163,    553,    677}, 0, {   308,    990}, {0x01, 0x7e, 0x00, 0xff}}},
    {{{ -1120,    553,    770}, 0, {     0,    990}, {0x01, 0x7e, 0x00, 0xff}}},
    {{{   -12,    540,    667}, 0, { -1254,   4482}, {0x01, 0x7e, 0x00, 0xff}}},
    {{{ -1799,    553,    517}, 0, {  1672,   -714}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1120,    553,    770}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1333,    553,    304}, 0, {  1672,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1586,    553,    983}, 0, {     0,   -714}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07010AF8 - 0x07010B50
static const Gfx ccm_seg7_dl_07010AF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_07010A60.l, 1),
    gsSPLight(&ccm_seg7_lights_07010A60.a, 2),
    gsSPVertex(ccm_seg7_vertex_07010A78, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07010B50 - 0x07010BD0
const Gfx ccm_seg7_dl_07010B50[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 90),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_07010AF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
