package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Map;

 mport javax. nject. nject;
 mport javax. nject.S ngleton;

 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.Maps;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Cl entErrorExcept on;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.earlyb rd_root.routers.FacetsRequestRouter;
 mport com.tw ter.search.earlyb rd_root.routers.RecencyRequestRouter;
 mport com.tw ter.search.earlyb rd_root.routers.RelevanceRequestRouter;
 mport com.tw ter.search.earlyb rd_root.routers.RequestRouter;
 mport com.tw ter.search.earlyb rd_root.routers.TermStatsRequestRouter;
 mport com.tw ter.search.earlyb rd_root.routers.TopT etsRequestRouter;
 mport com.tw ter.ut l.Future;

@S ngleton
publ c class SuperRootRequestTypeRouter
    extends Serv ce<Earlyb rdRequestContext, Earlyb rdResponse>  {

  pr vate f nal Map<Earlyb rdRequestType, RequestRouter> rout ngMap;

  /**
   * constructor
   */
  @ nject
  publ c SuperRootRequestTypeRouter(
      FacetsRequestRouter facetsRequestRouter,
      TermStatsRequestRouter termStatsRequestRouter,
      TopT etsRequestRouter topT etsRequestRouter,
      RecencyRequestRouter recencyRequestRouter,
      RelevanceRequestRouter relevanceRequestRouter
  ) {
    rout ngMap = Maps. mmutableEnumMap(
         mmutableMap.<Earlyb rdRequestType, RequestRouter>bu lder()
            .put(Earlyb rdRequestType.FACETS, facetsRequestRouter)
            .put(Earlyb rdRequestType.TERM_STATS, termStatsRequestRouter)
            .put(Earlyb rdRequestType.TOP_TWEETS, topT etsRequestRouter)
            .put(Earlyb rdRequestType.RECENCY, recencyRequestRouter)
            .put(Earlyb rdRequestType.STR CT_RECENCY, recencyRequestRouter)
            .put(Earlyb rdRequestType.RELEVANCE, relevanceRequestRouter)
            .bu ld());
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(Earlyb rdRequestContext requestContext) {
    Earlyb rdRequest request = requestContext.getRequest();
     f (request.getSearchQuery() == null) {
      return Future.except on(new Cl entErrorExcept on(
          "Cl ent must f ll  n search Query object  n request"));
    }

    Earlyb rdRequestType requestType = requestContext.getEarlyb rdRequestType();

     f (rout ngMap.conta nsKey(requestType)) {
      RequestRouter router = rout ngMap.get(requestType);
      return router.route(requestContext);
    } else {
      return Future.except on(
          new Cl entErrorExcept on(
            "Request type " + requestType + "  s unsupported.  "
                  + "Sorry t  ap   s a b  hard to use.\n"
                  + "for facets, call earlyb rdRequest.setFacetsRequest\n"
                  + "for termstats, call earluyb rdRequest.setTermStat st csRequest\n"
                  + "for recency, str ct recency, relevance or topt ets,\n"
                  + "   call req.setSearchQuery() and req.getSearchQuery().setRank ngMode()\n"
                  + "   w h t  correct rank ng mode and for str ct recency call\n"
                  + "   earlyb rdRequest.setQueryS ce(Thr ftQueryS ce.GN P)\n"));
    }
  }
}
