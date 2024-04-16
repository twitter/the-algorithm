package com.tw ter.search.earlyb rd.ut l;

 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cLong;
 mport java.ut l.funct on.Funct on;
 mport java.ut l.stream.Collectors;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.apac .commons.lang.mutable.MutableLong;
 mport org.apac .lucene. ndex. ndexOpt ons;
 mport org.apac .lucene. ndex.Terms;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;

/**
 * A background task that per od cally gets and exports t  number of terms per f eld that are
 *  ndexed on t  earlyb rd, averaged over all seg nts.
 * Spec f cally used for mak ng sure that   are not m ss ng terms for any f elds  n t  search
 * arch ves.
 * T  task loops though all t  seg nts that are  ndexed by t  earlyb rd, and for each seg nt
 * looks at t  term counts for all f elds  n that seg nt.
 *
 * Also keeps track of t  number of f elds that do not have any term counts (or below t  spec f ed
 * threshold)  n t  data that  s  ndexed on t  earlyb rd.
 */
publ c class TermCountMon or extends OneTaskSc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(TermCountMon or.class);

  pr vate stat c f nal Str ng THREAD_NAME_FORMAT = "TermCountMon or-%d";
  pr vate stat c f nal boolean THREAD_ S_DAEMON = true;

  publ c stat c f nal Str ng RUN_ NTERVAL_M NUTES_CONF G_NAME =
      "term_count_mon or_run_ nterval_m nutes";

  pr vate stat c Funct on<Str ng, Str ng> termStatNa Func =
      f eld -> "term_count_on_f eld_" + f eld;
  pr vate stat c Funct on<Str ng, Str ng> tokenStatNa Func =
      f eld -> "token_count_on_f eld_" + f eld;
  pr vate stat c Funct on<Str ng, Str ng> m ss ngF eldStatNa Func =
      f eld -> "term_count_mon or_m ss ng_f eld_" + f eld;

  pr vate stat c class RawF eldCounter {
    pr vate MutableLong numTerms = new MutableLong(0L);
    pr vate MutableLong numTokens = new MutableLong(0L);
  }

  @V s bleForTest ng
  stat c class ExportedF eldCounter {
    pr vate f nal Atom cLong numTerms;
    pr vate f nal Atom cLong numTokens;

    ExportedF eldCounter(RawF eldCounter rawCounter) {
      t .numTerms = new Atom cLong(rawCounter.numTerms.longValue());
      t .numTokens = new Atom cLong(rawCounter.numTokens.longValue());
    }

    ExportedF eldCounter(long num n  alTerms, long num n  alTokens) {
      t .numTerms = new Atom cLong(num n  alTerms);
      t .numTokens = new Atom cLong(num n  alTokens);
    }

    @V s bleForTest ng
    long getNumTerms() {
      return numTerms.longValue();
    }

    @V s bleForTest ng
    long getNumTokens() {
      return numTokens.longValue();
    }
  }

  pr vate f nal  nt f eldM nTermCount =
      Earlyb rdConf g.get nt("term_count_mon or_m n_count", 0);

  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Map<Str ng, SearchLongGauge> m ss ngF elds;
  pr vate f nal Map<Str ng, SearchLongGauge> termStats;
  pr vate f nal Map<Str ng, SearchLongGauge> tokenStats;
  pr vate f nal Map<Str ng, ExportedF eldCounter> exportedCounts;
  pr vate f nal SearchLongGauge termCountOnAllF elds;
  pr vate f nal SearchLongGauge tokenCountOnAllF elds;
  pr vate f nal SearchLongGauge f eldsW hNoTermCountStat;
  pr vate f nal SearchLongGauge  sRunn ngStat;
  pr vate f nal SearchT  rStats c ckT  Stat;

  @Overr de
  protected vo d runOne erat on() {
    LOG. nfo("Start ng to get per-f eld term counts");
     sRunn ngStat.set(1);
    f nal SearchT  r t  r = c ckT  Stat.startNewT  r();
    try {
      updateF eldTermCounts();
    } catch (Except on ex) {
      LOG.error("Unexpected except on wh le gett ng per-f eld term counts", ex);
    } f nally {
      LOG. nfo(
          "Done gett ng per-f eld term counts. F elds w h low term counts: {}",
          getF eldsW hLowTermCount());
       sRunn ngStat.set(0);
      c ckT  Stat.stopT  rAnd ncre nt(t  r);
    }
  }

  /**
   * Create a term count mon or wh ch mon ors t  number of terms  n seg nts
   * managed by t  g ven seg nt manager.
   */
  publ c TermCountMon or(
      Seg ntManager seg ntManager,
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
        Earlyb rdConf g.get nt(RUN_ NTERVAL_M NUTES_CONF G_NAME, -1),
        T  Un .M NUTES),
      new ShutdownWa T  Params(
        shutdownWa Durat on,
        shutdownWa Un 
      ),
      searchStatsRece ver,
        cr  calExcept onHandler);
    t .seg ntManager = seg ntManager;
    t .m ss ngF elds = new HashMap<>();
    t .termStats = new HashMap<>();
    t .tokenStats = new HashMap<>();
    t .exportedCounts = new HashMap<>();
    t .termCountOnAllF elds = getSearchStatsRece ver().getLongGauge("term_count_on_all_f elds");
    t .tokenCountOnAllF elds = getSearchStatsRece ver().getLongGauge("token_count_on_all_f elds");
    t .f eldsW hNoTermCountStat =
        getSearchStatsRece ver().getLongGauge("f elds_w h_low_term_counts");
    t . sRunn ngStat =
        getSearchStatsRece ver().getLongGauge("term_count_mon or_ s_runn ng");
    t .c ckT  Stat =
        getSearchStatsRece ver().getT  rStats(
            "term_count_mon or_c ck_t  ", T  Un .M LL SECONDS, true, true, false);
  }

  pr vate SearchLongGauge getOrCreateLongGauge(
      Map<Str ng, SearchLongGauge> gauges, Str ng f eld, Funct on<Str ng, Str ng> na Suppl er) {
    SearchLongGauge stat = gauges.get(f eld);

     f (stat == null) {
      stat = getSearchStatsRece ver().getLongGauge(na Suppl er.apply(f eld));
      gauges.put(f eld, stat);
    }

    return stat;
  }

  pr vate vo d updateF eldTermCounts() {
    // 0. Get t  current per-f eld term counts
    Map<Str ng, RawF eldCounter> newCounts = getF eldStats();
    LOG. nfo("Computed f eld stats for all seg nts");

    // 1. Update all ex st ng keys
    for (Map.Entry<Str ng, ExportedF eldCounter> exportedCount : exportedCounts.entrySet()) {
      Str ng f eld = exportedCount.getKey();
      ExportedF eldCounter exportedCountValue = exportedCount.getValue();

      RawF eldCounter newCount = newCounts.get(f eld);
       f (newCount == null) {
        exportedCountValue.numTerms.set(0L);
        exportedCountValue.numTokens.set(0L);
      } else {
        exportedCountValue.numTerms.set(newCount.numTerms.longValue());
        exportedCountValue.numTokens.set(newCount.numTokens.longValue());

        // clean up so that   don't c ck t  f eld aga n w n   look for new f eld
        newCounts.remove(f eld);
      }
    }

    // 2. Add and export all new f elds' term counts
    for (Map.Entry<Str ng, RawF eldCounter> newCount: newCounts.entrySet()) {
      Str ng f eld = newCount.getKey();
      Precond  ons.c ckState(!exportedCounts.conta nsKey(f eld),
          "Should have already processed and removed ex st ng f elds: " + f eld);

      ExportedF eldCounter newStat = new ExportedF eldCounter(newCount.getValue());
      exportedCounts.put(f eld, newStat);
    }

    // 3. Export as a stat t  term counts for all t  known f elds.
    for (Map.Entry<Str ng, ExportedF eldCounter> exportedCount : exportedCounts.entrySet()) {
      Str ng f eld = exportedCount.getKey();
      ExportedF eldCounter counter = exportedCount.getValue();

      getOrCreateLongGauge(termStats, f eld, termStatNa Func).set(counter.numTerms.get());
      getOrCreateLongGauge(tokenStats, f eld, tokenStatNa Func).set(counter.numTokens.get());
    }

    // 4. Export as a stat, number of f elds not hav ng enough term counts ( .e. <= 0)
     nt f eldsW hNoTermCounts = 0;
    for (Map.Entry<Str ng, ExportedF eldCounter> f eldTermCount : exportedCounts.entrySet()) {
      Str ng f eld = f eldTermCount.getKey();
      Atom cLong exportedCountValue = f eldTermCount.getValue().numTerms;
       f (exportedCountValue.get() <= f eldM nTermCount) {
        LOG.warn(
            "Found a f eld w h too few term counts. F eld: {} count: {}",
            f eld, exportedCountValue);
        f eldsW hNoTermCounts++;
      }
    }
    t .f eldsW hNoTermCountStat.set(f eldsW hNoTermCounts);
  }

  /**
   * Loops through all seg nts, and for each f eld gets t  average term/token count.
   * Based on that, returns a map from each f eld to  s term/token count (average per seg nt).
   */
  pr vate Map<Str ng, RawF eldCounter> getF eldStats() {
     erable<Seg nt nfo> seg nt nfos = seg ntManager.getSeg nt nfos(
        Seg ntManager.F lter.Enabled, Seg ntManager.Order.NEW_TO_OLD);
    Map<Str ng, RawF eldCounter> rawCounts = new HashMap<>();

     mmutableSc ma nterface sc maSnapshot =
        seg ntManager.getEarlyb rd ndexConf g().getSc ma().getSc maSnapshot();
    Set<Str ng> m ss ngF eldsCand dates = sc maSnapshot
        .getF eld nfos()
        .stream()
        .f lter(f eld nfo -> f eld nfo.getF eldType(). ndexOpt ons() !=  ndexOpt ons.NONE)
        .map(Sc ma.F eld nfo::getNa )
        .collect(Collectors.toSet());
     nt seg ntCount = 0;
    for (Seg nt nfo seg nt nfo : seg nt nfos) {
      seg ntCount++;
      try {
        Earlyb rdS ngleSeg ntSearc r searc r = seg ntManager.getSearc r(
            seg nt nfo.getT  Sl ce D(), sc maSnapshot);
         f (searc r != null) {
          Earlyb rd ndexSeg ntAtom cReader reader = searc r.getTw ter ndexReader();
          for (Sc ma.F eld nfo f eld nfo : sc maSnapshot.getF eld nfos()) {
             f (f eld nfo.getF eldType(). ndexOpt ons() ==  ndexOpt ons.NONE) {
              cont nue;
            }

            Str ng f eldNa  = f eld nfo.getNa ();
            RawF eldCounter count = rawCounts.get(f eldNa );
             f (count == null) {
              count = new RawF eldCounter();
              rawCounts.put(f eldNa , count);
            }
            Terms terms = reader.terms(f eldNa );
             f (terms != null) {
              m ss ngF eldsCand dates.remove(f eldNa );
              count.numTerms.add(terms.s ze());
              long sumTotalTermFreq = terms.getSumTotalTermFreq();
               f (sumTotalTermFreq != -1) {
                count.numTokens.add(sumTotalTermFreq);
              }
            }
          }
        }
      } catch (Except on e) {
        LOG.error("Except on gett ng average term count per f eld: " + seg nt nfo, e);
      }
    }

    // Update m ss ng f elds stats.
    m ss ngF eldsCand dates.forEach(
        f eld -> getOrCreateLongGauge(m ss ngF elds, f eld, m ss ngF eldStatNa Func).set(1));
    m ss ngF elds.keySet().stream()
        .f lter(
            f eld -> !m ss ngF eldsCand dates.conta ns(f eld))
        .forEach(
            f eld -> getOrCreateLongGauge(m ss ngF elds, f eld, m ss ngF eldStatNa Func).set(0));

    long totalTermCount = 0;
    long totalTokenCount = 0;
     f (seg ntCount == 0) {
      LOG.error("No seg nts are found to calculate per-f eld term counts.");
    } else {
      LOG.debug("TermCountMon or.getPerF eldTermCount.seg ntCount = {}", seg ntCount);
      LOG.debug("  f eld: term count (average per seg nt)");
      for (Map.Entry<Str ng, RawF eldCounter> entry : rawCounts.entrySet()) {
        Str ng f eld = entry.getKey();
        f nal long averageTermCount = entry.getValue().numTerms.longValue() / seg ntCount;
        f nal long averageTokenCount = entry.getValue().numTokens.longValue() / seg ntCount;
        totalTermCount += entry.getValue().numTerms.longValue();
        totalTokenCount += entry.getValue().numTokens.longValue();

        LOG.debug("  '{} term': {}", f eld, averageTermCount);
        LOG.debug("  '{} token': {}", f eld, averageTokenCount);

        entry.getValue().numTerms.setValue(averageTermCount);
        entry.getValue().numTokens.setValue(averageTokenCount);
      }
    }
    LOG. nfo("Total term count: {}", totalTermCount);
    LOG. nfo("Total token count: {}", totalTokenCount);
    t .termCountOnAllF elds.set(totalTermCount);
    t .tokenCountOnAllF elds.set(totalTokenCount);

    return rawCounts;
  }

  @V s bleForTest ng
  Map<Str ng, ExportedF eldCounter> getExportedCounts() {
    return Collect ons.unmod f ableMap(t .exportedCounts);
  }

  @V s bleForTest ng
  long getF eldsW hLowTermCount() {
    return f eldsW hNoTermCountStat.get();
  }

  @V s bleForTest ng
  Map<Str ng, SearchLongGauge> getM ss ngF elds() {
    return m ss ngF elds;
  }
}
