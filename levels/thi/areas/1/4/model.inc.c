// 0x07007078 - 0x07007090
static const Lights1 thi_seg7_lights_07007078 = gdSPDefLights1(
    0x7f, 0x7f, 0x3c,
    0xff, 0xff, 0x78, 0x28, 0x28, 0x28
);

// 0x07007090 - 0x07007180
static const Vtx thi_seg7_vertex_07007090[] = {
    {{{  5632,  -6655,  -6655}, 0, {    76,    806}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  5120,  -6655,  -7167}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  4608,  -6655,  -5631}, 0, {   506,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -8191,  -3583,  -8191}, 0, {  1502,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,  -4607}, 0, {     0,     96}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,  -8191}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -8191,  -3583,  -8191}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -8191,  -3583,   8192}, 0, {  8144,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,   8192}, 0, {  8144,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,  -4607}, 0, {  1756,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,   8192}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  6963,  -3583,   8192}, 0, {  5998,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  6963,  -3583,   4608}, 0, {  5998,     96}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -5119,  -3583,   4608}, 0, {     0,     96}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  5632,  -6655,  -4607}, 0, {   722,    620}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07007180 - 0x07007240
static const Vtx thi_seg7_vertex_07007180[] = {
    {{{  7680,  -6655,  -6655}, 0, {   224,    786}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  8192,  -6655,  -7167}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  5120,  -6655,  -7167}, 0, {  1502,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  5632,  -6655,  -6655}, 0, {  1246,    786}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -921,  -6655,  -5426}, 0, {  1940,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -921,  -6655,  -4607}, 0, {  1952,    844}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  5632,  -6655,  -4607}, 0, {  -382,    800}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  4608,  -6655,  -5631}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  7680,  -6655,  -6655}, 0, {   970,    790}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  7680,  -6655,  -5631}, 0, {   470,    890}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  7680,  -6655,  -4607}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  8192,  -6655,  -7167}, 0, {  1270,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07007240 - 0x070072E8
static const Gfx thi_seg7_dl_07007240[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, grass_0900B800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&thi_seg7_lights_07007078.l, 1),
    gsSPLight(&thi_seg7_lights_07007078.a, 2),
    gsSPVertex(thi_seg7_vertex_07007090, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(thi_seg7_vertex_07007180, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x070072E8 - 0x07007348
const Gfx thi_seg7_dl_070072E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_07007240),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
