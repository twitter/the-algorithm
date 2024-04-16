package com.tw ter.search.earlyb rd.search.facets;

 mport java.ut l.ArrayL st;
 mport java.ut l.L st;

 mport com.google.common.collect. mmutableL st;
 mport com.google.common.collect.Sets;

 mport org.apac .commons.lang.Str ngUt ls;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdF eldConstants.Earlyb rdF eldConstant;
 mport com.tw ter.search.earlyb rd.part  on.Aud oSpaceTable;
 mport com.tw ter.search.earlyb rd.thr ft.Aud oSpaceState;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResult;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftSearchResultAud oSpace;

publ c class SpaceFacetCollector extends AbstractFacetTermCollector {
  pr vate f nal L st<Thr ftSearchResultAud oSpace> spaces = new ArrayL st<>();

  pr vate f nal Aud oSpaceTable aud oSpaceTable;

  publ c SpaceFacetCollector(Aud oSpaceTable aud oSpaceTable) {
    t .aud oSpaceTable = aud oSpaceTable;
  }

  @Overr de
  publ c boolean collect( nt doc D, long term D,  nt f eld D) {

    Str ng space d = getTermFromFacet(term D, f eld D,
        Sets.newHashSet(Earlyb rdF eldConstant.SPACES_FACET));
     f (Str ngUt ls. sEmpty(space d)) {
      return false;
    }

    spaces.add(new Thr ftSearchResultAud oSpace(space d,
        aud oSpaceTable. sRunn ng(space d) ? Aud oSpaceState.RUNN NG
            : Aud oSpaceState.ENDED));

    return true;
  }

  @Overr de
  publ c vo d f llResultAndClear(Thr ftSearchResult result) {
    getExtra tadata(result).setSpaces( mmutableL st.copyOf(spaces));
    spaces.clear();
  }
}
