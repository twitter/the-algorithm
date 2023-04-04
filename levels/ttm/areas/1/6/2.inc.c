// 0x0700BB30 - 0x0700BB48
static const Lights1 ttm_seg7_lights_0700BB30 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700BB48 - 0x0700BBA8
static const Vtx ttm_seg7_vertex_0700BB48[] = {
    {{{   -13,  -1627,     28}, 0, {   990,    990}, {0xd3, 0x00, 0x76, 0xff}}},
    {{{   -28,   1580,    -57}, 0, {   510,      0}, {0xaf, 0x00, 0x9f, 0xff}}},
    {{{   -28,  -1627,    -57}, 0, {   510,    990}, {0xaf, 0x00, 0x9f, 0xff}}},
    {{{   -13,   1580,     28}, 0, {   990,      0}, {0xd3, 0x00, 0x76, 0xff}}},
    {{{    56,  -1627,    -26}, 0, {     0,    990}, {0x61, 0x00, 0xaf, 0xff}}},
    {{{    56,   1580,    -26}, 0, {     0,      0}, {0x75, 0x00, 0x2f, 0xff}}},
};

// 0x0700BBA8 - 0x0700BC10
static const Gfx ttm_seg7_dl_0700BBA8[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, ttm_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700BB30.l, 1),
    gsSPLight(&ttm_seg7_lights_0700BB30.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700BB48, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  1,  5,  4, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  5,  3,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BC10 - 0x0700BC70
const Gfx ttm_seg7_dl_0700BC10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700BBA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
