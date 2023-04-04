// 0x070079B0 - 0x070079C8
static const Lights1 bbh_seg7_lights_070079B0 = gdSPDefLights1(
    0x3f, 0x51, 0x66,
    0x9e, 0xcc, 0xff, 0x28, 0x28, 0x28
);

// 0x070079C8 - 0x070079E0
static const Lights1 bbh_seg7_lights_070079C8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070079E0 - 0x07007AA0
static const Vtx bbh_seg7_vertex_070079E0[] = {
    {{{  1331,    512,   2038}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1741,    512,   2038}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1741,    102,   2038}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1331,    102,   2038}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -409,   1536,   2038}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   1536,   2038}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   1126,   2038}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -409,   1126,   2038}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1331,   1536,   2038}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1741,   1536,   2038}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1741,   1126,   2038}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1331,   1126,   2038}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07007AA0 - 0x07007AE0
static const Vtx bbh_seg7_vertex_07007AA0[] = {
    {{{   512,   1075,   2038}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   922,    666,   2038}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   512,    666,   2038}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   922,   1075,   2038}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07007AE0 - 0x07007B48
static const Gfx bbh_seg7_dl_07007AE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_070079B0.l, 1),
    gsSPLight(&bbh_seg7_lights_070079B0.a, 2),
    gsSPVertex(bbh_seg7_vertex_070079E0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007B48 - 0x07007B90
static const Gfx bbh_seg7_dl_07007B48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bbh_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_070079C8.l, 1),
    gsSPLight(&bbh_seg7_lights_070079C8.a, 2),
    gsSPVertex(bbh_seg7_vertex_07007AA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007B90 - 0x07007C08
const Gfx bbh_seg7_dl_07007B90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07007AE0),
    gsSPDisplayList(bbh_seg7_dl_07007B48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
