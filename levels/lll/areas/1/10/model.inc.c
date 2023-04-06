// 0x070162E0 - 0x07016340
static const Vtx lll_seg7_vertex_070162E0[] = {
    {{{     0,    307,   -895}, 0, {     0,  -3012}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -767,    307,    384}, 0, { -2586,   1244}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    307,    896}, 0, {     0,   2946}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -767,    307,   -383}, 0, { -2586,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,    307,    384}, 0, {  2522,   1244}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,    307,   -383}, 0, {  2524,  -1310}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07016340 - 0x07016430
static const Vtx lll_seg7_vertex_07016340[] = {
    {{{  -767,      0,    384}, 0, {  1160,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,    307,   -383}, 0, { -3096,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,      0,   -383}, 0, { -3096,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   768,      0,   -383}, 0, {   330,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,    307,   -383}, 0, {   330,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,    307,    384}, 0, { -4118,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,      0,    384}, 0, { -4118,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   768,      0,    384}, 0, {  4226,    990}, {0x46, 0x00, 0x69, 0xff}}},
    {{{   768,    307,    384}, 0, {  4226,      0}, {0x46, 0x00, 0x69, 0xff}}},
    {{{     0,    307,    896}, 0, {     0,      0}, {0x46, 0x00, 0x69, 0xff}}},
    {{{     0,      0,    896}, 0, {     0,    990}, {0x46, 0x00, 0x69, 0xff}}},
    {{{     0,      0,    896}, 0, {  1160,    990}, {0xba, 0x00, 0x69, 0xff}}},
    {{{  -767,    307,    384}, 0, { -3096,      0}, {0xba, 0x00, 0x69, 0xff}}},
    {{{  -767,      0,    384}, 0, { -3096,    990}, {0xba, 0x00, 0x69, 0xff}}},
    {{{     0,    307,    896}, 0, {  1160,      0}, {0xba, 0x00, 0x69, 0xff}}},
};

// 0x07016430 - 0x070164E0
static const Vtx lll_seg7_vertex_07016430[] = {
    {{{     0,      0,   -895}, 0, {   966,    990}, {0x46, 0x00, 0x97, 0xff}}},
    {{{     0,    307,   -895}, 0, {   966,      0}, {0x46, 0x00, 0x97, 0xff}}},
    {{{   768,    307,   -383}, 0, { -3096,      0}, {0x46, 0x00, 0x97, 0xff}}},
    {{{  -767,      0,    384}, 0, {  1160,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,    307,    384}, 0, {  1160,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,    307,   -383}, 0, { -3096,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -767,      0,   -383}, 0, {  1160,    990}, {0xba, 0x00, 0x97, 0xff}}},
    {{{     0,    307,   -895}, 0, { -3096,      0}, {0xba, 0x00, 0x97, 0xff}}},
    {{{     0,      0,   -895}, 0, { -3096,    990}, {0xba, 0x00, 0x97, 0xff}}},
    {{{  -767,    307,   -383}, 0, {  1160,      0}, {0xba, 0x00, 0x97, 0xff}}},
    {{{   768,      0,   -383}, 0, { -3096,    990}, {0x46, 0x00, 0x97, 0xff}}},
};

// 0x070164E0 - 0x07016538
static const Gfx lll_seg7_dl_070164E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_07005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_070162E0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  0,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07016538 - 0x070165C8
static const Gfx lll_seg7_dl_07016538[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09007800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07016340, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(lll_seg7_vertex_07016430, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x070165C8 - 0x07016658
const Gfx lll_seg7_dl_070165C8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_070164E0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07016538),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
