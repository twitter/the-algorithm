// Door

// 0x03009CE0
static const Lights1 door_seg3_lights_03009CE0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x03009CF8
static const Lights1 door_seg3_lights_03009CF8 = gdSPDefLights1(
    0x3f, 0x3f, 0x00,
    0xff, 0xff, 0x00, 0x28, 0x28, 0x28
);

// 0x03009D10
ALIGNED8 static const Texture door_seg3_texture_03009D10[] = {
#include "actors/door/polished_wooden_door.rgba16.inc.c"
};

// 0x0300AD10
ALIGNED8 static const Texture door_seg3_texture_0300AD10[] = {
#include "actors/door/polished_wooden_door_overlay.rgba16.inc.c"
};

// 0x0300BD10
ALIGNED8 static const Texture door_seg3_texture_0300BD10[] = {
#include "actors/door/rough_wooden_door.rgba16.inc.c"
};

// 0x0300CD10
ALIGNED8 static const Texture door_seg3_texture_0300CD10[] = {
#include "actors/door/rough_wooden_door_overlay.rgba16.inc.c"
};

// 0x0300D510
ALIGNED8 static const Texture door_seg3_texture_0300D510[] = {
#include "actors/door/metal_door.rgba16.inc.c"
};

// 0x0300E510
ALIGNED8 static const Texture door_seg3_texture_0300E510[] = {
#include "actors/door/metal_door_overlay.rgba16.inc.c"
};

// 0x0300ED10
ALIGNED8 static const Texture door_seg3_texture_0300ED10[] = {
#include "actors/door/hmc_mural_door.rgba16.inc.c"
};

// 0x0300FD10
ALIGNED8 static const Texture door_seg3_texture_0300FD10[] = {
#include "actors/door/hmc_mural_door_overlay.rgba16.inc.c"
};

// 0x03010510
ALIGNED8 static const Texture door_seg3_texture_03010510[] = {
#include "actors/door/bbh_door.rgba16.inc.c"
};

// 0x03011510
ALIGNED8 static const Texture door_seg3_texture_03011510[] = {
#include "actors/door/bbh_door_overlay.rgba16.inc.c"
};

// 0x03011D10
ALIGNED8 static const Texture door_seg3_texture_03011D10[] = {
#include "actors/door/zero_star_door_sign.rgba16.inc.c"
};

// 0x03012510
ALIGNED8 static const Texture door_seg3_texture_03012510[] = {
#include "actors/door/one_star_door_sign.rgba16.inc.c"
};

// 0x03012D10
ALIGNED8 static const Texture door_seg3_texture_03012D10[] = {
#include "actors/door/three_star_door_sign.rgba16.inc.c"
};

// 0x03013510
ALIGNED8 static const Texture door_seg3_texture_03013510[] = {
#include "actors/door/door_lock.rgba16.inc.c"
};

// 0x03013910
static const Vtx door_seg3_vertex_03013910[] = {
    {{{   -12,      0,    -58}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -12,      0,     59}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -12,   1024,     59}, 0, {   990,   2012}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -12,   1024,    -58}, 0, {   990,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -12,   1024,     59}, 0, {     0,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   604,   1024,     59}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   604,   1024,    -58}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   604,      0,    -58}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -12,      0,     59}, 0, {   990,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -12,      0,    -58}, 0, {     0,   2012}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   604,      0,     59}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   604,      0,     59}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   604,      0,    -58}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   604,   1024,    -58}, 0, {   990,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   604,   1024,     59}, 0, {     0,   2012}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -12,   1024,    -58}, 0, {     0,   2012}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x03013A10
static const Vtx door_seg3_vertex_03013A10[] = {
    {{{   604,      0,    -58}, 0, {   974,   1820}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -12,      0,    -58}, 0, {     0,   1816}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -12,   1024,    -58}, 0, {     0,    124}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   604,   1024,    -58}, 0, {   974,    128}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -12,      0,     59}, 0, {     0,   1816}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   604,      0,     59}, 0, {   974,   1812}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   604,   1024,     59}, 0, {   974,    132}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -12,   1024,     59}, 0, {     0,    140}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x03013A90
static const Vtx door_seg3_vertex_03013A90[] = {
    {{{   512,    512,    104}, 0, {     0,      0}, {0xff, 0x7e, 0x01, 0xff}}},
    {{{   456,    432,    160}, 0, {     0,      0}, {0xa6, 0x1f, 0x53, 0xff}}},
    {{{   512,    488,    160}, 0, {     0,      0}, {0x16, 0x49, 0x65, 0xff}}},
    {{{   512,    372,    160}, 0, {     0,      0}, {0xe7, 0xba, 0x66, 0xff}}},
    {{{   572,    432,    160}, 0, {     0,      0}, {0x5a, 0xe3, 0x53, 0xff}}},
    {{{   596,    432,    104}, 0, {     0,      0}, {0x7e, 0x01, 0x01, 0xff}}},
    {{{   512,    348,    104}, 0, {     0,      0}, {0xff, 0x82, 0x01, 0xff}}},
    {{{   432,    432,    104}, 0, {     0,      0}, {0x82, 0x01, 0x01, 0xff}}},
    {{{   512,    488,     44}, 0, {     0,      0}, {0xda, 0x70, 0xd4, 0xff}}},
    {{{   456,    432,     44}, 0, {     0,      0}, {0x8f, 0xde, 0xd4, 0xff}}},
    {{{   512,    372,     44}, 0, {     0,      0}, {0x24, 0x90, 0xd3, 0xff}}},
    {{{   572,    432,     44}, 0, {     0,      0}, {0x6f, 0x28, 0xd3, 0xff}}},
};

// 0x03013B50
static const Vtx door_seg3_vertex_03013B50[] = {
    {{{   512,    488,   -156}, 0, {     0,      0}, {0xe8, 0x48, 0x9b, 0xff}}},
    {{{   512,    372,   -156}, 0, {     0,      0}, {0x17, 0xb9, 0x9a, 0xff}}},
    {{{   456,    432,   -156}, 0, {     0,      0}, {0xa5, 0xe4, 0xae, 0xff}}},
    {{{   432,    432,   -100}, 0, {     0,      0}, {0x82, 0x01, 0xff, 0xff}}},
    {{{   512,    512,   -100}, 0, {     0,      0}, {0xff, 0x7e, 0xff, 0xff}}},
    {{{   572,    432,   -156}, 0, {     0,      0}, {0x59, 0x20, 0xac, 0xff}}},
    {{{   512,    348,   -100}, 0, {     0,      0}, {0xff, 0x82, 0xff, 0xff}}},
    {{{   596,    432,   -100}, 0, {     0,      0}, {0x7e, 0x01, 0xff, 0xff}}},
    {{{   572,    432,    -40}, 0, {     0,      0}, {0x70, 0xdc, 0x2d, 0xff}}},
    {{{   512,    372,    -40}, 0, {     0,      0}, {0xd8, 0x91, 0x2d, 0xff}}},
    {{{   456,    432,    -40}, 0, {     0,      0}, {0x90, 0x26, 0x2c, 0xff}}},
    {{{   512,    488,    -40}, 0, {     0,      0}, {0x22, 0x71, 0x2c, 0xff}}},
};

// 0x03013C10 - 0x03013CC8
const Gfx door_seg3_dl_03013C10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300AD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPVertex(door_seg3_vertex_03013910, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03009D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03013A10, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x03013CC8 - 0x03013D78
const Gfx door_seg3_dl_03013CC8[] = {
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPVertex(door_seg3_vertex_03013A90, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 5,  0,  2, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 7,  6,  3, 0x0,  7,  3,  1, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0,  9,  6,  7, 0x0),
    gsSP2Triangles( 8,  7,  0, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(10,  5,  6, 0x0, 10, 11,  5, 0x0),
    gsSP2Triangles(11,  0,  5, 0x0, 11,  8,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x03013D78 - 0x03013E28
const Gfx door_seg3_dl_03013D78[] = {
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPVertex(door_seg3_vertex_03013B50, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 5,  0,  4, 0x0,  2,  1,  6, 0x0),
    gsSP2Triangles( 1,  7,  6, 0x0,  1,  5,  7, 0x0),
    gsSP2Triangles( 2,  6,  3, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  3,  6,  9, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  4,  3, 10, 0x0),
    gsSP2Triangles( 3,  9, 10, 0x0,  7,  4, 11, 0x0),
    gsSP2Triangles( 7, 11,  8, 0x0,  4, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x03013E28 - 0x03013EA8
const Gfx door_seg3_dl_03013E28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(door_seg3_dl_03013C10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPDisplayList(door_seg3_dl_03013CC8),
    gsSPDisplayList(door_seg3_dl_03013D78),
    gsSPEndDisplayList(),
};

// 0x03013EA8 - 0x03013F20
const Gfx door_seg3_dl_03013EA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(door_seg3_dl_03013C10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPDisplayList(door_seg3_dl_03013CC8),
    gsSPEndDisplayList(),
};

// 0x03013F20
static const Vtx door_seg3_vertex_03013F20[] = {
    {{{   591,      0,    -58}, 0, {   976,   1808}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,   1010,    -58}, 0, {   -16,    148}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   591,   1010,    -58}, 0, {   990,    140}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,      0,    -58}, 0, {   -30,   1812}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,      0,     59}, 0, {     0,   1816}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,   1010,     59}, 0, {   974,    132}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -8,   1010,     59}, 0, {     0,    140}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,      0,     59}, 0, {   974,   1812}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x03013FA0
static const Vtx door_seg3_vertex_03013FA0[] = {
    {{{   492,    468,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   546,    412,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   492,    357,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   437,    412,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   492,    468,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   492,    357,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   546,    412,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   437,    412,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x03014020 - 0x03014100
const Gfx door_seg3_dl_03014020[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03009D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPVertex(door_seg3_vertex_03013F20, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPVertex(door_seg3_vertex_03013FA0, 8, 0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014100 - 0x03014128
const Gfx door_seg3_dl_03014100[] = {
    gsSPDisplayList(door_seg3_dl_03014020),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014128 - 0x03014140
const Gfx door_seg3_dl_03014128[] = {
    gsSPDisplayList(door_seg3_dl_03014020),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014140
static const Vtx door_seg3_vertex_03014140[] = {
    {{{   441,    850,     64}, 0, {   992,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   141,    850,     64}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   141,    550,     64}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   441,    550,     64}, 0, {   992,    992}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x03014180
static const Vtx door_seg3_vertex_03014180[] = {
    {{{   441,    850,     59}, 0, {   992,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   141,    850,     59}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   141,    550,     59}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   441,    550,     59}, 0, {   992,    992}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x030141C0 - 0x03014218
const Gfx door_seg3_dl_030141C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPEndDisplayList(),
};

// 0x03014218 - 0x03014250
const Gfx door_seg3_dl_03014218[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014250 - 0x03014280
const Gfx door_seg3_dl_03014250[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03011D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014140, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x03014280 - 0x030142B0
const Gfx door_seg3_dl_03014280[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03011D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014180, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x030142B0 - 0x030142E0
const Gfx door_seg3_dl_030142B0[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03012510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014140, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x030142E0 - 0x03014310
const Gfx door_seg3_dl_030142E0[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03012510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014180, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x03014310 - 0x03014340
const Gfx door_seg3_dl_03014310[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03012D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014140, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x03014340 - 0x03014370
const Gfx door_seg3_dl_03014340[] = {
    gsSPDisplayList(door_seg3_dl_030141C0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03012D10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(door_seg3_vertex_03014180, 4, 0),
    gsSPBranchList(door_seg3_dl_03014218),
};

// 0x03014370
static const Vtx door_seg3_vertex_03014370[] = {
    {{{   595,    916,     59}, 0, {   478,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   405,    544,     59}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   595,    544,     59}, 0, {   478,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   405,    916,     59}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   595,    544,    -58}, 0, {   478,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    544,    -58}, 0, {     0,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    916,    -58}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   595,    916,    -58}, 0, {   478,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x030143F0
static const Vtx door_seg3_vertex_030143F0[] = {
    {{{   595,    916,     64}, 0, {   480,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   405,    544,     64}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   595,    544,     64}, 0, {   480,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   405,    916,     64}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   595,    544,    -63}, 0, {   480,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    544,    -63}, 0, {     0,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    916,    -63}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   595,    916,    -63}, 0, {   480,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x03014470 - 0x030144E0
const Gfx door_seg3_dl_03014470[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03013510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPEndDisplayList(),
};

// 0x030144E0 - 0x03014528
const Gfx door_seg3_dl_030144E0[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014528 - 0x03014540
const Gfx door_seg3_dl_03014528[] = {
    gsSPDisplayList(door_seg3_dl_03014470),
    gsSPVertex(door_seg3_vertex_03014370, 8, 0),
    gsSPBranchList(door_seg3_dl_030144E0),
};

// 0x03014540 - 0x03014558
const Gfx door_seg3_dl_03014540[] = {
    gsSPDisplayList(door_seg3_dl_03014470),
    gsSPVertex(door_seg3_vertex_030143F0, 8, 0),
    gsSPBranchList(door_seg3_dl_030144E0),
};

// 0x03014558
static const Vtx door_seg3_vertex_03014558[] = {
    {{{    -8,   1000,    -58}, 0, {     0,     70}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -8,      0,    -58}, 0, {     0,    936}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -8,      0,     59}, 0, {   990,    936}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    -8,      0,    -58}, 0, {   990,    114}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   591,      0,    -58}, 0, {   990,    856}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   591,      0,     59}, 0, {     0,    856}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -8,      0,     59}, 0, {     0,    114}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    -8,   1000,     59}, 0, {     0,    114}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   591,   1000,    -58}, 0, {   990,    856}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -8,   1000,    -58}, 0, {   990,    114}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   591,   1000,     59}, 0, {     0,    856}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   591,   1000,     59}, 0, {   990,     70}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   591,      0,     59}, 0, {   990,    936}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   591,      0,    -58}, 0, {     0,    936}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   591,   1000,    -58}, 0, {     0,     70}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    -8,   1000,     59}, 0, {   990,     70}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x03014658
static const Vtx door_seg3_vertex_03014658[] = {
    {{{    -8,      0,     59}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,   1000,     59}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -8,   1000,     59}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,      0,     59}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,      0,    -58}, 0, {     0,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,      0,    -58}, 0, {   990,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,   1000,    -58}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   591,   1000,    -58}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x030146D8
static const Vtx door_seg3_vertex_030146D8[] = {
    {{{   492,    468,   -152}, 0, {     0,      0}, {0xe8, 0x47, 0x9a, 0xff}}},
    {{{   414,    412,    -98}, 0, {     0,      0}, {0x82, 0x00, 0x00, 0xff}}},
    {{{   492,    491,    -98}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   492,    468,    153}, 0, {     0,      0}, {0x18, 0x47, 0x66, 0xff}}},
    {{{   492,    357,    153}, 0, {     0,      0}, {0xe9, 0xb8, 0x65, 0xff}}},
    {{{   546,    412,    153}, 0, {     0,      0}, {0x5b, 0xe2, 0x53, 0xff}}},
    {{{   437,    412,    153}, 0, {     0,      0}, {0xa5, 0x1d, 0x53, 0xff}}},
    {{{   492,    491,     99}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   569,    412,     99}, 0, {     0,      0}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   492,    334,     99}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   414,    412,     99}, 0, {     0,      0}, {0x82, 0x00, 0x00, 0xff}}},
    {{{   492,    468,     44}, 0, {     0,      0}, {0xdb, 0x6f, 0xd1, 0xff}}},
    {{{   437,    412,     44}, 0, {     0,      0}, {0x91, 0xdb, 0xd2, 0xff}}},
    {{{   492,    357,     44}, 0, {     0,      0}, {0x26, 0x91, 0xd2, 0xff}}},
    {{{   546,    412,     44}, 0, {     0,      0}, {0x70, 0x24, 0xd2, 0xff}}},
};

// 0x030147C8
static const Vtx door_seg3_vertex_030147C8[] = {
    {{{   492,    491,    -98}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   437,    412,    -43}, 0, {     0,      0}, {0x90, 0x24, 0x2e, 0xff}}},
    {{{   492,    468,    -43}, 0, {     0,      0}, {0x26, 0x6f, 0x2f, 0xff}}},
    {{{   492,    468,   -152}, 0, {     0,      0}, {0xe8, 0x47, 0x9a, 0xff}}},
    {{{   546,    412,   -152}, 0, {     0,      0}, {0x5b, 0x1d, 0xae, 0xff}}},
    {{{   492,    357,   -152}, 0, {     0,      0}, {0x18, 0xb9, 0x9b, 0xff}}},
    {{{   437,    412,   -152}, 0, {     0,      0}, {0xa6, 0xe2, 0xad, 0xff}}},
    {{{   414,    412,    -98}, 0, {     0,      0}, {0x82, 0x00, 0x00, 0xff}}},
    {{{   569,    412,    -98}, 0, {     0,      0}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   492,    334,    -98}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   492,    357,    -43}, 0, {     0,      0}, {0xdb, 0x90, 0x2e, 0xff}}},
    {{{   546,    412,    -43}, 0, {     0,      0}, {0x70, 0xdb, 0x2e, 0xff}}},
};

// 0x03014888 - 0x030149C0
const Gfx door_seg3_dl_03014888[] = {
    gsSPVertex(door_seg3_vertex_030146D8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  6,  3, 0x0),
    gsSP2Triangles( 8,  3,  5, 0x0,  8,  7,  3, 0x0),
    gsSP2Triangles( 9,  5,  4, 0x0, 10,  9,  4, 0x0),
    gsSP2Triangles(10,  4,  6, 0x0,  9,  8,  5, 0x0),
    gsSP2Triangles( 7, 10,  6, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  7, 0x0, 12,  9, 10, 0x0),
    gsSP2Triangles(12, 13,  9, 0x0, 13,  8,  9, 0x0),
    gsSP2Triangles(14,  7,  8, 0x0, 13, 14,  8, 0x0),
    gsSP1Triangle(14, 11,  7, 0x0),
    gsSPVertex(door_seg3_vertex_030147C8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  4,  3,  0, 0x0),
    gsSP2Triangles( 5,  4,  8, 0x0,  6,  5,  9, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 6,  9,  7, 0x0,  4,  0,  8, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 9,  8, 11, 0x0,  0,  7,  1, 0x0),
    gsSP2Triangles( 7, 10,  1, 0x0,  8,  0,  2, 0x0),
    gsSP1Triangle( 8,  2, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x030149C0 - 0x03014A20
const Gfx door_seg3_dl_030149C0[] = {
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPVertex(door_seg3_vertex_03014558, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x03014A20 - 0x03014A50
const Gfx door_seg3_dl_03014A20[] = {
    gsSPVertex(door_seg3_vertex_03014658, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x03014A50 - 0x03014A80
const Gfx door_seg3_dl_03014A50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPEndDisplayList(),
};

// 0x03014A80 - 0x03014B30
const Gfx door_seg3_dl_03014A80[] = {
    gsSPDisplayList(door_seg3_dl_03014A50),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300CD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_030149C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300BD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014A20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014888),
};

// 0x03014B30 - 0x03014BE0
const Gfx door_seg3_dl_03014B30[] = {
    gsSPDisplayList(door_seg3_dl_03014A50),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300CD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_030149C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300BD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014A20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014888),
};

// 0x03014BE0 - 0x03014C90
const Gfx door_seg3_dl_03014BE0[] = {
    gsSPDisplayList(door_seg3_dl_03014A50),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300E510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_030149C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300D510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014A20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014888),
};

// 0x03014C90 - 0x03014D40
const Gfx door_seg3_dl_03014C90[] = {
    gsSPDisplayList(door_seg3_dl_03014A50),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300FD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_030149C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300ED10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014A20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014888),
};

// 0x03014D40 - 0x03014DF0
const Gfx door_seg3_dl_03014D40[] = {
    gsSPDisplayList(door_seg3_dl_03014A50),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03011510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_030149C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03010510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014A20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014888),
};

// 0x03014DF0
static const Vtx door_seg3_vertex_03014DF0[] = {
    {{{   591,      0,    -58}, 0, {     0,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,   1000,    -58}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   591,   1000,    -58}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,      0,    -58}, 0, {   990,   2012}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -8,      0,     59}, 0, {   990,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,   1000,     59}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -8,   1000,     59}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   591,      0,     59}, 0, {     0,   2012}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x03014E70
static const Vtx door_seg3_vertex_03014E70[] = {
    {{{   492,    468,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   492,    357,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   546,    412,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   437,    412,    153}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   492,    468,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   546,    412,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   492,    357,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   437,    412,   -152}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x03014EF0 - 0x03014F30
const Gfx door_seg3_dl_03014EF0[] = {
    gsSPLight(&door_seg3_lights_03009CE0.l, 1),
    gsSPLight(&door_seg3_lights_03009CE0.a, 2),
    gsSPVertex(door_seg3_vertex_03014DF0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x03014F30 - 0x03014F68
const Gfx door_seg3_dl_03014F30[] = {
    gsSPVertex(door_seg3_vertex_03014E70, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x03014F68 - 0x03014F98
const Gfx door_seg3_dl_03014F68[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPEndDisplayList(),
};

// 0x03014F98 - 0x03015008
const Gfx door_seg3_dl_03014F98[] = {
    gsSPDisplayList(door_seg3_dl_03014F68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300BD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014EF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014F30),
};

// 0x03015008 - 0x03015078
const Gfx door_seg3_dl_03015008[] = {
    gsSPDisplayList(door_seg3_dl_03014F68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300BD10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014EF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014F30),
};

// 0x03015078 - 0x030150E8
const Gfx door_seg3_dl_03015078[] = {
    gsSPDisplayList(door_seg3_dl_03014F68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300D510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014EF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014F30),
};

// 0x030150E8 - 0x03015158
const Gfx door_seg3_dl_030150E8[] = {
    gsSPDisplayList(door_seg3_dl_03014F68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_0300ED10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014EF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014F30),
};

// 0x03015158 - 0x030151C8
const Gfx door_seg3_dl_03015158[] = {
    gsSPDisplayList(door_seg3_dl_03014F68),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, door_seg3_texture_03010510),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(door_seg3_dl_03014EF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPLight(&door_seg3_lights_03009CF8.l, 1),
    gsSPLight(&door_seg3_lights_03009CF8.a, 2),
    gsSPBranchList(door_seg3_dl_03014F30),
};
