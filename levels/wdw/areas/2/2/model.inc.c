// 0x07011F58 - 0x07011F70
static const Lights1 wdw_seg7_lights_07011F58 = gdSPDefLights1(
    0x54, 0x90, 0x88,
    0x8d, 0xf0, 0xe3, 0x28, 0x28, 0x28
);

// 0x07011F70 - 0x07011F88
static const Lights1 wdw_seg7_lights_07011F70 = gdSPDefLights1(
    0x56, 0x57, 0x8e,
    0x90, 0x92, 0xee, 0x28, 0x28, 0x28
);

// 0x07011F88 - 0x07011FA0
static const Lights1 wdw_seg7_lights_07011F88 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07011FA0 - 0x07012020
static const Vtx wdw_seg7_vertex_07011FA0[] = {
    {{{ -3800,   -827,  -1586}, 0, {  -584,    320}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3800,  -1219,  -1586}, 0, {  -584,   1100}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3523,  -1496,  -1586}, 0, {     0,   1654}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3131,  -1496,  -1586}, 0, {   750,   1654}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2854,  -1219,  -1586}, 0, {  1304,   1100}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2854,   -827,  -1586}, 0, {  1304,    320}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2928,   -753,  -1586}, 0, {  1156,    172}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3726,   -753,  -1586}, 0, {  -436,    172}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07012020 - 0x070120A0
static const Vtx wdw_seg7_vertex_07012020[] = {
    {{{ -3800,   -827,  -2738}, 0, {  1304,   1100}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3800,  -1219,  -2738}, 0, {  1304,    320}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3726,  -1293,  -2738}, 0, {  1156,    172}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2928,  -1293,  -2738}, 0, {  -436,    172}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2854,  -1219,  -2738}, 0, {  -584,    320}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -2854,   -827,  -2738}, 0, {  -584,   1100}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3131,   -550,  -2738}, 0, {     0,   1654}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{ -3523,   -550,  -2738}, 0, {   750,   1654}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x070120A0 - 0x07012160
static const Vtx wdw_seg7_vertex_070120A0[] = {
    {{{  1192,  -2559,   3460}, 0, {  1238,    990}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  1662,   -511,   2990}, 0, {     0,  -3098}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  1662,  -2559,   2990}, 0, {     0,    990}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  1662,  -2559,   2990}, 0, {     0,    990}, {0xe0, 0x00, 0x86, 0xff}}},
    {{{  1662,   -511,   2990}, 0, {     0,  -3098}, {0xe0, 0x00, 0x86, 0xff}}},
    {{{  2304,   -511,   2818}, 0, { -1130,  -3098}, {0xe0, 0x00, 0x86, 0xff}}},
    {{{  2304,  -2559,   2818}, 0, { -1130,    990}, {0xe0, 0x00, 0x86, 0xff}}},
    {{{  1192,   -511,   3460}, 0, {  1238,  -3098}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  1024,  -2559,   3840}, 0, {  2338,    990}, {0x8c, 0x00, 0xcd, 0xff}}},
    {{{  1192,   -511,   3460}, 0, {  1238,  -3098}, {0x8c, 0x00, 0xcd, 0xff}}},
    {{{  1192,  -2559,   3460}, 0, {  1238,    990}, {0x8c, 0x00, 0xcd, 0xff}}},
    {{{  1024,   -511,   3840}, 0, {  2338,  -3098}, {0x8c, 0x00, 0xcd, 0xff}}},
};

// 0x07012160 - 0x07012258
static const Gfx wdw_seg7_dl_07012160[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07011F58.l, 1),
    gsSPLight(&wdw_seg7_lights_07011F58.a, 2),
    gsSPVertex(wdw_seg7_vertex_07011FA0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 0,  6,  7, 0x0,  0,  4,  5, 0x0),
    gsSPLight(&wdw_seg7_lights_07011F70.l, 1),
    gsSPLight(&wdw_seg7_lights_07011F70.a, 2),
    gsSPVertex(wdw_seg7_vertex_07012020, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  5,  6, 0x0,  0,  6,  7, 0x0),
    gsSPLight(&wdw_seg7_lights_07011F88.l, 1),
    gsSPLight(&wdw_seg7_lights_07011F88.a, 2),
    gsSPVertex(wdw_seg7_vertex_070120A0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012258 - 0x070122C8
const Gfx wdw_seg7_dl_07012258[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_07012160),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
