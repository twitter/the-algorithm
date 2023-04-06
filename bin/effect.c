#include <ultra64.h>
#include "sm64.h"

#include "make_const_nonconst.h"

UNUSED static const u64 effect_unused_0 = 0;

// Flower (Unused)
// 0x0B000008
ALIGNED8 static const u8 effect_0B000008[] = {
#include "textures/effect/flower.00008.rgba16.inc.c"
};

// 0x0B000808
ALIGNED8 static const u8 effect_0B000808[] = {
#include "textures/effect/flower.00808.rgba16.inc.c"
};

// 0x0B001008
ALIGNED8 static const u8 effect_0B001008[] = {
#include "textures/effect/flower.01008.rgba16.inc.c"
};

// 0x0B001808
ALIGNED8 static const u8 effect_0B001808[] = {
#include "textures/effect/flower.01808.rgba16.inc.c"
};

// 0x0B002008
const u8 *const flower_bubbles_textures_ptr_0B002008[] = {
    effect_0B000008,
    effect_0B000808,
    effect_0B001008,
    effect_0B001808,
    effect_0B001008,
    effect_0B000808,
};

// Lava Bubble
// 0x0B002020
ALIGNED8 static const u8 effect_0B002020[] = {
#include "textures/effect/lava_bubble.02020.rgba16.inc.c"
};

// 0x0B002820
ALIGNED8 static const u8 effect_0B002820[] = {
#include "textures/effect/lava_bubble.02820.rgba16.inc.c"
};

// 0x0B003020
ALIGNED8 static const u8 effect_0B003020[] = {
#include "textures/effect/lava_bubble.03020.rgba16.inc.c"
};

// 0x0B003820
ALIGNED8 static const u8 effect_0B003820[] = {
#include "textures/effect/lava_bubble.03820.rgba16.inc.c"
};

// 0x0B004020
ALIGNED8 static const u8 effect_0B004020[] = {
#include "textures/effect/lava_bubble.04020.rgba16.inc.c"
};

// 0x0B004820
ALIGNED8 static const u8 effect_0B004820[] = {
#include "textures/effect/lava_bubble.04820.rgba16.inc.c"
};

// 0x0B005020
ALIGNED8 static const u8 effect_0B005020[] = {
#include "textures/effect/lava_bubble.05020.rgba16.inc.c"
};

// 0x0B005820
ALIGNED8 static const u8 effect_0B005820[] = {
#include "textures/effect/lava_bubble.05820.rgba16.inc.c"
};

// 0x0B006020
const u8 *const lava_bubble_ptr_0B006020[] = {
    effect_0B002020,
    effect_0B002820,
    effect_0B003020,
    effect_0B003020,
    effect_0B003020,
    effect_0B003820,
    effect_0B004020,
    effect_0B004820,
    effect_0B005020,
    effect_0B005820,
};

// Bubble
// 0x0B006048
ALIGNED8 static const u8 effect_0B006048[] = {
#include "textures/effect/bubble.06048.rgba16.inc.c"
};

// 0x0B006848
const u8 *const bubble_ptr_0B006848[] = {
    effect_0B006048,
};

// Tiny Bubble
// 0x0B00684C
ALIGNED8 static const u8 effect_0B00684C[] = {
#include "textures/effect/tiny_bubble.0684C.rgba16.inc.c"
};

// 0x0B006A50 - 0x0B006AB0
const Gfx tiny_bubble_dl_0B006A50[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, effect_0B00684C),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0B006AB0 - 0x0B006AD8
const Gfx tiny_bubble_dl_0B006AB0[] = {
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0B006AD8
ALIGNED8 static const u8 effect_0B006AD8[] = {
#include "textures/effect/tiny_bubble.06AD8.rgba16.inc.c"
};

// 0x0B006CD8 - 0x0B006D38
const Gfx tiny_bubble_dl_0B006CD8[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, effect_0B006AD8),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_AA_ZB_TEX_EDGE2),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0B006D38 - 0x0B006D68
const Gfx tiny_bubble_dl_0B006D38[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_SHADING_SMOOTH),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_AA_ZB_TEX_EDGE2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPEndDisplayList(),
};

// 0x0B006D68 - 0x0B006D98
const Gfx tiny_bubble_dl_0B006D68[] = {
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};
