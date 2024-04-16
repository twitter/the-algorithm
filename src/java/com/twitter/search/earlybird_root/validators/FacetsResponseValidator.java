package com.tw ter.search.earlyb rd_root.val dators;

 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.ut l.Future;

publ c class FacetsResponseVal dator  mple nts Serv ceResponseVal dator<Earlyb rdResponse> {

  pr vate f nal Earlyb rdCluster cluster;

  /**
   * Val dator for facets responses
   */
  publ c FacetsResponseVal dator(Earlyb rdCluster cluster) {
    t .cluster = cluster;
  }

  @Overr de
  publ c Future<Earlyb rdResponse> val date(Earlyb rdResponse response) {
     f (!response. sSetSearchResults() || !response.getSearchResults(). sSetResults()) {
      return Future.except on(
          new  llegalStateExcept on(cluster + " d dn't set search results."));
    }

     f (!response. sSetFacetResults()) {
      return Future.except on(
          new  llegalStateExcept on(
              cluster + " facets response does not have t  facetResults f eld set."));
    }

     f (response.getFacetResults().getFacetF elds(). sEmpty()) {
      return Future.except on(
          new  llegalStateExcept on(
              cluster + " facets response does not have any facet f elds set."));
    }

    return Future.value(response);
  }
}
