package com.tw ter.search.core.earlyb rd.facets;

 mport java.ut l.HashMap;
 mport java.ut l.Map;

 mport com.google.common.base.Precond  ons;

 mport org.slf4j.Logger;
 mport org.slf4j.LoggerFactory;

 mport com.tw ter.search.common.sc ma.base.Earlyb rdF eldType;
 mport com.tw ter.search.common.sc ma.base. ndexedNu r cF eldSett ngs;
 mport com.tw ter.search.common.sc ma.base.Sc ma;
 mport com.tw ter.search.common.sc ma.thr ftjava.Thr ftNu r cType;
 mport com.tw ter.search.core.earlyb rd. ndex. nverted. nverted ndex;

/**
 * A ut l y class for select ng  erators and label prov ders
 * for facets.
 *
 */
publ c abstract class FacetUt l {
  pr vate stat c f nal Logger LOG = LoggerFactory.getLogger(FacetUt l.class);

  pr vate FacetUt l() {
    // unused
  }

  /**
   * A ut l y  thod for choos ng t  r ght facet label prov der based on t  Earlyb rdF eldType.
   * Takes  n a  nverted ndex s nce so  facet label prov ders are or depend on t   nverted
   *  ndex.
   * Should never return null.
   *
   * @param f eldType A F eldType for t  facet
   * @param  nvertedF eld T   nverted  ndex assoc ated w h t  facet. May be null.
   * @return A non-null FacetLabelProv der
   */
  publ c stat c FacetLabelProv der chooseFacetLabelProv der(
      Earlyb rdF eldType f eldType,
       nverted ndex  nvertedF eld) {
    Precond  ons.c ckNotNull(f eldType);

    //  n t  case ne  r  nverted  ndex ex st ng nor us ng CSF,
    // return FacetLabelProv der. naccess bleFacetLabelProv der to throw except on
    // more  an ngfully and expl c ly.
     f ( nvertedF eld == null && !f eldType. sUseCSFForFacetCount ng()) {
      return new FacetLabelProv der. naccess bleFacetLabelProv der(f eldType.getFacetNa ());
    }

     f (f eldType. sUseCSFForFacetCount ng()) {
      return new FacetLabelProv der. dent yFacetLabelProv der();
    }
     ndexedNu r cF eldSett ngs nu r cSett ngs = f eldType.getNu r cF eldSett ngs();
     f (nu r cSett ngs != null && nu r cSett ngs. sUseTw terFormat()) {
       f (nu r cSett ngs.getNu r cType() == Thr ftNu r cType. NT) {
        return new FacetLabelProv der. ntTermFacetLabelProv der( nvertedF eld);
      } else  f (nu r cSett ngs.getNu r cType() == Thr ftNu r cType.LONG) {
        return nu r cSett ngs. sUseSortableEncod ng()
            ? new FacetLabelProv der.SortedLongTermFacetLabelProv der( nvertedF eld)
            : new FacetLabelProv der.LongTermFacetLabelProv der( nvertedF eld);
      } else {
        Precond  ons.c ckState(false,
            "Should never be reac d,  nd cates  ncomplete handl ng of d fferent k nds of facets");
        return null;
      }
    } else {
      return  nvertedF eld;
    }
  }

  /**
   * Get seg nt-spec f c facet label prov ders based on t  sc ma
   * and on t  f eldTo nverted ndexMapp ng for t  seg nt.
   * T se w ll be used by facet accumulators to get t  text of t  term Ds
   *
   * @param sc ma t  sc ma, for  nfo on f elds and facets
   * @param f eldTo nverted ndexMapp ng map of f elds to t  r  nverted  nd ces
   * @return facet label prov der map
   */
  publ c stat c Map<Str ng, FacetLabelProv der> getFacetLabelProv ders(
      Sc ma sc ma,
      Map<Str ng,  nverted ndex> f eldTo nverted ndexMapp ng) {

    HashMap<Str ng, FacetLabelProv der> facetLabelProv derBu lder
        = new HashMap<>();

    for (Sc ma.F eld nfo f eld nfo : sc ma.getFacetF elds()) {
      Earlyb rdF eldType f eldType = f eld nfo.getF eldType();
      Precond  ons.c ckNotNull(f eldType);
      Str ng f eldNa  = f eld nfo.getNa ();
      Str ng facetNa  = f eldType.getFacetNa ();
       nverted ndex  nverted ndex = f eldTo nverted ndexMapp ng.get(f eldNa );
       f ( nverted ndex == null && !f eldType. sUseCSFForFacetCount ng()) {
        LOG.warn("No docs  n seg nt had f eld " + f eldNa 
                + "  ndexed for facet " + facetNa 
                + " so  naccess bleFacetLabelProv der w ll be prov ded."
        );
      }
      facetLabelProv derBu lder.put(facetNa , Precond  ons.c ckNotNull(
          chooseFacetLabelProv der(f eldType,  nverted ndex)));
    }

    return facetLabelProv derBu lder;
  }
}
