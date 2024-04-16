package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.expandodo.thr ftscala.Card2
 mport com.tw ter.expandodo.thr ftscala.Card2RequestOpt ons
 mport com.tw ter.featuresw c s.v2.FeatureSw chResults
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.CardReferenceUr Extractor
 mport com.tw ter.t etyp e.core.NonTombstone
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object Card2Hydrator {
  type Type = ValueHydrator[Opt on[Card2], Ctx]

  case class Ctx(
    urlEnt  es: Seq[UrlEnt y],
     d aEnt  es: Seq[ d aEnt y],
    cardReference: Opt on[CardReference],
    underly ngT etCtx: T etCtx,
    featureSw chResults: Opt on[FeatureSw chResults])
      extends T etCtx.Proxy

  val hydratedF eld: F eldByPath = f eldByPath(T et.Card2F eld)
  val hydrat onUrlBlockL stKey = "card_hydrat on_blockl st"

  def apply(repo: Card2Repos ory.Type): ValueHydrator[Opt on[Card2], Ctx] =
    ValueHydrator[Opt on[Card2], Ctx] { (_, ctx) =>
      val repoCtx = requestOpt ons(ctx)
      val f lterURLs = ctx.featureSw chResults
        .flatMap(_.getStr ngArray(hydrat onUrlBlockL stKey, false))
        .getOrElse(Seq())

      val requests =
        ctx.cardReference match {
          case So (CardReferenceUr Extractor(cardUr )) =>
            cardUr  match {
              case NonTombstone(ur )  f !f lterURLs.conta ns(ur ) =>
                Seq((UrlCard2Key(ur ), repoCtx))
              case _ => N l
            }
          case _ =>
            ctx.urlEnt  es
              .f lterNot(e => e.expanded.ex sts(f lterURLs.conta ns))
              .map(e => (UrlCard2Key(e.url), repoCtx))
        }

      St ch
        .traverse(requests) {
          case (key, opts) => repo(key, opts).l ftNotFoundToOpt on
        }.l ftToTry.map {
          case Return(results) =>
            results.flatten.lastOpt on match {
              case None => ValueState.Unmod f edNone
              case res => ValueState.mod f ed(res)
            }
          case Throw(_) => ValueState.part al(None, hydratedF eld)
        }
    }.only f { (curr, ctx) =>
      curr. sEmpty &&
      ctx.t etF eldRequested(T et.Card2F eld) &&
      ctx.opts.cardsPlatformKey.nonEmpty &&
      !ctx. sRet et &&
      ctx. d aEnt  es. sEmpty &&
      (ctx.cardReference.nonEmpty || ctx.urlEnt  es.nonEmpty)
    }

  pr vate[t ] def requestOpt ons(ctx: Ctx) =
    Card2RequestOpt ons(
      platformKey = ctx.opts.cardsPlatformKey.get,
      perspect veUser d = ctx.opts.forUser d,
      allowNonTcoUrls = ctx.cardReference.nonEmpty,
      languageTag = So (ctx.opts.languageTag)
    )
}
