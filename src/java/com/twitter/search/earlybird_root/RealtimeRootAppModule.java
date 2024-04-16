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
 mport com.tw ter.search.earlyb rd_root.cach ng.FacetsCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.RecencyCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.RelevanceCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.Str ctRecencyCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TermStatsCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TopT etsCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TopT etsServ cePostProcessor;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;

publ c class Realt  RootAppModule extends Tw terModule {
  pr vate stat c f nal long RECENCY_CACHE_TTL_M LL S = 20000L;
  pr vate stat c f nal long RELEVANCE_CACHE_TTL_M LL S = 20000L;
  pr vate stat c f nal long FACETS_CACHE_TTL_M LL S = 300000L;
  pr vate stat c f nal long TERMSTATS_CACHE_TTL_M LL S = 300000L;

  @Overr de
  publ c vo d conf gure() {
    b nd(Key.get(Earlyb rdCluster.class)).to nstance(Earlyb rdCluster.REALT ME);

    b nd(Earlyb rdServ ceScatterGat rSupport.class)
      .to(Earlyb rdRealt  ScatterGat rSupport.class);

    b nd(Earlyb rdServ ce.Serv ce face.class).to(Realt  RootServ ce.class);
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _recency_root",
        ser al zedKeyPref x, RECENCY_CACHE_TTL_M LL S, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  @S ngleton
  @RelevanceCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deRelevanceCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _relevance_root",
        ser al zedKeyPref x, RELEVANCE_CACHE_TTL_M LL S, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  @S ngleton
  @Str ctRecencyCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deStr ctRecencyCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _str ct_recency_root",
        ser al zedKeyPref x, RECENCY_CACHE_TTL_M LL S, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  @S ngleton
  @FacetsCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deFacetsCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _facets_root",
        ser al zedKeyPref x, FACETS_CACHE_TTL_M LL S, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  @S ngleton
  @TermStatsCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deTermStatsCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _termstats_root",
        ser al zedKeyPref x, TERMSTATS_CACHE_TTL_M LL S, cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  @S ngleton
  @TopT etsCac 
  Cac <Earlyb rdRequest, Earlyb rdResponse> prov deTopT etsCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      @Na d(SearchRootModule.NAMED_SER AL ZED_KEY_PREF X) Str ng ser al zedKeyPref x,
      @Na d(SearchRootModule.NAMED_CACHE_KEY_MAX_BYTES)  nt cac KeyMaxBytes,
      @Na d(SearchRootModule.NAMED_CACHE_VALUE_MAX_BYTES)  nt cac ValueMaxBytes) {
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, "realt  _topt ets_root",
        ser al zedKeyPref x, TopT etsServ cePostProcessor.CACHE_AGE_ N_MS,
        cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  SearchRootWarmup<Earlyb rdServ ce.Serv ce face, ?, ?> prov desSearchRootWarmup(
      Clock clock,
      WarmupConf g conf g) {
    return new Earlyb rdWarmup(clock, conf g);
  }

  @Prov des
  publ c Logg ngSupport<Earlyb rdRequest, Earlyb rdResponse> prov deLogg ngSupport(
      SearchDec der dec der) {
    return new Earlyb rdServ ceLogg ngSupport(dec der);
  }

  @Prov des
  publ c Part  onLogg ngSupport<Earlyb rdRequestContext> prov dePart  onLogg ngSupport() {
    return new Earlyb rdServ cePart  onLogg ngSupport();
  }

  @Prov des
  publ c Val dat onBehav or<Earlyb rdRequest, Earlyb rdResponse> prov deVal dat onBehav or() {
    return new Earlyb rdServ ceVal dat onBehav or();
  }
}
