// 0x07013EB8 - 0x07013ED0
static const Lights1 wdw_seg7_lights_07013EB8 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07013ED0 - 0x07013FC0
static const Vtx wdw_seg7_vertex_07013ED0[] = {
    {{{     0,      0,      0}, 0, {  1738,      0}, {0x1f, 0x93, 0xc9, 0xff}}},
    {{{     0,    128,   -255}, 0, {  1738,  -2076}, {0x1f, 0x93, 0xc9, 0xff}}},
    {{{   222,    128,   -127}, 0, {     0,  -1054}, {0x1f, 0x93, 0xc9, 0xff}}},
    {{{     0,    128,   -255}, 0, {  1738,  -2076}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -221,    128,   -127}, 0, {  3508,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -221,    128,    128}, 0, {  3508,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    128,    256}, 0, {  1738,   2010}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   222,    128,    128}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   222,    128,   -127}, 0, {     0,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {  1738,      0}, {0xe1, 0x93, 0xc9, 0xff}}},
    {{{  -221,    128,   -127}, 0, {  3508,  -1054}, {0xe1, 0x93, 0xc9, 0xff}}},
    {{{     0,    128,   -255}, 0, {  1738,  -2076}, {0xe1, 0x93, 0xc9, 0xff}}},
    {{{     0,      0,      0}, 0, {  1738,      0}, {0xc1, 0x93, 0x00, 0xff}}},
    {{{  -221,    128,    128}, 0, {  3508,    990}, {0xc1, 0x93, 0x00, 0xff}}},
    {{{  -221,    128,   -127}, 0, {  3508,  -1054}, {0xc1, 0x93, 0x00, 0xff}}},
};

// 0x07013FC0 - 0x07014050
static const Vtx wdw_seg7_vertex_07013FC0[] = {
    {{{     0,      0,      0}, 0, {  1738,      0}, {0xe1, 0x93, 0x36, 0xff}}},
    {{{     0,    128,    256}, 0, {  1738,   2010}, {0xe1, 0x93, 0x36, 0xff}}},
    {{{  -221,    128,    128}, 0, {  3508,    990}, {0xe1, 0x93, 0x36, 0xff}}},
    {{{     0,      0,      0}, 0, {  1738,      0}, {0x1f, 0x93, 0x36, 0xff}}},
    {{{   222,    128,    128}, 0, {     0,    990}, {0x1f, 0x93, 0x36, 0xff}}},
    {{{     0,    128,    256}, 0, {  1738,   2010}, {0x1f, 0x93, 0x36, 0xff}}},
    {{{     0,      0,      0}, 0, {  1738,      0}, {0x3f, 0x92, 0x00, 0xff}}},
    {{{   222,    128,   -127}, 0, {     0,  -1054}, {0x3f, 0x92, 0x00, 0xff}}},
    {{{   222,    128,    128}, 0, {     0,    990}, {0x3f, 0x92, 0x00, 0xff}}},
};

// 0x07014050 - 0x070140E0
static const Gfx wdw_seg7_dl_07014050[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07013EB8.l, 1),
    gsSPLight(&wdw_seg7_lights_07013EB8.a, 2),
    gsSPVertex(wdw_seg7_vertex_07013ED0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 3,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(wdw_seg7_vertex_07013FC0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x070140E0 - 0x07014150
const Gfx wdw_seg7_dl_070140E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_07014050),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
