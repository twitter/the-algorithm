// 0x07009458 - 0x07009470
static const Lights1 vcutm_seg7_lights_07009458 = gdSPDefLights1(
    0x22, 0x22, 0x22,
    0x88, 0x88, 0x88, 0x28, 0x28, 0x28
);

// 0x07009470 - 0x07009488
static const Lights1 vcutm_seg7_lights_07009470 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009488 - 0x07009578
static const Vtx vcutm_seg7_vertex_07009488[] = {
    {{{   307,      0,    768}, 0, {     0,   1960}, {0x00, 0xc8, 0x71, 0xff}}},
    {{{  -306,    102,    819}, 0, {     0,    152}, {0x00, 0xc8, 0x71, 0xff}}},
    {{{  -306,      0,    768}, 0, {     0,    100}, {0x00, 0xc8, 0x71, 0xff}}},
    {{{   307,    102,    819}, 0, {     0,   2012}, {0x00, 0xc8, 0x71, 0xff}}},
    {{{   307,      0,   -767}, 0, {   958,   1960}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,    102,    819}, 0, {     0,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,      0,    768}, 0, {     0,   1960}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,    102,   -818}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,      0,    768}, 0, {     0,   1960}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -306,      0,    768}, 0, {     0,    100}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -306,      0,   -767}, 0, {   958,    100}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   307,      0,   -767}, 0, {   958,   1960}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -306,      0,    768}, 0, {     0,    100}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,    152}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,      0,   -767}, 0, {   958,    100}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07009578 - 0x070095E8
static const Vtx vcutm_seg7_vertex_07009578[] = {
    {{{  -306,      0,   -767}, 0, {   958,    100}, {0x00, 0xc8, 0x8f, 0xff}}},
    {{{   307,    102,   -818}, 0, {   990,   2012}, {0x00, 0xc8, 0x8f, 0xff}}},
    {{{   307,      0,   -767}, 0, {   958,   1960}, {0x00, 0xc8, 0x8f, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,    152}, {0x00, 0xc8, 0x8f, 0xff}}},
    {{{  -306,      0,    768}, 0, {     0,    100}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,    102,    819}, 0, {     0,    152}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,    152}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x070095E8 - 0x07009628
static const Vtx vcutm_seg7_vertex_070095E8[] = {
    {{{   307,    102,   -818}, 0, {   990,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,    152}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,    102,    819}, 0, {     0,    152}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   307,    102,    819}, 0, {     0,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07009628 - 0x070096E0
static const Gfx vcutm_seg7_dl_07009628[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, vcutm_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&vcutm_seg7_lights_07009458.l, 1),
    gsSPLight(&vcutm_seg7_lights_07009458.a, 2),
    gsSPVertex(vcutm_seg7_vertex_07009488, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(vcutm_seg7_vertex_07009578, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP1Triangle( 4,  5,  6, 0x0),
    gsSPLight(&vcutm_seg7_lights_07009470.l, 1),
    gsSPLight(&vcutm_seg7_lights_07009470.a, 2),
    gsSPVertex(vcutm_seg7_vertex_070095E8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x070096E0 - 0x07009750
const Gfx vcutm_seg7_dl_070096E0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(vcutm_seg7_dl_07009628),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
