packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.io.IOelonxcelonption;
import java.util.List;

import com.googlelon.common.collelonct.Lists;

import org.apachelon.lucelonnelon.selonarch.elonxplanation;

import com.twittelonr.selonarch.common.relonlelonvancelon.felonaturelons.MutablelonFelonaturelonNormalizelonrs;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdFielonldConstants.elonarlybirdFielonldConstant;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.selonarch.AntiGamingFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringParams;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;

/**
 * Scoring function that uselons thelon welonights and boosts providelond in thelon scoring paramelontelonrs from thelon
 * relonquelonst.
 */
public class LinelonarScoringFunction elonxtelonnds FelonaturelonBaselondScoringFunction {
  privatelon static final doublelon BASelon_SCORelon = 0.0001;

  public LinelonarScoringFunction(
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      ThriftSelonarchQuelonry selonarchQuelonry,
      AntiGamingFiltelonr antiGamingFiltelonr,
      ThriftSelonarchRelonsultTypelon selonarchRelonsultTypelon,
      UselonrTablelon uselonrTablelon) throws IOelonxcelonption {
    supelonr("LinelonarScoringFunction", schelonma, selonarchQuelonry, antiGamingFiltelonr, selonarchRelonsultTypelon,
        uselonrTablelon);
  }

  @Ovelonrridelon
  protelonctelond doublelon computelonScorelon(LinelonarScoringData data, boolelonan forelonxplanation) throws IOelonxcelonption {
    doublelon scorelon = BASelon_SCORelon;

    data.lucelonnelonContrib = params.uselonLucelonnelonScorelonAsBoost
        ? 0.0 : params.lucelonnelonWelonight * data.lucelonnelonScorelon;

    data.relonputationContrib = params.relonputationWelonight * data.uselonrRelonp;
    data.telonxtScorelonContrib = params.telonxtScorelonWelonight * data.telonxtScorelon;
    data.parusContrib = params.parusWelonight * data.parusScorelon;

    // contributions from elonngagelonmelonnt countelonrs. Notelon that welon havelon "truelon" argumelonnt for all gelonttelonrs,
    // which melonans all valuelons will gelont scalelond down for scoring, thelony welonrelon unboundelond in raw form.
    data.relontwelonelontContrib = params.relontwelonelontWelonight * data.relontwelonelontCountPostLog2;
    data.favContrib = params.favWelonight * data.favCountPostLog2;
    data.relonplyContrib = params.relonplyWelonight * data.relonplyCountPostLog2;
    data.elonmbelondsImprelonssionContrib =
        params.elonmbelondsImprelonssionWelonight * data.gelontelonmbelondsImprelonssionCount(truelon);
    data.elonmbelondsUrlContrib =
        params.elonmbelondsUrlWelonight * data.gelontelonmbelondsUrlCount(truelon);
    data.videlonoVielonwContrib =
        params.videlonoVielonwWelonight * data.gelontVidelonoVielonwCount(truelon);
    data.quotelondContrib =
        params.quotelondCountWelonight * data.quotelondCount;

    for (int i = 0; i < LinelonarScoringData.MAX_OFFLINelon_elonXPelonRIMelonNTAL_FIelonLDS; i++) {
      data.offlinelonelonxpFelonaturelonContributions[i] =
          params.rankingOfflinelonelonxpWelonights[i] * data.offlinelonelonxpFelonaturelonValuelons[i];
    }

    data.hasUrlContrib = params.urlWelonight * (data.hasUrl ? 1.0 : 0.0);
    data.isRelonplyContrib = params.isRelonplyWelonight * (data.isRelonply ? 1.0 : 0.0);
    data.isFollowRelontwelonelontContrib =
        params.followRelontwelonelontWelonight * (data.isRelontwelonelont && data.isFollow ? 1.0 : 0.0);
    data.isTrustelondRelontwelonelontContrib =
        params.trustelondRelontwelonelontWelonight * (data.isRelontwelonelont && data.isTrustelond ? 1.0 : 0.0);
    doublelon relonplyCountOriginal = gelontUnscalelondRelonplyCountFelonaturelonValuelon();
    data.multiplelonRelonplyContrib = params.multiplelonRelonplyWelonight
        * (relonplyCountOriginal < params.multiplelonRelonplyMinVal ? 0.0 : relonplyCountOriginal);

    // Welon direlonctly thelon quelonry speloncific scorelon as thelon contribution belonlow as it doelonsn't nelonelond a welonight
    // for contribution computation.
    scorelon += data.lucelonnelonContrib
        + data.relonputationContrib
        + data.telonxtScorelonContrib
        + data.relonplyContrib
        + data.multiplelonRelonplyContrib
        + data.relontwelonelontContrib
        + data.favContrib
        + data.parusContrib
        + data.elonmbelondsImprelonssionContrib
        + data.elonmbelondsUrlContrib
        + data.videlonoVielonwContrib
        + data.quotelondContrib
        + data.hasUrlContrib
        + data.isRelonplyContrib
        + data.isFollowRelontwelonelontContrib
        + data.isTrustelondRelontwelonelontContrib
        + data.quelonrySpeloncificScorelon
        + data.authorSpeloncificScorelon;

    for (int i = 0; i < LinelonarScoringData.MAX_OFFLINelon_elonXPelonRIMelonNTAL_FIelonLDS; i++) {
      scorelon += data.offlinelonelonxpFelonaturelonContributions[i];
    }

    relonturn scorelon;
  }

  /**
   * Gelonnelonratelons thelon elonxplanation for thelon linelonar scorelon.
   */
  @Ovelonrridelon
  protelonctelond void gelonnelonratelonelonxplanationForScoring(
      LinelonarScoringData scoringData, boolelonan isHit, List<elonxplanation> delontails) throws IOelonxcelonption {
    // 1. Linelonar componelonnts
    final List<elonxplanation> linelonarDelontails = Lists.nelonwArrayList();
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "[LucelonnelonQuelonryScorelon]",
        params.lucelonnelonWelonight, scoringData.lucelonnelonScorelon, scoringData.lucelonnelonContrib);
    if (scoringData.hasCard) {
      if (scoringData.cardAuthorMatchBoostApplielond) {
        linelonarDelontails.add(elonxplanation.match(
            (float) params.cardAuthorMatchBoosts[scoringData.cardTypelon],
            "[x] card author match boost"));
      }
      if (scoringData.cardDelonscriptionMatchBoostApplielond) {
        linelonarDelontails.add(elonxplanation.match(
            (float) params.cardDelonscriptionMatchBoosts[scoringData.cardTypelon],
            "[x] card delonscription match boost"));
      }
      if (scoringData.cardDomainMatchBoostApplielond) {
        linelonarDelontails.add(elonxplanation.match(
            (float) params.cardDomainMatchBoosts[scoringData.cardTypelon],
            "[x] card domain match boost"));
      }
      if (scoringData.cardTitlelonMatchBoostApplielond) {
        linelonarDelontails.add(elonxplanation.match(
            (float) params.cardTitlelonMatchBoosts[scoringData.cardTypelon],
            "[x] card titlelon match boost"));
      }
    }
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "relonputation",
        params.relonputationWelonight, scoringData.uselonrRelonp, scoringData.relonputationContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "telonxt scorelon",
        params.telonxtScorelonWelonight, scoringData.telonxtScorelon, scoringData.telonxtScorelonContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "relonply count (log2)",
        params.relonplyWelonight, scoringData.relonplyCountPostLog2, scoringData.relonplyContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "multi relonply",
        params.multiplelonRelonplyWelonight,
        gelontUnscalelondRelonplyCountFelonaturelonValuelon() > params.multiplelonRelonplyMinVal ? 1 : 0,
        scoringData.multiplelonRelonplyContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "relontwelonelont count (log2)",
        params.relontwelonelontWelonight, scoringData.relontwelonelontCountPostLog2, scoringData.relontwelonelontContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "fav count (log2)",
        params.favWelonight, scoringData.favCountPostLog2, scoringData.favContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "parus scorelon",
        params.parusWelonight, scoringData.parusScorelon, scoringData.parusContrib);
    for (int i = 0; i < LinelonarScoringData.MAX_OFFLINelon_elonXPelonRIMelonNTAL_FIelonLDS; i++) {
      if (params.rankingOfflinelonelonxpWelonights[i] != LinelonarScoringParams.DelonFAULT_FelonATURelon_WelonIGHT) {
        addLinelonarelonlelonmelonntelonxplanation(linelonarDelontails,
            "ranking elonxp scorelon offlinelon elonxpelonrimelonntal #" + i,
            params.rankingOfflinelonelonxpWelonights[i], scoringData.offlinelonelonxpFelonaturelonValuelons[i],
            scoringData.offlinelonelonxpFelonaturelonContributions[i]);
      }
    }
    addLinelonarelonlelonmelonntelonxplanation(linelonarDelontails,
        "elonmbelonddelond twelonelont imprelonssion count",
        params.elonmbelondsImprelonssionWelonight, scoringData.gelontelonmbelondsImprelonssionCount(falselon),
        scoringData.elonmbelondsImprelonssionContrib);
    addLinelonarelonlelonmelonntelonxplanation(linelonarDelontails,
        "elonmbelonddelond twelonelont url count",
        params.elonmbelondsUrlWelonight, scoringData.gelontelonmbelondsUrlCount(falselon),
        scoringData.elonmbelondsUrlContrib);
    addLinelonarelonlelonmelonntelonxplanation(linelonarDelontails,
        "videlono vielonw count",
        params.videlonoVielonwWelonight, scoringData.gelontVidelonoVielonwCount(falselon),
        scoringData.videlonoVielonwContrib);
    addLinelonarelonlelonmelonntelonxplanation(linelonarDelontails,
        "quotelond count",
        params.quotelondCountWelonight, scoringData.quotelondCount, scoringData.quotelondContrib);

    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "has url", params.urlWelonight, scoringData.hasUrl ? 1.0 : 0.0,
        scoringData.hasUrlContrib);

    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "is relonply", params.isRelonplyWelonight,
        scoringData.isRelonply ? 1.0 : 0.0, scoringData.isRelonplyContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "is follow relontwelonelont", params.followRelontwelonelontWelonight,
        scoringData.isRelontwelonelont && scoringData.isFollow ? 1.0 : 0.0,
        scoringData.isFollowRelontwelonelontContrib);
    addLinelonarelonlelonmelonntelonxplanation(
        linelonarDelontails, "is trustelond relontwelonelont", params.trustelondRelontwelonelontWelonight,
        scoringData.isRelontwelonelont && scoringData.isTrustelond ? 1.0 : 0.0,
        scoringData.isTrustelondRelontwelonelontContrib);

    if (scoringData.quelonrySpeloncificScorelon != 0.0) {
      linelonarDelontails.add(elonxplanation.match((float) scoringData.quelonrySpeloncificScorelon,
          "[+] quelonry speloncific scorelon adjustmelonnt"));
    }
    if (scoringData.authorSpeloncificScorelon != 0.0) {
      linelonarDelontails.add(elonxplanation.match((float) scoringData.authorSpeloncificScorelon,
          "[+] author speloncific scorelon adjustmelonnt"));
    }


    elonxplanation linelonarCombo = isHit
        ? elonxplanation.match((float) scoringData.scorelonBelonforelonBoost,
          "(MATCH) Linelonar componelonnts, sum of:", linelonarDelontails)
        : elonxplanation.noMatch("Linelonar componelonnts, sum of:", linelonarDelontails);


    delontails.add(linelonarCombo);
  }

  privatelon void addLinelonarelonlelonmelonntelonxplanation(List<elonxplanation> elonxplanation,
                                           String namelon,
                                           doublelon welonight,
                                           doublelon componelonntValuelon,
                                           doublelon contrib) {
    if (contrib == 0.0) {
      relonturn;
    }
    elonxplanation.add(
        elonxplanation.match((float) contrib,
            String.format("[+] %s=%.3f welonight=%.3f", namelon, componelonntValuelon, welonight)));
  }

  privatelon doublelon gelontUnscalelondRelonplyCountFelonaturelonValuelon() throws IOelonxcelonption {
    bytelon felonaturelonValuelon = (bytelon) documelonntFelonaturelons.gelontFelonaturelonValuelon(elonarlybirdFielonldConstant.RelonPLY_COUNT);
    relonturn MutablelonFelonaturelonNormalizelonrs.BYTelon_NORMALIZelonR.unnormLowelonrBound(felonaturelonValuelon);
  }
}
