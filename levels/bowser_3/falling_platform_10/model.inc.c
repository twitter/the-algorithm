// 0x07003CF8 - 0x07003D10
static const Lights1 bowser_3_seg7_lights_07003CF8 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x07003D10 - 0x07003D28
static const Lights1 bowser_3_seg7_lights_07003D10 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07003D28 - 0x07003D88
static const Vtx bowser_3_seg7_vertex_07003D28[] = {
    {{{     0,  -1340,  -3276}, 0, {  5896,  -1156}, {0xea, 0x99, 0xba, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {  5144,  -1156}, {0xea, 0x99, 0xba, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, {  5282,    786}, {0xea, 0x99, 0xba, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {  5144,  -1156}, {0xe7, 0x90, 0xcc, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {  3648,   -370}, {0xe7, 0x90, 0xcc, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, {  5282,    786}, {0xe7, 0x90, 0xcc, 0xff}}},
};

// 0x07003D88 - 0x07003DC8
static const Vtx bowser_3_seg7_vertex_07003D88[] = {
    {{{  -787,    307,  -1024}, 0, {   466,  -1872}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   990,  -2870}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,  -3276}, 0, {   990,  -3370}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, {  -302,  -2962}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07003DC8 - 0x07003EC8
static const Vtx bowser_3_seg7_vertex_07003DC8[] = {
    {{{  -787,  -2364,  -1024}, 0, { -1010,   2012}, {0x99, 0x00, 0x49, 0xff}}},
    {{{  -787,    307,  -1024}, 0, { -1010,      0}, {0x99, 0x00, 0x49, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, {   990,   2012}, {0x99, 0x00, 0x49, 0xff}}},
    {{{ -1945,  -1340,  -2661}, 0, { 13006,   1816}, {0xda, 0x00, 0x87, 0xff}}},
    {{{     0,    307,  -3276}, 0, { 11466,    172}, {0xda, 0x00, 0x87, 0xff}}},
    {{{     0,  -1340,  -3276}, 0, { 11466,   1816}, {0xda, 0x00, 0x87, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, { 13006,    172}, {0xda, 0x00, 0x87, 0xff}}},
    {{{     0,  -1340,  -3276}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,    307,  -3276}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,    307,  -2522}, 0, {   238,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {   238,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,    307,  -2522}, 0, {  -698,      0}, {0x70, 0x00, 0x3b, 0xff}}},
    {{{  -787,  -2364,  -1024}, 0, {   990,   2012}, {0x70, 0x00, 0x3b, 0xff}}},
    {{{     0,  -1852,  -2522}, 0, {  -698,   2012}, {0x70, 0x00, 0x3b, 0xff}}},
    {{{  -787,    307,  -1024}, 0, {   990,      0}, {0x70, 0x00, 0x3b, 0xff}}},
    {{{ -1945,    307,  -2661}, 0, {   990,      0}, {0x99, 0x00, 0x49, 0xff}}},
};

// 0x07003EC8 - 0x07003F38
static const Gfx bowser_3_seg7_dl_07003EC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07003CF8.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003CF8.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07003D28, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPLight(&bowser_3_seg7_lights_07003D10.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07003D10.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07003D88, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003F38 - 0x07003FA0
static const Gfx bowser_3_seg7_dl_07003F38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bowser_3_seg7_vertex_07003DC8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  1, 15,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07003FA0 - 0x07004030
const Gfx bowser_3_seg7_dl_07003FA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003EC8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_07003F38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
