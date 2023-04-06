// 0x0700AF78 - 0x0700AF90
static const Lights1 ddd_seg7_lights_0700AF78 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700AF90 - 0x0700B010
static const Vtx ddd_seg7_vertex_0700AF90[] = {
    {{{  3804,    776,     61}, 0, {   990,   2012}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  3804,   1134,   -296}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  3804,    776,   -296}, 0, {     0,   2012}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  3804,   1134,     61}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  4029,    776,   -296}, 0, {     0,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  4029,   1134,     61}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  4029,    776,     61}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  4029,   1134,   -296}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0700B010 - 0x0700B068
static const Gfx ddd_seg7_dl_0700B010[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ddd_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ddd_seg7_lights_0700AF78.l, 1),
    gsSPLight(&ddd_seg7_lights_0700AF78.a, 2),
    gsSPVertex(ddd_seg7_vertex_0700AF90, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B068 - 0x0700B0D8
const Gfx ddd_seg7_dl_0700B068[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ddd_seg7_dl_0700B010),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
