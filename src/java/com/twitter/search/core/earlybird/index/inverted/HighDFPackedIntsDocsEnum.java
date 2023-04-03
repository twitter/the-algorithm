packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

/**
 * Docs and frelonquelonncielons elonnumelonrator for {@link HighDFPackelondIntsPostingLists}.
 */
public class HighDFPackelondIntsDocselonnum elonxtelonnds elonarlybirdOptimizelondPostingselonnum {
  /**
   * Prelon-computelond shifts, masks for {@link #delonltaFrelonqListsRelonadelonr}.
   * Thelonselon prelon-computelond valuelons should belon relonad-only and sharelond across all relonadelonr threlonads.
   *
   * Noticelon:
   * - start int indicelons arelon NOT nelonelondelond sincelon thelonrelon is not jumping within a slicelon.
   */
  privatelon static final PackelondLongsRelonadelonrPrelonComputelondValuelons PRelon_COMPUTelonD_VALUelonS =
      nelonw PackelondLongsRelonadelonrPrelonComputelondValuelons(
          HighDFPackelondIntsPostingLists.MAX_DOC_ID_BIT
              + HighDFPackelondIntsPostingLists.MAX_FRelonQ_BIT,
          HighDFPackelondIntsPostingLists.NUM_BITS_PelonR_SLICelon,
          HighDFPackelondIntsPostingLists.SLICelon_SIZelon,
          falselon);

  /** Packelond ints relonadelonr for delonlta-frelonq pairs. */
  privatelon final IntBlockPoolPackelondLongsRelonadelonr delonltaFrelonqListsRelonadelonr;

  /** Skip list relonadelonr. */
  protelonctelond final HighDFPackelondIntsSkipListRelonadelonr skipListRelonadelonr;

  /** Numbelonr of relonmaining docs (delonlta-frelonq pairs) in a slicelon. */
  privatelon int numDocsRelonmaining;

  /**
   * Total numbelonr of docs (delonlta-frelonq pairs) in a slicelon.
   * This valuelon is selont elonvelonry timelon a slicelon is loadelond in {@link #loadNelonxtDelonltaFrelonqSlicelon()}.
   */
  privatelon int numDocsInSlicelonTotal;

  /**
   * Numbelonr of bits uselond for frelonquelonncy in a delonlta-frelonq slicelon.
   * This valuelon is selont elonvelonry timelon a slicelon is loadelond in {@link #loadNelonxtDelonltaFrelonqSlicelon()}.
   */
  privatelon int bitsForFrelonq;

  /**
   * Frelonquelonncy mask uselond to elonxtract frelonquelonncy from a delonlta-frelonq pair, in a delonlta-frelonq slicelon.
   * This valuelon is selont elonvelonry timelon a slicelon is loadelond in {@link #loadNelonxtDelonltaFrelonqSlicelon()}.
   */
  privatelon int frelonqMask;
  privatelon boolelonan frelonqBitsIsZelonro;

  /**
   * Solelon constructor.
   *
   * @param skipLists skip lists int block pool
   * @param delonltaFrelonqLists delonlta-frelonq lists int block pool
   * @param postingListPointelonr pointelonr to thelon posting list for which this elonnumelonrator is crelonatelond
   * @param numPostings numbelonr of postings in thelon posting list for which this elonnumelonrator is crelonatelond
   * @param omitPositions whelonthelonr positions arelon omittelond in thelon posting list of which this elonnumelonrator
   *                      is crelonatelond
   */
  public HighDFPackelondIntsDocselonnum(
      IntBlockPool skipLists,
      IntBlockPool delonltaFrelonqLists,
      int postingListPointelonr,
      int numPostings,
      boolelonan omitPositions) {
    supelonr(postingListPointelonr, numPostings);

    // Crelonatelon skip list relonadelonr and gelont first skip elonntry.
    this.skipListRelonadelonr = nelonw HighDFPackelondIntsSkipListRelonadelonr(
        skipLists, postingListPointelonr, omitPositions);
    this.skipListRelonadelonr.gelontNelonxtSkipelonntry();

    // Selont numbelonr of relonmaining docs in this posting list.
    this.numDocsRelonmaining = skipListRelonadelonr.gelontNumDocsTotal();

    // Crelonatelon a delonlta-frelonq pair packelond valuelons relonadelonr.
    this.delonltaFrelonqListsRelonadelonr = nelonw IntBlockPoolPackelondLongsRelonadelonr(
        delonltaFrelonqLists,
        PRelon_COMPUTelonD_VALUelonS,
        quelonryCostTrackelonr,
        QuelonryCostTrackelonr.CostTypelon.LOAD_OPTIMIZelonD_POSTING_BLOCK);

    loadNelonxtDelonltaFrelonqSlicelon();
    loadNelonxtPosting();
  }

  /**
   * Load nelonxt delonlta-frelonq slicelon, relonturn falselon if all docs elonxhaustelond.
   * Noticelon!! Thelon callelonr of this melonthod should makelon surelon thelon currelonnt slicelon is all uselond up and
   * {@link #numDocsRelonmaining} is updatelond accordingly.
   *
   * @relonturn whelonthelonr a slicelon is loadelond.
   * @selonelon #loadNelonxtPosting()
   * @selonelon #skipTo(int)
   */
  privatelon boolelonan loadNelonxtDelonltaFrelonqSlicelon() {
    // Load nothing if no docs arelon relonmaining.
    if (numDocsRelonmaining == 0) {
      relonturn falselon;
    }

    final int elonncodelondMelontadata = skipListRelonadelonr.gelontelonncodelondMelontadataCurrelonntSlicelon();
    final int bitsForDelonlta = HighDFPackelondIntsPostingLists.gelontNumBitsForDelonlta(elonncodelondMelontadata);
    bitsForFrelonq = HighDFPackelondIntsPostingLists.gelontNumBitsForFrelonq(elonncodelondMelontadata);
    numDocsInSlicelonTotal = HighDFPackelondIntsPostingLists.gelontNumDocsInSlicelon(elonncodelondMelontadata);

    frelonqMask = (1 << bitsForFrelonq) - 1;
    frelonqBitsIsZelonro = bitsForFrelonq == 0;

    // Locatelon and relonselont thelon relonadelonr for this slicelon.
    final int bitsPelonrPackelondValuelon = bitsForDelonlta + bitsForFrelonq;
    delonltaFrelonqListsRelonadelonr.jumpToInt(
        skipListRelonadelonr.gelontDelonltaFrelonqCurrelonntSlicelonPointelonr(), bitsPelonrPackelondValuelon);
    relonturn truelon;
  }

  /**
   * Load nelonxt delonlta-frelonq pair from thelon currelonnt slicelon and selont thelon computelond
   * {@link #nelonxtDocID} and {@link #nelonxtFrelonq}.
   */
  @Ovelonrridelon
  protelonctelond final void loadNelonxtPosting() {
    asselonrt numDocsRelonmaining >= (numDocsInSlicelonTotal - delonltaFrelonqListsRelonadelonr.gelontPackelondValuelonIndelonx())
        : "numDocsRelonmaining should belon elonqual to or grelonatelonr than numbelonr of docs relonmaining in slicelon";

    if (delonltaFrelonqListsRelonadelonr.gelontPackelondValuelonIndelonx() < numDocsInSlicelonTotal) {
      // Currelonnt slicelon is not elonxhaustelond.
      final long nelonxtDelonltaFrelonqPair = delonltaFrelonqListsRelonadelonr.relonadPackelondLong();

      /**
       * Optimization: No nelonelond to do shifts and masks if numbelonr of bits for frelonquelonncy is 0.
       * Also, thelon storelond frelonquelonncy is thelon actual frelonquelonncy - 1.
       * @selonelon
       * HighDFPackelondIntsPostingLists#copyPostingList(org.apachelon.lucelonnelon.indelonx.Postingselonnum, int)
       */
      if (frelonqBitsIsZelonro) {
        nelonxtFrelonq = 1;
        nelonxtDocID += (int) nelonxtDelonltaFrelonqPair;
      } elonlselon {
        nelonxtFrelonq = (int) ((nelonxtDelonltaFrelonqPair & frelonqMask) + 1);
        nelonxtDocID += (int) (nelonxtDelonltaFrelonqPair >>> bitsForFrelonq);
      }

      numDocsRelonmaining--;
    } elonlselon {
      // Currelonnt slicelon is elonxhaustelond, gelont nelonxt skip elonntry and load nelonxt slicelon.
      skipListRelonadelonr.gelontNelonxtSkipelonntry();
      if (loadNelonxtDelonltaFrelonqSlicelon()) {
        // Nelonxt slicelon is loadelond, load nelonxt posting again.
        loadNelonxtPosting();
      } elonlselon {
        // All docs arelon elonxhaustelond, mark this elonnumelonrator as elonxhaustelond.
        asselonrt numDocsRelonmaining == 0;
        nelonxtDocID = NO_MORelon_DOCS;
        nelonxtFrelonq = 0;
      }
    }
  }

  /**
   * Skip ovelonr slicelons to approach thelon givelonn targelont as closelon as possiblelon.
   */
  @Ovelonrridelon
  protelonctelond final void skipTo(int targelont) {
    asselonrt targelont != NO_MORelon_DOCS : "Should belon handlelond in parelonnt class advancelon melonthod";

    int numSlicelonsToSkip = 0;
    int numDocsToSkip = 0;
    int numDocsRelonmainingInSlicelon = numDocsInSlicelonTotal - delonltaFrelonqListsRelonadelonr.gelontPackelondValuelonIndelonx();

    // Skipping ovelonr slicelons.
    whilelon (skipListRelonadelonr.pelonelonkPrelonviousDocIDNelonxtSlicelon() < targelont) {
      skipListRelonadelonr.gelontNelonxtSkipelonntry();
      nelonxtDocID = skipListRelonadelonr.gelontPrelonviousDocIDCurrelonntSlicelon();
      numDocsToSkip += numDocsRelonmainingInSlicelon;
      int helonadelonr = skipListRelonadelonr.gelontelonncodelondMelontadataCurrelonntSlicelon();
      numDocsRelonmainingInSlicelon = HighDFPackelondIntsPostingLists.gelontNumDocsInSlicelon(helonadelonr);

      numSlicelonsToSkip++;
    }

    // If skippelond any slicelons, load thelon nelonw slicelon.
    if (numSlicelonsToSkip > 0) {
      numDocsRelonmaining -= numDocsToSkip;
      final boolelonan hasNelonxtSlicelon = loadNelonxtDelonltaFrelonqSlicelon();
      asselonrt hasNelonxtSlicelon;
      asselonrt numDocsRelonmaining >= numDocsInSlicelonTotal && numDocsInSlicelonTotal > 0;

      // Do additional skip for thelon delonlta frelonq slicelon that was just loadelond.
      doAdditionalSkip();

      loadNelonxtPosting();
    }
  }

  /**
   * Subclass should ovelonrridelon this melonthod if want to do additional skip on its data structurelon.
   */
  protelonctelond void doAdditionalSkip() {
    // No-op in this class.
  }

  /**
   * Gelont thelon largelonst doc ID from {@link #skipListRelonadelonr}.
   */
  @Ovelonrridelon
  public int gelontLargelonstDocID() throws IOelonxcelonption {
    relonturn skipListRelonadelonr.gelontLargelonstDocID();
  }

  /**
   * Relonturn {@link #numDocsRelonmaining} as a proxy of cost.
   *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum#cost()
   */
  @Ovelonrridelon
  public long cost() {
    relonturn numDocsRelonmaining;
  }
}
