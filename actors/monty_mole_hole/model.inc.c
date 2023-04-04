// Monty Mole Hole

// 0x05000000
static const Vtx monty_mole_hole_seg5_vertex_05000000[] = {
    {{{   -40,      0,    -40}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,      0,     40}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,      0,     40}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,      0,    -40}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05000040
ALIGNED8 static const Texture monty_mole_hole_seg5_texture_05000040[] = {
#include "actors/monty_mole_hole/monty_mole_hole.ia16.inc.c"
};

// 0x05000840 - 0x050008D8
const Gfx monty_mole_hole_seg5_dl_05000840[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsDPLoadTextureBlock(monty_mole_hole_seg5_texture_05000040, G_IM_FMT_IA, G_IM_SIZ_16b, 32, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(monty_mole_hole_seg5_vertex_05000000, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
