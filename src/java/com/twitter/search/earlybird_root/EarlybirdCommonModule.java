package com.tw ter.search.earlyb rd_root;

 mport javax.annotat on.Nullable;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport scala.Part alFunct on;

 mport com.google. nject.Prov des;

 mport org.apac .thr ft.protocol.TProtocolFactory;

 mport com.tw ter.app.Flag;
 mport com.tw ter.app.Flaggable;
 mport com.tw ter.common.ut l.Clock;
 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.mtls.author zat on.server.MtlsServerSess onTrackerF lter;
 mport com.tw ter.f nagle.serv ce.ReqRep;
 mport com.tw ter.f nagle.serv ce.ResponseClass;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter.f nagle.thr ft.R chServerParam;
 mport com.tw ter.f nagle.thr ft.Thr ftCl entRequest;
 mport com.tw ter. nject.Tw terModule;
 mport com.tw ter.search.common.dark.DarkProxy;
 mport com.tw ter.search.common.dark.ResolverProxy;
 mport com.tw ter.search.common.part  on ng.zookeeper.SearchZkCl ent;
 mport com.tw ter.search.common.root.Part  onConf g;
 mport com.tw ter.search.common.root.RemoteCl entBu lder;
 mport com.tw ter.search.common.root.RootCl entServ ceBu lder;
 mport com.tw ter.search.common.root.SearchRootModule;
 mport com.tw ter.search.common.root.ServerSetsConf g;
 mport com.tw ter.search.common.ut l.zookeeper.ZooKeeperProxy;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdRequest;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdFeatureSc ma rger;
 mport com.tw ter.search.earlyb rd_root.f lters.PreCac RequestTypeCountF lter;
 mport com.tw ter.search.earlyb rd_root.f lters.QueryLangStatF lter;

/**
 * Prov des common b nd ngs.
 */
publ c class Earlyb rdCommonModule extends Tw terModule {
  stat c f nal Str ng NAMED_ALT_CL ENT = "alt_cl ent";
  stat c f nal Str ng NAMED_EXP_CLUSTER_CL ENT = "exp_cluster_cl ent";

  pr vate f nal Flag<Str ng> altZkRoleFlag = createFlag(
      "alt_zk_role",
      "",
      "T  alternat ve ZooKeeper role",
      Flaggable.ofStr ng());
  pr vate f nal Flag<Str ng> altZkCl entEnvFlag = createFlag(
      "alt_zk_cl ent_env",
      "",
      "T  alternat ve zk cl ent env ron nt",
      Flaggable.ofStr ng());
  pr vate f nal Flag<Str ng> altPart  onZkPathFlag = createFlag(
      "alt_part  on_zk_path",
      "",
      "T  alternat ve cl ent part  on zk path",
      Flaggable.ofStr ng());

  @Overr de
  publ c vo d conf gure() {
    b nd( n  al zeF lter.class). n(S ngleton.class);
    b nd(PreCac RequestTypeCountF lter.class). n(S ngleton.class);

    b nd(Clock.class).to nstance(Clock.SYSTEM_CLOCK);
    b nd(QueryLangStatF lter.Conf g.class).to nstance(new QueryLangStatF lter.Conf g(100));
  }

  // Used  n SearchRootModule.
  @Prov des
  @S ngleton
  Part alFunct on<ReqRep, ResponseClass> prov deResponseClass f er() {
    return new RootResponseClass f er();
  }

  @Prov des
  @S ngleton
  Serv ce<byte[], byte[]> prov desByteServ ce(
      Earlyb rdServ ce.Serv ce face svc,
      DarkProxy<Thr ftCl entRequest, byte[]> darkProxy,
      TProtocolFactory protocolFactory) {
    return darkProxy.toF lter().andT n(
        new Earlyb rdServ ce.Serv ce(
            svc, new R chServerParam(protocolFactory, SearchRootModule.SCROOGE_BUFFER_S ZE)));
  }

  @Prov des
  @S ngleton
  @Na d(SearchRootModule.NAMED_SERV CE_ NTERFACE)
  Class prov desServ ce nterface() {
    return Earlyb rdServ ce.Serv ce face.class;
  }

  @Prov des
  @S ngleton
  ZooKeeperProxy prov deZookeeperCl ent() {
    return SearchZkCl ent.getSZooKeeperCl ent();
  }

  @Prov des
  @S ngleton
  Earlyb rdFeatureSc ma rger prov deFeatureSc ma rger() {
    return new Earlyb rdFeatureSc ma rger();
  }

  @Prov des
  @S ngleton
  @Nullable
  @Na d(NAMED_ALT_CL ENT)
  ServerSetsConf g prov deAltServerSetsConf g() {
     f (!altZkRoleFlag. sDef ned() || !altZkCl entEnvFlag. sDef ned()) {
      return null;
    }

    return new ServerSetsConf g(altZkRoleFlag.apply(), altZkCl entEnvFlag.apply());
  }

  @Prov des
  @S ngleton
  @Nullable
  @Na d(NAMED_ALT_CL ENT)
  Part  onConf g prov deAltPart  onConf g(Part  onConf g defaultPart  onConf g) {
     f (!altPart  onZkPathFlag. sDef ned()) {
      return null;
    }

    return new Part  onConf g(
        defaultPart  onConf g.getNumPart  ons(), altPart  onZkPathFlag.apply());
  }

  @Prov des
  @S ngleton
  @Nullable
  @Na d(NAMED_ALT_CL ENT)
  RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face> prov deAltRootCl entServ ceBu lder(
      @Na d(NAMED_ALT_CL ENT) @Nullable ServerSetsConf g serverSetsConf g,
      @Na d(SearchRootModule.NAMED_SERV CE_ NTERFACE) Class serv ce face,
      ResolverProxy resolverProxy,
      RemoteCl entBu lder<Earlyb rdServ ce.Serv ce face> remoteCl entBu lder) {
     f (serverSetsConf g == null) {
      return null;
    }

    return new RootCl entServ ceBu lder<>(
        serverSetsConf g, serv ce face, resolverProxy, remoteCl entBu lder);
  }

  @Prov des
  @S ngleton
  @Na d(NAMED_EXP_CLUSTER_CL ENT)
  RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face> prov deExpClusterRootCl entServ ceBu lder(
      @Na d(SearchRootModule.NAMED_EXP_CLUSTER_SERVER_SETS_CONF G)
          ServerSetsConf g serverSetsConf g,
      @Na d(SearchRootModule.NAMED_SERV CE_ NTERFACE) Class serv ce face,
      ResolverProxy resolverProxy,
      RemoteCl entBu lder<Earlyb rdServ ce.Serv ce face> remoteCl entBu lder) {
    return new RootCl entServ ceBu lder<>(
        serverSetsConf g, serv ce face, resolverProxy, remoteCl entBu lder);
  }

  @Prov des
  @S ngleton
  MtlsServerSess onTrackerF lter<Earlyb rdRequest, Earlyb rdResponse>
  prov deMtlsServerSess onTrackerF lter(StatsRece ver statsRece ver) {
    return new MtlsServerSess onTrackerF lter<>(statsRece ver);
  }
}
