// 0x0700FC80 - 0x0700FC98
static const Lights1 ttc_seg7_lights_0700FC80 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700FC98 - 0x0700FCB0
static const Lights1 ttc_seg7_lights_0700FC98 = gdSPDefLights1(
    0x46, 0x46, 0x46,
    0x8c, 0x8c, 0x8c, 0x28, 0x28, 0x28
);

// 0x0700FCB0 - 0x0700FD30
static const Vtx ttc_seg7_vertex_0700FCB0[] = {
    {{{  -229,      0,   -306}, 0, {  4510,    -34}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,   -137,   -306}, 0, {  4510,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,   -137,    308}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,      0,    308}, 0, {     0,    -34}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   230,      0,    308}, 0, {     0,    -34}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,   -137,   -306}, 0, {  4510,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,      0,   -306}, 0, {  4510,    -34}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,   -137,    308}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0700FD30 - 0x0700FE30
static const Vtx ttc_seg7_vertex_0700FD30[] = {
    {{{  -229,   -137,    308}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,    -35,    344}, 0, {   334,    234}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,      0,    308}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   230,      0,    308}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,    -35,    344}, 0, {   334,    234}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,   -137,    344}, 0, {   334,    990}, {0x54, 0xd6, 0x54, 0xff}}},
    {{{   230,   -137,    308}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,   -137,   -306}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,   -137,   -342}, 0, {   334,    990}, {0x33, 0x99, 0xcd, 0xff}}},
    {{{   230,    -35,   -342}, 0, {   334,    234}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   230,      0,   -306}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -229,    -86,    344}, 0, {   334,    612}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -229,   -137,    344}, 0, {   334,    990}, {0xcd, 0x99, 0x33, 0xff}}},
    {{{   230,    -86,    344}, 0, {   334,    612}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -229,   -137,   -342}, 0, {   334,    990}, {0xac, 0xd6, 0xac, 0xff}}},
    {{{   230,    -86,   -342}, 0, {   334,    612}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700FE30 - 0x0700FE90
static const Vtx ttc_seg7_vertex_0700FE30[] = {
    {{{  -229,      0,   -306}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,    -35,   -342}, 0, {   334,    234}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,   -137,   -342}, 0, {   334,    990}, {0xac, 0xd6, 0xac, 0xff}}},
    {{{  -229,   -137,   -306}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -229,    -86,   -342}, 0, {   334,    612}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   230,    -86,   -342}, 0, {   334,    612}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700FE90 - 0x0700FED0
static const Vtx ttc_seg7_vertex_0700FE90[] = {
    {{{   230,   -137,   -342}, 0, {     0,      0}, {0x33, 0x99, 0xcd, 0xff}}},
    {{{  -229,   -137,    344}, 0, {     0,      0}, {0xcd, 0x99, 0x33, 0xff}}},
    {{{  -229,   -137,   -342}, 0, {     0,      0}, {0xac, 0xd6, 0xac, 0xff}}},
    {{{   230,   -137,    344}, 0, {     0,      0}, {0x54, 0xd6, 0x54, 0xff}}},
};

// 0x0700FED0 - 0x0700FF28
static const Gfx ttc_seg7_dl_0700FED0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttc_seg7_lights_0700FC80.l, 1),
    gsSPLight(&ttc_seg7_lights_0700FC80.a, 2),
    gsSPVertex(ttc_seg7_vertex_0700FCB0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FF28 - 0x0700FFB8
static const Gfx ttc_seg7_dl_0700FF28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttc_seg7_vertex_0700FD30, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  5, 11, 12, 0x0),
    gsSP2Triangles( 5, 13, 11, 0x0, 14, 15,  8, 0x0),
    gsSP1Triangle( 0, 12,  1, 0x0),
    gsSPVertex(ttc_seg7_vertex_0700FE30, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP1Triangle( 2,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FFB8 - 0x0700FFE8
static const Gfx ttc_seg7_dl_0700FFB8[] = {
    gsSPLight(&ttc_seg7_lights_0700FC98.l, 1),
    gsSPLight(&ttc_seg7_lights_0700FC98.a, 2),
    gsSPVertex(ttc_seg7_vertex_0700FE90, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FFE8 - 0x070100B0
const Gfx ttc_seg7_dl_0700FFE8[] = {
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
    gsSPDisplayList(ttc_seg7_dl_0700FED0),
    gsSPDisplayList(ttc_seg7_dl_0700FF28),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_PASS2),
    gsSPDisplayList(ttc_seg7_dl_0700FFB8),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
