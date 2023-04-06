// 0x07013610 - 0x07013710
static const Vtx bits_seg7_vertex_07013610[] = {
    {{{  -204,    102,   -306}, 0, {    38,   2968}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{  -204,    102,    307}, 0, {    38,   1052}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{   205,    102,    307}, 0, {  1448,   1052}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{   205,    102,   -306}, 0, {  1448,   2968}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{   205,      0,   -306}, 0, {  1448,   2968}, {0x3d, 0x3c, 0x76, 0xff}}},
    {{{   205,      0,    307}, 0, {  1448,   1052}, {0x3d, 0x3c, 0x76, 0xff}}},
    {{{  -204,      0,    307}, 0, {    38,   1052}, {0x3d, 0x3c, 0x76, 0xff}}},
    {{{  -204,      0,   -306}, 0, {    38,   2968}, {0x3d, 0x3c, 0x76, 0xff}}},
    {{{  -204,      0,   -306}, 0, {    38,   2968}, {0x79, 0x8c, 0xeb, 0xff}}},
    {{{  -204,    102,    307}, 0, {    38,   1052}, {0x79, 0x8c, 0xeb, 0xff}}},
    {{{  -204,    102,   -306}, 0, {    38,   2968}, {0x79, 0x8c, 0xeb, 0xff}}},
    {{{  -204,      0,    307}, 0, {    38,   1052}, {0x79, 0x8c, 0xeb, 0xff}}},
    {{{   205,    102,   -306}, 0, {  1448,   2968}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,    102,    307}, 0, {  1448,   1052}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,      0,    307}, 0, {  1448,   1052}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,      0,   -306}, 0, {  1448,   2968}, {0x65, 0x7f, 0xff, 0xff}}},
};

// 0x07013710 - 0x07013790
static const Vtx bits_seg7_vertex_07013710[] = {
    {{{  -204,      0,    307}, 0, {    38,   1052}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,      0,    307}, 0, {  1448,   1052}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,    102,    307}, 0, {  1448,   1052}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,    102,    307}, 0, {    38,   1052}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,      0,   -306}, 0, {  1448,   2968}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,    102,   -306}, 0, {    38,   2968}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,    102,   -306}, 0, {  1448,   2968}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,      0,   -306}, 0, {    38,   2968}, {0x52, 0x61, 0xac, 0xff}}},
};

// 0x07013790 - 0x07013820
static const Gfx bits_seg7_dl_07013790[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07013610, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bits_seg7_vertex_07013710, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013820 - 0x07013890
const Gfx bits_seg7_dl_07013820[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07013790),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
