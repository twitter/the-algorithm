package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport com.tw ter.common.quant y.Amount;
 mport com.tw ter.common.quant y.T  ;
 mport com.tw ter.search.common.ut l.zktrylock.ZooKeeperTryLockFactory;
 mport com.tw ter.search.earlyb rd.arch ve.Arch veOnD skEarlyb rd ndexConf g;

publ c class Seg ntConf g {
  pr vate f nal Arch veOnD skEarlyb rd ndexConf g earlyb rd ndexConf g;
  pr vate f nal Amount<Long, T  > seg ntZKLockExp rat onT  ;
  pr vate f nal  nt maxRetr esOnFa lure;
  pr vate f nal ZooKeeperTryLockFactory tryLockFactory;

  publ c Seg ntConf g(
      Arch veOnD skEarlyb rd ndexConf g earlyb rd ndexConf g,
      Amount<Long, T  > seg ntZKLockExp rat onT  ,
       nt maxRetr esOnFa lure,
      ZooKeeperTryLockFactory tryLockFactory) {

    t .earlyb rd ndexConf g = earlyb rd ndexConf g;
    t .seg ntZKLockExp rat onT   = seg ntZKLockExp rat onT  ;
    t .maxRetr esOnFa lure = maxRetr esOnFa lure;
    t .tryLockFactory = tryLockFactory;
  }

  publ c Arch veOnD skEarlyb rd ndexConf g getEarlyb rd ndexConf g() {
    return earlyb rd ndexConf g;
  }

  publ c Amount<Long, T  > getSeg ntZKLockExp rat onT  () {
    return seg ntZKLockExp rat onT  ;
  }

  publ c  nt getMaxRetr esOnFa lure() {
    return maxRetr esOnFa lure;
  }

  publ c ZooKeeperTryLockFactory getTryLockFactory() {
    return tryLockFactory;
  }
}
