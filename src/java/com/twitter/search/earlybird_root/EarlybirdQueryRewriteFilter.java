package com.tw ter.search.earlyb rd_root;

 mport java.ut l.L st;
 mport java.ut l.Map;

 mport javax. nject. nject;
 mport javax. nject.Na d;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.Term;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.rewr er.Pred cateQueryNodeDropper;
 mport com.tw ter.search.queryparser.v s ors.TermExtractorV s or;
 mport com.tw ter.ut l.Future;

/**
 * F lter that rewr es t  ser al zed query on Earlyb rdRequest.
 * As of now, t  f lter performs t  follow ng rewr es:
 *   - Drop ":v annotated var ants based on dec der,  f t  query has enough term nodes.
 */
publ c class Earlyb rdQueryRewr eF lter extends
    S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdQueryRewr eF lter.class);

  pr vate stat c f nal Str ng DROP_PHRASE_VAR ANT_FROM_QUERY_DEC DER_KEY_PATTERN =
      "drop_var ants_from_%s_%s_quer es";

  // only drop var ants from quer es w h more than t  number of terms.
  pr vate stat c f nal Str ng M N_TERM_COUNT_FOR_VAR ANT_DROPP NG_DEC DER_KEY_PATTERN =
      "drop_var ants_from_%s_%s_quer es_term_count_threshold";

  pr vate stat c f nal SearchCounter QUERY_PARSER_FA LURE_COUNT =
      SearchCounter.export("query_rewr e_f lter_query_parser_fa lure_count");

  //   currently add var ants only to RECENCY and RELEVANCE requests, but   doesn't hurt to export
  // stats for all request types.
  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, SearchCounter> DROP_VAR ANTS_QUERY_COUNTS =
    Maps.newEnumMap(Earlyb rdRequestType.class);
  stat c {
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      DROP_VAR ANTS_QUERY_COUNTS.put(
          requestType,
          SearchCounter.export(Str ng.format("drop_%s_var ants_query_count",
                                             requestType.getNormal zedNa ())));
    }
  }

  pr vate stat c f nal Pred cate<Query> DROP_VAR ANTS_PRED CATE =
      q -> q.hasAnnotat onType(Annotat on.Type.VAR ANT);

  pr vate stat c f nal Pred cateQueryNodeDropper DROP_VAR ANTS_V S TOR =
    new Pred cateQueryNodeDropper(DROP_VAR ANTS_PRED CATE);

  pr vate f nal SearchDec der dec der;
  pr vate f nal Str ng normal zedSearchRootNa ;

  @ nject
  publ c Earlyb rdQueryRewr eF lter(
      SearchDec der dec der,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ) {
    t .dec der = dec der;
    t .normal zedSearchRootNa  = normal zedSearchRootNa ;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {

    Query query = requestContext.getParsedQuery();
    //  f t re's no ser al zed query, no rewr e  s necessary.
     f (query == null) {
      return serv ce.apply(requestContext);
    } else {
      try {
        Query var antsRemoved = maybeRemoveVar ants(requestContext, query);

         f (query == var antsRemoved) {
          return serv ce.apply(requestContext);
        } else {
          Earlyb rdRequestContext clonedRequestContext =
            Earlyb rdRequestContext.copyRequestContext(requestContext, var antsRemoved);

          return serv ce.apply(clonedRequestContext);
        }
      } catch (QueryParserExcept on e) {
        //    s not clear  re that t  QueryParserExcept on  s t  cl ent's fault, or   fault.
        // At t  po nt    s most l kely not t  cl ent's s nce   have a leg  mate parsed Query
        // from t  cl ent's request, and  's t  rewr  ng that fa led.
        //  n t  case   choose to send t  query as  s (w hout t  rewr e),  nstead of
        // fa l ng t  ent re request.
        QUERY_PARSER_FA LURE_COUNT. ncre nt();
        LOG.warn("Fa led to rewr e ser al zed query: " + query.ser al ze(), e);
        return serv ce.apply(requestContext);
      }
    }
  }

  pr vate Query maybeRemoveVar ants(Earlyb rdRequestContext requestContext, Query query)
      throws QueryParserExcept on {

     f (shouldDropVar ants(requestContext, query)) {
      Query rewr tenQuery = DROP_VAR ANTS_V S TOR.apply(query);
       f (!query.equals(rewr tenQuery)) {
        DROP_VAR ANTS_QUERY_COUNTS.get(requestContext.getEarlyb rdRequestType()). ncre nt();
        return rewr tenQuery;
      }
    }
    return query;
  }

  pr vate boolean shouldDropVar ants(Earlyb rdRequestContext requestContext, Query query)
      throws QueryParserExcept on {
    TermExtractorV s or termExtractorV s or = new TermExtractorV s or(false);
    L st<Term> terms = query.accept(termExtractorV s or);

    Earlyb rdRequestType requestType = requestContext.getEarlyb rdRequestType();

    boolean shouldDropVar ants = dec der. sAva lable(getDropPhaseVar antDec derKey(requestType));

    return terms != null
        && terms.s ze() >= dec der.getAva lab l y(
            getM nTermCountForVar antDropp ngDec derKey(requestType))
        && shouldDropVar ants;
  }

  pr vate Str ng getDropPhaseVar antDec derKey(Earlyb rdRequestType requestType) {
    return Str ng.format(DROP_PHRASE_VAR ANT_FROM_QUERY_DEC DER_KEY_PATTERN,
                         normal zedSearchRootNa ,
                         requestType.getNormal zedNa ());
  }

  pr vate Str ng getM nTermCountForVar antDropp ngDec derKey(Earlyb rdRequestType requestType) {
    return Str ng.format(M N_TERM_COUNT_FOR_VAR ANT_DROPP NG_DEC DER_KEY_PATTERN,
                         normal zedSearchRootNa ,
                         requestType.getNormal zedNa ());
  }
}
