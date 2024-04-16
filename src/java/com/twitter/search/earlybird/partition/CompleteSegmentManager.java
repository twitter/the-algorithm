package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.funct on.Suppl er;

 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. ndex ng.thr ftjava.Thr ftVers onedEvents;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdProperty;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.seg nt.Seg ntDataProv der;

/**
 * CompleteSeg ntManager  s used to parallel ze  ndex ng of complete (not part al) seg nts
 * on startup.    also populates t  f elds used by t  Part  onManager.
 */
publ c class CompleteSeg ntManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(CompleteSeg ntManager.class);

  pr vate stat c f nal Str ng  NDEX_COMPLETED_SEGMENTS =
      " ndex ng, opt m z ng and flush ng complete seg nts";
  pr vate stat c f nal Str ng LOAD_COMPLETED_SEGMENTS = "load ng complete seg nts";
  pr vate stat c f nal Str ng  NDEX_UPDATES_FOR_COMPLETED_SEGMENTS =
      " ndex ng updates for complete seg nts";
  pr vate stat c f nal Str ng BU LD_MULT _SEGMENT_TERM_D CT =
      "bu ld mult  seg nt term d ct onar es";

  // Max number of seg nts be ng loaded /  ndexed concurrently.
  pr vate f nal  nt maxConcurrentSeg nt ndexers =
      Earlyb rdProperty.MAX_CONCURRENT_SEGMENT_ NDEXERS.get(3);

  // T  state   are bu ld ng.
  protected f nal Seg ntDataProv der seg ntDataProv der;
  pr vate f nal  nstru ntedQueue<Thr ftVers onedEvents> retryQueue;

  pr vate f nal UserUpdatesStream ndexer userUpdatesStream ndexer;
  pr vate f nal UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer;

  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Clock clock;
  pr vate Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;

  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;

  pr vate boolean  nterrupted = false;

  publ c CompleteSeg ntManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      Seg ntDataProv der seg ntDataProv der,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      Seg ntManager seg ntManager,
       nstru ntedQueue<Thr ftVers onedEvents> retryQueue,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Clock clock,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      Seg ntSyncConf g seg ntSyncConf g,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t .zkTryLockFactory = zooKeeperTryLockFactory;
    t .seg ntDataProv der = seg ntDataProv der;
    t .userUpdatesStream ndexer = userUpdatesStream ndexer;
    t .userScrubGeoEventStream ndexer = userScrubGeoEventStream ndexer;
    t .seg ntManager = seg ntManager;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .clock = clock;
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .seg ntSyncConf g = seg ntSyncConf g;
    t .retryQueue = retryQueue;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
  }

  /**
   *  ndexes all user events.
   */
  publ c vo d  ndexUserEvents() {
    LOG. nfo("Load ng/ ndex ng user events.");
    StartupUserEvent ndexer startupUserEvent ndexer = new StartupUserEvent ndexer(
        search ndex ng tr cSet,
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        seg ntManager,
        clock
    );

    startupUserEvent ndexer. ndexAllEvents();
    LOG. nfo("F n s d load ng/ ndex ng user events.");
  }

  /**
   * Loads or  ndexes from scratch all complete seg nts.
   *
   * @param seg ntsTo ndexProv der A suppl er that prov des t  l st of all complete seg nts.
   */
  publ c vo d  ndexCompleteSeg nts(
      Suppl er< erable<Seg nt nfo>> seg ntsTo ndexProv der) throws Except on {
    L st<Thread> seg nt ndexers = L sts.newArrayL st();

    Earlyb rdStatus.beg nEvent(
         NDEX_COMPLETED_SEGMENTS, search ndex ng tr cSet.startup n ndexCompletedSeg nts);
    wh le (! nterrupted && !Thread.currentThread(). s nterrupted()) {
      try {
        // Get t  refres d l st of local seg nt databases.
        seg ntManager.updateSeg nts(seg ntDataProv der.newSeg ntL st());
         erator<Seg nt nfo> seg ntsTo ndex = seg ntsTo ndexProv der.get(). erator();

        // Start up to max concurrent seg nt  ndexers.
        seg nt ndexers.clear();
        wh le (seg ntsTo ndex.hasNext() && seg nt ndexers.s ze() < maxConcurrentSeg nt ndexers) {
          Seg nt nfo nextSeg nt = seg ntsTo ndex.next();
           f (!nextSeg nt. sComplete()) {
            Thread thread = new Thread(new S ngleSeg nt ndexer(nextSeg nt),
                                       "startup-seg nt- ndexer-" + nextSeg nt.getSeg ntNa ());
            thread.start();
            seg nt ndexers.add(thread);
          }
        }

        // No rema n ng  ndexer threads,  're done.
         f (seg nt ndexers.s ze() == 0) {
          LOG. nfo("F n s d  ndex ng complete seg nts");
          Earlyb rdStatus.endEvent(
               NDEX_COMPLETED_SEGMENTS, search ndex ng tr cSet.startup n ndexCompletedSeg nts);
          break;
        }

        // Wa  for threads to complete fully.
        LOG. nfo("Started {}  ndex ng threads", seg nt ndexers.s ze());
        for (Thread thread : seg nt ndexers) {
          thread.jo n();
        }
        LOG. nfo("Jo ned all {}  ndex ng threads", seg nt ndexers.s ze());
      } catch ( OExcept on e) {
        LOG.error(" OExcept on  n Seg ntStartupManager loop", e);
      } catch ( nterruptedExcept on e) {
         nterrupted = true;
        LOG.error(" nterrupted jo n ng seg nt  ndexer thread", e);
      }
    }
  }

  /**
   * Loads all g ven complete seg nts.
   *
   * @param completeSeg nts T  l st of all complete seg nts to be loaded.
   */
  publ c vo d loadCompleteSeg nts(L st<Seg nt nfo> completeSeg nts) throws Except on {
     f (! nterrupted && !Thread.currentThread(). s nterrupted()) {
      LOG. nfo("Start ng to load {} complete seg nts.", completeSeg nts.s ze());
      Earlyb rdStatus.beg nEvent(
          LOAD_COMPLETED_SEGMENTS, search ndex ng tr cSet.startup nLoadCompletedSeg nts);

      L st<Thread> seg ntThreads = L sts.newArrayL st();
      L st<Seg nt nfo> seg ntsToBeLoaded = L sts.newArrayL st();
      for (Seg nt nfo seg nt nfo : completeSeg nts) {
         f (seg nt nfo. sEnabled()) {
          seg ntsToBeLoaded.add(seg nt nfo);
          Thread seg ntLoaderThread = new Thread(
              () -> new Seg ntLoader(seg ntSyncConf g, cr  calExcept onHandler)
                  .load(seg nt nfo),
              "startup-seg nt-loader-" + seg nt nfo.getSeg ntNa ());
          seg ntThreads.add(seg ntLoaderThread);
          seg ntLoaderThread.start();
        } else {
          LOG. nfo("W ll not load seg nt {} because  's d sabled.", seg nt nfo.getSeg ntNa ());
        }
      }

      for (Thread seg ntLoaderThread : seg ntThreads) {
        seg ntLoaderThread.jo n();
      }

      for (Seg nt nfo seg nt nfo : seg ntsToBeLoaded) {
         f (!seg nt nfo.getSync nfo(). sLoaded()) {
          // Throw an except on  f a seg nt could not be loaded:   do not want earlyb rds to
          // startup w h m ss ng seg nts.
          throw new Runt  Except on("Could not load seg nt " + seg nt nfo.getSeg ntNa ());
        }
      }

      LOG. nfo("Loaded all complete seg nts, start ng  ndex ng all updates.");
      Earlyb rdStatus.beg nEvent(
           NDEX_UPDATES_FOR_COMPLETED_SEGMENTS,
          search ndex ng tr cSet.startup n ndexUpdatesForCompletedSeg nts);

      //  ndex all updates for all complete seg nts unt l  're fully caught up.
       f (!Earlyb rdCluster. sArch ve(seg ntManager.getEarlyb rd ndexConf g().getCluster())) {
        seg ntThreads.clear();
        for (Seg nt nfo seg nt nfo : completeSeg nts) {
           f (seg nt nfo. sEnabled()) {
            Thread seg ntUpdatesThread = new Thread(
                () -> new S mpleUpdate ndexer(
                    seg ntDataProv der.getSeg ntDataReaderSet(),
                    search ndex ng tr cSet,
                    retryQueue,
                    cr  calExcept onHandler). ndexAllUpdates(seg nt nfo),
                "startup-complete-seg nt-update- ndexer-" + seg nt nfo.getSeg ntNa ());
            seg ntThreads.add(seg ntUpdatesThread);
            seg ntUpdatesThread.start();
          } else {
            LOG. nfo("W ll not  ndex updates for seg nt {} because  's d sabled.",
                     seg nt nfo.getSeg ntNa ());
          }
        }

        for (Thread seg ntUpdatesThread : seg ntThreads) {
          seg ntUpdatesThread.jo n();
        }
      }
      LOG. nfo(" ndexed updates for all complete seg nts.");
      Earlyb rdStatus.endEvent(
           NDEX_UPDATES_FOR_COMPLETED_SEGMENTS,
          search ndex ng tr cSet.startup n ndexUpdatesForCompletedSeg nts);

      Earlyb rdStatus.endEvent(
          LOAD_COMPLETED_SEGMENTS, search ndex ng tr cSet.startup nLoadCompletedSeg nts);
    }
  }

  /**
   * Bu lds t  term d ct onary that spans all earlyb rd seg nts. So  f elds share t  term
   * d ct onary across seg nts as an opt m zat on.
   */
  publ c vo d bu ldMult Seg ntTermD ct onary() {
    Earlyb rdStatus.beg nEvent(
        BU LD_MULT _SEGMENT_TERM_D CT,
        search ndex ng tr cSet.startup nMult Seg ntTermD ct onaryUpdates);
     f (! nterrupted && !Thread.currentThread(). s nterrupted()) {
      LOG. nfo("Bu ld ng mult  seg nt term d ct onar es.");
      boolean bu lt = mult Seg ntTermD ct onaryManager.bu ldD ct onary();
      LOG. nfo("Done bu ld ng mult  seg nt term d ct onar es, result: {}", bu lt);
    }
    Earlyb rdStatus.endEvent(
        BU LD_MULT _SEGMENT_TERM_D CT,
        search ndex ng tr cSet.startup nMult Seg ntTermD ct onaryUpdates);
  }

  /**
   * Warms up t  data  n t  g ven seg nts. T  warm up w ll usually make sure that all necessary
   *  s loaded  n RAM and all relevant data structures are created before t  seg nts starts
   * serv ng real requests.
   *
   * @param seg nts T  l st of seg nts to warm up.
   */
  publ c f nal vo d warmSeg nts( erable<Seg nt nfo> seg nts) throws  nterruptedExcept on {
     nt thread d = 1;
     erator<Seg nt nfo>   = seg nts. erator();

    try {
      L st<Thread> seg ntWar rs = L sts.newL nkedL st();
      wh le ( .hasNext()) {

        seg ntWar rs.clear();
        wh le ( .hasNext() && seg ntWar rs.s ze() < maxConcurrentSeg nt ndexers) {
          f nal Seg nt nfo seg nt =  .next();
          Thread t = new Thread(() ->
            new Seg ntWar r(cr  calExcept onHandler).warmSeg nt fNecessary(seg nt),
              "startup-war r-" + thread d++);

          t.start();
          seg ntWar rs.add(t);
        }

        for (Thread t : seg ntWar rs) {
          t.jo n();
        }
      }
    } catch ( nterruptedExcept on e) {
      LOG.error(" nterrupted seg nt war r thread", e);
      Thread.currentThread(). nterrupt();
      throw e;
    }
  }

  /**
   *  ndexes a complete seg nt.
   */
  pr vate class S ngleSeg nt ndexer  mple nts Runnable {
    pr vate f nal Seg nt nfo seg nt nfo;

    publ c S ngleSeg nt ndexer(Seg nt nfo seg nt nfo) {
      t .seg nt nfo = seg nt nfo;
    }

    @Overr de
    publ c vo d run() {
      // 0) C ck  f t  seg nt can be loaded. T  m ght copy t  seg nt from HDFS.
       f (new Seg ntLoader(seg ntSyncConf g, cr  calExcept onHandler)
          .downloadSeg nt(seg nt nfo)) {
        LOG. nfo("W ll not  ndex seg nt {} because   was downloaded from HDFS.",
                 seg nt nfo.getSeg ntNa ());
        seg nt nfo.setComplete(true);
        return;
      }

      LOG. nfo("S ngleSeg nt ndexer start ng for seg nt: " + seg nt nfo);

      // 1)  ndex all t ets  n t  seg nt.
      RecordReader<T etDocu nt> t etReader;
      try {
        t etReader = seg ntDataProv der.getSeg ntDataReaderSet().newDocu ntReader(seg nt nfo);
         f (t etReader != null) {
          t etReader.setExhaustStream(true);
        }
      } catch (Except on e) {
        throw new Runt  Except on("Could not create t et reader for seg nt: " + seg nt nfo, e);
      }

      new S mpleSeg nt ndexer(t etReader, search ndex ng tr cSet). ndexSeg nt(seg nt nfo);

       f (!seg nt nfo. sComplete() || seg nt nfo. s ndex ng()) {
        throw new Runt  Except on("Seg nt does not appear to be complete: " + seg nt nfo);
      }

      // 2)  ndex all updates  n t  seg nt (arch ve earlyb rds don't have updates).
       f (!Earlyb rdCluster. sArch ve(seg ntManager.getEarlyb rd ndexConf g().getCluster())) {
        new S mpleUpdate ndexer(
            seg ntDataProv der.getSeg ntDataReaderSet(),
            search ndex ng tr cSet,
            retryQueue,
            cr  calExcept onHandler). ndexAllUpdates(seg nt nfo);
      }

      // 3) Opt m ze t  seg nt.
      Seg ntOpt m zer.opt m ze(seg nt nfo);

      // 4) Flush to HDFS  f necessary.
      new Seg ntHdfsFlus r(zkTryLockFactory, seg ntSyncConf g)
          .flushSeg ntToD skAndHDFS(seg nt nfo);

      // 5) Unload t  seg nt from  mory.
      seg nt nfo.get ndexSeg nt().close();
    }
  }

}
