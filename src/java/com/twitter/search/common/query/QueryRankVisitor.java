package com.tw ter.search.common.query;

 mport java.ut l. dent yHashMap;

 mport com.google.common.base.Precond  ons;
 mport com.google.common.collect.Maps;

 mport com.tw ter.search.queryparser.query.BooleanQuery;
 mport com.tw ter.search.queryparser.query.Query;
 mport com.tw ter.search.queryparser.query.QueryParserExcept on;
 mport com.tw ter.search.queryparser.query.annotat on.Annotat on;
 mport com.tw ter.search.queryparser.v s ors.DetectAnnotat onV s or;

/**
 * A v s or that collects node ranks from :r annotat on  n t  query
 */
publ c class QueryRankV s or extends DetectAnnotat onV s or {
  pr vate f nal  dent yHashMap<Query,  nteger> nodeToRankMap = Maps.new dent yHashMap();

  publ c QueryRankV s or() {
    super(Annotat on.Type.NODE_RANK);
  }

  @Overr de
  protected boolean v s BooleanQuery(BooleanQuery query) throws QueryParserExcept on {
     f (query.hasAnnotat onType(Annotat on.Type.NODE_RANK)) {
      collectNodeRank(query.getAnnotat onOf(Annotat on.Type.NODE_RANK).get(), query);
    }

    boolean found = false;
    for (Query ch ld : query.getCh ldren()) {
      found |= ch ld.accept(t );
    }
    return found;
  }

  @Overr de
  protected boolean v s Query(Query query) throws QueryParserExcept on {
     f (query.hasAnnotat onType(Annotat on.Type.NODE_RANK)) {
      collectNodeRank(query.getAnnotat onOf(Annotat on.Type.NODE_RANK).get(), query);
      return true;
    }

    return false;
  }

  pr vate vo d collectNodeRank(Annotat on anno, Query query) {
    Precond  ons.c ckArgu nt(anno.getType() == Annotat on.Type.NODE_RANK);
     nt rank = ( nteger) anno.getValue();
    nodeToRankMap.put(query, rank);
  }

  publ c  dent yHashMap<Query,  nteger> getNodeToRankMap() {
    return nodeToRankMap;
  }
}
