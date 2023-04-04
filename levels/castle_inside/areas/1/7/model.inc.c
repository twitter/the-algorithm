// 0x0702AA80 - 0x0702AA98
static const Lights1 inside_castle_seg7_lights_0702AA80 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0702AA98 - 0x0702AAD8
static const Vtx inside_castle_seg7_vertex_0702AA98[] = {
    {{{ -1330,   1690,   1526}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -716,   1690,   1526}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -716,   1075,   1526}, 0, {   990,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{ -1330,   1075,   1526}, 0, {     0,   2012}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0702AAD8 - 0x0702AB20
static const Gfx inside_castle_seg7_dl_0702AAD8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_0702AA80.l, 1),
    gsSPLight(&inside_castle_seg7_lights_0702AA80.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_0702AA98, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0702AB20 - 0x0702AB90
const Gfx inside_castle_seg7_dl_0702AB20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0702AAD8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
