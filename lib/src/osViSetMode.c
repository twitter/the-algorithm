#include "libultra_internal.h"
extern OSViContext *D_80334914;
void osViSetMode(OSViMode *mode) {
    register u32 int_disabled = __osDisableInt();
    D_80334914->unk08 = mode;
    D_80334914->unk00 = 1;
    D_80334914->features = D_80334914->unk08->comRegs.ctrl;
    __osRestoreInt(int_disabled);
}
