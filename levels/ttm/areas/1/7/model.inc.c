// 0x0700BC70 - 0x0700BC88
static const Lights1 ttm_seg7_lights_0700BC70 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700BC88 - 0x0700BCA0
static const Lights1 ttm_seg7_lights_0700BC88 = gdSPDefLights1(
    0x5d, 0x5d, 0x5d,
    0xbb, 0xbb, 0xbb, 0x28, 0x28, 0x28
);

// 0x0700BCA0 - 0x0700BCE0
static const Vtx ttm_seg7_vertex_0700BCA0[] = {
    {{{   307,     32,    307}, 0, {     0,   2418}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   307,     32,   -409}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,     32,   -409}, 0, { -1256,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,     32,    307}, 0, { -1256,   2418}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700BCE0 - 0x0700BDA0
static const Vtx ttm_seg7_vertex_0700BCE0[] = {
    {{{   307,     32,   -409}, 0, {     0,    990}, {0x58, 0x5a, 0x00, 0xff}}},
    {{{   307,     32,    307}, 0, {     0,   2418}, {0x58, 0x5a, 0x00, 0xff}}},
    {{{   355,    -15,    355}, 0, {    64,   2514}, {0x58, 0x5a, 0x00, 0xff}}},
    {{{   355,    -15,    355}, 0, {    64,   2514}, {0x00, 0x5a, 0x58, 0xff}}},
    {{{   307,     32,    307}, 0, {     0,   2418}, {0x00, 0x5a, 0x58, 0xff}}},
    {{{  -369,    -30,    370}, 0, { -1380,   2544}, {0x00, 0x5a, 0x58, 0xff}}},
    {{{   307,     32,    307}, 0, {     0,   2418}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{  -306,     32,    307}, 0, { -1256,   2418}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{  -369,    -30,    370}, 0, { -1380,   2544}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{  -369,    -30,    370}, 0, { -1380,   2544}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{  -306,     32,    307}, 0, { -1256,   2418}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{  -306,     32,   -409}, 0, { -1256,    990}, {0xa7, 0x5a, 0x00, 0xff}}},
};

// 0x0700BDA0 - 0x0700BE20
static const Gfx ttm_seg7_dl_0700BDA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700BC70.l, 1),
    gsSPLight(&ttm_seg7_lights_0700BC70.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700BCA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPLight(&ttm_seg7_lights_0700BC88.l, 1),
    gsSPLight(&ttm_seg7_lights_0700BC88.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700BCE0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BE20 - 0x0700BE90
const Gfx ttm_seg7_dl_0700BE20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700BDA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
