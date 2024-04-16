package com.tw ter.t  l neranker.ent y_t ets

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder
 mport com.tw ter.t  l neranker.repos ory.Cand datesRepos oryBu lder
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on

class Ent yT etsRepos oryBu lder(conf g: Runt  Conf gurat on, conf gBu lder: Conf gBu lder)
    extends Cand datesRepos oryBu lder(conf g) {

  // Default cl ent  d for t  repos ory  f t  upstream requests doesn't  nd cate one.
  overr de val cl entSub d = "commun y"
  overr de val requestScope: RequestScope = RequestScopes.Ent yT etsS ce
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutuallyFollow ngUser ds,
      SgsFollowGraphDataF elds.MutedUser ds
    )

  /**
   * [1] t  out  s der ved from p9999 TLR <-> Earlyb rd latency and shall be less than
   *     request t  out of t  l neranker cl ent w h n downstream t  l nem xer, wh ch  s
   *     1s now
   *
   * [2] process ng t  out  s less than request t  out [1] w h 100ms space for network ng and
   *     ot r t  s such as gc
   */
  overr de val searchProcess ngT  out: Durat on = 550.m ll seconds // [2]
  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = 650.m ll seconds, // [1]
      t  out = 900.m ll seconds, // [1]
      retryPol cy = conf g.underly ngCl ents.DefaultRetryPol cy
    )

  def apply(): Ent yT etsRepos ory = {
    val ent yT etsS ce = new Ent yT etsS ce(
      g zmoduckCl ent,
      searchCl ent,
      t etyP eH ghQoSCl ent,
      user tadataCl ent,
      followGraphDataProv der,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes.Ent yT etsS ce
      ),
      conf g.underly ngCl ents.contentFeaturesCac ,
      conf g.statsRece ver
    )

    new Ent yT etsRepos ory(ent yT etsS ce)
  }
}
