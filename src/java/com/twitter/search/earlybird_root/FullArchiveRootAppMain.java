package com.tw ter.search.earlyb rd_root;

 mport java.ut l.Arrays;
 mport java.ut l.Collect on;

 mport com.google. nject.Module;

 mport com.tw ter.search.common.root.SearchRootAppMa n;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;

publ c class FullArch veRootAppMa n extends SearchRootAppMa n<FullArch veRootServer> {
  /**
   * Bo lerplate for t  Java-fr endly AbstractTw terServer
   */
  publ c stat c class Ma n {
    publ c stat c vo d ma n(Str ng[] args) {
      new FullArch veRootAppMa n().ma n(args);
    }
  }

  @Overr de
  protected Collect on<? extends Module> getAdd  onalModules() {
    return Arrays.asL st(
        new Earlyb rdCommonModule(),
        new Earlyb rdCac CommonModule(),
        new FullArch veRootModule(),
        new QuotaModule()
    );
  }

  @Overr de
  protected Class<FullArch veRootServer> getSearchRootServerClass() {
    return FullArch veRootServer.class;
  }

  @Overr de
  protected Class<?> getServ ce faceClass() {
    return Earlyb rdServ ce.Serv ce face.class;
  }
}
