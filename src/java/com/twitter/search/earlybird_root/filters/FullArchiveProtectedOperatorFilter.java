package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.L st;

 mport javax. nject. nject;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdDebug nfo;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryNodeUt ls;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.v s ors.DropAllProtectedOperatorV s or;
 mport com.tw ter.search.queryparser.v s ors.QueryTree ndex;
 mport com.tw ter.ut l.Future;

/**
 * Full arch ve serv ce f lter val dates requests w h a protected operator, appends t 
 * '[exclude protected]' operator by default, and appends '[f lter protected]' operator  nstead  f
 * 'getProtectedT etsOnly' request param  s set. A cl ent error response  s returned  f any of t 
 * follow ng rules  s v olated.
 *   1. T re  s at most one 'protected' operator  n t  query.
 *   2.  f t re  s a 'protected' operator,   must be  n t  query root node.
 *   3. T  parent node of t  'protected' operator must not be negated and must be a conjunct on.
 *   4.  f t re  s a pos  ve 'protected' operator, 'follo dUser ds' and 'searc r d' request
 *   params must be set.
 */
publ c class FullArch veProtectedOperatorF lter extends
    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(FullArch veProtectedOperatorF lter.class);
  pr vate stat c f nal SearchOperator EXCLUDE_PROTECTED_OPERATOR =
      new SearchOperator(SearchOperator.Type.EXCLUDE, SearchOperatorConstants.PROTECTED);
  pr vate stat c f nal SearchOperator F LTER_PROTECTED_OPERATOR =
      new SearchOperator(SearchOperator.Type.F LTER, SearchOperatorConstants.PROTECTED);
  pr vate stat c f nal SearchCounter QUERY_PARSER_FA LURE_COUNT =
      SearchCounter.export("protected_operator_f lter_query_parser_fa lure_count");

  pr vate f nal DropAllProtectedOperatorV s or dropProtectedOperatorV s or;
  pr vate f nal SearchDec der dec der;

  @ nject
  publ c FullArch veProtectedOperatorF lter(
      DropAllProtectedOperatorV s or dropProtectedOperatorV s or,
      SearchDec der dec der) {
    t .dropProtectedOperatorV s or = dropProtectedOperatorV s or;
    t .dec der = dec der;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Query query = requestContext.getParsedQuery();
     f (query == null) {
      return serv ce.apply(requestContext);
    }

    QueryTree ndex queryTree ndex = QueryTree ndex.bu ldFor(query);
    L st<Query> nodeL st = queryTree ndex.getNodeL st();
    // try to f nd a protected operator, returns error response  f more than one protected
    // operator  s detected
    SearchOperator protectedOperator = null;
    for (Query node : nodeL st) {
       f (node  nstanceof SearchOperator) {
        SearchOperator searchOp = (SearchOperator) node;
         f (SearchOperatorConstants.PROTECTED.equals(searchOp.getOperand())) {
           f (protectedOperator == null) {
            protectedOperator = searchOp;
          } else {
            return createErrorResponse("Only one 'protected' operator  s expected.");
          }
        }
      }
    }

    Query processedQuery;
     f (protectedOperator == null) {
      // no protected operator  s detected, append '[exclude protected]' by default
      processedQuery = QueryNodeUt ls.appendAsConjunct on(query, EXCLUDE_PROTECTED_OPERATOR);
    } else {
      // protected operator must be  n t  query root node
       f (queryTree ndex.getParentOf(protectedOperator) != query) {
        return createErrorResponse("'protected' operator must be  n t  query root node");
      }
      // t  query node that conta ns protected operator must not be negated
       f (query.mustNotOccur()) {
        return createErrorResponse("T  query node that conta ns a 'protected' operator must not"
            + " be negated.");
      }
      // t  query node that conta ns protected operator must be a conjunct on
       f (!query. sTypeOf(Query.QueryType.CONJUNCT ON)) {
        return createErrorResponse("T  query node that conta ns a 'protected' operator must"
            + " be a conjunct on.");
      }
      // c ck t  ex stence of 'follo dUser ds' and 'searc r d'  f    s a pos  ve operator
       f ( sPos  ve(protectedOperator)) {
         f (!val dateRequestParam(requestContext.getRequest())) {
          return createErrorResponse("'follo dUser ds' and 'searc r d' are requ red "
              + "by pos  ve 'protected' operator.");
        }
      }
      processedQuery = query;
    }
    // update processedQuery  f 'getProtectedT etsOnly'  s set to true,   takes precedence over
    // t  ex st ng protected operators
     f (requestContext.getRequest(). sGetProtectedT etsOnly()) {
       f (!val dateRequestParam(requestContext.getRequest())) {
        return createErrorResponse("'follo dUser ds' and 'searc r d' are requ red "
            + "w n 'getProtectedT etsOnly'  s set to true.");
      }
      try {
        processedQuery = processedQuery.accept(dropProtectedOperatorV s or);
      } catch (QueryParserExcept on e) {
        // t  should not happen s nce   already have a parsed query
        QUERY_PARSER_FA LURE_COUNT. ncre nt();
        LOG.warn(
            "Fa led to drop protected operator for ser al zed query: " + query.ser al ze(), e);
      }
      processedQuery =
          QueryNodeUt ls.appendAsConjunct on(processedQuery, F LTER_PROTECTED_OPERATOR);
    }

     f (processedQuery == query) {
      return serv ce.apply(requestContext);
    } else {
      Earlyb rdRequestContext clonedRequestContext =
          Earlyb rdRequestContext.copyRequestContext(requestContext, processedQuery);
      return serv ce.apply(clonedRequestContext);
    }
  }

  pr vate boolean val dateRequestParam(Earlyb rdRequest request) {
    L st<Long> follo dUser ds = request.follo dUser ds;
    Long searc r d = (request.searchQuery != null && request.searchQuery. sSetSearc r d())
        ? request.searchQuery.getSearc r d() : null;
    return follo dUser ds != null && !follo dUser ds. sEmpty() && searc r d != null;
  }

  pr vate boolean  sPos  ve(SearchOperator searchOp) {
    boolean  sNegateExclude = searchOp.mustNotOccur()
        && searchOp.getOperatorType() == SearchOperator.Type.EXCLUDE;
    boolean  sPos  ve = !searchOp.mustNotOccur()
        && (searchOp.getOperatorType() == SearchOperator.Type. NCLUDE
        || searchOp.getOperatorType() == SearchOperator.Type.F LTER);
    return  sNegateExclude ||  sPos  ve;
  }

  pr vate Future<Earlyb rdResponse> createErrorResponse(Str ng errorMsg) {
    Earlyb rdResponse response = new Earlyb rdResponse(Earlyb rdResponseCode.CL ENT_ERROR, 0);
    response.setDebug nfo(new Earlyb rdDebug nfo().setHost("full_arch ve_root"));
    response.setDebugStr ng(errorMsg);
    return Future.value(response);
  }

}
