package com.tw ter.search.earlyb rd.search.facets;

 mport java. o. OExcept on;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.core.earlyb rd.facets.CSFFacetCount erator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount erator;
 mport com.tw ter.search.core.earlyb rd.facets.FacetCount eratorFactory;
 mport com.tw ter.search.core.earlyb rd. ndex.Earlyb rd ndexSeg ntAtom cReader;

/**
 * Factory of {@l nk FacetCount erator}  nstances for t et search.
 *   prov des a spec al  erator for t  ret ets facet.
 */
publ c f nal class T etSearchFacetCount eratorFactory extends FacetCount eratorFactory {
  publ c stat c f nal T etSearchFacetCount eratorFactory FACTORY =
      new T etSearchFacetCount eratorFactory();

  pr vate T etSearchFacetCount eratorFactory() {
  }

  @Overr de
  publ c FacetCount erator getFacetCount erator(
      Earlyb rd ndexSeg ntAtom cReader reader,
      Sc ma.F eld nfo f eld nfo) throws  OExcept on {
    Precond  ons.c ckNotNull(reader);
    Precond  ons.c ckNotNull(f eld nfo);
    Precond  ons.c ckArgu nt(f eld nfo.getF eldType(). sUseCSFForFacetCount ng());

    Str ng facetNa  = f eld nfo.getF eldType().getFacetNa ();

     f (Earlyb rdF eldConstant.RETWEETS_FACET.equals(facetNa )) {
      return new Ret etFacetCount erator(reader, f eld nfo);
    } else {
      return new CSFFacetCount erator(reader, f eld nfo);
    }
  }
}
