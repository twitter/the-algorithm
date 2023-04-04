// 0x07002D40 - 0x07002D80
static const Vtx rr_seg7_vertex_07002D40[] = {
    {{{  -409,      0,   -204}, 0, {   990,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,      0,    205}, 0, { -3096,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,      0,   -204}, 0, { -3096,    990}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,      0,    205}, 0, {   990,      0}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07002D80 - 0x07002E00
static const Vtx rr_seg7_vertex_07002D80[] = {
    {{{   410,    113,   -204}, 0, {  3590,    -12}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -406,    117,   -204}, 0, {     0,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,   -112,   -204}, 0, {   -42,    988}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,   -112,   -204}, 0, {  3590,    988}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,    113,    205}, 0, {  3590,    -12}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -406,    117,    205}, 0, {     0,      0}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{  -409,   -112,    205}, 0, {   -42,    988}, {0xff, 0xd4, 0x00, 0xff}}},
    {{{   410,   -112,    205}, 0, {  3590,    988}, {0xff, 0xd4, 0x00, 0xff}}},
};

// 0x07002E00 - 0x07002E38
static const Gfx rr_seg7_dl_07002E00[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09000000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_07002D40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002E38 - 0x07002E80
static const Gfx rr_seg7_dl_07002E38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09006000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(rr_seg7_vertex_07002D80, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002E80 - 0x07002EF8
const Gfx rr_seg7_dl_07002E80[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(rr_seg7_dl_07002E00),
    gsSPDisplayList(rr_seg7_dl_07002E38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
