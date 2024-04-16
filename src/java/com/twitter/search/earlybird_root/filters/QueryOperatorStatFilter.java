package com.tw ter.search.earlyb rd_root.f lters;

 mport java.ut l.EnumSet;
 mport java.ut l.Set;
 mport java.ut l.concurrent.T  Un ;

 mport scala.runt  .BoxedUn ;

 mport com.google.common.collect. mmutableMap;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.S mpleF lter;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchT  r;
 mport com.tw ter.search.common. tr cs.SearchT  rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;
 mport com.tw ter.search.queryparser.v s ors.DetectAnnotat onV s or;
 mport com.tw ter.search.queryparser.v s ors.DetectV s or;
 mport com.tw ter.ut l.Future;

/**
 * For a g ven query,  ncre nts counters  f that query has a number of search operators or
 * annotat ons appl ed to  . Used to detect unusual traff c patterns.
 */
publ c class QueryOperatorStatF lter
    extends S mpleF lter<Earlyb rdRequestContext, Earlyb rdResponse> {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(QueryOperatorStatF lter.class);

  pr vate f nal SearchCounter numQueryOperatorDetect onErrors =
      SearchCounter.export("query_operator_detect on_errors");

  pr vate f nal SearchCounter numQueryOperatorCons deredRequests =
      SearchCounter.export("query_operator_requests_cons dered");

  pr vate f nal  mmutableMap<Str ng, SearchT  rStats> f lterOperatorStats;

  // Keeps track of t  number of quer es w h a f lter appl ed, whose type   don't care about.
  pr vate f nal SearchCounter numUnknownF lterOperatorRequests =
      SearchCounter.export("query_operator_f lter_unknown_requests");

  pr vate f nal  mmutableMap<Str ng, SearchT  rStats>  ncludeOperatorStats;

  // Keeps track of t  number of quer es w h an  nclude operator appl ed, whose type   don't
  // know about.
  pr vate f nal SearchCounter numUnknown ncludeOperatorRequests =
      SearchCounter.export("query_operator_ nclude_unknown_requests");

  pr vate f nal  mmutableMap<SearchOperator.Type, SearchT  rStats> operatorTypeStats;

  pr vate f nal SearchCounter numVar antRequests =
      SearchCounter.export("query_operator_var ant_requests");

  /**
   * Construct t  QueryOperatorStatF lter by gett ng t  complete set of poss ble f lters a query
   * m ght have and assoc at ng each w h a counter.
   */
  publ c QueryOperatorStatF lter() {

     mmutableMap.Bu lder<Str ng, SearchT  rStats> f lterBu lder = new  mmutableMap.Bu lder<>();
    for (Str ng operand : SearchOperatorConstants.VAL D_F LTER_OPERANDS) {
      f lterBu lder.put(
          operand,
          SearchT  rStats.export(
              "query_operator_f lter_" + operand + "_requests",
              T  Un .M LL SECONDS,
              false,
              true));
    }
    f lterOperatorStats = f lterBu lder.bu ld();

     mmutableMap.Bu lder<Str ng, SearchT  rStats>  ncludeBu lder = new  mmutableMap.Bu lder<>();
    for (Str ng operand : SearchOperatorConstants.VAL D_ NCLUDE_OPERANDS) {
       ncludeBu lder.put(
          operand,
          SearchT  rStats.export(
              "query_operator_ nclude_" + operand + "_requests",
              T  Un .M LL SECONDS,
              false,
              true));
    }
     ncludeOperatorStats =  ncludeBu lder.bu ld();

     mmutableMap.Bu lder<SearchOperator.Type, SearchT  rStats> operatorBu lder =
        new  mmutableMap.Bu lder<>();
    for (SearchOperator.Type operatorType : SearchOperator.Type.values()) {
      operatorBu lder.put(
          operatorType,
          SearchT  rStats.export(
              "query_operator_" + operatorType.na ().toLo rCase() + "_requests",
              T  Un .M LL SECONDS,
              false,
              true
          ));
    }
    operatorTypeStats = operatorBu lder.bu ld();
  }

  @Overr de
  publ c Future<Earlyb rdResponse> apply(
      Earlyb rdRequestContext requestContext,
      Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> serv ce) {
    numQueryOperatorCons deredRequests. ncre nt();
    Query parsedQuery = requestContext.getParsedQuery();

     f (parsedQuery == null) {
      return serv ce.apply(requestContext);
    }

    SearchT  r t  r = new SearchT  r();
    t  r.start();

    return serv ce.apply(requestContext).ensure(() -> {
      t  r.stop();

      try {
        updateT  rsForOperatorsAndOperands(parsedQuery, t  r);
        updateCounters fVar antAnnotat on(parsedQuery);
      } catch (QueryParserExcept on e) {
        LOG.warn("Unable to test  f query has operators def ned", e);
        numQueryOperatorDetect onErrors. ncre nt();
      }
      return BoxedUn .UN T;
    });
  }

  /**
   * Tracks request stats for operators and operands.
   *
   * @param parsedQuery t  query to c ck.
   */
  pr vate vo d updateT  rsForOperatorsAndOperands(Query parsedQuery, SearchT  r t  r)
      throws QueryParserExcept on {
    f nal DetectV s or detectV s or = new DetectV s or(false, SearchOperator.Type.values());
    parsedQuery.accept(detectV s or);

    Set<SearchOperator.Type> detectedOperatorTypes = EnumSet.noneOf(SearchOperator.Type.class);
    for (Query query : detectV s or.getDetectedQuer es()) {
      // T  detectV s or only matc s on SearchOperators.
      SearchOperator operator = (SearchOperator) query;
      SearchOperator.Type operatorType = operator.getOperatorType();
      detectedOperatorTypes.add(operatorType);

       f (operatorType == SearchOperator.Type. NCLUDE) {
        updateOperandStats(
            operator,
             ncludeOperatorStats,
            t  r,
            numUnknown ncludeOperatorRequests);
      }
       f (operatorType == SearchOperator.Type.F LTER) {
        updateOperandStats(
            operator,
            f lterOperatorStats,
            t  r,
            numUnknownF lterOperatorRequests);
      }
    }

    for (SearchOperator.Type type : detectedOperatorTypes) {
      operatorTypeStats.get(type).stoppedT  r ncre nt(t  r);
    }
  }

  pr vate vo d updateOperandStats(
      SearchOperator operator,
       mmutableMap<Str ng, SearchT  rStats> operandRequestStats,
      SearchT  r t  r,
      SearchCounter unknownOperandStat) {
    Str ng operand = operator.getOperand();
    SearchT  rStats stats = operandRequestStats.get(operand);

     f (stats != null) {
      stats.stoppedT  r ncre nt(t  r);
    } else {
      unknownOperandStat. ncre nt();
    }
  }

  pr vate vo d updateCounters fVar antAnnotat on(Query parsedQuery) throws QueryParserExcept on {
    DetectAnnotat onV s or v s or = new DetectAnnotat onV s or(Annotat on.Type.VAR ANT);
     f (parsedQuery.accept(v s or)) {
      numVar antRequests. ncre nt();
    }
  }
}
