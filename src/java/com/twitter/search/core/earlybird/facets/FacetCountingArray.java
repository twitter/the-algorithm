package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.ut l. o.flushable.DataDeser al zer;
 mport com.tw ter.search.common.ut l. o.flushable.DataSer al zer;
 mport com.tw ter.search.common.ut l. o.flushable.Flush nfo;
 mport com.tw ter.search.common.ut l. o.flushable.Flushable;
 mport com.tw ter.search.core.earlyb rd. ndex.Doc DToT et DMapper;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. ntBlockPool;

 mport  .un m .ds .fastut l. nts. nt2 ntOpenHashMap;

publ c class FacetCount ngArray extends AbstractFacetCount ngArray {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FacetCount ngArray.class);

  pr vate f nal  nt2 ntOpenHashMap facetsMap;

  /**
   * Creates a new, empty FacetCount ngArray w h t  g ven s ze.
   */
  publ c FacetCount ngArray( nt maxSeg ntS ze) {
    super();
    facetsMap = new  nt2 ntOpenHashMap(maxSeg ntS ze);
    facetsMap.defaultReturnValue(UNASS GNED);
  }

  pr vate FacetCount ngArray( nt2 ntOpenHashMap facetsMap,  ntBlockPool facetsPool) {
    super(facetsPool);
    t .facetsMap = facetsMap;
  }

  @Overr de
  protected  nt getFacet( nt doc D) {
    return facetsMap.get(doc D);
  }

  @Overr de
  protected vo d setFacet( nt doc D,  nt facet D) {
    facetsMap.put(doc D, facet D);
  }

  @Overr de
  publ c AbstractFacetCount ngArray rewr eAndMap Ds(
      Map< nteger,  nt[]> term DMapper,
      Doc DToT et DMapper or g nalT et dMapper,
      Doc DToT et DMapper opt m zedT et dMapper) throws  OExcept on {
    Precond  ons.c ckNotNull(or g nalT et dMapper);
    Precond  ons.c ckNotNull(opt m zedT et dMapper);

    //   need to rewr e t  facet array, because t  term  ds have to be mapped to t 
    // key space of t  m n mum perfect hash funct on that replaces t  hash table.
    //   also need to remap t et  Ds to t  opt m zed doc  Ds.
     nt maxDoc D = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    AbstractFacetCount ngArray newArray = new Opt m zedFacetCount ngArray(maxDoc D + 1);
    f nal FacetCount ngArrayWr er wr er = new FacetCount ngArrayWr er(newArray);
    FacetCount erator  erator = new ArrayFacetCount erator() {
      @Overr de
      publ c boolean collect( nt doc D, long term D,  nt f eld D) {
         nt[] term DMap = term DMapper.get(f eld D);
         nt mappedTerm D;
        //  f t re  sn't a map for t  term,   are us ng t  or g nal term  Ds and can cont nue
        // w h that term  D.  f t re  s a term  D map, t n   need to use t  new term  D,
        // because t  new  ndex w ll use an MPH term d ct onary w h new term  Ds.
         f (term DMap == null) {
          mappedTerm D = ( nt) term D;
        } else  f (term D < term DMap.length) {
          mappedTerm D = term DMap[( nt) term D];
        } else {
          // Dur ng seg nt opt m zat on   m ght  ndex a new term after t  term DMap  s created
          //  n  ndexOpt m zer.opt m ze nverted ndexes().   can safely  gnore t se terms, as
          // t y w ll be re- ndexed later.
          return false;
        }

        try {
          long t et d = or g nalT et dMapper.getT et D(doc D);
           nt newDoc d = opt m zedT et dMapper.getDoc D(t et d);
          Precond  ons.c ckState(newDoc d != Doc DToT et DMapper. D_NOT_FOUND,
                                   "D d not f nd a mapp ng  n t  new t et  D mapper for doc  D "
                                   + newDoc d + ", t et  D " + t et d);

          wr er.addFacet(newDoc d, f eld D, mappedTerm D);
        } catch ( OExcept on e) {
          LOG.error("Caught an unexpected  OExcept on wh le opt m z ng facet.", e);
        }

        return true;
      }
    };

    //   want to  erate t  facets  n  ncreas ng t et  D order. T  m ght not correspond to
    // decreas ng doc  D order  n t  or g nal mapper (see OutOfOrderRealt  T et DMapper).
    // Ho ver, t  opt m zed mapper should be sorted both by t et  Ds and by doc  Ds ( n reverse
    // order). So   need to  erate  re over t  doc  Ds  n t  opt m zed mapper, convert t m
    // to doc  Ds  n t  or g nal mapper, and pass those doc  Ds to collect().
     nt doc d = opt m zedT et dMapper.getPrev ousDoc D( nteger.MAX_VALUE);
    wh le (doc d != Doc DToT et DMapper. D_NOT_FOUND) {
      long t et d = opt m zedT et dMapper.getT et D(doc d);
       nt or g nalDoc d = or g nalT et dMapper.getDoc D(t et d);
       erator.collect(or g nalDoc d);
      doc d = opt m zedT et dMapper.getPrev ousDoc D(doc d);
    }
    return newArray;
  }

  @Overr de
  publ c FlushHandler getFlushHandler() {
    return new FlushHandler(t );
  }

  publ c stat c f nal class FlushHandler extends Flushable.Handler<FacetCount ngArray> {
    pr vate stat c f nal Str ng FACETS_POOL_PROP_NAME = "facetsPool";
    pr vate f nal  nt maxSeg ntS ze;

    publ c FlushHandler( nt maxSeg ntS ze) {
      t .maxSeg ntS ze = maxSeg ntS ze;
    }

    publ c FlushHandler(FacetCount ngArray objectToFlush) {
      super(objectToFlush);
      maxSeg ntS ze = -1;
    }

    @Overr de
    publ c vo d doFlush(Flush nfo flush nfo, DataSer al zer out) throws  OExcept on {
      FacetCount ngArray array = getObjectToFlush();
      out.wr e nt(array.facetsMap.s ze());
      for ( nt2 ntOpenHashMap.Entry entry : array.facetsMap. nt2 ntEntrySet()) {
        out.wr e nt(entry.get ntKey());
        out.wr e nt(entry.get ntValue());
      }
      array.getFacetsPool().getFlushHandler().flush(
          flush nfo.newSubPropert es(FACETS_POOL_PROP_NAME), out);
    }

    @Overr de
    publ c FacetCount ngArray doLoad(Flush nfo flush nfo, DataDeser al zer  n) throws  OExcept on {
       nt s ze =  n.read nt();
       nt2 ntOpenHashMap facetsMap = new  nt2 ntOpenHashMap(maxSeg ntS ze);
      facetsMap.defaultReturnValue(UNASS GNED);
      for ( nt   = 0;   < s ze;  ++) {
        facetsMap.put( n.read nt(),  n.read nt());
      }
       ntBlockPool facetsPool = new  ntBlockPool.FlushHandler().load(
          flush nfo.getSubPropert es(FACETS_POOL_PROP_NAME),  n);
      return new FacetCount ngArray(facetsMap, facetsPool);
    }
  }
}
