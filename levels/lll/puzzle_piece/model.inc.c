// 0x0701A270 - 0x0701A2B0
static const Vtx lll_seg7_vertex_0701A270[] = {
    {{{  -245,      0,    246}, 0, {     0,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   246,      0,    246}, 0, {   992,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   246,      0,   -245}, 0, {   992,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -245,      0,   -245}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0701A2B0 - 0x0701A2F0
static const Vtx lll_seg7_vertex_0701A2B0[] = {
    {{{  -245,    -45,    246}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0xc8}}},
    {{{   246,    -45,    246}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0xc8}}},
    {{{   246,    -45,   -245}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0xc8}}},
    {{{  -245,    -45,   -245}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0xc8}}},
};

// 0x0701A2F0 - 0x0701A338
static const Gfx lll_seg7_dl_0701A2F0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0701A338 - 0x0701A388
static const Gfx lll_seg7_dl_0701A338[] = {
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_0701A270, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0701A388 - 0x0701A3B8
const Gfx lll_seg7_dl_0701A388[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A3B8 - 0x0701A3E8
const Gfx lll_seg7_dl_0701A3B8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A3E8 - 0x0701A418
const Gfx lll_seg7_dl_0701A3E8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A418 - 0x0701A448
const Gfx lll_seg7_dl_0701A418[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07007800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A448 - 0x0701A478
const Gfx lll_seg7_dl_0701A448[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A478 - 0x0701A4A8
const Gfx lll_seg7_dl_0701A478[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A4A8 - 0x0701A4D8
const Gfx lll_seg7_dl_0701A4A8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07009000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A4D8 - 0x0701A508
const Gfx lll_seg7_dl_0701A4D8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07009800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A508 - 0x0701A538
const Gfx lll_seg7_dl_0701A508[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700A000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A538 - 0x0701A568
const Gfx lll_seg7_dl_0701A538[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A568 - 0x0701A598
const Gfx lll_seg7_dl_0701A568[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A598 - 0x0701A5C8
const Gfx lll_seg7_dl_0701A598[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700B800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A5C8 - 0x0701A5F8
const Gfx lll_seg7_dl_0701A5C8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700C000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A5F8 - 0x0701A628
const Gfx lll_seg7_dl_0701A5F8[] = {
    gsSPDisplayList(lll_seg7_dl_0701A2F0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700C800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(lll_seg7_dl_0701A338),
    gsSPEndDisplayList(),
};

// 0x0701A628 - 0x0701A670
const Gfx lll_seg7_dl_0701A628[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPVertex(lll_seg7_vertex_0701A2B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
