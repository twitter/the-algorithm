// Piranha Plant

// 0x060113B0
static const Lights1 piranha_plant_seg6_lights_060113B0 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x060113C8
static const Lights1 piranha_plant_seg6_lights_060113C8 = gdSPDefLights1(
    0x0a, 0x2b, 0x02,
    0x2b, 0xae, 0x0a, 0x28, 0x28, 0x28
);

// 0x060113E0
static const Lights1 piranha_plant_seg6_lights_060113E0 = gdSPDefLights1(
    0x3f, 0x00, 0x00,
    0xff, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x060113F8
ALIGNED8 static const Texture piranha_plant_seg6_texture_060113F8[] = {
#include "actors/piranha_plant/piranha_plant_tongue.rgba16.inc.c"
};

// 0x060123F8
ALIGNED8 static const Texture piranha_plant_seg6_texture_060123F8[] = {
#include "actors/piranha_plant/piranha_plant_skin.rgba16.inc.c"
};

// 0x06012BF8
ALIGNED8 static const Texture piranha_plant_seg6_texture_06012BF8[] = {
#include "actors/piranha_plant/piranha_plant_stem.rgba16.inc.c"
};

// 0x060133F8
ALIGNED8 static const Texture piranha_plant_seg6_texture_060133F8[] = {
#include "actors/piranha_plant/piranha_plant_bottom_lip.rgba16.inc.c"
};

// 0x06013BF8
ALIGNED8 static const Texture piranha_plant_seg6_texture_06013BF8[] = {
#include "actors/piranha_plant/piranha_plant_tooth.rgba16.inc.c"
};

// 0x060143F8
ALIGNED8 static const Texture piranha_plant_seg6_texture_060143F8[] = {
#include "actors/piranha_plant/piranha_plant_leaf.rgba16.inc.c"
};

// 0x060153F8
static const Vtx piranha_plant_seg6_vertex_060153F8[] = {
    {{{     0,      1,   -223}, 0, {     0,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   519,      1,    211}, 0, {   990,     28}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   519,      1,   -223}, 0, {     0,     28}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      1,    211}, 0, {   990,   2012}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x06015438 - 0x06015480
const Gfx piranha_plant_seg6_dl_06015438[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060143F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_060153F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06015480 - 0x060154F0
const Gfx piranha_plant_seg6_dl_06015480[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06015438),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x060154F0
static const Vtx piranha_plant_seg6_vertex_060154F0[] = {
    {{{   518,      2,   -223}, 0, {   990,      8}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   518,      1,    211}, 0, {     0,      8}, {0x00, 0x82, 0x00, 0xff}}},
    {{{     0,     -1,    211}, 0, {     0,   2012}, {0x00, 0x82, 0x00, 0xff}}},
    {{{     0,      0,   -223}, 0, {   990,   2012}, {0x00, 0x82, 0x00, 0xff}}},
};

// 0x06015530 - 0x06015578
const Gfx piranha_plant_seg6_dl_06015530[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060143F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_060154F0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06015578 - 0x060155E8
const Gfx piranha_plant_seg6_dl_06015578[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06015530),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x060155E8
static const Vtx piranha_plant_seg6_vertex_060155E8[] = {
    {{{    49,    -71,      0}, 0, {   800,    384}, {0x6e, 0xc3, 0x0d, 0xff}}},
    {{{    46,     -7,    -87}, 0, {   168,    384}, {0x6c, 0xe5, 0xc4, 0xff}}},
    {{{    67,     22,      0}, 0, {   488,   -128}, {0x7e, 0x07, 0x00, 0xff}}},
    {{{    -6,   -131,      0}, 0, {   990,    990}, {0x5c, 0xac, 0xeb, 0xff}}},
    {{{   -12,    -28,   -141}, 0, {     0,    990}, {0x57, 0xff, 0xa4, 0xff}}},
    {{{   -22,    138,    -87}, 0, {  1016,    990}, {0x51, 0x5a, 0xdd, 0xff}}},
    {{{    40,     95,     54}, 0, {   166,    368}, {0x66, 0x44, 0x1c, 0xff}}},
    {{{    40,     95,    -53}, 0, {   814,    370}, {0x67, 0x35, 0xce, 0xff}}},
    {{{   -22,    138,     88}, 0, {     0,    990}, {0x53, 0x41, 0x46, 0xff}}},
    {{{    67,     22,      0}, 0, {   490,   -156}, {0x7e, 0x07, 0x00, 0xff}}},
    {{{   -12,    -28,   -141}, 0, {   990,    990}, {0x57, 0xff, 0xa4, 0xff}}},
    {{{   -22,    138,    -87}, 0, {     0,    990}, {0x51, 0x5a, 0xdd, 0xff}}},
    {{{    40,     95,    -53}, 0, {   124,    380}, {0x67, 0x35, 0xce, 0xff}}},
    {{{    46,     -7,    -87}, 0, {   756,    380}, {0x6c, 0xe5, 0xc4, 0xff}}},
};

// 0x060156C8
static const Vtx piranha_plant_seg6_vertex_060156C8[] = {
    {{{    40,     95,     54}, 0, {   796,    370}, {0x66, 0x44, 0x1c, 0xff}}},
    {{{    46,     -7,     88}, 0, {   164,    370}, {0x6b, 0xfe, 0x44, 0xff}}},
    {{{    67,     22,      0}, 0, {   482,   -152}, {0x7e, 0x07, 0x00, 0xff}}},
    {{{    46,     -7,    -87}, 0, {   756,    380}, {0x6c, 0xe5, 0xc4, 0xff}}},
    {{{    40,     95,    -53}, 0, {   124,    380}, {0x67, 0x35, 0xce, 0xff}}},
    {{{    67,     22,      0}, 0, {   406,   -134}, {0x7e, 0x07, 0x00, 0xff}}},
    {{{    46,     -7,     88}, 0, {   822,    370}, {0x6b, 0xfe, 0x44, 0xff}}},
    {{{    49,    -71,      0}, 0, {   190,    370}, {0x6e, 0xc3, 0x0d, 0xff}}},
    {{{    67,     22,      0}, 0, {   530,   -154}, {0x7e, 0x07, 0x00, 0xff}}},
    {{{   -12,    -28,    142}, 0, {   990,    990}, {0x59, 0xd5, 0x4e, 0xff}}},
    {{{    -6,   -131,      0}, 0, {     0,    990}, {0x5c, 0xac, 0xeb, 0xff}}},
    {{{   -22,    138,     88}, 0, {   990,    990}, {0x53, 0x41, 0x46, 0xff}}},
    {{{   -12,    -28,    142}, 0, {     0,    990}, {0x59, 0xd5, 0x4e, 0xff}}},
};

// 0x06015798 - 0x06015850
const Gfx piranha_plant_seg6_dl_06015798[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06012BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_060155E8, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  4,  1, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_060156C8, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  7,  6, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11,  1,  0, 0x0),
    gsSP1Triangle(11, 12,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06015850 - 0x060158B0
const Gfx piranha_plant_seg6_dl_06015850[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06015798),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060158B0
static const Vtx piranha_plant_seg6_vertex_060158B0[] = {
    {{{   519,     74,      0}, 0, {     0,     84}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   387,    136,    249}, 0, {  4082,    990}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   387,     66,    249}, 0, {  4074,      0}, {0x70, 0x00, 0x3b, 0xff}}},
    {{{   519,    136,      0}, 0, {     0,    990}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   387,     66,   -248}, 0, { -3114,      0}, {0x4b, 0x00, 0x9a, 0xff}}},
    {{{   387,    136,   -248}, 0, { -3122,    990}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   519,     74,      0}, 0, {   990,     84}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   519,    136,      0}, 0, {   990,    990}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   387,     68,    249}, 0, {   -54,    -20}, {0x16, 0x00, 0x7c, 0xff}}},
    {{{   387,    136,    249}, 0, {   -36,    988}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   186,     56,    286}, 0, {  3382,    996}, {0x16, 0x00, 0x7c, 0xff}}},
    {{{   186,     56,   -285}, 0, { -2756,    990}, {0x16, 0x00, 0x84, 0xff}}},
    {{{   387,    136,   -248}, 0, {   982,    990}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   387,     66,   -248}, 0, {   990,      0}, {0x4b, 0x00, 0x9a, 0xff}}},
};

// 0x06015990
static const Vtx piranha_plant_seg6_vertex_06015990[] = {
    {{{   597,     13,      0}, 0, {  -166,    532}, {0x7c, 0x17, 0xfe, 0xff}}},
    {{{   385,     72,    255}, 0, {   872,    938}, {0x2c, 0x6c, 0x2f, 0xff}}},
    {{{   391,     10,    335}, 0, {  1064,    610}, {0x30, 0xfe, 0x75, 0xff}}},
    {{{   391,     10,   -334}, 0, {  1090,    606}, {0x2d, 0x18, 0x8c, 0xff}}},
    {{{   381,    -93,   -260}, 0, {  1028,    -16}, {0x48, 0xbf, 0xb0, 0xff}}},
    {{{   101,    -60,   -288}, 0, {    -4,    150}, {0xd8, 0xc9, 0x96, 0xff}}},
    {{{   108,     56,   -294}, 0, {     2,    850}, {0xda, 0x65, 0xbf, 0xff}}},
    {{{   385,     72,   -253}, 0, {  1004,    972}, {0x16, 0x77, 0xdd, 0xff}}},
    {{{    56,      2,   -271}, 0, {  -192,    522}, {0xaf, 0xfb, 0x9f, 0xff}}},
    {{{   391,     10,    335}, 0, {   -70,    596}, {0x30, 0xfe, 0x75, 0xff}}},
    {{{   385,     72,    255}, 0, {   -12,    966}, {0x2c, 0x6c, 0x2f, 0xff}}},
    {{{   108,     56,    296}, 0, {   910,    840}, {0xe4, 0x73, 0x2c, 0xff}}},
    {{{    58,      2,    277}, 0, {  1100,    562}, {0xaf, 0xfc, 0x61, 0xff}}},
    {{{   100,    -62,    291}, 0, {   952,    216}, {0xc6, 0xc8, 0x61, 0xff}}},
    {{{   381,    -93,    261}, 0, {    14,     86}, {0x2c, 0xbe, 0x62, 0xff}}},
};

// 0x06015A80
static const Vtx piranha_plant_seg6_vertex_06015A80[] = {
    {{{   391,     10,   -334}, 0, {   -64,    582}, {0x2d, 0x18, 0x8c, 0xff}}},
    {{{   385,     72,   -253}, 0, {   130,    944}, {0x16, 0x77, 0xdd, 0xff}}},
    {{{   519,     79,      0}, 0, {   978,    956}, {0x33, 0x73, 0xf4, 0xff}}},
    {{{   391,     10,    335}, 0, {  1064,    610}, {0x30, 0xfe, 0x75, 0xff}}},
    {{{   381,    -93,    261}, 0, {   866,     20}, {0x2c, 0xbe, 0x62, 0xff}}},
    {{{   545,    -83,      0}, 0, {   -96,      0}, {0x6b, 0xbf, 0x14, 0xff}}},
    {{{   597,     13,      0}, 0, {  -166,    532}, {0x7c, 0x17, 0xfe, 0xff}}},
    {{{   519,     79,      0}, 0, {   -26,    904}, {0x33, 0x73, 0xf4, 0xff}}},
    {{{   385,     72,    255}, 0, {   872,    938}, {0x2c, 0x6c, 0x2f, 0xff}}},
    {{{   597,     13,      0}, 0, {  1100,    560}, {0x7c, 0x17, 0xfe, 0xff}}},
    {{{   381,    -93,   -260}, 0, {    88,    -48}, {0x48, 0xbf, 0xb0, 0xff}}},
    {{{   545,    -83,      0}, 0, {  1000,    -20}, {0x6b, 0xbf, 0x14, 0xff}}},
};

// 0x06015B40
static const Vtx piranha_plant_seg6_vertex_06015B40[] = {
    {{{   545,    -83,      0}, 0, {  -118,    908}, {0x6b, 0xbf, 0x14, 0xff}}},
    {{{   381,    -93,    261}, 0, {  1050,    888}, {0x2c, 0xbe, 0x62, 0xff}}},
    {{{   343,   -231,    195}, 0, {   880,     40}, {0x34, 0xa0, 0x3f, 0xff}}},
    {{{   -76,    -53,      0}, 0, {   858,    140}, {0x8d, 0xcc, 0xf6, 0xff}}},
    {{{   -89,     12,      0}, 0, {   742,   -130}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{    56,      2,   -271}, 0, {  -398,    542}, {0xaf, 0xfb, 0x9f, 0xff}}},
    {{{   101,    -60,   -288}, 0, {  -382,    902}, {0xd8, 0xc9, 0x96, 0xff}}},
    {{{   148,   -221,   -195}, 0, {   230,   1518}, {0xca, 0xa3, 0xbf, 0xff}}},
    {{{    11,   -205,      0}, 0, {  1066,    914}, {0xab, 0xa3, 0x0c, 0xff}}},
    {{{    58,      2,    277}, 0, {  1510,    656}, {0xaf, 0xfc, 0x61, 0xff}}},
    {{{   -89,     12,      0}, 0, {    64,   1326}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{   -76,    -53,      0}, 0, {   -16,   1022}, {0x8d, 0xcc, 0xf6, 0xff}}},
    {{{   100,    -62,    291}, 0, {  1546,    266}, {0xc6, 0xc8, 0x61, 0xff}}},
    {{{    11,   -205,      0}, 0, {  -100,    168}, {0xab, 0xa3, 0x0c, 0xff}}},
    {{{   440,   -225,      0}, 0, {    48,    -18}, {0x53, 0xa2, 0xf3, 0xff}}},
    {{{   148,   -222,    196}, 0, {   962,   -438}, {0xe1, 0xa2, 0x4e, 0xff}}},
};

// 0x06015C40
static const Vtx piranha_plant_seg6_vertex_06015C40[] = {
    {{{   440,   -225,      0}, 0, {   -64,   1068}, {0x53, 0xa2, 0xf3, 0xff}}},
    {{{   343,   -231,    195}, 0, {   342,    310}, {0x34, 0xa0, 0x3f, 0xff}}},
    {{{   249,   -293,      0}, 0, {   640,    920}, {0xfe, 0x82, 0x00, 0xff}}},
    {{{   381,    -93,    261}, 0, {     0,    766}, {0x2c, 0xbe, 0x62, 0xff}}},
    {{{   148,   -222,    196}, 0, {   800,    -16}, {0xe1, 0xa2, 0x4e, 0xff}}},
    {{{   343,   -231,    195}, 0, {   -22,     -8}, {0x34, 0xa0, 0x3f, 0xff}}},
    {{{   100,    -62,    291}, 0, {  1186,    906}, {0xc6, 0xc8, 0x61, 0xff}}},
    {{{   343,   -231,    195}, 0, {  1280,   -514}, {0x34, 0xa0, 0x3f, 0xff}}},
    {{{   148,   -222,    196}, 0, {   738,   -860}, {0xe1, 0xa2, 0x4e, 0xff}}},
    {{{   249,   -293,      0}, 0, {   682,     56}, {0xfe, 0x82, 0x00, 0xff}}},
    {{{   148,   -222,    196}, 0, {   740,   -860}, {0xe1, 0xa2, 0x4e, 0xff}}},
    {{{    11,   -205,      0}, 0, {   -28,   -436}, {0xab, 0xa3, 0x0c, 0xff}}},
    {{{   249,   -293,      0}, 0, {   592,     44}, {0xfe, 0x82, 0x00, 0xff}}},
    {{{   148,   -221,   -195}, 0, {   -80,    542}, {0xca, 0xa3, 0xbf, 0xff}}},
    {{{   343,   -231,   -194}, 0, {   430,    930}, {0x21, 0xa1, 0xb3, 0xff}}},
    {{{   440,   -225,      0}, 0, {  1090,    430}, {0x53, 0xa2, 0xf3, 0xff}}},
};

// 0x06015D40
static const Vtx piranha_plant_seg6_vertex_06015D40[] = {
    {{{   101,    -60,   -288}, 0, {   -78,    848}, {0xd8, 0xc9, 0x96, 0xff}}},
    {{{   343,   -231,   -194}, 0, {   878,    562}, {0x21, 0xa1, 0xb3, 0xff}}},
    {{{   148,   -221,   -195}, 0, {   264,    390}, {0xca, 0xa3, 0xbf, 0xff}}},
    {{{   381,    -93,   -260}, 0, {   838,   1014}, {0x48, 0xbf, 0xb0, 0xff}}},
    {{{   381,    -93,   -260}, 0, {  -186,    708}, {0x48, 0xbf, 0xb0, 0xff}}},
    {{{   440,   -225,      0}, 0, {   862,    -86}, {0x53, 0xa2, 0xf3, 0xff}}},
    {{{   343,   -231,   -194}, 0, {   -84,     -2}, {0x21, 0xa1, 0xb3, 0xff}}},
    {{{   545,    -83,      0}, 0, {  1162,    670}, {0x6b, 0xbf, 0x14, 0xff}}},
};

// 0x06015DC0
static const Vtx piranha_plant_seg6_vertex_06015DC0[] = {
    {{{   108,     56,    296}, 0, {     0,      0}, {0xe4, 0x73, 0x2c, 0xff}}},
    {{{   108,     56,   -294}, 0, {     0,      0}, {0xda, 0x65, 0xbf, 0xff}}},
    {{{   -89,     12,      0}, 0, {     0,      0}, {0x8f, 0x38, 0x00, 0xff}}},
    {{{   519,     79,      0}, 0, {     0,      0}, {0x33, 0x73, 0xf4, 0xff}}},
    {{{   385,     72,   -253}, 0, {     0,      0}, {0x16, 0x77, 0xdd, 0xff}}},
    {{{   385,     72,    255}, 0, {     0,      0}, {0x2c, 0x6c, 0x2f, 0xff}}},
    {{{    58,      2,    277}, 0, {     0,      0}, {0xaf, 0xfc, 0x61, 0xff}}},
    {{{    56,      2,   -271}, 0, {     0,      0}, {0xaf, 0xfb, 0x9f, 0xff}}},
};

// 0x06015E40 - 0x06015EA8
const Gfx piranha_plant_seg6_dl_06015E40[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06013BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_060158B0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x06015EA8 - 0x06015F68
const Gfx piranha_plant_seg6_dl_06015EA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060133F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113C8.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113C8.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06015990, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  3, 0x0,  3,  5,  8, 0x0),
    gsSP2Triangles( 3,  8,  6, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0, 13, 14,  9, 0x0),
    gsSP1Triangle( 9, 12, 13, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06015A80, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 0,  2,  9, 0x0,  9, 10,  0, 0x0),
    gsSP1Triangle( 9, 11, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x06015F68 - 0x06016060
const Gfx piranha_plant_seg6_dl_06015F68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060123F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06015B40, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  3,  7,  8, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(11, 12,  9, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles( 0,  2, 14, 0x0, 12, 13, 15, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06015C40, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 13, 12, 0x0),
    gsSP2Triangles(14, 15, 12, 0x0, 13, 14, 12, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06015D40, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016060 - 0x060160B0
const Gfx piranha_plant_seg6_dl_06016060[] = {
    gsSPLight(&piranha_plant_seg6_lights_060113E0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113E0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06015DC0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  0,  4,  1, 0x0),
    gsSP2Triangles( 6,  0,  2, 0x0,  2,  1,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x060160B0 - 0x06016120
const Gfx piranha_plant_seg6_dl_060160B0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06015EA8),
    gsSPDisplayList(piranha_plant_seg6_dl_06015F68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(piranha_plant_seg6_dl_06016060),
    gsSPEndDisplayList(),
};

// 0x06016120 - 0x06016190
const Gfx piranha_plant_seg6_dl_06016120[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06015E40),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06016190
static const Vtx piranha_plant_seg6_vertex_06016190[] = {
    {{{   228,      3,    232}, 0, { -2174,   1002}, {0xf5, 0x00, 0x7e, 0xff}}},
    {{{   412,   -109,    249}, 0, {   988,    986}, {0x51, 0x00, 0x61, 0xff}}},
    {{{   412,    -41,    249}, 0, {   982,    -34}, {0xf5, 0x00, 0x7e, 0xff}}},
    {{{   412,    -39,   -248}, 0, {   984,     58}, {0x39, 0x00, 0x8f, 0xff}}},
    {{{   412,   -109,   -248}, 0, {   990,    990}, {0x50, 0x00, 0x9e, 0xff}}},
    {{{   229,      3,   -229}, 0, { -2282,    990}, {0xf3, 0x00, 0x82, 0xff}}},
    {{{   554,    -39,      0}, 0, {   -36,    -44}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   412,   -109,   -248}, 0, {  4004,    996}, {0x50, 0x00, 0x9e, 0xff}}},
    {{{   412,    -39,   -248}, 0, {  4006,    -36}, {0x39, 0x00, 0x8f, 0xff}}},
    {{{   554,   -109,      0}, 0, {   -38,    990}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   412,    -39,    249}, 0, { -3106,    -70}, {0x6e, 0x00, 0x3e, 0xff}}},
    {{{   412,   -109,    249}, 0, { -3106,    952}, {0x51, 0x00, 0x61, 0xff}}},
    {{{   554,    -39,      0}, 0, {   990,      0}, {0x7e, 0x00, 0x00, 0xff}}},
    {{{   554,   -109,      0}, 0, {   990,    990}, {0x7e, 0x00, 0x00, 0xff}}},
};

// 0x06016270
static const Vtx piranha_plant_seg6_vertex_06016270[] = {
    {{{   109,     -3,   -277}, 0, {  -290,    482}, {0xc4, 0xb1, 0xb2, 0xff}}},
    {{{   432,     43,   -319}, 0, {  2054,    520}, {0x2b, 0xfb, 0x89, 0xff}}},
    {{{   410,    -50,   -253}, 0, {  1758,    -58}, {0x1b, 0x89, 0xe1, 0xff}}},
    {{{   410,    -50,    254}, 0, {  1096,     -6}, {0x12, 0x86, 0x1a, 0xff}}},
    {{{   554,    -47,      0}, 0, { -1736,    -12}, {0x1c, 0x85, 0x02, 0xff}}},
    {{{   645,     27,      0}, 0, { -2250,    462}, {0x7e, 0x04, 0x00, 0xff}}},
    {{{   432,     43,    320}, 0, {  1488,    596}, {0x2b, 0xfb, 0x77, 0xff}}},
    {{{   417,     98,    263}, 0, {  1116,    942}, {0x3d, 0x56, 0x45, 0xff}}},
    {{{   551,    106,      0}, 0, { -1734,    970}, {0x5b, 0x57, 0xf7, 0xff}}},
    {{{   645,     27,      0}, 0, {  1558,    510}, {0x7e, 0x04, 0x00, 0xff}}},
    {{{   432,     43,   -319}, 0, { -1512,    534}, {0x2b, 0xfb, 0x89, 0xff}}},
    {{{   417,     98,   -262}, 0, { -1202,    890}, {0x2b, 0x57, 0xaf, 0xff}}},
    {{{   410,    -50,   -253}, 0, { -1164,    -52}, {0x1b, 0x89, 0xe1, 0xff}}},
    {{{   554,    -47,      0}, 0, {  1168,     24}, {0x1c, 0x85, 0x02, 0xff}}},
    {{{   551,    106,      0}, 0, {  1146,    998}, {0x5b, 0x57, 0xf7, 0xff}}},
};

// 0x06016360
static const Vtx piranha_plant_seg6_vertex_06016360[] = {
    {{{   109,     -3,    278}, 0, {  -848,    398}, {0xc4, 0xb1, 0x4e, 0xff}}},
    {{{   153,     41,    300}, 0, { -1332,    812}, {0xd6, 0x3d, 0x66, 0xff}}},
    {{{   -39,     22,      0}, 0, {  2340,    726}, {0x91, 0x3b, 0x0a, 0xff}}},
    {{{   153,     41,   -299}, 0, {    80,    732}, {0xc5, 0x3b, 0xa1, 0xff}}},
    {{{   417,     98,   -262}, 0, {  1982,    884}, {0x2b, 0x57, 0xaf, 0xff}}},
    {{{   432,     43,   -319}, 0, {  2054,    520}, {0x2b, 0xfb, 0x89, 0xff}}},
    {{{   109,     -3,   -277}, 0, {  -290,    482}, {0xc4, 0xb1, 0xb2, 0xff}}},
    {{{   153,     41,    300}, 0, {  1252,    774}, {0xd6, 0x3d, 0x66, 0xff}}},
    {{{   109,     -3,    278}, 0, {  1494,    500}, {0xc4, 0xb1, 0x4e, 0xff}}},
    {{{   432,     43,    320}, 0, {  -100,    618}, {0x2b, 0xfb, 0x77, 0xff}}},
    {{{   417,     98,    263}, 0, {   -30,    998}, {0x3d, 0x56, 0x45, 0xff}}},
    {{{   410,    -50,    254}, 0, {    74,      2}, {0x12, 0x86, 0x1a, 0xff}}},
    {{{   -48,     -7,      0}, 0, {  2426,    480}, {0xd6, 0x89, 0x00, 0xff}}},
    {{{   109,     -3,   -277}, 0, {   920,    274}, {0xc4, 0xb1, 0xb2, 0xff}}},
    {{{   -48,     -7,      0}, 0, {   -36,    506}, {0xd6, 0x89, 0x00, 0xff}}},
    {{{   -39,     22,      0}, 0, {   -30,    756}, {0x91, 0x3b, 0x0a, 0xff}}},
};

// 0x06016460
static const Vtx piranha_plant_seg6_vertex_06016460[] = {
    {{{   -39,     22,      0}, 0, {   -30,    756}, {0x91, 0x3b, 0x0a, 0xff}}},
    {{{   153,     41,   -299}, 0, {  1032,    676}, {0xc5, 0x3b, 0xa1, 0xff}}},
    {{{   109,     -3,   -277}, 0, {   920,    274}, {0xc4, 0xb1, 0xb2, 0xff}}},
};

// 0x06016490
static const Vtx piranha_plant_seg6_vertex_06016490[] = {
    {{{   276,    254,      0}, 0, {   514,    234}, {0xf8, 0x7e, 0x00, 0xff}}},
    {{{   177,    186,    185}, 0, {  -146,     84}, {0xc7, 0x5f, 0x3d, 0xff}}},
    {{{   373,    203,    195}, 0, {     8,    832}, {0x17, 0x68, 0x44, 0xff}}},
    {{{   470,    201,      0}, 0, {   750,   1032}, {0x4a, 0x66, 0x0a, 0xff}}},
    {{{   373,    203,   -194}, 0, {  1288,    480}, {0x2b, 0x67, 0xc6, 0xff}}},
    {{{   177,    186,   -184}, 0, {  1068,   -250}, {0xdc, 0x62, 0xb9, 0xff}}},
    {{{    37,    161,      0}, 0, {   334,   -600}, {0xaa, 0x5c, 0xf4, 0xff}}},
    {{{   373,    203,   -194}, 0, {   906,     12}, {0x2b, 0x67, 0xc6, 0xff}}},
    {{{   470,    201,      0}, 0, {    12,     40}, {0x4a, 0x66, 0x0a, 0xff}}},
    {{{   551,    106,      0}, 0, {   -58,    818}, {0x5b, 0x57, 0xf7, 0xff}}},
    {{{   417,     98,   -262}, 0, {  1162,    832}, {0x2b, 0x57, 0xaf, 0xff}}},
    {{{   177,    186,   -184}, 0, {   654,     76}, {0xdc, 0x62, 0xb9, 0xff}}},
    {{{   417,     98,   -262}, 0, {   -30,    742}, {0x2b, 0x57, 0xaf, 0xff}}},
    {{{   153,     41,   -299}, 0, {  1006,    786}, {0xc5, 0x3b, 0xa1, 0xff}}},
    {{{   373,    203,   -194}, 0, {   -52,    210}, {0x2b, 0x67, 0xc6, 0xff}}},
};

// 0x06016580
static const Vtx piranha_plant_seg6_vertex_06016580[] = {
    {{{   470,    201,      0}, 0, {  1006,    194}, {0x4a, 0x66, 0x0a, 0xff}}},
    {{{   417,     98,    263}, 0, {   -92,    386}, {0x3d, 0x56, 0x45, 0xff}}},
    {{{   551,    106,      0}, 0, {   920,    662}, {0x5b, 0x57, 0xf7, 0xff}}},
    {{{    37,    161,      0}, 0, {  1384,     18}, {0xaa, 0x5c, 0xf4, 0xff}}},
    {{{   177,    186,   -184}, 0, {   406,    -90}, {0xdc, 0x62, 0xb9, 0xff}}},
    {{{   153,     41,   -299}, 0, {    84,    726}, {0xc5, 0x3b, 0xa1, 0xff}}},
    {{{   -39,     22,      0}, 0, {  1582,    740}, {0x91, 0x3b, 0x0a, 0xff}}},
    {{{   177,    186,    185}, 0, {  1240,   -122}, {0xc7, 0x5f, 0x3d, 0xff}}},
    {{{    37,    161,      0}, 0, {    50,      0}, {0xaa, 0x5c, 0xf4, 0xff}}},
    {{{   -39,     22,      0}, 0, {  -102,    668}, {0x91, 0x3b, 0x0a, 0xff}}},
    {{{   153,     41,    300}, 0, {  1742,    618}, {0xd6, 0x3d, 0x66, 0xff}}},
    {{{   373,    203,    195}, 0, {  1746,    660}, {0x17, 0x68, 0x44, 0xff}}},
    {{{   177,    186,    185}, 0, {   848,   1242}, {0xc7, 0x5f, 0x3d, 0xff}}},
    {{{   153,     41,    300}, 0, {   134,    728}, {0xd6, 0x3d, 0x66, 0xff}}},
    {{{   417,     98,    263}, 0, {  1526,    116}, {0x3d, 0x56, 0x45, 0xff}}},
    {{{   373,    203,    195}, 0, {   272,    -30}, {0x17, 0x68, 0x44, 0xff}}},
};

// 0x06016680
static const Vtx piranha_plant_seg6_vertex_06016680[] = {
    {{{   153,     26,    221}, 0, {   808,    604}, {0x02, 0x84, 0xe8, 0xff}}},
    {{{   -48,     -7,      0}, 0, {   486,    -60}, {0xd6, 0x89, 0x00, 0xff}}},
    {{{   153,     26,   -220}, 0, {   162,    604}, {0xf9, 0x83, 0x12, 0xff}}},
    {{{   410,    -50,    254}, 0, {   858,   1440}, {0x12, 0x86, 0x1a, 0xff}}},
    {{{   554,    -47,      0}, 0, {   486,   1912}, {0x1c, 0x85, 0x02, 0xff}}},
    {{{   410,    -50,   -253}, 0, {   114,   1440}, {0x1b, 0x89, 0xe1, 0xff}}},
};

// 0x060166E0
static const Vtx piranha_plant_seg6_vertex_060166E0[] = {
    {{{   410,    -50,   -253}, 0, {     0,      0}, {0x1b, 0x89, 0xe1, 0xff}}},
    {{{   153,     26,   -220}, 0, {     0,      0}, {0xf9, 0x83, 0x12, 0xff}}},
    {{{   109,     -3,   -277}, 0, {     0,      0}, {0xc4, 0xb1, 0xb2, 0xff}}},
    {{{   -48,     -7,      0}, 0, {     0,      0}, {0xd6, 0x89, 0x00, 0xff}}},
    {{{   153,     26,    221}, 0, {     0,      0}, {0x02, 0x84, 0xe8, 0xff}}},
    {{{   109,     -3,    278}, 0, {     0,      0}, {0xc4, 0xb1, 0x4e, 0xff}}},
    {{{   410,    -50,    254}, 0, {     0,      0}, {0x12, 0x86, 0x1a, 0xff}}},
};

// 0x06016750 - 0x060167B8
const Gfx piranha_plant_seg6_dl_06016750[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06013BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016190, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 13, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x060167B8 - 0x06016890
const Gfx piranha_plant_seg6_dl_060167B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060133F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113C8.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113C8.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016270, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  6,  5, 0x0),
    gsSP2Triangles( 5,  8,  7, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 10,  9, 0x0,  9, 13, 12, 0x0),
    gsSP1Triangle(11, 14,  9, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06016360, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0, 11,  9,  8, 0x0),
    gsSP2Triangles( 2, 12,  0, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06016460, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016890 - 0x06016960
const Gfx piranha_plant_seg6_dl_06016890[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060123F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016490, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  0,  5,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  9, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 11, 14, 12, 0x0),
    gsSPVertex(piranha_plant_seg6_vertex_06016580, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016960 - 0x060169A8
const Gfx piranha_plant_seg6_dl_06016960[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_060113F8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(piranha_plant_seg6_vertex_06016680, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 2,  4,  3, 0x0,  2,  5,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x060169A8 - 0x060169E8
const Gfx piranha_plant_seg6_dl_060169A8[] = {
    gsSPLight(&piranha_plant_seg6_lights_060113C8.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113C8.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_060166E0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  5,  4,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x060169E8 - 0x06016A78
const Gfx piranha_plant_seg6_dl_060169E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_060167B8),
    gsSPDisplayList(piranha_plant_seg6_dl_06016890),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06016960),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(piranha_plant_seg6_dl_060169A8),
    gsSPEndDisplayList(),
};

// 0x06016A78 - 0x06016AE8
const Gfx piranha_plant_seg6_dl_06016A78[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06016750),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x06016AE8
static const Vtx piranha_plant_seg6_vertex_06016AE8[] = {
    {{{    10,     34,      0}, 0, {     0,    990}, {0x05, 0x6f, 0x3c, 0xff}}},
    {{{    11,    -23,     35}, 0, {   990,    990}, {0x07, 0x94, 0x42, 0xff}}},
    {{{   191,    -17,     26}, 0, {   918,      0}, {0x06, 0x03, 0x7e, 0xff}}},
    {{{   191,     26,      0}, 0, {   142,      0}, {0x04, 0x6d, 0xc0, 0xff}}},
    {{{    11,    -25,    -33}, 0, {     0,    990}, {0x05, 0xff, 0x82, 0xff}}},
    {{{    10,     34,      0}, 0, {   950,    990}, {0x05, 0x6f, 0x3c, 0xff}}},
    {{{   191,     26,      0}, 0, {   864,    -26}, {0x04, 0x6d, 0xc0, 0xff}}},
    {{{   191,    -18,    -25}, 0, {   118,    -28}, {0x06, 0x91, 0xc4, 0xff}}},
    {{{    11,    -23,     35}, 0, {     0,    990}, {0x07, 0x94, 0x42, 0xff}}},
    {{{    11,    -25,    -33}, 0, {   990,    990}, {0x05, 0xff, 0x82, 0xff}}},
    {{{   191,    -18,    -25}, 0, {   860,    -30}, {0x06, 0x91, 0xc4, 0xff}}},
    {{{   191,    -17,     26}, 0, {    84,    -30}, {0x06, 0x03, 0x7e, 0xff}}},
};

// 0x06016BA8 - 0x06016C10
const Gfx piranha_plant_seg6_dl_06016BA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06012BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016AE8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016C10 - 0x06016C70
const Gfx piranha_plant_seg6_dl_06016C10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06016BA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06016C70
static const Vtx piranha_plant_seg6_vertex_06016C70[] = {
    {{{    -1,     34,      0}, 0, {     0,    990}, {0x01, 0x6f, 0x3c, 0xff}}},
    {{{     0,    -26,     37}, 0, {   990,    990}, {0x03, 0x94, 0x42, 0xff}}},
    {{{   172,    -22,     33}, 0, {   938,    -26}, {0x02, 0x03, 0x7e, 0xff}}},
    {{{   172,     32,      0}, 0, {    22,    -26}, {0x01, 0x6d, 0xbf, 0xff}}},
    {{{     0,    -28,    -35}, 0, {   -46,    984}, {0x02, 0xff, 0x82, 0xff}}},
    {{{    -1,     34,      0}, 0, {   974,    986}, {0x01, 0x6f, 0x3c, 0xff}}},
    {{{   172,     32,      0}, 0, {   942,      0}, {0x01, 0x6d, 0xbf, 0xff}}},
    {{{   172,    -24,    -31}, 0, {    26,    -34}, {0x04, 0x91, 0xc4, 0xff}}},
    {{{     0,    -26,     37}, 0, {     0,    990}, {0x03, 0x94, 0x42, 0xff}}},
    {{{     0,    -28,    -35}, 0, {   990,    990}, {0x02, 0xff, 0x82, 0xff}}},
    {{{   172,    -24,    -31}, 0, {   954,    -30}, {0x04, 0x91, 0xc4, 0xff}}},
    {{{   172,    -22,     33}, 0, {    38,    -30}, {0x02, 0x03, 0x7e, 0xff}}},
};

// 0x06016D30 - 0x06016D98
const Gfx piranha_plant_seg6_dl_06016D30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06012BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016C70, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016D98 - 0x06016DF8
const Gfx piranha_plant_seg6_dl_06016D98[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06016D30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x06016DF8
static const Vtx piranha_plant_seg6_vertex_06016DF8[] = {
    {{{   -18,     36,      0}, 0, {     0,    990}, {0x02, 0x6f, 0x3d, 0xff}}},
    {{{   -18,    -27,     38}, 0, {   990,    990}, {0x02, 0x94, 0x41, 0xff}}},
    {{{   174,    -25,     34}, 0, {   946,    -12}, {0x02, 0x02, 0x7e, 0xff}}},
    {{{   174,     32,      0}, 0, {     6,    -12}, {0x02, 0x6d, 0xc0, 0xff}}},
    {{{   -18,    -28,    -36}, 0, {   -48,    990}, {0x01, 0xff, 0x82, 0xff}}},
    {{{   -18,     36,      0}, 0, {   994,    990}, {0x02, 0x6f, 0x3d, 0xff}}},
    {{{   174,     32,      0}, 0, {   986,    -30}, {0x02, 0x6d, 0xc0, 0xff}}},
    {{{   174,    -27,    -33}, 0, {    26,      0}, {0x01, 0x91, 0xc3, 0xff}}},
    {{{   -18,    -27,     38}, 0, {   -36,    992}, {0x02, 0x94, 0x41, 0xff}}},
    {{{   -18,    -28,    -36}, 0, {   990,    990}, {0x01, 0xff, 0x82, 0xff}}},
    {{{   174,    -27,    -33}, 0, {   958,      0}, {0x01, 0x91, 0xc3, 0xff}}},
    {{{   174,    -25,     34}, 0, {    12,    -28}, {0x02, 0x02, 0x7e, 0xff}}},
};

// 0x06016EB8 - 0x06016F20
const Gfx piranha_plant_seg6_dl_06016EB8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, piranha_plant_seg6_texture_06012BF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.l, 1),
    gsSPLight(&piranha_plant_seg6_lights_060113B0.a, 2),
    gsSPVertex(piranha_plant_seg6_vertex_06016DF8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06016F20 - 0x06016F80
const Gfx piranha_plant_seg6_dl_06016F20[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(piranha_plant_seg6_dl_06016EB8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
