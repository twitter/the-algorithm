package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Collect ons;
 mport java.ut l.L st;
 mport java.ut l.Map;

 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestType;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;

/**
 * Adds query f lters that f lter out t ets outs de a t er's serv ng range. Two t ers m ght load
 * t  sa  t  sl ce, so  f t  f lter ng  s not done, t  two t ers m ght return dupl cates. T 
 *  rgers should know how to handle t  dupl cates, but t  m ght decrease t  number or t 
 * qual y of t  returned results.
 */
publ c class Earlyb rdT  F lterQueryRewr er {
  pr vate stat c f nal Logger LOG =
      LoggerFactory.getLogger(Earlyb rdT  F lterQueryRewr er.class);

  pr vate stat c f nal Map<Earlyb rdRequestType, SearchCounter> NO_QUERY_COUNTS;
  stat c {
    f nal Map<Earlyb rdRequestType, SearchCounter> tempMap =
      Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      tempMap.put(requestType, SearchCounter.export(
          "t  _f lter_query_rewr er_" + requestType.getNormal zedNa () + "_no_query_count"));
    }
    NO_QUERY_COUNTS = Collect ons.unmod f ableMap(tempMap);
  }

  @V s bleForTest ng
  stat c f nal Map<Earlyb rdRequestType, Str ng> ADD_S NCE_ D_MAX_ D_DEC DER_KEY_MAP;
  stat c {
    f nal Str ng ADD_S NCE_ D_MAX_ D_DEC DER_KEY_TEMPLATE =
      "add_s nce_ d_max_ d_operators_to_%s_query";
    f nal Map<Earlyb rdRequestType, Str ng> tempMap = Maps.newEnumMap(Earlyb rdRequestType.class);
    for (Earlyb rdRequestType requestType : Earlyb rdRequestType.values()) {
      tempMap.put(
          requestType,
          Str ng.format(ADD_S NCE_ D_MAX_ D_DEC DER_KEY_TEMPLATE, requestType.getNormal zedNa ()));
    }
    ADD_S NCE_ D_MAX_ D_DEC DER_KEY_MAP = Collect ons.unmod f ableMap(tempMap);
  }

  @V s bleForTest ng
  stat c f nal Str ng ADD_S NCE_ D_MAX_ D_TO_NULL_SER AL ZED_QUER ES_DEC DER_KEY =
      "add_s nce_ d_max_ d_operators_to_null_ser al zed_quer es";

  pr vate f nal SearchDec der dec der;
  pr vate f nal Serv ngRangeProv der serv ngRangeProv der;

  Earlyb rdT  F lterQueryRewr er(
      Serv ngRangeProv der serv ngRangeProv der,
      SearchDec der dec der) {

    t .serv ngRangeProv der = serv ngRangeProv der;
    t .dec der = dec der;
  }

  /**
   * Add max d and s nce d f elds to t  ser al zed query.
   *
   * T  must be done after calculat ng t   dT  Ranges to prevent  nterfer ng w h calculat ng
   *  dT  Ranges
   */
  publ c Earlyb rdRequestContext rewr eRequest(Earlyb rdRequestContext requestContext)
      throws QueryParserExcept on {
    Query q = requestContext.getParsedQuery();
     f (q == null) {
       f (requestContext.getEarlyb rdRequestType() != Earlyb rdRequestType.TERM_STATS) {
        LOG.warn("Rece ved request w hout a parsed query: " + requestContext.getRequest());
        NO_QUERY_COUNTS.get(requestContext.getEarlyb rdRequestType()). ncre nt();
      }

       f (!dec der. sAva lable(ADD_S NCE_ D_MAX_ D_TO_NULL_SER AL ZED_QUER ES_DEC DER_KEY)) {
        return requestContext;
      }
    }

    return addOperators(requestContext, q);
  }

  pr vate Earlyb rdRequestContext addOperators(
      Earlyb rdRequestContext requestContext,
      @Nullable Query query) throws QueryParserExcept on {

    // Add t  S NCE_ D and MAX_ D operators only  f t  dec der  s enabled.
     f (!dec der. sAva lable(
        ADD_S NCE_ D_MAX_ D_DEC DER_KEY_MAP.get(requestContext.getEarlyb rdRequestType()))) {
      return requestContext;
    }

    // Note: can't recompute t  search operators because t  serv ng range changes  n real t  
    // for t  most recent t er.
    Serv ngRange serv ngRange = serv ngRangeProv der.getServ ngRange(
        requestContext, requestContext.useOverr deT erConf g());

    long t erS nce d = serv ngRange.getServ ngRangeS nce d();
    SearchOperator s nce d = new SearchOperator(SearchOperator.Type.S NCE_ D,
                                                Long.toStr ng(t erS nce d));

    long t erMax d = serv ngRange.getServ ngRangeMax d();
    SearchOperator max d = new SearchOperator(SearchOperator.Type.MAX_ D,
                                              Long.toStr ng(t erMax d));

    L st<Query> conjunct onCh ldren = (query == null)
        ? L sts.<Query>newArrayL st(s nce d, max d)
        : L sts.newArrayL st(query, s nce d, max d);

    Query restr ctedQuery = new Conjunct on(conjunct onCh ldren).s mpl fy();

    Earlyb rdRequestContext cop edRequestContext =
        Earlyb rdRequestContext.copyRequestContext(requestContext, restr ctedQuery);

    return cop edRequestContext;
  }
}
