package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;

 mport com.tw ter.search.core.earlyb rd.facets.FacetAccumulator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetLabelProv der;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;
 mport com.tw ter.search.earlyb rd.search.facets.FacetResultsCollector.Accumulator;

publ c abstract class FacetScorer {
  protected abstract vo d startSeg nt(Earlyb rd ndexSeg ntAtom cReader reader) throws  OExcept on;

  /**
   *  ncre nts facet counts for t  g ven docu nt.
   */
  publ c abstract vo d  ncre ntCounts(Accumulator accumulator,  nt  nternalDoc D)
      throws  OExcept on;

  /**
   * Returns a FacetAccumulator for count ng facets.   w ll use t  g ven FacetLabelProv der
   * for facet result label ng.
   */
  publ c abstract FacetAccumulator<?> getFacetAccumulator(FacetLabelProv der labelProv der);
}
