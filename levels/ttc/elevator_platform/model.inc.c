// 0x07011428 - 0x07011440
static const Lights1 ttc_seg7_lights_07011428 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07011440 - 0x07011530
static const Vtx ttc_seg7_vertex_07011440[] = {
    {{{   256,   -153,   -127}, 0, {  3034,    990}, {0x54, 0xd6, 0xac, 0xff}}},
    {{{   256,      0,    128}, 0, { -2074,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{   256,   -153,    128}, 0, { -2074,    990}, {0x33, 0x99, 0x33, 0xff}}},
    {{{   256,   -153,    128}, 0, {     0,    990}, {0x33, 0x99, 0x33, 0xff}}},
    {{{  -255,   -153,    128}, 0, {     0,    990}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{  -255,   -153,   -127}, 0, {  4056,    990}, {0xcd, 0x99, 0xcd, 0xff}}},
    {{{   256,   -153,   -127}, 0, {  4056,    990}, {0x54, 0xd6, 0xac, 0xff}}},
    {{{   256,      0,    128}, 0, {  6100,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -255,   -153,    128}, 0, { -4118,    990}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{   256,   -153,    128}, 0, {  6100,    990}, {0x33, 0x99, 0x33, 0xff}}},
    {{{  -255,      0,    128}, 0, { -4118,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   256,   -153,   -127}, 0, { -5140,    990}, {0x54, 0xd6, 0xac, 0xff}}},
    {{{  -255,   -153,   -127}, 0, {  5078,    990}, {0xcd, 0x99, 0xcd, 0xff}}},
    {{{  -255,      0,   -127}, 0, {  5078,      0}, {0xac, 0x2a, 0xac, 0xff}}},
    {{{   256,      0,   -127}, 0, { -5140,      0}, {0x33, 0x67, 0xcd, 0xff}}},
};

// 0x07011530 - 0x070115A0
static const Vtx ttc_seg7_vertex_07011530[] = {
    {{{  -255,   -153,    128}, 0, {  3034,    990}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{  -255,      0,    128}, 0, {  3034,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{  -255,      0,   -127}, 0, { -2074,      0}, {0xac, 0x2a, 0xac, 0xff}}},
    {{{   256,   -153,   -127}, 0, {  3034,    990}, {0x54, 0xd6, 0xac, 0xff}}},
    {{{   256,      0,   -127}, 0, {  3034,      0}, {0x33, 0x67, 0xcd, 0xff}}},
    {{{   256,      0,    128}, 0, { -2074,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -255,   -153,   -127}, 0, { -2074,    990}, {0xcd, 0x99, 0xcd, 0xff}}},
};

// 0x070115A0 - 0x070115E0
static const Vtx ttc_seg7_vertex_070115A0[] = {
    {{{   256,      0,   -127}, 0, {   990,      0}, {0x33, 0x67, 0xcd, 0xff}}},
    {{{  -255,      0,    128}, 0, {     0,    990}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   256,      0,    128}, 0, {   990,    990}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -255,      0,   -127}, 0, {     0,      0}, {0xac, 0x2a, 0xac, 0xff}}},
};

// 0x070115E0 - 0x07011670
static const Gfx ttc_seg7_dl_070115E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttc_seg7_lights_07011428.l, 1),
    gsSPLight(&ttc_seg7_lights_07011428.a, 2),
    gsSPVertex(ttc_seg7_vertex_07011440, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(ttc_seg7_vertex_07011530, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07011670 - 0x070116A8
static const Gfx ttc_seg7_dl_07011670[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttc_seg7_vertex_070115A0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070116A8 - 0x07011758
const Gfx ttc_seg7_dl_070116A8[] = {
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
    gsSPDisplayList(ttc_seg7_dl_070115E0),
    gsSPDisplayList(ttc_seg7_dl_07011670),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
