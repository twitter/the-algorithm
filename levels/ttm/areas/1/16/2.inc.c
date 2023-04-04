// 0x0700EB40 - 0x0700EC00
static const Vtx ttm_seg7_vertex_0700EB40[] = {
    {{{   205,     57,   1382}, 0, {  -720,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   578,    -34,   1027}, 0, {  4404,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   578,     67,   1027}, 0, {  4424,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   205,    -44,   1382}, 0, {  -740,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   578,    -34,   1027}, 0, {  -246,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1229,     57,    614}, 0, {  7428,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   578,     67,   1027}, 0, {  -258,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  1229,    -44,    614}, 0, {  7442,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -562,    -44,    358}, 0, {  -262,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   205,     57,   1382}, 0, { 12512,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -580,     57,    335}, 0, {  -560,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   205,    -44,   1382}, 0, { 12512,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0700EC00 - 0x0700EC58
static const Gfx ttm_seg7_dl_0700EC00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttm_seg7_vertex_0700EB40, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700EC58 - 0x0700ECC8
const Gfx ttm_seg7_dl_0700EC58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_0700EC00),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
