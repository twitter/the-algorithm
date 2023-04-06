// 0x0702A8F0 - 0x0702A908
static const Lights1 inside_castle_seg7_lights_0702A8F0 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0702A908 - 0x0702A998
static const Vtx inside_castle_seg7_vertex_0702A908[] = {
    {{{  -757,    -50,    717}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1023,    -50,    983}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -757,    -50,    983}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1023,    -50,    451}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1289,    -50,    451}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1289,    -50,    717}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1023,    -50,    717}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -757,    -50,    451}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1289,    -50,    983}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0702A998 - 0x0702AA10
static const Gfx inside_castle_seg7_dl_0702A998[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_0702A8F0.l, 1),
    gsSPLight(&inside_castle_seg7_lights_0702A8F0.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_0702A908, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  3,  6, 0x0),
    gsSP2Triangles( 7,  6,  0, 0x0,  6,  8,  1, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  0,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0702AA10 - 0x0702AA80
const Gfx inside_castle_seg7_dl_0702AA10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0702A998),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
