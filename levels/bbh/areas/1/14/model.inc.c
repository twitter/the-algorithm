// 0x0700D350 - 0x0700D368
static const Lights1 bbh_seg7_lights_0700D350 = gdSPDefLights1(
    0x3f, 0x51, 0x66,
    0x9e, 0xcc, 0xff, 0x28, 0x28, 0x28
);

// 0x0700D368 - 0x0700D428
static const Vtx bbh_seg7_vertex_0700D368[] = {
    {{{ -1740,   1536,   2550}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1330,   1536,   2550}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1330,   1126,   2550}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1740,   1126,   2550}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1740,    614,   2550}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1330,    614,   2550}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1330,    205,   2550}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1740,    205,   2550}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,    614,   2038}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    614,   2038}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    205,   2038}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,    205,   2038}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700D428 - 0x0700D490
static const Gfx bbh_seg7_dl_0700D428[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_0700D350.l, 1),
    gsSPLight(&bbh_seg7_lights_0700D350.a, 2),
    gsSPVertex(bbh_seg7_vertex_0700D368, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D490 - 0x0700D500
const Gfx bbh_seg7_dl_0700D490[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0700D428),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
