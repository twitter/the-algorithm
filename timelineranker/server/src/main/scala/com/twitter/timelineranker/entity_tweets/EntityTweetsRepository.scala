package com.tw ter.t  l neranker.ent y_t ets

 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future

/**
 * A repos ory of ent y t ets cand dates.
 *
 * For now,   does not cac  any results t refore forwards all calls to t  underly ng s ce.
 */
class Ent yT etsRepos ory(s ce: Ent yT etsS ce) {
  def get(query: RecapQuery): Future[Cand dateT etsResult] = {
    s ce.get(query)
  }

  def get(quer es: Seq[RecapQuery]): Future[Seq[Cand dateT etsResult]] = {
    s ce.get(quer es)
  }
}
