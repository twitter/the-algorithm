package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.t  l neranker.model.ReverseChronT  l neQuery
 mport com.tw ter.t  l neranker.model.T  l ne
 mport com.tw ter.t  l neranker.para ters.revchron.ReverseChronT  l neQueryContextBu lder
 mport com.tw ter.t  l neranker.s ce.ReverseChronHo T  l neS ce
 mport com.tw ter.ut l.Future

/**
 * A repos ory of reverse-chron ho  t  l nes.
 *
 *   does not cac  any results t refore forwards all calls to t  underly ng s ce.
 */
class ReverseChronHo T  l neRepos ory(
  s ce: ReverseChronHo T  l neS ce,
  contextBu lder: ReverseChronT  l neQueryContextBu lder) {
  def get(query: ReverseChronT  l neQuery): Future[T  l ne] = {
    contextBu lder(query).flatMap(s ce.get)
  }
}
