// 0x0700AA78 - 0x0700AB38
static const Vtx bitfs_seg7_vertex_0700AA78[] = {
    {{{   410,     51,   -306}, 0, {  2012,   1340}, {0xaf, 0xaf, 0xaf, 0xff}}},
    {{{    72,    205,    307}, 0, {     0,      0}, {0xaf, 0xaf, 0xaf, 0xff}}},
    {{{   410,     51,    307}, 0, {     0,   1340}, {0xaf, 0xaf, 0xaf, 0xff}}},
    {{{    72,    205,   -306}, 0, {  2012,      0}, {0xaf, 0xaf, 0xaf, 0xff}}},
    {{{  -409,    205,    307}, 0, {     0,   -542}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{    72,    205,    307}, 0, {     0,    990}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{    72,    205,   -306}, 0, {  2012,    990}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{  -409,    205,   -306}, 0, {  2012,   -544}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   410,      0,   -306}, 0, {  1500,  -1258}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   410,     51,    307}, 0, {  2012,    786}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   410,      0,    307}, 0, {  1500,    786}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   410,     51,   -306}, 0, {  2012,  -1258}, {0x7d, 0x7d, 0x7d, 0xff}}},
};

// 0x0700AB38 - 0x0700AB90
static const Gfx bitfs_seg7_dl_0700AB38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700AA78, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AB90 - 0x0700AC00
const Gfx bitfs_seg7_dl_0700AB90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_0700AB38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
