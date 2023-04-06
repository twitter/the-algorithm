// 0x07020058 - 0x07020070
static const Lights1 bbh_seg7_lights_07020058 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07020070 - 0x07020130
static const Vtx bbh_seg7_vertex_07020070[] = {
    {{{  -204,      0,   -204}, 0, {     0,      0}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{     0,    410,      0}, 0, {   480,    784}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{   205,      0,   -204}, 0, {   990,      0}, {0x00, 0x38, 0x8f, 0xff}}},
    {{{  -204,      0,    205}, 0, {   990,      0}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{     0,    410,      0}, 0, {   478,    784}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{  -204,      0,   -204}, 0, {     0,      0}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{   205,      0,   -204}, 0, {     0,      0}, {0x71, 0x38, 0x00, 0xff}}},
    {{{     0,    410,      0}, 0, {   478,    784}, {0x71, 0x38, 0x00, 0xff}}},
    {{{   205,      0,    205}, 0, {   990,      0}, {0x71, 0x38, 0x00, 0xff}}},
    {{{   205,      0,    205}, 0, {   990,      0}, {0x00, 0x38, 0x71, 0xff}}},
    {{{     0,    410,      0}, 0, {   480,    784}, {0x00, 0x38, 0x71, 0xff}}},
    {{{  -204,      0,    205}, 0, {     0,      0}, {0x00, 0x38, 0x71, 0xff}}},
};

// 0x07020130 - 0x07020230
static const Vtx bbh_seg7_vertex_07020130[] = {
    {{{  1434,      0,  -1433}, 0, {  4056,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,   -204}, 0, {   990,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,    205}, 0, {     0,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,    205}, 0, {   990,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1433,      0,   1434}, 0, { -3096,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1434,      0,   1434}, 0, {  4056,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,    205}, 0, {     0,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1433,      0,   1434}, 0, {  4056,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,   -204}, 0, {     0,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1433,      0,  -1433}, 0, { -3096,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,    205}, 0, {   990,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{ -1433,      0,  -1433}, 0, {  4056,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,   -204}, 0, {     0,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1434,      0,  -1433}, 0, { -3096,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,   -204}, 0, {   990,    284}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1434,      0,   1434}, 0, { -3096,   2164}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07020230 - 0x07020288
static const Gfx bbh_seg7_dl_07020230[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_07020058.l, 1),
    gsSPLight(&bbh_seg7_lights_07020058.a, 2),
    gsSPVertex(bbh_seg7_vertex_07020070, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07020288 - 0x070202F0
static const Gfx bbh_seg7_dl_07020288[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bbh_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_07020130, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x070202F0 - 0x07020368
const Gfx bbh_seg7_dl_070202F0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07020230),
    gsSPDisplayList(bbh_seg7_dl_07020288),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
