package com.tw ter.search.earlyb rd_root.routers;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;


 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common. nject onNa s;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdT  RangeF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Serv ngRangeProv der;
 mport com.tw ter.search.earlyb rd_root. rgers.Earlyb rdResponse rger;
 mport com.tw ter.search.earlyb rd_root. rgers.SuperRootResponse rger;
 mport com.tw ter.search.earlyb rd_root. rgers.TermStat st csResponse rger;
 mport com.tw ter.search.earlyb rd_root. rgers.T erResponseAccumulator;
 mport com.tw ter.ut l.Funct on;
 mport com.tw ter.ut l.Future;

 mport stat c com.tw ter.search.common.ut l.earlyb rd.TermStat st csUt l.determ neB nS ze;

/**
 * For TermStats traff c SuperRoot h s both realt   and arch ve  n parallel, and t n  rges
 * t  results.
 */
publ c class TermStatsRequestRouter extends RequestRouter {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(TermStatsRequestRouter.class);

  pr vate stat c f nal Str ng SUPERROOT_SK P_FULL_ARCH VE_CLUSTER_FOR_TERM_STATS_REQUESTS =
      "superroot_sk p_full_arch ve_cluster_for_term_stats_requests";

  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  Serv ce;
  pr vate f nal Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch veServ ce;

  pr vate f nal SearchDec der dec der;

  pr vate f nal Serv ngRangeProv der realt  Serv ngRangeProv der;

  @ nject
  publ c TermStatsRequestRouter(
      @Na d( nject onNa s.REALT ME)
          Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> realt  ,
      @Na d(TermStatsRequestRouterModule.REALT ME_T ME_RANGE_F LTER)
          Earlyb rdT  RangeF lter realt  T  RangeF lter,
      @Na d( nject onNa s.FULL_ARCH VE)
          Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> fullArch ve,
      @Na d(TermStatsRequestRouterModule.FULL_ARCH VE_T ME_RANGE_F LTER)
          Earlyb rdT  RangeF lter fullArch veT  RangeF lter,
      SearchDec der dec der) {
    LOG. nfo(" nstant at ng a TermStatsRequestRouter");

    t .realt  Serv ce = realt  T  RangeF lter
        .andT n(realt  );

    t .fullArch veServ ce = fullArch veT  RangeF lter
        .andT n(fullArch ve);

    t .dec der = dec der;
    t .realt  Serv ngRangeProv der = realt  T  RangeF lter.getServ ngRangeProv der();
  }

  /**
   * H  both realt   and full-arch ve clusters t n  rges term stat request.
   */
  @Overr de
  publ c Future<Earlyb rdResponse> route(Earlyb rdRequestContext requestContext) {
    L st<RequestResponse> requestResponses = new ArrayL st<>();

    Future<Earlyb rdResponse> realt  ResponseFuture = realt  Serv ce.apply(requestContext);
    t .saveRequestResponse(requestResponses, "realt  ", requestContext, realt  ResponseFuture);

    Future<Earlyb rdResponse> arch veResponseFuture =
        requestContext.getRequest(). sGetOlderResults()
            && !dec der. sAva lable(SUPERROOT_SK P_FULL_ARCH VE_CLUSTER_FOR_TERM_STATS_REQUESTS)
            ? fullArch veServ ce.apply(requestContext)
            : Future.value(emptyResponse());
    t .saveRequestResponse(requestResponses, "arch ve", requestContext, arch veResponseFuture);

    Future<Earlyb rdResponse>  rgedResponse =
         rge(realt  ResponseFuture, arch veResponseFuture, requestContext);

    return t .maybeAttachSentRequestsToDebug nfo(
        requestResponses,
        requestContext,
         rgedResponse
    );
  }

  /**
   *  rge responses from realt   and full arch ve clusters.
   */
  pr vate Future<Earlyb rdResponse>  rge(
      f nal Future<Earlyb rdResponse> realt  ResponseFuture,
      f nal Future<Earlyb rdResponse> arch veResponseFuture,
      f nal Earlyb rdRequestContext requestContext) {

    return realt  ResponseFuture.flatMap(
        new Funct on<Earlyb rdResponse, Future<Earlyb rdResponse>>() {
          @Overr de
          publ c Future<Earlyb rdResponse> apply(f nal Earlyb rdResponse realt  Response) {
             f (!Earlyb rdResponseUt l. sSuccessfulResponse(realt  Response)) {
              return Future.value(realt  Response);
            }

            return arch veResponseFuture.flatMap(
                new Funct on<Earlyb rdResponse, Future<Earlyb rdResponse>>() {
                  @Overr de
                  publ c Future<Earlyb rdResponse> apply(Earlyb rdResponse arch veResponse) {
                     f (!Earlyb rdResponseUt l. sSuccessfulResponse(arch veResponse)) {
                      return Future.value(
                           rgeW hUnsuccessfulArch veResponse(
                              requestContext, realt  Response, arch veResponse));
                    }

                    L st<Future<Earlyb rdResponse>> responses =
                         mmutableL st.<Future<Earlyb rdResponse>>bu lder()
                            .add(realt  ResponseFuture)
                            .add(arch veResponseFuture)
                            .bu ld();

                    Earlyb rdResponse rger  rger = new TermStat st csResponse rger(
                        requestContext, responses, new T erResponseAccumulator());

                    return  rger. rge().map(new Funct on<Earlyb rdResponse, Earlyb rdResponse>() {
                      @Overr de
                      publ c Earlyb rdResponse apply(Earlyb rdResponse  rgedResponse) {
                         f (requestContext.getRequest().getDebugMode() > 0) {
                           rgedResponse.setDebugStr ng(
                              SuperRootResponse rger. rgeClusterDebugStr ngs(
                                  realt  Response, null, arch veResponse));
                        }
                        return  rgedResponse;
                      }
                    });
                  }
                });
          }
        });
  }

  pr vate Earlyb rdResponse  rgeW hUnsuccessfulArch veResponse(
      Earlyb rdRequestContext requestContext,
      Earlyb rdResponse realt  Response,
      Earlyb rdResponse arch veResponse) {
    //  f t  realt   cluster was sk pped, and t  full arch ve returned an error
    // response, return t  full arch ve response.
     f ( sT erSk ppedResponse(realt  Response)) {
      return arch veResponse;
    }

    //  f t  realt   response has results and t  full arch ve cluster returned an error
    // response,   return t  realt   response.  f t  cl ent needs more results,   can pag nate,
    // and on t  next request   w ll get t  error response from t  full arch ve cluster.
     f (realt  Response. sSetTermStat st csResults()
        && !realt  Response.getTermStat st csResults().getTermResults(). sEmpty()) {
      realt  Response.setDebugStr ng(
          "Full arch ve cluster returned an error response ("
              + arch veResponse.getResponseCode() + "). "
              + SuperRootResponse rger. rgeClusterDebugStr ngs(
              realt  Response, null, arch veResponse));
      return updateM nCompleteB n d(requestContext, realt  Response);
    }

    //  f t  realt   response has no results, and t  full arch ve cluster returned an error
    // response, return a PERS STENT_ERROR response, and  rge t  debug str ngs from t  two
    // responses.
    Earlyb rdResponse  rgedResponse =
        new Earlyb rdResponse(Earlyb rdResponseCode.PERS STENT_ERROR, 0);
     rgedResponse.setDebugStr ng(
        "Full arch ve cluster returned an error response ("
            + arch veResponse.getResponseCode()
            + "), and t  realt   response had no results. "
            + SuperRootResponse rger. rgeClusterDebugStr ngs(
            realt  Response, null, arch veResponse));
    return  rgedResponse;
  }

  /**
   *  f   get a completed realt   response but a fa led arch ve response, t  m nCompleteB n d  
   * return w ll be  ncorrect -- t  realt   m nCompleteB n d  s assu d to be t  oldest b n
   * returned, rat r than t  b n that  ntersects t  realt   serv ng boundary.  n t se cases,  
   * need to move t  m nCompleteB n d forward.
   * <p>
   * Note that   cannot always set t  m nCompleteB n d for t  realt   results to t  b n
   *  ntersect ng t  realt   serv ng boundary: so w re  n t  guts of t   rg ng log c,   set
   * t  m nCompleteB n d of t   rged response to t  max of t  m nCompleteB n ds of t  or g nal
   * responses. :-(
   */
  pr vate Earlyb rdResponse updateM nCompleteB n d(
      Earlyb rdRequestContext requestContext, Earlyb rdResponse realt  Response) {
    Precond  ons.c ckArgu nt(
        realt  Response.getTermStat st csResults(). sSetM nCompleteB n d());
     nt roundedServ ngRange = roundServ ngRangeUpToNearestB n d(requestContext, realt  Response);
     nt m nCompleteB n d = Math.max(
        roundedServ ngRange,
        realt  Response.getTermStat st csResults().getM nCompleteB n d());
    realt  Response.getTermStat st csResults().setM nCompleteB n d(m nCompleteB n d);
    return realt  Response;
  }

  pr vate stat c Earlyb rdResponse emptyResponse() {
    return new Earlyb rdResponse(Earlyb rdResponseCode.SUCCESS, 0)
        .setSearchResults(new Thr ftSearchResults()
            .setResults(L sts.newArrayL st()))
        .setDebugStr ng("Full arch ve cluster not requested or not ava lable.");
  }

  pr vate stat c boolean  sT erSk ppedResponse(Earlyb rdResponse response) {
    return response.getResponseCode() == Earlyb rdResponseCode.T ER_SK PPED;
  }

  /**
   * G ven a termstats request/response pa r, round t  serv ng range for t  appropr ate cluster up
   * to t  nearest b n d at t  appropr ate resolut on.
   */
  pr vate  nt roundServ ngRangeUpToNearestB n d(
      Earlyb rdRequestContext request, Earlyb rdResponse response) {
    Serv ngRange serv ngRange = realt  Serv ngRangeProv der.getServ ngRange(
        request, request.useOverr deT erConf g());
    long serv ngRangeStartSecs = serv ngRange.getServ ngRangeS nceT  SecondsFromEpoch();
     nt b nS ze = determ neB nS ze(response.getTermStat st csResults().get togramSett ngs());
    return ( nt) Math.ce l((double) serv ngRangeStartSecs / b nS ze);
  }
}
