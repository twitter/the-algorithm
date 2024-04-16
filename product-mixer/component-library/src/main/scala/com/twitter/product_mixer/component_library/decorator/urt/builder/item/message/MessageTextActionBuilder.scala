package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em. ssage

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssageTextAct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object  ssageTextAct onBu lder {
  val  ssageTextAct onCl entEvent nfoEle nt: Str ng = " ssage-text-act on"
}

case class  ssageTextAct onBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  textBu lder: BaseStr[Query, Cand date],
  d sm ssOnCl ck: Boolean,
  url: Opt on[Str ng] = None,
  cl entEvent nfo: Opt on[Cl entEvent nfo] = None,
  onCl ckCallbacks: Opt on[L st[Callback]] = None) {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ):  ssageTextAct on =  ssageTextAct on(
    text = textBu lder(query, cand date, cand dateFeatures),
    act on =  ssageAct on(
      d sm ssOnCl ck,
      url,
      cl entEvent nfo,
      onCl ckCallbacks
    )
  )
}
