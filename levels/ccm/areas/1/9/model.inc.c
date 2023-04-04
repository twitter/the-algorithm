// 0x0700FB78 - 0x0700FB90
static const Lights1 ccm_seg7_lights_0700FB78 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700FB90 - 0x0700FC90
static const Vtx ccm_seg7_vertex_0700FB90[] = {
    {{{  -123,   -613,    238}, 0, {     0,    990}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{  -283,    666,    -85}, 0, {   690,    138}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{  -283,   -613,    -85}, 0, {   690,    990}, {0x8f, 0x00, 0x38, 0xff}}},
    {{{   201,   -613,     78}, 0, {   690,    990}, {0x38, 0x00, 0x71, 0xff}}},
    {{{   201,    666,     78}, 0, {   690,   -288}, {0x38, 0x00, 0x71, 0xff}}},
    {{{  -123,    666,    238}, 0, {     0,   -288}, {0x38, 0x00, 0x71, 0xff}}},
    {{{  -123,   -613,    238}, 0, {     0,    990}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    41,   -613,   -246}, 0, {   690,    990}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{    41,    666,   -246}, 0, {   690,    138}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{   201,    666,     78}, 0, {     0,    138}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{   201,   -613,     78}, 0, {     0,    990}, {0x71, 0x00, 0xc8, 0xff}}},
    {{{  -283,   -613,    -85}, 0, {   690,   1244}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{    41,    666,   -246}, 0, {     0,      0}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{    41,   -613,   -246}, 0, {     0,   1244}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{  -283,    666,    -85}, 0, {   690,      0}, {0xc8, 0x00, 0x8f, 0xff}}},
    {{{  -123,    666,    238}, 0, {     0,    138}, {0x8f, 0x00, 0x38, 0xff}}},
};

// 0x0700FC90 - 0x0700FD08
static const Gfx ccm_seg7_dl_0700FC90[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ccm_seg7_lights_0700FB78.l, 1),
    gsSPLight(&ccm_seg7_lights_0700FB78.a, 2),
    gsSPVertex(ccm_seg7_vertex_0700FB90, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FD08 - 0x0700FD88
const Gfx ccm_seg7_dl_0700FD08[] = {
    gsDPPipeSync(),
    gsDPSetEnvColor(255, 255, 255, 90),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ccm_seg7_dl_0700FC90),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
