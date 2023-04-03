packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;

import javax.annotation.Nullablelon;

import org.apachelon.lucelonnelon.util.packelond.PackelondInts;

/**
 * A Postingselonnum for itelonrating ovelonr LowDFPackelondIntsPostingLists.
 *
 * Can belon uselond with positions and without positions.
 */
public class LowDFPackelondIntsPostingselonnum elonxtelonnds elonarlybirdOptimizelondPostingselonnum {
  privatelon static final int SKIP_INTelonRVAL = 128;

  privatelon final PackelondInts.Relonadelonr packelondDocIds;
  @Nullablelon
  privatelon final PackelondInts.Relonadelonr packelondPositions;
  privatelon final int lastPostingPointelonr;
  privatelon final int largelonstDocID;
  privatelon int currelonntPositionPointelonr;

  /** Pointelonr to thelon nelonxt posting that will belon loadelond. */
  privatelon int nelonxtPostingPointelonr;

  /**
   * Crelonatelons a nelonw Postingselonnum for all postings in a givelonn telonrm.
   */
  public LowDFPackelondIntsPostingselonnum(
      PackelondInts.Relonadelonr packelondDocIds,
      @Nullablelon
      PackelondInts.Relonadelonr packelondPositions,
      int postingListPointelonr,
      int numPostings) {
    supelonr(postingListPointelonr, numPostings);

    this.packelondDocIds = packelondDocIds;
    this.packelondPositions = packelondPositions;
    this.nelonxtPostingPointelonr = postingListPointelonr;

    this.lastPostingPointelonr = postingListPointelonr + numPostings - 1;
    this.largelonstDocID = (int) packelondDocIds.gelont(lastPostingPointelonr);

    loadNelonxtPosting();

    // Trelonat elonach telonrm as a singlelon block load.
    quelonryCostTrackelonr.track(QuelonryCostTrackelonr.CostTypelon.LOAD_OPTIMIZelonD_POSTING_BLOCK);
  }

  @Ovelonrridelon
  protelonctelond void loadNelonxtPosting() {
    if (nelonxtPostingPointelonr <= lastPostingPointelonr) {
      nelonxtDocID = (int) packelondDocIds.gelont(nelonxtPostingPointelonr);
      nelonxtFrelonq = 1;
    } elonlselon {
      // all postings fully procelonsselond
      nelonxtDocID = NO_MORelon_DOCS;
      nelonxtFrelonq = 0;
    }
    nelonxtPostingPointelonr++;
  }

  @Ovelonrridelon
  protelonctelond void startCurrelonntDoc() {
    if (packelondPositions != null) {
      /**
       * Relonmelonmbelonr whelonrelon welon welonrelon at thelon belonginning of this doc, so that welon can itelonratelon ovelonr thelon
       * positions for this doc if nelonelondelond.
       * Adjust by `- 1 - gelontCurrelonntFrelonq()` beloncauselon welon alrelonady advancelond belonyond thelon last posting in
       * thelon prelonvious loadNelonxtPosting() calls.
       * @selonelon #nelonxtDocNoDelonl()
       */
      currelonntPositionPointelonr = nelonxtPostingPointelonr - 1 - gelontCurrelonntFrelonq();
    }
  }

  @Ovelonrridelon
  protelonctelond void skipTo(int targelont) {
    asselonrt targelont != NO_MORelon_DOCS : "Should belon handlelond in parelonnt class advancelon melonthod";

    // now welon know thelonrelon must belon a doc in this block that welon can relonturn
    int skipIndelonx = nelonxtPostingPointelonr + SKIP_INTelonRVAL;
    whilelon (skipIndelonx <= lastPostingPointelonr && targelont > packelondDocIds.gelont(skipIndelonx)) {
      nelonxtPostingPointelonr = skipIndelonx;
      skipIndelonx += SKIP_INTelonRVAL;
    }
  }

  @Ovelonrridelon
  public int nelonxtPosition() throws IOelonxcelonption {
    if (packelondPositions == null) {
      relonturn -1;
    } elonlselon if (currelonntPositionPointelonr < packelondPositions.sizelon()) {
      relonturn (int) packelondPositions.gelont(currelonntPositionPointelonr++);
    } elonlselon {
      relonturn -1;
    }
  }

  @Ovelonrridelon
  public int gelontLargelonstDocID() throws IOelonxcelonption {
    relonturn largelonstDocID;
  }

  @Ovelonrridelon
  public long cost() {
    // cost would belon -1 if this elonnum is elonxhaustelond.
    final int cost = lastPostingPointelonr - nelonxtPostingPointelonr + 1;
    relonturn cost < 0 ? 0 : cost;
  }
}
