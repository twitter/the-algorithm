package com.tw ter.search.earlyb rd_root;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport javax. nject. nject;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;
 mport com.google.common.collect.Sets;

 mport com.tw ter.search.common.part  on ng.base.Part  onDataType;
 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.common.root.ScatterGat rSupport;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root. rgers.Earlyb rdResponse rger;
 mport com.tw ter.search.earlyb rd_root. rgers.Part  onResponseAccumulator;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.ut l.Future;

 mport stat c com.tw ter.search.earlyb rd_root.v s ors.Mult TermD sjunct onPerPart  onV s or.NO_MATCH_CONJUNCT ON;

publ c class Earlyb rdServ ceScatterGat rSupport
     mple nts ScatterGat rSupport<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Earlyb rdResponse EMPTY_RESPONSE = newEmptyResponse();

  pr vate f nal Part  onMapp ngManager part  onMapp ngManager;
  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal Earlyb rdFeatureSc ma rger featureSc ma rger;

  @ nject
  protected Earlyb rdServ ceScatterGat rSupport(Part  onMapp ngManager part  onMapp ngManager,
                                                 Earlyb rdCluster cluster,
                                                 Earlyb rdFeatureSc ma rger featureSc ma rger) {
    t .part  onMapp ngManager = part  onMapp ngManager;
    t .cluster = cluster;
    t .featureSc ma rger = featureSc ma rger;
  }

  /**
   * Fans out t  or g nal request to all part  ons.
   */
  pr vate L st<Earlyb rdRequestContext> fanoutToAllPart  ons(
      Earlyb rdRequestContext requestContext,  nt numPart  ons) {
    //   don't need to create a deep copy of t  or g nal requestContext for every part  on,
    // because requests are not rewr ten once t y get to t  level:   roots have f lters
    // that rewr e t  requests at t  top-level, but   do not rewr e requests per-part  on.
    L st<Earlyb rdRequestContext> requestContexts = new ArrayL st<>(numPart  ons);
    for ( nt   = 0;   < numPart  ons; ++ ) {
      requestContexts.add(requestContext);
    }
    return requestContexts;
  }

  pr vate Map< nteger, L st<Long>> populate dsForPart  on(Earlyb rdRequestContext requestContext) {
    Map< nteger, L st<Long>> perPart  on ds = Maps.newHashMap();
    // Based on part  on type, populate map for every part  on  f needed.
     f (part  onMapp ngManager.getPart  onDataType() == Part  onDataType.USER_ D
        && requestContext.getRequest().getSearchQuery().getFromUser DF lter64S ze() > 0) {
      for (long user d : requestContext.getRequest().getSearchQuery().getFromUser DF lter64()) {
         nt userPart  on = part  onMapp ngManager.getPart  on dForUser d(user d);
         f (!perPart  on ds.conta nsKey(userPart  on)) {
          perPart  on ds.put(userPart  on, L sts.newArrayL st());
        }
        perPart  on ds.get(userPart  on).add(user d);
      }
    } else  f (part  onMapp ngManager.getPart  onDataType() == Part  onDataType.TWEET_ D
        && requestContext.getRequest().getSearchQuery().getSearchStatus dsS ze() > 0) {
      for (long  d : requestContext.getRequest().getSearchQuery().getSearchStatus ds()) {
         nt t etPart  on = part  onMapp ngManager.getPart  on dForT et d( d);
         f (!perPart  on ds.conta nsKey(t etPart  on)) {
          perPart  on ds.put(t etPart  on, L sts.newArrayL st());
        }
        perPart  on ds.get(t etPart  on).add( d);
      }
    }
    return perPart  on ds;
  }

  pr vate vo d setPerPart  on ds(Earlyb rdRequest request, L st<Long>  ds) {
     f (part  onMapp ngManager.getPart  onDataType() == Part  onDataType.USER_ D) {
      request.getSearchQuery().setFromUser DF lter64( ds);
    } else {
      request.getSearchQuery().setSearchStatus ds(Sets.newHashSet( ds));
    }
  }

  @Overr de
  publ c Earlyb rdResponse emptyResponse() {
    return EMPTY_RESPONSE;
  }

  publ c stat c f nal Earlyb rdResponse newEmptyResponse() {
    return new Earlyb rdResponse(Earlyb rdResponseCode.PART T ON_SK PPED, 0)
        .setSearchResults(new Thr ftSearchResults());
  }

  @Overr de
  publ c L st<Earlyb rdRequestContext> rewr eRequest(
      Earlyb rdRequestContext requestContext,  nt rootNumPart  ons) {
     nt numPart  ons = part  onMapp ngManager.getNumPart  ons();
    Precond  ons.c ckState(rootNumPart  ons == numPart  ons,
        "Root's conf gured numPart  ons  s d fferent from that conf gured  n database.yml.");
    // Rewr e query based on "mult _term_d sjunct on  d/from_user_ d" and part  on  d  f needed.
    Map< nteger, Query> perPart  onQueryMap =
        requestContext.getRequest().getSearchQuery().getSearchStatus dsS ze() == 0
            ? Earlyb rdRootQueryUt ls.rewr eMult TermD sjunct onPerPart  onF lter(
            requestContext.getParsedQuery(),
            part  onMapp ngManager,
            numPart  ons)
            : Maps.newHashMap();

    // Key: part  on  d; Value: val d  ds l st for t  part  on
    Map< nteger, L st<Long>> perPart  on ds = populate dsForPart  on(requestContext);

     f (perPart  onQueryMap. sEmpty() && perPart  on ds. sEmpty()) {
      return fanoutToAllPart  ons(requestContext, numPart  ons);
    }

    L st<Earlyb rdRequestContext> requestContexts = new ArrayL st<>(numPart  ons);
    for ( nt   = 0;   < numPart  ons; ++ ) {
      requestContexts.add(null);
    }

    // Rewr e per part  on quer es  f ex st.
    for ( nt   = 0;   < numPart  ons; ++ ) {
       f (perPart  on ds.conta nsKey( )) {
         f (!perPart  onQueryMap.conta nsKey( )) {
          // Query does not need to be rewr ten for t  part  on
          // But   st ll need to create a copy, because  're gonna
          // set fromUser DF lter64/searchStatus ds
          requestContexts.set( , requestContext.deepCopy());
          setPerPart  on ds(requestContexts.get( ).getRequest(), perPart  on ds.get( ));
        } else  f (perPart  onQueryMap.get( ) != NO_MATCH_CONJUNCT ON) {
          requestContexts.set( , Earlyb rdRequestContext.copyRequestContext(
              requestContext, perPart  onQueryMap.get( )));
          setPerPart  on ds(requestContexts.get( ).getRequest(), perPart  on ds.get( ));
        }
      } else  f (perPart  on ds. sEmpty()) {
        // T  fromUser DF lter64/searchStatus ds f eld  s not set on t  or g nal request,
        // perPart  onQueryMap should dec de  f   send a request to t  part  on or not
         f (!perPart  onQueryMap.conta nsKey( )) {
          // Query does not need to be rewr ten for t  part  on
          // Don't need to create a copy, because request context won't be changed afterwards
          requestContexts.set( , requestContext);
        } else  f (perPart  onQueryMap.get( ) != NO_MATCH_CONJUNCT ON) {
          requestContexts.set( , Earlyb rdRequestContext.copyRequestContext(
              requestContext, perPart  onQueryMap.get( )));
        }
      }
    }
    return requestContexts;
  }

  /**
   *  rges all t  sub-results  ndexed by t  part  on  d. Sub-results w h value null
   *  nd cate an error w h that part  on such as t  out etc.
   */
  @Overr de
  publ c Future<Earlyb rdResponse>  rge(Earlyb rdRequestContext requestContext,
                                         L st<Future<Earlyb rdResponse>> responses) {
    Earlyb rdResponse rger  rger = Earlyb rdResponse rger.getResponse rger(
        requestContext,
        responses,
        new Part  onResponseAccumulator(),
        cluster,
        featureSc ma rger,
        part  onMapp ngManager.getNumPart  ons());
    return  rger. rge();
  }

  @Overr de
  publ c boolean  sSuccess(Earlyb rdResponse earlyb rdResponse) {
    return Earlyb rdResponseUt l. sSuccessfulResponse(earlyb rdResponse);
  }

  @Overr de
  publ c boolean  sT  out(Earlyb rdResponse earlyb rdResponse) {
    return earlyb rdResponse.getResponseCode() == Earlyb rdResponseCode.SERVER_T MEOUT_ERROR;
  }

  @Overr de
  publ c boolean  sCl entCancel(Earlyb rdResponse earlyb rdResponse) {
    return earlyb rdResponse.getResponseCode() == Earlyb rdResponseCode.CL ENT_CANCEL_ERROR;
  }

  @Overr de
  publ c Earlyb rdResponse errorResponse(Str ng debugStr ng) {
    return new Earlyb rdResponse()
        .setResponseCode(Earlyb rdResponseCode.TRANS ENT_ERROR)
        .setDebugStr ng(debugStr ng);
  }
}
