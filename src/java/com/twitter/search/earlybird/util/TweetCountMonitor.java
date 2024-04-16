package com.tw ter.search.earlyb rd.ut l;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Calendar;
 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom c nteger;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Maps;

 mport org.apac .commons.lang.mutable.Mutable nt;
 mport org.apac .commons.lang.mutable.MutableLong;
 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;
 mport org.apac .lucene.search.Doc dSet erator;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.core.earlyb rd. ndex.T  Mapper;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;

/**
 * A background task that per od cally gets and exports t  number of t ets per h  that are
 *  ndexed on t  earlyb rd.
 * Spec f cally used for mak ng sure that   are not m ss ng data for any h s  n t  search
 * arch ves.
 * T  task loops though all t  seg nts that are  ndexed by t  earlyb rd, and for each seg nt
 * looks at all t  createdAt dates for all of t  docu nts  n that seg nt.
 *
 * Also keeps track off an exposes as a stat t  number of h s that do not have any t ets  n t 
 * m n/max range of data that  S  ndexed on t  earlyb rd.  .e  f   only have data for
 * 2006/01/01:02 and 2006/01/01:04,   w ll cons der 2006/01/01:03 as a m ss ng h .
 * H s before 2006/01/01:02 or after 2006/01/01:04 w ll not be cons dered as m ss ng.
 */
publ c class T etCountMon or extends OneTaskSc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(T etCountMon or.class);

  pr vate stat c f nal Str ng THREAD_NAME_FORMAT = "T etCountMon or-%d";
  pr vate stat c f nal boolean THREAD_ S_DAEMON = true;

  publ c stat c f nal Str ng RUN_ NTERVAL_M NUTES_CONF G_NAME =
      "t et_count_mon or_run_ nterval_m nutes";
  publ c stat c f nal Str ng START_CHECK_HOUR_CONF G_NAME =
      "t et_count_mon or_start_c ck_h ";
  publ c stat c f nal Str ng HOURLY_M N_COUNT_CONF G_NAME =
      "t et_count_mon or_h ly_m n_count";
  publ c stat c f nal Str ng DA LY_M N_COUNT_CONF G_NAME =
      "t et_count_mon or_da ly_m n_count";

  @V s bleForTest ng
  publ c stat c f nal Atom c nteger  NSTANCE_COUNTER = new Atom c nteger(0);

  pr vate stat c f nal long M LL S_ N_A_DAY = T  Un .DAYS.toM ll s(1);

  pr vate f nal Seg ntManager seg ntManager;

  pr vate f nal SearchStatsRece ver searchStatsRece ver;
  pr vate f nal  nt  nstanceCounter;

  // T  f rst date  n format "YYYYMMDDHH" that   want to c ck counts for.
  pr vate f nal  nt startC ckH ;
  // T  last date  n format "YYYYMMDDHH" that   want to c ck counts for.
  pr vate f nal  nt endC ckH ;
  //Smallest number of docs   expect to have for each day.
  pr vate f nal  nt da lyM nCount;
  // Smallest number of docs   expect to have for each h .
  pr vate f nal  nt h lyM nCount;
  // B nary stat, set to 0 w n t  mon or  s runn ng
  pr vate f nal SearchLongGauge  sRunn ngStat;
  // How long each  erat on takes
  pr vate f nal SearchT  rStats c ckT  Stat;

  pr vate f nal Map<Str ng, F eldTermCounter> f eldTermCounters;
  pr vate f nal Map<Str ng, SearchT  rStats> f eldC ckT  Stats;

  /**
   * Create a T etCountMon or to mon or all seg nts  n t  g ven seg ntManager
   */
  publ c T etCountMon or(
      Seg ntManager seg ntManager,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      long shutdownWa Durat on,
      T  Un  shutdownWa Un ,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (seg ntManager,
        Earlyb rdConf g.get nt(START_CHECK_HOUR_CONF G_NAME, 0),
        Earlyb rdConf g.get nt(RUN_ NTERVAL_M NUTES_CONF G_NAME, -1),
        Earlyb rdConf g.get nt(HOURLY_M N_COUNT_CONF G_NAME, 0),
        Earlyb rdConf g.get nt(DA LY_M N_COUNT_CONF G_NAME, 0),
        executorServ ceFactory,
        shutdownWa Durat on,
        shutdownWa Un ,
        searchStatsRece ver,
        cr  calExcept onHandler);
  }

  @V s bleForTest ng
  T etCountMon or(
      Seg ntManager seg ntManager,
       nt startC ckH FromConf g,
       nt sc dulePer odM nutes,
       nt h lyM nCount,
       nt da lyM nCount,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      long shutdownWa Durat on,
      T  Un  shutdownWa Un ,
      SearchStatsRece ver searchStatsRece ver,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    super(
      executorServ ceFactory,
      THREAD_NAME_FORMAT,
      THREAD_ S_DAEMON,
      Per od cAct onParams.atF xedRate(
        sc dulePer odM nutes,
        T  Un .M NUTES
      ),
      new ShutdownWa T  Params(
        shutdownWa Durat on,
        shutdownWa Un 
      ),
      searchStatsRece ver,
        cr  calExcept onHandler);
    t .seg ntManager = seg ntManager;
    t .searchStatsRece ver = searchStatsRece ver;
    t . nstanceCounter =  NSTANCE_COUNTER. ncre ntAndGet();
    t .h lyM nCount = h lyM nCount;
    t .da lyM nCount = da lyM nCount;

    Str ng  sRunn ngStatNa  = "t et_count_mon or_ s_runn ng_v_" + t . nstanceCounter;
    t . sRunn ngStat = SearchLongGauge.export( sRunn ngStatNa );
    Str ng c ckT  StatNa  = "t et_count_mon or_c ck_t  _v_" + t . nstanceCounter;
    t .c ckT  Stat = SearchT  rStats.export(c ckT  StatNa , T  Un .M LL SECONDS, true);

    t .startC ckH  = Math.max(
        startC ckH FromConf g,
        dateToH Value(seg ntManager.getPart  onConf g().getT erStartDate()));
    t .endC ckH  = dateToH Value(seg ntManager.getPart  onConf g().getT erEndDate());

    t .f eldTermCounters = Maps.newHashMap();
    t .f eldTermCounters.put(
        F eldTermCounter.TWEET_COUNT_KEY,
        new F eldTermCounter(
            F eldTermCounter.TWEET_COUNT_KEY,
             nstanceCounter,
            startC ckH ,
            endC ckH ,
            h lyM nCount,
            da lyM nCount));
    t .f eldC ckT  Stats = Maps.newHashMap();
  }

  pr vate  nt dateToH Value(Date date) {
    Calendar cal = Calendar.get nstance(F eldTermCounter.T ME_ZONE);
    cal.setT  (date);
    return F eldTermCounter.getH Value(cal);
  }

  pr vate vo d updateH lyCounts() {
    //  erate t  current  ndex to count all t ets anf f eld h s.
    Map<Str ng, Map< nteger, Mutable nt>> newCountMap = getNewT etCountMap();

    for (Map.Entry<Str ng, Map< nteger, Mutable nt>> newCounts : newCountMap.entrySet()) {
      f nal Str ng f eldNa  = newCounts.getKey();
      F eldTermCounter termCounter = f eldTermCounters.get(f eldNa );
       f (termCounter == null) {
        termCounter = new F eldTermCounter(
            f eldNa ,
             nstanceCounter,
            startC ckH ,
            endC ckH ,
            h lyM nCount,
            da lyM nCount);
        f eldTermCounters.put(f eldNa , termCounter);
      }
      termCounter.runW hNewCounts(newCounts.getValue());
    }
  }

  /**
   * Loops through all seg nts, and all docu nts  n each seg nt, and for each docu nt
   * gets t  createdAt t  stamp ( n seconds) from t  T  Mapper.
   * Based on that, returns a map w h t  count of:
   * . t  number of t ets for each h 
   * . t  number of t ets correspond ng to each f eld for each h 
   */
  pr vate Map<Str ng, Map< nteger, Mutable nt>> getNewT etCountMap() {
     erable<Seg nt nfo> seg nt nfos = seg ntManager.getSeg nt nfos(
        Seg ntManager.F lter.Enabled, Seg ntManager.Order.NEW_TO_OLD);
    Map<Str ng, Map< nteger, Mutable nt>> newCountMap = Maps.newHashMap();

    Map< nteger, Mutable nt> newCounts = Maps.newHashMap();
    newCountMap.put(F eldTermCounter.TWEET_COUNT_KEY, newCounts);

     mmutableSc ma nterface sc maSnapshot =
        seg ntManager.getEarlyb rd ndexConf g().getSc ma().getSc maSnapshot();
    Calendar cal = Calendar.get nstance(F eldTermCounter.T ME_ZONE);
    for (Seg nt nfo seg nt nfo : seg nt nfos) {
      try {
        Earlyb rdS ngleSeg ntSearc r searc r = seg ntManager.getSearc r(
            seg nt nfo.getT  Sl ce D(), sc maSnapshot);
         f (searc r != null) {
          Earlyb rd ndexSeg ntAtom cReader reader = searc r.getTw ter ndexReader();
          T  Mapper t  Mapper = reader.getSeg ntData().getT  Mapper();
          L st<Pa r<Str ng,  nteger>> outs deEndDateRangeDocL st = new ArrayL st<>();

          // Get t  number of t ets for each h .
           nt docsOuts deEndDateRange = getNewT etCountsForSeg nt(
              seg nt nfo, reader, t  Mapper, cal, newCounts);
           f (docsOuts deEndDateRange > 0) {
            outs deEndDateRangeDocL st.add(new Pa r<>(
                F eldTermCounter.TWEET_COUNT_KEY, docsOuts deEndDateRange));
          }

          // Get t  number of t ets w h correspond ng f eld for each h .
          for (Sc ma.F eld nfo f eld nfo : sc maSnapshot.getF eld nfos()) {
             f (f eld nfo.getF eldType(). ndexOpt ons() ==  ndexOpt ons.NONE) {
              cont nue;
            }

            Str ng f eldNa  = f eld nfo.getNa ();
            docsOuts deEndDateRange = getNewF eldT etCountsForSeg nt(
                seg nt nfo, reader, t  Mapper, cal, f eldNa , newCountMap);
             f (docsOuts deEndDateRange > 0) {
              outs deEndDateRangeDocL st.add(new Pa r<>(f eldNa , docsOuts deEndDateRange));
            }
          }

          LOG. nfo(" nspected seg nt: " + seg nt nfo + " found "
              + outs deEndDateRangeDocL st.s ze()
              + " f elds w h docu nts outs de of seg nt end date.");
          for (Pa r<Str ng,  nteger> outs deEndRange : outs deEndDateRangeDocL st) {
            LOG. nfo("  outs de end date range - seg nt: " + seg nt nfo.getSeg ntNa ()
                + " f eld: " + outs deEndRange.toStr ng());
          }
        }
      } catch ( OExcept on e) {
        LOG.error("Except on gett ng da ly t et counts for t  sl ce: " + seg nt nfo, e);
      }
    }
    return newCountMap;
  }

  pr vate vo d  ncre ntNumDocsW h llegalT  Counter(Str ng seg ntNa , Str ng f eldSuff x) {
    Str ng statNa  = Str ng.format(
        "num_docs_w h_ llegal_t  _for_seg nt_%s%s_counter", seg ntNa , f eldSuff x);
    SearchCounter counter = SearchCounter.export(statNa );
    counter. ncre nt();
  }

  pr vate  nt getNewT etCountsForSeg nt(
      Seg nt nfo seg nt nfo,
      Earlyb rd ndexSeg ntAtom cReader reader,
      T  Mapper t  Mapper,
      Calendar cal,
      Map< nteger, Mutable nt> newT etCounts) {
    Doc DToT et DMapper t et dMapper = reader.getSeg ntData().getDoc DToT et DMapper();
    long dataEndT  Exclus veM ll s = getDataEndT  Exclus veM ll s(seg nt nfo);
     nt docsOuts deEndDateRange = 0;
     nt doc d =  nteger.M N_VALUE;
    wh le ((doc d = t et dMapper.getNextDoc D(doc d)) != Doc DToT et DMapper. D_NOT_FOUND) {
      UpdateCountType updateCountType =
          updateT etCount(t  Mapper, doc d, dataEndT  Exclus veM ll s, cal, newT etCounts);
       f (updateCountType == UpdateCountType. LLEGAL_T ME) {
         ncre ntNumDocsW h llegalT  Counter(seg nt nfo.getSeg ntNa (), "");
      } else  f (updateCountType == UpdateCountType.OUT_OF_RANGE_T ME) {
        docsOuts deEndDateRange++;
      }
    }
    return docsOuts deEndDateRange;
  }

  pr vate  nt getNewF eldT etCountsForSeg nt(
      Seg nt nfo seg nt nfo,
      Earlyb rd ndexSeg ntAtom cReader reader,
      T  Mapper t  Mapper,
      Calendar cal,
      Str ng f eld,
      Map<Str ng, Map< nteger, Mutable nt>> newCountMap) throws  OExcept on {
     nt docsOuts deEndDateRange = 0;
    Map< nteger, Mutable nt> f eldT etCounts =
        newCountMap.compute fAbsent(f eld, k -> Maps.newHashMap());

    Terms terms = reader.terms(f eld);
     f (terms == null) {
      LOG.warn("F eld <" + f eld + ">  s m ss ng terms  n seg nt: "
          + seg nt nfo.getSeg ntNa ());
      return 0;
    }
    long startT  M ll s = System.currentT  M ll s();

    long dataEndT  Exclus veM ll s = getDataEndT  Exclus veM ll s(seg nt nfo);
    for (TermsEnum termsEnum = terms. erator(); termsEnum.next() != null;) {
      Doc dSet erator docs erator = termsEnum.post ngs(null, Post ngsEnum.NONE);
      for ( nt doc d = docs erator.nextDoc();
           doc d != Doc dSet erator.NO_MORE_DOCS; doc d = docs erator.nextDoc()) {
        UpdateCountType updateCountType = updateT etCount(
            t  Mapper, doc d, dataEndT  Exclus veM ll s, cal, f eldT etCounts);
         f (updateCountType == UpdateCountType. LLEGAL_T ME) {
           ncre ntNumDocsW h llegalT  Counter(
              seg nt nfo.getSeg ntNa (), "_and_f eld_" + f eld);
        } else  f (updateCountType == UpdateCountType.OUT_OF_RANGE_T ME) {
          docsOuts deEndDateRange++;
        }
      }
    }
    updateF eldRunT  Stats(f eld, System.currentT  M ll s() - startT  M ll s);

    return docsOuts deEndDateRange;
  }

  pr vate enum UpdateCountType {
    OK_T ME,
     LLEGAL_T ME,
    OUT_OF_RANGE_T ME,
  }

  pr vate stat c UpdateCountType updateT etCount(
      T  Mapper t  Mapper,
       nt doc d,
      long dataEndT  Exclus veM ll s,
      Calendar cal,
      Map< nteger, Mutable nt> newT etCounts) {
     nt t  Secs = t  Mapper.getT  (doc d);
     f (t  Secs == T  Mapper. LLEGAL_T ME) {
      return UpdateCountType. LLEGAL_T ME;
    }
     f (dataEndT  Exclus veM ll s == Seg nt.NO_DATA_END_T ME
        || t  Secs * 1000L < dataEndT  Exclus veM ll s) {
       nteger h lyValue = F eldTermCounter.getH Value(cal, t  Secs);
      Mutable nt count = newT etCounts.get(h lyValue);
       f (count == null) {
        count = new Mutable nt(0);
        newT etCounts.put(h lyValue, count);
      }
      count. ncre nt();
      return UpdateCountType.OK_T ME;
    } else {
      return UpdateCountType.OUT_OF_RANGE_T ME;
    }
  }

  /**
   *  f a seg nt has an end date, return t  last t  stamp (exclus ve, and  n m ll s) for wh ch
   *   expect   to have data.
   * @return Seg nt.NO_DATA_END_T ME  f t  seg nt does not have an end date.
   */
  pr vate long getDataEndT  Exclus veM ll s(Seg nt nfo seg nt nfo) {
    long dataEndDate = seg nt nfo.getSeg nt().getDataEndDate nclus veM ll s();
     f (dataEndDate == Seg nt.NO_DATA_END_T ME) {
      return Seg nt.NO_DATA_END_T ME;
    } else {
      return dataEndDate + M LL S_ N_A_DAY;
    }
  }

  pr vate vo d updateF eldRunT  Stats(Str ng f eldNa , long runT  Ms) {
    SearchT  rStats t  rStats = f eldC ckT  Stats.get(f eldNa );
     f (t  rStats == null) {
      f nal Str ng statNa  = "t et_count_mon or_c ck_t  _f eld_" + f eldNa ;
      t  rStats = searchStatsRece ver.getT  rStats(
          statNa , T  Un .M LL SECONDS, false, false, false);
      f eldC ckT  Stats.put(f eldNa , t  rStats);
    }
    t  rStats.t  r ncre nt(runT  Ms);
  }

  @V s bleForTest ng
  Str ng getStatNa (Str ng f eldNa ,  nteger date) {
    return F eldTermCounter.getStatNa (f eldNa ,  nstanceCounter, date);
  }

  @V s bleForTest ng
  Map< nteger, Atom c nteger> getExportedCounts(Str ng f eldNa ) {
     f (f eldTermCounters.get(f eldNa ) == null) {
      return null;
    } else {
      return f eldTermCounters.get(f eldNa ).getExportedCounts();
    }
  }

  @V s bleForTest ng
  Map< nteger, MutableLong> getDa lyCounts(Str ng f eldNa ) {
     f (f eldTermCounters.get(f eldNa ) == null) {
      return null;
    } else {
      return f eldTermCounters.get(f eldNa ).getDa lyCounts();
    }
  }

  @V s bleForTest ng
  long getH sW hNoT ets(Str ng f eldNa ) {
    return f eldTermCounters.get(f eldNa ).getH sW hNoT ets();
  }

  @V s bleForTest ng
  long getDaysW hNoT ets(Str ng f eldNa ) {
    return f eldTermCounters.get(f eldNa ).getDaysW hNoT ets();
  }

  @V s bleForTest ng
  Map<Str ng, SearchLongGauge> getExportedH lyCountStats(Str ng f eldNa ) {
    return f eldTermCounters.get(f eldNa ).getExportedH lyCountStats();
  }

  @Overr de
  protected vo d runOne erat on() {
    LOG. nfo("Start ng to get h ly t et counts");
    f nal long startT  M ll s = System.currentT  M ll s();

     sRunn ngStat.set(1);
    try {
      updateH lyCounts();
    } catch (Except on ex) {
      LOG.error("Unexpected except on wh le gett ng h ly t et counts", ex);
    } f nally {
       sRunn ngStat.set(0);

      long elapsedT  M ll s = System.currentT  M ll s() - startT  M ll s;
      c ckT  Stat.t  r ncre nt(elapsedT  M ll s);
      LOG. nfo("Done gett ng da ly t et counts. H s w hout t ets: "
          + getH sW hNoT ets(F eldTermCounter.TWEET_COUNT_KEY));
      LOG. nfo("Updat ng t et count takes " + (elapsedT  M ll s / 1000) + " secs.");
    }
  }
}
