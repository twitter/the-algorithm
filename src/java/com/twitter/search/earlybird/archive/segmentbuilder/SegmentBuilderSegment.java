package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.search.common.database.DatabaseConf g;
 mport com.tw ter.search.common.ut l.zktrylock.TryLock;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veSeg nt;
 mport com.tw ter.search.earlyb rd.common.conf g.Earlyb rdConf g;
 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;

publ c abstract class Seg ntBu lderSeg nt {
  protected f nal Seg nt nfo seg nt nfo;
  protected f nal Seg ntConf g seg ntConf g;
  protected f nal Earlyb rdSeg ntFactory earlyb rdSeg ntFactory;
  protected f nal  nt alreadyRetr edCount;
  protected f nal Seg ntSyncConf g sync;

  publ c Seg ntBu lderSeg nt(Seg nt nfo seg nt nfo,
                               Seg ntConf g seg ntConf g,
                               Earlyb rdSeg ntFactory earlyb rdSeg ntFactory,
                                nt alreadyRetr edCount,
                               Seg ntSyncConf g seg ntSyncConf g) {
    t .seg ntConf g = seg ntConf g;
    t .earlyb rdSeg ntFactory = earlyb rdSeg ntFactory;
    t .alreadyRetr edCount = alreadyRetr edCount;
    t .sync = seg ntSyncConf g;
    Precond  ons.c ckState(seg nt nfo.getSeg nt()  nstanceof Arch veSeg nt);
    t .seg nt nfo = Precond  ons.c ckNotNull(seg nt nfo);
  }

  publ c Seg nt nfo getSeg nt nfo() {
    return seg nt nfo;
  }

  publ c Str ng getSeg ntNa () {
    return seg nt nfo.getSeg ntNa ();
  }

  publ c  nt getAlreadyRetr edCount() {
    return alreadyRetr edCount;
  }

  /**
   * Handle t  seg nt, potent ally trans  on ng to a new state.
   * @return T  state after handl ng.
   */
  publ c abstract Seg ntBu lderSeg nt handle()
      throws Seg nt nfoConstruct onExcept on, Seg ntUpdaterExcept on;

  publ c boolean  sBu lt() {
    return false;
  }

  @Overr de
  publ c Str ng toStr ng() {
    return "Seg ntBu lderSeg nt{"
        + "seg nt nfo=" + seg nt nfo
        + ", state=" + t .getClass().getS mpleNa ()
        + ", alreadyRetr edCount=" + alreadyRetr edCount + '}';
  }

  /**
   * G ven a Seg nt nfo, create a new one w h t  sa  t   sl ce and part  on D but clean
   *  nternal state.
   */
  protected Seg nt nfo createNewSeg nt nfo(Seg nt nfo oldSeg nt nfo)
      throws Seg nt nfoConstruct onExcept on {
    Precond  ons.c ckArgu nt(oldSeg nt nfo.getSeg nt()  nstanceof Arch veSeg nt);
    Arch veSeg nt arch veSeg nt = (Arch veSeg nt) oldSeg nt nfo.getSeg nt();

    try {
      Arch veSeg nt seg nt = new Arch veSeg nt(arch veSeg nt.getArch veT  Sl ce(),
          arch veSeg nt.getHashPart  on D(), Earlyb rdConf g.getMaxSeg ntS ze());

      return new Seg nt nfo(seg nt, earlyb rdSeg ntFactory, sync);
    } catch ( OExcept on e) {
      throw new Seg nt nfoConstruct onExcept on("Error creat ng new seg nts", e);
    }
  }

  protected TryLock getZooKeeperTryLock() {
    ZooKeeperTryLockFactory tryLockFactory = seg ntConf g.getTryLockFactory();
    Str ng zkRootPath = sync.getZooKeeperSyncFullPath();
    Str ng nodeNa  = seg nt nfo.getZkNodeNa ();
    Amount<Long, T  > exp rat onT   = seg ntConf g.getSeg ntZKLockExp rat onT  ();

    return tryLockFactory.createTryLock(
        DatabaseConf g.getLocalHostna (),
        zkRootPath,
        nodeNa ,
        exp rat onT  );
  }
}
