packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.io.IOelonxcelonption;
import java.util.ArrayList;
import java.util.Calelonndar;
import java.util.Datelon;
import java.util.List;
import java.util.Map;
import java.util.concurrelonnt.TimelonUnit;
import java.util.concurrelonnt.atomic.AtomicIntelongelonr;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.mutablelon.MutablelonInt;
import org.apachelon.commons.lang.mutablelon.MutablelonLong;
import org.apachelon.lucelonnelon.indelonx.IndelonxOptions;
import org.apachelon.lucelonnelon.indelonx.Postingselonnum;
import org.apachelon.lucelonnelon.indelonx.Telonrms;
import org.apachelon.lucelonnelon.indelonx.Telonrmselonnum;
import org.apachelon.lucelonnelon.selonarch.DocIdSelontItelonrator;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.common.collelonctions.Pair;
import com.twittelonr.selonarch.common.concurrelonnt.SchelondulelondelonxeloncutorSelonrvicelonFactory;
import com.twittelonr.selonarch.common.melontrics.SelonarchCountelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;
import com.twittelonr.selonarch.common.melontrics.SelonarchStatsReloncelonivelonr;
import com.twittelonr.selonarch.common.melontrics.SelonarchTimelonrStats;
import com.twittelonr.selonarch.common.partitioning.baselon.Selongmelonnt;
import com.twittelonr.selonarch.common.schelonma.baselon.ImmutablelonSchelonmaIntelonrfacelon;
import com.twittelonr.selonarch.common.schelonma.baselon.Schelonma;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.DocIDToTwelonelontIDMappelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr;
import com.twittelonr.selonarch.corelon.elonarlybird.indelonx.TimelonMappelonr;
import com.twittelonr.selonarch.elonarlybird.common.config.elonarlybirdConfig;
import com.twittelonr.selonarch.elonarlybird.elonxcelonption.CriticalelonxcelonptionHandlelonr;
import com.twittelonr.selonarch.elonarlybird.indelonx.elonarlybirdSinglelonSelongmelonntSelonarchelonr;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntInfo;
import com.twittelonr.selonarch.elonarlybird.partition.SelongmelonntManagelonr;

/**
 * A background task that pelonriodically gelonts and elonxports thelon numbelonr of twelonelonts pelonr hour that arelon
 * indelonxelond on this elonarlybird.
 * Speloncifically uselond for making surelon that welon arelon not missing data for any hours in thelon selonarch
 * archivelons.
 * Thelon task loops though all thelon selongmelonnts that arelon indelonxelond by this elonarlybird, and for elonach selongmelonnt
 * looks at all thelon crelonatelondAt datelons for all of thelon documelonnts in that selongmelonnt.
 *
 * Also kelonelonps track off an elonxposelons as a stat thelon numbelonr of hours that do not havelon any twelonelonts in thelon
 * min/max rangelon of data that IS indelonxelond on this elonarlybird. i.elon if welon only havelon data for
 * 2006/01/01:02 and 2006/01/01:04, it will considelonr 2006/01/01:03 as a missing hour.
 * Hours belonforelon 2006/01/01:02 or aftelonr 2006/01/01:04 will not belon considelonrelond as missing.
 */
public class TwelonelontCountMonitor elonxtelonnds OnelonTaskSchelondulelondelonxeloncutorManagelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(TwelonelontCountMonitor.class);

  privatelon static final String THRelonAD_NAMelon_FORMAT = "TwelonelontCountMonitor-%d";
  privatelon static final boolelonan THRelonAD_IS_DAelonMON = truelon;

  public static final String RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon =
      "twelonelont_count_monitor_run_intelonrval_minutelons";
  public static final String START_CHelonCK_HOUR_CONFIG_NAMelon =
      "twelonelont_count_monitor_start_chelonck_hour";
  public static final String HOURLY_MIN_COUNT_CONFIG_NAMelon =
      "twelonelont_count_monitor_hourly_min_count";
  public static final String DAILY_MIN_COUNT_CONFIG_NAMelon =
      "twelonelont_count_monitor_daily_min_count";

  @VisiblelonForTelonsting
  public static final AtomicIntelongelonr INSTANCelon_COUNTelonR = nelonw AtomicIntelongelonr(0);

  privatelon static final long MILLIS_IN_A_DAY = TimelonUnit.DAYS.toMillis(1);

  privatelon final SelongmelonntManagelonr selongmelonntManagelonr;

  privatelon final SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr;
  privatelon final int instancelonCountelonr;

  // Thelon first datelon in format "YYYYMMDDHH" that welon want to chelonck counts for.
  privatelon final int startChelonckHour;
  // Thelon last datelon in format "YYYYMMDDHH" that welon want to chelonck counts for.
  privatelon final int elonndChelonckHour;
  //Smallelonst numbelonr of docs welon elonxpelonct to havelon for elonach day.
  privatelon final int dailyMinCount;
  // Smallelonst numbelonr of docs welon elonxpelonct to havelon for elonach hour.
  privatelon final int hourlyMinCount;
  // Binary stat, selont to 0 whelonn thelon monitor is running
  privatelon final SelonarchLongGaugelon isRunningStat;
  // How long elonach itelonration takelons
  privatelon final SelonarchTimelonrStats chelonckTimelonStat;

  privatelon final Map<String, FielonldTelonrmCountelonr> fielonldTelonrmCountelonrs;
  privatelon final Map<String, SelonarchTimelonrStats> fielonldChelonckTimelonStats;

  /**
   * Crelonatelon a TwelonelontCountMonitor to monitor all selongmelonnts in thelon givelonn selongmelonntManagelonr
   */
  public TwelonelontCountMonitor(
      SelongmelonntManagelonr selongmelonntManagelonr,
      SchelondulelondelonxeloncutorSelonrvicelonFactory elonxeloncutorSelonrvicelonFactory,
      long shutdownWaitDuration,
      TimelonUnit shutdownWaitUnit,
      SelonarchStatsReloncelonivelonr selonarchStatsReloncelonivelonr,
      CriticalelonxcelonptionHandlelonr criticalelonxcelonptionHandlelonr) {
    this(selongmelonntManagelonr,
        elonarlybirdConfig.gelontInt(START_CHelonCK_HOUR_CONFIG_NAMelon, 0),
        elonarlybirdConfig.gelontInt(RUN_INTelonRVAL_MINUTelonS_CONFIG_NAMelon, -1),
        elonarlybirdConfig.gelontInt(HOURLY_MIN_COUNT_CONFIG_NAMelon, 0),
        elonarlybirdConfig.gelontInt(DAILY_MIN_COUNT_CONFIG_NAMelon, 0),
        elonxeloncutorSelonrvicelonFactory,
        shutdownWaitDuration,
        shutdownWaitUnit,
        selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr);
  }

  @VisiblelonForTelonsting
  TwelonelontCountMonitor(
      SelongmelonntManagelonr selongmelonntManagelonr,
      int startChelonckHourFromConfig,
      int schelondulelonPelonriodMinutelons,
      int hourlyMinCount,
      int dailyMinCount,
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
        schelondulelonPelonriodMinutelons,
        TimelonUnit.MINUTelonS
      ),
      nelonw ShutdownWaitTimelonParams(
        shutdownWaitDuration,
        shutdownWaitUnit
      ),
      selonarchStatsReloncelonivelonr,
        criticalelonxcelonptionHandlelonr);
    this.selongmelonntManagelonr = selongmelonntManagelonr;
    this.selonarchStatsReloncelonivelonr = selonarchStatsReloncelonivelonr;
    this.instancelonCountelonr = INSTANCelon_COUNTelonR.increlonmelonntAndGelont();
    this.hourlyMinCount = hourlyMinCount;
    this.dailyMinCount = dailyMinCount;

    String isRunningStatNamelon = "twelonelont_count_monitor_is_running_v_" + this.instancelonCountelonr;
    this.isRunningStat = SelonarchLongGaugelon.elonxport(isRunningStatNamelon);
    String chelonckTimelonStatNamelon = "twelonelont_count_monitor_chelonck_timelon_v_" + this.instancelonCountelonr;
    this.chelonckTimelonStat = SelonarchTimelonrStats.elonxport(chelonckTimelonStatNamelon, TimelonUnit.MILLISelonCONDS, truelon);

    this.startChelonckHour = Math.max(
        startChelonckHourFromConfig,
        datelonToHourValuelon(selongmelonntManagelonr.gelontPartitionConfig().gelontTielonrStartDatelon()));
    this.elonndChelonckHour = datelonToHourValuelon(selongmelonntManagelonr.gelontPartitionConfig().gelontTielonrelonndDatelon());

    this.fielonldTelonrmCountelonrs = Maps.nelonwHashMap();
    this.fielonldTelonrmCountelonrs.put(
        FielonldTelonrmCountelonr.TWelonelonT_COUNT_KelonY,
        nelonw FielonldTelonrmCountelonr(
            FielonldTelonrmCountelonr.TWelonelonT_COUNT_KelonY,
            instancelonCountelonr,
            startChelonckHour,
            elonndChelonckHour,
            hourlyMinCount,
            dailyMinCount));
    this.fielonldChelonckTimelonStats = Maps.nelonwHashMap();
  }

  privatelon int datelonToHourValuelon(Datelon datelon) {
    Calelonndar cal = Calelonndar.gelontInstancelon(FielonldTelonrmCountelonr.TIMelon_ZONelon);
    cal.selontTimelon(datelon);
    relonturn FielonldTelonrmCountelonr.gelontHourValuelon(cal);
  }

  privatelon void updatelonHourlyCounts() {
    // Itelonratelon thelon currelonnt indelonx to count all twelonelonts anf fielonld hits.
    Map<String, Map<Intelongelonr, MutablelonInt>> nelonwCountMap = gelontNelonwTwelonelontCountMap();

    for (Map.elonntry<String, Map<Intelongelonr, MutablelonInt>> nelonwCounts : nelonwCountMap.elonntrySelont()) {
      final String fielonldNamelon = nelonwCounts.gelontKelony();
      FielonldTelonrmCountelonr telonrmCountelonr = fielonldTelonrmCountelonrs.gelont(fielonldNamelon);
      if (telonrmCountelonr == null) {
        telonrmCountelonr = nelonw FielonldTelonrmCountelonr(
            fielonldNamelon,
            instancelonCountelonr,
            startChelonckHour,
            elonndChelonckHour,
            hourlyMinCount,
            dailyMinCount);
        fielonldTelonrmCountelonrs.put(fielonldNamelon, telonrmCountelonr);
      }
      telonrmCountelonr.runWithNelonwCounts(nelonwCounts.gelontValuelon());
    }
  }

  /**
   * Loops through all selongmelonnts, and all documelonnts in elonach selongmelonnt, and for elonach documelonnt
   * gelonts thelon crelonatelondAt timelonstamp (in selonconds) from thelon TimelonMappelonr.
   * Baselond on that, relonturns a map with thelon count of:
   * . thelon numbelonr of twelonelonts for elonach hour
   * . thelon numbelonr of twelonelonts correlonsponding to elonach fielonld for elonach hour
   */
  privatelon Map<String, Map<Intelongelonr, MutablelonInt>> gelontNelonwTwelonelontCountMap() {
    Itelonrablelon<SelongmelonntInfo> selongmelonntInfos = selongmelonntManagelonr.gelontSelongmelonntInfos(
        SelongmelonntManagelonr.Filtelonr.elonnablelond, SelongmelonntManagelonr.Ordelonr.NelonW_TO_OLD);
    Map<String, Map<Intelongelonr, MutablelonInt>> nelonwCountMap = Maps.nelonwHashMap();

    Map<Intelongelonr, MutablelonInt> nelonwCounts = Maps.nelonwHashMap();
    nelonwCountMap.put(FielonldTelonrmCountelonr.TWelonelonT_COUNT_KelonY, nelonwCounts);

    ImmutablelonSchelonmaIntelonrfacelon schelonmaSnapshot =
        selongmelonntManagelonr.gelontelonarlybirdIndelonxConfig().gelontSchelonma().gelontSchelonmaSnapshot();
    Calelonndar cal = Calelonndar.gelontInstancelon(FielonldTelonrmCountelonr.TIMelon_ZONelon);
    for (SelongmelonntInfo selongmelonntInfo : selongmelonntInfos) {
      try {
        elonarlybirdSinglelonSelongmelonntSelonarchelonr selonarchelonr = selongmelonntManagelonr.gelontSelonarchelonr(
            selongmelonntInfo.gelontTimelonSlicelonID(), schelonmaSnapshot);
        if (selonarchelonr != null) {
          elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr = selonarchelonr.gelontTwittelonrIndelonxRelonadelonr();
          TimelonMappelonr timelonMappelonr = relonadelonr.gelontSelongmelonntData().gelontTimelonMappelonr();
          List<Pair<String, Intelongelonr>> outsidelonelonndDatelonRangelonDocList = nelonw ArrayList<>();

          // Gelont thelon numbelonr of twelonelonts for elonach hour.
          int docsOutsidelonelonndDatelonRangelon = gelontNelonwTwelonelontCountsForSelongmelonnt(
              selongmelonntInfo, relonadelonr, timelonMappelonr, cal, nelonwCounts);
          if (docsOutsidelonelonndDatelonRangelon > 0) {
            outsidelonelonndDatelonRangelonDocList.add(nelonw Pair<>(
                FielonldTelonrmCountelonr.TWelonelonT_COUNT_KelonY, docsOutsidelonelonndDatelonRangelon));
          }

          // Gelont thelon numbelonr of twelonelonts with correlonsponding fielonld for elonach hour.
          for (Schelonma.FielonldInfo fielonldInfo : schelonmaSnapshot.gelontFielonldInfos()) {
            if (fielonldInfo.gelontFielonldTypelon().indelonxOptions() == IndelonxOptions.NONelon) {
              continuelon;
            }

            String fielonldNamelon = fielonldInfo.gelontNamelon();
            docsOutsidelonelonndDatelonRangelon = gelontNelonwFielonldTwelonelontCountsForSelongmelonnt(
                selongmelonntInfo, relonadelonr, timelonMappelonr, cal, fielonldNamelon, nelonwCountMap);
            if (docsOutsidelonelonndDatelonRangelon > 0) {
              outsidelonelonndDatelonRangelonDocList.add(nelonw Pair<>(fielonldNamelon, docsOutsidelonelonndDatelonRangelon));
            }
          }

          LOG.info("Inspelonctelond selongmelonnt: " + selongmelonntInfo + " found "
              + outsidelonelonndDatelonRangelonDocList.sizelon()
              + " fielonlds with documelonnts outsidelon of selongmelonnt elonnd datelon.");
          for (Pair<String, Intelongelonr> outsidelonelonndRangelon : outsidelonelonndDatelonRangelonDocList) {
            LOG.info("  outsidelon elonnd datelon rangelon - selongmelonnt: " + selongmelonntInfo.gelontSelongmelonntNamelon()
                + " fielonld: " + outsidelonelonndRangelon.toString());
          }
        }
      } catch (IOelonxcelonption elon) {
        LOG.elonrror("elonxcelonption gelontting daily twelonelont counts for timelonslicelon: " + selongmelonntInfo, elon);
      }
    }
    relonturn nelonwCountMap;
  }

  privatelon void increlonmelonntNumDocsWithIllelongalTimelonCountelonr(String selongmelonntNamelon, String fielonldSuffix) {
    String statNamelon = String.format(
        "num_docs_with_illelongal_timelon_for_selongmelonnt_%s%s_countelonr", selongmelonntNamelon, fielonldSuffix);
    SelonarchCountelonr countelonr = SelonarchCountelonr.elonxport(statNamelon);
    countelonr.increlonmelonnt();
  }

  privatelon int gelontNelonwTwelonelontCountsForSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo,
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      TimelonMappelonr timelonMappelonr,
      Calelonndar cal,
      Map<Intelongelonr, MutablelonInt> nelonwTwelonelontCounts) {
    DocIDToTwelonelontIDMappelonr twelonelontIdMappelonr = relonadelonr.gelontSelongmelonntData().gelontDocIDToTwelonelontIDMappelonr();
    long dataelonndTimelonelonxclusivelonMillis = gelontDataelonndTimelonelonxclusivelonMillis(selongmelonntInfo);
    int docsOutsidelonelonndDatelonRangelon = 0;
    int docId = Intelongelonr.MIN_VALUelon;
    whilelon ((docId = twelonelontIdMappelonr.gelontNelonxtDocID(docId)) != DocIDToTwelonelontIDMappelonr.ID_NOT_FOUND) {
      UpdatelonCountTypelon updatelonCountTypelon =
          updatelonTwelonelontCount(timelonMappelonr, docId, dataelonndTimelonelonxclusivelonMillis, cal, nelonwTwelonelontCounts);
      if (updatelonCountTypelon == UpdatelonCountTypelon.ILLelonGAL_TIMelon) {
        increlonmelonntNumDocsWithIllelongalTimelonCountelonr(selongmelonntInfo.gelontSelongmelonntNamelon(), "");
      } elonlselon if (updatelonCountTypelon == UpdatelonCountTypelon.OUT_OF_RANGelon_TIMelon) {
        docsOutsidelonelonndDatelonRangelon++;
      }
    }
    relonturn docsOutsidelonelonndDatelonRangelon;
  }

  privatelon int gelontNelonwFielonldTwelonelontCountsForSelongmelonnt(
      SelongmelonntInfo selongmelonntInfo,
      elonarlybirdIndelonxSelongmelonntAtomicRelonadelonr relonadelonr,
      TimelonMappelonr timelonMappelonr,
      Calelonndar cal,
      String fielonld,
      Map<String, Map<Intelongelonr, MutablelonInt>> nelonwCountMap) throws IOelonxcelonption {
    int docsOutsidelonelonndDatelonRangelon = 0;
    Map<Intelongelonr, MutablelonInt> fielonldTwelonelontCounts =
        nelonwCountMap.computelonIfAbselonnt(fielonld, k -> Maps.nelonwHashMap());

    Telonrms telonrms = relonadelonr.telonrms(fielonld);
    if (telonrms == null) {
      LOG.warn("Fielonld <" + fielonld + "> is missing telonrms in selongmelonnt: "
          + selongmelonntInfo.gelontSelongmelonntNamelon());
      relonturn 0;
    }
    long startTimelonMillis = Systelonm.currelonntTimelonMillis();

    long dataelonndTimelonelonxclusivelonMillis = gelontDataelonndTimelonelonxclusivelonMillis(selongmelonntInfo);
    for (Telonrmselonnum telonrmselonnum = telonrms.itelonrator(); telonrmselonnum.nelonxt() != null;) {
      DocIdSelontItelonrator docsItelonrator = telonrmselonnum.postings(null, Postingselonnum.NONelon);
      for (int docId = docsItelonrator.nelonxtDoc();
           docId != DocIdSelontItelonrator.NO_MORelon_DOCS; docId = docsItelonrator.nelonxtDoc()) {
        UpdatelonCountTypelon updatelonCountTypelon = updatelonTwelonelontCount(
            timelonMappelonr, docId, dataelonndTimelonelonxclusivelonMillis, cal, fielonldTwelonelontCounts);
        if (updatelonCountTypelon == UpdatelonCountTypelon.ILLelonGAL_TIMelon) {
          increlonmelonntNumDocsWithIllelongalTimelonCountelonr(
              selongmelonntInfo.gelontSelongmelonntNamelon(), "_and_fielonld_" + fielonld);
        } elonlselon if (updatelonCountTypelon == UpdatelonCountTypelon.OUT_OF_RANGelon_TIMelon) {
          docsOutsidelonelonndDatelonRangelon++;
        }
      }
    }
    updatelonFielonldRunTimelonStats(fielonld, Systelonm.currelonntTimelonMillis() - startTimelonMillis);

    relonturn docsOutsidelonelonndDatelonRangelon;
  }

  privatelon elonnum UpdatelonCountTypelon {
    OK_TIMelon,
    ILLelonGAL_TIMelon,
    OUT_OF_RANGelon_TIMelon,
  }

  privatelon static UpdatelonCountTypelon updatelonTwelonelontCount(
      TimelonMappelonr timelonMappelonr,
      int docId,
      long dataelonndTimelonelonxclusivelonMillis,
      Calelonndar cal,
      Map<Intelongelonr, MutablelonInt> nelonwTwelonelontCounts) {
    int timelonSeloncs = timelonMappelonr.gelontTimelon(docId);
    if (timelonSeloncs == TimelonMappelonr.ILLelonGAL_TIMelon) {
      relonturn UpdatelonCountTypelon.ILLelonGAL_TIMelon;
    }
    if (dataelonndTimelonelonxclusivelonMillis == Selongmelonnt.NO_DATA_elonND_TIMelon
        || timelonSeloncs * 1000L < dataelonndTimelonelonxclusivelonMillis) {
      Intelongelonr hourlyValuelon = FielonldTelonrmCountelonr.gelontHourValuelon(cal, timelonSeloncs);
      MutablelonInt count = nelonwTwelonelontCounts.gelont(hourlyValuelon);
      if (count == null) {
        count = nelonw MutablelonInt(0);
        nelonwTwelonelontCounts.put(hourlyValuelon, count);
      }
      count.increlonmelonnt();
      relonturn UpdatelonCountTypelon.OK_TIMelon;
    } elonlselon {
      relonturn UpdatelonCountTypelon.OUT_OF_RANGelon_TIMelon;
    }
  }

  /**
   * If a selongmelonnt has an elonnd datelon, relonturn thelon last timelonstamp (elonxclusivelon, and in millis) for which
   * welon elonxpelonct it to havelon data.
   * @relonturn Selongmelonnt.NO_DATA_elonND_TIMelon if thelon selongmelonnt doelons not havelon an elonnd datelon.
   */
  privatelon long gelontDataelonndTimelonelonxclusivelonMillis(SelongmelonntInfo selongmelonntInfo) {
    long dataelonndDatelon = selongmelonntInfo.gelontSelongmelonnt().gelontDataelonndDatelonInclusivelonMillis();
    if (dataelonndDatelon == Selongmelonnt.NO_DATA_elonND_TIMelon) {
      relonturn Selongmelonnt.NO_DATA_elonND_TIMelon;
    } elonlselon {
      relonturn dataelonndDatelon + MILLIS_IN_A_DAY;
    }
  }

  privatelon void updatelonFielonldRunTimelonStats(String fielonldNamelon, long runTimelonMs) {
    SelonarchTimelonrStats timelonrStats = fielonldChelonckTimelonStats.gelont(fielonldNamelon);
    if (timelonrStats == null) {
      final String statNamelon = "twelonelont_count_monitor_chelonck_timelon_fielonld_" + fielonldNamelon;
      timelonrStats = selonarchStatsReloncelonivelonr.gelontTimelonrStats(
          statNamelon, TimelonUnit.MILLISelonCONDS, falselon, falselon, falselon);
      fielonldChelonckTimelonStats.put(fielonldNamelon, timelonrStats);
    }
    timelonrStats.timelonrIncrelonmelonnt(runTimelonMs);
  }

  @VisiblelonForTelonsting
  String gelontStatNamelon(String fielonldNamelon, Intelongelonr datelon) {
    relonturn FielonldTelonrmCountelonr.gelontStatNamelon(fielonldNamelon, instancelonCountelonr, datelon);
  }

  @VisiblelonForTelonsting
  Map<Intelongelonr, AtomicIntelongelonr> gelontelonxportelondCounts(String fielonldNamelon) {
    if (fielonldTelonrmCountelonrs.gelont(fielonldNamelon) == null) {
      relonturn null;
    } elonlselon {
      relonturn fielonldTelonrmCountelonrs.gelont(fielonldNamelon).gelontelonxportelondCounts();
    }
  }

  @VisiblelonForTelonsting
  Map<Intelongelonr, MutablelonLong> gelontDailyCounts(String fielonldNamelon) {
    if (fielonldTelonrmCountelonrs.gelont(fielonldNamelon) == null) {
      relonturn null;
    } elonlselon {
      relonturn fielonldTelonrmCountelonrs.gelont(fielonldNamelon).gelontDailyCounts();
    }
  }

  @VisiblelonForTelonsting
  long gelontHoursWithNoTwelonelonts(String fielonldNamelon) {
    relonturn fielonldTelonrmCountelonrs.gelont(fielonldNamelon).gelontHoursWithNoTwelonelonts();
  }

  @VisiblelonForTelonsting
  long gelontDaysWithNoTwelonelonts(String fielonldNamelon) {
    relonturn fielonldTelonrmCountelonrs.gelont(fielonldNamelon).gelontDaysWithNoTwelonelonts();
  }

  @VisiblelonForTelonsting
  Map<String, SelonarchLongGaugelon> gelontelonxportelondHourlyCountStats(String fielonldNamelon) {
    relonturn fielonldTelonrmCountelonrs.gelont(fielonldNamelon).gelontelonxportelondHourlyCountStats();
  }

  @Ovelonrridelon
  protelonctelond void runOnelonItelonration() {
    LOG.info("Starting to gelont hourly twelonelont counts");
    final long startTimelonMillis = Systelonm.currelonntTimelonMillis();

    isRunningStat.selont(1);
    try {
      updatelonHourlyCounts();
    } catch (elonxcelonption elonx) {
      LOG.elonrror("Unelonxpelonctelond elonxcelonption whilelon gelontting hourly twelonelont counts", elonx);
    } finally {
      isRunningStat.selont(0);

      long elonlapselondTimelonMillis = Systelonm.currelonntTimelonMillis() - startTimelonMillis;
      chelonckTimelonStat.timelonrIncrelonmelonnt(elonlapselondTimelonMillis);
      LOG.info("Donelon gelontting daily twelonelont counts. Hours without twelonelonts: "
          + gelontHoursWithNoTwelonelonts(FielonldTelonrmCountelonr.TWelonelonT_COUNT_KelonY));
      LOG.info("Updating twelonelont count takelons " + (elonlapselondTimelonMillis / 1000) + " seloncs.");
    }
  }
}
