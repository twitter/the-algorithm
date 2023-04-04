// 0x0700B010 - 0x0700B110
static const Vtx bitdw_seg7_vertex_0700B010[] = {
    {{{   307,    102,   -818}, 0, {  5760,  -4460}, {0x8d, 0xf0, 0xe3, 0xff}}},
    {{{  -306,    102,   -818}, 0, {  3716,  -4460}, {0x8d, 0xf0, 0xe3, 0xff}}},
    {{{  -306,    102,    819}, 0, {  3716,    990}, {0x8d, 0xf0, 0xe3, 0xff}}},
    {{{   307,    102,    819}, 0, {  5760,    990}, {0x8d, 0xf0, 0xe3, 0xff}}},
    {{{  -306,      0,    768}, 0, {     0,    990}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{  -306,    102,    819}, 0, {   990,   1158}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{  -306,    102,   -818}, 0, {   990,  -4290}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{  -306,      0,   -767}, 0, {     0,  -4120}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{   307,      0,   -767}, 0, {     0,  -4120}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{   307,    102,    819}, 0, {   990,   1158}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{   307,      0,    768}, 0, {     0,    990}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{   307,    102,   -818}, 0, {   990,  -4290}, {0x3f, 0xbf, 0xaa, 0xff}}},
    {{{  -306,      0,   -767}, 0, {  3716,  -4290}, {0x00, 0x96, 0x96, 0xff}}},
    {{{   307,    102,   -818}, 0, {  5760,  -4460}, {0x00, 0x96, 0x96, 0xff}}},
    {{{   307,      0,   -767}, 0, {  5760,  -4290}, {0x00, 0x96, 0x96, 0xff}}},
    {{{  -306,    102,   -818}, 0, {  3716,  -4460}, {0x00, 0x96, 0x96, 0xff}}},
};

// 0x0700B110 - 0x0700B190
static const Vtx bitdw_seg7_vertex_0700B110[] = {
    {{{   307,      0,    768}, 0, {  5760,    820}, {0x00, 0x96, 0x96, 0xff}}},
    {{{   307,    102,    819}, 0, {  5760,    990}, {0x00, 0x96, 0x96, 0xff}}},
    {{{  -306,    102,    819}, 0, {  3716,    990}, {0x00, 0x96, 0x96, 0xff}}},
    {{{  -306,      0,    768}, 0, {  3716,    820}, {0x00, 0x96, 0x96, 0xff}}},
    {{{  -306,      0,   -767}, 0, {  3716,  -4290}, {0x00, 0x5d, 0x5d, 0xff}}},
    {{{   307,      0,    768}, 0, {  5760,    820}, {0x00, 0x5d, 0x5d, 0xff}}},
    {{{  -306,      0,    768}, 0, {  3716,    820}, {0x00, 0x5d, 0x5d, 0xff}}},
    {{{   307,      0,   -767}, 0, {  5760,  -4290}, {0x00, 0x5d, 0x5d, 0xff}}},
};

// 0x0700B190 - 0x0700B220
static const Gfx bitdw_seg7_dl_0700B190[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700B010, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(bitdw_seg7_vertex_0700B110, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B220 - 0x0700B290
const Gfx bitdw_seg7_dl_0700B220[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_0700B190),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
