// 0x07005850 - 0x07005868
static const Lights1 ddd_seg7_lights_07005850 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07005868 - 0x07005968
static const Vtx ddd_seg7_vertex_07005868[] = {
    {{{     0,   2048,   2048}, 0, { 10000,      0}, {0x9e, 0x00, 0xb1, 0xff}}},
    {{{ -1023,      0,   3072}, 0, {  8492,    990}, {0xb1, 0x00, 0x9e, 0xff}}},
    {{{ -1023,   2048,   3072}, 0, {  8492,      0}, {0xbc, 0x00, 0x96, 0xff}}},
    {{{ -3993,   2048,   3174}, 0, {  5124,      0}, {0x10, 0x00, 0x83, 0xff}}},
    {{{ -5119,      0,   3072}, 0, {  3904,    990}, {0x27, 0x00, 0x88, 0xff}}},
    {{{ -5119,   2048,   3072}, 0, {  3904,      0}, {0x43, 0x00, 0x95, 0xff}}},
    {{{ -6143,      0,   2048}, 0, {  2328,    990}, {0x62, 0x00, 0xb1, 0xff}}},
    {{{ -6143,   2048,   2048}, 0, {  2328,      0}, {0x6a, 0x00, 0xbc, 0xff}}},
    {{{ -3993,      0,   3174}, 0, {  5124,    990}, {0x15, 0x00, 0x83, 0xff}}},
    {{{ -6655,      0,   1024}, 0, {  1088,    990}, {0x7b, 0x00, 0xe4, 0xff}}},
    {{{ -6655,   2048,   1024}, 0, {  1088,      0}, {0x7e, 0x00, 0x03, 0xff}}},
    {{{ -2047,   2048,   3584}, 0, {  7296,      0}, {0xff, 0x00, 0x82, 0xff}}},
    {{{ -2047,      0,   3584}, 0, {  7296,    990}, {0xe2, 0x00, 0x85, 0xff}}},
    {{{ -2047,   2048,  -3583}, 0, { 17168,      0}, {0xbb, 0x00, 0x6a, 0xff}}},
    {{{ -2047,      0,  -3583}, 0, { 17168,    990}, {0xde, 0x00, 0x7a, 0xff}}},
    {{{ -1330,      0,  -2764}, 0, { 16060,    990}, {0xac, 0x00, 0x5e, 0xff}}},
};

// 0x07005968 - 0x07005A68
static const Vtx ddd_seg7_vertex_07005968[] = {
    {{{     0,   2048,   2048}, 0, { 10000,      0}, {0x9e, 0x00, 0xb1, 0xff}}},
    {{{     0,      0,   2048}, 0, { 10000,    990}, {0x96, 0x00, 0xbc, 0xff}}},
    {{{ -1023,      0,   3072}, 0, {  8492,    990}, {0xb1, 0x00, 0x9e, 0xff}}},
    {{{   512,   2048,   1024}, 0, { 11176,      0}, {0x88, 0x00, 0xda, 0xff}}},
    {{{   512,      0,   1024}, 0, { 11176,    990}, {0x83, 0x00, 0xed, 0xff}}},
    {{{   512,   2048,  -1023}, 0, { 13288,      0}, {0x83, 0x00, 0x13, 0xff}}},
    {{{   512,      0,  -1023}, 0, { 13288,    990}, {0x88, 0x00, 0x26, 0xff}}},
    {{{     0,   2048,  -2047}, 0, { 14464,      0}, {0x9d, 0x00, 0x4e, 0xff}}},
    {{{     0,      0,  -2047}, 0, { 14464,    990}, {0xaf, 0x00, 0x61, 0xff}}},
    {{{ -1330,   2048,  -2764}, 0, { 16060,      0}, {0xb7, 0x00, 0x67, 0xff}}},
    {{{ -1330,      0,  -2764}, 0, { 16060,    990}, {0xac, 0x00, 0x5e, 0xff}}},
    {{{ -2047,   2048,  -3583}, 0, { 17168,      0}, {0xbb, 0x00, 0x6a, 0xff}}},
    {{{ -4095,   2048,  -3583}, 0, { 19336,      0}, {0x13, 0x00, 0x7d, 0xff}}},
    {{{ -2047,      0,  -3583}, 0, { 17168,    990}, {0xde, 0x00, 0x7a, 0xff}}},
    {{{ -4095,      0,  -3583}, 0, { 19336,    990}, {0x26, 0x00, 0x78, 0xff}}},
    {{{ -5119,   2048,  -3071}, 0, { 20560,      0}, {0x44, 0x00, 0x6a, 0xff}}},
};

// 0x07005A68 - 0x07005AF8
static const Vtx ddd_seg7_vertex_07005A68[] = {
    {{{ -5119,   2048,  -3071}, 0, { 20560,      0}, {0x44, 0x00, 0x6a, 0xff}}},
    {{{ -5119,      0,  -3071}, 0, { 20560,    990}, {0x4f, 0x00, 0x62, 0xff}}},
    {{{ -4095,      0,  -3583}, 0, { 19336,    990}, {0x26, 0x00, 0x78, 0xff}}},
    {{{ -6143,   2048,  -2047}, 0, { 22132,      0}, {0x6d, 0x00, 0x40, 0xff}}},
    {{{ -6143,      0,  -2047}, 0, { 22132,    990}, {0x7a, 0x00, 0x20, 0xff}}},
    {{{ -6143,   2048,   -818}, 0, { 23440,      0}, {0x7e, 0x00, 0x0b, 0xff}}},
    {{{ -6143,      0,   -818}, 0, { 23440,    990}, {0x7c, 0x00, 0x16, 0xff}}},
    {{{ -6655,   2048,   1024}, 0, { 25616,      0}, {0x7e, 0x00, 0x03, 0xff}}},
    {{{ -6655,      0,   1024}, 0, { 25616,    990}, {0x7b, 0x00, 0xe4, 0xff}}},
};

// 0x07005AF8 - 0x07005C40
static const Gfx ddd_seg7_dl_07005AF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_0900B800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ddd_seg7_lights_07005850.l, 1),
    gsSPLight(&ddd_seg7_lights_07005850.a, 2),
    gsSPVertex(ddd_seg7_vertex_07005868, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 3,  8,  4, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11,  8,  3, 0x0),
    gsSP2Triangles(11, 12,  8, 0x0,  2, 12, 11, 0x0),
    gsSP2Triangles( 2,  1, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(ddd_seg7_vertex_07005968, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  4,  3, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 7,  8,  6, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles( 9, 10,  8, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(12, 13, 11, 0x0, 12, 14, 13, 0x0),
    gsSP1Triangle(15, 14, 12, 0x0),
    gsSPVertex(ddd_seg7_vertex_07005A68, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  4,  3, 0x0),
    gsSP2Triangles( 5,  6,  4, 0x0,  7,  6,  5, 0x0),
    gsSP1Triangle( 7,  8,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07005C40 - 0x07005CB0
const Gfx ddd_seg7_dl_07005C40[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ddd_seg7_dl_07005AF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};
