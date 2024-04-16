package com.tw ter.search.earlyb rd_root;

 mport com.google.common.base.Precond  ons;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.search.common.root.WarmupConf g;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;

publ c class Earlyb rdProtectedWarmup extends Earlyb rdWarmup {

  publ c Earlyb rdProtectedWarmup(Clock clock, WarmupConf g conf g) {
    super(clock, conf g);
  }

  /**
   * T  protected cluster requ res all quer es to spec fy a fromUser dF lter and a searc r d.
   */
  @Overr de
  protected Earlyb rdRequest createRequest( nt request d) {
    Earlyb rdRequest request = super.createRequest(request d);

    Precond  ons.c ckState(request. sSetSearchQuery());
    request.getSearchQuery().addToFromUser DF lter64(request d);
    request.getSearchQuery().setSearc r d(0L);

    return request;
  }
}
