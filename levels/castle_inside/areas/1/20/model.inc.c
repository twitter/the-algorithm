// 0x070379F8 - 0x07037A10
static const Lights1 inside_castle_seg7_lights_070379F8 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07037A10 - 0x07037B10
static const Vtx inside_castle_seg7_vertex_07037A10[] = {
    {{{  4301,   1229,   -716}, 0, {  1204,   2804}, {0xbf, 0x9b, 0x26, 0xff}}},
    {{{  3789,   1638,   -504}, 0, {  1304,   2510}, {0xd3, 0x8b, 0x0b, 0xff}}},
    {{{  3065,   1638,  -1228}, 0, {  1046,   2070}, {0xe9, 0x8b, 0x28, 0xff}}},
    {{{  1229,   1229,    256}, 0, {  1688,   1030}, {0x41, 0x9b, 0xda, 0xff}}},
    {{{  1741,   1638,     44}, 0, {  1588,   1324}, {0x19, 0x84, 0xfa, 0xff}}},
    {{{  2465,   1638,    768}, 0, {  1846,   1764}, {0x1d, 0x90, 0xce, 0xff}}},
    {{{  1229,   1229,   -716}, 0, {  1310,   1006}, {0x49, 0x9b, 0x13, 0xff}}},
    {{{  1741,   1638,   -504}, 0, {  1374,   1312}, {0x32, 0x90, 0x1d, 0xff}}},
    {{{  3789,   1638,     44}, 0, {  1518,   2522}, {0xdc, 0x88, 0xf2, 0xff}}},
    {{{  3065,   1638,    768}, 0, {  1826,   2114}, {0xed, 0x8d, 0xd0, 0xff}}},
    {{{  2465,   1638,  -1228}, 0, {  1068,   1718}, {0x0b, 0x8b, 0x2d, 0xff}}},
    {{{  2253,   1229,  -1740}, 0, {   874,   1582}, {0x26, 0x9b, 0x41, 0xff}}},
    {{{  3277,   1229,  -1740}, 0, {   840,   2182}, {0xed, 0x9b, 0x49, 0xff}}},
    {{{  2253,   1229,   1280}, 0, {  2054,   1652}, {0x13, 0x9b, 0xb7, 0xff}}},
    {{{  4301,   1229,    256}, 0, {  1584,   2826}, {0xbb, 0x9a, 0xe4, 0xff}}},
    {{{  3277,   1229,   1280}, 0, {  2018,   2250}, {0xe4, 0x9a, 0xbb, 0xff}}},
};

// 0x07037B10 - 0x07037BF8
static const Gfx inside_castle_seg7_dl_07037B10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_070379F8.l, 1),
    gsSPLight(&inside_castle_seg7_lights_070379F8.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_07037A10, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  4,  3, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 4,  8,  9, 0x0,  4,  1,  8, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  4, 10,  2, 0x0),
    gsSP2Triangles( 4,  7, 10, 0x0,  4,  9,  5, 0x0),
    gsSP2Triangles(11,  7,  6, 0x0, 11, 10,  7, 0x0),
    gsSP2Triangles(12, 10, 11, 0x0, 12,  2, 10, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0,  3,  5, 13, 0x0),
    gsSP2Triangles(14,  1,  0, 0x0, 14,  8,  1, 0x0),
    gsSP2Triangles( 9,  8, 14, 0x0, 13,  5,  9, 0x0),
    gsSP2Triangles(15,  9, 14, 0x0, 13,  9, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07037BF8 - 0x07037C58
const Gfx inside_castle_seg7_dl_07037BF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07037B10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
