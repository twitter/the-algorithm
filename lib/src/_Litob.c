#include "libultra_internal.h"
#include <stdlib.h>
#include <string.h>
#include "printf.h"

#define BUFF_LEN 0x18

static u8 D_80334960[] = "0123456789abcdef";
static u8 D_80334974[] = "0123456789ABCDEF";

void _Litob(printf_struct *args, u8 type) {
    u8 buff[BUFF_LEN];
    const u8 *num_map;
    s32 base;
    s32 buff_ind;
    u64 num;
    lldiv_t quotrem;

    if (type == 'X') {
        num_map = D_80334974;
    } else {
        num_map = D_80334960;
    }

    base = (type == 'o') ? 8 : ((type != 'x' && type != 'X') ? 10 : 16);
    buff_ind = BUFF_LEN;
    num = args->value.s64;

    if ((type == 'd' || type == 'i') && args->value.s64 < 0) {
        num = -num;
    }

    if (num != 0 || args->precision != 0) {
        buff[--buff_ind] = num_map[num % base];
    }

    args->value.s64 = num / base;

    while (args->value.s64 > 0 && buff_ind > 0) {
        quotrem = lldiv(args->value.s64, base);
        args->value.s64 = quotrem.quot;
        buff[--buff_ind] = num_map[quotrem.rem];
    }

    args->part2_len = BUFF_LEN - buff_ind;

    memcpy(args->buff, buff + buff_ind, args->part2_len);

    if (args->part2_len < args->precision) {
        args->num_leading_zeros = args->precision - args->part2_len;
    }

    if (args->precision < 0 && (args->flags & (FLAGS_ZERO | FLAGS_MINUS)) == FLAGS_ZERO) {
        buff_ind = args->width - args->part1_len - args->num_leading_zeros - args->part2_len;
        if (buff_ind > 0) {
            args->num_leading_zeros += buff_ind;
        }
    }
}
