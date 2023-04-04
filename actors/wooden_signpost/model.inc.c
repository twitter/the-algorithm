// Signpost

// 0x0302C940
static const Lights1 wooden_signpost_seg3_lights_0302C940 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0302C958
static const Vtx wooden_signpost_seg3_vertex_0302C958[] = {
    {{{     0,    504,     19}, 0, {   962,    478}, {0x00, 0x7e, 0xfc, 0xff}}},
    {{{    24,    490,     34}, 0, {   938,    780}, {0x62, 0x3b, 0x36, 0xff}}},
    {{{     0,    490,     -6}, 0, {   938,    478}, {0xff, 0x39, 0x90, 0xff}}},
    {{{   -23,    490,     34}, 0, {   938,    178}, {0x9e, 0x3a, 0x36, 0xff}}},
    {{{     0,    -39,     34}, 0, {    36,    478}, {0x00, 0xfc, 0x7e, 0xff}}},
    {{{   -23,    -39,     -6}, 0, {    36,    178}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{    24,    -39,     -6}, 0, {    36,    780}, {0x6d, 0x00, 0xc0, 0xff}}},
};

// 0x0302C9C8
ALIGNED8 static const Texture wooden_signpost_seg3_texture_0302C9C8[] = {
#include "actors/wooden_signpost/wooden_signpost_back.rgba16.inc.c"
};

// 0x0302D1C8
ALIGNED8 static const Texture wooden_signpost_seg3_texture_0302D1C8[] = {
#include "actors/wooden_signpost/wooden_signpost_front.rgba16.inc.c"
};

// 0x0302D9C8 - 0x0302DA48
const Gfx wooden_signpost_seg3_dl_0302D9C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wooden_signpost_seg3_texture_0302C9C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wooden_signpost_seg3_lights_0302C940.l, 1),
    gsSPLight(&wooden_signpost_seg3_lights_0302C940.a, 2),
    gsSPVertex(wooden_signpost_seg3_vertex_0302C958, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  4,  1,  3, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  5,  3,  2, 0x0),
    gsSP2Triangles( 2,  6,  5, 0x0,  1,  4,  6, 0x0),
    gsSP1Triangle( 6,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0302DA48 - 0x0302DAA8
const Gfx wooden_signpost_seg3_dl_0302DA48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wooden_signpost_seg3_dl_0302D9C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0302DAA8
static const Lights1 wooden_signpost_seg3_lights_0302DAA8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0302DAC0
static const Vtx wooden_signpost_seg3_vertex_0302DAC0[] = {
    {{{   180,    260,     20}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -179,    260,     60}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -179,    260,     20}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   180,    260,     20}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -179,    260,     20}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -179,    460,     20}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -179,    260,     20}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -179,    260,     60}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -179,    460,     60}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -179,    460,     20}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   180,    260,     60}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -179,    460,     20}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -179,    460,     60}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   180,    460,     60}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   180,    460,     20}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   180,    460,     20}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0302DBC0
static const Vtx wooden_signpost_seg3_vertex_0302DBC0[] = {
    {{{   180,    260,     60}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,    460,     20}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,    460,     60}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   180,    260,     20}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0302DC00
static const Vtx wooden_signpost_seg3_vertex_0302DC00[] = {
    {{{  -179,    260,     60}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   180,    460,     60}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -179,    460,     60}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   180,    260,     60}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0302DC40 - 0x0302DCD0
const Gfx wooden_signpost_seg3_dl_0302DC40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wooden_signpost_seg3_texture_0302C9C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wooden_signpost_seg3_lights_0302DAA8.l, 1),
    gsSPLight(&wooden_signpost_seg3_lights_0302DAA8.a, 2),
    gsSPVertex(wooden_signpost_seg3_vertex_0302DAC0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles( 0, 10,  1, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles( 3,  5, 14, 0x0, 11, 13, 15, 0x0),
    gsSPVertex(wooden_signpost_seg3_vertex_0302DBC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0302DCD0 - 0x0302DD08
const Gfx wooden_signpost_seg3_dl_0302DCD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wooden_signpost_seg3_texture_0302D1C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wooden_signpost_seg3_vertex_0302DC00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0302DD08 - 0x0302DD80
const Gfx wooden_signpost_seg3_dl_0302DD08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wooden_signpost_seg3_dl_0302DC40),
    gsSPDisplayList(wooden_signpost_seg3_dl_0302DCD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
