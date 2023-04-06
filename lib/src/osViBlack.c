#include "libultra_internal.h"
extern OSViContext *D_80334914;
// TODO: name magic constants
void osViBlack(u8 active) {
    register u32 int_disabled = __osDisableInt();
    if (active) {
        D_80334914->unk00 |= 0x20;
    } else {
        D_80334914->unk00 &= ~0x20;
    }
    __osRestoreInt(int_disabled);
}
