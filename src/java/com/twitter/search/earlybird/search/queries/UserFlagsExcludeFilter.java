packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.indelonx.NumelonricDocValuelons;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;

public final class UselonrFlagselonxcludelonFiltelonr elonxtelonnds Quelonry {
  /**
   * Relonturns a quelonry that filtelonrs hits baselond on thelonir author flags.
   *
   * @param elonxcludelonAntisocial Delontelonrminelons if thelon filtelonr should elonxcludelon hits from antisocial uselonrs.
   * @param elonxcludelonOffelonnsivelon Delontelonrminelons if thelon filtelonr should elonxcludelon hits from offelonnsivelon uselonrs.
   * @param elonxcludelonProtelonctelond Delontelonrminelons if thelon filtelonr should elonxcludelon hits from protelonctelond uselonrs
   * @relonturn A quelonry that filtelonrs hits baselond on thelonir author flags.
   */
  public static Quelonry gelontUselonrFlagselonxcludelonFiltelonr(UselonrTablelon uselonrTablelon,
                                                boolelonan elonxcludelonAntisocial,
                                                boolelonan elonxcludelonOffelonnsivelon,
                                                boolelonan elonxcludelonProtelonctelond) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw UselonrFlagselonxcludelonFiltelonr(
                uselonrTablelon, elonxcludelonAntisocial, elonxcludelonOffelonnsivelon, elonxcludelonProtelonctelond),
            BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon final UselonrTablelon uselonrTablelon;
  privatelon final boolelonan elonxcludelonAntisocial;
  privatelon final boolelonan elonxcludelonOffelonnsivelon;
  privatelon final boolelonan elonxcludelonProtelonctelond;

  privatelon UselonrFlagselonxcludelonFiltelonr(
      UselonrTablelon uselonrTablelon,
      boolelonan elonxcludelonAntisocial,
      boolelonan elonxcludelonOffelonnsivelon,
      boolelonan elonxcludelonProtelonctelond) {
    this.uselonrTablelon = uselonrTablelon;
    this.elonxcludelonAntisocial = elonxcludelonAntisocial;
    this.elonxcludelonOffelonnsivelon = elonxcludelonOffelonnsivelon;
    this.elonxcludelonProtelonctelond = elonxcludelonProtelonctelond;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (elonxcludelonAntisocial ? 13 : 0) + (elonxcludelonOffelonnsivelon ? 1 : 0) + (elonxcludelonProtelonctelond ? 2 : 0);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof UselonrFlagselonxcludelonFiltelonr)) {
      relonturn falselon;
    }

    UselonrFlagselonxcludelonFiltelonr filtelonr = UselonrFlagselonxcludelonFiltelonr.class.cast(obj);
    relonturn (elonxcludelonAntisocial == filtelonr.elonxcludelonAntisocial)
        && (elonxcludelonOffelonnsivelon == filtelonr.elonxcludelonOffelonnsivelon)
        && (elonxcludelonProtelonctelond == filtelonr.elonxcludelonProtelonctelond);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "UselonrFlagselonxcludelonFiltelonr";
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr relonadelonr = contelonxt.relonadelonr();
        if (uselonrTablelon == null) {
          relonturn nelonw AllDocsItelonrator(relonadelonr);
        }

        final int bits =
            (elonxcludelonAntisocial ? UselonrTablelon.ANTISOCIAL_BIT : 0)
                | (elonxcludelonOffelonnsivelon ? UselonrTablelon.OFFelonNSIVelon_BIT | UselonrTablelon.NSFW_BIT : 0)
                | (elonxcludelonProtelonctelond ? UselonrTablelon.IS_PROTelonCTelonD_BIT : 0);
        if (bits != 0) {
          relonturn nelonw UselonrFlagselonxcludelonDocIdSelontItelonrator(relonadelonr, uselonrTablelon) {
            @Ovelonrridelon
            protelonctelond boolelonan chelonckUselonrFlags(UselonrTablelon tablelon, long uselonrID) {
              relonturn !tablelon.isSelont(uselonrID, bits);
            }
          };
        }

        relonturn nelonw AllDocsItelonrator(relonadelonr);
      }
    };
  }

  privatelon abstract static class UselonrFlagselonxcludelonDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final UselonrTablelon uselonrTablelon;
    privatelon final NumelonricDocValuelons fromUselonrID;

    public UselonrFlagselonxcludelonDocIdSelontItelonrator(
        LelonafRelonadelonr indelonxRelonadelonr, UselonrTablelon tablelon) throws IOelonxcelonption {
      supelonr(indelonxRelonadelonr);
      uselonrTablelon = tablelon;
      fromUselonrID =
          indelonxRelonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF.gelontFielonldNamelon());
    }

    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      relonturn fromUselonrID.advancelonelonxact(docID())
          && chelonckUselonrFlags(uselonrTablelon, fromUselonrID.longValuelon());
    }

    protelonctelond abstract boolelonan chelonckUselonrFlags(UselonrTablelon tablelon, long uselonrID);
  }
}
