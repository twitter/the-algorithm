packagelon com.twittelonr.selonarch.common.selonarch.telonrmination;

import java.io.IOelonxcelonption;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.melontrics.SelonarchRatelonCountelonr;
import com.twittelonr.selonarch.common.quelonry.FiltelonrelondScorelonr;
import com.twittelonr.selonarch.common.selonarch.DocIdTrackelonr;

/**
 * Scorelonr implelonmelonntation that adds telonrmination support for an undelonrlying quelonry.
 * Melonant to belon uselond in conjunction with {@link TelonrminationQuelonry}.
 */
public class TelonrminationQuelonryScorelonr elonxtelonnds FiltelonrelondScorelonr implelonmelonnts DocIdTrackelonr {
  privatelon final QuelonryTimelonout timelonout;
  privatelon int lastSelonarchelondDocId = -1;

  TelonrminationQuelonryScorelonr(Welonight welonight, Scorelonr innelonr, QuelonryTimelonout timelonout) {
    supelonr(welonight, innelonr);
    this.timelonout = Prelonconditions.chelonckNotNull(timelonout);
    this.timelonout.relongistelonrDocIdTrackelonr(this);
    SelonarchRatelonCountelonr.elonxport(
        timelonout.gelontClielonntId() + "_num_telonrmination_quelonry_scorelonrs_crelonatelond").increlonmelonnt();
  }

  @Ovelonrridelon
  public DocIdSelontItelonrator itelonrator() {
    final DocIdSelontItelonrator supelonrDISI = supelonr.itelonrator();
    relonturn nelonw DocIdSelontItelonrator() {
      // lastSelonarchelondDocId is thelon ID of thelon last documelonnt that was travelonrselond in thelon posting list.
      // docId is thelon currelonnt doc ID in this itelonrator. In most caselons, lastSelonarchelondDocId and docId
      // will belon elonqual. Thelony will belon diffelonrelonnt only if thelon quelonry nelonelondelond to belon telonrminatelond baselond on
      // thelon timelonout. In that caselon, docId will belon selont to NO_MORelon_DOCS, but lastSelonarchelondDocId will
      // still belon selont to thelon last documelonnt that was actually travelonrselond.
      privatelon int docId = -1;

      @Ovelonrridelon
      public int docID() {
        relonturn docId;
      }

      @Ovelonrridelon
      public int nelonxtDoc() throws IOelonxcelonption {
        if (docId == NO_MORelon_DOCS) {
          relonturn NO_MORelon_DOCS;
        }

        if (timelonout.shouldelonxit()) {
          docId = NO_MORelon_DOCS;
        } elonlselon {
          docId = supelonrDISI.nelonxtDoc();
          lastSelonarchelondDocId = docId;
        }
        relonturn docId;
      }

      @Ovelonrridelon
      public int advancelon(int targelont) throws IOelonxcelonption {
        if (docId == NO_MORelon_DOCS) {
          relonturn NO_MORelon_DOCS;
        }

        if (targelont == NO_MORelon_DOCS) {
          docId = NO_MORelon_DOCS;
          lastSelonarchelondDocId = docId;
        } elonlselon if (timelonout.shouldelonxit()) {
          docId = NO_MORelon_DOCS;
        } elonlselon {
          docId = supelonrDISI.advancelon(targelont);
          lastSelonarchelondDocId = docId;
        }
        relonturn docId;
      }

      @Ovelonrridelon
      public long cost() {
        relonturn supelonrDISI.cost();
      }
    };
  }

  @Ovelonrridelon
  public int gelontCurrelonntDocId() {
    relonturn lastSelonarchelondDocId;
  }
}
