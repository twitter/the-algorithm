// 0x0701E5D8 - 0x0701E5F0
static const Lights1 bbh_seg7_lights_0701E5D8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0701E5F0 - 0x0701E6E0
static const Vtx bbh_seg7_vertex_0701E5F0[] = {
    {{{  5530,   1843,  -1945}, 0, {-10250,  -1054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  5530,   -204,  -1945}, 0, {-10250,   5758}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  5530,   -204,   4710}, 0, { 11210,   5758}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -4197,   1843,  -1945}, 0, { -4118,  -1054}, {0x59, 0x00, 0x59, 0xff}}},
    {{{ -2149,   -204,  -3993}, 0, {  5078,   5758}, {0x59, 0x00, 0x59, 0xff}}},
    {{{ -2149,   1843,  -3993}, 0, {  5078,  -1054}, {0x59, 0x00, 0x59, 0xff}}},
    {{{ -4197,   -204,  -1945}, 0, { -4118,   5758}, {0x59, 0x00, 0x59, 0xff}}},
    {{{ -2149,   1843,  -3993}, 0, { -8206,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2149,   -204,  -3993}, 0, { -8206,   6780}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  3482,   -204,  -3993}, 0, { 10188,   6780}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  3482,   1843,  -3993}, 0, { 10188,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  3482,   1843,  -3993}, 0, { -4118,  -1054}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{  3482,   -204,  -3993}, 0, { -4118,   5758}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{  5530,   -204,  -1945}, 0, {  5078,   5758}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{  5530,   1843,  -1945}, 0, {  5078,  -1054}, {0xa7, 0x00, 0x59, 0xff}}},
};

// 0x0701E6E0 - 0x0701E7D0
static const Vtx bbh_seg7_vertex_0701E6E0[] = {
    {{{  5530,   1843,   4710}, 0, { -4118,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  5530,   -204,   4710}, 0, { -4118,   6780}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  3482,   -204,   6758}, 0, {  5070,   6780}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  5530,   1843,  -1945}, 0, {-10250,  -1054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  5530,   -204,   4710}, 0, { 11210,   5758}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  5530,   1843,   4710}, 0, { 11210,  -1054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -4197,   1843,   4710}, 0, {-10250,  -1054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -4197,   -204,   4710}, 0, {-10250,   5758}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -4197,   -204,  -1945}, 0, { 11210,   5758}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -4197,   1843,  -1945}, 0, { 11210,  -1054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{ -2149,   1843,   6758}, 0, { -4118,  -2076}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -4197,   -204,   4710}, 0, {  5078,   4736}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -4197,   1843,   4710}, 0, {  5078,  -2076}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -2149,   -204,   6758}, 0, { -4118,   4736}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  3482,   1843,   6758}, 0, {  5070,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
};

// 0x0701E7D0 - 0x0701E810
static const Vtx bbh_seg7_vertex_0701E7D0[] = {
    {{{ -2149,   1843,   6758}, 0, {  9166,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  3482,   1843,   6758}, 0, { -9228,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  3482,   -204,   6758}, 0, { -9228,   6780}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -2149,   -204,   6758}, 0, {  9166,   6780}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0701E810 - 0x0701E8D8
static const Gfx bbh_seg7_dl_0701E810[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bbh_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_0701E5D8.l, 1),
    gsSPLight(&bbh_seg7_lights_0701E5D8.a, 2),
    gsSPVertex(bbh_seg7_vertex_0701E5F0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(bbh_seg7_vertex_0701E6E0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(bbh_seg7_vertex_0701E7D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701E8D8 - 0x0701E948
const Gfx bbh_seg7_dl_0701E8D8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701E810),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
