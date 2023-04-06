// 0x07051370 - 0x07051460
static const Vtx inside_castle_seg7_vertex_07051370[] = {
    {{{  1384,   2765,   7197}, 0, {  2012,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   6992}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2765,   6992}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -760,   3174,   4792}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   352,   3277,   4792}, 0, { 11210,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -760,   3277,   4792}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   352,   3174,   4792}, 0, { 11210,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -809,   3174,   4610}, 0, {  -896,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -760,   3277,   4792}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -809,   3277,   4610}, 0, {  -896,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -760,   3174,   4792}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   352,   3174,   4792}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   401,   3174,   4610}, 0, {  1856,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   401,   3277,   4610}, 0, {  1856,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   352,   3277,   4792}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07051460 - 0x07051560
static const Vtx inside_castle_seg7_vertex_07051460[] = {
    {{{ -1992,   2867,   6310}, 0, { -2074,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2867,   6509}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2765,   6509}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2765,   7197}, 0, {  2012,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   7197}, 0, {  2012,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   6992}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2765,   6992}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2867,   7197}, 0, { -1052,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2765,   7197}, 0, { -1052,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2867,   6992}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2867,   6603}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1793,   2765,   6603}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1992,   2765,   6310}, 0, { -2074,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2765,   6603}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   6603}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   6509}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07051560 - 0x070515B0
static const Vtx inside_castle_seg7_vertex_07051560[] = {
    {{{  1384,   2765,   6603}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2867,   6509}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1384,   2765,   6509}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1583,   2765,   6310}, 0, { -2074,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1583,   2867,   6310}, 0, { -2074,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070515B0 - 0x07051678
static const Gfx inside_castle_seg7_dl_070515B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_07051370, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(inside_castle_seg7_vertex_07051460, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 2,  1, 10, 0x0,  2, 10, 11, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(inside_castle_seg7_vertex_07051560, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP1Triangle( 3,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07051678 - 0x070516E8
const Gfx inside_castle_seg7_dl_07051678[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_070515B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
