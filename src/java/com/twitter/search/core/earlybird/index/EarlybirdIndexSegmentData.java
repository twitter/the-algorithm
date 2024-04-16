package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.ArrayL st;
 mport java.ut l.Collect ons;
 mport java.ut l.HashMap;
 mport java.ut l. erator;
 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.concurrent.ConcurrentHashMap;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.D rectoryReader;
 mport org.apac .lucene. ndex. ndexWr erConf g;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.LeafReaderContext;
 mport org.apac .lucene.store.D rectory;

 mport com.tw ter.common.collect ons.Pa r;
 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount ngArrayWr er;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.column.ColumnStr deByte ndex;
 mport com.tw ter.search.core.earlyb rd. ndex.column.DocValuesManager;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsData;
 mport com.tw ter.search.core.earlyb rd. ndex.extens ons.Earlyb rd ndexExtens onsFactory;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.DeletedDocs;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nvertedRealt   ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.Opt m zed mory ndex;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.TermPo nterEncod ng;

/**
 * Base class that references data structures belong ng to an Earlyb rd seg nt.
 */
publ c abstract class Earlyb rd ndexSeg ntData  mple nts Flushable {
  /**
   * T  class has a map wh ch conta ns a snapshot of max publ s d po nters, to d st ngu sh t 
   * docu nts  n t  sk p l sts that are fully  ndexed, and safe to return to searc rs and those
   * that are  n progress and should not be returned to searc rs. See
   * "Earlyb rd  ndex ng Latency Des gn Docu nt"
   * for rat onale and des gn.
   *
   *   also has t  smallestDoc D, wh ch determ nes t  smallest ass gned doc  D  n t  t et  D
   * mapper that  s safe to traverse.
   *
   * T  po nter map and smallestDoc D need to be updated atom cally. See SEARCH-27650.
   */
  publ c stat c class SyncData {
    pr vate f nal Map< nverted ndex,  nteger>  ndexPo nters;
    pr vate f nal  nt smallestDoc D;

    publ c SyncData(Map< nverted ndex,  nteger>  ndexPo nters,  nt smallestDoc D) {
      t . ndexPo nters =  ndexPo nters;
      t .smallestDoc D = smallestDoc D;
    }

    publ c Map< nverted ndex,  nteger> get ndexPo nters() {
      return  ndexPo nters;
    }

    publ c  nt getSmallestDoc D() {
      return smallestDoc D;
    }
  }

  pr vate volat le SyncData syncData;

  pr vate f nal  nt maxSeg ntS ze;
  pr vate f nal long t  Sl ce D;

  pr vate f nal ConcurrentHashMap<Str ng, QueryCac ResultForSeg nt> queryCac Map =
      new ConcurrentHashMap<>();
  pr vate f nal AbstractFacetCount ngArray facetCount ngArray;
  pr vate f nal boolean  sOpt m zed;
  pr vate f nal ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap;
  pr vate f nal ConcurrentHashMap<Str ng, ColumnStr deByte ndex> normsMap;

  pr vate f nal Map<Str ng, FacetLabelProv der> facetLabelProv ders;
  pr vate f nal Facet DMap facet DMap;

  pr vate f nal Sc ma sc ma;
  pr vate f nal DocValuesManager docValuesManager;

  pr vate f nal DeletedDocs deletedDocs;

  pr vate f nal Doc DToT et DMapper doc dToT et dMapper;
  pr vate f nal T  Mapper t  Mapper;

  stat c LeafReader getLeafReaderFromOpt m zedD rectory(D rectory d rectory) throws  OExcept on {
    L st<LeafReaderContext> leaves = D rectoryReader.open(d rectory).getContext().leaves();
     nt leavesS ze = leaves.s ze();
    Precond  ons.c ckState(1 == leavesS ze,
        "Expected one leaf reader  n d rectory %s, but found %s", d rectory, leavesS ze);
    return leaves.get(0).reader();
  }

  /**
   * Creates a new Seg ntData  nstance us ng t  prov ded data.
   */
  publ c Earlyb rd ndexSeg ntData(
       nt maxSeg ntS ze,
      long t  Sl ce D,
      Sc ma sc ma,
      boolean  sOpt m zed,
       nt smallestDoc D,
      ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
      ConcurrentHashMap<Str ng, ColumnStr deByte ndex> normsMap,
      AbstractFacetCount ngArray facetCount ngArray,
      DocValuesManager docValuesManager,
      Map<Str ng, FacetLabelProv der> facetLabelProv ders,
      Facet DMap facet DMap,
      DeletedDocs deletedDocs,
      Doc DToT et DMapper doc dToT et dMapper,
      T  Mapper t  Mapper) {
    t .maxSeg ntS ze = maxSeg ntS ze;
    t .t  Sl ce D = t  Sl ce D;
    t .sc ma = sc ma;
    t . sOpt m zed =  sOpt m zed;
    t .facetCount ngArray = facetCount ngArray;
    t .perF eldMap = perF eldMap;
    t .syncData = new SyncData(bu ld ndexPo nters(), smallestDoc D);
    t .normsMap = normsMap;
    t .docValuesManager = docValuesManager;
    t .facetLabelProv ders = facetLabelProv ders;
    t .facet DMap = facet DMap;
    t .deletedDocs = deletedDocs;
    t .doc dToT et dMapper = doc dToT et dMapper;
    t .t  Mapper = t  Mapper;

    Precond  ons.c ckNotNull(sc ma);
  }

  publ c f nal Sc ma getSc ma() {
    return sc ma;
  }

  /**
   * Returns all {@l nk Earlyb rd ndexExtens onsData}  nstances conta ned  n t  seg nt.
   * S nce  ndex extens ons are opt onal, t  returned map m ght be null or empty.
   */
  publ c abstract <S extends Earlyb rd ndexExtens onsData> S get ndexExtens onsData();

  publ c Doc DToT et DMapper getDoc DToT et DMapper() {
    return doc dToT et dMapper;
  }

  publ c T  Mapper getT  Mapper() {
    return t  Mapper;
  }

  publ c f nal DocValuesManager getDocValuesManager() {
    return docValuesManager;
  }

  publ c Map<Str ng, FacetLabelProv der> getFacetLabelProv ders() {
    return facetLabelProv ders;
  }

  publ c Facet DMap getFacet DMap() {
    return facet DMap;
  }

  /**
   * Returns t  QueryCac Result for t  g ven f lter for t  seg nt.
   */
  publ c QueryCac ResultForSeg nt getQueryCac Result(Str ng queryCac F lterNa ) {
    return queryCac Map.get(queryCac F lterNa );
  }

  publ c long getQueryCac sCard nal y() {
    return queryCac Map.values().stream().mapToLong(q -> q.getCard nal y()).sum();
  }

  /**
   * Get cac  card nal y for each query cac .
   * @return
   */
  publ c L st<Pa r<Str ng, Long>> getPerQueryCac Card nal y() {
    ArrayL st<Pa r<Str ng, Long>> result = new ArrayL st<>();

    queryCac Map.forEach((cac Na , queryCac Result) -> {
      result.add(Pa r.of(cac Na , queryCac Result.getCard nal y()));
    });
    return result;
  }

  /**
   * Updates t  QueryCac Result stored for t  g ven f lter for t  seg nt
   */
  publ c QueryCac ResultForSeg nt updateQueryCac Result(
      Str ng queryCac F lterNa , QueryCac ResultForSeg nt queryCac ResultForSeg nt) {
    return queryCac Map.put(queryCac F lterNa , queryCac ResultForSeg nt);
  }

  /**
   * Subclasses are allo d to return null  re to d sable wr  ng to a FacetCount ngArray.
   */
  publ c FacetCount ngArrayWr er createFacetCount ngArrayWr er() {
    return getFacetCount ngArray() != null
        ? new FacetCount ngArrayWr er(getFacetCount ngArray()) : null;
  }

  publ c  nt getMaxSeg ntS ze() {
    return maxSeg ntS ze;
  }

  publ c long getT  Sl ce D() {
    return t  Sl ce D;
  }

  publ c vo d updateSmallestDoc D( nt smallestDoc D) {
    // Atom c swap
    syncData = new SyncData(Collect ons.unmod f ableMap(bu ld ndexPo nters()), smallestDoc D);
  }

  pr vate Map< nverted ndex,  nteger> bu ld ndexPo nters() {
    Map< nverted ndex,  nteger> new ndexPo nters = new HashMap<>();
    for ( nverted ndex  ndex : perF eldMap.values()) {
       f ( ndex.hasMaxPubl s dPo nter()) {
        new ndexPo nters.put( ndex,  ndex.getMaxPubl s dPo nter());
      }
    }

    return new ndexPo nters;
  }

  publ c SyncData getSyncData() {
    return syncData;
  }

  publ c AbstractFacetCount ngArray getFacetCount ngArray() {
    return facetCount ngArray;
  }

  publ c vo d addF eld(Str ng f eldNa ,  nverted ndex f eld) {
    perF eldMap.put(f eldNa , f eld);
  }

  publ c Map<Str ng,  nverted ndex> getPerF eldMap() {
    return Collect ons.unmod f ableMap(perF eldMap);
  }

  publ c  nverted ndex getF eld ndex(Str ng f eldNa ) {
    return perF eldMap.get(f eldNa );
  }

  publ c Map<Str ng, ColumnStr deByte ndex> getNormsMap() {
    return Collect ons.unmod f ableMap(normsMap);
  }

  publ c DeletedDocs getDeletedDocs() {
    return deletedDocs;
  }

  /**
   * Returns t  norms  ndex for t  g ven f eld na .
   */
  publ c ColumnStr deByte ndex getNorm ndex(Str ng f eldNa ) {
    return normsMap == null ? null : normsMap.get(f eldNa );
  }

  /**
   * Returns t  norms  ndex for t  g ven f eld na , add  f not ex st.
   */
  publ c ColumnStr deByte ndex createNorm ndex(Str ng f eldNa ) {
     f (normsMap == null) {
      return null;
    }
    ColumnStr deByte ndex csf = normsMap.get(f eldNa );
     f (csf == null) {
      csf = new ColumnStr deByte ndex(f eldNa , maxSeg ntS ze);
      normsMap.put(f eldNa , csf);
    }
    return csf;
  }

  /**
   * Flus s t  seg nt to d sk.
   */
  publ c vo d flushSeg nt(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
    getFlushHandler().flush(flush nfo, out);
  }

  publ c f nal boolean  sOpt m zed() {
    return t . sOpt m zed;
  }

  /**
   * Returns a new atom c reader for t  seg nt.
   */
  publ c Earlyb rd ndexSeg ntAtom cReader createAtom cReader() throws  OExcept on {
    Earlyb rd ndexSeg ntAtom cReader reader = doCreateAtom cReader();
    Earlyb rd ndexExtens onsData  ndexExtens on = get ndexExtens onsData();
     f ( ndexExtens on != null) {
       ndexExtens on.setupExtens ons(reader);
    }
    return reader;
  }

  /**
   * Creates a new atom c reader for t  seg nt.
   */
  protected abstract Earlyb rd ndexSeg ntAtom cReader doCreateAtom cReader() throws  OExcept on;

  /**
   * Creates a new seg nt wr er for t  seg nt.
   */
  publ c abstract Earlyb rd ndexSeg ntWr er createEarlyb rd ndexSeg ntWr er(
       ndexWr erConf g  ndexWr erConf g) throws  OExcept on;

  publ c abstract stat c class AbstractSeg ntDataFlushHandler
      <S extends Earlyb rd ndexExtens onsData>
      extends Flushable.Handler<Earlyb rd ndexSeg ntData> {
    protected stat c f nal Str ng MAX_SEGMENT_S ZE_PROP_NAME = "maxSeg ntS ze";
    protected stat c f nal Str ng T ME_SL CE_ D_PROP_NAME = "t  _sl ce_ d";
    protected stat c f nal Str ng SMALLEST_DOC D_PROP_NAME = "smallestDoc D";
    protected stat c f nal Str ng DOC_ D_MAPPER_SUBPROPS_NAME = "doc_ d_mapper";
    protected stat c f nal Str ng T ME_MAPPER_SUBPROPS_NAME = "t  _mapper";
    publ c stat c f nal Str ng  S_OPT M ZED_PROP_NAME = " sOpt m zed";

    // Abstract  thods ch ld classes should  mple nt:
    // 1. How to add  onal data structures
    protected abstract vo d flushAdd  onalDataStructures(
        Flush nfo flush nfo, DataSer al zer out, Earlyb rd ndexSeg ntData toFlush)
            throws  OExcept on;

    // 2. Load add  onal data structures and construct Seg ntData.
    // Common data structures should be passed  nto t   thod to avo d code dupl cat on.
    // Subclasses should load add  onal data structures and construct a Seg ntData.
    protected abstract Earlyb rd ndexSeg ntData constructSeg ntData(
        Flush nfo flush nfo,
        ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap,
         nt maxSeg ntS ze,
        S  ndexExtens on,
        Doc DToT et DMapper doc dToT et dMapper,
        T  Mapper t  Mapper,
        DataDeser al zer  n) throws  OExcept on;

    protected abstract S new ndexExtens on();

    protected f nal Sc ma sc ma;
    protected f nal Earlyb rd ndexExtens onsFactory  ndexExtens onsFactory;
    pr vate f nal Flushable.Handler<? extends Doc DToT et DMapper> doc dMapperFlushHandler;
    pr vate f nal Flushable.Handler<? extends T  Mapper> t  MapperFlushHandler;

    publ c AbstractSeg ntDataFlushHandler(
        Sc ma sc ma,
        Earlyb rd ndexExtens onsFactory  ndexExtens onsFactory,
        Flushable.Handler<? extends Doc DToT et DMapper> doc dMapperFlushHandler,
        Flushable.Handler<? extends T  Mapper> t  MapperFlushHandler) {
      super();
      t .sc ma = sc ma;
      t . ndexExtens onsFactory =  ndexExtens onsFactory;
      t .doc dMapperFlushHandler = doc dMapperFlushHandler;
      t .t  MapperFlushHandler = t  MapperFlushHandler;
    }

    publ c AbstractSeg ntDataFlushHandler(Earlyb rd ndexSeg ntData objectToFlush) {
      super(objectToFlush);
      t .sc ma = objectToFlush.sc ma;
      t . ndexExtens onsFactory = null; // factory only needed for load ng Seg ntData from d sk
      t .doc dMapperFlushHandler = null; // doc dMapperFlushHandler needed only for load ng data
      t .t  MapperFlushHandler = null; // t  MapperFlushHandler needed only for load ng data
    }

    @Overr de
    protected vo d doFlush(Flush nfo flush nfo, DataSer al zer out)
        throws  OExcept on {
      Earlyb rd ndexSeg ntData seg ntData = getObjectToFlush();

      Precond  ons.c ckState(seg ntData.doc dToT et dMapper  nstanceof Flushable);
      ((Flushable) seg ntData.doc dToT et dMapper).getFlushHandler().flush(
          flush nfo.newSubPropert es(DOC_ D_MAPPER_SUBPROPS_NAME), out);

       f (seg ntData.t  Mapper != null) {
        seg ntData.t  Mapper.getFlushHandler()
            .flush(flush nfo.newSubPropert es(T ME_MAPPER_SUBPROPS_NAME), out);
      }

      flush nfo.addBooleanProperty( S_OPT M ZED_PROP_NAME, seg ntData. sOpt m zed());
      flush nfo.add ntProperty(MAX_SEGMENT_S ZE_PROP_NAME, seg ntData.getMaxSeg ntS ze());
      flush nfo.addLongProperty(T ME_SL CE_ D_PROP_NAME, seg ntData.getT  Sl ce D());
      flush nfo.add ntProperty(SMALLEST_DOC D_PROP_NAME,
                               seg ntData.getSyncData().getSmallestDoc D());

      flush ndexes(flush nfo, out, seg ntData);

      // Flush cluster spec f c data structures:
      // FacetCount ngArray, T et DMapper, LatLonMapper, and T  Mapper
      flushAdd  onalDataStructures(flush nfo, out, seg ntData);
    }

    pr vate vo d flush ndexes(
        Flush nfo flush nfo,
        DataSer al zer out,
        Earlyb rd ndexSeg ntData seg ntData) throws  OExcept on {
      Map<Str ng,  nverted ndex> perF eldMap = seg ntData.getPerF eldMap();
      Flush nfo f eldProps = flush nfo.newSubPropert es("f elds");
      long s zeBeforeFlush = out.length();
      for (Map.Entry<Str ng,  nverted ndex> entry : perF eldMap.entrySet()) {
        Str ng f eldNa  = entry.getKey();
        entry.getValue().getFlushHandler().flush(f eldProps.newSubPropert es(f eldNa ), out);
      }
      f eldProps.setS ze nBytes(out.length() - s zeBeforeFlush);
    }

    @Overr de
    protected Earlyb rd ndexSeg ntData doLoad(Flush nfo flush nfo, DataDeser al zer  n)
        throws  OExcept on {
      Doc DToT et DMapper doc dToT et dMapper = doc dMapperFlushHandler.load(
          flush nfo.getSubPropert es(DOC_ D_MAPPER_SUBPROPS_NAME),  n);

      Flush nfo t  MapperFlush nfo = flush nfo.getSubPropert es(T ME_MAPPER_SUBPROPS_NAME);
      T  Mapper t  Mapper =
          t  MapperFlush nfo != null ? t  MapperFlushHandler.load(t  MapperFlush nfo,  n) : null;

      f nal  nt maxSeg ntS ze = flush nfo.get ntProperty(MAX_SEGMENT_S ZE_PROP_NAME);
      ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap = load ndexes(flush nfo,  n);
      return constructSeg ntData(
          flush nfo,
          perF eldMap,
          maxSeg ntS ze,
          new ndexExtens on(),
          doc dToT et dMapper,
          t  Mapper,
           n);
    }

    // Move t   thod  nto Earlyb rdRealt   ndexSeg ntData (careful,
    //   may need to  ncre nt FlushVers on because Earlyb rdLucene ndexSeg ntData
    // currently has t  'f elds' subproperty  n  s Flush nfo as  ll)
    pr vate ConcurrentHashMap<Str ng,  nverted ndex> load ndexes(
        Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
      ConcurrentHashMap<Str ng,  nverted ndex> perF eldMap = new ConcurrentHashMap<>();

      Flush nfo f eldProps = flush nfo.getSubPropert es("f elds");
       erator<Str ng> f eld erator = f eldProps.getKey erator();
      wh le (f eld erator.hasNext()) {
        Str ng f eldNa  = f eld erator.next();
        Earlyb rdF eldType f eldType = sc ma.getF eld nfo(f eldNa ).getF eldType();
        Flush nfo subProp = f eldProps.getSubPropert es(f eldNa );
        boolean  sOpt m zed = subProp.getBooleanProperty(
            Opt m zed mory ndex.FlushHandler. S_OPT M ZED_PROP_NAME);
        f nal  nverted ndex  nverted ndex;
         f ( sOpt m zed) {
           f (!f eldType.beco s mmutable()) {
            throw new  OExcept on("Tr ed to load an opt m zed f eld that  s not  mmutable: "
                + f eldNa );
          }
           nverted ndex = (new Opt m zed mory ndex.FlushHandler(f eldType)).load(subProp,  n);
        } else {
           nverted ndex = (new  nvertedRealt   ndex.FlushHandler(
                               f eldType, TermPo nterEncod ng.DEFAULT_ENCOD NG))
              .load(subProp,  n);
        }
        perF eldMap.put(f eldNa ,  nverted ndex);
      }
      return perF eldMap;
    }
  }

  publ c  nt numDocs() {
    return doc dToT et dMapper.getNumDocs();
  }
}
