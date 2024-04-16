package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.collect.Maps;

 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene.search. ndexSearc r;

 mport com.tw ter.search.common.sc ma.SearchWh espaceAnalyzer;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.Earlyb rdFacetDocValueSet;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd.facets.FacetUt l;
 mport com.tw ter.search.core.earlyb rd.facets.Opt m zedFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.column.Opt m zedDocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.column.Unopt m zedDocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rdRealt   ndexExtens onsData;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.DeletedDocs;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ndexOpt m zer;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

/**
 *  mple nts {@l nk Earlyb rd ndexSeg ntData} for real-t    n- mory Earlyb rd seg nts.
 */
publ c class Earlyb rdRealt   ndexSeg ntData extends Earlyb rd ndexSeg ntData {
  pr vate f nal Earlyb rdRealt   ndexExtens onsData  ndexExtens on;

  pr vate Earlyb rdFacetDocValueSet facetDocValueSet;

  /**
   * Creates a new empty real-t   Seg ntData  nstance.
   */
  publ c Earlyb rdRealt   ndexSeg ntData(
       nt maxSeg ntS ze,
      long t  Sl ce D,
      Sc ma sc ma,
      Doc DToT et DMapper doc dToT et dMapper,
      T  Mapper t  Mapper,
      Earlyb rd ndexExtens onsFactory  ndexExtens onsFactory) {
    t (
        maxSeg ntS ze,
        t  Sl ce D,
        sc ma,
        false, //  sOpt m zed
         nteger.MAX_VALUE,
        new ConcurrentHashMap<>(),
        new FacetCount ngArray(maxSeg ntS ze),
        new Unopt m zedDocValuesManager(sc ma, maxSeg ntS ze),
        Maps.newHashMapW hExpectedS ze(sc ma.getNumFacetF elds()),
        Facet DMap.bu ld(sc ma),
        new DeletedDocs.Default(maxSeg ntS ze),
        doc dToT et dMapper,
        t  Mapper,
         ndexExtens onsFactory == null
            ? null
            :  ndexExtens onsFactory.newRealt   ndexExtens onsData());
  }

  /**
   * Creates a new real-t   Seg ntData  nstance us ng t  passed  n data structures. Usually t 
   * constructor  s used by t  FlushHandler after a seg nt was loaded from d sk, but also t 
   * {@l nk  ndexOpt m zer} uses   to create an
   * opt m zed seg nt.
   */
  publ c Earlyb rdRealt   ndexSeg ntData(
       nt maxSeg ntS ze,
      long t  Sl ce D,
      Sc ma sc ma,
      boolean  sOpt m zed,
       nt smallestDoc D,
      ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
      AbstractFacetCount ngArray facetCount ngArray,
      DocValuesManager docValuesManager,
      Map<Str ng, FacetLabelProv der> facetLabelProv ders,
      Facet DMap facet DMap,
      DeletedDocs deletedDocs,
      Doc DToT et DMapper doc dToT et dMapper,
      T  Mapper t  Mapper,
      Earlyb rdRealt   ndexExtens onsData  ndexExtens on) {
    super(maxSeg ntS ze,
          t  Sl ce D,
          sc ma,
           sOpt m zed,
          smallestDoc D,
          perF eldMap,
          new ConcurrentHashMap<>(),
          facetCount ngArray,
          docValuesManager,
          facetLabelProv ders,
          facet DMap,
          deletedDocs,
          doc dToT et dMapper,
          t  Mapper);
    t . ndexExtens on =  ndexExtens on;
    t .facetDocValueSet = null;
  }

  @Overr de
  publ c Earlyb rdRealt   ndexExtens onsData get ndexExtens onsData() {
    return  ndexExtens on;
  }

  /**
   * For realt   seg nts, t  wraps a facet datastructure  nto a SortedSetDocValues to
   * comply to Lucene facet ap .
   */
  publ c Earlyb rdFacetDocValueSet getFacetDocValueSet() {
     f (facetDocValueSet == null) {
      AbstractFacetCount ngArray facetCount ngArray = getFacetCount ngArray();
       f (facetCount ngArray != null) {
        facetDocValueSet = new Earlyb rdFacetDocValueSet(
            facetCount ngArray, getFacetLabelProv ders(), getFacet DMap());
      }
    }
    return facetDocValueSet;
  }

  @Overr de
  protected Earlyb rd ndexSeg ntAtom cReader doCreateAtom cReader() {
    return new Earlyb rdRealt   ndexSeg ntAtom cReader(t );
  }

  /**
   * Conven ence  thod for creat ng an Earlyb rd ndexSeg ntWr er for t  seg nt w h a default
   *  ndexSeg ntWr er conf g.
   */
  publ c Earlyb rd ndexSeg ntWr er createEarlyb rd ndexSeg ntWr er() {
    return createEarlyb rd ndexSeg ntWr er(
        new  ndexWr erConf g(new SearchWh espaceAnalyzer()).setS m lar y(
             ndexSearc r.getDefaultS m lar y()));
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntWr er createEarlyb rd ndexSeg ntWr er(
       ndexWr erConf g  ndexWr erConf g) {
    // Prepare t   n- mory seg nt w h all enabled CSF f elds.
    DocValuesManager docValuesManager = getDocValuesManager();
    for (Sc ma.F eld nfo f eld nfo : getSc ma().getF eld nfos()) {
       f (f eld nfo.getF eldType().getCsfType() != null) {
        docValuesManager.addColumnStr deF eld(f eld nfo.getNa (), f eld nfo.getF eldType());
      }
    }

    return new Earlyb rdRealt   ndexSeg ntWr er(
        t ,
         ndexWr erConf g.getAnalyzer(),
         ndexWr erConf g.getS m lar y());
  }

  @Overr de
  publ c Earlyb rd ndexSeg ntData.AbstractSeg ntDataFlushHandler getFlushHandler() {
    return new  n morySeg ntDataFlushHandler(t );
  }

  publ c stat c class  n morySeg ntDataFlushHandler
      extends AbstractSeg ntDataFlushHandler<Earlyb rdRealt   ndexExtens onsData> {
    publ c  n morySeg ntDataFlushHandler(Earlyb rd ndexSeg ntData objectToFlush) {
      super(objectToFlush);
    }

    publ c  n morySeg ntDataFlushHandler(
        Sc ma sc ma,
        Earlyb rd ndexExtens onsFactory factory,
        Flushable.Handler<? extends Doc DToT et DMapper> doc dMapperFlushHandler,
        Flushable.Handler<? extends T  Mapper> t  MapperFlushHandler) {
      super(sc ma, factory, doc dMapperFlushHandler, t  MapperFlushHandler);
    }

    @Overr de
    protected Earlyb rdRealt   ndexExtens onsData new ndexExtens on() {
      return  ndexExtens onsFactory.newRealt   ndexExtens onsData();
    }

    @Overr de
    protected vo d flushAdd  onalDataStructures(
        Flush nfo flush nfo,
        DataSer al zer out,
        Earlyb rd ndexSeg ntData seg ntData) throws  OExcept on {
      seg ntData.getFacetCount ngArray().getFlushHandler()
          .flush(flush nfo.newSubPropert es("facet_count ng_array"), out);

      // flush all column str de f elds
      seg ntData.getDocValuesManager().getFlushHandler()
          .flush(flush nfo.newSubPropert es("doc_values"), out);

      seg ntData.getFacet DMap().getFlushHandler()
          .flush(flush nfo.newSubPropert es("facet_ d_map"), out);

      seg ntData.getDeletedDocs().getFlushHandler()
          .flush(flush nfo.newSubPropert es("deleted_docs"), out);
    }

    @Overr de
    protected Earlyb rd ndexSeg ntData constructSeg ntData(
        Flush nfo flush nfo,
        ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
         nt maxSeg ntS ze,
        Earlyb rdRealt   ndexExtens onsData  ndexExtens on,
        Doc DToT et DMapper doc dToT et dMapper,
        T  Mapper t  Mapper,
        DataDeser al zer  n) throws  OExcept on {
      boolean  sOpt m zed = flush nfo.getBooleanProperty( S_OPT M ZED_PROP_NAME);

      Flushable.Handler<? extends AbstractFacetCount ngArray> facetLoader =  sOpt m zed
          ? new Opt m zedFacetCount ngArray.FlushHandler()
          : new FacetCount ngArray.FlushHandler(maxSeg ntS ze);
      AbstractFacetCount ngArray facetCount ngArray =
          facetLoader.load(flush nfo.getSubPropert es("facet_count ng_array"),  n);

      Flushable.Handler<? extends DocValuesManager> docValuesLoader =  sOpt m zed
          ? new Opt m zedDocValuesManager.Opt m zedFlushHandler(sc ma)
          : new Unopt m zedDocValuesManager.Unopt m zedFlushHandler(sc ma);
      DocValuesManager docValuesManager =
          docValuesLoader.load(flush nfo.getSubPropert es("doc_values"),  n);

      Facet DMap facet DMap = new Facet DMap.FlushHandler(sc ma)
          .load(flush nfo.getSubPropert es("facet_ d_map"),  n);

      DeletedDocs.Default deletedDocs = new DeletedDocs.Default.FlushHandler(maxSeg ntS ze)
          .load(flush nfo.getSubPropert es("deleted_docs"),  n);

      return new Earlyb rdRealt   ndexSeg ntData(
          maxSeg ntS ze,
          flush nfo.getLongProperty(T ME_SL CE_ D_PROP_NAME),
          sc ma,
           sOpt m zed,
          flush nfo.get ntProperty(SMALLEST_DOC D_PROP_NAME),
          perF eldMap,
          facetCount ngArray,
          docValuesManager,
          FacetUt l.getFacetLabelProv ders(sc ma, perF eldMap),
          facet DMap,
          deletedDocs,
          doc dToT et dMapper,
          t  Mapper,
           ndexExtens on);
    }
  }
}
