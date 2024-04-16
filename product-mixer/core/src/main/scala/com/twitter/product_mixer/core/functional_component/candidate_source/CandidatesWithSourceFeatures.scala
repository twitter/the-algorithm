package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

/**
 * Results from a cand date s ce, opt onally carry ng extracted query level features to add
 * to t  query's feature map (e.g, extract ng reusable features from t  thr ft response of thr ft
 * call).
 * @param cand dates T  cand dates returned from t  underly ng Cand dateS e
 * @param features [[FeatureMap]] conta n ng t  features from t  cand date s ce
 *                                    to  rge back  nto t  P pel neQuery FeatureMap.
 * @tparam Cand date T  type of result
 */
case class Cand datesW hS ceFeatures[+Cand date](
  cand dates: Seq[Cand date],
  features: FeatureMap)
