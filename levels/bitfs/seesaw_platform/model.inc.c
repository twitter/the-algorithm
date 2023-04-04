// 0x07011C18 - 0x07011D18
static const Vtx bitfs_seg7_vertex_07011C18[] = {
    {{{  -511,    179,    307}, 0, {     0,    990}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   512,    179,    307}, 0, {  1672,   -714}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   512,    179,   -306}, 0, {  2694,    308}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{  -511,    179,   -306}, 0, {   990,   2010}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{   512,     26,    307}, 0, {  1672,   -714}, {0x44, 0x44, 0x44, 0xff}}},
    {{{  -511,     26,    307}, 0, {     0,    990}, {0x44, 0x44, 0x44, 0xff}}},
    {{{  -511,     26,   -306}, 0, {   990,   2010}, {0x44, 0x44, 0x44, 0xff}}},
    {{{   512,     26,   -306}, 0, {  2694,    308}, {0x44, 0x44, 0x44, 0xff}}},
    {{{   512,    179,    307}, 0, {   478,    478}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   512,     26,   -306}, 0, {  1246,   1754}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   512,    179,   -306}, 0, {  1500,   1498}, {0x64, 0x64, 0x64, 0xff}}},
    {{{   512,     26,    307}, 0, {   224,    734}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -511,    179,    307}, 0, {   478,    478}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -511,    179,   -306}, 0, {  1500,   1498}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -511,     26,   -306}, 0, {  1246,   1754}, {0x64, 0x64, 0x64, 0xff}}},
    {{{  -511,     26,    307}, 0, {   224,    734}, {0x64, 0x64, 0x64, 0xff}}},
};

// 0x07011D18 - 0x07011D98
static const Vtx bitfs_seg7_vertex_07011D18[] = {
    {{{  -511,     26,    307}, 0, { -1988,  -1480}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,    179,    307}, 0, {     0,      0}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,    179,    307}, 0, { -1734,  -1736}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,     26,    307}, 0, {  -286,    224}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,    179,   -306}, 0, { -1734,  -1736}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,     26,   -306}, 0, {  -286,    224}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{  -511,     26,   -306}, 0, { -1988,  -1480}, {0x8c, 0x8c, 0x8c, 0xff}}},
    {{{   512,    179,   -306}, 0, {     0,      0}, {0x8c, 0x8c, 0x8c, 0xff}}},
};

// 0x07011D98 - 0x07011E28
const Gfx bitfs_seg7_dl_07011D98[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_07011C18, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bitfs_seg7_vertex_07011D18, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011E28 - 0x07011E98
const Gfx bitfs_seg7_dl_07011E28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_07011D98),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
