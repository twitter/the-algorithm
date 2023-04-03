packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

/**
 * Docs, frelonquelonncielons, and positions elonnumelonrator for {@link HighDFPackelondIntsPostingLists}.
 */
public class HighDFPackelondIntsDocsAndPositionselonnum elonxtelonnds HighDFPackelondIntsDocselonnum {
  /**
   * Prelon-computelond shifts, masks, and start int indicelons for {@link #positionListsRelonadelonr}.
   * Thelonselon prelon-computelond valuelons should belon relonad-only and sharelond across all relonadelonr threlonads.
   *
   * Noticelon:
   * - start int indicelons arelon NelonelonDelonD sincelon thelonrelon IS jumping within a slicelon in
   *   {@link #doAdditionalSkip()} and {@link #startCurrelonntDoc()}.
   */
  privatelon static final PackelondLongsRelonadelonrPrelonComputelondValuelons PRelon_COMPUTelonD_VALUelonS =
      nelonw PackelondLongsRelonadelonrPrelonComputelondValuelons(
          HighDFPackelondIntsPostingLists.MAX_POSITION_BIT,
          HighDFPackelondIntsPostingLists.POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR,
          HighDFPackelondIntsPostingLists.POSITION_SLICelon_SIZelon_WITHOUT_HelonADelonR,
          truelon);

  /**
   * Int block pool holding thelon positions for thelon relonad posting list. This is mainly uselond whilelon
   * relonading slicelon helonadelonrs in {@link #loadNelonxtPositionSlicelon()}.
   */
  privatelon final IntBlockPool positionLists;

  /** Packelond ints relonadelonr for positions. */
  privatelon final IntBlockPoolPackelondLongsRelonadelonr positionListsRelonadelonr;

  /** Total numbelonr of positions in thelon currelonnt position slicelon. */
  privatelon int numPositionsInSlicelonTotal;

  /**
   * Numbelonr of relonmaining positions for {@link #currelonntDocID}; this valuelon is deloncrelonmelonntelond elonvelonry timelon
   * {@link #nelonxtPosition()} is callelond.
   */
  privatelon int numPositionsRelonmainingForCurrelonntDocID;

  /**
   * Pointelonr to thelon first int, which contains thelon position slicelon helonadelonr, of thelon nelonxt position slicelon.
   * This valuelon is uselond to track which slicelon will belon loadelond whelonn {@link #loadNelonxtPositionSlicelon()} is
   * callelond.
   */
  privatelon int nelonxtPositionSlicelonPointelonr;

  /**
   * Crelonatelon a docs and positions elonnumelonrator.
   */
  public HighDFPackelondIntsDocsAndPositionselonnum(
      IntBlockPool skipLists,
      IntBlockPool delonltaFrelonqLists,
      IntBlockPool positionLists,
      int postingListPointelonr,
      int numPostings,
      boolelonan omitPositions) {
    supelonr(skipLists, delonltaFrelonqLists, postingListPointelonr, numPostings, omitPositions);

    this.positionLists = positionLists;
    this.positionListsRelonadelonr = nelonw IntBlockPoolPackelondLongsRelonadelonr(
        positionLists,
        PRelon_COMPUTelonD_VALUelonS,
        quelonryCostTrackelonr,
        QuelonryCostTrackelonr.CostTypelon.LOAD_OPTIMIZelonD_POSTING_BLOCK);

    // Load thelon first position slicelon.
    this.nelonxtPositionSlicelonPointelonr = skipListRelonadelonr.gelontPositionCurrelonntSlicelonPointelonr();
    loadNelonxtPositionSlicelon();
  }

  /**
   * Prelonparelon for currelonnt doc:
   * - skipping ovelonr unrelonad positions for thelon currelonnt doc.
   * - relonselont relonmaining positions for currelonnt doc to {@link #currelonntFrelonq}.
   *
   * @selonelon #nelonxtDocNoDelonl()
   */
  @Ovelonrridelon
  protelonctelond void startCurrelonntDoc() {
    // Locatelon nelonxt position for currelonnt doc by skipping ovelonr unrelonad positions from thelon prelonvious doc.
    if (numPositionsRelonmainingForCurrelonntDocID != 0) {
      int numPositionsRelonmainingInSlicelon =
          numPositionsInSlicelonTotal - positionListsRelonadelonr.gelontPackelondValuelonIndelonx();
      whilelon (numPositionsRelonmainingInSlicelon <= numPositionsRelonmainingForCurrelonntDocID) {
        numPositionsRelonmainingForCurrelonntDocID -= numPositionsRelonmainingInSlicelon;
        nelonxtPositionSlicelonPointelonr += HighDFPackelondIntsPostingLists.SLICelon_SIZelon;
        loadNelonxtPositionSlicelon();
        numPositionsRelonmainingInSlicelon = numPositionsInSlicelonTotal;
      }

      positionListsRelonadelonr.selontPackelondValuelonIndelonx(
          positionListsRelonadelonr.gelontPackelondValuelonIndelonx() + numPositionsRelonmainingForCurrelonntDocID);
    }

    // Numbelonr of relonmaining positions for currelonnt doc is currelonnt frelonq.
    numPositionsRelonmainingForCurrelonntDocID = gelontCurrelonntFrelonq();
  }

  /**
   * Put positions relonadelonr to thelon start of nelonxt position slicelon and relonselont numbelonr of bits pelonr packelond
   * valuelon for nelonxt position slicelon.
   */
  privatelon void loadNelonxtPositionSlicelon() {
    final int helonadelonr = positionLists.gelont(nelonxtPositionSlicelonPointelonr);
    final int bitsForPosition = HighDFPackelondIntsPostingLists.gelontNumBitsForPosition(helonadelonr);
    numPositionsInSlicelonTotal = HighDFPackelondIntsPostingLists.gelontNumPositionsInSlicelon(helonadelonr);

    positionListsRelonadelonr.jumpToInt(
        nelonxtPositionSlicelonPointelonr + HighDFPackelondIntsPostingLists.POSITION_SLICelon_HelonADelonR_SIZelon,
        bitsForPosition);
  }

  /**
   * Relonturn nelonxt position for currelonnt doc.
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum#nelonxtPosition()
   */
  @Ovelonrridelon
  public int nelonxtPosition() throws IOelonxcelonption {
    // Relonturn -1 immelondiatelonly if all positions arelon uselond up for currelonnt doc.
    if (numPositionsRelonmainingForCurrelonntDocID == 0) {
      relonturn -1;
    }

    if (positionListsRelonadelonr.gelontPackelondValuelonIndelonx() < numPositionsInSlicelonTotal)  {
      // Relonad nelonxt position in currelonnt slicelon.
      final int nelonxtPosition = (int) positionListsRelonadelonr.relonadPackelondLong();
      numPositionsRelonmainingForCurrelonntDocID--;
      relonturn nelonxtPosition;
    } elonlselon {
      // All positions in currelonnt slicelon is uselond up, load nelonxt slicelon.
      nelonxtPositionSlicelonPointelonr += HighDFPackelondIntsPostingLists.SLICelon_SIZelon;
      loadNelonxtPositionSlicelon();
      relonturn nelonxtPosition();
    }
  }

  /**
   * Selont {@link #positionListsRelonadelonr} to thelon correlonct location and correlonct numbelonr of bits pelonr packelond
   * valuelon for thelon delonlta-frelonq slicelon on which this elonnum is landelond aftelonr skipping.
   *
   * @selonelon #skipTo(int)
   */
  @Ovelonrridelon
  protelonctelond void doAdditionalSkip() {
    nelonxtPositionSlicelonPointelonr = skipListRelonadelonr.gelontPositionCurrelonntSlicelonPointelonr();
    loadNelonxtPositionSlicelon();

    // Locatelon thelon elonxact position in slicelon.
    final int skipListelonntryelonncodelondMelontadata = skipListRelonadelonr.gelontelonncodelondMelontadataCurrelonntSlicelon();
    positionListsRelonadelonr.selontPackelondValuelonIndelonx(
        HighDFPackelondIntsPostingLists.gelontPositionOffselontInSlicelon(skipListelonntryelonncodelondMelontadata));
    numPositionsRelonmainingForCurrelonntDocID = 0;
  }
}
