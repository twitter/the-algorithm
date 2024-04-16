package com.tw ter.t  l neranker.recap_author

 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.para ters.recap_author.RecapAuthorParams

/**
 * A repos ory of recap author results.
 *
 * For now,   does not cac  any results t refore forwards all calls to t  underly ng s ce.
 */
class RecapAuthorRepos ory(s ce: RecapAuthorS ce, realt  CGS ce: RecapAuthorS ce) {
  pr vate[t ] val enableRealt  CGProv der =
    DependencyProv der.from(RecapAuthorParams.EnableEarlyb rdRealt  CgM grat onParam)

  def get(query: RecapQuery): Future[Cand dateT etsResult] = {
     f (enableRealt  CGProv der(query)) {
      realt  CGS ce.get(query)
    } else {
      s ce.get(query)
    }
  }

  def get(quer es: Seq[RecapQuery]): Future[Seq[Cand dateT etsResult]] = {
    Future.collect(quer es.map(query => get(query)))
  }
}