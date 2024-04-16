package com.tw ter.t  l neranker. n_network_t ets

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder
 mport com.tw ter.t  l neranker.repos ory.Cand datesRepos oryBu lder
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.t  l nes.v s b l y.model.C ckedUserActorType
 mport com.tw ter.t  l nes.v s b l y.model.Exclus onReason
 mport com.tw ter.t  l nes.v s b l y.model.V s b l yC ckStatus
 mport com.tw ter.t  l nes.v s b l y.model.V s b l yC ckUser
 mport com.tw ter.ut l.Durat on

object  nNetworkT etRepos oryBu lder {
  val V s b l yRuleExclus ons: Set[Exclus onReason] = Set[Exclus onReason](
    Exclus onReason(
      C ckedUserActorType(So (false), V s b l yC ckUser.S ceUser),
      Set(V s b l yC ckStatus.Blocked)
    )
  )

  pr vate val Earlyb rdT  out = 600.m ll seconds
  pr vate val Earlyb rdRequestT  out = 600.m ll seconds

  /**
   * T  t  outs below are only used for t  Earlyb rd Cluster M grat on
   */
  pr vate val Earlyb rdRealt  CGT  out = 600.m ll seconds
  pr vate val Earlyb rdRealt  CGRequestT  out = 600.m ll seconds
}

class  nNetworkT etRepos oryBu lder(conf g: Runt  Conf gurat on, conf gBu lder: Conf gBu lder)
    extends Cand datesRepos oryBu lder(conf g) {
   mport  nNetworkT etRepos oryBu lder._

  overr de val cl entSub d = "recycled_t ets"
  overr de val requestScope: RequestScope = RequestScopes. nNetworkT etS ce
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutuallyFollow ngUser ds,
      SgsFollowGraphDataF elds.MutedUser ds,
      SgsFollowGraphDataF elds.Ret etsMutedUser ds
    )
  overr de val searchProcess ngT  out: Durat on = 200.m ll seconds

  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = Earlyb rdRequestT  out,
      t  out = Earlyb rdT  out,
      retryPol cy = RetryPol cy.Never
    )

  pr vate lazy val searchCl entForS ceT ets =
    newSearchCl ent(cl ent d = cl entSub d + "_s ce_t ets")

  /** T  Realt  CG cl ents below are only used for t  Earlyb rd Cluster M grat on */
  pr vate def earlyb rdRealt  CGCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdRealt  CgCl ent(
      scope = scope,
      requestT  out = Earlyb rdRealt  CGRequestT  out,
      t  out = Earlyb rdRealt  CGT  out,
      retryPol cy = RetryPol cy.Never
    )
  pr vate val realt  CGCl entSub d = "realt  _cg_recycled_t ets"
  pr vate lazy val searchRealt  CGCl ent =
    newSearchCl ent(earlyb rdRealt  CGCl ent, cl ent d = realt  CGCl entSub d)

  def apply():  nNetworkT etRepos ory = {
    val  nNetworkT etS ce = new  nNetworkT etS ce(
      g zmoduckCl ent,
      searchCl ent,
      searchCl entForS ceT ets,
      t etyP eH ghQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der,
      conf g.underly ngCl ents.contentFeaturesCac ,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes. nNetworkT etS ce,
        reasonsToExclude =  nNetworkT etRepos oryBu lder.V s b l yRuleExclus ons
      ),
      conf g.statsRece ver
    )

    val  nNetworkT etRealt  CGS ce = new  nNetworkT etS ce(
      g zmoduckCl ent,
      searchRealt  CGCl ent,
      searchCl entForS ceT ets, // do not m grate s ce_t ets as t y are sharded by T et D
      t etyP eH ghQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der,
      conf g.underly ngCl ents.contentFeaturesCac ,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes. nNetworkT etS ce,
        reasonsToExclude =  nNetworkT etRepos oryBu lder.V s b l yRuleExclus ons
      ),
      conf g.statsRece ver.scope("replace ntRealt  CG")
    )

    new  nNetworkT etRepos ory( nNetworkT etS ce,  nNetworkT etRealt  CGS ce)
  }
}
