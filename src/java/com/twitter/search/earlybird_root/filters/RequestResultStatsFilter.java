package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport javax. nject. nject;

 mport scala.runt  .BoxedUn ;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.Percent le;
 mport com.tw ter.search.common. tr cs.Percent leUt l;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.query.thr ftjava.CollectorParams;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;
 mport com.tw ter.search.earlyb rd.common.Cl ent dUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.snowflake. d.Snowflake d;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

publ c class RequestResultStatsF lter
    extends S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate f nal Clock clock;
  pr vate f nal RequestResultStats stats;

  stat c class RequestResultStats {
    pr vate stat c f nal Str ng PREF X = "request_result_propert es_";

    pr vate f nal SearchCounter resultsRequestedCount;
    pr vate f nal SearchCounter resultsReturnedCount;
    pr vate f nal SearchCounter maxH sToProcessCount;
    pr vate f nal SearchCounter h sProcessedCount;
    pr vate f nal SearchCounter docsProcessedCount;
    pr vate f nal SearchCounter t  outMsCount;
    pr vate Map<Str ng, Percent le< nteger>> requestedNumResultsPercent leByCl ent d;
    pr vate Map<Str ng, Percent le< nteger>> returnedNumResultsPercent leByCl ent d;
    pr vate Map<Str ng, Percent le<Long>> oldestResultPercent leByCl ent d;

    RequestResultStats() {
      // Request propert es
      resultsRequestedCount = SearchCounter.export(PREF X + "results_requested_cnt");
      maxH sToProcessCount = SearchCounter.export(PREF X + "max_h s_to_process_cnt");
      t  outMsCount = SearchCounter.export(PREF X + "t  out_ms_cnt");
      requestedNumResultsPercent leByCl ent d = new ConcurrentHashMap<>();

      // Result propert es
      resultsReturnedCount = SearchCounter.export(PREF X + "results_returned_cnt");
      h sProcessedCount = SearchCounter.export(PREF X + "h s_processed_cnt");
      docsProcessedCount = SearchCounter.export(PREF X + "docs_processed_cnt");
      returnedNumResultsPercent leByCl ent d = new ConcurrentHashMap<>();
      oldestResultPercent leByCl ent d = new ConcurrentHashMap<>();
    }

    SearchCounter getResultsRequestedCount() {
      return resultsRequestedCount;
    }

    SearchCounter getResultsReturnedCount() {
      return resultsReturnedCount;
    }

    SearchCounter getMaxH sToProcessCount() {
      return maxH sToProcessCount;
    }

    SearchCounter getH sProcessedCount() {
      return h sProcessedCount;
    }

    SearchCounter getDocsProcessedCount() {
      return docsProcessedCount;
    }

    SearchCounter getT  outMsCount() {
      return t  outMsCount;
    }

    Percent le<Long> getOldestResultPercent le(Str ng cl ent d) {
      return oldestResultPercent leByCl ent d.compute fAbsent(cl ent d,
          key -> Percent leUt l.createPercent le(statNa (cl ent d, "oldest_result_age_seconds")));
    }

    Percent le< nteger> getRequestedNumResultsPercent le(Str ng cl ent d) {
      return requestedNumResultsPercent leByCl ent d.compute fAbsent(cl ent d,
          key -> Percent leUt l.createPercent le(statNa (cl ent d, "requested_num_results")));
    }

    Percent le< nteger> getReturnedNumResultsPercent le(Str ng cl ent d) {
      return returnedNumResultsPercent leByCl ent d.compute fAbsent(cl ent d,
          key -> Percent leUt l.createPercent le(statNa (cl ent d, "returned_num_results")));
    }

    pr vate Str ng statNa (Str ng cl ent d, Str ng suff x) {
      return Str ng.format("%s%s_%s", PREF X, Cl ent dUt l.formatCl ent d(cl ent d), suff x);
    }
  }

  @ nject
  RequestResultStatsF lter(Clock clock, RequestResultStats stats) {
    t .clock = clock;
    t .stats = stats;
  }

  pr vate vo d updateRequestStats(Earlyb rdRequest request) {
    Thr ftSearchQuery searchQuery = request.getSearchQuery();
    CollectorParams collectorParams = searchQuery.getCollectorParams();

     f (collectorParams != null) {
      stats.getResultsRequestedCount().add(collectorParams.numResultsToReturn);
       f (request. sSetCl ent d()) {
        stats.getRequestedNumResultsPercent le(request.getCl ent d())
            .record(collectorParams.numResultsToReturn);
      }
      CollectorTerm nat onParams term nat onParams = collectorParams.getTerm nat onParams();
       f (term nat onParams != null) {
         f (term nat onParams. sSetMaxH sToProcess()) {
          stats.getMaxH sToProcessCount().add(term nat onParams.maxH sToProcess);
        }
         f (term nat onParams. sSetT  outMs()) {
          stats.getT  outMsCount().add(term nat onParams.t  outMs);
        }
      }
    } else {
       f (searchQuery. sSetNumResults()) {
        stats.getResultsRequestedCount().add(searchQuery.numResults);
         f (request. sSetCl ent d()) {
          stats.getRequestedNumResultsPercent le(request.getCl ent d())
              .record(searchQuery.numResults);
        }
      }
       f (searchQuery. sSetMaxH sToProcess()) {
        stats.getMaxH sToProcessCount().add(searchQuery.maxH sToProcess);
      }
       f (request. sSetT  outMs()) {
        stats.getT  outMsCount().add(request.t  outMs);
      }
    }
  }

  pr vate vo d updateResultsStats(Str ng cl ent d, Thr ftSearchResults results) {
    stats.getResultsReturnedCount().add(results.getResultsS ze());
     f (results. sSetNumH sProcessed()) {
      stats.getH sProcessedCount().add(results.numH sProcessed);
    }

     f (cl ent d != null) {
       f (results.getResultsS ze() > 0) {
        L st<Thr ftSearchResult> resultsL st = results.getResults();

        long last d = resultsL st.get(resultsL st.s ze() - 1).get d();
        long t etT   = Snowflake d.t  From d(last d). nLongSeconds();
        long t etAge = (clock.nowM ll s() / 1000) - t etT  ;
        stats.getOldestResultPercent le(cl ent d).record(t etAge);
      }

      stats.getReturnedNumResultsPercent le(cl ent d).record(results.getResultsS ze());
    }
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequest request,
      Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

    updateRequestStats(request);

    return serv ce.apply(request).onSuccess(
        new Funct on<Earlyb rdResponse, BoxedUn >() {
          @Overr de
          publ c BoxedUn  apply(Earlyb rdResponse response) {
             f (response. sSetSearchResults()) {
              updateResultsStats(request.getCl ent d(), response.searchResults);
            }
            return BoxedUn .UN T;
          }
        });
  }
}
