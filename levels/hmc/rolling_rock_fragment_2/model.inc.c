// 0x07023EB8 - 0x07023ED0
static const Lights1 hmc_seg7_lights_07023EB8 = gdSPDefLights1(
    0x79, 0x79, 0x79,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07023ED0 - 0x07023FD0
static const Vtx hmc_seg7_vertex_07023ED0[] = {
    {{{   -22,     29,     25}, 0, {     0,   2012}, {0x96, 0x13, 0x42, 0xff}}},
    {{{     2,     47,    -24}, 0, {     0,   2680}, {0x1d, 0x7a, 0xf1, 0xff}}},
    {{{   -32,      0,    -24}, 0, {   258,   2344}, {0x8e, 0xf4, 0xca, 0xff}}},
    {{{    46,    -25,     14}, 0, {     0,   2012}, {0x78, 0xdd, 0x12, 0xff}}},
    {{{    18,      0,    -55}, 0, {     0,   2916}, {0x16, 0xd9, 0x8a, 0xff}}},
    {{{     2,     47,    -24}, 0, {   302,   2840}, {0x1d, 0x7a, 0xf1, 0xff}}},
    {{{     6,      5,     44}, 0, {     0,   2012}, {0x0d, 0x07, 0x7e, 0xff}}},
    {{{    46,    -25,     14}, 0, {     0,   2672}, {0x78, 0xdd, 0x12, 0xff}}},
    {{{     2,     47,    -24}, 0, {   422,   2120}, {0x1d, 0x7a, 0xf1, 0xff}}},
    {{{     2,    -34,     -8}, 0, {     0,   2012}, {0xc9, 0x8f, 0x0e, 0xff}}},
    {{{     6,      5,     44}, 0, {     0,   2760}, {0x0d, 0x07, 0x7e, 0xff}}},
    {{{   -22,     29,     25}, 0, {   204,   2740}, {0x96, 0x13, 0x42, 0xff}}},
    {{{   -32,      0,    -24}, 0, {   258,   2076}, {0x8e, 0xf4, 0xca, 0xff}}},
    {{{    18,      0,    -55}, 0, {     0,   2012}, {0x16, 0xd9, 0x8a, 0xff}}},
    {{{     2,    -34,     -8}, 0, {     0,   2692}, {0xc9, 0x8f, 0x0e, 0xff}}},
    {{{   -32,      0,    -24}, 0, {   228,   2432}, {0x8e, 0xf4, 0xca, 0xff}}},
};

// 0x07023FD0 - 0x07024080
static const Vtx hmc_seg7_vertex_07023FD0[] = {
    {{{     6,      5,     44}, 0, {     0,   2012}, {0x0d, 0x07, 0x7e, 0xff}}},
    {{{     2,    -34,     -8}, 0, {     0,   2760}, {0xc9, 0x8f, 0x0e, 0xff}}},
    {{{    46,    -25,     14}, 0, {   212,   2460}, {0x78, 0xdd, 0x12, 0xff}}},
    {{{    18,      0,    -55}, 0, {     0,   2012}, {0x16, 0xd9, 0x8a, 0xff}}},
    {{{   -32,      0,    -24}, 0, {     0,   2680}, {0x8e, 0xf4, 0xca, 0xff}}},
    {{{     2,     47,    -24}, 0, {   258,   2344}, {0x1d, 0x7a, 0xf1, 0xff}}},
    {{{     2,     47,    -24}, 0, {     0,   2012}, {0x1d, 0x7a, 0xf1, 0xff}}},
    {{{   -22,     29,     25}, 0, {     0,   2680}, {0x96, 0x13, 0x42, 0xff}}},
    {{{     6,      5,     44}, 0, {   196,   2804}, {0x0d, 0x07, 0x7e, 0xff}}},
    {{{    46,    -25,     14}, 0, {     0,   2916}, {0x78, 0xdd, 0x12, 0xff}}},
    {{{     2,    -34,     -8}, 0, {   184,   2540}, {0xc9, 0x8f, 0x0e, 0xff}}},
};

// 0x07024080 - 0x07024110
static const Gfx hmc_seg7_dl_07024080[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, hmc_seg7_texture_07004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&hmc_seg7_lights_07023EB8.l, 1),
    gsSPLight(&hmc_seg7_lights_07023EB8.a, 2),
    gsSPVertex(hmc_seg7_vertex_07023ED0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(hmc_seg7_vertex_07023FD0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  3,  9, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07024110 - 0x070241B8
const Gfx hmc_seg7_dl_07024110[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_07024080),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
