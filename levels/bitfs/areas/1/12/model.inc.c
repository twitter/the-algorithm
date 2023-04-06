// 0x07009000 - 0x07009040
static const Vtx bitfs_seg7_vertex_07009000[] = {
    {{{  -588,    154,   -306}, 0, {  4464,   3440}, {0x8c, 0xcc, 0xd8, 0xff}}},
    {{{ -1714,    154,   -306}, 0, {     0,   3440}, {0x8c, 0xcc, 0xd8, 0xff}}},
    {{{ -1714,    154,    307}, 0, {     0,    990}, {0x8c, 0xcc, 0xd8, 0xff}}},
    {{{  -588,    154,    307}, 0, {  4464,    990}, {0x8c, 0xcc, 0xd8, 0xff}}},
};

// 0x07009040 - 0x07009140
static const Vtx bitfs_seg7_vertex_07009040[] = {
    {{{  1664,   -281,   -204}, 0, {   308,   -372}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  1664,   -178,   -204}, 0, {     0,   -372}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  1664,   -178,    205}, 0, {     0,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  1664,   -281,    205}, 0, {   308,    990}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  1664,   -178,   -204}, 0, {   308,   3372}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   947,   -178,    205}, 0, {  1672,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1664,   -178,    205}, 0, {  1672,   3372}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   947,   -178,   -204}, 0, {   308,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   947,   -383,   -206}, 0, {  1332,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  1664,   -178,   -206}, 0, {  2012,   3372}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   947,   -178,   -206}, 0, {  2012,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  1664,   -383,   -206}, 0, {  1332,   3372}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   947,   -383,    205}, 0, {  1332,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  1664,   -178,    205}, 0, {  2012,   3372}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   947,   -178,    205}, 0, {  2012,    990}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  1664,   -383,    205}, 0, {  1332,   3372}, {0x8c, 0x8c, 0x8c, 0xff}}},
};

// 0x07009140 - 0x07009178
static const Gfx bitfs_seg7_dl_07009140[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07009000, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009178 - 0x070091E0
static const Gfx bitfs_seg7_dl_07009178[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07009040, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x070091E0 - 0x07009258
const Gfx bitfs_seg7_dl_070091E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_07009140),
    gsSPDisplayList(bitfs_seg7_dl_07009178),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
