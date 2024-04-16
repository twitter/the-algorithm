package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.AdsCand dateP pel neQueryTransfor r.bu ldAdRequestParams
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Transform a P pel neQuery w h AdsQuery  nto an AdsRequestParams
 *
 * @param adsD splayLocat onBu lder Bu lder that determ nes t  d splay locat on for t  ads
 * @param est matedNumOrgan c ems  Est mate for t  number of organ c  ems that w ll be served
 *                                  alongs de  norgan c  ems such as ads. 
 */
case class AdsCand dateP pel neQueryTransfor r[Query <: P pel neQuery w h AdsQuery](
  adsD splayLocat onBu lder: AdsD splayLocat onBu lder[Query],
  est matedNumOrgan c ems: Est mateNumOrgan c ems[Query],
  urtRequest: Opt on[Boolean],
) extends Cand dateP pel neQueryTransfor r[Query, ads.AdRequestParams] {

  overr de def transform(query: Query): ads.AdRequestParams =
    bu ldAdRequestParams(
      query = query,
      adsD splayLocat on = adsD splayLocat onBu lder(query),
      organ c em ds = None,
      numOrgan c ems = So (est matedNumOrgan c ems(query)),
      urtRequest = urtRequest
    )
}

object AdsCand dateP pel neQueryTransfor r {

  def bu ldAdRequestParams(
    query: P pel neQuery w h AdsQuery,
    adsD splayLocat on: ads.D splayLocat on,
    organ c em ds: Opt on[Seq[Long]],
    numOrgan c ems: Opt on[Short],
    urtRequest: Opt on[Boolean],
  ): ads.AdRequestParams = {
    val searchRequestContext = query.searchRequestContext
    val queryStr ng = query.searchRequestContext.flatMap(_.queryStr ng)

    val adRequest = ads.AdRequest(
      queryStr ng = queryStr ng, 
      d splayLocat on = adsD splayLocat on,
      searchRequestContext = searchRequestContext,
      organ c em ds = organ c em ds,
      numOrgan c ems = numOrgan c ems,
      prof leUser d = query.userProf leV e dUser d,
       sDebug = So (false),
       sTest = So (false),
      requestTr ggerType = query.requestTr ggerType,
      d sableNsfwAvo dance = query.d sableNsfwAvo dance,
      t  l neRequestParams = query.t  l neRequestParams,
    )

    val context = query.cl entContext

    val cl ent nfo = ads.Cl ent nfo(
      cl ent d = context.app d.map(_.to nt),
      user d64 = context.user d,
      user p = context. pAddress,
      guest d = context.guest dAds,
      userAgent = context.userAgent,
      dev ce d = context.dev ce d,
      languageCode = context.languageCode,
      countryCode = context.countryCode,
      mob leDev ce d = context.mob leDev ce d,
      mob leDev ceAd d = context.mob leDev ceAd d,
      l m AdTrack ng = context.l m AdTrack ng,
      autoplayEnabled = query.autoplayEnabled,
      urtRequest = urtRequest,
      dspCl entContext = query.dspCl entContext
    )

    ads.AdRequestParams(adRequest, cl ent nfo)
  }
}
