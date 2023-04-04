// 0x0700DFE8 - 0x0700E000
static const Lights1 ttm_seg7_lights_0700DFE8 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700E000 - 0x0700E100
static const Vtx ttm_seg7_vertex_0700E000[] = {
    {{{   -43,     93,   -191}, 0, {     0,    376}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{    48,     -9,   -237}, 0, {     0,    582}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{   231,     -9,    127}, 0, {   782,    582}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{  -317,   -213,    -54}, 0, {     0,    990}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{  -317,     -9,    -54}, 0, {     0,    582}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{  -130,     -9,    319}, 0, {   802,    582}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{  -130,   -213,    319}, 0, {   802,    990}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{  -130,     -9,    319}, 0, {   802,    582}, {0x50, 0xa7, 0xd8, 0xff}}},
    {{{  -317,     -9,    -54}, 0, {     0,    582}, {0x50, 0xa7, 0xd8, 0xff}}},
    {{{  -226,     93,   -100}, 0, {     0,    376}, {0x50, 0xa7, 0xd8, 0xff}}},
    {{{  -130,     -9,    319}, 0, {   802,    582}, {0x5a, 0xb1, 0xd8, 0xff}}},
    {{{  -226,     93,   -100}, 0, {     0,    376}, {0x5a, 0xb1, 0xd8, 0xff}}},
    {{{     9,    215,    185}, 0, {   688,    132}, {0x5a, 0xb1, 0xd8, 0xff}}},
    {{{     9,    215,    185}, 0, {   134,    230}, {0x12, 0x88, 0x24, 0xff}}},
    {{{  -226,     93,   -100}, 0, {     0,    990}, {0x12, 0x88, 0x24, 0xff}}},
    {{{   -43,     93,   -191}, 0, {   376,    990}, {0x12, 0x88, 0x24, 0xff}}},
};

// 0x0700E100 - 0x0700E200
static const Vtx ttm_seg7_vertex_0700E100[] = {
    {{{     9,    215,    185}, 0, {   688,    132}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{   -43,     93,   -191}, 0, {     0,    376}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{   231,     -9,    127}, 0, {   782,    582}, {0xb0, 0xa7, 0x28, 0xff}}},
    {{{  -317,     -9,    -54}, 0, {  6732,  -5344}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    48,   -213,   -237}, 0, {  7510,  -4898}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    48,     -9,   -237}, 0, {  7530,  -5306}, {0x38, 0x00, 0x71, 0xff}}},
    {{{   231,     -9,    127}, 0, {   782,    582}, {0x8f, 0x00, 0x39, 0xff}}},
    {{{    48,     -9,   -237}, 0, {     0,    582}, {0x8f, 0x00, 0x39, 0xff}}},
    {{{    48,   -213,   -237}, 0, {     0,    990}, {0x8f, 0x00, 0x39, 0xff}}},
    {{{   231,   -213,    127}, 0, {   782,    990}, {0x8f, 0x00, 0x39, 0xff}}},
    {{{   231,   -213,    127}, 0, {  3290,  -7342}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    48,   -213,   -237}, 0, {  2926,  -8070}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     9,   -213,    185}, 0, {  2848,  -7226}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -317,   -213,    -54}, 0, {  2194,  -7704}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -130,   -213,    319}, 0, {  2568,  -6958}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -317,   -213,    -54}, 0, {  6712,  -4936}, {0x38, 0x00, 0x71, 0xff}}},
};

// 0x0700E200 - 0x0700E240
static const Vtx ttm_seg7_vertex_0700E200[] = {
    {{{  -317,     -9,    -54}, 0, {  6732,  -5344}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    48,     -9,   -237}, 0, {  7530,  -5306}, {0x38, 0x00, 0x71, 0xff}}},
    {{{   -43,     93,   -191}, 0, {  7340,  -5520}, {0x38, 0x00, 0x71, 0xff}}},
    {{{  -226,     93,   -100}, 0, {  6942,  -5538}, {0x38, 0x00, 0x71, 0xff}}},
};

// 0x0700E240 - 0x0700E308
static const Gfx ttm_seg7_dl_0700E240[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700DFE8.l, 1),
    gsSPLight(&ttm_seg7_lights_0700DFE8.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700E000, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(ttm_seg7_vertex_0700E100, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(11, 13, 12, 0x0,  3, 15,  4, 0x0),
    gsSPVertex(ttm_seg7_vertex_0700E200, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E308 - 0x0700E378
const Gfx ttm_seg7_dl_0700E308[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700E240),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
