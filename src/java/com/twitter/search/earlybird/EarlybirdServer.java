package com.tw ter.search.earlyb rd;

 mport java. o.BufferedWr er;
 mport java. o.Closeable;
 mport java. o.F le;
 mport java. o. OExcept on;
 mport java.n o.f le.F les;
 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ArrayBlock ngQueue;
 mport java.ut l.concurrent.Execut onExcept on;
 mport java.ut l.concurrent.ExecutorServ ce;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.RejectedExecut onExcept on;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cReference;
 mport javax.annotat on.Nullable;
 mport javax.annotat on.concurrent.GuardedBy;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Charsets;
 mport com.google.common.base.Stopwatch;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;
 mport com.google.common.ut l.concurrent.Atom cLongMap;

 mport org.apac .commons.codec.b nary.Base64;
 mport org.apac .lucene.search. ndexSearc r;
 mport org.apac .thr ft.TBase;
 mport org.apac .thr ft.TExcept on;
 mport org.apac .thr ft.TSer al zer;
 mport org.apac .zookeeper.KeeperExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common.zookeeper.ServerSet.UpdateExcept on;
 mport com.tw ter.common.zookeeper.ZooKeeperCl ent;
 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.f nagle.Fa lure;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.common. tr cs.T  r;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.FlushVers on;
 mport com.tw ter.search.common.search.term nat on.QueryT  outFactory;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.search.common.ut l.ml.tensorflow_eng ne.TensorflowModelsManager;
 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.QueryCostTracker;
 mport com.tw ter.search.earlyb rd.adm n.LastSearc sSummary;
 mport com.tw ter.search.earlyb rd.adm n.Quer edF eldsAndSc maStats;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestLogger;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestPostLogger;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestPreLogger;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd.common.RequestResponsePa r;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd.except on.Earlyb rdStartupExcept on;
 mport com.tw ter.search.earlyb rd.except on.Trans entExcept on;
 mport com.tw ter.search.earlyb rd.ml.Scor ngModelsManager;
 mport com.tw ter.search.earlyb rd.part  on.Aud oSpaceTable;
 mport com.tw ter.search.earlyb rd.part  on.Dynam cPart  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Earlyb rdStartup;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Part  onManager;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntManager;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntVulture;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdRPCStats;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServerStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusCode;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdStatusResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd.ut l.OneTaskSc duledExecutorManager;
 mport com.tw ter.search.earlyb rd.ut l.TermCountMon or;
 mport com.tw ter.search.earlyb rd.ut l.T etCountMon or;
 mport com.tw ter.snowflake. d.Snowflake d;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Funct on0;
 mport com.tw ter.ut l.Future;

publ c class Earlyb rdServer  mple nts Earlyb rdServ ce.Serv ce face, ServerSet mber {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdServer.class);

  pr vate stat c f nal Str ng EARLYB RD_STARTUP = "earlyb rd startup";
  publ c stat c f nal Str ng SERV CE_NAME = "Earlyb rd";

  pr vate stat c f nal boolean REG STER_W TH_ZK_ON_STARTUP =
      Earlyb rdConf g.getBool("reg ster_w h_zk_on_startup", true);
  pr vate stat c f nal Durat on SERVER_CLOSE_WA T_T ME = Durat on.apply(5L, T  Un .SECONDS);

  pr vate stat c f nal Fa lure QUEUE_FULL_FA LURE =
      Fa lure.rejected("Rejected due to full executor queue");

  pr vate f nal  nt port = Earlyb rdConf g.getThr ftPort();
  pr vate f nal  nt warmUpPort = Earlyb rdConf g.getWarmUpThr ftPort();
  pr vate f nal  nt numSearc rThreads = Earlyb rdConf g.getSearc rThreads();

  pr vate f nal SearchStatsRece ver earlyb rdServerStatsRece ver;
  pr vate f nal Earlyb rdRPCStats searchStats = new Earlyb rdRPCStats("search");
  pr vate f nal Earlyb rdSearc rStats t etsSearc rStats;

  pr vate stat c f nal Str ng REQUESTS_RECE VED_BY_F NAGLE_ D_COUNTER_NAME_PATTERN =
      "requests_for_f nagle_ d_%s_all";
  pr vate stat c f nal Str ng REQUESTS_RECE VED_BY_F NAGLE_ D_AND_CL ENT_ D_COUNTER_NAME_PATTERN =
      "requests_for_f nagle_ d_%s_and_cl ent_ d_%s";
  pr vate stat c f nal Str ng RESPONSES_PER_CL ENT_ D_STAT_TEMPLATE =
      "responses_for_cl ent_ d_%s_w h_response_code_%s";

  // Load ng cac  for per f nagle-cl ent- d stats. Stor ng t m  n a load ng cac  key-ed by
  // f nagle cl ent  d so   don't export t  stat mult ple t  s.
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> requestCountersByF nagleCl ent d =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchT  rStats>() {
            @Overr de
            publ c SearchT  rStats load(Str ng f nagleCl ent d) {
              return earlyb rdServerStatsRece ver.getT  rStats(
                  Str ng.format(
                      REQUESTS_RECE VED_BY_F NAGLE_ D_COUNTER_NAME_PATTERN,
                      f nagleCl ent d), T  Un .M CROSECONDS, false, true, false);
            }
          });

  // Counters per cl ent and response code.
  pr vate f nal Load ngCac <Str ng, SearchCounter> responseByCl ent dAndResponseCode =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchCounter>() {
              @Overr de
              publ c SearchCounter load(Str ng key) {
                  return earlyb rdServerStatsRece ver.getCounter(key);
              }
          });

  pr vate f nal Load ngCac <Str ng, SearchCounter> resultsAgeCounter =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchCounter>() {
            @Overr de
            publ c SearchCounter load(Str ng key) {
              return earlyb rdServerStatsRece ver.getCounter(key);
            }
          }
      );

  // Load ng cac  for per f nagle cl ent  d and cl ent  d stats. T se are stored separate
  // from t  ot r stats because t y are key-ed by t  pa r of f nagle cl ent  d and cl ent  d
  //  n order to make sure t  stats are only exported once.
  //  n t  key-pa r t  f rst ele nt  s t  f nagle cl ent  d wh le t  second ele nt  s t 
  // cl ent  d.
  pr vate f nal Load ngCac <Pa r<Str ng, Str ng>, SearchRateCounter>
      requestCountersByF nagle dAndCl ent d = Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Pa r<Str ng, Str ng>, SearchRateCounter>() {
            @Overr de
            publ c SearchRateCounter load(Pa r<Str ng, Str ng> cl entKey) {
              return earlyb rdServerStatsRece ver.getRateCounter(
                  Str ng.format(
                      REQUESTS_RECE VED_BY_F NAGLE_ D_AND_CL ENT_ D_COUNTER_NAME_PATTERN,
                      cl entKey.getF rst(),
                      cl entKey.getSecond()));
            }
          });

  // Load ng cac  for per-cl ent- d latency stats. Stored  n a load ng cac   re ma nly because
  // t  tests assert t  mock stats rece ver that each stat  s only exported once.
  pr vate f nal Load ngCac <Str ng, SearchT  rStats> cl ent dSearchStats =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchT  rStats>() {
            @Overr de
            publ c SearchT  rStats load(Str ng cl ent d) {
              Str ng formattedCl ent d = Cl ent dUt l.formatCl ent d(cl ent d);
              return earlyb rdServerStatsRece ver.getT  rStats(formattedCl ent d,
                  T  Un .M CROSECONDS, false, true, true);
            }
          });

  pr vate f nal Load ngCac <Str ng, SearchT  rStats> cl ent dScor ngPerQueryStats =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchT  rStats>() {
            @Overr de
            publ c SearchT  rStats load(Str ng cl ent d) {
              Str ng statNa  =
                  Str ng.format("scor ng_t  _per_query_for_cl ent_ d_%s", cl ent d);
              return earlyb rdServerStatsRece ver.getT  rStats(statNa ,
                  T  Un .NANOSECONDS, false, true, false);
            }
          });

  pr vate f nal Load ngCac <Str ng, SearchT  rStats> cl ent dScor ngPerH Stats =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, SearchT  rStats>() {
            @Overr de
            publ c SearchT  rStats load(Str ng cl ent d) {
              Str ng statNa  =
                  Str ng.format("scor ng_t  _per_h _for_cl ent_ d_%s", cl ent d);
              return earlyb rdServerStatsRece ver.getT  rStats(statNa ,
                  T  Un .NANOSECONDS, false, true, false);
            }
          });

  pr vate f nal Load ngCac <Str ng, Percent le< nteger>> cl ent dScor ngNumH sProcessedStats =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, Percent le< nteger>>() {
            @Overr de
            publ c Percent le< nteger> load(Str ng cl ent d) {
              Str ng statNa  =
                  Str ng.format("scor ng_num_h s_processed_for_cl ent_ d_%s", cl ent d);
              return Percent leUt l.createPercent le(statNa );
            }
          });

  pr vate f nal Load ngCac <Str ng, Atom cReference<RequestResponsePa r>> lastRequestPerCl ent d =
      Cac Bu lder.newBu lder().bu ld(
          new Cac Loader<Str ng, Atom cReference<RequestResponsePa r>>() {
            @Overr de
            publ c Atom cReference<RequestResponsePa r> load(Str ng key) throws Except on {
              return new Atom cReference<>(null);
            }
          });


  pr vate f nal SearchT  rStats overallScor ngT  PerQueryStats;
  pr vate f nal SearchT  rStats overallScor ngT  PerH Stats;
  pr vate f nal Percent le< nteger> overallScor ngNumH sProcessedStats;

  pr vate f nal Earlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal Dynam cPart  onConf g dynam cPart  onConf g;
  pr vate f nal Seg ntManager seg ntManager;
  pr vate f nal UpdateableEarlyb rdStateManager stateManager;
  pr vate f nal Aud oSpaceTable aud oSpaceTable;

  pr vate f nal SearchLongGauge startupT  Gauge;

  // T   spent  n an  nternal thread pool queue, bet en t  t     get t  search request
  // from f nagle unt l   actually starts be ng executed.
  pr vate f nal SearchT  rStats  nternalQueueWa T  Stats;

  // Track ng request that have exceeded t  r allocated t  out pr or to us actually be ng able
  // to start execut ng t  search.
  pr vate f nal SearchCounter requestT  outExceededBeforeSearchCounter;
  // Current number of runn ng searc r threads.
  pr vate f nal SearchLongGauge numSearc rThreadsGauge;
  pr vate f nal QueryT  outFactory queryT  outFactory;

  pr vate Part  onManager part  onManager;
  pr vate QueryCac Manager queryCac Manager;

  pr vate f nal Scor ngModelsManager scor ngModelsManager;

  pr vate f nal TensorflowModelsManager tensorflowModelsManager;

  pr vate f nal Earlyb rdRequestPreLogger requestPreLogger;
  pr vate f nal Earlyb rdRequestPostLogger requestLogger;

  pr vate f nal T etCountMon or t etCountMon or;
  pr vate f nal TermCountMon or termCountMon or;

  pr vate f nal Earlyb rdServerSetManager serverSetManager;
  pr vate f nal Earlyb rdWarmUpManager warmUpManager;
  pr vate f nal Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager;

  pr vate f nal Object shutdownLock = new Object();
  @GuardedBy("shutdownLock")
  pr vate f nal Earlyb rdFuturePoolManager futurePoolManager;
  @GuardedBy("shutdownLock")
  pr vate f nal Earlyb rdF nagleServerManager f nagleServerManager;

  //  f a search request co s  n w h a cl ent-s de start t  , and   see that based on that
  // t  t  out has exp red, w t r   should drop that query  m d ately.
  pr vate f nal boolean sk pT  dOutRequests =
      Earlyb rdConf g.getBool("sk p_t  dout_requests", false);

  // cl ent of szookeeper.local.tw ter.com.
  // T   s used to perform d str buted lock ng and la t read ng etc.
  pr vate f nal ZooKeeperProxy sZooKeeperCl ent;

  pr vate f nal Dec der dec der;

  pr vate f nal Clock clock;

  pr vate f nal L st<Closeable> toClose = new ArrayL st<>();

  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;

  pr vate f nal Earlyb rdDarkProxy earlyb rdDarkProxy;

  pr vate f nal  mmutableMap<Earlyb rdResponseCode, SearchCounter> responseCodeCounters;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;
  pr vate f nal Earlyb rdStartup earlyb rdStartup;
  pr vate f nal Qual yFactor qual yFactor;

  pr vate boolean  sShutdown = false;
  pr vate boolean  sShutt ngDown = false;

  pr vate f nal Atom cLongMap<Str ng> quer edF eldsCounts = Atom cLongMap.create();

  publ c Earlyb rdServer(QueryCac Manager queryCac Manager,
                         ZooKeeperProxy sZkCl ent,
                         Dec der dec der,
                         Earlyb rd ndexConf g earlyb rd ndexConf g,
                         Dynam cPart  onConf g dynam cPart  onConf g,
                         Part  onManager part  onManager,
                         Seg ntManager seg ntManager,
                         Aud oSpaceTable aud oSpaceTable,
                         TermCountMon or termCountMon or,
                         T etCountMon or t etCountMon or,
                         UpdateableEarlyb rdStateManager earlyb rdStateManager,
                         Earlyb rdFuturePoolManager futurePoolManager,
                         Earlyb rdF nagleServerManager f nagleServerManager,
                         Earlyb rdServerSetManager serverSetManager,
                         Earlyb rdWarmUpManager warmUpManager,
                         SearchStatsRece ver earlyb rdServerStatsRece ver,
                         Earlyb rdSearc rStats t etsSearc rStats,
                         Scor ngModelsManager scor ngModelsManager,
                         TensorflowModelsManager tensorflowModelsManager,
                         Clock clock,
                         Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
                         Earlyb rdDarkProxy earlyb rdDarkProxy,
                         Seg ntSyncConf g seg ntSyncConf g,
                         QueryT  outFactory queryT  outFactory,
                         Earlyb rdStartup earlyb rdStartup,
                         Qual yFactor qual yFactor,
                         Search ndex ng tr cSet search ndex ng tr cSet) {
    LOG. nfo("Creat ng Earlyb rdServer");
    t .dec der = dec der;
    t .clock = clock;
    t .sZooKeeperCl ent = sZkCl ent;
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    t .dynam cPart  onConf g = dynam cPart  onConf g;
    t .seg ntManager = seg ntManager;
    t .queryCac Manager = queryCac Manager;
    t .termCountMon or = termCountMon or;
    t .t etCountMon or = t etCountMon or;
    t .stateManager = earlyb rdStateManager;
    t .part  onManager = part  onManager;
    t .futurePoolManager = futurePoolManager;
    t .f nagleServerManager = f nagleServerManager;
    t .serverSetManager = serverSetManager;
    t .warmUpManager = warmUpManager;
    t .earlyb rdServerStatsRece ver = earlyb rdServerStatsRece ver;
    t .t etsSearc rStats = t etsSearc rStats;
    t .scor ngModelsManager = scor ngModelsManager;
    t .tensorflowModelsManager = tensorflowModelsManager;
    t .mult Seg ntTermD ct onaryManager = mult Seg ntTermD ct onaryManager;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .earlyb rdDarkProxy = earlyb rdDarkProxy;
    t .seg ntSyncConf g = seg ntSyncConf g;
    t .queryT  outFactory = queryT  outFactory;
    t .earlyb rdStartup = earlyb rdStartup;
    t .qual yFactor = qual yFactor;
    t .aud oSpaceTable = aud oSpaceTable;

    Earlyb rdStatus.setStartT  (System.currentT  M ll s());

    //    n  al status code  s START NG.
    Earlyb rdStatus.setStatus(Earlyb rdStatusCode.START NG);
    Earlyb rdStatus.THR FT_SERV CE_STARTED.set(false);

    Part  onConf g part  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
    earlyb rdServerStatsRece ver.getLongGauge(
        "search_cluster_" + part  onConf g.getClusterNa ()).set(1);
    earlyb rdServerStatsRece ver.getLongGauge(
        "t er_na _" + part  onConf g.getT erNa ()).set(1);

    earlyb rdServerStatsRece ver.getLongGauge("part  on").set(
        part  onConf g.get ndex ngHashPart  on D());
    earlyb rdServerStatsRece ver.getLongGauge("repl ca").set(
        part  onConf g.getHostPos  onW h nHashPart  on());
    earlyb rdServerStatsRece ver.getLongGauge("pengu n_vers on").set(
        Earlyb rdConf g.getPengu nVers onByte());

    earlyb rdServerStatsRece ver.getLongGauge("flush_vers on").set(
        FlushVers on.CURRENT_FLUSH_VERS ON.ord nal());
    Str ng bu ldGen = Earlyb rdConf g.getStr ng("offl ne_seg nt_bu ld_gen", "unknown");
    earlyb rdServerStatsRece ver.getLongGauge("bu ld_gen_" + bu ldGen).set(1);

    t .startupT  Gauge = earlyb rdServerStatsRece ver.getLongGauge("startup_t  _m ll s");
    t . nternalQueueWa T  Stats = earlyb rdServerStatsRece ver.getT  rStats(
        " nternal_queue_wa _t  ", T  Un .M LL SECONDS, false, true, false);
    t .requestT  outExceededBeforeSearchCounter = earlyb rdServerStatsRece ver.getCounter(
        "request_t  out_exceeded_before_search");
    t .numSearc rThreadsGauge =
        earlyb rdServerStatsRece ver.getLongGauge("num_searc r_threads");
    t .overallScor ngT  PerQueryStats = earlyb rdServerStatsRece ver.getT  rStats(
        "overall_scor ng_t  _per_query", T  Un .NANOSECONDS, false, true, false);

    // For most of   scor ng funct ons t  scor ng_t  _per_h  records t  actual t   to score a
    // s ngle h . Ho ver, t  tensorflow based scor ng funct on uses batch scor ng, so   do not
    // know t  actual t     takes to score a s ngle h .   are now  nclud ng batch scor ng t  
    //  n all scor ng t   stats (SEARCH-26014), wh ch  ans that t  scor ng_t  _per_h  stat may
    // be a b  m slead ng for tensorflow based quer es. For t se quer es t  scor ng_t  _per_h 
    // represents t  rat o bet en total_scor ng_t   and t  number_of_h s,  nstead of t  actual
    // t   to score a s ngle h .
    t .overallScor ngT  PerH Stats = earlyb rdServerStatsRece ver.getT  rStats(
        "overall_scor ng_t  _per_h ", T  Un .NANOSECONDS, false, true, false);
    t .overallScor ngNumH sProcessedStats = Percent leUt l.createPercent le(
        "overall_scor ng_num_h s_processed");

     mmutableMap.Bu lder<Earlyb rdResponseCode, SearchCounter> responseCodeCountersBu lder =
        new  mmutableMap.Bu lder<>();
    for (Earlyb rdResponseCode responseCode : Earlyb rdResponseCode.values()) {
      responseCodeCountersBu lder.put(
          responseCode,
          earlyb rdServerStatsRece ver.getCounter(
              "responses_w h_response_code_" + responseCode.na ().toLo rCase()));
    }
    responseCodeCounters = responseCodeCountersBu lder.bu ld();

    d sableLuceneQueryCac ();
     n Managers();

    requestPreLogger = Earlyb rdRequestPreLogger.bu ldForShard(
      Earlyb rdConf g.get nt("latency_warn_threshold", 100), dec der);
    requestLogger = Earlyb rdRequestPostLogger.bu ldForShard(
        Earlyb rdConf g.get nt("latency_warn_threshold", 100), dec der);

    t .qual yFactor.startUpdates();

    LOG. nfo("Created Earlyb rdServer");
  }

  publ c boolean  sShutdown() {
    return t . sShutdown;
  }

  pr vate vo d  n Managers() {
    LOG. nfo("Created Earlyb rd ndexConf g: " + earlyb rd ndexConf g.getClass().getS mpleNa ());

    seg ntManager.addUpdateL stener(queryCac Manager);
  }

  publ c Part  onManager getPart  onManager() {
    return part  onManager;
  }

  publ c QueryCac Manager getQueryCac Manager() {
    return queryCac Manager;
  }

  publ c Seg ntManager getSeg ntManager() {
    return seg ntManager;
  }

  publ c Mult Seg ntTermD ct onaryManager getMult Seg ntTermD ct onaryManager() {
    return t .mult Seg ntTermD ct onaryManager;
  }

  @V s bleForTest ng
  publ c  nt getPort() {
    return port;
  }

  pr vate vo d d sableLuceneQueryCac () {
    // SEARCH-30046: Look  nto poss bly re-enabl ng t  query ->   ght cac .
    //   can't use t  cac  unt l   upgrade to Lucene 6.0.0, because   have quer es w h a
    // boost of 0.0, and t y don't play n cely w h Lucene's LRUQueryCac .get()  thod.
    //
    // Lucene 6.0.0 changes how boosts are handled: "real" boosts should be wrapped  nto BoostQuery
    //  nstances, and quer es w h a boost of 0.0 should be rewr ten as "f lters"
    // (BooleanQuery.add(query, BooleanClause.Occur.F LTER)). So w n   upgrade to Lucene 6.0.0  
    // w ll be forced to refactor how   handle   current quer es w h a boost of 0.0, wh ch m ght
    // allow us to re-enable t  cac .
    //
    // Note that d sabl ng t  cac   s not a regress on:   should g ve us t  behav or that  
    // had w h Lucene 5.2.1 (and  's unclear  f t  cac   s useful at all).
    //
    // WARN NG: T  default 'DefaultQueryCac ' ma nta ns a stat c reference to t    ght forever,
    // caus ng a  mory leak.     ghts hold references to an ent re seg nt so t   mory leak  s
    // s gn f cant.
     ndexSearc r.setDefaultQueryCac (null);
  }

  /**
   * Starts t  earlyb rd server.
   */
  publ c vo d start() throws Earlyb rdStartupExcept on {
    // Make sure t   s at t  top of t  funct on before ot r parts of t  system start runn ng
    new Earlyb rdBlackl stHandler(Clock.SYSTEM_CLOCK, sZooKeeperCl ent)
        .blockT nEx  fBlackl sted();

    Stopwatch startupWatch = Stopwatch.createStarted();
    Earlyb rdStatus.beg nEvent(EARLYB RD_STARTUP, search ndex ng tr cSet.startup nProgress);

    LOG. nfo("java.l brary.path  s: " + System.getProperty("java.l brary.path"));

    Part  onConf g part  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();

    Seg ntVulture.removeUnusedSeg nts(part  onManager, part  onConf g,
        earlyb rd ndexConf g.getSc ma().getMajorVers onNumber(), seg ntSyncConf g);

    // Start t  sc ma manager
    sc dule(stateManager);

    Closeable closeable = earlyb rdStartup.start();
    toClose.add(closeable);
     f (Earlyb rdStatus.getStatusCode() == Earlyb rdStatusCode.STOPP NG) {
      LOG. nfo("Server  s shutdown. Ex  ng...");
      return;
    }

    startupT  Gauge.set(startupWatch.elapsed(T  Un .M LL SECONDS));

    Earlyb rdStatus.endEvent(EARLYB RD_STARTUP, search ndex ng tr cSet.startup nProgress);

    GCUt l.runGC();  // Attempt to force a full GC before jo n ng t  serverset

    try {
      startThr ftServ ce(null, true);
    } catch ( nterruptedExcept on e) {
      LOG. nfo(" nterrupted wh le start ng thr ft server, qu t ng earlyb rd");
      throw new Earlyb rdStartupExcept on(" nterrupted wh le start ng thr ft server");
    }

    Earlyb rdStatus.THR FT_SERV CE_STARTED.set(true);

    // only once  're current, k ck off da ly t et count mon ors only for arch ve cluster
     f (Earlyb rdConf g.get nt(T etCountMon or.RUN_ NTERVAL_M NUTES_CONF G_NAME, -1) > 0) {
      sc dule(t etCountMon or);
    }

    // only once  're current, k ck off per-f eld term count mon ors
     f (Earlyb rdConf g.get nt(TermCountMon or.RUN_ NTERVAL_M NUTES_CONF G_NAME, -1) > 0) {
      sc dule(termCountMon or);
    }

    startupT  Gauge.set(startupWatch.elapsed(T  Un .M LL SECONDS));
    LOG. nfo("Earlyb rdServer start up t  : {}", startupWatch);
  }

  /**
   * Starts t  thr ft server  f t  server  s not runn ng.
   *  f searc rThreads  s null,   uses t  value spec f ed by Earlyb rdConf g.
   */
  publ c vo d startThr ftServ ce(@Nullable  nteger searc rThreads, boolean  sStart ngUp)
      throws  nterruptedExcept on {
    synchron zed (shutdownLock) {
       f (!f nagleServerManager. sWarmUpServerRunn ng()
          && !f nagleServerManager. sProduct onServerRunn ng()) {
         nt threadCount = searc rThreads != null
            ? searc rThreads : t .numSearc rThreads;
        LOG. nfo("Start ng searc r pool w h " + threadCount + " threads");
        futurePoolManager.createUnderly ngFuturePool(threadCount);
        numSearc rThreadsGauge.set(threadCount);

        //  f t  server  s not shutt ng down, go through t  warm up stage.  f t  server  s
        //  nstructed to shut down dur ng warm up, warmUpManager.warmUp() should return w h n a
        // second, and should leave t  warm up server set.   should st ll shut down t  warm up
        // F nagle server.
         f ( sStart ngUp && (Earlyb rdStatus.getStatusCode() != Earlyb rdStatusCode.STOPP NG)) {
          LOG. nfo("Open ng warmup thr ft port...");
          f nagleServerManager.startWarmUpF nagleServer(t , SERV CE_NAME, warmUpPort);
          Earlyb rdStatus.WARMUP_THR FT_PORT_OPEN.set(true);

          try {
            warmUpManager.warmUp();
          } catch (UpdateExcept on e) {
            LOG.warn("Could not jo n or leave t  warm up server set.", e);
          } f nally {
            f nagleServerManager.stopWarmUpF nagleServer(SERVER_CLOSE_WA T_T ME);
            Earlyb rdStatus.WARMUP_THR FT_PORT_OPEN.set(false);
          }
        }

        //  f t  server  s not shutt ng down,   can start t  product on F nagle server and jo n
        // t  product on server set.
         f (Earlyb rdStatus.getStatusCode() != Earlyb rdStatusCode.STOPP NG) {
          LOG. nfo("Open ng product on thr ft port...");
          f nagleServerManager.startProduct onF nagleServer(
              earlyb rdDarkProxy.getDarkProxy(), t , SERV CE_NAME, port);
          Earlyb rdStatus.THR FT_PORT_OPEN.set(true);

           f (REG STER_W TH_ZK_ON_STARTUP) {
            // After t  earlyb rd starts up, reg ster w h ZooKeeper.
            try {
              jo nServerSet(" nternal start-up");

              // Jo n separate server set for Serv ceProxy on Arch ve Earlyb rds
               f (!Earlyb rdConf g. sAurora()) {
                jo nServerSetForServ ceProxy();
              }
            } catch (UpdateExcept on e) {
              throw new Runt  Except on("Unable to jo n ServerSet dur ng startup.", e);
            }
          }
        }
      }
    }
  }

  /**
   * Stops t  thr ft server  f t  server  s already runn ng.
   */
  publ c vo d stopThr ftServ ce(boolean shouldShutDown) {
    synchron zed (shutdownLock) {
      try {
        leaveServerSet(shouldShutDown ? " nternal shutdown" : "adm n stopThr ftServ ce");
      } catch (UpdateExcept on e) {
        LOG.warn("Leav ng product on ServerSet fa led.", e);
      }

       f (f nagleServerManager. sProduct onServerRunn ng()) {
        try {
          f nagleServerManager.stopProduct onF nagleServer(SERVER_CLOSE_WA T_T ME);
          futurePoolManager.stopUnderly ngFuturePool(
              SERVER_CLOSE_WA T_T ME. nSeconds(), T  Un .SECONDS);
          numSearc rThreadsGauge.set(0);
        } catch ( nterruptedExcept on e) {
          LOG.error(" nterrupted wh le stopp ng thr ft serv ce", e);
          Thread.currentThread(). nterrupt();
        }
        Earlyb rdStatus.THR FT_PORT_OPEN.set(false);
      }
    }
  }

  /**
   * Gets a str ng w h  nformat on about t  last request  've seen from each cl ent.
   */
  publ c Future<Str ng> getLastSearc sByCl ent(boolean  ncludeResults) {
    LastSearc sSummary summary = new LastSearc sSummary(
        lastRequestPerCl ent d, cl ent dSearchStats,  ncludeResults);
    return Future.value(summary.getSummary());
  }

  /**
   * T  follow ng are all t  Thr ft RPC  thods  n r ed from Earlyb rdServ ce. face
   */

  // Thr ft getNa  RPC.
  @Overr de
  publ c Future<Str ng> getNa () {
    return Future.value(SERV CE_NAME);
  }

  // Thr ft getStatus RPC.
  @Overr de
  publ c Future<Earlyb rdStatusResponse> getStatus() {
    Earlyb rdStatusResponse response = new Earlyb rdStatusResponse();
    response.setCode(Earlyb rdStatus.getStatusCode());
    response.setAl veS nce(Earlyb rdStatus.getStartT  ());
    response.set ssage(Earlyb rdStatus.getStatus ssage());
    return Future.value(response);
  }

  publ c Future<L st<Str ng>> getSeg nt tadata() {
    return Future.value(seg ntManager.getSeg nt tadata());
  }

  publ c Future<Str ng> getQueryCac sData() {
    return Future.value(seg ntManager.getQueryCac sData());
  }

  /**
   * Get a text summary for wh ch f elds d d   use  n a sc ma.
   */
  publ c Future<Str ng> getQuer edF eldsAndSc maStats() {
     mmutableSc ma nterface sc ma = t .earlyb rd ndexConf g.getSc ma().getSc maSnapshot();

    Quer edF eldsAndSc maStats summary = new Quer edF eldsAndSc maStats(sc ma,
        quer edF eldsCounts);
    return Future.value(summary.getSummary());
  }

  /**
   * Shuts down t  earlyb rd server.
   */
  publ c vo d shutdown() {
    LOG. nfo("shutdown(): status set to STOPP NG");
    Earlyb rdStatus.setStatus(Earlyb rdStatusCode.STOPP NG);
    try {
      LOG. nfo("Stopp ng F nagle server.");
      stopThr ftServ ce(true);
      Earlyb rdStatus.THR FT_SERV CE_STARTED.set(false);

       f (queryCac Manager != null) {
        queryCac Manager.shutdown();
      } else {
        LOG. nfo("No queryCac Manager to shut down");
      }

      earlyb rd ndexConf g.getRes ceCloser().shutdownExecutor();

       sShutt ngDown = true;
      LOG. nfo("Clos ng {} closeables.", toClose.s ze());
      for (Closeable closeable : toClose) {
        closeable.close();
      }
    } catch ( nterruptedExcept on |  OExcept on e) {
      Earlyb rdStatus.setStatus(Earlyb rdStatusCode.UNHEALTHY, e.get ssage());
      LOG.error(" nterrupted dur ng shutdown, status set to UNHEALTHY");
    }
    LOG. nfo("Earlyb rd server stopped!");
     sShutdown = true;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> search(f nal Earlyb rdRequest request) {
    f nal long requestRece vedT  M ll s = System.currentT  M ll s();
    // Record clock d ff as early as poss ble.
    Earlyb rdRequestUt l.recordCl entClockD ff(request);

     f (!futurePoolManager. sPoolReady()) {
      return Future.except on(new Trans entExcept on("Earlyb rd not yet able to handle requests."));
    }

    return futurePoolManager.apply(new Funct on0<Earlyb rdResponse>() {
      @Overr de
      publ c Earlyb rdResponse apply() {
        return doSearch(request, requestRece vedT  M ll s);
      }
    }).rescue(Funct on.func(
        // respond w h Nack w n t  queue  s full
        t -> Future.except on((t  nstanceof RejectedExecut onExcept on) ? QUEUE_FULL_FA LURE : t)));
  }

  pr vate Earlyb rdResponse doSearch(Earlyb rdRequest request, long requestRece vedT  M ll s) {
    f nal long queueWa T   = System.currentT  M ll s() - requestRece vedT  M ll s;
     nternalQueueWa T  Stats.t  r ncre nt(queueWa T  );

    // request restart t  , not to be confused w h startT   wh ch  s server restart t  
    T  r t  r = new T  r(T  Un .M CROSECONDS);

    requestPreLogger.logRequest(request);

    Str ng cl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    Str ng f nagleCl ent d = F nagleUt l.getF nagleCl entNa ();
    requestCountersByF nagle dAndCl ent d.getUnc cked(new Pa r<>(f nagleCl ent d, cl ent d))
        . ncre nt();

    Earlyb rdRequestUt l.c ckAndSetCollectorParams(request);

    //  f t  thr ft logger  s busy logg ng, queue t  thr ft request for logg ng.
     f (Earlyb rdThr ftRequestLogg ngUt l.thr ftLoggerBusy) {
      Earlyb rdThr ftRequestLogg ngUt l.REQUEST_BUFFER.offer(request);
    }

    Earlyb rdRequestUt l.logAndF xExcess veValues(request);

    f nal Earlyb rdSearc r searc r = new Earlyb rdSearc r(
        request,
        seg ntManager,
        aud oSpaceTable,
        queryCac Manager,
        earlyb rd ndexConf g.getSc ma().getSc maSnapshot(),
        earlyb rd ndexConf g.getCluster(),
        dynam cPart  onConf g.getCurrentPart  onConf g(),
        dec der,
        t etsSearc rStats,
        scor ngModelsManager,
        tensorflowModelsManager,
        clock,
        mult Seg ntTermD ct onaryManager,
        queryT  outFactory,
        qual yFactor);

    QueryCostTracker queryCostTracker = QueryCostTracker.getTracker();
    Earlyb rdResponse response = null;
    try {
       f (sk pT  dOutRequests
          && searc r.getTerm nat onTracker().getT  outEndT  W hReservat on()
          <= clock.nowM ll s()) {
        requestT  outExceededBeforeSearchCounter. ncre nt();
        response = new Earlyb rdResponse();
        response.setResponseCode(Earlyb rdResponseCode.SERVER_T MEOUT_ERROR);
      } else {
        queryCostTracker.reset();
        response = searc r.search();
      }
    } f nally {
       f (response == null) {
        // T  can only happen  f   fa led to catch an except on  n t  searc r.
        LOG.error("Response was null: " + request.toStr ng());
        response = new Earlyb rdResponse();
        response.setResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR);
      }

       f (response.getSearchResults() == null) {
        L st<Thr ftSearchResult> emptyResultSet = L sts.newArrayL st();
        response.setSearchResults(new Thr ftSearchResults(emptyResultSet));
      }

      long reqLatency = t  r.stop();
      response.setResponseT  (reqLatency / 1000);
      response.setResponseT  M cros(reqLatency);
      response.getSearchResults().setQueryCost(queryCostTracker.getTotalCost());

      requestLogger.logRequest(request, response, t  r);

       nt numResults = Earlyb rdRequestLogger.numResultsForLog(response);
      boolean success = response.getResponseCode() == Earlyb rdResponseCode.SUCCESS;
      boolean cl entError = response.getResponseCode() == Earlyb rdResponseCode.CL ENT_ERROR;
      boolean earlyTerm nated = (response.getSearchResults(). sSetNumPart  onsEarlyTerm nated()
          && response.getSearchResults().getNumPart  onsEarlyTerm nated() > 0)
          || searc r.getTerm nat onTracker(). sEarlyTerm nated();
      // Update term nat on stats.
      searc r.getTerm nat onTracker().getEarlyTerm nat onState(). ncre ntCount();

      searchStats.requestComplete(reqLatency, numResults, success, earlyTerm nated, cl entError);
       f (searc r.getRequestStats() != null) {
        searc r.getRequestStats().requestComplete(reqLatency, numResults, success,
            earlyTerm nated, cl entError);
      }

      getResponseCodeCounter(response.getResponseCode()). ncre nt();
      // Add ng t  counter to make   eas er to debug cases w re   see a sp ke  n
      // bad cl ent request errors but don't know w re t y're com ng from. (T 
      // alternat ve  s to ssh to a mach ne  n t  cluster and sample
      // /var/log/earlyb rd/earlyb rd.fa led_requests).
      getCl ent dResponseCodeCounter(cl ent d, response.getResponseCode()). ncre nt();

      // Export request latency as a stat.
      cl ent dSearchStats.getUnc cked(cl ent d).t  r ncre nt(reqLatency);
      requestCountersByF nagleCl ent d.getUnc cked(f nagleCl ent d).t  r ncre nt(reqLatency);
      addEarlyb rdServerStats(response, queueWa T  );
      // Export scor ng stats for t  request.
      exportScor ngT  Stats(response, cl ent d);
    }

    Set<Str ng> quer edF elds = searc r.getQuer edF elds();
     f (quer edF elds != null) {
      for (Str ng quer edF eld : quer edF elds) {
        quer edF eldsCounts. ncre ntAndGet(quer edF eld);
      }
    }

    //  ncre nt counters for age of t  returned results.
     f (response.getSearchResults() != null && response.getSearchResults().getResults() != null) {
      long currentT   = System.currentT  M ll s();
      for (Thr ftSearchResult result : response.getSearchResults().getResults()) {
        long t et d = result.get d();
         f (Snowflake d. sSnowflake d(t et d)) {
          long ageM ll s = Math.max(0L,
              currentT   - Snowflake d.un xT  M ll sFrom d(t et d));
           nt ageDays = Durat on.fromM ll seconds(ageM ll s). nDays();

           f (Earlyb rdConf g. sRealt  OrProtected()) {
            Str ng key = "result_age_ n_days_" + ageDays;
            resultsAgeCounter.getUnc cked(key). ncre nt();
          } else {
             nt ageYears = ageDays / 365;
            Str ng key = "result_age_ n_years_" + ageYears;
            resultsAgeCounter.getUnc cked(key). ncre nt();
          }
        }
      }
    }

    try {
      lastRequestPerCl ent d.get(cl ent d).set(
          new RequestResponsePa r(request, searc r.getParsedQuery(),
              searc r.getLuceneQuery(), response));
    } catch (Execut onExcept on ex) {
      // Not a b g problem,  'll just not ce that t  adm n page doesn't work, and  
      // probably won't happen.
    }


    return response;
  }

  pr vate vo d exportScor ngT  Stats(Earlyb rdResponse response, Str ng cl ent d) {
     f (response. sSetSearchResults()
        && response.getSearchResults(). sSetScor ngT  Nanos()
        && response.getSearchResults(). sSetNumH sProcessed()) {
       nt numH sProcessed = response.getSearchResults().getNumH sProcessed();
      long scor ngT  Nanos = response.getSearchResults().getScor ngT  Nanos();

       f (numH sProcessed > 0) {
        // Only compute and report scor ng t   per h  w n   have h s. ( .e.   don't just want
        // to report 0's for cases w re t re  re no h s, and only want to report leg  per-h 
        // t  s.
        long scor ngT  PerH  = scor ngT  Nanos / numH sProcessed;

        t .cl ent dScor ngPerH Stats.getUnc cked(cl ent d).t  r ncre nt(scor ngT  PerH );
        t .overallScor ngT  PerH Stats.t  r ncre nt(scor ngT  PerH );
      }

      t .cl ent dScor ngPerQueryStats.getUnc cked(cl ent d).t  r ncre nt(scor ngT  Nanos);
      t .overallScor ngT  PerQueryStats.t  r ncre nt(scor ngT  Nanos);

      // T  num h s processed stats  re are scoped only to quer es that  re actually scored.
      // T  would exclude quer es l ke term stats (that would ot rw se have huge num h s
      // processed).
      t .cl ent dScor ngNumH sProcessedStats.getUnc cked(cl ent d).record(numH sProcessed);
      t .overallScor ngNumH sProcessedStats.record(numH sProcessed);
    }
  }

  pr vate vo d addEarlyb rdServerStats(Earlyb rdResponse response, long queueWa T  ) {
    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();
    Earlyb rdServerStats earlyb rdServerStats = new Earlyb rdServerStats();
    response.setEarlyb rdServerStats(earlyb rdServerStats);
    earlyb rdServerStats.setHostna (DatabaseConf g.getLocalHostna ());
    earlyb rdServerStats.setPart  on(curPart  onConf g.get ndex ngHashPart  on D());
    earlyb rdServerStats.setT erNa (curPart  onConf g.getT erNa ());
    earlyb rdServerStats.setCurrentQps(searchStats.getRequestRate());
    earlyb rdServerStats.setQueueT  M ll s(queueWa T  );
    earlyb rdServerStats.setAverageQueueT  M ll s(
        (long) (double)  nternalQueueWa T  Stats.read());
    earlyb rdServerStats.setAverageLatencyM cros(searchStats.getAverageLatency());
  }

  @Overr de
  publ c vo d jo nServerSet(Str ng userna ) throws UpdateExcept on {
    serverSetManager.jo nServerSet(userna );
  }


  @Overr de
  publ c  nt getNumberOfServerSet mbers() throws  nterruptedExcept on,
      ZooKeeperCl ent.ZooKeeperConnect onExcept on, KeeperExcept on {
    return serverSetManager.getNumberOfServerSet mbers();
  }

  @Overr de
  publ c vo d leaveServerSet(Str ng userna ) throws UpdateExcept on {
    serverSetManager.leaveServerSet(userna );
  }

  @Overr de
  publ c vo d jo nServerSetForServ ceProxy() {
    serverSetManager.jo nServerSetForServ ceProxy();
  }

  @V s bleForTest ng
  protected stat c class Earlyb rdThr ftRequestLogg ngUt l {
    pr vate stat c f nal  nt DEFAULT_MAX_ENTR ES_TO_LOG = 50000;
    pr vate stat c f nal  nt DEFAULT_BUFFER_S ZE = 10000;
    pr vate stat c f nal  nt DEFAULT_LOGG NG_SLEEP_MS = 100;

    @V s bleForTest ng
    protected stat c volat le boolean thr ftLoggerBusy = false;
    pr vate stat c f nal ExecutorServ ce LOGG NG_EXECUTOR = Executors.newCac dThreadPool();

    // Synchron zed c rcular buffer used for buffer ng requests.
    //  f buffer  s full, t  oldest requests are replaced. T  should not be a problem for
    // logg ng purpose.
    @V s bleForTest ng
    protected stat c f nal ArrayBlock ngQueue<Earlyb rdRequest> REQUEST_BUFFER =
        new ArrayBlock ngQueue<>(DEFAULT_BUFFER_S ZE);


    /**
     * Create a separate thread to log thr ft request to t  g ven f le.  f a thread  s already
     * logg ng thr ft requests, t  does noth ng and throws an  OExcept on  nd cat ng that t 
     * logg ng thread  s busy.
     *
     * @param logF le F le to log to.
     * @param maxEntr esToLog Number of entr es to log.
     * @param postLogg ngHook Code to run after logg ng f n s s. Only used for test ng as of now.
     */
    @V s bleForTest ng
    protected stat c synchron zed vo d startThr ftLogg ng(f nal F le logF le,
                                                          f nal  nt maxEntr esToLog,
                                                          f nal Runnable postLogg ngHook)
        throws  OExcept on {
       f (thr ftLoggerBusy) {
        throw new  OExcept on("Already busy logg ng thr ft request. No act on taken.");
      }

       f (!logF le.canWr e()) {
        throw new  OExcept on("Unable to open log f le for wr  ng:  " + logF le);
      }

      f nal BufferedWr er thr ftLogWr er =
          F les.newBufferedWr er(logF le.toPath(), Charsets.UTF_8);

      // TSer al zer used by t  wr er thread.
      f nal TSer al zer ser al zer = new TSer al zer();

      REQUEST_BUFFER.clear();
      thr ftLoggerBusy = true;
      LOG. nfo("Started to log thr ft requests  nto f le " + logF le.getAbsolutePath());
      LOGG NG_EXECUTOR.subm (() -> {
        try {
           nt count = 0;
          wh le (count < maxEntr esToLog) {
             f (REQUEST_BUFFER. sEmpty()) {
              Thread.sleep(DEFAULT_LOGG NG_SLEEP_MS);
              cont nue;
            }

            try {
              Earlyb rdRequest ebRequest = REQUEST_BUFFER.poll();
              Str ng logL ne = ser al zeThr ftObject(ebRequest, ser al zer);
              thr ftLogWr er.wr e(logL ne);
              count++;
            } catch (TExcept on e) {
              LOG.warn("Unable to ser al ze Earlyb rdRequest for logg ng.", e);
            }
          }
          return count;
        } f nally {
          thr ftLogWr er.close();
          thr ftLoggerBusy = false;
          LOG. nfo("F n s d logg ng thr ft requests  nto f le " + logF le.getAbsolutePath());
          REQUEST_BUFFER.clear();
           f (postLogg ngHook != null) {
            postLogg ngHook.run();
          }
        }
      });
    }

    /**
     * Ser al ze a thr ft object to a base 64 encoded str ng.
     */
    pr vate stat c Str ng ser al zeThr ftObject(TBase<?, ?> tObject, TSer al zer ser al zer)
        throws TExcept on {
      return new Base64().encodeToStr ng(ser al zer.ser al ze(tObject)) + "\n";
    }
  }

  /**
   * Start to log thr ft Earlyb rdRequests.
   *
   * @param logF le Log f le to wr e to.
   * @param numRequestsToLog Number of requests to collect.  Default value of 50000 used  f
   * 0 or negat ve numbers are pass  n.
   */
  publ c vo d startThr ftLogg ng(F le logF le,  nt numRequestsToLog) throws  OExcept on {
     nt requestToLog = numRequestsToLog <= 0
        ? Earlyb rdThr ftRequestLogg ngUt l.DEFAULT_MAX_ENTR ES_TO_LOG : numRequestsToLog;
    Earlyb rdThr ftRequestLogg ngUt l.startThr ftLogg ng(logF le, requestToLog, null);
  }

  @V s bleForTest ng
  @Overr de
  publ c boolean  s nServerSet() {
    return serverSetManager. s nServerSet();
  }

  @V s bleForTest ng
  SearchCounter getResponseCodeCounter(Earlyb rdResponseCode responseCode) {
    return responseCodeCounters.get(responseCode);
  }

  @V s bleForTest ng
  SearchCounter getCl ent dResponseCodeCounter(
      Str ng cl ent d, Earlyb rdResponseCode responseCode) {
    Str ng key = Str ng.format(RESPONSES_PER_CL ENT_ D_STAT_TEMPLATE,
            cl ent d, responseCode.na ().toLo rCase());
    return responseByCl ent dAndResponseCode.getUnc cked(key);
  }

  publ c vo d setNoShutdownW nNot nLa t(boolean noShutdown) {
    stateManager.setNoShutdownW nNot nLa t(noShutdown);
  }

  pr vate vo d sc dule(OneTaskSc duledExecutorManager manager) {
     f (! sShutt ngDown) {
      manager.sc dule();
      toClose.add(manager);
    }
  }

  publ c Dynam cSc ma getSc ma() {
    return earlyb rd ndexConf g.getSc ma();
  }

  publ c Aud oSpaceTable getAud oSpaceTable() {
    return aud oSpaceTable;
  }
}
