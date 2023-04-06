#include <stdio.h>
#include <stdlib.h>
#include "vadpcm.h"

static void fmtchan(s32 size, u8 *p, s32 *data, s32 stride)
{
    s32 i;
    s32 c;

    for (i = 0; i < size; i++)
    {
        c = *data++;
        if (c < -0x7fff)
        {
            c = -0x7fff;
        }
        if (c >= 0x8000)
        {
            c = 0x7fff;
        }
        p[0] = c >> 8;
        p[1] = c;
        p += stride;
    }
}

void writeout(FILE *outfd, s32 size, s32 *l_out, s32 *r_out, s32 chans)
{
    static u8 obuf[0x1000];
    s32 i;

    switch (chans)
    {
    case 2:
        fmtchan(size, obuf, l_out, chans * 2);
        fmtchan(size, obuf + 2, r_out, chans * 2);
        if (outfd != NULL)
        {
            if ((i = fwrite(obuf, 1, size * 4, outfd)) != size * 4)
            {
                fprintf(stderr, "write error %d\n", i);
                exit(1);
            }
        }
        break;

    case 1:
        fmtchan(size, obuf, l_out, 2);
        if (outfd != NULL)
        {
            if ((i = fwrite(obuf, 1, size * 2, outfd)) != size * 2)
            {
                fprintf(stderr, "write error %d\n", i);
                exit(1);
            }
        }
        break;

    default:
        fprintf(stderr, "Error in number of channels\n");
        exit(1);
    }
}
