package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport org.apac .lucene. ndex.Nu r cDocValues;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * An  erator that looks up t  term D from t  appropr ate CSF
 */
publ c class CSFFacetCount erator extends FacetCount erator {
  pr vate f nal  nt f eld D;
  pr vate f nal Nu r cDocValues nu r cDocValues;

  /**
   * Creates a new  erator for t  g ven facet csf f eld.
   */
  publ c CSFFacetCount erator(
      Earlyb rd ndexSeg ntAtom cReader reader,
      Sc ma.F eld nfo facetF eld nfo) throws  OExcept on {
    Facet DMap.FacetF eld facetF eld = reader.getFacet DMap().getFacetF eld(facetF eld nfo);
    Precond  ons.c ckNotNull(facetF eld);
    t .f eld D = facetF eld.getFacet d();
    nu r cDocValues = reader.getNu r cDocValues(facetF eld nfo.getNa ());
    Precond  ons.c ckNotNull(nu r cDocValues);
  }

  @Overr de
  publ c vo d collect( nt  nternalDoc D) throws  OExcept on {
     f (nu r cDocValues.advanceExact( nternalDoc D)) {
      long term D = nu r cDocValues.longValue();
       f (shouldCollect( nternalDoc D, term D)) {
        collect( nternalDoc D, term D, f eld D);
      }
    }
  }

  /**
   * Subclasses should overr de  f t y need to restr ct t  docs or term Ds
   * that t y collect on. For example, t se may need to overr de  f
   *  1) Not all docs set t  f eld, so   should not collect on
   *     t  default value of 0
   *  2) T  sa  CSF f eld  ans d fferent th ngs ( n part cular, shared_status_ d  ans
   *     ret et OR reply parent  d) so   need to do so  ot r c ck to determ ne  f   should
   *     collect
   *
   * @return w t r   should collect on t  doc/term D
   */
  protected boolean shouldCollect( nt  nternalDoc D, long term D) throws  OExcept on {
    return true;
  }
}
