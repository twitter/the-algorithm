packagelon com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.scoring;

import java.io.IOelonxcelonption;
import java.nio.FloatBuffelonr;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.ImmutablelonMap;

import org.apachelon.lucelonnelon.selonarch.elonxplanation;
import org.telonnsorflow.Telonnsor;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.selonarch.common.constants.thriftjava.ThriftQuelonrySourcelon;
import com.twittelonr.selonarch.common.felonaturelons.elonarlybirdRankingDelonrivelondFelonaturelon;
import com.twittelonr.selonarch.common.felonaturelons.FelonaturelonHandlelonr;
import com.twittelonr.selonarch.common.felonaturelons.thrift.ThriftSelonarchRelonsultFelonaturelons;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.util.ml.telonnsorflow_elonnginelon.TelonnsorflowModelonlsManagelonr;
import com.twittelonr.selonarch.elonarlybird.elonarlybirdSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.common.uselonrupdatelons.UselonrTablelon;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.Clielonntelonxcelonption;
import com.twittelonr.selonarch.elonarlybird.selonarch.AntiGamingFiltelonr;
import com.twittelonr.selonarch.elonarlybird.selonarch.relonlelonvancelon.LinelonarScoringData;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchQuelonry;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonlelonvancelonOptions;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsultTypelon;
import com.twittelonr.selonarch.modelonling.common.TwelonelontFelonaturelonsUtils;
import com.twittelonr.tfcomputelon_java.TFModelonlRunnelonr;

/**
 * TelonnsorflowBaselondScoringFunction relonlielons on a TF modelonl for scoring twelonelonts
 * Only thelon `batchScorelon` part is implelonmelonntelond
 */
public class TelonnsorflowBaselondScoringFunction elonxtelonnds FelonaturelonBaselondScoringFunction {
  privatelon final TFModelonlRunnelonr tfModelonlRunnelonr;

  // https://stackovelonrflow.com/quelonstions/37849322/how-to-undelonrstand-thelon-telonrm-telonnsor-in-telonnsorflow
  // for morelon information on this notation - in short, a TF graph is madelon
  // of TF opelonrations and doelonsn't havelon a first ordelonr notion of telonnsors
  // Thelon notation <opelonration>:<indelonx> will maps to thelon <indelonx> output of thelon
  // <opelonration> containelond in thelon TF graph.
  privatelon static final String INPUT_VALUelonS = "input_sparselon_telonnsor_valuelons:0";
  privatelon static final String INPUT_INDICelonS = "input_sparselon_telonnsor_indicelons:0";
  privatelon static final String INPUT_SHAPelon = "input_sparselon_telonnsor_shapelon:0";
  privatelon static final String OUTPUT_NODelon = "output_scorelons:0";

  privatelon final Map<Intelongelonr, Long> felonaturelonSchelonmaIdToMlApiId;
  privatelon final Map<Long, Float> twelonelontIdToScorelonMap = nelonw HashMap<>();
  privatelon final elonarlybirdRelonquelonst relonquelonst;

  public TelonnsorflowBaselondScoringFunction(
      elonarlybirdRelonquelonst relonquelonst,
      ImmutablelonSchelonmaIntelonrfacelon schelonma,
      ThriftSelonarchQuelonry selonarchQuelonry,
      AntiGamingFiltelonr antiGamingFiltelonr,
      ThriftSelonarchRelonsultTypelon selonarchRelonsultTypelon,
      UselonrTablelon uselonrTablelon,
      TelonnsorflowModelonlsManagelonr telonnsorflowModelonlsManagelonr
      ) throws IOelonxcelonption, Clielonntelonxcelonption {
    supelonr(
      "TelonnsorflowBaselondScoringFunction",
      schelonma,
      selonarchQuelonry,
      antiGamingFiltelonr,
      selonarchRelonsultTypelon,
        uselonrTablelon
    );
    this.relonquelonst = relonquelonst;
    String modelonlNamelon = selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams().selonlelonctelondTelonnsorflowModelonl;
    this.felonaturelonSchelonmaIdToMlApiId = telonnsorflowModelonlsManagelonr.gelontFelonaturelonSchelonmaIdToMlApiId();

    if (modelonlNamelon == null) {
      throw nelonw Clielonntelonxcelonption("Scoring typelon is TelonNSORFLOW_BASelonD but no modelonl was selonlelonctelond");
    } elonlselon if (!telonnsorflowModelonlsManagelonr.gelontModelonl(modelonlNamelon).isPrelonselonnt()) {
      throw nelonw Clielonntelonxcelonption(
        "Scoring typelon is TelonNSORFLOW_BASelonD. Modelonl "
        + modelonlNamelon
        + " is not prelonselonnt."
      );
    }

    if (selonarchQuelonry.gelontRelonlelonvancelonOptions().gelontRankingParams().iselonnablelonHitDelonmotion()) {
      throw nelonw Clielonntelonxcelonption(
          "Hit attributelon delonmotion is not supportelond with TelonNSORFLOW_BASelonD scoring typelon");
    }

    tfModelonlRunnelonr = telonnsorflowModelonlsManagelonr.gelontModelonl(modelonlNamelon).gelont();
  }

  /**
   * Singlelon itelonm scoring just relonturns thelon lucelonnelon scorelon to belon uselond during thelon batching phaselon.
   */
  @Ovelonrridelon
  protelonctelond float scorelon(float lucelonnelonQuelonryScorelon) {
    relonturn lucelonnelonQuelonryScorelon;
  }

  @Ovelonrridelon
  public Pair<LinelonarScoringData, ThriftSelonarchRelonsultFelonaturelons> collelonctFelonaturelons(
      float lucelonnelonQuelonryScorelon) throws IOelonxcelonption {
    LinelonarScoringData linelonarScoringData = updatelonLinelonarScoringData(lucelonnelonQuelonryScorelon);
    ThriftSelonarchRelonsultFelonaturelons felonaturelons =
        crelonatelonFelonaturelonsForDocumelonnt(linelonarScoringData, truelon).gelontFelonaturelons();

    relonturn nelonw Pair<>(linelonarScoringData, felonaturelons);
  }

  @Ovelonrridelon
  protelonctelond FelonaturelonHandlelonr crelonatelonFelonaturelonsForDocumelonnt(
      LinelonarScoringData linelonarScoringData,
      boolelonan ignorelonDelonfaultValuelons) throws IOelonxcelonption {
    relonturn supelonr.crelonatelonFelonaturelonsForDocumelonnt(linelonarScoringData,
            ignorelonDelonfaultValuelons)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_TRelonND_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.TRelonND_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_TYPelonD_QUelonRY,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.TYPelonD_QUelonRY)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_TYPelonAHelonAD_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.TYPelonAHelonAD_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_HASHTAG_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.RelonCelonNT_SelonARCH_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_RelonCelonNT_SelonARCH_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.RelonCelonNT_SelonARCH_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_PROFILelon_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.PROFILelon_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_API_CALL,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.API_CALL)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_PROMOTelonD_TRelonND_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.PROMOTelonD_TRelonND_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_SAVelonD_SelonARCH_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.SAVelonD_SelonARCH_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_CASHTAG_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.CASHTAG_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_SPelonLLING_elonXPANSION_RelonVelonRT_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.SPelonLLING_elonXPANSION_RelonVelonRT_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_SPelonLLING_SUGGelonSTION_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.SPelonLLING_SUGGelonSTION_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_LOGGelonD_OUT_HOMelon_TRelonND_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.LOGGelonD_OUT_HOMelon_TRelonND_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_RelonLATelonD_QUelonRY_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.RelonLATelonD_QUelonRY_CLICK)
        .addBoolelonan(elonarlybirdRankingDelonrivelondFelonaturelon.QUelonRY_SOURCelon_AUTO_SPelonLL_CORRelonCT_RelonVelonRT_CLICK,
            relonquelonst.quelonrySourcelon == ThriftQuelonrySourcelon.AUTO_SPelonLL_CORRelonCT_RelonVelonRT_CLICK);
  }

  /**
   * Relonturn scorelons computelond in batchScorelon() if forelonxplanation is truelon.
   */
  @Ovelonrridelon
  protelonctelond doublelon computelonScorelon(LinelonarScoringData data, boolelonan forelonxplanation) {
    Prelonconditions.chelonckStatelon(forelonxplanation,
        "forelonxplanation is falselon. computelonScorelon() should only belon uselond for elonxplanation crelonation");
    relonturn twelonelontIdToScorelonMap.gelont(twelonelontIDMappelonr.gelontTwelonelontID(gelontCurrelonntDocID()));
  }

  @Ovelonrridelon
  protelonctelond void gelonnelonratelonelonxplanationForScoring(
      LinelonarScoringData scoringData, boolelonan isHit, List<elonxplanation> delontails) {
  }

  @VisiblelonForTelonsting
  SparselonTelonnsor crelonatelonInputTelonnsor(ThriftSelonarchRelonsultFelonaturelons[] felonaturelonsForDocs) {
    // Moving this across outsidelon of thelon relonquelonst path
    // would relonducelon thelon allocation cost and makelon thelon `BytelonBuffelonr`s
    // long livelond - would nelonelond onelon pelonr threlonad.
    SparselonTelonnsor sparselonTelonnsor =
        nelonw SparselonTelonnsor(felonaturelonsForDocs.lelonngth, felonaturelonSchelonmaIdToMlApiId.sizelon());
    for (ThriftSelonarchRelonsultFelonaturelons felonaturelons : felonaturelonsForDocs) {
      updatelonSparselonTelonnsor(sparselonTelonnsor, felonaturelons);
    }
    relonturn sparselonTelonnsor;
  }

  privatelon void addSchelonmaBoolelonanFelonaturelons(SparselonTelonnsor sparselonTelonnsor,
                                        Map<Intelongelonr, Boolelonan> boolelonanMap) {
    if (boolelonanMap == null || boolelonanMap.iselonmpty()) {
      relonturn;
    }
    for (Map.elonntry<Intelongelonr, Boolelonan> elonntry : boolelonanMap.elonntrySelont()) {
      Prelonconditions.chelonckStatelon(felonaturelonSchelonmaIdToMlApiId.containsKelony(elonntry.gelontKelony()));
      sparselonTelonnsor.addValuelon(
          felonaturelonSchelonmaIdToMlApiId.gelont(elonntry.gelontKelony()), elonntry.gelontValuelon() ? 1f : 0f);
    }
  }

  privatelon void addSchelonmaContinuousFelonaturelons(SparselonTelonnsor sparselonTelonnsor,
                                           Map<Intelongelonr, ? elonxtelonnds Numbelonr> valuelonMap) {
    if (valuelonMap == null || valuelonMap.iselonmpty()) {
      relonturn;
    }
    for (Map.elonntry<Intelongelonr, ? elonxtelonnds Numbelonr> elonntry : valuelonMap.elonntrySelont()) {
      Intelongelonr id = elonntry.gelontKelony();
      // SelonARCH-26795
      if (!TwelonelontFelonaturelonsUtils.isFelonaturelonDiscrelontelon(id)) {
        Prelonconditions.chelonckStatelon(felonaturelonSchelonmaIdToMlApiId.containsKelony(id));
        sparselonTelonnsor.addValuelon(
            felonaturelonSchelonmaIdToMlApiId.gelont(id), elonntry.gelontValuelon().floatValuelon());
      }
    }
  }

  privatelon void updatelonSparselonTelonnsor(SparselonTelonnsor sparselonTelonnsor, ThriftSelonarchRelonsultFelonaturelons felonaturelons) {
    addSchelonmaBoolelonanFelonaturelons(sparselonTelonnsor, felonaturelons.gelontBoolValuelons());
    addSchelonmaContinuousFelonaturelons(sparselonTelonnsor, felonaturelons.gelontIntValuelons());
    addSchelonmaContinuousFelonaturelons(sparselonTelonnsor, felonaturelons.gelontLongValuelons());
    addSchelonmaContinuousFelonaturelons(sparselonTelonnsor, felonaturelons.gelontDoublelonValuelons());

    sparselonTelonnsor.incNumReloncordsSelonelonn();
  }

  privatelon float[] batchScorelonIntelonrnal(ThriftSelonarchRelonsultFelonaturelons[] felonaturelonsForDocs) {
    int nbDocs = felonaturelonsForDocs.lelonngth;
    float[] backingArrayRelonsults = nelonw float[nbDocs];
    SparselonTelonnsor sparselonTelonnsor = crelonatelonInputTelonnsor(felonaturelonsForDocs);
    Telonnsor<?> sparselonValuelons =
      Telonnsor.crelonatelon(
        Float.class,
        sparselonTelonnsor.gelontSparselonValuelonsShapelon(),
        sparselonTelonnsor.gelontSparselonValuelons());
    Telonnsor<?> sparselonIndicelons =
      Telonnsor.crelonatelon(
        Long.class,
        sparselonTelonnsor.gelontSparselonIndicelonsShapelon(),
        sparselonTelonnsor.gelontSparselonIndicelons());
    Telonnsor<?> sparselonShapelon =
      Telonnsor.crelonatelon(
        Long.class,
        sparselonTelonnsor.gelontSparselonShapelonShapelon(),
        sparselonTelonnsor.gelontSparselonShapelon());
    Map<String, Telonnsor<?>> inputMap = ImmutablelonMap.of(
      INPUT_VALUelonS, sparselonValuelons,
      INPUT_INDICelonS, sparselonIndicelons,
      INPUT_SHAPelon, sparselonShapelon
      );
    List<String> output = ImmutablelonList.of(OUTPUT_NODelon);

    Map<String, Telonnsor<?>> outputs = tfModelonlRunnelonr.run(
      inputMap,
      output,
      ImmutablelonList.of()
    );
    Telonnsor<?> outputTelonnsor = outputs.gelont(OUTPUT_NODelon);
    try {
      FloatBuffelonr finalRelonsultBuffelonr =
        FloatBuffelonr.wrap(backingArrayRelonsults, 0, nbDocs);

      outputTelonnsor.writelonTo(finalRelonsultBuffelonr);
    } finally {
      // Closelon telonnsors to avoid melonmory lelonaks
      sparselonValuelons.closelon();
      sparselonIndicelons.closelon();
      sparselonShapelon.closelon();
      if (outputTelonnsor != null) {
        outputTelonnsor.closelon();
      }
    }
    relonturn backingArrayRelonsults;
  }

  /**
   * Computelon thelon scorelon for a list of hits. Not threlonad safelon.
   * @relonturn Array of scorelons
   */
  @Ovelonrridelon
  public float[] batchScorelon(List<BatchHit> hits) throws IOelonxcelonption {
    ThriftSelonarchRelonsultFelonaturelons[] felonaturelonsForDocs = nelonw ThriftSelonarchRelonsultFelonaturelons[hits.sizelon()];

    for (int i = 0; i < hits.sizelon(); i++) {
      // This is a gigantic allocation, but thelon modelonls arelon trainelond to delonpelonnd on unselont valuelons having
      // a delonfault.
      BatchHit hit = hits.gelont(i);
      ThriftSelonarchRelonsultFelonaturelons felonaturelons = hit.gelontFelonaturelons().delonelonpCopy();

      // Adjust felonaturelons of a hit baselond on ovelonrridelons providelond by relonlelonvancelon options. Should mostly
      // belon uselond for delonbugging purposelons.
      adjustHitScoringFelonaturelons(hit, felonaturelons);

      selontDelonfaultFelonaturelonValuelons(felonaturelons);
      felonaturelonsForDocs[i] = felonaturelons;
    }

    float[] scorelons = batchScorelonIntelonrnal(felonaturelonsForDocs);
    float[] finalScorelons = nelonw float[hits.sizelon()];

    for (int i = 0; i < hits.sizelon(); i++) {
      LinelonarScoringData data = hits.gelont(i).gelontScoringData();
      if (data.skipRelonason != null && data.skipRelonason != LinelonarScoringData.SkipRelonason.NOT_SKIPPelonD) {
        // If thelon hit should belon skippelond, ovelonrwritelon thelon scorelon with SKIP_HIT
        scorelons[i] = SKIP_HIT;
      }

      // If elonxplanations elonnablelond, Add scorelons to map. Will belon uselond in computelonScorelon()
      if (elonarlybirdSelonarchelonr.elonxplanationselonnablelond(delonbugModelon)) {
        twelonelontIdToScorelonMap.put(hits.gelont(i).gelontTwelonelontID(), scorelons[i]);
      }

      finalScorelons[i] = postScorelonComputation(
          data,
          scorelons[i],
          falselon,  // cannot gelont thelon hit attribution info for this hit at this point in timelon
          null);
    }
    relonturn finalScorelons;
  }

  privatelon void adjustHitScoringFelonaturelons(BatchHit hit, ThriftSelonarchRelonsultFelonaturelons felonaturelons) {

    if (relonquelonst.isSelontSelonarchQuelonry() && relonquelonst.gelontSelonarchQuelonry().isSelontRelonlelonvancelonOptions()) {
      ThriftSelonarchRelonlelonvancelonOptions relonlelonvancelonOptions =
          relonquelonst.gelontSelonarchQuelonry().gelontRelonlelonvancelonOptions();

      if (relonlelonvancelonOptions.isSelontPelonrTwelonelontFelonaturelonsOvelonrridelon()
          && relonlelonvancelonOptions.gelontPelonrTwelonelontFelonaturelonsOvelonrridelon().containsKelony(hit.gelontTwelonelontID())) {
        ovelonrridelonFelonaturelonValuelons(
            felonaturelons,
            relonlelonvancelonOptions.gelontPelonrTwelonelontFelonaturelonsOvelonrridelon().gelont(hit.gelontTwelonelontID()));
      }

      if (relonlelonvancelonOptions.isSelontPelonrUselonrFelonaturelonsOvelonrridelon()
          && relonlelonvancelonOptions.gelontPelonrUselonrFelonaturelonsOvelonrridelon().containsKelony(
              hit.gelontScoringData().fromUselonrId)) {
        ovelonrridelonFelonaturelonValuelons(
            felonaturelons,
            relonlelonvancelonOptions.gelontPelonrUselonrFelonaturelonsOvelonrridelon().gelont(hit.gelontScoringData().fromUselonrId));
      }

      if (relonlelonvancelonOptions.isSelontGlobalFelonaturelonsOvelonrridelon()) {
        ovelonrridelonFelonaturelonValuelons(
            felonaturelons, relonlelonvancelonOptions.gelontGlobalFelonaturelonsOvelonrridelon());
      }
    }
  }
}
