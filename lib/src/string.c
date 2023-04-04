#include "libultra_internal.h"
#include <string.h>

void *memcpy(void *dst, const void *src, size_t size) {
    u8 *_dst = dst;
    const u8 *_src = src;
    while (size > 0) {
        *_dst++ = *_src++;
        size--;
    }
    return dst;
}

size_t strlen(const char *str) {
    const u8 *ptr = (const u8 *) str;
    while (*ptr) {
        ptr++;
    }
    return (const char *) ptr - str;
}

char *strchr(const char *str, s32 ch) {
    u8 c = ch;
    while (*(u8 *)str != c) {
        if (*(u8 *)str == 0) {
            return NULL;
        }
        str++;
    }
    return (char *) str;
}
