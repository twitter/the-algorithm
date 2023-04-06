// 0x070516E8 - 0x070517E8
static const Vtx inside_castle_seg7_vertex_070516E8[] = {
    {{{  2081,   2970,   6091}, 0, {   478,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   2970,   6091}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   2970,   5681}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -2489,   2970,   6091}, 0, {   478,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -1977,   3379,   6091}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -2489,   3379,   6091}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -1977,   2970,   6091}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -2489,   2970,   5681}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -1977,   2970,   5681}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -1977,   3379,   5681}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{ -2489,   3379,   5681}, 0, {   478,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2081,   3379,   6091}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2081,   2970,   5681}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3021,   5989}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3277,   5989}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3277,   5784}, 0, {   480,      0}, {0xff, 0xff, 0xff, 0x91}}},
};

// 0x070517E8 - 0x070518D8
static const Vtx inside_castle_seg7_vertex_070517E8[] = {
    {{{  2081,   3379,   6091}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   3379,   6091}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   2970,   6091}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2081,   2970,   5681}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   2970,   5681}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  1569,   3379,   5681}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2081,   3379,   5681}, 0, {   478,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3021,   5784}, 0, {   480,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  3105,   3277,   5784}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  3105,   3021,   5784}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3277,   5784}, 0, {   480,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3021,   5989}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  3105,   3277,   5989}, 0, {   478,      0}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  3105,   3021,   5989}, 0, {   478,    990}, {0xff, 0xff, 0xff, 0x91}}},
    {{{  2900,   3277,   5989}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0x91}}},
};

// 0x070518D8 - 0x070519C8
static const Gfx inside_castle_seg7_dl_070518D8[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, texture_castle_light),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_070516E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  6,  3, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  5,  4,  9, 0x0),
    gsSP2Triangles( 5,  9, 10, 0x0, 10,  9,  8, 0x0),
    gsSP2Triangles(10,  8,  7, 0x0, 11,  1,  0, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(inside_castle_seg7_vertex_070517E8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  6,  5,  1, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 10,  7, 0x0),
    gsSP2Triangles( 9,  8, 12, 0x0,  9, 12, 13, 0x0),
    gsSP2Triangles(13, 14, 11, 0x0, 13, 12, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x070519C8 - 0x07051A38
const Gfx inside_castle_seg7_dl_070519C8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_070518D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
