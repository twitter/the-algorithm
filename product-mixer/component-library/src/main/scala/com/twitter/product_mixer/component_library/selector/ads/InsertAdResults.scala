package com.tw ter.product_m xer.component_l brary.selector.ads

 mport com.tw ter.goldf nch.ap .Ads nject onSurfaceAreas.SurfaceAreaNa 
 mport com.tw ter.goldf nch.ap .Ads njectorAdd  onalRequestParams
 mport com.tw ter.goldf nch.ap .Ads njectorOutput
 mport com.tw ter.goldf nch.ap .{Ads njector => Goldf nchAds njector}
 mport com.tw ter.product_m xer.component_l brary.model.query.ads._
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 *  njects t  sequence of AdCand dates  n t  `result`  n t 
 * sequence of t  Ot r Cand dates(wh ch are not ads).
 *
 * Every SurfaceArea or D splayLocat on runs t  r own des red set of adjusters(set  n p pel ne)
 * to  nject ads and repos  on t  ads  n t  sequence of ot r cand dates of `result` :
 * wh ch are fetc d by Ads nject onSurfaceAreaAdjustersMap
 * Note: T  or g nal sequence of non_promoted entr es(non-ads)  s reta ned and t  ads
 * are  nserted  n t  sequence us ng `goldf nch` l brary based on t  ' nsert on-pos  on'
 * hydrated  n AdsCand date by Adserver/Adm xer.
 *
 * ***** Goldf nch recom nds to run t  selector as close to t  marshall ng of cand dates to have
 * more real st c v ew of served-t  l ne  n Goldf nch-BQ-Logs and avo d any furt r updates on t 
 * t  l ne(sequence of entr es) created. ****
 *
 * Any surface area l ke `search_t ets(surface_area)` can call
 *  nsertAdResults(surfaceArea = "T etSearch", cand dateP pel ne = adsCand dateP pel ne. dent f er,
 * ProductM xerAds njector = productM xerAds njector)
 * w re t  p pel ne conf g can call
 * productM xerAds njector.forSurfaceArea("T etSearch") to get Ads njector Object
 *
 * @example
 * `Seq(s ce1NonAd_ d1, s ce1NonAd_ d2, s ce2NonAd_ d1, s ce2NonAd_ d2,s ce1NonAd_ d3, s ce3NonAd_ d3,s ce3Ad_ d1_ nsert onPos1, s ce3Ad_ d2_ nsert onPos4)`
 * t n t  output result can be
 * `Seq(s ce1NonAd_ d1, s ce3Ad_ d1_ nsert onPos1, s ce1NonAd_ d2, s ce2NonAd_ d1, s ce3Ad_ d2_ nsert onPos4,s ce2NonAd_ d2, s ce1NonAd_ d3, s ce3NonAd_ d3)`
 * depend ng on t   nsert on pos  on of Ads and ot r adjusters sh ft ng t  ads
 */
case class  nsertAdResults(
  surfaceAreaNa : SurfaceAreaNa ,
  ads njector: Goldf nchAds njector[
    P pel neQuery w h AdsQuery,
    Cand dateW hDeta ls,
    Cand dateW hDeta ls
  ],
  adsCand dateP pel ne: Cand dateP pel ne dent f er)
    extends Selector[P pel neQuery w h AdsQuery] {

  overr de val p pel neScope: Cand dateScope = Spec f cP pel ne(adsCand dateP pel ne)

  overr de def apply(
    query: P pel neQuery w h AdsQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    // Read  nto ads and non-ads cand dates.
    val Part  onedCand dates(adCand dates, ot rRema n ngCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    // Create t  param from Query/AdsCand date based on surface_area,  f requ red.
    val ads njectorAdd  onalRequestParams =
      Ads njectorAdd  onalRequestParams(budgetAwareExper  nt d = None)

    val ads njectorOutput: Ads njectorOutput[Cand dateW hDeta ls, Cand dateW hDeta ls] =
      ads njector.applyForAllEntr es(
        query = query,
        nonPromotedEntr es = result,
        promotedEntr es = adCand dates,
        ads njectorAdd  onalRequestParams = ads njectorAdd  onalRequestParams)

    val updatedRema n ngCand dates = ot rRema n ngCand dates ++
      Goldf nchResults(ads njectorOutput.unusedEntr es).adapt
    val  rgedResults = Goldf nchResults(ads njectorOutput. rgedEntr es).adapt
    SelectorResult(rema n ngCand dates = updatedRema n ngCand dates, result =  rgedResults)
  }

  /**
   * Goldf nch separates NonPromotedEntryType and PromotedEntryType models, wh le  n ProM x both
   * non-promoted and promoted entr es are Cand dateW hDeta ls. As such,   need to flatten t 
   * result back  nto a s ngle Seq of Cand dateW hDeta ls. See [[Ads njectorOutput]]
   */
  case class Goldf nchResults(results: Seq[E  r[Cand dateW hDeta ls, Cand dateW hDeta ls]]) {
    def adapt: Seq[Cand dateW hDeta ls] = {
      results.collect {
        case R ght(value) => value
        case Left(value) => value
      }
    }
  }
}
