#include "libultra_internal.h"

extern OSViContext *__osViCurr;

OSViContext *__osViGetCurrentContext() {
    return __osViCurr;
}
