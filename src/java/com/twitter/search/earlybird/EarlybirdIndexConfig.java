package com.tw ter.search.earlyb rd;

 mport java. o. OExcept on;

 mport com.google.common.annotat ons.V s bleForTest ng;
 mport com.google.common.base.Pred cate;
 mport com.google.common.base.Pred cates;

 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene.store.D rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.dec der.Dec der;
 mport com.tw ter.search.common.sc ma.Dynam cSc ma;
 mport com.tw ter.search.common.sc ma.base.Sc ma.Sc maVal dat onExcept on;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdSc maCreateTool;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ft ndex ngEvent;
 mport com.tw ter.search.common.ut l.CloseRes ceUt l;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntData;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.earlyb rd.docu nt.Docu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.Thr ft ndex ngEventDocu ntFactory;
 mport com.tw ter.search.earlyb rd.docu nt.Thr ft ndex ngEventUpdateFactory;
 mport com.tw ter.search.earlyb rd.except on.Cr  calExcept onHandler;
 mport com.tw ter.search.earlyb rd.part  on.Part  onConf g;
 mport com.tw ter.search.earlyb rd.part  on.Search ndex ng tr cSet;
 mport com.tw ter.search.earlyb rd.part  on.Seg ntSync nfo;
 mport com.tw ter.search.earlyb rd.part  on.UserPart  onUt l;

/**
 * Collect on of requ red  ndex ng ent  es that d ffer  n t  var ous Earlyb rd clusters.
 */
publ c abstract class Earlyb rd ndexConf g {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rd ndexConf g.class);

  pr vate f nal Earlyb rdCluster cluster;
  pr vate f nal Dynam cSc ma sc ma;
  pr vate f nal Dec der dec der;
  pr vate f nal Search ndex ng tr cSet search ndex ng tr cSet;
  protected f nal Cr  calExcept onHandler cr  calExcept onHandler;

  /**
   * Creates a new  ndex conf g us ng an appl cable sc ma bu lt for t  prov ded cluster.
   */
  protected Earlyb rd ndexConf g(
      Earlyb rdCluster cluster, Dec der dec der, Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t (cluster, bu ldSc ma(cluster), dec der, search ndex ng tr cSet,
        cr  calExcept onHandler);
  }

  @V s bleForTest ng
  protected Earlyb rd ndexConf g(
      Earlyb rdCluster cluster,
      Dynam cSc ma sc ma,
      Dec der dec der,
      Search ndex ng tr cSet search ndex ng tr cSet,
      Cr  calExcept onHandler cr  calExcept onHandler) {
    t .cluster = cluster;
    t .sc ma = sc ma;
    t .dec der = dec der;
    t .search ndex ng tr cSet = search ndex ng tr cSet;
    t .cr  calExcept onHandler = cr  calExcept onHandler;
    LOG. nfo("T  Earlyb rd uses  ndex conf g: " + t .getClass().getS mpleNa ());
  }

  pr vate stat c Dynam cSc ma bu ldSc ma(Earlyb rdCluster cluster) {
    try {
      return Earlyb rdSc maCreateTool.bu ldSc ma(cluster);
    } catch (Sc maVal dat onExcept on e) {
      throw new Runt  Except on(e);
    }
  }

  /**
   * Creates t  appropr ate docu nt factory for t  earlyb rd.
   */
  publ c f nal Docu ntFactory<Thr ft ndex ngEvent> createDocu ntFactory() {
    return new Thr ft ndex ngEventDocu ntFactory(
        getSc ma(), getCluster(), dec der, search ndex ng tr cSet,
        cr  calExcept onHandler);
  }

  /**
   * Creates a docu nt factory for Thr ft ndex ngEvents that are updates to t   ndex.
   */
  publ c f nal Docu ntFactory<Thr ft ndex ngEvent> createUpdateFactory() {
    return new Thr ft ndex ngEventUpdateFactory(
        getSc ma(), getCluster(), dec der, cr  calExcept onHandler);
  }

  /**
   * Return t  Earlyb rdCluster enum  dent fy ng t  cluster t  conf g  s for.
   */
  publ c f nal Earlyb rdCluster getCluster() {
    return cluster;
  }

  /**
   * Return t  default f lter for UserUpdatesTable - for t  arch ve cluster keep
   * users that belong to t  current part  on.
   */
  publ c f nal Pred cate<Long> getUserTableF lter(Part  onConf g part  onConf g) {
     f (Earlyb rdCluster. sArch ve(getCluster())) {
      return UserPart  onUt l.f lterUsersByPart  onPred cate(part  onConf g);
    }

    return Pred cates.alwaysTrue();
  }

  /**
   * Creates a new Lucene {@l nk D rectory} to be used for  ndex ng docu nts.
   */
  publ c abstract D rectory newLuceneD rectory(Seg ntSync nfo seg ntSync nfo) throws  OExcept on;

  /**
   * Creates a new Lucene  ndexWr erConf g that can be used for creat ng a seg nt wr er for a
   * new seg nt.
   */
  publ c abstract  ndexWr erConf g new ndexWr erConf g();

  /**
   * Creates a new Seg ntData object to add docu nts to.
   */
  publ c abstract Earlyb rd ndexSeg ntData newSeg ntData(
       nt maxSeg ntS ze,
      long t  Sl ce D,
      D rectory d r,
      Earlyb rd ndexExtens onsFactory extens onsFactory);

  /**
   * Loads a flus d  ndex for t  g ven seg nt.
   */
  publ c abstract Earlyb rd ndexSeg ntData loadSeg ntData(
      Flush nfo flush nfo,
      DataDeser al zer data nputStream,
      D rectory d r,
      Earlyb rd ndexExtens onsFactory extens onsFactory) throws  OExcept on;

  /**
   * Creates a new seg nt opt m zer for t  g ven seg nt data.
   */
  publ c abstract Earlyb rd ndexSeg ntData opt m ze(
      Earlyb rd ndexSeg ntData earlyb rd ndexSeg ntData) throws  OExcept on;

  /**
   * W t r t   ndex  s stored on d sk or not.  f an  ndex  s not on d sk,    s presu d to be
   *  n  mory.
   */
  publ c abstract boolean  s ndexStoredOnD sk();

  /**
   * W t r docu nts are search  n L FO order ng (RT mode), or default (Lucene) F FO order ng
   */
  publ c f nal boolean  sUs ngL FODocu ntOrder ng() {
    return ! s ndexStoredOnD sk();
  }

  /**
   * W t r t   ndex supports out-of-order  ndex ng
   */
  publ c abstract boolean supportOutOfOrder ndex ng();

  /**
   * Returns a CloseRes ceUt l used for clos ng res ces.
   */
  publ c abstract CloseRes ceUt l getRes ceCloser();

  /**
   * Returns t  sc ma for t   ndex conf gurat on.
   */
  publ c f nal Dynam cSc ma getSc ma() {
    return sc ma;
  }

  /**
   * Returns t  dec der used by t  Earlyb rd ndexConf g  nstance.
   */
  publ c Dec der getDec der() {
    return dec der;
  }

  publ c Search ndex ng tr cSet getSearch ndex ng tr cSet() {
    return search ndex ng tr cSet;
  }
}
