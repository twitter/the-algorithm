// 0x0700AD38 - 0x0700AD50
static const Lights1 jrb_seg7_lights_0700AD38 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700AD50 - 0x0700ADD0
static const Vtx jrb_seg7_vertex_0700AD50[] = {
    {{{  -153,    922,    154}, 0, {  -796,  -2076}, {0xa7, 0x0e, 0x59, 0xff}}},
    {{{  -153,    922,   -153}, 0, {   736,  -2076}, {0xa7, 0x0e, 0xa7, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,    990}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   154,    922,    154}, 0, {   736,  -2076}, {0x59, 0x0e, 0x59, 0xff}}},
    {{{     0,   1382,      0}, 0, {     0,  -3610}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{  -153,    922,   -153}, 0, {  -796,  -2076}, {0xa7, 0x0e, 0xa7, 0xff}}},
    {{{   154,    922,   -153}, 0, {   736,  -2076}, {0x59, 0x0e, 0xa7, 0xff}}},
    {{{   154,    922,    154}, 0, {  -796,  -2076}, {0x59, 0x0e, 0x59, 0xff}}},
};

// 0x0700ADD0 - 0x0700AE48
static const Gfx jrb_seg7_dl_0700ADD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&jrb_seg7_lights_0700AD38.l, 1),
    gsSPLight(&jrb_seg7_lights_0700AD38.a, 2),
    gsSPVertex(jrb_seg7_vertex_0700AD50, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  6,  4,  7, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 7,  2,  6, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AE48 - 0x0700AEF0
const Gfx jrb_seg7_dl_0700AE48[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(jrb_seg7_dl_0700ADD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
