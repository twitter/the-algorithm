package com.tw ter.search.core.earlyb rd.facets;


/**
 * Counts facet occurrences and prov des t  top  ems
 * at t  end. Actual subclass can  mple nt t  funct onal y d fferently: e.g. by us ng
 * a  ap (pr or y queue) or a hashmap w h prun ng step.
 * T  type R represents t  facet results, wh ch can e.g. be a thr ft class.
 */
publ c abstract class FacetAccumulator<R> {
  /** Called to not fy t  accumulator that t  g ven term D has occurred  n a docu nt
   *  Returns t  current count of t  g ven term D.
   */
  publ c abstract  nt add(long term D,  nt score ncre nt,  nt penalty ncre nt,  nt t epCred);

  /** After h  collect on  s done t  can be called to
   * retr eve t   ems that occurred most often */
  publ c abstract R getTopFacets( nt n);

  /** After h  collect on  s done t  can be called to retr eve all t   ems accumulated
   * (wh ch may not be all that occurred) */
  publ c abstract R getAllFacets();

  /** Called to reset a facet accumulator for re-use.  T   s an opt m zat on
   * wh ch takes advantage of t  fact that t se accumulators may allocate
   * large hash-tables, and   use one per-seg nt, wh ch may be as many as 10-20 **/
  publ c abstract vo d reset(FacetLabelProv der facetLabelProv der);

  /** Language  togram accumulat on and retr eval. T y both have no-op default  mple ntat ons.
   */
  publ c vo d recordLanguage( nt language d) { }

  publ c Language togram getLanguage togram() {
    return Language togram.EMPTY_H STOGRAM;
  }
}
