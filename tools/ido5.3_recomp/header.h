#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <assert.h>

#include "libc_impl.h"
#include "helpers.h"

#define RM_RN 0
#define RM_RZ 1
#define RM_RP 2
#define RM_RM 3

union FloatReg {
    float f[2];
    uint32_t w[2];
    double d;
};

#define cvt_w_d(f) \
    ((fcsr & RM_RZ) ? ((isnan(f) || f <= -2147483649.0 || f >= 2147483648.0) ? (fcsr |= 0x40, 2147483647) : (int)f) : (assert(0), 0))

#define cvt_w_s(f) cvt_w_d((double)f)

static union FloatReg f0 = {{0, 0}}, f2 = {{0, 0}}, f4 = {{0, 0}}, f6 = {{0, 0}}, f8 = {{0, 0}},
f10 = {{0, 0}}, f12 = {{0, 0}}, f14 = {{0, 0}}, f16 = {{0, 0}}, f18 = {{0, 0}}, f20 = {{0, 0}},
f22 = {{0, 0}}, f24 = {{0, 0}}, f26 = {{0, 0}}, f28 = {{0, 0}}, f30 = {{0, 0}};
static uint32_t fcsr = 1;
