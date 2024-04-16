package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport javax.annotat on.Nullable;

/**
 * A packed  nts reader read ng packed values ( nt/long) wr ten  n {@l nk  ntBlockPool}.
 * @see  ntBlockPoolPackedLongsWr er
 *
 * A standard usage would be :
 * - set reader at an  nt block pool po nter and number of b s per packed value:
 *   {@l nk #jumpTo nt( nt,  nt)}}
 * - read: {@l nk #readPackedLong()}
 *
 * Example usage:
 * @see H ghDFPacked ntsDocsEnum
 * @see H ghDFPacked ntsDocsAndPos  onsEnum
 */
publ c f nal class  ntBlockPoolPackedLongsReader {
  /**
   * Mask used to convert an  nt to a long.   cannot just cast because   w ll f ll  n t  h g r
   * 32 b s w h t  s gn b , but   need t  h g r 32 b s to be 0  nstead.
   */
  pr vate stat c f nal long LONG_MASK = 0xFFFFFFFFL;

  /** T   nt block pool from wh ch packed  nts w ll be read. */
  pr vate f nal  ntBlockPool  ntBlockPool;

  /** Pre-computed sh fts, masks, and start  nt  nd ces used to decode packed  nts. */
  pr vate f nal PackedLongsReaderPreComputedValues preComputedValues;

  /**
   * T  underly ng {@l nk # ntBlockPool} w ll be read block by blocks. T  current read
   * block w ll be  dent f ed by {@l nk #startPo nterForCurrentBlock} and ass gned to
   * {@l nk #currentBlock}. {@l nk # ndex nCurrentBlock} w ll be used access values from t 
   * {@l nk #currentBlock}.
   */
  pr vate  nt[] currentBlock;
  pr vate  nt  ndex nCurrentBlock;
  pr vate  nt startPo nterForCurrentBlock = -1;

  /**
   * W t r t  decoded packed values are spann ng more than 1  nt.
   * @see #readPackedLong()
   */
  pr vate boolean packedValueNeedsLong;

  /**
   * Masks used to extract packed values.
   * @see #readPackedLong()
   */
  pr vate long packedValueMask;

  /** PRE-COMPUTED: T   ndex of t  f rst  nt that has a spec f c packed values. */
  pr vate  nt[] packedValueStart nd ces;

  /** PRE-COMPUTED: T  sh fts and masks used to decode packed values. */
  pr vate  nt[] packedValueLowB sR ghtSh ft;
  pr vate  nt[] packedValueM ddleB sLeftSh ft;
  pr vate  nt[] packedValueM ddleB sMask;
  pr vate  nt[] packedValueH ghB sLeftSh ft;
  pr vate  nt[] packedValueH ghB sMask;

  /**  ndex of packed values. */
  pr vate  nt packedValue ndex;

  /**
   * T  {@l nk # ndex nCurrentBlock} and {@l nk #startPo nterForCurrentBlock} of t  f rst  nt
   * that holds packed values. T  two values toget r un quely form a  nt block pool po nter
   * --- {@l nk #packedValueStartBlockStart} + {@l nk #packedValueStartBlock ndex} --- that po nts
   * to t  f rst  nt that has po nter.
   *
   * @see #jumpTo nt( nt,  nt)
   */
  pr vate  nt packedValueStartBlock ndex;
  pr vate  nt packedValueStartBlockStart;

  /** Current  nt read from {@l nk #currentBlock}. */
  pr vate  nt current nt;

  /**
   *  f g ven, query cost w ll be tracked every t   a  nt block  s loaded.
   * @see #loadNextBlock()
   */
  pr vate f nal QueryCostTracker queryCostTracker;
  pr vate f nal QueryCostTracker.CostType queryCostType;

  /**
   * Default constructor.
   *
   * @param  ntBlockPool from wh ch packed  nts w ll be read
   * @param preComputedValues pre-computed sh fts, masks, and start  nt
   * @param queryCostTracker opt onal, query cost tracker used wh le load ng a new block
   * @param queryCostType opt onal, query cost type w ll be tracked wh le load ng a new block
   */
  publ c  ntBlockPoolPackedLongsReader(
       ntBlockPool  ntBlockPool,
      PackedLongsReaderPreComputedValues preComputedValues,
      @Nullable QueryCostTracker queryCostTracker,
      @Nullable QueryCostTracker.CostType queryCostType) {
    t . ntBlockPool =  ntBlockPool;
    t .preComputedValues = preComputedValues;

    // For query cost track ng.
    t .queryCostTracker = queryCostTracker;
    t .queryCostType = queryCostType;
  }

  /**
   * Constructor w h {@l nk #queryCostTracker} and {@l nk #queryCostType} set to null.
   *
   * @param  ntBlockPool from wh ch packed  nts w ll be read
   * @param preComputedValues pre-computed sh fts, masks, and start  nt
   */
  publ c  ntBlockPoolPackedLongsReader(
       ntBlockPool  ntBlockPool,
      PackedLongsReaderPreComputedValues preComputedValues) {
    t ( ntBlockPool, preComputedValues, null, null);
  }

  /**
   * 1. Set t  reader to start ng read ng at t  g ven  nt block pool po nter. Correct block w ll
   *    be loaded  f t  g ven po nter po nts to t  d fferent block than {@l nk #currentBlock}.
   * 2. Update sh fts, masks, and start  nt  nd ces based on g ven number of b s per packed value.
   * 3. Reset packed value sequence start data.
   *
   * @param  ntBlockPoolPo nter po nts to t   nt from wh ch t  reader w ll start read ng
   * @param b sPerPackedValue number of b s per packed value.
   */
  publ c vo d jumpTo nt( nt  ntBlockPoolPo nter,  nt b sPerPackedValue) {
    assert  b sPerPackedValue <= Long.S ZE;

    // Update  ndex nCurrentBlock and load a d fferent  ndex  f needed.
     nt newBlockStart =  ntBlockPool.getBlockStart( ntBlockPoolPo nter);
     ndex nCurrentBlock =  ntBlockPool.getOffset nBlock( ntBlockPoolPo nter);

     f (startPo nterForCurrentBlock != newBlockStart) {
      startPo nterForCurrentBlock = newBlockStart;
      loadNextBlock();
    }

    // Re-set sh fts, masks, and start  nt  nd ces for t  g ven number b s per packed value.
    packedValueNeedsLong = b sPerPackedValue >  nteger.S ZE;
    packedValueMask =
        b sPerPackedValue == Long.S ZE ? 0xFFFFFFFFFFFFFFFFL : (1L << b sPerPackedValue) - 1;
    packedValueStart nd ces = preComputedValues.getStart nt nd ces(b sPerPackedValue);
    packedValueLowB sR ghtSh ft = preComputedValues.getLowB sR ghtSh ft(b sPerPackedValue);
    packedValueM ddleB sLeftSh ft = preComputedValues.getM ddleB sLeftSh ft(b sPerPackedValue);
    packedValueM ddleB sMask = preComputedValues.getM ddleB sMask(b sPerPackedValue);
    packedValueH ghB sLeftSh ft = preComputedValues.getH ghB sLeftSh ft(b sPerPackedValue);
    packedValueH ghB sMask = preComputedValues.getH ghB sMask(b sPerPackedValue);

    // Update packed values sequence start data.
    packedValue ndex = 0;
    packedValueStartBlock ndex =  ndex nCurrentBlock;
    packedValueStartBlockStart = startPo nterForCurrentBlock;

    // Load an  nt to prepare for readPackedLong.
    load nt();
  }

  /**
   * Read next packed value as a long.
   *
   * Caller could cast t  returned long to an  nt  f needed.
   * NOT CE! Be careful of overflow wh le cast ng a long to an  nt.
   *
   * @return next packed value  n a long.
   */
  publ c long readPackedLong() {
    long packedValue;

     f (packedValueNeedsLong) {
      packedValue =
          (LONG_MASK & current nt)
              >>> packedValueLowB sR ghtSh ft[packedValue ndex] & packedValueMask;
      packedValue |=
          (LONG_MASK & load nt()
              & packedValueM ddleB sMask[packedValue ndex])
              << packedValueM ddleB sLeftSh ft[packedValue ndex];
       f (packedValueH ghB sLeftSh ft[packedValue ndex] != 0) {
        packedValue |=
            (LONG_MASK & load nt()
                & packedValueH ghB sMask[packedValue ndex])
                << packedValueH ghB sLeftSh ft[packedValue ndex];
      }
    } else {
      packedValue =
          current nt >>> packedValueLowB sR ghtSh ft[packedValue ndex] & packedValueMask;
       f (packedValueM ddleB sLeftSh ft[packedValue ndex] != 0) {
        packedValue |=
            (load nt()
                & packedValueM ddleB sMask[packedValue ndex])
                << packedValueM ddleB sLeftSh ft[packedValue ndex];
      }
    }

    packedValue ndex++;
    return packedValue;
  }

  /**
   * A s mple getter of {@l nk #packedValue ndex}.
   */
  publ c  nt getPackedValue ndex() {
    return packedValue ndex;
  }

  /**
   * A setter of {@l nk #packedValue ndex}. T  setter w ll also set t  correct
   * {@l nk # ndex nCurrentBlock} based on {@l nk #packedValueStart nd ces}.
   */
  publ c vo d setPackedValue ndex( nt packedValue ndex) {
    t .packedValue ndex = packedValue ndex;
    t . ndex nCurrentBlock =
        packedValueStartBlock ndex + packedValueStart nd ces[packedValue ndex];
    t .startPo nterForCurrentBlock = packedValueStartBlockStart;
    load nt();
  }

  /**************************
   * Pr vate  lper  thods *
   **************************/

  /**
   * Load a new  nt block, spec f ed by {@l nk #startPo nterForCurrentBlock}, from
   * {@l nk # ntBlockPool}.  f {@l nk #queryCostTracker}  s g ven, query cost w h type
   * {@l nk #queryCostType} w ll be tracked as  ll.
   */
  pr vate vo d loadNextBlock() {
     f (queryCostTracker != null) {
      assert queryCostType != null;
      queryCostTracker.track(queryCostType);
    }

    currentBlock =  ntBlockPool.getBlock(startPo nterForCurrentBlock);
  }

  /**
   * Load an  nt from {@l nk #currentBlock}. T  loaded  nt w ll be returned as  ll.
   *  f t  {@l nk #currentBlock}  s used up, next block w ll be automat cally loaded.
   */
  pr vate  nt load nt() {
    wh le ( ndex nCurrentBlock >=  ntBlockPool.BLOCK_S ZE) {
      startPo nterForCurrentBlock +=  ntBlockPool.BLOCK_S ZE;
      loadNextBlock();

       ndex nCurrentBlock = Math.max( ndex nCurrentBlock -  ntBlockPool.BLOCK_S ZE, 0);
    }

    current nt = currentBlock[ ndex nCurrentBlock++];
    return current nt;
  }
}
