#ifndef PREVENT_BSS_REORDERING_H
#define PREVENT_BSS_REORDERING_H

/**
 * To determine variable order for .bss, the compiler sorts variables by their
 * "name index" mod 256, where name index is something that, with -g, gets
 * incremented by struct and variable declarations, typedefs, and file markers,
 * among else. (Without -g, only variable declarations affects the index.)
 * This file contains enough dummy declarations to bump the index by 128.
 * Including it, or removing the include, should fix bss reordering problems
 * for a file, assuming the name index distance between its first and last bss
 * variable is at most 128.
 * Note that if a variable is declared "extern" within a header file, the name
 * index is taken at that point of the extern declaration. Thus, this include
 * must come before any such header.
 */

struct Dummy0 { int x; };
struct Dummy1 { int x; };
struct Dummy2 { int x; };
struct Dummy3 { int x; };
struct Dummy4 { int x; };
struct Dummy5 { int x; };
struct Dummy6 { int x; };
struct Dummy7 { int x; };
struct Dummy8 { int x; };
struct Dummy9 { int x; };
struct Dummy10 { int x; };
struct Dummy11 { int x; };
struct Dummy12 { int x; };
struct Dummy13 { int x; };
struct Dummy14 { int x; };
struct Dummy15 { int x; };
struct Dummy16 { int x; };
struct Dummy17 { int x; };
struct Dummy18 { int x; };
struct Dummy19 { int x; };
struct Dummy20 { int x; };
struct Dummy21 { int x; };
struct Dummy22 { int x; };
struct Dummy23 { int x; };
struct Dummy24 { int x; };
struct Dummy25 { int x; };
struct Dummy26 { int x; };
struct Dummy27 { int x; };
struct Dummy28 { int x; };
struct Dummy29 { int x; };
struct Dummy30 { int x; };
struct Dummy31 { int x; };
struct Dummy32 { int x; };
struct Dummy33 { int x; };
struct Dummy34 { int x; };
struct Dummy35 { int x; };
struct Dummy36 { int x; };
struct Dummy37 { int x; };
struct Dummy38 { int x; };
struct Dummy39 { int x; };
struct Dummy40 { int x; };
struct Dummy41 { int x; };
struct Dummy42 { int x; };
struct Dummy43 { int x; };
struct Dummy44 { int x; };
struct Dummy45 { int x; };
struct Dummy46 { int x; };
struct Dummy47 { int x; };
struct Dummy48 { int x; };
struct Dummy49 { int x; };
struct Dummy50 { int x; };
struct Dummy51 { int x; };
struct Dummy52 { int x; };
struct Dummy53 { int x; };
struct Dummy54 { int x; };
struct Dummy55 { int x; };
struct Dummy56 { int x; };
struct Dummy57 { int x; };
struct Dummy58 { int x; };
struct Dummy59 { int x; };
struct Dummy60 { int x; };
struct Dummy61 { int x; };
struct Dummy62 { int x; };
typedef int Dummy63;

#endif // PREVENT_BSS_REORDERING_H
