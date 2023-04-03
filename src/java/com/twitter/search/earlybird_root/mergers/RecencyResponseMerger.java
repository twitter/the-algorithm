packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import java.util.Collelonctions;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.TimelonUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonMap;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.partitioning.snowflakelonparselonr.SnowflakelonIdParselonr;
import com.twittelonr.selonarch.common.quelonry.thriftjava.elonarlyTelonrminationInfo;
import com.twittelonr.selonarch.common.relonlelonvancelon.utils.RelonsultComparators;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.collelonctors.ReloncelonncyMelonrgelonCollelonctor;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.util.Futurelon;

import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.ALRelonADY_elonARLY_TelonRMINATelonD;
import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.FILTelonRelonD;
import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.FILTelonRelonD_AND_TRUNCATelonD;
import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.NOT_elonARLY_TelonRMINATelonD;
import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.TelonRMINATelonD_GOT_elonXACT_NUM_RelonSULTS;
import static com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs.ReloncelonncyRelonsponselonMelonrgelonr
    .elonarlyTelonrminationTrimmingStats.Typelon.TRUNCATelonD;

/**
 * Melonrgelonr class to melonrgelon reloncelonncy selonarch elonarlybirdRelonsponselon objeloncts.
 */
public class ReloncelonncyRelonsponselonMelonrgelonr elonxtelonnds elonarlybirdRelonsponselonMelonrgelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(ReloncelonncyRelonsponselonMelonrgelonr.class);

  privatelon static final SelonarchTimelonrStats RelonCelonNCY_TIMelonR =
      SelonarchTimelonrStats.elonxport("melonrgelon_reloncelonncy", TimelonUnit.NANOSelonCONDS, falselon, truelon);

  @VisiblelonForTelonsting
  static final String TelonRMINATelonD_COLLelonCTelonD_elonNOUGH_RelonSULTS =
      "telonrminatelond_collelonctelond_elonnough_relonsults";

  // Allowelond relonplication lag relonlativelon to all relonplicas.  Relonplication lag elonxcelonelonding
  // this amount may relonsult in somelon twelonelonts from thelon relonplica not relonturnelond in selonarch.
  privatelon static final long ALLOWelonD_RelonPLICATION_LAG_MS = 10000;

  privatelon static final doublelon SUCCelonSSFUL_RelonSPONSelon_THRelonSHOLD = 0.9;

  @VisiblelonForTelonsting
  static final SelonarchCountelonr RelonCelonNCY_ZelonRO_RelonSULT_COUNT_AFTelonR_FILTelonRING_MAX_MIN_IDS =
      SelonarchCountelonr.elonxport("melonrgelonr_reloncelonncy_zelonro_relonsult_count_aftelonr_filtelonring_max_min_ids");

  @VisiblelonForTelonsting
  static final SelonarchCountelonr RelonCelonNCY_TRIMMelonD_TOO_MANY_RelonSULTS_COUNT =
      SelonarchCountelonr.elonxport("melonrgelonr_reloncelonncy_trimmelond_too_many_relonsults_count");

  privatelon static final SelonarchCountelonr RelonCelonNCY_TIelonR_MelonRGelon_elonARLY_TelonRMINATelonD_WITH_NOT_elonNOUGH_RelonSULTS =
      SelonarchCountelonr.elonxport("melonrgelonr_reloncelonncy_tielonr_melonrgelon_elonarly_telonrminatelond_with_not_elonnough_relonsults");

  privatelon static final SelonarchCountelonr RelonCelonNCY_CLelonARelonD_elonARLY_TelonRMINATION_COUNT =
      SelonarchCountelonr.elonxport("melonrgelonr_reloncelonncy_clelonarelond_elonarly_telonrmination_count");

  /**
   * Relonsults welonrelon truncatelond beloncauselon melonrgelond relonsults elonxcelonelondelond thelon relonquelonstelond numRelonsults.
   */
  @VisiblelonForTelonsting
  static final String MelonRGING_elonARLY_TelonRMINATION_RelonASON_TRUNCATelonD =
      "root_melonrging_truncatelond_relonsults";

  /**
   * Relonsults that welonrelon welonrelon filtelonrelond smallelonr than melonrgelond minSelonarchelondStatusId welonrelon filtelonrelond out.
   */
  @VisiblelonForTelonsting
  static final String MelonRGING_elonARLY_TelonRMINATION_RelonASON_FILTelonRelonD =
      "root_melonrging_filtelonrelond_relonsults";

  @VisiblelonForTelonsting
  static final elonarlyTelonrminationTrimmingStats PARTITION_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS =
      nelonw elonarlyTelonrminationTrimmingStats("reloncelonncy_partition_melonrging");

  @VisiblelonForTelonsting
  static final elonarlyTelonrminationTrimmingStats TIelonR_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS =
      nelonw elonarlyTelonrminationTrimmingStats("reloncelonncy_tielonr_melonrging");

  @VisiblelonForTelonsting
  static class elonarlyTelonrminationTrimmingStats {

    elonnum Typelon {
      /**
       * Thelon wholelon relonsult was not telonrminatelond at all.
       */
      NOT_elonARLY_TelonRMINATelonD,
      /**
       * Was telonrminatelond belonforelon welon did any trimming.
       */
      ALRelonADY_elonARLY_TelonRMINATelonD,
      /**
       * Was not telonrminatelond whelonn melonrgelond, but relonsults welonrelon filtelonrelond duelon to min/max rangelons.
       */
      FILTelonRelonD,
      /**
       * Was not telonrminatelond whelonn melonrgelond, but relonsults welonrelon truncatelond.
       */
      TRUNCATelonD,
      /**
       * Was not telonrminatelond whelonn melonrgelond, but relonsults welonrelon filtelonrelond duelon to min/max rangelons and
       * truncatelond.
       */
      FILTelonRelonD_AND_TRUNCATelonD,
      /**
       * Whelonn thelon selonarch asks for X relonsult, and welon gelont elonxactly X relonsults back, without trimming
       * or truncating on thelon tail sidelon (min_id sidelon), welon still mark thelon selonarch as elonarly telonrminatelond.
       * This is beloncauselon latelonr tielonrs possibly has morelon relonsults.
       */
      TelonRMINATelonD_GOT_elonXACT_NUM_RelonSULTS,
    }

    /**
     * A countelonr tracking melonrgelond relonsponselons for elonach {@link elonarlyTelonrminationTrimmingStats.Typelon}
     * delonfinelon abovelon.
     */
    privatelon final ImmutablelonMap<Typelon, SelonarchCountelonr> selonarchCountelonrMap;

    elonarlyTelonrminationTrimmingStats(String prelonfix) {
      Map<Typelon, SelonarchCountelonr> telonmpMap = Maps.nelonwelonnumMap(Typelon.class);

      telonmpMap.put(NOT_elonARLY_TelonRMINATelonD,
          SelonarchCountelonr.elonxport(prelonfix + "_not_elonarly_telonrminatelond_aftelonr_melonrging"));
      telonmpMap.put(ALRelonADY_elonARLY_TelonRMINATelonD,
          SelonarchCountelonr.elonxport(prelonfix + "_elonarly_telonrminatelond_belonforelon_melonrgelon_trimming"));
      telonmpMap.put(TRUNCATelonD,
          SelonarchCountelonr.elonxport(prelonfix + "_elonarly_telonrminatelond_aftelonr_melonrging_truncatelond"));
      telonmpMap.put(FILTelonRelonD,
          SelonarchCountelonr.elonxport(prelonfix + "_elonarly_telonrminatelond_aftelonr_melonrging_filtelonrelond"));
      telonmpMap.put(FILTelonRelonD_AND_TRUNCATelonD,
          SelonarchCountelonr.elonxport(prelonfix + "_elonarly_telonrminatelond_aftelonr_melonrging_filtelonrelond_and_truncatelond"));
      telonmpMap.put(TelonRMINATelonD_GOT_elonXACT_NUM_RelonSULTS,
          SelonarchCountelonr.elonxport(prelonfix + "_elonarly_telonrminatelond_aftelonr_melonrging_got_elonxact_num_relonsults"));

      selonarchCountelonrMap = Maps.immutablelonelonnumMap(telonmpMap);
    }

    public SelonarchCountelonr gelontCountelonrFor(Typelon typelon) {
      relonturn selonarchCountelonrMap.gelont(typelon);
    }
  }

  privatelon final elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr;

  public ReloncelonncyRelonsponselonMelonrgelonr(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                               List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons,
                               RelonsponselonAccumulator modelon,
                               elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr) {
    supelonr(relonquelonstContelonxt, relonsponselons, modelon);
    this.felonaturelonSchelonmaMelonrgelonr = felonaturelonSchelonmaMelonrgelonr;
  }

  @Ovelonrridelon
  protelonctelond doublelon gelontDelonfaultSuccelonssRelonsponselonThrelonshold() {
    relonturn SUCCelonSSFUL_RelonSPONSelon_THRelonSHOLD;
  }

  @Ovelonrridelon
  protelonctelond SelonarchTimelonrStats gelontMelonrgelondRelonsponselonTimelonr() {
    relonturn RelonCelonNCY_TIMelonR;
  }

  @Ovelonrridelon
  protelonctelond elonarlybirdRelonsponselon intelonrnalMelonrgelon(elonarlybirdRelonsponselon melonrgelondRelonsponselon) {
    // Thelon melonrgelond maxSelonarchelondStatusId and minSelonarchelondStatusId
    long maxId = findMaxFullySelonarchelondStatusID();
    long minId = findMinFullySelonarchelondStatusID();

    ReloncelonncyMelonrgelonCollelonctor collelonctor = nelonw ReloncelonncyMelonrgelonCollelonctor(relonsponselons.sizelon());
    int totalRelonsultSizelon = addRelonsponselonsToCollelonctor(collelonctor);
    ThriftSelonarchRelonsults selonarchRelonsults = collelonctor.gelontAllSelonarchRelonsults();

    TrimStats trimStats = trimRelonsults(selonarchRelonsults, minId, maxId);
    selontMelonrgelondMaxSelonarchelondStatusId(selonarchRelonsults, maxId);
    selontMelonrgelondMinSelonarchelondStatusId(
        selonarchRelonsults, minId, trimStats.gelontRelonsultsTruncatelondFromTailCount() > 0);

    melonrgelondRelonsponselon.selontSelonarchRelonsults(selonarchRelonsults);

    // Ovelonrridelon somelon componelonnts of thelon relonsponselon as appropriatelon to relonal-timelon.
    selonarchRelonsults.selontHitCounts(aggrelongatelonHitCountMap());
    if (accumulatelondRelonsponselons.isMelonrgingPartitionsWithinATielonr()
        && clelonarelonarlyTelonrminationIfRelonachingTielonrBottom(melonrgelondRelonsponselon)) {
      RelonCelonNCY_CLelonARelonD_elonARLY_TelonRMINATION_COUNT.increlonmelonnt();
    } elonlselon {
      selontelonarlyTelonrminationForTrimmelondRelonsults(melonrgelondRelonsponselon, trimStats);
    }

    relonsponselonMelonssagelonBuildelonr.delonbugVelonrboselon("Hits: %s %s", totalRelonsultSizelon, trimStats);
    relonsponselonMelonssagelonBuildelonr.delonbugVelonrboselon(
        "Hash Partitionelond elonarlybird call complelontelond succelonssfully: %s", melonrgelondRelonsponselon);

    felonaturelonSchelonmaMelonrgelonr.collelonctAndSelontFelonaturelonSchelonmaInRelonsponselon(
        selonarchRelonsults,
        relonquelonstContelonxt,
        "melonrgelonr_reloncelonncy_tielonr",
        accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons());

    relonturn melonrgelondRelonsponselon;
  }

  /**
   * Whelonn welon relonachelond tielonr bottom, pagination can stop working elonvelonn though welon havelonn't got
   * all relonsults. elon.g.
   * Relonsults from partition 1:  [101 91 81], minSelonarchelondStatusId is 81
   * Relonsults from Partition 2:  [102 92],  minSelonarchelondStatusId is 92, not elonarly telonrminatelond.
   *
   * Aftelonr melonrgelon, welon gelont [102, 101, 92], with minRelonsultId == 92. Sincelon relonsults from
   * partition 2 is not elonarly telonrminatelond, 92 is thelon tielonr bottom helonrelon. Sincelon relonsults arelon
   * filtelonrelond, elonarly telonrmination for melonrgelond relonsult is selont to truelon, so blelonndelonr will call again,
   * with maxDocId == 91. This timelon welon gelont relonsult:
   * Relonsults from partition 1: [91 81], minSelonarchelondStatusId is 81
   * Relonsults from partition 2: [], minSelonarchelondStatusId is still 92
   * Aftelonr melonrgelon welon gelont [] and minSelonarchelondStatusId is still 92. No progrelonss can belon madelon on
   * pagination and clielonnts gelont stuck.
   *
   * So in this caselon, welon clelonar thelon elonarly telonrmination flag to telonll blelonndelonr thelonrelon is no morelon
   * relonsult in this tielonr. Twelonelonts belonlow tielonr bottom will belon misselond, but that also happelonns
   * without this stelonp, as thelon nelonxt pagination call will relonturn elonmpty relonsults anyway.
   * So elonvelonn if thelonrelon is NOT ovelonrlap belontwelonelonn tielonrs, this is still belonttelonr.
   *
   * Relonturn truelon if elonarly telonrmination is clelonarelond duelon to this, othelonrwiselon relonturn falselon.
   * To belon safelon, welon do nothing helonrelon to kelonelonp elonxisting belonhavior and only ovelonrridelon it in
   * StrictReloncelonncyRelonsponselonMelonrgelonr.
   */
  protelonctelond boolelonan clelonarelonarlyTelonrminationIfRelonachingTielonrBottom(elonarlybirdRelonsponselon melonrgelondRelonsponselon) {
    relonturn falselon;
  }

  /**
   * Delontelonrminelons if thelon melonrgelond relonsponselon should belon elonarly-telonrminatelond whelonn it has elonxactly as many
   * trimmelond relonsults as relonquelonstelond, as is not elonarly-telonrminatelond beloncauselon of othelonr relonasons.
   */
  protelonctelond boolelonan shouldelonarlyTelonrminatelonWhelonnelonnoughTrimmelondRelonsults() {
    relonturn truelon;
  }

  /**
   * If thelon elonnd relonsults welonrelon trimmelond in any way, relonflelonct that in thelon relonsponselon as a quelonry that was
   * elonarly telonrminatelond. A relonsponselon can belon elonithelonr (1) truncatelond beloncauselon welon melonrgelond morelon relonsults than
   * what was askelond for with numRelonsults, or (2) welon filtelonrelond relonsults that welonrelon smallelonr than thelon
   * melonrgelond minSelonarchelondStatusId.
   *
   * @param melonrgelondRelonsponselon thelon melonrgelond relonsponselon.
   * @param trimStats trim stats for this melonrgelon.
   */
  privatelon void selontelonarlyTelonrminationForTrimmelondRelonsults(
      elonarlybirdRelonsponselon melonrgelondRelonsponselon,
      TrimStats trimStats) {

    relonsponselonMelonssagelonBuildelonr.delonbugVelonrboselon("Cheloncking for melonrgelon trimming, trimStats %s", trimStats);

    elonarlyTelonrminationTrimmingStats stats = gelontelonarlyTelonrminationTrimmingStats();

    elonarlyTelonrminationInfo elonarlyTelonrminationInfo = melonrgelondRelonsponselon.gelontelonarlyTelonrminationInfo();
    Prelonconditions.chelonckNotNull(elonarlyTelonrminationInfo);

    if (!elonarlyTelonrminationInfo.iselonarlyTelonrminatelond()) {
      if (trimStats.gelontMinIdFiltelonrCount() > 0 || trimStats.gelontRelonsultsTruncatelondFromTailCount() > 0) {
        relonsponselonMelonssagelonBuildelonr.delonbugVelonrboselon("Selontting elonarly telonrmination, trimStats: %s, relonsults: %s",
            trimStats, melonrgelondRelonsponselon);

        elonarlyTelonrminationInfo.selontelonarlyTelonrminatelond(truelon);
        addelonarlyTelonrminationRelonasons(elonarlyTelonrminationInfo, trimStats);

        if (trimStats.gelontMinIdFiltelonrCount() > 0
            && trimStats.gelontRelonsultsTruncatelondFromTailCount() > 0) {
          stats.gelontCountelonrFor(FILTelonRelonD_AND_TRUNCATelonD).increlonmelonnt();
        } elonlselon if (trimStats.gelontMinIdFiltelonrCount() > 0) {
          stats.gelontCountelonrFor(FILTelonRelonD).increlonmelonnt();
        } elonlselon if (trimStats.gelontRelonsultsTruncatelondFromTailCount() > 0) {
          stats.gelontCountelonrFor(TRUNCATelonD).increlonmelonnt();
        } elonlselon {
          Prelonconditions.chelonckStatelon(falselon, "Invalid TrimStats: %s", trimStats);
        }
      } elonlselon if ((computelonNumRelonsultsToKelonelonp() == melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon())
                 && shouldelonarlyTelonrminatelonWhelonnelonnoughTrimmelondRelonsults()) {
        elonarlyTelonrminationInfo.selontelonarlyTelonrminatelond(truelon);
        elonarlyTelonrminationInfo.addToMelonrgelondelonarlyTelonrminationRelonasons(
            TelonRMINATelonD_COLLelonCTelonD_elonNOUGH_RelonSULTS);
        stats.gelontCountelonrFor(TelonRMINATelonD_GOT_elonXACT_NUM_RelonSULTS).increlonmelonnt();
      } elonlselon {
        stats.gelontCountelonrFor(NOT_elonARLY_TelonRMINATelonD).increlonmelonnt();
      }
    } elonlselon {
      stats.gelontCountelonrFor(ALRelonADY_elonARLY_TelonRMINATelonD).increlonmelonnt();
      // elonvelonn if thelon relonsults welonrelon alrelonady markelond as elonarly telonrminatelond, welon can add additional
      // relonasons for delonbugging (if thelon melonrgelond relonsults welonrelon filtelonrelond or truncatelond).
      addelonarlyTelonrminationRelonasons(elonarlyTelonrminationInfo, trimStats);
    }
  }

  privatelon void addelonarlyTelonrminationRelonasons(
      elonarlyTelonrminationInfo elonarlyTelonrminationInfo,
      TrimStats trimStats) {

    if (trimStats.gelontMinIdFiltelonrCount() > 0) {
      elonarlyTelonrminationInfo.addToMelonrgelondelonarlyTelonrminationRelonasons(
          MelonRGING_elonARLY_TelonRMINATION_RelonASON_FILTelonRelonD);
    }

    if (trimStats.gelontRelonsultsTruncatelondFromTailCount() > 0) {
      elonarlyTelonrminationInfo.addToMelonrgelondelonarlyTelonrminationRelonasons(
          MelonRGING_elonARLY_TelonRMINATION_RelonASON_TRUNCATelonD);
    }
  }

  privatelon elonarlyTelonrminationTrimmingStats gelontelonarlyTelonrminationTrimmingStats() {
    if (accumulatelondRelonsponselons.isMelonrgingPartitionsWithinATielonr()) {
      relonturn gelontelonarlyTelonrminationTrimmingStatsForPartitions();
    } elonlselon {
      relonturn gelontelonarlyTelonrminationTrimmingStatsForTielonrs();
    }
  }

  protelonctelond elonarlyTelonrminationTrimmingStats gelontelonarlyTelonrminationTrimmingStatsForPartitions() {
    relonturn PARTITION_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS;
  }

  protelonctelond elonarlyTelonrminationTrimmingStats gelontelonarlyTelonrminationTrimmingStatsForTielonrs() {
    relonturn TIelonR_MelonRGING_elonARLY_TelonRMINATION_TRIMMING_STATS;
  }

  /**
   * If welon gelont elonnough relonsults, no nelonelond to go on.
   * If onelon of thelon partitions elonarly telonrminatelond, welon can't go on or elonlselon thelonrelon could belon a gap.
   */
  @Ovelonrridelon
  public boolelonan shouldelonarlyTelonrminatelonTielonrMelonrgelon(int totalRelonsultsFromSuccelonssfulShards,
                                                  boolelonan foundelonarlyTelonrmination) {


    int relonsultsRelonquelonstelond = computelonNumRelonsultsToKelonelonp();

    boolelonan shouldelonarlyTelonrminatelon = foundelonarlyTelonrmination
        || totalRelonsultsFromSuccelonssfulShards >= relonsultsRelonquelonstelond;

    if (shouldelonarlyTelonrminatelon && totalRelonsultsFromSuccelonssfulShards < relonsultsRelonquelonstelond) {
      RelonCelonNCY_TIelonR_MelonRGelon_elonARLY_TelonRMINATelonD_WITH_NOT_elonNOUGH_RelonSULTS.increlonmelonnt();
    }

    relonturn shouldelonarlyTelonrminatelon;
  }

  /**
   * Find thelon min status id that has belonelonn _complelontelonly_ selonarchelond across all partitions. Thelon
   * largelonst min status id across all partitions.
   *
   * @relonturn thelon min selonarchelond status id found
   */
  protelonctelond long findMinFullySelonarchelondStatusID() {
    List<Long> minIds = accumulatelondRelonsponselons.gelontMinIds();
    if (minIds.iselonmpty()) {
      relonturn Long.MIN_VALUelon;
    }

    if (accumulatelondRelonsponselons.isMelonrgingPartitionsWithinATielonr()) {
      // Whelonn melonrging partitions, thelon min ID should belon thelon largelonst among thelon min IDs.
      relonturn Collelonctions.max(accumulatelondRelonsponselons.gelontMinIds());
    } elonlselon {
      // Whelonn melonrging tielonrs, thelon min ID should belon thelon smallelonst among thelon min IDs.
      relonturn Collelonctions.min(accumulatelondRelonsponselons.gelontMinIds());
    }
  }

  /**
   * Find thelon max status id that has belonelonn _complelontelonly_ selonarchelond across all partitions. Thelon
   * smallelonst max status id across all partitions.
   *
   * This is whelonrelon welon relonconcilelon relonplication lag by selonleloncting thelon oldelonst maxid from thelon
   * partitions selonarchelond.
   *
   * @relonturn thelon max selonarchelond status id found
   */
   protelonctelond long findMaxFullySelonarchelondStatusID() {
    List<Long> maxIDs = accumulatelondRelonsponselons.gelontMaxIds();
    if (maxIDs.iselonmpty()) {
      relonturn Long.MAX_VALUelon;
    }
    Collelonctions.sort(maxIDs);

    final long nelonwelonst = maxIDs.gelont(maxIDs.sizelon() - 1);
    final long nelonwelonstTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(nelonwelonst);

    for (int i = 0; i < maxIDs.sizelon(); i++) {
      long oldelonst = maxIDs.gelont(i);
      long oldelonstTimelonstamp = SnowflakelonIdParselonr.gelontTimelonstampFromTwelonelontId(oldelonst);
      long delonltaMs = nelonwelonstTimelonstamp - oldelonstTimelonstamp;

      if (i == 0) {
        LOG.delonbug("Max delonlta is {}", delonltaMs);
      }

      if (delonltaMs < ALLOWelonD_RelonPLICATION_LAG_MS) {
        if (i != 0) {
          LOG.delonbug("{} partition relonplicas lagging morelon than {} ms", i, ALLOWelonD_RelonPLICATION_LAG_MS);
        }
        relonturn oldelonst;
      }
    }

    // Can't gelont helonrelon - by this point oldelonst == nelonwelonst, and delonlta is 0.
    relonturn nelonwelonst;
  }

  /**
   * Trim thelon ThriftSelonarchRelonsults if welon havelon elonnough relonsults, to relonturn thelon first
   * 'computelonNumRelonsultsToKelonelonp()' numbelonr of relonsults.
   *
   * If welon don't havelon elonnough relonsults aftelonr trimming, this function will first try to back fill
   * oldelonr relonsults, thelonn nelonwelonr relonsults
   *
   * @param selonarchRelonsults ThriftSelonarchRelonsults that hold thelon to belon trimmelond List<ThriftSelonarchRelonsult>
   * @relonturn TrimStats containing statistics about how many relonsults beloning relonmovelond
   */
  protelonctelond TrimStats trimRelonsults(
      ThriftSelonarchRelonsults selonarchRelonsults,
      long melonrgelondMin,
      long melonrgelondMax) {
    if (!selonarchRelonsults.isSelontRelonsults() || selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      // no relonsults, no trimming nelonelondelond
      relonturn TrimStats.elonMPTY_STATS;
    }

    if (relonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry().isSelontSelonarchStatusIds()) {
      // Not a normal selonarch, no trimming nelonelondelond
      relonturn TrimStats.elonMPTY_STATS;
    }

    TrimStats trimStats = nelonw TrimStats();
    trimelonxactDups(selonarchRelonsults, trimStats);

    int numRelonsultsRelonquelonstelond = computelonNumRelonsultsToKelonelonp();
    if (shouldSkipTrimmingWhelonnNotelonnoughRelonsults(selonarchRelonsults, numRelonsultsRelonquelonstelond)) {
      //////////////////////////////////////////////////////////
      // Welon don't havelon elonnough relonsults, lelont's not do trimming
      //////////////////////////////////////////////////////////
      relonturn trimStats;
    }

    if (accumulatelondRelonsponselons.isMelonrgingPartitionsWithinATielonr()) {
      trimRelonsultsBaselondSelonarchelondRangelon(
          selonarchRelonsults, trimStats, numRelonsultsRelonquelonstelond, melonrgelondMin, melonrgelondMax);
    }

    // Relonspelonct "computelonNumRelonsultsToKelonelonp()" helonrelon, only kelonelonp "computelonNumRelonsultsToKelonelonp()" relonsults.
    truncatelonRelonsults(selonarchRelonsults, trimStats);

    relonturn trimStats;
  }

  /**
   * Whelonn thelonrelon's not elonnough relonsults, welon don't relonmovelon relonsults baselond on thelon selonarchelond rangelon.
   * This has a tradelonoff:  with this, welon don't relonducelon our reloncall whelonn welon alrelonady don't havelon elonnough
   * relonsults. Howelonvelonr, with this, welon can loselon relonsults whilelon paginating beloncauselon welon relonturn relonsults
   * outsidelon of thelon valid selonarchelond rangelon.
   */
  protelonctelond boolelonan shouldSkipTrimmingWhelonnNotelonnoughRelonsults(
      ThriftSelonarchRelonsults selonarchRelonsults, int numRelonsultsRelonquelonstelond) {
    relonturn selonarchRelonsults.gelontRelonsultsSizelon() <= numRelonsultsRelonquelonstelond;
  }


  /**
   * Trim relonsults baselond on selonarch rangelon. Thelon selonarch rangelon [x, y] is delontelonrminelond by:
   *   x is thelon maximun of thelon minimun selonarch IDs;
   *   y is thelon minimun of thelon maximum selonarch IDs.
   *
   * Ids out sidelon of this rangelon arelon relonmovelond.
   * If welon do not gelont elonnough relonsults aftelonr thelon relonmoval, welon add IDs back until welon gelont elonnough relonsults.
   * Welon first add IDs back from thelon oldelonr sidelon back. If thelonrelon's still not elonnough relonsults,
   * welon start adding IDs from thelon nelonwelonr sidelon back.
   */
  privatelon void trimRelonsultsBaselondSelonarchelondRangelon(ThriftSelonarchRelonsults selonarchRelonsults,
                                             TrimStats trimStats,
                                             int numRelonsultsRelonquelonstelond,
                                             long melonrgelondMin,
                                             long melonrgelondMax) {
    ///////////////////////////////////////////////////////////////////
    // welon havelon morelon relonsults than relonquelonstelond, lelont's do somelon trimming
    ///////////////////////////////////////////////////////////////////

    // Savelon thelon original relonsults belonforelon trimming
    List<ThriftSelonarchRelonsult> originalRelonsults = selonarchRelonsults.gelontRelonsults();

    filtelonrRelonsultsByMelonrgelondMinMaxIds(selonarchRelonsults, melonrgelondMax, melonrgelondMin, trimStats);

    // This doelons happelonn. It is hard to say what welon should do helonrelon so welon just relonturn thelon original
    // relonsult helonrelon.
    if (selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      RelonCelonNCY_ZelonRO_RelonSULT_COUNT_AFTelonR_FILTelonRING_MAX_MIN_IDS.increlonmelonnt();
      selonarchRelonsults.selontRelonsults(originalRelonsults);

      // Clelonan up min/mix filtelonrelond count, sincelon welon'relon bringing back whatelonvelonr welon just filtelonrelond.
      trimStats.clelonarMaxIdFiltelonrCount();
      trimStats.clelonarMinIdFiltelonrCount();

      if (LOG.isDelonbugelonnablelond() || relonsponselonMelonssagelonBuildelonr.isDelonbugModelon()) {
        String elonrrMsg = "No trimming is donelon as filtelonrelond relonsults is elonmpty. "
            + "maxId=" + melonrgelondMax + ",minId=" + melonrgelondMin;
        LOG.delonbug(elonrrMsg);
        relonsponselonMelonssagelonBuildelonr.appelonnd(elonrrMsg + "\n");
      }
    } elonlselon {
      // oops! welon'relon trimming too many relonsults. Lelont's put somelon back
      if (selonarchRelonsults.gelontRelonsultsSizelon() < numRelonsultsRelonquelonstelond) {
        RelonCelonNCY_TRIMMelonD_TOO_MANY_RelonSULTS_COUNT.increlonmelonnt();

        List<ThriftSelonarchRelonsult> trimmelondRelonsults = selonarchRelonsults.gelontRelonsults();
        long firstTrimmelondRelonsultId = trimmelondRelonsults.gelont(0).gelontId();
        long lastTrimmelondRelonsultId = trimmelondRelonsults.gelont(trimmelondRelonsults.sizelon() - 1).gelontId();

        // First, try to back fill with oldelonr relonsults
        int i = 0;
        for (; i < originalRelonsults.sizelon(); ++i) {
          ThriftSelonarchRelonsult relonsult = originalRelonsults.gelont(i);
          if (relonsult.gelontId() < lastTrimmelondRelonsultId) {
            trimmelondRelonsults.add(relonsult);
            trimStats.deloncrelonaselonMinIdFiltelonrCount();
            if (trimmelondRelonsults.sizelon() >= numRelonsultsRelonquelonstelond) {
              brelonak;
            }
          }
        }

        // still not elonnough relonsults? back fill with nelonwelonr relonsults
        // find thelon oldelonst of thelon nelonwelonr relonsults
        if (trimmelondRelonsults.sizelon() < numRelonsultsRelonquelonstelond) {
          // still not elonnough relonsults? back fill with nelonwelonr relonsults
          // find thelon oldelonst of thelon nelonwelonr relonsults
          for (i = originalRelonsults.sizelon() - 1; i >= 0; --i) {
            ThriftSelonarchRelonsult relonsult = originalRelonsults.gelont(i);
            if (relonsult.gelontId() > firstTrimmelondRelonsultId) {
              trimmelondRelonsults.add(relonsult);
              trimStats.deloncrelonaselonMaxIdFiltelonrCount();
              if (trimmelondRelonsults.sizelon() >= numRelonsultsRelonquelonstelond) {
                brelonak;
              }
            }
          }

          // nelonwelonr relonsults welonrelon addelond to thelon back of thelon list, relon-sort
          Collelonctions.sort(trimmelondRelonsults, RelonsultComparators.ID_COMPARATOR);
        }
      }
    }
  }

  protelonctelond void selontMelonrgelondMinSelonarchelondStatusId(
      ThriftSelonarchRelonsults selonarchRelonsults,
      long currelonntMelonrgelondMin,
      boolelonan relonsultsWelonrelonTrimmelond) {
    if (accumulatelondRelonsponselons.gelontMinIds().iselonmpty()) {
      relonturn;
    }

    long melonrgelond;
    if (selonarchRelonsults == null
        || !selonarchRelonsults.isSelontRelonsults()
        || selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      melonrgelond = currelonntMelonrgelondMin;
    } elonlselon {
      List<ThriftSelonarchRelonsult> relonsults = selonarchRelonsults.gelontRelonsults();
      long firstRelonsultId = relonsults.gelont(0).gelontId();
      long lastRelonsultId = relonsults.gelont(relonsults.sizelon() - 1).gelontId();
      melonrgelond = Math.min(firstRelonsultId, lastRelonsultId);
      if (!relonsultsWelonrelonTrimmelond) {
        // If thelon relonsults welonrelon trimmelond, welon want to selont minSelonarchelondStatusID to thelon smallelonst
        // twelonelont ID in thelon relonsponselon. Othelonrwiselon, welon want to takelon thelon min belontwelonelonn that, and
        // thelon currelonnt minSelonarchelondStatusID.
        melonrgelond = Math.min(melonrgelond, currelonntMelonrgelondMin);
      }
    }

    selonarchRelonsults.selontMinSelonarchelondStatusID(melonrgelond);
  }

  privatelon void selontMelonrgelondMaxSelonarchelondStatusId(
      ThriftSelonarchRelonsults selonarchRelonsults,
      long currelonntMelonrgelondMax) {
    if (accumulatelondRelonsponselons.gelontMaxIds().iselonmpty()) {
      relonturn;
    }

    long melonrgelond;
    if (selonarchRelonsults == null
        || !selonarchRelonsults.isSelontRelonsults()
        || selonarchRelonsults.gelontRelonsultsSizelon() == 0) {
      melonrgelond = currelonntMelonrgelondMax;
    } elonlselon {
      List<ThriftSelonarchRelonsult> relonsults = selonarchRelonsults.gelontRelonsults();
      long firstRelonsultId = relonsults.gelont(0).gelontId();
      long lastRelonsultId = relonsults.gelont(relonsults.sizelon() - 1).gelontId();
      long maxRelonsultId = Math.max(firstRelonsultId, lastRelonsultId);
      melonrgelond = Math.max(maxRelonsultId, currelonntMelonrgelondMax);
    }

    selonarchRelonsults.selontMaxSelonarchelondStatusID(melonrgelond);
  }

  protelonctelond static void filtelonrRelonsultsByMelonrgelondMinMaxIds(
      ThriftSelonarchRelonsults relonsults, long maxStatusId, long minStatusId, TrimStats trimStats) {
    List<ThriftSelonarchRelonsult> trimelondRelonsults =
        Lists.nelonwArrayListWithCapacity(relonsults.gelontRelonsultsSizelon());

    for (ThriftSelonarchRelonsult relonsult : relonsults.gelontRelonsults()) {
      long statusId = relonsult.gelontId();

      if (statusId > maxStatusId) {
        trimStats.increlonaselonMaxIdFiltelonrCount();
      } elonlselon if (statusId < minStatusId) {
        trimStats.increlonaselonMinIdFiltelonrCount();
      } elonlselon {
        trimelondRelonsults.add(relonsult);
      }
    }

    relonsults.selontRelonsults(trimelondRelonsults);
  }
}
