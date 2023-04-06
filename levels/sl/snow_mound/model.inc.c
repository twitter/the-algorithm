// 0x0700A620 - 0x0700A638
static const Lights1 sl_seg7_lights_0700A620 = gdSPDefLights1(
    0x73, 0x73, 0x73,
    0xe6, 0xe6, 0xe6, 0x28, 0x28, 0x28
);

// 0x0700A638 - 0x0700A718
static const Vtx sl_seg7_vertex_0700A638[] = {
    {{{   102,      0,   -255}, 0, {  2522,   2010}, {0x59, 0x59, 0x00, 0xff}}},
    {{{     0,    102,    256}, 0, {     0,   1498}, {0x59, 0x59, 0x00, 0xff}}},
    {{{   102,      0,    256}, 0, {     0,   2010}, {0x59, 0x59, 0x00, 0xff}}},
    {{{     0,    102,   -255}, 0, {  2522,   1500}, {0x59, 0x59, 0x00, 0xff}}},
    {{{     0,    102,   -255}, 0, {  2522,   1500}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{  -101,      0,    256}, 0, {     0,    990}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{     0,    102,    256}, 0, {     0,   1498}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{  -101,      0,   -255}, 0, {  2522,    990}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{     0,    102,    256}, 0, {   478,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,      0,    256}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,    256}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,   -255}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,      0,   -255}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,    102,   -255}, 0, {   478,    480}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700A718 - 0x0700A780
static const Gfx sl_seg7_dl_0700A718[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&sl_seg7_lights_0700A620.l, 1),
    gsSPLight(&sl_seg7_lights_0700A620.a, 2),
    gsSPVertex(sl_seg7_vertex_0700A638, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700A780 - 0x0700A7F0
const Gfx sl_seg7_dl_0700A780[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(sl_seg7_dl_0700A718),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
