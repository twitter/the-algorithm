// 0x07002000 - 0x07002100
static const Vtx dl_cruiser_metal_holes_vertex_group[] = {
    {{{ -4786,   -975,   4124}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -4889,   -975,   4124}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -4786,  -1057,   4185}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6721,   -715,   -565}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6721,   -797,   -503}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6823,   -797,   -503}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  6823,   -715,   -565}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3250,  -1792,   5658}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3250,  -1874,   5719}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3353,  -1874,   5719}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  3353,  -1792,   5658}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -5757,  -1792,   5760}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -5860,  -1792,   5760}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -5757,  -1874,   5822}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -5860,  -1874,   5822}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -4889,  -1057,   4185}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07002100 - 0x07002168
static const Gfx dl_cruiser_metal_holes_model[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_metal_hole),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dl_cruiser_metal_holes_vertex_group, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0,  1, 15,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x07002168 - 0x070021D8
const Gfx dl_cruiser_metal_holes[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dl_cruiser_metal_holes_model),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
