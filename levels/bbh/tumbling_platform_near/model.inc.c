// 0x0701F858 - 0x0701F870
static const Lights1 bbh_seg7_lights_0701F858 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0701F870 - 0x0701F960
static const Vtx bbh_seg7_vertex_0701F870[] = {
    {{{   102,   -101,    -50}, 0, {     0,  -1054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   102,      0,     51}, 0, {   990,  -2076}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   102,   -101,     51}, 0, {     0,  -2076}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -101,      0,    -50}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,      0,    -50}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,   -101,    -50}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,   -101,    -50}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,      0,     51}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,      0,     51}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,   -101,     51}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,   -101,     51}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,   -101,     51}, 0, {     0,  -2076}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -101,      0,     51}, 0, {   990,  -2076}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -101,      0,    -50}, 0, {   990,  -1054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -101,   -101,    -50}, 0, {     0,  -1054}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x0701F960 - 0x0701FA10
static const Vtx bbh_seg7_vertex_0701F960[] = {
    {{{   102,      0,    -50}, 0, {   990,   2010}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,      0,     51}, 0, {     0,   3032}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,      0,     51}, 0, {   990,   3032}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,   -101,    -50}, 0, {     0,  -1054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   102,      0,    -50}, 0, {   990,  -1054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   102,      0,     51}, 0, {   990,  -2076}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   102,   -101,     51}, 0, {   990,   3032}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -101,   -101,     51}, 0, {     0,   3032}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -101,   -101,    -50}, 0, {     0,   2010}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   102,   -101,    -50}, 0, {   990,   2010}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -101,      0,    -50}, 0, {     0,   2010}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0701FA10 - 0x0701FAB0
static const Gfx bbh_seg7_dl_0701FA10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_0701F858.l, 1),
    gsSPLight(&bbh_seg7_lights_0701F858.a, 2),
    gsSPVertex(bbh_seg7_vertex_0701F870, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(bbh_seg7_vertex_0701F960, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0, 10,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701FAB0 - 0x0701FB20
const Gfx bbh_seg7_dl_0701FAB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701FA10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
