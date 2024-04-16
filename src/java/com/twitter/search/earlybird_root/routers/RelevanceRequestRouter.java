package com.tw ter.search.earlyb rd_root.routers;

 mport java.ut l.concurrent.T  Un ;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.query.thr ftjava.CollectorTerm nat onParams;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common. nject onNa s;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;

publ c class RelevanceRequestRouter extends AbstractRecencyAndRelevanceRequestRouter {
  pr vate stat c f nal long M LL S_ N_ONE_DAY = T  Un .DAYS.toM ll s(1);

  @ nject
  publ c RelevanceRequestRouter(
      @Na d( nject onNa s.REALT ME)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ,
      @Na d( nject onNa s.PROTECTED)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> protectedRealt  ,
      @Na d( nject onNa s.FULL_ARCH VE)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch ve,
      @Na d(RelevanceRequestRouterModule.REALT ME_T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter realt  T  RangeF lter,
      @Na d(RelevanceRequestRouterModule.PROTECTED_T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter protectedT  RangeF lter,
      @Na d(RelevanceRequestRouterModule.FULL_ARCH VE_T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter fullArch veT  RangeF lter,
      Clock clock,
      SearchDec der dec der,
      Earlyb rdFeatureSc ma rger featureSc ma rger) {
    super(realt  ,
          protectedRealt  ,
          fullArch ve,
          realt  T  RangeF lter,
          protectedT  RangeF lter,
          fullArch veT  RangeF lter,
          Thr ftSearchRank ngMode.RELEVANCE,
          clock,
          dec der,
          featureSc ma rger);
  }

  @Overr de
  protected boolean shouldSendRequestToFullArch veCluster(
      Earlyb rdRequest request, Earlyb rdResponse realt  Response) {
     nt numResultsRequested = request.getSearchQuery().getNumResults();
     nt numH sProcessed = realt  Response.getSearchResults(). sSetNumH sProcessed()
        ? realt  Response.getSearchResults().getNumH sProcessed()
        : -1;
     f (numH sProcessed < numResultsRequested) {
      // Send query to t  full arch ve cluster,  f    nt through fe r h s  n t  realt  
      // cluster than t  requested number of results.
      return true;
    }

    //  f   have enough h s, don't query t  full arch ve cluster yet.
     nt numSuccessfulPart  ons = realt  Response.getNumSuccessfulPart  ons();
    CollectorTerm nat onParams term nat onParams =
        request.getSearchQuery().getCollectorParams().getTerm nat onParams();

    Precond  ons.c ckArgu nt(term nat onParams. sSetMaxH sToProcess());
     nt maxH s = term nat onParams.getMaxH sToProcess() * numSuccessfulPart  ons;

     f (numH sProcessed >= maxH s) {
      return false;
    }

    // C ck  f t re  s a gap bet en t  last result and t  m n status  D of current search.
    //  f t  d fference  s larger than one day, t n   can st ll get more t ets from t  realt  
    // cluster, so t re's no need to query t  full arch ve cluster just yet.  f   don't c ck
    // t , t n   m ght end up w h a b g gap  n t  returned results.
     nt numReturnedResults = realt  Response.getSearchResults().getResultsS ze();
     f (numReturnedResults > 0) {
      Thr ftSearchResult lastResult =
          realt  Response.getSearchResults().getResults().get(numReturnedResults - 1);
      long lastResultT  M ll s = Snowflake dParser.getT  stampFromT et d(lastResult.get d());
      long m nSearc dStatus D = realt  Response.getSearchResults().getM nSearc dStatus D();
      long m nSearc dStatus DT  M ll s =
          Snowflake dParser.getT  stampFromT et d(m nSearc dStatus D);
       f (lastResultT  M ll s - m nSearc dStatus DT  M ll s > M LL S_ N_ONE_DAY) {
        return false;
      }
    }

    return true;
  }
}
