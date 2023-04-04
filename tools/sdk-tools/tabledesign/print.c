#include <stdio.h>
#include <stdlib.h>
#include "tabledesign.h"

int print_entry(FILE *out, double *row, int order)
{
    double **table;
    double fval;
    int ival;
    int i, j, k;
    int overflows;

    table = malloc(8 * sizeof(double*));

    for (i = 0; i < 8; i++)
    {
        table[i] = malloc(order * sizeof(double));
    }

    for (i = 0; i < order; i++)
    {
        for (j = 0; j < i; j++)
        {
            table[i][j] = 0.0;
        }

        for (j = i; j < order; j++)
        {
            table[i][j] = -row[order - j + i];
        }
    }

    for (i = order; i < 8; i++)
    {
        for (j = 0; j < order; j++)
        {
            table[i][j] = 0.0;
        }
    }

    for (i = 1; i < 8; i++)
    {
        for (j = 1; j <= order; j++)
        {
            if (i - j >= 0)
            {
                for (k = 0; k < order; k++)
                {
                    table[i][k] -= row[j] * table[i - j][k];
                }
            }
        }
    }

    overflows = 0;
    for (i = 0; i < order; i++)
    {
        for (j = 0; j < 8; j++)
        {
            fval = table[j][i] * 2048.0;
            if (fval < 0.0)
            {
                ival = (int) (fval - 0.5);
                if (ival < -0x8000)
                {
                    overflows++;
                }
            }
            else
            {
                ival = (int) (fval + 0.5);
                if (ival >= 0x8000)
                {
                    overflows++;
                }
            }
            fprintf(out, "%5d ", ival);
        }

        fprintf(out, "\n");
    }

    for (i = 0; i < 8; i++)
    {
        free(table[i]);
    }
    free(table);
    return overflows;
}
