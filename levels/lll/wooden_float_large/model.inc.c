// 0x07018D08 - 0x07018D88
static const Vtx lll_seg7_vertex_07018D08[] = {
    {{{   768,      0,    384}, 0, { 10188,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   768,    154,    384}, 0, { 10188,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -767,    154,    384}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -767,      0,    384}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -767,      0,   -383}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   768,    154,   -383}, 0, { -9228,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   768,      0,   -383}, 0, { -9228,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -767,    154,   -383}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07018D88 - 0x07018E48
static const Vtx lll_seg7_vertex_07018D88[] = {
    {{{   768,    154,   -383}, 0, {     0,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -767,    154,   -383}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -767,    154,    384}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,    154,    384}, 0, {   990,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,      0,   -383}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,    154,   -383}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,    154,    384}, 0, { -4118,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,      0,    384}, 0, { -4118,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -767,      0,    384}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,    154,   -383}, 0, { -4118,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,      0,   -383}, 0, { -4118,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,    154,    384}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07018E48 - 0x07018EA0
static const Gfx lll_seg7_dl_07018E48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_07018D08, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018EA0 - 0x07018EF8
static const Gfx lll_seg7_dl_07018EA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07018D88, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07018EF8 - 0x07018F70
const Gfx lll_seg7_dl_07018EF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07018E48),
    gsSPDisplayList(lll_seg7_dl_07018EA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
