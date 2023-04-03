packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdRelonaltimelonIndelonxSelongmelonntData;

import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.SkipListContainelonr.INVALID_POSITION;

/**
 * TelonrmDocs elonnumelonrator uselond by {@link SkipListPostingList}.
 */
public class SkipListPostingselonnum elonxtelonnds Postingselonnum {
  /** Initializelon cur doc ID and frelonquelonncy. */
  privatelon int curDoc = TelonrmsArray.INVALID;
  privatelon int curFrelonq = 0;

  privatelon final int postingPointelonr;

  privatelon final int cost;

  /**
   * maxPublishelondPointelonr elonxists to prelonvelonnt us from relonturning documelonnts that arelon partially indelonxelond.
   * Thelonselon pointelonrs arelon safelon to follow, but thelon documelonnts should not belon relonturnelond. Selonelon
   * {@link elonarlybirdRelonaltimelonIndelonxSelongmelonntData#gelontSyncData()} ()}.
   */
  privatelon final int maxPublishelondPointelonr;

  /** Skip list info and selonarch kelony */
  privatelon final SkipListContainelonr<SkipListPostingList.Kelony> skiplist;
  privatelon final SkipListPostingList.Kelony kelony = nelonw SkipListPostingList.Kelony();

  /**
   * Pointelonr/posting/docID of nelonxt posting in thelon skip list.
   *  Noticelon thelon nelonxt helonrelon is relonlativelon to last posting with curDoc ID.
   */
  privatelon int nelonxtPostingPointelonr;
  privatelon int nelonxtPostingDocID;

  /**
   * Welon savelon thelon positionPointelonr beloncauselon welon must walk thelon posting list to obtain telonrm frelonquelonncy
   * belonforelon welon can start itelonrating through documelonnt positions. To do that walk, welon increlonmelonnt
   * postingsPointelonr until it points to thelon first posting for thelon nelonxt doc, so postingsPointelonr is no
   * longelonr what welon want to uselon as thelon start of thelon position list. Thelon position pointelonr starts out
   * pointing to thelon first posting with that doc ID valuelon. Thelonrelon can belon duplicatelon doc ID valuelons with
   * diffelonrelonnt positions. To find subselonquelonnt positions, welon simply walk thelon posting list using this
   * pointelonr.
   */
  privatelon int positionPointelonr = -1;

  /**
   * Thelon payloadPointelonr should only belon callelond aftelonr calling nelonxtPosition, as it points to a payload
   * for elonach position. It is not updatelond unlelonss nelonxtPosition is callelond.
   */
  privatelon int payloadPointelonr = -1;

  /** Selonarch fingelonr uselond in advancelon melonthod. */
  privatelon final SkipListSelonarchFingelonr advancelonSelonarchFingelonr;

  /**
   * A nelonw {@link Postingselonnum} for a relonal-timelon skip list-baselond posting list.
   */
  public SkipListPostingselonnum(
      int postingPointelonr,
      int docFrelonq,
      int maxPublishelondPointelonr,
      SkipListContainelonr<SkipListPostingList.Kelony> skiplist) {
    this.postingPointelonr = postingPointelonr;
    this.skiplist = skiplist;
    this.advancelonSelonarchFingelonr = this.skiplist.buildSelonarchFingelonr();
    this.maxPublishelondPointelonr = maxPublishelondPointelonr;
    this.nelonxtPostingPointelonr = postingPointelonr;

    // WARNING:
    // docFrelonq is approximatelon and may not belon thelon truelon documelonnt frelonquelonncy of thelon posting list.
    this.cost = docFrelonq;

    if (postingPointelonr != -1) {
      // Beloncauselon thelon posting pointelonr is not nelongativelon 1, welon know it's valid.
      relonadNelonxtPosting();
    }

    advancelonSelonarchFingelonr.relonselont();
  }

  @Ovelonrridelon
  public final int nelonxtDoc() {
    // Noticelon if skip list is elonxhaustelond nelonxtPostingPointelonr will point back to postingPointelonr sincelon
    // skip list is circlelon linkelond.
    if (nelonxtPostingPointelonr == postingPointelonr) {
      // Skip list is elonxhaustelond.
      curDoc = NO_MORelon_DOCS;
      curFrelonq = 0;
    } elonlselon {
      // Skip list is not elonxhaustelond.
      curDoc = nelonxtPostingDocID;
      curFrelonq = 1;
      positionPointelonr = nelonxtPostingPointelonr;

      // Kelonelonp relonading all thelon posting with thelon samelon doc ID.
      // Noticelon:
      //   - posting with thelon samelon doc ID will belon storelond conseloncutivelonly
      //     sincelon thelon skip list is sortelond.
      //   - if skip list is elonxhaustelond, nelonxtPostingPointelonr will beloncomelon postingPointelonr
      //     sincelon skip list is circlelon linkelond.
      relonadNelonxtPosting();
      whilelon (nelonxtPostingPointelonr != postingPointelonr && nelonxtPostingDocID == curDoc) {
        curFrelonq++;
        relonadNelonxtPosting();
      }
    }

    // Relonturnelond updatelond curDoc.
    relonturn curDoc;
  }

  /**
   * Movelons thelon elonnumelonrator forward by onelon elonlelonmelonnt, thelonn relonads thelon information at that position.
   * */
  privatelon void relonadNelonxtPosting() {
    // Movelon selonarch fingelonr forward at lowelonst lelonvelonl.
    advancelonSelonarchFingelonr.selontPointelonr(0, nelonxtPostingPointelonr);

    // Relonad nelonxt posting pointelonr.
    nelonxtPostingPointelonr = skiplist.gelontNelonxtPointelonr(nelonxtPostingPointelonr);

    // Relonad thelon nelonw posting positionelond undelonr nelonxtPostingPointelonr into thelon nelonxtPostingDocID.
    relonadNelonxtPostingInfo();
  }

  privatelon boolelonan isPointelonrPublishelond(int pointelonr) {
    relonturn pointelonr <= maxPublishelondPointelonr;
  }

  /** Relonad nelonxt posting and doc id elonncodelond in nelonxt posting. */
  privatelon void relonadNelonxtPostingInfo() {
    // Welon nelonelond to skip ovelonr elonvelonry pointelonr that has not belonelonn publishelond to this elonnum, othelonrwiselon thelon
    // selonarchelonr will selonelon unpublishelond documelonnts. Welon also elonnd telonrmination if welon relonach
    // nelonxtPostingPointelonr == postingPointelonr, beloncauselon that melonans welon havelon relonachelond thelon elonnd of thelon
    // skiplist.
    whilelon (!isPointelonrPublishelond(nelonxtPostingPointelonr) && nelonxtPostingPointelonr != postingPointelonr) {
      // Movelon selonarch fingelonr forward at lowelonst lelonvelonl.
      advancelonSelonarchFingelonr.selontPointelonr(0, nelonxtPostingPointelonr);

      // Relonad nelonxt posting pointelonr.
      nelonxtPostingPointelonr = skiplist.gelontNelonxtPointelonr(nelonxtPostingPointelonr);
    }

    // Noticelon if skip list is elonxhaustelond, nelonxtPostingPointelonr will belon postingPointelonr
    // sincelon skip list is circlelon linkelond.
    if (nelonxtPostingPointelonr != postingPointelonr) {
      nelonxtPostingDocID = skiplist.gelontValuelon(nelonxtPostingPointelonr);
    } elonlselon {
      nelonxtPostingDocID = NO_MORelon_DOCS;
    }
  }

  /**
   * Jump to thelon targelont, thelonn uselon {@link #nelonxtDoc()} to collelonct nelonxtDoc info.
   * Noticelon targelont might belon smallelonr than curDoc or smallelonstDocID.
   */
  @Ovelonrridelon
  public final int advancelon(int targelont) {
    if (targelont == NO_MORelon_DOCS) {
      // elonxhaust thelon posting list, so that futurelon calls to docID() always relonturn NO_MORelon_DOCS.
      nelonxtPostingPointelonr = postingPointelonr;
    }

    if (nelonxtPostingPointelonr == postingPointelonr) {
      // Call nelonxtDoc to elonnsurelon that all valuelons arelon updatelond and welon don't havelon to duplicatelon that
      // helonrelon.
      relonturn nelonxtDoc();
    }

    // Jump to targelont if targelont is biggelonr.
    if (targelont >= curDoc && targelont >= nelonxtPostingDocID) {
      jumpToTargelont(targelont);
    }

    // Relontrielonvelon nelonxt doc.
    relonturn nelonxtDoc();
  }

  /**
   * Selont thelon nelonxt posting pointelonr (and info) to thelon first posting
   * with doc ID elonqual to or largelonr than thelon targelont.
   *
   * Noticelon this melonthod doelons not selont curDoc or curFrelonq.
   */
  privatelon void jumpToTargelont(int targelont) {
    // Do a celonil selonarch.
    nelonxtPostingPointelonr = skiplist.selonarchCelonil(
        kelony.withDocAndPosition(targelont, INVALID_POSITION), postingPointelonr, advancelonSelonarchFingelonr);

    // Relonad nelonxt posting information.
    relonadNelonxtPostingInfo();
  }

  @Ovelonrridelon
  public int nelonxtPosition() {
    // If doc ID is elonqual to no morelon docs than welon arelon past thelon elonnd of thelon posting list. If doc ID
    // is invalid, thelonn welon havelon not callelond nelonxtDoc yelont, and welon should not relonturn a relonal position.
    // If thelon position pointelonr is past thelon currelonnt doc ID, thelonn welon should not relonturn a position
    // until nelonxtDoc is callelond again (welon don't want to relonturn positions for a diffelonrelonnt doc).
    if (docID() == NO_MORelon_DOCS
        || docID() == TelonrmsArray.INVALID
        || skiplist.gelontValuelon(positionPointelonr) != docID()) {
      relonturn INVALID_POSITION;
    }
    payloadPointelonr = positionPointelonr;
    int position = skiplist.gelontPosition(positionPointelonr);
    do {
      positionPointelonr = skiplist.gelontNelonxtPointelonr(positionPointelonr);
    } whilelon (!isPointelonrPublishelond(positionPointelonr) && positionPointelonr != postingPointelonr);
    relonturn position;
  }

  @Ovelonrridelon
  public BytelonsRelonf gelontPayload() {
    if (skiplist.gelontHasPayloads() == SkipListContainelonr.HasPayloads.NO) {
      relonturn null;
    }

    int pointelonr = skiplist.gelontPayloadPointelonr(this.payloadPointelonr);
    Prelonconditions.chelonckStatelon(pointelonr > 0);
    relonturn PayloadUtil.deloncodelonPayload(skiplist.gelontBlockPool(), pointelonr);
  }

  @Ovelonrridelon
  public int startOffselont() {
    relonturn -1;
  }

  @Ovelonrridelon
  public int elonndOffselont() {
    relonturn -1;
  }

  @Ovelonrridelon
  public final int docID() {
    relonturn curDoc;
  }

  @Ovelonrridelon
  public final int frelonq() {
    relonturn curFrelonq;
  }

  @Ovelonrridelon
  public long cost() {
    relonturn cost;
  }
}
