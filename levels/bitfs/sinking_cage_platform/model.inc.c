// 0x0700F590 - 0x0700F650
static const Vtx bitfs_seg7_vertex_0700F590[] = {
    {{{   307,    152,   -288}, 0, { -3092,  -2076}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -306,    154,   -288}, 0, {     0,  -2076}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -306,    154,    307}, 0, {     0,    990}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{   307,    152,    307}, 0, { -3092,    990}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{   307,    154,   -286}, 0, {  3060,   -722}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,      0,   -286}, 0, {    -8,     44}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,      0,   -286}, 0, {  3060,     44}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    154,   -286}, 0, {    -8,   -722}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,    154,    307}, 0, {  3060,   -722}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,      0,    307}, 0, {    -8,     44}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,      0,    307}, 0, {  3060,     44}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    154,    307}, 0, {    -8,   -722}, {0x8c, 0x98, 0xd8, 0xff}}},
};

// 0x0700F650 - 0x0700F6A8
static const Gfx bitfs_seg7_dl_0700F650[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_0700F590, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700F6A8 - 0x0700F718
const Gfx bitfs_seg7_dl_0700F6A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_0700F650),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
