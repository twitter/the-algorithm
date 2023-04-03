packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import javax.annotation.Nullablelon;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

/**
 * An optimizelond posting lists implelonmelonntation storing doc delonltas, doc frelonqs, and positions as packelond
 * ints in a 64 ints slicelon backelond by {@link IntBlockPool}.
 *
 * Thelonrelon arelon threlonelon innelonr data structurelons uselond to storelon valuelons uselond by a posting lists instancelon:
 *
 * - Skip lists, uselond for fast {@link Postingselonnum#advancelon(int)}, arelon storelond in {@link #skipLists}
 *   int block pool.
 * - Doc delonltas and frelonqs arelon storelond in {@link #delonltaFrelonqLists} int block pool.
 * - Positions arelon storelond in {@link #positionLists} int block pool.
 *
 * For delontail layout and configuration, plelonaselon relonfelonr to thelon Javadoc of {@link #skipLists},
 * {@link #delonltaFrelonqLists} and {@link #positionLists}.
 *
 * <b>This implelonmelonntation delonsignelond for posting lists with a LARGelon numbelonr of postings.</b>
 *
 * <i>Acknowlelondgelonmelonnt</i>: thelon concelonpts of slicelon baselond packelond ints elonncoding/deloncoding is borrowelond
 *                         from {@codelon HighDFComprelonsselondPostinglists}, which will belon delonpreloncatelond duelon
 *                         to not supporting positions that arelon grelonatelonr than 255.
 */
public class HighDFPackelondIntsPostingLists elonxtelonnds OptimizelondPostingLists {
  /**
   * A countelonr uselond to track whelonn positions elonnum is relonquirelond and a posting lists instancelon is selont
   * to omit positions.
   *
   * @selonelon #postings(int, int, int)
   */
  privatelon static final SelonarchCountelonr GelonTTING_POSITIONS_WITH_OMIT_POSITIONS =
      SelonarchCountelonr.elonxport(
          "high_df_packelond_ints_posting_list_gelontting_positions_with_omit_positions");

  /**
   * Information relonlatelond to sizelon of a slicelon.
   */
  static final int SLICelon_SIZelon_BIT = 6;
  static final int SLICelon_SIZelon = 1 << SLICelon_SIZelon_BIT;                 //   64 ints pelonr block
  static final int NUM_BITS_PelonR_SLICelon = SLICelon_SIZelon * Intelongelonr.SIZelon;   // 2048 bits pelonr block

  /**
   * A skip list has ONelon skip list helonadelonr that contains 5 ints (4 ints if positions arelon omittelond):
   * - 1st int: numbelonr of skip elonntrielons in this skip list.
   * - 2nd int: largelonst doc ID in this posting list.
   * - 3rd int: numbelonr of docs in this posting list.
   * - 4th int: pointelonr to thelon start of thelon delonlta-frelonq list of this posting list.
   * - 5th int: (OPTIONAL) pointelonr to thelon start of thelon position list of this posting list.
   */
  static final int SKIPLIST_HelonADelonR_SIZelon = 5;
  static final int SKIPLIST_HelonADelonR_SIZelon_WITHOUT_POSITIONS = SKIPLIST_HelonADelonR_SIZelon - 1;

  /**
   * A skip list has MANY skip elonntrielons. elonach skip elonntry is for onelon slicelon in delonlta-frelonq list.
   * Thelonrelon arelon 3 ints in elonvelonry skip elonntry (2 ints if positions arelon omittelond):
   * - 1st int: last doc ID in prelonvious slicelon (0 for thelon first slicelon), this is mainly uselond during
   *            skipping beloncauselon delonltas, not absolutelon doc IDs, arelon storelond in a slicelon.
   * - 2nd int: elonncodelond melontadata of thelon correlonsponding delonlta-frelonq slicelon. Thelonrelon arelon 4 pieloncelon of
   *            information from thelon LOWelonST bits to HIGHelonST bits of this int:
   *            11 bits: numbelonr of docs (delonlta-frelonq pairs) in this slicelon.
   *             5 bits: numbelonr of bits uselond to elonncodelon elonach frelonq.
   *             5 bits: numbelonr of bits uselond to elonncodelon elonach delonlta.
   *            11 bits: POSITION SLICelon OFFSelonT: an indelonx of numbelonr of positions; this is whelonrelon thelon
   *                     first position of thelon first doc (in this delonlta-frelonq slicelon) is in thelon
   *                     position slicelon. Thelon position slicelon is idelonntifielond by thelon 3rd int belonlow.
   *                     Thelonselon two pieloncelon information uniquelonly idelonntifielond thelon location of thelon start
   *                     position of this delonlta-frelonq slicelon. This valuelon is always 0 if position is
   *                     omittelond.
   * - 3rd int: (OPTIONAL) POSITION SLICelon INDelonX: an indelonx of of numbelonr of slicelons; this valuelon
   *            idelonntifielons thelon slicelon in which thelon first position of thelon first doc (in this
   *            delonlta-frelonq slicelon) elonxists. Thelon elonxact location insidelon thelon position slicelon is idelonntifielond
   *            by POSITION SLICelon OFFSelonT that is storelond in thelon 2nd int abovelon.
   *            Noticelon: this is not thelon absolutelon addrelonss in thelon block pool, but instelonad a relonlativelon
   *            offselont (in numbelonr of slicelons) on top of this telonrm's first position slicelon.
   *            This valuelon DOelonS NOT elonXIST if position is omittelond.
   */
  static final int SKIPLIST_elonNTRY_SIZelon = 3;
  static final int SKIPLIST_elonNTRY_SIZelon_WITHOUT_POSITIONS = SKIPLIST_elonNTRY_SIZelon - 1;

  /**
   * Shifts and masks uselond to elonncodelon/deloncodelon melontadata from thelon 2nd int of a skip list elonntry.
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   * @selonelon #elonncodelonSkipListelonntryMelontadata(int, int, int, int)
   * @selonelon #gelontNumBitsForDelonlta(int)
   * @selonelon #gelontNumBitsForFrelonq(int)
   * @selonelon #gelontNumDocsInSlicelon(int)
   * @selonelon #gelontPositionOffselontInSlicelon(int)
   */
  static final int SKIPLIST_elonNTRY_POSITION_OFFSelonT_SHIFT = 21;
  static final int SKIPLIST_elonNTRY_NUM_BITS_DelonLTA_SHIFT = 16;
  static final int SKIPLIST_elonNTRY_NUM_BITS_FRelonQ_SHIFT = 11;
  static final int SKIPLIST_elonNTRY_POSITION_OFFSelonT_MASK = (1 << 11) - 1;
  static final int SKIPLIST_elonNTRY_NUM_BITS_DelonLTA_MASK = (1 << 5) - 1;
  static final int SKIPLIST_elonNTRY_NUM_BITS_FRelonQ_MASK = (1 << 5) - 1;
  static final int SKIPLIST_elonNTRY_NUM_DOCS_MASK = (1 << 11) - 1;

  /**
   * elonach position slicelon has a helonadelonr that is thelon 1st int in this position slicelon. From LOWelonST bits
   * to HIGHelonST bits, thelonrelon arelon 2 pieloncelons of information elonncodelond in this singlelon int:
   * 11 bits: numbelonr of positions in this slicelon.
   *  5 bits: numbelonr of bits uselond to elonncodelon elonach position.
   */
  static final int POSITION_SLICelon_HelonADelonR_SIZelon = 1;

  /**
   * Information relonlatelond to sizelon of a position slicelon. Thelon actual sizelon is thelon samelon as
   * {@link #SLICelon_SIZelon}, but thelonrelon is 1 int uselond for position slicelon helonadelonr.
   */
  static final int POSITION_SLICelon_SIZelon_WITHOUT_HelonADelonR = SLICelon_SIZelon - POSITION_SLICelon_HelonADelonR_SIZelon;
  static final int POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR =
      POSITION_SLICelon_SIZelon_WITHOUT_HelonADelonR * Intelongelonr.SIZelon;

  /**
   * Shifts and masks uselond to elonncodelon/deloncodelon melontadata from thelon position slicelon helonadelonr.
   * @selonelon #POSITION_SLICelon_HelonADelonR_SIZelon
   * @selonelon #elonncodelonPositionelonntryHelonadelonr(int, int)
   * @selonelon #gelontNumPositionsInSlicelon(int)
   * @selonelon #gelontNumBitsForPosition(int)
   */
  static final int POSITION_SLICelon_HelonADelonR_BITS_POSITION_SHIFT = 11;
  static final int POSITION_SLICelon_HelonADelonR_BITS_POSITION_MASK = (1 << 5) - 1;
  static final int POSITION_SLICelon_HelonADelonR_NUM_POSITIONS_MASK = (1 << 11) - 1;

  /**
   * Storelons skip list for elonach posting list.
   *
   * A skip list consists of ONelon skip list helonadelonr and MANY skip list elonntrielons, and elonach skip elonntry
   * correlonsponds to onelon delonlta-frelonq slicelon. Also, unlikelon {@link #delonltaFrelonqLists} and
   * {@link #positionLists}, valuelons in skip lists int pool arelon NOT storelond in unit of slicelons.
   *
   * elonxamplelon:
   * H: skip list helonadelonr int
   * elon: skip list elonntry int
   * ': int boundary
   * |: helonadelonr/elonntry boundary (also a boundary of int)
   *
   *  <----- skip list A -----> <- skip list B ->
   * |H'H'H'H'H|elon'elon|elon'elon|elon'elon|elon'elon|H'H'H'H'H|elon'elon|elon'elon|
   */
  privatelon final IntBlockPool skipLists;

  /**
   * Storelons delonlta-frelonq list for elonach posting list.
   *
   * A delonlta-frelonq list consists of MANY 64-int slicelons, and delonlta-frelonq pairs arelon storelond compactly
   * with a fixelond numbelonr of bits within a singlelon slicelon. elonach slicelon has a correlonsponding skip list
   * elonntry in {@link #skipLists} storing melontadata about this slicelon.
   *
   * elonxamplelon:
   * |: slicelon boundary
   *
   *  <----------------- delonlta-frelonq list A -----------------> <--- delonlta-frelonq list B --->
   * |64 ints slicelon|64 ints slicelon|64 ints slicelon|64 ints slicelon|64 ints slicelon|64 ints slicelon|
   */
  privatelon final IntBlockPool delonltaFrelonqLists;

  /**
   * Storelons position list for elonach posting list.
   *
   * A position list consists of MANY 64 ints slicelons, and positions arelon storelond compactly with a
   * fixelond numbelonr of bits within a singlelon slicelon. Thelon first int in elonach slicelon is uselond as a helonadelonr to
   * storelon thelon melontadata about this position slicelon.
   *
   * elonxamplelon:
   * H: position helonadelonr int
   * ': int boundary
   * |: slicelon boundary
   *
   *  <--------------- position list A ---------------> <---------- position list B ---------->
   * |H'63 ints|H'63 ints|H'63 ints|H'63 ints|H'63 ints|H'63 ints|H'63 ints|H'63 ints|H'63 ints|
   */
  privatelon final IntBlockPool positionLists;

  /**
   * Whelonthelonr positions arelon omittelond in this optimizelond posting lists.
   */
  privatelon final boolelonan omitPositions;

  /**
   * Skip list helonadelonr and elonntry sizelon for this posting lists, could belon diffelonrelonnt delonpelonnds on whelonthelonr
   * position is omittelond or not.
   *
   * @selonelon #SKIPLIST_HelonADelonR_SIZelon
   * @selonelon #SKIPLIST_HelonADelonR_SIZelon_WITHOUT_POSITIONS
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   * @selonelon #SKIPLIST_elonNTRY_SIZelon_WITHOUT_POSITIONS
   */
  privatelon final int skipListHelonadelonrSizelon;
  privatelon final int skiplistelonntrySizelon;

  /**
   * Buffelonr uselond in {@link #copyPostingList(Postingselonnum, int)}
   * to quelonuelon up valuelons nelonelondelond for a slicelon.
   * Loadelond posting lists havelon thelonm selont as null.
   */
  privatelon final PostingsBuffelonrQuelonuelon docFrelonqQuelonuelon;
  privatelon final PostingsBuffelonrQuelonuelon positionQuelonuelon;

  /**
   * Packelond ints writelonr uselond to writelon into delonlta-frelonq int pool and position int pool.
   * Loadelond posting lists havelon thelonm selont as null.
   */
  privatelon final IntBlockPoolPackelondLongsWritelonr delonltaFrelonqListsWritelonr;
  privatelon final IntBlockPoolPackelondLongsWritelonr positionListsWritelonr;

  /**
   * Delonfault constructor.
   *
   * @param omitPositions whelonthelonr positions will belon omittelond in thelonselon posting lists.
   */
  public HighDFPackelondIntsPostingLists(boolelonan omitPositions) {
    this(
        nelonw IntBlockPool("high_df_packelond_ints_skip_lists"),
        nelonw IntBlockPool("high_df_packelond_ints_delonlta_frelonq_lists"),
        nelonw IntBlockPool("high_df_packelond_ints_position_lists"),
        omitPositions,
        nelonw PostingsBuffelonrQuelonuelon(NUM_BITS_PelonR_SLICelon),
        nelonw PostingsBuffelonrQuelonuelon(POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR));
  }

  /**
   * Constructors uselond by loadelonr.
   *
   * @param skipLists loadelond int block pool relonprelonselonnts skip lists
   * @param delonltaFrelonqLists loadelond int block pool relonprelonselonnts delonlta-frelonq lists
   * @param positionLists loadelond int block pool relonprelonselonnts position lists
   * @param omitPositions whelonthelonr positions will belon omittelond in thelonselon posting lists
   * @param docFrelonqQuelonuelon buffelonr uselond to quelonuelon up valuelons uselond for a doc frelonq slicelon, null if loadelond
   * @param positionQuelonuelon buffelonr uselond to quelonuelon up valuelons uselond for a position slicelon, null if loadelond
   * @selonelon FlushHandlelonr#doLoad(FlushInfo, DataDelonselonrializelonr)
   */
  privatelon HighDFPackelondIntsPostingLists(
      IntBlockPool skipLists,
      IntBlockPool delonltaFrelonqLists,
      IntBlockPool positionLists,
      boolelonan omitPositions,
      @Nullablelon PostingsBuffelonrQuelonuelon docFrelonqQuelonuelon,
      @Nullablelon PostingsBuffelonrQuelonuelon positionQuelonuelon) {
    this.skipLists = skipLists;
    this.delonltaFrelonqLists = delonltaFrelonqLists;
    this.positionLists = positionLists;
    this.omitPositions = omitPositions;

    this.docFrelonqQuelonuelon = docFrelonqQuelonuelon;
    this.positionQuelonuelon = positionQuelonuelon;

    // docFrelonqQuelonuelon is null if this postingLists is loadelond,
    // welon don't nelonelond to crelonatelon writelonr at that caselon.
    if (docFrelonqQuelonuelon == null) {
      asselonrt positionQuelonuelon == null;
      this.delonltaFrelonqListsWritelonr = null;
      this.positionListsWritelonr = null;
    } elonlselon {
      this.delonltaFrelonqListsWritelonr = nelonw IntBlockPoolPackelondLongsWritelonr(delonltaFrelonqLists);
      this.positionListsWritelonr = nelonw IntBlockPoolPackelondLongsWritelonr(positionLists);
    }

    if (omitPositions) {
      skipListHelonadelonrSizelon = SKIPLIST_HelonADelonR_SIZelon_WITHOUT_POSITIONS;
      skiplistelonntrySizelon = SKIPLIST_elonNTRY_SIZelon_WITHOUT_POSITIONS;
    } elonlselon {
      skipListHelonadelonrSizelon = SKIPLIST_HelonADelonR_SIZelon;
      skiplistelonntrySizelon = SKIPLIST_elonNTRY_SIZelon;
    }
  }

  /**
   * A simplelon wrappelonr around assortelond statelons uselond whelonn coping positions in a posting elonnum.
   * @selonelon #copyPostingList(Postingselonnum, int)
   */
  privatelon static class PositionsStatelon {
    /** Max position has belonelonn selonelonn for thelon currelonnt position slicelon. */
    privatelon int maxPosition = 0;

    /** Bits nelonelondelond to elonncodelon/deloncodelon positions in thelon currelonnt position slicelon. */
    privatelon int bitsNelonelondelondForPosition = 0;

    /** Total numbelonr of position slicelons crelonatelond for currelonnt posting list. */
    privatelon int numPositionsSlicelons = 0;

    /**
     * Whelonnelonvelonr a slicelon of doc/frelonq pairs is writtelonn, this will point to thelon first position
     * associatelond with thelon first doc in thelon doc/frelonq slicelon.
     */
    privatelon int currelonntPositionsSlicelonIndelonx = 0;
    privatelon int currelonntPositionsSlicelonOffselont = 0;

    /**
     * Whelonnelonvelonr a nelonw documelonnt is procelonsselond, this points to thelon first position for this doc.
     * This is uselond if this doc elonnds up beloning choselonn as thelon first doc in a doc/frelonq slicelon.
     */
    privatelon int nelonxtPositionsSlicelonIndelonx = 0;
    privatelon int nelonxtPositionsSlicelonOffselont = 0;
  }

  /**
   * Copielons postings in thelon givelonn postings elonnum into this posting lists instancelon.
   *
   * @param postingselonnum elonnumelonrator of thelon posting list that nelonelonds to belon copielond
   * @param numPostings numbelonr of postings in thelon posting list that nelonelonds to belon copielond
   * @relonturn pointelonr to thelon copielond posting list in this posting lists instancelon
   */
  @Ovelonrridelon
  public int copyPostingList(Postingselonnum postingselonnum, int numPostings) throws IOelonxcelonption {
    asselonrt docFrelonqQuelonuelon.iselonmpty() : "elonach nelonw posting list should start with an elonmpty quelonuelon";
    asselonrt positionQuelonuelon.iselonmpty() : "elonach nelonw posting list should start with an elonmpty quelonuelon";

    final int skipListPointelonr = skipLists.lelonngth();
    final int delonltaFrelonqListPointelonr = delonltaFrelonqLists.lelonngth();
    final int positionListPointelonr = positionLists.lelonngth();
    asselonrt isSlicelonStart(delonltaFrelonqListPointelonr) : "elonach nelonw posting list should start at a nelonw slicelon";
    asselonrt isSlicelonStart(positionListPointelonr) : "elonach nelonw posting list should start at a nelonw slicelon";

    // Makelon room for skip list HelonADelonR.
    for (int i = 0; i < skipListHelonadelonrSizelon; i++) {
      skipLists.add(-1);
    }

    int doc;
    int prelonvDoc = 0;
    int prelonvWrittelonnDoc = 0;

    int maxDelonlta = 0;
    int maxFrelonq = 0;

    int bitsNelonelondelondForDelonlta = 0;
    int bitsNelonelondelondForFrelonq = 0;

    // Kelonelonp tracking positions relonlatelond info for this posting list.
    PositionsStatelon positionsStatelon = nelonw PositionsStatelon();

    int numDocs = 0;
    int numDelonltaFrelonqSlicelons = 0;
    whilelon ((doc = postingselonnum.nelonxtDoc()) != DocIdSelontItelonrator.NO_MORelon_DOCS) {
      numDocs++;

      int delonlta = doc - prelonvDoc;
      asselonrt delonlta <= MAX_DOC_ID;

      int nelonwBitsForDelonlta = bitsNelonelondelondForDelonlta;
      if (delonlta > maxDelonlta) {
        maxDelonlta = delonlta;
        nelonwBitsForDelonlta = log(maxDelonlta, 2);
        asselonrt nelonwBitsForDelonlta <= MAX_DOC_ID_BIT;
      }

      /**
       * Optimization: storelon frelonq - 1 sincelon a frelonq must belon positivelon. Savelon bits and improvelon deloncoding
       * spelonelond. At relonad sidelon, thelon relonad frelonquelonncy will plus 1.
       * @selonelon HighDFPackelondIntsDocselonnum#loadNelonxtPosting()
       */
      int frelonq = postingselonnum.frelonq() - 1;
      asselonrt frelonq >= 0;

      int nelonwBitsForFrelonq = bitsNelonelondelondForFrelonq;
      if (frelonq > maxFrelonq) {
        maxFrelonq = frelonq;
        nelonwBitsForFrelonq = log(maxFrelonq, 2);
        asselonrt nelonwBitsForFrelonq <= MAX_FRelonQ_BIT;
      }

      // Writelon positions for this doc if not omit positions.
      if (!omitPositions) {
        writelonPositionsForDoc(postingselonnum, positionsStatelon);
      }

      if ((nelonwBitsForDelonlta + nelonwBitsForFrelonq) * (docFrelonqQuelonuelon.sizelon() + 1) > NUM_BITS_PelonR_SLICelon) {
        //Thelon latelonst doc doelons not fit into this slicelon.
        asselonrt (bitsNelonelondelondForDelonlta + bitsNelonelondelondForFrelonq) * docFrelonqQuelonuelon.sizelon()
            <= NUM_BITS_PelonR_SLICelon;

        prelonvWrittelonnDoc = writelonDelonltaFrelonqSlicelon(
            bitsNelonelondelondForDelonlta,
            bitsNelonelondelondForFrelonq,
            positionsStatelon,
            prelonvWrittelonnDoc);
        numDelonltaFrelonqSlicelons++;

        maxDelonlta = delonlta;
        maxFrelonq = frelonq;
        bitsNelonelondelondForDelonlta = log(maxDelonlta, 2);
        bitsNelonelondelondForFrelonq = log(maxFrelonq, 2);
      } elonlselon {
        bitsNelonelondelondForDelonlta = nelonwBitsForDelonlta;
        bitsNelonelondelondForFrelonq = nelonwBitsForFrelonq;
      }

      docFrelonqQuelonuelon.offelonr(doc, frelonq);

      prelonvDoc = doc;
    }

    // Somelon positions may belon lelonft in thelon buffelonr quelonuelon.
    if (!positionQuelonuelon.iselonmpty()) {
      writelonPositionSlicelon(positionsStatelon.bitsNelonelondelondForPosition);
    }

    // Somelon docs may belon lelonft in thelon buffelonr quelonuelon.
    if (!docFrelonqQuelonuelon.iselonmpty()) {
      writelonDelonltaFrelonqSlicelon(
          bitsNelonelondelondForDelonlta,
          bitsNelonelondelondForFrelonq,
          positionsStatelon,
          prelonvWrittelonnDoc);
      numDelonltaFrelonqSlicelons++;
    }

    // Writelon skip list helonadelonr.
    int skipListHelonadelonrPointelonr = skipListPointelonr;
    final int numSkipListelonntrielons =
        (skipLists.lelonngth() - (skipListPointelonr + skipListHelonadelonrSizelon)) / skiplistelonntrySizelon;
    asselonrt numSkipListelonntrielons == numDelonltaFrelonqSlicelons
        : "numbelonr of delonlta frelonq slicelons should belon thelon samelon as numbelonr of skip list elonntrielons";
    skipLists.selont(skipListHelonadelonrPointelonr++, numSkipListelonntrielons);
    skipLists.selont(skipListHelonadelonrPointelonr++, prelonvDoc);
    skipLists.selont(skipListHelonadelonrPointelonr++, numDocs);
    skipLists.selont(skipListHelonadelonrPointelonr++, delonltaFrelonqListPointelonr);
    if (!omitPositions) {
      skipLists.selont(skipListHelonadelonrPointelonr, positionListPointelonr);
    }

    relonturn skipListPointelonr;
  }

  /**
   * Writelon positions for currelonnt doc into {@link #positionLists}.
   *
   * @param postingselonnum postings elonnumelonrator containing thelon positions nelonelond to belon writtelonn
   * @param positionsStatelon somelon statelons about {@link #positionLists} and {@link #positionQuelonuelon}
   * @selonelon #copyPostingList(Postingselonnum, int)
   */
  privatelon void writelonPositionsForDoc(
      Postingselonnum postingselonnum,
      PositionsStatelon positionsStatelon) throws IOelonxcelonption {
    asselonrt !omitPositions : "this melonthod should not belon callelond if positions arelon omittelond";

    for (int i = 0; i < postingselonnum.frelonq(); i++) {
      int pos = postingselonnum.nelonxtPosition();

      int nelonwBitsForPosition = positionsStatelon.bitsNelonelondelondForPosition;
      if (pos > positionsStatelon.maxPosition) {
        positionsStatelon.maxPosition = pos;
        nelonwBitsForPosition = log(positionsStatelon.maxPosition, 2);
        asselonrt nelonwBitsForPosition <= MAX_POSITION_BIT;
      }

      if (nelonwBitsForPosition * (positionQuelonuelon.sizelon() + 1)
          > POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR
          || positionQuelonuelon.isFull()) {
        asselonrt positionsStatelon.bitsNelonelondelondForPosition * positionQuelonuelon.sizelon()
            <= POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR;

        writelonPositionSlicelon(positionsStatelon.bitsNelonelondelondForPosition);
        positionsStatelon.numPositionsSlicelons++;

        positionsStatelon.maxPosition = pos;
        positionsStatelon.bitsNelonelondelondForPosition = log(positionsStatelon.maxPosition, 2);
      } elonlselon {
        positionsStatelon.bitsNelonelondelondForPosition = nelonwBitsForPosition;
      }

      // Updatelon first position pointelonr if this position is thelon first position of a doc
      if (i == 0) {
        positionsStatelon.nelonxtPositionsSlicelonIndelonx = positionsStatelon.numPositionsSlicelons;
        positionsStatelon.nelonxtPositionsSlicelonOffselont = positionQuelonuelon.sizelon();
      }

      // Storelons a dummy doc -1 sincelon doc is unuselond in position list.
      positionQuelonuelon.offelonr(-1, pos);
    }
  }

  /**
   * Writelon out all thelon buffelonrelond positions in {@link #positionQuelonuelon} into a position slicelon.
   *
   * @param bitsNelonelondelondForPosition numbelonr of bits uselond for elonach position in this position slicelon
   */
  privatelon void writelonPositionSlicelon(final int bitsNelonelondelondForPosition) {
    asselonrt !omitPositions;
    asselonrt 0 <= bitsNelonelondelondForPosition && bitsNelonelondelondForPosition <= MAX_POSITION_BIT;

    final int lelonngthBelonforelon = positionLists.lelonngth();
    asselonrt isSlicelonStart(lelonngthBelonforelon);

    // First int in this slicelon storelons numbelonr of bits nelonelondelond for position
    // and numbelonr of positions in this slicelon..
    positionLists.add(elonncodelonPositionelonntryHelonadelonr(bitsNelonelondelondForPosition, positionQuelonuelon.sizelon()));

    positionListsWritelonr.jumpToInt(positionLists.lelonngth(), bitsNelonelondelondForPosition);
    whilelon (!positionQuelonuelon.iselonmpty()) {
      int pos = PostingsBuffelonrQuelonuelon.gelontSeloncondValuelon(positionQuelonuelon.poll());
      asselonrt log(pos, 2) <= bitsNelonelondelondForPosition;

      positionListsWritelonr.writelonPackelondInt(pos);
    }

    // Fill up this slicelon in caselon it is only partially fillelond.
    whilelon (positionLists.lelonngth() < lelonngthBelonforelon + SLICelon_SIZelon) {
      positionLists.add(0);
    }

    asselonrt positionLists.lelonngth() - lelonngthBelonforelon == SLICelon_SIZelon;
  }

  /**
   * Writelon out all thelon buffelonrelond docs and frelonquelonncielons in {@link #docFrelonqQuelonuelon} into a delonlta-frelonq
   * slicelon and updatelon thelon skip list elonntry of this slicelon.
   *
   * @param bitsNelonelondelondForDelonlta numbelonr of bits uselond for elonach delonlta in this delonlta-frelonq slicelon
   * @param bitsNelonelondelondForFrelonq numbelonr of bits uselond for elonach frelonq in this delonlta-frelonq slicelon
   * @param positionsStatelon somelon statelons about {@link #positionLists} and {@link #positionQuelonuelon}
   * @param prelonvWrittelonnDoc last doc writtelonn in prelonvious slicelon
   * @relonturn last doc writtelonn in this slicelon
   */
  privatelon int writelonDelonltaFrelonqSlicelon(
      final int bitsNelonelondelondForDelonlta,
      final int bitsNelonelondelondForFrelonq,
      final PositionsStatelon positionsStatelon,
      final int prelonvWrittelonnDoc) {
    asselonrt 0 <= bitsNelonelondelondForDelonlta && bitsNelonelondelondForDelonlta <= MAX_DOC_ID_BIT;
    asselonrt 0 <= bitsNelonelondelondForFrelonq && bitsNelonelondelondForFrelonq <= MAX_FRelonQ_BIT;

    final int lelonngthBelonforelon = delonltaFrelonqLists.lelonngth();
    asselonrt isSlicelonStart(lelonngthBelonforelon);

    writelonSkipListelonntry(prelonvWrittelonnDoc, bitsNelonelondelondForDelonlta, bitsNelonelondelondForFrelonq, positionsStatelon);

    // Kelonelonp track of prelonvious docID so that welon computelon thelon docID delonltas.
    int prelonvDoc = prelonvWrittelonnDoc;

    // A <delonlta|frelonq> pair is storelond as a packelond valuelon.
    final int bitsPelonrPackelondValuelon = bitsNelonelondelondForDelonlta + bitsNelonelondelondForFrelonq;
    delonltaFrelonqListsWritelonr.jumpToInt(delonltaFrelonqLists.lelonngth(), bitsPelonrPackelondValuelon);
    whilelon (!docFrelonqQuelonuelon.iselonmpty()) {
      long valuelon = docFrelonqQuelonuelon.poll();
      int doc = PostingsBuffelonrQuelonuelon.gelontDocID(valuelon);
      int delonlta = doc - prelonvDoc;
      asselonrt log(delonlta, 2) <= bitsNelonelondelondForDelonlta;

      int frelonq = PostingsBuffelonrQuelonuelon.gelontSeloncondValuelon(valuelon);
      asselonrt log(frelonq, 2) <= bitsNelonelondelondForFrelonq;

      // Cast thelon delonlta to long belonforelon lelonft shift to avoid ovelonrflow.
      final long delonltaFrelonqPair = (((long) delonlta) << bitsNelonelondelondForFrelonq) + frelonq;
      delonltaFrelonqListsWritelonr.writelonPackelondLong(delonltaFrelonqPair);
      prelonvDoc = doc;
    }

    // Fill up this slicelon in caselon it is only partially fillelond.
    whilelon (delonltaFrelonqLists.lelonngth() <  lelonngthBelonforelon + SLICelon_SIZelon) {
      delonltaFrelonqLists.add(0);
    }

    positionsStatelon.currelonntPositionsSlicelonIndelonx = positionsStatelon.nelonxtPositionsSlicelonIndelonx;
    positionsStatelon.currelonntPositionsSlicelonOffselont = positionsStatelon.nelonxtPositionsSlicelonOffselont;

    asselonrt delonltaFrelonqLists.lelonngth() - lelonngthBelonforelon == SLICelon_SIZelon;
    relonturn prelonvDoc;
  }

  /**
   * Writelon thelon skip list elonntry for a delonlta-frelonq slicelon.
   *
   * @param prelonvWrittelonnDoc last doc writtelonn in prelonvious slicelon
   * @param bitsNelonelondelondForDelonlta numbelonr of bits uselond for elonach delonlta in this delonlta-frelonq slicelon
   * @param bitsNelonelondelondForFrelonq numbelonr of bits uselond for elonach frelonq in this delonlta-frelonq slicelon
   * @param positionsStatelon somelon statelons about {@link #positionLists} and {@link #positionQuelonuelon}
   * @selonelon #writelonDelonltaFrelonqSlicelon(int, int, PositionsStatelon, int)
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  privatelon void writelonSkipListelonntry(
      int prelonvWrittelonnDoc,
      int bitsNelonelondelondForDelonlta,
      int bitsNelonelondelondForFrelonq,
      PositionsStatelon positionsStatelon) {
    // 1st int: last writtelonn doc ID in prelonvious slicelon
    skipLists.add(prelonvWrittelonnDoc);

    // 2nd int: elonncodelond melontadata
    skipLists.add(
        elonncodelonSkipListelonntryMelontadata(
            positionsStatelon.currelonntPositionsSlicelonOffselont,
            bitsNelonelondelondForDelonlta,
            bitsNelonelondelondForFrelonq,
            docFrelonqQuelonuelon.sizelon()));

    // 3rd int: optional, position slicelon indelonx
    if (!omitPositions) {
      skipLists.add(positionsStatelon.currelonntPositionsSlicelonIndelonx);
    }
  }

  /**
   * Crelonatelon and relonturn a docs elonnumelonrator or docs-positions elonnumelonrator baselond on input flag.
   *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum
   */
  @Ovelonrridelon
  public elonarlybirdPostingselonnum postings(
      int postingListPointelonr, int numPostings, int flags) throws IOelonxcelonption {
    // Positions arelon omittelond but position elonnumelonrator arelon relonqurielond.
    if (omitPositions && Postingselonnum.felonaturelonRelonquelonstelond(flags, Postingselonnum.POSITIONS)) {
      GelonTTING_POSITIONS_WITH_OMIT_POSITIONS.increlonmelonnt();
    }

    if (!omitPositions && Postingselonnum.felonaturelonRelonquelonstelond(flags, Postingselonnum.POSITIONS)) {
      relonturn nelonw HighDFPackelondIntsDocsAndPositionselonnum(
          skipLists,
          delonltaFrelonqLists,
          positionLists,
          postingListPointelonr,
          numPostings,
          falselon);
    } elonlselon {
      relonturn nelonw HighDFPackelondIntsDocselonnum(
          skipLists,
          delonltaFrelonqLists,
          postingListPointelonr,
          numPostings,
          omitPositions);
    }
  }

  /******************************************************
   * Skip list elonntry elonncodelond data elonncoding and deloncoding *
   ******************************************************/

  /**
   * elonncodelon a skip list elonntry melontadata, which is storelond in thelon 2nd int of thelon skip list elonntry.
   *
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  privatelon static int elonncodelonSkipListelonntryMelontadata(
      int positionOffselontInSlicelon, int numBitsForDelonlta, int numBitsForFrelonq, int numDocsInSlicelon) {
    asselonrt 0 <= positionOffselontInSlicelon
        && positionOffselontInSlicelon < POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR;
    asselonrt 0 <= numBitsForDelonlta && numBitsForDelonlta <= MAX_DOC_ID_BIT;
    asselonrt 0 <= numBitsForFrelonq && numBitsForFrelonq <= MAX_FRelonQ_BIT;
    asselonrt 0 < numDocsInSlicelon && numDocsInSlicelon <= NUM_BITS_PelonR_SLICelon;
    relonturn (positionOffselontInSlicelon << SKIPLIST_elonNTRY_POSITION_OFFSelonT_SHIFT)
        + (numBitsForDelonlta << SKIPLIST_elonNTRY_NUM_BITS_DelonLTA_SHIFT)
        + (numBitsForFrelonq << SKIPLIST_elonNTRY_NUM_BITS_FRelonQ_SHIFT)
        // storelons numDocsInSlicelon - 1 to avoid ovelonr flow sincelon numDocsInSlicelon rangelons in [1, 2048]
        // and 11 bits arelon uselond to storelon numbelonr docs in slicelon
        + (numDocsInSlicelon - 1);
  }

  /**
   * Deloncodelon POSITION_SLICelon_OFFSelonT of thelon delonlta-frelonq slicelon having thelon givelonn skip elonntry elonncodelond data.
   *
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  static int gelontPositionOffselontInSlicelon(int skipListelonntryelonncodelondMelontadata) {
    relonturn (skipListelonntryelonncodelondMelontadata >>> SKIPLIST_elonNTRY_POSITION_OFFSelonT_SHIFT)
        & SKIPLIST_elonNTRY_POSITION_OFFSelonT_MASK;
  }

  /**
   * Deloncodelon numbelonr of bits uselond for delonlta in thelon slicelon having thelon givelonn skip elonntry elonncodelond data.
   *
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  static int gelontNumBitsForDelonlta(int skipListelonntryelonncodelondMelontadata) {
    relonturn (skipListelonntryelonncodelondMelontadata >>> SKIPLIST_elonNTRY_NUM_BITS_DelonLTA_SHIFT)
        & SKIPLIST_elonNTRY_NUM_BITS_DelonLTA_MASK;
  }

  /**
   * Deloncodelon numbelonr of bits uselond for frelonqs in thelon slicelon having thelon givelonn skip elonntry elonncodelond data.
   *
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  static int gelontNumBitsForFrelonq(int skipListelonntryelonncodelondMelontadata) {
    relonturn (skipListelonntryelonncodelondMelontadata >>> SKIPLIST_elonNTRY_NUM_BITS_FRelonQ_SHIFT)
        & SKIPLIST_elonNTRY_NUM_BITS_FRelonQ_MASK;
  }

  /**
   * Deloncodelon numbelonr of delonlta-frelonq pairs storelond in thelon slicelon having thelon givelonn skip elonntry elonncodelond data.
   *
   * @selonelon #SKIPLIST_elonNTRY_SIZelon
   */
  static int gelontNumDocsInSlicelon(int skipListelonntryelonncodelondMelontadata) {
    /**
     * Add 1 to thelon deloncodelon valuelon sincelon thelon storelond valuelon is subtractelond by 1.
     * @selonelon #elonncodelonSkipListelonntryMelontadata(int, int, int, int)
     */
    relonturn (skipListelonntryelonncodelondMelontadata & SKIPLIST_elonNTRY_NUM_DOCS_MASK) + 1;
  }

  /*****************************************************
   * Position slicelon elonntry helonadelonr elonncoding and deloncoding *
   *****************************************************/

  /**
   * elonncodelon a position slicelon elonntry helonadelonr.
   *
   * @param numBitsForPosition numbelonr of bits uselond to elonncodelon positions in this slicelon.
   * @param numPositionsInSlicelon numbelonr of positions in this slicelon.
   * @relonturn an int as thelon elonncodelond helonadelonr.
   * @selonelon #POSITION_SLICelon_HelonADelonR_SIZelon
   */
  privatelon static int elonncodelonPositionelonntryHelonadelonr(int numBitsForPosition, int numPositionsInSlicelon) {
    asselonrt 0 <= numBitsForPosition && numBitsForPosition <= MAX_POSITION_BIT;
    asselonrt 0 < numPositionsInSlicelon && numPositionsInSlicelon <= POSITION_SLICelon_NUM_BITS_WITHOUT_HelonADelonR;
    relonturn (numBitsForPosition << POSITION_SLICelon_HelonADelonR_BITS_POSITION_SHIFT) + numPositionsInSlicelon;
  }

  /**
   * Deloncodelon numbelonr of bits uselond for position in thelon slicelon having thelon givelonn helonadelonr.
   *
   * @param positionelonntryHelonadelonr elonntry helonadelonr will belon deloncodelond.
   * @selonelon #POSITION_SLICelon_HelonADelonR_SIZelon
   */
  static int gelontNumBitsForPosition(int positionelonntryHelonadelonr) {
    relonturn (positionelonntryHelonadelonr >>> POSITION_SLICelon_HelonADelonR_BITS_POSITION_SHIFT)
        & POSITION_SLICelon_HelonADelonR_BITS_POSITION_MASK;
  }

  /**
   * Deloncodelon numbelonr of positions storelond in thelon slicelon having thelon givelonn helonadelonr.
   *
   * @param positionelonntryHelonadelonr elonntry helonadelonr will belon deloncodelond.
   * @selonelon #POSITION_SLICelon_HelonADelonR_SIZelon
   */
  static int gelontNumPositionsInSlicelon(int positionelonntryHelonadelonr) {
    relonturn positionelonntryHelonadelonr & POSITION_SLICelon_HelonADelonR_NUM_POSITIONS_MASK;
  }

  /******************
   * Helonlpelonr melonthods *
   ******************/

  /**
   * Chelonck if givelonn pointelonr is pointing to thelon slicelon start.
   *
   * @param pointelonr thelon indelonx will belon chelonckelond.
   */
  static boolelonan isSlicelonStart(int pointelonr) {
    relonturn pointelonr % HighDFPackelondIntsPostingLists.SLICelon_SIZelon == 0;
  }

  /**
   * Celonil of log of x in thelon givelonn baselon.
   *
   * @relonturn x == 0 ? 0 : Math.celonil(Math.log(x) / Math.log(baselon))
   */
  privatelon static int log(int x, int baselon) {
    asselonrt baselon >= 2;
    if (x == 0) {
      relonturn 0;
    }
    int relont = 1;
    long n = baselon; // nelonelonds to belon a long to avoid ovelonrflow
    whilelon (x >= n) {
      n *= baselon;
      relont++;
    }
    relonturn relont;
  }

  /**********************
   * For flush and load *
   **********************/

  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<HighDFPackelondIntsPostingLists> {
    privatelon static final String OMIT_POSITIONS_PROP_NAMelon = "omitPositions";
    privatelon static final String SKIP_LISTS_PROP_NAMelon = "skipLists";
    privatelon static final String DelonLTA_FRelonQ_LISTS_PROP_NAMelon = "delonltaFrelonqLists";
    privatelon static final String POSITION_LISTS_PROP_NAMelon = "positionLists";

    public FlushHandlelonr() {
      supelonr();
    }

    public FlushHandlelonr(HighDFPackelondIntsPostingLists objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out)
        throws IOelonxcelonption {
      HighDFPackelondIntsPostingLists objelonctToFlush = gelontObjelonctToFlush();
      flushInfo.addBoolelonanPropelonrty(OMIT_POSITIONS_PROP_NAMelon, objelonctToFlush.omitPositions);
      objelonctToFlush.skipLists.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(SKIP_LISTS_PROP_NAMelon), out);
      objelonctToFlush.delonltaFrelonqLists.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(DelonLTA_FRelonQ_LISTS_PROP_NAMelon), out);
      objelonctToFlush.positionLists.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(POSITION_LISTS_PROP_NAMelon), out);
    }

    @Ovelonrridelon
    protelonctelond HighDFPackelondIntsPostingLists doLoad(
        FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      IntBlockPool skipLists = (nelonw IntBlockPool.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons(SKIP_LISTS_PROP_NAMelon), in);
      IntBlockPool delonltaFrelonqLists = (nelonw IntBlockPool.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons(DelonLTA_FRelonQ_LISTS_PROP_NAMelon), in);
      IntBlockPool positionLists = (nelonw IntBlockPool.FlushHandlelonr())
          .load(flushInfo.gelontSubPropelonrtielons(POSITION_LISTS_PROP_NAMelon), in);
      relonturn nelonw HighDFPackelondIntsPostingLists(
          skipLists,
          delonltaFrelonqLists,
          positionLists,
          flushInfo.gelontBoolelonanPropelonrty(OMIT_POSITIONS_PROP_NAMelon),
          null,
          null);
    }
  }
}
