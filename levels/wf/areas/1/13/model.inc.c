// 0x07009900 - 0x07009918
static const Lights1 wf_seg7_lights_07009900 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009918 - 0x07009958
static const Vtx wf_seg7_vertex_07009918[] = {
    {{{   794,     38,   -255}, 0, { 22452,   6606}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,     38,   -255}, 0, { 20408,   6606}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   128,     38,    512}, 0, { 20408,   8650}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   794,     38,    512}, 0, { 22452,   8650}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07009958 - 0x07009A48
static const Vtx wf_seg7_vertex_07009958[] = {
    {{{   794,    -89,    512}, 0, { -3096,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   128,    -89,   -255}, 0, {   990,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   794,    -89,   -255}, 0, { -3096,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   794,     38,    512}, 0, {     0,    478}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   794,    -89,    512}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   794,    -89,   -255}, 0, {  2012,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   794,     38,   -255}, 0, {  2012,    478}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   128,     38,    512}, 0, {     0,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   128,    -89,    512}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   794,    -89,    512}, 0, {  2012,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   794,     38,    512}, 0, {  2012,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   128,     38,   -255}, 0, { -1052,    480}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,    -89,   -255}, 0, { -1052,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,    -89,    512}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   128,     38,    512}, 0, {   990,    478}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07009A48 - 0x07009AB8
static const Vtx wf_seg7_vertex_07009A48[] = {
    {{{   794,     38,   -255}, 0, {     0,    478}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,    -89,   -255}, 0, {  2012,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   128,     38,   -255}, 0, {  2012,    480}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   794,    -89,    512}, 0, { -3096,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   128,    -89,    512}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   128,    -89,   -255}, 0, {   990,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   794,    -89,   -255}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07009AB8 - 0x07009BA8
static const Vtx wf_seg7_vertex_07009AB8[] = {
    {{{  -666,     38,   -178}, 0, {  7122,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -666,     63,   -178}, 0, {  7122,    582}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,     63,   -178}, 0, {     0,    582}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -666,     38,    -76}, 0, {   990,    376}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -666,     63,    -76}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -666,     63,   -178}, 0, {   172,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -666,     38,   -178}, 0, {   172,    376}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   358,     63,   -178}, 0, {  4568,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -666,     63,   -178}, 0, {-13316,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -666,     63,    -76}, 0, {-13316,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   358,     63,    -76}, 0, {  4568,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   358,     38,    -76}, 0, {  2012,   1396}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   358,     63,    -76}, 0, {  2012,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -666,     63,    -76}, 0, { -5140,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -666,     38,    -76}, 0, { -5140,   1396}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07009BA8 - 0x07009C58
static const Vtx wf_seg7_vertex_07009BA8[] = {
    {{{   358,     38,   -178}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   358,     63,   -178}, 0, {   990,    582}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   358,     63,    -76}, 0, {     0,    582}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -666,     38,   -178}, 0, {  7122,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,     63,   -178}, 0, {     0,    582}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,     38,   -178}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   358,     38,    -76}, 0, {  4568,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -666,     38,    -76}, 0, {-13316,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -666,     38,   -178}, 0, {-13316,  -1310}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   358,     38,   -178}, 0, {  4568,  -1310}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   358,     38,    -76}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x07009C58 - 0x07009CA0
static const Gfx wf_seg7_dl_07009C58[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07009900.l, 1),
    gsSPLight(&wf_seg7_lights_07009900.a, 2),
    gsSPVertex(wf_seg7_vertex_07009918, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009CA0 - 0x07009D20
static const Gfx wf_seg7_dl_07009CA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_07009958, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wf_seg7_vertex_07009A48, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009D20 - 0x07009DB0
static const Gfx wf_seg7_dl_07009D20[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_07009AB8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wf_seg7_vertex_07009BA8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07009DB0 - 0x07009E30
const Gfx wf_seg7_dl_07009DB0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07009C58),
    gsSPDisplayList(wf_seg7_dl_07009CA0),
    gsSPDisplayList(wf_seg7_dl_07009D20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
