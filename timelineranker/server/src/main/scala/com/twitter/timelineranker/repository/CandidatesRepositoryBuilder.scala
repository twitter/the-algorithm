package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.search.earlyb rd.thr ftscala.Earlyb rdServ ce
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.conf g.Runt  Conf gurat on
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.v s b l y.SgsFollowGraphDataF elds
 mport com.tw ter.t  l neranker.v s b l y.ScopedSgsFollowGraphDataProv derFactory
 mport com.tw ter.t  l nes.cl ents.relevance_search.ScopedSearchCl entFactory
 mport com.tw ter.t  l nes.cl ents.relevance_search.SearchCl ent
 mport com.tw ter.t  l nes.cl ents.user_t et_ent y_graph.UserT etEnt yGraphCl ent
 mport com.tw ter.t  l nes.ut l.stats.RequestScope
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.t  l neranker.conf g.Cl entWrapperFactor es
 mport com.tw ter.t  l neranker.conf g.Underly ngCl entConf gurat on
 mport com.tw ter.t  l neranker.v s b l y.FollowGraphDataProv der
 mport com.tw ter.t  l nes.cl ents.g zmoduck.G zmoduckCl ent
 mport com.tw ter.t  l nes.cl ents.manhattan.ManhattanUser tadataCl ent
 mport com.tw ter.t  l nes.cl ents.t etyp e.T etyP eCl ent

abstract class Cand datesRepos oryBu lder(conf g: Runt  Conf gurat on) extends Repos oryBu lder {

  def earlyb rdCl ent(scope: Str ng): Earlyb rdServ ce. thodPerEndpo nt
  def searchProcess ngT  out: Durat on
  def cl entSub d: Str ng
  def requestScope: RequestScope
  def followGraphDataF eldsToFetch: SgsFollowGraphDataF elds.ValueSet

  protected lazy val cl entConf g: Underly ngCl entConf gurat on = conf g.underly ngCl ents

  protected lazy val cl entFactor es: Cl entWrapperFactor es = conf g.cl entWrapperFactor es
  protected lazy val g zmoduckCl ent: G zmoduckCl ent =
    cl entFactor es.g zmoduckCl entFactory.scope(requestScope)
  protected lazy val searchCl ent: SearchCl ent = newSearchCl ent(cl ent d = cl entSub d)
  protected lazy val t etyP eH ghQoSCl ent: T etyP eCl ent =
    cl entFactor es.t etyP eH ghQoSCl entFactory.scope(requestScope)
  protected lazy val t etyP eLowQoSCl ent: T etyP eCl ent =
    cl entFactor es.t etyP eLowQoSCl entFactory.scope(requestScope)
  protected lazy val followGraphDataProv der: FollowGraphDataProv der =
    new ScopedSgsFollowGraphDataProv derFactory(
      cl entFactor es.soc alGraphCl entFactory,
      cl entFactor es.v s b l yProf leHydratorFactory,
      followGraphDataF eldsToFetch,
      conf g.statsRece ver
    ).scope(requestScope)
  protected lazy val user tadataCl ent: ManhattanUser tadataCl ent =
    cl entFactor es.user tadataCl entFactory.scope(requestScope)
  protected lazy val userT etEnt yGraphCl ent: UserT etEnt yGraphCl ent =
    new UserT etEnt yGraphCl ent(
      conf g.underly ngCl ents.userT etEnt yGraphCl ent,
      conf g.statsRece ver
    )

  protected lazy val perRequestSearchCl ent dProv der: DependencyProv der[Opt on[Str ng]] =
    DependencyProv der { recapQuery: RecapQuery =>
      recapQuery.searchCl entSub d.map { sub d =>
        cl entConf g.t  l neRankerCl ent d(So (s"$sub d.$cl entSub d")).na 
      }
    }

  protected lazy val perRequestS ceSearchCl ent dProv der: DependencyProv der[Opt on[Str ng]] =
    DependencyProv der { recapQuery: RecapQuery =>
      recapQuery.searchCl entSub d.map { sub d =>
        cl entConf g.t  l neRankerCl ent d(So (s"$sub d.${cl entSub d}_s ce_t ets")).na 
      }
    }

  protected def newSearchCl ent(cl ent d: Str ng): SearchCl ent =
    new ScopedSearchCl entFactory(
      searchServ ceCl ent = earlyb rdCl ent(cl ent d),
      cl ent d = cl entConf g.t  l neRankerCl ent d(So (cl ent d)).na ,
      process ngT  out = So (searchProcess ngT  out),
      collectConversat on dGate = Gate.True,
      statsRece ver = conf g.statsRece ver
    ).scope(requestScope)

  protected def newSearchCl ent(
    earlyb rdReplace ntCl ent: Str ng => Earlyb rdServ ce. thodPerEndpo nt,
    cl ent d: Str ng
  ): SearchCl ent =
    new ScopedSearchCl entFactory(
      searchServ ceCl ent = earlyb rdReplace ntCl ent(cl ent d),
      cl ent d = cl entConf g.t  l neRankerCl ent d(So (cl ent d)).na ,
      process ngT  out = So (searchProcess ngT  out),
      collectConversat on dGate = Gate.True,
      statsRece ver = conf g.statsRece ver
    ).scope(requestScope)
}
