package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.Date;
 mport java.ut l.HashMap;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;
 mport java.ut l.Random;
 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.collect.Compar sonCha n;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.ut l.concurrent.Un nterrupt bles;
 mport com.google. nject. nject;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter. nject.annotat ons.Flag;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver mpl;
 mport com.tw ter.search.common.part  on ng.zookeeper.SearchZkCl ent;
 mport com.tw ter.search.common.ut l.Kerberos;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veOnD skEarlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSeg nt;
 mport com.tw ter.search.earlyb rd.arch ve.Da lyStatusBatc s;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veT  Sl cer;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.ut l.ScrubGenUt l;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;

/**
 * T  class prov des t  core log c to bu ld seg nt  nd ces offl ne.
 * For each server,   coord nate v a zookeeper to p ck t  next seg nt, bu ld t   nd ces for  
 * and upload t m to HDFS. A state mach ne  s used to handle t  bu ld state trans  ons. T re
 * are three states:
 *  NOT_BU LD_YET: a seg nt that needs to be bu lt
 *  SOMEONE_ELSE_ S_BU LD NG: anot r server  s bu ld ng t  seg nt.
 *  BU LT_AND_F NAL ZED: t   nd ces of t  seg nt have already been bu lt.
 */
publ c class Seg ntBu lder {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntBu lder.class);

  pr vate f nal boolean onlyRunOnce;
  pr vate f nal  nt wa Bet enLoopsM ns;
  pr vate f nal  nt startUpBatchS ze;
  pr vate f nal  nt  nstance;
  pr vate f nal  nt wa Bet enSeg ntsSecs;
  pr vate f nal  nt wa BeforeQu M ns;

  // W n mult ple seg nt bu lders start s multaneously, t y m ght make t  HDFS na  node and
  // zookeeper overw l d. So,   let so   nstances sleep so t  s before t y start to avo d
  // t   ssues.
  pr vate f nal long startUpSleepM ns;

  //  f no more seg nts to bu lt, wa  t   nterval before c ck ng aga n.
  pr vate f nal long processWa  ng nterval = T  Un .M NUTES.toM ll s(10);

  // T  hash part  ons that seg nts w ll be bu lt.
  pr vate f nal  mmutableL st< nteger> hashPart  ons;

  pr vate f nal SearchStatsRece ver statsRece ver = new SearchStatsRece ver mpl();
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet =
      new Search ndex ng tr cSet(statsRece ver);
  pr vate f nal Earlyb rdSearc rStats searc rStats =
      new Earlyb rdSearc rStats(statsRece ver);

  pr vate f nal Arch veOnD skEarlyb rd ndexConf g earlyb rd ndexConf g;

  pr vate f nal ZooKeeperTryLockFactory zkTryLockFactory;
  pr vate f nal RateL m  ngSeg ntHandler seg ntHandler;
  pr vate f nal Clock clock;
  pr vate f nal  nt numSeg ntBu lderPart  ons;
  pr vate f nal  nt  Part  on d;
  pr vate f nal Seg ntConf g seg ntConf g;
  pr vate f nal Earlyb rdSeg ntFactory seg ntFactory;
  pr vate f nal Seg ntBu lderCoord nator seg ntBu lderCoord nator;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;
  pr vate f nal Random random = new Random();

  pr vate stat c f nal double SLEEP_RANDOM ZAT ON_RAT O = .2;

  // Stats
  // T  flush vers on used to bu ld seg nts
  pr vate stat c f nal SearchLongGauge CURRENT_FLUSH_VERS ON =
      SearchLongGauge.export("current_flush_vers on");

  // Accumulated number and t    n seconds spent on bu ld ng seg nts locally
  pr vate stat c SearchCounter seg ntsBu ltLocally =
      SearchCounter.export("seg nts_bu lt_locally");
  pr vate stat c SearchCounter t  SpentOnSuccessfulBu ldSecs =
      SearchCounter.export("t  _spent_on_successful_bu ld_secs");

  // T  total number of seg nts to be bu lt
  pr vate stat c f nal SearchLongGauge SEGMENTS_TO_BU LD =
      SearchLongGauge.export("seg nts_to_bu ld");

  // How many seg nts fa led locally
  pr vate stat c f nal SearchCounter FA LED_SEGMENTS =
      SearchCounter.export("fa led_seg nts");

  @ nject
  protected Seg ntBu lder(@Flag("onlyRunOnce") boolean onlyRunOnceFlag,
                           @Flag("wa Bet enLoopsM ns")  nt wa Bet enLoopsM nsFlag,
                           @Flag("startup_batch_s ze")  nt startUpBatchS zeFlag,
                           @Flag(" nstance")  nt  nstanceFlag,
                           @Flag("seg ntZkLockExp rat onH s")
                                  nt seg ntZkLockExp rat onH sFlag,
                           @Flag("startupSleepM ns") long startupSleepM nsFlag,
                           @Flag("maxRetr esOnFa lure")  nt maxRetr esOnFa lureFlag,
                           @Flag("hash_part  ons") L st< nteger> hashPart  onsFlag,
                           @Flag("numSeg ntBu lderPart  ons")  nt numSeg ntBu lderPart  onsFlag,
                           @Flag("wa Bet enSeg ntsSecs")  nt wa Bet enSeg ntsSecsFlag,
                           @Flag("wa BeforeQu M ns")  nt wa BeforeQu M nsFlag,
                           @Flag("scrubGen") Str ng scrubGen,
                           Dec der dec der) {
    t (onlyRunOnceFlag,
        wa Bet enLoopsM nsFlag,
        startUpBatchS zeFlag,
         nstanceFlag,
        seg ntZkLockExp rat onH sFlag,
        startupSleepM nsFlag,
        hashPart  onsFlag,
        maxRetr esOnFa lureFlag,
        wa Bet enSeg ntsSecsFlag,
        wa BeforeQu M nsFlag,
        SearchZkCl ent.getSZooKeeperCl ent().createZooKeeperTryLockFactory(),
        new RateL m  ngSeg ntHandler(T  Un .M NUTES.toM ll s(10), Clock.SYSTEM_CLOCK),
        Clock.SYSTEM_CLOCK,
        numSeg ntBu lderPart  onsFlag,
        dec der,
        getSyncConf g(scrubGen));
  }

  @V s bleForTest ng
  protected Seg ntBu lder(boolean onlyRunOnceFlag,
                            nt wa Bet enLoopsM nsFlag,
                            nt startUpBatchS zeFlag,
                            nt  nstanceFlag,
                            nt seg ntZkLockExp rat onH sFlag,
                           long startupSleepM nsFlag,
                           L st< nteger> hashPart  ons,
                            nt maxRetr esOnFa lure,
                            nt wa Bet enSeg ntsSecsFlag,
                            nt wa BeforeQu M nsFlag,
                           ZooKeeperTryLockFactory zooKeeperTryLockFactory,
                           RateL m  ngSeg ntHandler seg ntHandler,
                           Clock clock,
                            nt numSeg ntBu lderPart  ons,
                           Dec der dec der,
                           Seg ntSyncConf g syncConf g) {
    LOG. nfo("Creat ng Seg ntBu lder");
    LOG. nfo("Pengu n vers on  n use: " + Earlyb rdConf g.getPengu nVers on());

    // Set command l ne flag values
    t .onlyRunOnce = onlyRunOnceFlag;
    t .wa Bet enLoopsM ns = wa Bet enLoopsM nsFlag;
    t .startUpBatchS ze = startUpBatchS zeFlag;
    t . nstance =  nstanceFlag;
    t .wa Bet enSeg ntsSecs = wa Bet enSeg ntsSecsFlag;
    t .wa BeforeQu M ns = wa BeforeQu M nsFlag;

    t .seg ntHandler = seg ntHandler;
    t .zkTryLockFactory = zooKeeperTryLockFactory;
    t .seg ntSyncConf g = syncConf g;
    t .startUpSleepM ns = startupSleepM nsFlag;

     f (!hashPart  ons. sEmpty()) {
      t .hashPart  ons =  mmutableL st.copyOf(hashPart  ons);
    } else {
      t .hashPart  ons = null;
    }

    Amount<Long, T  > seg ntZKLockExp rat onT   = Amount.of((long)
        seg ntZkLockExp rat onH sFlag, T  .HOURS);

    t .earlyb rd ndexConf g =
        new Arch veOnD skEarlyb rd ndexConf g(dec der, search ndex ng tr cSet,
            new Cr  calExcept onHandler());

    t .seg ntConf g = new Seg ntConf g(
        earlyb rd ndexConf g,
        seg ntZKLockExp rat onT  ,
        maxRetr esOnFa lure,
        zkTryLockFactory);
    t .seg ntFactory = new Earlyb rdSeg ntFactory(
        earlyb rd ndexConf g,
        search ndex ng tr cSet,
        searc rStats,
        clock);
    t .seg ntBu lderCoord nator = new Seg ntBu lderCoord nator(
        zkTryLockFactory, syncConf g, clock);

    t .clock = clock;

    t .numSeg ntBu lderPart  ons = numSeg ntBu lderPart  ons;
    t . Part  on d =  nstance % numSeg ntBu lderPart  ons;
    SearchLongGauge.export("seg nt_bu lder_part  on_ d_" +  Part  on d).set(1);

    CURRENT_FLUSH_VERS ON.set(earlyb rd ndexConf g.getSc ma().getMajorVers onNumber());
  }

  vo d run() {
    LOG. nfo("Conf g values: {}", Earlyb rdConf g.allValuesAsStr ng());

    // Sleep so  t   un nterrupt bly before get started so that  f mult ple  nstances are runn ng,
    // t  HDFS na  node and zookeeper wont be overw l d
    // Say,   have 100  nstances ( nstance_arg w ll have value from 0 - 99,  
    // STARTUP_BATCH_S ZE_ARG  s 20 and startUpSleepM ns  s 3 m ns. T n t  f rst 20  nstances
    // w ll not sleep, but start  m d ately. t n  nstance 20 - 39 w ll sleep 3 m ns and t n
    // start to run.  nstance 40 - 59 w ll sleep 6 m ns t n start to run.  nstances 60 - 79 w ll
    // sleep 9 m ns and t n start to run and so forth.
    long sleepT   =  nstance / startUpBatchS ze * startUpSleepM ns;
    LOG. nfo(" nstance={}, Start up batch s ze={}",  nstance, startUpBatchS ze);
    LOG. nfo("Sleep {} m nutes to vo d HDFS na  node and ZooKeeper overw l d.", sleepT  );
    Un nterrupt bles.sleepUn nterrupt bly(sleepT  , T  Un .M NUTES);

    // K n   re.
    Kerberos.k n (
        Earlyb rdConf g.getStr ng("kerberos_user", ""),
        Earlyb rdConf g.getStr ng("kerberos_keytab_path", "")
    );

    long wa Bet enLoopsMs = T  Un .M NUTES.toM ll s(wa Bet enLoopsM ns);
     f (onlyRunOnce) {
      LOG. nfo("T  seg nt bu lder w ll run t  full rebu ld of all t  seg nts");
    } else {
      LOG. nfo("T  seg nt bu lder w ll  ncre ntally c ck for new data and rebu lt "
          + "current seg nts as needed.");
      LOG. nfo("T  wa  ng  nterval bet en two new data c ck ng  s: "
          + wa Bet enLoopsMs + " ms.");
    }

    boolean scrubGenPresent = seg ntSyncConf g.getScrubGen(). sPresent();
    LOG. nfo("Scrub gen present: {}", scrubGenPresent);
    boolean scrubGenDataFullyBu lt = seg ntBu lderCoord nator. sScrubGenDataFullyBu lt( nstance);
    LOG. nfo("Scrub gen data fully bu lt: {}", scrubGenDataFullyBu lt);

     f (!scrubGenPresent || scrubGenDataFullyBu lt) {
      LOG. nfo("Start ng seg nt bu ld ng loop...");
      wh le (!Thread.currentThread(). s nterrupted()) {
        try {
           ndex ngLoop();
           f (onlyRunOnce) {
            LOG. nfo("only run once  s true, break ng");
            break;
          }
          clock.wa For(wa Bet enLoopsMs);
        } catch ( nterruptedExcept on e) {
          LOG. nfo(" nterrupted, qu t ng seg nt bu lder");
          Thread.currentThread(). nterrupt();
        } catch (Seg nt nfoConstruct onExcept on e) {
          LOG.error("Error creat ng new seg nt nfo, qu t ng seg nt bu lder: ", e);
          break;
        } catch (Seg ntUpdaterExcept on e) {
          FA LED_SEGMENTS. ncre nt();
          // Before t  seg nt bu lder qu s, sleep for WA T_BEFORE_QU T_M NS m nutes so that t 
          // FA LED_SEGMENTS stat can be exported.
          try {
            clock.wa For(T  Un .M NUTES.toM ll s(wa BeforeQu M ns));
          } catch ( nterruptedExcept on ex) {
            LOG. nfo(" nterrupted, qu t ng seg nt bu lder");
            Thread.currentThread(). nterrupt();
          }
          LOG.error("Seg ntUpdater process ng seg nt error, qu t ng seg nt bu lder: ", e);
          break;
        }
      }
    } else {
      LOG. nfo("Cannot bu ld t  seg nts for scrub gen yet.");
    }
  }

  // Refactor ng t  run loop to  re for un test
  @V s bleForTest ng
  vo d  ndex ngLoop()
      throws Seg nt nfoConstruct onExcept on,  nterruptedExcept on, Seg ntUpdaterExcept on {
    // T  map conta ns all t  seg nts to be processed;  f a seg nt  s bu lt,   w ll be removed
    // from t  map.
    Map<Str ng, Seg ntBu lderSeg nt> bu ldableSeg nt nfoMap;
    try {
      bu ldableSeg nt nfoMap = createSeg nt nfoMap();
      pr ntSeg nt nfoMap(bu ldableSeg nt nfoMap);
    } catch ( OExcept on e) {
      LOG.error("Error creat ng seg nt nfoMap: ", e);
      return;
    }

    wh le (!bu ldableSeg nt nfoMap. sEmpty()) {
      boolean hasBu ltSeg nt = processSeg nts(bu ldableSeg nt nfoMap);

       f (!hasBu ltSeg nt) {
        //  f   successfully bu lt a seg nt, no need to sleep s nce bu ld ng a seg nt takes a
        // long t  
        clock.wa For(processWa  ng nterval);
      }
    }
  }

  // Actual shutdown.
  protected vo d doShutdown() {
    LOG. nfo("doShutdown()...");
    try {
      earlyb rd ndexConf g.getRes ceCloser().shutdownExecutor();
    } catch ( nterruptedExcept on e) {
      LOG.error(" nterrupted dur ng shutdown. ", e);
    }

    LOG. nfo("Seg nt bu lder stopped!");
  }

  pr vate L st<Arch veT  Sl cer.Arch veT  Sl ce> createT  Sl ces() throws  OExcept on {
    Precond  ons.c ckState(seg ntSyncConf g.getScrubGen(). sPresent());
    Date scrubGen = ScrubGenUt l.parseScrubGenToDate(seg ntSyncConf g.getScrubGen().get());

    f nal Da lyStatusBatc s da lyStatusBatc s =
        new Da lyStatusBatc s(zkTryLockFactory, scrubGen);
    f nal Arch veT  Sl cer arch veT  Sl cer = new Arch veT  Sl cer(
        Earlyb rdConf g.getMaxSeg ntS ze(), da lyStatusBatc s, earlyb rd ndexConf g);

    Stopwatch stopwatch = Stopwatch.createStarted();
    L st<Arch veT  Sl cer.Arch veT  Sl ce> t  Sl ces = arch veT  Sl cer.getT  Sl ces();

     f (t  Sl ces == null) {
      LOG.error("Fa led to load t  sl ce map after {}", stopwatch);
      return Collect ons.emptyL st();
    }

    LOG. nfo("Took {} to get t  sl ces", stopwatch);
    return t  Sl ces;
  }

  pr vate stat c class T  Sl ceAndHashPart  on  mple nts Comparable<T  Sl ceAndHashPart  on> {
    publ c f nal Arch veT  Sl cer.Arch veT  Sl ce t  Sl ce;
    publ c f nal  nteger hashPart  on;

    publ c T  Sl ceAndHashPart  on(
        Arch veT  Sl cer.Arch veT  Sl ce t  Sl ce,
         nteger hashPart  on) {
      t .t  Sl ce = t  Sl ce;
      t .hashPart  on = hashPart  on;
    }

    @Overr de
    publ c  nt compareTo(T  Sl ceAndHashPart  on o) {
       nteger  HashPart  on = t .hashPart  on;
       nteger ot rHashPart  on = o.hashPart  on;

      long  T  Sl ce d = t .t  Sl ce.getM nStatus D( HashPart  on);
      long ot rT  Sl ce d = o.t  Sl ce.getM nStatus D(ot rHashPart  on);

      return Compar sonCha n.start()
          .compare( HashPart  on, ot rHashPart  on)
          .compare( T  Sl ce d, ot rT  Sl ce d)
          .result();
    }
  }

  /**
   * For all t  t  sl ces, create t  correspond ng Seg nt nfo and store  n a map
   */
  @V s bleForTest ng
  Map<Str ng, Seg ntBu lderSeg nt> createSeg nt nfoMap() throws  OExcept on {
    f nal L st<Arch veT  Sl cer.Arch veT  Sl ce> t  Sl ces = createT  Sl ces();

    L st<T  Sl ceAndHashPart  on> t  Sl cePa rs = createPa rs(t  Sl ces);
    // Export how many seg nts should be bu lt
    SEGMENTS_TO_BU LD.set(t  Sl cePa rs.s ze());
    LOG. nfo("Total number of seg nts to be bu lt across all seg nt bu lders: {}",
        t  Sl cePa rs.s ze());

    L st<T  Sl ceAndHashPart  on>  Seg nts = getSeg ntsFor Part  on(t  Sl cePa rs);

    Map<Str ng, Seg ntBu lderSeg nt> seg nt nfoMap = new HashMap<>();
    for (T  Sl ceAndHashPart  on  Seg nt :  Seg nts) {
      Arch veSeg nt seg nt = new Arch veSeg nt( Seg nt.t  Sl ce,  Seg nt.hashPart  on,
          Earlyb rdConf g.getMaxSeg ntS ze());
      Seg nt nfo seg nt nfo = new Seg nt nfo(seg nt, seg ntFactory, seg ntSyncConf g);

      seg nt nfoMap.put(seg nt nfo.getSeg nt().getSeg ntNa (), new NotYetBu ltSeg nt(
          seg nt nfo, seg ntConf g, seg ntFactory, 0, seg ntSyncConf g));
    }

    return seg nt nfoMap;
  }

  pr vate L st<T  Sl ceAndHashPart  on> createPa rs(
      L st<Arch veT  Sl cer.Arch veT  Sl ce> t  Sl ces) {

    L st<T  Sl ceAndHashPart  on> t  Sl cePa rs = new ArrayL st<>();

    for (Arch veT  Sl cer.Arch veT  Sl ce sl ce : t  Sl ces) {
      L st< nteger> localPart  ons = hashPart  ons;
       f (localPart  ons == null) {
        localPart  ons = range(sl ce.getNumHashPart  ons());
      }

      for ( nteger part  on : localPart  ons) {
        t  Sl cePa rs.add(new T  Sl ceAndHashPart  on(sl ce, part  on));
      }
    }
    return t  Sl cePa rs;
  }

  pr vate L st<T  Sl ceAndHashPart  on> getSeg ntsFor Part  on(
      L st<T  Sl ceAndHashPart  on> t  Sl cePa rs) {

    Collect ons.sort(t  Sl cePa rs);

    L st<T  Sl ceAndHashPart  on>  T  Sl ces = new ArrayL st<>();
    for ( nt   =  Part  on d;   < t  Sl cePa rs.s ze();   += numSeg ntBu lderPart  ons) {
       T  Sl ces.add(t  Sl cePa rs.get( ));
    }

    LOG. nfo("Gett ng seg nts to be bu lt for part  on: {}",  Part  on d);
    LOG. nfo("Total number of part  ons: {}", numSeg ntBu lderPart  ons);
    LOG. nfo("Number of seg nts p cked: {}",  T  Sl ces.s ze());
    return  T  Sl ces;
  }

  /**
   * Pr nt out t  seg nt nfo Map for debugg ng
   */
  pr vate vo d pr ntSeg nt nfoMap(Map<Str ng, Seg ntBu lderSeg nt> seg nt nfoMap) {
    LOG. nfo("Seg nt nfoMap: ");
    for (Map.Entry<Str ng, Seg ntBu lderSeg nt> entry : seg nt nfoMap.entrySet()) {
      LOG. nfo(entry.getValue().toStr ng());
    }
    LOG. nfo("Total Seg nt nfoMap s ze: " + seg nt nfoMap.s ze() + ". done.");
  }

  /**
   * Bu ld  nd ces or refresh state for t  seg nts  n t  spec f ed seg nt nfoMap, wh ch only
   * conta ns t  seg nts that need to bu ld or are bu ld ng. W n a seg nt has not been bu lt,
   *    s bu lt  re.  f bu lt successfully,   w ll be removed from t  map; ot rw se,  s
   * state w ll be updated  n t  map.
   *
   * Returns true  ff t  process has bu lt a seg nt.
   */
  @V s bleForTest ng
  boolean processSeg nts(Map<Str ng, Seg ntBu lderSeg nt> seg nt nfoMap)
      throws Seg nt nfoConstruct onExcept on, Seg ntUpdaterExcept on,  nterruptedExcept on {

    boolean hasBu ltSeg nt = false;

     erator<Map.Entry<Str ng, Seg ntBu lderSeg nt>>  er =
        seg nt nfoMap.entrySet(). erator();
    wh le ( er.hasNext()) {
      Map.Entry<Str ng, Seg ntBu lderSeg nt> entry =  er.next();
      Seg ntBu lderSeg nt or g nalSeg nt = entry.getValue();

      LOG. nfo("About to process seg nt: {}", or g nalSeg nt.getSeg ntNa ());
      long startM ll s = System.currentT  M ll s();
      Seg ntBu lderSeg nt updatedSeg nt = seg ntHandler.processSeg nt(or g nalSeg nt);

       f (updatedSeg nt. sBu lt()) {
         er.remove();
        hasBu ltSeg nt = true;

         f (or g nalSeg nt  nstanceof NotYetBu ltSeg nt) {
          // Record t  total t   spent on successfully bu ld ng a semgent, used to compute t 
          // average seg nt bu ld ng t  .
          long t  Spent = System.currentT  M ll s() - startM ll s;
          seg ntsBu ltLocally. ncre nt();
          t  SpentOnSuccessfulBu ldSecs.add(t  Spent / 1000);
        }
      } else {
        entry.setValue(updatedSeg nt);
      }

      clock.wa For(getSeg ntSleepT  ());
    }

    return hasBu ltSeg nt;
  }

  pr vate long getSeg ntSleepT  () {
    // T  Hadoop na  node can handle only about 200 requests/sec before   gets overloaded.
    // Updat ng t  state of a node that has been bu lt takes about 1 second.   n t  worst case
    // scenar o w h 800 seg nt bu lders,   end up w h about 800 requests/sec.  Add ng a 10
    // second sleep lo rs t  worst case to about 80 requests/sec.

    long sleepM ll s = T  Un .SECONDS.toM ll s(wa Bet enSeg ntsSecs);

    // Use random zat on so that   can't get all seg nt bu lders h t ng   at t  exact sa  t  

     nt lo rSleepBoundM ll s = ( nt) (sleepM ll s * (1.0 - SLEEP_RANDOM ZAT ON_RAT O));
     nt upperSleepBoundM ll s = ( nt) (sleepM ll s * (1.0 + SLEEP_RANDOM ZAT ON_RAT O));
    return randRange(lo rSleepBoundM ll s, upperSleepBoundM ll s);
  }

  /**
   * Returns a pseudo-random number bet en m n and max,  nclus ve.
   */
  pr vate  nt randRange( nt m n,  nt max) {
    return random.next nt((max - m n) + 1) + m n;
  }

  /**
   * Returns l st of  ntegers 0, 1, 2, ..., count-1.
   */
  pr vate stat c L st< nteger> range( nt count) {
    L st< nteger> nums = new ArrayL st<>(count);

    for ( nt   = 0;   < count;  ++) {
      nums.add( );
    }

    return nums;
  }

  pr vate stat c Seg ntSyncConf g getSyncConf g(Str ng scrubGen) {
     f (scrubGen == null || scrubGen. sEmpty()) {
      throw new Runt  Except on(
          "Scrub gen expected, but could not get   from t  argu nts.");
    }

    LOG. nfo("Scrub gen: " + scrubGen);
    return new Seg ntSyncConf g(Opt onal.of(scrubGen));
  }
}
