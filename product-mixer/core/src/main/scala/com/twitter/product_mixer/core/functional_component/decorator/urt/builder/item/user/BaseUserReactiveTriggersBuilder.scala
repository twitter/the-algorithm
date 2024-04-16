package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.user

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseUserCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.user.UserReact veTr ggers
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseUserReact veTr ggersBu lder[-Query <: P pel neQuery, -Cand date <: BaseUserCand date] {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[UserReact veTr ggers]
}
