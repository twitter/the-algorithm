package com.tw ter.t  l neranker.uteg_l ked_by_t ets

 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future

/**
 * A repos ory of YML t ets cand d ates
 */
class UtegL kedByT etsRepos ory(s ce: UtegL kedByT etsS ce) {
  def get(query: RecapQuery): Future[Cand dateT etsResult] = {
    s ce.get(query)
  }

  def get(quer es: Seq[RecapQuery]): Future[Seq[Cand dateT etsResult]] = {
    s ce.get(quer es)
  }
}
