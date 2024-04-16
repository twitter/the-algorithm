package com.tw ter.search.earlyb rd.arch ve.seg ntbu lder;

 mport com.tw ter.search.earlyb rd. ndex.Earlyb rdSeg ntFactory;
 mport com.tw ter.search.earlyb rd.part  on.Seg nt nfo;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSyncConf g;

publ c class Bu ltAndF nal zedSeg nt extends Seg ntBu lderSeg nt {
  publ c Bu ltAndF nal zedSeg nt(
      Seg nt nfo seg nt nfo,
      Seg ntConf g seg ntConf g,
      Earlyb rdSeg ntFactory earlyb rdSeg ntFactory,
       nt alreadyRetr edCount,
      Seg ntSyncConf g sync) {

    super(seg nt nfo, seg ntConf g, earlyb rdSeg ntFactory, alreadyRetr edCount, sync);
  }

  @Overr de
  publ c Seg ntBu lderSeg nt handle() throws Seg nt nfoConstruct onExcept on,
      Seg ntUpdaterExcept on {

    throw new  llegalStateExcept on("Should not handle a Bu ldAndF nal zedSeg nt.");
  }

  @Overr de
  publ c boolean  sBu lt() {
    return true;
  }
}
