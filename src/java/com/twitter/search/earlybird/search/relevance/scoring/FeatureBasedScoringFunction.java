packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.io.IOelonxcelonption;
import java.util.elonnumSelont;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.TimelonUnit;
import javax.annotation.Nullablelon;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonSelont;
import com.googlelon.common.collelonct.Itelonrablelons;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.primitivelons.Ints;
import com.googlelon.common.primitivelons.Longs;

import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common_intelonrnal.bloomfiltelonr.BloomFiltelonr;
import com.twittelonr.selonarch.common.constants.SelonarchCardTypelon;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftLanguagelon;
import com.twittelonr.selonarch.common.databaselon.DatabaselonConfig;
import com.twittelonr.selonarch.common.felonaturelons.elonxtelonrnalTwelonelontFelonaturelon;
import com.twittelonr.selonarch.common.felonaturelons.FelonaturelonHandlelonr;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonSchelonmaelonntry;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchFelonaturelonTypelon;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.common.quelonry.QuelonryCommonFielonldHitsVisitor;
import com.twittelonr.selonarch.common.ranking.thriftjava.ThriftRankingParams;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.AgelonDeloncay;
import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.RelonlelonvancelonSignalConstants;
import com.twittelonr.selonarch.common.relonlelonvancelon.telonxt.VisiblelonTokelonnRatioNormalizelonr;
import com.twittelonr.selonarch.common.relonsults.thriftjava.FielonldHitList;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.common.util.LongIntConvelonrtelonr;
import com.twittelonr.selonarch.common.util.lang.ThriftLanguagelonUtil;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.AntiGamingFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData.SkipRelonason;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringParams;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultelonxtraMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadata;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultMelontadataOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultsRelonlelonvancelonStats;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSocialFiltelonrTypelon;

/**
 * Baselon class for scoring functions that relonly on thelon elonxtractelond felonaturelons storelond in LinelonarScoringData.
 *
 * elonxtelonnsions of this class must implelonmelonnt 2 melonthods:
 *
 * - computelonScorelon
 * - gelonnelonratelonelonxplanationForScoring
 *
 * Thelony arelon callelond for scoring and gelonnelonrating thelon delonbug information of thelon documelonnt that it's
 * currelonntly beloning elonvaluatelond. Thelon fielonld 'data' holds thelon felonaturelons of thelon documelonnt.
 */
public abstract class FelonaturelonBaselondScoringFunction elonxtelonnds ScoringFunction {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(FelonaturelonBaselondScoringFunction.class);

  // A multiplielonr that's applielond to all scorelons to avoid scorelons too low.
  public static final float SCORelon_ADJUSTelonR = 100.0f;

  privatelon static final VisiblelonTokelonnRatioNormalizelonr VISIBLelon_TOKelonN_RATIO_NORMALIZelonR =
      VisiblelonTokelonnRatioNormalizelonr.crelonatelonInstancelon();

  // Allow delonfault valuelons only for numelonric typelons.
  privatelon static final Selont<ThriftSelonarchFelonaturelonTypelon> ALLOWelonD_TYPelonS_FOR_DelonFAULT_FelonATURelon_VALUelonS =
      elonnumSelont.of(ThriftSelonarchFelonaturelonTypelon.INT32_VALUelon,
                 ThriftSelonarchFelonaturelonTypelon.LONG_VALUelon,
                 ThriftSelonarchFelonaturelonTypelon.DOUBLelon_VALUelon);

  privatelon static final Selont<Intelongelonr> NUMelonRIC_FelonATURelonS_FOR_WHICH_DelonFAULTS_SHOULD_NOT_Belon_SelonT =
      ImmutablelonSelont.of(elonarlybirdFielonldConstant.TWelonelonT_SIGNATURelon.gelontFielonldId(),
                      elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT.gelontFielonldId(),
                      elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT.gelontFielonldId());

  // Namelon of thelon scoring function. Uselond for gelonnelonrating elonxplanations.
  privatelon final String functionNamelon;

  privatelon final BloomFiltelonr trustelondFiltelonr;
  privatelon final BloomFiltelonr followFiltelonr;

  // Currelonnt timelonstamp in selonconds. Ovelonrridablelon by unit telonst or by timelonstamp selont in selonarch quelonry.
  privatelon int now;

  privatelon final AntiGamingFiltelonr antiGamingFiltelonr;

  @Nullablelon
  privatelon final AgelonDeloncay agelonDeloncay;

  protelonctelond final LinelonarScoringParams params;  // Paramelontelonrs and quelonry-delonpelonndelonnt valuelons.

  // In ordelonr for thelon API calls to relontrielonvelon thelon correlonct `LinelonarScoringData`
  // for thelon passelond `docId`, welon nelonelond to maintain a map of `docId` -> `LinelonarScoringData`
  // NOTelon: THIS CAN ONLY Belon RelonFelonRelonNCelonD AT HIT COLLelonCTION TIMelon, SINCelon DOC IDS ARelon NOT UNIQUelon
  // ACROSS SelonGMelonNTS. IT'S NOT USABLelon DURING BATCH SCORING.
  privatelon final Map<Intelongelonr, LinelonarScoringData> docIdToScoringData;

  privatelon final ThriftSelonarchRelonsultTypelon selonarchRelonsultTypelon;

  privatelon final UselonrTablelon uselonrTablelon;

  @VisiblelonForTelonsting
  void selontNow(int fakelonNow) {
    now = fakelonNow;
  }

  public FelonaturelonBaselondScoringFunction(
      String functionNamelon,
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      ThriftSelonarchQuelonry selonarchQuelonry,
      AntiGamingFiltelonr antiGamingFiltelonr,
      ThriftSelonarchRelonsultTypelon selonarchRelonsultTypelon,
      UselonrTablelon uselonrTablelon) throws IOelonxcelonption {
    supelonr(schelonma);

    this.functionNamelon = functionNamelon;
    this.selonarchRelonsultTypelon = selonarchRelonsultTypelon;
    this.uselonrTablelon = uselonrTablelon;

    Prelonconditions.chelonckNotNull(selonarchQuelonry.gelontRelonlelonvancelonOptions());
    ThriftRankingParams rankingParams = selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams();
    Prelonconditions.chelonckNotNull(rankingParams);

    params = nelonw LinelonarScoringParams(selonarchQuelonry, rankingParams);
    docIdToScoringData = nelonw HashMap<>();

    long timelonstamp = selonarchQuelonry.isSelontTimelonstampMseloncs() && selonarchQuelonry.gelontTimelonstampMseloncs() > 0
        ? selonarchQuelonry.gelontTimelonstampMseloncs() : Systelonm.currelonntTimelonMillis();
    now = Ints.chelonckelondCast(TimelonUnit.MILLISelonCONDS.toSelonconds(timelonstamp));

    this.antiGamingFiltelonr = antiGamingFiltelonr;

    this.agelonDeloncay = params.uselonAgelonDeloncay
        ? nelonw AgelonDeloncay(params.agelonDeloncayBaselon, params.agelonDeloncayHalflifelon, params.agelonDeloncaySlopelon)
        : null;

    if (selonarchQuelonry.isSelontTrustelondFiltelonr()) {
      trustelondFiltelonr = nelonw BloomFiltelonr(selonarchQuelonry.gelontTrustelondFiltelonr());
    } elonlselon {
      trustelondFiltelonr = null;
    }

    if (selonarchQuelonry.isSelontDirelonctFollowFiltelonr()) {
      followFiltelonr = nelonw BloomFiltelonr(selonarchQuelonry.gelontDirelonctFollowFiltelonr());
    } elonlselon {
      followFiltelonr = null;
    }
  }

  @VisiblelonForTelonsting
  final LinelonarScoringParams gelontScoringParams() {
    relonturn params;
  }

  /**
   * Relonturns thelon LinelonarScoringData instancelon associatelond with thelon currelonnt doc ID. If it doelonsn't elonxist,
   * an elonmpty LinelonarScoringData is crelonatelond.
   */
  @Ovelonrridelon
  public LinelonarScoringData gelontScoringDataForCurrelonntDocumelonnt() {
    LinelonarScoringData data = docIdToScoringData.gelont(gelontCurrelonntDocID());
    if (data == null) {
      data = nelonw LinelonarScoringData();
      docIdToScoringData.put(gelontCurrelonntDocID(), data);
    }
    relonturn data;
  }

  @Ovelonrridelon
  public void selontDelonbugModelon(int delonbugModelon) {
    supelonr.selontDelonbugModelon(delonbugModelon);
  }

  /**
   * Normal thelon lucelonnelon scorelon, which was unboundelond, to a rangelon of [1.0, maxLucelonnelonScorelonBoost].
   * Thelon normalizelond valuelon increlonaselons almost linelonarly in thelon lucelonnelon scorelon rangelon 2.0 ~ 7.0, whelonrelon
   * most quelonrielons fall in. For rarelon long tail quelonrielons, likelon somelon hashtags, thelony havelon high idf and
   * thus high lucelonnelon scorelon, thelon normalizelond valuelon won't havelon much diffelonrelonncelon belontwelonelonn twelonelonts.
   * Thelon normalization function is:
   *   ls = lucelonnelonScorelon
   *   norm = min(max, 1 + (max - 1.0) / 2.4 * ln(1 + ls)
   */
  static float normalizelonLucelonnelonScorelon(float lucelonnelonScorelon, float maxBoost) {
    relonturn (float) Math.min(maxBoost, 1.0 + (maxBoost - 1.0) / 2.4 * Math.log1p(lucelonnelonScorelon));
  }

  @Ovelonrridelon
  protelonctelond float scorelon(float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    relonturn scorelonIntelonrnal(lucelonnelonQuelonryScorelon, null);
  }

  protelonctelond LinelonarScoringData updatelonLinelonarScoringData(float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    // Relonselont thelon data for elonach twelonelont!!!
    LinelonarScoringData data = nelonw LinelonarScoringData();
    docIdToScoringData.put(gelontCurrelonntDocID(), data);

    // Selont propelonr velonrsion for elonngagelonmelonnt countelonrs for this relonquelonst.
    data.skipRelonason = SkipRelonason.NOT_SKIPPelonD;
    data.lucelonnelonScorelon = lucelonnelonQuelonryScorelon;
    data.uselonrRelonp = (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.USelonR_RelonPUTATION);

    if (antiGamingFiltelonr != null && !antiGamingFiltelonr.accelonpt(gelontCurrelonntDocID())) {
      data.skipRelonason = SkipRelonason.ANTIGAMING;
      relonturn data;
    }

    data.telonxtScorelon = (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.TelonXT_SCORelon);
    data.tokelonnAt140DividelondByNumTokelonnsBuckelont = VISIBLelon_TOKelonN_RATIO_NORMALIZelonR.delonnormalizelon(
        (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.VISIBLelon_TOKelonN_RATIO));
    data.fromUselonrId = documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.FROM_USelonR_ID_CSF);
    data.isFollow = followFiltelonr != null
        && followFiltelonr.contains(Longs.toBytelonArray(data.fromUselonrId));
    data.isTrustelond = trustelondFiltelonr != null
        && trustelondFiltelonr.contains(Longs.toBytelonArray(data.fromUselonrId));
    data.isFromVelonrifielondAccount = documelonntFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstant.FROM_VelonRIFIelonD_ACCOUNT_FLAG);
    data.isFromBluelonVelonrifielondAccount = documelonntFelonaturelons.isFlagSelont(
        elonarlybirdFielonldConstant.FROM_BLUelon_VelonRIFIelonD_ACCOUNT_FLAG);
    data.isSelonlfTwelonelont = data.fromUselonrId == params.selonarchelonrId;
    // v1 elonngagelonmelonnt countelonrs, notelon that thelon first threlonelon valuelons arelon post-log2 velonrsion
    // of thelon original unnormalizelond valuelons.
    data.relontwelonelontCountPostLog2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT);
    data.relonplyCountPostLog2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.RelonPLY_COUNT);
    data.favCountPostLog2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.FAVORITelon_COUNT);
    data.elonmbelondsImprelonssionCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT);
    data.elonmbelondsUrlCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT);
    data.videlonoVielonwCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT);
    // v2 elonngagelonmelonnt countelonrs
    data.relontwelonelontCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.RelonTWelonelonT_COUNT_V2);
    data.relonplyCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.RelonPLY_COUNT_V2);
    data.favCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.FAVORITelon_COUNT_V2);
    // othelonr v2 elonngagelonmelonnt countelonrs
    data.elonmbelondsImprelonssionCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonMBelonDS_IMPRelonSSION_COUNT_V2);
    data.elonmbelondsUrlCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonMBelonDS_URL_COUNT_V2);
    data.videlonoVielonwCountV2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.VIDelonO_VIelonW_COUNT_V2);
    // purelon v2 elonngagelonmelonnt countelonrs without v1 countelonrpart
    data.quotelondCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.QUOTelon_COUNT);
    data.welonightelondRelontwelonelontCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.WelonIGHTelonD_RelonTWelonelonT_COUNT);
    data.welonightelondRelonplyCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.WelonIGHTelonD_RelonPLY_COUNT);
    data.welonightelondFavCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.WelonIGHTelonD_FAVORITelon_COUNT);
    data.welonightelondQuotelonCount = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.WelonIGHTelonD_QUOTelon_COUNT);

    Doublelon quelonrySpeloncificScorelonAdjustmelonnt = params.quelonrySpeloncificScorelonAdjustmelonnts == null ? null
        : params.quelonrySpeloncificScorelonAdjustmelonnts.gelont(twelonelontIDMappelonr.gelontTwelonelontID(gelontCurrelonntDocID()));
    data.quelonrySpeloncificScorelon =
        quelonrySpeloncificScorelonAdjustmelonnt == null ? 0.0 : quelonrySpeloncificScorelonAdjustmelonnt;

    data.authorSpeloncificScorelon = params.authorSpeloncificScorelonAdjustmelonnts == null
        ? 0.0
        : params.authorSpeloncificScorelonAdjustmelonnts.gelontOrDelonfault(data.fromUselonrId, 0.0);

    // relonspelonct social filtelonr typelon
    if (params.socialFiltelonrTypelon != null && !data.isSelonlfTwelonelont) {
      if ((params.socialFiltelonrTypelon == ThriftSocialFiltelonrTypelon.ALL
              && !data.isFollow && !data.isTrustelond)
          || (params.socialFiltelonrTypelon == ThriftSocialFiltelonrTypelon.TRUSTelonD && !data.isTrustelond)
          || (params.socialFiltelonrTypelon == ThriftSocialFiltelonrTypelon.FOLLOWS && !data.isFollow)) {
        // welon can skip this hit as welon only want social relonsults in this modelon.
        data.skipRelonason = SkipRelonason.SOCIAL_FILTelonR;
        relonturn data;
      }
    }

    // 1. first apply all thelon filtelonrs to only non-follow twelonelonts and non-velonrifielond accounts,
    //    but belon telonndelonr to selonntinelonl valuelons
    // unlelonss you speloncifically askelond to apply filtelonrs relongardlelonss
    if (params.applyFiltelonrsAlways
            || (!data.isSelonlfTwelonelont && !data.isFollow && !data.isFromVelonrifielondAccount
                && !data.isFromBluelonVelonrifielondAccount)) {
      if (data.uselonrRelonp < params.relonputationMinVal
          // don't filtelonr unselont uselonrrelonps, welon givelon thelonm thelon belonnelonfit of doubt and lelont it
          // continuelon to scoring. uselonrrelonp is unselont whelonn elonithelonr uselonr just signelond up or
          // during ingelonstion timelon welon had troublelon gelontting uselonrrelonp from relonputation selonrvicelon.
          && data.uselonrRelonp != RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL) {
        data.skipRelonason = SkipRelonason.LOW_RelonPUTATION;
        relonturn data;
      } elonlselon if (data.telonxtScorelon < params.telonxtScorelonMinVal
                 // don't filtelonr unselont telonxt scorelons, uselon goodwill valuelon
                 && data.telonxtScorelon != RelonlelonvancelonSignalConstants.UNSelonT_TelonXT_SCORelon_SelonNTINelonL) {
        data.skipRelonason = SkipRelonason.LOW_TelonXT_SCORelon;
        relonturn data;
      } elonlselon if (data.relontwelonelontCountPostLog2 != LinelonarScoringData.UNSelonT_SIGNAL_VALUelon
                 && data.relontwelonelontCountPostLog2 < params.relontwelonelontMinVal) {
        data.skipRelonason = SkipRelonason.LOW_RelonTWelonelonT_COUNT;
        relonturn data;
      } elonlselon if (data.favCountPostLog2 != LinelonarScoringData.UNSelonT_SIGNAL_VALUelon
                 && data.favCountPostLog2 < params.favMinVal) {
        data.skipRelonason = SkipRelonason.LOW_FAV_COUNT;
        relonturn data;
      }
    }

    // if selonntinelonl valuelon is selont, assumelon goodwill scorelon and lelont scoring continuelon.
    if (data.telonxtScorelon == RelonlelonvancelonSignalConstants.UNSelonT_TelonXT_SCORelon_SelonNTINelonL) {
      data.telonxtScorelon = RelonlelonvancelonSignalConstants.GOODWILL_TelonXT_SCORelon;
    }
    if (data.uselonrRelonp == RelonlelonvancelonSignalConstants.UNSelonT_RelonPUTATION_SelonNTINelonL) {
      data.uselonrRelonp = RelonlelonvancelonSignalConstants.GOODWILL_RelonPUTATION;
    }

    data.twelonelontAgelonInSelonconds = now - timelonMappelonr.gelontTimelon(gelontCurrelonntDocID());
    if (data.twelonelontAgelonInSelonconds < 0) {
      data.twelonelontAgelonInSelonconds = 0; // Agelon cannot belon nelongativelon
    }

    // Thelon PARUS_SCORelon felonaturelon should belon relonad as is.
    data.parusScorelon = documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.PARUS_SCORelon);

    data.isNullcast = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_NULLCAST_FLAG);
    data.hasUrl =  documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_LINK_FLAG);
    data.hasImagelonUrl = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_IMAGelon_URL_FLAG);
    data.hasVidelonoUrl = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_VIDelonO_URL_FLAG);
    data.hasNelonwsUrl = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_NelonWS_URL_FLAG);
    data.isRelonply =  documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_RelonPLY_FLAG);
    data.isRelontwelonelont = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_RelonTWelonelonT_FLAG);
    data.isOffelonnsivelon = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_OFFelonNSIVelon_FLAG);
    data.hasTrelonnd = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_TRelonND_FLAG);
    data.hasMultiplelonHashtagsOrTrelonnds =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS_FLAG);
    data.isUselonrSpam = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_USelonR_SPAM_FLAG);
    data.isUselonrNSFW = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_USelonR_NSFW_FLAG)
        || uselonrTablelon.isSelont(data.fromUselonrId, UselonrTablelon.NSFW_BIT);
    data.isUselonrAntiSocial =
        uselonrTablelon.isSelont(data.fromUselonrId, UselonrTablelon.ANTISOCIAL_BIT);
    data.isUselonrBot = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_USelonR_BOT_FLAG);
    data.hasCard = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_CARD_FLAG);
    data.cardTypelon = SelonarchCardTypelon.UNKNOWN.gelontBytelonValuelon();
    if (data.hasCard) {
      data.cardTypelon =
          (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.CARD_TYPelon_CSF_FIelonLD);
    }
    data.hasVisiblelonLink = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_VISIBLelon_LINK_FLAG);

    data.hasConsumelonrVidelono =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_CONSUMelonR_VIDelonO_FLAG);
    data.hasProVidelono = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_PRO_VIDelonO_FLAG);
    data.hasVinelon = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_VINelon_FLAG);
    data.hasPelonriscopelon = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_PelonRISCOPelon_FLAG);
    data.hasNativelonImagelon = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_NATIVelon_IMAGelon_FLAG);
    data.hasQuotelon = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_QUOTelon_FLAG);
    data.isComposelonrSourcelonCamelonra =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.COMPOSelonR_SOURCelon_IS_CAMelonRA_FLAG);

    // Only relonad thelon sharelond status if thelon isRelontwelonelont or isRelonply bit is truelon (minor optimization).
    if (data.isRelontwelonelont || (params.gelontInRelonplyToStatusId && data.isRelonply)) {
      data.sharelondStatusId =
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.SHARelonD_STATUS_ID_CSF);
    }

    // Only relonad thelon relonfelonrelonncelon twelonelont author ID if thelon isRelontwelonelont or isRelonply bit
    // is truelon (minor optimization).
    if (data.isRelontwelonelont || data.isRelonply) {
      // thelon RelonFelonRelonNCelon_AUTHOR_ID_CSF storelons thelon sourcelon twelonelont author id for all relontwelonelonts
      long relonfelonrelonncelonAuthorId =
          documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_CSF);
      if (relonfelonrelonncelonAuthorId > 0) {
        data.relonfelonrelonncelonAuthorId = relonfelonrelonncelonAuthorId;
      } elonlselon {
        // welon also storelon thelon relonfelonrelonncelon author id for relontwelonelonts, direlonctelond at twelonelonts, and selonlf threlonadelond
        // twelonelonts selonparatelonly on Relonaltimelon/Protelonctelond elonarlybirds. This data will belon movelond to thelon
        // RelonFelonRelonNCelon_AUTHOR_ID_CSF and thelonselon fielonlds will belon delonpreloncatelond in SelonARCH-34958.
        relonfelonrelonncelonAuthorId = LongIntConvelonrtelonr.convelonrtTwoIntToOnelonLong(
            (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(
                elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_MOST_SIGNIFICANT_INT),
            (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(
                elonarlybirdFielonldConstant.RelonFelonRelonNCelon_AUTHOR_ID_LelonAST_SIGNIFICANT_INT));
        if (relonfelonrelonncelonAuthorId > 0) {
          data.relonfelonrelonncelonAuthorId = relonfelonrelonncelonAuthorId;
        }
      }
    }

    // Convelonrt languagelon to a thrift languagelon and thelonn back to an int in ordelonr to
    // elonnsurelon a valuelon compatiblelon with our currelonnt ThriftLanguagelon delonfinition.
    ThriftLanguagelon twelonelontLang = ThriftLanguagelonUtil.safelonFindByValuelon(
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LANGUAGelon));
    data.twelonelontLangId = twelonelontLang.gelontValuelon();
    // Selont thelon languagelon-relonlatelond felonaturelons helonrelon so that thelony can belon latelonr uselond in promotion/delonmotion
    // and also belon transfelonrrelond to ThriftSelonarchRelonsultMelontadata
    data.uselonrLangMult = computelonUselonrLangMultiplielonr(data, params);
    data.hasDiffelonrelonntLang = params.uiLangId != ThriftLanguagelon.UNKNOWN.gelontValuelon()
        && params.uiLangId != data.twelonelontLangId;
    data.haselonnglishTwelonelontAndDiffelonrelonntUILang = data.hasDiffelonrelonntLang
        && data.twelonelontLangId == ThriftLanguagelon.elonNGLISH.gelontValuelon();
    data.haselonnglishUIAndDiffelonrelonntTwelonelontLang = data.hasDiffelonrelonntLang
        && params.uiLangId == ThriftLanguagelon.elonNGLISH.gelontValuelon();

    // elonxposelond all thelonselon felonaturelons for thelon clielonnts.
    data.isSelonnsitivelonContelonnt =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_SelonNSITIVelon_CONTelonNT);
    data.hasMultiplelonMelondiaFlag =
        documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.HAS_MULTIPLelon_MelonDIA_FLAG);
    data.profilelonIselonggFlag = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.PROFILelon_IS_elonGG_FLAG);
    data.isUselonrNelonwFlag = documelonntFelonaturelons.isFlagSelont(elonarlybirdFielonldConstant.IS_USelonR_NelonW_FLAG);
    data.numMelonntions = (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.NUM_MelonNTIONS);
    data.numHashtags = (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.NUM_HASHTAGS);
    data.linkLanguagelon =
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.LINK_LANGUAGelon);
    data.prelonvUselonrTwelonelontelonngagelonmelonnt =
        (int) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT);

    // helonalth modelonl scorelons by HML
    data.toxicityScorelon = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.TOXICITY_SCORelon);
    data.pBlockScorelon = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.PBLOCK_SCORelon);
    data.pSpammyTwelonelontScorelon = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.P_SPAMMY_TWelonelonT_SCORelon);
    data.pRelonportelondTwelonelontScorelon = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.P_RelonPORTelonD_TWelonelonT_SCORelon);
    data.spammyTwelonelontContelonntScorelon = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.SPAMMY_TWelonelonT_CONTelonNT_SCORelon
    );
    data.elonxpelonrimelonntalHelonalthModelonlScorelon1 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_1);
    data.elonxpelonrimelonntalHelonalthModelonlScorelon2 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_2);
    data.elonxpelonrimelonntalHelonalthModelonlScorelon3 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_3);
    data.elonxpelonrimelonntalHelonalthModelonlScorelon4 = documelonntFelonaturelons.gelontUnnormalizelondFelonaturelonValuelon(
        elonarlybirdFielonldConstant.elonXPelonRIMelonNTAL_HelonALTH_MODelonL_SCORelon_4);

    relonturn data;
  }

  protelonctelond float scorelonIntelonrnal(
      float lucelonnelonQuelonryScorelon, elonxplanationWrappelonr elonxplanation) throws IOelonxcelonption {
    LinelonarScoringData data = updatelonLinelonarScoringData(lucelonnelonQuelonryScorelon);
    if (data.skipRelonason != null && data.skipRelonason != SkipRelonason.NOT_SKIPPelonD) {
      relonturn finalizelonScorelon(data, elonxplanation, SKIP_HIT);
    }

    doublelon scorelon = computelonScorelon(data, elonxplanation != null);
    relonturn postScorelonComputation(data, scorelon, truelon, elonxplanation);
  }

  protelonctelond float postScorelonComputation(
      LinelonarScoringData data,
      doublelon scorelon,
      boolelonan boostScorelonWithHitAttribution,
      elonxplanationWrappelonr elonxplanation) throws IOelonxcelonption {
    doublelon modifielondScorelon = scorelon;
    data.scorelonBelonforelonBoost = modifielondScorelon;
    if (params.applyBoosts) {
      modifielondScorelon =
          applyBoosts(data, modifielondScorelon, boostScorelonWithHitAttribution, elonxplanation != null);
    }
    // Final adjustmelonnt to avoid too-low scorelons.
    modifielondScorelon *= SCORelon_ADJUSTelonR;
    data.scorelonAftelonrBoost = modifielondScorelon;

    // 3. final scorelon filtelonr
    data.scorelonFinal = modifielondScorelon;
    if ((params.applyFiltelonrsAlways || (!data.isSelonlfTwelonelont && !data.isFollow))
        && modifielondScorelon < params.minScorelon) {
      data.skipRelonason = SkipRelonason.LOW_FINAL_SCORelon;
      modifielondScorelon = SKIP_HIT;
    }

    // clelonar fielonld hits
    this.fielonldHitAttribution = null;
    relonturn finalizelonScorelon(data, elonxplanation, modifielondScorelon);
  }

  /**
   * Applying promotion/delonmotion to thelon scorelons gelonnelonratelond by felonaturelon-baselond scoring functions
   *
   * @param data Original LinelonarScoringData (to belon modifielond with boosts helonrelon)
   * @param scorelon Scorelon gelonnelonratelond by thelon felonaturelon-baselond scoring function
   * @param withHitAttribution Delontelonrminelons if hit attribution data should belon includelond.
   * @param forelonxplanation Indicatelons if thelon scorelon will belon computelond for gelonnelonrating thelon elonxplanation.
   * @relonturn Scorelon aftelonr applying promotion/delonmotion
   */
  privatelon doublelon applyBoosts(
      LinelonarScoringData data,
      doublelon scorelon,
      boolelonan withHitAttribution,
      boolelonan forelonxplanation) {
    doublelon boostelondScorelon = scorelon;

    if (params.uselonLucelonnelonScorelonAsBoost) {
      data.normalizelondLucelonnelonScorelon = normalizelonLucelonnelonScorelon(
          (float) data.lucelonnelonScorelon, (float) params.maxLucelonnelonScorelonBoost);
      boostelondScorelon *= data.normalizelondLucelonnelonScorelon;
    }
    if (data.isOffelonnsivelon) {
      boostelondScorelon *= params.offelonnsivelonDamping;
    }
    if (data.isUselonrSpam && params.spamUselonrDamping != LinelonarScoringData.NO_BOOST_VALUelon) {
      data.spamUselonrDampApplielond = truelon;
      boostelondScorelon *= params.spamUselonrDamping;
    }
    if (data.isUselonrNSFW && params.nsfwUselonrDamping != LinelonarScoringData.NO_BOOST_VALUelon) {
      data.nsfwUselonrDampApplielond = truelon;
      boostelondScorelon *= params.nsfwUselonrDamping;
    }
    if (data.isUselonrBot && params.botUselonrDamping != LinelonarScoringData.NO_BOOST_VALUelon) {
      data.botUselonrDampApplielond = truelon;
      boostelondScorelon *= params.botUselonrDamping;
    }

    // cards
    if (data.hasCard && params.hasCardBoosts[data.cardTypelon] != LinelonarScoringData.NO_BOOST_VALUelon) {
      boostelondScorelon *= params.hasCardBoosts[data.cardTypelon];
      data.hasCardBoostApplielond = truelon;
    }

    // trelonnds
    if (data.hasMultiplelonHashtagsOrTrelonnds) {
      boostelondScorelon *= params.multiplelonHashtagsOrTrelonndsDamping;
    } elonlselon if (data.hasTrelonnd) {
      data.twelonelontHasTrelonndsBoostApplielond = truelon;
      boostelondScorelon *= params.twelonelontHasTrelonndBoost;
    }

    // Melondia/Nelonws url boosts.
    if (data.hasImagelonUrl || data.hasVidelonoUrl) {
      data.hasMelondialUrlBoostApplielond = truelon;
      boostelondScorelon *= params.twelonelontHasMelondiaUrlBoost;
    }
    if (data.hasNelonwsUrl) {
      data.hasNelonwsUrlBoostApplielond = truelon;
      boostelondScorelon *= params.twelonelontHasNelonwsUrlBoost;
    }

    if (data.isFromVelonrifielondAccount) {
      data.twelonelontFromVelonrifielondAccountBoostApplielond = truelon;
      boostelondScorelon *= params.twelonelontFromVelonrifielondAccountBoost;
    }

    if (data.isFromBluelonVelonrifielondAccount) {
      data.twelonelontFromBluelonVelonrifielondAccountBoostApplielond = truelon;
      boostelondScorelon *= params.twelonelontFromBluelonVelonrifielondAccountBoost;
    }

    if (data.isFollow) {
      // direlonct follow, so boost both relonplielons and non-relonplielons.
      data.direlonctFollowBoostApplielond = truelon;
      boostelondScorelon *= params.direlonctFollowBoost;
    } elonlselon if (data.isTrustelond) {
      // trustelond circlelon
      if (!data.isRelonply) {
        // non-at-relonply, in trustelond nelontwork
        data.trustelondCirclelonBoostApplielond = truelon;
        boostelondScorelon *= params.trustelondCirclelonBoost;
      }
    } elonlselon if (data.isRelonply) {
      // at-relonply out of my nelontwork
      data.outOfNelontworkRelonplyPelonnaltyApplielond = truelon;
      boostelondScorelon -= params.outOfNelontworkRelonplyPelonnalty;
    }

    if (data.isSelonlfTwelonelont) {
      data.selonlfTwelonelontBoostApplielond = truelon;
      data.selonlfTwelonelontMult = params.selonlfTwelonelontBoost;
      boostelondScorelon *= params.selonlfTwelonelontBoost;
    }

    // Languagelon Delonmotion
    // Uselonr languagelon baselond delonmotion
    // Thelon data.uselonrLangMult is selont in scorelonIntelonrnal(), and this selontting stelonp is always belonforelon
    // thelon applying boosts stelonp
    if (params.uselonUselonrLanguagelonInfo) {
      boostelondScorelon *= data.uselonrLangMult;
    }
    // UI languagelon baselond delonmotion
    if (params.uiLangId != ThriftLanguagelon.UNKNOWN.gelontValuelon()
        && params.uiLangId != data.twelonelontLangId) {
      if (data.twelonelontLangId == ThriftLanguagelon.elonNGLISH.gelontValuelon()) {
        data.uiLangMult = params.langelonnglishTwelonelontDelonmotelon;
      } elonlselon if (params.uiLangId == ThriftLanguagelon.elonNGLISH.gelontValuelon()) {
        data.uiLangMult = params.langelonnglishUIDelonmotelon;
      } elonlselon {
        data.uiLangMult = params.langDelonfaultDelonmotelon;
      }
    } elonlselon {
      data.uiLangMult = LinelonarScoringData.NO_BOOST_VALUelon;
    }
    boostelondScorelon *= data.uiLangMult;

    if (params.uselonAgelonDeloncay) {
      // shallow sigmoid with an inflelonction point at agelonDeloncayHalflifelon
      data.agelonDeloncayMult = agelonDeloncay.gelontAgelonDeloncayMultiplielonr(data.twelonelontAgelonInSelonconds);
      boostelondScorelon *= data.agelonDeloncayMult;
    }

    // Hit Attributelon Delonmotion
    // Scoring is currelonntly baselond on tokelonnizelond uselonr namelon, telonxt, and url in thelon twelonelont
    // If hit attributelon collelonction is elonnablelond, welon delonmotelon scorelon baselond on thelonselon fielonlds
    if (hitAttributelonHelonlpelonr != null && params.elonnablelonHitDelonmotion) {

      Map<Intelongelonr, List<String>> hitMap;
      if (forelonxplanation && fielonldHitAttribution != null) {
        // if this scoring call is for gelonnelonrating an elonxplanation,
        // welon'll uselon thelon fielonldHitAttribution found in thelon selonarch relonsult's melontadata beloncauselon
        // collelonctors arelon not callelond during thelon delonbug workflow
        hitMap = Maps.transformValuelons(fielonldHitAttribution.gelontHitMap(), FielonldHitList::gelontHitFielonlds);
      } elonlselon if (withHitAttribution) {
        hitMap = hitAttributelonHelonlpelonr.gelontHitAttribution(gelontCurrelonntDocID());
      } elonlselon {
        hitMap = Maps.nelonwHashMap();
      }
      Selont<String> uniquelonFielonldHits = ImmutablelonSelont.copyOf(Itelonrablelons.concat(hitMap.valuelons()));

      data.hitFielonlds.addAll(uniquelonFielonldHits);
      // thelonrelon should always belon fielonlds that arelon hit
      // if thelonrelon arelonn't, welon assumelon this is a call from 'elonxplain' in delonbug modelon
      // do not ovelonrridelon hit attributelon data if in delonbug modelon
      if (!uniquelonFielonldHits.iselonmpty()) {
        // delonmotions baselond strictly on fielonld hits
        if (uniquelonFielonldHits.sizelon() == 1) {
          if (uniquelonFielonldHits.contains(
                  elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon())) {
            // if url was thelon only fielonld that was hit, delonmotelon
            data.hasUrlOnlyHitDelonmotionApplielond = truelon;
            boostelondScorelon *= params.urlOnlyHitDelonmotion;
          } elonlselon if (uniquelonFielonldHits.contains(
                         elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon())) {
            // if namelon was thelon only fielonld that was hit, delonmotelon
            data.hasNamelonOnlyHitDelonmotionApplielond = truelon;
            boostelondScorelon *= params.namelonOnlyHitDelonmotion;
          }
        } elonlselon if (!uniquelonFielonldHits.contains(elonarlybirdFielonldConstant.TelonXT_FIelonLD.gelontFielonldNamelon())
            && !uniquelonFielonldHits.contains(elonarlybirdFielonldConstant.MelonNTIONS_FIelonLD.gelontFielonldNamelon())
            && !uniquelonFielonldHits.contains(elonarlybirdFielonldConstant.HASHTAGS_FIelonLD.gelontFielonldNamelon())
            && !uniquelonFielonldHits.contains(elonarlybirdFielonldConstant.STOCKS_FIelonLD.gelontFielonldNamelon())) {
          // if telonxt or speloncial telonxt was nelonvelonr hit, delonmotelon
          data.hasNoTelonxtHitDelonmotionApplielond = truelon;
          boostelondScorelon *= params.noTelonxtHitDelonmotion;
        } elonlselon if (uniquelonFielonldHits.sizelon() == 2) {
          // delonmotions baselond on fielonld hit combinations
          // want to delonmotelon if welon only hit two of thelon fielonlds (onelon beloning telonxt)
          // but with selonparatelon telonrms
          Selont<String> fielonldIntelonrselonctions = QuelonryCommonFielonldHitsVisitor.findIntelonrselonction(
              hitAttributelonHelonlpelonr.gelontNodelonToRankMap(),
              hitMap,
              quelonry);

          if (fielonldIntelonrselonctions.iselonmpty()) {
            if (uniquelonFielonldHits.contains(
                    elonarlybirdFielonldConstant.TOKelonNIZelonD_FROM_USelonR_FIelonLD.gelontFielonldNamelon())) {
              // if namelon is hit but has no hits in common with telonxt, delonmotelon
              // want to delonmotelon caselons whelonrelon welon hit part of thelon pelonrson's namelon
              // and twelonelont telonxt selonparatelonly
              data.hasSelonparatelonTelonxtAndNamelonHitDelonmotionApplielond = truelon;
              boostelondScorelon *= params.selonparatelonTelonxtAndNamelonHitDelonmotion;
            } elonlselon if (uniquelonFielonldHits.contains(
                           elonarlybirdFielonldConstant.RelonSOLVelonD_LINKS_TelonXT_FIelonLD.gelontFielonldNamelon())) {
              // if url is hit but has no hits in common with telonxt, delonmotelon
              // want to delonmotelon caselons whelonrelon welon hit a potelonntial domain kelonyword
              // and twelonelont telonxt selonparatelonly
              data.hasSelonparatelonTelonxtAndUrlHitDelonmotionApplielond = truelon;
              boostelondScorelon *= params.selonparatelonTelonxtAndUrlHitDelonmotion;
            }
          }
        }
      }
    }

    relonturn boostelondScorelon;
  }

  /**
   * Computelon thelon uselonr languagelon baselond delonmotion multiplielonr
   */
  privatelon static doublelon computelonUselonrLangMultiplielonr(
      LinelonarScoringData data, LinelonarScoringParams params) {
    if (data.twelonelontLangId == params.uiLangId
        && data.twelonelontLangId != ThriftLanguagelon.UNKNOWN.gelontValuelon()) {
      // elonffelonctivelonly thelon uiLang is considelonrelond a languagelon that uselonr knows with 1.0 confidelonncelon.
      relonturn LinelonarScoringData.NO_BOOST_VALUelon;
    }

    if (params.uselonrLangs[data.twelonelontLangId] > 0.0) {
      relonturn params.uselonrLangs[data.twelonelontLangId];
    }

    relonturn params.unknownLanguagelonBoost;
  }

  /**
   * Computelons thelon scorelon of thelon documelonnt that it's currelonntly beloning elonvaluatelond.
   *
   * Thelon elonxtractelond felonaturelons from thelon documelonnt arelon availablelon in thelon fielonld 'data'.
   *
   * @param data Thelon LinelonarScoringData instancelon that will storelon thelon documelonnt felonaturelons.
   * @param forelonxplanation Indicatelons if thelon scorelon will belon computelond for gelonnelonrating thelon elonxplanation.
   */
  protelonctelond abstract doublelon computelonScorelon(
      LinelonarScoringData data, boolelonan forelonxplanation) throws IOelonxcelonption;

  privatelon float finalizelonScorelon(
      LinelonarScoringData scoringData,
      elonxplanationWrappelonr elonxplanation,
      doublelon scorelon) throws IOelonxcelonption {
    scoringData.scorelonRelonturnelond = scorelon;
    if (elonxplanation != null) {
      elonxplanation.elonxplanation = gelonnelonratelonelonxplanation(scoringData);
    }
    relonturn (float) scorelon;
  }

  @Ovelonrridelon
  protelonctelond void initializelonNelonxtSelongmelonnt(elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr)
      throws IOelonxcelonption {
    if (antiGamingFiltelonr != null) {
      antiGamingFiltelonr.startSelongmelonnt(relonadelonr);
    }
  }

  /*
   * Gelonnelonratelon thelon scoring elonxplanation for delonbug.
   */
  privatelon elonxplanation gelonnelonratelonelonxplanation(LinelonarScoringData scoringData) throws IOelonxcelonption {
    final List<elonxplanation> delontails = Lists.nelonwArrayList();

    delontails.add(elonxplanation.match(0.0f, "[PROPelonRTIelonS] "
        + scoringData.gelontPropelonrtyelonxplanation()));

    // 1. Filtelonrs
    boolelonan isHit = scoringData.skipRelonason == SkipRelonason.NOT_SKIPPelonD;
    if (scoringData.skipRelonason == SkipRelonason.ANTIGAMING) {
      delontails.add(elonxplanation.noMatch("SKIPPelonD for antigaming"));
    }
    if (scoringData.skipRelonason == SkipRelonason.LOW_RelonPUTATION) {
      delontails.add(elonxplanation.noMatch(
          String.format("SKIPPelonD for low relonputation: %.3f < %.3f",
              scoringData.uselonrRelonp, params.relonputationMinVal)));
    }
    if (scoringData.skipRelonason == SkipRelonason.LOW_TelonXT_SCORelon) {
      delontails.add(elonxplanation.noMatch(
          String.format("SKIPPelonD for low telonxt scorelon: %.3f < %.3f",
              scoringData.telonxtScorelon, params.telonxtScorelonMinVal)));
    }
    if (scoringData.skipRelonason == SkipRelonason.LOW_RelonTWelonelonT_COUNT) {
      delontails.add(elonxplanation.noMatch(
          String.format("SKIPPelonD for low relontwelonelont count: %.3f < %.3f",
              scoringData.relontwelonelontCountPostLog2, params.relontwelonelontMinVal)));
    }
    if (scoringData.skipRelonason == SkipRelonason.LOW_FAV_COUNT) {
      delontails.add(elonxplanation.noMatch(
          String.format("SKIPPelonD for low fav count: %.3f < %.3f",
              scoringData.favCountPostLog2, params.favMinVal)));
    }
    if (scoringData.skipRelonason == SkipRelonason.SOCIAL_FILTelonR) {
      delontails.add(elonxplanation.noMatch("SKIPPelonD for not in thelon right social circlelon"));
    }

    // 2. elonxplanation delonpelonnding on thelon scoring typelon
    gelonnelonratelonelonxplanationForScoring(scoringData, isHit, delontails);

    // 3. elonxplanation delonpelonnding on boosts
    if (params.applyBoosts) {
      gelonnelonratelonelonxplanationForBoosts(scoringData, isHit, delontails);
    }

    // 4. Final scorelon filtelonr.
    if (scoringData.skipRelonason == SkipRelonason.LOW_FINAL_SCORelon) {
      delontails.add(elonxplanation.noMatch("SKIPPelonD for low final scorelon: " + scoringData.scorelonFinal));
      isHit = falselon;
    }

    String hostAndSelongmelonnt = String.format("%s host = %s  selongmelonnt = %s",
        functionNamelon, DatabaselonConfig.gelontLocalHostnamelon(), DatabaselonConfig.gelontDatabaselon());
    if (isHit) {
      relonturn elonxplanation.match((float) scoringData.scorelonFinal, hostAndSelongmelonnt, delontails);
    } elonlselon {
      relonturn elonxplanation.noMatch(hostAndSelongmelonnt, delontails);
    }
  }

  /**
   * Gelonnelonratelons thelon elonxplanation for thelon documelonnt that is currelonntly beloning elonvaluatelond.
   *
   * Implelonmelonntations of this melonthod must uselon thelon 'delontails' paramelontelonr to collelonct its output.
   *
   * @param scoringData Scoring componelonnts for thelon documelonnt
   * @param isHit Indicatelons whelonthelonr thelon documelonnt is not skippelond
   * @param delontails Delontails of thelon elonxplanation. Uselond to collelonct thelon output.
   */
  protelonctelond abstract void gelonnelonratelonelonxplanationForScoring(
      LinelonarScoringData scoringData, boolelonan isHit, List<elonxplanation> delontails) throws IOelonxcelonption;

  /**
   * Gelonnelonratelons thelon boosts part of thelon elonxplanation for thelon documelonnt that is currelonntly
   * beloning elonvaluatelond.
   */
  privatelon void gelonnelonratelonelonxplanationForBoosts(
      LinelonarScoringData scoringData,
      boolelonan isHit,
      List<elonxplanation> delontails) {
    List<elonxplanation> boostDelontails = Lists.nelonwArrayList();

    boostDelontails.add(elonxplanation.match((float) scoringData.scorelonBelonforelonBoost, "Scorelon belonforelon boost"));


    // Lucelonnelon scorelon boost
    if (params.uselonLucelonnelonScorelonAsBoost) {
      boostDelontails.add(elonxplanation.match(
          (float) scoringData.normalizelondLucelonnelonScorelon,
          String.format("[x] Lucelonnelon scorelon boost, lucelonnelonScorelon=%.3f",
              scoringData.lucelonnelonScorelon)));
    }

    // card boost
    if (scoringData.hasCardBoostApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.hasCardBoosts[scoringData.cardTypelon],
          "[x] card boost for typelon " + SelonarchCardTypelon.cardTypelonFromBytelonValuelon(scoringData.cardTypelon)));
    }

    // Offelonnsivelon
    if (scoringData.isOffelonnsivelon) {
      boostDelontails.add(elonxplanation.match((float) params.offelonnsivelonDamping, "[x] Offelonnsivelon damping"));
    } elonlselon {
      boostDelontails.add(elonxplanation.match(LinelonarScoringData.NO_BOOST_VALUelon,
          String.format("Not Offelonnsivelon, damping=%.3f", params.offelonnsivelonDamping)));
    }

    // Spam
    if (scoringData.spamUselonrDampApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.spamUselonrDamping, "[x] Spam"));
    }
    // NSFW
    if (scoringData.nsfwUselonrDampApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.nsfwUselonrDamping, "[X] NSFW"));
    }
    // Bot
    if (scoringData.botUselonrDampApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.botUselonrDamping, "[X] Bot"));
    }

    // Multiplelon hashtags or trelonnds
    if (scoringData.hasMultiplelonHashtagsOrTrelonnds) {
      boostDelontails.add(elonxplanation.match((float) params.multiplelonHashtagsOrTrelonndsDamping,
          "[x] Multiplelon hashtags or trelonnds boost"));
    } elonlselon {
      boostDelontails.add(elonxplanation.match(LinelonarScoringData.NO_BOOST_VALUelon,
          String.format("No multiplelon hashtags or trelonnds, damping=%.3f",
              params.multiplelonHashtagsOrTrelonndsDamping)));
    }

    if (scoringData.twelonelontHasTrelonndsBoostApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.twelonelontHasTrelonndBoost, "[x] Twelonelont has trelonnd boost"));
    }

    if (scoringData.hasMelondialUrlBoostApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.twelonelontHasMelondiaUrlBoost, "[x] Melondia url boost"));
    }

    if (scoringData.hasNelonwsUrlBoostApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.twelonelontHasNelonwsUrlBoost, "[x] Nelonws url boost"));
    }

    boostDelontails.add(elonxplanation.match(0.0f, "[FIelonLDS HIT] " + scoringData.hitFielonlds));

    if (scoringData.hasNoTelonxtHitDelonmotionApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.noTelonxtHitDelonmotion, "[x] No telonxt hit delonmotion"));
    }

    if (scoringData.hasUrlOnlyHitDelonmotionApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.urlOnlyHitDelonmotion, "[x] URL only hit delonmotion"));
    }

    if (scoringData.hasNamelonOnlyHitDelonmotionApplielond) {
      boostDelontails.add(elonxplanation.match(
          (float) params.namelonOnlyHitDelonmotion, "[x] Namelon only hit delonmotion"));
    }

    if (scoringData.hasSelonparatelonTelonxtAndNamelonHitDelonmotionApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.selonparatelonTelonxtAndNamelonHitDelonmotion,
          "[x] Selonparatelon telonxt/namelon delonmotion"));
    }

    if (scoringData.hasSelonparatelonTelonxtAndUrlHitDelonmotionApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.selonparatelonTelonxtAndUrlHitDelonmotion,
          "[x] Selonparatelon telonxt/url delonmotion"));
    }

    if (scoringData.twelonelontFromVelonrifielondAccountBoostApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.twelonelontFromVelonrifielondAccountBoost,
          "[x] Velonrifielond account boost"));
    }

    if (scoringData.twelonelontFromBluelonVelonrifielondAccountBoostApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.twelonelontFromBluelonVelonrifielondAccountBoost,
          "[x] Bluelon-velonrifielond account boost"));
    }

    if (scoringData.selonlfTwelonelontBoostApplielond) {
      boostDelontails.add(elonxplanation.match((float) params.selonlfTwelonelontBoost,
          "[x] Selonlf twelonelont boost"));
    }

    if (scoringData.skipRelonason == LinelonarScoringData.SkipRelonason.SOCIAL_FILTelonR) {
      boostDelontails.add(elonxplanation.noMatch("SKIPPelonD for social filtelonr"));
    } elonlselon {
      if (scoringData.direlonctFollowBoostApplielond) {
        boostDelontails.add(elonxplanation.match((float) params.direlonctFollowBoost,
            "[x] Direlonct follow boost"));
      }
      if (scoringData.trustelondCirclelonBoostApplielond) {
        boostDelontails.add(elonxplanation.match((float) params.trustelondCirclelonBoost,
            "[x] Trustelond circlelon boost"));
      }
      if (scoringData.outOfNelontworkRelonplyPelonnaltyApplielond) {
        boostDelontails.add(elonxplanation.match((float) params.outOfNelontworkRelonplyPelonnalty,
            "[-] Out of nelontwork relonply pelonnalty"));
      }
    }

    // Languagelon delonmotions
    String langDelontails = String.format(
        "twelonelontLang=[%s] uiLang=[%s]",
        ThriftLanguagelonUtil.gelontLocalelonOf(
            ThriftLanguagelon.findByValuelon(scoringData.twelonelontLangId)).gelontLanguagelon(),
        ThriftLanguagelonUtil.gelontLocalelonOf(ThriftLanguagelon.findByValuelon(params.uiLangId)).gelontLanguagelon());
    if (scoringData.uiLangMult == 1.0) {
      boostDelontails.add(elonxplanation.match(
          LinelonarScoringData.NO_BOOST_VALUelon, "No UI Languagelon delonmotion: " + langDelontails));
    } elonlselon {
      boostDelontails.add(elonxplanation.match(
          (float) scoringData.uiLangMult, "[x] UI LangMult: " + langDelontails));
    }
    StringBuildelonr uselonrLangDelontails = nelonw StringBuildelonr();
    uselonrLangDelontails.appelonnd("uselonrLang=[");
    for (int i = 0; i < params.uselonrLangs.lelonngth; i++) {
      if (params.uselonrLangs[i] > 0.0) {
        String lang = ThriftLanguagelonUtil.gelontLocalelonOf(ThriftLanguagelon.findByValuelon(i)).gelontLanguagelon();
        uselonrLangDelontails.appelonnd(String.format("%s:%.3f,", lang, params.uselonrLangs[i]));
      }
    }
    uselonrLangDelontails.appelonnd("]");
    if (!params.uselonUselonrLanguagelonInfo) {
      boostDelontails.add(elonxplanation.noMatch(
          "No Uselonr Languagelon Delonmotion: " + uselonrLangDelontails.toString()));
    } elonlselon {
      boostDelontails.add(elonxplanation.match(
          (float) scoringData.uselonrLangMult,
          "[x] Uselonr LangMult: " + uselonrLangDelontails.toString()));
    }

    // Agelon deloncay
    String agelonDeloncayDelontails = String.format(
        "agelon=%d selonconds, slopelon=%.3f, baselon=%.1f, half-lifelon=%.0f",
        scoringData.twelonelontAgelonInSelonconds, params.agelonDeloncaySlopelon,
        params.agelonDeloncayBaselon, params.agelonDeloncayHalflifelon);
    if (params.uselonAgelonDeloncay) {
      boostDelontails.add(elonxplanation.match(
          (float) scoringData.agelonDeloncayMult, "[x] AgelonDeloncay: " + agelonDeloncayDelontails));
    } elonlselon {
      boostDelontails.add(elonxplanation.match(1.0f, "Agelon deloncay disablelond: " + agelonDeloncayDelontails));
    }

    // Scorelon adjustelonr
    boostDelontails.add(elonxplanation.match(SCORelon_ADJUSTelonR, "[x] scorelon adjustelonr"));

    elonxplanation boostCombo = isHit
        ? elonxplanation.match((float) scoringData.scorelonAftelonrBoost,
          "(MATCH) Aftelonr Boosts and Delonmotions:", boostDelontails)
        : elonxplanation.noMatch("Aftelonr Boosts and Delonmotions:", boostDelontails);

    delontails.add(boostCombo);
  }

  @Ovelonrridelon
  protelonctelond elonxplanation doelonxplain(float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    // Run thelon scorelonr again and gelont thelon elonxplanation.
    elonxplanationWrappelonr elonxplanation = nelonw elonxplanationWrappelonr();
    scorelonIntelonrnal(lucelonnelonQuelonryScorelon, elonxplanation);
    relonturn elonxplanation.elonxplanation;
  }

  @Ovelonrridelon
  public void populatelonRelonsultMelontadataBaselondOnScoringData(
      ThriftSelonarchRelonsultMelontadataOptions options,
      ThriftSelonarchRelonsultMelontadata melontadata,
      LinelonarScoringData data) throws IOelonxcelonption {
    melontadata.selontRelonsultTypelon(selonarchRelonsultTypelon);
    melontadata.selontScorelon(data.scorelonRelonturnelond);
    melontadata.selontFromUselonrId(data.fromUselonrId);

    if (data.isTrustelond) {
      melontadata.selontIsTrustelond(truelon);
    }
    if (data.isFollow) {
      melontadata.selontIsFollow(truelon);
    }
    if (data.skipRelonason != SkipRelonason.NOT_SKIPPelonD) {
      melontadata.selontSkippelond(truelon);
    }
    if ((data.isRelontwelonelont || (params.gelontInRelonplyToStatusId && data.isRelonply))
        && data.sharelondStatusId != LinelonarScoringData.UNSelonT_SIGNAL_VALUelon) {
      melontadata.selontSharelondStatusId(data.sharelondStatusId);
    }
    if (data.hasCard) {
      melontadata.selontCardTypelon(data.cardTypelon);
    }

    // Optional felonaturelons.  Notelon: othelonr optional melontadata is populatelond by
    // AbstractRelonlelonvancelonCollelonctor, not thelon scoring function.

    if (options.isGelontLucelonnelonScorelon()) {
      melontadata.selontLucelonnelonScorelon(data.lucelonnelonScorelon);
    }
    if (options.isGelontRelonfelonrelonncelondTwelonelontAuthorId()
        && data.relonfelonrelonncelonAuthorId != LinelonarScoringData.UNSelonT_SIGNAL_VALUelon) {
      melontadata.selontRelonfelonrelonncelondTwelonelontAuthorId(data.relonfelonrelonncelonAuthorId);
    }

    if (options.isGelontMelondiaBits()) {
      melontadata.selontHasConsumelonrVidelono(data.hasConsumelonrVidelono);
      melontadata.selontHasProVidelono(data.hasProVidelono);
      melontadata.selontHasVinelon(data.hasVinelon);
      melontadata.selontHasPelonriscopelon(data.hasPelonriscopelon);
      boolelonan hasNativelonVidelono =
          data.hasConsumelonrVidelono || data.hasProVidelono || data.hasVinelon || data.hasPelonriscopelon;
      melontadata.selontHasNativelonVidelono(hasNativelonVidelono);
      melontadata.selontHasNativelonImagelon(data.hasNativelonImagelon);
    }

    melontadata
        .selontIsOffelonnsivelon(data.isOffelonnsivelon)
        .selontIsRelonply(data.isRelonply)
        .selontIsRelontwelonelont(data.isRelontwelonelont)
        .selontHasLink(data.hasUrl)
        .selontHasTrelonnd(data.hasTrelonnd)
        .selontHasMultiplelonHashtagsOrTrelonnds(data.hasMultiplelonHashtagsOrTrelonnds)
        .selontRelontwelonelontCount((int) data.relontwelonelontCountPostLog2)
        .selontFavCount((int) data.favCountPostLog2)
        .selontRelonplyCount((int) data.relonplyCountPostLog2)
        .selontelonmbelondsImprelonssionCount((int) data.elonmbelondsImprelonssionCount)
        .selontelonmbelondsUrlCount((int) data.elonmbelondsUrlCount)
        .selontVidelonoVielonwCount((int) data.videlonoVielonwCount)
        .selontRelonsultTypelon(selonarchRelonsultTypelon)
        .selontFromVelonrifielondAccount(data.isFromVelonrifielondAccount)
        .selontIsUselonrSpam(data.isUselonrSpam)
        .selontIsUselonrNSFW(data.isUselonrNSFW)
        .selontIsUselonrBot(data.isUselonrBot)
        .selontHasImagelon(data.hasImagelonUrl)
        .selontHasVidelono(data.hasVidelonoUrl)
        .selontHasNelonws(data.hasNelonwsUrl)
        .selontHasCard(data.hasCard)
        .selontHasVisiblelonLink(data.hasVisiblelonLink)
        .selontParusScorelon(data.parusScorelon)
        .selontTelonxtScorelon(data.telonxtScorelon)
        .selontUselonrRelonp(data.uselonrRelonp)
        .selontTokelonnAt140DividelondByNumTokelonnsBuckelont(data.tokelonnAt140DividelondByNumTokelonnsBuckelont);

    if (!melontadata.isSelontelonxtraMelontadata()) {
      melontadata.selontelonxtraMelontadata(nelonw ThriftSelonarchRelonsultelonxtraMelontadata());
    }
    ThriftSelonarchRelonsultelonxtraMelontadata elonxtraMelontadata = melontadata.gelontelonxtraMelontadata();

    // Promotion/Delonmotion felonaturelons
    elonxtraMelontadata.selontUselonrLangScorelon(data.uselonrLangMult)
        .selontHasDiffelonrelonntLang(data.hasDiffelonrelonntLang)
        .selontHaselonnglishTwelonelontAndDiffelonrelonntUILang(data.haselonnglishTwelonelontAndDiffelonrelonntUILang)
        .selontHaselonnglishUIAndDiffelonrelonntTwelonelontLang(data.haselonnglishUIAndDiffelonrelonntTwelonelontLang)
        .selontHasQuotelon(data.hasQuotelon)
        .selontQuotelondCount((int) data.quotelondCount)
        .selontWelonightelondRelontwelonelontCount((int) data.welonightelondRelontwelonelontCount)
        .selontWelonightelondRelonplyCount((int) data.welonightelondRelonplyCount)
        .selontWelonightelondFavCount((int) data.welonightelondFavCount)
        .selontWelonightelondQuotelonCount((int) data.welonightelondQuotelonCount)
        .selontQuelonrySpeloncificScorelon(data.quelonrySpeloncificScorelon)
        .selontAuthorSpeloncificScorelon(data.authorSpeloncificScorelon)
        .selontRelontwelonelontCountV2((int) data.relontwelonelontCountV2)
        .selontFavCountV2((int) data.favCountV2)
        .selontRelonplyCountV2((int) data.relonplyCountV2)
        .selontIsComposelonrSourcelonCamelonra(data.isComposelonrSourcelonCamelonra)
        .selontFromBluelonVelonrifielondAccount(data.isFromBluelonVelonrifielondAccount);

    // Helonalth modelonl scorelons felonaturelons
    elonxtraMelontadata
        .selontToxicityScorelon(data.toxicityScorelon)
        .selontPBlockScorelon(data.pBlockScorelon)
        .selontPSpammyTwelonelontScorelon(data.pSpammyTwelonelontScorelon)
        .selontPRelonportelondTwelonelontScorelon(data.pRelonportelondTwelonelontScorelon)
        .selontSpammyTwelonelontContelonntScorelon(data.spammyTwelonelontContelonntScorelon)
        .selontelonxpelonrimelonntalHelonalthModelonlScorelon1(data.elonxpelonrimelonntalHelonalthModelonlScorelon1)
        .selontelonxpelonrimelonntalHelonalthModelonlScorelon2(data.elonxpelonrimelonntalHelonalthModelonlScorelon2)
        .selontelonxpelonrimelonntalHelonalthModelonlScorelon3(data.elonxpelonrimelonntalHelonalthModelonlScorelon3)
        .selontelonxpelonrimelonntalHelonalthModelonlScorelon4(data.elonxpelonrimelonntalHelonalthModelonlScorelon4);

    // Relonturn all elonxtra felonaturelons for clielonnts to consumelon.
    if (options.isGelontAllFelonaturelons()) {
      elonxtraMelontadata.selontIsSelonnsitivelonContelonnt(data.isSelonnsitivelonContelonnt)
          .selontHasMultiplelonMelondiaFlag(data.hasMultiplelonMelondiaFlag)
          .selontProfilelonIselonggFlag(data.profilelonIselonggFlag)
          .selontIsUselonrNelonwFlag(data.isUselonrNelonwFlag)
          .selontNumMelonntions(data.numMelonntions)
          .selontNumHashtags(data.numHashtags)
          .selontLinkLanguagelon(data.linkLanguagelon)
          .selontPrelonvUselonrTwelonelontelonngagelonmelonnt(data.prelonvUselonrTwelonelontelonngagelonmelonnt);
    }

    // Selont felonaturelons in nelonw Felonaturelon Accelonss API format, in thelon futurelon this will belon thelon only part
    // nelonelondelond in this melonthod, welon don't nelonelond to selont any othelonr melontadata fielonlds any morelon.
    if (options.isRelonturnSelonarchRelonsultFelonaturelons()) {
      // If thelon felonaturelons arelon unselont, and thelony welonrelon relonquelonstelond, thelonn welon can relontrielonvelon thelonm. If thelony arelon
      // alrelonady selont, thelonn welon don't nelonelond to relon-relonad thelon documelonnt felonaturelons, and thelon relonadelonr
      // is probably positionelond ovelonr thelon wrong documelonnt so it will relonturn incorrelonct relonsults.
      if (!elonxtraMelontadata.isSelontFelonaturelons()) {
        // Welon ignorelon all felonaturelons with delonfault valuelons whelonn relonturning thelonm in thelon relonsponselon,
        // beloncauselon it savelons a lot of nelontwork bandwidth.
        ThriftSelonarchRelonsultFelonaturelons felonaturelons = crelonatelonFelonaturelonsForDocumelonnt(data, truelon).gelontFelonaturelons();
        elonxtraMelontadata.selontFelonaturelons(felonaturelons);
      }

      // Thelon raw scorelon may havelon changelond sincelon welon crelonatelond thelon felonaturelons, so welon should updatelon it.
      elonxtraMelontadata.gelontFelonaturelons().gelontDoublelonValuelons()
          .put(elonxtelonrnalTwelonelontFelonaturelon.RAW_elonARLYBIRD_SCORelon.gelontId(), data.scorelonFinal);
    }

    melontadata
        .selontIsSelonlfTwelonelont(data.isSelonlfTwelonelont)
        .selontIsUselonrAntiSocial(data.isUselonrAntiSocial);
  }

  /**
   * Crelonatelon elonarlybird basic felonaturelons and delonrvielond felonaturelons for currelonnt documelonnt.
   * @relonturn a FelonaturelonHandlelonr objelonct whelonrelon you can kelonelonp adding elonxtra felonaturelon valuelons, or you can
   * call .gelontFelonaturelons() on it to gelont a Thrift objelonct to relonturn.
   */
  protelonctelond FelonaturelonHandlelonr crelonatelonFelonaturelonsForDocumelonnt(
      LinelonarScoringData data, boolelonan ignorelonDelonfaultValuelons) throws IOelonxcelonption {
    ThriftSelonarchRelonsultFelonaturelons felonaturelons = documelonntFelonaturelons.gelontSelonarchRelonsultFelonaturelons(gelontSchelonma());
    if (!ignorelonDelonfaultValuelons) {
      selontDelonfaultFelonaturelonValuelons(felonaturelons);
    }

    // add delonrivelond felonaturelons
    relonturn nelonw FelonaturelonHandlelonr(felonaturelons, ignorelonDelonfaultValuelons)
        .addDoublelon(elonxtelonrnalTwelonelontFelonaturelon.LUCelonNelon_SCORelon, data.lucelonnelonScorelon)
        .addInt(elonxtelonrnalTwelonelontFelonaturelon.TWelonelonT_AGelon_IN_SelonCS, data.twelonelontAgelonInSelonconds)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.IS_SelonLF_TWelonelonT, data.isSelonlfTwelonelont)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.IS_FOLLOW_RelonTWelonelonT, data.isFollow && data.isRelontwelonelont)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.IS_TRUSTelonD_RelonTWelonelonT, data.isTrustelond && data.isRelontwelonelont)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.AUTHOR_IS_FOLLOW, data.isFollow)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.AUTHOR_IS_TRUSTelonD, data.isTrustelond)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.AUTHOR_IS_ANTISOCIAL, data.isUselonrAntiSocial)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.HAS_DIFF_LANG, data.hasDiffelonrelonntLang)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.HAS_elonNGLISH_TWelonelonT_DIFF_UI_LANG,
            data.haselonnglishTwelonelontAndDiffelonrelonntUILang)
        .addBoolelonan(elonxtelonrnalTwelonelontFelonaturelon.HAS_elonNGLISH_UI_DIFF_TWelonelonT_LANG,
            data.haselonnglishUIAndDiffelonrelonntTwelonelontLang)
        .addDoublelon(elonxtelonrnalTwelonelontFelonaturelon.SelonARCHelonR_LANG_SCORelon, data.uselonrLangMult)
        .addDoublelon(elonxtelonrnalTwelonelontFelonaturelon.QUelonRY_SPelonCIFIC_SCORelon, data.quelonrySpeloncificScorelon)
        .addDoublelon(elonxtelonrnalTwelonelontFelonaturelon.AUTHOR_SPelonCIFIC_SCORelon, data.authorSpeloncificScorelon);
  }

  /**
   * Adds delonfault valuelons for most numelonric felonaturelons that do not havelon a valuelon selont yelont in thelon givelonn
   * ThriftSelonarchRelonsultFelonaturelons instancelon.
   *
   * This melonthod is nelonelondelond beloncauselon somelon modelonls do not work propelonrly with missing felonaturelons. Instelonad,
   * thelony elonxpelonct all felonaturelons to belon prelonselonnt elonvelonn if thelony arelon unselont (thelonir valuelons arelon 0).
   */
  protelonctelond void selontDelonfaultFelonaturelonValuelons(ThriftSelonarchRelonsultFelonaturelons felonaturelons) {
    for (Map.elonntry<Intelongelonr, ThriftSelonarchFelonaturelonSchelonmaelonntry> elonntry
             : gelontSchelonma().gelontSelonarchFelonaturelonSchelonma().gelontelonntrielons().elonntrySelont()) {
      int felonaturelonId = elonntry.gelontKelony();
      ThriftSelonarchFelonaturelonSchelonmaelonntry schelonmaelonntry = elonntry.gelontValuelon();
      if (shouldSelontDelonfaultValuelonForFelonaturelon(schelonmaelonntry.gelontFelonaturelonTypelon(), felonaturelonId)) {
        switch (schelonmaelonntry.gelontFelonaturelonTypelon()) {
          caselon INT32_VALUelon:
            felonaturelons.gelontIntValuelons().putIfAbselonnt(felonaturelonId, 0);
            brelonak;
          caselon LONG_VALUelon:
            felonaturelons.gelontLongValuelons().putIfAbselonnt(felonaturelonId, 0L);
            brelonak;
          caselon DOUBLelon_VALUelon:
            felonaturelons.gelontDoublelonValuelons().putIfAbselonnt(felonaturelonId, 0.0);
            brelonak;
          delonfault:
            throw nelonw IllelongalArgumelonntelonxcelonption(
                "Should selont delonfault valuelons only for intelongelonr, long or doublelon felonaturelons. Instelonad, "
                + "found felonaturelon " + felonaturelonId + " of typelon " + schelonmaelonntry.gelontFelonaturelonTypelon());
        }
      }
    }
  }

  protelonctelond void ovelonrridelonFelonaturelonValuelons(ThriftSelonarchRelonsultFelonaturelons felonaturelons,
                                       ThriftSelonarchRelonsultFelonaturelons ovelonrridelonFelonaturelons) {
    LOG.info("Felonaturelons belonforelon ovelonrridelon {}", felonaturelons);
    if (ovelonrridelonFelonaturelons.isSelontIntValuelons()) {
      ovelonrridelonFelonaturelons.gelontIntValuelons().forelonach(felonaturelons::putToIntValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontLongValuelons()) {
      ovelonrridelonFelonaturelons.gelontLongValuelons().forelonach(felonaturelons::putToLongValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontDoublelonValuelons()) {
      ovelonrridelonFelonaturelons.gelontDoublelonValuelons().forelonach(felonaturelons::putToDoublelonValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontBoolValuelons()) {
      ovelonrridelonFelonaturelons.gelontBoolValuelons().forelonach(felonaturelons::putToBoolValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontStringValuelons()) {
      ovelonrridelonFelonaturelons.gelontStringValuelons().forelonach(felonaturelons::putToStringValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontBytelonsValuelons()) {
      ovelonrridelonFelonaturelons.gelontBytelonsValuelons().forelonach(felonaturelons::putToBytelonsValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontFelonaturelonStorelonDiscrelontelonValuelons()) {
      ovelonrridelonFelonaturelons.gelontFelonaturelonStorelonDiscrelontelonValuelons().forelonach(
          felonaturelons::putToFelonaturelonStorelonDiscrelontelonValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontSparselonBinaryValuelons()) {
      ovelonrridelonFelonaturelons.gelontSparselonBinaryValuelons().forelonach(felonaturelons::putToSparselonBinaryValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontSparselonContinuousValuelons()) {
      ovelonrridelonFelonaturelons.gelontSparselonContinuousValuelons().forelonach(felonaturelons::putToSparselonContinuousValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontGelonnelonralTelonnsorValuelons()) {
      ovelonrridelonFelonaturelons.gelontGelonnelonralTelonnsorValuelons().forelonach(felonaturelons::putToGelonnelonralTelonnsorValuelons);
    }
    if (ovelonrridelonFelonaturelons.isSelontStringTelonnsorValuelons()) {
      ovelonrridelonFelonaturelons.gelontStringTelonnsorValuelons().forelonach(felonaturelons::putToStringTelonnsorValuelons);
    }
    LOG.info("Felonaturelons aftelonr ovelonrridelon {}", felonaturelons);
  }

  /**
   * Chelonck if a felonaturelon is elonligiblelon to havelon its delonfault valuelon automatically selont whelonn abselonnt.
   * Welon havelon a similar logic for building data reloncord.
   */
  privatelon static boolelonan shouldSelontDelonfaultValuelonForFelonaturelon(
      ThriftSelonarchFelonaturelonTypelon typelon, int felonaturelonId) {
    relonturn ALLOWelonD_TYPelonS_FOR_DelonFAULT_FelonATURelon_VALUelonS.contains(typelon)
        && !NUMelonRIC_FelonATURelonS_FOR_WHICH_DelonFAULTS_SHOULD_NOT_Belon_SelonT.contains(felonaturelonId)
        && (elonxtelonrnalTwelonelontFelonaturelon.elonARLYBIRD_INDelonXelonD_FelonATURelon_IDS.contains(felonaturelonId)
            || elonxtelonrnalTwelonelontFelonaturelon.elonARLYBIRD_DelonRIVelonD_FelonATURelon_IDS.contains(felonaturelonId));
  }

  @Ovelonrridelon
  public void updatelonRelonlelonvancelonStats(ThriftSelonarchRelonsultsRelonlelonvancelonStats relonlelonvancelonStats) {
    if (relonlelonvancelonStats == null) {
      relonturn;
    }

    LinelonarScoringData data = gelontScoringDataForCurrelonntDocumelonnt();

    if (data.twelonelontAgelonInSelonconds > relonlelonvancelonStats.gelontOldelonstScorelondTwelonelontAgelonInSelonconds()) {
      relonlelonvancelonStats.selontOldelonstScorelondTwelonelontAgelonInSelonconds(data.twelonelontAgelonInSelonconds);
    }
    relonlelonvancelonStats.selontNumScorelond(relonlelonvancelonStats.gelontNumScorelond() + 1);
    if (data.scorelonRelonturnelond == SKIP_HIT) {
      relonlelonvancelonStats.selontNumSkippelond(relonlelonvancelonStats.gelontNumSkippelond() + 1);
      switch(data.skipRelonason) {
        caselon ANTIGAMING:
          relonlelonvancelonStats.selontNumSkippelondForAntiGaming(
              relonlelonvancelonStats.gelontNumSkippelondForAntiGaming() + 1);
          brelonak;
        caselon LOW_RelonPUTATION:
          relonlelonvancelonStats.selontNumSkippelondForLowRelonputation(
              relonlelonvancelonStats.gelontNumSkippelondForLowRelonputation() + 1);
          brelonak;
        caselon LOW_TelonXT_SCORelon:
          relonlelonvancelonStats.selontNumSkippelondForLowTelonxtScorelon(
              relonlelonvancelonStats.gelontNumSkippelondForLowTelonxtScorelon() + 1);
          brelonak;
        caselon SOCIAL_FILTelonR:
          relonlelonvancelonStats.selontNumSkippelondForSocialFiltelonr(
              relonlelonvancelonStats.gelontNumSkippelondForSocialFiltelonr() + 1);
          brelonak;
        caselon LOW_FINAL_SCORelon:
          relonlelonvancelonStats.selontNumSkippelondForLowFinalScorelon(
              relonlelonvancelonStats.gelontNumSkippelondForLowFinalScorelon() + 1);
          brelonak;
        caselon LOW_RelonTWelonelonT_COUNT:
          brelonak;
        delonfault:
          LOG.warn("Unknown SkipRelonason: " + data.skipRelonason);
      }
    }

    if (data.isFollow) {
      relonlelonvancelonStats.selontNumFromDirelonctFollows(relonlelonvancelonStats.gelontNumFromDirelonctFollows() + 1);
    }
    if (data.isTrustelond) {
      relonlelonvancelonStats.selontNumFromTrustelondCirclelon(relonlelonvancelonStats.gelontNumFromTrustelondCirclelon() + 1);
    }
    if (data.isRelonply) {
      relonlelonvancelonStats.selontNumRelonplielons(relonlelonvancelonStats.gelontNumRelonplielons() + 1);
      if (data.isTrustelond) {
        relonlelonvancelonStats.selontNumRelonplielonsTrustelond(relonlelonvancelonStats.gelontNumRelonplielonsTrustelond() + 1);
      } elonlselon if (!data.isFollow) {
        relonlelonvancelonStats.selontNumRelonplielonsOutOfNelontwork(relonlelonvancelonStats.gelontNumRelonplielonsOutOfNelontwork() + 1);
      }
    }
    if (data.isSelonlfTwelonelont) {
      relonlelonvancelonStats.selontNumSelonlfTwelonelonts(relonlelonvancelonStats.gelontNumSelonlfTwelonelonts() + 1);
    }
    if (data.hasImagelonUrl || data.hasVidelonoUrl) {
      relonlelonvancelonStats.selontNumWithMelondia(relonlelonvancelonStats.gelontNumWithMelondia() + 1);
    }
    if (data.hasNelonwsUrl) {
      relonlelonvancelonStats.selontNumWithNelonws(relonlelonvancelonStats.gelontNumWithNelonws() + 1);
    }
    if (data.isUselonrSpam) {
      relonlelonvancelonStats.selontNumSpamUselonr(relonlelonvancelonStats.gelontNumSpamUselonr() + 1);
    }
    if (data.isUselonrNSFW) {
      relonlelonvancelonStats.selontNumOffelonnsivelon(relonlelonvancelonStats.gelontNumOffelonnsivelon() + 1);
    }
    if (data.isUselonrBot) {
      relonlelonvancelonStats.selontNumBot(relonlelonvancelonStats.gelontNumBot() + 1);
    }
  }

  @VisiblelonForTelonsting
  static final class elonxplanationWrappelonr {
    privatelon elonxplanation elonxplanation;

    public elonxplanation gelontelonxplanation() {
      relonturn elonxplanation;
    }

    @Ovelonrridelon
    public String toString() {
      relonturn elonxplanation.toString();
    }
  }
}
