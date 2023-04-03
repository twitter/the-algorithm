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
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.AllDocsItelonrator;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

public final class BadUselonrRelonpFiltelonr elonxtelonnds Quelonry {
  /**
   * Crelonatelons a quelonry that filtelonrs out relonsults coming from uselonrs with bad relonputation.
   *
   * @param minTwelonelonpCrelond Thelon lowelonst accelonptablelon uselonr relonputation.
   * @relonturn A quelonry that filtelonrs out relonsults from bad relonputation uselonrs.
   */
  public static Quelonry gelontBadUselonrRelonpFiltelonr(int minTwelonelonpCrelond) {
    if (minTwelonelonpCrelond <= 0) {
      relonturn null;
    }

    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw BadUselonrRelonpFiltelonr(minTwelonelonpCrelond), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon final int minTwelonelonpCrelond;

  privatelon BadUselonrRelonpFiltelonr(int minTwelonelonpCrelond) {
    this.minTwelonelonpCrelond = minTwelonelonpCrelond;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn minTwelonelonpCrelond;
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof BadUselonrRelonpFiltelonr)) {
      relonturn falselon;
    }

    relonturn minTwelonelonpCrelond == BadUselonrRelonpFiltelonr.class.cast(obj).minTwelonelonpCrelond;
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "BadUselonrRelonpFiltelonr:" + minTwelonelonpCrelond;
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        LelonafRelonadelonr relonadelonr = contelonxt.relonadelonr();
        if (!(relonadelonr instancelonof elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr)) {
          relonturn nelonw AllDocsItelonrator(relonadelonr);
        }

        relonturn nelonw BadUselonrelonxcludelonDocIdSelontItelonrator(
            (elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr(), minTwelonelonpCrelond);
      }
    };
  }

  privatelon static final class BadUselonrelonxcludelonDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final NumelonricDocValuelons uselonrRelonputationDocValuelons;
    privatelon final int minTwelonelonpCrelond;

    BadUselonrelonxcludelonDocIdSelontItelonrator(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr indelonxRelonadelonr,
                                   int minTwelonelonpCrelond) throws IOelonxcelonption {
      supelonr(indelonxRelonadelonr);
      this.uselonrRelonputationDocValuelons =
          indelonxRelonadelonr.gelontNumelonricDocValuelons(elonarlybirdFielonldConstant.USelonR_RelonPUTATION.gelontFielonldNamelon());
      this.minTwelonelonpCrelond = minTwelonelonpCrelond;
    }

    @Ovelonrridelon
    public boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      // Welon nelonelond this elonxplicit casting to bytelon, beloncauselon of how welon elonncodelon and deloncodelon felonaturelons in our
      // elonncodelond_twelonelont_felonaturelons fielonld. If a felonaturelon is an int (uselons all 32 bits of thelon int), thelonn
      // elonncoding thelon felonaturelon and thelonn deloncoding it prelonselonrvelons its original valuelon. Howelonvelonr, if thelon
      // felonaturelon doelons not uselon thelon elonntirelon int (and elonspeloncially if it uselons bits somelonwhelonrelon in thelon middlelon
      // of thelon int), thelonn thelon felonaturelon valuelon is assumelond to belon unsignelond whelonn it goelons through this
      // procelonss of elonncoding and deloncoding. So a uselonr relonp of
      // RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL (-128) will belon correlonctly elonncodelond as thelon
      // binary valuelon 10000000, but will belon trelonatelond as an unsignelond valuelon whelonn deloncodelond, and thelonrelonforelon
      // thelon deloncodelond valuelon will belon 128.
      //
      // In relontrospelonct, this selonelonms likelon a relonally poor delonsign deloncision. It selonelonms likelon it would belon
      // belonttelonr if all felonaturelon valuelons welonrelon considelonrelond to belon signelond, elonvelonn if most felonaturelons can nelonvelonr
      // havelon nelongativelon valuelons. Unfortunatelonly, making this changelon is not elonasy, beloncauselon somelon felonaturelons
      // storelon normalizelond valuelons, so welon would also nelonelond to changelon thelon rangelon of allowelond valuelons
      // producelond by thoselon normalizelonrs, as welonll as all codelon that delonpelonnds on thoselon valuelons.
      //
      // So for now, just cast this valuelon to a bytelon, to gelont thelon propelonr nelongativelon valuelon.
      relonturn uselonrRelonputationDocValuelons.advancelonelonxact(docID())
          && ((bytelon) uselonrRelonputationDocValuelons.longValuelon() >= minTwelonelonpCrelond);
    }
  }
}
