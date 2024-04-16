package com.tw ter.search.earlyb rd_root.f lters;

 mport com.tw ter.search.earlyb rd.conf g.Serv ngRange;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c  nterface Serv ngRangeProv der {
  /**
   * Get a Serv ngRange  mple ntat on.
   * Usually backed by e  r T er nfoWrapper or RootClusterBoundary nfo.
   */
  Serv ngRange getServ ngRange(Earlyb rdRequestContext requestContext, boolean useBoundaryOverr de);
}
