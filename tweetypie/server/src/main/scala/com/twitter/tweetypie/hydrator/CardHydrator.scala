package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.expandodo.thr ftscala.Card
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object CardHydrator {
  type Type = ValueHydrator[Opt on[Seq[Card]], Ctx]

  case class Ctx(
    urlEnt  es: Seq[UrlEnt y],
     d aEnt  es: Seq[ d aEnt y],
    underly ngT etCtx: T etCtx)
      extends T etCtx.Proxy

  val hydratedF eld: F eldByPath = f eldByPath(T et.CardsF eld)

  pr vate[t ] val part alResult = ValueState.part al(None, hydratedF eld)

  def apply(repo: CardRepos ory.Type): Type = {
    def getCards(url: Str ng): St ch[Seq[Card]] =
      repo(url).handle { case NotFound => N l }

    ValueHydrator[Opt on[Seq[Card]], Ctx] { (_, ctx) =>
      val urls = ctx.urlEnt  es.map(_.url)

      St ch.traverse(urls)(getCards _).l ftToTry.map {
        case Return(cards) =>
          // even though   are hydrat ng a type of Opt on[Seq[Card]],   only
          // ever return at most one card, and always t  last one.
          val res = cards.flatten.lastOpt on.toSeq
           f (res. sEmpty) ValueState.Unmod f edNone
          else ValueState.mod f ed(So (res))
        case _ => part alResult
      }
    }.only f { (curr, ctx) =>
      curr. sEmpty &&
      ctx.t etF eldRequested(T et.CardsF eld) &&
      !ctx. sRet et &&
      ctx. d aEnt  es. sEmpty
    }
  }
}
