// 0x0700B948 - 0x0700BA48
static const Vtx bitdw_seg7_vertex_0700B948[] = {
    {{{   205,      0,   -306}, 0, {  2012,  -2076}, {0x3d, 0x41, 0x74, 0xff}}},
    {{{   205,      0,    307}, 0, {  2012,    990}, {0x3d, 0x41, 0x74, 0xff}}},
    {{{  -204,      0,   -306}, 0, {     0,  -2076}, {0x3d, 0x41, 0x74, 0xff}}},
    {{{  -204,      0,    307}, 0, {     0,    990}, {0x3d, 0x41, 0x74, 0xff}}},
    {{{  -204,    102,   -306}, 0, {     0,  -2076}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{  -204,    102,    307}, 0, {     0,    990}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{   205,    102,   -306}, 0, {  2012,  -2076}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{   205,    102,    307}, 0, {  2012,    990}, {0xc7, 0xee, 0xff, 0xff}}},
    {{{  -204,    102,   -306}, 0, {  2012,    990}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,    102,   -306}, 0, {     0,    990}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{  -204,      0,   -306}, 0, {  2012,      0}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,      0,   -306}, 0, {     0,      0}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,    102,    307}, 0, {     0,    990}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{  -204,    102,    307}, 0, { -2074,    990}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{   205,      0,    307}, 0, {     0,      0}, {0x65, 0x7f, 0xff, 0xff}}},
    {{{  -204,      0,    307}, 0, { -2074,      0}, {0x65, 0x7f, 0xff, 0xff}}},
};

// 0x0700BA48 - 0x0700BAC8
static const Vtx bitdw_seg7_vertex_0700BA48[] = {
    {{{  -204,      0,   -306}, 0, { -2074,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,      0,    307}, 0, {   990,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,    102,   -306}, 0, { -2074,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{  -204,    102,    307}, 0, {   990,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,    102,   -306}, 0, {  3034,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,    102,    307}, 0, {     0,    990}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,      0,   -306}, 0, {  3034,      0}, {0x52, 0x61, 0xac, 0xff}}},
    {{{   205,      0,    307}, 0, {     0,      0}, {0x52, 0x61, 0xac, 0xff}}},
};

// 0x0700BAC8 - 0x0700BB58
static const Gfx bitdw_seg7_dl_0700BAC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bitdw_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700B948, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 13, 15, 14, 0x0),
    gsSPVertex(bitdw_seg7_vertex_0700BA48, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BB58 - 0x0700BBC8
const Gfx bitdw_seg7_dl_0700BB58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_0700BAC8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
