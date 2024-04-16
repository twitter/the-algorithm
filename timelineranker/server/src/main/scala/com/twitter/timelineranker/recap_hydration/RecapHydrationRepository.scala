package com.tw ter.t  l neranker.recap_hydrat on

 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future

/**
 * A repos ory of recap hydrat on results.
 *
 * For now,   does not cac  any results t refore forwards all calls to t  underly ng s ce.
 */
class RecapHydrat onRepos ory(s ce: RecapHydrat onS ce) {
  def hydrate(query: RecapQuery): Future[Cand dateT etsResult] = {
    s ce.hydrate(query)
  }

  def hydrate(quer es: Seq[RecapQuery]): Future[Seq[Cand dateT etsResult]] = {
    s ce.hydrate(quer es)
  }
}
