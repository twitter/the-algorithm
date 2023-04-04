// 0x070115D8 - 0x070116D8
static const Vtx bitfs_seg7_vertex_070115D8[] = {
    {{{   307,    205,   -306}, 0, {  4056,   5076}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -306,    205,   -306}, 0, {     0,   5076}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{  -306,    205,    307}, 0, {     0,    990}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{   307,    205,    307}, 0, {  4056,    990}, {0xad, 0xbb, 0xd1, 0xff}}},
    {{{   307,      0,   -306}, 0, {     0,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    205,   -306}, 0, {  4056,      0}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,    205,   -306}, 0, {     0,      0}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,      0,   -306}, 0, {  4056,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,    205,    307}, 0, {  4056,      0}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,      0,    307}, 0, {     0,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,      0,    307}, 0, {  4056,    990}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{  -306,    205,    307}, 0, {     0,      0}, {0x8c, 0x98, 0xd8, 0xff}}},
    {{{   307,      0,   -306}, 0, {  4056,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,    205,    307}, 0, {     0,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,      0,    307}, 0, {     0,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   307,    205,   -306}, 0, {  4056,      0}, {0x52, 0x61, 0xac, 0xff}}},
};

// 0x070116D8 - 0x07011718
static const Vtx bitfs_seg7_vertex_070116D8[] = {
    {{{  -306,      0,    307}, 0, {     0,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -306,    205,    307}, 0, {     0,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -306,    205,   -306}, 0, {  4056,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -306,      0,   -306}, 0, {  4056,    990}, {0x52, 0x61, 0xac, 0xff}}},
};

// 0x07011718 - 0x07011798
static const Gfx bitfs_seg7_dl_07011718[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitfs_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitfs_seg7_vertex_070115D8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitfs_seg7_vertex_070116D8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011798 - 0x07011808
const Gfx bitfs_seg7_dl_07011798[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitfs_seg7_dl_07011718),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
