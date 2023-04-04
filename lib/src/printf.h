#ifndef _PRINTF_H_
#define _PRINTF_H_
#include <stdarg.h>

typedef struct
{
    union {
        /* 00 */ s64 s64;
        u64 u64;
        f64 f64;
        u32 u32;
        u16 u16;
    } value;
    /* 08 */ char *buff;
    /* 0c */ s32 part1_len;
    /* 10 */ s32 num_leading_zeros;
    /* 14 */ s32 part2_len;
    /* 18 */ s32 num_mid_zeros;
    /* 1c */ s32 part3_len;
    /* 20 */ s32 num_trailing_zeros;
    /* 24 */ s32 precision;
    /* 28 */ s32 width;
    /* 2c */ u32 size;
    /* 30 */ u32 flags;
    /* 34 */ u8 length;
} printf_struct;

#define FLAGS_SPACE 1
#define FLAGS_PLUS 2
#define FLAGS_MINUS 4
#define FLAGS_HASH 8
#define FLAGS_ZERO 16
s32 _Printf(char *(*prout)(char *, const char *, size_t), char *dst, const char *fmt, va_list args);
void _Litob(printf_struct *args, u8 type);
void _Ldtob(printf_struct *args, u8 type);
#endif
