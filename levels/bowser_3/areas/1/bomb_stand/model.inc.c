// 0x07004740 - 0x07004758
static const Lights1 bowser_3_seg7_lights_07004740 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07004758 - 0x07004848
static const Vtx bowser_3_seg7_vertex_07004758[] = {
    {{{   205,    256,      0}, 0, {   478,    478}, {0x4d, 0x3e, 0xb2, 0xff}}},
    {{{     0,    256,   -204}, 0, {   734,    478}, {0x4d, 0x3e, 0xb2, 0xff}}},
    {{{     0,    512,      0}, 0, {   606,      0}, {0x4d, 0x3e, 0xb2, 0xff}}},
    {{{     0,    256,    205}, 0, {   224,    480}, {0x4e, 0x3e, 0x4e, 0xff}}},
    {{{   205,    256,      0}, 0, {   478,    478}, {0x4e, 0x3e, 0x4e, 0xff}}},
    {{{     0,    512,      0}, 0, {   352,      0}, {0x4e, 0x3e, 0x4e, 0xff}}},
    {{{   205,    256,      0}, 0, {   478,    478}, {0x4e, 0xc2, 0x4e, 0xff}}},
    {{{     0,    256,    205}, 0, {   224,    480}, {0x4e, 0xc2, 0x4e, 0xff}}},
    {{{     0,      0,      0}, 0, {   352,    990}, {0x4e, 0xc2, 0x4e, 0xff}}},
    {{{     0,    256,   -204}, 0, {   734,    478}, {0x4d, 0xc2, 0xb2, 0xff}}},
    {{{   205,    256,      0}, 0, {   478,    478}, {0x4d, 0xc2, 0xb2, 0xff}}},
    {{{     0,      0,      0}, 0, {   606,    990}, {0x4d, 0xc2, 0xb2, 0xff}}},
    {{{  -204,    256,      0}, 0, {     0,    480}, {0xb2, 0x3e, 0x4d, 0xff}}},
    {{{     0,    256,    205}, 0, {   224,    480}, {0xb2, 0x3e, 0x4d, 0xff}}},
    {{{     0,    512,      0}, 0, {    96,      0}, {0xb2, 0x3e, 0x4d, 0xff}}},
};

// 0x07004848 - 0x070048D8
static const Vtx bowser_3_seg7_vertex_07004848[] = {
    {{{     0,    256,    205}, 0, {   224,    480}, {0xb2, 0xc2, 0x4d, 0xff}}},
    {{{  -204,    256,      0}, 0, {     0,    480}, {0xb2, 0xc2, 0x4d, 0xff}}},
    {{{     0,      0,      0}, 0, {    96,    990}, {0xb2, 0xc2, 0x4d, 0xff}}},
    {{{     0,    256,   -204}, 0, {   734,    478}, {0xb2, 0x3e, 0xb2, 0xff}}},
    {{{  -204,    256,      0}, 0, {   990,    480}, {0xb2, 0x3e, 0xb2, 0xff}}},
    {{{     0,    512,      0}, 0, {   862,      0}, {0xb2, 0x3e, 0xb2, 0xff}}},
    {{{  -204,    256,      0}, 0, {   990,    480}, {0xb2, 0xc2, 0xb2, 0xff}}},
    {{{     0,    256,   -204}, 0, {   734,    478}, {0xb2, 0xc2, 0xb2, 0xff}}},
    {{{     0,      0,      0}, 0, {   862,    990}, {0xb2, 0xc2, 0xb2, 0xff}}},
};

// 0x070048D8 - 0x07004958
static const Gfx bowser_3_seg7_dl_070048D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bowser_3_seg7_texture_07000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bowser_3_seg7_lights_07004740.l, 1),
    gsSPLight(&bowser_3_seg7_lights_07004740.a, 2),
    gsSPVertex(bowser_3_seg7_vertex_07004758, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(bowser_3_seg7_vertex_07004848, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x07004958 - 0x070049C8
const Gfx bowser_3_seg7_dl_07004958[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bowser_3_seg7_dl_070048D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
