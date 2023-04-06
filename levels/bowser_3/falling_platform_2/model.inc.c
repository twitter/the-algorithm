// 0x07002338 - 0x07002350
static const Lights1 bowser_3_seg7_lights_07002338 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07002350 - 0x07002368
static const Lights1 bowser_3_seg7_lights_07002350 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07002368 - 0x070023C8
static const Vtx bowser_3_seg7_vertex_07002368[] = {
    {{{ -2364,  -1852,   -787}, 0, {  3412,   1202}, {0xb6, 0x9a, 0x00, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, {  1604,   1908}, {0xb6, 0x9a, 0x00, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, {  3648,   1908}, {0xb6, 0x9a, 0x00, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  3412,   1202}, {0xc7, 0x90, 0x09, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {  2232,     24}, {0xc7, 0x90, 0x09, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, {  1604,   1908}, {0xc7, 0x90, 0x09, 0xff}}},
};

// 0x070023C8 - 0x07002408
static const Vtx bowser_3_seg7_vertex_070023C8[] = {
    {{{ -3071,    307,   1024}, 0, { -1052,   -508}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1182,    307,    394}, 0, {   204,   -928}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {  -582,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -3071,    307,  -1023}, 0, { -1052,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07002408 - 0x07002508
static const Vtx bowser_3_seg7_vertex_07002408[] = {
    {{{ -1182,  -2364,    394}, 0, {  -996,   2012}, {0x28, 0x00, 0x78, 0xff}}},
    {{{ -3071,    307,   1024}, 0, {   990,      0}, {0x28, 0x00, 0x78, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, {   990,   2012}, {0x28, 0x00, 0x78, 0xff}}},
    {{{ -3071,  -1340,   1024}, 0, { 16084,   1816}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -3071,    307,  -1023}, 0, { 14512,    172}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, { 14512,   1816}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -3071,    307,   1024}, 0, { 16084,    172}, {0x81, 0x00, 0x00, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, {   990,   2012}, {0x28, 0x00, 0x88, 0xff}}},
    {{{ -3071,    307,  -1023}, 0, {   990,      0}, {0x28, 0x00, 0x88, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {   246,      0}, {0x28, 0x00, 0x88, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {   246,   2012}, {0x28, 0x00, 0x88, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {  -678,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -1182,  -2364,    394}, 0, {   990,   2012}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  -678,   2012}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -1182,    307,    394}, 0, {   990,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{ -1182,    307,    394}, 0, {  -996,      0}, {0x28, 0x00, 0x78, 0xff}}},
};

// 0x07002508 - 0x07002578
static const Gfx bowser_3_seg7_dl_07002508[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07002338.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002338.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07002368, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07002350.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002350.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_070023C8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002578 - 0x070025E0
static const Gfx bowser_3_seg7_dl_07002578[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07002408, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070025E0 - 0x07002670
const Gfx bowser_3_seg7_dl_070025E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002508),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002578),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
