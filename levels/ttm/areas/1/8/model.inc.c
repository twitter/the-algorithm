// 0x0700BE90 - 0x0700BEA8
static const Lights1 ttm_seg7_lights_0700BE90 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x0700BEA8 - 0x0700BEC0
static const Lights1 ttm_seg7_lights_0700BEA8 = gdSPDefLights1(
    0x5d, 0x5d, 0x5d,
    0xbb, 0xbb, 0xbb, 0x28, 0x28, 0x28
);

// 0x0700BEC0 - 0x0700BF40
static const Vtx ttm_seg7_vertex_0700BEC0[] = {
    {{{   614,  -1486,   -697}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,  -1486,   -697}, 0, {  2420,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,   1487,   -697}, 0, {  2420,  -4946}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   205,   1487,   -697}, 0, {   786,  -4946}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -613,   1282,    717}, 0, {     0,  -1470}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -613,     49,    717}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   614,     49,    717}, 0, {  2420,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,   1282,    717}, 0, {  1604,  -1470}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0700BF40 - 0x0700BFE0
static const Vtx ttm_seg7_vertex_0700BF40[] = {
    {{{   205,   1282,    717}, 0, {  1604,    990}, {0x00, 0x7d, 0x12, 0xff}}},
    {{{   205,   1487,   -697}, 0, {  1604,  -1864}, {0x00, 0x7d, 0x12, 0xff}}},
    {{{  -613,   1282,    717}, 0, {     0,    990}, {0x00, 0x7d, 0x12, 0xff}}},
    {{{  -613,   1487,   -697}, 0, {     0,  -1864}, {0x00, 0x7d, 0x12, 0xff}}},
    {{{   205,   1282,    717}, 0, {  4874,  -4536}, {0x78, 0x27, 0x05, 0xff}}},
    {{{   614,     49,    717}, 0, {  4874,  -2076}, {0x78, 0x27, 0x05, 0xff}}},
    {{{   205,   1487,   -697}, 0, {  7698,  -4946}, {0x78, 0x27, 0x05, 0xff}}},
    {{{   614,  -1486,   -697}, 0, {  7698,    990}, {0x7c, 0x11, 0xee, 0xff}}},
    {{{   205,   1487,   -697}, 0, {  7698,  -4946}, {0x7c, 0x11, 0xee, 0xff}}},
    {{{   614,     49,    717}, 0, {  4874,  -2076}, {0x7c, 0x11, 0xee, 0xff}}},
};

// 0x0700BFE0 - 0x0700C070
static const Gfx ttm_seg7_dl_0700BFE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0700BE90.l, 1),
    gsSPLight(&ttm_seg7_lights_0700BE90.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700BEC0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  4, 0x0),
    gsSPLight(&ttm_seg7_lights_0700BEA8.l, 1),
    gsSPLight(&ttm_seg7_lights_0700BEA8.a, 2),
    gsSPVertex(ttm_seg7_vertex_0700BF40, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700C070 - 0x0700C0E0
const Gfx ttm_seg7_dl_0700C070[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700BFE0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
