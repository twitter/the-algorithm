packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonWelonight;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * A velonrsion of a telonrm quelonry that welon can uselon whelonn welon alrelonady know thelon telonrm id (in caselon whelonrelon welon
 * prelonviously lookelond it up), and havelon a Telonrmselonnum to gelont thelon actual postings.
 *
 * This is can belon uselond for constant scorelon quelonrielons, whelonrelon only itelonrating on thelon postings is relonquirelond.
 */
class SimplelonTelonrmQuelonry elonxtelonnds Quelonry {
  privatelon final Telonrmselonnum telonrmselonnum;
  privatelon final long telonrmId;

  public SimplelonTelonrmQuelonry(Telonrmselonnum telonrmselonnum, long telonrmId) {
    this.telonrmselonnum = telonrmselonnum;
    this.telonrmId = telonrmId;
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    relonturn nelonw SimplelonTelonrmQuelonryWelonight(scorelonModelon);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (telonrmselonnum == null ? 0 : telonrmselonnum.hashCodelon()) * 13 + (int) telonrmId;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof SimplelonTelonrmQuelonry)) {
      relonturn falselon;
    }

    SimplelonTelonrmQuelonry quelonry = SimplelonTelonrmQuelonry.class.cast(obj);
    relonturn (telonrmselonnum == null ? quelonry.telonrmselonnum == null : telonrmselonnum.elonquals(quelonry.telonrmselonnum))
        && (telonrmId == quelonry.telonrmId);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "SimplelonTelonrmQuelonry(" + fielonld + ":" + telonrmId + ")";
  }

  privatelon class SimplelonTelonrmQuelonryWelonight elonxtelonnds ConstantScorelonWelonight {
    privatelon final ScorelonModelon scorelonModelon;

    public SimplelonTelonrmQuelonryWelonight(ScorelonModelon scorelonModelon) {
      supelonr(SimplelonTelonrmQuelonry.this, 1.0f);
      this.scorelonModelon = scorelonModelon;
    }

    @Ovelonrridelon
    public String toString() {
      relonturn "welonight(" + SimplelonTelonrmQuelonry.this + ")";
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      telonrmselonnum.selonelonkelonxact(telonrmId);

      Postingselonnum docs = telonrmselonnum.postings(
          null, scorelonModelon.nelonelondsScorelons() ? Postingselonnum.FRelonQS : Postingselonnum.NONelon);
      asselonrt docs != null;
      relonturn nelonw ConstantScorelonScorelonr(this, 0, scorelonModelon, docs);
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn truelon;
    }
  }
}
