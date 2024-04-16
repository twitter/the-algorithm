package com.tw ter.search.core.earlyb rd. ndex. nverted;

/**
 * A packed  nts wr er wr  ng packed values ( nt/long)  nto {@l nk  ntBlockPool}.
 * @see  ntBlockPoolPackedLongsReader
 *
 * A standard useage would be:
 * - set wr er at an  nt block pool po nter and number of b s per packed value:
 *   {@l nk #jumpTo nt( nt,  nt)}
 * - wr e: {@l nk #wr ePacked nt( nt)} or {@l nk #wr ePackedLong(long)}
 *
 * Example usage:
 * @see H ghDFPacked ntsPost ngL sts
 */
publ c f nal class  ntBlockPoolPackedLongsWr er {
  /**
   * Mask used to convert an  nt to a long.   cannot just cast because   w ll f ll  n t  h g r
   * 32 b s w h t  s gn b , but   need t  h g r 32 b s to be 0  nstead.
   */
  pr vate stat c f nal long LONG_MASK = 0xFFFFFFFFL;

  /** T   nt block pool  nto wh ch packed  nts w ll be wr ten. */
  pr vate f nal  ntBlockPool  ntBlockPool;

  /** T  value  n t  current pos  on  n t   nt block pool. */
  pr vate  nt current ntValue = 0;

  /** Start ng b   ndex of unused b s  n {@l nk #current ntValue}. */
  pr vate  nt current ntB  ndex = 0;

  /** Po nter of {@l nk #current ntValue}  n {@l nk # ntBlockPool}. */
  pr vate  nt current ntPo nter = -1;

  /**
   * Number of b s per packed value that w ll be wr ten w h
   * {@l nk #wr ePacked nt( nt)} or {@l nk #wr ePackedLong(long)}.
   */
  pr vate  nt numB sPerPackedValue = -1;

  /**
   * Mask used to extract t  lo r {@l nk #numB sPerPackedValue}  n a g ven value.
   */
  pr vate long packedValueB sMask = 0;

  /**
   * Sole constructor.
   *
   * @param  ntBlockPool  nto wh ch packed  nts w ll be wr ten
   */
  publ c  ntBlockPoolPackedLongsWr er( ntBlockPool  ntBlockPool) {
    t . ntBlockPool =  ntBlockPool;
  }

  /**
   * 1. Set t  wr er to start wr  ng at t  g ven  nt block pool po nter.
   * 2. Set number of b s per packed value that w ll be wr e.
   * 3. Re-set {@l nk #current ntValue} and {@l nk #current ntB  ndex} to 0.
   *
   * @param  ntBlockPoolPo nter t  pos  on t  wr er should start wr  ng packed values. T 
   *                            po nter must be less t n or equal to   length of t  block pool.
   *                            Subsequent wr es w ll {@l nk  ntBlockPool#add( nt)} to t 
   *                            end of t   nt block pool  f t  g ven po nter equals to t  length.
   * @param b sPerPackedValue must be non-negat ve.
   */
  publ c vo d jumpTo nt( nt  ntBlockPoolPo nter,  nt b sPerPackedValue) {
    assert  ntBlockPoolPo nter <=  ntBlockPool.length();
    assert b sPerPackedValue >= 0;

    // Set t  wr er to start wr  ng at t  g ven  nt block pool po nter.
    t .current ntPo nter =  ntBlockPoolPo nter;

    // Set number of b s that w ll be wr e per packed value.
    t .numB sPerPackedValue = b sPerPackedValue;

    // Compute t  mask used to extract lo r number of b sPerPackedValue.
    t .packedValueB sMask =
        b sPerPackedValue == Long.S ZE ? -1L : (1L << b sPerPackedValue) - 1;

    // Reset current  nt data to 0.
    t .current ntValue = 0;
    t .current ntB  ndex = 0;
  }

  /**
   * T  g ven  nt value w ll be ZERO extended to a long and wr ten us ng
   * {@l nk #wr ePackedValue nternal(long)} (long)}.
   *
   * @see #LONG_MASK
   */
  publ c vo d wr ePacked nt(f nal  nt value) {
    assert numB sPerPackedValue <=  nteger.S ZE;
    wr ePackedValue nternal(LONG_MASK & value);
  }

  /**
   * Wr e a long value.
   * T  g ven long value must bu UNABLE to f   n an  nt.
   */
  publ c vo d wr ePackedLong(f nal long value) {
    assert numB sPerPackedValue <= Long.S ZE;
    wr ePackedValue nternal(value);
  }

  /*************************
   * Pr vate  lper  thod *
   *************************/

  /**
   * Wr e t  g ven number of b s of t  g ven value  nto t   nt pool as a packed  nt.
   *
   * @param value value w ll be wr ten
   */
  pr vate vo d wr ePackedValue nternal(f nal long value) {
    // Extract lo r 'numB sPerPackedValue' from t  g ven value.
    long val = value & packedValueB sMask;

    assert val == value : Str ng.format(
        "g ven value %d needs more b s than spec f ed %d", value, numB sPerPackedValue);

     nt numB sWr tenCur er;
     nt numB sRema n ng = numB sPerPackedValue;

    // Each  erat on of t  wh le loop  s wr  ng part of t  g ven value.
    wh le (numB sRema n ng > 0) {
      // Wr e  nto 'current ntValue'  nt.
      current ntValue |= val << current ntB  ndex;

      // Calculate number of b s have been wr ten  n t   erat on,
      //   e  r used up all t  rema n ng b s  n 'current ntValue' or
      // f n s d up wr  ng t  value, wh c ver  s smaller.
      numB sWr tenCur er = Math.m n( nteger.S ZE - current ntB  ndex, numB sRema n ng);

      // Number of b s rema n ng should be decre nted.
      numB sRema n ng -= numB sWr tenCur er;

      // R ght sh ft t  value to remove t  b s have been wr ten.
      val >>>= numB sWr tenCur er;

      // Update b   ndex  n current  nt.
      current ntB  ndex += numB sWr tenCur er;
      assert current ntB  ndex <=  nteger.S ZE;

      flush();

      //  f 'current ntValue'  nt  s used up.
       f (current ntB  ndex ==  nteger.S ZE) {
        current ntPo nter++;

        current ntValue = 0;
        current ntB  ndex = 0;
      }
    }
  }

  /**
   * Flush t  {@l nk #current ntValue}  nt  nto t   nt pool  f t  any b s of t   nt are used.
   */
  pr vate vo d flush() {
     f (current ntPo nter ==  ntBlockPool.length()) {
       ntBlockPool.add(current ntValue);
      assert current ntPo nter + 1 ==  ntBlockPool.length();
    } else {
       ntBlockPool.set(current ntPo nter, current ntValue);
    }
  }
}
