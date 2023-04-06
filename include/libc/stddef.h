#ifndef STDDEF_H
#define STDDEF_H

#include "PR/ultratypes.h"

#ifndef offsetof
#define offsetof(st, m) ((size_t)&(((st *)0)->m))
#endif

#endif
