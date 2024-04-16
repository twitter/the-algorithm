package com.tw ter.search.earlyb rd.search;

 mport java. o. OExcept on;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;

/**
 * Created w h  ntell J  DEA.
 * Date: 6/20/12
 * T  : 12:06 PM
 * To change t  template use F le | Sett ngs | F le Templates.
 */
publ c class Soc alSearchResultsCollector extends SearchResultsCollector {

  pr vate f nal Soc alF lter soc alF lter;

  publ c Soc alSearchResultsCollector(
       mmutableSc ma nterface sc ma,
      SearchRequest nfo searchRequest nfo,
      Soc alF lter soc alF lter,
      Earlyb rdSearc rStats searc rStats,
      Earlyb rdCluster cluster,
      UserTable userTable,
       nt requestDebugMode) {
    super(sc ma, searchRequest nfo, Clock.SYSTEM_CLOCK, searc rStats, cluster, userTable,
        requestDebugMode);
    t .soc alF lter = soc alF lter;
  }

  @Overr de
  publ c f nal vo d doCollect(long t et D) throws  OExcept on {
     f (soc alF lter == null || soc alF lter.accept(curDoc d)) {
      results.add(new H (currT  Sl ce D, t et D));
    }
  }

  @Overr de
  publ c vo d startSeg nt() throws  OExcept on {
     f (soc alF lter != null) {
      soc alF lter.startSeg nt(currTw terReader);
    }
  }
}
