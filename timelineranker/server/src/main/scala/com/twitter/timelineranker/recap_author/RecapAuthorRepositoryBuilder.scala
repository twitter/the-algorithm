package com.tw ter.t  l neranker.recap_author

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.repos ory.Cand datesRepos oryBu lder
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on

class RecapAuthorRepos oryBu lder(conf g: Runt  Conf gurat on)
    extends Cand datesRepos oryBu lder(conf g) {
  overr de val cl entSub d = "recap_by_author"
  overr de val requestScope: RequestScope = RequestScopes.RecapAuthorS ce
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutuallyFollow ngUser ds,
      SgsFollowGraphDataF elds.MutedUser ds
    )

  /**
   * Budget for process ng w h n t  search root cluster for t  recap_by_author query.
   */
  overr de val searchProcess ngT  out: Durat on = 250.m ll seconds
  pr vate val Earlyb rdT  out = 650.m ll seconds
  pr vate val Earlyb rdRequestT  out = 600.m ll seconds

  pr vate val Earlyb rdRealt  CGT  out = 650.m ll seconds
  pr vate val Earlyb rdRealt  CGRequestT  out = 600.m ll seconds

  /**
   * TLM -> TLR t  out  s 1s for cand date retr eval, so make t  f nagle TLR -> EB t  out
   * a b  shorter than 1s.
   */
  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = Earlyb rdRequestT  out,
      // t  out  s sl ght less than t  l neranker cl ent t  out  n t  l nem xer
      t  out = Earlyb rdT  out,
      retryPol cy = RetryPol cy.Never
    )

  /** T  Realt  CG cl ents below are only used for t  Earlyb rd Cluster M grat on */
  pr vate def earlyb rdRealt  CGCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdRealt  CgCl ent(
      scope = scope,
      requestT  out = Earlyb rdRealt  CGRequestT  out,
      t  out = Earlyb rdRealt  CGT  out,
      retryPol cy = RetryPol cy.Never
    )

  pr vate val realt  CGCl entSub d = "realt  _cg_recap_by_author"
  pr vate lazy val searchRealt  CGCl ent =
    newSearchCl ent(earlyb rdRealt  CGCl ent, cl ent d = realt  CGCl entSub d)

  def apply(): RecapAuthorRepos ory = {
    val recapAuthorS ce = new RecapAuthorS ce(
      g zmoduckCl ent,
      searchCl ent,
      t etyP eLowQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der, // Used to early-enforce v s b l y f lter ng, even though author ds  s part of query
      conf g.underly ngCl ents.contentFeaturesCac ,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes.RecapAuthorS ce
      ),
      conf g.statsRece ver
    )
    val recapAuthorRealt  CGS ce = new RecapAuthorS ce(
      g zmoduckCl ent,
      searchRealt  CGCl ent,
      t etyP eLowQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der, // Used to early-enforce v s b l y f lter ng, even though author ds  s part of query
      conf g.underly ngCl ents.contentFeaturesCac ,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes.RecapAuthorS ce
      ),
      conf g.statsRece ver.scope("replace ntRealt  CG")
    )

    new RecapAuthorRepos ory(recapAuthorS ce, recapAuthorRealt  CGS ce)  
  }
}
