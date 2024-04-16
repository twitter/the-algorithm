package com.tw ter.search.common.query;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.funct on.Funct on;

 mport com.google.common.collect.Maps;

 mport com.tw ter.search.queryparser.query.Query;

 mport stat c com.tw ter.search.common.query.F eldRankH  nfo.UNSET_DOC_ D;

/**
 * Gener c  lper class conta n ng t  data needed to set up and collect f eld h  attr but ons.
 */
publ c class H Attr bute lper  mple nts H Attr buteProv der {
  pr vate f nal H Attr buteCollector collector;
  pr vate f nal Funct on< nteger, Str ng> f eld dsToF eldNa s;

  // T   s a mapp ng of type T query nodes to rank  d
  pr vate f nal Map<Query,  nteger> nodeToRankMap;

  // T   s  ant to expand  nd v dual Query nodes  nto mult ple ranks,
  // for example, expand ng a mult _term_d sjunct on to  nclude a rank for each d sjunct on value.
  pr vate f nal Map<Query, L st< nteger>> expandedNodeToRankMap;

  // A s ngle-entry cac  for h  attr but on, so   can reuse t   m d ate result. W ll be used
  // only w n lastDoc d matc s
  pr vate ThreadLocal<Map< nteger, L st<Str ng>>> lastH AttrHolder = new ThreadLocal<>();
  pr vate ThreadLocal< nteger> lastDoc dHolder = ThreadLocal.w h n  al(() -> UNSET_DOC_ D);

  protected H Attr bute lper(
      H Attr buteCollector collector,
      Funct on< nteger, Str ng> f eld dsToF eldNa s,
      Map<Query,  nteger> nodeToRankMap,
      Map<Query, L st< nteger>> expandedNodeToRankMap) {
    t .collector = collector;
    t .f eld dsToF eldNa s = f eld dsToF eldNa s;
    t .nodeToRankMap = nodeToRankMap;
    t .expandedNodeToRankMap = expandedNodeToRankMap;
  }

  /**
   * Constructs a new {@code H Attr bute lper} w h t  spec f ed {@code H Attr buteCollector}
   *  nstance and f elds.
   *
   * @param collector a collector  nstance
   * @param f eld dsToF eldNa s a l st of f eld na s  ndexed by  d
   */
  publ c H Attr bute lper(H Attr buteCollector collector, Str ng[] f eld dsToF eldNa s) {
    t (collector,
        (f eld d) -> f eld dsToF eldNa s[f eld d],
        Maps.newHashMap(),
        Maps.newHashMap());
  }

  publ c H Attr buteCollector getF eldRankH Attr buteCollector() {
    return collector;
  }

  /**
   * Returns h  attr but on  nformat on  ndexed by node rank
   *
   * @param doc d t  docu nt  d
   * @return a mapp ng from t  query's node rank to a l st of f eld na s that  re h .
   */
  publ c Map< nteger, L st<Str ng>> getH Attr but on( nt doc d) {
    // c ck cac  f rst so   don't have to recompute t  sa  th ng.
     f (lastDoc dHolder.get() == doc d) {
      return lastH AttrHolder.get();
    }

    lastDoc dHolder.set(doc d);
    Map< nteger, L st<Str ng>> h Attr but on =
        collector.getH Attr but on(doc d, f eld dsToF eldNa s);
    lastH AttrHolder.set(h Attr but on);
    return h Attr but on;
  }

  /**
   * Adds a new node and  s respect ve rank to t   lper's node-to-rank map
   * W ll throw an except on  f attempt ng to add/update an ex st ng node
   *
   * @param node t  query node
   * @param rank t  rank assoc ated w h t  node
   */
  publ c vo d addNodeRank(Query node,  nt rank) {
    //  f t re are two of t  sa  terms, just map t m to t  f rst rank, t y should get t  sa 
    // h s back
     f (!nodeToRankMap.conta nsKey(node)) {
      nodeToRankMap.put(node, rank);
    }
  }

  publ c Map<Query,  nteger> getNodeToRankMap() {
    return nodeToRankMap;
  }

  publ c Map<Query, L st< nteger>> getExpandedNodeToRankMap() {
    return expandedNodeToRankMap;
  }
}
