// 0x0701ED88 - 0x0701EE08
static const Vtx bbh_seg7_vertex_0701ED88[] = {
    {{{  1331,    563,   2161}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{   922,    154,   2161}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{  1331,    154,   2161}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{   922,    563,   2161}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{     0,    154,   2161}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{   410,    154,   2161}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{   410,    563,   2161}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0x9a}}},
    {{{     0,    563,   2161}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0x9a}}},
};

// 0x0701EE08 - 0x0701EE48
static const Vtx bbh_seg7_vertex_0701EE08[] = {
    {{{ -1740,    717,  -1648}, 0, {   990,      0}, {0xff, 0x57, 0x00, 0x9a}}},
    {{{ -1381,    717,  -1648}, 0, {     0,      0}, {0xff, 0x57, 0x00, 0x9a}}},
    {{{ -1381,      0,  -1648}, 0, {     0,   2012}, {0xff, 0x57, 0x00, 0x9a}}},
    {{{ -1740,      0,  -1648}, 0, {   990,   2012}, {0xff, 0x57, 0x00, 0x9a}}},
};

// 0x0701EE48 - 0x0701EE90
static const Gfx bbh_seg7_dl_0701EE48[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0701ED88, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701EE90 - 0x0701EEC8
static const Gfx bbh_seg7_dl_0701EE90[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900B800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0701EE08, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701EEC8 - 0x0701EF58
const Gfx bbh_seg7_dl_0701EEC8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701EE48),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701EE90),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
