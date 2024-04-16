package com.tw ter.search.earlyb rd.part  on;

 mport java. o.Closeable;
 mport java.ut l.Opt onal;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Stopwatch;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.conf g.Conf g;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.part  on.freshstartup.FreshStartupHandler;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

/**
 * Handles start ng an Earlyb rd from Kafka top cs.
 *
 * Currently very unopt m zed -- future vers ons w ll  mple nt parallel  ndex ng and load ng
 * ser al zed data from HDFS. See http://go/remov ng-dl-tdd.
 */
publ c class KafkaStartup  mple nts Earlyb rdStartup {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(KafkaStartup.class);

  pr vate f nal Earlyb rdKafkaConsu r earlyb rdKafkaConsu r;
  pr vate f nal StartupUserEvent ndexer startupUserEvent ndexer;
  pr vate f nal QueryCac Manager queryCac Manager;
  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal Earlyb rd ndexLoader earlyb rd ndexLoader;
  pr vate f nal FreshStartupHandler freshStartupHandler;
  pr vate f nal UserUpdatesStream ndexer userUpdatesStream ndexer;
  pr vate f nal UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal SearchLongGauge loaded ndex;
  pr vate f nal SearchLongGauge freshStartup;
  pr vate f nal Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;
  pr vate f nal Aud oSpaceEventsStream ndexer aud oSpaceEventsStream ndexer;
  pr vate f nal Cr  calExcept onHandler earlyb rdExcept onHandler;
  pr vate f nal SearchDec der dec der;

  pr vate stat c f nal Str ng FRESH_STARTUP = "fresh startup";
  pr vate stat c f nal Str ng  NGEST_UNT L_CURRENT = " ngest unt l current";
  pr vate stat c f nal Str ng LOAD_FLUSHED_ NDEX = "load flus d  ndex";
  pr vate stat c f nal Str ng SETUP_QUERY_CACHE = "sett ng up query cac ";
  pr vate stat c f nal Str ng USER_UPDATES_STARTUP = "user updates startup";
  pr vate stat c f nal Str ng AUD O_SPACES_STARTUP = "aud o spaces startup";
  pr vate stat c f nal Str ng BU LD_MULT _SEGMENT_TERM_D CT ONARY =
          "bu ld mult  seg nt term d ct onary";

  publ c KafkaStartup(
      Seg ntManager seg ntManager,
      Earlyb rdKafkaConsu r earlyb rdKafkaConsu r,
      StartupUserEvent ndexer startupUserEvent ndexer,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      Aud oSpaceEventsStream ndexer aud oSpaceEventsStream ndexer,
      QueryCac Manager queryCac Manager,
      Earlyb rd ndexLoader earlyb rd ndexLoader,
      FreshStartupHandler freshStartupHandler,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      Cr  calExcept onHandler earlyb rdExcept onHandler,
      SearchDec der dec der
  ) {
    t .seg ntManager = seg ntManager;
    t .earlyb rdKafkaConsu r = earlyb rdKafkaConsu r;
    t .startupUserEvent ndexer = startupUserEvent ndexer;
    t .queryCac Manager = queryCac Manager;
    t .earlyb rd ndexLoader = earlyb rd ndexLoader;
    t .freshStartupHandler = freshStartupHandler;
    t .userUpdatesStream ndexer = userUpdatesStream ndexer;
    t .userScrubGeoEventStream ndexer = userScrubGeoEventStream ndexer;
    t .aud oSpaceEventsStream ndexer = aud oSpaceEventsStream ndexer;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .loaded ndex = SearchLongGauge.export("kafka_startup_loaded_ ndex");
    t .freshStartup = SearchLongGauge.export("fresh_startup");
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .earlyb rdExcept onHandler = earlyb rdExcept onHandler;
    t .dec der = dec der;
    freshStartup.set(0);
  }

  pr vate vo d userEventsStartup() {
    LOG. nfo("Start  ndex ng user events.");

    startupUserEvent ndexer. ndexAllEvents();

    LOG. nfo("F n s d load ng/ ndex ng user events.");

    // User updates are now current, keep t m current by cont nu ng to  ndex from t  stream.
    LOG. nfo("Start ng to run UserUpdatesStream ndexer");
    new Thread(userUpdatesStream ndexer::run, "userupdates-stream- ndexer").start();

     f (Earlyb rdConf g.consu UserScrubGeoEvents()) {
      // User scrub geo events are now current,
      // keep t m current by cont nu ng to  ndex from t  stream.
      LOG. nfo("Start ng to run UserScrubGeoEventsStream ndexer");
      new Thread(userScrubGeoEventStream ndexer::run,
          "userScrubGeoEvents-stream- ndexer").start();
    }
  }

  pr vate vo d loadAud oSpaceEvents() {
    LOG. nfo(" ndex aud o space events...");
    Earlyb rdStatus.beg nEvent(AUD O_SPACES_STARTUP,
        search ndex ng tr cSet.startup nAud oSpaceEvent ndexer);

     f (aud oSpaceEventsStream ndexer == null) {
      LOG.error("Null aud oSpaceEventsStream ndexer");
      return;
    }

     f (dec der. sAva lable("enable_read ng_aud o_space_events")) {
      Stopwatch stopwatch = Stopwatch.createStarted();
      aud oSpaceEventsStream ndexer.seekToBeg nn ng();
      aud oSpaceEventsStream ndexer.readRecordsUnt lCurrent();
      LOG. nfo("F n s d read ng aud o spaces  n {}", stopwatch);
      aud oSpaceEventsStream ndexer.pr ntSummary();

      new Thread(aud oSpaceEventsStream ndexer::run,
          "aud oSpaceEvents-stream- ndexer").start();
    } else {
      LOG. nfo("Read ng aud o space events not enabled");
    }

    Earlyb rdStatus.endEvent(AUD O_SPACES_STARTUP,
        search ndex ng tr cSet.startup nAud oSpaceEvent ndexer);
  }

  pr vate vo d t etsAndUpdatesStartup() throws Earlyb rdStartupExcept on {
    LOG. nfo(" ndex t ets and updates...");
    Earlyb rdStatus.beg nEvent(LOAD_FLUSHED_ NDEX,
        search ndex ng tr cSet.startup nLoadFlus d ndex);
    Earlyb rd ndex  ndex;

    // Set w n   want to get a server from start ng to ready qu ckly for develop nt
    // purposes.
    boolean fastDevStartup = Earlyb rdConf g.getBool("fast_dev_startup");

    Opt onal<Earlyb rd ndex> opt ndex = Opt onal.empty();
     f (!fastDevStartup) {
      opt ndex = earlyb rd ndexLoader.load ndex();
    }

     f (opt ndex. sPresent()) {
      loaded ndex.set(1);
      LOG. nfo("Loaded an  ndex.");
       ndex = opt ndex.get();
      Earlyb rdStatus.endEvent(LOAD_FLUSHED_ NDEX,
          search ndex ng tr cSet.startup nLoadFlus d ndex);
    } else {
      LOG. nfo("D dn't load an  ndex,  ndex ng from scratch.");
      freshStartup.set(1);
      boolean parallel ndexFromScratch = Earlyb rdConf g.getBool(
          "parallel_ ndex_from_scratch");
      LOG. nfo("parallel_ ndex_from_scratch: {}", parallel ndexFromScratch);
      Earlyb rdStatus.beg nEvent(FRESH_STARTUP,
          search ndex ng tr cSet.startup nFreshStartup);
      try {
         f (fastDevStartup) {
           ndex = freshStartupHandler.fast ndexFromScratchForDevelop nt();
        } else  f (parallel ndexFromScratch) {
           ndex = freshStartupHandler.parallel ndexFromScratch();
        } else {
           ndex = freshStartupHandler. ndexFromScratch();
        }
      } catch (Except on ex) {
        throw new Earlyb rdStartupExcept on(ex);
      } f nally {
        Earlyb rdStatus.endEvent(FRESH_STARTUP,
            search ndex ng tr cSet.startup nFreshStartup);
      }
    }

    LOG. nfo(" ndex has {} seg nts.",  ndex.getSeg nt nfoL st().s ze());
     f ( ndex.getSeg nt nfoL st().s ze() > 0) {
      LOG. nfo(" nsert ng seg nts  nto Seg ntManager");
      for (Seg nt nfo seg nt nfo :  ndex.getSeg nt nfoL st()) {
        seg ntManager.putSeg nt nfo(seg nt nfo);
      }

      earlyb rdKafkaConsu r.prepareAfterStart ngW h ndex(
           ndex.getMax ndexedT et d()
      );
    }

    // Bu ld t  Mult  seg nt term d ct onary before catch ng up on  ndex ng to ensure that t 
    // seg nts won't roll and delete t  oldest seg nt wh le a mult  seg nt term d ct onary that
    //  ncludes that seg nt  s be ng bu lt.
    bu ldMult Seg ntTermD ct onary();

    seg ntManager.logState("Start ng  ngestUnt lCurrent");
    LOG. nfo("part al updates  ndexed: {}", seg ntManager.getNumPart alUpdates());
    Earlyb rdStatus.beg nEvent( NGEST_UNT L_CURRENT,
        search ndex ng tr cSet.startup n ngestUnt lCurrent);

    earlyb rdKafkaConsu r. ngestUnt lCurrent( ndex.getT etOffset(),  ndex.getUpdateOffset());

    val dateSeg nts();
    seg ntManager.logState(" ngestUnt lCurrent  s done");
    LOG. nfo("part al updates  ndexed: {}", seg ntManager.getNumPart alUpdates());
    Earlyb rdStatus.endEvent( NGEST_UNT L_CURRENT,
        search ndex ng tr cSet.startup n ngestUnt lCurrent);
    new Thread(earlyb rdKafkaConsu r::run, "earlyb rd-kafka-consu r").start();
  }

  protected vo d val dateSeg nts() throws Earlyb rdStartupExcept on {
     f (!Conf g.env ron nt sTest()) {
      // Unfortunately, many tests start Earlyb rds w h 0  ndexed docu nts, so   d sable t 
      // c ck  n tests.
      val dateSeg ntsForNonTest();
    }
  }

  protected vo d val dateSeg ntsForNonTest() throws Earlyb rdStartupExcept on {
    // SEARCH-24123: Prevent Earlyb rd from start ng  f t re are no  ndexed docu nts.
     f (seg ntManager.getNum ndexedDocu nts() == 0) {
      throw new Earlyb rdStartupExcept on("Earlyb rd has zero  ndexed docu nts.");
    }
  }

  pr vate vo d queryCac Startup() throws Earlyb rdStartupExcept on {
    Earlyb rdStatus.beg nEvent(SETUP_QUERY_CACHE,
        search ndex ng tr cSet.startup nQueryCac Updates);
    try {
      queryCac Manager.setupTasks fNeeded(seg ntManager);
    } catch (QueryParserExcept on e) {
      LOG.error("Except on w n sett ng up query cac  tasks");
      throw new Earlyb rdStartupExcept on(e);
    }

    queryCac Manager.wa Unt lAllQueryCac sAreBu lt();

    // Pr nt t  s zes of t  query cac s so that   can see that t y're bu lt.
     erable<Seg nt nfo> seg nt nfos =
        seg ntManager.getSeg nt nfos(Seg ntManager.F lter.All, Seg ntManager.Order.OLD_TO_NEW);
    seg ntManager.logState("After bu ld ng query cac s");
    for (Seg nt nfo seg nt nfo : seg nt nfos) {
      LOG. nfo("Seg nt: {}, Total card nal y: {}", seg nt nfo.getSeg ntNa (),
          seg nt nfo.get ndexSeg nt().getQueryCac sCard nal y());
    }

    //  're done bu ld ng t  query cac s for all seg nts, and t  earlyb rd  s ready to beco 
    // current. Restr ct all future query cac  task runs to one s ngle core, to make sure  
    // searc r threads are not  mpacted.
    queryCac Manager.setWorkerPoolS zeAfterStartup();
    Earlyb rdStatus.endEvent(SETUP_QUERY_CACHE,
        search ndex ng tr cSet.startup nQueryCac Updates);
  }

  /**
   * Closes all currently runn ng  ndexers.
   */
  @V s bleForTest ng
  publ c vo d shutdown ndex ng() {
    LOG. nfo("Shutt ng down KafkaStartup.");

    earlyb rdKafkaConsu r.close();
    userUpdatesStream ndexer.close();
    userScrubGeoEventStream ndexer.close();
    // Note that t  QueryCac Manager  s shut down  n Earlyb rdServer::shutdown.
  }

  pr vate vo d bu ldMult Seg ntTermD ct onary() {
    Earlyb rdStatus.beg nEvent(BU LD_MULT _SEGMENT_TERM_D CT ONARY,
            search ndex ng tr cSet.startup nMult Seg ntTermD ct onaryUpdates);
    Stopwatch stopwatch = Stopwatch.createStarted();
    LOG. nfo("Bu ld ng mult  seg nt term d ct onary");
    mult Seg ntTermD ct onaryManager.bu ldD ct onary();
    LOG. nfo("Done w h bu ld ng mult  seg nt term d ct onary  n {}", stopwatch);
    Earlyb rdStatus.endEvent(BU LD_MULT _SEGMENT_TERM_D CT ONARY,
            search ndex ng tr cSet.startup nMult Seg ntTermD ct onaryUpdates);
  }

  pr vate vo d parallel ndex ngStartup() throws Earlyb rdStartupExcept on {
    Thread userEventsThread = new Thread(t ::userEventsStartup, " ndex-user-events-startup");
    Thread t etsAndUpdatesThread = new Thread(() -> {
      try {
        t etsAndUpdatesStartup();
      } catch (Earlyb rdStartupExcept on e) {
        earlyb rdExcept onHandler.handle(t , e);
      }
    }, " ndex-t ets-and-updates-startup");
    Thread aud oSpaceEventsThread = new Thread(t ::loadAud oSpaceEvents,
        " ndex-aud o-space-events-startup");
    userEventsThread.start();
    t etsAndUpdatesThread.start();
    aud oSpaceEventsThread.start();

    try {
      userEventsThread.jo n();
    } catch ( nterruptedExcept on e) {
      throw new Earlyb rdStartupExcept on(" nterrupted wh le  ndex ng user events");
    }
    try {
      t etsAndUpdatesThread.jo n();
    } catch ( nterruptedExcept on e) {
      throw new Earlyb rdStartupExcept on(" nterrupted wh le  ndex ng t ets and updates");
    }
    try {
      aud oSpaceEventsThread.jo n();
    } catch ( nterruptedExcept on e) {
      throw new Earlyb rdStartupExcept on(" nterrupted wh le  ndex ng aud o space events");
    }
  }

  /**
   * Does startups and starts  ndex ng. Returns w n t  earlyb rd
   *  s current.
   */
  @Overr de
  publ c Closeable start() throws Earlyb rdStartupExcept on {
    parallel ndex ngStartup();
    queryCac Startup();

    Earlyb rdStatus.setStatus(Earlyb rdStatusCode.CURRENT);

    return t ::shutdown ndex ng;
  }
}
