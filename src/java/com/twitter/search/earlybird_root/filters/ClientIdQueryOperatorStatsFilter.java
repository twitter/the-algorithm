package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.Arrays;
 mport java.ut l.EnumSet;
 mport java.ut l.HashSet;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentHashMap;
 mport java.ut l.concurrent.ConcurrentMap;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common.cl entstats.RequestCounters;
 mport com.tw ter.search.common.cl entstats.RequestCountersEventL stener;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.v s ors.DetectV s or;
 mport com.tw ter.ut l.Future;

/**
* T  f lter exports RequestCounters stats for each un que comb nat on of cl ent_ d and
* query_operator. RequestCounters produce 19 stats for each pref x, and   have nu rous
* cl ents and operators, so t  f lter can produce a large number of stats. To keep t 
* number of exported stats reasonable   use an allow l st of operators. T  l st currently
*  ncludes t  geo operators wh le   mon or t   mpacts of realt   geo f lter ng. See
* SEARCH-33699 for project deta ls.
*
* To f nd t  stats look for query_cl ent_operator_* exported by arch ve roots.
*
 **/

publ c class Cl ent dQueryOperatorStatsF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Cl ent dQueryOperatorStatsF lter.class);

  publ c stat c f nal Str ng COUNTER_PREF X_PATTERN = "query_cl ent_operator_%s_%s";
  pr vate f nal Clock clock;
  pr vate f nal ConcurrentMap<Str ng, RequestCounters> requestCountersByCl ent dAndOperator =
      new ConcurrentHashMap<>();
  pr vate f nal Set<SearchOperator.Type> operatorsToRecordStatsFor = new HashSet<>(Arrays.asL st(
      SearchOperator.Type.GEO_BOUND NG_BOX,
      SearchOperator.Type.GEOCODE,
      SearchOperator.Type.GEOLOCAT ON_TYPE,
      SearchOperator.Type.NEAR,
      SearchOperator.Type.PLACE,
      SearchOperator.Type.W TH N));

  publ c Cl ent dQueryOperatorStatsF lter() {
    t .clock = Clock.SYSTEM_CLOCK;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    Earlyb rdRequest req = requestContext.getRequest();
    Query parsedQuery = requestContext.getParsedQuery();

     f (parsedQuery == null) {
      return serv ce.apply(requestContext);
    }

    Set<SearchOperator.Type> operators = getOperators(parsedQuery);
    Future<Earlyb rdResponse> response = serv ce.apply(requestContext);
    for (SearchOperator.Type operator : operators) {

      RequestCounters cl entOperatorCounters = getCl entOperatorCounters(req.cl ent d, operator);
      RequestCountersEventL stener<Earlyb rdResponse> cl entOperatorCountersEventL stener =
          new RequestCountersEventL stener<>(
              cl entOperatorCounters, clock, Earlyb rdSuccessfulResponseHandler. NSTANCE);

      response = response.addEventL stener(cl entOperatorCountersEventL stener);
    }
    return response;
  }

  /**
   * Gets or creates RequestCounters for t  g ven cl ent d and operatorType
   */
  pr vate RequestCounters getCl entOperatorCounters(Str ng cl ent d,
                                                    SearchOperator.Type operatorType) {
    Str ng counterPref x = Str ng.format(COUNTER_PREF X_PATTERN, cl ent d, operatorType.toStr ng());
    RequestCounters cl entCounters = requestCountersByCl ent dAndOperator.get(counterPref x);
     f (cl entCounters == null) {
      cl entCounters = new RequestCounters(counterPref x);
      RequestCounters ex st ngCounters =
          requestCountersByCl ent dAndOperator.put fAbsent(counterPref x, cl entCounters);
       f (ex st ngCounters != null) {
        cl entCounters = ex st ngCounters;
      }
    }
    return cl entCounters;
  }

  /**
   * Returns a set of t  SearchOperator types that are:
   * 1) used by t  query
   * 2)  ncluded  n t  allow l st: operatorsToRecordStatsFor
   */
  pr vate Set<SearchOperator.Type> getOperators(Query parsedQuery) {
    f nal DetectV s or detectV s or = new DetectV s or(false, SearchOperator.Type.values());
    Set<SearchOperator.Type> detectedOperatorTypes = EnumSet.noneOf(SearchOperator.Type.class);

    try {
      parsedQuery.accept(detectV s or);
    } catch (QueryParserExcept on e) {
      LOG.error("Fa led to detect SearchOperators  n query: " + parsedQuery.toStr ng());
      return detectedOperatorTypes;
    }

    for (Query query : detectV s or.getDetectedQuer es()) {
      // T  detectV s or only matc s on SearchOperators.
      SearchOperator operator = (SearchOperator) query;
      SearchOperator.Type operatorType = operator.getOperatorType();
       f (operatorsToRecordStatsFor.conta ns(operatorType)) {
        detectedOperatorTypes.add(operatorType);
      }
    }
    return detectedOperatorTypes;
  }
}
