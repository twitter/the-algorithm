package com.tw ter.search.earlyb rd.querycac ;

 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect.L sts;
 mport com.google.common.pr m  ves.Longs;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.F lter;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.Order;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.Seg ntUpdateL stener;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * Ma n class to manage Earlyb rd's QueryCac .
 *
 *  n  al ze t  QueryCac  and new seg nts are not f ed to t  QueryCac  subsystem
 * through t  class.
 *
 * T  class  s thread-safe w n call ng  thods that mod fy t  l st of tasks that
 *  're execut ng or w n   need to traverse all tasks and c ck so th ng. T  way
 * thread-safety  s ach eved  re r ght now  s through mak ng  thods synchron zed.
 */
publ c class QueryCac Manager  mple nts Seg ntUpdateL stener {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(QueryCac Manager.class);

  pr vate stat c f nal Amount<Long, T  > ZERO_SECONDS = Amount.of(0L, T  .SECONDS);

  pr vate f nal boolean enabled = Earlyb rdConf g.getBool("querycac ", false);

  // seg nts are removed from Seg nt nfoMap laz ly, and t re may be a wa  t  .
  // So, beware that t re's short per od of t   w re t re's more seg nts than
  // maxEnabledSeg nts.
  pr vate f nal  nt maxEnabledSeg nts;

  pr vate f nal UserTable userTable;
  pr vate f nal UserScrubGeoMap userScrubGeoMap;
  pr vate f nal Earlyb rd ndexConf g  ndexConf g;
  pr vate QueryCac Updater updater;
  pr vate f nal Map<Str ng, QueryCac F lter> f lters;
  pr vate f nal Sc duledExecutorServ ceFactory updaterSc duledExecutorServ ceFactory;

  pr vate f nal SearchStatsRece ver searchStatsRece ver;

  pr vate stat c f nal SearchLongGauge NUM_CACHE_ENTRY_STAT =
      SearchLongGauge.export("querycac _num_entr es");

  pr vate stat c f nal SearchCounter NUM_UPDATE_SEGMENTS_CALLS =
      SearchCounter.export("querycac _num_update_seg nts_calls");

  pr vate volat le boolean d dSetup = false;

  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate f nal Dec der dec der;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal Clock clock;

  publ c QueryCac Manager(
      QueryCac Conf g conf g,
      Earlyb rd ndexConf g  ndexConf g,
       nt maxEnabledSeg nts,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Sc duledExecutorServ ceFactory updaterSc duledExecutorServ ceFactory,
      SearchStatsRece ver searchStatsRece ver,
      Earlyb rdSearc rStats searc rStats,
      Dec der dec der,
      Cr  calExcept onHandler cr  calExcept onHandler,
      Clock clock) {

    Precond  ons.c ckArgu nt(maxEnabledSeg nts > 0);

    QueryCac Conf g queryCac Conf g = conf g;
     f (queryCac Conf g == null) {
      queryCac Conf g = new QueryCac Conf g(searchStatsRece ver);
    }
    t . ndexConf g =  ndexConf g;
    t .maxEnabledSeg nts = maxEnabledSeg nts;
    t .userTable = userTable;
    t .userScrubGeoMap = userScrubGeoMap;
    t .updaterSc duledExecutorServ ceFactory = updaterSc duledExecutorServ ceFactory;
    t .searchStatsRece ver = searchStatsRece ver;
    t .searc rStats = searc rStats;
    t .f lters = new HashMap<>();
    t .dec der = dec der;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t .clock = clock;
    for (QueryCac F lter f lter : queryCac Conf g.f lters()) {
      f lters.put(f lter.getF lterNa (), f lter);
    }
    NUM_CACHE_ENTRY_STAT.set(f lters.s ze());
  }

  publ c Earlyb rd ndexConf g get ndexConf g() {
    return  ndexConf g;
  }

  publ c UserScrubGeoMap getUserScrubGeoMap() {
    return userScrubGeoMap;
  }

  /** Setup all update tasks at once, should only be called after Earlyb rd has loaded/ ndexed all
   * seg nts dur ng start-up
   *
   * Only t  f rst call to t  funct on has effect, subsequent calls are no-ops
   */
  publ c vo d setupTasks fNeeded(Seg ntManager seg ntManager)
      throws QueryParserExcept on {
    setupTasks(
        seg ntManager.getSeg nt nfos(F lter.All, Order.OLD_TO_NEW),
        seg ntManager.getEarlyb rd ndexConf g().getCluster());
  }

  @V s bleForTest ng
  synchron zed vo d setupTasks(
       erable<Seg nt nfo> newSeg nts,
      Earlyb rdCluster earlyb rdCluster) throws QueryParserExcept on {
    // Setup needs to be done only once after all  ndex caught up.
     f (d dSetup) {
      return;
    }

    LOG. nfo("Sett ng up {} query cac  tasks", f lters.values().s ze());

    for (QueryCac F lter f lter : f lters.values()) {
      f lter.setup(t , userTable, earlyb rdCluster);
    }

     f (!enabled()) {
      // Note that t  def n  on of d sabl ng t  query cac s  re  s "don't compute t  cac s".
      //   st ll load t  quer es from t  .yml,   st ll rewr e search quer es to use
      // cac d quer es. T  reason   are choos ng t  def n  on  s that  's so what s mpler
      // to  mple nt (no need to turn off rewr  ng) and because   m ght get external quer es that
      // conta n cac d f lters (t y're l sted  n go/searchsyntax).
      //
      //  f   need a str cter def n  on of turn ng off query cac s,   can  mple nt   too, or
      // just t ghten t  one.
      return;
    }

    Precond  ons.c ckState(updater == null);
    updater = new QueryCac Updater(
        f lters.values(),
        updaterSc duledExecutorServ ceFactory,
        userTable,
        searchStatsRece ver,
        searc rStats,
        dec der,
        cr  calExcept onHandler,
        clock);

    LOG. nfo("F n s d sett ng up query cac  updater.");

    sc duleTasks(newSeg nts, false);

    d dSetup = true;
  }

  pr vate vo d sc duleTasks( erable<Seg nt nfo> seg nts, boolean  sCurrent) {
    L st<Seg nt nfo> sortedSeg nts = L sts.newArrayL st(seg nts);
    Collect ons.sort(sortedSeg nts, (o1, o2) -> {
      // sort new to old (o2 and o1 are reversed  re)
      return Longs.compare(o2.getT  Sl ce D(), o1.getT  Sl ce D());
    });

    LOG. nfo("Sc dul ng tasks for {} seg nts.", sortedSeg nts.s ze());

    for ( nt seg nt ndex = 0; seg nt ndex < sortedSeg nts.s ze(); ++seg nt ndex) {
      Seg nt nfo seg nt nfo = sortedSeg nts.get(seg nt ndex);
       f (seg nt ndex == maxEnabledSeg nts) {
        LOG.warn("Tr ed to add more seg nts than MaxEnabledSeg nts (" + maxEnabledSeg nts
            + "). Removed oldest seg nt " + seg nt nfo.getT  Sl ce D());
        cont nue;
      }
      addQueryCac TasksForSeg nt(seg nt nfo, seg nt ndex, ! sCurrent);
    }
  }

  /**
   * Rebu lds t  query cac  for t  g ven seg nt after   was opt m zed.
   */
  publ c synchron zed vo d rebu ldQueryCac sAfterSeg ntOpt m zat on(
      Seg nt nfo opt m zedSeg nt) {
    Precond  ons.c ckState(opt m zedSeg nt.get ndexSeg nt(). sOpt m zed(),
                             "Seg nt " + opt m zedSeg nt.getSeg ntNa () + "  s not opt m zed.");

     f (!d dSetup) {
      // Once    ndex ng  s current,  'll just start tasks for all seg nts, opt m zed or not.
      // Before that event,   don't do anyth ng query cac  related.
      LOG. nfo("Haven't done  n  al setup, return ng.");
      return;
    }

    LOG. nfo("Rebu ld ng query cac s for opt m zed seg nt {}",
        opt m zedSeg nt.getSeg ntNa ());

    // T  opt m zed seg nt should always be t  1st seg nt (t  current seg nt has  ndex 0).
    Stopwatch stopwatch = Stopwatch.createStarted();
    updater.removeAllTasksForSeg nt(opt m zedSeg nt);
    addQueryCac TasksForSeg nt(opt m zedSeg nt, 1, true);

    wh le (!updater.allTasksRanForSeg nt(opt m zedSeg nt)) {
      try {
        Thread.sleep(1000);
      } catch ( nterruptedExcept on e) {
        //  gnore
      }
    }

    LOG. nfo("Rebu ld ng all query cac s for t  opt m zed seg nt {} took {}.",
             opt m zedSeg nt.getSeg ntNa (), stopwatch);
  }

  /**
   * Block unt l all t  tasks  ns de t  manager have ran at least once.
   */
  publ c vo d wa Unt lAllQueryCac sAreBu lt() {
    LOG. nfo("Wa  ng unt l all query cac s are bu lt...");

    Stopwatch stopwatch = Stopwatch.createStarted();
    wh le (!allTasksRan()) {
      try {
        Thread.sleep(1000);
      } catch ( nterruptedExcept on ex) {
        Thread.currentThread(). nterrupt();
      }
    }

    LOG. nfo("Ran query cac  tasks  n: {}", stopwatch);
  }

  pr vate vo d addQueryCac TasksForSeg nt(
      Seg nt nfo seg nt nfo,  nt seg nt ndex, boolean sc dule m d ately) {
    LOG. nfo("Add ng query cac  tasks for seg nt {}.", seg nt nfo.getT  Sl ce D());
    double update ntervalMult pl er =
        Earlyb rdConf g.getDouble("query_cac _update_ nterval_mult pl er", 1.0);
    for (QueryCac F lter f lter : f lters.values()) {
      Amount<Long, T  > update ntervalFromConf g = f lter.getUpdate nterval(seg nt ndex);
      Amount<Long, T  > update nterval = Amount.of(
          (long) (update ntervalFromConf g.getValue() * update ntervalMult pl er),
          update ntervalFromConf g.getUn ());

      Amount<Long, T  >  n  alDelay = sc dule m d ately ? ZERO_SECONDS : update nterval;
      updater.addTask(f lter, seg nt nfo, update nterval,  n  alDelay);
    }
  }

  /**
   * Not fy QueryCac Manager of a new l st of seg nts   currently have, so that cac  tasks
   * can be updated.
   *
   * @param seg nts fresh l st of all seg nts
   *
   * All ex st ng tasks w ll be canceled/removed/destroyed, new tasks w ll be created for all
   * seg nts.
   */
  @Overr de
  publ c synchron zed vo d update(Collect on<Seg nt nfo> seg nts, Str ng  ssage) {
     f (!enabled()) {
      return;
    }

    // T  manager  s created r ght at t  beg nn ng of a startup. Before   set   up,
    //  'll read t ets and create seg nts and t refore t   thod w ll be called.
    //   don't want to start comput ng query cac s dur ng that t  , so   just return.
     f (!d dSetup) {
      return;
    }

    NUM_UPDATE_SEGMENTS_CALLS. ncre nt();

    LOG. nfo("Resc dul ng all query cac  tasks ({}). Number of seg nts rece ved = {}.",
         ssage, seg nts.s ze());
    updater.clearTasks(); // cancel and remove all sc duled tasks

    //  f Earlyb rd  s st ll start ng up, and   get a part  on roll, don't delay rebu ld ng
    // t  query cac .
    boolean  sCurrent = Earlyb rdStatus.getStatusCode() == Earlyb rdStatusCode.CURRENT;
    sc duleTasks(seg nts,  sCurrent);
  }

  /**
   * Determ nes  f all query cac  tasks ran at least once (even  f t y fa led).
   */
  publ c synchron zed boolean allTasksRan() {
    return (!(enabled() && d dSetup)) || updater.allTasksRan();
  }

  /**
   * Determ nes  f t  query cac  manager  s enabled.
   */
  publ c boolean enabled() {
    return enabled;
  }

  /**
   * Returns t  query cac  f lter w h t  g ven na .
   */
  publ c QueryCac F lter getF lter(Str ng f lterNa ) {
    return f lters.get(f lterNa );
  }

  /**
   * Shuts down t  query cac  manager.
   */
  publ c synchron zed vo d shutdown() throws  nterruptedExcept on {
    LOG. nfo("Shutt ng down QueryCac Manager");
     f (updater != null) {
      updater.shutdown();
      updater = null;
    }
    d dSetup = false; // needed for un  test
  }

  /**
   * After startup,   want only one thread to update t  query cac .
   */
  publ c vo d setWorkerPoolS zeAfterStartup() {
     f (t .updater != null) {
      t .updater.setWorkerPoolS zeAfterStartup();
    }
  }

  publ c Dec der getDec der() {
    return t .dec der;
  }

  //////////////////////////
  // for un  tests only
  //////////////////////////
  QueryCac Updater getUpdaterForTest() {
    return updater;
  }
  Map<Str ng, QueryCac F lter> getCac MapForTest() {
    return f lters;
  }
}
