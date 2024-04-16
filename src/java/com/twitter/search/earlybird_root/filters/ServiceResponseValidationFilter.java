package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.HashMap;
 mport java.ut l.Map;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponse rgeUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.earlyb rd_root.val dators.FacetsResponseVal dator;
 mport com.tw ter.search.earlyb rd_root.val dators.PassThroughResponseVal dator;
 mport com.tw ter.search.earlyb rd_root.val dators.Serv ceResponseVal dator;
 mport com.tw ter.search.earlyb rd_root.val dators.TermStatsResultsVal dator;
 mport com.tw ter.search.earlyb rd_root.val dators.TopT etsResultsVal dator;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

/**
 * F lter respons ble for handl ng  nval d response returned by downstream serv ces, and
 * translat ng t m  nto Earlyb rdResponseExcept ons.
 */
publ c class Serv ceResponseVal dat onF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate f nal Map<Earlyb rdRequestType, Serv ceResponseVal dator<Earlyb rdResponse>>
      requestTypeToResponseVal dators = new HashMap<>();
  pr vate f nal Earlyb rdCluster cluster;

  /**
   * Creates a new f lter for handl ng  nval d response
   */
  publ c Serv ceResponseVal dat onF lter(Earlyb rdCluster cluster) {
    t .cluster = cluster;

    Serv ceResponseVal dator<Earlyb rdResponse> passThroughVal dator =
        new PassThroughResponseVal dator();

    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.FACETS, new FacetsResponseVal dator(cluster));
    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.RECENCY, passThroughVal dator);
    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.RELEVANCE, passThroughVal dator);
    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.STR CT_RECENCY, passThroughVal dator);
    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.TERM_STATS, new TermStatsResultsVal dator(cluster));
    requestTypeToResponseVal dators
        .put(Earlyb rdRequestType.TOP_TWEETS, new TopT etsResultsVal dator(cluster));
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      f nal Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    return serv ce.apply(requestContext).flatMap(
        new Funct on<Earlyb rdResponse, Future<Earlyb rdResponse>>() {
          @Overr de
          publ c Future<Earlyb rdResponse> apply(Earlyb rdResponse response) {
             f (response == null) {
              return Future.except on(new  llegalStateExcept on(
                                          cluster + " returned null response"));
            }

             f (response.getResponseCode() == Earlyb rdResponseCode.SUCCESS) {
              return requestTypeToResponseVal dators
                .get(requestContext.getEarlyb rdRequestType())
                .val date(response);
            }

            return Future.value(Earlyb rdResponse rgeUt l.transform nval dResponse(
                response,
                Str ng.format("Fa lure from %s (%s)", cluster, response.getResponseCode())));
          }
        });
  }
}
