// Blue Fish

// 0x0301B5C8
static const Lights1 blue_fish_seg3_lights_0301B5C8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0301B5E0
ALIGNED8 static const Texture blue_fish_seg3_texture_0301B5E0[] = {
#include "actors/blue_fish/blue_fish.rgba16.inc.c"
};

// 0x0301BDE0
static const Vtx blue_fish_seg3_vertex_0301BDE0[] = {
    {{{  -108,      1,      0}, 0, {    14,    566}, {0x89, 0x00, 0xd7, 0xff}}},
    {{{   -44,    -13,    -31}, 0, {   286,    688}, {0xeb, 0x89, 0xdc, 0xff}}},
    {{{   -62,    -19,     12}, 0, {   210,    502}, {0xe8, 0x84, 0x0a, 0xff}}},
    {{{   -44,     16,    -31}, 0, {   286,    688}, {0xea, 0x77, 0xdc, 0xff}}},
    {{{   -62,     21,     12}, 0, {   210,    502}, {0xe8, 0x7c, 0x0b, 0xff}}},
    {{{   -47,      1,    -64}, 0, {   274,    830}, {0xbd, 0xff, 0x95, 0xff}}},
    {{{   -97,      1,     23}, 0, {    62,    462}, {0xa2, 0x00, 0x54, 0xff}}},
    {{{    55,      1,    127}, 0, {   712,     -8}, {0x1a, 0x00, 0x7c, 0xff}}},
    {{{    41,     16,     56}, 0, {   656,    298}, {0x43, 0x67, 0x1b, 0xff}}},
    {{{    50,     16,    -43}, 0, {   692,    722}, {0x35, 0x6f, 0xe6, 0xff}}},
    {{{    41,    -14,     56}, 0, {   656,    298}, {0x43, 0x99, 0x1b, 0xff}}},
    {{{    50,    -13,    -43}, 0, {   692,    722}, {0x35, 0x91, 0xe6, 0xff}}},
    {{{    62,      1,    -92}, 0, {   742,    930}, {0x42, 0xff, 0x94, 0xff}}},
    {{{   101,      1,      1}, 0, {   910,    520}, {0x7e, 0x00, 0x01, 0xff}}},
};

// 0x0301BEC0 - 0x0301BFB8
const Gfx blue_fish_seg3_dl_0301BEC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, blue_fish_seg3_texture_0301B5E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&blue_fish_seg3_lights_0301B5C8.l, 1),
    gsSPLight(&blue_fish_seg3_lights_0301B5C8.a, 2),
    gsSPVertex(blue_fish_seg3_vertex_0301BDE0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  0,  3, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 0,  6,  4, 0x0,  5,  1,  0, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  6,  2,  7, 0x0),
    gsSP2Triangles( 7,  8,  4, 0x0,  9,  4,  8, 0x0),
    gsSP2Triangles( 9,  3,  4, 0x0,  2, 10,  7, 0x0),
    gsSP2Triangles( 1, 11,  2, 0x0, 11, 10,  2, 0x0),
    gsSP2Triangles( 1,  5, 12, 0x0, 12,  5,  3, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0,  3,  9, 12, 0x0),
    gsSP2Triangles(12, 11,  1, 0x0,  8,  7, 10, 0x0),
    gsSP2Triangles(13,  9,  8, 0x0,  9, 13, 11, 0x0),
    gsSP2Triangles(11, 13, 10, 0x0, 10, 13,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0301BFB8 - 0x0301C018
const Gfx blue_fish_seg3_dl_0301BFB8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(blue_fish_seg3_dl_0301BEC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0301C018
static const Vtx blue_fish_seg3_vertex_0301C018[] = {
    {{{    67,      0,    -58}, 0, {   736,    728}, {0x7a, 0x00, 0xde, 0xff}}},
    {{{    67,     -3,    -28}, 0, {   704,    574}, {0x53, 0xa3, 0x14, 0xff}}},
    {{{     0,      1,     -3}, 0, {  1022,    356}, {0xa1, 0xf9, 0xad, 0xff}}},
    {{{    67,      4,     27}, 0, {   644,    290}, {0x3f, 0x6d, 0xf3, 0xff}}},
    {{{    53,      0,      0}, 0, {   744,    414}, {0x7e, 0x00, 0xfe, 0xff}}},
    {{{    67,     -3,     27}, 0, {   644,    290}, {0x40, 0x95, 0xeb, 0xff}}},
    {{{     0,      0,      5}, 0, {  1016,    312}, {0x9e, 0xfd, 0x50, 0xff}}},
    {{{    67,      4,    -28}, 0, {   704,    574}, {0x54, 0x5c, 0x15, 0xff}}},
    {{{    67,      0,     57}, 0, {   612,    138}, {0x7a, 0x00, 0x22, 0xff}}},
};

// 0x0301C0A8 - 0x0301C150
const Gfx blue_fish_seg3_dl_0301C0A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, blue_fish_seg3_texture_0301B5E0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&blue_fish_seg3_lights_0301B5C8.l, 1),
    gsSPLight(&blue_fish_seg3_lights_0301B5C8.a, 2),
    gsSPVertex(blue_fish_seg3_vertex_0301C018, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  5,  2, 0x0,  2,  5,  6, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  6,  3, 0x0),
    gsSP2Triangles( 4,  7,  2, 0x0,  2,  7,  0, 0x0),
    gsSP2Triangles( 6,  8,  3, 0x0,  3,  8,  5, 0x0),
    gsSP2Triangles( 3,  5,  4, 0x0,  4,  1,  7, 0x0),
    gsSP2Triangles( 6,  5,  8, 0x0,  1,  0,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0301C150 - 0x0301C1B0
const Gfx blue_fish_seg3_dl_0301C150[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(blue_fish_seg3_dl_0301C0A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
