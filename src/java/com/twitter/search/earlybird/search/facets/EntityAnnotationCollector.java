package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.L st;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.L sts;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;

 mport com.tw ter.esc rb rd.thr ftjava.T etEnt yAnnotat on;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;

publ c class Ent yAnnotat onCollector extends AbstractFacetTermCollector {
  pr vate L st<T etEnt yAnnotat on> annotat ons = L sts.newArrayL st();

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {

    Str ng term = getTermFromFacet(term D, f eld D,
        Sets.newHashSet(Earlyb rdF eldConstant.ENT TY_ D_F ELD.getF eldNa ()));
     f (Str ngUt ls. sEmpty(term)) {
      return false;
    }

    Str ng[]  dParts = term.spl ("\\.");

    // Only  nclude t  full three-part form of t  ent y  D: "group d.doma n d.ent y d"
    // Exclude t  less-spec f c forms    ndex: "doma n d.ent y d" and "ent y d"
     f ( dParts.length < 3) {
      return false;
    }

    annotat ons.add(new T etEnt yAnnotat on(
        Long.valueOf( dParts[0]),
        Long.valueOf( dParts[1]),
        Long.valueOf( dParts[2])));

    return true;
  }

  @Overr de
  publ c vo d f llResultAndClear(Thr ftSearchResult result) {
    getExtra tadata(result).setEnt yAnnotat ons( mmutableL st.copyOf(annotat ons));
    annotat ons.clear();
  }
}
