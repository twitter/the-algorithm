// 0x07009190 - 0x070091D0
static const Vtx ddd_seg7_vertex_07009190[] = {
    {{{  4941,  -1015,  -4187}, 0, {  1118,   -288}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2893,  -1015,  -4187}, 0, {  -158,   -288}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2893,  -3063,  -4187}, 0, {  -158,   2264}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  4941,  -3063,  -4187}, 0, {  1118,   2264}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070091D0 - 0x07009208
static const Gfx ddd_seg7_dl_070091D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ddd_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ddd_seg7_vertex_07009190, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009208 - 0x07009288
const Gfx ddd_seg7_dl_07009208[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 128),
    gsDPSetCombineMode(G_CC_MODULATERGBFADEA, G_CC_MODULATERGBFADEA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ddd_seg7_dl_070091D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
