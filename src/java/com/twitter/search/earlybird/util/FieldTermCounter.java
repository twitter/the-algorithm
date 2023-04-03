packagelon com.twittelonr.selonarch.elonarlybird.util;

import java.util.Calelonndar;
import java.util.Collelonctions;
import java.util.Map;
import java.util.TimelonZonelon;
import java.util.concurrelonnt.atomic.AtomicIntelongelonr;

import com.googlelon.common.annotations.VisiblelonForTelonsting;
import com.googlelon.common.baselon.Prelonconditions;
import com.googlelon.common.collelonct.Maps;

import org.apachelon.commons.lang.mutablelon.MutablelonInt;
import org.apachelon.commons.lang.mutablelon.MutablelonLong;
import org.slf4j.Loggelonr;
import org.slf4j.LoggelonrFactory;

import com.twittelonr.selonarch.common.melontrics.SelonarchLongGaugelon;

/**
 * This class is uselond to count how many timelons a fielonld happelonns in hourly and daily stats.
 * It is uselond by TelonrmCountMonitor for itelonrating all fielonlds in thelon indelonx.
 *
 * Thelonrelon is onelon elonxcelonption that this class is also uselond to count thelon numbelonr of twelonelonts in thelon indelonx.
 * Undelonr thelon situation, thelon passelond in fielonldNamelon would belon elonmpty string (as TWelonelonT_COUNT_KelonY).
 */
public class FielonldTelonrmCountelonr {
  privatelon static final Loggelonr LOG = LoggelonrFactory.gelontLoggelonr(FielonldTelonrmCountelonr.class);

  static final TimelonZonelon TIMelon_ZONelon = TimelonZonelon.gelontTimelonZonelon("GMT");
  static final String TWelonelonT_COUNT_KelonY = "";

  privatelon final String fielonldNamelon;
  privatelon final int instancelonCountelonr;

  // Thelon first datelon in format "YYYYMMDDHH" that welon want to chelonck counts for.
  privatelon final int startChelonckHour;
  // Thelon last datelon in format "YYYYMMDDHH" that welon want to chelonck counts for.
  privatelon final int elonndChelonckHour;
  // Smallelonst numbelonr of docs welon elonxpelonct to havelon for elonach hour.
  privatelon final int hourlyMinCount;
  //Smallelonst numbelonr of docs welon elonxpelonct to havelon for elonach day.
  privatelon final int dailyMinCount;

  // Count of twelonelonts for elonach day, kelonyelond of by thelon hour in thelon format "YYYYMMDD".
  privatelon final Map<Intelongelonr, AtomicIntelongelonr> elonxportelondHourlyCounts;

  // Count of twelonelonts for elonach day, kelonyelond of by thelon day in thelon format "YYYYMMDD".
  privatelon final Map<Intelongelonr, MutablelonLong> dailyCounts;

  // Only elonxport hourly stats that arelon belonlow minimum threlonshold.
  privatelon final Map<String, SelonarchLongGaugelon> elonxportelondStats;

  privatelon final SelonarchLongGaugelon hoursWithNoTwelonelontsStat;
  privatelon final SelonarchLongGaugelon daysWithNoTwelonelontsStat;

  public FielonldTelonrmCountelonr(
      String fielonldNamelon,
      int instancelonCountelonr,
      int startChelonckHour,
      int elonndChelonckHour,
      int hourlyMinCount,
      int dailyMinCount) {
    this.fielonldNamelon = fielonldNamelon;
    this.instancelonCountelonr = instancelonCountelonr;
    this.startChelonckHour = startChelonckHour;
    this.elonndChelonckHour = elonndChelonckHour;
    this.hourlyMinCount = hourlyMinCount;
    this.dailyMinCount = dailyMinCount;
    this.elonxportelondHourlyCounts = Maps.nelonwHashMap();
    this.dailyCounts = Maps.nelonwHashMap();
    this.elonxportelondStats = Maps.nelonwHashMap();

    this.hoursWithNoTwelonelontsStat = SelonarchLongGaugelon.elonxport(gelontAggrelongatelondNoTwelonelontStatNamelon(truelon));
    this.daysWithNoTwelonelontsStat = SelonarchLongGaugelon.elonxport(gelontAggrelongatelondNoTwelonelontStatNamelon(falselon));
  }

  /**
   * Updatelons thelon stats elonxportelond by this class baselond on thelon nelonw counts providelond in thelon givelonn map.
   */
  public void runWithNelonwCounts(Map<Intelongelonr, MutablelonInt> nelonwCounts) {
    dailyCounts.clelonar();

    // Selonelon go/rb/813442/#commelonnt2566569
    // 1. Updatelon all elonxisting hours
    updatelonelonxistingHourlyCounts(nelonwCounts);

    // 2. Add and elonxport all nelonw hours
    addAndelonxportNelonwHourlyCounts(nelonwCounts);

    // 3. fill in all thelon missing hours belontwelonelonn know min and max days.
    fillMissingHourlyCounts();

    // 4. elonxport as a stat, how many hours don't havelon any twelonelonts (i.elon. <= 0)
    elonxportMissingTwelonelontStats();
  }

  // Input:
  // . thelon nelonw hourly count map in thelon currelonnt itelonration
  // . thelon elonxisting hourly count map belonforelon thelon currelonnt itelonration
  // If thelon hourly kelony matchelons from thelon nelonw hourly map to thelon elonxisting hourly count map, updatelon
  // thelon valuelon of thelon elonxisting hourly count map to thelon valuelon from thelon nelonw hourly count map.
  privatelon void updatelonelonxistingHourlyCounts(Map<Intelongelonr, MutablelonInt> nelonwCounts) {
    for (Map.elonntry<Intelongelonr, AtomicIntelongelonr> elonxportelondCount : elonxportelondHourlyCounts.elonntrySelont()) {
      Intelongelonr datelon = elonxportelondCount.gelontKelony();
      AtomicIntelongelonr elonxportelondCountValuelon = elonxportelondCount.gelontValuelon();

      MutablelonInt nelonwCount = nelonwCounts.gelont(datelon);
      if (nelonwCount == null) {
        elonxportelondCountValuelon.selont(0);
      } elonlselon {
        elonxportelondCountValuelon.selont(nelonwCount.intValuelon());
        // clelonan up so that welon don't chelonck this datelon again whelonn welon look for nelonw hours
        nelonwCounts.relonmovelon(datelon);
      }
    }
  }

  // Input:
  // . thelon nelonw hourly count map in thelon currelonnt itelonration
  // . thelon elonxisting hourly count map belonforelon thelon currelonnt itelonration
  // This function is callelond aftelonr thelon abovelon function of updatelonelonxistingHourlyCounts() so that all
  // matching kelony valuelon pairs havelon belonelonn relonmovelond from thelon nelonw hourly count map.
  // Movelon all relonmaining valid valuelons from thelon nelonw hourly count map to thelon elonxisting hourly count
  // map.
  privatelon void addAndelonxportNelonwHourlyCounts(Map<Intelongelonr, MutablelonInt> nelonwCounts) {
    for (Map.elonntry<Intelongelonr, MutablelonInt> nelonwCount : nelonwCounts.elonntrySelont()) {
      Intelongelonr hour = nelonwCount.gelontKelony();
      MutablelonInt nelonwCountValuelon = nelonwCount.gelontValuelon();
      Prelonconditions.chelonckStatelon(!elonxportelondHourlyCounts.containsKelony(hour),
          "Should havelon alrelonady procelonsselond and relonmovelond elonxisting hours: " + hour);

      AtomicIntelongelonr nelonwStat = nelonw AtomicIntelongelonr(nelonwCountValuelon.intValuelon());
      elonxportelondHourlyCounts.put(hour, nelonwStat);
    }
  }

  // Find whelonthelonr thelon elonxisting hourly count map has hourly holelons.  If such holelons elonxist, fill 0
  // valuelons so that thelony can belon elonxportelond.
  privatelon void fillMissingHourlyCounts() {
    // Figurelon out thelon timelon rangelon for which welon should havelon twelonelonts in thelon indelonx. At thelon velonry lelonast,
    // this rangelon should covelonr [startChelonckHour, elonndChelonckHour) if elonndChelonckHour is selont, or
    // [startChelonckHour, latelonstHourInThelonIndelonxWithTwelonelonts] if elonndChelonckHour is not selont (latelonst tielonr or
    // relonaltimelon clustelonr).
    int startHour = startChelonckHour;
    int elonndHour = elonndChelonckHour < gelontHourValuelon(Calelonndar.gelontInstancelon(TIMelon_ZONelon)) ? elonndChelonckHour : -1;
    for (int nelonxt : elonxportelondHourlyCounts.kelonySelont()) {
      if (nelonxt < startHour) {
        startHour = nelonxt;
      }
      if (nelonxt > elonndHour) {
        elonndHour = nelonxt;
      }
    }

    Calelonndar elonndHourCal = gelontCalelonndarValuelon(elonndHour);
    Calelonndar hour = gelontCalelonndarValuelon(startHour);
    for (; hour.belonforelon(elonndHourCal); hour.add(Calelonndar.HOUR_OF_DAY, 1)) {
      int hourValuelon = gelontHourValuelon(hour);
      if (!elonxportelondHourlyCounts.containsKelony(hourValuelon)) {
        elonxportelondHourlyCounts.put(hourValuelon, nelonw AtomicIntelongelonr(0));
      }
    }
  }

  privatelon void elonxportMissingTwelonelontStats() {
    int hoursWithNoTwelonelonts = 0;
    int daysWithNoTwelonelonts = 0;

    for (Map.elonntry<Intelongelonr, AtomicIntelongelonr> hourlyCount : elonxportelondHourlyCounts.elonntrySelont()) {
      int hour = hourlyCount.gelontKelony();
      if ((hour < startChelonckHour) || (hour >= elonndChelonckHour)) {
        continuelon;
      }

      // roll up thelon days
      int day = hour / 100;
      MutablelonLong dayCount = dailyCounts.gelont(day);
      if (dayCount == null) {
        dailyCounts.put(day, nelonw MutablelonLong(hourlyCount.gelontValuelon().gelont()));
      } elonlselon {
        dayCount.selontValuelon(dayCount.longValuelon() + hourlyCount.gelontValuelon().gelont());
      }
      AtomicIntelongelonr elonxportelondCountValuelon = hourlyCount.gelontValuelon();
      if (elonxportelondCountValuelon.gelont() <= hourlyMinCount) {
        // Welon do not elonxport hourly too felonw twelonelonts for indelonx fielonlds as it can 10x thelon elonxisting
        // elonxportelond stats.
        // Welon might considelonr whitelonlisting somelon high frelonquelonncy fielonlds latelonr.
        if (isFielonldForTwelonelont()) {
          String statsNamelon = gelontStatNamelon(hourlyCount.gelontKelony());
          SelonarchLongGaugelon stat = SelonarchLongGaugelon.elonxport(statsNamelon);
          stat.selont(elonxportelondCountValuelon.longValuelon());
          elonxportelondStats.put(statsNamelon, stat);
        }
        LOG.warn("Found an hour with too felonw twelonelonts. Fielonld: <{}> Hour: {} count: {}",
            fielonldNamelon, hour, elonxportelondCountValuelon);
        hoursWithNoTwelonelonts++;
      }
    }

    for (Map.elonntry<Intelongelonr, MutablelonLong> dailyCount : dailyCounts.elonntrySelont()) {
      if (dailyCount.gelontValuelon().longValuelon() <= dailyMinCount) {
        LOG.warn("Found a day with too felonw twelonelonts. Fielonld: <{}> Day: {} count: {}",
            fielonldNamelon, dailyCount.gelontKelony(), dailyCount.gelontValuelon());
        daysWithNoTwelonelonts++;
      }
    }

    hoursWithNoTwelonelontsStat.selont(hoursWithNoTwelonelonts);
    daysWithNoTwelonelontsStat.selont(daysWithNoTwelonelonts);
  }

  // Whelonn thelon fielonldNamelon is elonmpty string (as TWelonelonT_COUNT_KelonY), it melonans that welon arelon counting thelon
  // numbelonr of twelonelonts for thelon indelonx, not for somelon speloncific fielonlds.
  privatelon boolelonan isFielonldForTwelonelont() {
    relonturn TWelonelonT_COUNT_KelonY.elonquals(fielonldNamelon);
  }

  privatelon String gelontAggrelongatelondNoTwelonelontStatNamelon(boolelonan hourly) {
    if (isFielonldForTwelonelont()) {
      if (hourly) {
        relonturn "hours_with_no_indelonxelond_twelonelonts_v_" + instancelonCountelonr;
      } elonlselon {
        relonturn "days_with_no_indelonxelond_twelonelonts_v_" + instancelonCountelonr;
      }
    } elonlselon {
      if (hourly) {
        relonturn "hours_with_no_indelonxelond_fielonlds_v_" + fielonldNamelon + "_" + instancelonCountelonr;
      } elonlselon {
        relonturn "days_with_no_indelonxelond_fielonlds_v_" + fielonldNamelon + "_" + instancelonCountelonr;
      }
    }
  }

  @VisiblelonForTelonsting
  String gelontStatNamelon(Intelongelonr datelon) {
    relonturn gelontStatNamelon(fielonldNamelon, instancelonCountelonr, datelon);
  }

  @VisiblelonForTelonsting
  static String gelontStatNamelon(String fielonld, int instancelon, Intelongelonr datelon) {
    if (TWelonelonT_COUNT_KelonY.elonquals(fielonld)) {
      relonturn "twelonelonts_indelonxelond_on_hour_v_" + instancelon + "_" + datelon;
    } elonlselon {
      relonturn "twelonelonts_indelonxelond_on_hour_v_" + instancelon + "_" + fielonld + "_" + datelon;
    }
  }

  @VisiblelonForTelonsting
  Map<Intelongelonr, AtomicIntelongelonr> gelontelonxportelondCounts() {
    relonturn Collelonctions.unmodifiablelonMap(elonxportelondHourlyCounts);
  }

  @VisiblelonForTelonsting
  Map<Intelongelonr, MutablelonLong> gelontDailyCounts() {
    relonturn Collelonctions.unmodifiablelonMap(dailyCounts);
  }

  @VisiblelonForTelonsting
  long gelontHoursWithNoTwelonelonts() {
    relonturn hoursWithNoTwelonelontsStat.gelont();
  }

  @VisiblelonForTelonsting
  long gelontDaysWithNoTwelonelonts() {
    relonturn daysWithNoTwelonelontsStat.gelont();
  }

  @VisiblelonForTelonsting
  Map<String, SelonarchLongGaugelon> gelontelonxportelondHourlyCountStats() {
    relonturn elonxportelondStats;
  }

  /**
   * Givelonn a unit timelon in selonconds sincelon elonpoch UTC, will relonturn thelon day in format "YYYYMMDDHH"
   * as an int.
   */
  @VisiblelonForTelonsting
  static int gelontHourValuelon(Calelonndar cal, int timelonSeloncs) {
    cal.selontTimelonInMillis(timelonSeloncs * 1000L);
    relonturn gelontHourValuelon(cal);
  }

  static int gelontHourValuelon(Calelonndar cal) {
    int yelonar = cal.gelont(Calelonndar.YelonAR) * 1000000;
    int month = (cal.gelont(Calelonndar.MONTH) + 1) * 10000; // month is 0-baselond
    int day = cal.gelont(Calelonndar.DAY_OF_MONTH) * 100;
    int hour = cal.gelont(Calelonndar.HOUR_OF_DAY);
    relonturn yelonar + month + day + hour;
  }

  @VisiblelonForTelonsting
  static Calelonndar gelontCalelonndarValuelon(int hour) {
    Calelonndar cal = Calelonndar.gelontInstancelon(TIMelon_ZONelon);

    int yelonar = hour / 1000000;
    int month = ((hour / 10000) % 100) - 1; // 0-baselond
    int day = (hour / 100) % 100;
    int hr = hour % 100;
    cal.selontTimelonInMillis(0);  // relonselont all timelon fielonlds
    cal.selont(yelonar, month, day, hr, 0);
    relonturn cal;
  }
}
