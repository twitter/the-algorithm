// Tornado

// 0x05013128
ALIGNED8 static const Texture tornado_seg5_texture_05013128[] = {
#include "actors/tornado/tornado.ia16.inc.c"
};

// 0x05014128
static const Lights1 tornado_seg5_lights_05014128 = gdSPDefLights1(
    0x3b, 0x34, 0x23,
    0xee, 0xd0, 0x8d, 0x28, 0x28, 0x28
);

// 0x05014140
static const Vtx tornado_seg5_vertex_05014140[] = {
    {{{   474,    661,    822}, 0, {   650,   1708}, {0x30, 0xbe, 0x60, 0x64}}},
    {{{     0,    661,    949}, 0, {   480,   1708}, {0x00, 0xb6, 0x67, 0x64}}},
    {{{     0,      0,      0}, 0, {   564,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{   822,    661,    474}, 0, {   820,   1708}, {0x60, 0xc3, 0x37, 0x64}}},
    {{{     0,      0,      0}, 0, {   734,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{   969,   4107,   1679}, 0, {   650,    128}, {0x46, 0xde, 0x63, 0x64}}},
    {{{     0,   4107,   1938}, 0, {   480,    128}, {0x00, 0xdd, 0x7a, 0x64}}},
    {{{  1679,   4107,    969}, 0, {   820,    128}, {0x69, 0xdd, 0x3d, 0x64}}},
    {{{  -474,    661,    822}, 0, {   308,   1708}, {0xc9, 0xc3, 0x60, 0x64}}},
    {{{  -969,   4107,   1679}, 0, {   308,    128}, {0xc3, 0xdd, 0x69, 0x64}}},
    {{{     0,      0,      0}, 0, {   394,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{   949,    661,      0}, 0, {   990,   1708}, {0x67, 0xb6, 0x00, 0x64}}},
    {{{     0,      0,      0}, 0, {   904,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{  1938,   4107,      0}, 0, {   990,    128}, {0x7a, 0xdd, 0x00, 0x64}}},
};

// 0x05014220
static const Vtx tornado_seg5_vertex_05014220[] = {
    {{{  -474,    661,   -822}, 0, {  1672,   1708}, {0xd0, 0xbe, 0xa0, 0x64}}},
    {{{  -969,   4107,  -1678}, 0, {  1672,    128}, {0xba, 0xde, 0x9d, 0x64}}},
    {{{     0,   4107,  -1938}, 0, {  1502,    128}, {0x00, 0xdd, 0x86, 0x64}}},
    {{{   822,    661,   -474}, 0, {  1160,   1708}, {0x60, 0xbe, 0xd0, 0x64}}},
    {{{  1938,   4107,      0}, 0, {   990,    128}, {0x7a, 0xdd, 0x00, 0x64}}},
    {{{   949,    661,      0}, 0, {   990,   1708}, {0x67, 0xb6, 0x00, 0x64}}},
    {{{  1679,   4107,   -969}, 0, {  1160,    128}, {0x63, 0xde, 0xba, 0x64}}},
    {{{     0,      0,      0}, 0, {  1076,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{   474,    661,   -822}, 0, {  1330,   1708}, {0x37, 0xc3, 0xa0, 0x64}}},
    {{{     0,      0,      0}, 0, {  1246,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{   969,   4107,  -1678}, 0, {  1330,    128}, {0x3d, 0xdd, 0x97, 0x64}}},
    {{{     0,    661,   -949}, 0, {  1502,   1708}, {0x00, 0xb6, 0x99, 0x64}}},
    {{{     0,      0,      0}, 0, {  1416,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{  -474,    661,    822}, 0, {   308,   1708}, {0xc9, 0xc3, 0x60, 0x64}}},
    {{{  -822,    661,    474}, 0, {   138,   1708}, {0xa0, 0xbe, 0x30, 0x64}}},
    {{{     0,      0,      0}, 0, {   224,   2012}, {0x00, 0x82, 0x00, 0x64}}},
};

// 0x05014320
static const Vtx tornado_seg5_vertex_05014320[] = {
    {{{  -474,    661,   -822}, 0, {  1672,   1708}, {0xd0, 0xbe, 0xa0, 0x64}}},
    {{{     0,    661,   -949}, 0, {  1502,   1708}, {0x00, 0xb6, 0x99, 0x64}}},
    {{{     0,      0,      0}, 0, {  1586,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{  -822,    661,   -474}, 0, {  1842,   1708}, {0xa0, 0xc3, 0xc9, 0x64}}},
    {{{     0,      0,      0}, 0, {  1756,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{  -969,   4107,  -1678}, 0, {  1672,    128}, {0xba, 0xde, 0x9d, 0x64}}},
    {{{ -1678,   4107,   -969}, 0, {  1842,    128}, {0x97, 0xdd, 0xc3, 0x64}}},
    {{{  -949,    661,      0}, 0, {  2012,   1708}, {0x99, 0xb6, 0x00, 0x64}}},
    {{{     0,      0,      0}, 0, {  1926,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{ -1938,   4107,      0}, 0, {  2012,    128}, {0x86, 0xdd, 0x00, 0x64}}},
    {{{  -822,    661,    474}, 0, {   138,   1708}, {0xa0, 0xbe, 0x30, 0x64}}},
    {{{  -949,    661,      0}, 0, {     0,   1708}, {0x99, 0xb6, 0x00, 0x64}}},
    {{{     0,      0,      0}, 0, {    54,   2012}, {0x00, 0x82, 0x00, 0x64}}},
    {{{ -1938,   4107,      0}, 0, {     0,    128}, {0x86, 0xdd, 0x00, 0x64}}},
};

// 0x05014400
static const Vtx tornado_seg5_vertex_05014400[] = {
    {{{  -474,    661,    822}, 0, {   308,   1708}, {0xc9, 0xc3, 0x60, 0x64}}},
    {{{  -969,   4107,   1679}, 0, {   308,    128}, {0xc3, 0xdd, 0x69, 0x64}}},
    {{{ -1678,   4107,    969}, 0, {   138,    128}, {0x9d, 0xde, 0x46, 0x64}}},
    {{{  -822,    661,    474}, 0, {   138,   1708}, {0xa0, 0xbe, 0x30, 0x64}}},
    {{{ -1938,   4107,      0}, 0, {     0,    128}, {0x86, 0xdd, 0x00, 0x64}}},
};

// 0x05014450 - 0x050145C0
const Gfx tornado_seg5_dl_05014450[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, tornado_seg5_texture_05013128),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&tornado_seg5_lights_05014128.l, 1),
    gsSPLight(&tornado_seg5_lights_05014128.a, 2),
    gsSPVertex(tornado_seg5_vertex_05014140, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  5,  6, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 3,  5,  0, 0x0,  3,  7,  5, 0x0),
    gsSP2Triangles( 1,  6,  8, 0x0,  6,  9,  8, 0x0),
    gsSP2Triangles( 1,  8, 10, 0x0, 11,  3, 12, 0x0),
    gsSP2Triangles(11, 13,  3, 0x0, 13,  7,  3, 0x0),
    gsSPVertex(tornado_seg5_vertex_05014220, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  3,  5,  7, 0x0),
    gsSP2Triangles( 8,  3,  9, 0x0,  8,  6,  3, 0x0),
    gsSP2Triangles( 8, 10,  6, 0x0, 11,  8, 12, 0x0),
    gsSP2Triangles( 2, 10,  8, 0x0, 11,  2,  8, 0x0),
    gsSP2Triangles( 0,  2, 11, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(tornado_seg5_vertex_05014320, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 3,  5,  0, 0x0,  3,  6,  5, 0x0),
    gsSP2Triangles( 7,  3,  8, 0x0,  9,  6,  3, 0x0),
    gsSP2Triangles( 7,  9,  3, 0x0, 10, 11, 12, 0x0),
    gsSP1Triangle(10, 13, 11, 0x0),
    gsSPVertex(tornado_seg5_vertex_05014400, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP1Triangle( 3,  2,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x050145C0 - 0x05014630
const Gfx tornado_seg5_dl_050145C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(tornado_seg5_dl_05014450),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};
