package com.tw ter.search.earlyb rd_root;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Key;
 mport com.google. nject.Prov des;

 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle. mcac d.JavaCl ent;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.root.Logg ngSupport;
 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.common.root.SearchRootWarmup;
 mport com.tw ter.search.common.root.Val dat onBehav or;
 mport com.tw ter.search.common.root.WarmupConf g;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.cach ng.DefaultForcedCac M ssDec der;
 mport com.tw ter.search.earlyb rd_root.cach ng.RecencyCac ;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class ProtectedRootAppModule extends Tw terModule {
  @Overr de
  publ c vo d conf gure() {
    b nd(Key.get(Earlyb rdCluster.class)).to nstance(Earlyb rdCluster.PROTECTED);

    b nd(Earlyb rdServ ceScatterGat rSupport.class)
        .to(Earlyb rdProtectedScatterGat rSupport.class);

    b nd(Earlyb rdServ ce.Serv ce face.class).to(ProtectedRootServ ce.class);
  }

  @Prov des
  @S ngleton
  Logg ngSupport<Earlyb rdRequest, Earlyb rdResponse> prov deLogg ngSupport(
      SearchDec der dec der) {
    return new Earlyb rdServ ceLogg ngSupport(dec der);
  }

  @Prov des
  @S ngleton
  Part  onLogg ngSupport<Earlyb rdRequestContext> prov dePart  onLogg ngSupport() {
    return new Earlyb rdServ cePart  onLogg ngSupport();
  }

  @Prov des
  @S ngleton
  Val dat onBehav or<Earlyb rdRequest, Earlyb rdResponse> prov desVal dat on() {
    return new Earlyb rdProtectedVal dat onBehav or();
  }

  @Prov des
  @S ngleton
  @RecencyCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deRecencyCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule
        .createCac (cl ent, dec der, "realt  _protected_recency_root", ser al zedKeyPref x,
            20000L, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  SearchRootWarmup<Earlyb rdServ ce.Serv ce face, ?, ?> prov desSearchRootWarmup(
      Clock clock,
      WarmupConf g conf g) {
    return new Earlyb rdProtectedWarmup(clock, conf g);
  }
}
