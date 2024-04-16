package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.t  l neranker.model.T  l ne
 mport com.tw ter.t  l neranker.model.T  l neQuery
 mport com.tw ter.ut l.Future

/**
 * A repos ory of ranked ho  t  l nes.
 */
class RankedHo T  l neRepos ory extends T  l neRepos ory {
  def get(quer es: Seq[T  l neQuery]): Seq[Future[T  l ne]] = {
    quer es.map { _ =>
      Future.except on(new UnsupportedOperat onExcept on("ranked t  l nes are not yet supported."))
    }
  }
}
