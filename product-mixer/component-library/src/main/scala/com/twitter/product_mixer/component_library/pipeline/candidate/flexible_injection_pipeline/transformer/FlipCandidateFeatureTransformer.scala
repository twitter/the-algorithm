package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.flex ble_ nject on_p pel ne.transfor r

 mport com.tw ter.onboard ng. nject ons.{thr ftscala => onboard ngthr ft}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.flex ble_ nject on_p pel ne. nter d atePrompt
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BasePromptCand date
 mport com.tw ter.product_m xer.component_l brary.model.cand date.PromptCarouselT leCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Transfor r dent f er

case object Fl pPromptCarouselT leFeature
    extends Feature[PromptCarouselT leCand date, Opt on[onboard ngthr ft.T le]]

case object Fl pPrompt nject onsFeature
    extends Feature[BasePromptCand date[Str ng], onboard ngthr ft. nject on]

case object Fl pPromptOffset nModuleFeature
    extends Feature[PromptCarouselT leCand date, Opt on[ nt]]

object Fl pCand dateFeatureTransfor r extends Cand dateFeatureTransfor r[ nter d atePrompt] {

  overr de val  dent f er: Transfor r dent f er = Transfor r dent f er("Fl pCand dateFeature")

  overr de val features: Set[Feature[_, _]] =
    Set(Fl pPrompt nject onsFeature, Fl pPromptOffset nModuleFeature, Fl pPromptCarouselT leFeature)

  /** Hydrates a [[FeatureMap]] for a g ven [[ nputs]] */
  overr de def transform( nput:  nter d atePrompt): FeatureMap = {
    FeatureMapBu lder()
      .add(Fl pPrompt nject onsFeature,  nput. nject on)
      .add(Fl pPromptOffset nModuleFeature,  nput.offset nModule)
      .add(Fl pPromptCarouselT leFeature,  nput.carouselT le)
      .bu ld()
  }
}
