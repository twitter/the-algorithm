package com.tw ter.t  l neranker. n_network_t ets

 mport com.tw ter.t  l neranker.model.Cand dateT etsResult
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.t  l neranker.model.RecapQuery.DependencyProv der
 mport com.tw ter.t  l neranker.para ters. n_network_t ets. nNetworkT etParams
 mport com.tw ter.ut l.Future

/**
 * A repos ory of  n-network t et cand dates.
 * For now,   does not cac  any results t refore forwards all calls to t  underly ng s ce.
 */
class  nNetworkT etRepos ory(
  s ce:  nNetworkT etS ce,
  realt  CGS ce:  nNetworkT etS ce) {

  pr vate[t ] val enableRealt  CGProv der =
    DependencyProv der.from( nNetworkT etParams.EnableEarlyb rdRealt  CgM grat onParam)

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
