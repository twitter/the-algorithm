// 0x07064C40 - 0x07064D00
static const Vtx inside_castle_seg7_vertex_07064C40[] = {
    {{{  1669,   -972,  -1847}, 0, { -7184,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,  -1074,  -3475}, 0, {  8654,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,   -972,  -3475}, 0, {  8654,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,  -1074,  -1847}, 0, { -7184,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,   -972,  -1847}, 0, {  8144,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3277,  -1074,  -1847}, 0, { -7694,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,  -1074,  -1847}, 0, {  8144,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3277,   -972,  -1847}, 0, { -7694,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3277,   -972,  -3475}, 0, {  8654,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,  -1074,  -3475}, 0, { -7184,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3277,  -1074,  -3475}, 0, {  8654,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1669,   -972,  -3475}, 0, { -7184,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07064D00 - 0x07064D58
static const Gfx inside_castle_seg7_dl_07064D00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_07064C40, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07064D58 - 0x07064DC8
const Gfx inside_castle_seg7_dl_07064D58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07064D00),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
