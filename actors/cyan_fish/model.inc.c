// Cyan Fish

// ???
UNUSED static const u64 cyan_fish_unused_1 = 0;

// 0x0600D468
ALIGNED8 static const Texture cyan_fish_seg6_texture_0600D468[] = {
#include "actors/cyan_fish/cyan_fish.rgba16.inc.c"
};

static const Lights1 cyan_fish_seg6_lights_0600DC68 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600DC80
static const Vtx cyan_fish_seg6_vertex_0600DC80[] = {
    {{{   -95,     32,      0}, 0, {    32,    436}, {0xa9, 0x5b, 0xff, 0xff}}},
    {{{   -39,     20,     28}, 0, {   184,    480}, {0xf4, 0x0a, 0x7d, 0xff}}},
    {{{    32,    112,      0}, 0, {   376,    144}, {0xe2, 0x7b, 0xff, 0xff}}},
    {{{   -39,     20,    -27}, 0, {   184,    478}, {0xf5, 0x09, 0x82, 0xff}}},
    {{{  -115,      0,      0}, 0, {   -22,    552}, {0x95, 0xbd, 0x00, 0xff}}},
    {{{    28,    -79,      8}, 0, {   366,    844}, {0xd7, 0xb9, 0x60, 0xff}}},
    {{{   161,     14,      0}, 0, {   724,    500}, {0x69, 0x46, 0x00, 0xff}}},
    {{{   161,    -13,      0}, 0, {   724,    604}, {0x69, 0xba, 0x00, 0xff}}},
    {{{    28,    -79,     -7}, 0, {   366,    844}, {0xd7, 0xba, 0xa0, 0xff}}},
    {{{    56,   -111,      0}, 0, {   442,    960}, {0xc2, 0x92, 0x00, 0xff}}},
};

// 0x0600DD20 - 0x0600DDD8
const Gfx cyan_fish_seg6_dl_0600DD20[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cyan_fish_seg6_texture_0600D468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&cyan_fish_seg6_lights_0600DC68.l, 1),
    gsSPLight(&cyan_fish_seg6_lights_0600DC68.a, 2),
    gsSPVertex(cyan_fish_seg6_vertex_0600DC80, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  4,  0,  3, 0x0),
    gsSP2Triangles( 4,  5,  1, 0x0,  1,  6,  2, 0x0),
    gsSP2Triangles( 1,  5,  7, 0x0,  1,  7,  6, 0x0),
    gsSP2Triangles( 6,  3,  2, 0x0,  4,  3,  8, 0x0),
    gsSP2Triangles( 7,  8,  3, 0x0,  6,  7,  3, 0x0),
    gsSP2Triangles( 5,  9,  7, 0x0,  7,  9,  8, 0x0),
    gsSP2Triangles( 4,  8,  5, 0x0,  5,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DDD8 - 0x0600DE38
const Gfx cyan_fish_seg6_dl_0600DDD8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(cyan_fish_seg6_dl_0600DD20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0600DE38
static const Lights1 cyan_fish_seg6_lights_0600DE38 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600DE50
static const Vtx cyan_fish_seg6_vertex_0600DE50[] = {
    {{{     9,    -26,      0}, 0, {   420,    230}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    57,    -48,      0}, 0, {   560,    260}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   120,      0,      0}, 0, {   688,     26}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,      0,      0}, 0, {   376,    144}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0600DE90 - 0x0600DED8
const Gfx cyan_fish_seg6_dl_0600DE90[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cyan_fish_seg6_texture_0600D468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&cyan_fish_seg6_lights_0600DE38.l, 1),
    gsSPLight(&cyan_fish_seg6_lights_0600DE38.a, 2),
    gsSPVertex(cyan_fish_seg6_vertex_0600DE50, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600DED8 - 0x0600DF48
const Gfx cyan_fish_seg6_dl_0600DED8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(cyan_fish_seg6_dl_0600DE90),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0600DF48
static const Lights1 cyan_fish_seg6_lights_0600DF48 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0600DF60
static const Vtx cyan_fish_seg6_vertex_0600DF60[] = {
    {{{    55,    -37,      6}, 0, {   868,    690}, {0x4e, 0xae, 0x37, 0xff}}},
    {{{    55,    -37,     -5}, 0, {   868,    690}, {0x15, 0xc0, 0x95, 0xff}}},
    {{{    88,     64,     -3}, 0, {   958,    318}, {0x64, 0x15, 0xb6, 0xff}}},
    {{{     0,    -13,      0}, 0, {   718,    604}, {0xca, 0xb5, 0x56, 0xff}}},
    {{{     0,     14,      0}, 0, {   718,    500}, {0xc7, 0x4c, 0xad, 0xff}}},
    {{{    88,     64,      4}, 0, {   958,    318}, {0x13, 0x24, 0x78, 0xff}}},
};

// 0x0600DFC0 - 0x0600E038
const Gfx cyan_fish_seg6_dl_0600DFC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cyan_fish_seg6_texture_0600D468),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&cyan_fish_seg6_lights_0600DF48.l, 1),
    gsSPLight(&cyan_fish_seg6_lights_0600DF48.a, 2),
    gsSPVertex(cyan_fish_seg6_vertex_0600DF60, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  3,  0,  5, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  0,  2,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600E038 - 0x0600E098
const Gfx cyan_fish_seg6_dl_0600E038[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(cyan_fish_seg6_dl_0600DFC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
