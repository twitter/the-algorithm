// Flame

// 0x030172E0
static const Vtx flame_seg3_vertex_030172E0[] = {
    {{{   -25,    -25,      0}, 0, {     0,    992}, {0x00, 0x00, 0x00, 0x00}}},
    {{{    25,    -25,      0}, 0, {   992,    992}, {0x00, 0x00, 0x00, 0x00}}},
    {{{    25,     25,      0}, 0, {   992,      0}, {0x00, 0x00, 0x00, 0x00}}},
    {{{   -25,     25,      0}, 0, {     0,      0}, {0x00, 0x00, 0x00, 0x00}}},
};

// 0x03017320
ALIGNED8 static const Texture flame_seg3_texture_03017320[] = {
#include "actors/flame/flame_0.ia16.inc.c"
};

// 0x03017B20
ALIGNED8 static const Texture flame_seg3_texture_03017B20[] = {
#include "actors/flame/flame_1.ia16.inc.c"
};

// 0x03018320
ALIGNED8 static const Texture flame_seg3_texture_03018320[] = {
#include "actors/flame/flame_2.ia16.inc.c"
};

// 0x03018B20
ALIGNED8 static const Texture flame_seg3_texture_03018B20[] = {
#include "actors/flame/flame_3.ia16.inc.c"
};

// 0x03019320
ALIGNED8 static const Texture flame_seg3_texture_03019320[] = {
#include "actors/flame/flame_4.ia16.inc.c"
};

// 0x03019B20
ALIGNED8 static const Texture flame_seg3_texture_03019B20[] = {
#include "actors/flame/flame_5.ia16.inc.c"
};

// 0x0301A320
ALIGNED8 static const Texture flame_seg3_texture_0301A320[] = {
#include "actors/flame/flame_6.ia16.inc.c"
};

// 0x0301AB20
ALIGNED8 static const Texture flame_seg3_texture_0301AB20[] = {
#include "actors/flame/flame_7.ia16.inc.c"
};

// 0x0301B320 - 0x0301B3B0
const Gfx flame_seg3_dl_0301B320[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 50, 0, 200),
    gsDPSetCombineMode(G_CC_FADEA, G_CC_FADEA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(flame_seg3_vertex_030172E0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0301B3B0 - 0x0301B3C8
const Gfx flame_seg3_dl_0301B3B0[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03017320),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B3C8 - 0x0301B3E0
const Gfx flame_seg3_dl_0301B3C8[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03017B20),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B3E0 - 0x0301B3F8
const Gfx flame_seg3_dl_0301B3E0[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03018320),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B3F8 - 0x0301B410
const Gfx flame_seg3_dl_0301B3F8[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03018B20),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B410 - 0x0301B428
const Gfx flame_seg3_dl_0301B410[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03019320),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B428 - 0x0301B440
const Gfx flame_seg3_dl_0301B428[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03019B20),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B440 - 0x0301B458
const Gfx flame_seg3_dl_0301B440[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_0301A320),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B458 - 0x0301B470
const Gfx flame_seg3_dl_0301B458[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_0301AB20),
    gsSPBranchList(flame_seg3_dl_0301B320),
};

// 0x0301B470 - 0x0301B500
const Gfx flame_seg3_dl_0301B470[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetEnvColor(100, 100, 255, 255),
    gsDPSetCombineMode(G_CC_FADEA, G_CC_FADEA),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(flame_seg3_vertex_030172E0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0301B500 - 0x0301B518
const Gfx flame_seg3_dl_0301B500[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03017320),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B518 - 0x0301B530
const Gfx flame_seg3_dl_0301B518[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03017B20),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B530 - 0x0301B548
const Gfx flame_seg3_dl_0301B530[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03018320),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B548 - 0x0301B560
const Gfx flame_seg3_dl_0301B548[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03018B20),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B560 - 0x0301B578
const Gfx flame_seg3_dl_0301B560[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03019320),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B578 - 0x0301B590
const Gfx flame_seg3_dl_0301B578[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_03019B20),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B590 - 0x0301B5A8
const Gfx flame_seg3_dl_0301B590[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_0301A320),
    gsSPBranchList(flame_seg3_dl_0301B470),
};

// 0x0301B5A8 - 0x0301B5C0
const Gfx flame_seg3_dl_0301B5A8[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, flame_seg3_texture_0301AB20),
    gsSPBranchList(flame_seg3_dl_0301B470),
};
