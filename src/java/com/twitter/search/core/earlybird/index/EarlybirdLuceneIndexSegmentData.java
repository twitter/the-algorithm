package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene.store.D rectory;
 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount ngArrayWr er;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deF eld ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.column.Opt m zedDocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsData;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.DeletedDocs;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

/**
 *  mple nts {@l nk Earlyb rd ndexSeg ntData} for Lucene-based on-d sk Earlyb rd seg nts.
 */
publ c f nal class Earlyb rdLucene ndexSeg ntData extends Earlyb rd ndexSeg ntData {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(Earlyb rdLucene ndexSeg ntData.class);

  pr vate f nal D rectory d rectory;
  pr vate f nal Earlyb rd ndexExtens onsData  ndexExtens on;

  /**
   * Creates a new Lucene-based Seg ntData  nstance from a lucene d rectory.
   */
  publ c Earlyb rdLucene ndexSeg ntData(
      D rectory d rectory,
       nt maxSeg ntS ze,
      long t  Sl ce D,
      Sc ma sc ma,
      Doc DToT et DMapper doc dToT et dMapper,
      T  Mapper t  Mapper,
      Earlyb rd ndexExtens onsFactory  ndexExtens onsFactory) {
    t (
        d rectory,
        maxSeg ntS ze,
        t  Sl ce D,
        sc ma,
        false, //  sOpt m zed
        0, // smallestDoc d
        new ConcurrentHashMap<>(),
        AbstractFacetCount ngArray.EMPTY_ARRAY,
        new Opt m zedDocValuesManager(sc ma, maxSeg ntS ze),
        doc dToT et dMapper,
        t  Mapper,
         ndexExtens onsFactory == null
            ? null :  ndexExtens onsFactory.newLucene ndexExtens onsData());
  }

  publ c Earlyb rdLucene ndexSeg ntData(
      D rectory d rectory,
       nt maxSeg ntS ze,
      long t  Sl ce D,
      Sc ma sc ma,
      boolean  sOpt m zed,
       nt smallestDoc D,
      ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
      AbstractFacetCount ngArray facetCount ngArray,
      DocValuesManager docValuesManager,
      Doc DToT et DMapper doc dToT et dMapper,
      T  Mapper t  Mapper,
      Earlyb rd ndexExtens onsData  ndexExtens on) {
    super(maxSeg ntS ze,
          t  Sl ce D,
          sc ma,
           sOpt m zed,
          smallestDoc D,
          perF eldMap,
          new ConcurrentHashMap<>(),
          facetCount ngArray,
          docValuesManager,
          null, // facetLabelProv ders
          null, // facet DMap
          DeletedDocs.NO_DELETES,
          doc dToT et dMapper,
          t  Mapper);
    t .d rectory = d rectory;
    t . ndexExtens on =  ndexExtens on;
  }

  publ c D rectory getLuceneD rectory() {
    return d rectory;
  }

  @Overr de
  publ c Earlyb rd ndexExtens onsData get ndexExtens onsData() {
    return  ndexExtens on;
  }

  @Overr de
  publ c FacetCount ngArrayWr er createFacetCount ngArrayWr er() {
    return null;
  }

  @Overr de
  protected Earlyb rd ndexSeg ntAtom cReader doCreateAtom cReader() throws  OExcept on {
    // Earlyb rdSeg nt creates one s ngle Earlyb rd ndexSeg ntAtom cReader  nstance per seg nt
    // and cac s  , and t  cac d  nstance  s recreated only w n t  seg nt's data changes.
    // T   s why t   s a good place to reload all CSFs that should be loaded  n RAM. Also,  's
    // eas er and less error-prone to do    re, than try ng to track down all places that mutate
    // t  seg nt data and do   t re.
    LeafReader reader = getLeafReaderFromOpt m zedD rectory(d rectory);
    for (Sc ma.F eld nfo f eld nfo : getSc ma().getF eld nfos()) {
      // Load CSF  nto RAM based on conf gurat ons  n t  sc ma.
       f (f eld nfo.getF eldType().getCsfType() != null
          && f eld nfo.getF eldType(). sCsfLoad ntoRam()) {
         f (reader.getNu r cDocValues(f eld nfo.getNa ()) != null) {
          ColumnStr deF eld ndex  ndex = getDocValuesManager().addColumnStr deF eld(
              f eld nfo.getNa (), f eld nfo.getF eldType());
           ndex.load(reader, f eld nfo.getNa ());
        } else {
          LOG.warn("F eld {} does not have Nu r cDocValues.", f eld nfo.getNa ());
        }
      }
    }

    return new Earlyb rdLucene ndexSeg ntAtom cReader(t , d rectory);
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntWr er createEarlyb rd ndexSeg ntWr er(
       ndexWr erConf g  ndexWr erConf g) throws  OExcept on {
    return new Earlyb rdLucene ndexSeg ntWr er(t ,  ndexWr erConf g);
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntData.AbstractSeg ntDataFlushHandler getFlushHandler() {
    return new OnD skSeg ntDataFlushHandler(t );
  }

  publ c stat c class OnD skSeg ntDataFlushHandler
      extends AbstractSeg ntDataFlushHandler<Earlyb rd ndexExtens onsData> {
    pr vate f nal D rectory d rectory;

    publ c OnD skSeg ntDataFlushHandler(Earlyb rdLucene ndexSeg ntData objectToFlush) {
      super(objectToFlush);
      t .d rectory = objectToFlush.d rectory;
    }

    publ c OnD skSeg ntDataFlushHandler(
        Sc ma sc ma,
        D rectory d rectory,
        Earlyb rd ndexExtens onsFactory  ndexExtens onsFactory,
        Flushable.Handler<? extends Doc DToT et DMapper> doc dMapperFlushHandler,
        Flushable.Handler<? extends T  Mapper> t  MapperFlushHandler) {
      super(sc ma,  ndexExtens onsFactory, doc dMapperFlushHandler, t  MapperFlushHandler);
      t .d rectory = d rectory;
    }

    @Overr de
    protected Earlyb rd ndexExtens onsData new ndexExtens on() {
      return  ndexExtens onsFactory.newLucene ndexExtens onsData();
    }

    @Overr de
    protected vo d flushAdd  onalDataStructures(
        Flush nfo flush nfo, DataSer al zer out, Earlyb rd ndexSeg ntData toFlush) {
    }

    @Overr de
    protected Earlyb rd ndexSeg ntData constructSeg ntData(
        Flush nfo flush nfo,
        ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
         nt maxSeg ntS ze,
        Earlyb rd ndexExtens onsData  ndexExtens on,
        Doc DToT et DMapper doc dToT et dMapper,
        T  Mapper t  Mapper,
        DataDeser al zer  n) {
      return new Earlyb rdLucene ndexSeg ntData(
          d rectory,
          maxSeg ntS ze,
          flush nfo.getLongProperty(T ME_SL CE_ D_PROP_NAME),
          sc ma,
          flush nfo.getBooleanProperty( S_OPT M ZED_PROP_NAME),
          flush nfo.get ntProperty(SMALLEST_DOC D_PROP_NAME),
          perF eldMap,
          AbstractFacetCount ngArray.EMPTY_ARRAY,
          new Opt m zedDocValuesManager(sc ma, maxSeg ntS ze),
          doc dToT et dMapper,
          t  Mapper,
           ndexExtens on);
    }
  }
}
