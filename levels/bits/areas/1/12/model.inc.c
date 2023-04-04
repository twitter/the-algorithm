// 0x0700B528 - 0x0700B5A8
static const Vtx bits_seg7_vertex_0700B528[] = {
    {{{   836,   -306,    401}, 0, {  2694,   6438}, {0x44, 0x53, 0x41, 0xff}}},
    {{{  -801,   -306,   -417}, 0, {     0,    990}, {0x44, 0x53, 0x41, 0xff}}},
    {{{   836,   -306,   -417}, 0, {     0,   6438}, {0x44, 0x53, 0x41, 0xff}}},
    {{{  -801,   -306,    401}, 0, {  2694,    990}, {0x44, 0x53, 0x41, 0xff}}},
    {{{  -801,      0,    401}, 0, {  2694,    990}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{   836,      0,   -417}, 0, {     0,   6438}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{  -801,      0,   -417}, 0, {     0,    990}, {0xbc, 0xca, 0xbf, 0xff}}},
    {{{   836,      0,    401}, 0, {  2694,   6438}, {0xbc, 0xca, 0xbf, 0xff}}},
};

// 0x0700B5A8 - 0x0700B698
static const Vtx bits_seg7_vertex_0700B5A8[] = {
    {{{   836,      0,   -417}, 0, {  5076,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -801,   -306,   -417}, 0, {     0,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -801,      0,   -417}, 0, {     0,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   836,   -306,   -417}, 0, {  5076,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -801,      0,    401}, 0, {     0,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   836,   -306,    401}, 0, {  5076,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{   836,      0,    401}, 0, {  5076,      0}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -801,   -306,    401}, 0, {     0,    990}, {0x6e, 0x7c, 0x6c, 0xff}}},
    {{{  -801,      0,     -8}, 0, {  2808,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,   -306,    401}, 0, {   992,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,      0,    401}, 0, {   992,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,   -306,     -8}, 0, {  2240,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,   -306,   -417}, 0, {  4056,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,      0,   -417}, 0, {  4056,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,      0,     -8}, 0, {  2240,      0}, {0x88, 0x98, 0x84, 0xff}}},
};

// 0x0700B698 - 0x0700B748
static const Vtx bits_seg7_vertex_0700B698[] = {
    {{{   836,      0,    401}, 0, {   992,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,   -306,     -8}, 0, {  2808,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,      0,     -8}, 0, {  2808,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{   836,   -306,    401}, 0, {   992,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,      0,     -8}, 0, {  2240,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,      0,   -417}, 0, {  4056,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,   -306,   -417}, 0, {  4056,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,   -306,     -8}, 0, {  2240,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,      0,     -8}, 0, {  2808,      0}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,   -306,     -8}, 0, {  2808,    990}, {0x88, 0x98, 0x84, 0xff}}},
    {{{  -801,   -306,    401}, 0, {   992,    990}, {0x88, 0x98, 0x84, 0xff}}},
};

// 0x0700B748 - 0x0700B790
static const Gfx bits_seg7_dl_0700B748[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_0700B528, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B790 - 0x0700B820
static const Gfx bits_seg7_dl_0700B790[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, bits_seg7_texture_07001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_0700B5A8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(bits_seg7_vertex_0700B698, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP1Triangle( 8,  9, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B820 - 0x0700B8B0
const Gfx bits_seg7_dl_0700B820[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_0700B748),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_0700B790),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
