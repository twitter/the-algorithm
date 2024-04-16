package com.tw ter.search.earlyb rd.arch ve;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene. ndex.KeepOnlyLastComm Delet onPol cy;
 mport org.apac .lucene. ndex.LogByteS ze rgePol cy;
 mport org.apac .lucene. ndex.Ser al rgeSc duler;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.SearchWh espaceAnalyzer;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.ut l.CloseRes ceUt l;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rdLucene ndexSeg ntData;
 mport com.tw ter.search.earlyb rd.Earlyb rd ndexConf g;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;

/**
 * Base conf g for t  top arch ve t et clusters.
 */
publ c abstract class Arch veEarlyb rd ndexConf g extends Earlyb rd ndexConf g {

  pr vate f nal CloseRes ceUt l res ceCloser = new CloseRes ceUt l();

  publ c Arch veEarlyb rd ndexConf g(
      Earlyb rdCluster cluster, Dec der dec der, Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    super(cluster, dec der, search ndex ng tr cSet, cr  calExcept onHandler);
  }

  @Overr de
  publ c  ndexWr erConf g new ndexWr erConf g() {
    return new  ndexWr erConf g(new SearchWh espaceAnalyzer())
        .set ndexDelet onPol cy(new KeepOnlyLastComm Delet onPol cy())
        .set rgeSc duler(new Ser al rgeSc duler())
        .set rgePol cy(new LogByteS ze rgePol cy())
        .setRAMBufferS zeMB( ndexWr erConf g.DEFAULT_RAM_PER_THREAD_HARD_L M T_MB)
        .setMaxBufferedDocs( ndexWr erConf g.D SABLE_AUTO_FLUSH)
        .setOpenMode( ndexWr erConf g.OpenMode.CREATE_OR_APPEND);
  }

  @Overr de
  publ c CloseRes ceUt l getRes ceCloser() {
    return res ceCloser;
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntData opt m ze(
      Earlyb rd ndexSeg ntData seg ntData) throws  OExcept on {
    Precond  ons.c ckArgu nt(
        seg ntData  nstanceof Earlyb rdLucene ndexSeg ntData,
        "Expected Earlyb rdLucene ndexSeg ntData but got %s",
        seg ntData.getClass());
    Earlyb rdLucene ndexSeg ntData data = (Earlyb rdLucene ndexSeg ntData) seg ntData;

    return new Earlyb rdLucene ndexSeg ntData(
        data.getLuceneD rectory(),
        data.getMaxSeg ntS ze(),
        data.getT  Sl ce D(),
        data.getSc ma(),
        true, //  sOpt m zed
        data.getSyncData().getSmallestDoc D(),
        new ConcurrentHashMap<>(data.getPerF eldMap()),
        data.getFacetCount ngArray(),
        data.getDocValuesManager(),
        data.getDoc DToT et DMapper(),
        data.getT  Mapper(),
        data.get ndexExtens onsData());
  }
}
