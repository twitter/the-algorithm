// 0x07011108 - 0x07011120
static const Lights1 ttc_seg7_lights_07011108 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07011120 - 0x070111A0
static const Vtx ttc_seg7_vertex_07011120[] = {
    {{{   102,      0,    102}, 0, {  1982,    960}, {0x49, 0xb7, 0x49, 0xff}}},
    {{{  -101,      0,    102}, 0, {     0,    960}, {0xb7, 0xb7, 0x49, 0xff}}},
    {{{   102,      0,   -101}, 0, {  1982,  -1054}, {0x49, 0xb7, 0xb7, 0xff}}},
    {{{  -101,      0,   -101}, 0, {     0,  -1054}, {0xb7, 0xb7, 0xb7, 0xff}}},
    {{{   102,    205,   -101}, 0, {  1982,  -1054}, {0x33, 0x67, 0xcd, 0xff}}},
    {{{  -101,    205,    102}, 0, {     0,    960}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   102,    205,    102}, 0, {  1982,    960}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -101,    205,   -101}, 0, {     0,  -1054}, {0xac, 0x2a, 0xac, 0xff}}},
};

// 0x070111A0 - 0x070112A0
static const Vtx ttc_seg7_vertex_070111A0[] = {
    {{{   102,    205,    102}, 0, {  3034,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -101,    205,    102}, 0, {     0,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{  -101,      0,    102}, 0, {     0,    990}, {0xb7, 0xb7, 0x49, 0xff}}},
    {{{   102,    205,    102}, 0, {     0,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{   102,      0,    102}, 0, {     0,    990}, {0x49, 0xb7, 0x49, 0xff}}},
    {{{   102,      0,   -101}, 0, {  3034,    990}, {0x49, 0xb7, 0xb7, 0xff}}},
    {{{   102,    205,   -101}, 0, {  3034,      0}, {0x33, 0x67, 0xcd, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  3034,      0}, {0xac, 0x2a, 0xac, 0xff}}},
    {{{   102,      0,   -101}, 0, {     0,    990}, {0x49, 0xb7, 0xb7, 0xff}}},
    {{{  -101,      0,   -101}, 0, {  3034,    990}, {0xb7, 0xb7, 0xb7, 0xff}}},
    {{{   102,    205,   -101}, 0, {     0,      0}, {0x33, 0x67, 0xcd, 0xff}}},
    {{{  -101,    205,   -101}, 0, {     0,      0}, {0xac, 0x2a, 0xac, 0xff}}},
    {{{  -101,      0,   -101}, 0, {     0,    990}, {0xb7, 0xb7, 0xb7, 0xff}}},
    {{{  -101,      0,    102}, 0, {  3034,    990}, {0xb7, 0xb7, 0x49, 0xff}}},
    {{{  -101,    205,    102}, 0, {  3034,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   102,      0,    102}, 0, {  3034,    990}, {0x49, 0xb7, 0x49, 0xff}}},
};

// 0x070112A0 - 0x070112F8
static const Gfx ttc_seg7_dl_070112A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttc_seg7_lights_07011108.l, 1),
    gsSPLight(&ttc_seg7_lights_07011108.a, 2),
    gsSPVertex(ttc_seg7_vertex_07011120, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x070112F8 - 0x07011360
static const Gfx ttc_seg7_dl_070112F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttc_seg7_vertex_070111A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011360 - 0x07011428
const Gfx ttc_seg7_dl_07011360[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(200, 255, 255, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttc_seg7_dl_070112A0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttc_seg7_dl_070112F8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
