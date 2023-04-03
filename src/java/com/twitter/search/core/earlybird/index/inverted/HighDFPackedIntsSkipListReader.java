packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

/**
 * A skip list relonadelonr of a singlelon telonrm uselond {@link HighDFPackelondIntsDocselonnum}.
 * @selonelon HighDFPackelondIntsPostingLists
 */
class HighDFPackelondIntsSkipListRelonadelonr {
  /** Skip lists int pool. */
  privatelon final IntBlockPool skipLists;

  /** Whelonthelonr positions arelon omittelond in thelon posting list having thelon relonad skip list. */
  privatelon final boolelonan omitPositions;

  /**
   * Last doc in thelon prelonvious slicelon relonlativelon to thelon currelonnt delonlta-frelonq slicelon. This valuelon is 0 if
   * thelon currelonnt slicelon is thelon first delonlta-frelonq slicelon.
   */
  privatelon int prelonviousDocIDCurrelonntSlicelon;

  /** elonncodelond melontadata of thelon currelonnt delonlta-frelonq slicelon.*/
  privatelon int elonncodelondMelontadataCurrelonntSlicelon;

  /**
   * Pointelonr to thelon first int (contains thelon position slicelon helonadelonr) of thelon position slicelon that has
   * thelon first position of thelon first doc in thelon currelonnt delonlta-frelonq slicelon.
   */
  privatelon int positionCurrelonntSlicelonIndelonx;

  /** Pointelonr to thelon first int in thelon currelonnt delonlta-frelonq slicelon. */
  privatelon int delonltaFrelonqCurrelonntSlicelonPointelonr;

  /** Data of nelonxt slicelon. */
  privatelon int prelonviousDocIDNelonxtSlicelon;
  privatelon int elonncodelondMelontadataNelonxtSlicelon;
  privatelon int positionNelonxtSlicelonIndelonx;
  privatelon int delonltaFrelonqNelonxtSlicelonPointelonr;

  /** Uselond to load blocks and relonad ints from skip lists int pool. */
  privatelon int[] currelonntSkipListBlock;
  privatelon int skipListBlockStart;
  privatelon int skipListBlockIndelonx;

  /** Numbelonr of relonmaining skip elonntrielons for thelon relonad skip list. */
  privatelon int numSkipListelonntrielonsRelonmaining;

  /** Largelonst doc ID in thelon posting list having thelon relonad skip list. */
  privatelon final int largelonstDocID;

  /** Pointelonr to thelon first int in thelon first slicelon that storelons positions for this telonrm. */
  privatelon final int positionListPointelonr;

  /** Total numbelonr of docs in thelon posting list having thelon relonad skip list. */
  privatelon final int numDocsTotal;

  /**
   * Crelonatelon a skip list relonadelonr speloncifielond by thelon givelonn skip list pointelonr in thelon givelonn skip lists int
   * pool.
   *
   * @param skipLists int pool whelonrelon thelon relonad skip list elonxists
   * @param skipListPointelonr pointelonr to thelon relonad skip list
   * @param omitPositions whelonthelonr positions arelon omittelond in thelon positing list to which thelon relonad skip
   *                      list belonlongs
   */
  public HighDFPackelondIntsSkipListRelonadelonr(
      final IntBlockPool skipLists,
      final int skipListPointelonr,
      final boolelonan omitPositions) {
    this.skipLists = skipLists;
    this.omitPositions = omitPositions;

    this.skipListBlockStart = IntBlockPool.gelontBlockStart(skipListPointelonr);
    this.skipListBlockIndelonx = IntBlockPool.gelontOffselontInBlock(skipListPointelonr);
    this.currelonntSkipListBlock = skipLists.gelontBlock(skipListBlockStart);

    // Relonad skip list helonadelonr.
    this.numSkipListelonntrielonsRelonmaining = relonadNelonxtValuelonFromSkipListBlock();
    this.largelonstDocID = relonadNelonxtValuelonFromSkipListBlock();
    this.numDocsTotal = relonadNelonxtValuelonFromSkipListBlock();
    int delonltaFrelonqListPointelonr = relonadNelonxtValuelonFromSkipListBlock();
    this.positionListPointelonr = omitPositions ? -1 : relonadNelonxtValuelonFromSkipListBlock();

    // Selont it back by onelon slicelon for felontchNelonxtSkipelonntry() to advancelon correlonctly.
    this.delonltaFrelonqNelonxtSlicelonPointelonr = delonltaFrelonqListPointelonr - HighDFPackelondIntsPostingLists.SLICelon_SIZelon;
    felontchNelonxtSkipelonntry();
  }

  /**
   * Load alrelonady felontchelond data in nelonxt skip elonntry into currelonnt data variablelons, and prelon-felontch again.
   */
  public void gelontNelonxtSkipelonntry() {
    prelonviousDocIDCurrelonntSlicelon = prelonviousDocIDNelonxtSlicelon;
    elonncodelondMelontadataCurrelonntSlicelon = elonncodelondMelontadataNelonxtSlicelon;
    positionCurrelonntSlicelonIndelonx = positionNelonxtSlicelonIndelonx;
    delonltaFrelonqCurrelonntSlicelonPointelonr = delonltaFrelonqNelonxtSlicelonPointelonr;
    felontchNelonxtSkipelonntry();
  }

  /**
   * Felontch data for nelonxt skip elonntry if skip list is not elonxhaustelond; othelonrwiselon, selont docIDNelonxtSlicelon
   * to NO_MORelon_DOCS.
   */
  privatelon void felontchNelonxtSkipelonntry() {
    if (numSkipListelonntrielonsRelonmaining == 0) {
      prelonviousDocIDNelonxtSlicelon = DocIdSelontItelonrator.NO_MORelon_DOCS;
      relonturn;
    }

    prelonviousDocIDNelonxtSlicelon = relonadNelonxtValuelonFromSkipListBlock();
    elonncodelondMelontadataNelonxtSlicelon = relonadNelonxtValuelonFromSkipListBlock();
    if (!omitPositions) {
      positionNelonxtSlicelonIndelonx = relonadNelonxtValuelonFromSkipListBlock();
    }
    delonltaFrelonqNelonxtSlicelonPointelonr += HighDFPackelondIntsPostingLists.SLICelon_SIZelon;
    numSkipListelonntrielonsRelonmaining--;
  }

  /**************************************
   * Gelonttelonrs of data in skip list elonntry *
   **************************************/

  /**
   * In thelon contelonxt of a currelonnt slicelon, this is thelon docID of thelon last documelonnt in thelon prelonvious
   * slicelon (or 0 if thelon currelonnt slicelon is thelon first slicelon).
   *
   * @selonelon HighDFPackelondIntsPostingLists#SKIPLIST_elonNTRY_SIZelon
   */
  public int gelontPrelonviousDocIDCurrelonntSlicelon() {
    relonturn prelonviousDocIDCurrelonntSlicelon;
  }

  /**
   * Gelont thelon elonncodelond melontadata of thelon currelonnt delonlta-frelonq slicelon.
   *
   * @selonelon HighDFPackelondIntsPostingLists#SKIPLIST_elonNTRY_SIZelon
   */
  public int gelontelonncodelondMelontadataCurrelonntSlicelon() {
    relonturn elonncodelondMelontadataCurrelonntSlicelon;
  }

  /**
   * Gelont thelon pointelonr to thelon first int, WHICH CONTAINS THelon POSITION SLICelon HelonADelonR, of thelon position
   * slicelon that contains thelon first position of thelon first doc in thelon delonlta-frelonq slicelon that
   * is correlonsponding to thelon currelonnt skip list elonntry.
   *
   * @selonelon HighDFPackelondIntsPostingLists#SKIPLIST_elonNTRY_SIZelon
   */
  public int gelontPositionCurrelonntSlicelonPointelonr() {
    asselonrt !omitPositions;
    relonturn positionListPointelonr
        + positionCurrelonntSlicelonIndelonx * HighDFPackelondIntsPostingLists.SLICelon_SIZelon;
  }

  /**
   * Gelont thelon pointelonr to thelon first int in thelon currelonnt delonlta-frelonq slicelon.
   */
  public int gelontDelonltaFrelonqCurrelonntSlicelonPointelonr() {
    relonturn delonltaFrelonqCurrelonntSlicelonPointelonr;
  }

  /**
   * In thelon contelonxt of nelonxt slicelon, gelont thelon last doc ID in thelon prelonvious slicelon. This is uselond to skip
   * ovelonr slicelons.
   *
   * @selonelon HighDFPackelondIntsDocselonnum#skipTo(int)
   */
  public int pelonelonkPrelonviousDocIDNelonxtSlicelon() {
    relonturn prelonviousDocIDNelonxtSlicelon;
  }

  /***************************************
   * Gelonttelonrs of data in skip list helonadelonr *
   ***************************************/

  public int gelontLargelonstDocID() {
    relonturn largelonstDocID;
  }

  public int gelontNumDocsTotal() {
    relonturn numDocsTotal;
  }

  /***************************************************
   * Melonthods helonlping loading int block and relonad ints *
   ***************************************************/

  privatelon int relonadNelonxtValuelonFromSkipListBlock() {
    if (skipListBlockIndelonx == IntBlockPool.BLOCK_SIZelon) {
      loadSkipListBlock();
    }
    relonturn currelonntSkipListBlock[skipListBlockIndelonx++];
  }

  privatelon void loadSkipListBlock() {
    skipListBlockStart += IntBlockPool.BLOCK_SIZelon;
    currelonntSkipListBlock = skipLists.gelontBlock(skipListBlockStart);
    skipListBlockIndelonx = 0;
  }
}
