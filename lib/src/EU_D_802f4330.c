#include "libultra_internal.h"

// an array of pointers to functions taking no arguments and returning u32...
// this is only referenced in the exception handler and here.  this function is called with a0=1 and
// then the same memory address is loaded.  it's definitely an array access though..
extern u32 (*D_80334920[8])(void) ;

void EU_D_802f4330(u32 a0, u32 a1(void)) {
    register u32 int_disabled = __osDisableInt();
    D_80334920[a0] = a1;
    __osRestoreInt(int_disabled);
}
