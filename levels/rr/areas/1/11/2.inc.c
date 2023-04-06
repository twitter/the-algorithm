// 0x0700DC58 - 0x0700DD48
static const Vtx rr_seg7_vertex_0700DC58[] = {
    {{{  -529,   -242,   -536}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -899,     19,   -761}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -541,     35,   -762}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -899,     19,    737}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -887,   -258,    511}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -529,   -242,    511}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -541,     35,    738}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -285,     46,    738}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -273,   -231,    512}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{    84,   -216,    513}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{    72,     61,    739}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   700,     75,    726}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   342,     59,    725}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   712,   -202,    500}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   354,   -218,    499}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
};

// 0x0700DD48 - 0x0700DDF8
static const Vtx rr_seg7_vertex_0700DD48[] = {
    {{{   712,   -202,   -545}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   354,   -218,   -544}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   342,     59,   -770}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -529,   -242,   -536}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -887,   -258,   -535}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -899,     19,   -761}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{    84,   -216,   -537}, 0, {   990,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -273,   -231,   -536}, 0, {     0,    990}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{  -285,     46,   -762}, 0, {     0,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{    72,     61,   -763}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
    {{{   700,     75,   -771}, 0, {   990,      0}, {0xeb, 0xeb, 0x79, 0xff}}},
};

// 0x0700DDF8 - 0x0700DE88
static const Gfx rr_seg7_dl_0700DDF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_metal_hole),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0700DC58, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(rr_seg7_vertex_0700DD48, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700DE88 - 0x0700DEF8
const Gfx rr_seg7_dl_0700DE88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0700DDF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
