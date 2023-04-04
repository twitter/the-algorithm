// 0x07009080 - 0x07009098
static const Lights1 ddd_seg7_lights_07009080 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009098 - 0x070090D8
static const Vtx ddd_seg7_vertex_07009098[] = {
    {{{  4941,  -1015,  -4197}, 0, {  4056,  -3098}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  2893,  -1015,  -4197}, 0, {     0,  -3098}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  2893,  -3063,  -4197}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  4941,  -3063,  -4197}, 0, {  4056,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x070090D8 - 0x07009120
static const Gfx ddd_seg7_dl_070090D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ddd_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ddd_seg7_lights_07009080.l, 1),
    gsSPLight(&ddd_seg7_lights_07009080.a, 2),
    gsSPVertex(ddd_seg7_vertex_07009098, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009120 - 0x07009190
const Gfx ddd_seg7_dl_07009120[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ddd_seg7_dl_070090D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
