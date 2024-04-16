package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads.AdsCand dateP pel neQueryTransfor r.bu ldAdRequestParams
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.DependentCand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Transform a P pel neQuery w h AdsQuery  nto an AdsRequestParams
 *
 * @param adsD splayLocat onBu lder Bu lder that determ nes t  d splay locat on for t  ads
 * @param countNumOrgan c ems      Count organ c  ems from t  response 
 */
case class AdsDependentCand dateP pel neQueryTransfor r[Query <: P pel neQuery w h AdsQuery](
  adsD splayLocat onBu lder: AdsD splayLocat onBu lder[Query],
  getOrgan c em ds: GetOrgan c em ds,
  countNumOrgan c ems: CountNumOrgan c ems[Query],
  urtRequest: Opt on[Boolean],
) extends DependentCand dateP pel neQueryTransfor r[Query, ads.AdRequestParams] {

  overr de def transform(
    query: Query,
    prev ousCand dates: Seq[Cand dateW hDeta ls]
  ): ads.AdRequestParams = bu ldAdRequestParams(
    query = query,
    adsD splayLocat on = adsD splayLocat onBu lder(query),
    organ c em ds = getOrgan c em ds.apply(prev ousCand dates),
    numOrgan c ems = So (countNumOrgan c ems.apply(query, prev ousCand dates)),
    urtRequest = urtRequest
  )
}
