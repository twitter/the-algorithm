// 0x070097F0 - 0x07009808
static const Lights1 wf_seg7_lights_070097F0 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009808 - 0x07009848
static const Vtx wf_seg7_vertex_07009808[] = {
    {{{  -255,   -281,   1152}, 0, {     0,   1124}, {0x00, 0x7b, 0x1e, 0xff}}},
    {{{   256,    282,  -1151}, 0, {   990,  -3608}, {0x00, 0x7b, 0x1e, 0xff}}},
    {{{  -255,    282,  -1151}, 0, {     0,  -3608}, {0x00, 0x7b, 0x1e, 0xff}}},
    {{{   256,   -281,   1152}, 0, {   990,   1124}, {0x00, 0x7b, 0x1e, 0xff}}},
};

// 0x07009848 - 0x07009890
static const Gfx wf_seg7_dl_07009848[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_070097F0.l, 1),
    gsSPLight(&wf_seg7_lights_070097F0.a, 2),
    gsSPVertex(wf_seg7_vertex_07009808, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009890 - 0x07009900
const Gfx wf_seg7_dl_07009890[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07009848),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
