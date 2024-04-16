package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.expandodo.thr ftscala._
 mport com.tw ter.st ch.MapGroup
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.backends.Expandodo

object CardRepos ory {
  type Type = Str ng => St ch[Seq[Card]]

  def apply(getCards: Expandodo.GetCards, maxRequestS ze:  nt): Type = {
    object RequestGroup extends MapGroup[Str ng, Seq[Card]] {
      overr de def run(urls: Seq[Str ng]): Future[Str ng => Try[Seq[Card]]] =
        getCards(urls.toSet).map { responseMap => url =>
          responseMap.get(url) match {
            case None => Throw(NotFound)
            case So (r) => Return(r.cards.getOrElse(N l))
          }
        }

      overr de def maxS ze:  nt = maxRequestS ze
    }

    url => St ch.call(url, RequestGroup)
  }
}
