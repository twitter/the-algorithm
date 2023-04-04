#include "libultra_internal.h"

u8 __osContAddressCrc(u16 addr) {
    u8 temp;
    u8 temp2;
    int i;
    temp = 0;
    for (i = 0; i < 16; i++) {
        if (temp & 0x10) {
            temp2 = 21;
        } else {
            temp2 = 0;
        }

        temp <<= 1;
        temp |= (u8)((addr & 0x400) ? 1 : 0);
        addr <<= 1;
        temp ^= temp2;
    }
    return temp & 0x1f;
}

u8 __osContDataCrc(u8 *data) {
    u8 temp;
    u8 temp2;
    int i;
    int j;
    temp = 0;
    for (i = 0; i <= 32; i++, data++) {
        for (j = 7; j >= 0; j--) {
            if (temp & 0x80) {
                temp2 = 133;
            } else {
                temp2 = 0;
            }
            temp <<= 1;
            if (i == 32) {
                temp &= -1;
            } else {
                temp |= ((*data & (1 << j)) ? 1 : 0);
            }
            temp ^= temp2;
        }
    }
    return temp;
}
