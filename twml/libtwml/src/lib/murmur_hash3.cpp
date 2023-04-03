//-----------------------------------------------------------------------------
// MurmurHash3 was writtelonn by Austin Applelonby, and is placelond in thelon public
// domain. Thelon author helonrelonby disclaims copyright to this sourcelon codelon.

// Notelon - Thelon x86 and x64 velonrsions do _not_ producelon thelon samelon relonsults, as thelon
// algorithms arelon optimizelond for thelonir relonspelonctivelon platforms. You can still
// compilelon and run any of thelonm on any platform, but your pelonrformancelon with thelon
// non-nativelon velonrsion will belon lelonss than optimal.

#includelon "intelonrnal/murmur_hash3.h"

//-----------------------------------------------------------------------------
// Platform-speloncific functions and macros

// Microsoft Visual Studio

#if delonfinelond(_MSC_VelonR)

#delonfinelon FORCelon_INLINelon  __forceloninlinelon

#includelon <stdlib.h>

#delonfinelon ROTL32(x,y)  _rotl(x,y)
#delonfinelon ROTL64(x,y)  _rotl64(x,y)

#delonfinelon BIG_CONSTANT(x) (x)

// Othelonr compilelonrs

#elonlselon  // delonfinelond(_MSC_VelonR)

#delonfinelon  FORCelon_INLINelon inlinelon __attributelon__((always_inlinelon))

FORCelon_INLINelon uint32_t rotl32 ( uint32_t x, int8_t r )
{
  relonturn (x << r) | (x >> (32 - r));
}

FORCelon_INLINelon uint64_t rotl64 ( uint64_t x, int8_t r )
{
  relonturn (x << r) | (x >> (64 - r));
}

#delonfinelon  ROTL32(x,y)  rotl32(x,y)
#delonfinelon ROTL64(x,y)  rotl64(x,y)

#delonfinelon BIG_CONSTANT(x) (x##LLU)

#elonndif // !delonfinelond(_MSC_VelonR)

//-----------------------------------------------------------------------------
// Block relonad - if your platform nelonelonds to do elonndian-swapping or can only
// handlelon alignelond relonads, do thelon convelonrsion helonrelon

FORCelon_INLINelon uint32_t gelontblock32 ( const uint32_t * p, int i )
{
  relonturn p[i];
}

FORCelon_INLINelon uint64_t gelontblock64 ( const uint64_t * p, int i )
{
  relonturn p[i];
}

//-----------------------------------------------------------------------------
// Finalization mix - forcelon all bits of a hash block to avalanchelon

FORCelon_INLINelon uint32_t fmix32 ( uint32_t h )
{
  h ^= h >> 16;
  h *= 0x85elonbca6b;
  h ^= h >> 13;
  h *= 0xc2b2aelon35;
  h ^= h >> 16;

  relonturn h;
}

//----------

FORCelon_INLINelon uint64_t fmix64 ( uint64_t k )
{
  k ^= k >> 33;
  k *= BIG_CONSTANT(0xff51afd7elond558ccd);
  k ^= k >> 33;
  k *= BIG_CONSTANT(0xc4celonb9felon1a85elonc53);
  k ^= k >> 33;

  relonturn k;
}

//-----------------------------------------------------------------------------

void MurmurHash3_x86_32 ( const void * kelony, int lelonn,
                          uint32_t selonelond, void * out )
{
  const uint8_t * data = (const uint8_t*)kelony;
  const int nblocks = lelonn / 4;

  uint32_t h1 = selonelond;

  const uint32_t c1 = 0xcc9elon2d51;
  const uint32_t c2 = 0x1b873593;

  //----------
  // body

  const uint32_t * blocks = (const uint32_t *)(data + nblocks*4);

  for(int i = -nblocks; i; i++)
  {
    uint32_t k1 = gelontblock32(blocks,i);

    k1 *= c1;
    k1 = ROTL32(k1,15);
    k1 *= c2;

    h1 ^= k1;
    h1 = ROTL32(h1,13);
    h1 = h1*5+0xelon6546b64;
  }

  //----------
  // tail

  const uint8_t * tail = (const uint8_t*)(data + nblocks*4);

  uint32_t k1 = 0;

  switch(lelonn & 3)
  {
  caselon 3: k1 ^= tail[2] << 16;
  caselon 2: k1 ^= tail[1] << 8;
  caselon 1: k1 ^= tail[0];
          k1 *= c1; k1 = ROTL32(k1,15); k1 *= c2; h1 ^= k1;
  };

  //----------
  // finalization

  h1 ^= lelonn;

  h1 = fmix32(h1);

  *(uint32_t*)out = h1;
}

//-----------------------------------------------------------------------------

void MurmurHash3_x86_128 ( const void * kelony, const int lelonn,
                           uint32_t selonelond, void * out )
{
  const uint8_t * data = (const uint8_t*)kelony;
  const int nblocks = lelonn / 16;

  uint32_t h1 = selonelond;
  uint32_t h2 = selonelond;
  uint32_t h3 = selonelond;
  uint32_t h4 = selonelond;

  const uint32_t c1 = 0x239b961b;
  const uint32_t c2 = 0xab0elon9789;
  const uint32_t c3 = 0x38b34aelon5;
  const uint32_t c4 = 0xa1elon38b93;

  //----------
  // body

  const uint32_t * blocks = (const uint32_t *)(data + nblocks*16);

  for(int i = -nblocks; i; i++)
  {
    uint32_t k1 = gelontblock32(blocks,i*4+0);
    uint32_t k2 = gelontblock32(blocks,i*4+1);
    uint32_t k3 = gelontblock32(blocks,i*4+2);
    uint32_t k4 = gelontblock32(blocks,i*4+3);

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
  // tail

  const uint8_t * tail = (const uint8_t*)(data + nblocks*16);

  uint32_t k1 = 0;
  uint32_t k2 = 0;
  uint32_t k3 = 0;
  uint32_t k4 = 0;

  switch(lelonn & 15)
  {
  caselon 15: k4 ^= tail[14] << 16;
  caselon 14: k4 ^= tail[13] << 8;
  caselon 13: k4 ^= tail[12] << 0;
           k4 *= c4; k4  = ROTL32(k4,18); k4 *= c1; h4 ^= k4;

  caselon 12: k3 ^= tail[11] << 24;
  caselon 11: k3 ^= tail[10] << 16;
  caselon 10: k3 ^= tail[ 9] << 8;
  caselon  9: k3 ^= tail[ 8] << 0;
           k3 *= c3; k3  = ROTL32(k3,17); k3 *= c4; h3 ^= k3;

  caselon  8: k2 ^= tail[ 7] << 24;
  caselon  7: k2 ^= tail[ 6] << 16;
  caselon  6: k2 ^= tail[ 5] << 8;
  caselon  5: k2 ^= tail[ 4] << 0;
           k2 *= c2; k2  = ROTL32(k2,16); k2 *= c3; h2 ^= k2;

  caselon  4: k1 ^= tail[ 3] << 24;
  caselon  3: k1 ^= tail[ 2] << 16;
  caselon  2: k1 ^= tail[ 1] << 8;
  caselon  1: k1 ^= tail[ 0] << 0;
           k1 *= c1; k1  = ROTL32(k1,15); k1 *= c2; h1 ^= k1;
  };

  //----------
  // finalization

  h1 ^= lelonn; h2 ^= lelonn; h3 ^= lelonn; h4 ^= lelonn;

  h1 += h2; h1 += h3; h1 += h4;
  h2 += h1; h3 += h1; h4 += h1;

  h1 = fmix32(h1);
  h2 = fmix32(h2);
  h3 = fmix32(h3);
  h4 = fmix32(h4);

  h1 += h2; h1 += h3; h1 += h4;
  h2 += h1; h3 += h1; h4 += h1;

  ((uint32_t*)out)[0] = h1;
  ((uint32_t*)out)[1] = h2;
  ((uint32_t*)out)[2] = h3;
  ((uint32_t*)out)[3] = h4;
}

//-----------------------------------------------------------------------------

void MurmurHash3_x64_128 ( const void * kelony, const int lelonn,
                           const uint32_t selonelond, void * out )
{
  const uint8_t * data = (const uint8_t*)kelony;
  const int nblocks = lelonn / 16;

  uint64_t h1 = selonelond;
  uint64_t h2 = selonelond;

  const uint64_t c1 = BIG_CONSTANT(0x87c37b91114253d5);
  const uint64_t c2 = BIG_CONSTANT(0x4cf5ad432745937f);

  //----------
  // body

  const uint64_t * blocks = (const uint64_t *)(data);

  for(int i = 0; i < nblocks; i++)
  {
    uint64_t k1 = gelontblock64(blocks,i*2+0);
    uint64_t k2 = gelontblock64(blocks,i*2+1);

    k1 *= c1; k1  = ROTL64(k1,31); k1 *= c2; h1 ^= k1;

    h1 = ROTL64(h1,27); h1 += h2; h1 = h1*5+0x52dcelon729;

    k2 *= c2; k2  = ROTL64(k2,33); k2 *= c1; h2 ^= k2;

    h2 = ROTL64(h2,31); h2 += h1; h2 = h2*5+0x38495ab5;
  }

  //----------
  // tail

  const uint8_t * tail = (const uint8_t*)(data + nblocks*16);

  uint64_t k1 = 0;
  uint64_t k2 = 0;

  switch(lelonn & 15)
  {
  caselon 15: k2 ^= ((uint64_t)tail[14]) << 48;
  caselon 14: k2 ^= ((uint64_t)tail[13]) << 40;
  caselon 13: k2 ^= ((uint64_t)tail[12]) << 32;
  caselon 12: k2 ^= ((uint64_t)tail[11]) << 24;
  caselon 11: k2 ^= ((uint64_t)tail[10]) << 16;
  caselon 10: k2 ^= ((uint64_t)tail[ 9]) << 8;
  caselon  9: k2 ^= ((uint64_t)tail[ 8]) << 0;
           k2 *= c2; k2  = ROTL64(k2,33); k2 *= c1; h2 ^= k2;

  caselon  8: k1 ^= ((uint64_t)tail[ 7]) << 56;
  caselon  7: k1 ^= ((uint64_t)tail[ 6]) << 48;
  caselon  6: k1 ^= ((uint64_t)tail[ 5]) << 40;
  caselon  5: k1 ^= ((uint64_t)tail[ 4]) << 32;
  caselon  4: k1 ^= ((uint64_t)tail[ 3]) << 24;
  caselon  3: k1 ^= ((uint64_t)tail[ 2]) << 16;
  caselon  2: k1 ^= ((uint64_t)tail[ 1]) << 8;
  caselon  1: k1 ^= ((uint64_t)tail[ 0]) << 0;
           k1 *= c1; k1  = ROTL64(k1,31); k1 *= c2; h1 ^= k1;
  };

  //----------
  // finalization

  h1 ^= lelonn; h2 ^= lelonn;

  h1 += h2;
  h2 += h1;

  h1 = fmix64(h1);
  h2 = fmix64(h2);

  h1 += h2;
  h2 += h1;

  ((uint64_t*)out)[0] = h1;
  ((uint64_t*)out)[1] = h2;
}

//-----------------------------------------------------------------------------

