package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

/**
 * Docs and frequenc es enu rator for {@l nk H ghDFPacked ntsPost ngL sts}.
 */
publ c class H ghDFPacked ntsDocsEnum extends Earlyb rdOpt m zedPost ngsEnum {
  /**
   * Pre-computed sh fts, masks for {@l nk #deltaFreqL stsReader}.
   * T se pre-computed values should be read-only and shared across all reader threads.
   *
   * Not ce:
   * - start  nt  nd ces are NOT needed s nce t re  s not jump ng w h n a sl ce.
   */
  pr vate stat c f nal PackedLongsReaderPreComputedValues PRE_COMPUTED_VALUES =
      new PackedLongsReaderPreComputedValues(
          H ghDFPacked ntsPost ngL sts.MAX_DOC_ D_B T
              + H ghDFPacked ntsPost ngL sts.MAX_FREQ_B T,
          H ghDFPacked ntsPost ngL sts.NUM_B TS_PER_SL CE,
          H ghDFPacked ntsPost ngL sts.SL CE_S ZE,
          false);

  /** Packed  nts reader for delta-freq pa rs. */
  pr vate f nal  ntBlockPoolPackedLongsReader deltaFreqL stsReader;

  /** Sk p l st reader. */
  protected f nal H ghDFPacked ntsSk pL stReader sk pL stReader;

  /** Number of rema n ng docs (delta-freq pa rs)  n a sl ce. */
  pr vate  nt numDocsRema n ng;

  /**
   * Total number of docs (delta-freq pa rs)  n a sl ce.
   * T  value  s set every t   a sl ce  s loaded  n {@l nk #loadNextDeltaFreqSl ce()}.
   */
  pr vate  nt numDocs nSl ceTotal;

  /**
   * Number of b s used for frequency  n a delta-freq sl ce.
   * T  value  s set every t   a sl ce  s loaded  n {@l nk #loadNextDeltaFreqSl ce()}.
   */
  pr vate  nt b sForFreq;

  /**
   * Frequency mask used to extract frequency from a delta-freq pa r,  n a delta-freq sl ce.
   * T  value  s set every t   a sl ce  s loaded  n {@l nk #loadNextDeltaFreqSl ce()}.
   */
  pr vate  nt freqMask;
  pr vate boolean freqB s sZero;

  /**
   * Sole constructor.
   *
   * @param sk pL sts sk p l sts  nt block pool
   * @param deltaFreqL sts delta-freq l sts  nt block pool
   * @param post ngL stPo nter po nter to t  post ng l st for wh ch t  enu rator  s created
   * @param numPost ngs number of post ngs  n t  post ng l st for wh ch t  enu rator  s created
   * @param om Pos  ons w t r pos  ons are om ted  n t  post ng l st of wh ch t  enu rator
   *                       s created
   */
  publ c H ghDFPacked ntsDocsEnum(
       ntBlockPool sk pL sts,
       ntBlockPool deltaFreqL sts,
       nt post ngL stPo nter,
       nt numPost ngs,
      boolean om Pos  ons) {
    super(post ngL stPo nter, numPost ngs);

    // Create sk p l st reader and get f rst sk p entry.
    t .sk pL stReader = new H ghDFPacked ntsSk pL stReader(
        sk pL sts, post ngL stPo nter, om Pos  ons);
    t .sk pL stReader.getNextSk pEntry();

    // Set number of rema n ng docs  n t  post ng l st.
    t .numDocsRema n ng = sk pL stReader.getNumDocsTotal();

    // Create a delta-freq pa r packed values reader.
    t .deltaFreqL stsReader = new  ntBlockPoolPackedLongsReader(
        deltaFreqL sts,
        PRE_COMPUTED_VALUES,
        queryCostTracker,
        QueryCostTracker.CostType.LOAD_OPT M ZED_POST NG_BLOCK);

    loadNextDeltaFreqSl ce();
    loadNextPost ng();
  }

  /**
   * Load next delta-freq sl ce, return false  f all docs exhausted.
   * Not ce!! T  caller of t   thod should make sure t  current sl ce  s all used up and
   * {@l nk #numDocsRema n ng}  s updated accord ngly.
   *
   * @return w t r a sl ce  s loaded.
   * @see #loadNextPost ng()
   * @see #sk pTo( nt)
   */
  pr vate boolean loadNextDeltaFreqSl ce() {
    // Load noth ng  f no docs are rema n ng.
     f (numDocsRema n ng == 0) {
      return false;
    }

    f nal  nt encoded tadata = sk pL stReader.getEncoded tadataCurrentSl ce();
    f nal  nt b sForDelta = H ghDFPacked ntsPost ngL sts.getNumB sForDelta(encoded tadata);
    b sForFreq = H ghDFPacked ntsPost ngL sts.getNumB sForFreq(encoded tadata);
    numDocs nSl ceTotal = H ghDFPacked ntsPost ngL sts.getNumDocs nSl ce(encoded tadata);

    freqMask = (1 << b sForFreq) - 1;
    freqB s sZero = b sForFreq == 0;

    // Locate and reset t  reader for t  sl ce.
    f nal  nt b sPerPackedValue = b sForDelta + b sForFreq;
    deltaFreqL stsReader.jumpTo nt(
        sk pL stReader.getDeltaFreqCurrentSl cePo nter(), b sPerPackedValue);
    return true;
  }

  /**
   * Load next delta-freq pa r from t  current sl ce and set t  computed
   * {@l nk #nextDoc D} and {@l nk #nextFreq}.
   */
  @Overr de
  protected f nal vo d loadNextPost ng() {
    assert numDocsRema n ng >= (numDocs nSl ceTotal - deltaFreqL stsReader.getPackedValue ndex())
        : "numDocsRema n ng should be equal to or greater than number of docs rema n ng  n sl ce";

     f (deltaFreqL stsReader.getPackedValue ndex() < numDocs nSl ceTotal) {
      // Current sl ce  s not exhausted.
      f nal long nextDeltaFreqPa r = deltaFreqL stsReader.readPackedLong();

      /**
       * Opt m zat on: No need to do sh fts and masks  f number of b s for frequency  s 0.
       * Also, t  stored frequency  s t  actual frequency - 1.
       * @see
       * H ghDFPacked ntsPost ngL sts#copyPost ngL st(org.apac .lucene. ndex.Post ngsEnum,  nt)
       */
       f (freqB s sZero) {
        nextFreq = 1;
        nextDoc D += ( nt) nextDeltaFreqPa r;
      } else {
        nextFreq = ( nt) ((nextDeltaFreqPa r & freqMask) + 1);
        nextDoc D += ( nt) (nextDeltaFreqPa r >>> b sForFreq);
      }

      numDocsRema n ng--;
    } else {
      // Current sl ce  s exhausted, get next sk p entry and load next sl ce.
      sk pL stReader.getNextSk pEntry();
       f (loadNextDeltaFreqSl ce()) {
        // Next sl ce  s loaded, load next post ng aga n.
        loadNextPost ng();
      } else {
        // All docs are exhausted, mark t  enu rator as exhausted.
        assert numDocsRema n ng == 0;
        nextDoc D = NO_MORE_DOCS;
        nextFreq = 0;
      }
    }
  }

  /**
   * Sk p over sl ces to approach t  g ven target as close as poss ble.
   */
  @Overr de
  protected f nal vo d sk pTo( nt target) {
    assert target != NO_MORE_DOCS : "Should be handled  n parent class advance  thod";

     nt numSl cesToSk p = 0;
     nt numDocsToSk p = 0;
     nt numDocsRema n ng nSl ce = numDocs nSl ceTotal - deltaFreqL stsReader.getPackedValue ndex();

    // Sk pp ng over sl ces.
    wh le (sk pL stReader.peekPrev ousDoc DNextSl ce() < target) {
      sk pL stReader.getNextSk pEntry();
      nextDoc D = sk pL stReader.getPrev ousDoc DCurrentSl ce();
      numDocsToSk p += numDocsRema n ng nSl ce;
       nt  ader = sk pL stReader.getEncoded tadataCurrentSl ce();
      numDocsRema n ng nSl ce = H ghDFPacked ntsPost ngL sts.getNumDocs nSl ce( ader);

      numSl cesToSk p++;
    }

    //  f sk pped any sl ces, load t  new sl ce.
     f (numSl cesToSk p > 0) {
      numDocsRema n ng -= numDocsToSk p;
      f nal boolean hasNextSl ce = loadNextDeltaFreqSl ce();
      assert hasNextSl ce;
      assert numDocsRema n ng >= numDocs nSl ceTotal && numDocs nSl ceTotal > 0;

      // Do add  onal sk p for t  delta freq sl ce that was just loaded.
      doAdd  onalSk p();

      loadNextPost ng();
    }
  }

  /**
   * Subclass should overr de t   thod  f want to do add  onal sk p on  s data structure.
   */
  protected vo d doAdd  onalSk p() {
    // No-op  n t  class.
  }

  /**
   * Get t  largest doc  D from {@l nk #sk pL stReader}.
   */
  @Overr de
  publ c  nt getLargestDoc D() throws  OExcept on {
    return sk pL stReader.getLargestDoc D();
  }

  /**
   * Return {@l nk #numDocsRema n ng} as a proxy of cost.
   *
   * @see org.apac .lucene. ndex.Post ngsEnum#cost()
   */
  @Overr de
  publ c long cost() {
    return numDocsRema n ng;
  }
}
