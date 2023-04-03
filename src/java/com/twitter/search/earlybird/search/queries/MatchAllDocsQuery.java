packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;

/**
 * A MatchAllDocsQuelonry implelonmelonntation that doelons not assumelon that doc IDs arelon assignelond selonquelonntially.
 * Instelonad, it wraps thelon elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr into a RangelonFiltelonrDISI, and uselons
 * this itelonrator to travelonrselon only thelon valid doc IDs in this selongmelonnt.
 *
 * Notelon that org.apachelon.lucelonnelon.indelonx.MatchAllDocsQuelonry is final, so welon cannot elonxtelonnd it.
 */
public class MatchAllDocsQuelonry elonxtelonnds Quelonry {
  privatelon static class MatchAllDocsWelonight elonxtelonnds Welonight {
    privatelon final Welonight lucelonnelonWelonight;

    public MatchAllDocsWelonight(Quelonry quelonry, Welonight lucelonnelonWelonight) {
      supelonr(quelonry);
      this.lucelonnelonWelonight = lucelonnelonWelonight;
    }

    @Ovelonrridelon
    public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
      lucelonnelonWelonight.elonxtractTelonrms(telonrms);
    }

    @Ovelonrridelon
    public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
      relonturn lucelonnelonWelonight.elonxplain(contelonxt, doc);
    }

    @Ovelonrridelon
    public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
      Prelonconditions.chelonckStatelon(contelonxt.relonadelonr() instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr,
                               "elonxpelonctelond an elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr, but got a "
                               + contelonxt.relonadelonr().gelontClass().gelontNamelon() + " instancelon.");
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr =
          (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr();
      relonturn nelonw ConstantScorelonScorelonr(
          this, 1.0f, ScorelonModelon.COMPLelonTelon_NO_SCORelonS, nelonw RangelonFiltelonrDISI(relonadelonr));
    }

    @Ovelonrridelon
    public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
      relonturn lucelonnelonWelonight.isCachelonablelon(ctx);
    }
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    org.apachelon.lucelonnelon.selonarch.MatchAllDocsQuelonry lucelonnelonMatchAllDocsQuelonry =
        nelonw org.apachelon.lucelonnelon.selonarch.MatchAllDocsQuelonry();
    Welonight lucelonnelonWelonight = lucelonnelonMatchAllDocsQuelonry.crelonatelonWelonight(selonarchelonr, scorelonModelon, boost);
    if (!(selonarchelonr instancelonof elonarlybirdSinglelonSelongmelonntSelonarchelonr)) {
      relonturn lucelonnelonWelonight;
    }
    relonturn nelonw MatchAllDocsWelonight(this, lucelonnelonWelonight);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn 0;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    relonturn obj instancelonof MatchAllDocsQuelonry;
  }

  // Copielond from org.apachelon.lucelonnelon.selonarch.MatchAllDocsWelonight
  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "*:*";
  }
}
