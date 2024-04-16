package com.tw ter.search.earlyb rd_root.f lters;

 mport javax. nject. nject;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.v s ors.DropAllProtectedOperatorV s or;
 mport com.tw ter.ut l.Future;

publ c class DropAllProtectedOperatorF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(DropAllProtectedOperatorF lter.class);
  pr vate stat c f nal SearchCounter QUERY_PARSER_FA LURE_COUNTER =
      SearchCounter.export("protected_operator_f lter_query_parser_fa lure_count");
  @V s bleForTest ng
  stat c f nal SearchCounter TOTAL_REQUESTS_COUNTER =
      SearchCounter.export("drop_all_protected_operator_f lter_total");
  @V s bleForTest ng
  stat c f nal SearchCounter OPERATOR_DROPPED_REQUESTS_COUNTER =
      SearchCounter.export("drop_all_protected_operator_f lter_operator_dropped");

  pr vate f nal DropAllProtectedOperatorV s or dropProtectedOperatorV s or;

  @ nject
  publ c DropAllProtectedOperatorF lter(
      DropAllProtectedOperatorV s or dropProtectedOperatorV s or
  ) {
    t .dropProtectedOperatorV s or = dropProtectedOperatorV s or;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    TOTAL_REQUESTS_COUNTER. ncre nt();
    Query query = requestContext.getParsedQuery();
     f (query == null) {
      return serv ce.apply(requestContext);
    }

    Query processedQuery = query;
    try {
      processedQuery = query.accept(dropProtectedOperatorV s or);
    } catch (QueryParserExcept on e) {
      // t  should not happen s nce   already have a parsed query
      QUERY_PARSER_FA LURE_COUNTER. ncre nt();
      LOG.warn(
          "Fa led to drop protected operator for ser al zed query: " + query.ser al ze(), e);
    }

     f (processedQuery == query) {
      return serv ce.apply(requestContext);
    } else {
      OPERATOR_DROPPED_REQUESTS_COUNTER. ncre nt();
      Earlyb rdRequestContext clonedRequestContext =
          Earlyb rdRequestContext.copyRequestContext(requestContext, processedQuery);
      return serv ce.apply(clonedRequestContext);
    }
  }
}
