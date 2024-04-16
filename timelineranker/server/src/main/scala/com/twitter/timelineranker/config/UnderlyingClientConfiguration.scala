package com.tw ter.t  l neranker.conf g

 mport com.tw ter.cortex_t et_annotate.thr ftscala.CortexT etQueryServ ce
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.thr ft.Thr ftCl entRequest
 mport com.tw ter.g zmoduck.thr ftscala.{UserServ ce => G zmoduck}
 mport com.tw ter.manhattan.v1.thr ftscala.{ManhattanCoord nator => ManhattanV1}
 mport com.tw ter. rl n.thr ftscala.UserRolesServ ce
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala.UserT etEnt yGraph
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.storehaus.Store
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l neranker.thr ftscala.T  l neRanker
 mport com.tw ter.t  l nes.conf g.Conf gUt ls
 mport com.tw ter.t  l nes.conf g.T  l nesUnderly ngCl entConf gurat on
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.t  l neserv ce.thr ftscala.T  l neServ ce
 mport com.tw ter.t etyp e.thr ftscala.{T etServ ce => T etyP e}
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Try
 mport org.apac .thr ft.protocol.TCompactProtocol

abstract class Underly ngCl entConf gurat on(
  flags: T  l neRankerFlags,
  val statsRece ver: StatsRece ver)
    extends T  l nesUnderly ngCl entConf gurat on
    w h Conf gUt ls {

  lazy val thr ftCl ent d: Cl ent d = t  l neRankerCl ent d()
  overr de lazy val serv ce dent f er: Serv ce dent f er = flags.getServ ce dent f er

  def t  l neRankerCl ent d(scope: Opt on[Str ng] = None): Cl ent d = {
    cl ent dW hScopeOpt("t  l neranker", flags.getEnv, scope)
  }

  def createEarlyb rdCl ent(
    scope: Str ng,
    requestT  out: Durat on,
    t  out: Durat on,
    retryPol cy: RetryPol cy[Try[Noth ng]]
  ): Earlyb rdServ ce. thodPerEndpo nt = {
    val scopedNa  = s"earlyb rd/$scope"

     thodPerEndpo ntCl ent[
      Earlyb rdServ ce.Serv cePerEndpo nt,
      Earlyb rdServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder(
        scopedNa ,
        protocolFactoryOpt on = So (new TCompactProtocol.Factory),
        requ reMutualTls = true)
        .dest("/s/earlyb rd-root-superroot/root-superroot")
        .t  out(t  out)
        .requestT  out(requestT  out)
        .retryPol cy(retryPol cy)
    )
  }

  def createEarlyb rdRealt  CgCl ent(
    scope: Str ng,
    requestT  out: Durat on,
    t  out: Durat on,
    retryPol cy: RetryPol cy[Try[Noth ng]]
  ): Earlyb rdServ ce. thodPerEndpo nt = {
    val scopedNa  = s"earlyb rd/$scope"

     thodPerEndpo ntCl ent[
      Earlyb rdServ ce.Serv cePerEndpo nt,
      Earlyb rdServ ce. thodPerEndpo nt](
      thr ftMuxCl entBu lder(
        scopedNa ,
        protocolFactoryOpt on = So (new TCompactProtocol.Factory),
        requ reMutualTls = true)
        .dest("/s/earlyb rd-rootrealt  cg/root-realt  _cg")
        .t  out(t  out)
        .requestT  out(requestT  out)
        .retryPol cy(retryPol cy)
    )
  }

  def cortexT etQueryServ ceCl ent: CortexT etQueryServ ce. thodPerEndpo nt
  def g zmoduckCl ent: G zmoduck. thodPerEndpo nt
  def manhattanStarbuckCl ent: ManhattanV1. thodPerEndpo nt
  def sgsCl ent: Soc alGraphServ ce. thodPerEndpo nt
  def t  l neRankerForward ngCl ent: T  l neRanker.F nagledCl ent
  def t  l neServ ceCl ent: T  l neServ ce. thodPerEndpo nt
  def t etyP eH ghQoSCl ent: T etyP e. thodPerEndpo nt
  def t etyP eLowQoSCl ent: T etyP e. thodPerEndpo nt
  def userRolesServ ceCl ent: UserRolesServ ce. thodPerEndpo nt
  def contentFeaturesCac : Store[T et d, ContentFeatures]
  def userT etEnt yGraphCl ent: UserT etEnt yGraph.F nagledCl ent
  def stratoCl ent: StratoCl ent

  def darkTraff cProxy: Opt on[Serv ce[Thr ftCl entRequest, Array[Byte]]] = {
     f (flags.darkTraff cDest nat on.tr m.nonEmpty) {
      So (darkTraff cCl ent(flags.darkTraff cDest nat on))
    } else {
      None
    }
  }

}
