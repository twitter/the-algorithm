#include <stdio.h>
#include <stdlib.h>
#include "vadpcm.h"

static s32 input_word = 0;
static s32 in_bit_pos = -1;

static u32 getshort(FILE *ifile)
{
    u32 c1;
    u32 c2;

    if ((c1 = getc(ifile)) == -1)
    {
        return 0;
    }

    if ((c2 = getc(ifile)) == -1)
    {
        return 0;
    }

    return (c1 << 8) | c2;
}

u32 readbits(u32 nbits, FILE *ifile)
{
    u32 c;
    u32 b;
    u32 left;
    u32 mask;

    if (nbits <= in_bit_pos + 1)
    {
        mask = (1U << nbits) - 1;
        b = ((u32) input_word >> (in_bit_pos - nbits + 1)) & mask;
        in_bit_pos -= nbits;
        if (in_bit_pos < 0)
        {
            c = getshort(ifile);
            input_word = c;
            in_bit_pos = 15;
        }
        return b;
    }
    else
    {
        b = input_word & ((1U << (in_bit_pos + 1)) - 1);
        left = nbits - in_bit_pos - 1;
        c = getshort(ifile);
        input_word = c;
        in_bit_pos = 15;
        b = readbits(left, ifile) | (b << left);
        return b;
    }
}

char *ReadPString(FILE *ifile)
{
    u8 c;
    char *st;

    fread(&c, 1, 1, ifile);
    st = malloc(c + 1);
    fread(st, c, 1, ifile);
    st[c] = '\0';
    if ((c & 1) == 0)
    {
        fread(&c, 1, 1, ifile);
    }
    return st;
}

s32 lookupMarker(u32 *sample, s16 loopPoint, Marker *markers, s32 nmarkers)
{
    s32 i;

    for (i = 0; i < nmarkers; i++)
    {
        if (markers[i].MarkerID == loopPoint)
        {
            *sample = (markers[i].positionH << 16) + markers[i].positionL;
            return 0;
        }
    }
    return 1;
}

ALADPCMloop *readlooppoints(FILE *ifile, s16 *nloops)
{
    s32 i;
    ALADPCMloop *al;

    fread(nloops, sizeof(s16), 1, ifile);
    BSWAP16(*nloops)
    al = malloc(*nloops * sizeof(ALADPCMloop));
    for (i = 0; i < *nloops; i++)
    {
        fread(&al[i], sizeof(ALADPCMloop), 1, ifile);
        BSWAP32(al[i].start)
        BSWAP32(al[i].end)
        BSWAP32(al[i].count)
        BSWAP16_MANY(al[i].state, 16)
    }
    return al;
}
