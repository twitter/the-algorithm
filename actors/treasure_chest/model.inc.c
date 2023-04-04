// Treasure Chest

// 0x06013F90
static const Lights1 treasure_chest_seg6_lights_06013F90 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06013FA8
ALIGNED8 static const Texture treasure_chest_seg6_texture_06013FA8[] = {
#include "actors/treasure_chest/treasure_chest_lock.rgba16.inc.c"
};

// 0x060147A8
ALIGNED8 static const Texture treasure_chest_seg6_texture_060147A8[] = {
#include "actors/treasure_chest/treasure_chest_side.rgba16.inc.c"
};

// 0x06014FA8
ALIGNED8 static const Texture treasure_chest_seg6_texture_06014FA8[] = {
#include "actors/treasure_chest/treasure_chest_lock_top.rgba16.inc.c"
};

// 0x060157A8
ALIGNED8 static const Texture treasure_chest_seg6_texture_060157A8[] = {
#include "actors/treasure_chest/treasure_chest_front.rgba16.inc.c"
};

// 0x060167A8
static const Vtx treasure_chest_seg6_vertex_060167A8[] = {
    {{{    20,    102,     87}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -19,     72,     87}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    20,     72,     87}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -19,    102,     87}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x060167E8
static const Vtx treasure_chest_seg6_vertex_060167E8[] = {
    {{{    20,     72,     77}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -19,     72,     87}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -19,     72,     77}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,    102,     77}, 0, {   478,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,    102,     87}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,    102,     77}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -19,    102,     87}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -19,    102,     77}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -19,     72,     77}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,    102,     87}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,    102,     77}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,     72,     87}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    20,     72,     87}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    20,    102,     77}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,     72,     87}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,     72,     77}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x060168E8
static const Vtx treasure_chest_seg6_vertex_060168E8[] = {
    {{{    20,    102,     77}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,    102,     87}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,     72,     87}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x06016918
static const Vtx treasure_chest_seg6_vertex_06016918[] = {
    {{{    77,    102,    -50}, 0, {     0,  -1736}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    77,     20,    -50}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    77,     20,     51}, 0, {  3374,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -76,     20,    -50}, 0, {     0,  -2416}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,     20,     51}, 0, {  5078,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,     20,    -50}, 0, {  5078,  -2416}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,     20,     51}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,    102,     51}, 0, {     0,  -1736}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -76,     20,    -50}, 0, {  3374,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -76,    102,    -50}, 0, {  3374,  -1736}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -76,     20,     51}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    77,    102,     51}, 0, {     0,  -1736}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -76,     20,     51}, 0, {  5078,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -76,    102,     51}, 0, {  5078,  -1736}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    77,     20,     51}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x06016A08
static const Vtx treasure_chest_seg6_vertex_06016A08[] = {
    {{{   -91,    102,    -76}, 0, {  5244,    962}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,    102,    -50}, 0, {  4734,     46}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,    102,    -50}, 0, {  -368,      2}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,    102,    -50}, 0, {     0,  -1736}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    77,     20,     51}, 0, {  3374,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    77,    102,     51}, 0, {  3374,  -1736}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -76,    102,    -50}, 0, {     0,  -1736}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -76,     20,    -50}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    77,     20,    -50}, 0, {  5078,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    77,    102,    -50}, 0, {  5078,  -1736}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -76,    102,    -50}, 0, {  -542,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -91,    102,    -76}, 0, { -1818,     66}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -91,    102,     77}, 0, {  5844,     66}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,    102,     51}, 0, {  4568,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    92,    102,    -76}, 0, {  -880,    908}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x06016AF8
static const Vtx treasure_chest_seg6_vertex_06016AF8[] = {
    {{{    92,    102,    -76}, 0, {  6356,    964}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,    102,    -50}, 0, {  5078,     86}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,    102,     51}, 0, {     0,     34}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    92,    102,     77}, 0, { -1308,    886}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    77,    102,     51}, 0, {  -540,     34}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -91,    102,     77}, 0, {  7888,    946}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    92,    102,     77}, 0, { -1308,    942}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -76,    102,     51}, 0, {  7122,     38}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x06016B78
static const Vtx treasure_chest_seg6_vertex_06016B78[] = {
    {{{    92,      0,    -68}, 0, {  2012,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,    102,      0}, 0, {   152,  -2392}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,      0,      0}, 0, {   340,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,    102,    -76}, 0, {  2012,  -2392}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,    102,      0}, 0, {    60,  -2358}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,    102,     77}, 0, {  1976,  -2358}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,      0,     69}, 0, {  2012,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,      0,      0}, 0, {   288,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -91,    102,      0}, 0, {    96,  -2410}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,    102,    -76}, 0, {  2012,  -2410}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,    -68}, 0, {  2012,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,      0}, 0, {   288,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,    -68}, 0, {  2012,    990}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{   -91,    102,    -76}, 0, {  2012,  -2416}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{     0,    102,    -76}, 0, { -1664,  -2416}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{     0,      0,    -68}, 0, { -1664,    990}, {0x00, 0xf7, 0x82, 0xff}}},
};

// 0x06016C78
static const Vtx treasure_chest_seg6_vertex_06016C78[] = {
    {{{    92,      0,     69}, 0, {  2012,    990}, {0x00, 0xf7, 0x7e, 0xff}}},
    {{{     0,    102,     77}, 0, { -1664,  -2416}, {0x00, 0xf7, 0x7e, 0xff}}},
    {{{     0,      0,     69}, 0, { -1664,    990}, {0x00, 0xf7, 0x7e, 0xff}}},
    {{{   -91,      0,     69}, 0, {  2012,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,    102,     77}, 0, {  2040,  -2428}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,    102,      0}, 0, {   236,  -2428}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,      0}, 0, {   388,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    102,    -76}, 0, { -1664,  -2416}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{    92,    102,    -76}, 0, {  2012,  -2416}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{    92,      0,    -68}, 0, {  2012,    990}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{     0,      0,    -68}, 0, { -1664,    990}, {0x00, 0xf7, 0x82, 0xff}}},
    {{{   -91,      0,     69}, 0, {  2012,    990}, {0x00, 0xf7, 0x7e, 0xff}}},
    {{{   -91,    102,     77}, 0, {  2012,  -2416}, {0x00, 0xf7, 0x7e, 0xff}}},
    {{{    92,    102,     77}, 0, {  2012,  -2416}, {0x00, 0xf7, 0x7e, 0xff}}},
};

// 0x06016D58 - 0x06016DA0
const Gfx treasure_chest_seg6_dl_06016D58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_06013FA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&treasure_chest_seg6_lights_06013F90.l, 1),
    gsSPLight(&treasure_chest_seg6_lights_06013F90.a, 2),
    gsSPVertex(treasure_chest_seg6_vertex_060167A8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016DA0 - 0x06016E18
const Gfx treasure_chest_seg6_dl_06016DA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_06014FA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(treasure_chest_seg6_vertex_060167E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  3,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles( 0, 12,  1, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_060168E8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016E18 - 0x06016EE0
const Gfx treasure_chest_seg6_dl_06016E18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_060147A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(treasure_chest_seg6_vertex_06016918, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06016A08, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06016AF8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016EE0 - 0x06016F90
const Gfx treasure_chest_seg6_dl_06016EE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_060157A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(treasure_chest_seg6_vertex_06016B78, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06016C78, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  1, 11,  2, 0x0),
    gsSP2Triangles( 1, 12, 11, 0x0,  0, 13,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016F90 - 0x06017030
const Gfx treasure_chest_seg6_dl_06016F90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(treasure_chest_seg6_dl_06016D58),
    gsSPDisplayList(treasure_chest_seg6_dl_06016DA0),
    gsSPDisplayList(treasure_chest_seg6_dl_06016E18),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(treasure_chest_seg6_dl_06016EE0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06017030
static const Vtx treasure_chest_seg6_vertex_06017030[] = {
    {{{    92,      0,    154}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,      0,      0}, 0, {  5078,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,     30,     27}, 0, {  4184,  -1022}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,     46,     77}, 0, {  2524,  -2028}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    92,     30,    127}, 0, {   862,  -1022}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -91,     30,     27}, 0, {  1310,   -988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,     30,    127}, 0, {  6292,   -988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,     46,     77}, 0, {  3800,  -1978}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,      0}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,    154}, 0, {  7632,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -73,     20,    127}, 0, {  6100,  -2332}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,     20,     27}, 0, {  6100,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,     20,     27}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,     20,    127}, 0, {     0,  -2332}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x06017110
static const Vtx treasure_chest_seg6_vertex_06017110[] = {
    {{{   -91,      0,      0}, 0, { -1564,     36}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,      0,     15}, 0, {  6714,    752}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,      0,     15}, 0, {  -644,    752}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -91,      0,      0}, 0, {  6180,     34}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,      0,     15}, 0, {  5466,    902}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,      0,    138}, 0, {  -274,    894}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -91,      0,    154}, 0, {  -994,     24}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -91,      0,    154}, 0, { -2176,    946}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,      0,    138}, 0, { -1256,     82}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,      0,    138}, 0, {  6100,    -14}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    92,      0,    154}, 0, {  7020,    828}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,      0,     15}, 0, {  5590,     32}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    92,      0,      0}, 0, {  6356,    894}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    92,      0,    154}, 0, { -1308,    894}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,      0,    138}, 0, {  -542,     32}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x06017200
static const Vtx treasure_chest_seg6_vertex_06017200[] = {
    {{{    74,      0,    138}, 0, { -3720,     10}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    74,     20,     27}, 0, {  1206,    974}, {0x81, 0x00, 0x00, 0xff}}},
    {{{    74,      0,     15}, 0, {  1730,     30}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -91,      0,      0}, 0, { -1564,     36}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    92,      0,      0}, 0, {  7632,     36}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    74,      0,     15}, 0, {  6714,    752}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -73,      0,     15}, 0, { -4772,     20}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -73,     20,     27}, 0, { -4174,    962}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -73,     20,    127}, 0, {   948,    986}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -73,      0,    138}, 0, {  1532,     50}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    74,      0,     15}, 0, { -3914,     60}, {0x00, 0xbf, 0x6c, 0xff}}},
    {{{   -73,     20,     27}, 0, {   990,    990}, {0x00, 0xbf, 0x6c, 0xff}}},
    {{{   -73,      0,     15}, 0, {   990,     60}, {0x00, 0xbf, 0x6c, 0xff}}},
    {{{    74,     20,     27}, 0, { -3914,    990}, {0x00, 0xbf, 0x6c, 0xff}}},
    {{{    74,     20,    127}, 0, { -3222,    958}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x060172F0
static const Vtx treasure_chest_seg6_vertex_060172F0[] = {
    {{{   -73,      0,    138}, 0, { -6024,    156}, {0x00, 0xc3, 0x91, 0xff}}},
    {{{    74,     20,    127}, 0, {  1162,    896}, {0x00, 0xc3, 0x91, 0xff}}},
    {{{    74,      0,    138}, 0, {  1212,    112}, {0x00, 0xc3, 0x91, 0xff}}},
    {{{   -73,     20,    127}, 0, { -6072,    940}, {0x00, 0xc3, 0x91, 0xff}}},
};

// 0x06017330
static const Vtx treasure_chest_seg6_vertex_06017330[] = {
    {{{   -19,     20,    136}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,     20,    154}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,     20,    136}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,     20,    154}, 0, {   990,      0}, {0x00, 0x38, 0x71, 0xff}}},
    {{{   -19,     20,    154}, 0, {     0,      0}, {0x00, 0x38, 0x71, 0xff}}},
    {{{   -19,      0,    164}, 0, {     0,    990}, {0x00, 0x38, 0x71, 0xff}}},
    {{{    20,      0,    164}, 0, {   990,    990}, {0x00, 0x38, 0x71, 0xff}}},
    {{{    20,      0,    154}, 0, {   -28,    -80}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    20,      0,    164}, 0, {     0,    984}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -19,      0,    164}, 0, {   968,    998}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -19,      0,    154}, 0, {   974,    -64}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    20,     20,    136}, 0, {   996,    -44}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,      0,    164}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,      0,    154}, 0, {   524,    980}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    20,     20,    154}, 0, {    10,    -24}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x06017420
static const Vtx treasure_chest_seg6_vertex_06017420[] = {
    {{{   -19,      0,    154}, 0, {   480,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,      0,    164}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,     20,    154}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -19,     20,    136}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -19,     20,    154}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    20,     20,    154}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -19,     20,    136}, 0, {    86,      0}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x06017490
static const Vtx treasure_chest_seg6_vertex_06017490[] = {
    {{{   -91,     46,     77}, 0, {  2012,  -1036}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     30,    127}, 0, { -1664,    990}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     46,     77}, 0, { -1664,  -1036}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,      0,    154}, 0, { -1664,   1992}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{    92,     30,    127}, 0, {  2012,      0}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{     0,     30,    127}, 0, { -1664,      0}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{    92,      0,    154}, 0, {  2012,   1992}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{   -91,     30,    127}, 0, {  2012,      0}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{   -91,      0,    154}, 0, {  2012,   1992}, {0x00, 0x54, 0x5e, 0xff}}},
    {{{     0,     30,    127}, 0, { -1664,   1028}, {0x00, 0x78, 0x26, 0xff}}},
    {{{    92,     30,    127}, 0, {  2012,   1028}, {0x00, 0x78, 0x26, 0xff}}},
    {{{    92,     46,     77}, 0, {  2012,  -1066}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     46,     77}, 0, { -1664,  -1066}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     30,     27}, 0, { -1664,      0}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{    92,     30,     27}, 0, {  2012,      0}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{    92,      0,      0}, 0, {  2012,   1992}, {0x00, 0x54, 0xa2, 0xff}}},
};

// 0x06017590
static const Vtx treasure_chest_seg6_vertex_06017590[] = {
    {{{   -91,     46,     77}, 0, {  2012,  -1036}, {0x00, 0x78, 0x26, 0xff}}},
    {{{   -91,     30,    127}, 0, {  2012,    990}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     30,    127}, 0, { -1664,    990}, {0x00, 0x78, 0x26, 0xff}}},
    {{{     0,     46,     77}, 0, { -1664,  -1022}, {0x00, 0x78, 0xda, 0xff}}},
    {{{    92,     30,     27}, 0, {  2012,    990}, {0x00, 0x78, 0xda, 0xff}}},
    {{{     0,     30,     27}, 0, { -1664,    990}, {0x00, 0x78, 0xda, 0xff}}},
    {{{    92,     46,     77}, 0, {  2012,  -1022}, {0x00, 0x78, 0xda, 0xff}}},
    {{{   -91,     30,     27}, 0, {  2012,    990}, {0x00, 0x78, 0xda, 0xff}}},
    {{{   -91,     46,     77}, 0, {  2012,  -1052}, {0x00, 0x78, 0xda, 0xff}}},
    {{{     0,     46,     77}, 0, { -1664,  -1052}, {0x00, 0x78, 0xda, 0xff}}},
    {{{     0,     30,     27}, 0, { -1664,      0}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{    92,      0,      0}, 0, {  2012,   1992}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{     0,      0,      0}, 0, { -1664,   1992}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{   -91,      0,      0}, 0, {  2012,   1992}, {0x00, 0x54, 0xa2, 0xff}}},
    {{{   -91,     30,     27}, 0, {  2012,      0}, {0x00, 0x54, 0xa2, 0xff}}},
};

// 0x06017680 - 0x06017790
const Gfx treasure_chest_seg6_dl_06017680[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_060147A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&treasure_chest_seg6_lights_06013F90.l, 1),
    gsSPLight(&treasure_chest_seg6_lights_06013F90.a, 2),
    gsSPVertex(treasure_chest_seg6_vertex_06017030, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0,  5,  9,  6, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06017110, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(14, 11, 13, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06017200, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0, 14,  1, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_060172F0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06017790 - 0x06017810
const Gfx treasure_chest_seg6_dl_06017790[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_06014FA8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(treasure_chest_seg6_vertex_06017330, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06017420, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x06017810 - 0x060178C0
const Gfx treasure_chest_seg6_dl_06017810[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, treasure_chest_seg6_texture_060157A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(treasure_chest_seg6_vertex_06017490, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  3,  5, 0x0),
    gsSP2Triangles( 7,  8,  3, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(treasure_chest_seg6_vertex_06017590, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9,  5, 0x0, 10, 11, 12, 0x0),
    gsSP2Triangles(13, 10, 12, 0x0, 13, 14, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x060178C0 - 0x06017958
const Gfx treasure_chest_seg6_dl_060178C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(treasure_chest_seg6_dl_06017680),
    gsSPDisplayList(treasure_chest_seg6_dl_06017790),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(treasure_chest_seg6_dl_06017810),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
