package com.tw ter.search.earlyb rd_root;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Prov des;

 mport com.tw ter.f nagle. mcac d.JavaCl ent;
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.cach ng.Cac ;
 mport com.tw ter.search.common.cach ng.Earlyb rdCac Ser al zer;
 mport com.tw ter.search.common.cach ng.SearchCac Bu lder;
 mport com.tw ter.search.common.cach ng.Search mcac Cl entConf g;
 mport com.tw ter.search.common.cach ng.Search mcac Cl entFactory;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd_root.cach ng.Cac CommonUt l;
 mport com.tw ter.search.earlyb rd_root.cach ng.Cac Stats;
 mport com.tw ter.search.earlyb rd_root.cach ng.DefaultForcedCac M ssDec der;
 mport com.tw ter.search.earlyb rd_root.f lters.PostCac RequestTypeCountF lter;
 mport com.tw ter.ut l.Durat on;

/**
 * Prov des common b nd ngs for cac  related modules.
 */
publ c class Earlyb rdCac CommonModule extends Tw terModule {
  pr vate stat c f nal Str ng CACHE_VERS ON = "1";

  @Overr de
  publ c vo d conf gure() {
    b nd(PostCac RequestTypeCountF lter.class). n(S ngleton.class);
    b nd(DefaultForcedCac M ssDec der.class). n(S ngleton.class);
  }

  @Prov des
  @S ngleton
  @Na d(Cac CommonUt l.NAMED_MAX_CACHE_RESULTS)
   nteger prov deMaxCac Results() {
    return 100;
  }

  @Prov des
  @S ngleton
  JavaCl ent prov de mCac Cl ent(
      StatsRece ver statsRece ver, Serv ce dent f er serv ce dent f er) {
    Search mcac Cl entConf g conf g = new Search mcac Cl entConf g();
    conf g.connectT  outMs = Durat on.fromM ll seconds(100);
    conf g.requestT  outMs = Durat on.fromM ll seconds(100);
    conf g.fa lureAccrualFa luresNumber = 150;
    conf g.fa lureAccrualFa luresDurat onM ll s = 30000;
    conf g.fa lureAccrualDurat on = Durat on.fromM ll seconds(60000);

    return Search mcac Cl entFactory.createMtlsCl ent(
        "",
        "earlyb rd_root",
        statsRece ver,
        conf g,
        serv ce dent f er
    );
  }

  /**
   * Create a new Earlyb rd cac .
   *
   * @param cl ent t   mcac  cl ent to use.
   * @param dec der t  dec der to use for t  cac .
   * @param cac Pref x t  common cac  pref x for t  cac  type.
   * @param ser al zedKeyPref x t  common cac  pref x for t  cluster.
   * @param cac Exp ryM ll s cac  entry ttl  n m ll seconds.
   */
  stat c Cac <Earlyb rdRequest, Earlyb rdResponse> createCac (
      JavaCl ent cl ent,
      DefaultForcedCac M ssDec der dec der,
      Str ng cac Pref x,
      Str ng ser al zedKeyPref x,
      long cac Exp ryM ll s,
       nt cac KeyMaxBytes,
       nt cac ValueMaxBytes) {
    return new SearchCac Bu lder<Earlyb rdRequest, Earlyb rdResponse>(
        CACHE_VERS ON,
        cl ent,
        cac Pref x,
        ser al zedKeyPref x,
        cac Exp ryM ll s)
        .w hMaxKeyBytes(cac KeyMaxBytes)
        .w hMaxValueBytes(cac ValueMaxBytes)
        .w hRequestT  outCounter(Cac Stats.REQUEST_T MEOUT_COUNTER)
        .w hRequestFa ledCounter(Cac Stats.REQUEST_FA LED_COUNTER)
        .w hCac Ser al zer(new Earlyb rdCac Ser al zer())
        .w hForceCac M ssDec der(dec der)
        .w h nProcessCac ()
        .bu ld();
  }
}
