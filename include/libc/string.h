#ifndef STRING_H
#define STRING_H

#include "PR/ultratypes.h"

void *memcpy(void *dst, const void *src, size_t size);
size_t strlen(const char *str);
char *strchr(const char *str, s32 ch);

#endif
