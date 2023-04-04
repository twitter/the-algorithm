#ifndef _ABI_H_
#define _ABI_H_

/**************************************************************************
 *                                                                        *
 *               Copyright (C) 1994, Silicon Graphics, Inc.               *
 *                                                                        *
 *  These coded instructions, statements, and computer programs  contain  *
 *  unpublished  proprietary  information of Silicon Graphics, Inc., and  *
 *  are protected by Federal copyright law.  They  may  not be disclosed  *
 *  to  third  parties  or copied or duplicated in any form, in whole or  *
 *  in part, without the prior written consent of Silicon Graphics, Inc.  *
 *                                                                        *
 **************************************************************************/

/**************************************************************************
 *
 *  $Revision: 1.32 $
 *  $Date: 1997/02/11 08:16:37 $
 *  $Source: /exdisk2/cvs/N64OS/Master/cvsmdev2/PR/include/abi.h,v $
 *
 **************************************************************************/

/*
 * Header file for the Audio Binary Interface.
 * This is included in the Media Binary Interface file
 * mbi.h.
 *
 * This file follows the framework used for graphics.
 *
 */

/* Audio commands: */
#define A_SPNOOP                0
#define A_ADPCM                 1
#define A_CLEARBUFF             2
#define A_RESAMPLE              5
#define A_SETBUFF               8
#define A_DMEMMOVE              10
#define A_LOADADPCM             11
#define A_MIXER                 12
#define A_INTERLEAVE            13
#define A_SETLOOP               15

#ifndef VERSION_SH

#define A_ENVMIXER              3
#define A_LOADBUFF              4
#define A_RESAMPLE              5
#define A_SAVEBUFF              6
#define A_SEGMENT               7
#define A_SETVOL                9
#define A_POLEF                 14

#else

#define A_ADDMIXER              4
#define A_RESAMPLE_ZOH          6
#define A_DMEMMOVE2             16
#define A_DOWNSAMPLE_HALF       17
#define A_ENVSETUP1             18
#define A_ENVMIXER              19
#define A_LOADBUFF              20
#define A_SAVEBUFF              21
#define A_ENVSETUP2             22
#define A_S8DEC                 23
#define A_HILOGAIN              24
#define A_UNK_25                25
#define A_DUPLICATE             26
#define A_FILTER                27

#endif

#define ACMD_SIZE               32
/*
 * Audio flags
 */

#define A_INIT                  0x01
#define A_CONTINUE              0x00
#define A_LOOP                  0x02
#define A_OUT                   0x02
#define A_LEFT                  0x02
#define A_RIGHT                 0x00
#define A_VOL                   0x04
#define A_RATE                  0x00
#define A_AUX                   0x08
#define A_NOAUX                 0x00
#define A_MAIN                  0x00
#define A_MIX                   0x10

/*
 * BEGIN C-specific section: (typedef's)
 */
#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/*
 * Data Structures.
 */

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    gain:16;
    unsigned int    addr;
} Aadpcm;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    gain:16;
    unsigned int    addr;
} Apolef;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    pad1:16;
    unsigned int    addr;
} Aenvelope;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:8;
    unsigned int    dmem:16;
    unsigned int    pad2:16;
    unsigned int    count:16;
} Aclearbuff;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:8;
    unsigned int    pad2:16;
    unsigned int    inL:16;
    unsigned int    inR:16;
} Ainterleave;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:24;
    unsigned int    addr;
} Aloadbuff;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    pad1:16;
    unsigned int    addr;
} Aenvmixer;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    gain:16;
    unsigned int    dmemi:16;
    unsigned int    dmemo:16;
} Amixer;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    dmem2:16;
    unsigned int    addr;
} Apan;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    pitch:16;
    unsigned int    addr;
} Aresample;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    pad1:16;
    unsigned int    addr;
} Areverb;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:24;
    unsigned int    addr;
} Asavebuff;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:24;
    unsigned int    pad2:2;
    unsigned int    number:4;
    unsigned int    base:24;
} Asegment;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    dmemin:16;
    unsigned int    dmemout:16;
    unsigned int    count:16;
} Asetbuff;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    flags:8;
    unsigned int    vol:16;
    unsigned int    voltgt:16;
    unsigned int    volrate:16;
} Asetvol;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:8;
    unsigned int    dmemin:16;
    unsigned int    dmemout:16;
    unsigned int    count:16;
} Admemmove;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:8;
    unsigned int    count:16;
    unsigned int    addr;
} Aloadadpcm;

typedef struct {
    unsigned int    cmd:8;
    unsigned int    pad1:8;
    unsigned int    pad2:16;
    unsigned int    addr;
} Asetloop;

/*
 * Generic Acmd Packet
 */

typedef struct {
    uintptr_t w0;
    uintptr_t w1;
} Awords;

typedef union {
    Awords          words;
#if IS_BIG_ENDIAN && !IS_64_BIT
    Aadpcm          adpcm;
    Apolef          polef;
    Aclearbuff      clearbuff;
    Aenvelope       envelope;
    Ainterleave     interleave;
    Aloadbuff       loadbuff;
    Aenvmixer       envmixer;
    Aresample       resample;
    Areverb         reverb;
    Asavebuff       savebuff;
    Asegment        segment;
    Asetbuff        setbuff;
    Asetvol         setvol;
    Admemmove       dmemmove;
    Aloadadpcm      loadadpcm;
    Amixer          mixer;
    Asetloop        setloop;
#endif
    long long int   force_union_align;      /* dummy, force alignment */
} Acmd;

/*
 * ADPCM State
 */
typedef short ADPCM_STATE[16];

/*
 * Pole filter state
 */
typedef short POLEF_STATE[4];

/*
 * Resampler state
 */
typedef short RESAMPLE_STATE[16];

/*
 * Resampler constants
 */
#define UNITY_PITCH 0x8000
#define MAX_RATIO 1.99996       /* within .03 cents of +1 octave */

/*
 * Enveloper/Mixer state
 */
typedef short ENVMIX_STATE[40];

/*
 * Macros to assemble the audio command list
 */

/*
 * Info about parameters:
 *
 * A "count" in the following macros is always measured in bytes.
 *
 * All volumes/gains are in Q1.15 signed fixed point numbers:
 *  0x8000 is the minimum volume (-100%), negating the audio curve.
 *  0x0000 is silent.
 *  0x7fff is maximum volume (99.997%).
 *
 * All DRAM addresses refer to segmented addresses. A segment table shall
 * first be set up by calling aSegment for each segment. When a DRAM
 * address is later used as parameter, the 8 high bits will be an index
 * to the segment table and the lower 24 bits are added to the base address
 * stored in the segment table for this entry. The result is the physical address.
 * With the newer rsp audio code, this segment table is not used. The address is
 * used directly instead.
 *
 * Transfers to/from DRAM are executed using DMA and hence follow these restrictions:
 * All DRAM addresses should be aligned by 8 bytes, or they will be
 * rounded down to the nearest multiple of 8 bytes.
 * All DRAM lengths should be aligned by 8 bytes, or they will be
 * rounded up to the nearest multiple of 8 bytes.
 */

/*
 * Decompresses ADPCM data.
 * Possible flags: A_INIT and A_LOOP.
 *
 * First set up internal data in DMEM:
 * aLoadADPCM(cmd++, nEntries * 16, physicalAddressOfBook)
 * aSetLoop(cmd++, physicalAddressOfLoopState)    (if A_LOOP is set)
 *
 * Then before this command, call:
 * aSetBuffer(cmd++, 0, in, out, count)
 *
 * Note: count will be rounded up to the nearest multiple of 32 bytes.
 *
 * ADPCM decompression works on a block of 16 (uncompressed) samples.
 * The previous 2 samples and 9 bytes of input are decompressed to
 * 16 new samples using the code book previously loaded.
 *
 * Before the algorithm starts, the previous 16 samples are loaded according to flag:
 *   A_INIT: all zeros
 *   A_LOOP: the address set by aSetLoop
 *   no flags: the DRAM address in the s parameter
 * These 16 samples are immediately copied to the destination address.
 *
 * The result of "count" bytes will be written after these 16 initial samples.
 * The last 16 samples written to the destination will also be written to
 * the state address in DRAM.
 */
#define aADPCMdec(pkt, f, s)                                            \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_ADPCM, 24, 8) | _SHIFTL(f, 16, 8);     \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Not used in SM64.
 */
#define aPoleFilter(pkt, f, g, s)                                       \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_POLEF, 24, 8) | _SHIFTL(f, 16, 8) |   \
                        _SHIFTL(g, 0, 16));                             \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Clears DMEM data, where d is address and c is count, by writing zeros.
 *
 * Note: c is rounded up to the nearest multiple of 16 bytes.
 */
#define aClearBuffer(pkt, d, c)                                         \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_CLEARBUFF, 24, 8) | _SHIFTL(d, 0, 24); \
        _a->words.w1 = (uintptr_t)(c);                                  \
}

/*
 * Mixes an envelope with mono sound into 2 or 4 channels.
 * Possible flags: A_INIT, A_AUX (indicates that 4 channels should be used).
 *
 * Before this command, call:
 * aSetBuffer(cmd++, 0, inBuf, dryLeft, count)
 * aSetBuffer(cmd++, A_AUX, dryRight, wetLeft, wetRight)
 *
 * The first time (A_INIT is set), volume also needs to be set:
 * aSetVolume(cmd++, A_VOL | A_LEFT, initialVolumeLeft, 0, 0)
 * aSetVolume(cmd++, A_VOL | A_RIGHT, initialVolumeRight, 0, 0)
 * aSetVolume32(cmd++, A_RATE | A_LEFT, targetVolumeLeft, rampLeft)
 * aSetVolume32(cmd++, A_RATE | A_RIGHT, targetVolumeRight, rampRight)
 * aSetVolume(cmd++, A_AUX, dryVolume, 0, wetVolume)
 *
 * This command will now mix samples in inBuf into the destination buffers (dry and wet),
 * but with the volume increased (or decreased) from initial volumes to target volumes,
 * with the specified ramp rate. Once the target volume is reached, the volume stays
 * at that level. Before the samples are finally mixed (added) into the destination
 * buffers (dry and wet), the volume is changed according to dryVolume and wetVolume.
 *
 * Note: count will be rounded up to the nearest multiple of 16 bytes.
 * Note: the wet channels are used for reverb.
 *
 */
#define aEnvMixer(pkt, f, s)                                            \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_ENVMIXER, 24, 8) | _SHIFTL(f, 16, 8);  \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Interleaves two mono channels into stereo.
 *
 * First call:
 * aSetBuffer(cmd++, 0, 0, output, count)
 *
 * The count refers to the size of each input. Hence 2 * count bytes will be written out.
 * A left sample will be placed before the right sample.
 *
 * Note: count will be rounded up to the nearest multiple of 16 bytes.
 */
#define aInterleave(pkt, l, r)                                          \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_INTERLEAVE, 24, 8);                    \
        _a->words.w1 = _SHIFTL(l, 16, 16) | _SHIFTL(r, 0, 16);          \
}

/*
 * Loads a buffer from DRAM to DMEM.
 *
 * First call:
 * aSetBuffer(cmd++, 0, in, 0, count)
 *
 * The in parameter to aSetBuffer is the destination in DMEM and the
 * s parameter to this command is the source in DRAM.
 */
#define aLoadBuffer(pkt, s)                                             \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_LOADBUFF, 24, 8);                      \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Mixes audio.
 * Possible flags: no flags used, although parameter present.
 *
 * First call:
 * aSetBuffer(cmd++, 0, 0, 0, count)
 *
 * Input and output addresses are taken from the i and o parameters.
 * The volume with which the input is changed is taken from the g parameter.
 * After the volume of the input samples have been changed, the result
 * is added to the output.
 *
 * Note: count will be rounded up to the nearest multiple of 32 bytes.
 */
#define aMix(pkt, f, g, i, o)                                           \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_MIXER, 24, 8) | _SHIFTL(f, 16, 8) |   \
                        _SHIFTL(g, 0, 16));                             \
        _a->words.w1 = _SHIFTL(i,16, 16) | _SHIFTL(o, 0, 16);           \
}

// Not present in the audio microcode.
#define aPan(pkt, f, d, s)                                              \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_PAN, 24, 8) | _SHIFTL(f, 16, 8) |     \
                        _SHIFTL(d, 0, 16));                             \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Resamples audio.
 * Possible flags: A_INIT, A_OUT? (not used in SM64).
 *
 * First call:
 * aSetBuffer(cmd++, 0, in, out, count)
 *
 * This command resamples the audio using the given frequency ratio (pitch)
 * using a filter that uses a window of 4 source samples. This can be used
 * either for just resampling audio to be able to be played back at a different
 * sample rate, or to change the pitch if the result is played back at
 * the same sample rate as the input.
 *
 * The frequency ratio is given in UQ1.15 fixed point format.
 * For no change in frequency, use pitch 0x8000.
 * For 1 octave up or downsampling to (roughly) half number of samples, use pitch 0xffff.
 * For 1 octave down or upsampling to double as many samples, use pitch 0x4000.
 *
 * Note: count represents the number of output sample bytes and is rounded up to
 * the nearest multiple of 16 bytes.
 *
 * The state consists of the four following source samples when the algorithm stopped as
 * well as a fractional position, and is initialized to all zeros if A_INIT is given.
 * Otherwise it is loaded from DRAM at address s.
 *
 * The algorithm starts by writing the four source samples from the state (or zero)
 * to just before the input address given. It then creates one output sample by examining
 * the four next source samples and then moving the source position zero or more
 * samples forward. The first output sample (when A_INIT is given) is always 0.
 *
 * When "count" bytes have been written, the following four source samples
 * are written to the state in DRAM as well as a fractional position.
 */
#define aResample(pkt, f, p, s)                                         \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_RESAMPLE, 24, 8) | _SHIFTL(f, 16, 8) |\
                        _SHIFTL(p, 0, 16));                             \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Stores a buffer in DMEM to DRAM.
 *
 * First call:
 * aSetBuffer(cmd++, 0, 0, out, count)
 *
 * The out parameter to aSetBuffer is the source in DMEM and the
 * s parameter to this command is the destination in DRAM.
 */
#define aSaveBuffer(pkt, s)                                             \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_SAVEBUFF, 24, 8);                      \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Sets up an entry in the segment table.
 *
 * The s parameter is a segment index, 0 to 15.
 * The b parameter is the base offset.
 */
#define aSegment(pkt, s, b)                                             \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_SEGMENT, 24, 8);                       \
        _a->words.w1 = _SHIFTL(s, 24, 8) | _SHIFTL(b, 0, 24);           \
}

/*
 * Sets internal DMEM buffer addresses used for later commands.
 * See each command for how to use aSetBuffer.
 */
#define aSetBuffer(pkt, f, i, o, c)                                     \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_SETBUFF, 24, 8) | _SHIFTL(f, 16, 8) | \
                        _SHIFTL(i, 0, 16));                             \
        _a->words.w1 = _SHIFTL(o, 16, 16) | _SHIFTL(c, 0, 16);          \
}

/*
 * Sets internal volume parameters.
 * See aEnvMixer for more info.
 */
#define aSetVolume(pkt, f, v, t, r)                                     \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_SETVOL, 24, 8) | _SHIFTL(f, 16, 16) | \
                        _SHIFTL(v, 0, 16));                             \
        _a->words.w1 = _SHIFTL(t, 16, 16) | _SHIFTL(r, 0, 16);          \
}

/*
 * Sets the address to ADPCM loop state.
 *
 * The a parameter is a DRAM address.
 * See aADPCMdec for more info.
 */
#define aSetLoop(pkt, a)                                                \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
        _a->words.w0 = _SHIFTL(A_SETLOOP, 24, 8);                       \
        _a->words.w1 = (uintptr_t)(a);                                  \
}

/*
 * Copies memory in DMEM.
 *
 * Copies c bytes from address i to address o.
 *
 * Note: count is rounded up to the nearest multiple of 16 bytes.
 *
 * Note: This acts as memcpy where 16 bytes are moved at a time, therefore
 * if input and output overlap, output address should be less than input address.
 */
#define aDMEMMove(pkt, i, o, c)                                         \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_DMEMMOVE, 24, 8) | _SHIFTL(i, 0, 24);  \
        _a->words.w1 = _SHIFTL(o, 16, 16) | _SHIFTL(c, 0, 16);          \
}

/*
 * Loads ADPCM book from DRAM into DMEM.
 *
 * This command loads ADPCM table entries from DRAM to DMEM.
 *
 * The count parameter c should be a multiple of 16 bytes.
 * The d parameter is a DRAM address.
 */
#define aLoadADPCM(pkt, c, d)                                           \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_LOADADPCM, 24, 8) | _SHIFTL(c, 0, 24); \
        _a->words.w1 = (uintptr_t) (d);                                 \
}

// This is a version of aSetVolume which takes a single 32-bit parameter
// instead of two 16-bit ones. According to AziAudio, it is used to set
// ramping values when neither bit 4 nor bit 8 is set in the flags parameter.
// It does not appear in the official abi.h header.
/*
 * Sets internal volume parameters.
 * See aEnvMixer for more info.
 */
#define aSetVolume32(pkt, f, v, tr)                                     \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_SETVOL, 24, 8) | _SHIFTL(f, 16, 16) | \
                    _SHIFTL(v, 0, 16));                                 \
        _a->words.w1 = (uintptr_t)(tr);                                 \
}

#ifdef VERSION_SH
#undef aLoadBuffer
#undef aSaveBuffer
#undef aMix
#undef aEnvMixer
#undef aInterleave

// New or modified operations in the new audio microcode below

/**
 * Decompresses S8 data.
 * Possible flags: A_INIT and A_LOOP.
 *
 * First set up internal data in DMEM:
 * aSetLoop(cmd++, physicalAddressOfLoopState)    (if A_LOOP is set)
 *
 * Then before this command, call:
 * aSetBuffer(cmd++, 0, in, out, count)
 *
 * Note: count will be rounded up to the nearest multiple of 32 bytes.
 *
 * S8 decompression works by expanding s8 bytes into s16 numbers,
 * by performing a left shift of 8 steps.
 *
 * Before the algorithm starts, the previous 16 samples are loaded according to flag:
 *   A_INIT: all zeros
 *   A_LOOP: the address set by aSetLoop
 *   no flags: the DRAM address in the s parameter
 * These 16 samples are immediately copied to the destination address.
 *
 * The result of "count" bytes will be written after these 16 initial samples.
 * The last 16 samples written to the destination will also be written to
 * the state address in DRAM.
 */
#define aS8Dec(pkt, f, s)                                               \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_S8DEC, 24, 8) | _SHIFTL(f, 16, 8);     \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Mix two tracks by simple clamped addition.
 *
 * s: DMEM source track 1
 * d: DMEM source track 2 and destination
 * c: number of bytes to write
 *
 * Note: count is first rounded down to the nearest multiple of 16 bytes
 * and then rounded up to the nearest multiple of 64 bytes.
 */
#define aAddMixer(pkt, s, d, c)                                         \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_ADDMIXER, 24, 8) |                    \
                 _SHIFTL((c) >> 4, 16, 8) | _SHIFTL(0x7fff, 0, 16));    \
        _a->words.w1 = (_SHIFTL(s, 16, 16) | _SHIFTL(d, 0, 16));        \
}

/*
 * Loads a buffer from DRAM to DMEM.
 *
 * s: DRAM source
 * d: DMEM destination
 * c: number of bytes to copy (rounded down to 16 byte alignment)
 */
#define aLoadBuffer(pkt, s, d, c)                                       \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_LOADBUFF, 24, 8) |                     \
                    _SHIFTL((c) >> 4, 16, 8) | _SHIFTL(d, 0, 16);       \
        _a->words.w1 = (uintptr_t)(s);                                  \
}

/*
 * Stores a buffer from DMEM to DRAM.
 *
 * s: DMEM source
 * d: DRAM destination
 * c: number of bytes to copy (rounded down to 16 byte alignment)
 */
#define aSaveBuffer(pkt, s, d, c)                                       \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_SAVEBUFF, 24, 8) |                     \
                    _SHIFTL((c) >> 4, 16, 8) | _SHIFTL(s, 0, 16);       \
        _a->words.w1 = (uintptr_t)(d);                                  \
}

/*
 * Duplicates 128 bytes of data a number of times.
 *
 * 128 bytes are read from source DMEM address s.
 * Then c identical copies of these bytes are written to DMEM address d.
 */
#define aDuplicate(pkt, s, d, c)                                        \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_DUPLICATE, 24, 8) |                   \
                    _SHIFTL(c, 16, 8) | _SHIFTL(s, 0, 16));             \
        _a->words.w1 = (_SHIFTL(d, 16, 16) | _SHIFTL(0x80, 0, 16));     \
}

/*
 * Copies memory in DMEM, second version.
 *
 * Copies t * c bytes from address i to address o.
 *
 * Note: count is first rounded up to the nearest multiple of 32 bytes,
 * before the multiplication by t.
 *
 * Note: This acts as memcpy where 32 bytes are moved at a time, therefore
 * if input and output overlap, output address should be less than input address.
 *
 * Not used in SM64.
 */
#define aDMEMMove2(pkt, t, i, o, c)                                     \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_DMEMMOVE2, 24, 8) |                    \
                    _SHIFTL(t, 16, 8) | _SHIFTL(i, 0, 16);              \
        _a->words.w1 = _SHIFTL(o, 16, 16) | _SHIFTL(c, 0, 16);          \
}

/*
 * Fast resample.
 *
 * Before this command, call:
 * aSetBuffer(cmd++, 0, in, out, count)
 *
 * This works like the other resample command but just takes the "nearest" sample,
 * instead of a function of the four nearest samples.
 *
 * Initially the current position is calculated as (in << 16) + startFract.
 * For every sample to create, the value is simply taken from the sample
 * at address ((position >> 17) << 1). Then the current position is incremented
 * by (pitch << 2).
 *
 * Note: count represents the number of output bytes to create, and is
 * rounded up to the nearest multiple of 8 bytes.
 */
#define aResampleZoh(pkt, pitch, startFract)                            \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_RESAMPLE_ZOH, 24, 8) |                \
                    _SHIFTL(pitch, 0, 16));                             \
        _a->words.w1 = _SHIFTL(startFract, 0, 16);                      \
}

/*
 * Fast downsampling by taking every other sample, discarding others.
 *
 * Note: nSamples refers to the number of output samples to create, and
 * is first rounded up to the nearest multiple of 8.
 */
#define aDownsampleHalf(pkt, nSamples, i, o)                            \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_DOWNSAMPLE_HALF, 24, 8) |             \
                        _SHIFTL(nSamples, 0, 16));                      \
        _a->words.w1 = _SHIFTL(i, 16, 16) | _SHIFTL(o, 0, 16);          \
}

/*
 * Mixes audio.
 *
 * Input and output addresses are taken from the i and o parameters.
 * The volume with which the input is changed is taken from the g parameter.
 * After the volume of the input samples have been changed, the result
 * is added to the output.
 *
 * Note: count is first rounded down to the nearest multiple of 16 bytes
 * and then rounded up to the nearest multiple of 32 bytes.
 */
#define aMix(pkt, g, i, o, c)                                           \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_MIXER, 24, 8) |                       \
                    _SHIFTL((c) >> 4, 16, 8) | _SHIFTL(g, 0, 16));      \
        _a->words.w1 = _SHIFTL(i, 16, 16) | _SHIFTL(o, 0, 16);          \
}

/*
 * See aEnvMixer for more info.
 */
#define aEnvSetup1(pkt, initialVolReverb, rampReverb, rampLeft, rampRight) \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_ENVSETUP1, 24, 8) |                   \
                    _SHIFTL(initialVolReverb, 16, 8) |                  \
                    _SHIFTL(rampReverb, 0, 16));                        \
        _a->words.w1 = _SHIFTL(rampLeft, 16, 16) |                      \
                    _SHIFTL(rampRight, 0, 16);                          \
}

/*
 * See aEnvMixer for more info.
 */
#define aEnvSetup2(pkt, initialVolLeft, initialVolRight)                \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_ENVSETUP2, 24, 8);                     \
        _a->words.w1 = _SHIFTL(initialVolLeft, 16, 16) |                \
                    _SHIFTL(initialVolRight, 0, 16);                    \
}

/*
 * Mixes an envelope with mono sound into 4 channels.
 *
 * To allow for many parameters, a sequence of aEnvSetup1, aEnvSetup2,
 * aEnvMixer shall always be called.
 *
 * The function works in blocks of 8 samples.
 * However, nSamples is rounded up to the nearest multiple of 16 samples.
 *
 * For each sample in a block:
 * 1. sampleLeft = in * volLeft * (negLeft ? -1 : 1)
 * 2. sampleRight = in * volRight * (negRight ? -1 : 1)
 * 3. dryLeft += sampleLeft
 * 4. dryRight += sampleRight
 * 5. if swapReverb: swap sampleLeft and sampleRight
 * 6. wetLeft += sampleLeft * volReverb
 * 7. wetRight += sampleRight * volReverb
 *
 * After each block, all vol variables are added by their corresponding
 * ramp value.
 *
 * Each volume variable is treated as a UQ0.16 number. Make sure
 * the ramp additions don't overflow, or wrapping will occur.
 * The initialVolReverb parameter is only 8 bits, but will be left
 * shifted 8 bits by the rsp.
 */
#define aEnvMixer(pkt, inBuf, nSamples, swapReverb, negLeft, negRight,  \
                  dryLeft, dryRight, wetLeft, wetRight)                 \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_ENVMIXER, 24, 8) |                    \
                    _SHIFTL((inBuf) >> 4, 16, 8) |                      \
                    _SHIFTL(nSamples, 8, 8)) |                          \
                    _SHIFTL(swapReverb, 2, 1) | _SHIFTL(negLeft, 1, 1) |\
                    _SHIFTL(negRight, 0, 1);                            \
        _a->words.w1 = _SHIFTL((dryLeft) >> 4, 24, 8) |                 \
                    _SHIFTL((dryRight) >> 4, 16, 8) |                   \
                    _SHIFTL((wetLeft) >> 4, 8, 8) |                     \
                    _SHIFTL((wetRight) >> 4, 0, 8);                     \
}

/*
 * Interleaves two mono channels into stereo.
 *
 * The count refers to the size of each input. Hence 2 * count bytes
 * will be written out.
 *
 * A left sample will be placed before the right sample.
 * All addresses (output, left, right) are DMEM addresses.
 *
 * Note: count will be rounded up to the nearest multiple of 8 bytes.
 * The previous version of this function rounded up to the nearest
 * multiple of 16 bytes.
 */
#define aInterleave(pkt, o, l, r, c)                                    \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_INTERLEAVE, 24, 8) |                   \
                    _SHIFTL((c) >> 4, 16, 8) | _SHIFTL(o, 0, 16);       \
        _a->words.w1 = _SHIFTL(l, 16, 16) | _SHIFTL(r, 0, 16);          \
}

/*
 * Linear filter function.
 *
 * Calculates out[i] = sum all elements in the vector in[i..i-7] * filter[0..7],
 * where "*" represents dot multiplication. The input/output contains s16
 * samples and filter contains Q1.15 signed fixed point numbers.
 * Every result sample is rounded and clamped.
 *
 * First initiate by calling with the flag f set to 2, countOrBuf contains
 * the length in bytes that shall be processed in the next call. The addr
 * parameter shall contain the DRAM address to the filter table (16 bytes).
 * The count will be rounded up to the nearest multiple of 16 bytes.
 *
 * The aFilter function shall then be called in direct succession, with flag
 * set to either 0 or 1. The countOrBuf parameter shall contain the DMEM
 * address for the input/output. The addr parameter shall contain the DRAM
 * address for the state, containing the last previous 8 input samples.
 * The state is always written to upon exit, but is only read at entry if
 * the flag is 0 (otherwise all-zero samples are used instead).
 */
#define aFilter(pkt, f, countOrBuf, addr)                               \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_FILTER, 24, 8) | _SHIFTL((f), 16, 8) | \
                    _SHIFTL((countOrBuf), 0, 16);                       \
        _a->words.w1 = (uintptr_t)(addr);                               \
}

/*
 * Modifies the volume of samples using a simple UQ4.4 gain multiplier.
 *
 * Performs the following:
 *
 * 1. Count c is rounded up to 32 byte alignment
 * 2. g is a u8 that contains a UQ4.4 number
 * 3. Modify each sample s, so that s = clamp_s16(s * g >> 4)
 */
#define aHiLoGain(pkt, g, buflen, i)                                    \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = _SHIFTL(A_HILOGAIN, 24, 8) |                     \
                _SHIFTL((g), 16, 8) | _SHIFTL((buflen), 0, 16);         \
        _a->words.w1 = _SHIFTL((i), 16, 16);                            \
}

/*
 * Performs the following:
 *
 * 1. Count c is rounded up to 64 byte alignment
 * 2. f is added to i
 * 3. i and o are from now treated as s16 pointers
 * 4. 32 s16 samples are loaded from i to tbl
 * 5. for (u32 idx = 0; idx * sizeof(s16) < c; idx++)
 *        o[idx] = clamp_s16((s32)o[idx] * (s32)tbl[idx % 32]);
 */
#define aUnknown25(pkt, f, c, o, i)                                     \
{                                                                       \
        Acmd *_a = (Acmd *)pkt;                                         \
                                                                        \
        _a->words.w0 = (_SHIFTL(A_UNK_25, 24, 8) |                      \
                _SHIFTL((f), 16, 8) |  _SHIFTL((c), 0, 16));            \
        _a->words.w1 = _SHIFTL((o), 16, 16) | _SHIFTL((i), 0, 16);      \
}

#endif

#endif /* _LANGUAGE_C */

#endif /* !_ABI_H_ */
