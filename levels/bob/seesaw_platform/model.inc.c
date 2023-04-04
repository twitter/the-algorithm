// 0x0700E510 - 0x0700E528
static const Lights1 bob_seg7_lights_0700E510 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700E528 - 0x0700E628
static const Vtx bob_seg7_vertex_0700E528[] = {
    {{{  -180,     51,   -904}, 0, {   990,      0}, {0xcd, 0x33, 0x99, 0xff}}},
    {{{  -180,      0,   -904}, 0, {   950,    284}, {0xac, 0xac, 0xd6, 0xff}}},
    {{{  -180,     51,    905}, 0, { -3222,      0}, {0xac, 0x54, 0x2a, 0xff}}},
    {{{  -180,      0,   -904}, 0, { -3204,      0}, {0xac, 0xac, 0xd6, 0xff}}},
    {{{   181,      0,   -904}, 0, { -3204,   2012}, {0x33, 0xcd, 0x99, 0xff}}},
    {{{   181,      0,    905}, 0, {   990,   2012}, {0x54, 0xac, 0x2a, 0xff}}},
    {{{  -180,      0,    905}, 0, {   990,      0}, {0xcd, 0xcd, 0x67, 0xff}}},
    {{{   181,     51,   -904}, 0, {   990,   2012}, {0x54, 0x54, 0xd6, 0xff}}},
    {{{   181,     51,    905}, 0, { -3222,   2012}, {0x33, 0x33, 0x67, 0xff}}},
    {{{   181,      0,    905}, 0, { -3244,    304}, {0x54, 0xac, 0x2a, 0xff}}},
    {{{   181,      0,   -904}, 0, {   950,    284}, {0x33, 0xcd, 0x99, 0xff}}},
    {{{   181,     51,   -904}, 0, {   990,      0}, {0x54, 0x54, 0xd6, 0xff}}},
    {{{   181,     51,    905}, 0, { -3222,      0}, {0x33, 0x33, 0x67, 0xff}}},
    {{{  -180,      0,    905}, 0, {     0,    172}, {0xcd, 0xcd, 0x67, 0xff}}},
    {{{   181,     51,    905}, 0, {   690,      0}, {0x33, 0x33, 0x67, 0xff}}},
    {{{  -180,     51,    905}, 0, {     0,      0}, {0xac, 0x54, 0x2a, 0xff}}},
};

// 0x0700E628 - 0x0700E6C8
static const Vtx bob_seg7_vertex_0700E628[] = {
    {{{  -180,      0,   -904}, 0, {   950,    284}, {0xac, 0xac, 0xd6, 0xff}}},
    {{{  -180,      0,    905}, 0, { -3244,    304}, {0xcd, 0xcd, 0x67, 0xff}}},
    {{{  -180,     51,    905}, 0, { -3222,      0}, {0xac, 0x54, 0x2a, 0xff}}},
    {{{   181,      0,   -904}, 0, {   268,   2012}, {0x33, 0xcd, 0x99, 0xff}}},
    {{{  -180,      0,   -904}, 0, {   990,   2012}, {0xac, 0xac, 0xd6, 0xff}}},
    {{{  -180,     51,   -904}, 0, {   990,   1808}, {0xcd, 0x33, 0x99, 0xff}}},
    {{{   181,     51,   -904}, 0, {   268,   1808}, {0x54, 0x54, 0xd6, 0xff}}},
    {{{  -180,      0,    905}, 0, {     0,    172}, {0xcd, 0xcd, 0x67, 0xff}}},
    {{{   181,      0,    905}, 0, {   690,    172}, {0x54, 0xac, 0x2a, 0xff}}},
    {{{   181,     51,    905}, 0, {   690,      0}, {0x33, 0x33, 0x67, 0xff}}},
};

// 0x0700E6C8 - 0x0700E768
static const Gfx bob_seg7_dl_0700E6C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bob_seg7_lights_0700E510.l, 1),
    gsSPLight(&bob_seg7_lights_0700E510.a, 2),
    gsSPVertex(bob_seg7_vertex_0700E528, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  0,  2, 0x0),
    gsSP2Triangles( 8,  7,  2, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  9, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(bob_seg7_vertex_0700E628, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E768 - 0x0700E810
const Gfx bob_seg7_dl_0700E768[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(160, 160, 160, 255),
    gsSPFogPosition(980, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bob_seg7_dl_0700E6C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
