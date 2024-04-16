package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.t  l neranker.model._
 mport com.tw ter.ut l.Future

class Rout ngT  l neRepos ory(
  reverseChronT  l neRepos ory: ReverseChronHo T  l neRepos ory,
  rankedT  l neRepos ory: RankedHo T  l neRepos ory)
    extends T  l neRepos ory {

  overr de def get(query: T  l neQuery): Future[T  l ne] = {
    query match {
      case q: ReverseChronT  l neQuery => reverseChronT  l neRepos ory.get(q)
      case q: RankedT  l neQuery => rankedT  l neRepos ory.get(q)
      case _ =>
        throw new  llegalArgu ntExcept on(
          s"Query types ot r than RankedT  l neQuery and ReverseChronT  l neQuery are not supported. Found: $query"
        )
    }
  }

  overr de def get(quer es: Seq[T  l neQuery]): Seq[Future[T  l ne]] = {
    quer es.map(get)
  }
}
