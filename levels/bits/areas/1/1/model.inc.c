// 0x07002800 - 0x070028C0
static const Vtx bits_seg7_vertex_07002800[] = {
    {{{  5570,   3242,  -3991}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5468,   3242,  -3991}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5570,   3160,  -3930}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5468,   3160,  -3930}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6517,   3791,  -1836}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6414,   3791,  -1836}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6517,   3709,  -1774}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6414,   3709,  -1774}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5966,   3777,  -3991}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5864,   3777,  -3991}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5966,   3695,  -3930}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5864,   3695,  -3930}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070028C0 - 0x07002918
static const Gfx bits_seg7_dl_070028C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_metal_hole),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07002800, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002918 - 0x07002988
const Gfx bits_seg7_dl_07002918[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_070028C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
