// Exclamation Box

// 0x08012E10
static const Lights1 exclamation_box_seg8_lights_08012E10 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x08012E28
ALIGNED8 static const Texture exclamation_box_seg8_texture_08012E28[] = {
#include "actors/exclamation_box/vanish_cap_box_front.rgba16.inc.c"
};

// 0x08013628
ALIGNED8 static const Texture exclamation_box_seg8_texture_08013628[] = {
#include "actors/exclamation_box/vanish_cap_box_side.rgba16.inc.c"
};

// 0x08014628
ALIGNED8 static const Texture exclamation_box_seg8_texture_08014628[] = {
#include "actors/exclamation_box/metal_cap_box_front.rgba16.inc.c"
};

// 0x08014E28
ALIGNED8 static const Texture exclamation_box_seg8_texture_08014E28[] = {
#include "actors/exclamation_box/metal_cap_box_side.rgba16.inc.c"
};

// 0x08015E28
ALIGNED8 static const Texture exclamation_box_seg8_texture_08015E28[] = {
#include "actors/exclamation_box/wing_cap_box_front.rgba16.inc.c"
};

// 0x08016628
ALIGNED8 static const Texture exclamation_box_seg8_texture_08016628[] = {
#include "actors/exclamation_box/wing_cap_box_side.rgba16.inc.c"
};

// 0x08017628
ALIGNED8 static const Texture exclamation_box_seg8_texture_08017628[] = {
#include "actors/exclamation_box/exclamation_box_front.rgba16.inc.c"
};

// 0x08017E28
ALIGNED8 static const Texture exclamation_box_seg8_texture_08017E28[] = {
#include "actors/exclamation_box/exclamation_box_side.rgba16.inc.c"
};

// 0x08018E28
static const Vtx exclamation_box_seg8_vertex_08018E28[] = {
    {{{   -25,      1,    -25}, 0, {   992,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -25,     52,    -25}, 0, {   992,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    26,     52,    -25}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -25,      1,     26}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    26,      1,     26}, 0, {   992,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    26,     52,     26}, 0, {   992,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -25,     52,     26}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    26,     52,    -25}, 0, {   992,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,      1,     26}, 0, {     0,    992}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,      1,    -25}, 0, {   992,    992}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,     52,     26}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -25,      1,    -25}, 0, {     0,    992}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -25,      1,     26}, 0, {   992,    992}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -25,     52,     26}, 0, {   992,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -25,     52,    -25}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    26,      1,    -25}, 0, {     0,    992}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x08018F28
static const Vtx exclamation_box_seg8_vertex_08018F28[] = {
    {{{    26,      1,    -25}, 0, {   992,   2014}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    26,      1,     26}, 0, {   992,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -25,      1,     26}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -25,      1,    -25}, 0, {     0,   2014}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -25,     52,    -25}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -25,     52,     26}, 0, {     0,   2014}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    26,     52,     26}, 0, {   992,   2014}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    26,     52,    -25}, 0, {   992,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x08018FA8 - 0x08019008
const Gfx exclamation_box_seg8_dl_08018FA8[] = {
    gsSPLight(&exclamation_box_seg8_lights_08012E10.l, 1),
    gsSPLight(&exclamation_box_seg8_lights_08012E10.a, 2),
    gsSPVertex(exclamation_box_seg8_vertex_08018E28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x08019008 - 0x08019058
const Gfx exclamation_box_seg8_dl_08019008[] = {
    gsSPVertex(exclamation_box_seg8_vertex_08018F28, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x08019058 - 0x080190A0
const Gfx exclamation_box_seg8_dl_08019058[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x080190A0
static const Vtx exclamation_box_seg8_vertex_080190A0[] = {
    {{{   -25,      1,     26}, 0, {   992,    992}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -25,     52,     26}, 0, {   992,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -25,     52,    -25}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    26,      1,     26}, 0, {   992,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    26,     52,     26}, 0, {   992,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -25,     52,     26}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -25,      1,     26}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    26,      1,    -25}, 0, {   992,    992}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,     52,    -25}, 0, {   992,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,     52,     26}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    26,      1,     26}, 0, {     0,    992}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -25,      1,    -25}, 0, {   992,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    26,     52,    -25}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    26,      1,    -25}, 0, {     0,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -25,     52,    -25}, 0, {   992,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -25,      1,    -25}, 0, {     0,    992}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x080191A0
static const Vtx exclamation_box_seg8_vertex_080191A0[] = {
    {{{   -25,     52,     26}, 0, {     0,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    26,     52,     26}, 0, {  2014,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    26,     52,    -25}, 0, {  2014,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -25,     52,    -25}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -25,      1,     26}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -25,      1,    -25}, 0, {     0,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    26,      1,    -25}, 0, {  2014,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    26,      1,     26}, 0, {  2014,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x08019220 - 0x08019280
const Gfx exclamation_box_seg8_dl_08019220[] = {
    gsSPLight(&exclamation_box_seg8_lights_08012E10.l, 1),
    gsSPLight(&exclamation_box_seg8_lights_08012E10.a, 2),
    gsSPVertex(exclamation_box_seg8_vertex_080190A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x08019280 - 0x080192D0
const Gfx exclamation_box_seg8_dl_08019280[] = {
    gsSPVertex(exclamation_box_seg8_vertex_080191A0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x080192D0 - 0x08019318
const Gfx exclamation_box_seg8_dl_080192D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x08019318 - 0x08019378
const Gfx exclamation_box_seg8_dl_08019318[] = {
    gsSPDisplayList(exclamation_box_seg8_dl_08019058),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08015E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(exclamation_box_seg8_dl_08018FA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08016628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(exclamation_box_seg8_dl_08019008),
};

// 0x08019378 - 0x080193D8
const Gfx exclamation_box_seg8_dl_08019378[] = {
    gsSPDisplayList(exclamation_box_seg8_dl_080192D0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08014628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(exclamation_box_seg8_dl_08019220),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08014E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(exclamation_box_seg8_dl_08019280),
};

// 0x080193D8 - 0x08019438
const Gfx exclamation_box_seg8_dl_080193D8[] = {
    gsSPDisplayList(exclamation_box_seg8_dl_08019058),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08012E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(exclamation_box_seg8_dl_08018FA8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08013628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(exclamation_box_seg8_dl_08019008),
};

// 0x08019438 - 0x08019498
const Gfx exclamation_box_seg8_dl_08019438[] = {
    gsSPDisplayList(exclamation_box_seg8_dl_080192D0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08017628),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(exclamation_box_seg8_dl_08019220),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, exclamation_box_seg8_texture_08017E28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPBranchList(exclamation_box_seg8_dl_08019280),
};
