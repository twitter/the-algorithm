// 0x0702ACE8 - 0x0702AD00
static const Lights1 ttm_seg7_lights_0702ACE8 = gdSPDefLights1(
    0x0c, 0x0c, 0x0c,
    0x33, 0x33, 0x33, 0x28, 0x28, 0x28
);

// 0x0702AD00 - 0x0702AD18
static const Lights1 ttm_seg7_lights_0702AD00 = gdSPDefLights1(
    0x1d, 0x1d, 0x1d,
    0x77, 0x77, 0x77, 0x28, 0x28, 0x28
);

// 0x0702AD18 - 0x0702AD30
static const Lights1 ttm_seg7_lights_0702AD18 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0702AD30 - 0x0702B530
ALIGNED8 static const Texture ttm_seg7_texture_0702AD30[] = {
#include "levels/ttm/8.rgba16.inc.c"
};

// 0x0702B530 - 0x0702B570
static const Vtx ttm_seg7_vertex_0702B530[] = {
    {{{   102,     10,    102}, 0, {  4568,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,     10,   -101}, 0, {  5590,  -8720}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,     10,    102}, 0, {  4568,  -8720}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,     10,   -101}, 0, {  5590,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0702B570 - 0x0702B670
static const Vtx ttm_seg7_vertex_0702B570[] = {
    {{{  -101,      0,   -101}, 0, {  5590,  -8720}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,    205,   -101}, 0, {  5590,  -7698}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  5590,  -8720}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,   -101}, 0, {  5590,  -7698}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,      0,   -101}, 0, {  5590,  -8720}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -101,    205,    102}, 0, {  4568,  -8720}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -101,      0,    102}, 0, {  4568,  -8720}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  5590,  -8720}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -101,    205,    102}, 0, {  4568,  -8720}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,      0,    102}, 0, {  4568,  -7698}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,      0,    102}, 0, {  4568,  -8720}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,    205,    102}, 0, {  4568,  -7698}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,      0,    102}, 0, {  4568,  -7698}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   102,    205,    102}, 0, {  4568,  -7698}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   102,    205,   -101}, 0, {  5590,  -7698}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   102,      0,   -101}, 0, {  5590,  -7698}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x0702B670 - 0x0702B770
static const Vtx ttm_seg7_vertex_0702B670[] = {
    {{{  -204,      0,   -204}, 0, {  6100,  -9230}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    307,   -204}, 0, {  6100,  -9230}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  1024,      0,   -204}, 0, {  6100,  -3098}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    307,    205}, 0, {  4056,  -9230}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -204,      0,    205}, 0, {  4056,  -9230}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  1024,      0,    205}, 0, {  4056,  -3098}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -204,    307,   -204}, 0, {  6100,  -9230}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{  -204,    307,    205}, 0, {  4056,  -9230}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{  -101,    205,    102}, 0, {  4568,  -8720}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  5590,  -8720}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{  -204,    307,   -204}, 0, {  6100,  -9230}, {0x18, 0x64, 0x4a, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  5590,  -8720}, {0x18, 0x64, 0x4a, 0xff}}},
    {{{   205,    205,   -204}, 0, {  6100,  -7186}, {0x18, 0x64, 0x4a, 0xff}}},
    {{{   205,    205,   -204}, 0, {  6100,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,    205,   -101}, 0, {  5590,  -8720}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,    205,   -101}, 0, {  5590,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0702B770 - 0x0702B870
static const Vtx ttm_seg7_vertex_0702B770[] = {
    {{{  -204,    102,    614}, 0, {  2012,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    102,    614}, 0, {  2012,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    102,    205}, 0, {  4056,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,    102,    205}, 0, {  4056,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,    205}, 0, {  4056,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,    102,    205}, 0, {  4056,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,    102,    614}, 0, {  2012,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,      0,    614}, 0, {  2012,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -204,      0,    614}, 0, {  2012,  -9230}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,    102,    614}, 0, {  2012,  -7186}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -204,    102,    614}, 0, {  2012,  -9230}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,      0,    614}, 0, {  2012,  -7186}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -204,    102,   -613}, 0, {  8144,  -9230}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   205,    102,   -613}, 0, {  8144,  -7186}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   205,      0,   -613}, 0, {  8144,  -7186}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,      0,   -613}, 0, {  8144,  -9230}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0702B870 - 0x0702B970
static const Vtx ttm_seg7_vertex_0702B870[] = {
    {{{   102,    205,   -101}, 0, {  5590,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    205,    205}, 0, {  4056,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    205,   -204}, 0, {  6100,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,   -613}, 0, {  8144,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,    102,   -613}, 0, {  8144,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,    102,   -204}, 0, {  6100,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   205,      0,   -204}, 0, {  6100,  -7186}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -204,    102,   -204}, 0, {  6100,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    102,   -204}, 0, {  6100,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    102,   -613}, 0, {  8144,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,    102,   -613}, 0, {  8144,  -9230}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1024,      0,   -204}, 0, {  6100,  -3098}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{   205,    205,   -204}, 0, {  6100,  -7186}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{   205,    205,    205}, 0, {  4056,  -7186}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{  1024,      0,    205}, 0, {  4056,  -3098}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{   102,    205,    102}, 0, {  4568,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0702B970 - 0x0702B9D0
static const Vtx ttm_seg7_vertex_0702B970[] = {
    {{{   205,    205,    205}, 0, {  4056,  -7186}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,    205,    102}, 0, {  4568,  -7698}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,    205,    102}, 0, {  4568,  -8720}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    205,    205}, 0, {  4056,  -7186}, {0x18, 0x64, 0xb6, 0xff}}},
    {{{  -101,    205,    102}, 0, {  4568,  -8720}, {0x18, 0x64, 0xb6, 0xff}}},
    {{{  -204,    307,    205}, 0, {  4056,  -9230}, {0x18, 0x64, 0xb6, 0xff}}},
};

// 0x0702B9D0 - 0x0702BB60
static const Gfx ttm_seg7_dl_0702B9D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ttm_seg7_texture_0702AD30),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttm_seg7_lights_0702ACE8.l, 1),
    gsSPLight(&ttm_seg7_lights_0702ACE8.a, 2),
    gsSPVertex(ttm_seg7_vertex_0702B530, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPLight(&ttm_seg7_lights_0702AD00.l, 1),
    gsSPLight(&ttm_seg7_lights_0702AD00.a, 2),
    gsSPVertex(ttm_seg7_vertex_0702B570, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPLight(&ttm_seg7_lights_0702AD18.l, 1),
    gsSPLight(&ttm_seg7_lights_0702AD18.a, 2),
    gsSPVertex(ttm_seg7_vertex_0702B670, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(ttm_seg7_vertex_0702B770, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(ttm_seg7_vertex_0702B870, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPVertex(ttm_seg7_vertex_0702B970, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0702BB60 - 0x0702BBD0
const Gfx ttm_seg7_dl_0702BB60[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0702B9D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
