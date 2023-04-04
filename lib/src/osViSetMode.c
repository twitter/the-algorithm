#include "libultra_internal.h"

extern OSViContext *__osViNext;

void osViSetMode(OSViMode *mode) {
    register u32 int_disabled = __osDisableInt();
    __osViNext->modep = mode;
    __osViNext->unk00 = 1;
    __osViNext->features = __osViNext->modep->comRegs.ctrl;
    __osRestoreInt(int_disabled);
}
