// 0x0700B590 - 0x0700B5A8
static const Lights1 ttm_seg7_lights_0700B590 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700B5A8 - 0x0700B608
static const Vtx ttm_seg7_vertex_0700B5A8[] = {
    {{{    29,  -1653,     57}, 0, {   990,    990}, {0x9f, 0x00, 0x51, 0xff}}},
    {{{    14,   1556,    -28}, 0, {   510,      0}, {0x89, 0x00, 0xd5, 0xff}}},
    {{{    14,  -1653,    -28}, 0, {   510,    990}, {0xea, 0x00, 0x84, 0xff}}},
    {{{    98,   1556,      2}, 0, {     0,      0}, {0x60, 0x00, 0xae, 0xff}}},
    {{{    98,  -1653,      2}, 0, {     0,    990}, {0x76, 0x00, 0x2e, 0xff}}},
    {{{    29,   1556,     57}, 0, {   990,      0}, {0x12, 0x00, 0x7d, 0xff}}},
};

// 0x0700B608 - 0x0700B670
static const Gfx ttm_seg7_dl_0700B608[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, ttm_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700B590.l, 1),
    gsSPLight(&ttm_seg7_lights_0700B590.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700B5A8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  4,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B670 - 0x0700B6D0
const Gfx ttm_seg7_dl_0700B670[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700B608),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
