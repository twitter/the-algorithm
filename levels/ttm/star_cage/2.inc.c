// 0x070134A0 - 0x070135A0
static const Vtx ttm_seg7_vertex_070134A0[] = {
    {{{   -76,     77,    -76}, 0, {     0,    160}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,    -50,    -76}, 0, {  2290,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,    -50,    -76}, 0, {     0,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,     77,     77}, 0, {   316,    132}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,    -50,     77}, 0, {   316,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,    -50,    -76}, 0, {  1902,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,     77,    -76}, 0, {  1902,    132}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,     77,    -76}, 0, {  1902,    132}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,    -50,    -76}, 0, {  1902,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,    -50,     77}, 0, {   316,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,     77,     77}, 0, {   316,    132}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,    -50,     77}, 0, {     0,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,     77,     77}, 0, {  2290,    160}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{   -76,     77,     77}, 0, {     0,    160}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,    -50,     77}, 0, {  2290,    990}, {0xfe, 0xfe, 0xfe, 0xff}}},
    {{{    77,     77,    -76}, 0, {  2290,    160}, {0xfe, 0xfe, 0xfe, 0xff}}},
};

// 0x070135A0 - 0x07013608
static const Gfx ttm_seg7_dl_070135A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, mountain_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ttm_seg7_vertex_070134A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013608 - 0x07013678
const Gfx ttm_seg7_dl_07013608[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttm_seg7_dl_070135A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
