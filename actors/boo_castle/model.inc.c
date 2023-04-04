// Boo (Castle)

// 0x06015658
static const Lights1 boo_castle_seg6_lights_06015658 = gdSPDefLights1(
    0x97, 0x9a, 0xff,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06015670
ALIGNED8 static const Texture boo_castle_seg6_texture_06015670[] = {
#include "actors/boo_castle/bbh_boo_eyes.rgba16.inc.c"
};

// 0x06016670
ALIGNED8 static const Texture boo_castle_seg6_texture_06016670[] = {
#include "actors/boo_castle/bbh_boo_mouth.rgba16.inc.c"
};

// 0x06016E70
static const Vtx boo_castle_seg6_vertex_06016E70[] = {
    {{{     0,   -117,    131}, 0, {   458,    990}, {0x00, 0x9d, 0x4e, 0x9e}}},
    {{{    87,    -78,    123}, 0, {  1096,    684}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{     0,    -47,    171}, 0, {   458,    168}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{     0,   -117,    131}, 0, {   458,    990}, {0x00, 0x9d, 0x4e, 0x9e}}},
    {{{     0,    -47,    171}, 0, {   458,    168}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{   -86,    -78,    123}, 0, {  -176,    684}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{    87,    -78,    123}, 0, {  1096,    684}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{   108,     20,    118}, 0, {  1248,   -172}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{     0,    -47,    171}, 0, {   458,    168}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{   -86,    -78,    123}, 0, {  -176,    684}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{     0,    -47,    171}, 0, {   458,    168}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{  -107,     20,    118}, 0, {  -328,   -172}, {0xa9, 0x14, 0x59, 0x9e}}},
};

// 0x06016F30
static const Vtx boo_castle_seg6_vertex_06016F30[] = {
    {{{  -107,     20,    118}, 0, {  -656,    538}, {0xa9, 0x14, 0x59, 0x9e}}},
    {{{     0,     60,    166}, 0, {   988,    148}, {0x00, 0x26, 0x79, 0x9e}}},
    {{{   -57,    128,    108}, 0, {    76,   -690}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{  -107,     20,    118}, 0, {  -656,    538}, {0xa9, 0x14, 0x59, 0x9e}}},
    {{{     0,    -47,    171}, 0, {  1024,   1364}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{     0,     60,    166}, 0, {   988,    148}, {0x00, 0x26, 0x79, 0x9e}}},
    {{{     0,     60,    166}, 0, {   988,    148}, {0x00, 0x26, 0x79, 0x9e}}},
    {{{     0,    -47,    171}, 0, {  1024,   1364}, {0x00, 0xe9, 0x7c, 0x9e}}},
    {{{   108,     20,    118}, 0, {  2660,    540}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{   108,     20,    118}, 0, {  2660,    540}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{    58,    128,    108}, 0, {  1852,   -688}, {0x35, 0x58, 0x49, 0x9e}}},
    {{{     0,     60,    166}, 0, {   988,    148}, {0x00, 0x26, 0x79, 0x9e}}},
};

// 0x06016FF0
static const Vtx boo_castle_seg6_vertex_06016FF0[] = {
    {{{  -135,    -70,     23}, 0, {     0,      0}, {0xb2, 0xaa, 0x33, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
    {{{     0,   -117,    131}, 0, {     0,      0}, {0x00, 0x9d, 0x4e, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{   -86,    -78,    123}, 0, {     0,      0}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{  -135,    -70,     23}, 0, {     0,      0}, {0xb2, 0xaa, 0x33, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{   -59,   -126,    -86}, 0, {     0,      0}, {0xe1, 0x90, 0xd0, 0x9e}}},
    {{{    60,   -126,    -86}, 0, {     0,      0}, {0x20, 0x8b, 0xdb, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{    60,   -126,    -86}, 0, {     0,      0}, {0x20, 0x8b, 0xdb, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
};

// 0x060170E0
static const Vtx boo_castle_seg6_vertex_060170E0[] = {
    {{{   -86,    -78,    123}, 0, {     0,      0}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{     0,   -117,    131}, 0, {     0,      0}, {0x00, 0x9d, 0x4e, 0x9e}}},
    {{{   -59,   -126,    -86}, 0, {     0,      0}, {0xe1, 0x90, 0xd0, 0x9e}}},
    {{{   -72,   -138,     30}, 0, {     0,      0}, {0xcf, 0x8c, 0x0a, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{    60,   -126,    -86}, 0, {     0,      0}, {0x20, 0x8b, 0xdb, 0x9e}}},
    {{{   -59,   -126,    -86}, 0, {     0,      0}, {0xe1, 0x90, 0xd0, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{    60,   -126,    -86}, 0, {     0,      0}, {0x20, 0x8b, 0xdb, 0x9e}}},
    {{{    60,   -126,    -86}, 0, {     0,      0}, {0x20, 0x8b, 0xdb, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
};

// 0x060171D0
static const Vtx boo_castle_seg6_vertex_060171D0[] = {
    {{{   136,    -70,     23}, 0, {     0,      0}, {0x4e, 0xaa, 0x33, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{    87,    -78,    123}, 0, {     0,      0}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
    {{{   136,    -70,     23}, 0, {     0,      0}, {0x4e, 0xaa, 0x33, 0x9e}}},
    {{{    87,    -78,    123}, 0, {     0,      0}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{     0,   -117,    131}, 0, {     0,      0}, {0x00, 0x9d, 0x4e, 0x9e}}},
    {{{    73,   -138,     30}, 0, {     0,      0}, {0x39, 0x90, 0x0e, 0x9e}}},
    {{{   -43,    162,     15}, 0, {     0,      0}, {0xdf, 0x7a, 0xf8, 0x9e}}},
    {{{   -57,    128,    108}, 0, {     0,      0}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{    44,    162,     15}, 0, {     0,      0}, {0x21, 0x7a, 0x09, 0x9e}}},
    {{{   -43,    162,     15}, 0, {     0,      0}, {0xdf, 0x7a, 0xf8, 0x9e}}},
    {{{    44,    162,     15}, 0, {     0,      0}, {0x21, 0x7a, 0x09, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
};

// 0x060172C0
static const Vtx boo_castle_seg6_vertex_060172C0[] = {
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{    44,    162,     15}, 0, {     0,      0}, {0x21, 0x7a, 0x09, 0x9e}}},
    {{{    58,    128,    108}, 0, {     0,      0}, {0x35, 0x58, 0x49, 0x9e}}},
    {{{    58,    128,    108}, 0, {     0,      0}, {0x35, 0x58, 0x49, 0x9e}}},
    {{{    44,    162,     15}, 0, {     0,      0}, {0x21, 0x7a, 0x09, 0x9e}}},
    {{{   -57,    128,    108}, 0, {     0,      0}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{    44,    162,     15}, 0, {     0,      0}, {0x21, 0x7a, 0x09, 0x9e}}},
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{     0,     62,   -162}, 0, {     0,      0}, {0x00, 0x5a, 0xa8, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
};

// 0x060173B0
static const Vtx boo_castle_seg6_vertex_060173B0[] = {
    {{{   -41,    130,    -88}, 0, {     0,      0}, {0xd4, 0x65, 0xc3, 0x9e}}},
    {{{   -43,    162,     15}, 0, {     0,      0}, {0xdf, 0x7a, 0xf8, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
    {{{     0,     62,   -162}, 0, {     0,      0}, {0x00, 0x5a, 0xa8, 0x9e}}},
    {{{   -41,    130,    -88}, 0, {     0,      0}, {0xd4, 0x65, 0xc3, 0x9e}}},
    {{{    42,    130,    -88}, 0, {     0,      0}, {0x25, 0x6a, 0xc7, 0x9e}}},
    {{{  -135,    -70,     23}, 0, {     0,      0}, {0xb2, 0xaa, 0x33, 0x9e}}},
    {{{   -86,    -78,    123}, 0, {     0,      0}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{   -86,    -78,    123}, 0, {     0,      0}, {0xad, 0xc9, 0x4e, 0x9e}}},
    {{{  -107,     20,    118}, 0, {     0,      0}, {0xa9, 0x14, 0x59, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{  -199,    -60,     25}, 0, {     0,      0}, {0xa2, 0xbf, 0x36, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{  -135,    -70,     23}, 0, {     0,      0}, {0xb2, 0xaa, 0x33, 0x9e}}},
};

// 0x060174A0
static const Vtx boo_castle_seg6_vertex_060174A0[] = {
    {{{  -107,     20,    118}, 0, {     0,      0}, {0xa9, 0x14, 0x59, 0x9e}}},
    {{{   -57,    128,    108}, 0, {     0,      0}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{  -107,     20,    118}, 0, {     0,      0}, {0xa9, 0x14, 0x59, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
    {{{  -199,    -60,     25}, 0, {     0,      0}, {0xa2, 0xbf, 0x36, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
};

// 0x06017590
static const Vtx boo_castle_seg6_vertex_06017590[] = {
    {{{  -199,    -60,     25}, 0, {     0,      0}, {0xa2, 0xbf, 0x36, 0x9e}}},
    {{{  -135,    -70,     23}, 0, {     0,      0}, {0xb2, 0xaa, 0x33, 0x9e}}},
    {{{  -155,      6,     33}, 0, {     0,      0}, {0x87, 0x0f, 0x22, 0x9e}}},
    {{{   200,    -60,     25}, 0, {     0,      0}, {0x5e, 0xbf, 0x36, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{   108,     20,    118}, 0, {     0,      0}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
    {{{   200,    -60,     25}, 0, {     0,      0}, {0x5e, 0xbf, 0x36, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{   136,    -70,     23}, 0, {     0,      0}, {0x4e, 0xaa, 0x33, 0x9e}}},
};

// 0x06017680
static const Vtx boo_castle_seg6_vertex_06017680[] = {
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
    {{{   200,    -60,     25}, 0, {     0,      0}, {0x5e, 0xbf, 0x36, 0x9e}}},
    {{{   136,    -70,     23}, 0, {     0,      0}, {0x4e, 0xaa, 0x33, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{   -43,    162,     15}, 0, {     0,      0}, {0xdf, 0x7a, 0xf8, 0x9e}}},
    {{{   -41,    130,    -88}, 0, {     0,      0}, {0xd4, 0x65, 0xc3, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
    {{{   -57,    128,    108}, 0, {     0,      0}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{   -43,    162,     15}, 0, {     0,      0}, {0xdf, 0x7a, 0xf8, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
    {{{   128,    -69,    -89}, 0, {     0,      0}, {0x64, 0xc6, 0xce, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
};

// 0x06017770
static const Vtx boo_castle_seg6_vertex_06017770[] = {
    {{{     0,     62,   -162}, 0, {     0,      0}, {0x00, 0x5a, 0xa8, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
    {{{     0,     24,   -213}, 0, {     0,      0}, {0x00, 0x22, 0x86, 0x9e}}},
    {{{     0,     24,   -213}, 0, {     0,      0}, {0x00, 0x22, 0x86, 0x9e}}},
    {{{   122,     59,    -94}, 0, {     0,      0}, {0x5f, 0x2e, 0xbb, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{     0,     62,   -162}, 0, {     0,      0}, {0x00, 0x5a, 0xa8, 0x9e}}},
    {{{     0,     24,   -213}, 0, {     0,      0}, {0x00, 0x22, 0x86, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{   -41,    130,    -88}, 0, {     0,      0}, {0xd4, 0x65, 0xc3, 0x9e}}},
    {{{     0,     62,   -162}, 0, {     0,      0}, {0x00, 0x5a, 0xa8, 0x9e}}},
    {{{     0,     24,   -213}, 0, {     0,      0}, {0x00, 0x22, 0x86, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{   -45,    -82,   -160}, 0, {     0,      0}, {0xc9, 0xc5, 0x9f, 0x9e}}},
};

// 0x06017860
static const Vtx boo_castle_seg6_vertex_06017860[] = {
    {{{   -45,    -82,   -160}, 0, {     0,      0}, {0xc9, 0xc5, 0x9f, 0x9e}}},
    {{{    46,    -82,   -160}, 0, {     0,      0}, {0x2e, 0xba, 0xa2, 0x9e}}},
    {{{   -59,   -126,    -86}, 0, {     0,      0}, {0xe1, 0x90, 0xd0, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{   -45,    -82,   -160}, 0, {     0,      0}, {0xc9, 0xc5, 0x9f, 0x9e}}},
    {{{   -45,    -82,   -160}, 0, {     0,      0}, {0xc9, 0xc5, 0x9f, 0x9e}}},
    {{{   -59,   -126,    -86}, 0, {     0,      0}, {0xe1, 0x90, 0xd0, 0x9e}}},
    {{{  -127,    -69,    -89}, 0, {     0,      0}, {0x9c, 0xc6, 0xce, 0x9e}}},
    {{{     0,     24,   -213}, 0, {     0,      0}, {0x00, 0x22, 0x86, 0x9e}}},
    {{{   -45,    -82,   -160}, 0, {     0,      0}, {0xc9, 0xc5, 0x9f, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{   -41,    130,    -88}, 0, {     0,      0}, {0xd4, 0x65, 0xc3, 0x9e}}},
    {{{  -121,     59,    -94}, 0, {     0,      0}, {0xa1, 0x2e, 0xbb, 0x9e}}},
    {{{  -124,     99,     15}, 0, {     0,      0}, {0x9b, 0x4c, 0x08, 0x9e}}},
};

// 0x06017950
static const Vtx boo_castle_seg6_vertex_06017950[] = {
    {{{   -57,    128,    108}, 0, {     0,      0}, {0xd3, 0x60, 0x45, 0x9e}}},
    {{{     0,     60,    166}, 0, {     0,      0}, {0x00, 0x26, 0x79, 0x9e}}},
    {{{    58,    128,    108}, 0, {     0,      0}, {0x35, 0x58, 0x49, 0x9e}}},
    {{{   108,     20,    118}, 0, {     0,      0}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{   125,     99,     15}, 0, {     0,      0}, {0x65, 0x4c, 0x08, 0x9e}}},
    {{{    58,    128,    108}, 0, {     0,      0}, {0x35, 0x58, 0x49, 0x9e}}},
    {{{   136,    -70,     23}, 0, {     0,      0}, {0x4e, 0xaa, 0x33, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
    {{{    87,    -78,    123}, 0, {     0,      0}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{   108,     20,    118}, 0, {     0,      0}, {0x57, 0x14, 0x5a, 0x9e}}},
    {{{    87,    -78,    123}, 0, {     0,      0}, {0x53, 0xc9, 0x4e, 0x9e}}},
    {{{   156,      6,     33}, 0, {     0,      0}, {0x79, 0x0f, 0x22, 0x9e}}},
};

// 0x06017A10 - 0x06017A78
const Gfx boo_castle_seg6_dl_06017A10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, boo_castle_seg6_texture_06016670),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&boo_castle_seg6_lights_06015658.l, 1),
    gsSPLight(&boo_castle_seg6_lights_06015658.a, 2),
    gsSPVertex(boo_castle_seg6_vertex_06016E70, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06017A78 - 0x06017AD0
const Gfx boo_castle_seg6_dl_06017A78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, boo_castle_seg6_texture_06015670),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(boo_castle_seg6_vertex_06016F30, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06017AD0 - 0x06017CE0
const Gfx boo_castle_seg6_dl_06017AD0[] = {
    gsSPVertex(boo_castle_seg6_vertex_06016FF0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_060170E0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_060171D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_060172C0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_060173B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_060174A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_06017590, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_06017680, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_06017770, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_06017860, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(boo_castle_seg6_vertex_06017950, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x06017CE0 - 0x06017D80
const Gfx boo_castle_seg6_dl_06017CE0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_BLENDRGBFADEA, G_CC_BLENDRGBFADEA),
    gsSPNumLights(NUMLIGHTS_1),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(boo_castle_seg6_dl_06017A10),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(boo_castle_seg6_dl_06017A78),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(boo_castle_seg6_dl_06017AD0),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
