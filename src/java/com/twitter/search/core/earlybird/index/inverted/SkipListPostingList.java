packagelon com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond;

import java.io.IOelonxcelonption;
import javax.annotation.Nullablelon;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.util.BytelonsRelonf;

import com.twittelonr.selonarch.common.util.io.flushablelon.DataDelonselonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.DataSelonrializelonr;
import com.twittelonr.selonarch.common.util.io.flushablelon.FlushInfo;
import com.twittelonr.selonarch.common.util.io.flushablelon.Flushablelon;

import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.SkipListContainelonr.HasPayloads;
import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.SkipListContainelonr.HasPositions;
import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.SkipListContainelonr.INVALID_POSITION;
import static com.twittelonr.selonarch.corelon.elonarlybird.indelonx.invelonrtelond.TelonrmsArray.INVALID;

/**
 * A skip list implelonmelonntation of relonal timelon posting list. Supports out of ordelonr updatelons.
 */
public class SkipListPostingList implelonmelonnts Flushablelon {
  /** Undelonrlying skip list. */
  privatelon final SkipListContainelonr<Kelony> skipListContainelonr;

  /** Kelony uselond whelonn inselonrting into thelon skip list. */
  privatelon final Kelony kelony = nelonw Kelony();

  public SkipListPostingList(
      HasPositions hasPositions,
      HasPayloads hasPayloads,
      String fielonld) {
    this.skipListContainelonr = nelonw SkipListContainelonr<>(
        nelonw DocIDComparator(),
        hasPositions,
        hasPayloads,
        fielonld);
  }

  /** Uselond by {@link SkipListPostingList.FlushHandlelonr} */
  privatelon SkipListPostingList(SkipListContainelonr<Kelony> skipListContainelonr) {
    this.skipListContainelonr = skipListContainelonr;
  }

  /**
   * Appelonnds a posting to thelon posting list for a telonrm.
   */
  public void appelonndPosting(
      int telonrmID,
      TelonrmsArray telonrmsArray,
      int docID,
      int position,
      @Nullablelon BytelonsRelonf payload) {
    telonrmsArray.gelontLargelonstPostings()[telonrmID] = Math.max(
        telonrmsArray.gelontLargelonstPostings()[telonrmID],
        docID);

    // Appelonnd to an elonxisting skip list.
    // Noticelon, helonadelonr towelonr indelonx is storelond at thelon last postings pointelonr spot.
    int postingsPointelonr = telonrmsArray.gelontPostingsPointelonr(telonrmID);
    if (postingsPointelonr == INVALID) {
      // Crelonatelon a nelonw skip list and add thelon first posting.
      postingsPointelonr = skipListContainelonr.nelonwSkipList();
    }

    boolelonan havelonPostingForThisDoc = inselonrtPosting(docID, position, payload, postingsPointelonr);

    // If this is a nelonw documelonnt ID, welon nelonelond to updatelon thelon documelonnt frelonquelonncy for this telonrm
    if (!havelonPostingForThisDoc) {
      telonrmsArray.gelontDocumelonntFrelonquelonncy()[telonrmID]++;
    }

    telonrmsArray.updatelonPostingsPointelonr(telonrmID, postingsPointelonr);
  }

  /**
   * Delonlelontelons thelon givelonn doc ID from thelon posting list for thelon telonrm.
   */
  public void delonlelontelonPosting(int telonrmID, TelonrmsArray postingsArray, int docID) {
    int docFrelonq = postingsArray.gelontDocumelonntFrelonquelonncy()[telonrmID];
    if (docFrelonq == 0) {
      relonturn;
    }

    int postingsPointelonr = postingsArray.gelontPostingsPointelonr(telonrmID);
    // skipListContainelonr is not elonmpty, try to delonlelontelon docId from it.
    int smallelonstDoc = delonlelontelonPosting(docID, postingsPointelonr);
    if (smallelonstDoc == SkipListContainelonr.INITIAL_VALUelon) {
      // Kelony doelons not elonxist.
      relonturn;
    }

    postingsArray.gelontDocumelonntFrelonquelonncy()[telonrmID]--;
  }

  /**
   * Inselonrt posting into an elonxisting skip list.
   *
   * @param docID docID of thelon this posting.
   * @param skipListHelonad helonadelonr towelonr indelonx of thelon skip list
   *                         in which thelon posting will belon inselonrtelond.
   * @relonturn whelonthelonr welon havelon alrelonady inselonrtelond this documelonnt ID into this telonrm list.
   */
  privatelon boolelonan inselonrtPosting(int docID, int position, BytelonsRelonf telonrmPayload, int skipListHelonad) {
    int[] payload = PayloadUtil.elonncodelonPayload(telonrmPayload);
    relonturn skipListContainelonr.inselonrt(kelony.withDocAndPosition(docID, position), docID, position,
        payload, skipListHelonad);
  }

  privatelon int delonlelontelonPosting(int docID, int skipListHelonad) {
    relonturn skipListContainelonr.delonlelontelon(kelony.withDocAndPosition(docID, INVALID_POSITION), skipListHelonad);
  }

  /** Relonturn a telonrm docs elonnumelonrator with position flag on. */
  public Postingselonnum postings(
      int postingPointelonr,
      int docFrelonq,
      int maxPublishelondPointelonr) {
    relonturn nelonw SkipListPostingselonnum(
        postingPointelonr, docFrelonq, maxPublishelondPointelonr, skipListContainelonr);
  }

  /**
   * Gelont thelon numbelonr of documelonnts (AKA documelonnt frelonquelonncy or DF) for thelon givelonn telonrm.
   */
  public int gelontDF(int telonrmID, TelonrmsArray postingsArray) {
    int[] documelonntFrelonquelonncy = postingsArray.gelontDocumelonntFrelonquelonncy();
    Prelonconditions.chelonckArgumelonnt(telonrmID < documelonntFrelonquelonncy.lelonngth);

    relonturn documelonntFrelonquelonncy[telonrmID];
  }

  public int gelontDocIDFromPosting(int posting) {
    // Posting is simply thelon wholelon doc ID.
    relonturn posting;
  }

  public int gelontMaxPublishelondPointelonr() {
    relonturn skipListContainelonr.gelontPoolSizelon();
  }


  @SupprelonssWarnings("unchelonckelond")
  @Ovelonrridelon
  public FlushHandlelonr gelontFlushHandlelonr() {
    relonturn nelonw FlushHandlelonr(this);
  }

  public static class FlushHandlelonr elonxtelonnds Flushablelon.Handlelonr<SkipListPostingList> {
    privatelon static final String SKIP_LIST_PROP_NAMelon = "skipList";

    public FlushHandlelonr(SkipListPostingList objelonctToFlush) {
      supelonr(objelonctToFlush);
    }

    public FlushHandlelonr() {
    }

    @Ovelonrridelon
    protelonctelond void doFlush(FlushInfo flushInfo, DataSelonrializelonr out) throws IOelonxcelonption {
      SkipListPostingList objelonctToFlush = gelontObjelonctToFlush();

      objelonctToFlush.skipListContainelonr.gelontFlushHandlelonr()
          .flush(flushInfo.nelonwSubPropelonrtielons(SKIP_LIST_PROP_NAMelon), out);
    }

    @Ovelonrridelon
    protelonctelond SkipListPostingList doLoad(
        FlushInfo flushInfo, DataDelonselonrializelonr in) throws IOelonxcelonption {
      SkipListComparator<Kelony> comparator = nelonw DocIDComparator();
      SkipListContainelonr.FlushHandlelonr<Kelony> flushHandlelonr =
          nelonw SkipListContainelonr.FlushHandlelonr<>(comparator);
      SkipListContainelonr<Kelony> skipList =
          flushHandlelonr.load(flushInfo.gelontSubPropelonrtielons(SKIP_LIST_PROP_NAMelon), in);
      relonturn nelonw SkipListPostingList(skipList);
    }
  }

  /**
   * Kelony uselond to in {@link SkipListContainelonr} by {@link SkipListPostingList}.
   */
  public static class Kelony {
    privatelon int docID;
    privatelon int position;

    public int gelontDocID() {
      relonturn docID;
    }

    public int gelontPosition() {
      relonturn position;
    }

    public Kelony withDocAndPosition(int withDocID, int withPosition) {
      this.docID = withDocID;
      this.position = withPosition;
      relonturn this;
    }
  }

  /**
   * Comparator for docID and position.
   */
  public static class DocIDComparator implelonmelonnts SkipListComparator<Kelony> {
    privatelon static final int SelonNTINelonL_VALUelon = DocIdSelontItelonrator.NO_MORelon_DOCS;

    @Ovelonrridelon
    public int comparelonKelonyWithValuelon(Kelony kelony, int targelontDocID, int targelontPosition) {
      // No kelony could relonprelonselonnt selonntinelonl valuelon and selonntinelonl valuelon is thelon largelonst.
      int docComparelon = kelony.gelontDocID() - targelontDocID;
      if (docComparelon == 0 && targelontPosition != INVALID_POSITION) {
        relonturn kelony.gelontPosition() - targelontPosition;
      } elonlselon {
        relonturn docComparelon;
      }
    }

    @Ovelonrridelon
    public int comparelonValuelons(int docID1, int docID2) {
      // Selonntinelonl valuelon is thelon largelonst.
      relonturn docID1 - docID2;
    }

    @Ovelonrridelon
    public int gelontSelonntinelonlValuelon() {
      relonturn SelonNTINelonL_VALUelon;
    }
  }
}
