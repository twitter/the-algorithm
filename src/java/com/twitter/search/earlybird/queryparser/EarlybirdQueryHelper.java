package com.tw ter.search.earlyb rd.queryparser;

 mport javax.annotat on.Nullable;

 mport com.google.common.base.Opt onal;
 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.constants.QueryCac Constants;
 mport com.tw ter.search.common.query.H Attr buteCollector;
 mport com.tw ter.search.common.query.H Attr bute lper;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.search.term nat on.QueryT  out;
 mport com.tw ter.search.common.search.term nat on.Term nat onQuery;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryNodeUt ls;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;
 mport com.tw ter.search.queryparser.query.search.SearchOperatorConstants;

publ c abstract class Earlyb rdQuery lper {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdQuery lper.class);

  /**
   * Wraps t  g ven query and so  clauses to exclude ant soc al t ets  nto a conjunct on.
   */
  publ c stat c Query requ reExcludeAnt soc al(
      Query bas cQuery,
      QueryCac Manager queryCac Manager) throws QueryParserExcept on {
    // Do not set exclude ant soc al  f t y have any ot r ant soc al f lters set
    Query query = bas cQuery;
    DetectAnt soc alV s or detectAnt soc alV s or = new DetectAnt soc alV s or();
    query.accept(detectAnt soc alV s or);
     f (detectAnt soc alV s or.hasAnyAnt soc alOperator()) {
      return query;
    }

    // No operator found, force ant soc al f lter.
     f (queryCac Manager.enabled()) {
      SearchOperator f lter =
          new SearchOperator(SearchOperator.Type.CACHED_F LTER,
              QueryCac Constants.EXCLUDE_ANT SOC AL);

      query = QueryNodeUt ls.appendAsConjunct on(query, f lter);
    } else {
      SearchOperator f lter = new SearchOperator(SearchOperator.Type.EXCLUDE,
          SearchOperatorConstants.ANT SOC AL);

      query = QueryNodeUt ls.appendAsConjunct on(query, f lter);
    }
    return query;
  }

  /**
   * Wraps t  g ven query  nto an equ valent query that w ll also collect h  attr but on data.
   *
   * @param query T  or g nal query.
   * @param node T  query parser node stor ng t  query.
   * @param f eld nfo T  f eld  n wh ch t  g ven query w ll be search ng.
   * @param h Attr bute lper T   lper that w ll collect all h  attr but on data.
   * @return An equ valent query that w ll also collect h  attr but on data.
   */
  publ c stat c f nal org.apac .lucene.search.Query maybeWrapW hH Attr but onCollector(
      org.apac .lucene.search.Query query,
      @Nullable com.tw ter.search.queryparser.query.Query node,
      Sc ma.F eld nfo f eld nfo,
      @Nullable H Attr bute lper h Attr bute lper) {
    // Prevents l nt error for ass gn ng to a funct on para ter.
    org.apac .lucene.search.Query luceneQuery = query;
     f (h Attr bute lper != null && node != null) {
      Opt onal<Annotat on> annotat on = node.getAnnotat onOf(Annotat on.Type.NODE_RANK);

       f (annotat on. sPresent()) {
         nteger nodeRank = ( nteger) annotat on.get().getValue();
        luceneQuery = wrapW hH Attr but onCollector(
            luceneQuery,
            f eld nfo,
            nodeRank,
            h Attr bute lper.getF eldRankH Attr buteCollector());
      }
    }

    return luceneQuery;
  }

  /**
   * Wraps t  g ven query  nto an equ valent query that w ll also collect h  attr but on data.
   *
   * @param query T  or g nal query.
   * @param nodeRank T  rank of t  g ven query  n t  overall request query.
   * @param f eld nfo T  f eld  n wh ch t  g ven query w ll be search ng.
   * @param h Attr bute lper T   lper that w ll collect all h  attr but on data.
   * @return An equ valent query that w ll also collect h  attr but on data.
   */
  publ c stat c f nal org.apac .lucene.search.Query maybeWrapW hH Attr but onCollector(
      org.apac .lucene.search.Query query,
       nt nodeRank,
      Sc ma.F eld nfo f eld nfo,
      @Nullable H Attr bute lper h Attr bute lper) {

    org.apac .lucene.search.Query luceneQuery = query;
     f (h Attr bute lper != null && nodeRank != -1) {
      Precond  ons.c ckArgu nt(nodeRank > 0);
      luceneQuery = wrapW hH Attr but onCollector(
          luceneQuery, f eld nfo, nodeRank, h Attr bute lper.getF eldRankH Attr buteCollector());
    }
    return luceneQuery;
  }

  pr vate stat c f nal org.apac .lucene.search.Query wrapW hH Attr but onCollector(
      org.apac .lucene.search.Query luceneQuery,
      Sc ma.F eld nfo f eld nfo,
       nt nodeRank,
      H Attr buteCollector h Attr buteCollector) {
    Precond  ons.c ckNotNull(f eld nfo,
        "Tr ed collect ng h  attr but on for unknown f eld: " + f eld nfo.getNa ()
            + " luceneQuery: " + luceneQuery);
    return h Attr buteCollector.new dent f ableQuery(
        luceneQuery, f eld nfo.getF eld d(), nodeRank);
  }

  /**
   * Returns a query equ valent to t  g ven query, and w h t  g ven t  out enforced.
   */
  publ c stat c org.apac .lucene.search.Query maybeWrapW hT  out(
      org.apac .lucene.search.Query query,
      QueryT  out t  out) {
     f (t  out != null) {
      return new Term nat onQuery(query, t  out);
    }
    return query;
  }

  /**
   * Returns a query equ valent to t  g ven query, and w h t  g ven t  out enforced.  f t 
   * g ven query  s negated,    s returned w hout any mod f cat ons.
   */
  publ c stat c org.apac .lucene.search.Query maybeWrapW hT  out(
      org.apac .lucene.search.Query query,
      @Nullable com.tw ter.search.queryparser.query.Query node,
      QueryT  out t  out) {
    //  f t  node  s look ng for negat on of so th ng,   don't want to  nclude    n node-level
    // t  out c cks.  n general, nodes keep track of t  last doc seen, but non-match ng docs
    // encountered by "must not occur" node do not reflect overall progress  n t   ndex.
     f (node != null && node.mustNotOccur()) {
      return query;
    }
    return maybeWrapW hT  out(query, t  out);
  }
}
