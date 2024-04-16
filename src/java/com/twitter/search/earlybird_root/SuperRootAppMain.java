package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Arrays;
 mport java.ut l.Collect on;

 mport com.google. nject.Module;

 mport com.tw ter.search.common.root.SearchRootAppMa n;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.routers.FacetsRequestRouterModule;
 mport com.tw ter.search.earlyb rd_root.routers.RecencyRequestRouterModule;
 mport com.tw ter.search.earlyb rd_root.routers.RelevanceRequestRouterModule;
 mport com.tw ter.search.earlyb rd_root.routers.TermStatsRequestRouterModule;
 mport com.tw ter.search.earlyb rd_root.routers.TopT etsRequestRouterModule;

publ c class SuperRootAppMa n extends SearchRootAppMa n<SuperRootServer> {
  /**
   * Bo lerplate for t  Java-fr endly AbstractTw terServer
   */
  publ c stat c class Ma n {
    publ c stat c vo d ma n(Str ng[] args) {
      new SuperRootAppMa n().ma n(args);
    }
  }

  @Overr de
  protected Collect on<? extends Module> getAdd  onalModules() {
    return Arrays.asL st(
        new Earlyb rdCommonModule(),
        new SuperRootAppModule(),
        new TermStatsRequestRouterModule(),
        new RecencyRequestRouterModule(),
        new RelevanceRequestRouterModule(),
        new TopT etsRequestRouterModule(),
        new FacetsRequestRouterModule(),
        new QuotaModule());
  }

  @Overr de
  protected Class<SuperRootServer> getSearchRootServerClass() {
    return SuperRootServer.class;
  }

  @Overr de
  protected Class<?> getServ ce faceClass() {
    return Earlyb rdServ ce.Serv ce face.class;
  }
}
