// 0x070111A8 - 0x070111E8
static const Vtx bitfs_seg7_vertex_070111A8[] = {
    {{{   307,    307,   -306}, 0, {  5078,  -4120}, {0xe0, 0xf5, 0xc9, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0xe0, 0xf5, 0xc9, 0xff}}},
    {{{   307,    307,    307}, 0, {  5078,    990}, {0xe0, 0xf5, 0xc9, 0xff}}},
    {{{  -306,    307,   -306}, 0, {     0,  -4120}, {0xe0, 0xf5, 0xc9, 0xff}}},
};

// 0x070111E8 - 0x07011298
static const Vtx bitfs_seg7_vertex_070111E8[] = {
    {{{   307,    307,   -306}, 0, {     0,   1604}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {   990,   -132}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {  2012,   1604}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {   734,   -132}, {0xff, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {  1500,   1604}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,   1604}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {   990,   -132}, {0x96, 0x00, 0x00, 0xff}}},
    {{{   307,    307,   -306}, 0, {  2012,   1604}, {0x96, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {     0,   1604}, {0x96, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {  2012,   1604}, {0x96, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {     0,   1604}, {0x96, 0x00, 0x00, 0xff}}},
};

// 0x07011298 - 0x070112D0
static const Gfx bitfs_seg7_dl_07011298[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_070111A8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070112D0 - 0x07011318
static const Gfx bitfs_seg7_dl_070112D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_070111E8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011318 - 0x070113A8
const Gfx bitfs_seg7_dl_07011318[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_07011298),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_070112D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
