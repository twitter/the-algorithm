package com.tw ter.search.common.query;

 mport java.ut l.Collect ons;
 mport java.ut l. dent yHashMap;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.funct on.Funct on;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.v s ors.Mult TermD sjunct onRankV s or;
 mport com.tw ter.search.queryparser.v s ors.NodeRankAnnotator;
 mport com.tw ter.search.queryparser.v s ors.QueryTree ndex;

/**
 * A  lper class to collect f eld and query node h  attr but ons.
 */
publ c class QueryH Attr bute lper extends H Attr bute lper {
  pr vate f nal Query annotatedQuery;

  protected QueryH Attr bute lper(H Attr buteCollector collector,
                                    Funct on< nteger, Str ng> f eld dsToF eldNa s,
                                     dent yHashMap<Query,  nteger> nodeToRankMap,
                                    Query annotatedQuery,
                                    Map<Query, L st< nteger>> expandedRanksMap) {
    super(collector, f eld dsToF eldNa s, nodeToRankMap, expandedRanksMap);
    t .annotatedQuery = annotatedQuery;
  }

  /**
   * Constructor spec f c for com.tw ter.search.queryParser.query.Query
   *
   * T   lper v s s a parsed query to construct a node-to-rank mapp ng,
   * and uses a sc ma to determ ne all of t  poss ble f elds to be tracked.
   * A collector  s t n created.
   *
   * @param query t  query for wh ch   w ll collect h  attr but on.
   * @param sc ma t   ndex ng sc ma.
   */
  publ c stat c QueryH Attr bute lper from(Query query, f nal Sc ma sc ma)
      throws QueryParserExcept on {
     dent yHashMap<Query,  nteger> nodeToRankMap;
    Query annotatedQuery;

    // F rst see  f t  query already has node rank annotat ons on  .  f so,  'll just use those
    // to  dent fy query nodes.
    //   enforce that all prov ded ranks are  n t  range of [0, N-1] so not to blow up t  s ze
    // of t  collect on array.
    QueryRankV s or rankV s or = new QueryRankV s or();
     f (query.accept(rankV s or)) {
      nodeToRankMap = rankV s or.getNodeToRankMap();
      annotatedQuery = query;
    } else {
      // Ot rw se,   w ll ass gn all nodes  n-order ranks, and use those to track per-node h 
      // attr but on
      QueryTree ndex queryTree ndex = QueryTree ndex.bu ldFor(query);
      NodeRankAnnotator annotator = new NodeRankAnnotator(queryTree ndex.getNodeTo ndexMap());
      annotatedQuery = query.accept(annotator);
      nodeToRankMap = annotator.getUpdatedNodeToRankMap();
    }

    // Extract ranks for mult _term_d sjunct on operators
    Mult TermD sjunct onRankV s or mult TermD sjunct onRankV s or =
        new Mult TermD sjunct onRankV s or(Collect ons.max(nodeToRankMap.values()));
    annotatedQuery.accept(mult TermD sjunct onRankV s or);
    Map<Query, L st< nteger>> expandedRanksMap =
        mult TermD sjunct onRankV s or.getMult TermD sjunct onRankExpans onsMap();

    return new QueryH Attr bute lper(
        new H Attr buteCollector(),
        (f eld d) -> sc ma.getF eldNa (f eld d),
        nodeToRankMap,
        annotatedQuery,
        expandedRanksMap);
  }

  publ c Query getAnnotatedQuery() {
    return annotatedQuery;
  }
}
