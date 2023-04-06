// 0x07022910 - 0x07022928
static const Lights1 hmc_seg7_lights_07022910 = gdSPDefLights1(
    0x79, 0x79, 0x79,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07022928 - 0x07022A08
static const Vtx hmc_seg7_vertex_07022928[] = {
    {{{  -255,      0,   -255}, 0, {   990,     96}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{   256,    102,   -255}, 0, {     0,      0}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{   256,      0,   -255}, 0, {     0,     96}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{   256,      0,    256}, 0, {   990,      0}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{  -255,      0,    256}, 0, {   990,    990}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{  -255,      0,   -255}, 0, {     0,    990}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{   256,      0,   -255}, 0, {     0,      0}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{  -255,    102,    256}, 0, {   990,    990}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   256,    102,    256}, 0, {   990,      0}, {0x67, 0x33, 0x33, 0xff}}},
    {{{  -255,    102,   -255}, 0, {     0,    990}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{   256,      0,    256}, 0, {   990,     96}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{  -255,    102,    256}, 0, {     0,      0}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{  -255,    102,   -255}, 0, {   990,      0}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{  -255,      0,    256}, 0, {     0,     96}, {0x99, 0xcd, 0x33, 0xff}}},
};

// 0x07022A08 - 0x07022AA0
static const Gfx hmc_seg7_dl_07022A08[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cave_09002800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&hmc_seg7_lights_07022910.l, 1),
    gsSPLight(&hmc_seg7_lights_07022910.a, 2),
    gsSPVertex(hmc_seg7_vertex_07022928, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  1, 0x0),
    gsSP2Triangles( 7,  1,  9, 0x0,  2,  8, 10, 0x0),
    gsSP2Triangles( 2,  1,  8, 0x0, 10,  8, 11, 0x0),
    gsSP2Triangles( 0, 12,  1, 0x0, 13, 12,  0, 0x0),
    gsSP2Triangles(10, 11, 13, 0x0, 13, 11, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x07022AA0 - 0x07022B48
const Gfx hmc_seg7_dl_07022AA0[] = {
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
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_07022A08),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
