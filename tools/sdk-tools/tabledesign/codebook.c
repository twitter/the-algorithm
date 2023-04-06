#include <stdlib.h>
#include "tabledesign.h"

void split(double **table, double *delta, int order, int npredictors, double scale)
{
    int i, j;

    for (i = 0; i < npredictors; i++)
    {
        for (j = 0; j <= order; j++)
        {
            table[i + npredictors][j] = table[i][j] + delta[j] * scale;
        }
    }
}

void refine(double **table, int order, int npredictors, double **data, int dataSize, int refineIters, UNUSED double unused)
{
    int iter; // spD8
    double **rsums;
    int *counts; // spD0
    double *temp_s7;
    double dist;
    double dummy; // spC0
    double bestValue;
    int bestIndex;
    int i, j;

    rsums = malloc(npredictors * sizeof(double*));
    for (i = 0; i < npredictors; i++)
    {
        rsums[i] = malloc((order + 1) * sizeof(double));
    }

    counts = malloc(npredictors * sizeof(int));
    temp_s7 = malloc((order + 1) * sizeof(double));

    for (iter = 0; iter < refineIters; iter++)
    {
        for (i = 0; i < npredictors; i++)
        {
            counts[i] = 0;
            for (j = 0; j <= order; j++)
            {
                rsums[i][j] = 0.0;
            }
        }

        for (i = 0; i < dataSize; i++)
        {
            bestValue = 1e30;
            bestIndex = 0;

            for (j = 0; j < npredictors; j++)
            {
                dist = model_dist(table[j], data[i], order);
                if (dist < bestValue)
                {
                    bestValue = dist;
                    bestIndex = j;
                }
            }

            counts[bestIndex]++;
            rfroma(data[i], order, temp_s7);
            for (j = 0; j <= order; j++)
            {
                rsums[bestIndex][j] += temp_s7[j];
            }
        }

        for (i = 0; i < npredictors; i++)
        {
            if (counts[i] > 0)
            {
                for (j = 0; j <= order; j++)
                {
                    rsums[i][j] /= counts[i];
                }
            }
        }

        for (i = 0; i < npredictors; i++)
        {
            durbin(rsums[i], order, temp_s7, table[i], &dummy);

            for (j = 1; j <= order; j++)
            {
                if (temp_s7[j] >=  1.0) temp_s7[j] =  0.9999999999;
                if (temp_s7[j] <= -1.0) temp_s7[j] = -0.9999999999;
            }

            afromk(temp_s7, table[i], order);
        }
    }

    free(counts);
    for (i = 0; i < npredictors; i++)
    {
        free(rsums[i]);
    }
    free(rsums);
    free(temp_s7);
}
