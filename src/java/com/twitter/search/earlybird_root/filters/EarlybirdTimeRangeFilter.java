package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Collect ons;
 mport java.ut l.Map;
 mport java.ut l.Opt onal;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.ut l.earlyb rd.Earlyb rdResponseUt l;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResults;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.ut l. dT  Ranges;
 mport com.tw ter.ut l.Future;

/**
 * A F nagle f lter used to f lter requests to t ers.
 * Parses ser al zed query on Earlyb rd request, and extracts s nce / unt l / s nce_ d / max_ d
 * operators. T  f lter t n tests w t r t  request overlaps w h t  g ven t er.  f t re
 *  s no overlap, an empty response  s returned w hout actually forward ng t  requests to t 
 * underly ng serv ce.
 */
publ c class Earlyb rdT  RangeF lter extends
    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdT  RangeF lter.class);

  pr vate stat c f nal Earlyb rdResponse ERROR_RESPONSE =
      new Earlyb rdResponse(Earlyb rdResponseCode.PERS STENT_ERROR, 0)
          .setSearchResults(new Thr ftSearchResults());

  pr vate f nal Serv ngRangeProv der serv ngRangeProv der;
  pr vate f nal Opt onal<Earlyb rdT  F lterQueryRewr er> queryRewr er;

  pr vate stat c f nal Map<Earlyb rdRequestType, SearchCounter> FA LED_REQUESTS;
  stat c {
    f nal Map<Earlyb rdRequestType, SearchCounter> tempMap =
      Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      tempMap.put(requestType, SearchCounter.export(
          "t  _range_f lter_" + requestType.getNormal zedNa () + "_fa led_requests"));
    }
    FA LED_REQUESTS = Collect ons.unmod f ableMap(tempMap);
  }

  publ c stat c Earlyb rdT  RangeF lter newT  RangeF lterW hQueryRewr er(
      Serv ngRangeProv der serv ngRangeProv der,
      SearchDec der dec der) {

    return new Earlyb rdT  RangeF lter(serv ngRangeProv der,
        Opt onal.of(new Earlyb rdT  F lterQueryRewr er(serv ngRangeProv der, dec der)));
  }

  publ c stat c Earlyb rdT  RangeF lter newT  RangeF lterW houtQueryRewr er(
      Serv ngRangeProv der serv ngRangeProv der) {

    return new Earlyb rdT  RangeF lter(serv ngRangeProv der, Opt onal.empty());
  }

  /**
   * Construct a f lter that avo ds forward ng requests to unrelated t ers
   * based on requests' s nce / unt l / s nce_ d / max_ d.
   * @param prov der Holds t  boundary  nformat on.
   */
  Earlyb rdT  RangeF lter(
      Serv ngRangeProv der prov der,
      Opt onal<Earlyb rdT  F lterQueryRewr er> rewr er) {

    t .serv ngRangeProv der = prov der;
    t .queryRewr er = rewr er;
  }

  publ c Serv ngRangeProv der getServ ngRangeProv der() {
    return serv ngRangeProv der;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Query parsedQuery = requestContext.getParsedQuery();
     f (parsedQuery != null) {
      // Only perform f lter ng  f ser al zed query  s set.
      try {
         dT  Ranges queryRanges =  dT  Ranges.fromQuery(parsedQuery);
         f (queryRanges == null) {
          // No t   ranges  n query.
          return  ssueServ ceRequest(serv ce, requestContext);
        }

        Serv ngRange serv ngRange =
            serv ngRangeProv der.getServ ngRange(
                requestContext, requestContext.useOverr deT erConf g());

         f (queryDoesNotOverlapW hServ ngRange(queryRanges, serv ngRange)) {
          return Future.value(t erSk ppedResponse(requestContext.getEarlyb rdRequestType(),
                                                  serv ngRange));
        } else {
          return  ssueServ ceRequest(serv ce, requestContext);
        }
      } catch (QueryParserExcept on e) {
        LOG.warn("Unable to get  dT  Ranges from query: " + parsedQuery.ser al ze());
        // T  fa lure  re  s not due to a m ss-for d query from t  cl ent, s nce   already
        //  re able to successfully get a parsed Query from t  request.
        //  f   can't determ ne t  t   ranges, pass t  query along to t  t er, and just
        // restr ct   to t  t  ranges of t  t er.
        return  ssueServ ceRequest(serv ce, requestContext);
      }
    } else {
      // T re's no ser al zed query. Just pass through l ke an  dent y f lter.
      return  ssueServ ceRequest(serv ce, requestContext);
    }
  }

  pr vate boolean queryDoesNotOverlapW hServ ngRange( dT  Ranges queryRanges,
        Serv ngRange serv ngRange) {
    // As long as a query overlaps w h t  t er serv ng range on e  r s de,
    // t  request  s not f ltered.  .e.   want to be conservat ve w n do ng t  f lter ng,
    // because    s just an opt m zat on.    gnore t   nclus veness / exclus veness of t 
    // boundar es.  f t  t er boundary and t  query boundry happen to be t  sa ,   do not
    // f lter t  request.
    return queryRanges.getS nce DExclus ve().or(0L)
          > serv ngRange.getServ ngRangeMax d()
      || queryRanges.getMax D nclus ve().or(Long.MAX_VALUE)
          < serv ngRange.getServ ngRangeS nce d()
      || queryRanges.getS nceT   nclus ve().or(0)
          > serv ngRange.getServ ngRangeUnt lT  SecondsFromEpoch()
      || queryRanges.getUnt lT  Exclus ve().or( nteger.MAX_VALUE)
          < serv ngRange.getServ ngRangeS nceT  SecondsFromEpoch();
  }

  pr vate Future<Earlyb rdResponse>  ssueServ ceRequest(
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce,
      Earlyb rdRequestContext requestContext) {

    try {
      Earlyb rdRequestContext request = requestContext;
       f (queryRewr er. sPresent()) {
        request = queryRewr er.get().rewr eRequest(requestContext);
      }
      return serv ce.apply(request);
    } catch (QueryParserExcept on e) {
      FA LED_REQUESTS.get(requestContext.getEarlyb rdRequestType()). ncre nt();
      Str ng msg = "Fa led to add t   f lter operators";
      LOG.error(msg, e);

      // Note that  n t  case    s not clear w t r t  error  s t  cl ent's fault or  
      // fault, so   don't necessar ly return a CL ENT_ERROR  re.
      // Currently t  actually returns a PERS STENT_ERROR.
       f (requestContext.getRequest().getDebugMode() > 0) {
        return Future.value(
            ERROR_RESPONSE.deepCopy().setDebugStr ng(msg + ": " + e.get ssage()));
      } else {
        return Future.value(ERROR_RESPONSE);
      }
    }
  }

  /**
   * Creates a t er sk pped response, based on t  g ven request type.
   *
   * For recency, relevance, facets and top t ets requests, t   thod returns a SUCCESS response
   * w h no search results and t  m nSearc dStatus D and maxSearc dStatus D appropr ately set.
   * For term stats response,   returns a T ER_SK PPED response, but   need to rev s  t .
   *
   * @param requestType T  type of t  request.
   * @param serv ngRange T  serv ng range of t  t er that  're sk pp ng.
   */
  @V s bleForTest ng
  publ c stat c Earlyb rdResponse t erSk ppedResponse(
      Earlyb rdRequestType requestType,
      Serv ngRange serv ngRange) {
    Str ng debug ssage =
      "T er sk pped because   does not  ntersect w h query t   boundar es.";
     f (requestType == Earlyb rdRequestType.TERM_STATS) {
      //  f  's a term stats request, return a T ER_SK PPED response for now.
      // But   need to f gure out t  r ght th ng to do  re.
      return new Earlyb rdResponse(Earlyb rdResponseCode.T ER_SK PPED, 0)
        .setDebugStr ng(debug ssage);
    } else {
      // m n ds  n Serv ngRange  nstances are set to t erLo rBoundary - 1, because t 
      // s nce_ d operator  s exclus ve. T  max_ d operator on t  ot r hand  s  nclus ve,
      // so max ds  n Serv ngRange  nstances are also set to t erUpperBoundary - 1.
      //  re   want both of t m to be  nclus ve, so   need to  ncre nt t  m n d by 1.
      return Earlyb rdResponseUt l.t erSk ppedRootResponse(
          serv ngRange.getServ ngRangeS nce d() + 1,
          serv ngRange.getServ ngRangeMax d(),
          debug ssage);
    }
  }
}
