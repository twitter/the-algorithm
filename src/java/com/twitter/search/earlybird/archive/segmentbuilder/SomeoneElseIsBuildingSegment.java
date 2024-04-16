package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java.ut l.concurrent.atom c.Atom cBoolean;

 mport com.google.common.annotat ons.V s bleForTest ng;

 mport com.tw ter.common.base.Command;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veHDFSUt ls;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;

publ c class So oneElse sBu ld ngSeg nt extends Seg ntBu lderSeg nt {
  publ c So oneElse sBu ld ngSeg nt(
      Seg nt nfo seg nt nfo,
      Seg ntConf g seg ntConf g,
      Earlyb rdSeg ntFactory earlyb rdSeg ntFactory,
       nt alreadyRetr edCount,
      Seg ntSyncConf g sync) {

    super(seg nt nfo, seg ntConf g, earlyb rdSeg ntFactory, alreadyRetr edCount, sync);
  }

  /**
   * T   thod refres s local state of a seg nt.
   * 1. Try to grab t  ZK lock
   *   2a.  f got t  lock, t  seg nt  s not be ng bu lt; mark seg nt as NOT_BU LT_YET.
   *   2b. ot rw se, t  seg nt  s be ng bu lt; keep t  SOMEONE_ELSE_ S_BU LD NG state
   */
  @Overr de
  publ c Seg ntBu lderSeg nt handle()
      throws Seg nt nfoConstruct onExcept on, Seg ntUpdaterExcept on {

    TryLock lock = getZooKeeperTryLock();

    f nal Atom cBoolean alreadyBu lt = new Atom cBoolean(false);
    boolean gotLock = lock.tryW hLock((Command) () -> {
      // T  seg nt m ght have already f n s d bu lt by ot rs
       f (seg ntEx stsOnHdfs()) {
        alreadyBu lt.set(true);
      }
    });

     f (!gotLock) {
      return t ;
    }

     f (alreadyBu lt.get()) {
      return new Bu ltAndF nal zedSeg nt(
          seg nt nfo, seg ntConf g, earlyb rdSeg ntFactory, 0, sync);
    } else {
      // W n a seg nt fa led bu ld ng,  s state m ght not be clean. So,    s necessary to
      // create a new Seg nt nfo w h a clean state
      Seg nt nfo newSeg nt nfo = createNewSeg nt nfo(seg nt nfo);
      return new NotYetBu ltSeg nt(
          newSeg nt nfo,
          seg ntConf g,
          earlyb rdSeg ntFactory,
          alreadyRetr edCount + 1,
          sync);
    }
  }

  @V s bleForTest ng
  boolean seg ntEx stsOnHdfs() {
    return Arch veHDFSUt ls.hasSeg nt nd cesOnHDFS(sync, seg nt nfo);
  }
}
