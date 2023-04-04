// Yoshi Egg (unused)

// 0x05005778
static const Vtx yoshi_egg_seg5_vertex_05005778[] = {
    {{{  -128,      0,      0}, 0, {     0,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{   128,      0,      0}, 0, {  1984,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{   128,    256,      0}, 0, {  1984,      0}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{  -128,    256,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0x00}}},
};

// 0x050057B8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_050057B8[] = {
#include "actors/yoshi_egg/yoshi_egg_0_unused.rgba16.inc.c"
};

// 0x05005FB8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_05005FB8[] = {
#include "actors/yoshi_egg/yoshi_egg_1_unused.rgba16.inc.c"
};

// 0x050067B8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_050067B8[] = {
#include "actors/yoshi_egg/yoshi_egg_2_unused.rgba16.inc.c"
};

// 0x05006FB8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_05006FB8[] = {
#include "actors/yoshi_egg/yoshi_egg_3_unused.rgba16.inc.c"
};

// 0x050077B8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_050077B8[] = {
#include "actors/yoshi_egg/yoshi_egg_4_unused.rgba16.inc.c"
};

// 0x05007FB8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_05007FB8[] = {
#include "actors/yoshi_egg/yoshi_egg_5_unused.rgba16.inc.c"
};

// 0x050087B8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_050087B8[] = {
#include "actors/yoshi_egg/yoshi_egg_6_unused.rgba16.inc.c"
};

// 0x05008FB8
ALIGNED8 static const Texture yoshi_egg_seg5_texture_05008FB8[] = {
#include "actors/yoshi_egg/yoshi_egg_7_unused.rgba16.inc.c"
};

// 0x050097B8 - 0x05009820
const Gfx yoshi_egg_seg5_dl_050097B8[] = {
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPVertex(yoshi_egg_seg5_vertex_05005778, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x05009820 - 0x05009838
const Gfx yoshi_egg_seg5_dl_05009820[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_05008FB8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x05009838 - 0x05009850
const Gfx yoshi_egg_seg5_dl_05009838[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_050087B8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x05009850 - 0x05009868
const Gfx yoshi_egg_seg5_dl_05009850[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_05007FB8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x05009868 - 0x05009880
const Gfx yoshi_egg_seg5_dl_05009868[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_050077B8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x05009880 - 0x05009898
const Gfx yoshi_egg_seg5_dl_05009880[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_05006FB8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x05009898 - 0x050098B0
const Gfx yoshi_egg_seg5_dl_05009898[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_050067B8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x050098B0 - 0x050098C8
const Gfx yoshi_egg_seg5_dl_050098B0[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_05005FB8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};

// 0x050098C8 - 0x050098E0
const Gfx yoshi_egg_seg5_dl_050098C8[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yoshi_egg_seg5_texture_050057B8),
    gsSPBranchList(yoshi_egg_seg5_dl_050097B8),
};
