#ifndef MAKE_CONST_NONCONST_H
#define MAKE_CONST_NONCONST_H

#ifdef TARGET_N64
// IDO sometimes puts const variables in .rodata and sometimes in .data, which breaks ordering.
// This makes sure all variables are put into the same section (.data). We need to do this for
// both IDO and gcc for TARGET_N64.
#define const
#endif

#endif // MAKE_CONST_NONCONST_H
