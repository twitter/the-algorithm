// 0x0705E138 - 0x0705E238
static const Vtx inside_castle_seg7_vertex_0705E138[] = {
    {{{ -1586,   -972,   1567}, 0, { -2074,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1893,   -972,   1567}, 0, { -5140,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1893,  -1074,   1567}, 0, { -5140,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,  -1074,   2816}, 0, { 10188,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,   -972,   1784}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,  -1074,   1784}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,   -972,   2816}, 0, { 10188,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -656,  -1074,   1567}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,   -972,   1784}, 0, {  2858,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -656,   -972,   1567}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -439,  -1074,   1784}, 0, {  2858,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -656,   -972,   1567}, 0, {  7122,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1177,   -972,   1567}, 0, {  2012,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1177,  -1074,   1567}, 0, {  2012,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -656,  -1074,   1567}, 0, {  7122,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -1586,  -1074,   1567}, 0, { -2074,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0705E238 - 0x0705E2A0
static const Gfx inside_castle_seg7_dl_0705E238[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_0705E138, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0705E2A0 - 0x0705E310
const Gfx inside_castle_seg7_dl_0705E2A0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_0705E238),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
