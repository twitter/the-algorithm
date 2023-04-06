// 0x07007C98 - 0x07007D18
static const Vtx bits_seg7_vertex_07007C98[] = {
    {{{   717,      0,    307}, 0, {  6270,   6268}, {0x44, 0x53, 0x41, 0xff}}},
    {{{  -716,      0,    307}, 0, {  3886,   3884}, {0x44, 0x53, 0x41, 0xff}}},
    {{{  -716,      0,   -306}, 0, {  2864,   4906}, {0x44, 0x53, 0x41, 0xff}}},
    {{{   717,      0,   -306}, 0, {  5248,   7290}, {0x44, 0x53, 0x41, 0xff}}},
    {{{   717,    205,   -306}, 0, {  1842,   3884}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{  -716,    205,    307}, 0, {   480,    480}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{   717,    205,    307}, 0, {  2864,   2862}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{  -716,    205,   -306}, 0, {  -540,   1500}, {0xbc, 0xca, 0xbf, 0xff}}},
};

// 0x07007D18 - 0x07007E18
static const Vtx bits_seg7_vertex_07007D18[] = {
    {{{   717,    205,   -306}, 0, {     0,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   717,      0,   -306}, 0, {     0,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -716,      0,   -306}, 0, {  6120,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -716,    205,   -306}, 0, {  6120,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -716,    205,    307}, 0, {     0,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   717,      0,    307}, 0, {  6120,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   717,    205,    307}, 0, {  6120,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -716,      0,    307}, 0, {     0,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -716,    205,   -306}, 0, { -1048,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -716,      0,   -306}, 0, { -1052,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -716,      0,    307}, 0, {  2012,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -716,    205,    307}, 0, {  2012,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   717,    205,    307}, 0, {     0,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   717,      0,   -306}, 0, {  3036,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   717,    205,   -306}, 0, {  3036,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   717,      0,    307}, 0, {     0,    990}, {0x88, 0x98, 0x84, 0xff}}},
};

// 0x07007E18 - 0x07007E60
static const Gfx bits_seg7_dl_07007E18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07007C98, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007E60 - 0x07007EC8
static const Gfx bits_seg7_dl_07007E60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bits_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07007D18, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007EC8 - 0x07007F58
const Gfx bits_seg7_dl_07007EC8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07007E18),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07007E60),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
