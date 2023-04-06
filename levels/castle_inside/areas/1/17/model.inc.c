// 0x070351E8 - 0x07035200
static const Lights1 inside_castle_seg7_lights_070351E8 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07035200 - 0x07035240
static const Vtx inside_castle_seg7_vertex_07035200[] = {
    {{{   512,     51,  -1136}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,     51,  -1136}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,    205,  -1136}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   512,    205,  -1136}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07035240 - 0x07035288
static const Gfx inside_castle_seg7_dl_07035240[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_castle_seg7_texture_07003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_070351E8.l, 1),
    gsSPLight(&inside_castle_seg7_lights_070351E8.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_07035200, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07035288 - 0x070352F8
const Gfx inside_castle_seg7_dl_07035288[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07035240),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
