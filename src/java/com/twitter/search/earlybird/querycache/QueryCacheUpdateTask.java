package com.tw ter.search.earlyb rd.querycac ;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.core.earlyb rd. ndex.QueryCac ResultForSeg nt;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdExcept on;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg nt;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.search.SearchResults nfo;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.ut l.Sc duledExecutorTask;

/**
 * Each task  s respons ble for one f lter on one seg nt.   should have a total
 * of num_of_f lter * num_of_seg nts tasks
 */
@V s bleForTest ng
class QueryCac UpdateTask extends Sc duledExecutorTask {
  pr vate stat c f nal Logger LOG =  LoggerFactory.getLogger(QueryCac UpdateTask.class);

  // See OBSERVE-10347
  pr vate stat c f nal boolean EXPORT_STATS =
      Earlyb rdConf g.getBool("export_query_cac _update_task_stats", false);

  pr vate stat c f nal Load ngCac <Str ng, TaskStats> TASK_STATS =
      Cac Bu lder.newBu lder().bu ld(new Cac Loader<Str ng, TaskStats>() {
        @Overr de
        publ c TaskStats load(Str ng statNa Pref x) {
          return new TaskStats(statNa Pref x, EXPORT_STATS);
        }
      });

  pr vate stat c f nal SearchCounter F N SHED_TASKS = SearchCounter.export(
      "querycac _f n s d_tasks");

  pr vate f nal QueryCac F lter f lter;

  //  nfo/data of t  seg nt t  task  s respons ble for
  pr vate f nal Seg nt nfo seg nt nfo;

  pr vate f nal UserTable userTable;

  pr vate volat le boolean ranOnce;
  pr vate f nal TaskStats stats;
  pr vate Amount<Long, T  > lastRunF n shT  ;

  // See SEARCH-4346
  pr vate f nal Str ng f lterAndSeg nt;

  pr vate f nal Dec der dec der;

  pr vate stat c f nal class TaskStats {
    pr vate f nal SearchLongGauge numH sStat;
    pr vate f nal SearchLongGauge updateLatencyStat;
    pr vate f nal SearchCounter updateSuccessCountStat;
    pr vate f nal SearchCounter updateFa lureCountStat;

    pr vate TaskStats(Str ng statNa Pref x, boolean exportStats) {
      // See SEARCH-3698
      numH sStat = exportStats ? SearchLongGauge.export(statNa Pref x + "numh ")
          : new SearchLongGauge(statNa Pref x + "numh ");
      updateLatencyStat = exportStats
          ? SearchLongGauge.export(statNa Pref x + "update_latency_ms")
          : new SearchLongGauge(statNa Pref x + "update_latency_ms");
      updateSuccessCountStat = exportStats
          ? SearchCounter.export(statNa Pref x + "update_success_count")
          : SearchCounter.create(statNa Pref x + "update_success_count");
      updateFa lureCountStat = exportStats
          ? SearchCounter.export(statNa Pref x + "update_fa lure_count")
          : SearchCounter.create(statNa Pref x + "update_fa lure_count");
    }
  }

  pr vate f nal Amount<Long, T  > update nterval;
  pr vate f nal Amount<Long, T  >  n  alDelay;

  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  /**
   * Constructor
   * @param f lter F lter to be used to populate t  cac 
   * @param seg nt nfo Seg nt t  task  s respons ble for
   * @param update nterval T   bet en success ve updates
   * @param  n  alDelay T   before t  f rst update
   * @param update erat onCounter
   * @param dec der
   */
  publ c QueryCac UpdateTask(QueryCac F lter f lter,
                              Seg nt nfo seg nt nfo,
                              UserTable userTable,
                              Amount<Long, T  > update nterval,
                              Amount<Long, T  >  n  alDelay,
                              SearchCounter update erat onCounter,
                              Earlyb rdSearc rStats searc rStats,
                              Dec der dec der,
                              Cr  calExcept onHandler cr  calExcept onHandler,
                              Clock clock) {
    super(update erat onCounter, clock);
    t .f lter = f lter;
    t .seg nt nfo = seg nt nfo;
    t .userTable = userTable;
    t .ranOnce = false;
    t .update nterval = update nterval;
    t . n  alDelay =  n  alDelay;
    t .stats = setupStats();
    t .f lterAndSeg nt = Str ng.format(
        "QueryCac F lter: %s | Seg nt: %d",
        f lter.getF lterNa (), seg nt nfo.getT  Sl ce D());
    t .searc rStats = searc rStats;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .dec der = dec der;
  }

  @Overr de
  protected vo d runOne erat on() {
    try {
       f (LOG. sDebugEnabled()) {
        LOG.debug(
            "[{}] Updat ng w h query [{}] for t  {} th t  .",
            f lterAndSeg nt,
            f lter.getQueryStr ng(),
            stats.updateSuccessCountStat.get() + stats.updateFa lureCountStat.get() + 1
        );
         f (lastRunF n shT   != null) {
          LOG.debug(
              "[{}] Last run, {} th t  , f n s d {} secs ago. Should run every {} secs",
              f lterAndSeg nt,
              stats.updateSuccessCountStat.get() + stats.updateFa lureCountStat.get(),
              T  Un .NANOSECONDS.toSeconds(
                  System.nanoT  () - lastRunF n shT  .as(T  .NANOSECONDS)),
              update nterval.as(T  .SECONDS)
          );
        }
      }

      T  r t  r = new T  r(T  Un .M LL SECONDS);
      SearchResults nfo result = null;
      try {
        result = update();
      } catch (Except on e) {
        Str ng msg = "Fa led to update query cac  entry [" + f lter.getF lterNa ()
            + "] on seg nt [" + seg nt nfo.getT  Sl ce D() + "]";
        LOG.warn(msg, e);
      }

      long endT   = t  r.stop();
      updateStats(result, endT  );

       f (LOG. sDebugEnabled()) {
        LOG.debug("[{}] Updated  n {} ms, h  {} docs.",
            f lterAndSeg nt, endT  , stats.numH sStat.read());
      }
      // Need to catch throwable  re  nstead of except on so   handle errors l ke OutOf mory
      // See RB=528695 and SEARCH-4402
    } catch (Throwable t) {
      Str ng  ssage = Str ng.format("Got unexpected throwable  n %s", getClass().getNa ());
      LOG.error( ssage, t);

      // Wrap t  Throwable  n a FatalEarlyb rdExcept on to categor ze   and ensure  's
      // handled as a fatal except on
      cr  calExcept onHandler.handle(t ,
          new Earlyb rdExcept on( ssage, t));
    } f nally {
      // Earlyb rd won't beco  CURRENT unt l all tasks are run at least once.   don't want
      // fa led "run" (update) to prevent Earlyb rd from becom ng CURRENT. As long as all tasks
      // got a chance to run at least once,   are good to go.
      ranOnce = true;

      lastRunF n shT   = Amount.of(System.nanoT  (), T  .NANOSECONDS);
    }
  }

  publ c boolean ranOnce() {
    return ranOnce;
  }

  pr vate TaskStats setupStats() {
    return TASK_STATS.getUnc cked(statNa Pref x());
  }

  pr vate SearchResults nfo update() throws  OExcept on {
    // T re's a chance that t  Earlyb rdSeg nt of a Seg nt nfo to change at any
    // t  . T refore,  's not safe to operate seg nts on t  Seg nt nfo level.
    // On t  arch ve clusters   create a new Earlyb rdSeg nt and t n swap    n w n t re's
    // new data  nstead of append ng to an ex st ng Earlyb rdSeg nt.
    Earlyb rdSeg nt earlyb rdSeg nt = seg nt nfo.get ndexSeg nt();

    Earlyb rdS ngleSeg ntSearc r searc r = earlyb rdSeg nt.getSearc r(userTable);
     f (searc r == null) {
      LOG.warn("Unable to get searc r from Tw ter ndexManager for seg nt ["
          + seg nt nfo.getT  Sl ce D() + "]. Has   been dropped?");
      return null;
    }

    QueryCac ResultCollector collector = new QueryCac ResultCollector(
        searc r.getSc maSnapshot(), f lter, searc rStats, dec der, clock, 0);
    searc r.search(f lter.getLuceneQuery(), collector);

    QueryCac ResultForSeg nt cac Result = collector.getCac dResult();
    searc r.getTw ter ndexReader().getSeg ntData().updateQueryCac Result(
        f lter.getF lterNa (), cac Result);

    F N SHED_TASKS. ncre nt();

     f (LOG. sDebugEnabled()) {
      Term nat onTracker tracker = collector.getSearchRequest nfo().getTerm nat onTracker();
      LOG.debug(
          "[{}] Updat ng query f n s d, start t   ms  s {}, term nat on reason  s {}",
          f lterAndSeg nt,
          tracker.getLocalStartT  M ll s(),
          tracker.getEarlyTerm nat onState().getTerm nat onReason());
    }

    return collector.getResults();
  }

  pr vate vo d updateStats(SearchResults nfo result, long endT  ) {
     f (result != null) {
      stats.numH sStat.set(result.getNumH sProcessed());
      stats.updateSuccessCountStat. ncre nt();
    } else {
      stats.updateFa lureCountStat. ncre nt();
    }
    stats.updateLatencyStat.set(endT  );
  }

  @V s bleForTest ng
  Str ng statNa Pref x() {
    //  f   use t  and try to d splay  n monv z "ts(part  on, s ngle_ nstance, querycac *)",
    // t  U  shows "Really expens ve query"  ssage.   can keep t  around for t  s w n  
    // want to start th ngs manually and debug.
    return "querycac _" + f lter.getF lterNa () + "_" + seg nt nfo.getT  Sl ce D() + "_";
  }

  publ c long getT  Sl ce D() {
    return seg nt nfo.getT  Sl ce D();
  }

  //////////////////////////
  // for un  tests only
  //////////////////////////
  @V s bleForTest ng
  Str ng getF lterNa ForTest() {
    return f lter.getF lterNa ();
  }

  @V s bleForTest ng
  Amount<Long, T  > getUpdate ntervalForTest() {
    return update nterval;
  }

  @V s bleForTest ng
  Amount<Long, T  > get n  alDelayForTest() {
    return  n  alDelay;
  }

  @V s bleForTest ng
  TaskStats getTaskStatsForTest() {
    return stats;
  }
}
