package com.tw ter.search.earlyb rd_root;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;

publ c class Earlyb rdProtectedVal dat onBehav or extends Earlyb rdServ ceVal dat onBehav or {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdProtectedVal dat onBehav or.class);

  @Overr de
  publ c Earlyb rdResponse getResponse f nval dRequest(Earlyb rdRequest request) {
     f (!request. sSetSearchQuery() || request.getSearchQuery() == null) {
      Str ng errorMsg = " nval d Earlyb rdRequest, no Thr ftSearchQuery spec f ed. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }
    Thr ftSearchQuery searchQuery = request.getSearchQuery();

    // Make sure t  request  s val d for t  protected t ets cluster.
     f (!searchQuery. sSetFromUser DF lter64() || searchQuery.getFromUser DF lter64(). sEmpty()) {
      Str ng errorMsg = "Thr ftSearchQuery.fromUser DF lter64 not set. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (!searchQuery. sSetSearc r d()) {
      Str ng errorMsg = "Thr ftSearchQuery.searc r d not set. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (searchQuery.getSearc r d() < 0) {
      Str ng errorMsg = " nval d Thr ftSearchQuery.searc r d: " + searchQuery.getSearc r d()
          + ". " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

    return super.getResponse f nval dRequest(request);
  }
}
