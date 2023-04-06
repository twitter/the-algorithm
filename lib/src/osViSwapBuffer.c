#include "libultra_internal.h"
extern OSViContext *D_80334914;
void osViSwapBuffer(void *vaddr) {
    u32 int_disabled = __osDisableInt();
    D_80334914->buffer = vaddr;
    D_80334914->unk00 |= 0x10; // TODO: figure out what this flag means
    __osRestoreInt(int_disabled);
}
