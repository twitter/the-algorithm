package com.tw ter.search.earlyb rd.part  on;

 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.seg nt.Seg ntDataProv der;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.search.earlyb rd.ut l.OneTaskSc duledExecutorManager;
 mport com.tw ter.search.earlyb rd.ut l.Per od cAct onParams;
 mport com.tw ter.search.earlyb rd.ut l.ShutdownWa T  Params;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * Part  onManager  s respons ble for  ndex ng data for a part  on,  nclud ng T ets and Users.
 */
publ c abstract class Part  onManager extends OneTaskSc duledExecutorManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Part  onManager.class);

  pr vate stat c f nal SearchCounter  GNORED_EXCEPT ONS =
      SearchCounter.export("part  on_manager_ gnored_except ons");

  pr vate stat c f nal Str ng PART T ON_MANAGER_THREAD_NAME = "Part  onManager";
  pr vate stat c f nal boolean THREAD_ S_DAEMON = true;
  protected stat c f nal Str ng  NDEX_CURRENT_SEGMENT = " ndex ng t  current seg nt";
  protected stat c f nal Str ng SETUP_QUERY_CACHE = "sett ng up query cac ";

  protected f nal Seg ntManager seg ntManager;
  protected f nal QueryCac Manager queryCac Manager;
  // Should be updated by  nfo read from ZK
  protected f nal Dynam cPart  onConf g dynam cPart  onConf g;

  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;

  pr vate boolean part  onManagerF rstLoop = true;

  publ c Part  onManager(QueryCac Manager queryCac Manager,
                          Seg ntManager seg ntManager,
                          Dynam cPart  onConf g dynam cPart  onConf g,
                          Sc duledExecutorServ ceFactory executorServ ceFactory,
                          Search ndex ng tr cSet search ndex ng tr cSet,
                          SearchStatsRece ver searchStatsRece ver,
                          Cr  calExcept onHandler cr  calExcept onHandler) {
    super(
        executorServ ceFactory,
        PART T ON_MANAGER_THREAD_NAME,
        THREAD_ S_DAEMON,
        Per od cAct onParams.w hF xedDelay(
          Earlyb rdConf g.get nt("t  _sl ce_roll_c ck_ nterval_ms", 500),
          T  Un .M LL SECONDS),
        ShutdownWa T  Params. ndef n ely(),
        searchStatsRece ver,
        cr  calExcept onHandler);

    t .seg ntManager = seg ntManager;
    t .queryCac Manager = queryCac Manager;
    t .dynam cPart  onConf g = dynam cPart  onConf g;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
  }

  /**
   * Runs t  part  on manager.
   */
  publ c f nal vo d run mpl() {
     f (part  onManagerF rstLoop) {
      try {
        testHookBeforeStartUp();
        startUp();
        val dateSeg nts();
        seg ntManager.logState("After startUp");
      } catch (Throwable t) {
        cr  calExcept onHandler.handle(t , t);
        shutDown ndex ng();
        throw new Runt  Except on("Part  onManager unhandled except on, stopp ng sc duler", t);
      }
    }

    try {
      testHookAfterSleep();
       ndex ngLoop(part  onManagerF rstLoop);
    } catch ( nterruptedExcept on e) {
      LOG.warn("Part  onManager thread  nterrupted, stop ng sc duler", e);
      shutDown ndex ng();
      throw new Runt  Except on("Part  onManager thread  nterrupted", e);
    } catch (Except on e) {
      LOG.error("Except on  n  ndex ng Part  onManager loop", e);
       GNORED_EXCEPT ONS. ncre nt();
    } catch (Throwable t) {
      LOG.error("Unhandled except on  n  ndex ng Part  onManager loop", t);
      cr  calExcept onHandler.handle(t , t);
      shutDown ndex ng();
      throw new Runt  Except on("Part  onManager unhandled except on, stopp ng sc duler", t);
    } f nally {
      part  onManagerF rstLoop = false;
    }
  }

  /**
   * Returns t  Seg ntDataProv der  nstance that w ll be used to fetch t   nformat on for all
   * seg nts.
   */
  publ c abstract Seg ntDataProv der getSeg ntDataProv der();

  /**
   * Starts up t  part  on manager.
   */
  protected abstract vo d startUp() throws Except on;

  /**
   * Runs one  ndex ng  erat on.
   *
   * @param f rstLoop Determ nes  f t   s t  f rst t   t   ndex ng loop  s runn ng.
   */
  protected abstract vo d  ndex ngLoop(boolean f rstLoop) throws Except on;

  /**
   * Shuts down all  ndex ng.
   */
  protected abstract vo d shutDown ndex ng();

  @Overr de
  publ c vo d shutdownComponent() {
    shutDown ndex ng();
  }

  /**
   * Not f es all ot r threads that t  part  on manager has beco  current ( e. has  ndexed all
   * ava lable events).
   */
  publ c vo d beco Current() {
    LOG. nfo("Part  onManager beca  current");
     f (Earlyb rdStatus. sStart ng()) {
      Earlyb rdStatus.setStatus(Earlyb rdStatusCode.CURRENT);
    } else {
      LOG.warn("Could not set statusCode to CURRENT from " + Earlyb rdStatus.getStatusCode());
    }

    // Now that  're done start ng up, set t  query cac  thread pool s ze to one.
    queryCac Manager.setWorkerPoolS zeAfterStartup();
  }

  protected vo d setupQueryCac  fNeeded() throws QueryParserExcept on {
    queryCac Manager.setupTasks fNeeded(seg ntManager);
  }

  // Only for tests, used for test ng except on handl ng
  pr vate stat c TestHook testHookBeforeStartUp;
  pr vate stat c TestHook testHookAfterSleep;

  pr vate stat c vo d testHookBeforeStartUp() throws Except on {
     f (Conf g.env ron nt sTest() && testHookBeforeStartUp != null) {
      testHookBeforeStartUp.run();
    }
  }

  pr vate stat c vo d testHookAfterSleep() throws Except on {
     f (Conf g.env ron nt sTest() && testHookAfterSleep != null) {
      testHookAfterSleep.run();
    }
  }

  @Overr de
  protected vo d runOne erat on() {
    try {
      run mpl();
    } catch (Throwable t) {
      LOG.error("Unhandled except on  n Part  onManager loop", t);
      throw new Runt  Except on(t.get ssage());
    }
  }

  publ c Search ndex ng tr cSet getSearch ndex ng tr cSet() {
    return search ndex ng tr cSet;
  }

  /**
   * Allows tests to run code before t  part  on manager starts up.
   *
   * @param testHook T  code to run before t  start up.
   */
  @V s bleForTest ng
  publ c stat c vo d setTestHookBeforeStartUp(TestHook testHook) {
     f (Conf g.env ron nt sTest()) {
      testHookBeforeStartUp = testHook;
    } else {
      throw new Runt  Except on("Try ng to set startup test hook  n non-test code!!");
    }
  }

  /**
   * Allows tests to run code before t   ndex ng loop.
   *
   * @param testHook T  code to run before t   ndex ng loop.
   */
  @V s bleForTest ng
  publ c stat c vo d setTestHookAfterSleep(TestHook testHook) {
     f (Conf g.env ron nt sTest()) {
      testHookAfterSleep = testHook;
    } else {
      throw new Runt  Except on("Try ng to set test hook  n non-test code!!");
    }
  }

  /**
   * An  nterface that allows tests to run code at var ous po nts  n t  Part  onManager's
   * lyfecycle.
   */
  @V s bleForTest ng
  publ c  nterface TestHook {
    /**
     * Def nes t  code that should be run.
     */
    vo d run() throws Except on;
  }

  /**
   * Allows tests to determ ne  f t  part  on manager  s all caught up.
   *
   * @return {@code true}  f t  part  on manager  s caught up, {@code false} ot rw se.
   */
  @V s bleForTest ng
  publ c abstract boolean  sCaughtUpForTests();

  @V s bleForTest ng
  protected vo d val dateSeg nts() throws Earlyb rdStartupExcept on {
    // T   s necessary because many tests rely on start ng part  on manager but not  ndex ng any
    // t ets. Ho ver,   do not want Earlyb rds to start  n product on  f t y are not serv ng any
    // t ets. (SEARCH-24238)
     f (Conf g.env ron nt sTest()) {
      return;
    }
    val dateSeg ntsForNonTest();
  }

  @V s bleForTest ng
  protected vo d val dateSeg ntsForNonTest() throws Earlyb rdStartupExcept on {
    // Subclasses can overr de t  and prov de add  onal c cks.
     f (seg ntManager.getNum ndexedDocu nts() == 0) {
      throw new Earlyb rdStartupExcept on("Earlyb rd has zero  ndexed docu nts.");
    }
  }
}
