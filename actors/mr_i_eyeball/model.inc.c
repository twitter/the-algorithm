// Mr I (white eyeball)

// 0x06000000
static const Vtx mr_i_eyeball_seg6_vertex_06000000[] = {
    {{{  -100,      0,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    200,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -100,    200,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   100,      0,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   100,    200,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    200,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06000080
ALIGNED8 static const Texture mr_i_eyeball_seg6_texture_06000080[] = {
#include "actors/mr_i_eyeball/mr_i_eyeball_left_side.rgba16.inc.c"
};

// 0x06001080
ALIGNED8 static const Texture mr_i_eyeball_seg6_texture_06001080[] = {
#include "actors/mr_i_eyeball/mr_i_eyeball_right_side.rgba16.inc.c"
};

// 0x06002080 - 0x06002128
const Gfx mr_i_eyeball_seg6_dl_06002080[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(mr_i_eyeball_seg6_vertex_06000000, 8, 0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_eyeball_seg6_texture_06000080),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_eyeball_seg6_texture_06001080),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
