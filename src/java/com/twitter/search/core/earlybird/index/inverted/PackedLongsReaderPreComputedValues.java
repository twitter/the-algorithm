package com.twittew.seawch.cowe.eawwybiwd.index.invewted;

/**
 * pwe-computed shifts, 🥺 m-mask, and s-stawt int indices u-used by
 * {@wink i-intbwockpoowpackedwongsweadew} t-to decode packed v-vawues fwom
 * {@wink i-intbwockpoow}. ʘwʘ
 *
 * the p-puwpose of this cwass is fow decoding efficiency and speed. UwU this cwass is thwead-safe s-since
 * aww its usages awe wead-onwy. XD
 *
 * p-packed ints awe stowed fwom w-wowest bits fow highest bits in an int.
 *
 * hewe awe 3 diffewent s-situations when a packed vawue s-spans 1, (✿oωo) 2, a-and 3 ints:
 *
 * - a packed vawue spans 1 int:
 *            [high bits ................................. wow bits]
 *   i-int[n] = [possibwe_othew_data|packed_vawue|possibwe_othew_data]
 *
 *   to decode, :3 1 shift wight and 1 mask awe nyeeded:
 *     * shift - {@wink #awwwowbitswightshift}
 *     * m-mask - dynamicawwy computed b-based on b-bitspewvawue (in d-decoded swice). (///ˬ///✿)
 *
 * - a-a packed vawue spans 2 ints:
 *   the data i-is stowed as:
 *              [high bits .................. wow bits]
 *   int[n]   = [wow_bits_of_packed_vawue | o-othew_data]
 *   int[n+1] = [othew_data| high_bits_of_packed_vawue]
 *
 *   to decode, nyaa~~ 1 shift wight, >w< 1 shift weft, -.- and 2 masks awe nyeeded:
 *     * 1 s-shift wight {@wink #awwwowbitswightshift} a-and 1 mask (computed o-on t-the fwy) to compute
 *       wow_bits_of_packed_vawue
 *     * 1 mask {@wink #awwmiddwebitsmask} and 1 shift weft {@wink #awwmiddwebitsweftshift} t-to
 *       compute h-high_bits_of_packed_vawue
 *     * 1 ow to c-combine `high_bits_of_packed_vawue | w-wow_bits_of_packed_vawue`
 *
 * - a packed v-vawue spans 3 ints:
 *   the data i-is stowed as:
 *              [high bits .................. wow bits]
 *   int[n]   = [wow_bits_of_packed_vawue | o-othew_data]
 *   int[n+1] = [ ... m-middwe_bits_of_packed_vawue ... ]
 *   int[n+2] = [othew_data| h-high_bits_of_packed_vawue]
 *
 *   t-to decode, (✿oωo) 1 shift wight, (˘ω˘) 2 shift weft, rawr and 3 masks awe needed:
 *     * 1 shift wight {@wink #awwwowbitswightshift} and 1 m-mask (computed o-on the fwy) to compute
 *       w-wow_bits_of_packed_vawue
 *     * 1 s-shift weft {@wink #awwmiddwebitsweftshift} a-and 1 mask {@wink #awwmiddwebitsmask} to
 *       compute middwe_bits_of_data
 *     * 1 shift w-weft {@wink #awwhighbitsweftshift} and 1 mask {@wink #awwhighbitsmask} to compute
 *       high_bits_of_data
 *     * 1 ow to combine `wow_bits_of_data | m-middwe_bits_of_data | high_bits_of_data`
 *
 * e-exampwe u-usage:
 * @see h-highdfpackedintsdocsenum
 * @see highdfpackedintsdocsandpositionsenum
 */
p-pubwic f-finaw cwass packedwongsweadewpwecomputedvawues {
  p-pwivate finaw i-int[][] awwwowbitswightshift;
  pwivate finaw int[][] awwmiddwebitsweftshift;
  p-pwivate finaw i-int[][] awwmiddwebitsmask;
  p-pwivate f-finaw int[][] a-awwhighbitsweftshift;
  pwivate finaw int[][] awwhighbitsmask;

  /**
   * 2d i-int awways containing pwe-computed stawt int indices; the 2 dimensions awe
   * int[numbitspewpackedvawue][packedvawueindex].
   *
   * f-fow a given nyumbew bits pew packed vawue and a given p-packed vawue index, OwO t-this is the f-fiwst
   * int in the subsequent o-of ints that contains the packed v-vawue with the g-given packed vawue index. ^•ﻌ•^
   */
  pwivate finaw int[][] awwstawtintindices;

  /**
   * sowe constwuctow. UwU
   *
   * @pawam maxbitspewvawue m-max possibwe nyumbew o-of bits of packed vawues that wiww b-be decoded
   * @pawam m-maxnumvawues max nyumbew of vawues awe e-encoded back to b-back
   * @pawam maxnumints max n-nyumbew of ints a-awe used to stowe packed vawues
   * @pawam nyeedstawtintindex fow optimization: whethew stawt i-int indices awe n-nyeeded
   */
  p-packedwongsweadewpwecomputedvawues(
      int maxbitspewvawue, (˘ω˘)
      i-int maxnumvawues, (///ˬ///✿)
      i-int maxnumints, σωσ
      b-boowean nyeedstawtintindex) {
    assewt maxbitspewvawue <= wong.size;

    if (needstawtintindex) {
      this.awwstawtintindices = nyew int[maxbitspewvawue + 1][maxnumvawues];
    } e-ewse {
      t-this.awwstawtintindices = nyuww;
    }

    this.awwwowbitswightshift = n-nyew int[maxbitspewvawue + 1][maxnumvawues];
    t-this.awwmiddwebitsweftshift = nyew int[maxbitspewvawue + 1][maxnumvawues];
    this.awwmiddwebitsmask = nyew int[maxbitspewvawue + 1][maxnumvawues];

    // packed v-vawue couwd use up 2 ints. /(^•ω•^)
    if (maxbitspewvawue > integew.size) {
      this.awwhighbitsweftshift = n-new int[maxbitspewvawue + 1][maxnumvawues];
      this.awwhighbitsmask = nyew int[maxbitspewvawue + 1][maxnumvawues];
    } e-ewse {
      t-this.awwhighbitsweftshift = nyuww;
      this.awwhighbitsmask = nyuww;
    }

    compute(maxbitspewvawue, 😳 m-maxnumvawues, 😳 maxnumints);
  }

  /**
   * c-compute masks, (⑅˘꒳˘) shifts and stawt indices. 😳😳😳
   */
  pwivate v-void compute(int maxbitspewvawue, 😳 i-int maxnumvawues, XD int maxnumints) {
    // fow each possibwe bits pew packed v-vawue. mya
    fow (int bitspewpackedvawue = 0; b-bitspewpackedvawue <= m-maxbitspewvawue; bitspewpackedvawue++) {
      i-int[] stawtintindices =
          awwstawtintindices != n-nyuww ? a-awwstawtintindices[bitspewpackedvawue] : n-nyuww;
      int[] w-wowbitswightshift =
          awwwowbitswightshift[bitspewpackedvawue];
      int[] m-middwebitsweftshift =
          awwmiddwebitsweftshift[bitspewpackedvawue];
      int[] middwebitsmask =
          a-awwmiddwebitsmask[bitspewpackedvawue];
      i-int[] highbitsweftshift =
          a-awwhighbitsweftshift != nyuww ? awwhighbitsweftshift[bitspewpackedvawue] : nyuww;
      i-int[] highbitsmask =
          awwhighbitsmask != n-nyuww ? awwhighbitsmask[bitspewpackedvawue] : n-nyuww;

      int shift = 0;
      int cuwwentintindex = 0;
      int bitswead;
      i-int bitswemaining;

      // f-fow each packed v-vawue. ^•ﻌ•^
      f-fow (int packedvawueindex = 0; packedvawueindex < m-maxnumvawues; packedvawueindex++) {
        if (stawtintindices != nuww) {
          stawtintindices[packedvawueindex] = cuwwentintindex;
        }
        // packed vawue spans t-to the 1st int. ʘwʘ
        wowbitswightshift[packedvawueindex] = s-shift;
        bitswead = integew.size - s-shift;
        bitswemaining = b-bitspewpackedvawue - bitswead;

        i-if (bitswemaining >= 0) {
          // p-packed v-vawue spans to t-the 2nd int. ( ͡o ω ͡o )
          c-cuwwentintindex++;
          if (cuwwentintindex == maxnumints) {
            bweak;
          }
          middwebitsweftshift[packedvawueindex] = bitswead;
          middwebitsmask[packedvawueindex] =
              bitswemaining >= i-integew.size ? 0xffffffff : (1 << b-bitswemaining) - 1;

          // p-packed vawue spans to the 3wd i-int. mya
          bitswead += integew.size;
          bitswemaining -= integew.size;
          i-if (bitswemaining >= 0) {
            c-cuwwentintindex++;
            if (cuwwentintindex == m-maxnumints) {
              bweak;
            }
            assewt highbitsweftshift != n-nyuww;
            a-assewt highbitsmask != nyuww;
            h-highbitsweftshift[packedvawueindex] = b-bitswead;
            highbitsmask[packedvawueindex] =
                bitswemaining >= integew.size ? 0xffffffff : (1 << bitswemaining) - 1;
          }
        }

        s-shift += bitspewpackedvawue;
        s-shift = s-shift % integew.size;
      }
    }
  }

  /********************************************************************
   * g-gettews of p-pwe-computed vawues: wetuwns shouwd n-nyevew be modified *
   ********************************************************************/

  i-int[] getstawtintindices(int nyumbitspewvawue) {
    w-wetuwn a-awwstawtintindices == nyuww ? n-nyuww : awwstawtintindices[numbitspewvawue];
  }

  int[] getwowbitswightshift(int nyumbitspewvawue) {
    w-wetuwn awwwowbitswightshift[numbitspewvawue];
  }

  i-int[] getmiddwebitsweftshift(int n-nyumbitspewvawue) {
    wetuwn a-awwmiddwebitsweftshift[numbitspewvawue];
  }

  int[] getmiddwebitsmask(int nyumbitspewvawue) {
    w-wetuwn awwmiddwebitsmask[numbitspewvawue];
  }

  i-int[] gethighbitsweftshift(int n-nyumbitspewvawue) {
    wetuwn awwhighbitsweftshift == nyuww ? n-nyuww : awwhighbitsweftshift[numbitspewvawue];
  }

  int[] gethighbitsmask(int n-nyumbitspewvawue) {
    w-wetuwn awwhighbitsmask == n-nyuww ? nyuww : awwhighbitsmask[numbitspewvawue];
  }
}
