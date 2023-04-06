// 0x0700BF90 - 0x0700BFA8
static const Lights1 ssl_seg7_lights_0700BF90 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700BFA8 - 0x0700C7A8
ALIGNED8 static const u8 ssl_seg7_texture_0700BFA8[] = {
#include "levels/ssl/8.rgba16.inc.c"
};

// 0x0700C7A8 - 0x0700D7A8
ALIGNED8 static const u8 ssl_seg7_texture_0700C7A8[] = {
#include "levels/ssl/9.rgba16.inc.c"
};

// 0x0700D7A8 - 0x0700E7A8
ALIGNED8 static const u8 ssl_seg7_texture_0700D7A8[] = {
#include "levels/ssl/10.rgba16.inc.c"
};

// 0x0700E7A8 - 0x0700F7A8
ALIGNED8 static const u8 ssl_seg7_texture_0700E7A8[] = {
#include "levels/ssl/11.rgba16.inc.c"
};

// 0x0700F7A8 - 0x0700F898
static const Vtx ssl_seg7_vertex_0700F7A8[] = {
    {{{  -153,   -255,   -153}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,   -255,   -153}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,    154,   -153}, 0, {   990,    -86}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,    154,    154}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -153,    154,   -153}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   154,    154,   -153}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   154,    154,    154}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -153,    154,    154}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -153,   -255,    154}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -153,   -255,   -153}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -153,    154,   -153}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   154,    154,   -153}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,   -255,    154}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,    154,    154}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,   -255,   -153}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x0700F898 - 0x0700F908
static const Vtx ssl_seg7_vertex_0700F898[] = {
    {{{   154,   -255,    154}, 0, {   -26,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -153,    154,    154}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   154,    154,    154}, 0, {   -26,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -153,   -255,   -153}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,    154,   -153}, 0, {   990,    -86}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,    154,   -153}, 0, {     0,    -86}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,   -255,    154}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700F908 - 0x0700FA08
static const Vtx ssl_seg7_vertex_0700F908[] = {
    {{{   256,   -255,   -255}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,    256,   -255}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,    256,    256}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -255,   -255,    256}, 0, {   990,   2012}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,    256,   -255}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,   -255,   -255}, 0, {     0,   2012}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,    256,    256}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,   -255,    154}, 0, {   172,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   154,   -255,   -153}, 0, {   786,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   -255,   -255}, 0, {   990,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   -255,    256}, 0, {     0,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,   -255,    256}, 0, {     0,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,   -255,   -255}, 0, {     0,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -153,   -255,    154}, 0, {   786,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,   -255,    256}, 0, {   990,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -153,   -255,   -153}, 0, {   172,   2012}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0700FA08 - 0x0700FB08
static const Vtx ssl_seg7_vertex_0700FA08[] = {
    {{{  -255,   -255,   -255}, 0, {   990,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -255,    256,   -255}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,    256,   -255}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,   -255,   -255}, 0, {     0,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -255,   -255,   -255}, 0, {   990,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   -255,   -255}, 0, {     0,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   154,   -255,   -153}, 0, {   172,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -153,   -255,   -153}, 0, {   786,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   -255,    256}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,    256,    256}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,    256,    256}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,   -255,    256}, 0, {     0,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   154,   -255,    154}, 0, {   786,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,   -255,    256}, 0, {   990,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,   -255,    256}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,   -255,    154}, 0, {   172,   2012}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0700FB08 - 0x0700FB48
static const Vtx ssl_seg7_vertex_0700FB08[] = {
    {{{  -255,    256,    256}, 0, {     0,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,    256,    256}, 0, {   990,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,    256,   -255}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,    256,   -255}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700FB48 - 0x0700FBD8
static const Gfx ssl_seg7_dl_0700FB48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_0700BFA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ssl_seg7_lights_0700BF90.l, 1),
    gsSPLight(&ssl_seg7_lights_0700BF90.a, 2),
    gsSPVertex(ssl_seg7_vertex_0700F7A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(ssl_seg7_vertex_0700F898, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FBD8 - 0x0700FC40
static const Gfx ssl_seg7_dl_0700FBD8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_0700C7A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_0700F908, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 0,  2, 10, 0x0,  7,  9, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FC40 - 0x0700FCA8
static const Gfx ssl_seg7_dl_0700FC40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_0700E7A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_0700FA08, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles( 8, 10, 14, 0x0, 11, 15, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FCA8 - 0x0700FCE0
static const Gfx ssl_seg7_dl_0700FCA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_0700D7A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_0700FB08, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700FCE0 - 0x0700FD80
const Gfx ssl_seg7_dl_0700FCE0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_0700FB48),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_0700FBD8),
    gsSPDisplayList(ssl_seg7_dl_0700FC40),
    gsSPDisplayList(ssl_seg7_dl_0700FCA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
