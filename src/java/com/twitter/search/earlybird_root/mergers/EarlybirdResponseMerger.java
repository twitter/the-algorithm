packagelon com.twittelonr.selonarch.elonarlybird_root.melonrgelonrs;

import java.util.Collelonctions;
import java.util.HashSelont;
import java.util.List;
import java.util.Map;

import scala.runtimelon.BoxelondUnit;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Optional;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.ImmutablelonList;
import com.googlelon.common.collelonct.Lists;
import com.googlelon.common.collelonct.Maps;
import com.googlelon.common.collelonct.Selonts;

import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.schelonma.elonarlybird.elonarlybirdClustelonr;
import com.twittelonr.selonarch.common.util.FinaglelonUtil;
import com.twittelonr.selonarch.common.util.elonarlybird.elonarlybirdRelonsponselonMelonrgelonUtil;
import com.twittelonr.selonarch.common.util.elonarlybird.RelonsultsUtil;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdDelonbugInfo;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonquelonst;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselon;
import com.twittelonr.selonarch.elonarlybird.thrift.elonarlybirdRelonsponselonCodelon;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsult;
import com.twittelonr.selonarch.elonarlybird.thrift.ThriftSelonarchRelonsults;
import com.twittelonr.selonarch.elonarlybird_root.collelonctors.MultiwayMelonrgelonCollelonctor;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdFelonaturelonSchelonmaMelonrgelonr;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstContelonxt;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstTypelon;
import com.twittelonr.selonarch.elonarlybird_root.common.elonarlybirdRelonquelonstUtil;
import com.twittelonr.util.Function;
import com.twittelonr.util.Futurelon;

/**
 * Baselon elonarlybirdRelonsponselonMelonrgelonr containing basic logic to melonrgelon elonarlybirdRelonsponselon objeloncts
 */
public abstract class elonarlybirdRelonsponselonMelonrgelonr implelonmelonnts elonarlyTelonrminatelonTielonrMelonrgelonPrelondicatelon {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(elonarlybirdRelonsponselonMelonrgelonr.class);
  privatelon static final Loggelonr MIN_SelonARCHelonD_STATUS_ID_LOGGelonR =
      LoggelonrFactory.gelontLoggelonr("MinSelonarchelondStatusIdLoggelonr");

  privatelon static final SelonarchCountelonr NO_SelonARCH_RelonSULT_COUNTelonR =
      SelonarchCountelonr.elonxport("no_selonarch_relonsult_count");
  privatelon static final SelonarchCountelonr NO_RelonSPONSelonS_TO_MelonRGelon =
      SelonarchCountelonr.elonxport("no_relonsponselons_to_melonrgelon");
  privatelon static final SelonarchCountelonr elonARLYBIRD_RelonSPONSelon_NO_MORelon_RelonSULTS =
      SelonarchCountelonr.elonxport("melonrgelonr_elonarlybird_relonsponselon_no_morelon_relonsults");
  privatelon static final String PARTITION_OR_TIelonR_COUNTelonR_NAMelon_FORMAT =
      "melonrgelonr_waitelond_for_relonsponselon_from_%s_countelonr";
  privatelon static final String PARTITION_OR_TIelonR_elonRROR_COUNTelonR_NAMelon_FORMAT =
      "melonrgelonr_num_elonrror_relonsponselons_from_%s";
  privatelon static final String PARTITION_OR_TIelonR_RelonSPONSelon_CODelon_COUNTelonR_NAMelon_FORMAT =
      "melonrgelonr_elonarlybird_relonsponselon_codelon_from_%s_%s";

  protelonctelond final elonarlybirdRelonsponselonDelonbugMelonssagelonBuildelonr relonsponselonMelonssagelonBuildelonr;
  protelonctelond final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt;
  protelonctelond final ImmutablelonList<Futurelon<elonarlybirdRelonsponselon>> relonsponselons;
  protelonctelond AccumulatelondRelonsponselons accumulatelondRelonsponselons;


  @VisiblelonForTelonsting
  static final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> MelonRGelonR_CRelonATelonD_STATS =
      pelonrRelonquelonstTypelonCountelonrImmutablelonMap("elonarlybird_relonsponselon_melonrgelonr_%s_crelonatelond_count");

  @VisiblelonForTelonsting
  static final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr>
    MIN_SelonARCHelonD_STATUS_ID_LARGelonR_THAN_RelonQUelonST_MAX_ID = pelonrRelonquelonstTypelonCountelonrImmutablelonMap(
        "melonrgelonr_%s_min_selonarchelond_status_id_largelonr_than_relonquelonst_max_id");

  @VisiblelonForTelonsting
  static final Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr>
    MIN_SelonARCHelonD_STATUS_ID_LARGelonR_THAN_RelonQUelonST_UNTIL_TIMelon = pelonrRelonquelonstTypelonCountelonrImmutablelonMap(
        "melonrgelonr_%s_min_selonarchelond_status_id_largelonr_than_relonquelonst_until_timelon");

  privatelon static Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> pelonrRelonquelonstTypelonCountelonrImmutablelonMap(
      String statPattelonrn) {
    Map<elonarlybirdRelonquelonstTypelon, SelonarchCountelonr> statsMap = Maps.nelonwelonnumMap(elonarlybirdRelonquelonstTypelon.class);
    for (elonarlybirdRelonquelonstTypelon elonarlybirdRelonquelonstTypelon : elonarlybirdRelonquelonstTypelon.valuelons()) {
      String statNamelon = String.format(statPattelonrn, elonarlybirdRelonquelonstTypelon.gelontNormalizelondNamelon());
      statsMap.put(elonarlybirdRelonquelonstTypelon, SelonarchCountelonr.elonxport(statNamelon));
    }

    relonturn Maps.immutablelonelonnumMap(statsMap);
  }

  public static final com.googlelon.common.baselon.Function<elonarlybirdRelonsponselon, Map<Long, Intelongelonr>>
    HIT_COUNT_GelonTTelonR =
      relonsponselon -> relonsponselon.gelontSelonarchRelonsults() == null
        ? null
        : relonsponselon.gelontSelonarchRelonsults().gelontHitCounts();

  privatelon final ChainMelonrgelonr chainMelonrgelonr;

  privatelon class ChainMelonrgelonr {
    privatelon final elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt;
    privatelon final RelonsponselonAccumulator relonsponselonAccumulator;
    privatelon final List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons;
    privatelon final elonarlybirdRelonsponselonDelonbugMelonssagelonBuildelonr relonsponselonMelonssagelonBuildelonr;
    privatelon int currelonntFuturelonIndelonx = -1;

    public ChainMelonrgelonr(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                       RelonsponselonAccumulator relonsponselonAccumulator,
                       List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons,
                       elonarlybirdRelonsponselonDelonbugMelonssagelonBuildelonr relonsponselonMelonssagelonBuildelonr) {
      this.relonquelonstContelonxt = relonquelonstContelonxt;
      this.relonsponselonAccumulator = relonsponselonAccumulator;
      this.relonsponselons = relonsponselons;
      this.relonsponselonMelonssagelonBuildelonr = relonsponselonMelonssagelonBuildelonr;
    }

    public Futurelon<elonarlybirdRelonsponselon> melonrgelon() {
      // 'relonsponselonFuturelons' should always belon sortelond.
      // Whelonn relonturnelond by elonarlybirdScattelonrGathelonr selonrvicelon, thelon relonsponselons arelon sortelond by partition ID.
      // Whelonn relonturnelond by elonarlybirdChainelondScattelonrGathelonrSelonrvicelon,
      // relonsponselons arelon sortelond delonscelonnding by tielonr start datelon. Selonelon:
      // com.twittelonr.selonarch.elonarlybird_root.elonarlybirdChainelondScattelonrGathelonrSelonrvicelon.TIelonR_COMPARATOR.
      //
      // Whelonn melonrging relonsponselons from partitions, welon want to wait for relonsponselons from all partitions,
      // so thelon ordelonr in which welon wait for thoselon relonsults doelons not mattelonr. Whelonn melonrging relonsponselons
      // from tielonrs, welon want to wait for thelon relonsponselon from thelon latelonst. If welon don't nelonelond any morelon
      // relonsponselons to computelon thelon final relonsponselon, thelonn welon don't nelonelond to wait for thelon relonsponselons from
      // othelonr tielonrs. If welon cannot telonrminatelon elonarly, thelonn welon want to wait for thelon relonsponselons from thelon
      // seloncond tielonr, and so on.
      //
      // Welon do not nelonelond to havelon any elonxplicit synchronization, beloncauselon:
      //   1. Thelon callbacks for futurelon_i arelon selont by thelon flatMap() callback on futurelon_{i-1} (whelonn
      //      reloncursivelonly calling melonrgelon() insidelon thelon flatMap()).
      //   2. Belonforelon selontting thelon callbacks on futurelon_i, futurelon_{i-1}.flatMap() adds thelon relonsponselon
      //      relonsults to melonrgelonHelonlpelonr.
      //   3. Whelonn thelon callbacks on futurelon_i arelon selont, thelon melonmory barrielonr belontwelonelonn
      //      threlonad_running_futurelon_{i-1} and threlonad_running_futurelon_i is crosselond. This guarantelonelons
      //      that threlonad_running_futurelon_i will selonelon thelon updatelons to melonrgelonHelonlpelonr belonforelon it selonelons thelon
      //      callbacks. (Or threlonad_running_futurelon_{i-1} == threlonad_running_futurelon_i, in which caselon
      //      synchronization is not an issuelon, and correlonctnelonss is guaratelonelond by thelon ordelonr in which
      //      things will run.)
      //   4. Thelon samelon relonasoning applielons to currelonntFuturelonIndelonx.

      ++currelonntFuturelonIndelonx;
      if (currelonntFuturelonIndelonx >= relonsponselons.sizelon()) {
        relonturn Futurelon.valuelon(gelontTimelondMelonrgelondRelonsponselon(relonsponselonAccumulator.gelontAccumulatelondRelonsults()));
      }

      final String partitionTielonrNamelon =
          relonsponselonAccumulator.gelontNamelonForLogging(currelonntFuturelonIndelonx, relonsponselons.sizelon());
      final String namelonForelonarlybirdRelonsponselonCodelonStats =
          relonsponselonAccumulator.gelontNamelonForelonarlybirdRelonsponselonCodelonStats(
              currelonntFuturelonIndelonx, relonsponselons.sizelon());

      // If a tielonr in thelon chain throws an elonxcelonption, convelonrt it to a null relonsponselon, and lelont thelon
      // melonrgelonHelonlpelonr handlelon it appropriatelonly.
      relonturn relonsponselons.gelont(currelonntFuturelonIndelonx)
        .handlelon(Function.func(t -> {
          if (FinaglelonUtil.isCancelonlelonxcelonption(t)) {
            relonturn nelonw elonarlybirdRelonsponselon()
                .selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.CLIelonNT_CANCelonL_elonRROR);
          } elonlselon if (FinaglelonUtil.isTimelonoutelonxcelonption(t)) {
            relonturn nelonw elonarlybirdRelonsponselon()
                .selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.SelonRVelonR_TIMelonOUT_elonRROR);
          } elonlselon {
            SelonarchCountelonr.elonxport(
                String.format(PARTITION_OR_TIelonR_elonRROR_COUNTelonR_NAMelon_FORMAT, partitionTielonrNamelon))
                .increlonmelonnt();
            if (relonsponselonMelonssagelonBuildelonr.isDelonbugModelon()) {
              relonsponselonMelonssagelonBuildelonr.delonbugAndLogWarning(
                  String.format("[%s] failelond, elonxcelonption [%s]",
                      partitionTielonrNamelon, t.toString()));
            }
            LOG.warn("elonxcelonption relonsponselon from: " + partitionTielonrNamelon, t);
            relonturn nelonw elonarlybirdRelonsponselon()
                .selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
          }
        }))
        .flatMap(Function.func(relonsponselon -> {
          Prelonconditions.chelonckNotNull(relonsponselon);

          SelonarchCountelonr.elonxport(
              String.format(PARTITION_OR_TIelonR_RelonSPONSelon_CODelon_COUNTelonR_NAMelon_FORMAT,
                            namelonForelonarlybirdRelonsponselonCodelonStats,
                            relonsponselon.gelontRelonsponselonCodelon().namelon().toLowelonrCaselon()))
              .increlonmelonnt();

          if ((relonsponselon.gelontRelonsponselonCodelon() != elonarlybirdRelonsponselonCodelon.PARTITION_SKIPPelonD)
              && (relonsponselon.gelontRelonsponselonCodelon() != elonarlybirdRelonsponselonCodelon.TIelonR_SKIPPelonD)) {
            SelonarchCountelonr.elonxport(
                String.format(PARTITION_OR_TIelonR_COUNTelonR_NAMelon_FORMAT, partitionTielonrNamelon))
              .increlonmelonnt();
          }

          if (relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.CLIelonNT_CANCelonL_elonRROR) {
            // thelon relonquelonst has belonelonn cancelonllelond, no nelonelond to procelonelond
            relonturn Futurelon.valuelon(relonsponselon);
          }

          relonwritelonRelonsponselonCodelonIfSelonarchRelonsultsMissing(relonquelonstContelonxt, partitionTielonrNamelon, relonsponselon);
          relonsponselonMelonssagelonBuildelonr.logRelonsponselonDelonbugInfo(
              relonquelonstContelonxt.gelontRelonquelonst(),
              partitionTielonrNamelon,
              relonsponselon);
          relonsponselonAccumulator.addRelonsponselon(
              relonsponselonMelonssagelonBuildelonr,
              relonquelonstContelonxt.gelontRelonquelonst(),
              relonsponselon);

          if (relonsponselonAccumulator.shouldelonarlyTelonrminatelonMelonrgelon(elonarlybirdRelonsponselonMelonrgelonr.this)) {
            relonturn Futurelon.valuelon(gelontTimelondMelonrgelondRelonsponselon(
                relonsponselonAccumulator.gelontAccumulatelondRelonsults()));
          }
          relonturn melonrgelon();
        }));
    }
  }

  privatelon void relonwritelonRelonsponselonCodelonIfSelonarchRelonsultsMissing(
      elonarlybirdRelonquelonstContelonxt elonarlybirdRelonquelonstContelonxt,
      String partitionTielonrNamelon,
      elonarlybirdRelonsponselon relonsponselon) {
    // Welon always relonquirelon selonarchRelonsults to belon selont, elonvelonn for telonrm stats and facelont relonquelonsts.
    // This is beloncauselon selonarchRelonsults contains important info such as pagination cursors
    // likelon minSelonarchStatusId and minSelonarchelondTimelonSincelonelonpoch.
    // Welon elonxpelonct all succelonssful relonsponselons to havelon selonarchRelonsults selont.
    if (relonsponselon.isSelontRelonsponselonCodelon()
        && relonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS
        && relonsponselon.gelontSelonarchRelonsults() == null) {
      NO_SelonARCH_RelonSULT_COUNTelonR.increlonmelonnt();
      LOG.warn("Reloncelonivelond elonarlybird relonsponselon with null selonarchRelonsults from [{}]"
               + " elonarlybirdRelonquelonst [{}] elonarlybirdRelonsponselon [{}] ",
               partitionTielonrNamelon, elonarlybirdRelonquelonstContelonxt.gelontRelonquelonst(), relonsponselon);
      relonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.TRANSIelonNT_elonRROR);
    }
  }

  /**
   * Construct a elonarlybirdRelonsponselonMelonrgelonr to melonrgelon relonsponselons from multiplelon partitions or tielonrs
   * baselond on modelon.
   */
  elonarlybirdRelonsponselonMelonrgelonr(elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
                          List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons,
                          RelonsponselonAccumulator relonsponselonAccumulator) {
    this.relonquelonstContelonxt = relonquelonstContelonxt;
    this.relonsponselons = ImmutablelonList.copyOf(relonsponselons);
    this.relonsponselonMelonssagelonBuildelonr =
        nelonw elonarlybirdRelonsponselonDelonbugMelonssagelonBuildelonr(relonquelonstContelonxt.gelontRelonquelonst());
    this.chainMelonrgelonr = nelonw ChainMelonrgelonr(relonquelonstContelonxt, relonsponselonAccumulator, relonsponselons,
        relonsponselonMelonssagelonBuildelonr);
  }

  /**
   * Gelont a relonsponselon melonrgelonr to melonrgelon thelon givelonn relonsponselons.
   */
  public static elonarlybirdRelonsponselonMelonrgelonr gelontRelonsponselonMelonrgelonr(
      elonarlybirdRelonquelonstContelonxt relonquelonstContelonxt,
      List<Futurelon<elonarlybirdRelonsponselon>> relonsponselons,
      RelonsponselonAccumulator helonlpelonr,
      elonarlybirdClustelonr clustelonr,
      elonarlybirdFelonaturelonSchelonmaMelonrgelonr felonaturelonSchelonmaMelonrgelonr,
      int numPartitions) {
    elonarlybirdRelonquelonstTypelon typelon = relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon();
    MelonRGelonR_CRelonATelonD_STATS.gelont(typelon).increlonmelonnt();
    switch (typelon) {
      caselon FACelonTS:
        relonturn nelonw FacelontRelonsponselonMelonrgelonr(relonquelonstContelonxt, relonsponselons, helonlpelonr);
      caselon TelonRM_STATS:
        relonturn nelonw TelonrmStatisticsRelonsponselonMelonrgelonr(relonquelonstContelonxt, relonsponselons, helonlpelonr);
      caselon RelonCelonNCY:
        relonturn nelonw ReloncelonncyRelonsponselonMelonrgelonr(relonquelonstContelonxt, relonsponselons, helonlpelonr, felonaturelonSchelonmaMelonrgelonr);
      caselon STRICT_RelonCelonNCY:
        relonturn nelonw StrictReloncelonncyRelonsponselonMelonrgelonr(
            relonquelonstContelonxt, relonsponselons, helonlpelonr, felonaturelonSchelonmaMelonrgelonr, clustelonr);
      caselon RelonLelonVANCelon:
        relonturn nelonw RelonlelonvancelonRelonsponselonMelonrgelonr(
            relonquelonstContelonxt, relonsponselons, helonlpelonr, felonaturelonSchelonmaMelonrgelonr, numPartitions);
      caselon TOP_TWelonelonTS:
        relonturn nelonw TopTwelonelontsRelonsponselonMelonrgelonr(relonquelonstContelonxt, relonsponselons, helonlpelonr);
      delonfault:
        throw nelonw Runtimelonelonxcelonption("elonarlybirdRelonquelonstTypelon " + typelon + "is not supportelond by melonrgelon");
    }
  }

  /**
   * This melonthod can pelonrform two typelons of melonrgelons:
   *   1. melonrgelon relonsponselons within a tielonr from diffelonrelonnt partitions.
   *   2. melonrgelon relonsponselons from multiplelon tielonrs.
   */
  public final Futurelon<elonarlybirdRelonsponselon> melonrgelon() {
    relonturn chainMelonrgelonr.melonrgelon()
        .onSuccelonss(chelonckMinSelonarchelondStatusIdFunction(
                 "max_id",
                 elonarlybirdRelonquelonstUtil.gelontRelonquelonstMaxId(relonquelonstContelonxt.gelontParselondQuelonry()),
                 MIN_SelonARCHelonD_STATUS_ID_LARGelonR_THAN_RelonQUelonST_MAX_ID.gelont(
                     relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon())))
        .onSuccelonss(chelonckMinSelonarchelondStatusIdFunction(
                 "until_timelon",
                 elonarlybirdRelonquelonstUtil.gelontRelonquelonstMaxIdFromUntilTimelon(relonquelonstContelonxt.gelontParselondQuelonry()),
                 MIN_SelonARCHelonD_STATUS_ID_LARGelonR_THAN_RelonQUelonST_UNTIL_TIMelon.gelont(
                     relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon())));
  }

  /**
   * Relonturns thelon function that cheloncks if thelon minSelonarchelondStatusID on thelon melonrgelond relonsponselon is highelonr
   * than thelon max ID in thelon relonquelonst.
   */
  privatelon Function<elonarlybirdRelonsponselon, BoxelondUnit> chelonckMinSelonarchelondStatusIdFunction(
      final String opelonrator, final Optional<Long> relonquelonstMaxId, final SelonarchCountelonr stat) {
    relonturn Function.cons(melonrgelondRelonsponselon -> {
      if (relonquelonstMaxId.isPrelonselonnt()
          && relonquelonstMaxId.gelont() != Long.MAX_VALUelon
          && (melonrgelondRelonsponselon.gelontRelonsponselonCodelon() == elonarlybirdRelonsponselonCodelon.SUCCelonSS)
          && melonrgelondRelonsponselon.isSelontSelonarchRelonsults()
          && melonrgelondRelonsponselon.gelontSelonarchRelonsults().isSelontMinSelonarchelondStatusID()) {
        long minSelonarchelondStatusId = melonrgelondRelonsponselon.gelontSelonarchRelonsults().gelontMinSelonarchelondStatusID();
        // Welon somelontimelons selont minSelonarchelondStatusId = max_id + 1 whelonn a relonquelonst timelons out elonvelonn
        // belonforelon any selonarch happelonns.
        // Chelonck SelonARCH-10134 for morelon delontails.
        if (minSelonarchelondStatusId > relonquelonstMaxId.gelont() + 1) {
          stat.increlonmelonnt();
          String logMelonssagelon = "Relonsponselon has a minSelonarchelondStatusID ({}) largelonr than relonquelonst "
              + opelonrator + " ({})."
              + "\nrelonquelonst typelon: {}"
              + "\nrelonquelonst: {}"
              + "\nmelonrgelond relonsponselon: {}"
              + "\nSuccelonssful accumulatelond relonsponselons:";
          List<Objelonct> logMelonssagelonParams = Lists.nelonwArrayList();
          logMelonssagelonParams.add(minSelonarchelondStatusId);
          logMelonssagelonParams.add(relonquelonstMaxId.gelont());
          logMelonssagelonParams.add(relonquelonstContelonxt.gelontelonarlybirdRelonquelonstTypelon());
          logMelonssagelonParams.add(relonquelonstContelonxt.gelontRelonquelonst());
          logMelonssagelonParams.add(melonrgelondRelonsponselon);
          for (elonarlybirdRelonsponselon relonsponselon : accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons()) {
            logMelonssagelon += "\naccumulatelond relonsponselon: {}";
            logMelonssagelonParams.add(relonsponselon);
          }
          MIN_SelonARCHelonD_STATUS_ID_LOGGelonR.warn(logMelonssagelon, logMelonssagelonParams.toArray());
        }
      }
    });
  }

  privatelon elonarlybirdRelonsponselon gelontTimelondMelonrgelondRelonsponselon(AccumulatelondRelonsponselons accRelonsponselons) {
    long start = Systelonm.nanoTimelon();
    try {
      relonturn gelontMelonrgelondRelonsponselon(accRelonsponselons);
    } finally {
      long totalTimelon = Systelonm.nanoTimelon() - start;
      gelontMelonrgelondRelonsponselonTimelonr().timelonrIncrelonmelonnt(totalTimelon);
    }
  }

  privatelon elonarlybirdRelonsponselon initializelonMelonrgelondSuccelonssRelonsponselonFromAccumulatelondRelonsponselons() {
    elonarlybirdRelonsponselon melonrgelondRelonsponselon = nelonw elonarlybirdRelonsponselon();

    AccumulatelondRelonsponselons.PartitionCounts partitionCounts =
        accumulatelondRelonsponselons.gelontPartitionCounts();

    melonrgelondRelonsponselon.selontNumPartitions(partitionCounts.gelontNumPartitions())
        .selontNumSuccelonssfulPartitions(partitionCounts.gelontNumSuccelonssfulPartitions())
        .selontPelonrTielonrRelonsponselon(partitionCounts.gelontPelonrTielonrRelonsponselon())
        .selontNumSelonarchelondSelongmelonnts(accumulatelondRelonsponselons.gelontNumSelonarchelondSelongmelonnts());

    melonrgelondRelonsponselon.selontelonarlyTelonrminationInfo(accumulatelondRelonsponselons.gelontMelonrgelondelonarlyTelonrminationInfo());
    melonrgelondRelonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.SUCCelonSS);

    relonturn melonrgelondRelonsponselon;
  }

  privatelon elonarlybirdRelonsponselon gelontMelonrgelondRelonsponselon(AccumulatelondRelonsponselons accRelonsponselons) {
    accumulatelondRelonsponselons = accRelonsponselons;
    elonarlybirdRelonsponselon melonrgelondRelonsponselon;

    if (accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().iselonmpty()
        && !accumulatelondRelonsponselons.foundelonrror()) {
      // No succelonssful or elonrror relonsponselons. This melonans that all tielonrs / partitions arelon intelonntionally
      // skippelond. Relonturn a blank succelonssful relonsponselon.
      NO_RelonSPONSelonS_TO_MelonRGelon.increlonmelonnt();
      melonrgelondRelonsponselon = nelonw elonarlybirdRelonsponselon()
          .selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.SUCCelonSS)
          .selontSelonarchRelonsults(nelonw ThriftSelonarchRelonsults())
          .selontDelonbugString("No relonsponselons to melonrgelon, probably beloncauselon all tielonrs/partitions "
              + "welonrelon skippelond.");
    } elonlselon if (accumulatelondRelonsponselons.isMelonrgingAcrossTielonrs()) {
      melonrgelondRelonsponselon = gelontMelonrgelondRelonsponselonAcrossTielonrs();
    } elonlselon {
      melonrgelondRelonsponselon = gelontMelonrgelondRelonsponselonAcrossPartitions();
    }

    savelonMelonrgelondDelonbugString(melonrgelondRelonsponselon);
    relonturn melonrgelondRelonsponselon;
  }

  privatelon elonarlybirdRelonsponselon gelontMelonrgelondRelonsponselonAcrossTielonrs() {
    Prelonconditions.chelonckStatelon(
        !accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().iselonmpty()
            || accumulatelondRelonsponselons.foundelonrror());

    // Whelonn melonrging across tielonrs, if welon havelon onelon failelond tielonr, welon should fail thelon wholelon
    // relonsponselon. Notelon that duelon to elonarly telonrmination, if a tielonr that is old fails
    // but thelon nelonwelonr tielonrs relonturn elonnough relonsults, thelon failelond tielonr won't show up
    // helonrelon in accumulatelondRelonsponselons -- thelon only tielonrs that show up helonrelon
    // will belon succelonssful.
    if (accumulatelondRelonsponselons.foundelonrror()) {
      // Thelon TielonrRelonsponselonAccumulator elonarly telonrminatelons on thelon first elonrror, so welon should
      // nelonvelonr gelont morelon than onelon elonrror. This melonans that thelon gelontMelonrgelondelonrrorRelonsponselon will
      // relonturn an elonrror relonsponselon with thelon elonrror codelon of that onelon elonrror, and will nelonvelonr
      // havelon to deloncidelon which elonrror relonsponselon to relonturn if thelon elonrror relonsponselons arelon all
      // diffelonrelonnt.

      // Pelonrhaps welon should just relonturn accumulatelondRelonsponselons.gelontelonrrorRelonsponselons().gelont(0);
      Prelonconditions.chelonckStatelon(accumulatelondRelonsponselons.gelontelonrrorRelonsponselons().sizelon() == 1);
      relonturn accumulatelondRelonsponselons.gelontMelonrgelondelonrrorRelonsponselon();
    } elonlselon {
      elonarlybirdRelonsponselon melonrgelondRelonsponselon = initializelonMelonrgelondSuccelonssRelonsponselonFromAccumulatelondRelonsponselons();
      relonturn intelonrnalMelonrgelon(melonrgelondRelonsponselon);
    }
  }

  privatelon elonarlybirdRelonsponselon gelontMelonrgelondRelonsponselonAcrossPartitions() {
    Prelonconditions.chelonckStatelon(
        !accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().iselonmpty()
            || accumulatelondRelonsponselons.foundelonrror());

    elonarlybirdRelonsponselon melonrgelondRelonsponselon;

    // Unlikelon tielonr melonrging, onelon failelond relonsponselon doelonsn't melonan thelon melonrgelond relonsponselon should
    // fail. If welon havelon succelonssful relonsponselons welon can chelonck thelon succelonss ratio and if its
    // good welon can still relonturn a succelonssful melonrgelon.
    if (!accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().iselonmpty()) {
      // Welon havelon at lelonast onelon succelonssful relonsponselon, but still nelonelond to chelonck thelon succelonss ratio.
      // melonrgelondRelonsponselon is a SUCCelonSS relonsponselon aftelonr this call, but welon will
      // selont it to failurelon belonlow if neloncelonssary.
      melonrgelondRelonsponselon = initializelonMelonrgelondSuccelonssRelonsponselonFromAccumulatelondRelonsponselons();

      int numSuccelonssRelonsponselons = melonrgelondRelonsponselon.gelontNumSuccelonssfulPartitions();
      int numPartitions = melonrgelondRelonsponselon.gelontNumPartitions();
      doublelon succelonssThrelonshold = gelontSuccelonssRelonsponselonThrelonshold();
      if (chelonckSuccelonssPartitionRatio(numSuccelonssRelonsponselons, numPartitions, succelonssThrelonshold)) {
        // Succelonss! Procelonelond with melonrging.
        melonrgelondRelonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.SUCCelonSS);
        melonrgelondRelonsponselon = intelonrnalMelonrgelon(melonrgelondRelonsponselon);
      } elonlselon {
        relonsponselonMelonssagelonBuildelonr.logBelonlowSuccelonssThrelonshold(
            relonquelonstContelonxt.gelontRelonquelonst().gelontSelonarchQuelonry(), numSuccelonssRelonsponselons, numPartitions,
            succelonssThrelonshold);
        melonrgelondRelonsponselon.selontRelonsponselonCodelon(elonarlybirdRelonsponselonCodelon.TOO_MANY_PARTITIONS_FAILelonD_elonRROR);
      }
    } elonlselon {
      melonrgelondRelonsponselon = accumulatelondRelonsponselons.gelontMelonrgelondelonrrorRelonsponselon();
    }

    relonturn melonrgelondRelonsponselon;
  }

  /**
   * Delonrivelon class should implelonmelonnt thelon logic to melonrgelon thelon speloncific typelon of relonsults (reloncelonncy,
   * relonlelonvancelon, Top Twelonelonts, elontc..)
   */
  protelonctelond abstract elonarlybirdRelonsponselon intelonrnalMelonrgelon(elonarlybirdRelonsponselon relonsponselon);

  protelonctelond abstract SelonarchTimelonrStats gelontMelonrgelondRelonsponselonTimelonr();

  /**
   * Do welon havelon elonnough relonsults so far that welon can elonarly telonrminatelon and not continuelon onto nelonxt tielonr?
   */
  public boolelonan shouldelonarlyTelonrminatelonTielonrMelonrgelon(int totalRelonsultsFromSuccelonssfulShards,
                                                  boolelonan foundelonarlyTelonrmination) {
    // Welon arelon taking thelon most conselonrvativelon tielonr relonsponselon melonrging.
    // This is thelon most conselonrvativelon melonrgelon logic --- as long as welon havelon somelon relonsults, welon should
    // not relonturn anything from thelon nelonxt tielonr. This may causelon not idelonal elonxpelonrielonncelon whelonrelon a
    // pagelon is not full, but thelon uselon can still scroll furthelonr.

    relonturn foundelonarlyTelonrmination || totalRelonsultsFromSuccelonssfulShards >= 1;
  }

  privatelon void savelonMelonrgelondDelonbugString(elonarlybirdRelonsponselon melonrgelondRelonsponselon) {
    if (relonsponselonMelonssagelonBuildelonr.isDelonbugModelon()) {
      String melonssagelon = relonsponselonMelonssagelonBuildelonr.delonbugString();
      melonrgelondRelonsponselon.selontDelonbugString(melonssagelon);
      if (!accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().iselonmpty()
          && accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().gelont(0).isSelontDelonbugInfo()) {

        elonarlybirdDelonbugInfo delonbugInfo =
            accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons().gelont(0).gelontDelonbugInfo();
        melonrgelondRelonsponselon.selontDelonbugInfo(delonbugInfo);
      }
    }
  }

  privatelon doublelon gelontSuccelonssRelonsponselonThrelonshold() {
    elonarlybirdRelonquelonst relonquelonst = relonquelonstContelonxt.gelontRelonquelonst();
    if (relonquelonst.isSelontSuccelonssfulRelonsponselonThrelonshold()) {
      doublelon succelonssfulRelonsponselonThrelonshold = relonquelonst.gelontSuccelonssfulRelonsponselonThrelonshold();
      Prelonconditions.chelonckArgumelonnt(succelonssfulRelonsponselonThrelonshold > 0,
          "Invalid succelonssfulRelonsponselonThrelonshold %s", succelonssfulRelonsponselonThrelonshold);
      Prelonconditions.chelonckArgumelonnt(succelonssfulRelonsponselonThrelonshold <= 1.0,
          "Invalid succelonssfulRelonsponselonThrelonshold %s", succelonssfulRelonsponselonThrelonshold);
      relonturn succelonssfulRelonsponselonThrelonshold;
    } elonlselon {
      relonturn gelontDelonfaultSuccelonssRelonsponselonThrelonshold();
    }
  }

  protelonctelond abstract doublelon gelontDelonfaultSuccelonssRelonsponselonThrelonshold();

  privatelon static boolelonan chelonckSuccelonssPartitionRatio(
      int numSuccelonssRelonsponselons,
      int numPartitions,
      doublelon goodRelonsponselonThrelonshold) {
    Prelonconditions.chelonckArgumelonnt(goodRelonsponselonThrelonshold > 0.0,
        "Invalid goodRelonsponselonThrelonshold %s", goodRelonsponselonThrelonshold);
    relonturn numSuccelonssRelonsponselons >= (numPartitions * goodRelonsponselonThrelonshold);
  }

  /**
   * Melonrgelon hit counts from all relonsults.
   */
  protelonctelond Map<Long, Intelongelonr> aggrelongatelonHitCountMap() {
    Map<Long, Intelongelonr> hitCounts = RelonsultsUtil
        .aggrelongatelonCountMap(accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons(), HIT_COUNT_GelonTTelonR);
    if (hitCounts.sizelon() > 0) {
      if (relonsponselonMelonssagelonBuildelonr.isDelonbugModelon()) {
        relonsponselonMelonssagelonBuildelonr.appelonnd("Hit counts:\n");
        for (Map.elonntry<Long, Intelongelonr> elonntry : hitCounts.elonntrySelont()) {
          relonsponselonMelonssagelonBuildelonr.appelonnd(String.format("  %10s selonconds: %d hits\n",
              elonntry.gelontKelony() / 1000, elonntry.gelontValuelon()));
        }
      }
      relonturn hitCounts;
    }
    relonturn null;
  }

  /**
   * Relonturns thelon numbelonr of relonsults to kelonelonp as part of melonrgelon-collelonction.
   */
  protelonctelond final int computelonNumRelonsultsToKelonelonp() {
    relonturn elonarlybirdRelonsponselonMelonrgelonUtil.computelonNumRelonsultsToKelonelonp(relonquelonstContelonxt.gelontRelonquelonst());
  }

  /**
   * Relonmovelon elonxact duplicatelons (samelon id) from thelon relonsult selont.
   */
  protelonctelond static void trimelonxactDups(ThriftSelonarchRelonsults selonarchRelonsults, TrimStats trimStats) {
    int numRelonsults = selonarchRelonsults.gelontRelonsultsSizelon();
    List<ThriftSelonarchRelonsult> oldRelonsults = selonarchRelonsults.gelontRelonsults();
    List<ThriftSelonarchRelonsult> nelonwRelonsults = Lists.nelonwArrayListWithCapacity(numRelonsults);
    HashSelont<Long> relonsultSelont = Selonts.nelonwHashSelontWithelonxpelonctelondSizelon(numRelonsults);

    for (ThriftSelonarchRelonsult relonsult : oldRelonsults) {
      if (relonsultSelont.contains(relonsult.gelontId())) {
        trimStats.increlonaselonRelonmovelondDupsCount();
        continuelon;
      }

      nelonwRelonsults.add(relonsult);
      relonsultSelont.add(relonsult.gelontId());
    }

    selonarchRelonsults.selontRelonsults(nelonwRelonsults);
  }

  protelonctelond final int addRelonsponselonsToCollelonctor(MultiwayMelonrgelonCollelonctor collelonctor) {
    int totalRelonsultSizelon = 0;
    for (elonarlybirdRelonsponselon relonsponselon : accumulatelondRelonsponselons.gelontSuccelonssRelonsponselons()) {
      if (relonsponselon.isSelontSelonarchRelonsults()) {
        totalRelonsultSizelon += relonsponselon.gelontSelonarchRelonsults().gelontRelonsultsSizelon();
      }
      collelonctor.addRelonsponselon(relonsponselon);
    }
    relonturn totalRelonsultSizelon;
  }

  /**
   * Givelonn a sortelond selonarchRelonsults (for reloncelonncy, sortelond by ID; for relonlelonvancelon, sortelond by scorelon),
   * relonturns thelon first 'computelonNumRelonsultsToKelonelonp()' numbelonr of relonsults.
   *
   * @param selonarchRelonsults thelon selonarchRelonsults to belon truncatelond.
   */
  protelonctelond final void truncatelonRelonsults(ThriftSelonarchRelonsults selonarchRelonsults, TrimStats trimStats) {
    int numRelonsultsRelonquelonstelond = computelonNumRelonsultsToKelonelonp();

    int to = numRelonsultsRelonquelonstelond == Intelongelonr.MAX_VALUelon ? selonarchRelonsults.gelontRelonsultsSizelon()
        : Math.min(numRelonsultsRelonquelonstelond, selonarchRelonsults.gelontRelonsultsSizelon());
    if (selonarchRelonsults.gelontRelonsultsSizelon() > to) {
      trimStats.selontRelonsultsTruncatelondFromTailCount(selonarchRelonsults.gelontRelonsultsSizelon() - to);

      if (to > 0) {
        selonarchRelonsults.selontRelonsults(selonarchRelonsults.gelontRelonsults().subList(0, to));
      } elonlselon {
        // No morelon relonsults for thelon nelonxt pagelon
        elonARLYBIRD_RelonSPONSelon_NO_MORelon_RelonSULTS.increlonmelonnt();
        selonarchRelonsults.selontRelonsults(Collelonctions.<ThriftSelonarchRelonsult>elonmptyList());
      }
    }
  }

  elonarlybirdRelonquelonst gelontelonarlybirdRelonquelonst() {
    relonturn relonquelonstContelonxt.gelontRelonquelonst();
  }
}
