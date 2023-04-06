// 0x07002000 - 0x07002018
static const Lights1 bowser_3_seg7_lights_07002000 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07002018 - 0x07002030
static const Lights1 bowser_3_seg7_lights_07002018 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07002030 - 0x07002090
static const Vtx bowser_3_seg7_vertex_07002030[] = {
    {{{ -1945,  -1340,  -2661}, 0, {  5282,    786}, {0xc4, 0x99, 0xd7, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  3412,   1202}, {0xc4, 0x99, 0xd7, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, {  3648,   1908}, {0xc4, 0x99, 0xd7, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, {  5282,    786}, {0xd6, 0x90, 0xd8, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  3648,   -370}, {0xd6, 0x90, 0xd8, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  3412,   1202}, {0xd6, 0x90, 0xd8, 0xff}}},
};

// 0x07002090 - 0x070020D0
static const Vtx bowser_3_seg7_vertex_07002090[] = {
    {{{ -3071,    307,  -1023}, 0, { -1052,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {   466,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, {  -302,  -2962}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {  -582,  -1714}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x070020D0 - 0x070021D0
static const Vtx bowser_3_seg7_vertex_070020D0[] = {
    {{{ -1945,  -1340,  -2661}, 0, {   990,   2012}, {0x67, 0x00, 0xb7, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, {   990,      0}, {0x67, 0x00, 0xb7, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, { -1010,   2012}, {0x67, 0x00, 0xb7, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {   494,      0}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{ -3071,    307,  -1023}, 0, {   990,      0}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, {   990,   2012}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {   494,   2012}, {0xd8, 0x00, 0x78, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  -504,   2012}, {0x12, 0x00, 0x7d, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {  -506,      0}, {0x12, 0x00, 0x7d, 0xff}}},
    {{{ -2364,  -1852,   -787}, 0, {  1048,   2012}, {0x12, 0x00, 0x7d, 0xff}}},
    {{{ -2364,    307,   -787}, 0, {  1046,      0}, {0x12, 0x00, 0x7d, 0xff}}},
    {{{ -3071,  -1340,  -1023}, 0, { 14512,   1816}, {0x98, 0x00, 0xb9, 0xff}}},
    {{{ -3071,    307,  -1023}, 0, { 14512,    172}, {0x98, 0x00, 0xb9, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, { 13006,   1816}, {0x98, 0x00, 0xb9, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, { 13006,    172}, {0x98, 0x00, 0xb9, 0xff}}},
    {{{  -787,    307,  -1024}, 0, { -1010,      0}, {0x67, 0x00, 0xb7, 0xff}}},
};

// 0x070021D0 - 0x07002240
static const Gfx bowser_3_seg7_dl_070021D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07002000.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002000.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07002030, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07002018.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07002018.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07002090, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002240 - 0x070022A8
static const Gfx bowser_3_seg7_dl_07002240[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_070020D0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0,  1, 15,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x070022A8 - 0x07002338
const Gfx bowser_3_seg7_dl_070022A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070021D0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07002240),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
