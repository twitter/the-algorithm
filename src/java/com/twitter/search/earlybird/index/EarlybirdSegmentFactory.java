package com.tw ter.search.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport org.apac .lucene.store.D rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSync nfo;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;

publ c class Earlyb rdSeg ntFactory {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdSeg ntFactory.class);

  pr vate f nal Earlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate Clock clock;

  publ c Earlyb rdSeg ntFactory(
      Earlyb rd ndexConf g earlyb rd ndexConf g,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Earlyb rdSearc rStats searc rStats,
      Clock clock) {
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .searc rStats = searc rStats;
    t .clock = clock;
  }

  publ c Earlyb rd ndexConf g getEarlyb rd ndexConf g() {
    return earlyb rd ndexConf g;
  }

  /**
   * Creates a new earlyb rd seg nt.
   */
  publ c Earlyb rdSeg nt newEarlyb rdSeg nt(Seg nt seg nt, Seg ntSync nfo seg ntSync nfo)
      throws  OExcept on {
    D rectory d r = earlyb rd ndexConf g.newLuceneD rectory(seg ntSync nfo);

    LOG. nfo("Creat ng Earlyb rdSeg nt on " + d r.toStr ng());

    return new Earlyb rdSeg nt(
        seg nt.getSeg ntNa (),
        seg nt.getT  Sl ce D(),
        seg nt.getMaxSeg ntS ze(),
        d r,
        earlyb rd ndexConf g,
        search ndex ng tr cSet,
        searc rStats,
        clock);
  }
}
