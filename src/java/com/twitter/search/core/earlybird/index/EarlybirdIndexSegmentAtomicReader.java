package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;
 mport java.ut l.Map;
 mport java.ut l.Set;

 mport com.google.common.collect.Sets;

 mport org.apac .lucene. ndex.F eld nfos;
 mport org.apac .lucene. ndex.F elds;
 mport org.apac .lucene. ndex.LeafReader;
 mport org.apac .lucene. ndex.Nu r cDocValues;
 mport org.apac .lucene. ndex.Post ngsEnum;
 mport org.apac .lucene. ndex.Term;
 mport org.apac .lucene.search.Doc dSet erator;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd.facets.AbstractFacetCount ngArray;
 mport com.tw ter.search.core.earlyb rd.facets.Facet DMap;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted.DeletedDocs;

/**
 * Base class for atom c Earlyb rd seg nt readers.
 */
publ c abstract class Earlyb rd ndexSeg ntAtom cReader extends LeafReader {
  publ c stat c f nal  nt TERM_NOT_FOUND = -1;

  pr vate f nal DeletedDocs.V ew deletesV ew;
  pr vate f nal Earlyb rd ndexSeg ntData seg ntData;
  protected f nal Earlyb rd ndexSeg ntData.SyncData syncData;

  pr vate F eld nfos f eld nfos;

  /**
   * Creates a new atom c reader for t  Earlyb rd seg nt.
   */
  publ c Earlyb rd ndexSeg ntAtom cReader(Earlyb rd ndexSeg ntData seg ntData) {
    super();
    t .seg ntData = seg ntData;
    t .syncData = seg ntData.getSyncData();
    t .deletesV ew = seg ntData.getDeletedDocs().getV ew();
    // f eld nfos w ll be  n  al zed laz ly  f requ red
    t .f eld nfos = null;
  }

  publ c  nt getSmallestDoc D() {
    return syncData.getSmallestDoc D();
  }

  publ c f nal Facet DMap getFacet DMap() {
    return seg ntData.getFacet DMap();
  }

  publ c f nal Map<Str ng, FacetLabelProv der> getFacetLabelProv ders() {
    return seg ntData.getFacetLabelProv ders();
  }

  publ c AbstractFacetCount ngArray getFacetCount ngArray() {
    return seg ntData.getFacetCount ngArray();
  }

  publ c f nal FacetLabelProv der getFacetLabelProv ders(Sc ma.F eld nfo f eld) {
    Str ng facetNa  = f eld.getF eldType().getFacetNa ();
    return facetNa  != null && seg ntData.getFacetLabelProv ders() != null
            ? seg ntData.getFacetLabelProv ders().get(facetNa ) : null;
  }

  @Overr de
  publ c F eld nfos getF eld nfos() {
     f (f eld nfos == null) {
      // Tw ter n mory ndexReader  s constructed per query, and t  call  s only needed for
      // opt m ze.   wouldn't want to create a new F eld nfos per search, so   deffer  .
      Sc ma sc ma = seg ntData.getSc ma();
      f nal Set<Str ng> f eldSet = Sets.newHashSet(seg ntData.getPerF eldMap().keySet());
      f eldSet.addAll(seg ntData.getDocValuesManager().getDocValueNa s());
      f eld nfos = sc ma.getLuceneF eld nfos( nput ->  nput != null && f eldSet.conta ns( nput));
    }
    return f eld nfos;
  }

  /**
   * Returns t   D that was ass gned to t  g ven term  n
   * {@l nk com.tw ter.search.core.earlyb rd. ndex. nverted. nvertedRealt   ndex}
   */
  publ c abstract  nt getTerm D(Term t) throws  OExcept on;

  /**
   * Returns t  oldest post ng for t  g ven term
   * NOTE: T   thod may return a deleted doc  d.
   */
  publ c abstract  nt getOldestDoc D(Term t) throws  OExcept on;

  @Overr de
  publ c abstract Nu r cDocValues getNu r cDocValues(Str ng f eld) throws  OExcept on;

  /**
   * Determ nes  f t  reader has any docu nts to traverse. Note that    s poss ble for t  t et
   *  D mapper to have docu nts, but for t  reader to not see t m yet.  n t  case, t   thod
   * w ll return false.
   */
  publ c boolean hasDocs() {
    return seg ntData.numDocs() > 0;
  }

  /**
   * Returns t  ne st post ng for t  g ven term
   */
  publ c f nal  nt getNe stDoc D(Term term) throws  OExcept on {
    Post ngsEnum td = post ngs(term);
     f (td == null) {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }

     f (td.nextDoc() != Doc dSet erator.NO_MORE_DOCS) {
      return td.doc D();
    } else {
      return Earlyb rd ndexSeg ntAtom cReader.TERM_NOT_FOUND;
    }
  }

  publ c f nal DeletedDocs.V ew getDeletesV ew() {
    return deletesV ew;
  }

  @Overr de
  publ c f nal F elds getTermVectors( nt doc D) {
    // Earlyb rd does not use term vectors.
    return null;
  }

  publ c Earlyb rd ndexSeg ntData getSeg ntData() {
    return seg ntData;
  }

  publ c Sc ma getSc ma() {
    return seg ntData.getSc ma();
  }
}
