package com.tw ter.t  l neranker.conf g

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.cortex_t et_annotate.thr ftscala.CortexT etQueryServ ce
 mport com.tw ter.f nagle.ssl.Opportun st cTls
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.g zmoduck.thr ftscala.{UserServ ce => G zmoduck}
 mport com.tw ter.manhattan.v1.thr ftscala.{ManhattanCoord nator => ManhattanV1}
 mport com.tw ter. rl n.thr ftscala.UserRolesServ ce
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yGraph
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.storehaus.Store
 mport com.tw ter.strato.cl ent.Strato
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t  l neranker.cl ents.content_features_cac .ContentFeatures mcac Bu lder
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l neranker.thr ftscala.T  l neRanker
 mport com.tw ter.t  l nes.cl ents. mcac _common.Storehaus mcac Conf g
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l neserv ce.thr ftscala.T  l neServ ce
 mport com.tw ter.t etyp e.thr ftscala.{T etServ ce => T etyP e}
 mport com.tw ter.ut l.T  r
 mport org.apac .thr ft.protocol.TCompactProtocol

class DefaultUnderly ngCl entConf gurat on(flags: T  l neRankerFlags, statsRece ver: StatsRece ver)
    extends Underly ngCl entConf gurat on(flags, statsRece ver) { top =>

  val t  r: T  r = DefaultT  r
  val twCac Pref x = "/srv#/prod/local/cac "

  overr de val cortexT etQueryServ ceCl ent: CortexT etQueryServ ce. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[
      CortexT etQueryServ ce.Serv cePerEndpo nt,
      CortexT etQueryServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder("cortex-t et-query", requ reMutualTls = true)
        .dest("/s/cortex-t et-annotate/cortex-t et-query")
        .requestT  out(200.m ll seconds)
        .t  out(500.m ll seconds)
    )
  }

  overr de val g zmoduckCl ent: G zmoduck. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[G zmoduck.Serv cePerEndpo nt, G zmoduck. thodPerEndpo nt](
      thr ftMuxCl entBu lder("g zmoduck", requ reMutualTls = true)
        .dest("/s/g zmoduck/g zmoduck")
        .requestT  out(400.m ll seconds)
        .t  out(900.m ll seconds)
    )
  }

  overr de lazy val manhattanStarbuckCl ent: ManhattanV1. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[ManhattanV1.Serv cePerEndpo nt, ManhattanV1. thodPerEndpo nt](
      thr ftMuxCl entBu lder("manhattan.starbuck", requ reMutualTls = true)
        .dest("/s/manhattan/starbuck.nat ve-thr ft")
        .requestT  out(600.m ll s)
    )
  }

  overr de val sgsCl ent: Soc alGraphServ ce. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[
      Soc alGraphServ ce.Serv cePerEndpo nt,
      Soc alGraphServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder("soc algraph", requ reMutualTls = true)
        .dest("/s/soc algraph/soc algraph")
        .requestT  out(350.m ll seconds)
        .t  out(700.m ll seconds)
    )
  }

  overr de lazy val t  l neRankerForward ngCl ent: T  l neRanker.F nagledCl ent =
    new T  l neRanker.F nagledCl ent(
      thr ftMuxCl entBu lder(
        T  l neRankerConstants.ForwardedCl entNa ,
        Cl ent d(T  l neRankerConstants.ForwardedCl entNa ),
        protocolFactoryOpt on = So (new TCompactProtocol.Factory()),
        requ reMutualTls = true
      ).dest("/s/t  l neranker/t  l neranker:compactthr ft").bu ld(),
      protocolFactory = new TCompactProtocol.Factory()
    )

  overr de val t  l neServ ceCl ent: T  l neServ ce. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[T  l neServ ce.Serv cePerEndpo nt, T  l neServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder("t  l neserv ce", requ reMutualTls = true)
        .dest("/s/t  l neserv ce/t  l neserv ce")
        .requestT  out(600.m ll seconds)
        .t  out(800.m ll seconds)
    )
  }

  overr de val t etyP eH ghQoSCl ent: T etyP e. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[T etyP e.Serv cePerEndpo nt, T etyP e. thodPerEndpo nt](
      thr ftMuxCl entBu lder("t etyp eH ghQoS", requ reMutualTls = true)
        .dest("/s/t etyp e/t etyp e")
        .requestT  out(450.m ll seconds)
        .t  out(800.m ll seconds),
      maxExtraLoadPercent = So (1.percent)
    )
  }

  /**
   * Prov de less costly T etP e cl ent w h t  trade-off of reduced qual y.  ntended for use cases
   * wh ch are not essent al for successful complet on of t  l ne requests. Currently t  cl ent d ffers
   * from t  h ghQoS endpo nt by hav ng retr es count set to 1  nstead of 2.
   */
  overr de val t etyP eLowQoSCl ent: T etyP e. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[T etyP e.Serv cePerEndpo nt, T etyP e. thodPerEndpo nt](
      thr ftMuxCl entBu lder("t etyp eLowQoS", requ reMutualTls = true)
        .dest("/s/t etyp e/t etyp e")
        .retryPol cy(mkRetryPol cy(1)) // overr de default value
        .requestT  out(450.m ll seconds)
        .t  out(800.m ll seconds),
      maxExtraLoadPercent = So (1.percent)
    )
  }

  overr de val userRolesServ ceCl ent: UserRolesServ ce. thodPerEndpo nt = {
     thodPerEndpo ntCl ent[
      UserRolesServ ce.Serv cePerEndpo nt,
      UserRolesServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder(" rl n", requ reMutualTls = true)
        .dest("/s/ rl n/ rl n")
        .requestT  out(1.second)
    )
  }

  lazy val contentFeaturesCac : Store[T et d, ContentFeatures] =
    new ContentFeatures mcac Bu lder(
      conf g = new Storehaus mcac Conf g(
        destNa  = s"$twCac Pref x/t  l nes_content_features:t mcac s",
        keyPref x = "",
        requestT  out = 50.m ll seconds,
        numTr es = 1,
        globalT  out = 60.m ll seconds,
        tcpConnectT  out = 50.m ll seconds,
        connect onAcqu s  onT  out = 25.m ll seconds,
        numPend ngRequests = 100,
         sReadOnly = false,
        serv ce dent f er = serv ce dent f er
      ),
      ttl = 48.h s,
      statsRece ver
    ).bu ld

  overr de val userT etEnt yGraphCl ent: UserT etEnt yGraph.F nagledCl ent =
    new UserT etEnt yGraph.F nagledCl ent(
      thr ftMuxCl entBu lder("user_t et_ent y_graph", requ reMutualTls = true)
        .dest("/s/cassowary/user_t et_ent y_graph")
        .retryPol cy(mkRetryPol cy(2))
        .requestT  out(300.m ll seconds)
        .bu ld()
    )

  overr de val stratoCl ent: StratoCl ent =
    Strato.cl ent.w hMutualTls(serv ce dent f er, Opportun st cTls.Requ red).bu ld()
}
