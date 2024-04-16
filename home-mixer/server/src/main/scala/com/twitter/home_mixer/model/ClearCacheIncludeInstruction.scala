package com.tw ter.ho _m xer.model

 mport com.tw ter.ho _m xer.model.request.Dev ceContext.RequestContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder. nclude nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T et em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

/**
 *  nclude a clear cac  t  l ne  nstruct on w n   sat sfy t se cr er a:
 * - Request Provenance  s "pull to refresh"
 * - Atleast N non-ad t et entr es  n t  response
 *
 * T   s to ensure that   have suff c ent new content to just fy jump ng users to t 
 * top of t  new t  l nes response and don't add unnecessary load to backend systems
 */
case class ClearCac  nclude nstruct on(
  enableParam: FSParam[Boolean],
  m nEntr esParam: FSBoundedParam[ nt])
    extends  nclude nstruct on[P pel neQuery w h HasDev ceContext] {

  overr de def apply(
    query: P pel neQuery w h HasDev ceContext,
    entr es: Seq[T  l neEntry]
  ): Boolean = {
    val enabled = query.params(enableParam)

    val ptr =
      query.dev ceContext.flatMap(_.requestContextValue).conta ns(RequestContext.PullToRefresh)

    val m nT ets = query.params(m nEntr esParam) <= entr es.collect {
      case  em: T et em  f  em.promoted tadata. sEmpty => 1
      case module: T  l neModule  f module. ems. ad. em. s nstanceOf[T et em] =>
        module. ems.s ze
    }.sum

    enabled && ptr && m nT ets
  }
}
