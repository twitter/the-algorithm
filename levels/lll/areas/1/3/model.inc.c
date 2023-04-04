// 0x07013830 - 0x070138B0
static const Vtx lll_seg7_vertex_07013830[] = {
    {{{ -3839,    307,   3328}, 0, {   992,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -4095,    307,   3328}, 0, {   992,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -4095,    307,   3584}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -3839,    307,   3584}, 0, {     0,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1536,    307,   3584}, 0, {   992,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1280,    307,   3840}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1536,    307,   3840}, 0, {     0,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1280,    307,   3584}, 0, {   992,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070138B0 - 0x070138F8
static const Gfx lll_seg7_dl_070138B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07013830, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x070138F8 - 0x07013968
const Gfx lll_seg7_dl_070138F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_070138B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
