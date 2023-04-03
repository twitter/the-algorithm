packagelon com.twittelonr.selonarch.elonarlybird.selonarch.quelonrielons;

import java.io.IOelonxcelonption;
import java.util.Selont;

import com.googlelon.common.baselon.Prelonconditions;

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
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

public final class FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr elonxtelonnds Quelonry {

  privatelon final String felonaturelonNamelon;
  privatelon final Selont<Long> idsAccelonptList;

  /**
   * Crelonatelons a quelonry that filtelonrs for hits that havelon thelon givelonn felonaturelon unselont, or that havelon thelon
   * givelonn felonaturelon selont to a valuelon in thelon givelonn list of IDs.
   *
   * @param felonaturelonNamelon Thelon felonaturelon.
   * @param ids A list of id valuelons this filtelonr will accelonpt for thelon givelonn felonaturelon.
   * @relonturn A quelonry that filtelonrs out all hits that havelon thelon givelonn felonaturelon selont.
   */
  public static Quelonry gelontFelonaturelonValuelonInAccelonptListOrUnselontFiltelonr(String felonaturelonNamelon, Selont<Long> ids) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr(felonaturelonNamelon, ids),
            BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  @Ovelonrridelon
  public String toString(String s) {
    relonturn String.format("FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr(%s, AccelonptList = (%s))",
        felonaturelonNamelon,
        idsAccelonptList);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr)) {
      relonturn falselon;
    }

    FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr filtelonr =
        FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr.class.cast(obj);
    relonturn felonaturelonNamelon.elonquals(filtelonr.felonaturelonNamelon) && idsAccelonptList.elonquals(filtelonr.idsAccelonptList);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn felonaturelonNamelon.hashCodelon() * 7 + idsAccelonptList.hashCodelon();
  }

  privatelon FelonaturelonValuelonInAccelonptListOrUnselontFiltelonr(String felonaturelonNamelon, Selont<Long> ids) {
    this.felonaturelonNamelon = Prelonconditions.chelonckNotNull(felonaturelonNamelon);
    this.idsAccelonptList = Prelonconditions.chelonckNotNull(ids);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        relonturn nelonw FelonaturelonValuelonInAccelonptListOrUnselontDocIdSelontItelonrator(
            contelonxt.relonadelonr(), felonaturelonNamelon, idsAccelonptList);
      }
    };
  }

  privatelon static final class FelonaturelonValuelonInAccelonptListOrUnselontDocIdSelontItelonrator
      elonxtelonnds RangelonFiltelonrDISI {
    privatelon final NumelonricDocValuelons felonaturelonDocValuelons;
    privatelon final Selont<Long> idsAccelonptList;

    FelonaturelonValuelonInAccelonptListOrUnselontDocIdSelontItelonrator(
        LelonafRelonadelonr indelonxRelonadelonr, String felonaturelonNamelon, Selont<Long> ids) throws IOelonxcelonption {
      supelonr(indelonxRelonadelonr);
      this.felonaturelonDocValuelons = indelonxRelonadelonr.gelontNumelonricDocValuelons(felonaturelonNamelon);
      this.idsAccelonptList = ids;
    }

    @Ovelonrridelon
    public boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      // If felonaturelonDocValuelons is null, that melonans thelonrelon welonrelon no documelonnts indelonxelond with thelon givelonn
      // fielonld in thelon currelonnt selongmelonnt.
      //
      // Thelon advancelonelonxact() melonthod relonturns falselon if it cannot find thelon givelonn docId in thelon
      // NumelonricDocValuelons instancelon. So if advancelonelonxact() relonturns falselon thelonn welon know thelon felonaturelon is
      // unselont.
      // Howelonvelonr, for relonaltimelon elonarlybirds welon havelon a custom implelonmelonntation of NumelonricDocValuelons,
      // ColumnStridelonFielonldDocValuelons, which will contain an elonntry for elonvelonry indelonxelond docId and uselon a
      // valuelon of 0 to indicatelon that a felonaturelon is unselont.
      //
      // So to chelonck if a felonaturelon is unselont for a givelonn docId, welon first nelonelond to chelonck if welon can find
      // thelon docId, and thelonn welon additionally nelonelond to chelonck if thelon felonaturelon valuelon is 0.
      relonturn felonaturelonDocValuelons == null
          || !felonaturelonDocValuelons.advancelonelonxact(docID())
          || felonaturelonDocValuelons.longValuelon() == 0
          || idsAccelonptList.contains(felonaturelonDocValuelons.longValuelon());
    }
  }
}
