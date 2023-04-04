// 0x07024AB0 - 0x07024B90
static const Vtx ttm_seg7_vertex_07024AB0[] = {
    {{{ -1475,   -140,  -6832}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1475,   -345,  -6832}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1528,   -191,  -6582}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1433,   -293,  -7033}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1475,   -242,  -6832}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1433,    -89,  -7033}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -1475,    -37,  -6832}, 0, {     0,      0}, {0xdf, 0x1f, 0x1f, 0xff}}},
    {{{ -3196,     13,  -6675}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3143,    166,  -6925}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3143,     64,  -6925}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3101,    115,  -7126}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3101,    -89,  -7126}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3143,    -37,  -6925}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
    {{{ -3143,   -140,  -6925}, 0, {     0,      0}, {0xdf, 0x5f, 0x1f, 0xff}}},
};

// 0x07024B90 - 0x07024C00
static const Vtx ttm_seg7_vertex_07024B90[] = {
    {{{ -4335,    218,  -6760}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4293,     64,  -6960}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4335,    115,  -6760}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4335,     13,  -6760}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4388,    166,  -6509}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4293,    269,  -6960}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
    {{{ -4335,    320,  -6760}, 0, {     0,      0}, {0xff, 0xaa, 0x00, 0xff}}},
};

// 0x07024C00 - 0x07024C78
static const Gfx ttm_seg7_dl_07024C00[] = {
    gsSPVertex(ttm_seg7_vertex_07024AB0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 11, 12, 0x0,  9, 13,  7, 0x0),
    gsSPVertex(ttm_seg7_vertex_07024B90, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  5,  1, 0x0,  4,  6,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x07024C78 - 0x07024CA8
const Gfx ttm_seg7_dl_07024C78[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsSPDisplayList(ttm_seg7_dl_07024C00),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
