#include <math.h>
#include <stdlib.h>
#include "tabledesign.h"

/**
 * Computes the autocorrelation of a vector. More precisely, it computes the
 * dot products of vec[i:] and vec[:-i] for i in [0, k). Unused.
 *
 * See https://en.wikipedia.org/wiki/Autocorrelation.
 */
void acf(double *vec, int n, double *out, int k)
{
    int i, j;
    double sum;
    for (i = 0; i < k; i++)
    {
        sum = 0.0;
        for (j = 0; j < n - i; j++)
        {
            sum += vec[j + i] * vec[j];
        }
        out[i] = sum;
    }
}

// https://en.wikipedia.org/wiki/Durbin%E2%80%93Watson_statistic ?
// "detects the presence of autocorrelation at lag 1 in the residuals (prediction errors)"
int durbin(double *arg0, int n, double *arg2, double *arg3, double *outSomething)
{
    int i, j;
    double sum, div;
    int ret;

    arg3[0] = 1.0;
    div = arg0[0];
    ret = 0;

    for (i = 1; i <= n; i++)
    {
        sum = 0.0;
        for (j = 1; j <= i-1; j++)
        {
            sum += arg3[j] * arg0[i - j];
        }

        arg3[i] = (div > 0.0 ? -(arg0[i] + sum) / div : 0.0);
        arg2[i] = arg3[i];

        if (fabs(arg2[i]) > 1.0)
        {
            ret++;
        }

        for (j = 1; j < i; j++)
        {
            arg3[j] += arg3[i - j] * arg3[i];
        }

        div *= 1.0 - arg3[i] * arg3[i];
    }
    *outSomething = div;
    return ret;
}

void afromk(double *in, double *out, int n)
{
    int i, j;
    out[0] = 1.0;
    for (i = 1; i <= n; i++)
    {
        out[i] = in[i];
        for (j = 1; j <= i - 1; j++)
        {
            out[j] += out[i - j] * out[i];
        }
    }
}

int kfroma(double *in, double *out, int n)
{
    int i, j;
    double div;
    double temp;
    double *next;
    int ret;

    ret = 0;
    next = malloc((n + 1) * sizeof(double));

    out[n] = in[n];
    for (i = n - 1; i >= 1; i--)
    {
        for (j = 0; j <= i; j++)
        {
            temp = out[i + 1];
            div = 1.0 - (temp * temp);
            if (div == 0.0)
            {
                free(next);
                return 1;
            }
            next[j] = (in[j] - in[i + 1 - j] * temp) / div;
        }

        for (j = 0; j <= i; j++)
        {
            in[j] = next[j];
        }

        out[i] = next[i];
        if (fabs(out[i]) > 1.0)
        {
            ret++;
        }
    }

    free(next);
    return ret;
}

void rfroma(double *arg0, int n, double *arg2)
{
    int i, j;
    double **mat;
    double div;

    mat = malloc((n + 1) * sizeof(double*));
    mat[n] = malloc((n + 1) * sizeof(double));
    mat[n][0] = 1.0;
    for (i = 1; i <= n; i++)
    {
        mat[n][i] = -arg0[i];
    }

    for (i = n; i >= 1; i--)
    {
        mat[i - 1] = malloc(i * sizeof(double));
        div = 1.0 - mat[i][i] * mat[i][i];
        for (j = 1; j <= i - 1; j++)
        {
            mat[i - 1][j] = (mat[i][i - j] * mat[i][i] + mat[i][j]) / div;
        }
    }

    arg2[0] = 1.0;
    for (i = 1; i <= n; i++)
    {
        arg2[i] = 0.0;
        for (j = 1; j <= i; j++)
        {
            arg2[i] += mat[i][j] * arg2[i - j];
        }
    }

    free(mat[n]);
    for (i = n; i > 0; i--)
    {
        free(mat[i - 1]);
    }
    free(mat);
}

double model_dist(double *arg0, double *arg1, int n)
{
    double *sp3C;
    double *sp38;
    double ret;
    int i, j;

    sp3C = malloc((n + 1) * sizeof(double));
    sp38 = malloc((n + 1) * sizeof(double));
    rfroma(arg1, n, sp3C);

    for (i = 0; i <= n; i++)
    {
        sp38[i] = 0.0;
        for (j = 0; j <= n - i; j++)
        {
            sp38[i] += arg0[j] * arg0[i + j];
        }
    }

    ret = sp38[0] * sp3C[0];
    for (i = 1; i <= n; i++)
    {
        ret += 2 * sp3C[i] * sp38[i];
    }

    free(sp3C);
    free(sp38);
    return ret;
}

// compute autocorrelation matrix?
void acmat(short *in, int n, int m, double **out)
{
    int i, j, k;
    for (i = 1; i <= n; i++)
    {
        for (j = 1; j <= n; j++)
        {
            out[i][j] = 0.0;
            for (k = 0; k < m; k++)
            {
                out[i][j] += in[k - i] * in[k - j];
            }
        }
    }
}

// compute autocorrelation vector?
void acvect(short *in, int n, int m, double *out)
{
    int i, j;
    for (i = 0; i <= n; i++)
    {
        out[i] = 0.0;
        for (j = 0; j < m; j++)
        {
            out[i] -= in[j - i] * in[j];
        }
    }
}

/**
 * Replaces a real n-by-n matrix "a" with the LU decomposition of a row-wise
 * permutation of itself.
 *
 * Input parameters:
 * a: The matrix which is operated on. 1-indexed; it should be of size
 *    (n+1) x (n+1), and row/column index 0 is not used.
 * n: The size of the matrix.
 *
 * Output parameters:
 * indx: The row permutation performed. 1-indexed; it should be of size n+1,
 *       and index 0 is not used.
 * d: the determinant of the permutation matrix.
 *
 * Returns 1 to indicate failure if the matrix is singular or has zeroes on the
 * diagonal, 0 on success.
 *
 * Derived from ludcmp in "Numerical Recipes in C: The Art of Scientific Computing",
 * with modified error handling.
 */
int lud(double **a, int n, int *indx, int *d)
{
    int i,imax,j,k;
    double big,dum,sum,temp;
    double min,max;
    double *vv;

    vv = malloc((n + 1) * sizeof(double));
    *d=1;
    for (i=1;i<=n;i++) {
        big=0.0;
        for (j=1;j<=n;j++)
            if ((temp=fabs(a[i][j])) > big) big=temp;
        if (big == 0.0) return 1;
        vv[i]=1.0/big;
    }
    for (j=1;j<=n;j++) {
        for (i=1;i<j;i++) {
            sum=a[i][j];
            for (k=1;k<i;k++) sum -= a[i][k]*a[k][j];
            a[i][j]=sum;
        }
        big=0.0;
        for (i=j;i<=n;i++) {
            sum=a[i][j];
            for (k=1;k<j;k++)
                sum -= a[i][k]*a[k][j];
            a[i][j]=sum;
            if ( (dum=vv[i]*fabs(sum)) >= big) {
                big=dum;
                imax=i;
            }
        }
        if (j != imax) {
            for (k=1;k<=n;k++) {
                dum=a[imax][k];
                a[imax][k]=a[j][k];
                a[j][k]=dum;
            }
            *d = -(*d);
            vv[imax]=vv[j];
        }
        indx[j]=imax;
        if (a[j][j] == 0.0) return 1;
        if (j != n) {
            dum=1.0/(a[j][j]);
            for (i=j+1;i<=n;i++) a[i][j] *= dum;
        }
    }
    free(vv);

    min = 1e10;
    max = 0.0;
    for (i = 1; i <= n; i++)
    {
        temp = fabs(a[i][i]);
        if (temp < min) min = temp;
        if (temp > max) max = temp;
    }
    return min / max < 1e-10 ? 1 : 0;
}

/**
 * Solves the set of n linear equations Ax = b, using LU decomposition
 * back-substitution.
 *
 * Input parameters:
 * a: The LU decomposition of a matrix, created by "lud".
 * n: The size of the matrix.
 * indx: Row permutation vector, created by "lud".
 * b: The vector b in the equation. 1-indexed; is should be of size n+1, and
 *    index 0 is not used.
 *
 * Output parameters:
 * b: The output vector x. 1-indexed.
 *
 * From "Numerical Recipes in C: The Art of Scientific Computing".
 */
void lubksb(double **a, int n, int *indx, double *b)
{
    int i,ii=0,ip,j;
    double sum;

    for (i=1;i<=n;i++) {
        ip=indx[i];
        sum=b[ip];
        b[ip]=b[i];
        if (ii)
            for (j=ii;j<=i-1;j++) sum -= a[i][j]*b[j];
        else if (sum) ii=i;
        b[i]=sum;
    }
    for (i=n;i>=1;i--) {
        sum=b[i];
        for (j=i+1;j<=n;j++) sum -= a[i][j]*b[j];
        b[i]=sum/a[i][i];
    }
}
