// 0x06046734
static const s16 bowser_seg6_animvalue_06046734[] = {
    0x0000, 0x0076, 0x00DB, 0x0050, 0xC21C, 0xE8CC, 0xC63B, 0x0000,
};

// 0x06046744
static const u16 bowser_seg6_animindex_06046744[] = {
    0x0001, 0x0001, 0x0001, 0x0002, 0x0001, 0x0003, 0x0001, 0x0004, 0x0001, 0x0005, 0x0001, 0x0006,
};

// 0x0604675C
static const struct Animation bowser_seg6_anim_0604675C = {
    0,
    0,
    0,
    0,
    0x64,
    ANIMINDEX_NUMPARTS(bowser_seg6_animindex_06046744),
    bowser_seg6_animvalue_06046734,
    bowser_seg6_animindex_06046744,
    0,
};
