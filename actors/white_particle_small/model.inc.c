// White Particle Small

// 0x04032700
static const Vtx white_particle_small_vertex[] = {
    {{{    -4,      0,      0}, 0, {     0,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     4,      0,      0}, 0, {   960,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     4,      8,      0}, 0, {   960,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    -4,      8,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x04032740
static const Vtx white_particle_small_unused_vertex[] = {
    {{{    -4,      0,      0}, 0, {     0,    960}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     4,      0,      0}, 0, {   960,    960}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     4,      8,      0}, 0, {   960,      0}, {0xff, 0x00, 0x00, 0xff}}},
    {{{    -4,      8,      0}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0xff}}},
};

// 0x04032780
ALIGNED8 static const Texture white_particle_small_texture[] = {
#include "actors/white_particle_small/small_snow_particle.rgba16.inc.c"
};

// 0x04032980 - 0x040329E0
const Gfx white_particle_small_dl_begin[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPLoadTextureBlock(white_particle_small_texture, G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 16, 0, G_TX_CLAMP, G_TX_CLAMP, 4, 4, G_TX_NOLOD, G_TX_NOLOD),
    gsSPEndDisplayList(),
};

// 0x040329E0 - 0x04032A18
const Gfx white_particle_small_dl_end[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPPipeSync(),
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x04032A18 - 0x04032A30
const Gfx white_particle_small_dl[] = {
    gsSPDisplayList(white_particle_small_dl_begin),
    gsSPVertex(white_particle_small_vertex, 4, 0),
    gsSPBranchList(white_particle_small_dl_end),
};

// 0x04032A30 - 0x04032A48 # Unused, has different vertex color
const Gfx white_particle_small_unused_dl[] = {
    gsSPDisplayList(white_particle_small_dl_begin),
    gsSPVertex(white_particle_small_unused_vertex, 4, 0),
    gsSPBranchList(white_particle_small_dl_end),
};
