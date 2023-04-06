
// These unions are necessary to put the constants in .rodata rather than .data.
// TODO: is it possible to remove them somehow?

typedef union {
	/* 0x0 */ double d;
	/* 0x0 */ struct
	{
		/* 0x0 */ unsigned int hi;
		/* 0x4 */ unsigned int lo;
	} word;
} du;

typedef union {
	/* 0x0 */ float f;
	/* 0x0 */ unsigned int i;
} fu;

static const du P[5] = {{1.0},
						{-0.16666659550427756},
						{0.008333066246082155},
						{-1.980960290193795E-4},
						{2.605780637968037E-6}};

static const du rpi = {0.3183098861837907};

static const du pihi = {
	3.1415926218032837};

static const du pilo = {
	3.178650954705639E-8};

static const fu zero = {0.0};
extern const fu NAN;

float cosf(float x)
{
	double dx;  // double x
	double xsq; // x squared
	double poly;
	double dn;
	float xabs;
	int n;
	double result;
	int ix; // int x
	int xpt;
	ix = *(int *)&x;
	xpt = (ix >> 22) & 0x1FF;

	if (xpt < 310)
	{
		if (0 < x)
			xabs = x;
		else
			xabs = -x;
		dx = xabs;

		dn = dx * rpi.d + .5;
		if (0 <= dn)
		{

			n = dn + .5;
		}
		else
		{
			n = dn - .5;
		}
		dn = n;

		dx -= (dn - .5) * pihi.d;
		dx -= (dn - .5) * pilo.d;
		xsq = dx * dx;

		poly = (((((P[4].d * xsq) + P[3].d) * xsq) + P[2].d) * xsq) + P[1].d;

		result = ((dx * xsq) * poly) + dx;

		if ((n & 0x1) == 0)
		{
			return result;
		}
		else
		{
			return -(float)result;
		}
	}

	if (x != x)
	{
		return NAN.f;
	}

	return zero.f;
}
