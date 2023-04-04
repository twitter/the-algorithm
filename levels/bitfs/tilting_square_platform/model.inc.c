// 0x0700FBA8 - 0x0700FBE8
static const Vtx bitfs_seg7_vertex_0700FBA8[] = {
    {{{   307,    307,   -306}, 0, {  2420,    990}, {0x70, 0x7f, 0x60, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0x70, 0x7f, 0x60, 0xff}}},
    {{{   307,    307,    307}, 0, {  1194,   -236}, {0x70, 0x7f, 0x60, 0xff}}},
    {{{  -306,    307,   -306}, 0, {  1194,   2214}, {0x70, 0x7f, 0x60, 0xff}}},
};

// 0x0700FBE8 - 0x0700FC88
static const Vtx bitfs_seg7_vertex_0700FBE8[] = {
    {{{   307,    307,   -306}, 0, {   478,   1498}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {   478,    480}, {0xff, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {  -540,   1498}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {  -542,    480}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {  -542,    480}, {0x96, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {   478,    480}, {0x96, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,    990}, {0x96, 0x00, 0x00, 0xff}}},
    {{{   307,    307,   -306}, 0, {   478,   1498}, {0x96, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {  -540,   1498}, {0x96, 0x00, 0x00, 0xff}}},
};

// 0x0700FC88 - 0x0700FCC0
static const Gfx bitfs_seg7_dl_0700FC88[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700FBA8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FCC0 - 0x0700FD08
static const Gfx bitfs_seg7_dl_0700FCC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700FBE8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FD08 - 0x0700FD80
const Gfx bitfs_seg7_dl_0700FD08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_0700FC88),
    gsSPDisplayList(bitfs_seg7_dl_0700FCC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
