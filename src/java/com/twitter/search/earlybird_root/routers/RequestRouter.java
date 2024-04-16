package com.tw ter.search.earlyb rd_root.routers;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.futures.Futures;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequestResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.ut l.Future;
 mport com.tw ter.ut l.Try;

/**
 * Respons ble for handl ng requests  n superroot.
 */
publ c abstract class RequestRouter {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(RequestRouter.class);

  /**
   * Saved request and response, to be  ncluded  n debug  nfo.
   */
  class RequestResponse {
    // W re  s t  request sent to. Freeform text l ke "realt  ", "arch ve", etc.
    pr vate Str ng sentTo;
    pr vate Earlyb rdRequestContext requestContext;
    pr vate Future<Earlyb rdResponse> earlyb rdResponseFuture;

    RequestResponse(Str ng sentTo,
                           Earlyb rdRequestContext requestContext,
                           Future<Earlyb rdResponse> earlyb rdResponseFuture) {
      t .sentTo = sentTo;
      t .requestContext = requestContext;
      t .earlyb rdResponseFuture = earlyb rdResponseFuture;
    }

    Str ng getSentTo() {
      return sentTo;
    }

    publ c Earlyb rdRequestContext getRequestContext() {
      return requestContext;
    }

    Future<Earlyb rdResponse> getEarlyb rdResponseFuture() {
      return earlyb rdResponseFuture;
    }
  }

  /**
   * Forward a request to d fferent clusters and  rge t  responses back  nto one response.
   * @param requestContext
   */
  publ c abstract Future<Earlyb rdResponse> route(Earlyb rdRequestContext requestContext);

  /**
   * Save a request (and  s response future) to be  ncluded  n debug  nfo.
   */
  vo d saveRequestResponse(
      L st<RequestResponse> requestResponses,
      Str ng sentTo,
      Earlyb rdRequestContext earlyb rdRequestContext,
      Future<Earlyb rdResponse> earlyb rdResponseFuture
  ) {
    requestResponses.add(
        new RequestResponse(
            sentTo,
            earlyb rdRequestContext,
            earlyb rdResponseFuture
        )
    );
  }

  Future<Earlyb rdResponse> maybeAttachSentRequestsToDebug nfo(
      L st<RequestResponse> requestResponses,
      Earlyb rdRequestContext requestContext,
      Future<Earlyb rdResponse> response
  ) {
     f (requestContext.getRequest().getDebugMode() >= 4) {
      return t .attachSentRequestsToDebug nfo(
          response,
          requestResponses
      );
    } else {
      return response;
    }
  }

  /**
   * Attac s saved cl ent requests and t  r responses to t  debug  nfo w h n t 
   * ma n Earlyb rdResponse.
   */
  Future<Earlyb rdResponse> attachSentRequestsToDebug nfo(
      Future<Earlyb rdResponse> currentResponse,
      L st<RequestResponse> requestResponses) {

    // Get all t  response futures that  're wa  ng on.
    L st<Future<Earlyb rdResponse>> allResponseFutures = new ArrayL st<>();
    for (RequestResponse rr : requestResponses) {
      allResponseFutures.add(rr.getEarlyb rdResponseFuture());
    }

    // Pack all t  futures  nto a s ngle future.
    Future<L st<Try<Earlyb rdResponse>>> allResponsesFuture =
        Futures.collectAll(allResponseFutures);

    return currentResponse.flatMap(ma nResponse -> {
       f (!ma nResponse. sSetDebug nfo()) {
        ma nResponse.setDebug nfo(new Earlyb rdDebug nfo());
      }

      Future<Earlyb rdResponse> responseW hRequests = allResponsesFuture.map(allResponses -> {
        // Get all  nd v dual response "Trys" and see  f   can extract so th ng from t m
        // that   can attach to t  debug nfo.
        for ( nt   = 0;   < allResponses.s ze();  ++) {

          Try<Earlyb rdResponse> responseTry = allResponses.get( );

           f (responseTry. sReturn()) {
            Earlyb rdResponse attac dResponse = responseTry.get();

            // Don't  nclude t  debug str ng,  's already a part of t  ma n response's
            // debug str ng.
            attac dResponse.unsetDebugStr ng();

            Earlyb rdRequestResponse reqResp = new Earlyb rdRequestResponse();
            reqResp.setSentTo(requestResponses.get( ).getSentTo());
            reqResp.setRequest(requestResponses.get( ).getRequestContext().getRequest());
            reqResp.setResponse(attac dResponse.toStr ng());

            ma nResponse.debug nfo.addToSentRequests(reqResp);
          }
        }

        return ma nResponse;
      });

      return responseW hRequests;
    });
  }
}
