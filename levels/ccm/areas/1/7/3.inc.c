// 0x0700F6E0 - 0x0700F6F8
static const Lights1 ccm_seg7_lights_0700F6E0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700F6F8 - 0x0700F738
static const Vtx ccm_seg7_vertex_0700F6F8[] = {
    {{{   219,   -378,     76}, 0, {     0,    990}, {0x56, 0x00, 0x5c, 0xff}}},
    {{{   294,   -378,      6}, 0, {   990,    990}, {0x56, 0x00, 0x5c, 0xff}}},
    {{{   294,   -275,      6}, 0, {   990,      0}, {0x56, 0x00, 0x5c, 0xff}}},
    {{{   219,   -275,     76}, 0, {     0,      0}, {0x56, 0x00, 0x5c, 0xff}}},
};

// 0x0700F738 - 0x0700F780
static const Gfx ccm_seg7_dl_0700F738[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_0700F6E0.l, 1),
    gsSPLight(&ccm_seg7_lights_0700F6E0.a, 2),
    gsSPVertex(ccm_seg7_vertex_0700F6F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F780 - 0x0700F800
const Gfx ccm_seg7_dl_0700F780[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 90),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0700F738),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
