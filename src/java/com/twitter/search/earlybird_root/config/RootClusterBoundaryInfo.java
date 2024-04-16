package com.tw ter.search.earlyb rd_root.conf g;

 mport java.ut l.Date;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd.conf g.T erServ ngBoundaryEndPo nt;

/**
 * T   boundary  nformat on for a root cluster.
 * Used by Earlyb rdT  RangeF lter.
 */
publ c class RootClusterBoundary nfo  mple nts Serv ngRange {

  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeS nce;
  pr vate f nal T erServ ngBoundaryEndPo nt serv ngRangeMax;

  /**
   * Bu ld a t   boundary  nformat on
   */
  publ c RootClusterBoundary nfo(
      Date startDate,
      Date clusterEndDate,
      Str ng s nce dBoundaryStr ng,
      Str ng max dBoundaryStr ng,
      Clock clock) {
    t .serv ngRangeS nce = T erServ ngBoundaryEndPo nt
        .newT erServ ngBoundaryEndPo nt(s nce dBoundaryStr ng, startDate, clock);
    t .serv ngRangeMax = T erServ ngBoundaryEndPo nt
        .newT erServ ngBoundaryEndPo nt(max dBoundaryStr ng, clusterEndDate, clock);
  }

  publ c long getServ ngRangeS nce d() {
    return serv ngRangeS nce.getBoundaryT et d();
  }

  publ c long getServ ngRangeMax d() {
    return serv ngRangeMax.getBoundaryT et d();
  }

  publ c long getServ ngRangeS nceT  SecondsFromEpoch() {
    return serv ngRangeS nce.getBoundaryT  SecondsFromEpoch();
  }

  publ c long getServ ngRangeUnt lT  SecondsFromEpoch() {
    return serv ngRangeMax.getBoundaryT  SecondsFromEpoch();
  }
}
