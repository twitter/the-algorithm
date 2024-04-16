package com.tw ter.ho _m xer.product.follow ng.model

 mport com.tw ter.adserver.thr ftscala.Ho T  l neType
 mport com.tw ter.adserver.thr ftscala.T  l neRequestParams
 mport com.tw ter.ho _m xer.model.Ho AdsQuery
 mport com.tw ter.dspb dder.commons.{thr ftscala => dsp}
 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.onboard ng.task.serv ce.{thr ftscala => ots}
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.HasFl p nject onParams
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request._
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Params

case class Follow ngQuery(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val p pel neCursor: Opt on[UrtOrderedCursor],
  overr de val requestedMaxResults: Opt on[ nt],
  overr de val debugOpt ons: Opt on[DebugOpt ons],
  overr de val features: Opt on[FeatureMap],
  overr de val dev ceContext: Opt on[Dev ceContext],
  overr de val seenT et ds: Opt on[Seq[Long]],
  overr de val dspCl entContext: Opt on[dsp.DspCl entContext])
    extends P pel neQuery
    w h HasP pel neCursor[UrtOrderedCursor]
    w h HasDev ceContext
    w h HasSeenT et ds
    w h HasFl p nject onParams
    w h Ho AdsQuery {
  overr de val product: Product = Follow ngProduct

  overr de def w hFeatureMap(features: FeatureMap): Follow ngQuery =
    copy(features = So (features))

  overr de val t  l neRequestParams: Opt on[T  l neRequestParams] =
    So (T  l neRequestParams(ho T  l neType = So (Ho T  l neType.Ho Latest)))

  // F elds below are used for FL P  nject on  n Onboard ng Task Serv ce (OTS)
  overr de val d splayLocat on: ots.D splayLocat on = ots.D splayLocat on.Ho LatestT  l ne
  overr de val rank ngD sablerW hLatestControlsAva lable: Opt on[Boolean] = None
  overr de val  sEmptyState: Opt on[Boolean] = None
  overr de val  sF rstRequestAfterS gnup: Opt on[Boolean] = None
  overr de val  sEndOfT  l ne: Opt on[Boolean] = None
  overr de val t  l ne d: Opt on[Long] = None
}
