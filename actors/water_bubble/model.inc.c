// Water Bubble

// 0x0500FE68
static const Lights1 water_bubble_seg5_lights_0500FE68 = gdSPDefLights1(
    0xbf, 0xbf, 0xbf,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500FE80
ALIGNED8 static const Texture water_bubble_seg5_texture_0500FE80[] = {
#include "actors/water_bubble/water_bubble.rgba16.inc.c"
};

// 0x05010680
static const Vtx water_bubble_seg5_vertex_05010680[] = {
    {{{   -20,    -64,    -33}, 0, {     0,      0}, {0xdd, 0x95, 0xc7, 0xff}}},
    {{{     0,    -76,      0}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   -44,    -61,      0}, 0, {     0,      0}, {0xb6, 0x9a, 0x00, 0xff}}},
    {{{   -20,    -64,     34}, 0, {     0,      0}, {0xdf, 0x98, 0x3f, 0xff}}},
    {{{   -55,    -39,     34}, 0, {     0,      0}, {0xa5, 0xbd, 0x37, 0xff}}},
    {{{    21,    -64,     34}, 0, {     0,      0}, {0x22, 0x94, 0x38, 0xff}}},
    {{{   -11,    -37,    -64}, 0, {     0,      0}, {0xeb, 0xbe, 0x96, 0xff}}},
    {{{   -55,    -39,    -33}, 0, {     0,      0}, {0xa8, 0xbf, 0xc0, 0xff}}},
    {{{   -39,      0,    -64}, 0, {     0,      0}, {0xbc, 0x00, 0x96, 0xff}}},
    {{{   -68,      0,    -33}, 0, {     0,      0}, {0x8f, 0x00, 0xc8, 0xff}}},
    {{{     0,      0,    -76}, 0, {     0,      0}, {0x00, 0x00, 0x82, 0xff}}},
    {{{    56,    -39,    -33}, 0, {     0,      0}, {0x5b, 0xbd, 0xc9, 0xff}}},
    {{{    73,    -23,      0}, 0, {     0,      0}, {0x78, 0xd9, 0x00, 0xff}}},
    {{{    45,    -61,      0}, 0, {     0,      0}, {0x4a, 0x9a, 0x00, 0xff}}},
    {{{    56,    -39,     34}, 0, {     0,      0}, {0x58, 0xbf, 0x3f, 0xff}}},
    {{{    69,      0,     34}, 0, {     0,      0}, {0x72, 0x00, 0x37, 0xff}}},
};

// 0x05010780
static const Vtx water_bubble_seg5_vertex_05010780[] = {
    {{{    33,    -23,    -64}, 0, {     0,      0}, {0x37, 0xd7, 0x96, 0xff}}},
    {{{    56,    -39,    -33}, 0, {     0,      0}, {0x5b, 0xbd, 0xc9, 0xff}}},
    {{{    21,    -64,    -33}, 0, {     0,      0}, {0x21, 0x98, 0xc0, 0xff}}},
    {{{   -11,    -37,    -64}, 0, {     0,      0}, {0xeb, 0xbe, 0x96, 0xff}}},
    {{{   -20,    -64,    -33}, 0, {     0,      0}, {0xdd, 0x95, 0xc7, 0xff}}},
    {{{     0,      0,    -76}, 0, {     0,      0}, {0x00, 0x00, 0x82, 0xff}}},
    {{{    45,     62,      0}, 0, {     0,      0}, {0x4a, 0x66, 0x00, 0xff}}},
    {{{    56,     40,     34}, 0, {     0,      0}, {0x58, 0x40, 0x40, 0xff}}},
    {{{    73,     24,      0}, 0, {     0,      0}, {0x78, 0x27, 0x00, 0xff}}},
    {{{    69,      0,     34}, 0, {     0,      0}, {0x72, 0x00, 0x37, 0xff}}},
    {{{   -44,     62,      0}, 0, {     0,      0}, {0xb6, 0x66, 0x00, 0xff}}},
    {{{   -20,     65,     34}, 0, {     0,      0}, {0xdf, 0x68, 0x3f, 0xff}}},
    {{{     0,     77,      0}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    21,     65,     34}, 0, {     0,      0}, {0x22, 0x6c, 0x38, 0xff}}},
    {{{    56,     40,    -33}, 0, {     0,      0}, {0x5b, 0x43, 0xc8, 0xff}}},
};

// 0x05010870
static const Vtx water_bubble_seg5_vertex_05010870[] = {
    {{{    33,     24,    -64}, 0, {     0,      0}, {0x37, 0x29, 0x96, 0xff}}},
    {{{    56,     40,    -33}, 0, {     0,      0}, {0x5b, 0x43, 0xc8, 0xff}}},
    {{{    69,      0,    -33}, 0, {     0,      0}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{     0,      0,    -76}, 0, {     0,      0}, {0x00, 0x00, 0x82, 0xff}}},
    {{{    33,    -23,    -64}, 0, {     0,      0}, {0x37, 0xd7, 0x96, 0xff}}},
    {{{    56,    -39,    -33}, 0, {     0,      0}, {0x5b, 0xbd, 0xc9, 0xff}}},
    {{{     0,     77,      0}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{   -20,     65,     34}, 0, {     0,      0}, {0xdf, 0x68, 0x3f, 0xff}}},
    {{{    21,     65,     34}, 0, {     0,      0}, {0x22, 0x6c, 0x38, 0xff}}},
    {{{   -20,     65,    -33}, 0, {     0,      0}, {0xdd, 0x6b, 0xc7, 0xff}}},
    {{{   -44,     62,      0}, 0, {     0,      0}, {0xb6, 0x66, 0x00, 0xff}}},
    {{{   -55,     40,     34}, 0, {     0,      0}, {0xa4, 0x43, 0x37, 0xff}}},
    {{{   -11,     38,    -64}, 0, {     0,      0}, {0xeb, 0x41, 0x96, 0xff}}},
    {{{    21,     65,    -33}, 0, {     0,      0}, {0x21, 0x68, 0xc0, 0xff}}},
};

// 0x05010950
static const Vtx water_bubble_seg5_vertex_05010950[] = {
    {{{   -72,    -23,      0}, 0, {     0,      0}, {0x88, 0xd9, 0x00, 0xff}}},
    {{{   -68,      0,     34}, 0, {     0,      0}, {0x92, 0x00, 0x3f, 0xff}}},
    {{{   -72,     24,      0}, 0, {     0,      0}, {0x88, 0x27, 0x00, 0xff}}},
    {{{   -55,     40,     34}, 0, {     0,      0}, {0xa4, 0x43, 0x37, 0xff}}},
    {{{   -68,      0,    -33}, 0, {     0,      0}, {0x8f, 0x00, 0xc8, 0xff}}},
    {{{   -55,    -39,     34}, 0, {     0,      0}, {0xa5, 0xbd, 0x37, 0xff}}},
    {{{   -39,      0,    -64}, 0, {     0,      0}, {0xbc, 0x00, 0x96, 0xff}}},
    {{{   -55,     40,    -33}, 0, {     0,      0}, {0xa8, 0x40, 0xc0, 0xff}}},
    {{{     0,      0,    -76}, 0, {     0,      0}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   -11,     38,    -64}, 0, {     0,      0}, {0xeb, 0x41, 0x96, 0xff}}},
    {{{   -20,     65,    -33}, 0, {     0,      0}, {0xdd, 0x6b, 0xc7, 0xff}}},
    {{{    21,    -64,     34}, 0, {     0,      0}, {0x22, 0x94, 0x38, 0xff}}},
    {{{     0,    -76,      0}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{    45,    -61,      0}, 0, {     0,      0}, {0x4a, 0x9a, 0x00, 0xff}}},
    {{{    21,    -64,    -33}, 0, {     0,      0}, {0x21, 0x98, 0xc0, 0xff}}},
    {{{    56,    -39,    -33}, 0, {     0,      0}, {0x5b, 0xbd, 0xc9, 0xff}}},
};

// 0x05010A50
static const Vtx water_bubble_seg5_vertex_05010A50[] = {
    {{{     0,    -76,      0}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   -20,    -64,    -33}, 0, {     0,      0}, {0xdd, 0x95, 0xc7, 0xff}}},
    {{{    21,    -64,    -33}, 0, {     0,      0}, {0x21, 0x98, 0xc0, 0xff}}},
    {{{    12,    -37,     65}, 0, {     0,      0}, {0x14, 0xbe, 0x6a, 0xff}}},
    {{{    21,    -64,     34}, 0, {     0,      0}, {0x22, 0x94, 0x38, 0xff}}},
    {{{    56,    -39,     34}, 0, {     0,      0}, {0x58, 0xbf, 0x3f, 0xff}}},
    {{{    40,      0,     65}, 0, {     0,      0}, {0x44, 0x00, 0x6b, 0xff}}},
    {{{    69,      0,     34}, 0, {     0,      0}, {0x72, 0x00, 0x37, 0xff}}},
    {{{     0,      0,     77}, 0, {     0,      0}, {0x00, 0x00, 0x7e, 0xff}}},
    {{{   -55,    -39,     34}, 0, {     0,      0}, {0xa5, 0xbd, 0x37, 0xff}}},
    {{{   -72,    -23,      0}, 0, {     0,      0}, {0x88, 0xd9, 0x00, 0xff}}},
    {{{   -44,    -61,      0}, 0, {     0,      0}, {0xb6, 0x9a, 0x00, 0xff}}},
    {{{   -55,    -39,    -33}, 0, {     0,      0}, {0xa8, 0xbf, 0xc0, 0xff}}},
    {{{   -68,      0,    -33}, 0, {     0,      0}, {0x8f, 0x00, 0xc8, 0xff}}},
    {{{   -32,    -23,     65}, 0, {     0,      0}, {0xc8, 0xd7, 0x6a, 0xff}}},
    {{{   -20,    -64,     34}, 0, {     0,      0}, {0xdf, 0x98, 0x3f, 0xff}}},
};

// 0x05010B50
static const Vtx water_bubble_seg5_vertex_05010B50[] = {
    {{{   -55,     40,     34}, 0, {     0,      0}, {0xa4, 0x43, 0x37, 0xff}}},
    {{{   -44,     62,      0}, 0, {     0,      0}, {0xb6, 0x66, 0x00, 0xff}}},
    {{{   -72,     24,      0}, 0, {     0,      0}, {0x88, 0x27, 0x00, 0xff}}},
    {{{   -55,     40,    -33}, 0, {     0,      0}, {0xa8, 0x40, 0xc0, 0xff}}},
    {{{   -68,      0,    -33}, 0, {     0,      0}, {0x8f, 0x00, 0xc8, 0xff}}},
    {{{   -20,     65,    -33}, 0, {     0,      0}, {0xdd, 0x6b, 0xc7, 0xff}}},
    {{{   -32,     24,     65}, 0, {     0,      0}, {0xc8, 0x29, 0x6a, 0xff}}},
    {{{   -68,      0,     34}, 0, {     0,      0}, {0x92, 0x00, 0x3f, 0xff}}},
    {{{   -32,    -23,     65}, 0, {     0,      0}, {0xc8, 0xd7, 0x6a, 0xff}}},
    {{{   -55,    -39,     34}, 0, {     0,      0}, {0xa5, 0xbd, 0x37, 0xff}}},
    {{{     0,      0,     77}, 0, {     0,      0}, {0x00, 0x00, 0x7e, 0xff}}},
    {{{     0,     77,      0}, 0, {     0,      0}, {0x00, 0x7e, 0x00, 0xff}}},
    {{{    21,     65,    -33}, 0, {     0,      0}, {0x21, 0x68, 0xc0, 0xff}}},
    {{{    45,     62,      0}, 0, {     0,      0}, {0x4a, 0x66, 0x00, 0xff}}},
    {{{    21,     65,     34}, 0, {     0,      0}, {0x22, 0x6c, 0x38, 0xff}}},
    {{{    56,     40,    -33}, 0, {     0,      0}, {0x5b, 0x43, 0xc8, 0xff}}},
};

// 0x05010C50
static const Vtx water_bubble_seg5_vertex_05010C50[] = {
    {{{    12,     38,     65}, 0, {     0,      0}, {0x15, 0x41, 0x6a, 0xff}}},
    {{{    21,     65,     34}, 0, {     0,      0}, {0x22, 0x6c, 0x38, 0xff}}},
    {{{   -20,     65,     34}, 0, {     0,      0}, {0xdf, 0x68, 0x3f, 0xff}}},
    {{{     0,      0,     77}, 0, {     0,      0}, {0x00, 0x00, 0x7e, 0xff}}},
    {{{   -32,     24,     65}, 0, {     0,      0}, {0xc8, 0x29, 0x6a, 0xff}}},
    {{{   -55,     40,     34}, 0, {     0,      0}, {0xa4, 0x43, 0x37, 0xff}}},
    {{{    73,    -23,      0}, 0, {     0,      0}, {0x78, 0xd9, 0x00, 0xff}}},
    {{{    69,      0,    -33}, 0, {     0,      0}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{    73,     24,      0}, 0, {     0,      0}, {0x78, 0x27, 0x00, 0xff}}},
    {{{    56,     40,    -33}, 0, {     0,      0}, {0x5b, 0x43, 0xc8, 0xff}}},
    {{{    69,      0,     34}, 0, {     0,      0}, {0x72, 0x00, 0x37, 0xff}}},
    {{{    56,     40,     34}, 0, {     0,      0}, {0x58, 0x40, 0x40, 0xff}}},
    {{{    56,    -39,    -33}, 0, {     0,      0}, {0x5b, 0xbd, 0xc9, 0xff}}},
    {{{    40,      0,     65}, 0, {     0,      0}, {0x44, 0x00, 0x6b, 0xff}}},
};

// 0x05010D30 - 0x05011000
const Gfx water_bubble_seg5_dl_05010D30[] = {
    gsSPLight(&water_bubble_seg5_lights_0500FE68.l, 1),
    gsSPLight(&water_bubble_seg5_lights_0500FE68.a, 2),
    gsSPVertex(water_bubble_seg5_vertex_05010680, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  1,  5,  3, 0x0),
    gsSP2Triangles( 6,  0,  7, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10,  6,  8, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 12, 14, 13, 0x0),
    gsSP2Triangles(12, 15, 14, 0x0, 13, 14,  5, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010780, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  2,  4, 0x0,  5,  0,  3, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0,  6, 13,  7, 0x0),
    gsSP1Triangle(14,  6,  8, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010870, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(10, 11,  7, 0x0, 12,  9, 13, 0x0),
    gsSP2Triangles( 3, 12,  0, 0x0, 12, 13,  0, 0x0),
    gsSP1Triangle( 0, 13,  1, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010950, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 6,  7,  9, 0x0,  9,  7, 10, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0, 12, 14, 13, 0x0),
    gsSP1Triangle(13, 14, 15, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010A50, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  5,  7, 0x0,  3,  5,  6, 0x0),
    gsSP2Triangles( 8,  3,  6, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(10, 12, 11, 0x0, 11, 12,  1, 0x0),
    gsSP2Triangles(10, 13, 12, 0x0, 14,  9, 15, 0x0),
    gsSP2Triangles(14, 15,  3, 0x0,  3, 15,  4, 0x0),
    gsSP1Triangle( 8, 14,  3, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010B50, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  1,  5,  3, 0x0),
    gsSP2Triangles( 6,  0,  7, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10,  6,  8, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles(11, 12,  5, 0x0, 13, 12, 11, 0x0),
    gsSP2Triangles(14, 13, 11, 0x0, 13, 15, 12, 0x0),
    gsSPVertex(water_bubble_seg5_vertex_05010C50, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  4,  2,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10,  6,  8, 0x0,  0, 11,  1, 0x0),
    gsSP2Triangles( 6, 12,  7, 0x0, 13, 10, 11, 0x0),
    gsSP2Triangles( 3, 13,  0, 0x0, 13, 11,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05011000 - 0x05011098
const Gfx water_bubble_seg5_dl_05011000[] = {
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_MODULATERGBFADE),
    gsDPSetEnvColor(255, 255, 255, 205),
    gsDPLoadTextureBlock(water_bubble_seg5_texture_0500FE80, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_WRAP | G_TX_NOMIRROR, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0x07C0, 0x07C0, 0, G_TX_RENDERTILE, G_ON),
    gsSPDisplayList(water_bubble_seg5_dl_05010D30),
    gsSPTexture(0x07C0, 0x07C0, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_TEXTURE_GEN),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
