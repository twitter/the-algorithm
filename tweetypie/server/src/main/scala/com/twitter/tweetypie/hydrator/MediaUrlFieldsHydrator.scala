package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e. d a. d a
 mport com.tw ter.t etyp e. d a. d aUrl
 mport com.tw ter.t etyp e.thr ftscala._

object  d aUrlF eldsHydrator {
  type Ctx =  d aEnt yHydrator.Cac able.Ctx
  type Type =  d aEnt yHydrator.Cac able.Type

  def  d aPermal nk(ctx: Ctx): Opt on[UrlEnt y] =
    ctx.urlEnt  es.v ew.reverse.f nd( d aUrl.Permal nk.hasT et d(_, ctx.t et d))

  def apply(): Type =
    ValueHydrator
      .map[ d aEnt y, Ctx] { (curr, ctx) =>
         d aPermal nk(ctx) match {
          case None => ValueState.unmod f ed(curr)
          case So (urlEnt y) => ValueState.mod f ed( d a.copyFromUrlEnt y(curr, urlEnt y))
        }
      }
      .only f((curr, ctx) => curr.url == null &&  d a. sOwn d a(ctx.t et d, curr))
}
