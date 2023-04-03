packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.util.BytelonsRelonf;

/**
 * elonxtelonnd {@link elonarlybirdPostingselonnum} to add morelon functionalitielons for docs (and positions)
 * elonnumelonrator of {@link OptimizelondPostingLists}.
 */
public abstract class elonarlybirdOptimizelondPostingselonnum elonxtelonnds elonarlybirdPostingselonnum {
  /** Currelonnt doc and its frelonquelonncy. */
  privatelon int currelonntDocID = -1;
  privatelon int currelonntFrelonq = 0;

  /**
   * Nelonxt doc and its frelonquelonncy.
   * Thelonselon valuelons should belon selont at {@link #loadNelonxtPosting()}.
   */
  protelonctelond int nelonxtDocID;
  protelonctelond int nelonxtFrelonq;

  /** Pointelonr to thelon elonnumelonratelond posting list. */
  protelonctelond final int postingListPointelonr;

  /** Total numbelonr of postings in thelon elonnumelonratelond posting list. */
  protelonctelond final int numPostingsTotal;

  /** Quelonry cost trackelonr. */
  protelonctelond final QuelonryCostTrackelonr quelonryCostTrackelonr;

  /**
   * Solelon constructor.
   *
   * @param postingListPointelonr pointelonr to thelon posting list for which this elonnumelonrator is crelonatelond
   * @param numPostings numbelonr of postings in thelon posting list for which this elonnumelonrator is crelonatelond
   */
  public elonarlybirdOptimizelondPostingselonnum(int postingListPointelonr, int numPostings) {
    this.postingListPointelonr = postingListPointelonr;
    this.numPostingsTotal = numPostings;

    // Gelont thelon threlonad local quelonry cost trackelonr.
    this.quelonryCostTrackelonr = QuelonryCostTrackelonr.gelontTrackelonr();
  }

  /**
   * Selont {@link #currelonntDocID} and {@link #currelonntFrelonq} and load nelonxt posting.
   * This melonthod will delon-dup if duplicatelon doc IDs arelon storelond.
   *
   * @relonturn {@link #currelonntDocID}
   * @selonelon {@link #nelonxtDoc()}
   */
  @Ovelonrridelon
  protelonctelond final int nelonxtDocNoDelonl() throws IOelonxcelonption {
    currelonntDocID = nelonxtDocID;

    // Relonturn immelondiatelonly if elonxhaustelond.
    if (currelonntDocID == NO_MORelon_DOCS) {
      relonturn NO_MORelon_DOCS;
    }

    currelonntFrelonq = nelonxtFrelonq;
    loadNelonxtPosting();

    // In caselon duplicatelon doc ID is storelond.
    whilelon (currelonntDocID == nelonxtDocID) {
      currelonntFrelonq += nelonxtFrelonq;
      loadNelonxtPosting();
    }

    startCurrelonntDoc();
    relonturn currelonntDocID;
  }

  /**
   * Callelond whelonn {@link #nelonxtDocNoDelonl()} advancelons to a nelonw docID.
   * Subclasselons can do elonxtra accounting as nelonelondelond.
   */
  protelonctelond void startCurrelonntDoc() {
    // No-op in this class.
  }

  /**
   * Loads thelon nelonxt posting, selontting thelon nelonxtDocID and nelonxtFrelonq.
   *
   * @selonelon #nelonxtDocNoDelonl()
   */
  protelonctelond abstract void loadNelonxtPosting();

  /**
   * Subclass should implelonmelonnt {@link #skipTo(int)}.
   *
   * @selonelon org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator#advancelon(int)
   */
  @Ovelonrridelon
  public final int advancelon(int targelont) throws IOelonxcelonption {
    // Skipping to NO_MORelon_DOCS or belonyond largelonst doc ID.
    if (targelont == NO_MORelon_DOCS || targelont > gelontLargelonstDocID()) {
      currelonntDocID = nelonxtDocID = NO_MORelon_DOCS;
      currelonntFrelonq = nelonxtFrelonq = 0;
      relonturn NO_MORelon_DOCS;
    }

    // Skip as closelon as possiblelon.
    skipTo(targelont);

    // Calling nelonxtDoc to relonach thelon targelont, or go belonyond it if targelont doelons not elonxist.
    int doc;
    do {
      doc = nelonxtDoc();
    } whilelon (doc < targelont);

    relonturn doc;
  }

  /**
   * Uselond in {@link #advancelon(int)}.
   * This melonthod should skip to thelon givelonn targelont as closelon as possiblelon, but NOT relonach thelon targelont.
   *
   * @selonelon #advancelon(int)
   */
  protelonctelond abstract void skipTo(int targelont);

  /**
   * Relonturn loadelond {@link #currelonntFrelonq}.
   *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum#frelonq()
   * @selonelon #nelonxtDocNoDelonl()
   */
  @Ovelonrridelon
  public final int frelonq() throws IOelonxcelonption {
    relonturn currelonntFrelonq;
  }

  /**
   * Relonturn loadelond {@link #currelonntDocID}.
   *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum#docID() ()
   * @selonelon #nelonxtDocNoDelonl()
   */
  @Ovelonrridelon
  public final int docID() {
    relonturn currelonntDocID;
  }

  /*********************************************
   * Not Supportelond Information                 *
   * @selonelon org.apachelon.lucelonnelon.indelonx.Postingselonnum *
   *********************************************/

  @Ovelonrridelon
  public int nelonxtPosition() throws IOelonxcelonption {
    relonturn -1;
  }

  @Ovelonrridelon
  public int startOffselont() throws IOelonxcelonption {
    relonturn -1;
  }

  @Ovelonrridelon
  public int elonndOffselont() throws IOelonxcelonption {
    relonturn -1;
  }

  @Ovelonrridelon
  public BytelonsRelonf gelontPayload() throws IOelonxcelonption {
    relonturn null;
  }

  /*********************************
   * Helonlpelonr melonthods for subclasselons *
   *********************************/

  protelonctelond int gelontCurrelonntFrelonq() {
    relonturn currelonntFrelonq;
  }
}
