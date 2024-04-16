package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.GroupByKey
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPrompt nject onsFeature
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r.Fl pPromptOffset nModuleFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Fl pPromptModuleGroup ng extends GroupByKey[P pel neQuery, Un versalNoun[Any],  nt] {
  overr de def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Opt on[ nt] = {
    val  nject on = cand dateFeatures.get(Fl pPrompt nject onsFeature)
    val offset nModule = cand dateFeatures.getOrElse(Fl pPromptOffset nModuleFeature, None)

    //   return None for any cand date that doesn't have an offset nModule, so that t y are left as  ndependent  ems.
    // Ot rw se,   return a hash of t   nject on  nstance wh ch w ll be used to aggregate cand dates w h match ng values  nto a module.
    offset nModule.map(_ =>  nject on.hashCode())
  }
}
