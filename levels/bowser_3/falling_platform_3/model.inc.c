// 0x07002670 - 0x07002688
static const Lights1 bowser_3_seg7_lights_07002670 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07002688 - 0x070026A0
static const Lights1 bowser_3_seg7_lights_07002688 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070026A0 - 0x07002700
static const Vtx bowser_3_seg7_vertex_070026A0[] = {
    {{{ -3071,  -1340,   1024}, 0, {  1604,   1908}, {0xc4, 0x99, 0x29, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   580,    338}, {0xc4, 0x99, 0x29, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {     0,    786}, {0xc4, 0x99, 0x29, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, {  1604,   1908}, {0xcc, 0x90, 0x18, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  2232,     24}, {0xcc, 0x90, 0x18, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   580,    338}, {0xcc, 0x90, 0x18, 0xff}}},
};

// 0x07002700 - 0x07002740
static const Vtx bowser_3_seg7_vertex_07002700[] = {
    {{{ -3071,    307,   1024}, 0, { -1052,   -508}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {    -6,    174}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1182,    307,    394}, 0, {   204,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1945,    307,   2662}, 0, {  -302,    582}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07002740 - 0x07002840
static const Vtx bowser_3_seg7_vertex_07002740[] = {
    {{{ -3071,  -1340,   1024}, 0, {  -996,   2012}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{ -1182,    307,    394}, 0, {   990,      0}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {   990,   2012}, {0xd8, 0x00, 0x88, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {  2260,   1816}, {0x98, 0x00, 0x47, 0xff}}},
    {{{ -1945,    307,   2662}, 0, {  2260,    172}, {0x98, 0x00, 0x47, 0xff}}},
    {{{ -3071,    307,   1024}, 0, {   754,    172}, {0x98, 0x00, 0x47, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, {   754,   1816}, {0x98, 0x00, 0x47, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  -690,   2012}, {0x7c, 0x00, 0x17, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {   990,      0}, {0x7c, 0x00, 0x17, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   990,   2012}, {0x7c, 0x00, 0x17, 0xff}}},
    {{{ -1182,    307,    394}, 0, {  -690,      0}, {0x7c, 0x00, 0x17, 0xff}}},
    {{{ -1497,    307,   2050}, 0, {   234,      0}, {0x66, 0x00, 0x4b, 0xff}}},
    {{{ -1945,  -1340,   2662}, 0, {   990,   2012}, {0x66, 0x00, 0x4b, 0xff}}},
    {{{ -1497,  -1852,   2050}, 0, {   234,   2012}, {0x66, 0x00, 0x4b, 0xff}}},
    {{{ -1945,    307,   2662}, 0, {   990,      0}, {0x66, 0x00, 0x4b, 0xff}}},
    {{{ -3071,    307,   1024}, 0, {  -996,      0}, {0xd8, 0x00, 0x88, 0xff}}},
};

// 0x07002840 - 0x070028B0
static const Gfx bowser_3_seg7_dl_07002840[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07002670.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002670.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070026A0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07002688.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002688.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07002700, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070028B0 - 0x07002918
static const Gfx bowser_3_seg7_dl_070028B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07002740, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002918 - 0x070029A8
const Gfx bowser_3_seg7_dl_07002918[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002840),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070028B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
