// 0x0700D0A8 - 0x0700D0C0
static const Lights1 wf_seg7_lights_0700D0A8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700D0C0 - 0x0700D1B0
static const Vtx wf_seg7_vertex_0700D0C0[] = {
    {{{  -613,      0,  -2175}, 0, {-20266,   -542}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -613,   -383,      0}, 0, {-13316,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -613,      0,      0}, 0, {-13316,   -542}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,   -383,   2432}, 0, { -3096,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,    640,   2432}, 0, { -3096,  -2002}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,      0,      0}, 0, {-13316,   -542}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,   -383,      0}, 0, {-13316,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   614,    640,   2432}, 0, {     0,      0}, {0x00, 0x7a, 0xe0, 0xff}}},
    {{{   614,      0,      0}, 0, {     0, -10252}, {0x00, 0x7a, 0xe0, 0xff}}},
    {{{   154,      0,      0}, 0, { -1870, -10252}, {0x00, 0x7a, 0xe0, 0xff}}},
    {{{   154,    640,   2432}, 0, { -1870,      0}, {0x00, 0x7a, 0xe0, 0xff}}},
    {{{   154,   -383,      0}, 0, {  3034,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,      0,      0}, 0, {  3034,   -542}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -613,      0,      0}, 0, {   172,   -542}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -613,   -383,      0}, 0, {   172,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0700D1B0 - 0x0700D260
static const Vtx wf_seg7_vertex_0700D1B0[] = {
    {{{   614,      0,  -2175}, 0, { -3914,   -542}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,   -383,  -2175}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,      0,  -2175}, 0, {   990,   -542}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,      0,  -2175}, 0, {-20266,   -542}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -613,   -383,  -2175}, 0, {-20266,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -613,   -383,      0}, 0, {-13316,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -613,      0,  -2175}, 0, { -3914,  -6164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -613,      0,      0}, 0, { -3914,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   614,      0,      0}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   614,      0,  -2175}, 0, {   990,  -6164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   614,   -383,  -2175}, 0, { -3914,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700D260 - 0x0700D300
static const Gfx wf_seg7_dl_0700D260[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700D0A8.l, 1),
    gsSPLight(&wf_seg7_lights_0700D0A8.a, 2),
    gsSPVertex(wf_seg7_vertex_0700D0C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(14, 11, 13, 0x0),
    gsSPVertex(wf_seg7_vertex_0700D1B0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0, 10,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D300 - 0x0700D370
const Gfx wf_seg7_dl_0700D300[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700D260),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
