#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "vadpcm.h"

void vencodeframe(FILE *ofile, s16 *inBuffer, s32 *state, s32 ***coefTable, s32 order, s32 npredictors, s32 nsam)
{
    s16 ix[16];
    s32 prediction[16];
    s32 inVector[16];
    s32 saveState[16];
    s32 optimalp;
    s32 scale;
    s32 llevel;
    s32 ulevel;
    s32 i;
    s32 j;
    s32 k;
    s32 ie[16];
    s32 nIter;
    s32 max;
    s32 cV;
    s32 maxClip;
    u8 header;
    u8 c;
    f32 e[16];
    f32 se;
    f32 min;

    // We are only given 'nsam' samples; pad with zeroes to 16.
    for (i = nsam; i < 16; i++)
    {
        inBuffer[i] = 0;
    }

    llevel = -8;
    ulevel = -llevel - 1;

    // Determine the best-fitting predictor.
    min = 1e30;
    optimalp = 0;
    for (k = 0; k < npredictors; k++)
    {
        // Copy over the last 'order' samples from the previous output.
        for (i = 0; i < order; i++)
        {
            inVector[i] = state[16 - order + i];
        }

        // For 8 samples...
        for (i = 0; i < 8; i++)
        {
            // Compute a prediction based on 'order' values from the old state,
            // plus previous errors in this chunk, as an inner product with the
            // coefficient table.
            prediction[i] = inner_product(order + i, coefTable[k][i], inVector);
            // Record the error in inVector (thus, its first 'order' samples
            // will contain actual values, the rest will be error terms), and
            // in floating point form in e (for no particularly good reason).
            inVector[i + order] = inBuffer[i] - prediction[i];
            e[i] = (f32) inVector[i + order];
        }

        // For the next 8 samples, start with 'order' values from the end of
        // the previous 8-sample chunk of inBuffer. (The code is equivalent to
        // inVector[i] = inBuffer[8 - order + i].)
        for (i = 0; i < order; i++)
        {
            inVector[i] = prediction[8 - order + i] + inVector[8 + i];
        }

        // ... and do the same thing as before to get predictions.
        for (i = 0; i < 8; i++)
        {
            prediction[8 + i] = inner_product(order + i, coefTable[k][i], inVector);
            inVector[i + order] = inBuffer[8 + i] - prediction[8 + i];
            e[8 + i] = (f32) inVector[i + order];
        }

        // Compute the L2 norm of the errors; the lowest norm decides which
        // predictor to use.
        se = 0.0f;
        for (j = 0; j < 16; j++)
        {
            se += e[j] * e[j];
        }

        if (se < min)
        {
            min = se;
            optimalp = k;
        }
    }

    // Do exactly the same thing again, for real.
    for (i = 0; i < order; i++)
    {
        inVector[i] = state[16 - order + i];
    }

    for (i = 0; i < 8; i++)
    {
        prediction[i] = inner_product(order + i, coefTable[optimalp][i], inVector);
        inVector[i + order] = inBuffer[i] - prediction[i];
        e[i] = (f32) inVector[i + order];
    }

    for (i = 0; i < order; i++)
    {
        inVector[i] = prediction[8 - order + i] + inVector[8 + i];
    }

    for (i = 0; i < 8; i++)
    {
        prediction[8 + i] = inner_product(order + i, coefTable[optimalp][i], inVector);
        inVector[i + order] = inBuffer[8 + i] - prediction[8 + i];
        e[8 + i] = (f32) inVector[i + order];
    }

    // Clamp the errors to 16-bit signed ints, and put them in ie.
    clamp(16, e, ie, 16);

    // Find a value with highest absolute value.
    // @bug If this first finds -2^n and later 2^n, it should set 'max' to the
    // latter, which needs a higher value for 'scale'.
    max = 0;
    for (i = 0; i < 16; i++)
    {
        if (fabs(ie[i]) > fabs(max))
        {
            max = ie[i];
        }
    }

    // Compute which power of two we need to scale down by in order to make
    // all values representable as 4-bit signed integers (i.e. be in [-8, 7]).
    // The worst-case 'max' is -2^15, so this will be at most 12.
    for (scale = 0; scale <= 12; scale++)
    {
        if (max <= ulevel && max >= llevel)
        {
            goto out;
        }
        max /= 2;
    }
out:;

    for (i = 0; i < 16; i++)
    {
        saveState[i] = state[i];
    }

    // Try with the computed scale, but if it turns out we don't fit in 4 bits
    // (if some |cV| >= 2), use scale + 1 instead (i.e. downscaling by another
    // factor of 2).
    scale--;
    nIter = 0;
    do
    {
        nIter++;
        maxClip = 0;
        scale++;
        if (scale > 12)
        {
            scale = 12;
        }

        // Copy over the last 'order' samples from the previous output.
        for (i = 0; i < order; i++)
        {
            inVector[i] = saveState[16 - order + i];
        }

        // For 8 samples...
        for (i = 0; i < 8; i++)
        {
            // Compute a prediction based on 'order' values from the old state,
            // plus previous *quantized* errors in this chunk (because that's
            // all the decoder will have available).
            prediction[i] = inner_product(order + i, coefTable[optimalp][i], inVector);

            // Compute the error, and divide it by 2^scale, rounding to the
            // nearest integer. This should ideally result in a 4-bit integer.
            se = (f32) inBuffer[i] - (f32) prediction[i];
            ix[i] = qsample(se, 1 << scale);

            // Clamp the error to a 4-bit signed integer, and record what delta
            // was needed for that.
            cV = (s16) clip(ix[i], llevel, ulevel) - ix[i];
            if (maxClip < abs(cV))
            {
                maxClip = abs(cV);
            }
            ix[i] += cV;

            // Record the quantized error in inVector for later predictions,
            // and the quantized (decoded) output in state (for use in the next
            // batch of 8 samples).
            inVector[i + order] = ix[i] * (1 << scale);
            state[i] = prediction[i] + inVector[i + order];
        }

        // Copy over the last 'order' decoded samples from the above chunk.
        for (i = 0; i < order; i++)
        {
            inVector[i] = state[8 - order + i];
        }

        // ... and do the same thing as before.
        for (i = 0; i < 8; i++)
        {
            prediction[8 + i] = inner_product(order + i, coefTable[optimalp][i], inVector);
            se = (f32) inBuffer[8 + i] - (f32) prediction[8 + i];
            ix[8 + i] = qsample(se, 1 << scale);
            cV = (s16) clip(ix[8 + i], llevel, ulevel) - ix[8 + i];
            if (maxClip < abs(cV))
            {
                maxClip = abs(cV);
            }
            ix[8 + i] += cV;
            inVector[i + order] = ix[8 + i] * (1 << scale);
            state[8 + i] = prediction[8 + i] + inVector[i + order];
        }
    }
    while (maxClip >= 2 && nIter < 2);

    // The scale, the predictor index, and the 16 computed outputs are now all
    // 4-bit numbers. Write them out as 1 + 8 bytes.
    header = (scale << 4) | (optimalp & 0xf);
    fwrite(&header, 1, 1, ofile);
    for (i = 0; i < 16; i += 2)
    {
        c = (ix[i] << 4) | (ix[i + 1] & 0xf);
        fwrite(&c, 1, 1, ofile);
    }
}
