// 0x0700AFF0 - 0x0700B008
static const Lights1 ttm_seg7_lights_0700AFF0 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700B008 - 0x0700B068
static const Vtx ttm_seg7_vertex_0700B008[] = {
    {{{   -43,   1523,     95}, 0, {   990,      0}, {0xd3, 0x00, 0x76, 0xff}}},
    {{{   -58,   1523,      9}, 0, {   510,      0}, {0xeb, 0x00, 0x83, 0xff}}},
    {{{   -58,  -1634,      9}, 0, {   510,    990}, {0x89, 0x00, 0xd5, 0xff}}},
    {{{    26,  -1634,     40}, 0, {     0,    990}, {0x7d, 0x00, 0xec, 0xff}}},
    {{{    26,   1523,     40}, 0, {     0,      0}, {0x7d, 0x00, 0xec, 0xff}}},
    {{{   -43,  -1634,     95}, 0, {   990,    990}, {0xd3, 0x00, 0x76, 0xff}}},
};

// 0x0700B068 - 0x0700B0D0
static const Gfx ttm_seg7_dl_0700B068[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, ttm_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700AFF0.l, 1),
    gsSPLight(&ttm_seg7_lights_0700AFF0.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700B008, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 3,  0,  5, 0x0,  5,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B0D0 - 0x0700B130
const Gfx ttm_seg7_dl_0700B0D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700B068),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
