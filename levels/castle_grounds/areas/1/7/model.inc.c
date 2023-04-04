// 0x0700BAB8 - 0x0700BB38
static const Vtx castle_grounds_seg7_vertex_0700BAB8[] = {
    {{{ -3532,  -1330,  -6069}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -4044,  -1330,  -5557}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -3532,  -1330,  -5557}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -4044,  -1330,  -6069}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -3225,   -562,  -2178}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -3532,   -562,  -2178}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -3225,   -562,  -1871}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -3532,   -562,  -1871}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0700BB38 - 0x0700BB80
static const Gfx castle_grounds_seg7_dl_0700BB38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, outside_09005800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(castle_grounds_seg7_vertex_0700BAB8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BB80 - 0x0700BBF0
const Gfx castle_grounds_seg7_dl_0700BB80[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(castle_grounds_seg7_dl_0700BB38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
