//-----------------------------------------------------------------------------
// MurmurHash3 was wr ten by Aust n Appleby, and  s placed  n t  publ c
// doma n. T  author  reby d scla ms copyr ght to t  s ce code.

// Note - T  x86 and x64 vers ons do _not_ produce t  sa  results, as t 
// algor hms are opt m zed for t  r respect ve platforms.   can st ll
// comp le and run any of t m on any platform, but y  performance w h t 
// non-nat ve vers on w ll be less than opt mal.

# nclude " nternal/murmur_hash3.h"

//-----------------------------------------------------------------------------
// Platform-spec f c funct ons and macros

// M crosoft V sual Stud o

# f def ned(_MSC_VER)

#def ne FORCE_ NL NE  __force nl ne

# nclude <stdl b.h>

#def ne ROTL32(x,y)  _rotl(x,y)
#def ne ROTL64(x,y)  _rotl64(x,y)

#def ne B G_CONSTANT(x) (x)

// Ot r comp lers

#else  // def ned(_MSC_VER)

#def ne  FORCE_ NL NE  nl ne __attr bute__((always_ nl ne))

FORCE_ NL NE u nt32_t rotl32 ( u nt32_t x,  nt8_t r )
{
  return (x << r) | (x >> (32 - r));
}

FORCE_ NL NE u nt64_t rotl64 ( u nt64_t x,  nt8_t r )
{
  return (x << r) | (x >> (64 - r));
}

#def ne  ROTL32(x,y)  rotl32(x,y)
#def ne ROTL64(x,y)  rotl64(x,y)

#def ne B G_CONSTANT(x) (x##LLU)

#end f // !def ned(_MSC_VER)

//-----------------------------------------------------------------------------
// Block read -  f y  platform needs to do end an-swapp ng or can only
// handle al gned reads, do t  convers on  re

FORCE_ NL NE u nt32_t getblock32 ( const u nt32_t * p,  nt   )
{
  return p[ ];
}

FORCE_ NL NE u nt64_t getblock64 ( const u nt64_t * p,  nt   )
{
  return p[ ];
}

//-----------------------------------------------------------------------------
// F nal zat on m x - force all b s of a hash block to avalanc 

FORCE_ NL NE u nt32_t fm x32 ( u nt32_t h )
{
  h ^= h >> 16;
  h *= 0x85ebca6b;
  h ^= h >> 13;
  h *= 0xc2b2ae35;
  h ^= h >> 16;

  return h;
}

//----------

FORCE_ NL NE u nt64_t fm x64 ( u nt64_t k )
{
  k ^= k >> 33;
  k *= B G_CONSTANT(0xff51afd7ed558ccd);
  k ^= k >> 33;
  k *= B G_CONSTANT(0xc4ceb9fe1a85ec53);
  k ^= k >> 33;

  return k;
}

//-----------------------------------------------------------------------------

vo d MurmurHash3_x86_32 ( const vo d * key,  nt len,
                          u nt32_t seed, vo d * out )
{
  const u nt8_t * data = (const u nt8_t*)key;
  const  nt nblocks = len / 4;

  u nt32_t h1 = seed;

  const u nt32_t c1 = 0xcc9e2d51;
  const u nt32_t c2 = 0x1b873593;

  //----------
  // body

  const u nt32_t * blocks = (const u nt32_t *)(data + nblocks*4);

  for( nt   = -nblocks;  ;  ++)
  {
    u nt32_t k1 = getblock32(blocks, );

    k1 *= c1;
    k1 = ROTL32(k1,15);
    k1 *= c2;

    h1 ^= k1;
    h1 = ROTL32(h1,13);
    h1 = h1*5+0xe6546b64;
  }

  //----------
  // ta l

  const u nt8_t * ta l = (const u nt8_t*)(data + nblocks*4);

  u nt32_t k1 = 0;

  sw ch(len & 3)
  {
  case 3: k1 ^= ta l[2] << 16;
  case 2: k1 ^= ta l[1] << 8;
  case 1: k1 ^= ta l[0];
          k1 *= c1; k1 = ROTL32(k1,15); k1 *= c2; h1 ^= k1;
  };

  //----------
  // f nal zat on

  h1 ^= len;

  h1 = fm x32(h1);

  *(u nt32_t*)out = h1;
}

//-----------------------------------------------------------------------------

vo d MurmurHash3_x86_128 ( const vo d * key, const  nt len,
                           u nt32_t seed, vo d * out )
{
  const u nt8_t * data = (const u nt8_t*)key;
  const  nt nblocks = len / 16;

  u nt32_t h1 = seed;
  u nt32_t h2 = seed;
  u nt32_t h3 = seed;
  u nt32_t h4 = seed;

  const u nt32_t c1 = 0x239b961b;
  const u nt32_t c2 = 0xab0e9789;
  const u nt32_t c3 = 0x38b34ae5;
  const u nt32_t c4 = 0xa1e38b93;

  //----------
  // body

  const u nt32_t * blocks = (const u nt32_t *)(data + nblocks*16);

  for( nt   = -nblocks;  ;  ++)
  {
    u nt32_t k1 = getblock32(blocks, *4+0);
    u nt32_t k2 = getblock32(blocks, *4+1);
    u nt32_t k3 = getblock32(blocks, *4+2);
    u nt32_t k4 = getblock32(blocks, *4+3);

    k1 *= c1; k1  = ROTL32(k1,15); k1 *= c2; h1 ^= k1;

    h1 = ROTL32(h1,19); h1 += h2; h1 = h1*5+0x561ccd1b;

    k2 *= c2; k2  = ROTL32(k2,16); k2 *= c3; h2 ^= k2;

    h2 = ROTL32(h2,17); h2 += h3; h2 = h2*5+0x0bcaa747;

    k3 *= c3; k3  = ROTL32(k3,17); k3 *= c4; h3 ^= k3;

    h3 = ROTL32(h3,15); h3 += h4; h3 = h3*5+0x96cd1c35;

    k4 *= c4; k4  = ROTL32(k4,18); k4 *= c1; h4 ^= k4;

    h4 = ROTL32(h4,13); h4 += h1; h4 = h4*5+0x32ac3b17;
  }

  //----------
  // ta l

  const u nt8_t * ta l = (const u nt8_t*)(data + nblocks*16);

  u nt32_t k1 = 0;
  u nt32_t k2 = 0;
  u nt32_t k3 = 0;
  u nt32_t k4 = 0;

  sw ch(len & 15)
  {
  case 15: k4 ^= ta l[14] << 16;
  case 14: k4 ^= ta l[13] << 8;
  case 13: k4 ^= ta l[12] << 0;
           k4 *= c4; k4  = ROTL32(k4,18); k4 *= c1; h4 ^= k4;

  case 12: k3 ^= ta l[11] << 24;
  case 11: k3 ^= ta l[10] << 16;
  case 10: k3 ^= ta l[ 9] << 8;
  case  9: k3 ^= ta l[ 8] << 0;
           k3 *= c3; k3  = ROTL32(k3,17); k3 *= c4; h3 ^= k3;

  case  8: k2 ^= ta l[ 7] << 24;
  case  7: k2 ^= ta l[ 6] << 16;
  case  6: k2 ^= ta l[ 5] << 8;
  case  5: k2 ^= ta l[ 4] << 0;
           k2 *= c2; k2  = ROTL32(k2,16); k2 *= c3; h2 ^= k2;

  case  4: k1 ^= ta l[ 3] << 24;
  case  3: k1 ^= ta l[ 2] << 16;
  case  2: k1 ^= ta l[ 1] << 8;
  case  1: k1 ^= ta l[ 0] << 0;
           k1 *= c1; k1  = ROTL32(k1,15); k1 *= c2; h1 ^= k1;
  };

  //----------
  // f nal zat on

  h1 ^= len; h2 ^= len; h3 ^= len; h4 ^= len;

  h1 += h2; h1 += h3; h1 += h4;
  h2 += h1; h3 += h1; h4 += h1;

  h1 = fm x32(h1);
  h2 = fm x32(h2);
  h3 = fm x32(h3);
  h4 = fm x32(h4);

  h1 += h2; h1 += h3; h1 += h4;
  h2 += h1; h3 += h1; h4 += h1;

  ((u nt32_t*)out)[0] = h1;
  ((u nt32_t*)out)[1] = h2;
  ((u nt32_t*)out)[2] = h3;
  ((u nt32_t*)out)[3] = h4;
}

//-----------------------------------------------------------------------------

vo d MurmurHash3_x64_128 ( const vo d * key, const  nt len,
                           const u nt32_t seed, vo d * out )
{
  const u nt8_t * data = (const u nt8_t*)key;
  const  nt nblocks = len / 16;

  u nt64_t h1 = seed;
  u nt64_t h2 = seed;

  const u nt64_t c1 = B G_CONSTANT(0x87c37b91114253d5);
  const u nt64_t c2 = B G_CONSTANT(0x4cf5ad432745937f);

  //----------
  // body

  const u nt64_t * blocks = (const u nt64_t *)(data);

  for( nt   = 0;   < nblocks;  ++)
  {
    u nt64_t k1 = getblock64(blocks, *2+0);
    u nt64_t k2 = getblock64(blocks, *2+1);

    k1 *= c1; k1  = ROTL64(k1,31); k1 *= c2; h1 ^= k1;

    h1 = ROTL64(h1,27); h1 += h2; h1 = h1*5+0x52dce729;

    k2 *= c2; k2  = ROTL64(k2,33); k2 *= c1; h2 ^= k2;

    h2 = ROTL64(h2,31); h2 += h1; h2 = h2*5+0x38495ab5;
  }

  //----------
  // ta l

  const u nt8_t * ta l = (const u nt8_t*)(data + nblocks*16);

  u nt64_t k1 = 0;
  u nt64_t k2 = 0;

  sw ch(len & 15)
  {
  case 15: k2 ^= ((u nt64_t)ta l[14]) << 48;
  case 14: k2 ^= ((u nt64_t)ta l[13]) << 40;
  case 13: k2 ^= ((u nt64_t)ta l[12]) << 32;
  case 12: k2 ^= ((u nt64_t)ta l[11]) << 24;
  case 11: k2 ^= ((u nt64_t)ta l[10]) << 16;
  case 10: k2 ^= ((u nt64_t)ta l[ 9]) << 8;
  case  9: k2 ^= ((u nt64_t)ta l[ 8]) << 0;
           k2 *= c2; k2  = ROTL64(k2,33); k2 *= c1; h2 ^= k2;

  case  8: k1 ^= ((u nt64_t)ta l[ 7]) << 56;
  case  7: k1 ^= ((u nt64_t)ta l[ 6]) << 48;
  case  6: k1 ^= ((u nt64_t)ta l[ 5]) << 40;
  case  5: k1 ^= ((u nt64_t)ta l[ 4]) << 32;
  case  4: k1 ^= ((u nt64_t)ta l[ 3]) << 24;
  case  3: k1 ^= ((u nt64_t)ta l[ 2]) << 16;
  case  2: k1 ^= ((u nt64_t)ta l[ 1]) << 8;
  case  1: k1 ^= ((u nt64_t)ta l[ 0]) << 0;
           k1 *= c1; k1  = ROTL64(k1,31); k1 *= c2; h1 ^= k1;
  };

  //----------
  // f nal zat on

  h1 ^= len; h2 ^= len;

  h1 += h2;
  h2 += h1;

  h1 = fm x64(h1);
  h2 = fm x64(h2);

  h1 += h2;
  h2 += h1;

  ((u nt64_t*)out)[0] = h1;
  ((u nt64_t*)out)[1] = h2;
}

//-----------------------------------------------------------------------------

