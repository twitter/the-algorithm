// 0x070129A8 - 0x07012AA8
static const Vtx bits_seg7_vertex_070129A8[] = {
    {{{   307,    205,    287}, 0, {  2012,  -1054}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{   307,    205,   -286}, 0, {  3442,    376}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{  -511,    205,   -286}, 0, {  1398,   2418}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{  -511,    205,    287}, 0, {     0,    990}, {0xb1, 0xbf, 0xfa, 0xff}}},
    {{{  -511,    205,    287}, 0, {  1910,     70}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -511,    205,   -286}, 0, {   478,   1498}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -511,      0,   -286}, 0, {     0,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -511,      0,    287}, 0, {  1398,   -440}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,    205,    287}, 0, {  2524,   2522}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -511,      0,    287}, 0, {     0,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,      0,    287}, 0, {  2012,   3032}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -511,    205,    287}, 0, {   480,    480}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,      0,   -286}, 0, {  2012,   3032}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -511,    205,   -286}, 0, {   478,    480}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,    205,   -286}, 0, {  2524,   2522}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -511,      0,   -286}, 0, {     0,    990}, {0x52, 0x61, 0xac, 0xff}}},
};

// 0x07012AA8 - 0x07012B10
static const Gfx bits_seg7_dl_07012AA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_070129A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012B10 - 0x07012B80
const Gfx bits_seg7_dl_07012B10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07012AA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
