// 0x0700B8B0 - 0x0700B9B0
static const Vtx bits_seg7_vertex_0700B8B0[] = {
    {{{   410,      0,    410}, 0, {  2012,   1040}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,      0,   -409}, 0, {  2012,  -3046}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   154,    154,   -153}, 0, {   -20,  -1770}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   154,    154,    154}, 0, {   -20,   -236}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,   -409}, 0, {  2012,   1040}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,    410}, 0, {  2012,  -3046}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -153,    154,    154}, 0, {   -40,  -1770}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -153,    154,   -153}, 0, {   -40,   -236}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,    410}, 0, {  2012,    990}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{   154,    154,    154}, 0, {   -40,  -1820}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{  -153,    154,    154}, 0, {   -40,   -288}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{   410,      0,    410}, 0, {  2012,  -3098}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{  -409,      0,   -409}, 0, {  2012,  -3098}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{  -153,    154,   -153}, 0, {   -40,  -1820}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{   154,    154,   -153}, 0, {   -40,   -288}, {0xbe, 0xbe, 0x00, 0xff}}},
    {{{   410,      0,   -409}, 0, {  2012,    990}, {0xbe, 0xbe, 0x00, 0xff}}},
};

// 0x0700B9B0 - 0x0700BA18
static const Gfx bits_seg7_dl_0700B9B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_0700B8B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BA18 - 0x0700BA88
const Gfx bits_seg7_dl_0700BA18[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_0700B9B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
