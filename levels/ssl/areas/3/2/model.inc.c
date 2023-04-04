// 0x07021AC8 - 0x07021AE0
static const Lights1 ssl_seg7_lights_07021AC8 = gdSPDefLights1(
    0x46, 0x46, 0x46,
    0x8c, 0x8c, 0x8c, 0x28, 0x28, 0x28
);

// 0x07021AE0 - 0x07021BA0
static const Vtx ssl_seg7_vertex_07021AE0[] = {
    {{{   947,  -3224,  -1855}, 0, { -3608,   4668}, {0x71, 0x00, 0x38, 0x00}}},
    {{{   947,  -3224,  -3954}, 0, {  4772,   4668}, {0x59, 0x00, 0x59, 0x00}}},
    {{{   947,  -2200,  -3954}, 0, {  4772,    582}, {0x59, 0x00, 0x59, 0xff}}},
    {{{   947,  -2200,  -1855}, 0, { -3608,    582}, {0x38, 0x00, 0x71, 0xff}}},
    {{{  -946,  -3224,  -3954}, 0, { -3300,   3646}, {0x8f, 0x00, 0x38, 0x00}}},
    {{{  -946,  -3224,  -1855}, 0, {  5078,   3646}, {0xc8, 0x00, 0x71, 0x00}}},
    {{{  -946,  -2200,  -1855}, 0, {  5078,   -440}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{  -946,  -2200,  -3954}, 0, { -3300,   -440}, {0xc8, 0x00, 0x71, 0xff}}},
    {{{  -946,  -3224,  -1855}, 0, { -3096,   4668}, {0xc8, 0x00, 0x71, 0x00}}},
    {{{   947,  -2200,  -1855}, 0, {  4464,    582}, {0x38, 0x00, 0x71, 0xff}}},
    {{{  -946,  -2200,  -1855}, 0, { -3096,    582}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{   947,  -3224,  -1855}, 0, {  4464,   4668}, {0x71, 0x00, 0x38, 0x00}}},
};

// 0x07021BA0 - 0x07021C90
static const Vtx ssl_seg7_vertex_07021BA0[] = {
    {{{ -2559,  -3224,  -3954}, 0, { -9228,   7120}, {0x59, 0x00, 0x59, 0x00}}},
    {{{  -946,  -3224,  -3954}, 0, { -2790,   7120}, {0x8f, 0x00, 0x38, 0x00}}},
    {{{  -946,  -2200,  -3954}, 0, { -2790,   3032}, {0xc8, 0x00, 0x71, 0xff}}},
    {{{  2560,  -2200,  -3954}, 0, { -6162,    990}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{  2560,  -3224,  -3954}, 0, { -6162,   5076}, {0xc8, 0x00, 0x71, 0x00}}},
    {{{  2560,  -3224,   -370}, 0, {  8144,   5076}, {0x8f, 0x00, 0xc8, 0x00}}},
    {{{  2560,  -2200,   -370}, 0, {  8144,    990}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{ -2559,  -2200,   -370}, 0, { -7184,   2010}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{ -2559,  -3224,   -370}, 0, { -7184,   6098}, {0x38, 0x00, 0x8f, 0x00}}},
    {{{ -2559,  -3224,  -3954}, 0, {  7122,   6098}, {0x59, 0x00, 0x59, 0x00}}},
    {{{ -2559,  -2200,  -3954}, 0, {  7122,   2010}, {0x59, 0x00, 0x59, 0xff}}},
    {{{   947,  -2200,  -3954}, 0, {  4772,   3032}, {0x59, 0x00, 0x59, 0xff}}},
    {{{  2560,  -3224,  -3954}, 0, { 11210,   7120}, {0xc8, 0x00, 0x71, 0x00}}},
    {{{  2560,  -2200,  -3954}, 0, { 11210,   3032}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{   947,  -3224,  -3954}, 0, {  4772,   7120}, {0x59, 0x00, 0x59, 0x00}}},
};

// 0x07021C90 - 0x07021D00
static const Vtx ssl_seg7_vertex_07021C90[] = {
    {{{  2560,  -2200,   -370}, 0, { -9228,    990}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{  2560,  -3224,   -370}, 0, { -9228,   5076}, {0x8f, 0x00, 0xc8, 0x00}}},
    {{{ -2559,  -3224,   -370}, 0, { 11210,   5076}, {0x38, 0x00, 0x8f, 0x00}}},
    {{{ -2559,  -3224,  -3954}, 0, { -9228,   7120}, {0x59, 0x00, 0x59, 0x00}}},
    {{{  -946,  -2200,  -3954}, 0, { -2790,   3032}, {0xc8, 0x00, 0x71, 0xff}}},
    {{{ -2559,  -2200,  -3954}, 0, { -9228,   3032}, {0x59, 0x00, 0x59, 0xff}}},
    {{{ -2559,  -2200,   -370}, 0, { 11210,    990}, {0x71, 0x00, 0xc8, 0xff}}},
};

// 0x07021D00 - 0x07021D68
static const Gfx ssl_seg7_dl_07021D00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ssl_seg7_lights_07021AC8.l, 1),
    gsSPLight(&ssl_seg7_lights_07021AC8.a, 2),
    gsSPVertex(ssl_seg7_vertex_07021AE0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07021D68 - 0x07021DE8
static const Gfx ssl_seg7_dl_07021D68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_07021BA0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(ssl_seg7_vertex_07021C90, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07021DE8 - 0x07021E50
const Gfx ssl_seg7_dl_07021DE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_07021D00),
    gsSPDisplayList(ssl_seg7_dl_07021D68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
