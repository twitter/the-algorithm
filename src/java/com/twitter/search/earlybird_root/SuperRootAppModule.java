package com.tw ter.search.earlyb rd_root;

 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Key;
 mport com.google. nject.Prov des;
 mport com.google. nject.ut l.Prov ders;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers onConf g;
 mport com.tw ter.f nagle.Na ;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.conf g.SearchPengu nVers onsConf g;
 mport com.tw ter.search.common.dark.ResolverProxy;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.root.Logg ngSupport;
 mport com.tw ter.search.common.root.RemoteCl entBu lder;
 mport com.tw ter.search.common.root.SearchRootWarmup;
 mport com.tw ter.search.common.root.Val dat onBehav or;
 mport com.tw ter.search.common.root.WarmupConf g;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd.thr ft.Thr ftT etS ce;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.common. nject onNa s;
 mport com.tw ter.search.earlyb rd_root.f lters.Earlyb rdClusterAva lableF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.MarkT etS ceF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestContextToEarlyb rdRequestF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestTypeCountF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Serv ceExcept onHandl ngF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.Serv ceResponseVal dat onF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.UnsetSuperRootF eldsF lter;
 mport com.tw ter.ut l.Future;

publ c class SuperRootAppModule extends Tw terModule {
  pr vate f nal Flag<Str ng> rootRealt  Flag = createFlag(
      "root-realt  ",
      "",
      "Overr de t  path to root-realt  ",
      Flaggable.ofStr ng());
  pr vate f nal Flag<Str ng> rootProtectedFlag = createFlag(
      "root-protected",
      "",
      "Overr de t  path to root-protected",
      Flaggable.ofStr ng());
  pr vate f nal Flag<Str ng> rootArch veFullFlag = createFlag(
      "root-arch ve-full",
      "",
      "Overr de t  path to root-arch ve-full",
      Flaggable.ofStr ng());
  pr vate f nal Flag<Str ng> pengu nVers onsFlag = createMandatoryFlag(
      "pengu n_vers ons",
      "Pengu n vers ons to be token zed",
      "",
      Flaggable.ofStr ng());

  @Overr de
  publ c vo d conf gure() {
    // SuperRoot uses all clusters, not just one.   b nd Earlyb rdCluster to null to  nd cate that
    // t re  s not one spec f c cluster to use.
    b nd(Key.get(Earlyb rdCluster.class)).toProv der(Prov ders.<Earlyb rdCluster>of(null));

    b nd(Earlyb rdServ ce.Serv ce face.class).to(SuperRootServ ce.class);
  }

  @Prov des
  SearchRootWarmup<Earlyb rdServ ce.Serv ce face, ?, ?> prov desSearchRootWarmup(
      Clock clock,
      WarmupConf g conf g) {
    return new Earlyb rdWarmup(clock, conf g);
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.REALT ME)
  pr vate Earlyb rdServ ce.Serv ce face prov desRealt   face(
      RemoteCl entBu lder<Earlyb rdServ ce.Serv ce face> bu lder,
      ResolverProxy proxy) throws Except on {
    Na  na  = proxy.resolve(rootRealt  Flag.apply());
    return bu lder.createRemoteCl ent(na , "realt  ", "realt  _");
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.REALT ME)
  pr vate Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> prov desRealt  Serv ce(
      @Na d( nject onNa s.REALT ME)
      Earlyb rdServ ce.Serv ce face realt  Serv ce face,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      StatsRece ver statsRece ver,
      SearchDec der dec der) {
    return bu ldCl entServ ce(
        realt  Serv ce face,
        new Earlyb rdClusterAva lableF lter(dec der, Earlyb rdCluster.REALT ME),
        new MarkT etS ceF lter(Thr ftT etS ce.REALT ME_CLUSTER),
        new Serv ceExcept onHandl ngF lter(Earlyb rdCluster.REALT ME),
        new Serv ceResponseVal dat onF lter(Earlyb rdCluster.REALT ME),
        new RequestTypeCountF lter(Earlyb rdCluster.REALT ME.getNa ForStats()),
        requestContextToEarlyb rdRequestF lter,
        new UnsetSuperRootF eldsF lter(),
        new Cl entLatencyF lter(Earlyb rdCluster.REALT ME.getNa ForStats()));
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.FULL_ARCH VE)
  pr vate Earlyb rdServ ce.Serv ce face prov desFullArch ve face(
      RemoteCl entBu lder<Earlyb rdServ ce.Serv ce face> bu lder,
      ResolverProxy proxy) throws Except on {
    Na  na  = proxy.resolve(rootArch veFullFlag.apply());
    return bu lder.createRemoteCl ent(na , "fullarch ve", "full_arch ve_");
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.FULL_ARCH VE)
  pr vate Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> prov desFullArch veServ ce(
      @Na d( nject onNa s.FULL_ARCH VE)
      Earlyb rdServ ce.Serv ce face fullArch veServ ce face,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      StatsRece ver statsRece ver,
      SearchDec der dec der) {
    return bu ldCl entServ ce(
        fullArch veServ ce face,
        new Earlyb rdClusterAva lableF lter(dec der, Earlyb rdCluster.FULL_ARCH VE),
        new MarkT etS ceF lter(Thr ftT etS ce.FULL_ARCH VE_CLUSTER),
        new Serv ceExcept onHandl ngF lter(Earlyb rdCluster.FULL_ARCH VE),
        new Serv ceResponseVal dat onF lter(Earlyb rdCluster.FULL_ARCH VE),
        new RequestTypeCountF lter(Earlyb rdCluster.FULL_ARCH VE.getNa ForStats()),
        requestContextToEarlyb rdRequestF lter,
        // D sable unset follo dUser ds for arch ve s nce arch ve earlyb rds rely on t  f eld
        // to rewr e query to  nclude protected T ets
        new UnsetSuperRootF eldsF lter(false),
        new Cl entLatencyF lter(Earlyb rdCluster.FULL_ARCH VE.getNa ForStats()));
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.PROTECTED)
  pr vate Earlyb rdServ ce.Serv ce face prov desProtected face(
      RemoteCl entBu lder<Earlyb rdServ ce.Serv ce face> bu lder,
      ResolverProxy proxy) throws Except on {
    Na  na  = proxy.resolve(rootProtectedFlag.apply());
    return bu lder.createRemoteCl ent(na , "protected", "protected_");
  }

  @Prov des
  @S ngleton
  @Na d( nject onNa s.PROTECTED)
  pr vate Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> prov desProtectedServ ce(
      @Na d( nject onNa s.PROTECTED)
      Earlyb rdServ ce.Serv ce face protectedServ ce face,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      StatsRece ver statsRece ver,
      SearchDec der dec der) {
    return bu ldCl entServ ce(
        protectedServ ce face,
        new Earlyb rdClusterAva lableF lter(dec der, Earlyb rdCluster.PROTECTED),
        new MarkT etS ceF lter(Thr ftT etS ce.REALT ME_PROTECTED_CLUSTER),
        new Serv ceExcept onHandl ngF lter(Earlyb rdCluster.PROTECTED),
        new Serv ceResponseVal dat onF lter(Earlyb rdCluster.PROTECTED),
        new RequestTypeCountF lter(Earlyb rdCluster.PROTECTED.getNa ForStats()),
        requestContextToEarlyb rdRequestF lter,
        new UnsetSuperRootF eldsF lter(),
        new Cl entLatencyF lter(Earlyb rdCluster.PROTECTED.getNa ForStats()));
  }

  /**
   * Bu lds a F nagle Serv ce based on a Earlyb rdServ ce.Serv ce face.
   */
  pr vate Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> bu ldCl entServ ce(
      f nal Earlyb rdServ ce.Serv ce face serv ce face,
      Earlyb rdClusterAva lableF lter earlyb rdClusterAva lableF lter,
      MarkT etS ceF lter markT etS ceF lter,
      Serv ceExcept onHandl ngF lter serv ceExcept onHandl ngF lter,
      Serv ceResponseVal dat onF lter serv ceResponseVal dat onF lter,
      RequestTypeCountF lter requestTypeCountF lter,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      UnsetSuperRootF eldsF lter unsetSuperRootF eldsF lter,
      Cl entLatencyF lter latencyF lter) {
    Serv ce<Earlyb rdRequest, Earlyb rdResponse> serv ce =
        new Serv ce<Earlyb rdRequest, Earlyb rdResponse>() {

          @Overr de
          publ c Future<Earlyb rdResponse> apply(Earlyb rdRequest requestContext) {
            return serv ce face.search(requestContext);
          }
        };

    //   should apply Serv ceResponseVal dat onF lter f rst, to val date t  response.
    // T n,  f t  response  s val d,   should tag all results w h t  appropr ate t et s ce.
    // Serv ceExcept onHandl ngF lter should co  last, to catch all poss ble except ons (that  re
    // thrown by t  serv ce, or by Serv ceResponseVal dat onF lter and MarkT etS ceF lter).
    //
    // But before   do all of t ,   should apply t  Earlyb rdClusterAva lableF lter to see  f
    //   even need to send t  request to t  cluster.
    return earlyb rdClusterAva lableF lter
        .andT n(serv ceExcept onHandl ngF lter)
        .andT n(markT etS ceF lter)
        .andT n(serv ceResponseVal dat onF lter)
        .andT n(requestTypeCountF lter)
        .andT n(requestContextToEarlyb rdRequestF lter)
        .andT n(latencyF lter)
        .andT n(unsetSuperRootF eldsF lter)
        .andT n(serv ce);
  }

  @Prov des
  publ c Logg ngSupport<Earlyb rdRequest, Earlyb rdResponse> prov deLogg ngSupport(
      SearchDec der dec der) {
    return new Earlyb rdServ ceLogg ngSupport(dec der);
  }

  @Prov des
  publ c Val dat onBehav or<Earlyb rdRequest, Earlyb rdResponse> prov deVal dat onBehav or() {
    return new Earlyb rdServ ceVal dat onBehav or();
  }

  /**
   * Prov des t  pengu n vers ons that   should use to retoken ze t  query  f requested.
   */
  @Prov des
  @S ngleton
  publ c Pengu nVers onConf g prov dePengu nVers ons() {
    return SearchPengu nVers onsConf g.deser al ze(pengu nVers onsFlag.apply());
  }
}
