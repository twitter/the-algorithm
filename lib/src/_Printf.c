#include "libultra_internal.h"
#include <stdarg.h>
#include <string.h>
#include "printf.h"

#define ATOI(i, a)                                                                                     \
    for (i = 0; *a >= '0' && *a <= '9'; a++)                                                           \
        if (i < 999)                                                                                   \
            i = *a + i * 10 - '0';
#define _PROUT(dst, fmt, _size)                                                                        \
    if (_size > 0) {                                                                                   \
        dst = prout(dst, fmt, _size);                                                                  \
        if (dst != 0)                                                                                  \
            sp78.size += _size;                                                                        \
        else                                                                                           \
            return sp78.size;                                                                          \
    }
#define _PAD(i, m, c, src, extracond)                                                                  \
    if (extracond && m > 0)                                                                            \
        for (i = m; i > 0; i -= c) {                                                                   \
            if ((u32) i > 32)                                                                          \
                c = 32;                                                                                \
            else                                                                                       \
                c = i;                                                                                 \
            _PROUT(dst, src, c);                                                                       \
        }

const char length_str[] = "hlL";
const char flags_str[] = " +-#0";
const u32 flags_arr[] = { FLAGS_SPACE, FLAGS_PLUS, FLAGS_MINUS, FLAGS_HASH, FLAGS_ZERO, 0 };
char _spaces[] = "                                ";
char _zeroes[] = "00000000000000000000000000000000";

static void _Putfld(printf_struct *, va_list *, u8, u8 *);

s32 _Printf(char *(*prout)(char *, const char *, size_t), char *dst, const char *fmt, va_list args) {
    printf_struct sp78;
    const u8 *fmt_ptr;
    u8 c;
    const char *flag_index;
    u8 sp4c[0x20]; // probably a buffer?
    s32 sp48, sp44, sp40, sp3c, sp38, sp34, sp30, sp2c, sp28, sp24;
    sp78.size = 0;
    while (TRUE) {
        fmt_ptr = (u8 *) fmt;
#ifdef VERSION_SH
        // new version: don't point fmt_ptr beyond NUL character
        while ((c = *fmt_ptr) != 0 && c != '%') {
            fmt_ptr++;
        }
#else
        while ((c = *fmt_ptr++) > 0) {
            if (c == '%') {
                fmt_ptr--;
                break;
            }
        }
#endif
        _PROUT(dst, fmt, fmt_ptr - (u8 *) fmt);
        if (c == 0) {
            return sp78.size;
        }
        fmt = (char *) ++fmt_ptr;
        sp78.flags = 0;
        for (; (flag_index = strchr(flags_str, *fmt_ptr)) != NULL; fmt_ptr++) {
            sp78.flags |= flags_arr[flag_index - flags_str];
        }
        if (*fmt_ptr == '*') {
            sp78.width = va_arg(args, s32);
            if (sp78.width < 0) {
                sp78.width = -sp78.width;
                sp78.flags |= FLAGS_MINUS;
            }
            fmt_ptr++;
        } else {
            ATOI(sp78.width, fmt_ptr);
        }
        if (*fmt_ptr != '.') {
            sp78.precision = -1;
        } else {
            fmt_ptr++;
            if (*fmt_ptr == '*') {
                sp78.precision = va_arg(args, s32);
                fmt_ptr++;
            } else {
                ATOI(sp78.precision, fmt_ptr);
            }
        }
        if (strchr(length_str, *fmt_ptr) != NULL) {
            sp78.length = *fmt_ptr++;
        } else {
            sp78.length = 0;
        }

        if (sp78.length == 'l' && *fmt_ptr == 'l') {
            sp78.length = 'L';
            fmt_ptr++;
        }
        _Putfld(&sp78, &args, *fmt_ptr, sp4c);
        sp78.width -= sp78.part1_len + sp78.num_leading_zeros + sp78.part2_len + sp78.num_mid_zeros
                      + sp78.part3_len + sp78.num_trailing_zeros;
        _PAD(sp44, sp78.width, sp48, _spaces, !(sp78.flags & FLAGS_MINUS));
        _PROUT(dst, (char *) sp4c, sp78.part1_len);
        _PAD(sp3c, sp78.num_leading_zeros, sp40, _zeroes, 1);
        _PROUT(dst, sp78.buff, sp78.part2_len);
        _PAD(sp34, sp78.num_mid_zeros, sp38, _zeroes, 1);
        _PROUT(dst, (char *) (&sp78.buff[sp78.part2_len]), sp78.part3_len)
        _PAD(sp2c, sp78.num_trailing_zeros, sp30, _zeroes, 1);
        _PAD(sp24, sp78.width, sp28, _spaces, sp78.flags & FLAGS_MINUS);
        fmt = (char *) fmt_ptr + 1;
    }
}

static void _Putfld(printf_struct *a0, va_list *args, u8 type, u8 *buff) {
    a0->part1_len = a0->num_leading_zeros = a0->part2_len = a0->num_mid_zeros = a0->part3_len =
        a0->num_trailing_zeros = 0;

    switch (type) {

        case 'c':
            buff[a0->part1_len++] = va_arg(*args, u32);
            break;

        case 'd':
        case 'i':
            if (a0->length == 'l') {
                a0->value.s64 = va_arg(*args, s32);
            } else if (a0->length == 'L') {
                a0->value.s64 = va_arg(*args, s64);
            } else {
                a0->value.s64 = va_arg(*args, s32);
            }

            if (a0->length == 'h') {
                a0->value.s64 = (s16) a0->value.s64;
            }

            if (a0->value.s64 < 0) {
                buff[a0->part1_len++] = '-';
            } else if (a0->flags & FLAGS_PLUS) {
                buff[a0->part1_len++] = '+';
            } else if (a0->flags & FLAGS_SPACE) {
                buff[a0->part1_len++] = ' ';
            }

            a0->buff = (char *) &buff[a0->part1_len];

            _Litob(a0, type);
            break;

        case 'x':
        case 'X':
        case 'u':
        case 'o':
            if (a0->length == 'l') {
                a0->value.s64 = va_arg(*args, s32);
            } else if (a0->length == 'L') {
                a0->value.s64 = va_arg(*args, s64);
            } else {
                a0->value.s64 = va_arg(*args, s32);
            }

            if (a0->length == 'h') {
                a0->value.s64 = (u16) a0->value.s64;
            } else if (a0->length == 0) {
                a0->value.s64 = (u32) a0->value.s64;
            }

            if (a0->flags & FLAGS_HASH) {
                buff[a0->part1_len++] = '0';
                if (type == 'x' || type == 'X') {

                    buff[a0->part1_len++] = type;
                }
            }
            a0->buff = (char *) &buff[a0->part1_len];
            _Litob(a0, type);
            break;

        case 'e':
        case 'f':
        case 'g':
        case 'E':
        case 'G':
            //... okay?
            a0->value.f64 = a0->length == 'L' ? va_arg(*args, f64) : va_arg(*args, f64);

            if (a0->value.u16 & 0x8000) {
                buff[a0->part1_len++] = '-';
            } else {
                if (a0->flags & FLAGS_PLUS) {
                    buff[a0->part1_len++] = '+';
                } else if (a0->flags & FLAGS_SPACE) {
                    buff[a0->part1_len++] = ' ';
                }
            }

            a0->buff = (char *) &buff[a0->part1_len];
            _Ldtob(a0, type);
            break;

        case 'n':
            if (a0->length == 'h') {
                *(va_arg(*args, u16 *)) = a0->size;
            } else if (a0->length == 'l') {
                *va_arg(*args, u32 *) = a0->size;
            } else if (a0->length == 'L') {
                *va_arg(*args, u64 *) = a0->size;
            } else {
                *va_arg(*args, u32 *) = a0->size;
            }
            break;

        case 'p':
            a0->value.s64 = (intptr_t) va_arg(*args, void *);
            a0->buff = (char *) &buff[a0->part1_len];
            _Litob(a0, 'x');
            break;

        case 's':
            a0->buff = va_arg(*args, char *);
            a0->part2_len = strlen(a0->buff);
            if (a0->precision >= 0 && a0->part2_len > a0->precision) {
                a0->part2_len = a0->precision;
            }
            break;

        case '%':
            buff[a0->part1_len++] = '%';
            break;

        default:
            buff[a0->part1_len++] = type;
            break;
    }
}
