// 0x07007348 - 0x07007388
static const Vtx thi_seg7_vertex_07007348[] = {
    {{{  4690,   -511,   2131}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0xb4}}},
    {{{  4690,   -511,   2377}, 0, {     0,    990}, {0x00, 0x00, 0x00, 0xb4}}},
    {{{  4936,   -511,   2377}, 0, {   990,    990}, {0x00, 0x00, 0x00, 0xb4}}},
    {{{  4936,   -511,   2131}, 0, {   990,      0}, {0x00, 0x00, 0x00, 0xb4}}},
};

// 0x07007388 - 0x070073C0
static const Gfx thi_seg7_dl_07007388[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, grass_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(thi_seg7_vertex_07007348, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x070073C0 - 0x07007430
const Gfx thi_seg7_dl_070073C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_07007388),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
