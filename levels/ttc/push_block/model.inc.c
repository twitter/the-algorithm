// 0x070100B0 - 0x070100C8
static const Lights1 ttc_seg7_lights_070100B0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070100C8 - 0x070101C8
static const Vtx ttc_seg7_vertex_070100C8[] = {
    {{{   102,    102,   -450}, 0, {   224,    990}, {0x38, 0x71, 0x00, 0xff}}},
    {{{  -101,    102,     10}, 0, {  2268,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   102,    102,     10}, 0, {   224,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{   102,    102,     10}, 0, {  2268,      0}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{   102,   -101,   -450}, 0, {   224,    990}, {0x71, 0xc8, 0x00, 0xff}}},
    {{{   102,    102,   -450}, 0, {  2268,    990}, {0x38, 0x71, 0x00, 0xff}}},
    {{{   102,   -101,     10}, 0, {   224,      0}, {0x33, 0x99, 0x33, 0xff}}},
    {{{  -101,    102,   -450}, 0, { -1308,    990}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{  -101,   -101,   -450}, 0, {   734,    990}, {0xc8, 0x8f, 0x00, 0xff}}},
    {{{  -101,   -101,     10}, 0, {   734,      0}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{  -101,    102,     10}, 0, { -1308,      0}, {0xcd, 0x67, 0x33, 0xff}}},
    {{{   102,   -101,     10}, 0, {  2268,      0}, {0x33, 0x99, 0x33, 0xff}}},
    {{{  -101,   -101,     10}, 0, {   224,      0}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{  -101,   -101,   -450}, 0, {   224,    990}, {0xc8, 0x8f, 0x00, 0xff}}},
    {{{   102,   -101,   -450}, 0, {  2268,    990}, {0x71, 0xc8, 0x00, 0xff}}},
    {{{  -101,    102,   -450}, 0, {  2268,    990}, {0x8f, 0x38, 0x00, 0xff}}},
};

// 0x070101C8 - 0x07010208
static const Vtx ttc_seg7_vertex_070101C8[] = {
    {{{   102,    102,     10}, 0, {   990,     16}, {0x54, 0x2a, 0x54, 0xff}}},
    {{{  -101,   -101,     10}, 0, {     0,    990}, {0xac, 0xd6, 0x54, 0xff}}},
    {{{   102,   -101,     10}, 0, {   990,    990}, {0x33, 0x99, 0x33, 0xff}}},
    {{{  -101,    102,     10}, 0, {     0,     16}, {0xcd, 0x67, 0x33, 0xff}}},
};

// 0x07010208 - 0x07010280
static const Gfx ttc_seg7_dl_07010208[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttc_seg7_lights_070100B0.l, 1),
    gsSPLight(&ttc_seg7_lights_070100B0.a, 2),
    gsSPVertex(ttc_seg7_vertex_070100C8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07010280 - 0x070102B8
static const Gfx ttc_seg7_dl_07010280[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttc_seg7_vertex_070101C8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070102B8 - 0x07010380
const Gfx ttc_seg7_dl_070102B8[] = {
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
    gsSPDisplayList(ttc_seg7_dl_07010208),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttc_seg7_dl_07010280),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
