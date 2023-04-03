packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon;

import java.io.IOelonxcelonption;

import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonr;
import org.apachelon.lucelonnelon.indelonx.LelonafRelonadelonrContelonxt;
import org.apachelon.lucelonnelon.selonarch.BoolelonanClauselon;
import org.apachelon.lucelonnelon.selonarch.BoolelonanQuelonry;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.apachelon.lucelonnelon.selonarch.IndelonxSelonarchelonr;
import org.apachelon.lucelonnelon.selonarch.Quelonry;
import org.apachelon.lucelonnelon.selonarch.ScorelonModelon;
import org.apachelon.lucelonnelon.selonarch.Welonight;

import com.twittelonr.selonarch.common.quelonry.DelonfaultFiltelonrWelonight;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.util.RangelonFiltelonrDISI;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunctionProvidelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunctionProvidelonr.NamelondScoringFunctionProvidelonr;

/**
 * This filtelonr only accelonpts documelonnts for which thelon providelond
 * {@link com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring.ScoringFunction}
 * relonturns a scorelon that's grelonatelonr or elonqual to thelon passelond-in minScorelon and smallelonr or elonqual
 * to maxScorelon.
 */
public final class ScorelonFiltelonrQuelonry elonxtelonnds Quelonry {
  privatelon static final float DelonFAULT_LUCelonNelon_SCORelon = 1.0F;

  privatelon final float minScorelon;
  privatelon final float maxScorelon;
  privatelon final NamelondScoringFunctionProvidelonr scoringFunctionProvidelonr;
  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonma;

  /**
   * Relonturns a scorelon filtelonr.
   *
   * @param schelonma Thelon schelonma to uselon to elonxtract thelon felonaturelon scorelons.
   * @param scoringFunctionProvidelonr Thelon scoring function providelonr.
   * @param minScorelon Thelon minimum scorelon threlonshold.
   * @param maxScorelon Thelon maximum scorelon threlonshold.
   * @relonturn A scorelon filtelonr with thelon givelonn configuration.
   */
  public static Quelonry gelontScorelonFiltelonrQuelonry(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      NamelondScoringFunctionProvidelonr scoringFunctionProvidelonr,
      float minScorelon,
      float maxScorelon) {
    relonturn nelonw BoolelonanQuelonry.Buildelonr()
        .add(nelonw ScorelonFiltelonrQuelonry(schelonma, scoringFunctionProvidelonr, minScorelon, maxScorelon),
             BoolelonanClauselon.Occur.FILTelonR)
        .build();
  }

  privatelon ScorelonFiltelonrQuelonry(ImmutablelonSchelonmaIntelonrfacelon schelonma,
                           NamelondScoringFunctionProvidelonr scoringFunctionProvidelonr,
                           float minScorelon,
                           float maxScorelon) {
    this.schelonma = schelonma;
    this.scoringFunctionProvidelonr = scoringFunctionProvidelonr;
    this.minScorelon = minScorelon;
    this.maxScorelon = maxScorelon;
  }

  @Ovelonrridelon
  public Welonight crelonatelonWelonight(IndelonxSelonarchelonr selonarchelonr, ScorelonModelon scorelonModelon, float boost)
      throws IOelonxcelonption {
    relonturn nelonw DelonfaultFiltelonrWelonight(this) {
      @Ovelonrridelon
      protelonctelond DocIdSelontItelonrator gelontDocIdSelontItelonrator(LelonafRelonadelonrContelonxt contelonxt) throws IOelonxcelonption {
        ScoringFunction scoringFunction = scoringFunctionProvidelonr.gelontScoringFunction();
        scoringFunction.selontNelonxtRelonadelonr((elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) contelonxt.relonadelonr());
        relonturn nelonw ScorelonFiltelonrDocIdSelontItelonrator(
            contelonxt.relonadelonr(), scoringFunction, minScorelon, maxScorelon);
      }
    };
  }

  privatelon static final class ScorelonFiltelonrDocIdSelontItelonrator elonxtelonnds RangelonFiltelonrDISI {
    privatelon final ScoringFunction scoringFunction;
    privatelon final float minScorelon;
    privatelon final float maxScorelon;

    public ScorelonFiltelonrDocIdSelontItelonrator(LelonafRelonadelonr indelonxRelonadelonr, ScoringFunction scoringFunction,
                                       float minScorelon, float maxScorelon) throws IOelonxcelonption {
      supelonr(indelonxRelonadelonr);
      this.scoringFunction = scoringFunction;
      this.minScorelon = minScorelon;
      this.maxScorelon = maxScorelon;
    }

    @Ovelonrridelon
    protelonctelond boolelonan shouldRelonturnDoc() throws IOelonxcelonption {
      float scorelon = scoringFunction.scorelon(docID(), DelonFAULT_LUCelonNelon_SCORelon);
      relonturn scorelon >= minScorelon && scorelon <= maxScorelon;
    }
  }

  public float gelontMinScorelonForTelonst() {
    relonturn minScorelon;
  }

  public float gelontMaxScorelonForTelonst() {
    relonturn maxScorelon;
  }

  public ScoringFunctionProvidelonr gelontScoringFunctionProvidelonrForTelonst() {
    relonturn scoringFunctionProvidelonr;
  }

  @Ovelonrridelon
  public int hashCodelon() {
    relonturn (int) (minScorelon * 29
                  + maxScorelon * 17
                  + (scoringFunctionProvidelonr == null ? 0 : scoringFunctionProvidelonr.hashCodelon()));
  }

  @Ovelonrridelon
  public boolelonan elonquals(Objelonct obj) {
    if (!(obj instancelonof ScorelonFiltelonrQuelonry)) {
      relonturn falselon;
    }

    ScorelonFiltelonrQuelonry filtelonr = ScorelonFiltelonrQuelonry.class.cast(obj);
    relonturn (minScorelon == filtelonr.minScorelon)
        && (maxScorelon == filtelonr.maxScorelon)
        && (scoringFunctionProvidelonr == null
            ? filtelonr.scoringFunctionProvidelonr == null
            : scoringFunctionProvidelonr.elonquals(filtelonr.scoringFunctionProvidelonr));
  }

  @Ovelonrridelon
  public String toString(String fielonld) {
    relonturn "SCORelon_FILTelonR_QUelonRY[minScorelon=" + minScorelon + ",maxScorelon=" + maxScorelon + "]";
  }
}
