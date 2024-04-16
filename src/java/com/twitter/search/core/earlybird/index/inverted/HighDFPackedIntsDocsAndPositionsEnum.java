package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

/**
 * Docs, frequenc es, and pos  ons enu rator for {@l nk H ghDFPacked ntsPost ngL sts}.
 */
publ c class H ghDFPacked ntsDocsAndPos  onsEnum extends H ghDFPacked ntsDocsEnum {
  /**
   * Pre-computed sh fts, masks, and start  nt  nd ces for {@l nk #pos  onL stsReader}.
   * T se pre-computed values should be read-only and shared across all reader threads.
   *
   * Not ce:
   * - start  nt  nd ces are NEEDED s nce t re  S jump ng w h n a sl ce  n
   *   {@l nk #doAdd  onalSk p()} and {@l nk #startCurrentDoc()}.
   */
  pr vate stat c f nal PackedLongsReaderPreComputedValues PRE_COMPUTED_VALUES =
      new PackedLongsReaderPreComputedValues(
          H ghDFPacked ntsPost ngL sts.MAX_POS T ON_B T,
          H ghDFPacked ntsPost ngL sts.POS T ON_SL CE_NUM_B TS_W THOUT_HEADER,
          H ghDFPacked ntsPost ngL sts.POS T ON_SL CE_S ZE_W THOUT_HEADER,
          true);

  /**
   *  nt block pool hold ng t  pos  ons for t  read post ng l st. T   s ma nly used wh le
   * read ng sl ce  aders  n {@l nk #loadNextPos  onSl ce()}.
   */
  pr vate f nal  ntBlockPool pos  onL sts;

  /** Packed  nts reader for pos  ons. */
  pr vate f nal  ntBlockPoolPackedLongsReader pos  onL stsReader;

  /** Total number of pos  ons  n t  current pos  on sl ce. */
  pr vate  nt numPos  ons nSl ceTotal;

  /**
   * Number of rema n ng pos  ons for {@l nk #currentDoc D}; t  value  s decre nted every t  
   * {@l nk #nextPos  on()}  s called.
   */
  pr vate  nt numPos  onsRema n ngForCurrentDoc D;

  /**
   * Po nter to t  f rst  nt, wh ch conta ns t  pos  on sl ce  ader, of t  next pos  on sl ce.
   * T  value  s used to track wh ch sl ce w ll be loaded w n {@l nk #loadNextPos  onSl ce()}  s
   * called.
   */
  pr vate  nt nextPos  onSl cePo nter;

  /**
   * Create a docs and pos  ons enu rator.
   */
  publ c H ghDFPacked ntsDocsAndPos  onsEnum(
       ntBlockPool sk pL sts,
       ntBlockPool deltaFreqL sts,
       ntBlockPool pos  onL sts,
       nt post ngL stPo nter,
       nt numPost ngs,
      boolean om Pos  ons) {
    super(sk pL sts, deltaFreqL sts, post ngL stPo nter, numPost ngs, om Pos  ons);

    t .pos  onL sts = pos  onL sts;
    t .pos  onL stsReader = new  ntBlockPoolPackedLongsReader(
        pos  onL sts,
        PRE_COMPUTED_VALUES,
        queryCostTracker,
        QueryCostTracker.CostType.LOAD_OPT M ZED_POST NG_BLOCK);

    // Load t  f rst pos  on sl ce.
    t .nextPos  onSl cePo nter = sk pL stReader.getPos  onCurrentSl cePo nter();
    loadNextPos  onSl ce();
  }

  /**
   * Prepare for current doc:
   * - sk pp ng over unread pos  ons for t  current doc.
   * - reset rema n ng pos  ons for current doc to {@l nk #currentFreq}.
   *
   * @see #nextDocNoDel()
   */
  @Overr de
  protected vo d startCurrentDoc() {
    // Locate next pos  on for current doc by sk pp ng over unread pos  ons from t  prev ous doc.
     f (numPos  onsRema n ngForCurrentDoc D != 0) {
       nt numPos  onsRema n ng nSl ce =
          numPos  ons nSl ceTotal - pos  onL stsReader.getPackedValue ndex();
      wh le (numPos  onsRema n ng nSl ce <= numPos  onsRema n ngForCurrentDoc D) {
        numPos  onsRema n ngForCurrentDoc D -= numPos  onsRema n ng nSl ce;
        nextPos  onSl cePo nter += H ghDFPacked ntsPost ngL sts.SL CE_S ZE;
        loadNextPos  onSl ce();
        numPos  onsRema n ng nSl ce = numPos  ons nSl ceTotal;
      }

      pos  onL stsReader.setPackedValue ndex(
          pos  onL stsReader.getPackedValue ndex() + numPos  onsRema n ngForCurrentDoc D);
    }

    // Number of rema n ng pos  ons for current doc  s current freq.
    numPos  onsRema n ngForCurrentDoc D = getCurrentFreq();
  }

  /**
   * Put pos  ons reader to t  start of next pos  on sl ce and reset number of b s per packed
   * value for next pos  on sl ce.
   */
  pr vate vo d loadNextPos  onSl ce() {
    f nal  nt  ader = pos  onL sts.get(nextPos  onSl cePo nter);
    f nal  nt b sForPos  on = H ghDFPacked ntsPost ngL sts.getNumB sForPos  on( ader);
    numPos  ons nSl ceTotal = H ghDFPacked ntsPost ngL sts.getNumPos  ons nSl ce( ader);

    pos  onL stsReader.jumpTo nt(
        nextPos  onSl cePo nter + H ghDFPacked ntsPost ngL sts.POS T ON_SL CE_HEADER_S ZE,
        b sForPos  on);
  }

  /**
   * Return next pos  on for current doc.
   * @see org.apac .lucene. ndex.Post ngsEnum#nextPos  on()
   */
  @Overr de
  publ c  nt nextPos  on() throws  OExcept on {
    // Return -1  m d ately  f all pos  ons are used up for current doc.
     f (numPos  onsRema n ngForCurrentDoc D == 0) {
      return -1;
    }

     f (pos  onL stsReader.getPackedValue ndex() < numPos  ons nSl ceTotal)  {
      // Read next pos  on  n current sl ce.
      f nal  nt nextPos  on = ( nt) pos  onL stsReader.readPackedLong();
      numPos  onsRema n ngForCurrentDoc D--;
      return nextPos  on;
    } else {
      // All pos  ons  n current sl ce  s used up, load next sl ce.
      nextPos  onSl cePo nter += H ghDFPacked ntsPost ngL sts.SL CE_S ZE;
      loadNextPos  onSl ce();
      return nextPos  on();
    }
  }

  /**
   * Set {@l nk #pos  onL stsReader} to t  correct locat on and correct number of b s per packed
   * value for t  delta-freq sl ce on wh ch t  enum  s landed after sk pp ng.
   *
   * @see #sk pTo( nt)
   */
  @Overr de
  protected vo d doAdd  onalSk p() {
    nextPos  onSl cePo nter = sk pL stReader.getPos  onCurrentSl cePo nter();
    loadNextPos  onSl ce();

    // Locate t  exact pos  on  n sl ce.
    f nal  nt sk pL stEntryEncoded tadata = sk pL stReader.getEncoded tadataCurrentSl ce();
    pos  onL stsReader.setPackedValue ndex(
        H ghDFPacked ntsPost ngL sts.getPos  onOffset nSl ce(sk pL stEntryEncoded tadata));
    numPos  onsRema n ngForCurrentDoc D = 0;
  }
}
