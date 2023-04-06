// 0x07066D88 - 0x07066E48
static const Vtx inside_castle_seg7_vertex_07066D88[] = {
    {{{  3731,  -2421,   -665}, 0, { -9300,  -2076}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  3731,  -2421,   -357}, 0, { -9300,   -542}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  3894,  -2297,   -511}, 0, { -8278,  -1310}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  4179,  -2079,   -511}, 0, { -6490,  -1310}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  4016,  -2204,   -665}, 0, { -7512,  -2076}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  4016,  -2204,   -357}, 0, { -7512,   -542}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  2011,  -2405,   -357}, 0, {   318,   -544}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  2011,  -2405,   -665}, 0, {   318,  -2076}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  1867,  -2261,   -511}, 0, {  -702,  -1310}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  1722,  -2116,   -357}, 0, { -1724,   -544}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  1722,  -2116,   -665}, 0, { -1724,  -2076}, {0xff, 0xff, 0x00, 0x80}}},
    {{{  1577,  -1971,   -511}, 0, { -2746,  -1310}, {0xff, 0xff, 0x00, 0x80}}},
};

// 0x07066E48 - 0x07066E90
static const Gfx inside_castle_seg7_dl_07066E48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_07066D88, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07066E90 - 0x07066F00
const Gfx inside_castle_seg7_dl_07066E90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07066E48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
