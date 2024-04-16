package com.tw ter.search.common.relevance;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.Executors;
 mport java.ut l.concurrent.Sc duledExecutorServ ce;
 mport java.ut l.concurrent.T  Un ;
 mport java.ut l.concurrent.atom c.Atom cLong;
 mport java.ut l.stream.Collectors;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Sets;
 mport com.google.common.ut l.concurrent.ThreadFactoryBu lder;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.Thr ftMux;
 mport com.tw ter.f nagle.bu lder.Cl entBu lder;
 mport com.tw ter.f nagle.bu lder.Cl entConf g;
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er;
 mport com.tw ter.f nagle.mtls.cl ent.MtlsCl entBu lder;
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver;
 mport com.tw ter.f nagle.thr ft.Thr ftCl entRequest;
 mport com.tw ter.search.common. tr cs.RelevanceStats;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.trends.plus.Module;
 mport com.tw ter.trends.plus.TrendsPlusRequest;
 mport com.tw ter.trends.plus.TrendsPlusResponse;
 mport com.tw ter.trends.serv ce.gen.Locat on;
 mport com.tw ter.trends.trend ng_content.thr ftjava.Trend ngContentServ ce;
 mport com.tw ter.trends.trends_ tadata.thr ftjava.Trends tadataServ ce;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Try;

/**
 * Manages trends data retr eved from trends thr ft AP  and perform automat c refresh.
 */
publ c f nal class TrendsThr ftDataServ ceManager {
  pr vate stat c f nal Logger LOG =
    LoggerFactory.getLogger(TrendsThr ftDataServ ceManager.class.getNa ());

  pr vate stat c f nal  nt DEFAULT_T ME_TO_K LL_SEC = 60;

  @V s bleForTest ng
  protected stat c f nal Map<Str ng, Str ng> DEFAULT_TRENDS_PARAMS_MAP =  mmutableMap.of(
      "MAX_ TEMS_TO_RETURN", "10");   //   only take top 10 for each woe d.

  @V s bleForTest ng
  protected stat c f nal  nt MAX_TRENDS_PER_WOE D = 10;

  pr vate f nal Durat on requestT  out;
  pr vate f nal Durat on refreshDelayDurat on;
  pr vate f nal Durat on reload ntervalDurat on;
  pr vate f nal  nt numRetr es;

  // a l st of trends cac    want to update
  pr vate f nal L st<NGramCac > trendsCac L st;

  pr vate f nal SearchCounter getAva lableSuccessCounter =
      RelevanceStats.exportLong("trends_extractor_get_ava lable_success");
  pr vate f nal SearchCounter getAva lableFa lureCounter =
      RelevanceStats.exportLong("trends_extractor_get_ava lable_fa lure");
  pr vate f nal SearchCounter getTrendsSuccessCounter =
      RelevanceStats.exportLong("trends_extractor_success_fetch");
  pr vate f nal SearchCounter getTrendsFa lureCounter =
      RelevanceStats.exportLong("trends_extractor_fa led_fetch");
  pr vate f nal SearchCounter updateFa lureCounter =
      RelevanceStats.exportLong("trends_extractor_fa led_update");

  pr vate f nal Serv ce dent f er serv ce dent f er;
  pr vate Sc duledExecutorServ ce sc duler;


  @V s bleForTest ng
  protected Serv ce<Thr ftCl entRequest, byte[]> contentServ ce;
  protected Trend ngContentServ ce.Serv ceToCl ent contentCl ent;
  protected Serv ce<Thr ftCl entRequest, byte[]>  tadataServ ce;
  protected Trends tadataServ ce.Serv ceToCl ent  tadataCl ent;

  @V s bleForTest ng
  protected TrendsUpdater trendsUpdater;

  /**
   * Returns an  nstance of TrendsThr ftDataServ ceManager.
   * @param serv ce dent f er T  serv ce that wants to call
   *  nto Trend's serv ces.
   * @param numRetr es T  number of retr es  n t  event of
   * request fa lures.
   * @param requestT  out T  amount of t     wa  before   cons der a
   * a request as fa led.
   * @param  n TrendsCac Delay How long to wa  before t   n  al
   * f ll ng of t  Trends cac   n m ll seconds.
   * @param reload nterval How often to refresh t  cac  w h updated trends.
   * @param trendsCac L st T  cac  of trends.
   * @return An  nstance of TrendsThr ftDataServ ceManager conf gured
   * w h respect to t  params prov ded.
   */
  publ c stat c TrendsThr ftDataServ ceManager new nstance(
      Serv ce dent f er serv ce dent f er,
       nt numRetr es,
      Durat on requestT  out,
      Durat on  n TrendsCac Delay,
      Durat on reload nterval,
      L st<NGramCac > trendsCac L st) {
    return new TrendsThr ftDataServ ceManager(
        serv ce dent f er,
        numRetr es,
        requestT  out,
         n TrendsCac Delay,
        reload nterval,
        trendsCac L st);
  }

  /**
   * Resu  auto refresh. Always called  n constructor. Can be  nvoked after a
   * stopAuthRefresh call to resu  auto refresh ng.  nvok ng   after shutDown  s undef ned.
   */
  publ c synchron zed vo d startAutoRefresh() {
     f (sc duler == null) {
      sc duler = Executors.newS ngleThreadSc duledExecutor(
          new ThreadFactoryBu lder().setDaemon(true).setNa Format(
              "trends-data-refres r[%d]").bu ld());
      sc duler.sc duleAtF xedRate(
          trendsUpdater,
          refreshDelayDurat on. nSeconds(),
          reload ntervalDurat on. nSeconds(),
          T  Un .SECONDS);
    }
  }

  /**
   * Stop auto refresh. Wa  for t  current execut on thread to f n sh.
   * T   s a block ng call.
   */
  publ c synchron zed vo d stopAutoRefresh() {
     f (sc duler != null) {
      sc duler.shutdown(); // D sable new tasks from be ng subm ted
      try {
        // Wa  a wh le for ex st ng tasks to term nate
         f (!sc duler.awa Term nat on(DEFAULT_T ME_TO_K LL_SEC, T  Un .SECONDS)) {
          sc duler.shutdownNow(); // Cancel currently execut ng tasks
          // Wa  a wh le for tasks to respond to be ng cancelled
           f (!sc duler.awa Term nat on(DEFAULT_T ME_TO_K LL_SEC, T  Un .SECONDS)) {
            LOG. nfo("Executor thread pool d d not term nate.");
          }
        }
      } catch ( nterruptedExcept on  e) {
        // (Re-)Cancel  f current thread also  nterrupted
        sc duler.shutdownNow();
        // Preserve  nterrupt status
        Thread.currentThread(). nterrupt();
      }
      sc duler = null;
    }
  }

  /** Shuts down t  manager. */
  publ c vo d shutDown() {
    stopAutoRefresh();
    // clear t  cac 
    for (NGramCac  cac  : trendsCac L st) {
      cac .clear();
    }

     f (contentServ ce != null) {
      contentServ ce.close();
    }

     f ( tadataServ ce != null) {
       tadataServ ce.close();
    }
  }

  pr vate TrendsThr ftDataServ ceManager(
      Serv ce dent f er serv ce dent f er,
       nt numRetr es,
      Durat on requestT  outMS,
      Durat on refreshDelayDurat on,
      Durat on reload ntervalDurat on,
      L st<NGramCac > trendsCac L st) {
    t .numRetr es = numRetr es;
    t .requestT  out = requestT  outMS;
    t .refreshDelayDurat on = refreshDelayDurat on;
    t .reload ntervalDurat on = reload ntervalDurat on;
    t .serv ce dent f er = serv ce dent f er;
    t .trendsCac L st = Precond  ons.c ckNotNull(trendsCac L st);
    trendsUpdater = new TrendsUpdater();
     tadataServ ce = bu ld tadataServ ce();
     tadataCl ent = bu ld tadataCl ent( tadataServ ce);
    contentServ ce = bu ldContentServ ce();
    contentCl ent = bu ldContentCl ent(contentServ ce);
  }

  @V s bleForTest ng
  protected Serv ce<Thr ftCl entRequest, byte[]> bu ldContentServ ce() {
    Cl entBu lder<
        Thr ftCl entRequest,
        byte[], Cl entConf g.Yes,
        Cl entConf g.Yes,
        Cl entConf g.Yes
        >
        bu lder = Cl entBu lder.get()
          .stack(Thr ftMux.cl ent())
          .na ("trends_thr ft_data_serv ce_manager_content")
          .dest("")
          .retr es(numRetr es)
          .reportTo(DefaultStatsRece ver.get())
          .tcpConnectT  out(requestT  out)
          .requestT  out(requestT  out);
    Cl entBu lder mtlsBu lder =
        new MtlsCl entBu lder.MtlsCl entBu lderSyntax<>(bu lder).mutualTls(serv ce dent f er);

    return Cl entBu lder.safeBu ld(mtlsBu lder);
  }

  @V s bleForTest ng
  protected Trend ngContentServ ce.Serv ceToCl ent bu ldContentCl ent(
      Serv ce<Thr ftCl entRequest, byte[]> serv ce) {
    return new Trend ngContentServ ce.Serv ceToCl ent(serv ce);
  }

  @V s bleForTest ng
  protected Serv ce<Thr ftCl entRequest, byte[]> bu ld tadataServ ce() {
    Cl entBu lder<
        Thr ftCl entRequest,
        byte[],
        Cl entConf g.Yes,
        Cl entConf g.Yes,
        Cl entConf g.Yes
        >
        bu lder = Cl entBu lder.get()
          .stack(Thr ftMux.cl ent())
          .na ("trends_thr ft_data_serv ce_manager_ tadata")
          .dest("")
          .retr es(numRetr es)
          .reportTo(DefaultStatsRece ver.get())
          .tcpConnectT  out(requestT  out)
          .requestT  out(requestT  out);
    Cl entBu lder mtlsBu lder =
        new MtlsCl entBu lder.MtlsCl entBu lderSyntax<>(bu lder).mutualTls(serv ce dent f er);

    return Cl entBu lder.safeBu ld(mtlsBu lder);
  }

  @V s bleForTest ng
  protected Trends tadataServ ce.Serv ceToCl ent bu ld tadataCl ent(
      Serv ce<Thr ftCl entRequest, byte[]> serv ce) {
    return new Trends tadataServ ce.Serv ceToCl ent(serv ce);
  }

  /**
   * Updater that fetc s ava lable woe ds and correspond ng trend ng terms.
   */
  @V s bleForTest ng
  protected class TrendsUpdater  mple nts Runnable {
    @Overr de
    publ c vo d run() {
      populateCac FromTrendsServ ce();
    }

    pr vate Future<BoxedUn > populateCac FromTrendsServ ce() {
      long startT   = System.currentT  M ll s();
      Atom cLong numTrendsRece ved = new Atom cLong(0);
      return  tadataCl ent.getAva lable().flatMap(locat ons -> {
         f (locat ons == null) {
          getAva lableFa lureCounter. ncre nt();
          LOG.warn("Fa led to get woe ds from trends.");
          return Future.value(BoxedUn .UN T);
        }
        getAva lableSuccessCounter. ncre nt();
        return populateCac FromTrendLocat ons(locat ons, numTrendsRece ved);
      }).onFa lure(throwable -> {
        LOG. nfo("Update fa led", throwable);
        updateFa lureCounter. ncre nt();
        return BoxedUn .UN T;
      }).ensure(() -> {
        logRefreshStatus(startT  , numTrendsRece ved);
        return BoxedUn .UN T;
      });
    }

    pr vate Future<BoxedUn > populateCac FromTrendLocat ons(
        L st<Locat on> locat ons,
        Atom cLong numTrendsRece ved) {
      L st<Future<TrendsPlusResponse>> trendsPlusFutures = locat ons.stream()
          .map(locat on -> makeTrendsPlusRequest(locat on))
          .collect(Collectors.toL st());

      Future<L st<Try<TrendsPlusResponse>>> trendsPlusFuture =
          Future.collectToTry(trendsPlusFutures);
      return trendsPlusFuture.map(tryResponses -> {
        populateCac FromResponses(tryResponses, numTrendsRece ved);
        return BoxedUn .UN T;
      });
    }

    pr vate Future<TrendsPlusResponse> makeTrendsPlusRequest(Locat on locat on) {
      TrendsPlusRequest request = new TrendsPlusRequest()
          .setWoe d(locat on.getWoe d())
          .setMaxTrends(MAX_TRENDS_PER_WOE D);
      long startT   = System.currentT  M ll s();
      return contentCl ent.getTrendsPlus(request)
          .onSuccess(response -> {
            getTrendsSuccessCounter. ncre nt();
            return BoxedUn .UN T;
          }).onFa lure(throwable -> {
            getTrendsFa lureCounter. ncre nt();
            return BoxedUn .UN T;
          });
    }

    pr vate vo d populateCac FromResponses(
        L st<Try<TrendsPlusResponse>> tryResponses,
        Atom cLong numTrendsRece ved) {
      Set<Str ng> trendStr ngs = Sets.newHashSet();

      for (Try<TrendsPlusResponse> tryResponse : tryResponses) {
         f (tryResponse. sThrow()) {
          LOG.warn("Fa led to fetch trends:" + tryResponse.toStr ng());
          cont nue;
        }

        TrendsPlusResponse trendsPlusResponse = tryResponse.get();
        numTrendsRece ved.addAndGet(trendsPlusResponse.modules.s ze());
        for (Module module : trendsPlusResponse.modules) {
          trendStr ngs.add(module.getTrend().na );
        }
      }

      for (NGramCac  cac  : trendsCac L st) {
        cac .addAll(trendStr ngs);
      }
    }
  }

  pr vate vo d logRefreshStatus(long startT  , Atom cLong numTrendsRece ved) {
    LOG. nfo(Str ng.format("Refresh done  n [%dms] :\nfetchSuccess[%d] fetchFa lure[%d] "
            + "updateFa lure[%d] num trends rece ved [%d]",
        System.currentT  M ll s() - startT  ,
        getTrendsSuccessCounter.get(),
        getTrendsFa lureCounter.get(),
        updateFa lureCounter.get(),
        numTrendsRece ved.get()));
  }
}
