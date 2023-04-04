// Springboard (unused)

// 0x05000000
static const Lights1 springboard_seg5_lights_05000000 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05000018
ALIGNED8 static const Texture springboard_seg5_texture_05000018[] = {
#include "actors/springboard/springboard_top_unused.rgba16.inc.c"
};

// 0x05000818
ALIGNED8 static const Texture springboard_seg5_texture_05000818[] = {
#include "actors/springboard/springboard_base_unused.rgba16.inc.c"
};

// 0x05001018
static const Vtx springboard_seg5_vertex_05001018[] = {
    {{{   205,      0,      0}, 0, {  2524,    478}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,      0,   -176}, 0, {  1502,  -1292}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    478}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{   102,    -19,   -176}, 0, {  1502,  -1292}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{   102,      0,   -176}, 0, {  1502,  -1292}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{   102,      0,   -176}, 0, {  1502,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,    -19,   -176}, 0, {  -542,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,    -19,   -176}, 0, {  1502,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    -19,      0}, 0, { -1564,    478}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -101,    -19,   -176}, 0, {  -542,  -1292}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   102,    -19,   -176}, 0, {  1502,  -1292}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   102,    -19,    177}, 0, {  1502,   2248}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   205,    -19,      0}, 0, {  2524,    478}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   205,    -19,      0}, 0, {  2524,    478}, {0x6d, 0x00, 0xc0, 0xff}}},
};

// 0x05001118
static const Vtx springboard_seg5_vertex_05001118[] = {
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -101,    -19,   -176}, 0, {  -542,  -1292}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -204,    -19,      0}, 0, { -1564,    478}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    478}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    478}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    478}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{   102,    -19,    177}, 0, {  1502,   2248}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -101,    -19,    177}, 0, {  -542,   2248}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,    -19,      0}, 0, { -1564,    478}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    478}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -204,    -19,      0}, 0, { -1564,    478}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,    -19,    177}, 0, {  -542,   2248}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x93, 0x00, 0x3f, 0xff}}},
};

// 0x05001208
static const Vtx springboard_seg5_vertex_05001208[] = {
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,    -19,    177}, 0, {  -542,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,    -19,    177}, 0, {  1502,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    478}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   102,    -19,    177}, 0, {  1502,   2248}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   205,    -19,      0}, 0, {  2524,    478}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    478}, {0x6d, 0x00, 0x3f, 0xff}}},
};

// 0x050012B8
static const Vtx springboard_seg5_vertex_050012B8[] = {
    {{{   -76,    -55,   -132}, 0, {     0,    990}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{  -153,     56,      0}, 0, {   990,      0}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{   -76,     56,   -132}, 0, {     0,      0}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{   -76,     56,    133}, 0, {   990,      0}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -153,     56,      0}, 0, {     0,      0}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -153,    -55,      0}, 0, {     0,    990}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{   -76,    -55,    133}, 0, {   990,    990}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{    77,     56,    133}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -76,     56,    133}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -76,    -55,    133}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    77,    -55,    133}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,     56,      0}, 0, {   990,      0}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{    77,     56,    133}, 0, {     0,      0}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{    77,    -55,    133}, 0, {     0,    990}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   154,    -55,      0}, 0, {   990,    990}, {0x6d, 0x00, 0x3f, 0xff}}},
};

// 0x050013A8
static const Vtx springboard_seg5_vertex_050013A8[] = {
    {{{    77,     56,   -132}, 0, {   990,      0}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{   154,    -55,      0}, 0, {     0,    990}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{    77,    -55,   -132}, 0, {   990,    990}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{   -76,    -55,   -132}, 0, {     0,    990}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{  -153,    -55,      0}, 0, {   990,    990}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{  -153,     56,      0}, 0, {   990,      0}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{   -76,     56,   -132}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    77,     56,   -132}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    77,    -55,   -132}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -76,    -55,   -132}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   154,     56,      0}, 0, {     0,      0}, {0x6d, 0x00, 0xc1, 0xff}}},
};

// 0x05001458
static const Vtx springboard_seg5_vertex_05001458[] = {
    {{{   102,     20,   -176}, 0, {  1502,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   102,      0,   -176}, 0, {  1502,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -101,     20,   -176}, 0, {  -542,  -1292}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   205,     20,      0}, 0, {  2524,    480}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,     20,   -176}, 0, {  1502,  -1292}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,     20,    177}, 0, {  1502,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,     20,      0}, 0, { -1564,    480}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,     20,      0}, 0, {  2524,    480}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{   102,      0,   -176}, 0, {  1502,  -1292}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{   102,     20,   -176}, 0, {  1502,  -1292}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{  -101,     20,   -176}, 0, {  -542,  -1292}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    480}, {0x6d, 0x00, 0xc0, 0xff}}},
    {{{  -101,     20,   -176}, 0, {  -542,  -1292}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -101,      0,   -176}, 0, {  -542,  -1292}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    480}, {0x93, 0x00, 0xc0, 0xff}}},
};

// 0x05001558
static const Vtx springboard_seg5_vertex_05001558[] = {
    {{{  -101,     20,   -176}, 0, {  -542,  -1292}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    480}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{  -204,     20,      0}, 0, { -1564,    480}, {0x93, 0x00, 0xc0, 0xff}}},
    {{{   102,     20,    177}, 0, {  1502,   2248}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   205,      0,      0}, 0, {  2524,    480}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{   205,     20,      0}, 0, {  2524,    480}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{  -101,     20,    177}, 0, {  -542,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,     20,    177}, 0, {  1502,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x6d, 0x00, 0x3f, 0xff}}},
    {{{  -204,     20,      0}, 0, { -1564,    480}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -101,     20,    177}, 0, {  -542,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   102,     20,    177}, 0, {  1502,   2248}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,     20,      0}, 0, { -1564,    480}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -204,      0,      0}, 0, { -1564,    480}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x93, 0x00, 0x3f, 0xff}}},
};

// 0x05001658
static const Vtx springboard_seg5_vertex_05001658[] = {
    {{{  -204,     20,      0}, 0, { -1564,    480}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,     20,    177}, 0, {  -542,   2248}, {0x93, 0x00, 0x3f, 0xff}}},
    {{{  -101,     20,    177}, 0, {  -542,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -101,      0,    177}, 0, {  -542,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,      0,    177}, 0, {  1502,   2248}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x050016B8 - 0x05001800
const Gfx springboard_checkerboard_seg5_dl_050016B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, springboard_seg5_texture_05000018),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&springboard_seg5_lights_05000000.l, 1),
    gsSPLight(&springboard_seg5_lights_05000000.a, 2),
    gsSPVertex(springboard_seg5_vertex_05001018, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 10, 12, 0x0),
    gsSP2Triangles(13, 12, 14, 0x0,  3, 15,  4, 0x0),
    gsSPVertex(springboard_seg5_vertex_05001118, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  0,  2,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(springboard_seg5_vertex_05001208, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 0,  2,  6, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 7,  9, 10, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x05001800 - 0x05001900
const Gfx springboard_spring_seg5_dl_05001800[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, springboard_seg5_texture_05000818),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&springboard_seg5_lights_05000000.l, 1),
    gsSPLight(&springboard_seg5_lights_05000000.a, 2),
    gsSPVertex(springboard_seg5_vertex_050012B8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(springboard_seg5_vertex_050013A8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0, 10,  1, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x05001900 - 0x05001A28
const Gfx springboard_checkerboard_seg5_dl_05001900[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, springboard_seg5_texture_05000018),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&springboard_seg5_lights_05000000.l, 1),
    gsSPLight(&springboard_seg5_lights_05000000.a, 2),
    gsSPVertex(springboard_seg5_vertex_05001458, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  5, 11,  7, 0x0),
    gsSP2Triangles( 8, 12,  9, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(springboard_seg5_vertex_05001558, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(springboard_seg5_vertex_05001658, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
