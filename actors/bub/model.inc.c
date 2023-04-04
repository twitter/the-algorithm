// Bub

// 0x0600E278
static const Lights1 bub_seg6_lights_0600E280 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600E290
static const Lights1 bub_seg6_lights_0600E290 = gdSPDefLights1(
    0x3f, 0x1d, 0x25,
    0xff, 0x75, 0x94, 0x28, 0x28, 0x28
);

// 0x0600E2A8
ALIGNED8 static const Texture bub_seg6_texture_0600E2A8[] = {
#include "actors/bub/bub_eye_border.rgba16.inc.c"
};

// 0x0600EAA8
ALIGNED8 static const Texture bub_seg6_texture_0600EAA8[] = {
#include "actors/bub/bub_fins.rgba16.inc.c"
};

// 0x0600F2A8
ALIGNED8 static const Texture bub_seg6_texture_0600F2A8[] = {
#include "actors/bub/bub_eyes.rgba16.inc.c"
};

// 0x060102A8
ALIGNED8 static const Texture bub_seg6_texture_060102A8[] = {
#include "actors/bub/bub_scales.rgba16.inc.c"
};

// 0x060112A8
static const Vtx bub_seg6_vertex_060112A8[] = {
    {{{   -47,     79,     27}, 0, {    26,    980}, {0x00, 0x6b, 0x43, 0xff}}},
    {{{   -94,     80,     38}, 0, {   928,    968}, {0xf7, 0x73, 0x33, 0xff}}},
    {{{   -53,      0,     91}, 0, {   132,    102}, {0x10, 0x01, 0x7d, 0xff}}},
    {{{  -111,     38,    102}, 0, {  1338,    362}, {0xa2, 0x33, 0x43, 0xff}}},
    {{{  -111,    -41,    102}, 0, {  -428,    296}, {0xa4, 0xca, 0x43, 0xff}}},
    {{{   -92,    -82,     38}, 0, {   -40,    958}, {0xf9, 0x8d, 0x33, 0xff}}},
    {{{   -53,      0,     91}, 0, {   910,     -4}, {0x10, 0x01, 0x7d, 0xff}}},
    {{{   -45,    -81,     27}, 0, {   940,    960}, {0x00, 0x95, 0x43, 0xff}}},
    {{{  -100,    -71,    -23}, 0, {   302,     68}, {0x9f, 0xaf, 0x00, 0xff}}},
    {{{   -45,    -81,     27}, 0, {   926,    956}, {0x00, 0x95, 0x43, 0xff}}},
    {{{   -92,    -82,     38}, 0, {     4,    956}, {0xf9, 0x8d, 0x33, 0xff}}},
    {{{  -111,    -41,    102}, 0, {   -54,    146}, {0xa4, 0xca, 0x43, 0xff}}},
    {{{   -53,      0,     91}, 0, {   664,    466}, {0x10, 0x01, 0x7d, 0xff}}},
    {{{  -111,     38,    102}, 0, {   -12,    790}, {0xa2, 0x33, 0x43, 0xff}}},
};

// 0x06011388
static const Vtx bub_seg6_vertex_06011388[] = {
    {{{  -102,     68,    -23}, 0, {   688,     60}, {0x9e, 0x4f, 0x00, 0xff}}},
    {{{   -94,     80,     38}, 0, {   960,    960}, {0xf7, 0x73, 0x33, 0xff}}},
    {{{   -47,     79,     27}, 0, {    16,    968}, {0x00, 0x6b, 0x43, 0xff}}},
};

// 0x060113B8
static const Vtx bub_seg6_vertex_060113B8[] = {
    {{{   -17,    -15,     85}, 0, {   426,   1050}, {0xfd, 0x8e, 0x35, 0xff}}},
    {{{    93,      0,     75}, 0, {  2024,   1040}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    30,      0,    126}, 0, {  1056,   1694}, {0x3c, 0x02, 0x6f, 0xff}}},
    {{{   -46,      0,    152}, 0, {   -72,   1972}, {0xa7, 0x04, 0x5a, 0xff}}},
    {{{   -53,      0,     91}, 0, {   -86,   1094}, {0x10, 0x01, 0x7d, 0xff}}},
    {{{   -17,     13,     85}, 0, {   422,   1050}, {0xfe, 0x73, 0x34, 0xff}}},
};

// 0x06011418
static const Vtx bub_seg6_vertex_06011418[] = {
    {{{  -100,    -71,    -23}, 0, {  2008,   1286}, {0x9f, 0xaf, 0x00, 0xff}}},
    {{{   -92,    -82,     38}, 0, {  2180,    384}, {0xf9, 0x8d, 0x33, 0xff}}},
    {{{  -111,    -41,    102}, 0, {  1576,   -554}, {0xa4, 0xca, 0x43, 0xff}}},
    {{{  -102,     68,    -23}, 0, {     0,   1286}, {0x9e, 0x4f, 0x00, 0xff}}},
    {{{  -111,     38,    102}, 0, {   404,   -554}, {0xa2, 0x33, 0x43, 0xff}}},
    {{{   -94,     80,     38}, 0, {  -200,    384}, {0xf7, 0x73, 0x33, 0xff}}},
    {{{  -113,     -1,      6}, 0, {   992,    844}, {0x87, 0x00, 0x26, 0xff}}},
};

// 0x06011488
static const Vtx bub_seg6_vertex_06011488[] = {
    {{{    69,     68,     18}, 0, {  1976,   -164}, {0x3a, 0x6a, 0x24, 0xff}}},
    {{{    63,     77,    -64}, 0, {  1872,   1220}, {0x35, 0x55, 0xb3, 0xff}}},
    {{{   -62,    106,    -67}, 0, {  -288,   1334}, {0xe7, 0x7b, 0xf2, 0xff}}},
    {{{   134,      0,     22}, 0, {  3456,     86}, {0x77, 0x00, 0x2a, 0xff}}},
    {{{    93,      0,     75}, 0, {  2620,   -532}, {0x38, 0x00, 0x71, 0xff}}},
    {{{    70,    -68,     18}, 0, {  2152,    990}, {0x3b, 0x97, 0x24, 0xff}}},
    {{{   -17,    -15,     85}, 0, {   528,   -486}, {0xfd, 0x8e, 0x35, 0xff}}},
    {{{   -45,    -81,     27}, 0, {     0,    990}, {0x00, 0x95, 0x43, 0xff}}},
    {{{   -53,      0,     91}, 0, {  -100,   -752}, {0x10, 0x01, 0x7d, 0xff}}},
    {{{    93,      0,     75}, 0, {  2384,  -1138}, {0x38, 0x00, 0x71, 0xff}}},
    {{{   134,      0,     22}, 0, {  3100,   -272}, {0x77, 0x00, 0x2a, 0xff}}},
    {{{   -47,     79,     27}, 0, {   -40,   -258}, {0x00, 0x6b, 0x43, 0xff}}},
    {{{   -17,     13,     85}, 0, {   456,  -1234}, {0xfe, 0x73, 0x34, 0xff}}},
    {{{   134,      0,    -17}, 0, {  3104,    404}, {0x7c, 0x00, 0xe7, 0xff}}},
    {{{   -53,      0,     91}, 0, {  -152,  -1316}, {0x10, 0x01, 0x7d, 0xff}}},
};

// 0x06011578
static const Vtx bub_seg6_vertex_06011578[] = {
    {{{   134,      0,    -17}, 0, {  1744,   1076}, {0x7c, 0x00, 0xe7, 0xff}}},
    {{{   134,      0,     22}, 0, {  1444,    174}, {0x77, 0x00, 0x2a, 0xff}}},
    {{{    70,    -68,     18}, 0, {   124,    848}, {0x3b, 0x97, 0x24, 0xff}}},
    {{{   134,      0,    -17}, 0, {  3680,   -186}, {0x7c, 0x00, 0xe7, 0xff}}},
    {{{    70,    -68,     18}, 0, {  2148,   -526}, {0x3b, 0x97, 0x24, 0xff}}},
    {{{    64,    -77,    -64}, 0, {  2176,    910}, {0x36, 0xac, 0xb3, 0xff}}},
    {{{   -45,    -81,     27}, 0, {     0,   -416}, {0x00, 0x95, 0x43, 0xff}}},
    {{{   -60,   -108,    -67}, 0, {  -204,   1278}, {0xe9, 0x85, 0xf2, 0xff}}},
    {{{    64,    -77,    -64}, 0, {   616,    104}, {0x36, 0xac, 0xb3, 0xff}}},
    {{{    85,      0,    -97}, 0, {  1508,    946}, {0x43, 0x00, 0x95, 0xff}}},
    {{{   134,      0,    -17}, 0, {  2204,   -676}, {0x7c, 0x00, 0xe7, 0xff}}},
    {{{    63,     77,    -64}, 0, {  -188,    674}, {0x35, 0x55, 0xb3, 0xff}}},
    {{{   134,      0,    -17}, 0, {  1092,   -116}, {0x7c, 0x00, 0xe7, 0xff}}},
    {{{    85,      0,    -97}, 0, {  1016,   1428}, {0x43, 0x00, 0x95, 0xff}}},
};

// 0x06011658
static const Vtx bub_seg6_vertex_06011658[] = {
    {{{   -60,   -108,    -67}, 0, {  1920,    450}, {0xe9, 0x85, 0xf2, 0xff}}},
    {{{   -45,    -81,     27}, 0, {  2056,  -1674}, {0x00, 0x95, 0x43, 0xff}}},
    {{{  -100,    -71,    -23}, 0, {   852,   -280}, {0x9f, 0xaf, 0x00, 0xff}}},
    {{{  -102,     68,    -23}, 0, {   700,   -344}, {0x9e, 0x4f, 0x00, 0xff}}},
    {{{   -47,     79,     27}, 0, {  1968,  -1410}, {0x00, 0x6b, 0x43, 0xff}}},
    {{{   -62,    106,    -67}, 0, {  1876,    472}, {0xe7, 0x7b, 0xf2, 0xff}}},
    {{{    63,     77,    -64}, 0, {  1884,    272}, {0x35, 0x55, 0xb3, 0xff}}},
    {{{   -77,     71,    -91}, 0, {  -540,   1024}, {0xc0, 0x30, 0x9e, 0xff}}},
    {{{   -62,    106,    -67}, 0, {  -288,    328}, {0xe7, 0x7b, 0xf2, 0xff}}},
    {{{   -60,   -108,    -67}, 0, {  -228,   -428}, {0xe9, 0x85, 0xf2, 0xff}}},
    {{{   -76,    -73,    -91}, 0, {  -344,    416}, {0xc1, 0xcf, 0x9e, 0xff}}},
    {{{    64,    -77,    -64}, 0, {  2188,   -424}, {0x36, 0xac, 0xb3, 0xff}}},
};

// 0x06011718
static const Vtx bub_seg6_vertex_06011718[] = {
    {{{   -76,    -73,    -91}, 0, {     0,      0}, {0xc1, 0xcf, 0x9e, 0xff}}},
    {{{  -107,     -1,    -79}, 0, {     0,      0}, {0x83, 0x00, 0xef, 0xff}}},
    {{{   -83,     -1,   -102}, 0, {     0,      0}, {0xcb, 0x00, 0x8d, 0xff}}},
    {{{   -89,     -1,    -52}, 0, {     0,      0}, {0x8e, 0x00, 0xca, 0xff}}},
    {{{   -77,     71,    -91}, 0, {     0,      0}, {0xc0, 0x30, 0x9e, 0xff}}},
    {{{  -138,     -1,    -51}, 0, {     0,      0}, {0xa1, 0x00, 0xad, 0xff}}},
    {{{  -102,     68,    -23}, 0, {     0,      0}, {0x9e, 0x4f, 0x00, 0xff}}},
    {{{   -62,    106,    -67}, 0, {     0,      0}, {0xe7, 0x7b, 0xf2, 0xff}}},
    {{{  -100,    -71,    -23}, 0, {     0,      0}, {0x9f, 0xaf, 0x00, 0xff}}},
    {{{  -113,     -1,      6}, 0, {     0,      0}, {0x87, 0x00, 0x26, 0xff}}},
    {{{  -147,     -1,    -24}, 0, {     0,      0}, {0x87, 0x00, 0x24, 0xff}}},
    {{{   -60,   -108,    -67}, 0, {     0,      0}, {0xe9, 0x85, 0xf2, 0xff}}},
};

// 0x060117D8
static const Vtx bub_seg6_vertex_060117D8[] = {
    {{{    63,     77,    -64}, 0, {     0,      0}, {0x35, 0x55, 0xb3, 0xff}}},
    {{{    85,      0,    -97}, 0, {     0,      0}, {0x43, 0x00, 0x95, 0xff}}},
    {{{   -24,      0,   -108}, 0, {     0,      0}, {0x07, 0x00, 0x82, 0xff}}},
    {{{   -83,     -1,   -102}, 0, {     0,      0}, {0xcb, 0x00, 0x8d, 0xff}}},
    {{{   -77,     71,    -91}, 0, {     0,      0}, {0xc0, 0x30, 0x9e, 0xff}}},
    {{{    64,    -77,    -64}, 0, {     0,      0}, {0x36, 0xac, 0xb3, 0xff}}},
    {{{   -76,    -73,    -91}, 0, {     0,      0}, {0xc1, 0xcf, 0x9e, 0xff}}},
};

// 0x06011848 - 0x060118C0
const Gfx bub_seg6_dl_06011848[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600E2A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bub_seg6_lights_0600E280.l, 1),
    gsSPLight(&bub_seg6_lights_0600E280.a, 2),
    gsSPVertex(bub_seg6_vertex_060112A8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bub_seg6_vertex_06011388, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x060118C0 - 0x06011918
const Gfx bub_seg6_dl_060118C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600EAA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bub_seg6_vertex_060113B8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  5,  2,  1, 0x0),
    gsSP2Triangles( 5,  3,  2, 0x0,  4,  3,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011918 - 0x06011968
const Gfx bub_seg6_dl_06011918[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600F2A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bub_seg6_vertex_06011418, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  6,  2,  4, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011968 - 0x06011A50
const Gfx bub_seg6_dl_06011968[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_060102A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bub_seg6_vertex_06011488, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  7,  5,  4, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  9, 10,  0, 0x0),
    gsSP2Triangles( 9,  0, 11, 0x0, 11, 12,  9, 0x0),
    gsSP2Triangles(10, 13,  0, 0x0, 13,  1,  0, 0x0),
    gsSP2Triangles( 2, 11,  0, 0x0, 11, 14, 12, 0x0),
    gsSPVertex(bub_seg6_vertex_06011578, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPVertex(bub_seg6_vertex_06011658, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011A50 - 0x06011B28
const Gfx bub_seg6_dl_06011A50[] = {
    gsSPLight(&bub_seg6_lights_0600E290.l, 1),
    gsSPLight(&bub_seg6_lights_0600E290.a, 2),
    gsSPVertex(bub_seg6_vertex_06011718, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  0,  5,  3, 0x0),
    gsSP2Triangles( 6,  7,  4, 0x0,  4,  5,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8,  5,  0, 0x0),
    gsSP2Triangles( 0, 11,  8, 0x0,  8, 10,  5, 0x0),
    gsSP2Triangles( 5, 10,  6, 0x0, 10,  9,  6, 0x0),
    gsSPLight(&bub_seg6_lights_0600E280.l, 1),
    gsSPLight(&bub_seg6_lights_0600E280.a, 2),
    gsSPVertex(bub_seg6_vertex_060117D8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 2,  6,  3, 0x0,  0,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011B28 - 0x06011BD8
const Gfx bub_seg6_dl_06011B28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011848),
    gsSPDisplayList(bub_seg6_dl_060118C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011918),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011968),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(bub_seg6_dl_06011A50),
    gsSPEndDisplayList(),
};

// 0x06011BD8
static const Vtx bub_seg6_vertex_06011BD8[] = {
    {{{    45,     16,      2}, 0, {   488,    418}, {0x04, 0x7e, 0x00, 0xff}}},
    {{{    61,      1,    -60}, 0, {   996,    234}, {0x1d, 0xff, 0x85, 0xff}}},
    {{{     0,      0,    -16}, 0, {   634,    976}, {0x91, 0xfe, 0xc4, 0xff}}},
    {{{     0,      0,     22}, 0, {   320,    974}, {0x90, 0xfe, 0x3b, 0xff}}},
    {{{    61,      1,     66}, 0, {   -16,    226}, {0x1e, 0xff, 0x7b, 0xff}}},
    {{{    81,      1,     26}, 0, {   306,    -18}, {0x7c, 0x00, 0x1a, 0xff}}},
    {{{    81,      1,    -20}, 0, {   682,    -16}, {0x7c, 0x00, 0xe5, 0xff}}},
    {{{    46,    -14,      2}, 0, {   488,    412}, {0x07, 0x82, 0x00, 0xff}}},
};

// 0x06011C58 - 0x06011CF0
const Gfx bub_seg6_dl_06011C58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600EAA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bub_seg6_lights_0600E280.l, 1),
    gsSPLight(&bub_seg6_lights_0600E280.a, 2),
    gsSPVertex(bub_seg6_vertex_06011BD8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 4,  5,  0, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 6,  1,  0, 0x0,  4,  0,  3, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 7,  1,  6, 0x0,  2,  7,  3, 0x0),
    gsSP2Triangles( 3,  7,  4, 0x0,  1,  7,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011CF0 - 0x06011D50
const Gfx bub_seg6_dl_06011CF0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011C58),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011D50
static const Vtx bub_seg6_vertex_06011D50[] = {
    {{{   113,     -7,      0}, 0, {   586,    220}, {0x4b, 0x9b, 0x0c, 0xff}}},
    {{{   113,     12,      0}, 0, {   584,    220}, {0x33, 0x73, 0xf7, 0xff}}},
    {{{    84,      7,     42}, 0, {   120,    132}, {0x37, 0x30, 0x67, 0xff}}},
    {{{    -9,      0,      0}, 0, {   182,    954}, {0xb1, 0x00, 0x63, 0xff}}},
    {{{    84,     -2,     42}, 0, {   120,    132}, {0x17, 0xbd, 0x69, 0xff}}},
    {{{    69,      1,    -51}, 0, {   900,    814}, {0x48, 0x00, 0x98, 0xff}}},
    {{{    18,      1,    -30}, 0, {   540,    978}, {0xbb, 0xfe, 0x96, 0xff}}},
};

// 0x06011DC0 - 0x06011E48
const Gfx bub_seg6_dl_06011DC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600EAA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bub_seg6_lights_0600E280.l, 1),
    gsSPLight(&bub_seg6_lights_0600E280.a, 2),
    gsSPVertex(bub_seg6_vertex_06011D50, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  4,  0, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 6,  5,  0, 0x0,  3,  6,  0, 0x0),
    gsSP2Triangles( 1,  5,  6, 0x0,  6,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011E48 - 0x06011EA8
const Gfx bub_seg6_dl_06011E48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011DC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06011EA8
static const Vtx bub_seg6_vertex_06011EA8[] = {
    {{{   127,      8,      0}, 0, {   454,    956}, {0x4a, 0x65, 0x0d, 0xff}}},
    {{{    31,      0,    -30}, 0, {   138,      4}, {0xbb, 0x01, 0x96, 0xff}}},
    {{{     3,      0,      0}, 0, {   518,    -40}, {0xb3, 0x00, 0x64, 0xff}}},
    {{{    82,      0,    -52}, 0, {  -152,    282}, {0x4a, 0x00, 0x99, 0xff}}},
    {{{   127,    -10,      0}, 0, {   454,    956}, {0x34, 0x8d, 0xf8, 0xff}}},
    {{{    97,      4,     41}, 0, {   974,    972}, {0x17, 0x43, 0x69, 0xff}}},
    {{{    97,     -5,     41}, 0, {   974,    972}, {0x36, 0xd1, 0x68, 0xff}}},
};

// 0x06011F18 - 0x06011FA0
const Gfx bub_seg6_dl_06011F18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bub_seg6_texture_0600EAA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bub_seg6_lights_0600E280.l, 1),
    gsSPLight(&bub_seg6_lights_0600E280.a, 2),
    gsSPVertex(bub_seg6_vertex_06011EA8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  3,  0, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  6,  4,  0, 0x0),
    gsSP2Triangles( 6,  5,  2, 0x0,  4,  6,  2, 0x0),
    gsSP2Triangles( 4,  2,  1, 0x0,  1,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x06011FA0 - 0x06012000
const Gfx bub_seg6_dl_06011FA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bub_seg6_dl_06011F18),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
