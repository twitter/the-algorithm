// 0x0700A928 - 0x0700A940
static const Lights1 ttm_seg7_lights_0700A928 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700A940 - 0x0700A9C0
static const Vtx ttm_seg7_vertex_0700A940[] = {
    {{{    80,    654,   -199}, 0, {   308,    138}, {0x39, 0xf5, 0x70, 0xff}}},
    {{{   -22,    654,   -199}, 0, {   308,    138}, {0x8f, 0xfb, 0x38, 0xff}}},
    {{{   -22,  -1719,    -97}, 0, {     0,    990}, {0xc8, 0x04, 0x71, 0xff}}},
    {{{    80,  -1719,    -97}, 0, {     0,    990}, {0x71, 0x02, 0x38, 0xff}}},
    {{{    80,    654,   -302}, 0, {   650,    138}, {0x65, 0x03, 0xb5, 0xff}}},
    {{{   -22,  -1719,   -199}, 0, {   308,    990}, {0xa7, 0xfd, 0xa7, 0xff}}},
    {{{   -22,    654,   -302}, 0, {   650,    138}, {0xba, 0x0f, 0x98, 0xff}}},
    {{{    80,  -1719,   -199}, 0, {   308,    990}, {0x59, 0xfd, 0xa7, 0xff}}},
};

// 0x0700A9C0 - 0x0700AA40
static const Vtx ttm_seg7_vertex_0700A9C0[] = {
    {{{    80,    654,   -302}, 0, {     0,      0}, {0x65, 0x03, 0xb5, 0xff}}},
    {{{    80,   1473,      5}, 0, {     0,      0}, {0x71, 0xf3, 0x37, 0xff}}},
    {{{    80,    654,   -199}, 0, {     0,      0}, {0x39, 0xf5, 0x70, 0xff}}},
    {{{   -22,   1473,      5}, 0, {     0,      0}, {0xc8, 0xe5, 0x6e, 0xff}}},
    {{{   -22,    654,   -199}, 0, {     0,      0}, {0x8f, 0xfb, 0x38, 0xff}}},
    {{{    80,   1473,    -97}, 0, {     0,      0}, {0x38, 0x1b, 0x92, 0xff}}},
    {{{   -22,   1473,    -97}, 0, {     0,      0}, {0x8f, 0x0d, 0xc9, 0xff}}},
    {{{   -22,    654,   -302}, 0, {     0,      0}, {0xba, 0x0f, 0x98, 0xff}}},
};

// 0x0700AA40 - 0x0700AAB8
static const Gfx ttm_seg7_dl_0700AA40[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, ttm_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700A928.l, 1),
    gsSPLight(&ttm_seg7_lights_0700A928.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700A940, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 1,  6,  5, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  5,  4,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AAB8 - 0x0700AB08
static const Gfx ttm_seg7_dl_0700AAB8[] = {
    gsSPVertex(ttm_seg7_vertex_0700A9C0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 7,  5,  0, 0x0,  7,  6,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700AB08 - 0x0700AB70
const Gfx ttm_seg7_dl_0700AB08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700AA40),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(ttm_seg7_dl_0700AAB8),
    gsSPEndDisplayList(),
};
