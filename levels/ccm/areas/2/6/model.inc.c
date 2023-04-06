// 0x0701FDE8 - 0x0701FE28
static const Vtx ccm_seg7_vertex_0701FDE8[] = {
    {{{ -7592,  -5703,  -6819}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -7592,  -5600,  -6716}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -7592,  -5703,  -6716}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -7592,  -5600,  -6819}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0701FE28 - 0x0701FE60
static const Gfx ccm_seg7_dl_0701FE28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ccm_seg7_vertex_0701FDE8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701FE60 - 0x0701FEE0
const Gfx ccm_seg7_dl_0701FE60[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 150),
    gsDPSetCombineMode(G_CC_DECALFADE, G_CC_DECALFADE),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0701FE28),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
