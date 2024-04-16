package com.tw ter.search.earlyb rd_root;

 mport javax.annotat on.Nullable;
 mport javax. nject.Na d;
 mport javax. nject.S ngleton;

 mport com.google. nject.Prov des;

 mport com.tw ter.f nagle.Serv ce;
 mport com.tw ter.f nagle.stats.StatsRece ver;
 mport com.tw ter.search.common.dec der.SearchDec der;
 mport com.tw ter.search.common.root.Part  onConf g;
 mport com.tw ter.search.common.root.Part  onLogg ngSupport;
 mport com.tw ter.search.common.root.RequestSuccessStats;
 mport com.tw ter.search.common.root.RootCl entServ ceBu lder;
 mport com.tw ter.search.common.sc ma.earlyb rd.Earlyb rdCluster;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdResponse;
 mport com.tw ter.search.earlyb rd.thr ft.Earlyb rdServ ce;
 mport com.tw ter.search.earlyb rd_root.common.Earlyb rdRequestContext;
 mport com.tw ter.search.earlyb rd_root.f lters.RequestContextToEarlyb rdRequestF lter;

publ c class ProtectedScatterGat rModule extends ScatterGat rModule {
  /**
   * Prov des t  scatterGat rServ ce for t  protected cluster.
   */
  @Prov des
  @S ngleton
  @Na d(NAMED_SCATTER_GATHER_SERV CE)
  @Overr de
  publ c Serv ce<Earlyb rdRequestContext, Earlyb rdResponse> prov deScatterGat rServ ce(
      Earlyb rdServ ceScatterGat rSupport scatterGat rSupport,
      RequestSuccessStats requestSuccessStats,
      Part  onLogg ngSupport<Earlyb rdRequestContext> part  onLogg ngSupport,
      RequestContextToEarlyb rdRequestF lter requestContextToEarlyb rdRequestF lter,
      Part  onAccessController part  onAccessController,
      Part  onConf g part  onConf g,
      RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face> rootCl entServ ceBu lder,
      @Na d(Earlyb rdCommonModule.NAMED_EXP_CLUSTER_CL ENT)
          RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face>
          expClusterRootCl entServ ceBu lder, // unused  n protected roots
      @Na d(Earlyb rdCommonModule.NAMED_ALT_CL ENT) @Nullable Part  onConf g altPart  onConf g,
      @Na d(Earlyb rdCommonModule.NAMED_ALT_CL ENT) @Nullable
          RootCl entServ ceBu lder<Earlyb rdServ ce.Serv ce face> altRootCl entServ ceBu lder,
      StatsRece ver statsRece ver,
      Earlyb rdCluster cluster,
      SearchDec der dec der) {
    return bu ldScatterOrSpl terServ ce(
        scatterGat rSupport,
        requestSuccessStats,
        part  onLogg ngSupport,
        requestContextToEarlyb rdRequestF lter,
        part  onAccessController,
        part  onConf g,
        rootCl entServ ceBu lder,
        altPart  onConf g,
        altRootCl entServ ceBu lder,
        statsRece ver,
        cluster,
        dec der
    );
  }
}
