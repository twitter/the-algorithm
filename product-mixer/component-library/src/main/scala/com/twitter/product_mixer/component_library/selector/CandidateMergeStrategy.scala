package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date. sP nnedFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateP pel nes
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS ces
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateS cePos  on

/**
 * Once a pa r of dupl cate cand dates has been found   need to so one 'resolve' t  dupl cat on.
 * T  may be as s mple as p ck ng wh c ver cand date ca  f rst (see [[P ckF rstCand date rger]]
 * but t  strategy could  an los ng  mportant cand date  nformat on. Cand dates m ght, for
 * example, have d fferent features. [[Cand date rgeStrategy]] lets   def ne a custom behav or
 * for resolv ng dupl cat on to  lp support t se more nuanced s uat ons.
 */
tra  Cand date rgeStrategy {
  def apply(
    ex st ngCand date:  emCand dateW hDeta ls,
    newCand date:  emCand dateW hDeta ls
  ):  emCand dateW hDeta ls
}

/**
 * Keep wh c ver cand date was encountered f rst.
 */
object P ckF rstCand date rger extends Cand date rgeStrategy {
  overr de def apply(
    ex st ngCand date:  emCand dateW hDeta ls,
    newCand date:  emCand dateW hDeta ls
  ):  emCand dateW hDeta ls = ex st ngCand date
}

/**
 * Keep t  cand date encountered f rst but comb ne all cand date feature maps.
 */
object Comb neFeatureMapsCand date rger extends Cand date rgeStrategy {
  overr de def apply(
    ex st ngCand date:  emCand dateW hDeta ls,
    newCand date:  emCand dateW hDeta ls
  ):  emCand dateW hDeta ls = {
    // Prepend new because l st set keeps  nsert on order, and last operat ons  n L stSet are O(1)
    val  rgedCand dateS ce dent f ers =
      newCand date.features.get(Cand dateS ces) ++ ex st ngCand date.features
        .get(Cand dateS ces)
    val  rgedCand dateP pel ne dent f ers =
      newCand date.features.get(Cand dateP pel nes) ++ ex st ngCand date.features
        .get(Cand dateP pel nes)

    // t  un ary features are pulled from t  ex st ng cand date as expla ned above, wh le
    // Set Features are  rged/accumulated.
    val  rgedCommonFeatureMap = FeatureMapBu lder()
      .add(Cand dateP pel nes,  rgedCand dateP pel ne dent f ers)
      .add(Cand dateS ces,  rgedCand dateS ce dent f ers)
      .add(Cand dateS cePos  on, ex st ngCand date.s cePos  on)
      .bu ld()

    ex st ngCand date.copy(features =
      ex st ngCand date.features ++ newCand date.features ++  rgedCommonFeatureMap)
  }
}

/**
 * Keep t  p nnable cand date. For cases w re   are deal ng w h dupl cate entr es across
 * d fferent cand date types, such as d fferent sub-classes of
 * [[com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date]],   w ll
 * pr or  ze t  cand date w h [[ sP nnedFeature]] because   conta ns add  onal  nformat on
 * needed for t  pos  on ng of a p nned entry on a t  l ne.
 */
object P ckP nnedCand date rger extends Cand date rgeStrategy {
  overr de def apply(
    ex st ngCand date:  emCand dateW hDeta ls,
    newCand date:  emCand dateW hDeta ls
  ):  emCand dateW hDeta ls =
    Seq(ex st ngCand date, newCand date)
      .collectF rst {
        case cand date @  emCand dateW hDeta ls(_: BaseT etCand date, _, features)
             f features.getTry( sP nnedFeature).toOpt on.conta ns(true) =>
          cand date
      }.getOrElse(ex st ngCand date)
}
