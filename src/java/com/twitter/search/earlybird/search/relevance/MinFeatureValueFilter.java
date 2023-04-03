packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon;

import java.io.IOelonxcelonption;
import java.util.Objeloncts;

import com.googlelon.common.annotations.VisiblelonForTelonsting;

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

import com.twittelonr.selonarch.common.elonncoding.felonaturelons.BytelonNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.ClampBytelonNormalizelonr;
import com.twittelonr.selonarch.common.elonncoding.felonaturelons.SinglelonBytelonPositivelonFloatNormalizelonr;
import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.common.quelonry.FiltelonrelondQuelonry;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;

public final class MinFelonaturelonValuelonFiltelonr elonxtelonnds Quelonry implelonmelonnts FiltelonrelondQuelonry.DocIdFiltelonrFactory {
  privatelon final String felonaturelonNamelon;
  privatelon final BytelonNormalizelonr normalizelonr;
  privatelon final doublelon minValuelon;

  /**
   * Crelonatelons a quelonry that filtelonrs out all hits that havelon a valuelon smallelonr than thelon givelonn threlonshold
   * for thelon givelonn felonaturelon.
   *
   * @param felonaturelonNamelon Thelon felonaturelon.
   * @param minValuelon Thelon threlonshold for thelon felonaturelon valuelons.
   * @relonturn A quelonry that filtelonrs out all hits that havelon a valuelon smallelonr than thelon givelonn threlonshold
   *         for thelon givelonn felonaturelon.
   */
  public static Quelonry gelontMinFelonaturelonValuelonFiltelonr(String felonaturelonNamelon, doublelon minValuelon) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw MinFelonaturelonValuelonFiltelonr(felonaturelonNamelon, minValuelon), BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  public static FiltelonrelondQuelonry.DocIdFiltelonrFactory gelontDocIdFiltelonrFactory(
      String felonaturelonNamelon, doublelon minValuelon) {
    relonturn nelonw MinFelonaturelonValuelonFiltelonr(felonaturelonNamelon, minValuelon);
  }

  /**
   * Relonturns thelon normalizelonr that should belon uselond to normalizelon thelon valuelons for thelon givelonn felonaturelon.
   *
   * @param felonaturelonNamelon Thelon felonaturelon.
   * @relonturn Thelon normalizelonr that should belon uselond to normalizelon thelon valuelons for thelon givelonn felonaturelon.
   */
  @VisiblelonForTelonsting
  public static BytelonNormalizelonr gelontMinFelonaturelonValuelonNormalizelonr(String felonaturelonNamelon) {
    if (felonaturelonNamelon.elonquals(elonarlybirdFielonldConstant.USelonR_RelonPUTATION.gelontFielonldNamelon())) {
      relonturn nelonw ClampBytelonNormalizelonr(0, 100);
    }

    if (felonaturelonNamelon.elonquals(elonarlybirdFielonldConstant.FAVORITelon_COUNT.gelontFielonldNamelon())
        || felonaturelonNamelon.elonquals(elonarlybirdFielonldConstant.PARUS_SCORelon.gelontFielonldNamelon())
        || felonaturelonNamelon.elonquals(elonarlybirdFielonldConstant.RelonPLY_COUNT.gelontFielonldNamelon())
        || felonaturelonNamelon.elonquals(elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT.gelontFielonldNamelon())) {
      relonturn nelonw SinglelonBytelonPositivelonFloatNormalizelonr();
    }

    throw nelonw IllelongalArgumelonntelonxcelonption("Unknown normalization melonthod for fielonld " + felonaturelonNamelon);
  }

  @Ovelonrridelon
  public int hashCodelon() {
    // Probably doelonsn't makelon selonnselon to includelon thelon schelonmaSnapshot and normalizelonr helonrelon.
    relonturn (int) ((felonaturelonNamelon == null ? 0 : felonaturelonNamelon.hashCodelon() * 7) + minValuelon);
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof MinFelonaturelonValuelonFiltelonr)) {
      relonturn falselon;
    }

    // Probably doelonsn't makelon selonnselon to includelon thelon schelonmaSnapshot and normalizelonr helonrelon.
    MinFelonaturelonValuelonFiltelonr filtelonr = MinFelonaturelonValuelonFiltelonr.class.cast(obj);
    relonturn Objeloncts.elonquals(felonaturelonNamelon, filtelonr.felonaturelonNamelon) && (minValuelon == filtelonr.minValuelon);
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn String.format("MinFelonaturelonValuelonFiltelonr(%s, %f)", felonaturelonNamelon, minValuelon);
  }

  privatelon MinFelonaturelonValuelonFiltelonr(String felonaturelonNamelon, doublelon minValuelon) {
    this.felonaturelonNamelon = felonaturelonNamelon;
    this.normalizelonr = gelontMinFelonaturelonValuelonNormalizelonr(felonaturelonNamelon);
    this.minValuelon = normalizelonr.normalizelon(minValuelon);
  }

  @Ovelonrridelon
  public FiltelonrelondQuelonry.DocIdFiltelonr gelontDocIdFiltelonr(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
    final NumelonricDocValuelons felonaturelonDocValuelons = contelonxt.relonadelonr().gelontNumelonricDocValuelons(felonaturelonNamelon);
    relonturn (docId) -> felonaturelonDocValuelons.advancelonelonxact(docId)
        && ((bytelon) felonaturelonDocValuelons.longValuelon() >= minValuelon);
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost) {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        relonturn nelonw MinFelonaturelonValuelonDocIdSelontItelonrator(
            contelonxt.relonadelonr(), felonaturelonNamelon, minValuelon);
      }
    };
  }

  privatelon static final class MinFelonaturelonValuelonDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final NumelonricDocValuelons felonaturelonDocValuelons;
    privatelon final doublelon minValuelon;

    MinFelonaturelonValuelonDocIdSelontItelonrator(LelonafRelonadelonr indelonxRelonadelonr,
                                    String felonaturelonNamelon,
                                    doublelon minValuelon) throws IOelonxcelonption {
      supelonr(indelonxRelonadelonr);
      this.felonaturelonDocValuelons = indelonxRelonadelonr.gelontNumelonricDocValuelons(felonaturelonNamelon);
      this.minValuelon = minValuelon;
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
      relonturn felonaturelonDocValuelons.advancelonelonxact(docID())
          && ((bytelon) felonaturelonDocValuelons.longValuelon() >= minValuelon);
    }
  }

  public doublelon gelontMinValuelon() {
    relonturn minValuelon;
  }

  public BytelonNormalizelonr gelontNormalizelonr() {
    relonturn normalizelonr;
  }
}
