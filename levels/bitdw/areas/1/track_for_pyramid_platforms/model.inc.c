// 0x0700A3F8 - 0x0700A4F8
static const Vtx bitdw_seg7_vertex_0700A3F8[] = {
    {{{  -562,     57,   -568}, 0, {   260,  -6504}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   614,     57,   -620}, 0, {   -76,   1328}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -613,     57,   -620}, 0, {   -76,  -6846}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   563,     57,    558}, 0, {   260,    990}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -562,     57,    558}, 0, {   260,   8482}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -613,     57,    609}, 0, {   -76,   8824}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   614,     57,    609}, 0, {   -76,    650}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   563,     57,   -568}, 0, {   260,  -8208}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   614,     57,    609}, 0, {   -76,    990}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   614,     57,   -620}, 0, {   -76,  -8548}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   563,     57,    558}, 0, {   260,    650}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -613,     57,    609}, 0, {   -76,    990}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -562,     57,    558}, 0, {   260,   1328}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -562,     57,   -568}, 0, {   260,  10186}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{  -613,     57,   -620}, 0, {   -76,  10526}, {0xe6, 0xee, 0x91, 0xff}}},
    {{{   563,     57,   -568}, 0, {   260,    990}, {0xe6, 0xee, 0x91, 0xff}}},
};

// 0x0700A4F8 - 0x0700A5F8
static const Vtx bitdw_seg7_vertex_0700A4F8[] = {
    {{{   563,     57,    558}, 0, {   404,   1328}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{   512,      0,    513}, 0, {   -76,    990}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{  -511,      0,    513}, 0, {   -76,  -5824}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{  -562,     57,    558}, 0, {   404,  -6164}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{  -562,     57,   -568}, 0, {   460,    650}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{   512,      0,   -511}, 0, {   -76,   7802}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{   563,     57,   -568}, 0, {   460,   8142}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{  -511,      0,   -511}, 0, {   -76,    990}, {0xb7, 0xc2, 0x52, 0xff}}},
    {{{   563,     57,   -568}, 0, {   432,    982}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{   512,      0,    513}, 0, {   -76,   9538}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{   563,     57,    558}, 0, {   432,   9838}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{   512,      0,   -511}, 0, {   -76,   1362}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{  -511,      0,    513}, 0, {   -76,    956}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{  -511,      0,   -511}, 0, {   -76,  -7220}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{  -562,     57,   -568}, 0, {   432,  -7602}, {0x8e, 0xac, 0x52, 0xff}}},
    {{{  -562,     57,    558}, 0, {   432,   1254}, {0x8e, 0xac, 0x52, 0xff}}},
};

// 0x0700A5F8 - 0x0700A6A8
static const Gfx bitdw_seg7_dl_0700A5F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700A3F8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPVertex(bitdw_seg7_vertex_0700A4F8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 15, 12, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700A6A8 - 0x0700A718
const Gfx bitdw_seg7_dl_0700A6A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_0700A5F8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
