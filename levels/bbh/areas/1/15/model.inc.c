// 0x0700D500 - 0x0700D600
static const Vtx bbh_seg7_vertex_0700D500[] = {
    {{{  -537,      0,   1756}, 0, {     0,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,    282,   2038}, 0, {     0,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,    538,   2038}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,   1357,   2447}, 0, {   734,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,   1459,   2550}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,   1357,   2447}, 0, {   734,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,   1459,   2550}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,   1101,   2447}, 0, {    96,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,   1203,   2550}, 0, {   352,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,   1203,   2550}, 0, {   352,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,   1101,   2447}, 0, {    96,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -281,      0,   1756}, 0, {   990,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,      0,   1500}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,      0,   2268}, 0, {     0,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,    282,   2550}, 0, {     0,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,    538,   2550}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
};

// 0x0700D600 - 0x0700D6F0
static const Vtx bbh_seg7_vertex_0700D600[] = {
    {{{  -281,      0,   1756}, 0, {   990,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -281,    282,   2038}, 0, {   990,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,    282,   2038}, 0, {     0,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,      0,   1500}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -537,    538,   2038}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -281,    538,   2038}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{  -281,      0,   1500}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,      0,   2012}, 0, {     0,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,    538,   2550}, 0, {   990,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,      0,   2012}, 0, {   990,      0}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,    538,   2550}, 0, {     0,    990}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,      0,   2268}, 0, {     0,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,    282,   2550}, 0, {   990,    746}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1407,      0,   2268}, 0, {   990,    212}, {0xff, 0xec, 0x40, 0x50}}},
    {{{ -1663,    282,   2550}, 0, {     0,    746}, {0xff, 0xec, 0x40, 0x50}}},
};

// 0x0700D6F0 - 0x0700D7E0
static const Gfx bbh_seg7_dl_0700D6F0[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0700D500, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  6,  3, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  5,  4,  9, 0x0),
    gsSP2Triangles( 5,  9, 10, 0x0, 10,  9,  8, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bbh_seg7_vertex_0700D600, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  6,  5,  1, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 10,  7, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0,  9, 12, 13, 0x0),
    gsSP2Triangles(13, 12, 14, 0x0, 13, 14, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D7E0 - 0x0700D850
const Gfx bbh_seg7_dl_0700D7E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0700D6F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
