// 0x07013CE8 - 0x07013DE8
static const Vtx bits_seg7_vertex_07013CE8[] = {
    {{{  -460,    102,   -306}, 0, {   990,    -10}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   461,      0,   -306}, 0, {   990,    968}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -460,      0,   -306}, 0, {   990,    -10}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   461,    102,   -306}, 0, {   990,    968}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -460,      0,    307}, 0, {     0,    -10}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   461,      0,    307}, 0, {     0,    968}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{   461,    102,    307}, 0, {     0,    968}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -460,    102,    307}, 0, {     0,    -10}, {0x7d, 0x7d, 0x7d, 0xff}}},
    {{{  -460,      0,   -306}, 0, {   990,    -10}, {0x55, 0x55, 0x55, 0xff}}},
    {{{   461,      0,    307}, 0, {     0,    968}, {0x55, 0x55, 0x55, 0xff}}},
    {{{  -460,      0,    307}, 0, {     0,    -10}, {0x55, 0x55, 0x55, 0xff}}},
    {{{   461,      0,   -306}, 0, {   990,    968}, {0x55, 0x55, 0x55, 0xff}}},
    {{{   461,    102,   -306}, 0, {   990,    968}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -460,    102,   -306}, 0, {   990,    -10}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -460,    102,    307}, 0, {     0,    -10}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   461,    102,    307}, 0, {     0,    968}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07013DE8 - 0x07013E68
static const Vtx bits_seg7_vertex_07013DE8[] = {
    {{{  -460,      0,    307}, 0, {     0,    -10}, {0x96, 0x96, 0x96, 0xff}}},
    {{{  -460,    102,   -306}, 0, {   990,    -10}, {0x96, 0x96, 0x96, 0xff}}},
    {{{  -460,      0,   -306}, 0, {   990,    -10}, {0x96, 0x96, 0x96, 0xff}}},
    {{{  -460,    102,    307}, 0, {     0,    -10}, {0x96, 0x96, 0x96, 0xff}}},
    {{{   461,    102,   -306}, 0, {   990,    968}, {0x96, 0x96, 0x96, 0xff}}},
    {{{   461,      0,    307}, 0, {     0,    968}, {0x96, 0x96, 0x96, 0xff}}},
    {{{   461,      0,   -306}, 0, {   990,    968}, {0x96, 0x96, 0x96, 0xff}}},
    {{{   461,    102,    307}, 0, {     0,    968}, {0x96, 0x96, 0x96, 0xff}}},
};

// 0x07013E68 - 0x07013EF8
static const Gfx bits_seg7_dl_07013E68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bits_seg7_texture_07002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07013CE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bits_seg7_vertex_07013DE8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013EF8 - 0x07013F68
const Gfx bits_seg7_dl_07013EF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07013E68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
