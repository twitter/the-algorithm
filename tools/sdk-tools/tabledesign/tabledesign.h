#ifndef TABLEDESIGN_H
#define TABLEDESIGN_H

#include <stdio.h>

#ifdef __GNUC__
#define UNUSED __attribute__((unused))
#else
#define UNUSED
#endif

//  estimate.c
int durbin(double *thing, int n, double *thing2, double *thing3, double *outSomething);
void afromk(double *in, double *out, int n);
int kfroma(double *in, double *out, int n);
void rfroma(double *dataRow, int n, double *thing3);
double model_dist(double *first, double *second, int n);
void acmat(short *in, int n, int m, double **mat);
void acvect(short *in, int n, int m, double *vec);
int lud(double **a, int n, int *indx, int *d);
void lubksb(double **a, int n, int *indx, double *b);

// codebook.c
void split(double **table, double *delta, int order, int npredictors, double scale);
void refine(double **table, int order, int npredictors, double **data, int dataSize, int refineIters, double unused);

// print.c
int print_entry(FILE *out, double *row, int order);

#endif
