// 0x0701AE68 - 0x0701AF48
static const Vtx rr_seg7_vertex_0701AE68[] = {
    {{{     0,      0,      0}, 0, {   990,    990}, {0x54, 0x16, 0xce, 0xff}}},
    {{{   307,    307,   -306}, 0, {  2012,    990}, {0x54, 0x16, 0xce, 0xff}}},
    {{{   307,    307,    307}, 0, {   990,      0}, {0x54, 0x16, 0xce, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0x54, 0x16, 0xce, 0xff}}},
    {{{  -306,    307,   -306}, 0, {   990,   2010}, {0x54, 0x16, 0xce, 0xff}}},
    {{{   307,    307,   -306}, 0, {  2012,    990}, {0x55, 0x3f, 0xbf, 0xff}}},
    {{{     0,      0,      0}, 0, {   990,    990}, {0x55, 0x3f, 0xbf, 0xff}}},
    {{{  -306,    307,   -306}, 0, {   990,   2010}, {0x55, 0x3f, 0xbf, 0xff}}},
    {{{   307,    307,    307}, 0, {   990,      0}, {0x55, 0x3f, 0xbf, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0x55, 0x3f, 0xbf, 0xff}}},
    {{{   307,    307,   -306}, 0, {  2012,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    307,   -306}, 0, {   990,   2010}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,    307,    307}, 0, {   990,      0}, {0x8c, 0x98, 0xd8, 0xff}}},
};

// 0x0701AF48 - 0x0701AFA0
static const Gfx rr_seg7_dl_0701AF48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0701AE68, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701AFA0 - 0x0701B010
const Gfx rr_seg7_dl_0701AFA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0701AF48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
