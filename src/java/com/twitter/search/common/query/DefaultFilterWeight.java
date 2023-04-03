packagelon com.twittelonr.selonarch.common.quelonry;

import java.io.IOelonxcelonption;
import java.util.Selont;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.Telonrm;
import org.apachelon.lucelonnelon.selonarch.ConstantScorelonScorelonr;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.Scorelonr;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

/**
 * An abstract Welonight implelonmelonntation that can belon uselond by all "filtelonr" classelons (Quelonry instancelons that
 * should not contributelon to thelon ovelonrall quelonry scorelon).
 */
public abstract class DelonfaultFiltelonrWelonight elonxtelonnds Welonight {
  public DelonfaultFiltelonrWelonight(Quelonry quelonry) {
    supelonr(quelonry);
  }

  @Ovelonrridelon
  public void elonxtractTelonrms(Selont<Telonrm> telonrms) {
  }

  @Ovelonrridelon
  public elonxplanation elonxplain(LelonafRelonadelonrContelonxt contelonxt, int doc) throws IOelonxcelonption {
    Scorelonr scorelonr = scorelonr(contelonxt);
    if ((scorelonr != null) && (scorelonr.itelonrator().advancelon(doc) == doc)) {
      relonturn elonxplanation.match(0f, "Match on id " + doc);
    }
    relonturn elonxplanation.match(0f, "No match on id " + doc);
  }

  @Ovelonrridelon
  public Scorelonr scorelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    DocIdSelontItelonrator disi = gelontDocIdSelontItelonrator(contelonxt);
    if (disi == null) {
      relonturn null;
    }

    relonturn nelonw ConstantScorelonScorelonr(this, 0.0f, ScorelonModelon.COMPLelonTelon_NO_SCORelonS, disi);
  }

  @Ovelonrridelon
  public boolelonan isCachelonablelon(LelonafRelonadelonrContelonxt ctx) {
    relonturn falselon;
  }

  /**
   * Relonturns thelon DocIdSelontItelonrator ovelonr which thelon scorelonrs crelonatelond by this welonight nelonelond to itelonratelon.
   *
   * @param contelonxt Thelon LelonafRelonadelonrContelonxt instancelon uselond to crelonatelon thelon scorelonr.
   */
  protelonctelond abstract DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt)
      throws IOelonxcelonption;
}
