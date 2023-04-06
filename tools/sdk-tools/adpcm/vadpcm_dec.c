#include <unistd.h>
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <fcntl.h>
#include "vadpcm.h"

static void int_handler(s32 sig)
{
    s32 flags;

    flags = fcntl(STDIN_FILENO, F_GETFL, 0);
    flags &= ~FNDELAY;
    fcntl(STDIN_FILENO, F_SETFL, flags);
    exit(0);
}

static char usage[] = "bitfile";

#ifdef __sgi

// Declaring a sigaction like this is wildly unportable; you're supposed to
// assign members one by one in code. We do that in the non-SGI case.
static struct sigaction int_act = {
    /* sa_flags =   */ SA_RESTART,
    /* sa_handler = */ &int_handler,
    /* sa_mask =    */ 0,
};

#endif

s32 main(s32 argc, char **argv)
{
    s32 c;
    u8 cc;
    u8 doloop = 0;
    s16 order;
    s16 version;
    s16 nloops;
    s16 npredictors;
    s32 flags;
    s32 ***coefTable = NULL;
    s32 i;
    s32 j;
    s32 *outp;
    s32 *state;
    s32 done = 0;
    s32 num;
    u32 ts;
    s32 soundPointer;
    s32 cType;
    s32 offset;
    s32 currPos = 0;
    s32 nSamples;
    s32 framePos;
    s32 loopBegin;
    s32 left;
    ALADPCMloop *aloops;
    Chunk FormChunk;
    ChunkHeader Header;
    CommonChunk CommChunk;
    SoundDataChunk SndDChunk;
    char *ChunkName;
    FILE *ifile;
    char *progname = argv[0];

#ifndef __sgi
    nloops = 0;
#endif

    if (argc < 2)
    {
        fprintf(stderr, "%s %s\n", progname, usage);
        exit(1);
    }

    while ((c = getopt(argc, argv, "l")) != -1)
    {
        switch (c)
        {
        case 'l':
            doloop = 1;
            break;
        }
    }

    argv += optind - 1;
    if ((ifile = fopen(argv[1], MODE_READ)) == NULL)
    {
        fprintf(stderr, "%s: bitstream file [%s] could not be opened\n", progname, argv[1]);
        exit(1);
    }

    state = malloc(16 * sizeof(int));
    for (i = 0; i < 16; i++)
    {
        state[i] = 0;
    }

    fread(&FormChunk, sizeof(FormChunk), 1, ifile);
    BSWAP32(FormChunk.ckID)
    BSWAP32(FormChunk.formType)
    if ((FormChunk.ckID != 0x464f524d) || (FormChunk.formType != 0x41494643)) // FORM, AIFC
    {
        fprintf(stderr, "%s: [%s] is not an AIFF-C File\n", progname, argv[1]);
        exit(1);
    }

    while (!done)
    {
        num = fread(&Header, sizeof(Header), 1, ifile);
        if (num <= 0)
        {
            done = 1;
            break;
        }
        BSWAP32(Header.ckID)
        BSWAP32(Header.ckSize)

        Header.ckSize++, Header.ckSize &= ~1;
        switch (Header.ckID)
        {
        case 0x434f4d4d: // COMM
            offset = ftell(ifile);
            num = fread(&CommChunk, sizeof(CommChunk), 1, ifile);
            if (num <= 0)
            {
                fprintf(stderr, "%s: error parsing file [%s]\n", progname, argv[1]);
                done = 1;
            }
            BSWAP16(CommChunk.numChannels)
            BSWAP16(CommChunk.numFramesH)
            BSWAP16(CommChunk.numFramesL)
            BSWAP16(CommChunk.sampleSize)
            BSWAP16(CommChunk.compressionTypeH)
            BSWAP16(CommChunk.compressionTypeL)
            cType = (CommChunk.compressionTypeH << 16) + CommChunk.compressionTypeL;
            if (cType != 0x56415043) // VAPC
            {
                fprintf(stderr, "%s: file [%s] is of the wrong compression type.\n", progname, argv[1]);
                exit(1);
            }
            if (CommChunk.numChannels != 1)
            {
                fprintf(stderr, "%s: file [%s] contains %ld channels, only 1 channel supported.\n", progname, argv[1], (long) CommChunk.numChannels);
                exit(1);
            }
            if (CommChunk.sampleSize != 16)
            {
                fprintf(stderr, "%s: file [%s] contains %ld bit samples, only 16 bit samples supported.\n", progname, argv[1], (long) CommChunk.sampleSize);
                exit(1);
            }
            nSamples = (CommChunk.numFramesH << 16) + CommChunk.numFramesL;
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        case 0x53534e44: // SSND
            offset = ftell(ifile);
            fread(&SndDChunk, sizeof(SndDChunk), 1, ifile);
            BSWAP32(SndDChunk.offset)
            BSWAP32(SndDChunk.blockSize)
            // The assert error messages specify line numbers 165/166. Match
            // that using a #line directive.
#ifdef __sgi
#  line 164
#endif
            assert(SndDChunk.offset == 0);
            assert(SndDChunk.blockSize == 0);
            soundPointer = ftell(ifile);
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        case 0x4150504c: // APPL
            offset = ftell(ifile);
            fread(&ts, sizeof(u32), 1, ifile);
            BSWAP32(ts)
            if (ts == 0x73746f63) // stoc
            {
                ChunkName = ReadPString(ifile);
                if (strcmp("VADPCMCODES", ChunkName) == 0)
                {
                    fread(&version, sizeof(s16), 1, ifile);
                    BSWAP16(version)
                    if (version != 1)
                    {
                        fprintf(stderr, "Non-identical codebook chunk versions\n");
                    }
                    readaifccodebook(ifile, &coefTable, &order, &npredictors);
                }
                else if (strcmp("VADPCMLOOPS", ChunkName) == 0)
                {
                    fread(&version, sizeof(s16), 1, ifile);
                    BSWAP16(version)
                    if (version != 1)
                    {
                        fprintf(stderr, "Non-identical loop chunk versions\n");
                    }
                    aloops = readlooppoints(ifile, &nloops);
                }
            }
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        default:
            // We don't understand this chunk. Skip it.
            fseek(ifile, Header.ckSize, SEEK_CUR);
            break;
        }
    }

    if (coefTable == NULL)
    {
        // @bug should use progname; argv[0] may be an option
        fprintf(stderr, "%s: Codebook missing from bitstream [%s]\n", argv[0], argv[1]);
        exit(1);
    }

    outp = malloc(16 * sizeof(s32));
    for (i = 0; i < order; i++)
    {
        outp[15 - i] = 0;
    }

    fseek(ifile, soundPointer, SEEK_SET);
    if (doloop && nloops > 0)
    {
#ifndef __sgi
        struct sigaction int_act;
        int_act.sa_flags = SA_RESTART;
        int_act.sa_handler = int_handler;
        sigemptyset(&int_act.sa_mask);
#endif

        sigaction(SIGINT, &int_act, NULL);
        flags = fcntl(STDIN_FILENO, F_GETFL, 0);
        flags |= FNDELAY;
        fcntl(STDIN_FILENO, F_SETFL, flags);
        for (i = 0; i < nloops; i++)
        {
            while (currPos < aloops[i].end)
            {
                left = aloops[i].end - currPos;
                vdecodeframe(ifile, outp, order, coefTable);
                writeout(stdout, left < 16 ? left : 16, outp, outp, 1);
                currPos += 16;
            }

            while (read(STDIN_FILENO, &cc, 1) == 0)
            {
                framePos = (aloops[i].start >> 4) + 1;
                fseek(ifile, (framePos * 9) + soundPointer, SEEK_SET);
                for (j = 0; j < 16; j++)
                {
                    outp[j] = aloops[i].state[j];
                }
                loopBegin = aloops[i].start & 0xf;
                writeout(stdout, 16 - loopBegin, outp + loopBegin, outp + loopBegin, 1);
                currPos = framePos * 16;
                while (currPos < aloops[i].end)
                {
                    left = aloops[i].end - currPos;
                    vdecodeframe(ifile, outp, order, coefTable);
                    writeout(stdout, left < 16 ? left : 16, outp, outp, 1);
                    currPos += 16;
                }
            }

            left = 16 - left;
            if (left != 0)
            {
                writeout(stdout, left, &outp[left], &outp[left], 1);
            }

            while (currPos < nSamples)
            {
                vdecodeframe(ifile, outp, order, coefTable);
                left = nSamples - currPos;
                writeout(stdout, left < 16 ? left : 16, outp, outp, 1);
                currPos += 16;
            }

            flags = fcntl(STDIN_FILENO, F_GETFL, 0);
            flags &= ~FNDELAY;
            fcntl(STDIN_FILENO, F_SETFL, flags);
        }
    }
    else
    {
        while (currPos < nSamples)
        {
            vdecodeframe(ifile, outp, order, coefTable);
            writeout(stdout, 16, outp, outp, 1);
            currPos += 16;
        }
    }

    fclose(ifile);
    return 0;
}
