package com.tw ter.search.earlyb rd.part  on;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect on;
 mport java.ut l.Collect ons;
 mport java.ut l.Comparator;
 mport java.ut l.HashSet;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.Set;
 mport java.ut l.concurrent.ConcurrentSk pL stMap;
 mport java.ut l.stream.Collectors;
 mport javax.annotat on.Nullable;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Precond  ons;
 mport com.google.common.base.Pred cate;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Maps;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common. tr cs.SearchCounter;
 mport com.tw ter.search.common. tr cs.SearchLongGauge;
 mport com.tw ter.search.common. tr cs.SearchStatsRece ver;
 mport com.tw ter.search.common.part  on ng.base.Seg nt;
 mport com.tw ter.search.common.part  on ng.base.T  Sl ce;
 mport com.tw ter.search.common.part  on ng.snowflakeparser.Snowflake dParser;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.common.CaughtUpMon or;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserScrubGeoMap;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserUpdate;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserUpdatesC cker;
 mport com.tw ter.search.earlyb rd.common.userupdates.UserTable;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdS ngleSeg ntSearc r;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdLuceneSearc r;
 mport com.tw ter.search.earlyb rd.search.Earlyb rdMult Seg ntSearc r;
 mport com.tw ter.search.earlyb rd.stats.Earlyb rdSearc rStats;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponseCode;
 mport com.tw ter.t etyp e.thr ftjava.UserScrubGeoEvent;

publ c class Seg ntManager {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Seg ntManager.class);
  pr vate f nal Clock clock;
  pr vate stat c f nal Str ng STATS_PREF X = "seg nt_manager_";
  pr vate stat c f nal SearchLongGauge SEGMENT_COUNT_STATS =
          SearchLongGauge.export(STATS_PREF X + "total_seg nts");
  pr vate stat c f nal SearchCounter OPT M ZED_SEGMENTS =
          SearchCounter.export(STATS_PREF X + "opt m zed_seg nts");
  pr vate stat c f nal SearchCounter UNOPT M ZED_SEGMENTS =
          SearchCounter.export(STATS_PREF X + "unopt m zed_seg nts");

  publ c enum F lter {
    All( nfo -> true),
    Enabled(Seg nt nfo:: sEnabled),
    Needs ndex ng(Seg nt nfo::needs ndex ng),
    Complete(Seg nt nfo:: sComplete);

    pr vate f nal Pred cate<Seg nt nfo> pred cate;

    F lter(Pred cate<Seg nt nfo> pred cate) {
      t .pred cate = pred cate;
    }

    pr vate stat c f nal Map<Str ng, F lter> NAME_ NDEX =
        Maps.newHashMapW hExpectedS ze(F lter.values().length);

    stat c {
      for (F lter f lter : F lter.values()) {
        NAME_ NDEX.put(f lter.na ().toLo rCase(), f lter);
      }
    }

    /**
     * Parses t  f lter from t  g ven str ng, based on t  f lter na .
     */
    publ c stat c F lter fromStr ng gnoreCase(Str ng str) {
       f (str == null) {
        return null;
      }

      return NAME_ NDEX.get(str.toLo rCase());
    }
  }

  publ c enum Order {
    OLD_TO_NEW,
    NEW_TO_OLD,
  }

  /**
   * A l stener that gets not f ed w n t  l st of seg nts changes.
   */
  publ c  nterface Seg ntUpdateL stener {
    /**
     * Called w h t  new l st of seg nts w n   changes.
     *
     * @param seg nts T  new l st of seg nts.
     */
    vo d update(Collect on<Seg nt nfo> seg nts, Str ng  ssage);
  }

  pr vate f nal L st<Seg ntUpdateL stener> updateL steners =
          Collect ons.synchron zedL st(L sts.newL nkedL st());

  pr vate f nal ConcurrentSk pL stMap<Long,  Seg ntWr er> seg ntWr ers =
      new ConcurrentSk pL stMap<>();

  pr vate f nal Set<Long> badT  sl ce ds = new HashSet<>();

  pr vate f nal  nt maxEnabledSeg nts;
  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal Earlyb rdSeg ntFactory earlyb rdSeg ntFactory;
  pr vate f nal UserTable userTable;
  pr vate f nal UserScrubGeoMap userScrubGeoMap;
  pr vate f nal Earlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal Dynam cPart  onConf g dynam cPart  onConf g;
  pr vate f nal UserUpdatesC cker userUpdatesC cker;
  pr vate f nal Seg ntSyncConf g seg ntSyncConf g;
  pr vate f nal Earlyb rdSearc rStats searc rStats;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  pr vate f nal Cr  calExcept onHandler cr  calExcept onHandler;
  pr vate f nal CaughtUpMon or  ndexCaughtUpMon or;

  publ c Seg ntManager(
      Dynam cPart  onConf g dynam cPart  onConf g,
      Earlyb rd ndexConf g earlyb rd ndexConf g,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Earlyb rdSearc rStats searc rStats,
      SearchStatsRece ver earlyb rdStatsRece ver,
      UserUpdatesC cker userUpdatesC cker,
      Seg ntSyncConf g seg ntSyncConf g,
      UserTable userTable,
      UserScrubGeoMap userScrubGeoMap,
      Clock clock,
       nt maxSeg ntS ze,
      Cr  calExcept onHandler cr  calExcept onHandler,
      CaughtUpMon or  ndexCaughtUpMon or) {

    Part  onConf g curPart  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();

    t .userTable = userTable;
    t .userScrubGeoMap = userScrubGeoMap;

    t .earlyb rdSeg ntFactory = new Earlyb rdSeg ntFactory(
        earlyb rd ndexConf g,
        search ndex ng tr cSet,
        searc rStats,
        clock);
    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    t .maxEnabledSeg nts = curPart  onConf g.getMaxEnabledLocalSeg nts();
    t .dynam cPart  onConf g = dynam cPart  onConf g;
    t .userUpdatesC cker = userUpdatesC cker;
    t .seg ntSyncConf g = seg ntSyncConf g;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .searc rStats = searc rStats;
    t .clock = clock;
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    t . ndexCaughtUpMon or =  ndexCaughtUpMon or;

    earlyb rdStatsRece ver.getCustomGauge("total_loaded_seg nts",
        seg ntWr ers::s ze);
    earlyb rdStatsRece ver.getCustomGauge("total_ ndexed_docu nts",
        t ::getNum ndexedDocu nts);
    earlyb rdStatsRece ver.getCustomGauge("total_seg nt_s ze_bytes",
        t ::getTotalSeg ntS zeOnD sk);
    earlyb rdStatsRece ver.getCustomGauge("earlyb rd_ ndex_depth_m ll s",
        t ::get ndexDepthM ll s);
  }

  /**
   * Logs t  current state of t  seg nt manager.
   *
   * @param label A label that should  dent fy t  seg nt manager.
   */
  publ c vo d logState(Str ng label) {
    Str ngBu lder sb = new Str ngBu lder();
    sb.append("State of Seg ntManager (" + label + "):\n");
    sb.append("Number of seg nts: " + seg ntWr ers.s ze());
    boolean hasSeg nts = false;
    for (Map.Entry<Long,  Seg ntWr er> entry : t .seg ntWr ers.entrySet()) {
      Seg nt nfo seg nt nfo = entry.getValue().getSeg nt nfo();
      hasSeg nts = true;

      sb.append(Str ng.format("\nSeg nt (%s):  sClosed: %5s,  sComplete: %5s, "
              + " sEnabled: %5s,  s ndex ng: %5s,  sOpt m zed: %5s, was ndexed: %5s",
          seg nt nfo.getSeg ntNa (),
          seg nt nfo. sClosed(),
          seg nt nfo. sComplete(),
          seg nt nfo. sEnabled(),
          seg nt nfo. s ndex ng(),
          seg nt nfo. sOpt m zed(),
          seg nt nfo.was ndexed()
      ));

      sb.append(Str ng.format(" |  ndex stats: %s", seg nt nfo.get ndexStats().toStr ng()));
    }
     f (!hasSeg nts) {
      sb.append(" No seg nts.");
    }
    LOG. nfo(sb.toStr ng());
  }


  publ c Part  onConf g getPart  onConf g() {
    return dynam cPart  onConf g.getCurrentPart  onConf g();
  }

  publ c  nt getMaxEnabledSeg nts() {
    return maxEnabledSeg nts;
  }

  publ c Earlyb rdSeg ntFactory getEarlyb rdSeg ntFactory() {
    return earlyb rdSeg ntFactory;
  }

  publ c Earlyb rd ndexConf g getEarlyb rd ndexConf g() {
    return earlyb rd ndexConf g;
  }

  publ c UserTable getUserTable() {
    return userTable;
  }

  publ c UserScrubGeoMap getUserScrubGeoMap() {
    return userScrubGeoMap;
  }

  @V s bleForTest ng
  publ c vo d reset() {
    seg ntWr ers.clear();
  }

  /**
   * Returns t  l st of all seg nts that match t  g ven f lter,  n t  g ven order.
   */
  publ c  erable<Seg nt nfo> getSeg nt nfos(F lter f lter, Order order) {
    Comparator<Seg nt nfo> comparator;

     f (order == Order.OLD_TO_NEW) {
      comparator = Comparator.naturalOrder();
    } else {
      comparator = Comparator.reverseOrder();
    }

    return () -> seg ntWr ers.values().stream()
        .map( Seg ntWr er::getSeg nt nfo)
        .f lter(f lter.pred cate::apply)
        .sorted(comparator)
        . erator();
  }

  pr vate vo d createAndPutSeg nt nfo(Seg nt seg nt) throws  OExcept on {
    LOG. nfo("Creat ng new Seg nt nfo for seg nt " + seg nt.getSeg ntNa ());
    putSeg nt nfo(new Seg nt nfo(seg nt, earlyb rdSeg ntFactory, seg ntSyncConf g));
  }

  /**
   * Updates t  l st of seg nts managed by t  manager, based on t  g ven l st.
   */
  publ c vo d updateSeg nts(L st<Seg nt> seg ntsL st) throws  OExcept on {
    // Truncate to t  amount of seg nts   want to keep enabled.
    L st<Seg nt> truncatedSeg ntL st =
        Seg ntManager.truncateSeg ntL st(seg ntsL st, maxEnabledSeg nts);

    f nal long ne stT  Sl ce D = getNe stT  Sl ce D();
    f nal Set<Long> seg ntsToD sable = new HashSet<>(seg ntWr ers.keySet());

    for (Seg nt seg nt : truncatedSeg ntL st) {
      f nal long t  Sl ce D = seg nt.getT  Sl ce D();
      seg ntsToD sable.remove(t  Sl ce D);

      // On t  f rst loop  erat on of t  f rst call to updateSeg nts(), ne stT  Sl ce D should
      // be set to -1, so t  cond  on should be false. After that, all seg nts should e  r be
      // ne r than t  latest process seg nt, or  f  're replac ng an old seg nt,   should have
      // a Seg nt nfo  nstance assoc ated w h  .
       f (t  Sl ce D <= ne stT  Sl ce D) {
         Seg ntWr er seg ntWr er = seg ntWr ers.get(t  Sl ce D);
        // Old t   sl ce  D.   should have a Seg nt nfo  nstance assoc ated w h  .
         f (seg ntWr er == null) {
           f (!badT  sl ce ds.conta ns(t  Sl ce D)) {
            //  're deal ng w h a bad t  sl ce. Log an error, but do   only once per t  sl ce.
            LOG.error("T  Seg nt nfo  nstance assoc ated w h an old t  Sl ce D should never be "
                      + "null. T  Sl ce D: {}", t  Sl ce D);
            badT  sl ce ds.add(t  Sl ce D);
          }
        } else  f (seg ntWr er.getSeg nt nfo(). sClosed()) {
          //  f t  Seg nt nfo was closed, create a new one.
          LOG. nfo("Seg nt nfo for seg nt {}  s closed.", seg nt.getSeg ntNa ());
          createAndPutSeg nt nfo(seg nt);
        }
      } else {
        // New t   sl ce  D: create a Seg nt nfo  nstance for  .
        createAndPutSeg nt nfo(seg nt);
      }
    }

    // Anyth ng   d dn't see locally can be d sabled.
    for (Long seg nt D : seg ntsToD sable) {
      d sableSeg nt(seg nt D);
    }

    // Update seg nt stats and ot r exported var ables.
    updateStats();
  }

  /**
   * Re-export stats after a seg nt has changed, or t  set of seg nts has changed.
   */
  publ c vo d updateStats() {
    // Update t  part  on count stats.
    SEGMENT_COUNT_STATS.set(seg ntWr ers.s ze());

    OPT M ZED_SEGMENTS.reset();
    UNOPT M ZED_SEGMENTS.reset();
    for ( Seg ntWr er wr er : seg ntWr ers.values()) {
       f (wr er.getSeg nt nfo(). sOpt m zed()) {
        OPT M ZED_SEGMENTS. ncre nt();
      } else {
        UNOPT M ZED_SEGMENTS. ncre nt();
      }
    }
  }

  pr vate long get ndexDepthM ll s() {
    long oldestT  Sl ce D = getOldestEnabledT  Sl ce D();
     f (oldestT  Sl ce D == Seg nt nfo. NVAL D_ D) {
      return 0;
    } else {
      // Compute t  stamp from t  sl ce d, wh ch  s also a snowflake t et d
      long t  stamp = Snowflake dParser.getT  stampFromT et d(oldestT  Sl ce D);
      // Set current  ndex depth  n m ll seconds
      long  ndexDepth nM ll s = System.currentT  M ll s() - t  stamp;
      //  ndex depth should never be negat ve.
       f ( ndexDepth nM ll s < 0) {
        LOG.warn("Negat ve  ndex depth. Large t   skew on t  Earlyb rd?");
        return 0;
      } else {
        return  ndexDepth nM ll s;
      }
    }
  }

  pr vate vo d updateExportedSeg ntStats() {
     nt  ndex = 0;
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.Enabled, Order.NEW_TO_OLD)) {
      Seg nt ndexStatsExporter.export(seg nt nfo,  ndex++);
    }
  }

  // Marks t  Seg nt nfo object match ng t  t   sl ce as d sabled.
  pr vate vo d d sableSeg nt(long t  Sl ce D) {
    Seg nt nfo  nfo = getSeg nt nfo(t  Sl ce D);
     f ( nfo == null) {
      LOG.warn("Tr ed to d sable m ss ng seg nt " + t  Sl ce D);
      return;
    }
     nfo.set sEnabled(false);
    LOG. nfo("D sabled seg nt " +  nfo);
  }

  publ c long getNe stT  Sl ce D() {
    f nal  erator<Seg nt nfo> seg nts = getSeg nt nfos(F lter.All, Order.NEW_TO_OLD). erator();
    return seg nts.hasNext() ? seg nts.next().getT  Sl ce D() : Seg nt nfo. NVAL D_ D;
  }

  /**
   * Returns t  t  sl ce  D of t  oldest enabled seg nt.
   */
  publ c long getOldestEnabledT  Sl ce D() {
     f (seg ntWr ers.s ze() == 0) {
      return Seg nt nfo. NVAL D_ D;
    }
     Seg ntWr er seg ntWr er = seg ntWr ers.f rstEntry().getValue();
    return seg ntWr er.getSeg nt nfo().getT  Sl ce D();
  }

  /**
   * Returns t  Seg nt nfo for t  g ven t  Sl ce D.
   */
  publ c f nal Seg nt nfo getSeg nt nfo(long t  Sl ce D) {
     Seg ntWr er seg ntWr er = seg ntWr ers.get(t  Sl ce D);
    return seg ntWr er == null ? null : seg ntWr er.getSeg nt nfo();
  }

  /**
   * Returns t  seg nt  nfo for t  seg nt that should conta n t  g ven t et  D.
   */
  publ c f nal Seg nt nfo getSeg nt nfoFromStatus D(long t et D) {
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.All, Order.NEW_TO_OLD)) {
       f (t et D >= seg nt nfo.getT  Sl ce D()) {
        return seg nt nfo;
      }
    }

    return null;
  }

  /**
   * Removes t  seg nt assoc ated w h t  g ven t  sl ce  D from t  seg nt manager. T  w ll
   * also take care of all requ red clean up related to t  seg nt be ng removed, such as clos ng
   *  s wr er.
   */
  publ c boolean removeSeg nt nfo(long t  Sl ce D) {
     f (t  Sl ce D == getNe stT  Sl ce D()) {
      throw new Runt  Except on("Cannot drop seg nt of current t  -sl ce " + t  Sl ce D);
    }

     Seg ntWr er removed = seg ntWr ers.get(t  Sl ce D);
     f (removed == null) {
      return false;
    }

    LOG. nfo("Remov ng seg nt {}", removed.getSeg nt nfo());
    Precond  ons.c ckState(!removed.getSeg nt nfo(). sEnabled());
    removed.getSeg nt nfo().get ndexSeg nt().close();
    seg ntWr ers.remove(t  Sl ce D);

    Str ng seg ntNa  = removed.getSeg nt nfo().getSeg ntNa ();
    updateAllL steners("Removed seg nt " + seg ntNa );
    LOG. nfo("Removed seg nt " + seg ntNa );
    updateExportedSeg ntStats();
    updateStats();
    return true;
  }

  /**
   * Add t  g ven Seg ntWr er  nto t  seg ntWr ers map.
   *  f a seg nt w h t  sa  t  sl ce D already ex sts  n t  map, t  old one  s replaced
   * w h t  new one; t  should only happen  n t  arch ve.
   *
   * T  replaced seg nt  s destroyed after a delay to allow  n-fl ght requests to f n sh.
   */
  publ c  Seg ntWr er putSeg nt nfo(Seg nt nfo  nfo) {
     Seg ntWr er usedSeg ntWr er;

    Seg ntWr er seg ntWr er
        = new Seg ntWr er( nfo, search ndex ng tr cSet.updateFreshness);

     f (! nfo. sOpt m zed()) {
      LOG. nfo(" nsert ng an opt m z ng seg nt wr er for seg nt: {}",
           nfo.getSeg ntNa ());

      usedSeg ntWr er = new Opt m z ngSeg ntWr er(
          seg ntWr er,
          cr  calExcept onHandler,
          search ndex ng tr cSet,
           ndexCaughtUpMon or);
    } else {
      usedSeg ntWr er = seg ntWr er;
    }

    putSeg ntWr er(usedSeg ntWr er);
    return usedSeg ntWr er;
  }

  pr vate vo d putSeg ntWr er( Seg ntWr er seg ntWr er) {
    Seg nt nfo newSeg nt nfo = seg ntWr er.getSeg nt nfo();
    Seg nt nfo oldSeg nt nfo = getSeg nt nfo(newSeg nt nfo.getT  Sl ce D());

    // So  san y c cks.
     f (oldSeg nt nfo != null) {
      // T  map  s thread safe, so t  put can be cons dered atom c.
      seg ntWr ers.put(newSeg nt nfo.getT  Sl ce D(), seg ntWr er);
      LOG. nfo("Replaced Seg nt nfo w h a new one  n seg ntWr ers map. "
          + "Old Seg nt nfo: {} New Seg nt nfo: {}", oldSeg nt nfo, newSeg nt nfo);

       f (!oldSeg nt nfo. sClosed()) {
        oldSeg nt nfo.delete ndexSeg ntD rectoryAfterDelay();
      }
    } else {
      long ne stT  Sl ce D = getNe stT  Sl ce D();
       f (ne stT  Sl ce D != Seg nt nfo. NVAL D_ D
          && ne stT  Sl ce D > newSeg nt nfo.getT  Sl ce D()) {
        LOG.error("Not add ng out-of-order seg nt " + newSeg nt nfo);
        return;
      }

      seg ntWr ers.put(newSeg nt nfo.getT  Sl ce D(), seg ntWr er);
      LOG. nfo("Added seg nt " + newSeg nt nfo);
    }

    updateAllL steners("Added seg nt " + newSeg nt nfo.getT  Sl ce D());
    updateExportedSeg ntStats();
    updateStats();
  }

  pr vate Seg nt nfo createSeg nt nfo(long t  sl ce D) throws  OExcept on {
    Part  onConf g part  onConf g = dynam cPart  onConf g.getCurrentPart  onConf g();

    T  Sl ce t  Sl ce = new T  Sl ce(
        t  sl ce D,
        maxSeg ntS ze,
        part  onConf g.get ndex ngHashPart  on D(),
        part  onConf g.getNumPart  ons());

    Seg nt nfo seg nt nfo =
        new Seg nt nfo(t  Sl ce.getSeg nt(), earlyb rdSeg ntFactory, seg ntSyncConf g);

    return seg nt nfo;
  }

  /**
   * Create a new opt m z ng seg nt wr er and add   to t  map.
   */
  publ c Opt m z ngSeg ntWr er createAndPutOpt m z ngSeg ntWr er(
      long t  sl ce D) throws  OExcept on {
    Seg nt nfo seg nt nfo = createSeg nt nfo(t  sl ce D);

    Opt m z ngSeg ntWr er wr er = new Opt m z ngSeg ntWr er(
        new Seg ntWr er(seg nt nfo, search ndex ng tr cSet.updateFreshness),
        cr  calExcept onHandler,
        search ndex ng tr cSet,
         ndexCaughtUpMon or);

    putSeg ntWr er(wr er);
    return wr er;
  }

  /**
   * Create a new seg nt wr er.
   */
  publ c Seg ntWr er createSeg ntWr er(long t  sl ce D) throws  OExcept on {
    Seg nt nfo seg nt nfo = createSeg nt nfo(t  sl ce D);

    Seg ntWr er wr er = new Seg ntWr er(
        seg nt nfo, search ndex ng tr cSet.updateFreshness);

    return wr er;
  }

  pr vate vo d updateAllL steners(Str ng  ssage) {
    L st<Seg nt nfo> seg nt nfos = seg ntWr ers.values().stream()
        .map( Seg ntWr er::getSeg nt nfo)
        .collect(Collectors.toL st());
    for (Seg ntUpdateL stener l stener : updateL steners) {
      try {
        l stener.update(seg nt nfos,  ssage);
      } catch (Except on e) {
        LOG.warn("Seg ntManager: Unable to call update() on l stener.", e);
      }
    }
  }

  // Returns true  f t  map conta ns a Seg nt nfo match ng t  g ven t   sl ce.
  publ c f nal boolean hasSeg nt nfo(long t  Sl ce D) {
    return seg ntWr ers.conta nsKey(t  Sl ce D);
  }

  publ c vo d addUpdateL stener(Seg ntUpdateL stener l stener) {
    updateL steners.add(l stener);
  }

  /**
   * Look up t  seg nt conta n ng t  g ven status  d.
   *  f found,  s t  sl ce  d  s returned.
   *  f none found, -1  s returned.
   */
  publ c long lookupT  Sl ce D(long status D) throws  OExcept on {
    Seg nt nfo seg nt nfo = getSeg nt nfoFor D(status D);
     f (seg nt nfo == null) {
      return -1;
    }
     f (!seg nt nfo.get ndexSeg nt().hasDocu nt(status D)) {
        return -1;
    }

    return seg nt nfo.getT  Sl ce D();
  }

  /**
   * Truncates t  g ven seg nt l st to t  spec f ed number of seg nts, by keep ng t  ne st
   * seg nts.
   */
  @V s bleForTest ng
  publ c stat c L st<Seg nt> truncateSeg ntL st(L st<Seg nt> seg ntL st,  nt maxNumSeg nts) {
    // Maybe cut-off t  beg nn ng of t  sorted l st of  Ds.
     f (maxNumSeg nts > 0 && maxNumSeg nts < seg ntL st.s ze()) {
      return seg ntL st.subL st(seg ntL st.s ze() - maxNumSeg nts, seg ntL st.s ze());
    } else {
      return seg ntL st;
    }
  }

  @V s bleForTest ng
  publ c vo d setOffens ve(long user D, boolean offens ve) {
    userTable.setOffens ve(user D, offens ve);
  }

  @V s bleForTest ng
  publ c vo d setAnt soc al(long user D, boolean ant soc al) {
    userTable.setAnt soc al(user D, ant soc al);
  }

  /**
   * Returns a searc r for all seg nts.
   */
  publ c Earlyb rdMult Seg ntSearc r getMult Searc r( mmutableSc ma nterface sc maSnapshot)
      throws  OExcept on {
    return new Earlyb rdMult Seg ntSearc r(
        sc maSnapshot,
        getSearc rs(sc maSnapshot, F lter.All, Order.NEW_TO_OLD),
        searc rStats,
        clock);
  }

  /**
   * Returns a new searc r for t  g ven seg nt.
   */
  @Nullable
  publ c Earlyb rdLuceneSearc r getSearc r(
      Seg nt seg nt,
       mmutableSc ma nterface sc maSnapshot) throws  OExcept on {
    return getSearc r(seg nt.getT  Sl ce D(), sc maSnapshot);
  }

  /**
   * Get max t et  d across all enabled seg nts.
   * @return max t et  d or -1  f none found
   */
  publ c long getMaxT et dFromEnabledSeg nts() {
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.Enabled, Order.NEW_TO_OLD)) {
      long maxT et d = seg nt nfo.get ndexSeg nt().getMaxT et d();
       f (maxT et d != -1) {
        return maxT et d;
      }
    }

    return -1;
  }

  /**
   * Create a t et  ndex searc r on t  seg nt represented by t  t  sl ce  d.  For product on
   * search sess on, t  sc ma snapshot should be always passed  n to make sure that t  sc ma
   * usage  ns de scor ng  s cons stent.
   *
   * For non-product on usage, l ke one-off debugg ng search,   can use t  funct on call w hout
   * t  sc ma snapshot.
   *
   * @param t  Sl ce D t  t  sl ce  d, wh ch represents t   ndex seg nt
   * @param sc maSnapshot t  sc ma snapshot
   * @return t  t et  ndex searc r
   */
  @Nullable
  publ c Earlyb rdS ngleSeg ntSearc r getSearc r(
      long t  Sl ce D,
       mmutableSc ma nterface sc maSnapshot) throws  OExcept on {
    Seg nt nfo seg nt nfo = getSeg nt nfo(t  Sl ce D);
     f (seg nt nfo == null) {
      return null;
    }
    return seg nt nfo.get ndexSeg nt().getSearc r(userTable, sc maSnapshot);
  }

  /**
   * Returns a new searc r for t  seg nt w h t  g ven t  sl ce  D.  f t  g ven t  sl ce  D
   * does not correspond to any act ve seg nt, {@code null}  s returned.
   *
   * @param t  Sl ce D T  seg nt's t  sl ce  D.
   * @return A new searc r for t  seg nt w h t  g ven t  sl ce  D.
   */
  @Nullable
  publ c Earlyb rdS ngleSeg ntSearc r getSearc r(long t  Sl ce D) throws  OExcept on {
    Seg nt nfo seg nt nfo = getSeg nt nfo(t  Sl ce D);
     f (seg nt nfo == null) {
      return null;
    }
    return seg nt nfo.get ndexSeg nt().getSearc r(userTable);
  }

  @Nullable
  publ c Earlyb rdResponseCode c ckSeg nt(Seg nt seg nt) {
    return c ckSeg nt nternal(getSeg nt nfo(seg nt.getT  Sl ce D()));
  }

  pr vate stat c Earlyb rdResponseCode c ckSeg nt nternal(Seg nt nfo  nfo) {
     f ( nfo == null) {
      return Earlyb rdResponseCode.PART T ON_NOT_FOUND;
    } else  f ( nfo. sEnabled()) {
      return Earlyb rdResponseCode.SUCCESS;
    } else {
      return Earlyb rdResponseCode.PART T ON_D SABLED;
    }
  }

  pr vate L st<Earlyb rdS ngleSeg ntSearc r> getSearc rs(
       mmutableSc ma nterface sc maSnapshot,
      F lter f lter,
      Order order) throws  OExcept on {
    L st<Earlyb rdS ngleSeg ntSearc r> searc rs = L sts.newArrayL st();
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(f lter, order)) {
      Earlyb rdS ngleSeg ntSearc r searc r =
          seg nt nfo.get ndexSeg nt().getSearc r(userTable, sc maSnapshot);
       f (searc r != null) {
        searc rs.add(searc r);
      }
    }
    return searc rs;
  }

  /**
   * Gets  tadata for seg nts for debugg ng purposes.
   */
  publ c L st<Str ng> getSeg nt tadata() {
    L st<Str ng> seg nt tadata = new ArrayL st<>();
    for (Seg nt nfo seg nt : getSeg nt nfos(F lter.All, Order.OLD_TO_NEW)) {
      seg nt tadata.add(seg nt.getSeg nt tadata());
    }
    return seg nt tadata;
  }

  /**
   * Gets  nfo for query cac s to be d splayed  n an adm n page.
   */
  publ c Str ng getQueryCac sData() {
    Str ngBu lder output = new Str ngBu lder();
    for (Seg nt nfo seg nt : getSeg nt nfos(F lter.All, Order.OLD_TO_NEW)) {
      output.append(seg nt.getQueryCac sData() + "\n");
    }
    return output.toStr ng();
  }

  /**
   *  ndex t  g ven user update. Returns false  f t  g ven update  s sk pped.
   */
  publ c boolean  ndexUserUpdate(UserUpdate userUpdate) {
    return userTable. ndexUserUpdate(userUpdatesC cker, userUpdate);
  }

  /**
   *  ndex t  g ven UserScrubGeoEvent.
   * @param userScrubGeoEvent
   */
  publ c vo d  ndexUserScrubGeoEvent(UserScrubGeoEvent userScrubGeoEvent) {
    userScrubGeoMap. ndexUserScrubGeoEvent(userScrubGeoEvent);
  }

  /**
   * Return how many docu nts t  seg nt manager has  ndexed  n all of  s enabled seg nts.
   */
  publ c long getNum ndexedDocu nts() {
    // Order  re doesn't matter,   just want all enabled seg nts, and allocate
    // as l tle as needed.
    long  ndexedDocs = 0;
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.Enabled, Order.OLD_TO_NEW)) {
       ndexedDocs += seg nt nfo.get ndexSeg nt().get ndexStats().getStatusCount();
    }
    return  ndexedDocs;
  }

  /**
   * Return how many part al updates t  seg nt manager has appl ed
   *  n all of  s enabled seg nts.
   */
  publ c long getNumPart alUpdates() {
    long part alUpdates = 0;
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.Enabled, Order.OLD_TO_NEW)) {
      part alUpdates += seg nt nfo.get ndexSeg nt().get ndexStats().getPart alUpdateCount();
    }
    return part alUpdates;
  }

  /**
   * Returns t  seg nt  nfo for t  seg nt conta n ng t  g ven t et  D.
   */
  publ c Seg nt nfo getSeg nt nfoFor D(long t et D) {
     Seg ntWr er seg ntWr er = getSeg ntWr erFor D(t et D);
    return seg ntWr er == null ? null : seg ntWr er.getSeg nt nfo();
  }

  /**
   * Returns t  seg nt wr er for t  seg nt conta n ng t  g ven t et  D.
   */
  @Nullable
  publ c  Seg ntWr er getSeg ntWr erFor D(long t et D) {
    Map.Entry<Long,  Seg ntWr er> entry = seg ntWr ers.floorEntry(t et D);
    return entry == null ? null : entry.getValue();
  }

  /**
   * Remove old seg nts unt l   have less than or equal to t  number of max enabled seg nts.
   */
  publ c vo d removeExcessSeg nts() {
     nt removedSeg ntCount = 0;
    wh le (seg ntWr ers.s ze() > getMaxEnabledSeg nts()) {
      long t  sl ce D = getOldestEnabledT  Sl ce D();
      d sableSeg nt(t  sl ce D);
      removeSeg nt nfo(t  sl ce D);
      removedSeg ntCount += 1;
    }
    LOG. nfo("Seg nt manager removed {} excess seg nts", removedSeg ntCount);
  }

  /**
   * Returns total  ndex s ze on d sk across all enabled seg nts  n t  seg nt manager.
   */
  pr vate long getTotalSeg ntS zeOnD sk() {
    long total ndexS ze = 0;
    for (Seg nt nfo seg nt nfo : getSeg nt nfos(F lter.Enabled, Order.OLD_TO_NEW)) {
      total ndexS ze += seg nt nfo.get ndexSeg nt().get ndexStats().get ndexS zeOnD sk nBytes();
    }
    return total ndexS ze;
  }

  @V s bleForTest ng
   Seg ntWr er getSeg ntWr erW houtCreat onForTests(long t  sl ce D) {
    return seg ntWr ers.get(t  sl ce D);
  }

  @V s bleForTest ng
  ArrayL st<Long> getT  Sl ce dsForTests() {
    return new ArrayL st<Long>(seg ntWr ers.keySet());
  }
}
