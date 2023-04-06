// 0x0701A608 - 0x0701A648
static const Vtx rr_seg7_vertex_0701A608[] = {
    {{{   227,      4,      5}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -79,      0,     -1}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   214,   -397,    947}, 0, {  2012,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -93,   -402,    940}, 0, {  2012,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0701A648 - 0x0701A680
static const Gfx rr_seg7_dl_0701A648[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, rr_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701A608, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701A680 - 0x0701A6F0
const Gfx rr_seg7_dl_0701A680[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701A648),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
