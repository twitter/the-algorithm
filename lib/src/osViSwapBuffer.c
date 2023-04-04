#include "libultra_internal.h"

extern OSViContext *__osViNext;

void osViSwapBuffer(void *vaddr) {
    u32 int_disabled = __osDisableInt();
    __osViNext->buffer = vaddr;
    __osViNext->unk00 |= 0x10; // TODO: figure out what this flag means
    __osRestoreInt(int_disabled);
}
