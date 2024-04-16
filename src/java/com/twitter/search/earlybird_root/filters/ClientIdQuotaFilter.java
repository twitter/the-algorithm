package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Opt onal;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport javax. nject. nject;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.cac .Cac Bu lder;
 mport com.google.common.cac .Cac Loader;
 mport com.google.common.cac .Load ngCac ;
 mport com.google.common.ut l.concurrent.RateL m erProxy;
 mport com.google.common.ut l.concurrent.Tw terRateL m erProxyFactory;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.ut l.F nagleUt l;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.quota.Cl ent dQuotaManager;
 mport com.tw ter.search.earlyb rd_root.quota.Quota nfo;
 mport com.tw ter.ut l.Future;

/**
 * A f lter that tracks and l m s t  per-cl ent request rate. T   D of t  cl ent  s determ ned
 * by look ng at t  F nagle cl ent  D and t  Earlyb rdRequest.cl ent d f eld.
 *
 * T  conf gurat on currently has one conf g based  mple ntat on: see Conf gRepoBasedQuotaManager.
 *
 *  f a cl ent has a quota set, t  f lter w ll rate l m  t  requests from that cl ent based on
 * that quota. Ot rw se, t  cl ent  s assu d to use a "common request pool", wh ch has  s own
 * quota. A quota for t  common pool must always ex st (even  f  's set to 0).
 *
 * All rate l m ers used  n t  class are tolerant to bursts. See Tw terRateL m erFactory for
 * more deta ls.
 *
 *  f a cl ent sends us more requests than  s allo d quota,   keep track of t  excess traff c
 * and export that number  n a counter. Ho ver,   rate l m  t  requests from that cl ent only  f
 * t  Quota nfo returned from Cl ent dQuotaManager has t  shouldEnforceQuota property set to true.
 *
 *  f a request  s rate l m ed, t  f lter w ll return an Earlyb rdResponse w h a
 * QUOTA_EXCEEDED_ERROR response code.
 */
publ c class Cl ent dQuotaF lter extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal class Cl entQuota {
    pr vate f nal Quota nfo quota nfo;
    pr vate f nal boolean shouldAllowRequest;
    pr vate f nal Cl ent dRequestCounters requestCounters;

    pr vate Cl entQuota(
        Quota nfo quota nfo,
        boolean shouldAllowRequest,
        Cl ent dRequestCounters requestCounters) {

      t .quota nfo = quota nfo;
      t .shouldAllowRequest = shouldAllowRequest;
      t .requestCounters = requestCounters;
    }
  }

  pr vate stat c f nal class Cl ent dRequestCounters {
    pr vate stat c f nal Str ng REQUESTS_RECE VED_COUNTER_NAME_PATTERN =
        "quota_requests_rece ved_for_cl ent_ d_%s";

    pr vate stat c f nal Str ng THROTTLED_REQUESTS_COUNTER_NAME_PATTERN =
        "quota_requests_throttled_for_cl ent_ d_%s";

    pr vate stat c f nal Str ng REQUESTS_ABOVE_QUOTA_COUNTER_NAME_PATTERN =
        "quota_requests_above_quota_for_cl ent_ d_%s";

    pr vate stat c f nal Str ng REQUESTS_W TH N_QUOTA_COUNTER_NAME_PATTERN =
        "quota_requests_w h n_quota_for_cl ent_ d_%s";

    pr vate stat c f nal Str ng PER_CL ENT_QUOTA_GAUGE_NAME_PATTERN =
        "quota_for_cl ent_ d_%s";

    pr vate f nal SearchRateCounter throttledRequestsCounter;
    pr vate f nal SearchRateCounter requestsRece vedCounter;
    pr vate f nal SearchRateCounter requestsAboveQuotaCounter;
    pr vate f nal SearchRateCounter requestsW h nQuotaCounter;
    pr vate f nal SearchLongGauge quotaCl entGauge;

    pr vate Cl ent dRequestCounters(Str ng cl ent d) {
      t .throttledRequestsCounter = SearchRateCounter.export(
          Str ng.format(THROTTLED_REQUESTS_COUNTER_NAME_PATTERN, cl ent d));

      t .requestsRece vedCounter = SearchRateCounter.export(
          Str ng.format(REQUESTS_RECE VED_COUNTER_NAME_PATTERN, cl ent d), true);

      t .quotaCl entGauge = SearchLongGauge.export(
          Str ng.format(PER_CL ENT_QUOTA_GAUGE_NAME_PATTERN, cl ent d));

      t .requestsAboveQuotaCounter = SearchRateCounter.export(
            Str ng.format(REQUESTS_ABOVE_QUOTA_COUNTER_NAME_PATTERN, cl ent d));

      t .requestsW h nQuotaCounter = SearchRateCounter.export(
            Str ng.format(REQUESTS_W TH N_QUOTA_COUNTER_NAME_PATTERN, cl ent d));
    }
  }

  pr vate stat c f nal Str ng REQUESTS_RECE VED_FOR_EMA L_COUNTER_NAME_PATTERN =
      "quota_requests_rece ved_for_ema l_%s";

  //   have t  aggregate stat only because do ng sumany(...) on t 
  // per-cl ent stat st c  s too expens ve for an alert.
  @V s bleForTest ng
  stat c f nal SearchRateCounter TOTAL_REQUESTS_RECE VED_COUNTER =
      SearchRateCounter.export("total_quota_requests_rece ved", true);

  pr vate stat c f nal  nt DEFAULT_BURST_FACTOR_SECONDS = 60;
  pr vate stat c f nal Str ng QUOTA_STAT_CACHE_S ZE = "quota_stat_cac _s ze";
  pr vate stat c f nal Str ng M SS NG_QUOTA_FOR_CL ENT_ D_COUNTER_NAME_PATTERN =
      "quota_requests_w h_m ss ng_quota_for_cl ent_ d_%s";

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Cl ent dQuotaF lter.class);

  pr vate f nal ConcurrentMap<Str ng, RateL m erProxy> rateL m erProx esByCl ent d =
      new ConcurrentHashMap<>();

  pr vate f nal Cl ent dQuotaManager quotaManager;
  pr vate f nal Tw terRateL m erProxyFactory rateL m erProxyFactory;
  pr vate f nal Load ngCac <Str ng, Cl ent dRequestCounters> cl entRequestCounters;
  pr vate f nal Load ngCac <Str ng, SearchRateCounter> ema lRequestCounters;

  /** Creates a new Cl ent dQuotaF lter  nstance. */
  @ nject
  publ c Cl ent dQuotaF lter(Cl ent dQuotaManager quotaManager,
                             Tw terRateL m erProxyFactory rateL m erProxyFactory) {
    t .quotaManager = quotaManager;
    t .rateL m erProxyFactory = rateL m erProxyFactory;

    t .cl entRequestCounters = Cac Bu lder.newBu lder()
        .bu ld(new Cac Loader<Str ng, Cl ent dRequestCounters>() {
          @Overr de
          publ c Cl ent dRequestCounters load(Str ng cl ent d) {
            return new Cl ent dRequestCounters(cl ent d);
          }
        });
    t .ema lRequestCounters = Cac Bu lder.newBu lder()
        .bu ld(new Cac Loader<Str ng, SearchRateCounter>() {
          @Overr de
          publ c SearchRateCounter load(Str ng ema l) {
            return SearchRateCounter.export(
                Str ng.format(REQUESTS_RECE VED_FOR_EMA L_COUNTER_NAME_PATTERN, ema l));
          }
        });

    SearchCustomGauge.export(QUOTA_STAT_CACHE_S ZE, () -> cl entRequestCounters.s ze());
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {
    Str ng f nagleCl ent d = F nagleUt l.getF nagleCl entNa ();
    Str ng requestCl ent d = Cl ent dUt l.getCl ent dFromRequest(request);
    LOG.debug(Str ng.format("Cl ent  d from request or attr but on: %s", requestCl ent d));

    // Mult ple cl ent  ds may be grouped  nto a s ngle quota cl ent  d, all t 
    // unknown or unset cl ent  ds for example.
    Str ng quotaCl ent d = Cl ent dUt l.getQuotaCl ent d(requestCl ent d);
    LOG.debug(Str ng.format("Cl ent  d used for c ck ng quota: %s", quotaCl ent d));

    Cl entQuota cl entQuota = getCl entQuota(quotaCl ent d);
     f (!cl entQuota.shouldAllowRequest && cl entQuota.quota nfo.shouldEnforceQuota()) {
      cl entQuota.requestCounters.throttledRequestsCounter. ncre nt();

      return Future.value(getQuotaExceededResponse(
          f nagleCl ent d,
          cl entQuota.quota nfo.getQuotaCl ent d(),
          cl entQuota.quota nfo.getQuota()));
    }

    return serv ce.apply(request);
  }

  pr vate Cl entQuota getCl entQuota(Str ng cl ent d) {
    Opt onal<Quota nfo> quota nfoOpt onal = quotaManager.getQuotaForCl ent(cl ent d);
     f (!quota nfoOpt onal. sPresent()) {
      SearchRateCounter noQuotaFoundForCl entCounter = SearchRateCounter.export(
          Str ng.format(M SS NG_QUOTA_FOR_CL ENT_ D_COUNTER_NAME_PATTERN, cl ent d));
      noQuotaFoundForCl entCounter. ncre nt();
    }

    //  f a quota was set for t  cl ent, use  . Ot rw se, use t  common pool's quota.
    // A quota for t  common pool must always ex st.
    Quota nfo quota nfo = quota nfoOpt onal.orElseGet(quotaManager::getCommonPoolQuota);

    Cl ent dRequestCounters requestCounters = cl entRequestCounters
        .getUnc cked(quota nfo.getQuotaCl ent d());
    ema lRequestCounters.getUnc cked(quota nfo.getQuotaEma l()). ncre nt();

    //  ncre nt a stat for each request t  f lter rece ves.
    requestCounters.requestsRece vedCounter. ncre nt();

    // Also  ncre nt t  total stat
    TOTAL_REQUESTS_RECE VED_COUNTER. ncre nt();

    //  f shouldEnforceQuota  s false,   already know that t  request w ll be allo d.
    // Ho ver,   st ll want to update t  rate l m er and t  stats.
    f nal boolean requestAllo d;
     f (quota nfo.getQuota() == 0) {
      //  f t  quota for t  cl ent  s set to 0, t n t  request should not be allo d.
      //
      // Do not update t  rate l m er's rate: RateL m er only accepts pos  ve rates, and  n any
      // case,   already know that t  request should not be allo d.
      requestAllo d = false;
    } else {
      // T  quota  s not 0: update t  rate l m er w h t  new quota, and see  f t  request
      // should be allo d.
      RateL m erProxy rateL m erProxy = getCl entRateL m erProxy(quota nfo.getQuotaCl ent d(),
          quota nfo.getQuota());
      requestAllo d = rateL m erProxy.tryAcqu re();
    }

    // Report t  current quota for each cl ent
    requestCounters.quotaCl entGauge.set(quota nfo.getQuota());

    // Update t  correspond ng counter,  f t  request should not be allo d.
     f (!requestAllo d) {
      requestCounters.requestsAboveQuotaCounter. ncre nt();
    } else {
      requestCounters.requestsW h nQuotaCounter. ncre nt();
    }

    // Throttle t  request only  f t  quota for t  serv ce should be enforced.
    return new Cl entQuota(quota nfo, requestAllo d, requestCounters);
  }

  pr vate RateL m erProxy getCl entRateL m erProxy(Str ng cl ent d,  nt rate) {
    //  f a RateL m er for t  cl ent doesn't ex st, create one,
    // unless anot r thread beat us to  .
    RateL m erProxy cl entRateL m erProxy = rateL m erProx esByCl ent d.get(cl ent d);
     f (cl entRateL m erProxy == null) {
      cl entRateL m erProxy =
          rateL m erProxyFactory.createRateL m erProxy(rate, DEFAULT_BURST_FACTOR_SECONDS);
      RateL m erProxy ex st ngCl entRateL m erProxy =
        rateL m erProx esByCl ent d.put fAbsent(cl ent d, cl entRateL m erProxy);
       f (ex st ngCl entRateL m erProxy != null) {
        cl entRateL m erProxy = ex st ngCl entRateL m erProxy;
      }
      LOG. nfo("Us ng rate l m er w h rate {} for cl ent d {}.",
               cl entRateL m erProxy.getRate(), cl ent d);
    }

    // Update t  quota,  f needed.
     f (cl entRateL m erProxy.getRate() != rate) {
      LOG. nfo("Updat ng t  rate from {} to {} for cl ent d {}.",
               cl entRateL m erProxy.getRate(), rate, cl ent d);
      cl entRateL m erProxy.setRate(rate);
    }

    return cl entRateL m erProxy;
  }

  pr vate stat c Earlyb rdResponse getQuotaExceededResponse(
      Str ng f nagleCl ent d, Str ng quotaCl ent d,  nt quota) {
    return new Earlyb rdResponse(Earlyb rdResponseCode.QUOTA_EXCEEDED_ERROR, 0)
      .setSearchResults(new Thr ftSearchResults())
      .setDebugStr ng(Str ng.format(
          "Cl ent %s (f nagle cl ent  D %s) has exceeded  s request quota of %d. "
          + "Please request more quota at go/searchquota.",
          quotaCl ent d, f nagleCl ent d, quota));
  }
}
