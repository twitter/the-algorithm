// 0x07007B60 - 0x07007BE0
static const Vtx bits_seg7_vertex_07007B60[] = {
    {{{  -716,      0,   -306}, 0, {   480,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   717,      0,   -306}, 0, { 14788,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   717,    102,   -306}, 0, { 14788,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -716,    102,   -306}, 0, {   480,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -716,      0,    307}, 0, {   480,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   717,      0,    307}, 0, { 14788,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   717,    102,    307}, 0, { 14788,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -716,    102,    307}, 0, {   480,      0}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07007BE0 - 0x07007C28
static const Gfx bits_seg7_dl_07007BE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07007B60, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007C28 - 0x07007C98
const Gfx bits_seg7_dl_07007C28[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07007BE0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
