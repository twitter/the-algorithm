#include <string.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include "vadpcm.h"

static char usage[] = "[-t -l min_loop_length] -c codebook aifcfile compressedfile";

int main(int argc, char **argv)
{
    s32 c;
    char *progname = argv[0];
    s16 nloops = 0;
    s16 numMarkers;
    s16 *inBuffer;
    s16 ts;
    s32 minLoopLength = 800;
    s32 ***coefTable = NULL;
    s32 *state;
    s32 order;
    s32 npredictors;
    s32 done = 0;
    s32 truncate = 0;
    s32 num;
    s32 tableSize;
    s32 nsam;
    s32 left;
    u32 newEnd;
    s32 nRepeats;
    s32 i;
    s32 j;
    s32 k;
    s32 nFrames;
    s32 offset;
    s32 cChunkPos;
    s32 currentPos;
    s32 soundPointer = 0;
    s32 startPointer = 0;
    s32 startSoundPointer = 0;
    s32 cType;
    s32 nBytes = 0;
    u32 loopEnd;
    char *compName = "VADPCM ~4-1";
    char *appCodeName = "VADPCMCODES";
    char *appLoopName = "VADPCMLOOPS";
    u8 strnLen;
    Chunk AppChunk;
    Chunk FormChunk;
    ChunkHeader CSndChunk;
    ChunkHeader Header;
    CommonChunk CommChunk;
    SoundDataChunk SndDChunk;
    InstrumentChunk InstChunk;
    Loop *loops = NULL;
    ALADPCMloop *aloops;
    Marker *markers;
    CodeChunk cChunk;
    char filename[1024];
    FILE *fhandle;
    FILE *ifile;
    FILE *ofile;

    if (argc < 2)
    {
        fprintf(stderr, "%s %s\n", progname, usage);
        exit(1);
    }

    while ((c = getopt(argc, argv, "tc:l:")) != -1)
    {
        switch (c)
        {
        case 'c':
            if (sscanf(optarg, "%s", filename) == 1)
            {
                if ((fhandle = fopen(filename, "r")) == NULL)
                {
                    fprintf(stderr, "Codebook file %s could not be opened\n", filename);
                    exit(1);
                }
                if (readcodebook(fhandle, &coefTable, &order, &npredictors) != 0)
                {
                    fprintf(stderr, "Error reading codebook\n");
                    exit(1);
                }
            }
            break;

        case 't':
            truncate = 1;
            break;

        case 'l':
            sscanf(optarg, "%d", &minLoopLength);
            break;

        default:
            break;
        }
    }

    if (coefTable == 0)
    {
        fprintf(stderr, "You should specify a coefficient codebook with the [-c] option\n");
        exit(1);
    }

    argv += optind - 1;
    if ((ifile = fopen(argv[1], MODE_READ)) == NULL)
    {
        fprintf(stderr, "%s: input file [%s] could not be opened.\n", progname, argv[1]);
        exit(1);
    }
    if ((ofile = fopen(argv[2], MODE_WRITE)) == NULL)
    {
        fprintf(stderr, "%s: output file [%s] could not be opened.\n", progname, argv[2]);
        exit(1);
    }

    state = malloc(16 * sizeof(s32));
    for (i = 0; i < 16; i++)
    {
        state[i] = 0;
    }

#ifndef __sgi
    // If there is no instrument chunk, make sure to output zeroes instead of
    // garbage. (This matches how the IRIX -g-compiled version behaves.)
    memset(&InstChunk, 0, sizeof(InstChunk));
#endif

    inBuffer = malloc(16 * sizeof(s16));

    fread(&FormChunk, sizeof(Chunk), 1, ifile);
    BSWAP32(FormChunk.ckID)
    BSWAP32(FormChunk.ckSize)
    BSWAP32(FormChunk.formType)

    // @bug This doesn't check for FORM for AIFF files, probably due to mistaken operator precedence.
    if (!((FormChunk.ckID == 0x464f524d && // FORM
           FormChunk.formType == 0x41494643) || // AIFC
           FormChunk.formType == 0x41494646)) // AIFF
    {
        fprintf(stderr, "%s: [%s] is not an AIFF-C File\n", progname, argv[1]);
        exit(1);
    }

    while (!done)
    {
        num = fread(&Header, 8, 1, ifile);
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
            num = fread(&CommChunk, sizeof(CommonChunk), 1, ifile);
            if (num <= 0)
            {
                fprintf(stderr, "%s: error parsing file [%s]\n", progname, argv[1]);
                done = 1;
            }
            BSWAP16(CommChunk.numChannels)
            BSWAP16(CommChunk.numFramesH)
            BSWAP16(CommChunk.numFramesL)
            BSWAP16(CommChunk.sampleSize)
            if (FormChunk.formType != 0x41494646) // AIFF
            {
                BSWAP16(CommChunk.compressionTypeH)
                BSWAP16(CommChunk.compressionTypeL)
                cType = (CommChunk.compressionTypeH << 16) + CommChunk.compressionTypeL;
                if (cType != 0x4e4f4e45) // NONE
                {
                    fprintf(stderr, "%s: file [%s] contains compressed data.\n", progname, argv[1]);
                    exit(1);
                }
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
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        case 0x53534e44: // SSND
            offset = ftell(ifile);
            fread(&SndDChunk, sizeof(SoundDataChunk), 1, ifile);
            BSWAP32(SndDChunk.offset)
            BSWAP32(SndDChunk.blockSize)
            // The assert error messages specify line numbers 219/220. Match
            // that using a #line directive.
#ifdef __sgi
#  line 218
#endif
            assert(SndDChunk.offset == 0);
            assert(SndDChunk.blockSize == 0);
            soundPointer = ftell(ifile);
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        case 0x4d41524b: // MARK
            offset = ftell(ifile);
            fread(&numMarkers, sizeof(s16), 1, ifile);
            BSWAP16(numMarkers)
            markers = malloc(numMarkers * sizeof(Marker));
            for (i = 0; i < numMarkers; i++)
            {
                fread(&markers[i], sizeof(Marker), 1, ifile);
                BSWAP16(markers[i].MarkerID)
                BSWAP16(markers[i].positionH)
                BSWAP16(markers[i].positionL)
                fread(&strnLen, 1, 1, ifile);
                if ((strnLen & 1) != 0)
                {
                    fseek(ifile, strnLen, SEEK_CUR);
                }
                else
                {
                    fseek(ifile, strnLen + 1, SEEK_CUR);
                }
            }
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        case 0x494e5354: // INST
            offset = ftell(ifile);
            fread(&InstChunk, sizeof(InstrumentChunk), 1, ifile);
            BSWAP16(InstChunk.sustainLoop.playMode)
            BSWAP16(InstChunk.sustainLoop.beginLoop)
            BSWAP16(InstChunk.sustainLoop.endLoop)
            BSWAP16(InstChunk.releaseLoop.playMode)
            BSWAP16(InstChunk.releaseLoop.beginLoop)
            BSWAP16(InstChunk.releaseLoop.endLoop)
            aloops = malloc(2 * sizeof(ALADPCMloop));
            loops = malloc(2 * sizeof(Loop));
            if (InstChunk.sustainLoop.playMode == 1)
            {
                loops[nloops].beginLoop = InstChunk.sustainLoop.beginLoop;
                loops[nloops].endLoop = InstChunk.sustainLoop.endLoop;
                nloops++;
            }
            if (InstChunk.releaseLoop.playMode == 1)
            {
                loops[nloops].beginLoop = InstChunk.releaseLoop.beginLoop;
                loops[nloops].endLoop = InstChunk.releaseLoop.endLoop;
                nloops++;
            }
            fseek(ifile, offset + Header.ckSize, SEEK_SET);
            break;

        default:
            fseek(ifile, Header.ckSize, SEEK_CUR);
            break;
        }
    }

    FormChunk.formType = 0x41494643; // AIFC
    BSWAP32(FormChunk.ckID)
    BSWAP32(FormChunk.ckSize)
    BSWAP32(FormChunk.formType)
    fwrite(&FormChunk, sizeof(Chunk), 1, ofile);

    Header.ckID = 0x434f4d4d; // COMM
    Header.ckSize = sizeof(CommonChunk) + 1 + 11;
    BSWAP32(Header.ckID)
    BSWAP32(Header.ckSize)
    fwrite(&Header, sizeof(ChunkHeader), 1, ofile);
    CommChunk.compressionTypeH = 0x5641; // VA
    CommChunk.compressionTypeL = 0x5043; // PC
    cChunkPos = ftell(ofile);
    // CommChunk written later
    fwrite(&CommChunk, sizeof(CommonChunk), 1, ofile);
    strnLen = sizeof("VADPCM ~4-1") - 1;
    fwrite(&strnLen, 1, 1, ofile);
    fwrite(compName, strnLen, 1, ofile);

    Header.ckID = 0x494e5354; // INST
    Header.ckSize = sizeof(InstrumentChunk);
    BSWAP32(Header.ckID)
    BSWAP32(Header.ckSize)
    fwrite(&Header, sizeof(ChunkHeader), 1, ofile);
    BSWAP16(InstChunk.sustainLoop.playMode)
    BSWAP16(InstChunk.sustainLoop.beginLoop)
    BSWAP16(InstChunk.sustainLoop.endLoop)
    BSWAP16(InstChunk.releaseLoop.playMode)
    BSWAP16(InstChunk.releaseLoop.beginLoop)
    BSWAP16(InstChunk.releaseLoop.endLoop)
    fwrite(&InstChunk, sizeof(InstrumentChunk), 1, ofile);

    tableSize = order * 2 * npredictors * 8;
    strnLen = sizeof("VADPCMCODES") - 1;
    AppChunk.ckID = 0x4150504c; // APPL
    AppChunk.ckSize = 4 + tableSize + 1 + strnLen + sizeof(CodeChunk);
    AppChunk.formType = 0x73746f63; // stoc
    BSWAP32(AppChunk.ckID)
    BSWAP32(AppChunk.ckSize)
    BSWAP32(AppChunk.formType)
    fwrite(&AppChunk, sizeof(Chunk), 1, ofile);
    cChunk.version = 1;
    cChunk.order = order;
    cChunk.nEntries = npredictors;
    BSWAP16(cChunk.version)
    BSWAP16(cChunk.order)
    BSWAP16(cChunk.nEntries)
    fwrite(&strnLen, 1, 1, ofile);
    fwrite(appCodeName, strnLen, 1, ofile);
    fwrite(&cChunk, sizeof(CodeChunk), 1, ofile);

    for (i = 0; i < npredictors; i++)
    {
        for (j = 0; j < order; j++)
        {
            for (k = 0; k < 8; k++)
            {
                ts = coefTable[i][k][j];
                BSWAP16(ts)
                fwrite(&ts, sizeof(s16), 1, ofile);
            }
        }
    }

    currentPos = 0;
    if (soundPointer > 0)
    {
        fseek(ifile, soundPointer, SEEK_SET);
    }
    else
    {
        fprintf(stderr, "%s: Error in sound chunk", progname);
        exit(1);
    }

    soundPointer = ftell(ofile);
    // CSndChunk written later
    fwrite(&CSndChunk, sizeof(ChunkHeader), 1, ofile);
    BSWAP32(SndDChunk.offset)
    BSWAP32(SndDChunk.blockSize)
    fwrite(&SndDChunk, sizeof(SoundDataChunk), 1, ofile);
    startSoundPointer = ftell(ifile);
    for (i = 0; i < nloops; i++)
    {
        if (lookupMarker(&aloops[i].start, loops[i].beginLoop, markers, numMarkers) != 0)
        {
            fprintf(stderr, "%s: Start loop marker not found\n", progname);
        }
        else if (lookupMarker(&aloops[i].end, loops[i].endLoop, markers, numMarkers) != 0)
        {
            fprintf(stderr, "%s: End loop marker not found\n", progname);
        }
        else
        {
            startPointer = startSoundPointer + aloops[i].start * 2;
            nRepeats = 0;
            newEnd = aloops[i].end;
            while (newEnd - aloops[i].start < minLoopLength)
            {
                nRepeats++;
                newEnd += aloops[i].end - aloops[i].start;
            }

            while (currentPos <= aloops[i].start)
            {
                if (fread(inBuffer, sizeof(s16), 16, ifile) == 16)
                {
                    BSWAP16_MANY(inBuffer, 16)
                    vencodeframe(ofile, inBuffer, state, coefTable, order, npredictors, 16);
                    currentPos += 16;
                    nBytes += 9;
                }
                else
                {
                    fprintf(stderr, "%s: Not enough samples in file [%s]\n", progname, argv[1]);
                    exit(1);
                }
            }

            for (j = 0; j < 16; j++)
            {
                if (state[j] >= 0x8000)
                {
                    state[j] = 0x7fff;
                }
                if (state[j] < -0x7fff)
                {
                    state[j] = -0x7fff;
                }
                aloops[i].state[j] = state[j];
            }

            aloops[i].count = -1;
            while (nRepeats > 0)
            {
                for (; currentPos + 16 < aloops[i].end; currentPos += 16)
                {
                    if (fread(inBuffer, sizeof(s16), 16, ifile) == 16)
                    {
                        BSWAP16_MANY(inBuffer, 16)
                        vencodeframe(ofile, inBuffer, state, coefTable, order, npredictors, 16);
                        nBytes += 9;
                    }
                }
                left = aloops[i].end - currentPos;
                fread(inBuffer, sizeof(s16), left, ifile);
                BSWAP16_MANY(inBuffer, left)
                fseek(ifile, startPointer, SEEK_SET);
                fread(inBuffer + left, sizeof(s16), 16 - left, ifile);
                BSWAP16_MANY(inBuffer + left, 16 - left)
                vencodeframe(ofile, inBuffer, state, coefTable, order, npredictors, 16);
                nBytes += 9;
                currentPos = aloops[i].start - left + 16;
                nRepeats--;
            }
            aloops[i].end = newEnd;
        }
    }

    nFrames = (CommChunk.numFramesH << 16) + CommChunk.numFramesL;
    if ((nloops > 0U) & truncate)
    {
        lookupMarker(&loopEnd, loops[nloops - 1].endLoop, markers, numMarkers);
        nFrames = (loopEnd + 16 < nFrames ? loopEnd + 16 : nFrames);
    }

    while (currentPos < nFrames)
    {
        if (nFrames - currentPos < 16)
        {
            nsam = nFrames - currentPos;
        }
        else
        {
            nsam = 16;
        }

        if (fread(inBuffer, 2, nsam, ifile) == nsam)
        {
            BSWAP16_MANY(inBuffer, nsam)
            vencodeframe(ofile, inBuffer, state, coefTable, order, npredictors, nsam);
            currentPos += nsam;
            nBytes += 9;
        }
        else
        {
            fprintf(stderr, "Missed a frame!\n");
            break;
        }
    }

    if (nBytes % 2)
    {
        nBytes++;
        ts = 0;
        fwrite(&ts, 1, 1, ofile);
    }

    if (nloops > 0)
    {
        strnLen = sizeof("VADPCMLOOPS") - 1;
        AppChunk.ckID = 0x4150504c; // APPL
        AppChunk.ckSize = nloops * sizeof(ALADPCMloop) + strnLen + 4 + 1 + 2 + 2;
        AppChunk.formType = 0x73746f63; // stoc
        BSWAP32(AppChunk.ckID)
        BSWAP32(AppChunk.ckSize)
        BSWAP32(AppChunk.formType)
        fwrite(&AppChunk, sizeof(Chunk), 1, ofile);
        fwrite(&strnLen, 1, 1, ofile);
        fwrite(appLoopName, strnLen, 1, ofile);
        ts = 1;
        BSWAP16(ts)
        fwrite(&ts, sizeof(s16), 1, ofile);
        BSWAP16(nloops)
        fwrite(&nloops, sizeof(s16), 1, ofile);
        BSWAP16(nloops)
        for (i = 0; i < nloops; i++)
        {
            BSWAP32(aloops[i].start)
            BSWAP32(aloops[i].end)
            BSWAP32(aloops[i].count)
            BSWAP16_MANY(aloops[i].state, 16)
            fwrite(&aloops[i], sizeof(ALADPCMloop), 1, ofile);
        }
    }

    fseek(ofile, soundPointer, SEEK_SET);
    CSndChunk.ckID = 0x53534e44; // SSND
    CSndChunk.ckSize = nBytes + 8;
    BSWAP32(CSndChunk.ckID)
    BSWAP32(CSndChunk.ckSize)
    fwrite(&CSndChunk, sizeof(ChunkHeader), 1, ofile);
    fseek(ofile, cChunkPos, SEEK_SET);
    nFrames = nBytes * 16 / 9;
    CommChunk.numFramesH = nFrames >> 16;
    CommChunk.numFramesL = nFrames & 0xffff;
    BSWAP16(CommChunk.numChannels)
    BSWAP16(CommChunk.numFramesH)
    BSWAP16(CommChunk.numFramesL)
    BSWAP16(CommChunk.sampleSize)
    BSWAP16(CommChunk.compressionTypeH)
    BSWAP16(CommChunk.compressionTypeL)
    fwrite(&CommChunk, sizeof(CommonChunk), 1, ofile);
    fclose(ifile);
    fclose(ofile);
    return 0;
}
