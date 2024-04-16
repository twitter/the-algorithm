package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.dataproducts.enr ch nts.thr ftscala.Prof leGeoEnr ch nt
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.Prof leGeoKey
 mport com.tw ter.t etyp e.repos ory.Prof leGeoRepos ory
 mport com.tw ter.t etyp e.thr ftscala.F eldByPath

object Prof leGeoHydrator {
  type Type = ValueHydrator[Opt on[Prof leGeoEnr ch nt], T etCtx]

  val hydratedF eld: F eldByPath = f eldByPath(T et.Prof leGeoEnr ch ntF eld)

  pr vate[t ] val part alResult = ValueState.part al(None, hydratedF eld)

  def apply(repo: Prof leGeoRepos ory.Type): Type =
    ValueHydrator[Opt on[Prof leGeoEnr ch nt], T etCtx] { (curr, ctx) =>
      val key =
        Prof leGeoKey(
          t et d = ctx.t et d,
          user d = So (ctx.user d),
          coords = ctx.geoCoord nates
        )
      repo(key).l ftToTry.map {
        case Return(enr ch nt) => ValueState.mod f ed(So (enr ch nt))
        case Throw(_) => part alResult
      }
    }.only f((curr, ctx) =>
      curr. sEmpty && ctx.t etF eldRequested(T et.Prof leGeoEnr ch ntF eld))
}
