// 0x07022AA0 - 0x07022AB8
static const Lights1 ssl_seg7_lights_07022AA0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07022AB8 - 0x07022BB8
static const Vtx ssl_seg7_vertex_07022AB8[] = {
    {{{   -63,      0,    307}, 0, {  2420,    990}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{   -63,    512,    307}, 0, {  2420,  -1054}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   -63,    512,   -306}, 0, {     0,  -1054}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{   -63,      0,   -306}, 0, {   990,    990}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{   -63,    512,   -306}, 0, {   990,  -1054}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{    64,    512,   -306}, 0, {   478,  -1054}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{    64,      0,   -306}, 0, {   478,    990}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{    64,      0,   -306}, 0, {  2420,    990}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{    64,    512,    307}, 0, {     0,  -1054}, {0x67, 0x33, 0x33, 0xff}}},
    {{{    64,      0,    307}, 0, {     0,    990}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{    64,    512,   -306}, 0, {  2420,  -1054}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{    64,      0,    307}, 0, {   990,    990}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{   -63,    512,    307}, 0, {   478,  -1054}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   -63,      0,    307}, 0, {   478,    990}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{    64,    512,    307}, 0, {   990,  -1054}, {0x67, 0x33, 0x33, 0xff}}},
    {{{   -63,      0,   -306}, 0, {     0,    990}, {0xd6, 0xac, 0xac, 0xff}}},
};

// 0x07022BB8 - 0x07022C38
static const Vtx ssl_seg7_vertex_07022BB8[] = {
    {{{    64,      0,    307}, 0, {   990,   1464}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{   -63,      0,    307}, 0, {   480,   1464}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{   -63,      0,   -306}, 0, {   480,   -986}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{    64,      0,   -306}, 0, {   990,   -986}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{    64,    512,   -306}, 0, {   990,   -986}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{   -63,    512,    307}, 0, {   480,   1464}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{    64,    512,    307}, 0, {   990,   1464}, {0x67, 0x33, 0x33, 0xff}}},
    {{{   -63,    512,   -306}, 0, {   480,   -986}, {0x99, 0x33, 0xcd, 0xff}}},
};

// 0x07022C38 - 0x07022CB0
static const Gfx ssl_seg7_dl_07022C38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ssl_seg7_lights_07022AA0.l, 1),
    gsSPLight(&ssl_seg7_lights_07022AA0.a, 2),
    gsSPVertex(ssl_seg7_vertex_07022AB8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07022CB0 - 0x07022CF8
static const Gfx ssl_seg7_dl_07022CB0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_0900A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_07022BB8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07022CF8 - 0x07022DA8
const Gfx ssl_seg7_dl_07022CF8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogFactor(0x0E49, 0xF2B7), // This isn't gsSPFogPosition since there is no valid min/max pair that corresponds to 0x0E49F2B7
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_07022C38),
    gsSPDisplayList(ssl_seg7_dl_07022CB0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
