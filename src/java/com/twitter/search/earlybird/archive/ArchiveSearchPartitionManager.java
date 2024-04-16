package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.Date;
 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.concurrent.Sc duledExecutorServ ceFactory;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.search.common.ut l. o.recordreader.RecordReader;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.Earlyb rdStatus;
 mport com.tw ter.search.earlyb rd.ServerSet mber;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer.Arch veT  Sl ce;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.ut l.ScrubGenUt l;
 mport com.tw ter.search.earlyb rd.docu nt.T etDocu nt;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.CompleteSeg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Part  onManager;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntHdfsFlus r;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntLoader;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.F lter;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager.Order;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntOpt m zer;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntWar r;
 mport com.tw ter.search.earlyb rd.part  on.S mpleSeg nt ndexer;
 mport com.tw ter.search.earlyb rd.part  on.UserScrubGeoEventStream ndexer;
 mport com.tw ter.search.earlyb rd.part  on.UserUpdatesStream ndexer;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.seg nt.Seg ntDataProv der;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct on;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct on nterface;
 mport com.tw ter.search.earlyb rd.ut l.Coord natedEarlyb rdAct onLockFa led;

publ c class Arch veSearchPart  onManager extends Part  onManager {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Arch veSearchPart  onManager.class);

  publ c stat c f nal Str ng CONF G_NAME = "arch ve";

  pr vate stat c f nal long ONE_DAY_M LL S = T  Un .DAYS.toM ll s(1);

  pr vate f nal Arch veT  Sl cer t  Sl cer;
  pr vate f nal Arch veSeg ntDataProv der seg ntDataProv der;

  pr vate f nal UserUpdatesStream ndexer userUpdatesStream ndexer;
  pr vate f nal UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer;

  pr vate f nal Seg ntWar r seg ntWar r;
  pr vate f nal Earlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal Clock clock;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;
  protected f nal SearchCounter gcAfter ndex ng;

  // Used for coord nat ng da ly updated across d fferent repl cas on t  sa  hash part  on,
  // to run t m one at a t  , and m n m ze t   mpact on query latenc es.
  pr vate f nal Coord natedEarlyb rdAct on nterface coord natedDa lyUpdate;

  pr vate f nal Search ndex ng tr cSet  ndex ng tr cSet;

  // T   s only used  n tests w re no coord nat on  s needed.
  @V s bleForTest ng
  publ c Arch veSearchPart  onManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      QueryCac Manager queryCac Manager,
      Seg ntManager seg ntManager,
      Dynam cPart  onConf g dynam cPart  onConf g,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      SearchStatsRece ver searchStatsRece ver,
      Arch veEarlyb rd ndexConf g earlyb rd ndexConf g,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Sc duledExecutorServ ceFactory userUpdate ndexerSc duledExecutorFactory,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Seg ntSyncConf g syncConf g,
      Clock clock,
      Cr  calExcept onHandler cr  calExcept onHandler)
      throws  OExcept on {
    t (
        zooKeeperTryLockFactory,
        queryCac Manager,
        seg ntManager,
        dynam cPart  onConf g,
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        searchStatsRece ver,
        earlyb rd ndexConf g,
        null,
        executorServ ceFactory,
        userUpdate ndexerSc duledExecutorFactory,
        search ndex ng tr cSet,
        syncConf g,
        clock,
        cr  calExcept onHandler);
  }

  publ c Arch veSearchPart  onManager(
      ZooKeeperTryLockFactory zooKeeperTryLockFactory,
      QueryCac Manager queryCac Manager,
      Seg ntManager seg ntManager,
      Dynam cPart  onConf g dynam cPart  onConf g,
      UserUpdatesStream ndexer userUpdatesStream ndexer,
      UserScrubGeoEventStream ndexer userScrubGeoEventStream ndexer,
      SearchStatsRece ver searchStatsRece ver,
      Arch veEarlyb rd ndexConf g earlyb rd ndexConf g,
      ServerSet mber serverSet mber,
      Sc duledExecutorServ ceFactory executorServ ceFactory,
      Sc duledExecutorServ ceFactory userUpdate ndexerExecutorFactory,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Seg ntSyncConf g syncConf g,
      Clock clock,
      Cr  calExcept onHandler cr  calExcept onHandler) throws  OExcept on {
    super(queryCac Manager, seg ntManager, dynam cPart  onConf g, executorServ ceFactory,
        search ndex ng tr cSet, searchStatsRece ver, cr  calExcept onHandler);

    Precond  ons.c ckState(syncConf g.getScrubGen(). sPresent());
    Date scrubGen = ScrubGenUt l.parseScrubGenToDate(syncConf g.getScrubGen().get());

    t .zkTryLockFactory = zooKeeperTryLockFactory;
    f nal Da lyStatusBatc s da lyStatusBatc s = new Da lyStatusBatc s(
        zkTryLockFactory,
        scrubGen);
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();

    t . ndex ng tr cSet = search ndex ng tr cSet;

    t .t  Sl cer = new Arch veT  Sl cer(
        Earlyb rdConf g.getMaxSeg ntS ze(), da lyStatusBatc s,
        curPart  onConf g.getT erStartDate(), curPart  onConf g.getT erEndDate(),
        earlyb rd ndexConf g);
    t .seg ntDataProv der =
        new Arch veSeg ntDataProv der(
            dynam cPart  onConf g,
            t  Sl cer,
            t .earlyb rd ndexConf g);

    t .userUpdatesStream ndexer = userUpdatesStream ndexer;
    t .userScrubGeoEventStream ndexer = userScrubGeoEventStream ndexer;

    t .coord natedDa lyUpdate = new Coord natedEarlyb rdAct on(
        zkTryLockFactory,
        "arch ve_da ly_update",
        dynam cPart  onConf g,
        serverSet mber,
        cr  calExcept onHandler,
        syncConf g);

    t .seg ntWar r = new Seg ntWar r(cr  calExcept onHandler);
    t .clock = clock;
    t .seg ntSyncConf g = syncConf g;
    t .gcAfter ndex ng = SearchCounter.export("gc_after_ ndex ng");
  }

  @Overr de
  publ c Seg ntDataProv der getSeg ntDataProv der() {
    return seg ntDataProv der;
  }

  @Overr de
  protected vo d startUp() throws Except on {
    LOG. nfo("Us ng CompleteSeg ntManager to  ndex complete seg nts.");

    // deferr ng handl ng of mult -seg nt term d ct onary for t  arch ve.
    // SEARCH-11952
    CompleteSeg ntManager completeSeg ntManager = new CompleteSeg ntManager(
        zkTryLockFactory,
        seg ntDataProv der,
        userUpdatesStream ndexer,
        userScrubGeoEventStream ndexer,
        seg ntManager,
        null,
         ndex ng tr cSet,
        clock,
        Mult Seg ntTermD ct onaryManager.NOOP_ NSTANCE,
        seg ntSyncConf g,
        cr  calExcept onHandler);

    completeSeg ntManager. ndexUserEvents();
    completeSeg ntManager. ndexCompleteSeg nts(
        () -> seg ntManager.getSeg nt nfos(F lter.Needs ndex ng, Order.OLD_TO_NEW));

    //  n t  arch ve cluster, t  current seg nt needs to be loaded too.
    L st<Seg nt nfo> allSeg nts =
        L sts.newArrayL st(seg ntManager.getSeg nt nfos(F lter.All, Order.OLD_TO_NEW));
    completeSeg ntManager.loadCompleteSeg nts(allSeg nts);

    completeSeg ntManager.bu ldMult Seg ntTermD ct onary();

    completeSeg ntManager.warmSeg nts(allSeg nts);

    LOG. nfo("Start ng to run UserUpdatesKafkaConsu r");
    new Thread(userUpdatesStream ndexer::run, "userupdates-stream- ndexer").start();

     f (Earlyb rdConf g.consu UserScrubGeoEvents()) {
      LOG. nfo("Start ng to run UserScrubGeoEventKafkaConsu r");
      new Thread(userScrubGeoEventStream ndexer::run,
          "userScrubGeoEvent-stream- ndexer").start();
    }
  }

  pr vate stat c L st<Arch veT  Sl ce> truncateSeg ntL st(L st<Arch veT  Sl ce> seg ntL st,
                                                             nt maxNumSeg nts) {
    // Maybe cut-off t  beg nn ng of t  sorted l st of  Ds.
     f (maxNumSeg nts > 0 && maxNumSeg nts < seg ntL st.s ze()) {
      return seg ntL st.subL st(seg ntL st.s ze() - maxNumSeg nts, seg ntL st.s ze());
    } else {
      return seg ntL st;
    }
  }


  @Overr de
  protected vo d  ndex ngLoop(boolean f rstLoop) throws Except on {
     f (f rstLoop) {
      Earlyb rdStatus.beg nEvent(
           NDEX_CURRENT_SEGMENT, getSearch ndex ng tr cSet().startup nCurrentSeg nt);
    }

    L st<Arch veT  Sl ce> t  Sl ces = t  Sl cer.getT  Sl ces nT erRange();
    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
    t  Sl ces = truncateSeg ntL st(t  Sl ces, curPart  onConf g.getMaxEnabledLocalSeg nts());

    for (f nal Arch veT  Sl ce t  Sl ce : t  Sl ces) {
      //  f any t  sl ce bu ld fa led, do not try to bu ld t  sl ce after that to prevent
      // poss ble holes bet en t  sl ces.
      try {
         f (!processArch veT  Sl ce(t  Sl ce)) {
          LOG.warn("Bu ld ng t  sl ce {} has fa led, stopp ng future bu lds.",
              t  Sl ce.getDescr pt on());
           ndex ng tr cSet.arch veT  Sl ceBu ldFa ledCounter. ncre nt();
          return;
        }
      } catch (Coord natedEarlyb rdAct onLockFa led e) {
        //  f t  t  sl ce bu ld fa led because of lock coord nat on,   can wa  for t  next
        //  erat on to bu ld aga n.
        return;
      }
    }

     f (f rstLoop) {
      Earlyb rdStatus.endEvent(
           NDEX_CURRENT_SEGMENT, getSearch ndex ng tr cSet().startup nCurrentSeg nt);
      LOG. nfo("F rst  ndex ng loop complete. Sett ng up query cac ...");
      Earlyb rdStatus.beg nEvent(
          SETUP_QUERY_CACHE, getSearch ndex ng tr cSet().startup nQueryCac Updates);
    }
    setupQueryCac  fNeeded();

     f (Earlyb rdStatus. sStart ng() && queryCac Manager.allTasksRan()) {
      LOG. nfo("Query cac  setup complete. Becom ng current now...");
      Earlyb rdStatus.endEvent(
          SETUP_QUERY_CACHE, getSearch ndex ng tr cSet().startup nQueryCac Updates);

      beco Current();
      Earlyb rdStatus.recordEarlyb rdEvent("Arch ve Earlyb rd  s current");
    }

    update ndexFreshnessStats(t  Sl ces);
  }

  @V s bleForTest ng
  protected boolean processArch veT  Sl ce(f nal Arch veT  Sl ce t  Sl ce)
      throws Coord natedEarlyb rdAct onLockFa led,  OExcept on {
    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
    long m nStatus D = t  Sl ce.getM nStatus D(curPart  onConf g.get ndex ngHashPart  on D());
    Seg nt nfo seg nt nfo = seg ntManager.getSeg nt nfo(m nStatus D);
     f (seg nt nfo == null) {
      return  ndexSeg ntFromScratch(t  Sl ce);
    } else  f (ex st ngSeg ntNeedsUpdat ng(t  Sl ce, seg nt nfo)) {
      return  ndexNewDayAndAppendEx st ngSeg nt(t  Sl ce, seg nt nfo);
    }
    return true;
  }


  @V s bleForTest ng
  Seg nt nfo newSeg nt nfo(Arch veT  Sl ce t  Sl ce) throws  OExcept on {
    return new Seg nt nfo(seg ntDataProv der.newArch veSeg nt(t  Sl ce),
        seg ntManager.getEarlyb rdSeg ntFactory(), seg ntSyncConf g);
  }

  pr vate boolean  ndexNewDayAndAppendEx st ngSeg nt(f nal Arch veT  Sl ce t  Sl ce,
                                                      Seg nt nfo seg nt nfo)
      throws Coord natedEarlyb rdAct onLockFa led,  OExcept on {

    LOG. nfo("Updat ng seg nt: {}; new endDate w ll be {} seg nt nfo: {}",
        seg nt nfo.getSeg nt().getT  Sl ce D(), t  Sl ce.getEndDate(), seg nt nfo);

    // Create anot r new Seg nt nfo for  ndex ng
    f nal Seg nt nfo newSeg nt nfoFor ndex ng = newSeg nt nfo(t  Sl ce);
    // make a f nal reference of t  old seg nt  nfo to be passed  nto closure.
    f nal Seg nt nfo oldSeg nt nfo = seg nt nfo;

    // San y c ck: t  old and new seg nt should not share t  sa  lucene d rectory.
    Precond  ons.c ckState(
        !newSeg nt nfoFor ndex ng.getSync nfo().getLocalLuceneSyncD r().equals(
            oldSeg nt nfo.getSync nfo().getLocalLuceneSyncD r()));

    Precond  ons.c ckState(
        !newSeg nt nfoFor ndex ng.getSync nfo().getLocalSyncD r().equals(
            oldSeg nt nfo.getSync nfo().getLocalSyncD r()));

    f nal Arch veSeg nt oldSeg nt = (Arch veSeg nt) seg nt nfo.getSeg nt();

    return  ndexSeg nt(newSeg nt nfoFor ndex ng, oldSeg nt nfo,  nput -> {
      //  're updat ng t  seg nt - only  ndex days after t  old end date, but only  f
      //  're  n t  on-d sk arch ve, and  're sure that t  prev ous days have already
      // been  ndexed.
      return !earlyb rd ndexConf g. s ndexStoredOnD sk()
          // F rst t   around, and t  seg nt has not been  ndexed and opt m zed yet,
          //   w ll want to add all t  days
          || !oldSeg nt nfo. sOpt m zed()
          || oldSeg nt nfo.get ndexSeg nt().get ndexStats().getStatusCount() == 0
          || !oldSeg nt.getDataEndDate().before(t  Sl ce.getEndDate())
          //  ndex any new days
          ||  nput.after(oldSeg nt.getDataEndDate());
    });
  }

  pr vate boolean ex st ngSeg ntNeedsUpdat ng(Arch veT  Sl ce t  Sl ce,
                                               Seg nt nfo seg nt nfo) {
    return ((Arch veSeg nt) seg nt nfo.getSeg nt())
        .getDataEndDate().before(t  Sl ce.getEndDate())
        // F rst t   around, t  end date  s t  sa  as t  t  Sl ce end date, but
        // t  seg nt has not been  ndexed and opt m zed yet
        || (!seg nt nfo. sOpt m zed() && !seg nt nfo.was ndexed())
        //  f  ndex ng fa led, t   ndex w ll not be marked as complete, and   w ll want
        // to re ndex
        || !seg nt nfo. sComplete();
  }

  pr vate boolean  ndexSeg ntFromScratch(Arch veT  Sl ce t  Sl ce) throws
      Coord natedEarlyb rdAct onLockFa led,  OExcept on {

    Seg nt nfo seg nt nfo = newSeg nt nfo(t  Sl ce);
    LOG. nfo("Creat ng seg nt: " + seg nt nfo.getSeg nt().getT  Sl ce D()
        + "; new endDate w ll be " + t  Sl ce.getEndDate() + " seg nt nfo: " + seg nt nfo);

    return  ndexSeg nt(seg nt nfo, null, Arch veSeg nt.MATCH_ALL_DATE_PRED CATE);
  }

  pr vate vo d update ndexFreshnessStats(L st<Arch veT  Sl ce> t  Sl ces) {
     f (!t  Sl ces. sEmpty()) {
      Arch veT  Sl ce lastT  sl ce = t  Sl ces.get(t  Sl ces.s ze() - 1);

      // Add ~24 h s to start of end date to est mate fres st t et t  .
       ndex ng tr cSet.fres stT etT  M ll s.set(
          lastT  sl ce.getEndDate().getT  () + ONE_DAY_M LL S);

      Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
      long maxStatus d = lastT  sl ce.getMaxStatus D(
          curPart  onConf g.get ndex ngHashPart  on D());
       f (maxStatus d >  ndex ng tr cSet.h g stStatus d.get()) {
         ndex ng tr cSet.h g stStatus d.set(maxStatus d);
      }
    }
  }

  @Overr de
  publ c vo d shutDown ndex ng() {
    LOG. nfo("Shutt ng down.");
    userUpdatesStream ndexer.close();
    userScrubGeoEventStream ndexer.close();
    LOG. nfo("Closed User Event Kafka Consu rs. Now Shutt ng down reader set.");
    getSeg ntDataProv der().getSeg ntDataReaderSet().stopAll();
  }

  /**
   * Attempts to  ndex new days of data  nto t  prov ded seg nt,  ndex ng only t  days that
   * match t  "dateF lter" pred cate.
   * @return true  ff  ndex ng succeeded, false ot rw se.
   */
  @V s bleForTest ng
  protected boolean  ndexSeg nt(f nal Seg nt nfo seg nt nfo,
                                 @Nullable f nal Seg nt nfo seg ntToAppend,
                                 f nal Pred cate<Date> dateF lter)
      throws Coord natedEarlyb rdAct onLockFa led,  OExcept on {
    // Don't coord nate wh le  're start ng up
     f (!Earlyb rdStatus. sStart ng()) {
      return coord natedDa lyUpdate.execute(seg nt nfo.getSeg ntNa (),
           sCoord nated ->  nner ndexSeg nt(seg nt nfo, seg ntToAppend, dateF lter));
    } else {
      return  nner ndexSeg nt(seg nt nfo, seg ntToAppend, dateF lter);
    }
  }

  pr vate boolean  nner ndexSeg nt(Seg nt nfo seg nt nfo,
                                    @Nullable Seg nt nfo seg ntToAppend,
                                    Pred cate<Date> dateF lter)
      throws  OExcept on {

    // F rst try to load t  new day from HDFS / Local d sk
     f (new Seg ntLoader(seg ntSyncConf g, cr  calExcept onHandler).load(seg nt nfo)) {
      LOG. nfo("Successful loaded seg nt for new day: " + seg nt nfo);
      seg ntManager.putSeg nt nfo(seg nt nfo);
      gcAfter ndex ng. ncre nt();
      GCUt l.runGC();
      return true;
    }

    LOG. nfo("Fa led to load seg nt for new day. W ll  ndex seg nt: " + seg nt nfo);
    RecordReader<T etDocu nt> t etReader = ((Arch veSeg nt) seg nt nfo.getSeg nt())
        .getStatusRecordReader(earlyb rd ndexConf g.createDocu ntFactory(), dateF lter);
    try {
      // Read and  ndex t  statuses
      boolean success = newS mpleSeg nt ndexer(t etReader, seg ntToAppend)
          . ndexSeg nt(seg nt nfo);
       f (!success) {
        return false;
      }
    } f nally {
      t etReader.stop();
    }

     f (!Seg ntOpt m zer.opt m ze(seg nt nfo)) {
      //   cons der t  whole  ndex ng event as fa led  f   fa l to opt m ze.
      LOG.error("Fa led to opt m ze seg nt: " + seg nt nfo);
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      return false;
    }

     f (!seg ntWar r.warmSeg nt fNecessary(seg nt nfo)) {
      //   cons der t  whole  ndex ng event as fa led  f   fa led to warm (because   open
      //  ndex readers  n t  war r).
      LOG.error("Fa led to warm seg nt: " + seg nt nfo);
      seg nt nfo.deleteLocal ndexedSeg ntD rectory m d ately();
      return false;
    }

    // Flush and upload seg nt to HDFS.  f t  fa ls,   just log a warn ng and return true.
    boolean success = new Seg ntHdfsFlus r(zkTryLockFactory, seg ntSyncConf g)
        .flushSeg ntToD skAndHDFS(seg nt nfo);
     f (!success) {
      LOG.warn("Fa led to flush seg nt to HDFS: " + seg nt nfo);
    }

    seg ntManager.putSeg nt nfo(seg nt nfo);
    gcAfter ndex ng. ncre nt();
    GCUt l.runGC();
    return true;
  }

  @V s bleForTest ng
  protected S mpleSeg nt ndexer newS mpleSeg nt ndexer(
      RecordReader<T etDocu nt> t etReader, Seg nt nfo seg ntToAppend) {
    return new S mpleSeg nt ndexer(t etReader,  ndex ng tr cSet, seg ntToAppend);
  }

  @Overr de
  publ c boolean  sCaughtUpForTests() {
    return Earlyb rdStatus.getStatusCode() == Earlyb rdStatusCode.CURRENT;
  }

  publ c Coord natedEarlyb rdAct on nterface getCoord natedOpt m zer() {
    return t .coord natedDa lyUpdate;
  }

  publ c Arch veT  Sl cer getT  Sl cer() {
    return t  Sl cer;
  }
}
