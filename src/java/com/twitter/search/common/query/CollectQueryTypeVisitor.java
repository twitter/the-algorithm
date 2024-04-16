package com.tw ter.search.common.query;

 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.collect.Maps;

 mport com.tw ter.search.queryparser.query.BooleanQuery;
 mport com.tw ter.search.queryparser.query.Conjunct on;
 mport com.tw ter.search.queryparser.query.D sjunct on;
 mport com.tw ter.search.queryparser.query.Operator;
 mport com.tw ter.search.queryparser.query.Phrase;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.QueryV s or;
 mport com.tw ter.search.queryparser.query.Spec alTerm;
 mport com.tw ter.search.queryparser.query.Term;

/**
 * Collects t  nodes w h a spec f ed query type  n t  g ven query.
 */
publ c class CollectQueryTypeV s or extends QueryV s or<Boolean> {

  protected f nal Query.QueryType queryType;

  protected f nal Map<Query, Boolean> nodeToTypeMap = Maps.new dent yHashMap();

  publ c CollectQueryTypeV s or(Query.QueryType queryType) {
    t .queryType = queryType;
  }

  @Overr de
  publ c Boolean v s (D sjunct on d sjunct on) throws QueryParserExcept on {
    return v s BooleanQuery(d sjunct on);
  }

  @Overr de
  publ c Boolean v s (Conjunct on conjunct on) throws QueryParserExcept on {
    return v s BooleanQuery(conjunct on);
  }

  @Overr de
  publ c Boolean v s (Phrase phrase) throws QueryParserExcept on {
    return v s Query(phrase);
  }

  @Overr de
  publ c Boolean v s (Term term) throws QueryParserExcept on {
    return v s Query(term);
  }

  @Overr de
  publ c Boolean v s (Operator operator) throws QueryParserExcept on {
    return v s Query(operator);
  }

  @Overr de
  publ c Boolean v s (Spec alTerm spec al) throws QueryParserExcept on {
    return v s Query(spec al);
  }

  publ c Set<Query> getCollectedNodes() {
    return nodeToTypeMap.keySet();
  }

  protected boolean v s Query(Query query) throws QueryParserExcept on {
     f (query. sTypeOf(queryType)) {
      collectNode(query);
      return true;
    }
    return false;
  }

  protected vo d collectNode(Query query) {
    nodeToTypeMap.put(query, true);
  }

  protected boolean v s BooleanQuery(BooleanQuery query) throws QueryParserExcept on {
    boolean found = false;
     f (query. sTypeOf(queryType)) {
      collectNode(query);
      found = true;
    }
    for (Query ch ld : query.getCh ldren()) {
      found |= ch ld.accept(t );
    }
    return found;
  }
}
