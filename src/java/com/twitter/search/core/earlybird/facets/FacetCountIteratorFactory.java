package com.tw ter.search.core.earlyb rd.facets;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * A factory for {@l nk FacetCount erator}s.
 */
publ c abstract class FacetCount eratorFactory {
  /**
   * For a f eld that  s be ng faceted on and for wh ch   should use a CSF for facet count ng,
   * return t   erator   should use for count ng.
   *
   * @param reader T  reader to use w n gett ng CSF values
   * @param f eld nfo T  Sc ma.F eld nfo correspond ng to t  facet  're count ng
   * @return An  erator for t  f eld
   */
  publ c abstract FacetCount erator getFacetCount erator(
      Earlyb rd ndexSeg ntAtom cReader reader,
      Sc ma.F eld nfo f eld nfo) throws  OExcept on;
}
