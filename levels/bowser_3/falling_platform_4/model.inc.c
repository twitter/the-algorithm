// 0x070029A8 - 0x070029C0
static const Lights1 bowser_3_seg7_lights_070029A8 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x070029C0 - 0x070029D8
static const Lights1 bowser_3_seg7_lights_070029C0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070029D8 - 0x07002A38
static const Vtx bowser_3_seg7_vertex_070029D8[] = {
    {{{ -1497,  -1852,   2050}, 0, {   580,    338}, {0xf8, 0x90, 0x39, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {  1366,  -1156}, {0xf8, 0x90, 0x39, 0xff}}},
    {{{     0,  -1340,   3277}, 0, {  -644,  -1156}, {0xf8, 0x90, 0x39, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   580,    338}, {0xea, 0x99, 0x46, 0xff}}},
    {{{     0,  -1340,   3277}, 0, {  -644,  -1156}, {0xea, 0x99, 0x46, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {     0,    786}, {0xea, 0x99, 0x46, 0xff}}},
};

// 0x07002A38 - 0x07002A78
static const Vtx bowser_3_seg7_vertex_07002A38[] = {
    {{{ -1945,    307,   2662}, 0, {  -302,    582}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,   3277}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,   1262}, 0, {   990,   -350}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {    -6,    174}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07002A78 - 0x07002B78
static const Vtx bowser_3_seg7_vertex_07002A78[] = {
    {{{     0,  -2364,   1262}, 0, { -1020,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,    307,   1262}, 0, { -1020,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,  -1340,   3277}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,  -1340,   3277}, 0, {  3800,   1816}, {0xda, 0x00, 0x79, 0xff}}},
    {{{ -1945,    307,   2662}, 0, {  2260,    172}, {0xda, 0x00, 0x79, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {  2260,   1816}, {0xda, 0x00, 0x79, 0xff}}},
    {{{     0,    307,   3277}, 0, {  3800,    172}, {0xda, 0x00, 0x79, 0xff}}},
    {{{ -1945,    307,   2662}, 0, {   234,      0}, {0x9a, 0x00, 0xb5, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {   990,      0}, {0x9a, 0x00, 0xb5, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   990,   2012}, {0x9a, 0x00, 0xb5, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {   234,   2012}, {0x9a, 0x00, 0xb5, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {  -698,      0}, {0xc5, 0x00, 0x90, 0xff}}},
    {{{     0,  -2364,   1262}, 0, {   990,   2012}, {0xc5, 0x00, 0x90, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {  -698,   2012}, {0xc5, 0x00, 0x90, 0xff}}},
    {{{     0,    307,   1262}, 0, {   990,      0}, {0xc5, 0x00, 0x90, 0xff}}},
    {{{     0,    307,   3277}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x07002B78 - 0x07002BE8
static const Gfx bowser_3_seg7_dl_07002B78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_070029A8.l, 1),
    gsSPLight(&bowser_3_seg7_lights_070029A8.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070029D8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_070029C0.l, 1),
    gsSPLight(&bowser_3_seg7_lights_070029C0.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07002A38, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002BE8 - 0x07002C50
static const Gfx bowser_3_seg7_dl_07002BE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07002A78, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  1, 15,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002C50 - 0x07002CE0
const Gfx bowser_3_seg7_dl_07002C50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002B78),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002BE8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
