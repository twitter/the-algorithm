// 0x07019A98 - 0x07019B18
static const Vtx lll_seg7_vertex_07019A98[] = {
    {{{     5,     41,   1024}, 0, {   -26,   1000}, {0x0d, 0x7e, 0x00, 0xff}}},
    {{{   384,      0,  -1023}, 0, {  1006,  -4116}, {0x0d, 0x7e, 0x00, 0xff}}},
    {{{     5,     41,  -1023}, 0, {   -14,  -4108}, {0x0d, 0x7e, 0x00, 0xff}}},
    {{{   384,      0,   1024}, 0, {   994,    992}, {0x0d, 0x7e, 0x00, 0xff}}},
    {{{  -383,      0,  -1023}, 0, {   -48,  -4040}, {0xf3, 0x7e, 0x00, 0xff}}},
    {{{     5,     41,   1024}, 0, {  1008,    994}, {0xf3, 0x7e, 0x00, 0xff}}},
    {{{     5,     41,  -1023}, 0, {   984,  -4052}, {0xf3, 0x7e, 0x00, 0xff}}},
    {{{  -383,      0,   1024}, 0, {   -26,   1006}, {0xf3, 0x7e, 0x00, 0xff}}},
};

// 0x07019B18 - 0x07019B78
static const Vtx lll_seg7_vertex_07019B18[] = {
    {{{  -383,      0,  -1023}, 0, {  7122,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     5,     41,  -1023}, 0, {  4016,    662}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   384,      0,  -1023}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   384,      0,   1024}, 0, {  6100,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     5,     41,   1024}, 0, {  3074,   -360}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -380,      0,   1024}, 0, {    -8,    -34}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07019B78 - 0x07019BD0
static const Gfx lll_seg7_dl_07019B78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_07019A98, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07019BD0 - 0x07019C08
static const Gfx lll_seg7_dl_07019BD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07019B18, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07019C08 - 0x07019C80
const Gfx lll_seg7_dl_07019C08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07019B78),
    gsSPDisplayList(lll_seg7_dl_07019BD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
