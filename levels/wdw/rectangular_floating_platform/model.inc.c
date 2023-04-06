// 0x07013BE8 - 0x07013C00
static const Lights1 wdw_seg7_lights_07013BE8 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07013C00 - 0x07013D00
static const Vtx wdw_seg7_vertex_07013C00[] = {
    {{{   256,    -63,    640}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,     64,    640}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,     64,    640}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,    -63,    640}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,    -63,    640}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,     64,    640}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,     64,   -639}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,    -63,   -639}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,    -63,   -639}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     64,   -639}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,    -63,   -639}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,    -63,   -639}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,     64,    640}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,    -63,    640}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,     64,   -639}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -255,     64,   -639}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07013D00 - 0x07013D80
static const Vtx wdw_seg7_vertex_07013D00[] = {
    {{{   256,    -63,    640}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,    -63,    640}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,    -63,   -639}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,    -63,   -639}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,     64,   -639}, 0, {   990,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     64,    640}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,     64,    640}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     64,   -639}, 0, {     0,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07013D80 - 0x07013DF8
static const Gfx wdw_seg7_dl_07013D80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wdw_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07013BE8.l, 1),
    gsSPLight(&wdw_seg7_lights_07013BE8.a, 2),
    gsSPVertex(wdw_seg7_vertex_07013C00, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  8, 15,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013DF8 - 0x07013E40
static const Gfx wdw_seg7_dl_07013DF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wdw_seg7_vertex_07013D00, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013E40 - 0x07013EB8
const Gfx wdw_seg7_dl_07013E40[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_07013D80),
    gsSPDisplayList(wdw_seg7_dl_07013DF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
