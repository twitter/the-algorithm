package com.tw ter.search. ngester.p pel ne.ut l;

 mport java.ut l.HashSet;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.collect.Sets;

 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Prom se;

/**
 * Batc s s ngle requests of type RQ -> Future<RP> to an underly ng cl ent that supports batch
 * calls w h mult ple values of type RQ. Threadsafe.
 */
publ c class Batch ngCl ent<RQ, RP> {
  @Funct onal nterface
  publ c  nterface BatchCl ent<RQ, RP> {
    /**
     *  ssue a request to t  underly ng store wh ch supports batc s of requests.
     */
    Future<Map<RQ, RP>> batchGet(Set<RQ> requests);
  }

  /**
   * unsentRequests  s not threadsafe, and so   must be externally synchron zed.
   */
  pr vate f nal HashSet<RQ> unsentRequests = new HashSet<>();

  pr vate f nal ConcurrentHashMap<RQ, Prom se<RP>> prom ses = new ConcurrentHashMap<>();

  pr vate f nal BatchCl ent<RQ, RP> batchCl ent;
  pr vate f nal  nt batchS ze;

  publ c Batch ngCl ent(
      BatchCl ent<RQ, RP> batchCl ent,
       nt batchS ze
  ) {
    t .batchCl ent = batchCl ent;
    t .batchS ze = batchS ze;
  }

  /**
   * Send a request and rece ve a Future<RP>. T  future w ll not be resolved unt l at t re at
   * least batchS ze requests ready to send.
   */
  publ c Future<RP> call(RQ request) {
    Prom se<RP> prom se = prom ses.compute fAbsent(request, r -> new Prom se<>());

    maybeBatchCall(request);

    return prom se;
  }

  pr vate vo d maybeBatchCall(RQ request) {
    Set<RQ> frozenRequests;
    synchron zed (unsentRequests) {
      unsentRequests.add(request);
       f (unsentRequests.s ze() < batchS ze) {
        return;
      }

      // Make a copy of requests so   can mod fy    ns de executeBatchCall w hout add  onal
      // synchron zat on.
      frozenRequests = new HashSet<>(unsentRequests);
      unsentRequests.clear();
    }

    executeBatchCall(frozenRequests);
  }

  pr vate vo d executeBatchCall(Set<RQ> requests) {
    batchCl ent.batchGet(requests)
        .onSuccess(responseMap -> {
          for (Map.Entry<RQ, RP> entry : responseMap.entrySet()) {
            Prom se<RP> prom se = prom ses.remove(entry.getKey());
             f (prom se != null) {
              prom se.beco (Future.value(entry.getValue()));
            }
          }

          Set<RQ> outstand ngRequests = Sets.d fference(requests, responseMap.keySet());
          for (RQ request : outstand ngRequests) {
            Prom se<RP> prom se = prom ses.remove(request);
             f (prom se != null) {
              prom se.beco (Future.except on(new ResponseNotReturnedExcept on(request)));
            }
          }

          return null;
        })
        .onFa lure(except on -> {
          for (RQ request : requests) {
            Prom se<RP> prom se = prom ses.remove(request);
             f (prom se != null) {
              prom se.beco (Future.except on(except on));
            }
          }

          return null;
        });
  }
}

