// 0x07017480 - 0x07017498
static const Lights1 bbh_seg7_lights_07017480 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07017498 - 0x07017518
static const Vtx bbh_seg7_vertex_07017498[] = {
    {{{   102,   2372,   1132}, 0, {     0,    990}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  -306,   2676,    858}, 0, {   990,      0}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{   102,   2676,    858}, 0, {     0,      0}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  -306,   2372,   1132}, 0, {   990,    990}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  1638,   2372,   1132}, 0, {     0,    990}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  1229,   2676,    858}, 0, {   990,      0}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  1638,   2676,    858}, 0, {     0,      0}, {0x00, 0xab, 0xa2, 0xff}}},
    {{{  1229,   2372,   1132}, 0, {   990,    990}, {0x00, 0xab, 0xa2, 0xff}}},
};

// 0x07017518 - 0x07017570
static const Gfx bbh_seg7_dl_07017518[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_07017480.l, 1),
    gsSPLight(&bbh_seg7_lights_07017480.a, 2),
    gsSPVertex(bbh_seg7_vertex_07017498, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07017570 - 0x070175E0
const Gfx bbh_seg7_dl_07017570[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07017518),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
