package com.tw ter.search.common.query;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.funct on.B Funct on;
 mport java.ut l.funct on.Funct on;

 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.search.Query;

/**
 * Not threadsafe, but should be reused across d fferent quer es unless t  s ze of t  ex st ng
 * one  s too small for a new huge ser al zed query.
 */
publ c class H Attr buteCollector {
  pr vate f nal L st<F eldRankH  nfo> h  nfos = L sts.newArrayL st();
  pr vate f nal B Funct on< nteger,  nteger, F eldRankH  nfo> h  nfoSuppl er;

  pr vate  nt docBase = 0;

  publ c H Attr buteCollector() {
    t .h  nfoSuppl er = F eldRankH  nfo::new;
  }

  /**
   * Constructs a new {@code H Attr but onCollector} w h t  spec f ed {@code F eldRankH  nfo}
   * suppl er.
   *
   * @param h  nfoSuppl er funct on to supply a {@code F eldRankH  nfo}  nstance
   */
  publ c H Attr buteCollector(B Funct on< nteger,  nteger, F eldRankH  nfo> h  nfoSuppl er) {
    t .h  nfoSuppl er = h  nfoSuppl er;
  }

  /**
   * Creates a new  dent f ableQuery for t  g ven query, f eld d and rank, and "reg sters"
   * t  f eld d and t  rank w h t  collector.
   *
   * @param query t  query to be wrapped.
   * @param f eld d t   D of t  f eld to be searc d.
   * @param rank T  rank of t  query.
   * @return A new  dent f ableQuery  nstance for t  g ven query, f eld d and rank.
   */
  publ c  dent f ableQuery new dent f ableQuery(Query query,  nt f eld d,  nt rank) {
    F eldRankH  nfo f eldRankH  nfo = h  nfoSuppl er.apply(f eld d, rank);
    h  nfos.add(f eldRankH  nfo);
    return new  dent f ableQuery(query, f eldRankH  nfo, t );
  }

  publ c vo d clearH Attr but ons(LeafReaderContext ctx, F eldRankH  nfo h  nfo) {
    docBase = ctx.docBase;
    h  nfo.resetDoc d();
  }

  publ c vo d collectScorerAttr but on( nt doc d, F eldRankH  nfo h  nfo) {
    h  nfo.setDoc d(doc d + docBase);
  }

  /**
   * T   thod should be called w n a global h  occurs.
   * T   thod returns h  attr but on summary for t  whole query tree.
   * T  supports gett ng h  attr but on for only t  curDoc.
   *
   * @param doc d doc d passed  n for c ck ng aga nst curDoc.
   * @return Returns a map from node rank to a set of match ng f eld  Ds. T  map does not conta n
   *         entr es for ranks that d d not h  at all.
   */
  publ c Map< nteger, L st< nteger>> getH Attr but on( nt doc d) {
    return getH Attr but on(doc d, (f eld d) -> f eld d);
  }

  /**
   * T   thod should be called w n a global h  occurs.
   * T   thod returns h  attr but on summary for t  whole query tree.
   * T  supports gett ng h  attr but on for only t  curDoc.
   *
   * @param doc d doc d passed  n for c ck ng aga nst curDoc.
   * @param f eld dFunc T  mapp ng of f eld  Ds to objects of type T.
   * @return Returns a map from node rank to a set of match ng objects (usually f eld  Ds or na s).
   *         T  map does not conta n entr es for ranks that d d not h  at all.
   */
  publ c <T> Map< nteger, L st<T>> getH Attr but on( nt doc d, Funct on< nteger, T> f eld dFunc) {
     nt key = doc d + docBase;
    Map< nteger, L st<T>> h Map = Maps.newHashMap();

    // Manually  erate through all h  nfos ele nts.  's sl ghtly faster than us ng an  erator.
    for (F eldRankH  nfo h  nfo : h  nfos) {
       f (h  nfo.getDoc d() == key) {
         nt rank = h  nfo.getRank();
        L st<T> rankH s = h Map.compute fAbsent(rank, k -> L sts.newArrayL st());
        T f eldDescr pt on = f eld dFunc.apply(h  nfo.getF eld d());
        rankH s.add(f eldDescr pt on);
      }
    }

    return h Map;
  }
}
