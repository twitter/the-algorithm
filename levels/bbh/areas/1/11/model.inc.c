// 0x0700BA50 - 0x0700BB50
static const Vtx bbh_seg7_vertex_0700BA50[] = {
    {{{  2739,    819,   2166}, 0, {     0,    172}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,   1203,   2550}, 0, {     0,    786}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,   1459,   2550}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,      0,   2268}, 0, {     0,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,    282,   2550}, 0, {     0,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,    538,   2550}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,      0,   2012}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,      0,   2268}, 0, {   990,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,    282,   2550}, 0, {   990,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,    538,   2550}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,      0,   2012}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2739,    819,   1910}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,    819,   2166}, 0, {   990,    172}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,    819,   1910}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,   1459,   2550}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  2995,   1203,   2550}, 0, {   990,    786}, {0xff, 0xec, 0x40, 0x50}}},
};

// 0x0700BB50 - 0x0700BBF8
static const Gfx bbh_seg7_dl_0700BB50[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0700BA50, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 7,  8,  4, 0x0,  6,  5,  9, 0x0),
    gsSP2Triangles( 6,  9, 10, 0x0, 10,  8,  7, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  0,  2, 11, 0x0),
    gsSP2Triangles(12,  1,  0, 0x0, 13, 14, 15, 0x0),
    gsSP2Triangles(12, 15,  1, 0x0, 11,  2, 14, 0x0),
    gsSP2Triangles(11, 14, 13, 0x0, 13, 15, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BBF8 - 0x0700BC68
const Gfx bbh_seg7_dl_0700BBF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0700BB50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
