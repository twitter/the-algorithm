package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.concurrent.T  Un ;
 mport javax. nject. nject;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers onConf g;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.f nagle.trac ng.Trace;
 mport com.tw ter.f nagle.trac ng.Trac ng;
 mport com.tw ter.search.common. tr cs.SearchRateCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.QueryPars ngUt ls;
 mport com.tw ter.search.queryparser.parser.Ser al zedQueryParser;
 mport com.tw ter.search.queryparser.parser.Ser al zedQueryParser.Token zat onOpt on;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.ut l.Durat on;
 mport com.tw ter.ut l.Future;

publ c class QueryToken zerF lter extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Str ng PREF X = "query_token zer_";
  pr vate stat c f nal SearchRateCounter SUCCESS_COUNTER =
      SearchRateCounter.export(PREF X + "success");
  pr vate stat c f nal SearchRateCounter FA LURE_COUNTER =
      SearchRateCounter.export(PREF X + "error");
  pr vate stat c f nal SearchRateCounter SK PPED_COUNTER =
      SearchRateCounter.export(PREF X + "sk pped");
  pr vate stat c f nal SearchT  rStats QUERY_TOKEN ZER_T ME =
      SearchT  rStats.export(PREF X + "t  ", T  Un .M LL SECONDS, false);

  pr vate f nal Token zat onOpt on token zat onOpt on;

  @ nject
  publ c QueryToken zerF lter(Pengu nVers onConf g pengu nvers ons) {
    Pengu nVers on[] supportedVers ons = pengu nvers ons
        .getSupportedVers ons().toArray(new Pengu nVers on[0]);
    token zat onOpt on = new Token zat onOpt on(true, supportedVers ons);
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

     f (!requestContext.getRequest(). sRetoken zeSer al zedQuery()
        || !requestContext.getRequest(). sSetSearchQuery()
        || !requestContext.getRequest().getSearchQuery(). sSetSer al zedQuery()) {
      SK PPED_COUNTER. ncre nt();
      return serv ce.apply(requestContext);
    }

    SearchT  r t  r = QUERY_TOKEN ZER_T ME.startNewT  r();
    try {
      Str ng ser al zedQuery = requestContext.getRequest().getSearchQuery().getSer al zedQuery();
      Query parsedQuery = reparseQuery(ser al zedQuery);
      SUCCESS_COUNTER. ncre nt();
      return serv ce.apply(Earlyb rdRequestContext.copyRequestContext(requestContext, parsedQuery));
    } catch (QueryParserExcept on e) {
      FA LURE_COUNTER. ncre nt();
      return QueryPars ngUt ls.newCl entErrorResponse(requestContext.getRequest(), e);
    } f nally {
      long elapsed = t  r.stop();
      QUERY_TOKEN ZER_T ME.t  r ncre nt(elapsed);
      Trac ng trace = Trace.apply();
       f (trace. sAct velyTrac ng()) {
        trace.record(PREF X + "t  ", Durat on.fromM ll seconds(elapsed));
      }
    }
  }

  publ c Query reparseQuery(Str ng ser al zedQuery) throws QueryParserExcept on {
    Ser al zedQueryParser parser = new Ser al zedQueryParser(token zat onOpt on);
    return parser.parse(ser al zedQuery);
  }

  /**
   *  n  al z ng t  query parser can take many seconds.    n  al ze   at warmup so that
   * requests don't t   out after   jo n t  serverset. SEARCH-28801
   */
  publ c vo d performExpens ve n  al zat on() throws QueryParserExcept on {
    Ser al zedQueryParser queryParser = new Ser al zedQueryParser(token zat onOpt on);

    // T  Korean query parser takes a few seconds on  's own to  n  al ze.
    Str ng koreanQuery = "스포츠";
    queryParser.parse(koreanQuery);
  }
}
