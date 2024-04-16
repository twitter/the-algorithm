package com.tw ter.search.earlyb rd_root.routers;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchRank ngMode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common. nject onNa s;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;

publ c class RecencyRequestRouter extends AbstractRecencyAndRelevanceRequestRouter {
  pr vate stat c f nal SearchCounter SK PPED_ARCH VE_DUE_TO_REALT ME_EARLY_TERM NAT ON_COUNTER =
      SearchCounter.export("recency_sk pped_arch ve_due_to_realt  _early_term nat on");
  pr vate stat c f nal SearchCounter SK PPED_ARCH VE_DUE_TO_REALT ME_ENOUGH_RESULTS_COUNTER =
      SearchCounter.export("recency_sk pped_arch ve_due_to_realt  _enough_results");

  @ nject
  publ c RecencyRequestRouter(
      @Na d( nject onNa s.REALT ME)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ,
      @Na d( nject onNa s.PROTECTED)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> protectedRealt  ,
      @Na d( nject onNa s.FULL_ARCH VE)
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch ve,
      @Na d(RecencyRequestRouterModule.REALT ME_T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter realt  T  RangeF lter,
      @Na d(RecencyRequestRouterModule.PROTECTED_T ME_RANGE_F LTER)
      Earlyb rdT  RangeF lter protectedT  RangeF lter,
      @Na d(RecencyRequestRouterModule.FULL_ARCH VE_T ME_RANGE_F LTER)
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
          Thr ftSearchRank ngMode.RECENCY,
          clock,
          dec der,
          featureSc ma rger);
  }

  @Overr de
  protected boolean shouldSendRequestToFullArch veCluster(
      Earlyb rdRequest request, Earlyb rdResponse realt  Response) {
    boolean  sEarlyTerm nated = realt  Response. sSetEarlyTerm nat on nfo()
        && realt  Response.getEarlyTerm nat on nfo(). sEarlyTerm nated();
     f ( sEarlyTerm nated) {
      SK PPED_ARCH VE_DUE_TO_REALT ME_EARLY_TERM NAT ON_COUNTER. ncre nt();
      return false;
    }

    // C ck  f   have t  m n mum number of results to fulf ll t  or g nal request.
     nt numResultsRequested = request.getSearchQuery().getNumResults();
     nt actualNumResults = realt  Response.getSearchResults().getResultsS ze();
     f (actualNumResults >= numResultsRequested) {
      SK PPED_ARCH VE_DUE_TO_REALT ME_ENOUGH_RESULTS_COUNTER. ncre nt();
      return false;
    }

    return true;
  }
}
