package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.HashMap;
 mport java.ut l.Map;
 mport javax.annotat on.Nullable;
 mport javax. nject. nject;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.constants.thr ftjava.Thr ftQueryS ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.ut l.Future;

/**
 * Rejects requests based on t  query s ce of t  request.  ntended to be used at super-root
 * or arch ve-root.  f used to reject cl ent request at super-root, t  cl ent w ll get a response
 * w h empty results and a REQUEST_BLOCKED_ERROR status code.  f used at arch ve-root t  cl ent
 * w ll get a response wh ch m ght conta n so  results from realt   and protected and t  status
 * code of t  response w ll depend on how super-root comb nes responses from t  three downstream
 * roots.
 */
publ c class RejectRequestsByQueryS ceF lter extends
    S mpleF lter<Earlyb rdRequest, Earlyb rdResponse> {

  @V s bleForTest ng
  protected stat c f nal Str ng NUM_REJECTED_REQUESTS_STAT_NAME_PATTERN =
      "num_root_%s_rejected_requests_w h_query_s ce_%s";
  @V s bleForTest ng
  protected stat c f nal Str ng REJECT_REQUESTS_DEC DER_KEY_PATTERN =
      "root_%s_reject_requests_w h_query_s ce_%s";
  pr vate f nal Map<Thr ftQueryS ce, SearchRateCounter> rejectedRequestsCounterPerQueryS ce =
      new HashMap<>();
  pr vate f nal Map<Thr ftQueryS ce, Str ng> rejectRequestsDec derKeyPerQueryS ce =
      new HashMap<>();
  pr vate f nal SearchDec der searchDec der;


  @ nject
  publ c RejectRequestsByQueryS ceF lter(
      @Nullable Earlyb rdCluster cluster,
      SearchDec der searchDec der) {

    t .searchDec der = searchDec der;

    Str ng clusterNa  = cluster != null
        ? cluster.getNa ForStats()
        : Earlyb rdCluster.SUPERROOT.getNa ForStats();

    for (Thr ftQueryS ce queryS ce : Thr ftQueryS ce.values()) {
      Str ng queryS ceNa  = queryS ce.na ().toLo rCase();

      rejectedRequestsCounterPerQueryS ce.put(queryS ce,
          SearchRateCounter.export(
              Str ng.format(
                  NUM_REJECTED_REQUESTS_STAT_NAME_PATTERN, clusterNa , queryS ceNa )));

      rejectRequestsDec derKeyPerQueryS ce.put(queryS ce,
          Str ng.format(
              REJECT_REQUESTS_DEC DER_KEY_PATTERN, clusterNa , queryS ceNa ));
    }
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest request,
                                         Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce) {

    Thr ftQueryS ce queryS ce = request. sSetQueryS ce()
        ? request.getQueryS ce()
        : Thr ftQueryS ce.UNKNOWN;

    Str ng dec derKey = rejectRequestsDec derKeyPerQueryS ce.get(queryS ce);
     f (searchDec der. sAva lable(dec derKey)) {
      rejectedRequestsCounterPerQueryS ce.get(queryS ce). ncre nt();
      return Future.value(getRejectedRequestResponse(queryS ce, dec derKey));
    }
    return serv ce.apply(request);
  }

  pr vate stat c Earlyb rdResponse getRejectedRequestResponse(
      Thr ftQueryS ce queryS ce, Str ng dec derKey) {
    return new Earlyb rdResponse(Earlyb rdResponseCode.REQUEST_BLOCKED_ERROR, 0)
        .setSearchResults(new Thr ftSearchResults())
        .setDebugStr ng(Str ng.format(
            "Request w h query s ce %s  s blocked by dec der %s", queryS ce, dec derKey));
  }
}
