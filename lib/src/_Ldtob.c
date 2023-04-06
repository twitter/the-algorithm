#include "libultra_internal.h"
#include <macros.h>
#include <stdlib.h>
#include <string.h>
#include "printf.h"

#define BUFF_LEN 0x20

static s16 _Ldunscale(s16 *, printf_struct *);
static void _Genld(printf_struct *, u8, u8 *, s16, s16);

const double D_80338670[] = { 10e0L, 10e1L, 10e3L, 10e7L, 10e15L, 10e31L, 10e63L, 10e127L, 10e255L };

/* float properties */
#define _D0 0
#define _DBIAS 0x3ff
#define _DLONG 1
#define _DOFF 4
#define _FBIAS 0x7e
#define _FOFF 7
#define _FRND 1
#define _LBIAS 0x3ffe
#define _LOFF 15
/* integer properties */
#define _C2 1
#define _CSIGN 1
#define _ILONG 0
#define _MBMAX 8
#define NAN 2
#define INF 1
#define FINITE -1
#define _DFRAC ((1 << _DOFF) - 1)
#define _DMASK (0x7fff & ~_DFRAC)
#define _DMAX ((1 << (15 - _DOFF)) - 1)
#define _DNAN (0x8000 | _DMAX << _DOFF | 1 << (_DOFF - 1))
#define _DSIGN 0x8000
#if _D0 == 3
#define _D1 2 /* little-endian order */
#define _D2 1
#define _D3 0
#else
#define _D1 1 /* big-endian order */
#define _D2 2
#define _D3 3
#endif

void _Ldtob(printf_struct *args, u8 type) {
    u8 buff[BUFF_LEN];
    u8 *ptr;
    UNUSED u32 sp70;
    f64 val;
    /* maybe struct? */
    s16 err;
    s16 nsig;
    s16 exp;

    s32 i;
    s32 n;
    f64 factor;
    s32 gen;
    s32 j;
    s32 lo;
    ldiv_t qr;
    u8 drop;
    s32 n2;
    /* */
    UNUSED u8 unused[0x4];
    ptr = buff;
    val = args->value.f64;
    if (args->precision < 0) {
        args->precision = 6;
    } else {
        if (args->precision == 0 && (type == 'g' || type == 'G')) {
            args->precision = 1;
        }
    }
    err = _Ldunscale(&exp, args);
    if (err > 0) {
        memcpy(args->buff, err == 2 ? "NaN" : "Inf", args->part2_len = 3);
        return;
    }
    if (err == 0) {
        nsig = 0;
        exp = 0;
    } else {
        if (val < 0) {
            val = -val;
        }
        exp = exp * 30103 / 0x000186A0 - 4;
        if (exp < 0) {
            n = (3 - exp) & ~3;
            exp = -n;
            for (i = 0; n > 0; n >>= 1, i++) {
                if ((n & 1) != 0) {
                    val *= D_80338670[i];
                }
            }
        } else {
            if (exp > 0) {
                factor = 1;
                exp &= ~3;
                for (n = exp, i = 0; n > 0; n >>= 1, i++) {
                    if ((n & 1) != 0) {
                        factor *= D_80338670[i];
                    }
                }
                val /= factor;
            }
        }
        gen = ((type == 'f') ? exp + 10 : 6) + args->precision;
        if (gen > 0x13) {
            gen = 0x13;
        }
        *ptr++ = '0';
        while (gen > 0 && 0 < val) {
            lo = val;
            if ((gen -= 8) > 0) {
                val = (val - lo) * 1.0e8;
            }
            ptr = ptr + 8;
            for (j = 8; lo > 0 && --j >= 0;) {
                qr = ldiv(lo, 10);
                *--ptr = qr.rem + '0';
                lo = qr.quot;
            }
            while (--j >= 0) {
                ptr--;
                *ptr = '0';
            }
            ptr += 8;
        }

        gen = ptr - &buff[1];
        for (ptr = &buff[1], exp += 7; *ptr == '0'; ptr++) {
            --gen, --exp;
        }

        nsig = ((type == 'f') ? exp + 1 : ((type == 'e' || type == 'E') ? 1 : 0)) + args->precision;
        if (gen < nsig) {
            nsig = gen;
        }
        if (nsig > 0) {
            if (nsig < gen && ptr[nsig] > '4') {
                drop = '9';
            } else {
                drop = '0';
            }

            for (n2 = nsig; ptr[--n2] == drop;) {
                nsig--;
            }
            if (drop == '9') {
                ptr[n2]++;
            }
            if (n2 < 0) {
                --ptr, ++nsig, ++exp;
            }
        }
    }
    _Genld(args, type, ptr, nsig, exp);
}

static s16 _Ldunscale(s16 *pex, printf_struct *px) {

    unsigned short *ps = (unsigned short *) px;
    short xchar = (ps[_D0] & _DMASK) >> _DOFF;
    if (xchar == _DMAX) { /* NaN or INF */
        *pex = 0;
        return (s16)(ps[_D0] & _DFRAC || ps[_D1] || ps[_D2] || ps[_D3] ? NAN : INF);
    } else if (0 < xchar) {
        ps[_D0] = (ps[_D0] & ~_DMASK) | (_DBIAS << _DOFF);
        *pex = xchar - (_DBIAS - 1);
        return (FINITE);
    }
    if (0 > xchar) {
        return NAN;
    } else {
        *pex = 0;
        return (0);
    }
}

static void _Genld(printf_struct *px, u8 code, u8 *p, s16 nsig, s16 xexp) {
    u8 point = '.';
    if (nsig <= 0) {
        nsig = 1,

        p = (u8 *) "0";
    }

    if (code == 'f'
        || ((code == 'g' || code == 'G') && (-4 <= xexp) && (xexp < px->precision))) { /* 'f' format */
        ++xexp;            /* change to leading digit count */
        if (code != 'f') { /* fixup for 'g' */
            if (!(px->flags & FLAGS_HASH) && nsig < px->precision) {
                px->precision = nsig;
            }
            if ((px->precision -= xexp) < 0) {
                px->precision = 0;
            }
        }
        if (xexp <= 0) { /* digits only to right of point */
            px->buff[px->part2_len++] = '0';
            if (0 < px->precision || px->flags & FLAGS_HASH) {
                px->buff[px->part2_len++] = point;
            }
            if (px->precision < -xexp) {
                xexp = -px->precision;
            }
            px->num_mid_zeros = -xexp;
            px->precision += xexp;
            if (px->precision < nsig) {
                nsig = px->precision;
            }
            memcpy(&px->buff[px->part2_len], p, px->part3_len = nsig);
            px->num_trailing_zeros = px->precision - nsig;
        } else if (nsig < xexp) { /* zeros before point */
            memcpy(&px->buff[px->part2_len], p, nsig);
            px->part2_len += nsig;
            px->num_mid_zeros = xexp - nsig;
            if (0 < px->precision || px->flags & FLAGS_HASH) {
                px->buff[px->part2_len] = point, ++px->part3_len;
            }
            px->num_trailing_zeros = px->precision;
        } else { /* enough digits before point */
            memcpy(&px->buff[px->part2_len], p, xexp);
            px->part2_len += xexp;
            nsig -= xexp;
            if (0 < px->precision || px->flags & FLAGS_HASH) {
                px->buff[px->part2_len++] = point;
            }
            if (px->precision < nsig) {
                nsig = px->precision;
            }
            memcpy(&px->buff[px->part2_len], p + xexp, nsig);
            px->part2_len += nsig;
            px->num_mid_zeros = px->precision - nsig;
        }
    } else {                              /* 'e' format */
        if (code == 'g' || code == 'G') { /* fixup for 'g' */
            if (nsig < px->precision) {
                px->precision = nsig;
            }
            if (--px->precision < 0) {
                px->precision = 0;
            }
            code = code == 'g' ? 'e' : 'E';
        }
        px->buff[px->part2_len++] = *p++;
        if (0 < px->precision || px->flags & FLAGS_HASH) {
            px->buff[px->part2_len++] = point;
        }
        if (0 < px->precision) { /* put fraction digits */
            if (px->precision < --nsig) {
                nsig = px->precision;
            }
            memcpy(&px->buff[px->part2_len], p, nsig);
            px->part2_len += nsig;
            px->num_mid_zeros = px->precision - nsig;
        }
        p = (u8 *) &px->buff[px->part2_len]; /* put exponent */
        *p++ = code;
        if (0 <= xexp) {
            *p++ = '+';
        } else { /* negative exponent */
            *p++ = '-';
            xexp = -xexp;
        }
        if (100 <= xexp) { /* put oversize exponent */
            if (1000 <= xexp) {
                *p++ = xexp / 1000 + '0', xexp %= 1000;
            }
            *p++ = xexp / 100 + '0', xexp %= 100;
        }
        *p++ = xexp / 10 + '0', xexp %= 10;
        *p++ = xexp + '0';
        px->part3_len = p - (u8 *) &px->buff[px->part2_len];
    }
    if ((px->flags & (FLAGS_ZERO | FLAGS_MINUS)) == FLAGS_ZERO) { /* pad with leading zeros */
        int n =
            px->part1_len + px->part2_len + px->num_mid_zeros + px->part3_len + px->num_trailing_zeros;

        if (n < px->width) {
            px->num_leading_zeros = px->width - n;
        }
    }
}
