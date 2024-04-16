package com.tw ter.t  l neranker.recap_hydrat on

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder
 mport com.tw ter.t  l neranker.repos ory.Cand datesRepos oryBu lder
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on

class RecapHydrat onRepos oryBu lder(conf g: Runt  Conf gurat on, conf gBu lder: Conf gBu lder)
    extends Cand datesRepos oryBu lder(conf g) {

  overr de val cl entSub d = "feature_hydrat on"
  overr de val requestScope: RequestScope = RequestScopes.RecapHydrat onS ce
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutuallyFollow ngUser ds
    )
  overr de val searchProcess ngT  out: Durat on = 200.m ll seconds //[2]

  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = 500.m ll seconds, // [1]
      t  out = 500.m ll seconds, // [1]
      retryPol cy = RetryPol cy.Never
    )

  def apply(): RecapHydrat onRepos ory = {
    val recapHydrat onS ce = new RecapHydrat onS ce(
      g zmoduckCl ent,
      searchCl ent,
      t etyP eLowQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der,
      conf g.underly ngCl ents.contentFeaturesCac ,
      conf g.statsRece ver
    )

    new RecapHydrat onRepos ory(recapHydrat onS ce)
  }
}
