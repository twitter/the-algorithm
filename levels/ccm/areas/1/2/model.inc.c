// 0x0700B118 - 0x0700B130
static const Lights1 ccm_seg7_lights_0700B118 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700B130 - 0x0700B190
static const Vtx ccm_seg7_vertex_0700B130[] = {
    {{{   768,  -1535,   5118}, 0, {   820,    450}, {0x2c, 0x76, 0xf8, 0xff}}},
    {{{   805,  -1535,   5320}, 0, {     0,    990}, {0x2c, 0x76, 0xf8, 0xff}}},
    {{{  2566,  -2303,   3621}, 0, {     0, -11806}, {0x2c, 0x76, 0xf8, 0xff}}},
    {{{   768,  -1535,   5118}, 0, {   820,    450}, {0x1d, 0x78, 0xe5, 0xff}}},
    {{{  2566,  -2303,   3621}, 0, {     0, -11806}, {0x1d, 0x78, 0xe5, 0xff}}},
    {{{  2391,  -2303,   3433}, 0, {  1246, -11830}, {0x1d, 0x78, 0xe5, 0xff}}},
};

// 0x0700B190 - 0x0700B1D8
static const Gfx ccm_seg7_dl_0700B190[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_0700B118.l, 1),
    gsSPLight(&ccm_seg7_lights_0700B118.a, 2),
    gsSPVertex(ccm_seg7_vertex_0700B130, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B1D8 - 0x0700B248
const Gfx ccm_seg7_dl_0700B1D8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0700B190),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
