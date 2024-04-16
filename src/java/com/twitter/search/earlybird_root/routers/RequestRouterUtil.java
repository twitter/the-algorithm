package com.tw ter.search.earlyb rd_root.routers;

 mport java.ut l.L st;

 mport com.google.common.base.Opt onal;
 mport com.google.common.collect.L sts;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdServ ceResponse;
 mport com.tw ter.ut l.Awa ;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

publ c f nal class RequestRouterUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(RequestRouterUt l.class);

  pr vate RequestRouterUt l() {
  }

  /**
   * Returns t  funct on that c cks  f t  m nSearc dStatus D on t   rged response  s h g r
   * than t  max  D  n t  request.
   *
   * @param requestContext T  request context that stores t  request.
   * @param operator T  operator that  're c ck ng aga nst (max_ d or unt l_t  ).
   * @param requestMax d T  max d spec f ed  n t  request ( n t  g ven operator).
   * @param realt  ResponseFuture T  response from t  realt   cluster.
   * @param protectedResponseFuture T  response from t  protected cluster.
   * @param fullArch veResponseFuture T  response from t  full arch ve cluster.
   * @param stat T  stat to  ncre nt  f m nSearc dStatus D on t   rged response  s h g r than
   *             t  max  D  n t  request.
   * @return A funct on that c cks  f t  m nSearc dStatus D on t   rged response  s h g r than
   *         t  max  D  n t  request.
   */
  publ c stat c Funct on<Earlyb rdResponse, Earlyb rdResponse> c ckM nSearc dStatus d(
      f nal Earlyb rdRequestContext requestContext,
      f nal Str ng operator,
      f nal Opt onal<Long> requestMax d,
      f nal Future<Earlyb rdServ ceResponse> realt  ResponseFuture,
      f nal Future<Earlyb rdServ ceResponse> protectedResponseFuture,
      f nal Future<Earlyb rdServ ceResponse> fullArch veResponseFuture,
      f nal SearchCounter stat) {
    return new Funct on<Earlyb rdResponse, Earlyb rdResponse>() {
      @Overr de
      publ c Earlyb rdResponse apply(Earlyb rdResponse  rgedResponse) {
         f (requestMax d. sPresent()
            && ( rgedResponse.getResponseCode() == Earlyb rdResponseCode.SUCCESS)
            &&  rgedResponse. sSetSearchResults()
            &&  rgedResponse.getSearchResults(). sSetM nSearc dStatus D()) {
          long m nSearc dStatus d =  rgedResponse.getSearchResults().getM nSearc dStatus D();
           f (m nSearc dStatus d > requestMax d.get()) {
            stat. ncre nt();
            //  're logg ng t  only for STR CT RECENCY as   was very spam  for all types of
            // request.   don't expect t  to happen for STR CT RECENCY but  're track ng
            // w h t  stat w n   happens for RELEVANCE and RECENCY
             f (requestContext.getEarlyb rdRequestType() == Earlyb rdRequestType.STR CT_RECENCY) {
              Str ng log ssage = "Response has a m nSearc dStatus D ({}) larger than request "
                  + operator + " ({})."
                  + "\nrequest type: {}"
                  + "\nrequest: {}"
                  + "\n rged response: {}"
                  + "\nrealt   response: {}"
                  + "\nprotected response: {}"
                  + "\nfull arch ve response: {}";
              L st<Object> log ssageParams = L sts.newArrayL st();
              log ssageParams.add(m nSearc dStatus d);
              log ssageParams.add(requestMax d.get());
              log ssageParams.add(requestContext.getEarlyb rdRequestType());
              log ssageParams.add(requestContext.getRequest());
              log ssageParams.add( rgedResponse);

              // T  realt  , protected and full arch ve response futures are "done" at t  po nt:
              //   have to wa  for t m  n order to bu ld t   rged response. So  's ok to call
              // Awa .result()  re to get t  responses:  's a no-op.
              try {
                log ssageParams.add(Awa .result(realt  ResponseFuture).getResponse());
              } catch (Except on e) {
                log ssageParams.add(e);
              }
              try {
                log ssageParams.add(Awa .result(protectedResponseFuture).getResponse());
              } catch (Except on e) {
                log ssageParams.add(e);
              }
              try {
                log ssageParams.add(Awa .result(fullArch veResponseFuture).getResponse());
              } catch (Except on e) {
                log ssageParams.add(e);
              }

              LOG.warn(log ssage, log ssageParams.toArray());
            }
          }
        }

        return  rgedResponse;
      }
    };
  }
}
