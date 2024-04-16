package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder
 mport com.tw ter.t  l neranker.repos ory.Cand datesRepos oryBu lder
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on

class UtegL kedByT etsRepos oryBu lder(conf g: Runt  Conf gurat on, conf gBu lder: Conf gBu lder)
    extends Cand datesRepos oryBu lder(conf g) {
  overr de val cl entSub d = "uteg_l ked_by_t ets"
  overr de val requestScope: RequestScope = RequestScopes.UtegL kedByT etsS ce
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutuallyFollow ngUser ds,
      SgsFollowGraphDataF elds.MutedUser ds
    )
  overr de val searchProcess ngT  out: Durat on = 400.m ll seconds
  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = 500.m ll seconds,
      t  out = 900.m ll seconds,
      retryPol cy = conf g.underly ngCl ents.DefaultRetryPol cy
    )

  def apply(): UtegL kedByT etsRepos ory = {
    val utegL kedByT etsS ce = new UtegL kedByT etsS ce(
      userT etEnt yGraphCl ent = userT etEnt yGraphCl ent,
      g zmoduckCl ent = g zmoduckCl ent,
      searchCl ent = searchCl ent,
      t etyP eCl ent = t etyP eH ghQoSCl ent,
      user tadataCl ent = user tadataCl ent,
      followGraphDataProv der = followGraphDataProv der,
      contentFeaturesCac  = conf g.underly ngCl ents.contentFeaturesCac ,
      statsRece ver = conf g.statsRece ver
    )

    new UtegL kedByT etsRepos ory(utegL kedByT etsS ce)
  }
}
