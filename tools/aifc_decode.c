/**
 * Bruteforcing decoder for converting ADPCM-encoded AIFC into AIFF, in a way
 * that roundtrips with vadpcm_enc.
 */
#include <unistd.h>
#include <assert.h>
#include <math.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>

typedef signed char s8;
typedef short s16;
typedef int s32;
typedef unsigned char u8;
typedef unsigned short u16;
typedef unsigned int u32;
typedef unsigned long long u64;
typedef float f32;

#define bswap16(x) __builtin_bswap16(x)
#define bswap32(x) __builtin_bswap32(x)
#define BSWAP16(x) x = __builtin_bswap16(x)
#define BSWAP32(x) x = __builtin_bswap32(x)
#define BSWAP16_MANY(x, n) for (s32 _i = 0; _i < n; _i++) BSWAP16((x)[_i])

#define NORETURN __attribute__((noreturn))
#define UNUSED __attribute__((unused))

typedef struct {
    u32 ckID;
    u32 ckSize;
} ChunkHeader;

typedef struct {
    u32 ckID;
    u32 ckSize;
    u32 formType;
} Chunk;

typedef struct {
    s16 numChannels;
    u16 numFramesH;
    u16 numFramesL;
    s16 sampleSize;
    s16 sampleRate[5]; // 80-bit float
    u16 compressionTypeH;
    u16 compressionTypeL;
} CommonChunk;

typedef struct {
    s16 MarkerID;
    u16 positionH;
    u16 positionL;
} Marker;

typedef struct {
    s16 playMode;
    s16 beginLoop;
    s16 endLoop;
} Loop;

typedef struct {
    s8 baseNote;
    s8 detune;
    s8 lowNote;
    s8 highNote;
    s8 lowVelocity;
    s8 highVelocity;
    s16 gain;
    Loop sustainLoop;
    Loop releaseLoop;
} InstrumentChunk;

typedef struct {
    s32 offset;
    s32 blockSize;
} SoundDataChunk;

typedef struct {
    s16 version;
    s16 order;
    s16 nEntries;
} CodeChunk;

typedef struct
{
    u32 start;
    u32 end;
    u32 count;
    s16 state[16];
} ALADPCMloop;


static char usage[] = "input.aifc output.aiff";
static const char *progname, *infilename;

#define checked_fread(a, b, c, d) if (fread(a, b, c, d) != c) fail_parse("error parsing file")

NORETURN
void fail_parse(const char *fmt, ...)
{
    char *formatted = NULL;
    va_list ap;
    va_start(ap, fmt);
    int size = vsnprintf(NULL, 0, fmt, ap);
    va_end(ap);
    if (size >= 0) {
        size++;
        formatted = malloc(size);
        if (formatted != NULL) {
            va_start(ap, fmt);
            size = vsnprintf(formatted, size, fmt, ap);
            va_end(ap);
            if (size < 0) {
                free(formatted);
                formatted = NULL;
            }
        }
    }

    if (formatted != NULL) {
        fprintf(stderr, "%s: %s [%s]\n", progname, formatted, infilename);
        free(formatted);
    }
    exit(1);
}

s32 myrand()
{
    static u64 state = 1619236481962341ULL;
    state *= 3123692312231ULL;
    state++;
    return state >> 33;
}

s16 qsample(s32 x, s32 scale)
{
    // Compute x / 2^scale rounded to the nearest integer, breaking ties towards zero.
    if (scale == 0) return x;
    return (x + (1 << (scale - 1)) - (x > 0)) >> scale;
}

s16 clamp_to_s16(s32 x)
{
    if (x < -0x8000) return -0x8000;
    if (x > 0x7fff) return 0x7fff;
    return (s16) x;
}

s32 toi4(s32 x)
{
    if (x >= 8) return x - 16;
    return x;
}

s32 readaifccodebook(FILE *fhandle, s32 ****table, s16 *order, s16 *npredictors)
{
    checked_fread(order, sizeof(s16), 1, fhandle);
    BSWAP16(*order);
    checked_fread(npredictors, sizeof(s16), 1, fhandle);
    BSWAP16(*npredictors);
    *table = malloc(*npredictors * sizeof(s32 **));
    for (s32 i = 0; i < *npredictors; i++) {
        (*table)[i] = malloc(8 * sizeof(s32 *));
        for (s32 j = 0; j < 8; j++) {
            (*table)[i][j] = malloc((*order + 8) * sizeof(s32));
        }
    }

    for (s32 i = 0; i < *npredictors; i++) {
        s32 **table_entry = (*table)[i];
        for (s32 j = 0; j < *order; j++) {
            for (s32 k = 0; k < 8; k++) {
                s16 ts;
                checked_fread(&ts, sizeof(s16), 1, fhandle);
                BSWAP16(ts);
                table_entry[k][j] = ts;
            }
        }

        for (s32 k = 1; k < 8; k++) {
            table_entry[k][*order] = table_entry[k - 1][*order - 1];
        }

        table_entry[0][*order] = 1 << 11;

        for (s32 k = 1; k < 8; k++) {
            s32 j = 0;
            for (; j < k; j++) {
                table_entry[j][k + *order] = 0;
            }

            for (; j < 8; j++) {
                table_entry[j][k + *order] = table_entry[j - k][*order];
            }
        }
    }
    return 0;
}

ALADPCMloop *readlooppoints(FILE *ifile, s16 *nloops)
{
    checked_fread(nloops, sizeof(s16), 1, ifile);
    BSWAP16(*nloops);
    ALADPCMloop *al = malloc(*nloops * sizeof(ALADPCMloop));
    for (s32 i = 0; i < *nloops; i++) {
        checked_fread(&al[i], sizeof(ALADPCMloop), 1, ifile);
        BSWAP32(al[i].start);
        BSWAP32(al[i].end);
        BSWAP32(al[i].count);
        BSWAP16_MANY(al[i].state, 16);
    }
    return al;
}

s32 inner_product(s32 length, s32 *v1, s32 *v2)
{
    s32 out = 0;
    for (s32 i = 0; i < length; i++) {
        out += v1[i] * v2[i];
    }

    // Compute "out / 2^11", rounded down.
    s32 dout = out / (1 << 11);
    s32 fiout = dout * (1 << 11);
    return dout - (out - fiout < 0);
}

void my_decodeframe(u8 *frame, s32 *state, s32 order, s32 ***coefTable)
{
    s32 ix[16];

    u8 header = frame[0];
    s32 scale = 1 << (header >> 4);
    s32 optimalp = header & 0xf;

    for (s32 i = 0; i < 16; i += 2) {
        u8 c = frame[1 + i/2];
        ix[i] = c >> 4;
        ix[i + 1] = c & 0xf;
    }

    for (s32 i = 0; i < 16; i++) {
        if (ix[i] >= 8) ix[i] -= 16;
        ix[i] *= scale;
    }

    for (s32 j = 0; j < 2; j++) {
        s32 in_vec[16];
        if (j == 0) {
            for (s32 i = 0; i < order; i++) {
                in_vec[i] = state[16 - order + i];
            }
        } else {
            for (s32 i = 0; i < order; i++) {
                in_vec[i] = state[8 - order + i];
            }
        }

        for (s32 i = 0; i < 8; i++) {
            s32 ind = j * 8 + i;
            in_vec[order + i] = ix[ind];
            state[ind] = inner_product(order + i, coefTable[optimalp][i], in_vec) + ix[ind];
        }
    }
}

void my_encodeframe(u8 *out, s16 *inBuffer, s32 *state, s32 ***coefTable, s32 order, s32 npredictors)
{
    s16 ix[16];
    s32 prediction[16];
    s32 inVector[16];
    s32 saveState[16];
    s32 optimalp = 0;
    s32 scale;
    s32 ie[16];
    s32 e[16];
    f32 min = 1e30;

    for (s32 k = 0; k < npredictors; k++) {
        for (s32 j = 0; j < 2; j++) {
            for (s32 i = 0; i < order; i++) {
                inVector[i] = (j == 0 ? state[16 - order + i] : inBuffer[8 - order + i]);
            }

            for (s32 i = 0; i < 8; i++) {
                prediction[j * 8 + i] = inner_product(order + i, coefTable[k][i], inVector);
                e[j * 8 + i] = inVector[i + order] = inBuffer[j * 8 + i] - prediction[j * 8 + i];
            }
        }

        f32 se = 0.0f;
        for (s32 j = 0; j < 16; j++) {
            se += (f32) e[j] * (f32) e[j];
        }

        if (se < min) {
            min = se;
            optimalp = k;
        }
    }

    for (s32 j = 0; j < 2; j++) {
        for (s32 i = 0; i < order; i++) {
            inVector[i] = (j == 0 ? state[16 - order + i] : inBuffer[8 - order + i]);
        }

        for (s32 i = 0; i < 8; i++) {
            prediction[j * 8 + i] = inner_product(order + i, coefTable[optimalp][i], inVector);
            e[j * 8 + i] = inVector[i + order] = inBuffer[j * 8 + i] - prediction[j * 8 + i];
        }
    }

    for (s32 i = 0; i < 16; i++) {
        ie[i] = clamp_to_s16(e[i]);
    }

    s32 max = 0;
    for (s32 i = 0; i < 16; i++) {
        if (abs(ie[i]) > abs(max)) {
            max = ie[i];
        }
    }

    for (scale = 0; scale <= 12; scale++) {
        if (max <= 7 && max >= -8) break;
        max /= 2;
    }

    for (s32 i = 0; i < 16; i++) {
        saveState[i] = state[i];
    }

    for (s32 nIter = 0, again = 1; nIter < 2 && again; nIter++) {
        again = 0;
        if (nIter == 1) scale++;
        if (scale > 12) {
            scale = 12;
        }

        for (s32 j = 0; j < 2; j++) {
            s32 base = j * 8;
            for (s32 i = 0; i < order; i++) {
                inVector[i] = (j == 0 ?
                        saveState[16 - order + i] : state[8 - order + i]);
            }

            for (s32 i = 0; i < 8; i++) {
                prediction[base + i] = inner_product(order + i, coefTable[optimalp][i], inVector);
                s32 se = inBuffer[base + i] - prediction[base + i];
                ix[base + i] = qsample(se, scale);
                s32 cV = clamp_to_s16(ix[base + i]) - ix[base + i];
                if (cV > 1 || cV < -1) again = 1;
                ix[base + i] += cV;
                inVector[i + order] = ix[base + i] * (1 << scale);
                state[base + i] = prediction[base + i] + inVector[i + order];
            }
        }
    }

    u8 header = (scale << 4) | (optimalp & 0xf);
    out[0] = header;
    for (s32 i = 0; i < 16; i += 2) {
        u8 c = ((ix[i] & 0xf) << 4) | (ix[i + 1] & 0xf);
        out[1 + i/2] = c;
    }
}

void permute(s16 *out, s32 *in, s32 scale)
{
    for (s32 i = 0; i < 16; i++) {
        out[i] = clamp_to_s16(in[i] - scale / 2 + myrand() % (scale + 1));
    }
}

void write_header(FILE *ofile, const char *id, s32 size)
{
    fwrite(id, 4, 1, ofile);
    BSWAP32(size);
    fwrite(&size, sizeof(s32), 1, ofile);
}

int main(int argc, char **argv)
{
    s16 order = -1;
    s16 nloops = 0;
    ALADPCMloop *aloops = NULL;
    s16 npredictors = -1;
    s32 ***coefTable = NULL;
    s32 state[16];
    s32 soundPointer = -1;
    s32 currPos = 0;
    s32 nSamples = 0;
    Chunk FormChunk;
    ChunkHeader Header;
    CommonChunk CommChunk;
    InstrumentChunk InstChunk;
    SoundDataChunk SndDChunk;
    FILE *ifile;
    FILE *ofile;
    progname = argv[0];

    if (argc < 3) {
        fprintf(stderr, "%s %s\n", progname, usage);
        exit(1);
    }

    infilename = argv[1];

    if ((ifile = fopen(infilename, "rb")) == NULL) {
        fail_parse("AIFF-C file could not be opened");
        exit(1);
    }

    if ((ofile = fopen(argv[2], "wb")) == NULL) {
        fprintf(stderr, "%s: output file could not be opened [%s]\n", progname, argv[2]);
        exit(1);
    }

    memset(&InstChunk, 0, sizeof(InstChunk));

    checked_fread(&FormChunk, sizeof(FormChunk), 1, ifile);
    BSWAP32(FormChunk.ckID);
    BSWAP32(FormChunk.formType);
    if ((FormChunk.ckID != 0x464f524d) || (FormChunk.formType != 0x41494643)) { // FORM, AIFC
        fail_parse("not an AIFF-C file");
    }

    for (;;) {
        s32 num = fread(&Header, sizeof(Header), 1, ifile);
        u32 ts;
        if (num <= 0) break;
        BSWAP32(Header.ckID);
        BSWAP32(Header.ckSize);

        Header.ckSize++;
        Header.ckSize &= ~1;
        s32 offset = ftell(ifile);

        switch (Header.ckID) {
        case 0x434f4d4d: // COMM
            checked_fread(&CommChunk, sizeof(CommChunk), 1, ifile);
            BSWAP16(CommChunk.numChannels);
            BSWAP16(CommChunk.numFramesH);
            BSWAP16(CommChunk.numFramesL);
            BSWAP16(CommChunk.sampleSize);
            BSWAP16(CommChunk.compressionTypeH);
            BSWAP16(CommChunk.compressionTypeL);
            s32 cType = (CommChunk.compressionTypeH << 16) + CommChunk.compressionTypeL;
            if (cType != 0x56415043) { // VAPC
                fail_parse("file is of the wrong compression type");
            }
            if (CommChunk.numChannels != 1) {
                fail_parse("file contains %d channels, only 1 channel supported", CommChunk.numChannels);
            }
            if (CommChunk.sampleSize != 16) {
                fail_parse("file contains %d bit samples, only 16 bit samples supported", CommChunk.sampleSize);
            }

            nSamples = (CommChunk.numFramesH << 16) + CommChunk.numFramesL;

            // Allow broken input lengths
            if (nSamples % 16) {
                nSamples--;
            }

            if (nSamples % 16 != 0) {
                fail_parse("number of chunks must be a multiple of 16, found %d", nSamples);
            }
            break;

        case 0x53534e44: // SSND
            checked_fread(&SndDChunk, sizeof(SndDChunk), 1, ifile);
            BSWAP32(SndDChunk.offset);
            BSWAP32(SndDChunk.blockSize);
            assert(SndDChunk.offset == 0);
            assert(SndDChunk.blockSize == 0);
            soundPointer = ftell(ifile);
            break;

        case 0x4150504c: // APPL
            checked_fread(&ts, sizeof(u32), 1, ifile);
            BSWAP32(ts);
            if (ts == 0x73746f63) { // stoc
                u8 len;
                checked_fread(&len, 1, 1, ifile);
                if (len == 11) {
                    char ChunkName[12];
                    s16 version;
                    checked_fread(ChunkName, 11, 1, ifile);
                    ChunkName[11] = '\0';
                    if (strcmp("VADPCMCODES", ChunkName) == 0) {
                        checked_fread(&version, sizeof(s16), 1, ifile);
                        BSWAP16(version);
                        if (version != 1) {
                            fail_parse("Unknown codebook chunk version");
                        }
                        readaifccodebook(ifile, &coefTable, &order, &npredictors);
                    }
                    else if (strcmp("VADPCMLOOPS", ChunkName) == 0) {
                        checked_fread(&version, sizeof(s16), 1, ifile);
                        BSWAP16(version);
                        if (version != 1) {
                            fail_parse("Unknown loop chunk version");
                        }
                        aloops = readlooppoints(ifile, &nloops);
                        if (nloops != 1) {
                            fail_parse("Only a single loop supported");
                        }
                    }
                }
            }
            break;
        }

        fseek(ifile, offset + Header.ckSize, SEEK_SET);
    }

    if (coefTable == NULL) {
        fail_parse("Codebook missing from bitstream");
    }

    for (s32 i = 0; i < order; i++) {
        state[15 - i] = 0;
    }

    u32 outputBytes = nSamples * sizeof(s16);
    u8 *outputBuf = malloc(outputBytes);

    fseek(ifile, soundPointer, SEEK_SET);
    while (currPos < nSamples) {
        u8 input[9];
        u8 encoded[9];
        s32 lastState[16];
        s32 decoded[16];
        s16 guess[16];
        s16 origGuess[16];

        memcpy(lastState, state, sizeof(lastState));
        checked_fread(input, 9, 1, ifile);

        // Decode for real
        my_decodeframe(input, state, order, coefTable);
        memcpy(decoded, state, sizeof(lastState));

        // Create a guess from that, by clamping to 16 bits
        for (s32 i = 0; i < 16; i++) {
            origGuess[i] = clamp_to_s16(state[i]);
        }

        // Encode the guess
        memcpy(state, lastState, sizeof(lastState));
        memcpy(guess, origGuess, sizeof(guess));
        my_encodeframe(encoded, guess, state, coefTable, order, npredictors);

        // If it doesn't match, randomly round numbers until it does.
        if (memcmp(input, encoded, 9) != 0) {
            s32 scale = 1 << (input[0] >> 4);
            do {
                permute(guess, decoded, scale);
                memcpy(state, lastState, sizeof(lastState));
                my_encodeframe(encoded, guess, state, coefTable, order, npredictors);
            } while (memcmp(input, encoded, 9) != 0);

            // Bring the matching closer to the original decode (not strictly
            // necessary, but it will move us closer to the target on average).
            for (s32 failures = 0; failures < 50; failures++) {
                s32 ind = myrand() % 16;
                s32 old = guess[ind];
                if (old == origGuess[ind]) continue;
                guess[ind] = origGuess[ind];
                if (myrand() % 2) guess[ind] += (old - origGuess[ind]) / 2;
                memcpy(state, lastState, sizeof(lastState));
                my_encodeframe(encoded, guess, state, coefTable, order, npredictors);
                if (memcmp(input, encoded, 9) == 0) {
                    failures = -1;
                }
                else {
                    guess[ind] = old;
                }
            }
        }

        memcpy(state, decoded, sizeof(lastState));
        BSWAP16_MANY(guess, 16);
        memcpy(outputBuf + currPos * 2, guess, sizeof(guess));
        currPos += 16;
    }

    // Write an incomplete file header. We'll fill in the size later.
    fwrite("FORM\0\0\0\0AIFF", 12, 1, ofile);

    // Subtract 4 from the COMM size to skip the compression field.
    write_header(ofile, "COMM", sizeof(CommonChunk) - 4);
    CommChunk.numFramesH = nSamples >> 16;
    CommChunk.numFramesL = nSamples & 0xffff;
    BSWAP16(CommChunk.numChannels);
    BSWAP16(CommChunk.numFramesH);
    BSWAP16(CommChunk.numFramesL);
    BSWAP16(CommChunk.sampleSize);
    fwrite(&CommChunk, sizeof(CommonChunk) - 4, 1, ofile);

    if (nloops > 0) {
        s32 startPos = aloops[0].start, endPos = aloops[0].end;
        const char *markerNames[2] = {"start", "end"};
        Marker markers[2] = {
            {1, startPos >> 16, startPos & 0xffff},
            {2, endPos >> 16, endPos & 0xffff}
        };
        write_header(ofile, "MARK", 2 + 2 * sizeof(Marker) + 1 + 5 + 1 + 3);
        s16 numMarkers = bswap16(2);
        fwrite(&numMarkers, sizeof(s16), 1, ofile);
        for (s32 i = 0; i < 2; i++) {
            u8 len = (u8) strlen(markerNames[i]);
            BSWAP16(markers[i].MarkerID);
            BSWAP16(markers[i].positionH);
            BSWAP16(markers[i].positionL);
            fwrite(&markers[i], sizeof(Marker), 1, ofile);
            fwrite(&len, 1, 1, ofile);
            fwrite(markerNames[i], len, 1, ofile);
        }

        write_header(ofile, "INST", sizeof(InstrumentChunk));
        InstChunk.sustainLoop.playMode = bswap16(1);
        InstChunk.sustainLoop.beginLoop = bswap16(1);
        InstChunk.sustainLoop.endLoop = bswap16(2);
        InstChunk.releaseLoop.playMode = 0;
        InstChunk.releaseLoop.beginLoop = 0;
        InstChunk.releaseLoop.endLoop = 0;
        fwrite(&InstChunk, sizeof(InstrumentChunk), 1, ofile);
    }

    // Save the coefficient table for use when encoding. Ideally this wouldn't
    // be needed and "tabledesign -s 1" would generate the right table, but in
    // practice it's difficult to adjust samples to make that happen.
    write_header(ofile, "APPL", 4 + 12 + sizeof(CodeChunk) + npredictors * order * 8 * 2);
    fwrite("stoc", 4, 1, ofile);
    CodeChunk cChunk;
    cChunk.version = bswap16(1);
    cChunk.order = bswap16(order);
    cChunk.nEntries = bswap16(npredictors);
    fwrite("\x0bVADPCMCODES", 12, 1, ofile);
    fwrite(&cChunk, sizeof(CodeChunk), 1, ofile);
    for (s32 i = 0; i < npredictors; i++) {
        for (s32 j = 0; j < order; j++) {
            for (s32 k = 0; k < 8; k++) {
                s16 ts = bswap16(coefTable[i][k][j]);
                fwrite(&ts, sizeof(s16), 1, ofile);
            }
        }
    }

    write_header(ofile, "SSND", outputBytes + 8);
    SndDChunk.offset = 0;
    SndDChunk.blockSize = 0;
    fwrite(&SndDChunk, sizeof(SoundDataChunk), 1, ofile);
    fwrite(outputBuf, outputBytes, 1, ofile);

    // Fix the size in the header
    s32 fileSize = bswap32(ftell(ofile) - 8);
    fseek(ofile, 4, SEEK_SET);
    fwrite(&fileSize, 4, 1, ofile);

    fclose(ifile);
    fclose(ofile);
    return 0;
}
