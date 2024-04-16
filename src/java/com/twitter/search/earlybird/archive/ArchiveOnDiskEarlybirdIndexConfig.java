package com.tw ter.search.earlyb rd.arch ve;

 mport java. o.F le;
 mport java. o. OExcept on;

 mport org.apac .lucene.store.D rectory;
 mport org.apac .lucene.store.FSD rectory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdLucene ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd. ndex.DocValuesBasedT  Mapper;
 mport com.tw ter.search.earlyb rd. ndex.DocValuesBasedT et DMapper;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSync nfo;

/**
 *  ndex conf g for t  on-d sk T et clusters.
 */
publ c class Arch veOnD skEarlyb rd ndexConf g extends Arch veEarlyb rd ndexConf g {
  publ c Arch veOnD skEarlyb rd ndexConf g(
      Dec der dec der, Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    super(Earlyb rdCluster.FULL_ARCH VE, dec der, search ndex ng tr cSet,
        cr  calExcept onHandler);
  }

  @Overr de
  publ c boolean  s ndexStoredOnD sk() {
    return true;
  }

  @Overr de
  publ c D rectory newLuceneD rectory(Seg ntSync nfo seg ntSync nfo) throws  OExcept on {
    F le d rPath = new F le(seg ntSync nfo.getLocalLuceneSyncD r());
    return FSD rectory.open(d rPath.toPath());
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntData newSeg ntData(
       nt maxSeg ntS ze,
      long t  Sl ce D,
      D rectory d r,
      Earlyb rd ndexExtens onsFactory extens onsFactory) {
    return new Earlyb rdLucene ndexSeg ntData(
        d r,
        maxSeg ntS ze,
        t  Sl ce D,
        getSc ma(),
        new DocValuesBasedT et DMapper(),
        new DocValuesBasedT  Mapper(),
        extens onsFactory);
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntData loadSeg ntData(
      Flush nfo flush nfo,
      DataDeser al zer data nputStream,
      D rectory d r,
      Earlyb rd ndexExtens onsFactory extens onsFactory) throws  OExcept on {
    //  O Except on w ll be thrown  f t re's an error dur ng load
    return (new Earlyb rdLucene ndexSeg ntData.OnD skSeg ntDataFlushHandler(
        getSc ma(),
        d r,
        extens onsFactory,
        new DocValuesBasedT et DMapper.FlushHandler(),
        new DocValuesBasedT  Mapper.FlushHandler())).load(flush nfo, data nputStream);
  }

  @Overr de
  publ c boolean supportOutOfOrder ndex ng() {
    return false;
  }
}
