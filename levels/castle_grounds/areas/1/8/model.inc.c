// 0x0700BBF0 - 0x0700BC30
static const Vtx castle_grounds_seg7_vertex_0700BBF0[] = {
    {{{  2283,     65,   2072}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2485,     65,   2072}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2485,     65,   1849}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2283,     65,   1849}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0700BC30 - 0x0700BC68
static const Gfx castle_grounds_seg7_dl_0700BC30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, outside_09005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(castle_grounds_seg7_vertex_0700BBF0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BC68 - 0x0700BCD8
const Gfx castle_grounds_seg7_dl_0700BC68[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(castle_grounds_seg7_dl_0700BC30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
