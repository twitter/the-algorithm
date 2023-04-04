// 0x0701E610 - 0x0701E628
static const Lights1 ccm_seg7_lights_0701E610 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0701E628 - 0x0701E668
static const Vtx ccm_seg7_vertex_0701E628[] = {
    {{{ -6296,  -5836,  -6501}, 0, {  3034,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -6706,  -5836,  -6297}, 0, { -1052,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -6296,  -5836,  -6297}, 0, {  3034,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -6706,  -5836,  -6501}, 0, { -1052,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0701E668 - 0x0701E6B0
static const Gfx ccm_seg7_dl_0701E668[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ccm_seg7_texture_07004B00),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_0701E610.l, 1),
    gsSPLight(&ccm_seg7_lights_0701E610.a, 2),
    gsSPVertex(ccm_seg7_vertex_0701E628, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701E6B0 - 0x0701E720
const Gfx ccm_seg7_dl_0701E6B0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0701E668),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
