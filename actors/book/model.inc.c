// Book (Pushable Book)

// This actor file requires including actor bookend because of bookend_seg5_texture_05000C60

// 0x05002558
static const Lights1 book_seg5_lights_05002558 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05002570
ALIGNED8 static const Texture book_seg5_texture_05002570[] = {
#include "actors/book/book_cover.rgba16.inc.c"
};

// 0x05002D70
static const Vtx book_seg5_vertex_05002D70[] = {
    {{{    -9,     31,    -50}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    10,     31,      0}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    10,     31,    -50}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -9,     31,      0}, 0, {   479,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    10,    -30,    -50}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    10,    -30,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -9,    -30,      0}, 0, {   479,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -9,    -30,    -50}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    10,    -30,    -50}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -9,     31,    -50}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    10,     31,    -50}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -9,    -30,    -50}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x05002E30
static const Vtx book_seg5_vertex_05002E30[] = {
    {{{    10,     31,      0}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -9,     31,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -9,    -30,      0}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    10,    -30,      0}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -9,    -30,    -50}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -9,     31,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -9,     31,    -50}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -9,    -30,      0}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    10,     31,    -50}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    10,     31,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    10,    -30,      0}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    10,    -30,    -50}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x05002EF0 - 0x05002F58
const Gfx book_seg5_dl_05002EF0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bookend_seg5_texture_05000C60),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&book_seg5_lights_05002558.l, 1),
    gsSPLight(&book_seg5_lights_05002558.a, 2),
    gsSPVertex(book_seg5_vertex_05002D70, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x05002F58 - 0x05002FB0
const Gfx book_seg5_dl_05002F58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, book_seg5_texture_05002570),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(book_seg5_vertex_05002E30, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05002FB0 - 0x05003040
const Gfx book_seg5_dl_05002FB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(book_seg5_dl_05002EF0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(book_seg5_dl_05002F58),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
