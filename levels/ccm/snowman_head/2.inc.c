// 0x07013730 - 0x070137B0
static const Vtx ccm_seg7_vertex_07013730[] = {
    {{{   120,     40,    177}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    41,     40,    200}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   120,    -41,    176}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    41,    -41,    198}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -119,    -41,    176}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,    -41,    198}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,     40,    200}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -119,     40,    177}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070137B0 - 0x070137F0
static const Vtx ccm_seg7_vertex_070137B0[] = {
    {{{    51,    -68,    195}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -50,    -68,    195}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -50,   -118,    159}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,   -118,    159}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070137F0 - 0x07013838
static const Gfx ccm_seg7_dl_070137F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ccm_seg7_texture_07002900),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ccm_seg7_vertex_07013730, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013838 - 0x07013870
static const Gfx ccm_seg7_dl_07013838[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ccm_seg7_texture_07002100),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ccm_seg7_vertex_070137B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013870 - 0x070138E8
const Gfx ccm_seg7_dl_07013870[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_070137F0),
    gsSPDisplayList(ccm_seg7_dl_07013838),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
