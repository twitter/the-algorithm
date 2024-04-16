package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.Calendar;
 mport java.ut l.Collect ons;
 mport java.ut l.Map;
 mport java.ut l.T  Zone;
 mport java.ut l.concurrent.atom c.Atom c nteger;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.mutable.Mutable nt;
 mport org.apac .commons.lang.mutable.MutableLong;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchLongGauge;

/**
 * T  class  s used to count how many t  s a f eld happens  n h ly and da ly stats.
 *    s used by TermCountMon or for  erat ng all f elds  n t   ndex.
 *
 * T re  s one except on that t  class  s also used to count t  number of t ets  n t   ndex.
 * Under t  s uat on, t  passed  n f eldNa  would be empty str ng (as TWEET_COUNT_KEY).
 */
publ c class F eldTermCounter {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(F eldTermCounter.class);

  stat c f nal T  Zone T ME_ZONE = T  Zone.getT  Zone("GMT");
  stat c f nal Str ng TWEET_COUNT_KEY = "";

  pr vate f nal Str ng f eldNa ;
  pr vate f nal  nt  nstanceCounter;

  // T  f rst date  n format "YYYYMMDDHH" that   want to c ck counts for.
  pr vate f nal  nt startC ckH ;
  // T  last date  n format "YYYYMMDDHH" that   want to c ck counts for.
  pr vate f nal  nt endC ckH ;
  // Smallest number of docs   expect to have for each h .
  pr vate f nal  nt h lyM nCount;
  //Smallest number of docs   expect to have for each day.
  pr vate f nal  nt da lyM nCount;

  // Count of t ets for each day, keyed of by t  h   n t  format "YYYYMMDD".
  pr vate f nal Map< nteger, Atom c nteger> exportedH lyCounts;

  // Count of t ets for each day, keyed of by t  day  n t  format "YYYYMMDD".
  pr vate f nal Map< nteger, MutableLong> da lyCounts;

  // Only export h ly stats that are below m n mum threshold.
  pr vate f nal Map<Str ng, SearchLongGauge> exportedStats;

  pr vate f nal SearchLongGauge h sW hNoT etsStat;
  pr vate f nal SearchLongGauge daysW hNoT etsStat;

  publ c F eldTermCounter(
      Str ng f eldNa ,
       nt  nstanceCounter,
       nt startC ckH ,
       nt endC ckH ,
       nt h lyM nCount,
       nt da lyM nCount) {
    t .f eldNa  = f eldNa ;
    t . nstanceCounter =  nstanceCounter;
    t .startC ckH  = startC ckH ;
    t .endC ckH  = endC ckH ;
    t .h lyM nCount = h lyM nCount;
    t .da lyM nCount = da lyM nCount;
    t .exportedH lyCounts = Maps.newHashMap();
    t .da lyCounts = Maps.newHashMap();
    t .exportedStats = Maps.newHashMap();

    t .h sW hNoT etsStat = SearchLongGauge.export(getAggregatedNoT etStatNa (true));
    t .daysW hNoT etsStat = SearchLongGauge.export(getAggregatedNoT etStatNa (false));
  }

  /**
   * Updates t  stats exported by t  class based on t  new counts prov ded  n t  g ven map.
   */
  publ c vo d runW hNewCounts(Map< nteger, Mutable nt> newCounts) {
    da lyCounts.clear();

    // See go/rb/813442/#com nt2566569
    // 1. Update all ex st ng h s
    updateEx st ngH lyCounts(newCounts);

    // 2. Add and export all new h s
    addAndExportNewH lyCounts(newCounts);

    // 3. f ll  n all t  m ss ng h s bet en know m n and max days.
    f llM ss ngH lyCounts();

    // 4. Export as a stat, how many h s don't have any t ets ( .e. <= 0)
    exportM ss ngT etStats();
  }

  //  nput:
  // . t  new h ly count map  n t  current  erat on
  // . t  ex st ng h ly count map before t  current  erat on
  //  f t  h ly key matc s from t  new h ly map to t  ex st ng h ly count map, update
  // t  value of t  ex st ng h ly count map to t  value from t  new h ly count map.
  pr vate vo d updateEx st ngH lyCounts(Map< nteger, Mutable nt> newCounts) {
    for (Map.Entry< nteger, Atom c nteger> exportedCount : exportedH lyCounts.entrySet()) {
       nteger date = exportedCount.getKey();
      Atom c nteger exportedCountValue = exportedCount.getValue();

      Mutable nt newCount = newCounts.get(date);
       f (newCount == null) {
        exportedCountValue.set(0);
      } else {
        exportedCountValue.set(newCount. ntValue());
        // clean up so that   don't c ck t  date aga n w n   look for new h s
        newCounts.remove(date);
      }
    }
  }

  //  nput:
  // . t  new h ly count map  n t  current  erat on
  // . t  ex st ng h ly count map before t  current  erat on
  // T  funct on  s called after t  above funct on of updateEx st ngH lyCounts() so that all
  // match ng key value pa rs have been removed from t  new h ly count map.
  // Move all rema n ng val d values from t  new h ly count map to t  ex st ng h ly count
  // map.
  pr vate vo d addAndExportNewH lyCounts(Map< nteger, Mutable nt> newCounts) {
    for (Map.Entry< nteger, Mutable nt> newCount : newCounts.entrySet()) {
       nteger h  = newCount.getKey();
      Mutable nt newCountValue = newCount.getValue();
      Precond  ons.c ckState(!exportedH lyCounts.conta nsKey(h ),
          "Should have already processed and removed ex st ng h s: " + h );

      Atom c nteger newStat = new Atom c nteger(newCountValue. ntValue());
      exportedH lyCounts.put(h , newStat);
    }
  }

  // F nd w t r t  ex st ng h ly count map has h ly holes.   f such holes ex st, f ll 0
  // values so that t y can be exported.
  pr vate vo d f llM ss ngH lyCounts() {
    // F gure out t  t   range for wh ch   should have t ets  n t   ndex. At t  very least,
    // t  range should cover [startC ckH , endC ckH )  f endC ckH   s set, or
    // [startC ckH , latestH  nT  ndexW hT ets]  f endC ckH   s not set (latest t er or
    // realt   cluster).
     nt startH  = startC ckH ;
     nt endH  = endC ckH  < getH Value(Calendar.get nstance(T ME_ZONE)) ? endC ckH  : -1;
    for ( nt next : exportedH lyCounts.keySet()) {
       f (next < startH ) {
        startH  = next;
      }
       f (next > endH ) {
        endH  = next;
      }
    }

    Calendar endH Cal = getCalendarValue(endH );
    Calendar h  = getCalendarValue(startH );
    for (; h .before(endH Cal); h .add(Calendar.HOUR_OF_DAY, 1)) {
       nt h Value = getH Value(h );
       f (!exportedH lyCounts.conta nsKey(h Value)) {
        exportedH lyCounts.put(h Value, new Atom c nteger(0));
      }
    }
  }

  pr vate vo d exportM ss ngT etStats() {
     nt h sW hNoT ets = 0;
     nt daysW hNoT ets = 0;

    for (Map.Entry< nteger, Atom c nteger> h lyCount : exportedH lyCounts.entrySet()) {
       nt h  = h lyCount.getKey();
       f ((h  < startC ckH ) || (h  >= endC ckH )) {
        cont nue;
      }

      // roll up t  days
       nt day = h  / 100;
      MutableLong dayCount = da lyCounts.get(day);
       f (dayCount == null) {
        da lyCounts.put(day, new MutableLong(h lyCount.getValue().get()));
      } else {
        dayCount.setValue(dayCount.longValue() + h lyCount.getValue().get());
      }
      Atom c nteger exportedCountValue = h lyCount.getValue();
       f (exportedCountValue.get() <= h lyM nCount) {
        //   do not export h ly too few t ets for  ndex f elds as   can 10x t  ex st ng
        // exported stats.
        //   m ght cons der wh el st ng so  h gh frequency f elds later.
         f ( sF eldForT et()) {
          Str ng statsNa  = getStatNa (h lyCount.getKey());
          SearchLongGauge stat = SearchLongGauge.export(statsNa );
          stat.set(exportedCountValue.longValue());
          exportedStats.put(statsNa , stat);
        }
        LOG.warn("Found an h  w h too few t ets. F eld: <{}> H : {} count: {}",
            f eldNa , h , exportedCountValue);
        h sW hNoT ets++;
      }
    }

    for (Map.Entry< nteger, MutableLong> da lyCount : da lyCounts.entrySet()) {
       f (da lyCount.getValue().longValue() <= da lyM nCount) {
        LOG.warn("Found a day w h too few t ets. F eld: <{}> Day: {} count: {}",
            f eldNa , da lyCount.getKey(), da lyCount.getValue());
        daysW hNoT ets++;
      }
    }

    h sW hNoT etsStat.set(h sW hNoT ets);
    daysW hNoT etsStat.set(daysW hNoT ets);
  }

  // W n t  f eldNa   s empty str ng (as TWEET_COUNT_KEY),    ans that   are count ng t 
  // number of t ets for t   ndex, not for so  spec f c f elds.
  pr vate boolean  sF eldForT et() {
    return TWEET_COUNT_KEY.equals(f eldNa );
  }

  pr vate Str ng getAggregatedNoT etStatNa (boolean h ly) {
     f ( sF eldForT et()) {
       f (h ly) {
        return "h s_w h_no_ ndexed_t ets_v_" +  nstanceCounter;
      } else {
        return "days_w h_no_ ndexed_t ets_v_" +  nstanceCounter;
      }
    } else {
       f (h ly) {
        return "h s_w h_no_ ndexed_f elds_v_" + f eldNa  + "_" +  nstanceCounter;
      } else {
        return "days_w h_no_ ndexed_f elds_v_" + f eldNa  + "_" +  nstanceCounter;
      }
    }
  }

  @V s bleForTest ng
  Str ng getStatNa ( nteger date) {
    return getStatNa (f eldNa ,  nstanceCounter, date);
  }

  @V s bleForTest ng
  stat c Str ng getStatNa (Str ng f eld,  nt  nstance,  nteger date) {
     f (TWEET_COUNT_KEY.equals(f eld)) {
      return "t ets_ ndexed_on_h _v_" +  nstance + "_" + date;
    } else {
      return "t ets_ ndexed_on_h _v_" +  nstance + "_" + f eld + "_" + date;
    }
  }

  @V s bleForTest ng
  Map< nteger, Atom c nteger> getExportedCounts() {
    return Collect ons.unmod f ableMap(exportedH lyCounts);
  }

  @V s bleForTest ng
  Map< nteger, MutableLong> getDa lyCounts() {
    return Collect ons.unmod f ableMap(da lyCounts);
  }

  @V s bleForTest ng
  long getH sW hNoT ets() {
    return h sW hNoT etsStat.get();
  }

  @V s bleForTest ng
  long getDaysW hNoT ets() {
    return daysW hNoT etsStat.get();
  }

  @V s bleForTest ng
  Map<Str ng, SearchLongGauge> getExportedH lyCountStats() {
    return exportedStats;
  }

  /**
   * G ven a un  t    n seconds s nce epoch UTC, w ll return t  day  n format "YYYYMMDDHH"
   * as an  nt.
   */
  @V s bleForTest ng
  stat c  nt getH Value(Calendar cal,  nt t  Secs) {
    cal.setT   nM ll s(t  Secs * 1000L);
    return getH Value(cal);
  }

  stat c  nt getH Value(Calendar cal) {
     nt year = cal.get(Calendar.YEAR) * 1000000;
     nt month = (cal.get(Calendar.MONTH) + 1) * 10000; // month  s 0-based
     nt day = cal.get(Calendar.DAY_OF_MONTH) * 100;
     nt h  = cal.get(Calendar.HOUR_OF_DAY);
    return year + month + day + h ;
  }

  @V s bleForTest ng
  stat c Calendar getCalendarValue( nt h ) {
    Calendar cal = Calendar.get nstance(T ME_ZONE);

     nt year = h  / 1000000;
     nt month = ((h  / 10000) % 100) - 1; // 0-based
     nt day = (h  / 100) % 100;
     nt hr = h  % 100;
    cal.setT   nM ll s(0);  // reset all t   f elds
    cal.set(year, month, day, hr, 0);
    return cal;
  }
}
