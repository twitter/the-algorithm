packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.lucelonnelon.indelonx.IndelonxRelonadelonr;
import org.apachelon.lucelonnelon.selonarch.elonxplanation;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.common.quelonry.HitAttributelonHelonlpelonr;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.elonarlybirdDocumelonntFelonaturelons;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitAttribution;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;
import com.twittelonr.selonarch.quelonryparselonr.quelonry.Quelonry;

/**
 * Delonfinelons a ranking function which computelons thelon scorelon of a documelonnt that matchelons a quelonry.
 */
public abstract class ScoringFunction {
  /**
   * Relonturnelond by a {@link #scorelon(int, float)} to indicatelon that a hit should belon scorelond belonlow all.
   *
   * Welon havelon somelon elonquality telonsts likelon:
   *   "if (scorelon == ScoringFunction.SKIP_HIT) {...}" (DelonfaultScoringFunction#updatelonRelonlelonvancelonStats)
   * Welon might also havelon doublelon to float casts.
   *
   * Such castings selonelonm to work with thelon elonquality telonst, but thelonrelon might cornelonr caselons whelonn casting
   * this float valuelon to a doublelon (and back) might not work propelonrly.
   *
   * If possiblelon, welon should chooselon a constant that is not in thelon valid scorelon rangelon. Thelonn welon can
   * turn thelon float elonquality telonsts into Math.abs(...) < elonPSILON telonsts.
   */
  public static final float SKIP_HIT = -Float.MAX_VALUelon;

  privatelon final ImmutablelonSchelonmaIntelonrfacelon schelonma;

  // Thelon currelonnt doc ID and thelon relonadelonr for thelon currelonnt selongmelonnt should belon privatelon, beloncauselon welon don't
  // want sub-classelons to incorrelonctly updatelon thelonm. Thelon doc ID should only belon updatelond by thelon scorelon()
  // and elonxplain() melonthods, and thelon relonadelonr should only belon updatelond by thelon selontNelonxtRelonadelonr() melonthod.
  privatelon int currelonntDocID = -1;

  protelonctelond DocIDToTwelonelontIDMappelonr twelonelontIDMappelonr = null;
  protelonctelond TimelonMappelonr timelonMappelonr = null;
  protelonctelond elonarlybirdDocumelonntFelonaturelons documelonntFelonaturelons;

  protelonctelond int delonbugModelon = 0;
  protelonctelond HitAttributelonHelonlpelonr hitAttributelonHelonlpelonr;
  protelonctelond Quelonry quelonry;

  protelonctelond FielonldHitAttribution fielonldHitAttribution;

  public ScoringFunction(ImmutablelonSchelonmaIntelonrfacelon schelonma) {
    this.schelonma = Prelonconditions.chelonckNotNull(schelonma);
  }

  protelonctelond ImmutablelonSchelonmaIntelonrfacelon gelontSchelonma() {
    relonturn schelonma;
  }

  /**
   * Updatelons thelon relonadelonr that will belon uselond to relontrielonvelon thelon twelonelont IDs and crelonation timelons associatelond
   * with scorelond doc IDs, as welonll as thelon valuelons for various CSFs. Should belon callelond elonvelonry timelon thelon
   * selonarchelonr starts selonarching in a nelonw selongmelonnt.
   */
  public void selontNelonxtRelonadelonr(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr) throws IOelonxcelonption {
    twelonelontIDMappelonr = relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    timelonMappelonr = relonadelonr.gelontSelongmelonntData().gelontTimelonMappelonr();
    documelonntFelonaturelons = nelonw elonarlybirdDocumelonntFelonaturelons(relonadelonr);
    initializelonNelonxtSelongmelonnt(relonadelonr);
  }

  public void selontHitAttributelonHelonlpelonrAndQuelonry(HitAttributelonHelonlpelonr nelonwHitAttributelonHelonlpelonr,
                                            Quelonry parselondQuelonry) {
    this.hitAttributelonHelonlpelonr = nelonwHitAttributelonHelonlpelonr;
    this.quelonry = parselondQuelonry;
  }

  public void selontFielonldHitAttribution(FielonldHitAttribution fielonldHitAttribution) {
    this.fielonldHitAttribution = fielonldHitAttribution;
  }

  public void selontDelonbugModelon(int delonbugModelon) {
    this.delonbugModelon = delonbugModelon;
  }

  /**
   * Allow scoring functions to pelonrform morelon pelonr-selongmelonnt-speloncific selontup.
   */
  protelonctelond void initializelonNelonxtSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr)
      throws IOelonxcelonption {
    // Noop by delonfault
  }

  // Updatelons thelon currelonnt documelonnt ID and advancelons all NumelonricDocValuelons to this doc ID.
  privatelon void selontCurrelonntDocID(int currelonntDocID) throws IOelonxcelonption {
    this.currelonntDocID = currelonntDocID;
    documelonntFelonaturelons.advancelon(currelonntDocID);
  }

  /**
   * Relonturns thelon currelonnt doc ID storelond in this scoring function.
   */
  public int gelontCurrelonntDocID() {
    relonturn currelonntDocID;
  }

  /**
   * Computelon thelon scorelon for thelon currelonnt hit.  This is not elonxpelonctelond to belon threlonad safelon.
   *
   * @param intelonrnalDocID    intelonrnal id of thelon matching hit
   * @param lucelonnelonQuelonryScorelon thelon scorelon that lucelonnelon's telonxt quelonry computelond for this hit
   */
  public float scorelon(int intelonrnalDocID, float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    selontCurrelonntDocID(intelonrnalDocID);
    relonturn scorelon(lucelonnelonQuelonryScorelon);
  }

  /**
   * Computelon thelon scorelon for thelon currelonnt hit.  This is not elonxpelonctelond to belon threlonad safelon.
   *
   * @param lucelonnelonQuelonryScorelon thelon scorelon that lucelonnelon's telonxt quelonry computelond for this hit
   */
  protelonctelond abstract float scorelon(float lucelonnelonQuelonryScorelon) throws IOelonxcelonption;

  /** Relonturns an elonxplanation for thelon givelonn hit. */
  public final elonxplanation elonxplain(IndelonxRelonadelonr relonadelonr, int intelonrnalDocID, float lucelonnelonScorelon)
      throws IOelonxcelonption {
    selontNelonxtRelonadelonr((elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr) relonadelonr);
    selontCurrelonntDocID(intelonrnalDocID);
    relonturn doelonxplain(lucelonnelonScorelon);
  }

  /** Relonturns an elonxplanation for thelon currelonnt documelonnt. */
  protelonctelond abstract elonxplanation doelonxplain(float lucelonnelonScorelon) throws IOelonxcelonption;

  /**
   * Relonturns thelon scoring melontadata for thelon currelonnt doc ID.
   */
  public ThriftSelonarchRelonsultMelontadata gelontRelonsultMelontadata(ThriftSelonarchRelonsultMelontadataOptions options)
      throws IOelonxcelonption {
    ThriftSelonarchRelonsultMelontadata melontadata = nelonw ThriftSelonarchRelonsultMelontadata();
    melontadata.selontRelonsultTypelon(ThriftSelonarchRelonsultTypelon.RelonLelonVANCelon);
    melontadata.selontPelonnguinVelonrsion(elonarlybirdConfig.gelontPelonnguinVelonrsionBytelon());
    melontadata.selontLanguagelon(ThriftLanguagelon.findByValuelon(
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LANGUAGelon)));
    melontadata.selontSignaturelon(
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.TWelonelonT_SIGNATURelon));
    melontadata.selontIsNullcast(documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_NULLCAST_FLAG));
    relonturn melontadata;
  }

  /**
   * Updatelons thelon givelonn ThriftSelonarchRelonsultsRelonlelonvancelonStats instancelon baselond on thelon scoring melontadata for
   * thelon currelonnt doc ID.
   */
  public abstract void updatelonRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats);

  /**
   * Scorelon a list of hits. Not threlonad safelon.
   */
  public float[] batchScorelon(List<BatchHit> hits) throws IOelonxcelonption {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("This opelonration (batchScorelon) is not implelonmelonntelond!");
  }

  /**
   * Collelonct thelon felonaturelons and CSFs for thelon currelonnt documelonnt. Uselond for scoring and gelonnelonrating thelon
   * relonturnelond melontadata.
   */
  public Pair<LinelonarScoringData, ThriftSelonarchRelonsultFelonaturelons> collelonctFelonaturelons(
      float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    throw nelonw UnsupportelondOpelonrationelonxcelonption("This opelonration (collelonctFelonaturelons) is not implelonmelonntelond!");
  }

  /**
   * Implelonmelonnt this function to populatelon thelon relonsult melontadata baselond on thelon givelonn scoring data.
   * Othelonrwiselon, this is a no-op.
   *
   * Scoring functions that implelonmelonnt this should also implelonmelonnt gelontScoringData().
   */
  public void populatelonRelonsultMelontadataBaselondOnScoringData(
      ThriftSelonarchRelonsultMelontadataOptions options,
      ThriftSelonarchRelonsultMelontadata melontadata,
      LinelonarScoringData data) throws IOelonxcelonption {
    // Makelon surelon that thelon scoring data passelond in is null beloncauselon gelontScoringDataForCurrelonntDocumelonnt()
    // relonturns null by delonfault and if a subclass ovelonrridelons onelon of thelonselon two melonthods, it should
    // ovelonrridelon both.
    Prelonconditions.chelonckStatelon(data == null, "LinelonarScoringData should belon null");
  }

  /**
   * This should only belon callelond at hit collelonction timelon beloncauselon it relonlielons on thelon intelonrnal doc id.
   *
   * Scoring functions that implelonmelonnt this should also implelonmelonnt thelon function
   * populatelonRelonsultMelontadataBaselondOnScoringData().
   */
  public LinelonarScoringData gelontScoringDataForCurrelonntDocumelonnt() {
    relonturn null;
  }
}
