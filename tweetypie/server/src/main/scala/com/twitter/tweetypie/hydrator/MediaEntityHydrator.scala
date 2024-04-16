package com.tw ter.t etyp e
package hydrator

 mport com.tw ter. d aserv ces.commons.thr ftscala. d aKey
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

object  d aEnt  esHydrator {
  object Cac able {
    type Ctx =  d aEnt yHydrator.Cac able.Ctx
    type Type = ValueHydrator[Seq[ d aEnt y], Ctx]

    def once(h:  d aEnt yHydrator.Cac able.Type): Type =
      T etHydrat on.completeOnlyOnce(
        queryF lter =  d aEnt yHydrator.queryF lter,
        hydrat onType = Hydrat onType.Cac able d a,
        dependsOn = Set(Hydrat onType.Urls),
        hydrator = h.l ftSeq
      )
  }

  object Uncac able {
    type Ctx =  d aEnt yHydrator.Uncac able.Ctx
    type Type = ValueHydrator[Seq[ d aEnt y], Ctx]
  }
}

object  d aEnt yHydrator {
  val hydratedF eld: F eldByPath = f eldByPath(T et. d aF eld)

  object Cac able {
    type Type = ValueHydrator[ d aEnt y, Ctx]

    case class Ctx(urlEnt  es: Seq[UrlEnt y], underly ngT etCtx: T etCtx) extends T etCtx.Proxy

    /**
     * Bu lds a s ngle  d a-hydrator out of f ner-gra ned hydrators
     * only w h cac able  nformat on.
     */
    def apply(hydrate d aUrls: Type, hydrate d a sProtected: Type): Type =
      hydrate d aUrls.andT n(hydrate d a sProtected)
  }

  object Uncac able {
    type Type = ValueHydrator[ d aEnt y, Ctx]

    case class Ctx( d aKeys: Opt on[Seq[ d aKey]], underly ngT etCtx: T etCtx)
        extends T etCtx.Proxy {

      def  nclude d aEnt  es: Boolean = t etF eldRequested(T et. d aF eld)
      def  ncludeAdd  onal tadata: Boolean =
        opts. nclude. d aF elds.conta ns( d aEnt y.Add  onal tadataF eld. d)
    }

    /**
     * Bu lds a s ngle  d a-hydrator out of f ner-gra ned hydrators
     * only w h uncac able  nformat on.
     */
    def apply(hydrate d aKey: Type, hydrate d a nfo: Type): Type =
      (hydrate d aKey
        .andT n(hydrate d a nfo))
        .only f((_, ctx) => ctx. nclude d aEnt  es)
  }

  def queryF lter(opts: T etQuery.Opt ons): Boolean =
    opts. nclude.t etF elds.conta ns(T et. d aF eld. d)
}
