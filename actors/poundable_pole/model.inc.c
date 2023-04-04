// Poundable Pole

// 0x06001038
static const Lights1 poundable_pole_seg6_lights_06001038 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06001050
ALIGNED8 static const Texture poundable_pole_seg6_texture_06001050[] = {
#include "actors/poundable_pole/poundable_pole_top.rgba16.inc.c"
};

// 0x06001850
ALIGNED8 static const Texture poundable_pole_seg6_texture_06001850[] = {
#include "actors/poundable_pole/poundable_pole_side.rgba16.inc.c"
};

// 0x06002050
static const Vtx poundable_pole_seg6_vertex_06002050[] = {
    {{{   -38,    410,    -94}, 0, {    78,     78}, {0xdf, 0x5c, 0xb0, 0xff}}},
    {{{   -94,    410,    -38}, 0, {   -86,    478}, {0xa1, 0x49, 0xd9, 0xff}}},
    {{{    95,    410,     39}, 0, {  1046,    478}, {0x39, 0x6e, 0x18, 0xff}}},
    {{{    95,    410,    -38}, 0, {   880,     78}, {0x5f, 0x49, 0xd9, 0xff}}},
    {{{    39,    410,    -94}, 0, {   478,    -88}, {0x2d, 0x2a, 0x92, 0xff}}},
    {{{   -94,    410,     39}, 0, {    78,    880}, {0xa1, 0x49, 0x27, 0xff}}},
    {{{   -38,    410,     95}, 0, {   480,   1044}, {0xd9, 0x49, 0x5f, 0xff}}},
    {{{    39,    410,     95}, 0, {   880,    880}, {0x2d, 0x2a, 0x6e, 0xff}}},
};

// 0x060020D0
static const Vtx poundable_pole_seg6_vertex_060020D0[] = {
    {{{   -71,    -19,     72}, 0, {     0,    990}, {0xa7, 0xff, 0x59, 0xff}}},
    {{{   -38,    410,     95}, 0, {   990,  -1012}, {0xd9, 0x49, 0x5f, 0xff}}},
    {{{   -94,    410,     39}, 0, {     0,  -1012}, {0xa1, 0x49, 0x27, 0xff}}},
    {{{   -71,    -19,    -71}, 0, {   -26,    990}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  -101,    -19,      0}, 0, {   990,    990}, {0x82, 0xff, 0x00, 0xff}}},
    {{{   -94,    410,    -38}, 0, {   990,  -1054}, {0xa1, 0x49, 0xd9, 0xff}}},
    {{{   -38,    410,    -94}, 0, {   -26,  -1054}, {0xdf, 0x5c, 0xb0, 0xff}}},
    {{{     0,    -19,   -101}, 0, {   -26,    990}, {0x00, 0xff, 0x82, 0xff}}},
    {{{   -71,    -19,    -71}, 0, {   990,    990}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   -38,    410,    -94}, 0, {   990,  -1054}, {0xdf, 0x5c, 0xb0, 0xff}}},
    {{{    39,    410,    -94}, 0, {   -26,  -1054}, {0x2d, 0x2a, 0x92, 0xff}}},
    {{{   -94,    410,     39}, 0, {   990,  -1054}, {0xa1, 0x49, 0x27, 0xff}}},
    {{{  -101,    -19,      0}, 0, {     0,    990}, {0x82, 0xff, 0x00, 0xff}}},
    {{{   -71,    -19,     72}, 0, {   990,    990}, {0xa7, 0xff, 0x59, 0xff}}},
    {{{   -94,    410,    -38}, 0, {     0,  -1054}, {0xa1, 0x49, 0xd9, 0xff}}},
};

// 0x060021C0
static const Vtx poundable_pole_seg6_vertex_060021C0[] = {
    {{{   102,    -19,      0}, 0, {   -26,    990}, {0x7e, 0xff, 0x00, 0xff}}},
    {{{    95,    410,    -38}, 0, {   990,  -1054}, {0x5f, 0x49, 0xd9, 0xff}}},
    {{{    95,    410,     39}, 0, {   -26,  -1054}, {0x39, 0x6e, 0x18, 0xff}}},
    {{{   -71,    -19,     72}, 0, {     0,    990}, {0xa7, 0xff, 0x59, 0xff}}},
    {{{     0,    -19,    102}, 0, {   990,    990}, {0x00, 0xff, 0x7e, 0xff}}},
    {{{   -38,    410,     95}, 0, {   990,  -1012}, {0xd9, 0x49, 0x5f, 0xff}}},
    {{{     0,    -19,    102}, 0, {   -26,    990}, {0x00, 0xff, 0x7e, 0xff}}},
    {{{    39,    410,     95}, 0, {   990,  -1054}, {0x2d, 0x2a, 0x6e, 0xff}}},
    {{{   -38,    410,     95}, 0, {   -26,  -1054}, {0xd9, 0x49, 0x5f, 0xff}}},
    {{{    72,    -19,     72}, 0, {   990,    990}, {0x59, 0xff, 0x59, 0xff}}},
    {{{    95,    410,     39}, 0, {   990,  -1054}, {0x39, 0x6e, 0x18, 0xff}}},
    {{{    72,    -19,     72}, 0, {     0,    990}, {0x59, 0xff, 0x59, 0xff}}},
    {{{   102,    -19,      0}, 0, {   990,    990}, {0x7e, 0xff, 0x00, 0xff}}},
    {{{    39,    410,     95}, 0, {     0,  -1054}, {0x2d, 0x2a, 0x6e, 0xff}}},
    {{{    72,    -19,    -71}, 0, {   990,    990}, {0x59, 0xff, 0xa7, 0xff}}},
};

// 0x060022B0
static const Vtx poundable_pole_seg6_vertex_060022B0[] = {
    {{{    39,    410,    -94}, 0, {   990,  -1054}, {0x2d, 0x2a, 0x92, 0xff}}},
    {{{    95,    410,    -38}, 0, {   -26,  -1054}, {0x5f, 0x49, 0xd9, 0xff}}},
    {{{    72,    -19,    -71}, 0, {   -26,    990}, {0x59, 0xff, 0xa7, 0xff}}},
    {{{     0,    -19,   -101}, 0, {   990,    990}, {0x00, 0xff, 0x82, 0xff}}},
};

// 0x060022F0 - 0x06002358
const Gfx poundable_pole_seg6_dl_060022F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, poundable_pole_seg6_texture_06001050),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&poundable_pole_seg6_lights_06001038.l, 1),
    gsSPLight(&poundable_pole_seg6_lights_06001038.a, 2),
    gsSPVertex(poundable_pole_seg6_vertex_06002050, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 2,  5,  6, 0x0,  2,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002358 - 0x06002410
const Gfx poundable_pole_seg6_dl_06002358[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, poundable_pole_seg6_texture_06001850),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(poundable_pole_seg6_vertex_060020D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 11, 14, 0x0),
    gsSPVertex(poundable_pole_seg6_vertex_060021C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 10, 13, 0x0),
    gsSP1Triangle( 1,  0, 14, 0x0),
    gsSPVertex(poundable_pole_seg6_vertex_060022B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002410 - 0x06002490
const Gfx poundable_pole_seg6_dl_06002410[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(poundable_pole_seg6_dl_060022F0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(poundable_pole_seg6_dl_06002358),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
