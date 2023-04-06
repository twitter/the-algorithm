// 0x0701EF58 - 0x0701F018
static const Vtx bbh_seg7_vertex_0701EF58[] = {
    {{{ -1561,    538,  -1673}, 0, {   478,    990}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1535,    614,  -1648}, 0, {     0,      0}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1535,    538,  -1648}, 0, {     0,    990}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1561,    614,  -1673}, 0, {   480,      0}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1586,    538,  -1648}, 0, {   990,    990}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1561,    538,  -1673}, 0, {   480,    990}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1586,    614,  -1648}, 0, {   990,      0}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1561,    614,  -1673}, 0, {   480,    990}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1586,    614,  -1648}, 0, {   990,    480}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1535,    614,  -1648}, 0, {     0,    480}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1586,    538,  -1648}, 0, {   990,    480}, {0xff, 0xcc, 0x90, 0xff}}},
    {{{ -1535,    538,  -1648}, 0, {     0,    480}, {0xff, 0xcc, 0x90, 0xff}}},
};

// 0x0701F018 - 0x0701F070
static const Gfx bbh_seg7_dl_0701F018[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0701EF58, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  4,  6,  3, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0, 10,  5, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701F070 - 0x0701F0E0
const Gfx bbh_seg7_dl_0701F070[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701F018),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
