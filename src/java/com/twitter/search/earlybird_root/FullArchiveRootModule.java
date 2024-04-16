package com.tw ter.search.earlyb rd_root;

 mport java.ut l.L st;
 mport java.ut l.concurrent.T  Un ;

 mport javax.annotat on.Nullable;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Key;
 mport com.google. nject.Prov des;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle. mcac d.JavaCl ent;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.root.Logg ngSupport;
 mport com.tw ter.search.common.root.Part  onConf g;
 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.common.root.RootCl entServ ceBu lder;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.common.root.SearchRootWarmup;
 mport com.tw ter.search.common.root.Spl terServ ce;
 mport com.tw ter.search.common.root.Val dat onBehav or;
 mport com.tw ter.search.common.root.WarmupConf g;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.conf g.T er nfoS ce;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.cach ng.DefaultForcedCac M ssDec der;
 mport com.tw ter.search.earlyb rd_root.cach ng.RecencyCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.RelevanceCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.Str ctRecencyCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TermStatsCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TopT etsCac ;
 mport com.tw ter.search.earlyb rd_root.cach ng.TopT etsServ cePostProcessor;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestContextToEarlyb rdRequestF lter;
 mport com.tw ter.ut l.Future;

 mport stat c com.tw ter.search.earlyb rd_root.Earlyb rdCommonModule.NAMED_ALT_CL ENT;

publ c class FullArch veRootModule extends Tw terModule {
  pr vate stat c f nal Str ng CLUSTER = "arch ve_full";
  pr vate stat c f nal Str ng ALT_TRAFF C_PERCENTAGE_DEC DER_KEY =
      "full_arch ve_alt_cl ent_traff c_percentage";

  pr vate f nal Flag<Boolean> forceAltCl entFlag = createFlag(
      "force_alt_cl ent",
      false,
      "Always sends traff c to t  alt cl ent",
      Flaggable.ofJavaBoolean());

  @Overr de
  publ c vo d conf gure() {
    b nd(Key.get(Earlyb rdCluster.class)).to nstance(Earlyb rdCluster.FULL_ARCH VE);

    b nd(Earlyb rdServ ceScatterGat rSupport.class)
      .to(Earlyb rdFullArch veScatterGat rSupport.class);

    b nd(Earlyb rdServ ce.Serv ce face.class).to(FullArch veRootServ ce.class);
  }

  @Prov des
  Logg ngSupport<Earlyb rdRequest, Earlyb rdResponse> prov deLogg ngSupport(
      SearchDec der dec der) {
    return new Earlyb rdServ ceLogg ngSupport(dec der);
  }

  @Prov des
  Part  onLogg ngSupport<Earlyb rdRequestContext> prov dePart  onLogg ngSupport() {
    return new Earlyb rdServ cePart  onLogg ngSupport();
  }

  @Prov des
  Val dat onBehav or<Earlyb rdRequest, Earlyb rdResponse> prov deVal dat onBehav or() {
    return new Earlyb rdServ ceVal dat onBehav or();
  }

  @Prov des
  @S ngleton
  @Nullable
  @Na d(NAMED_ALT_CL ENT)
  Earlyb rdServ ceCha nBu lder prov deAltEarlyb rdServ ceCha nBu lder(
      @Na d(NAMED_ALT_CL ENT) @Nullable Part  onConf g altPart  onConf g,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      Earlyb rdT erThrottleDec ders t erThrottleDec ders,
      @Na d(SearchRootModule.NAMED_NORMAL ZED_SEARCH_ROOT_NAME) Str ng normal zedSearchRootNa ,
      SearchDec der dec der,
      T er nfoS ce t erConf g,
      @Na d(NAMED_ALT_CL ENT) @Nullable
          RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face> altRootCl entServ ceBu lder,
      Part  onAccessController part  onAccessController,
      StatsRece ver statsRece ver
  ) {
     f (altPart  onConf g == null || altRootCl entServ ceBu lder == null) {
      return null;
    }

    return new Earlyb rdServ ceCha nBu lder(
        altPart  onConf g,
        requestContextToEarlyb rdRequestF lter,
        t erThrottleDec ders,
        normal zedSearchRootNa ,
        dec der,
        t erConf g,
        altRootCl entServ ceBu lder,
        part  onAccessController,
        statsRece ver
    );
  }

  @Prov des
  @S ngleton
  @Nullable
  @Na d(NAMED_ALT_CL ENT)
  Earlyb rdCha nedScatterGat rServ ce prov deAltEarlyb rdCha nedScatterGat rServ ce(
      @Na d(NAMED_ALT_CL ENT) @Nullable Earlyb rdServ ceCha nBu lder altServ ceCha nBu lder,
      Earlyb rdServ ceScatterGat rSupport scatterGat rSupport,
      Part  onLogg ngSupport<Earlyb rdRequestContext> part  onLogg ngSupport
  ) {
     f (altServ ceCha nBu lder == null) {
      return null;
    }

    return new Earlyb rdCha nedScatterGat rServ ce(
        altServ ceCha nBu lder,
        scatterGat rSupport,
        part  onLogg ngSupport
    );
  }

  @Prov des
  @S ngleton
  Serv ce<Earlyb rdRequestContext, L st<Future<Earlyb rdResponse>>>
  prov deEarlyb rdCha nedScatterGat rServ ce(
      Earlyb rdCha nedScatterGat rServ ce cha nedScatterGat rServ ce,
      @Na d(NAMED_ALT_CL ENT) @Nullable
          Earlyb rdCha nedScatterGat rServ ce altCha nedScatterGat rServ ce,
      SearchDec der dec der
  ) {
     f (forceAltCl entFlag.apply()) {
       f (altCha nedScatterGat rServ ce == null) {
        throw new Runt  Except on(
            "alt cl ent cannot be null w n 'force_alt_cl ent'  s set to true");
      } else {
        return altCha nedScatterGat rServ ce;
      }
    }

     f (altCha nedScatterGat rServ ce == null) {
      return cha nedScatterGat rServ ce;
    }

    return new Spl terServ ce<>(
        cha nedScatterGat rServ ce,
        altCha nedScatterGat rServ ce,
        dec der,
        ALT_TRAFF C_PERCENTAGE_DEC DER_KEY
    );
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, CLUSTER + "_recency_root",
        ser al zedKeyPref x, T  Un .HOURS.toM ll s(2), cac KeyMaxBytes, cac ValueMaxBytes);
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, CLUSTER + "_relevance_root",
        ser al zedKeyPref x, T  Un .HOURS.toM ll s(2), cac KeyMaxBytes, cac ValueMaxBytes);
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, CLUSTER + "_str ct_recency_root",
        ser al zedKeyPref x, T  Un .HOURS.toM ll s(2), cac KeyMaxBytes, cac ValueMaxBytes);
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, CLUSTER + "_termstats_root",
        ser al zedKeyPref x, T  Un .M NUTES.toM ll s(5), cac KeyMaxBytes, cac ValueMaxBytes);
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
    return Earlyb rdCac CommonModule.createCac (cl ent, dec der, CLUSTER + "_topt ets_root",
        ser al zedKeyPref x, TopT etsServ cePostProcessor.CACHE_AGE_ N_MS,
        cac KeyMaxBytes, cac ValueMaxBytes);
  }

  @Prov des
  SearchRootWarmup<Earlyb rdServ ce.Serv ce face, ?, ?> prov desSearchRootWarmup(
      Clock clock,
      WarmupConf g conf g) {
    return new Earlyb rdWarmup(clock, conf g);
  }
}
