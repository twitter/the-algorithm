package com.tw ter.search.earlyb rd.queryparser;

 mport java.ut l.Map;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport org.apac .lucene.search.Query;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.query.MappableF eld;
 mport com.tw ter.search.common.sc ma.base.F eld  ghtDefault;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.search.Term nat onTracker;
 mport com.tw ter.search.common.search.term nat on.QueryT  out;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.part  on.Mult Seg ntTermD ct onaryManager;
 mport com.tw ter.search.earlyb rd.querycac .QueryCac Manager;
 mport com.tw ter.search.queryparser.query.search.SearchOperator;

publ c class LuceneRelevanceQueryV s or extends Earlyb rdLuceneQueryV s or {
  publ c LuceneRelevanceQueryV s or(
       mmutableSc ma nterface sc ma,
      QueryCac Manager queryCac Manager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Term nat onTracker term nat onTracker,
      Map<Str ng, F eld  ghtDefault> f eld  ghtMap,
      Map<MappableF eld, Str ng> mappableF eldMap,
      Mult Seg ntTermD ct onaryManager mult Seg ntTermD ct onaryManager,
      Dec der dec der,
      Earlyb rdCluster earlyb rdCluster,
      QueryT  out queryT  out) {
    super(
        sc ma,
        queryCac Manager,
        userTable,
        userScrubGeoMap,
        term nat onTracker,
        f eld  ghtMap,
        mappableF eldMap,
        mult Seg ntTermD ct onaryManager,
        dec der,
        earlyb rdCluster,
        queryT  out);
  }

  @V s bleForTest ng
  protected LuceneRelevanceQueryV s or(
       mmutableSc ma nterface sc ma,
      QueryCac Manager queryCac Manager,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Earlyb rdCluster earlyb rdCluster) {
    super(sc ma,
          queryCac Manager,
          userTable,
          userScrubGeoMap,
          earlyb rdCluster,
          queryCac Manager.getDec der());
  }

  @Overr de
  protected Query v s S nce DOperator(SearchOperator op) {
    // s nce_ d  s handled by t  blender for relevance quer es, so don't f lter on  .
    return null;
  }
}
