package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l neranker.conf g.RequestScopes
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.para ters.Conf gBu lder
 mport com.tw ter.t  l neranker.para ters.revchron.ReverseChronT  l neQueryContextBu lder
 mport com.tw ter.t  l neranker.para ters.ut l.RequestContextBu lder mpl
 mport com.tw ter.t  l neranker.s ce.ReverseChronHo T  l neS ce
 mport com.tw ter.t  l neranker.v s b l y.RealGraphFollowGraphDataProv der
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on

class ReverseChronHo T  l neRepos oryBu lder(
  conf g: Runt  Conf gurat on,
  conf gBu lder: Conf gBu lder)
    extends Cand datesRepos oryBu lder(conf g) {

  overr de val cl entSub d = "ho _mater al zat on"
  overr de val requestScope: RequestScope = RequestScopes.Ho T  l neMater al zat on
  overr de val followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet =
    SgsFollowGraphDataF elds.ValueSet(
      SgsFollowGraphDataF elds.Follo dUser ds,
      SgsFollowGraphDataF elds.MutedUser ds,
      SgsFollowGraphDataF elds.Ret etsMutedUser ds
    )
  overr de val searchProcess ngT  out: Durat on = 800.m ll seconds // [3]

  overr de def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt =
    conf g.underly ngCl ents.createEarlyb rdCl ent(
      scope = scope,
      requestT  out = 1.second, // [1]
      t  out = 1900.m ll seconds, // [2]
      retryPol cy = conf g.underly ngCl ents.DefaultRetryPol cy
    )

  val realGraphFollowGraphDataProv der = new RealGraphFollowGraphDataProv der(
    followGraphDataProv der,
    conf g.cl entWrapperFactor es.realGraphCl entFactory
      .scope(RequestScopes.ReverseChronHo T  l neS ce),
    conf g.cl entWrapperFactor es.soc alGraphCl entFactory
      .scope(RequestScopes.ReverseChronHo T  l neS ce),
    conf g.dec derGateBu lder. dGate(Dec derKey.Supple ntFollowsW hRealGraph),
    conf g.statsRece ver.scope(RequestScopes.ReverseChronHo T  l neS ce.scope)
  )

  def apply(): ReverseChronHo T  l neRepos ory = {
    val reverseChronT  l neS ce = new ReverseChronHo T  l neS ce(
      searchCl ent,
      realGraphFollowGraphDataProv der,
      cl entFactor es.v s b l yEnforcerFactory.apply(
        V s b l yRules,
        RequestScopes.ReverseChronHo T  l neS ce
      ),
      conf g.statsRece ver
    )

    val contextBu lder = new ReverseChronT  l neQueryContextBu lder(
      conf gBu lder.rootConf g,
      conf g,
      new RequestContextBu lder mpl(conf g.conf gAp Conf gurat on.requestContextFactory)
    )

    new ReverseChronHo T  l neRepos ory(
      reverseChronT  l neS ce,
      contextBu lder
    )
  }
}
