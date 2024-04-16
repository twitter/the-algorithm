package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Map;

 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.part  on ng.base.Part  onMapp ngManager;
 mport com.tw ter.search.earlyb rd_root.v s ors.Mult TermD sjunct onPerPart  onV s or;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;

publ c f nal class Earlyb rdRootQueryUt ls {

  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdRootQueryUt ls.class);

  pr vate Earlyb rdRootQueryUt ls() {
  }

  /**
   * Rewr e 'mult _term_d sjunct on from_user_ d' or 'mult _term_d sjunct on  d' based on part  on
   * for USER_ D/TWEET_ D part  oned cluster
   * @return a map w h part  on  d as key and rewr ten query as value.
   *  f t re  s no 'mult _term_d sjunct on from_user_ d/ d'  n query, t  map w ll be empty;  f all
   *  ds are truncated for a part  on,   w ll add a NO_MATCH_CONJUNCT ON  re.
   */
  publ c stat c Map< nteger, Query> rewr eMult TermD sjunct onPerPart  onF lter(
      Query query, Part  onMapp ngManager part  onMapp ngManager,  nt numPart  ons) {
    Map< nteger, Query> m = Maps.newHashMap();
    //  f t re  s no parsed query, just return
     f (query == null) {
      return m;
    }
    for ( nt   = 0;   < numPart  ons; ++ ) {
      Mult TermD sjunct onPerPart  onV s or v s or =
          new Mult TermD sjunct onPerPart  onV s or(part  onMapp ngManager,  );
      try {
        Query q = query.accept(v s or);
         f (q != null && q != query) {
          m.put( , q);
        }
      } catch (QueryParserExcept on e) {
        // Should not happen, put and log error  re just  n case
        m.put( , query);
        LOG.error(
            "Mult TermD sjuct onPerPart  onV s or cannot process query: " + query.ser al ze());
      }
    }
    return m;
  }
}
