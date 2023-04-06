// 0x0700DEF8 - 0x0700DFF8
static const Vtx rr_seg7_vertex_0700DEF8[] = {
    {{{   307,    -50,   -306}, 0, {     0,    990}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{   307,    154,   -306}, 0, {     0,      0}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{   307,    154,    307}, 0, {  3034,      0}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{   307,    -50,    307}, 0, {  3034,    990}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{  -306,    -50,    307}, 0, {  3034,    990}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{  -306,    154,    307}, 0, {  3034,      0}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{  -306,    154,   -306}, 0, {     0,      0}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{  -306,    -50,   -306}, 0, {     0,    990}, {0x8a, 0x6b, 0x00, 0xff}}},
    {{{  -306,    -50,   -306}, 0, {     0,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   307,    -50,   -306}, 0, {  3034,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   307,    154,   -306}, 0, {  3034,      0}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -306,    154,   -306}, 0, {     0,      0}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -306,    -50,    307}, 0, {     0,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   307,    -50,    307}, 0, {  3034,    990}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{   307,    154,    307}, 0, {  3034,      0}, {0xce, 0xaf, 0x16, 0xff}}},
    {{{  -306,    154,    307}, 0, {     0,      0}, {0xce, 0xaf, 0x16, 0xff}}},
};

// 0x0700DFF8 - 0x0700E0A8
static const Vtx rr_seg7_vertex_0700DFF8[] = {
    {{{  -101,      0,   -101}, 0, {     0,      0}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{   102,      0,   -101}, 0, {   990,      0}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{   298,      0,   -297}, 0, {  2012,  -1054}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -297,      0,   -297}, 0, { -1052,  -1054}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{   102,      0,    102}, 0, {   990,    990}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{   298,      0,    298}, 0, {  2012,   2010}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -101,      0,    102}, 0, {     0,    990}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -297,      0,    298}, 0, { -1052,   2010}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -297,      0,   -297}, 0, { -1052,   3032}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -101,      0,   -101}, 0, {     0,   2010}, {0xef, 0xcd, 0x8e, 0xff}}},
    {{{  -297,      0,    298}, 0, { -1052,      0}, {0xef, 0xcd, 0x8e, 0xff}}},
};

// 0x0700E0A8 - 0x0700E110
static const Gfx rr_seg7_dl_0700E0A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0700DEF8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E110 - 0x0700E178
static const Gfx rr_seg7_dl_0700E110[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_0700DFF8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 8,  6,  9, 0x0,  8, 10,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E178 - 0x0700E208
const Gfx rr_seg7_dl_0700E178[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0700E0A8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_0700E110),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
