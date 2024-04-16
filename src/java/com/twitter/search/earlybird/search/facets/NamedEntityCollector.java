package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.L st;
 mport java.ut l.Map;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect. mmutableMap;
 mport com.google.common.collect.L sts;

 mport org.apac .commons.lang.Str ngUt ls;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.thr ft.Na dEnt yS ce;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultNa dEnt y;

publ c class Na dEnt yCollector extends AbstractFacetTermCollector {
  pr vate stat c f nal Map<Str ng, Na dEnt yS ce> NAMED_ENT TY_W TH_TYPE_F ELDS =
       mmutableMap.of(
          Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_TEXT_F ELD.getF eldNa (),
          Na dEnt yS ce.TEXT,
          Earlyb rdF eldConstant.NAMED_ENT TY_W TH_TYPE_FROM_URL_F ELD.getF eldNa (),
          Na dEnt yS ce.URL);

  pr vate L st<Thr ftSearchResultNa dEnt y> na dEnt  es = L sts.newArrayL st();

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {

    Str ng term = getTermFromFacet(term D, f eld D, NAMED_ENT TY_W TH_TYPE_F ELDS.keySet());
     f (Str ngUt ls. sEmpty(term)) {
      return false;
    }

     nt  ndex = term.last ndexOf(":");
    na dEnt  es.add(new Thr ftSearchResultNa dEnt y(
        term.substr ng(0,  ndex),
        term.substr ng( ndex + 1),
        NAMED_ENT TY_W TH_TYPE_F ELDS.get(f ndFacetNa (f eld D))));

    return true;
  }

  @Overr de
  publ c vo d f llResultAndClear(Thr ftSearchResult result) {
    getExtra tadata(result).setNa dEnt  es( mmutableL st.copyOf(na dEnt  es));
    na dEnt  es.clear();
  }
}
