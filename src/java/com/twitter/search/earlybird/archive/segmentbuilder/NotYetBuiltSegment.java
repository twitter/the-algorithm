package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java.ut l.concurrent.atom c.Atom cBoolean;

 mport com.google.common.base.Stopwatch;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.ut l.GCUt l;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSeg ntUpdater;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;

publ c class NotYetBu ltSeg nt extends Seg ntBu lderSeg nt {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(NotYetBu ltSeg nt.class);

  publ c NotYetBu ltSeg nt(
      Seg nt nfo seg nt nfo,
      Seg ntConf g seg ntConf g,
      Earlyb rdSeg ntFactory earlyb rdSeg ntFactory,
       nt alreadyRetr edCount,
      Seg ntSyncConf g sync) {

    super(seg nt nfo, seg ntConf g, earlyb rdSeg ntFactory, alreadyRetr edCount, sync);
  }

  /**
   * 1. Grab t  ZK lock for t  seg nt.
   *   2a.  f lock fa ls, anot r host  s updat ng; return t  SOMEONE_ELSE_ S_BU LD NG state.
   *   2b.  f lock succeeds, c ck aga n  f t  updated seg nt ex sts on HDFS.
   *     3a.  f so, just move on.
   *     3b.  f not, update t  seg nt.
   *      n both cases,   need to c ck  f t  seg nt can now be marked as BU LT_AND_F NAL ZED.
   */
  @Overr de
  publ c Seg ntBu lderSeg nt handle()
      throws Seg ntUpdaterExcept on, Seg nt nfoConstruct onExcept on {
    LOG. nfo("Handl ng a not yet bu lt seg nt: {}", t .getSeg ntNa ());
    Stopwatch stopwatch = Stopwatch.createStarted();
    TryLock lock = getZooKeeperTryLock();

    // T  tryW hLock can only access var ables from parent class that are f nal. Ho ver,  
    // would l ke to pass t  process() return value to t  parent class. So  re   use
    // Atom cBoolean reference  nstead of Boolean.
    f nal Atom cBoolean successRef = new Atom cBoolean(false);
    boolean gotLock = lock.tryW hLock(() -> {
      Arch veSeg ntUpdater updater = new Arch veSeg ntUpdater(
          seg ntConf g.getTryLockFactory(),
          sync,
          seg ntConf g.getEarlyb rd ndexConf g(),
          Clock.SYSTEM_CLOCK);

      boolean success = updater.updateSeg nt(seg nt nfo);
      successRef.set(success);
    });

     f (!gotLock) {
      LOG. nfo("cannot acqu re zookeeper lock for: " + seg nt nfo);
      return new So oneElse sBu ld ngSeg nt(
          seg nt nfo,
          seg ntConf g,
          earlyb rdSeg ntFactory,
          alreadyRetr edCount,
          sync);
    }

    // 1.   want to make sure t   ap  s clean r ght after bu ld ng a seg nt so that  's ready
    //   for us to start allocat ons for a new seg nt
    // —   th nk  've had cases w re    re see ng OOM's wh le bu ld ng
    // 2. t  th ng that   th nk    lps w h  s compact on (vs just organ cally runn ng CMS)
    // — wh ch would clean up t   ap, but may leave    n a frag nted state
    // — and runn ng a Full GC  s supposed to compact t  rema n ng tenured space.
    GCUt l.runGC();

     f (successRef.get()) {
      LOG. nfo(" ndex ng seg nt {} took {}", seg nt nfo, stopwatch);
      LOG. nfo("F n s d bu ld ng {}", seg nt nfo.getSeg nt().getSeg ntNa ());
      return new Bu ltAndF nal zedSeg nt(
          seg nt nfo, seg ntConf g, earlyb rdSeg ntFactory, 0, sync);
    } else {
       nt alreadyTr ed = alreadyRetr edCount + 1;
      Str ng errMsg = "fa led updat ng seg nt for: " + seg nt nfo
          + " for " + alreadyTr ed + " t  s";
      LOG.error(errMsg);
       f (alreadyTr ed < seg ntConf g.getMaxRetr esOnFa lure()) {
        return new NotYetBu ltSeg nt(
            createNewSeg nt nfo(seg nt nfo),
            seg ntConf g,
            earlyb rdSeg ntFactory,
            alreadyTr ed,
            sync);
      } else {
        throw new Seg ntUpdaterExcept on(errMsg);
      }
    }
  }
}
