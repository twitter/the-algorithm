// 0x070076A8 - 0x070076C0
static const Lights1 thi_seg7_lights_070076A8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070076C0 - 0x070076D8
static const Lights1 thi_seg7_lights_070076C0 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0x99, 0x99, 0x99, 0x28, 0x28, 0x28
);

// 0x070076D8 - 0x07007718
static const Vtx thi_seg7_vertex_070076D8[] = {
    {{{  -409,      0,   -409}, 0, {  -848,    172}, {0xac, 0x54, 0xd6, 0xff}}},
    {{{   410,      0,    410}, 0, {   786,   1806}, {0x54, 0x54, 0x2a, 0xff}}},
    {{{   410,      0,   -409}, 0, {   786,    172}, {0x33, 0x33, 0x99, 0xff}}},
    {{{  -409,      0,    410}, 0, {  -848,   1806}, {0xcd, 0x33, 0x67, 0xff}}},
};

// 0x07007718 - 0x070077D8
static const Vtx thi_seg7_vertex_07007718[] = {
    {{{  -409,   -101,   -409}, 0, {   990,    224}, {0xc0, 0xd4, 0x9c, 0xff}}},
    {{{  -409,      0,   -409}, 0, {   990,      0}, {0xac, 0x54, 0xd6, 0xff}}},
    {{{   410,      0,   -409}, 0, {     0,      0}, {0x33, 0x33, 0x99, 0xff}}},
    {{{   410,   -101,   -409}, 0, {     0,    224}, {0x64, 0xd4, 0xc0, 0xff}}},
    {{{   410,      0,    410}, 0, {   990,      0}, {0x54, 0x54, 0x2a, 0xff}}},
    {{{   410,   -101,    410}, 0, {   990,    224}, {0x40, 0xd4, 0x64, 0xff}}},
    {{{   410,   -101,    410}, 0, {     0,    224}, {0x40, 0xd4, 0x64, 0xff}}},
    {{{  -409,      0,    410}, 0, {   990,      0}, {0xcd, 0x33, 0x67, 0xff}}},
    {{{  -409,   -101,    410}, 0, {   990,    224}, {0x9c, 0xd4, 0x40, 0xff}}},
    {{{   410,      0,    410}, 0, {     0,      0}, {0x54, 0x54, 0x2a, 0xff}}},
    {{{  -409,      0,   -409}, 0, {     0,      0}, {0xac, 0x54, 0xd6, 0xff}}},
    {{{  -409,   -101,   -409}, 0, {     0,    224}, {0xc0, 0xd4, 0x9c, 0xff}}},
};

// 0x070077D8 - 0x07007828
static const Vtx thi_seg7_vertex_070077D8[] = {
    {{{  -409,   -101,   -409}, 0, {  -438,   2624}, {0xc0, 0xd4, 0x9c, 0xff}}},
    {{{   410,   -101,   -409}, 0, { -1666,   2624}, {0x64, 0xd4, 0xc0, 0xff}}},
    {{{     0,   -613,      0}, 0, { -1052,   2010}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   410,   -101,    410}, 0, { -1666,   1396}, {0x40, 0xd4, 0x64, 0xff}}},
    {{{  -409,   -101,    410}, 0, {  -438,   1396}, {0x9c, 0xd4, 0x40, 0xff}}},
};

// 0x07007828 - 0x07007870
static const Gfx thi_seg7_dl_07007828[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&thi_seg7_lights_070076A8.l, 1),
    gsSPLight(&thi_seg7_lights_070076A8.a, 2),
    gsSPVertex(thi_seg7_vertex_070076D8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007870 - 0x070078D8
static const Gfx thi_seg7_dl_07007870[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(thi_seg7_vertex_07007718, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 8,  7, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x070078D8 - 0x07007930
static const Gfx thi_seg7_dl_070078D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&thi_seg7_lights_070076C0.l, 1),
    gsSPLight(&thi_seg7_lights_070076C0.a, 2),
    gsSPVertex(thi_seg7_vertex_070077D8, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 3,  4,  2, 0x0,  4,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007930 - 0x070079D0
const Gfx thi_seg7_dl_07007930[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_07007828),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_07007870),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thi_seg7_dl_070078D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
