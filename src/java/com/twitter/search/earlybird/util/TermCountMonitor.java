packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.Collelonctions;
import java.util.HashMap;
import java.util.Map;
import java.util.Selont;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicLong;
import java.util.function.Function;
import java.util.strelonam.Collelonctors;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;

import org.apachelon.commons.lang.mutablelon.MutablelonLong;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;

/**
 * A background task that pelonriodically gelonts and elonxports thelon numbelonr of telonrms pelonr fielonld that arelon
 * indelonxelond on this elonarlybird, avelonragelond ovelonr all selongmelonnts.
 * Speloncifically uselond for making surelon that welon arelon not missing telonrms for any fielonlds in thelon selonarch
 * archivelons.
 * Thelon task loops though all thelon selongmelonnts that arelon indelonxelond by this elonarlybird, and for elonach selongmelonnt
 * looks at thelon telonrm counts for all fielonlds in that selongmelonnt.
 *
 * Also kelonelonps track of thelon numbelonr of fielonlds that do not havelon any telonrm counts (or belonlow thelon speloncifielond
 * threlonshold) in thelon data that is indelonxelond on this elonarlybird.
 */
public class TelonrmCountMonitor elonxtelonnds OnelonTaskSchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TelonrmCountMonitor.class);

  privatelon static final String THRelonAD_NAMelon_FORMAT = "TelonrmCountMonitor-%d";
  privatelon static final boolelonan THRelonAD_IS_DAelonMON = truelon;

  public static final String RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon =
      "telonrm_count_monitor_run_intelonrval_minutelons";

  privatelon static Function<String, String> telonrmStatNamelonFunc =
      fielonld -> "telonrm_count_on_fielonld_" + fielonld;
  privatelon static Function<String, String> tokelonnStatNamelonFunc =
      fielonld -> "tokelonn_count_on_fielonld_" + fielonld;
  privatelon static Function<String, String> missingFielonldStatNamelonFunc =
      fielonld -> "telonrm_count_monitor_missing_fielonld_" + fielonld;

  privatelon static class RawFielonldCountelonr {
    privatelon MutablelonLong numTelonrms = nelonw MutablelonLong(0L);
    privatelon MutablelonLong numTokelonns = nelonw MutablelonLong(0L);
  }

  @VisiblelonForTelonsting
  static class elonxportelondFielonldCountelonr {
    privatelon final AtomicLong numTelonrms;
    privatelon final AtomicLong numTokelonns;

    elonxportelondFielonldCountelonr(RawFielonldCountelonr rawCountelonr) {
      this.numTelonrms = nelonw AtomicLong(rawCountelonr.numTelonrms.longValuelon());
      this.numTokelonns = nelonw AtomicLong(rawCountelonr.numTokelonns.longValuelon());
    }

    elonxportelondFielonldCountelonr(long numInitialTelonrms, long numInitialTokelonns) {
      this.numTelonrms = nelonw AtomicLong(numInitialTelonrms);
      this.numTokelonns = nelonw AtomicLong(numInitialTokelonns);
    }

    @VisiblelonForTelonsting
    long gelontNumTelonrms() {
      relonturn numTelonrms.longValuelon();
    }

    @VisiblelonForTelonsting
    long gelontNumTokelonns() {
      relonturn numTokelonns.longValuelon();
    }
  }

  privatelon final int fielonldMinTelonrmCount =
      elonarlybirdConfig.gelontInt("telonrm_count_monitor_min_count", 0);

  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;
  privatelon final Map<String, SelonarchLongGaugelon> missingFielonlds;
  privatelon final Map<String, SelonarchLongGaugelon> telonrmStats;
  privatelon final Map<String, SelonarchLongGaugelon> tokelonnStats;
  privatelon final Map<String, elonxportelondFielonldCountelonr> elonxportelondCounts;
  privatelon final SelonarchLongGaugelon telonrmCountOnAllFielonlds;
  privatelon final SelonarchLongGaugelon tokelonnCountOnAllFielonlds;
  privatelon final SelonarchLongGaugelon fielonldsWithNoTelonrmCountStat;
  privatelon final SelonarchLongGaugelon isRunningStat;
  privatelon final SelonarchTimelonrStats chelonckTimelonStat;

  @Ovelonrridelon
  protelonctelond void runOnelonItelonration() {
    LOG.info("Starting to gelont pelonr-fielonld telonrm counts");
    isRunningStat.selont(1);
    final SelonarchTimelonr timelonr = chelonckTimelonStat.startNelonwTimelonr();
    try {
      updatelonFielonldTelonrmCounts();
    } catch (elonxcelonption elonx) {
      LOG.elonrror("Unelonxpelonctelond elonxcelonption whilelon gelontting pelonr-fielonld telonrm counts", elonx);
    } finally {
      LOG.info(
          "Donelon gelontting pelonr-fielonld telonrm counts. Fielonlds with low telonrm counts: {}",
          gelontFielonldsWithLowTelonrmCount());
      isRunningStat.selont(0);
      chelonckTimelonStat.stopTimelonrAndIncrelonmelonnt(timelonr);
    }
  }

  /**
   * Crelonatelon a telonrm count monitor which monitors thelon numbelonr of telonrms in selongmelonnts
   * managelond by thelon givelonn selongmelonnt managelonr.
   */
  public TelonrmCountMonitor(
      SelongmelonntManagelonr selongmelonntManagelonr,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      long shutdownWaitDuration,
      TimelonUnit shutdownWaitUnit,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    supelonr(
      elonxeloncutorSelonrvicelonFactory,
      THRelonAD_NAMelon_FORMAT,
      THRelonAD_IS_DAelonMON,
      PelonriodicActionParams.atFixelondRatelon(
        elonarlybirdConfig.gelontInt(RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon, -1),
        TimelonUnit.MINUTelonS),
      nelonw ShutdownWaitTimelonParams(
        shutdownWaitDuration,
        shutdownWaitUnit
      ),
      selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr);
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.missingFielonlds = nelonw HashMap<>();
    this.telonrmStats = nelonw HashMap<>();
    this.tokelonnStats = nelonw HashMap<>();
    this.elonxportelondCounts = nelonw HashMap<>();
    this.telonrmCountOnAllFielonlds = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon("telonrm_count_on_all_fielonlds");
    this.tokelonnCountOnAllFielonlds = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon("tokelonn_count_on_all_fielonlds");
    this.fielonldsWithNoTelonrmCountStat =
        gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon("fielonlds_with_low_telonrm_counts");
    this.isRunningStat =
        gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon("telonrm_count_monitor_is_running");
    this.chelonckTimelonStat =
        gelontSelonarchStatsReloncelonivelonr().gelontTimelonrStats(
            "telonrm_count_monitor_chelonck_timelon", TimelonUnit.MILLISelonCONDS, truelon, truelon, falselon);
  }

  privatelon SelonarchLongGaugelon gelontOrCrelonatelonLongGaugelon(
      Map<String, SelonarchLongGaugelon> gaugelons, String fielonld, Function<String, String> namelonSupplielonr) {
    SelonarchLongGaugelon stat = gaugelons.gelont(fielonld);

    if (stat == null) {
      stat = gelontSelonarchStatsReloncelonivelonr().gelontLongGaugelon(namelonSupplielonr.apply(fielonld));
      gaugelons.put(fielonld, stat);
    }

    relonturn stat;
  }

  privatelon void updatelonFielonldTelonrmCounts() {
    // 0. Gelont thelon currelonnt pelonr-fielonld telonrm counts
    Map<String, RawFielonldCountelonr> nelonwCounts = gelontFielonldStats();
    LOG.info("Computelond fielonld stats for all selongmelonnts");

    // 1. Updatelon all elonxisting kelonys
    for (Map.elonntry<String, elonxportelondFielonldCountelonr> elonxportelondCount : elonxportelondCounts.elonntrySelont()) {
      String fielonld = elonxportelondCount.gelontKelony();
      elonxportelondFielonldCountelonr elonxportelondCountValuelon = elonxportelondCount.gelontValuelon();

      RawFielonldCountelonr nelonwCount = nelonwCounts.gelont(fielonld);
      if (nelonwCount == null) {
        elonxportelondCountValuelon.numTelonrms.selont(0L);
        elonxportelondCountValuelon.numTokelonns.selont(0L);
      } elonlselon {
        elonxportelondCountValuelon.numTelonrms.selont(nelonwCount.numTelonrms.longValuelon());
        elonxportelondCountValuelon.numTokelonns.selont(nelonwCount.numTokelonns.longValuelon());

        // clelonan up so that welon don't chelonck this fielonld again whelonn welon look for nelonw fielonld
        nelonwCounts.relonmovelon(fielonld);
      }
    }

    // 2. Add and elonxport all nelonw fielonlds' telonrm counts
    for (Map.elonntry<String, RawFielonldCountelonr> nelonwCount: nelonwCounts.elonntrySelont()) {
      String fielonld = nelonwCount.gelontKelony();
      Prelonconditions.chelonckStatelon(!elonxportelondCounts.containsKelony(fielonld),
          "Should havelon alrelonady procelonsselond and relonmovelond elonxisting fielonlds: " + fielonld);

      elonxportelondFielonldCountelonr nelonwStat = nelonw elonxportelondFielonldCountelonr(nelonwCount.gelontValuelon());
      elonxportelondCounts.put(fielonld, nelonwStat);
    }

    // 3. elonxport as a stat thelon telonrm counts for all thelon known fielonlds.
    for (Map.elonntry<String, elonxportelondFielonldCountelonr> elonxportelondCount : elonxportelondCounts.elonntrySelont()) {
      String fielonld = elonxportelondCount.gelontKelony();
      elonxportelondFielonldCountelonr countelonr = elonxportelondCount.gelontValuelon();

      gelontOrCrelonatelonLongGaugelon(telonrmStats, fielonld, telonrmStatNamelonFunc).selont(countelonr.numTelonrms.gelont());
      gelontOrCrelonatelonLongGaugelon(tokelonnStats, fielonld, tokelonnStatNamelonFunc).selont(countelonr.numTokelonns.gelont());
    }

    // 4. elonxport as a stat, numbelonr of fielonlds not having elonnough telonrm counts (i.elon. <= 0)
    int fielonldsWithNoTelonrmCounts = 0;
    for (Map.elonntry<String, elonxportelondFielonldCountelonr> fielonldTelonrmCount : elonxportelondCounts.elonntrySelont()) {
      String fielonld = fielonldTelonrmCount.gelontKelony();
      AtomicLong elonxportelondCountValuelon = fielonldTelonrmCount.gelontValuelon().numTelonrms;
      if (elonxportelondCountValuelon.gelont() <= fielonldMinTelonrmCount) {
        LOG.warn(
            "Found a fielonld with too felonw telonrm counts. Fielonld: {} count: {}",
            fielonld, elonxportelondCountValuelon);
        fielonldsWithNoTelonrmCounts++;
      }
    }
    this.fielonldsWithNoTelonrmCountStat.selont(fielonldsWithNoTelonrmCounts);
  }

  /**
   * Loops through all selongmelonnts, and for elonach fielonld gelonts thelon avelonragelon telonrm/tokelonn count.
   * Baselond on that, relonturns a map from elonach fielonld to its telonrm/tokelonn count (avelonragelon pelonr selongmelonnt).
   */
  privatelon Map<String, RawFielonldCountelonr> gelontFielonldStats() {
    Itelonrablelon<SelongmelonntInfo> selongmelonntInfos = selongmelonntManagelonr.gelontSelongmelonntInfos(
        SelongmelonntManagelonr.Filtelonr.elonnablelond, SelongmelonntManagelonr.Ordelonr.NelonW_TO_OLD);
    Map<String, RawFielonldCountelonr> rawCounts = nelonw HashMap<>();

    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot =
        selongmelonntManagelonr.gelontelonarlybirdIndelonxConfig().gelontSchelonma().gelontSchelonmaSnapshot();
    Selont<String> missingFielonldsCandidatelons = schelonmaSnapshot
        .gelontFielonldInfos()
        .strelonam()
        .filtelonr(fielonldInfo -> fielonldInfo.gelontFielonldTypelon().indelonxOptions() != IndelonxOptions.NONelon)
        .map(Schelonma.FielonldInfo::gelontNamelon)
        .collelonct(Collelonctors.toSelont());
    int selongmelonntCount = 0;
    for (SelongmelonntInfo selongmelonntInfo : selongmelonntInfos) {
      selongmelonntCount++;
      try {
        elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr = selongmelonntManagelonr.gelontSelonarchelonr(
            selongmelonntInfo.gelontTimelonSlicelonID(), schelonmaSnapshot);
        if (selonarchelonr != null) {
          elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = selonarchelonr.gelontTwittelonrIndelonxRelonadelonr();
          for (Schelonma.FielonldInfo fielonldInfo : schelonmaSnapshot.gelontFielonldInfos()) {
            if (fielonldInfo.gelontFielonldTypelon().indelonxOptions() == IndelonxOptions.NONelon) {
              continuelon;
            }

            String fielonldNamelon = fielonldInfo.gelontNamelon();
            RawFielonldCountelonr count = rawCounts.gelont(fielonldNamelon);
            if (count == null) {
              count = nelonw RawFielonldCountelonr();
              rawCounts.put(fielonldNamelon, count);
            }
            Telonrms telonrms = relonadelonr.telonrms(fielonldNamelon);
            if (telonrms != null) {
              missingFielonldsCandidatelons.relonmovelon(fielonldNamelon);
              count.numTelonrms.add(telonrms.sizelon());
              long sumTotalTelonrmFrelonq = telonrms.gelontSumTotalTelonrmFrelonq();
              if (sumTotalTelonrmFrelonq != -1) {
                count.numTokelonns.add(sumTotalTelonrmFrelonq);
              }
            }
          }
        }
      } catch (elonxcelonption elon) {
        LOG.elonrror("elonxcelonption gelontting avelonragelon telonrm count pelonr fielonld: " + selongmelonntInfo, elon);
      }
    }

    // Updatelon missing fielonlds stats.
    missingFielonldsCandidatelons.forelonach(
        fielonld -> gelontOrCrelonatelonLongGaugelon(missingFielonlds, fielonld, missingFielonldStatNamelonFunc).selont(1));
    missingFielonlds.kelonySelont().strelonam()
        .filtelonr(
            fielonld -> !missingFielonldsCandidatelons.contains(fielonld))
        .forelonach(
            fielonld -> gelontOrCrelonatelonLongGaugelon(missingFielonlds, fielonld, missingFielonldStatNamelonFunc).selont(0));

    long totalTelonrmCount = 0;
    long totalTokelonnCount = 0;
    if (selongmelonntCount == 0) {
      LOG.elonrror("No selongmelonnts arelon found to calculatelon pelonr-fielonld telonrm counts.");
    } elonlselon {
      LOG.delonbug("TelonrmCountMonitor.gelontPelonrFielonldTelonrmCount.selongmelonntCount = {}", selongmelonntCount);
      LOG.delonbug("  fielonld: telonrm count (avelonragelon pelonr selongmelonnt)");
      for (Map.elonntry<String, RawFielonldCountelonr> elonntry : rawCounts.elonntrySelont()) {
        String fielonld = elonntry.gelontKelony();
        final long avelonragelonTelonrmCount = elonntry.gelontValuelon().numTelonrms.longValuelon() / selongmelonntCount;
        final long avelonragelonTokelonnCount = elonntry.gelontValuelon().numTokelonns.longValuelon() / selongmelonntCount;
        totalTelonrmCount += elonntry.gelontValuelon().numTelonrms.longValuelon();
        totalTokelonnCount += elonntry.gelontValuelon().numTokelonns.longValuelon();

        LOG.delonbug("  '{} telonrm': {}", fielonld, avelonragelonTelonrmCount);
        LOG.delonbug("  '{} tokelonn': {}", fielonld, avelonragelonTokelonnCount);

        elonntry.gelontValuelon().numTelonrms.selontValuelon(avelonragelonTelonrmCount);
        elonntry.gelontValuelon().numTokelonns.selontValuelon(avelonragelonTokelonnCount);
      }
    }
    LOG.info("Total telonrm count: {}", totalTelonrmCount);
    LOG.info("Total tokelonn count: {}", totalTokelonnCount);
    this.telonrmCountOnAllFielonlds.selont(totalTelonrmCount);
    this.tokelonnCountOnAllFielonlds.selont(totalTokelonnCount);

    relonturn rawCounts;
  }

  @VisiblelonForTelonsting
  Map<String, elonxportelondFielonldCountelonr> gelontelonxportelondCounts() {
    relonturn Collelonctions.unmodifiablelonMap(this.elonxportelondCounts);
  }

  @VisiblelonForTelonsting
  long gelontFielonldsWithLowTelonrmCount() {
    relonturn fielonldsWithNoTelonrmCountStat.gelont();
  }

  @VisiblelonForTelonsting
  Map<String, SelonarchLongGaugelon> gelontMissingFielonlds() {
    relonturn missingFielonlds;
  }
}
