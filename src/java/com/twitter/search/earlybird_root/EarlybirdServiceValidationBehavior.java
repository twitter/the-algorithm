package com.tw ter.search.earlyb rd_root;

 mport org.apac .thr ft.TExcept on;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.root.Val dat onBehav or;
 mport com.tw ter.search.earlyb rd.common.Earlyb rdRequestUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchQuery;

publ c class Earlyb rdServ ceVal dat onBehav or
    extends Val dat onBehav or.DefaultVal dat onBehav or<Earlyb rdRequest, Earlyb rdResponse> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdServ ceVal dat onBehav or.class);

  pr vate stat c f nal Earlyb rdDebug nfo EARLYB RD_DEBUG_ NFO =
          new Earlyb rdDebug nfo().setHost("earlyb rd_root");

  pr vate stat c f nal SearchCounter  NVAL D_SUCCESS_RESPONSE_THRESHOLD_TOO_LOW =
      SearchCounter.export(" nval d_success_response_threshold_too_low");
  pr vate stat c f nal SearchCounter  NVAL D_SUCCESS_RESPONSE_THRESHOLD_TOO_H GH =
      SearchCounter.export(" nval d_success_response_threshold_too_h gh");

  protected Earlyb rdResponse createErrorResponse(Str ng errorMsg) {
    Earlyb rdResponse response = new Earlyb rdResponse(Earlyb rdResponseCode.CL ENT_ERROR, 0);

    //  're chang ng so  ERROR logs to WARN on   s de, so   want to ensure
    // that t  response conta ns t  debug  nformat on t  cl ent needs to
    // resolve t  problem.
    response.setDebug nfo(EARLYB RD_DEBUG_ NFO);
    response.setDebugStr ng(errorMsg);

    return response;
  }

  @Overr de
  publ c Earlyb rdResponse getResponse f nval dRequest(Earlyb rdRequest request) {
    // F rst, f x up t  query.
    Earlyb rdRequestUt l.c ckAndSetCollectorParams(request);
    Earlyb rdRequestUt l.logAndF xExcess veValues(request);

    try {
      request.val date();
    } catch (TExcept on e) {
      Str ng errorMsg = " nval d Earlyb rdRequest. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (request. sSetSearchSeg nt d() && request.getSearchSeg nt d() <= 0) {
      Str ng errorMsg = "Bad t   sl ce  D: " + request.getSearchSeg nt d();
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (request. sSetTermStat st csRequest()
        && request.getTermStat st csRequest(). sSet togramSett ngs()
        && request.getTermStat st csRequest().get togramSett ngs().getNumB ns() == 0) {

      Str ng errorMsg = "numB ns for term stat st cs  tograms request cannot be zero: " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (!request. sSetSearchQuery()
        || request.getSearchQuery() == null) {
      Str ng errorMsg = " nval d Earlyb rdRequest, no Thr ftSearchQuery spec f ed. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

    Thr ftSearchQuery searchQuery = request.getSearchQuery();

     f (!searchQuery.getCollectorParams(). sSetNumResultsToReturn()) {
      Str ng errorMsg = "Thr ftSearchQuery.numResultsToReturn not set. " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (searchQuery.getCollectorParams().getNumResultsToReturn() < 0) {
      Str ng errorMsg = " nval d Thr ftSearchQuery.collectorParams.numResultsToReturn: "
          + searchQuery.getCollectorParams().getNumResultsToReturn() + ". " + request;
      LOG.warn(errorMsg);
      return createErrorResponse(errorMsg);
    }

     f (request. sSetSuccessfulResponseThreshold()) {
      double successfulResponseThreshold = request.getSuccessfulResponseThreshold();
       f (successfulResponseThreshold <= 0) {
        Str ng errorMsg = "Success response threshold  s below or equal to 0: "
            + successfulResponseThreshold + " request: " + request;
        LOG.warn(errorMsg);
         NVAL D_SUCCESS_RESPONSE_THRESHOLD_TOO_LOW. ncre nt();
        return createErrorResponse(errorMsg);
      } else  f (successfulResponseThreshold > 1) {
        Str ng errorMsg = "Success response threshold  s above 1: " + successfulResponseThreshold
            + " request: " + request;
        LOG.warn(errorMsg);
         NVAL D_SUCCESS_RESPONSE_THRESHOLD_TOO_H GH. ncre nt();
        return createErrorResponse(errorMsg);
      }
    }

    return null;
  }
}
