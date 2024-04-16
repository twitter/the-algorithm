package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.SortedSet;
 mport java.ut l.TreeSet;

 mport javax. nject. nject;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.root.Part  onConf g;
 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.common.root.RequestSuccessStats;
 mport com.tw ter.search.common.root.RootCl entServ ceBu lder;
 mport com.tw ter.search.common.root.ScatterGat rServ ce;
 mport com.tw ter.search.common.root.ScatterGat rSupport;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.conf g.T erConf g;
 mport com.tw ter.search.earlyb rd.conf g.T er nfo;
 mport com.tw ter.search.earlyb rd.conf g.T er nfoS ce;
 mport com.tw ter.search.earlyb rd.conf g.T er nfoUt l;
 mport com.tw ter.search.earlyb rd.conf g.T er nfoWrapper;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce.Serv ce face;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestContextToEarlyb rdRequestF lter;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

@S ngleton
publ c class Earlyb rdServ ceCha nBu lder {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdServ ceCha nBu lder.class);

  pr vate stat c f nal Str ng SEARCH_METHOD_NAME = "search";

  pr vate stat c f nal Earlyb rdResponse T ER_SK PPED_RESPONSE =
      new Earlyb rdResponse(Earlyb rdResponseCode.T ER_SK PPED, 0)
          .setSearchResults(new Thr ftSearchResults())
          .setDebugStr ng("Request to cluster dropped by dec der, or sent as dark read.");

  pr vate f nal Earlyb rdT erThrottleDec ders t erThrottleDec ders;

  pr vate f nal RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter;

  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng normal zedSearchRootNa ;
  pr vate f nal RootCl entServ ceBu lder<Serv ce face> cl entServ ceBu lder;
  pr vate f nal Str ng part  onPath;
  pr vate f nal  nt numPart  ons;
  pr vate f nal SortedSet<T er nfo> t er nfos;
  pr vate f nal Part  onAccessController part  onAccessController;
  pr vate f nal StatsRece ver statsRece ver;

  /**
   * Construct a ScatterGat rServ ceCha n, by load ng conf gurat ons from earlyb rd-t ers.yml.
   */
  @ nject
  publ c Earlyb rdServ ceCha nBu lder(
      Part  onConf g part  onConf g,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      Earlyb rdT erThrottleDec ders t erThrottleDec ders,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ,
      SearchDec der dec der,
      T er nfoS ce t erConf g,
      RootCl entServ ceBu lder<Serv ce face> cl entServ ceBu lder,
      Part  onAccessController part  onAccessController,
      StatsRece ver statsRece ver) {
    t .part  onAccessController = part  onAccessController;
    t .t erThrottleDec ders = Precond  ons.c ckNotNull(t erThrottleDec ders);
    t .requestContextToEarlyb rdRequestF lter = requestContextToEarlyb rdRequestF lter;
    t .normal zedSearchRootNa  = normal zedSearchRootNa ;
    t .dec der = dec der;
    t .statsRece ver = statsRece ver;

    L st<T er nfo> t er nformat on = t erConf g.getT er nformat on();
     f (t er nformat on == null || t er nformat on. sEmpty()) {
      LOG.error(
          "No t er found  n conf g f le {} D d   set SEARCH_ENV correctly?",
          t erConf g.getConf gF leType());
      throw new Runt  Except on("No t er found  n t er conf g f le.");
    }

    // Get t  t er  nfo from t  t er conf g yml f le
    TreeSet<T er nfo>  nfos = new TreeSet<>(T er nfoUt l.T ER_COMPARATOR);
     nfos.addAll(t er nformat on);
    t .t er nfos = Collect ons.unmod f ableSortedSet( nfos);
    t .cl entServ ceBu lder = cl entServ ceBu lder;
    t .part  onPath = part  onConf g.getPart  onPath();
    t .numPart  ons = part  onConf g.getNumPart  ons();

    LOG. nfo("Found t  follow ng t ers from conf g: {}", t er nfos);
  }

  /** Bu lds t  cha n of serv ces that should be quer ed on each request. */
  publ c L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> bu ldServ ceCha n(
      ScatterGat rSupport<Earlyb rdRequestContext, Earlyb rdResponse> support,
      Part  onLogg ngSupport<Earlyb rdRequestContext> part  onLogg ngSupport) {
    // Make sure t  t er serv ng ranges do not overlap and do not have gaps.
    T er nfoUt l.c ckT erServ ngRanges(t er nfos);

    L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> cha n = L sts.newArrayL st();

    for (T er nfo t er nfo : t er nfos) {
      Str ng t erNa  = t er nfo.getT erNa ();
       f (t er nfo. sEnabled()) {
        Str ng rewr tenPart  onPath = part  onPath;
        // T  rewr  ng rule must match t  rewr  ng rule  ns de
        // Earlyb rdServer#jo nServerSet().
         f (!T erConf g.DEFAULT_T ER_NAME.equals(t erNa )) {
          rewr tenPart  onPath = part  onPath + "/" + t erNa ;
        }

        cl entServ ceBu lder. n  al zeW hPathSuff x(
            t er nfo.getT erNa (),
            numPart  ons,
            rewr tenPart  onPath);

        try {
          cha n.add(createT erServ ce(
                        support, t er nfo, cl entServ ceBu lder, part  onLogg ngSupport));
        } catch (Except on e) {
          LOG.error("Fa led to bu ld cl ents for t er: {}", t er nfo.getT erNa ());
          throw new Runt  Except on(e);
        }

      } else {
        LOG. nfo("Sk pped d sabled t er: {}", t erNa );
      }
    }

    return cha n;
  }

  pr vate Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> createT erServ ce(
      ScatterGat rSupport<Earlyb rdRequestContext, Earlyb rdResponse> support,
      f nal T er nfo t er nfo,
      RootCl entServ ceBu lder<Serv ce face> bu lder,
      Part  onLogg ngSupport<Earlyb rdRequestContext> part  onLogg ngSupport) {

    f nal Str ng t erNa  = t er nfo.getT erNa ();
    RequestSuccessStats stats = new RequestSuccessStats(t erNa );

    L st<Serv ce<Earlyb rdRequest, Earlyb rdResponse>> serv ces =
        bu lder.safeBu ldServ ceL st(SEARCH_METHOD_NAME);

    // Get t  cl ent l st for t  t er, and apply t  degradat onTrackerF lter to each response.
    //
    //   currently do t  only for t  Earlyb rdSearchMult T erAdaptor (t  full arch ve cluster).
    //  f   want to do t  for all clusters (or  f   want to apply any ot r f lter to all
    // earlyb rd responses, for ot r clusters),   should change ScatterGat rServ ce's constructor
    // to take  n a f lter, and apply   t re.
    Cl entBackupF lter backupF lter = new Cl entBackupF lter(
        "root_" + Earlyb rdCluster.FULL_ARCH VE.getNa ForStats(),
        t erNa ,
        statsRece ver,
        dec der);
    L st<Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>> cl ents = L sts.newArrayL st();
    Cl entLatencyF lter latencyF lter = new Cl entLatencyF lter(t erNa );
    for (Serv ce<Earlyb rdRequest, Earlyb rdResponse> cl ent : serv ces) {
        cl ents.add(requestContextToEarlyb rdRequestF lter
            .andT n(backupF lter)
            .andT n(latencyF lter)
            .andT n(cl ent));
    }

    cl ents = Sk pPart  onF lter.wrapServ ces(t erNa , cl ents, part  onAccessController);

    // Bu ld t  scatter gat r serv ce for t  t er.
    // Each t er has t  r own stats.
    ScatterGat rServ ce<Earlyb rdRequestContext, Earlyb rdResponse> scatterGat rServ ce =
        new ScatterGat rServ ce<>(
            support, cl ents, stats, part  onLogg ngSupport);

    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> t erThrottleF lter =
        getT erThrottleF lter(t er nfo, t erNa );

    Earlyb rdT  RangeF lter t  RangeF lter =
        Earlyb rdT  RangeF lter.newT  RangeF lterW hQueryRewr er(
            (requestContext, userOverr de) -> new T er nfoWrapper(t er nfo, userOverr de),
            dec der);

    return t erThrottleF lter
        .andT n(t  RangeF lter)
        .andT n(scatterGat rServ ce);
  }

  pr vate S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> getT erThrottleF lter(
      f nal T er nfo t er nfo,
      f nal Str ng t erNa ) {

    // A f lter that throttles request rate.
    f nal Str ng t erThrottleDec derKey = t erThrottleDec ders.getT erThrottleDec derKey(
        normal zedSearchRootNa , t erNa );

    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> t erThrottleF lter =
        new S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse>() {
          pr vate f nal Map<T er nfo.RequestReadType, SearchCounter> readCounts =
              getReadCountsMap();

          pr vate Map<T er nfo.RequestReadType, SearchCounter> getReadCountsMap() {
            Map<T er nfo.RequestReadType, SearchCounter> readCountsMap =
                Maps.newEnumMap(T er nfo.RequestReadType.class);
            for (T er nfo.RequestReadType readType : T er nfo.RequestReadType.values()) {
              readCountsMap.put(readType,
                  SearchCounter.export("earlyb rd_t er_" + t erNa  + "_"
                      + readType.na ().toLo rCase() + "_read_count"));
            }
            return Collect ons.unmod f ableMap(readCountsMap);
          }

          pr vate f nal SearchCounter t erRequestDroppedByDec derCount =
              SearchCounter.export("earlyb rd_t er_" + t erNa 
                  + "_request_dropped_by_dec der_count");

          @Overr de
          publ c Future<Earlyb rdResponse> apply(
              Earlyb rdRequestContext requestContext,
              Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

            // a blank response  s returned w n a request  s dropped by dec der, or
            // a request  s sent as a dark read.
            f nal Future<Earlyb rdResponse> blankT erResponse = Future.value(T ER_SK PPED_RESPONSE);
             f (t erThrottleDec ders.shouldSendRequestToT er(t erThrottleDec derKey)) {
              T er nfoWrapper t er nfoWrapper =
                  new T er nfoWrapper(t er nfo, requestContext.useOverr deT erConf g());

              T er nfo.RequestReadType readType = t er nfoWrapper.getReadType();
              readCounts.get(readType). ncre nt();
              sw ch (readType) {
                case DARK:
                  // dark read: call backend but do not wa  for results
                  serv ce.apply(requestContext);
                  return blankT erResponse;
                case GREY:
                  // grey read: call backend, wa  for results, but d scard results.
                  return serv ce.apply(requestContext).flatMap(
                      new Funct on<Earlyb rdResponse, Future<Earlyb rdResponse>>() {
                        @Overr de
                        publ c Future<Earlyb rdResponse> apply(Earlyb rdResponse v1) {
                          // No matter what's returned, always return blankT erResponse.
                          return blankT erResponse;
                        }
                      });
                case L GHT:
                  // l ght read: return t  future from t  backend serv ce.
                  return serv ce.apply(requestContext);
                default:
                  throw new Runt  Except on("Unknown read type: " + readType);
              }
            } else {
              // Request  s dropped by throttle dec der
              t erRequestDroppedByDec derCount. ncre nt();
              return blankT erResponse;
            }
          }
        };
    return t erThrottleF lter;
  }
}
