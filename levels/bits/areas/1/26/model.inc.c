// 0x07013F68 - 0x07014068
static const Vtx bits_seg7_vertex_07013F68[] = {
    {{{   307,    102,   -818}, 0, {     0,  -2076}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{  -306,      0,   -767}, 0, {   480,    990}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{  -306,    102,   -818}, 0, {     0,    990}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{   307,      0,   -767}, 0, {   478,  -2076}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{  -306,    102,    819}, 0, {     0,    990}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{  -306,      0,    768}, 0, {   480,    990}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{   307,      0,    768}, 0, {   478,  -2076}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{   307,    102,    819}, 0, {     0,  -2076}, {0xbb, 0x73, 0x84, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,    990}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{  -306,      0,    768}, 0, {   480,   9164}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{  -306,    102,    819}, 0, {   990,   9164}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{  -306,      0,   -767}, 0, {   478,    990}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{   307,    102,    819}, 0, {   990,   9164}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{   307,      0,    768}, 0, {   480,   9164}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{   307,      0,   -767}, 0, {   478,    990}, {0x7c, 0x56, 0x6a, 0xff}}},
    {{{   307,    102,   -818}, 0, {   990,    990}, {0x7c, 0x56, 0x6a, 0xff}}},
};

// 0x07014068 - 0x070140E8
static const Vtx bits_seg7_vertex_07014068[] = {
    {{{  -306,      0,    768}, 0, {     0,    990}, {0x5e, 0x3c, 0x45, 0xff}}},
    {{{   307,      0,   -767}, 0, {  1500,   5076}, {0x5e, 0x3c, 0x45, 0xff}}},
    {{{   307,      0,    768}, 0, {  1502,    990}, {0x5e, 0x3c, 0x45, 0xff}}},
    {{{  -306,      0,   -767}, 0, {     0,   5076}, {0x5e, 0x3c, 0x45, 0xff}}},
    {{{   307,    102,    819}, 0, {  1502,    990}, {0xff, 0xcc, 0x65, 0xff}}},
    {{{   307,    102,   -818}, 0, {  1502,   5076}, {0xff, 0xcc, 0x65, 0xff}}},
    {{{  -306,    102,   -818}, 0, {     0,   5076}, {0xff, 0xcc, 0x65, 0xff}}},
    {{{  -306,    102,    819}, 0, {     0,    990}, {0xff, 0xcc, 0x65, 0xff}}},
};

// 0x070140E8 - 0x07014178
static const Gfx bits_seg7_dl_070140E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07013F68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bits_seg7_vertex_07014068, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07014178 - 0x070141E8
const Gfx bits_seg7_dl_07014178[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_070140E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
