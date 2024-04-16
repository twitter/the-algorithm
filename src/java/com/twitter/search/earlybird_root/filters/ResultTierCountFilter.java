package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.L st;
 mport java.ut l.Nav gableMap;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect. mmutableSortedMap;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchCustomGauge;
 mport com.tw ter.search.earlyb rd.conf g.T er nfo;
 mport com.tw ter.search.earlyb rd.conf g.T er nfoS ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.snowflake. d.Snowflake d;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.FutureEventL stener;

/**
 * A f lter to count t  t er to wh ch t  oldest t et  n t  results belong.
 */
@S ngleton
publ c class ResultT erCountF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Str ng COUNTER_PREF X = "result_t er_count";
  pr vate f nal long f rstT etT  S nceEpochSec;
  pr vate f nal Nav gableMap<Long, SearchCounter> t erBuckets;
  pr vate f nal SearchCounter allCounter = SearchCounter.export(COUNTER_PREF X + "_all");
  pr vate f nal SearchCounter noResultsCounter =
      SearchCounter.export(COUNTER_PREF X + "_no_results");

  @ nject
  @SuppressWarn ngs("unused")
  ResultT erCountF lter(T er nfoS ce t er nfoS ce) {
    L st<T er nfo> t er nfos = t er nfoS ce.getT er nformat on();
    t er nfos.sort(Comparator.compar ng(T er nfo::getDataStartDate));

    f rstT etT  S nceEpochSec = t er nfos.get(0).getServ ngRangeS nceT  SecondsFromEpoch();

     mmutableSortedMap.Bu lder<Long, SearchCounter> bu lder =  mmutableSortedMap.naturalOrder();
    Collect ons.reverse(t er nfos);

    for (T er nfo t er nfo : t er nfos) {
      SearchCounter searchCounter = SearchCounter.export(
          Str ng.format("%s_%s", COUNTER_PREF X, t er nfo.getT erNa ()));
      bu lder.put(t er nfo.getServ ngRangeS nceT  SecondsFromEpoch(), searchCounter);

      // export cumulat ve  tr cs to sum from t  latest to a lo r t er
      Collect on<SearchCounter> counters = bu lder.bu ld().values();
      SearchCustomGauge.export(
          Str ng.format("%s_down_to_%s", COUNTER_PREF X, t er nfo.getT erNa ()),
          () -> counters.stream()
              .mapToLong(SearchCounter::get)
              .sum());
    }

    t erBuckets = bu lder.bu ld();
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext context,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    return serv ce.apply(context).addEventL stener(
        new FutureEventL stener<Earlyb rdResponse>() {
          @Overr de
          publ c vo d onFa lure(Throwable cause) {
            // do noth ng
          }

          @Overr de
          publ c vo d onSuccess(Earlyb rdResponse response) {
            record(response);
          }
        });
  }

  @V s bleForTest ng
  vo d record(Earlyb rdResponse response) {
     f (response. sSetSearchResults()) {
      long m nResultsStatus d = response.getSearchResults().getResults().stream()
          .mapToLong(Thr ftSearchResult::get d)
          .m n()
          .orElse(-1);
      getBucket(m nResultsStatus d). ncre nt();
    }
    allCounter. ncre nt();
  }

  pr vate SearchCounter getBucket(long status d) {
     f (status d < 0) {
      return noResultsCounter;
    }

    //  f non-negat ve status d  s not a Snowflake d, t  t et must have been created before
    // T poch (2010-11-04T01:42:54Z) and thus belongs to full1.
    long t  S nceEpochSec = f rstT etT  S nceEpochSec;
     f (Snowflake d. sSnowflake d(status d)) {
      t  S nceEpochSec = Snowflake d.t  From d(status d). nSeconds();
    }

    return t erBuckets.floorEntry(t  S nceEpochSec).getValue();
  }
}
