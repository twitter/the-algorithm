package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport org.apac .lucene.search.Doc dSet erator;

/**
 * A sk p l st reader of a s ngle term used {@l nk H ghDFPacked ntsDocsEnum}.
 * @see H ghDFPacked ntsPost ngL sts
 */
class H ghDFPacked ntsSk pL stReader {
  /** Sk p l sts  nt pool. */
  pr vate f nal  ntBlockPool sk pL sts;

  /** W t r pos  ons are om ted  n t  post ng l st hav ng t  read sk p l st. */
  pr vate f nal boolean om Pos  ons;

  /**
   * Last doc  n t  prev ous sl ce relat ve to t  current delta-freq sl ce. T  value  s 0  f
   * t  current sl ce  s t  f rst delta-freq sl ce.
   */
  pr vate  nt prev ousDoc DCurrentSl ce;

  /** Encoded  tadata of t  current delta-freq sl ce.*/
  pr vate  nt encoded tadataCurrentSl ce;

  /**
   * Po nter to t  f rst  nt (conta ns t  pos  on sl ce  ader) of t  pos  on sl ce that has
   * t  f rst pos  on of t  f rst doc  n t  current delta-freq sl ce.
   */
  pr vate  nt pos  onCurrentSl ce ndex;

  /** Po nter to t  f rst  nt  n t  current delta-freq sl ce. */
  pr vate  nt deltaFreqCurrentSl cePo nter;

  /** Data of next sl ce. */
  pr vate  nt prev ousDoc DNextSl ce;
  pr vate  nt encoded tadataNextSl ce;
  pr vate  nt pos  onNextSl ce ndex;
  pr vate  nt deltaFreqNextSl cePo nter;

  /** Used to load blocks and read  nts from sk p l sts  nt pool. */
  pr vate  nt[] currentSk pL stBlock;
  pr vate  nt sk pL stBlockStart;
  pr vate  nt sk pL stBlock ndex;

  /** Number of rema n ng sk p entr es for t  read sk p l st. */
  pr vate  nt numSk pL stEntr esRema n ng;

  /** Largest doc  D  n t  post ng l st hav ng t  read sk p l st. */
  pr vate f nal  nt largestDoc D;

  /** Po nter to t  f rst  nt  n t  f rst sl ce that stores pos  ons for t  term. */
  pr vate f nal  nt pos  onL stPo nter;

  /** Total number of docs  n t  post ng l st hav ng t  read sk p l st. */
  pr vate f nal  nt numDocsTotal;

  /**
   * Create a sk p l st reader spec f ed by t  g ven sk p l st po nter  n t  g ven sk p l sts  nt
   * pool.
   *
   * @param sk pL sts  nt pool w re t  read sk p l st ex sts
   * @param sk pL stPo nter po nter to t  read sk p l st
   * @param om Pos  ons w t r pos  ons are om ted  n t  pos  ng l st to wh ch t  read sk p
   *                      l st belongs
   */
  publ c H ghDFPacked ntsSk pL stReader(
      f nal  ntBlockPool sk pL sts,
      f nal  nt sk pL stPo nter,
      f nal boolean om Pos  ons) {
    t .sk pL sts = sk pL sts;
    t .om Pos  ons = om Pos  ons;

    t .sk pL stBlockStart =  ntBlockPool.getBlockStart(sk pL stPo nter);
    t .sk pL stBlock ndex =  ntBlockPool.getOffset nBlock(sk pL stPo nter);
    t .currentSk pL stBlock = sk pL sts.getBlock(sk pL stBlockStart);

    // Read sk p l st  ader.
    t .numSk pL stEntr esRema n ng = readNextValueFromSk pL stBlock();
    t .largestDoc D = readNextValueFromSk pL stBlock();
    t .numDocsTotal = readNextValueFromSk pL stBlock();
     nt deltaFreqL stPo nter = readNextValueFromSk pL stBlock();
    t .pos  onL stPo nter = om Pos  ons ? -1 : readNextValueFromSk pL stBlock();

    // Set   back by one sl ce for fetchNextSk pEntry() to advance correctly.
    t .deltaFreqNextSl cePo nter = deltaFreqL stPo nter - H ghDFPacked ntsPost ngL sts.SL CE_S ZE;
    fetchNextSk pEntry();
  }

  /**
   * Load already fetc d data  n next sk p entry  nto current data var ables, and pre-fetch aga n.
   */
  publ c vo d getNextSk pEntry() {
    prev ousDoc DCurrentSl ce = prev ousDoc DNextSl ce;
    encoded tadataCurrentSl ce = encoded tadataNextSl ce;
    pos  onCurrentSl ce ndex = pos  onNextSl ce ndex;
    deltaFreqCurrentSl cePo nter = deltaFreqNextSl cePo nter;
    fetchNextSk pEntry();
  }

  /**
   * Fetch data for next sk p entry  f sk p l st  s not exhausted; ot rw se, set doc DNextSl ce
   * to NO_MORE_DOCS.
   */
  pr vate vo d fetchNextSk pEntry() {
     f (numSk pL stEntr esRema n ng == 0) {
      prev ousDoc DNextSl ce = Doc dSet erator.NO_MORE_DOCS;
      return;
    }

    prev ousDoc DNextSl ce = readNextValueFromSk pL stBlock();
    encoded tadataNextSl ce = readNextValueFromSk pL stBlock();
     f (!om Pos  ons) {
      pos  onNextSl ce ndex = readNextValueFromSk pL stBlock();
    }
    deltaFreqNextSl cePo nter += H ghDFPacked ntsPost ngL sts.SL CE_S ZE;
    numSk pL stEntr esRema n ng--;
  }

  /**************************************
   * Getters of data  n sk p l st entry *
   **************************************/

  /**
   *  n t  context of a current sl ce, t   s t  doc D of t  last docu nt  n t  prev ous
   * sl ce (or 0  f t  current sl ce  s t  f rst sl ce).
   *
   * @see H ghDFPacked ntsPost ngL sts#SK PL ST_ENTRY_S ZE
   */
  publ c  nt getPrev ousDoc DCurrentSl ce() {
    return prev ousDoc DCurrentSl ce;
  }

  /**
   * Get t  encoded  tadata of t  current delta-freq sl ce.
   *
   * @see H ghDFPacked ntsPost ngL sts#SK PL ST_ENTRY_S ZE
   */
  publ c  nt getEncoded tadataCurrentSl ce() {
    return encoded tadataCurrentSl ce;
  }

  /**
   * Get t  po nter to t  f rst  nt, WH CH CONTA NS THE POS T ON SL CE HEADER, of t  pos  on
   * sl ce that conta ns t  f rst pos  on of t  f rst doc  n t  delta-freq sl ce that
   *  s correspond ng to t  current sk p l st entry.
   *
   * @see H ghDFPacked ntsPost ngL sts#SK PL ST_ENTRY_S ZE
   */
  publ c  nt getPos  onCurrentSl cePo nter() {
    assert !om Pos  ons;
    return pos  onL stPo nter
        + pos  onCurrentSl ce ndex * H ghDFPacked ntsPost ngL sts.SL CE_S ZE;
  }

  /**
   * Get t  po nter to t  f rst  nt  n t  current delta-freq sl ce.
   */
  publ c  nt getDeltaFreqCurrentSl cePo nter() {
    return deltaFreqCurrentSl cePo nter;
  }

  /**
   *  n t  context of next sl ce, get t  last doc  D  n t  prev ous sl ce. T   s used to sk p
   * over sl ces.
   *
   * @see H ghDFPacked ntsDocsEnum#sk pTo( nt)
   */
  publ c  nt peekPrev ousDoc DNextSl ce() {
    return prev ousDoc DNextSl ce;
  }

  /***************************************
   * Getters of data  n sk p l st  ader *
   ***************************************/

  publ c  nt getLargestDoc D() {
    return largestDoc D;
  }

  publ c  nt getNumDocsTotal() {
    return numDocsTotal;
  }

  /***************************************************
   *  thods  lp ng load ng  nt block and read  nts *
   ***************************************************/

  pr vate  nt readNextValueFromSk pL stBlock() {
     f (sk pL stBlock ndex ==  ntBlockPool.BLOCK_S ZE) {
      loadSk pL stBlock();
    }
    return currentSk pL stBlock[sk pL stBlock ndex++];
  }

  pr vate vo d loadSk pL stBlock() {
    sk pL stBlockStart +=  ntBlockPool.BLOCK_S ZE;
    currentSk pL stBlock = sk pL sts.getBlock(sk pL stBlockStart);
    sk pL stBlock ndex = 0;
  }
}
