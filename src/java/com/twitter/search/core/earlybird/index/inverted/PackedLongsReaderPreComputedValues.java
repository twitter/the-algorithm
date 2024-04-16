package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 * Pre-computed sh fts, mask, and start  nt  nd ces used by
 * {@l nk  ntBlockPoolPackedLongsReader} to decode packed values from
 * {@l nk  ntBlockPool}.
 *
 * T  purpose of t  class  s for decod ng eff c ency and speed. T  class  s thread-safe s nce
 * all  s usages are read-only.
 *
 * Packed  nts are stored from LOWEST b s for H GHEST b s  n an  nt.
 *
 *  re are 3 d fferent s uat ons w n a packed value spans 1, 2, and 3  nts:
 *
 * - A packed value spans 1  nt:
 *            [H gh B s ................................. Low B s]
 *    nt[n] = [poss ble_ot r_data|packed_value|poss ble_ot r_data]
 *
 *   To decode, 1 sh ft r ght and 1 mask are needed:
 *     * sh ft - {@l nk #allLowB sR ghtSh ft}
 *     * mask - dynam cally computed based on b sPerValue ( n decoded sl ce).
 *
 * - A packed value spans 2  nts:
 *   T  data  s stored as:
 *              [H gh B s .................. Low B s]
 *    nt[n]   = [low_b s_of_packed_value | ot r_data]
 *    nt[n+1] = [ot r_data| h gh_b s_of_packed_value]
 *
 *   To decode, 1 sh ft r ght, 1 sh ft left, and 2 masks are needed:
 *     * 1 sh ft r ght {@l nk #allLowB sR ghtSh ft} and 1 mask (computed on t  fly) to compute
 *       low_b s_of_packed_value
 *     * 1 mask {@l nk #allM ddleB sMask} and 1 sh ft left {@l nk #allM ddleB sLeftSh ft} to
 *       compute h gh_b s_of_packed_value
 *     * 1 OR to comb ne `h gh_b s_of_packed_value | low_b s_of_packed_value`
 *
 * - A packed value spans 3  nts:
 *   T  data  s stored as:
 *              [H gh B s .................. Low B s]
 *    nt[n]   = [low_b s_of_packed_value | ot r_data]
 *    nt[n+1] = [ ... m ddle_b s_of_packed_value ... ]
 *    nt[n+2] = [ot r_data| h gh_b s_of_packed_value]
 *
 *   To decode, 1 sh ft r ght, 2 sh ft left, and 3 masks are needed:
 *     * 1 sh ft r ght {@l nk #allLowB sR ghtSh ft} and 1 mask (computed on t  fly) to compute
 *       low_b s_of_packed_value
 *     * 1 sh ft left {@l nk #allM ddleB sLeftSh ft} and 1 mask {@l nk #allM ddleB sMask} to
 *       compute m ddle_b s_of_data
 *     * 1 sh ft left {@l nk #allH ghB sLeftSh ft} and 1 mask {@l nk #allH ghB sMask} to compute
 *       h gh_b s_of_data
 *     * 1 OR to comb ne `low_b s_of_data | m ddle_b s_of_data | h gh_b s_of_data`
 *
 * Example usage:
 * @see H ghDFPacked ntsDocsEnum
 * @see H ghDFPacked ntsDocsAndPos  onsEnum
 */
publ c f nal class PackedLongsReaderPreComputedValues {
  pr vate f nal  nt[][] allLowB sR ghtSh ft;
  pr vate f nal  nt[][] allM ddleB sLeftSh ft;
  pr vate f nal  nt[][] allM ddleB sMask;
  pr vate f nal  nt[][] allH ghB sLeftSh ft;
  pr vate f nal  nt[][] allH ghB sMask;

  /**
   * 2D  nt arrays conta n ng pre-computed start  nt  nd ces; t  2 d  ns ons are
   *  nt[numB sPerPackedValue][packedValue ndex].
   *
   * For a g ven number b s per packed value and a g ven packed value  ndex, t   s t  f rst
   *  nt  n t  subsequent of  nts that conta ns t  packed value w h t  g ven packed value  ndex.
   */
  pr vate f nal  nt[][] allStart nt nd ces;

  /**
   * Sole constructor.
   *
   * @param maxB sPerValue max poss ble number of b s of packed values that w ll be decoded
   * @param maxNumValues max number of values are encoded back to back
   * @param maxNum nts max number of  nts are used to store packed values
   * @param needStart nt ndex for opt m zat on: w t r start  nt  nd ces are needed
   */
  PackedLongsReaderPreComputedValues(
       nt maxB sPerValue,
       nt maxNumValues,
       nt maxNum nts,
      boolean needStart nt ndex) {
    assert maxB sPerValue <= Long.S ZE;

     f (needStart nt ndex) {
      t .allStart nt nd ces = new  nt[maxB sPerValue + 1][maxNumValues];
    } else {
      t .allStart nt nd ces = null;
    }

    t .allLowB sR ghtSh ft = new  nt[maxB sPerValue + 1][maxNumValues];
    t .allM ddleB sLeftSh ft = new  nt[maxB sPerValue + 1][maxNumValues];
    t .allM ddleB sMask = new  nt[maxB sPerValue + 1][maxNumValues];

    // Packed value could use up 2  nts.
     f (maxB sPerValue >  nteger.S ZE) {
      t .allH ghB sLeftSh ft = new  nt[maxB sPerValue + 1][maxNumValues];
      t .allH ghB sMask = new  nt[maxB sPerValue + 1][maxNumValues];
    } else {
      t .allH ghB sLeftSh ft = null;
      t .allH ghB sMask = null;
    }

    compute(maxB sPerValue, maxNumValues, maxNum nts);
  }

  /**
   * Compute masks, sh fts and start  nd ces.
   */
  pr vate vo d compute( nt maxB sPerValue,  nt maxNumValues,  nt maxNum nts) {
    // For each poss ble b s per packed value.
    for ( nt b sPerPackedValue = 0; b sPerPackedValue <= maxB sPerValue; b sPerPackedValue++) {
       nt[] start nt nd ces =
          allStart nt nd ces != null ? allStart nt nd ces[b sPerPackedValue] : null;
       nt[] lowB sR ghtSh ft =
          allLowB sR ghtSh ft[b sPerPackedValue];
       nt[] m ddleB sLeftSh ft =
          allM ddleB sLeftSh ft[b sPerPackedValue];
       nt[] m ddleB sMask =
          allM ddleB sMask[b sPerPackedValue];
       nt[] h ghB sLeftSh ft =
          allH ghB sLeftSh ft != null ? allH ghB sLeftSh ft[b sPerPackedValue] : null;
       nt[] h ghB sMask =
          allH ghB sMask != null ? allH ghB sMask[b sPerPackedValue] : null;

       nt sh ft = 0;
       nt current nt ndex = 0;
       nt b sRead;
       nt b sRema n ng;

      // For each packed value.
      for ( nt packedValue ndex = 0; packedValue ndex < maxNumValues; packedValue ndex++) {
         f (start nt nd ces != null) {
          start nt nd ces[packedValue ndex] = current nt ndex;
        }
        // Packed value spans to t  1st  nt.
        lowB sR ghtSh ft[packedValue ndex] = sh ft;
        b sRead =  nteger.S ZE - sh ft;
        b sRema n ng = b sPerPackedValue - b sRead;

         f (b sRema n ng >= 0) {
          // Packed value spans to t  2nd  nt.
          current nt ndex++;
           f (current nt ndex == maxNum nts) {
            break;
          }
          m ddleB sLeftSh ft[packedValue ndex] = b sRead;
          m ddleB sMask[packedValue ndex] =
              b sRema n ng >=  nteger.S ZE ? 0xFFFFFFFF : (1 << b sRema n ng) - 1;

          // Packed value spans to t  3rd  nt.
          b sRead +=  nteger.S ZE;
          b sRema n ng -=  nteger.S ZE;
           f (b sRema n ng >= 0) {
            current nt ndex++;
             f (current nt ndex == maxNum nts) {
              break;
            }
            assert h ghB sLeftSh ft != null;
            assert h ghB sMask != null;
            h ghB sLeftSh ft[packedValue ndex] = b sRead;
            h ghB sMask[packedValue ndex] =
                b sRema n ng >=  nteger.S ZE ? 0xFFFFFFFF : (1 << b sRema n ng) - 1;
          }
        }

        sh ft += b sPerPackedValue;
        sh ft = sh ft %  nteger.S ZE;
      }
    }
  }

  /********************************************************************
   * Getters of Pre-computed Values: returns should NEVER be mod f ed *
   ********************************************************************/

   nt[] getStart nt nd ces( nt numB sPerValue) {
    return allStart nt nd ces == null ? null : allStart nt nd ces[numB sPerValue];
  }

   nt[] getLowB sR ghtSh ft( nt numB sPerValue) {
    return allLowB sR ghtSh ft[numB sPerValue];
  }

   nt[] getM ddleB sLeftSh ft( nt numB sPerValue) {
    return allM ddleB sLeftSh ft[numB sPerValue];
  }

   nt[] getM ddleB sMask( nt numB sPerValue) {
    return allM ddleB sMask[numB sPerValue];
  }

   nt[] getH ghB sLeftSh ft( nt numB sPerValue) {
    return allH ghB sLeftSh ft == null ? null : allH ghB sLeftSh ft[numB sPerValue];
  }

   nt[] getH ghB sMask( nt numB sPerValue) {
    return allH ghB sMask == null ? null : allH ghB sMask[numB sPerValue];
  }
}
