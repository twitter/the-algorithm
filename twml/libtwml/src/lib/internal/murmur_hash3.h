//-----------------------------------------------------------------------------
// MurmurHash3 was wr ten by Aust n Appleby, and  s placed  n t  publ c
// doma n. T  author  reby d scla ms copyr ght to t  s ce code.

# fndef _MURMURHASH3_H_
#def ne _MURMURHASH3_H_

//-----------------------------------------------------------------------------
// Platform-spec f c funct ons and macros

// M crosoft V sual Stud o

# f def ned(_MSC_VER) && (_MSC_VER < 1600)

typedef uns gned char u nt8_t;
typedef uns gned  nt u nt32_t;
typedef uns gned __ nt64 u nt64_t;

// Ot r comp lers

#else  // def ned(_MSC_VER)

# nclude <std nt.h>

#end f // !def ned(_MSC_VER)

//-----------------------------------------------------------------------------

vo d MurmurHash3_x86_32  ( const vo d * key,  nt len, u nt32_t seed, vo d * out );

vo d MurmurHash3_x86_128 ( const vo d * key,  nt len, u nt32_t seed, vo d * out );

vo d MurmurHash3_x64_128 ( const vo d * key,  nt len, u nt32_t seed, vo d * out );

//-----------------------------------------------------------------------------

#end f // _MURMURHASH3_H_
