package com.tw ter.search.earlyb rd_root.common;

 mport java.ut l.concurrent.T  Un ;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.queryparser.parser.Ser al zedQueryParser;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.ut l.Future;

/**
 * Common ut ls for pars ng ser al zed quer es, and handl ng query parser except ons.
 */
publ c f nal class QueryPars ngUt ls {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(QueryPars ngUt ls.class);

  @V s bleForTest ng
  publ c stat c f nal SearchCounter QUERYPARSE_COUNT =
      SearchCounter.export("root_queryparse_count");
  pr vate stat c f nal SearchT  rStats QUERYPARSE_T MER =
      SearchT  rStats.export("root_queryparse_t  ", T  Un .NANOSECONDS, false, true);
  pr vate stat c f nal SearchCounter NO_PARSED_QUERY_COUNT =
      SearchCounter.export("root_no_parsed_query_count");

  pr vate QueryPars ngUt ls() { }

  /**
   * Takes an earlyb rd request, and parses  s ser al zed query ( f    s set).
   * Expects t  requ red Thr ftSearchQuery to be set on t  passed  n Earlyb rdRequest.
   *
   * @param request t  earlyb rd request to parse.
   * @return null  f t  request does not spec fy a ser al zed query.
   * @throws QueryParserExcept on  f querry pars ng fa ls.
   */
  @Nullable
  stat c Query getParsedQuery(Earlyb rdRequest request) throws QueryParserExcept on {
    // searchQuery  s requ red on Earlyb rdRequest.
    Precond  ons.c ckState(request. sSetSearchQuery());
    Query parsedQuery;
     f (request.getSearchQuery(). sSetSer al zedQuery()) {
      long startT   = System.nanoT  ();
      try {
        Str ng ser al zedQuery = request.getSearchQuery().getSer al zedQuery();

        parsedQuery = new Ser al zedQueryParser().parse(ser al zedQuery);
      } f nally {
        QUERYPARSE_COUNT. ncre nt();
        QUERYPARSE_T MER.t  r ncre nt(System.nanoT  () - startT  );
      }
    } else {
      NO_PARSED_QUERY_COUNT. ncre nt();
      parsedQuery = null;
    }
    return parsedQuery;
  }

  /**
   * Creates a new Earlyb rdResponse w h a CL ENT_ERROR response code, to be used as a response
   * to a request w re   fa led to parse a user passed  n ser al zed query.
   */
  publ c stat c Future<Earlyb rdResponse> newCl entErrorResponse(
      Earlyb rdRequest request,
      QueryParserExcept on e) {

    Str ng msg = "Fa led to parse query";
    LOG.warn(msg, e);

    Earlyb rdResponse errorResponse =
        new Earlyb rdResponse(Earlyb rdResponseCode.CL ENT_ERROR, 0);
    errorResponse.setDebugStr ng(msg + ": " + e.get ssage());
    return Future.value(errorResponse);
  }
}
