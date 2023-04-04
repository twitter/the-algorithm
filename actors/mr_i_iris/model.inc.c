// Mr I (Iris)

// 0x06002130
static const Vtx mr_i_iris_seg6_vertex_06002130[] = {
    {{{   -50,    -50,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    50,    -50,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    50,     50,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -50,     50,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002170
ALIGNED8 static const Texture mr_i_iris_seg6_texture_06002170[] = {
#include "actors/mr_i_iris/mr_i_iris_open.rgba16.inc.c"
};

// 0x06002970
ALIGNED8 static const Texture mr_i_iris_seg6_texture_06002970[] = {
#include "actors/mr_i_iris/mr_i_iris_mostly_open.rgba16.inc.c"
};

// 0x06003170
ALIGNED8 static const Texture mr_i_iris_seg6_texture_06003170[] = {
#include "actors/mr_i_iris/mr_i_iris_mostly_closed.rgba16.inc.c"
};

// 0x06003970
ALIGNED8 static const Texture mr_i_iris_seg6_texture_06003970[] = {
#include "actors/mr_i_iris/mr_i_iris_closed.rgba16.inc.c"
};

// 0x06004170 - 0x060041D8
const Gfx mr_i_iris_seg6_dl_06004170[] = {
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPVertex(mr_i_iris_seg6_vertex_06002130, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x060041D8 - 0x060041F0
const Gfx mr_i_iris_seg6_dl_060041D8[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_iris_seg6_texture_06002170),
    gsSPBranchList(mr_i_iris_seg6_dl_06004170),
};

// 0x060041F0 - 0x06004208
const Gfx mr_i_iris_seg6_dl_060041F0[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_iris_seg6_texture_06002970),
    gsSPBranchList(mr_i_iris_seg6_dl_06004170),
};

// 0x06004208 - 0x06004220
const Gfx mr_i_iris_seg6_dl_06004208[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_iris_seg6_texture_06003170),
    gsSPBranchList(mr_i_iris_seg6_dl_06004170),
};

// 0x06004220 - 0x06004238
const Gfx mr_i_iris_seg6_dl_06004220[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mr_i_iris_seg6_texture_06003970),
    gsSPBranchList(mr_i_iris_seg6_dl_06004170),
};
